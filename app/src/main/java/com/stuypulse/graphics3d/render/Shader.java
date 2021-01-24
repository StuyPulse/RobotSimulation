package com.stuypulse.graphics3d.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import com.stuypulse.graphics3d.Window;
import com.stuypulse.stuylib.math.SLMath;

import org.joml.Matrix4f;

public final class Shader implements GlObject {

    // Helpers
    private static final int createShader(String text, int type) {
        int ptr = glCreateShader(type);
        glShaderSource(ptr, text);
        glCompileShader(ptr);

        if (glGetShaderi(ptr, GL_COMPILE_STATUS) == GL_FALSE) {
            throw new IllegalArgumentException(
                "Shader could not be compiled: \n" +
                glGetShaderInfoLog(ptr) +
                "\n\nSource:\n" +
                text
            );
        }

        return ptr;
    }

    private static final void handleProgramError(int program, int catchFor) {
        if (glGetProgrami(program, catchFor) == GL_FALSE) {
            throw new IllegalArgumentException("Program returned an error: " + glGetProgramInfoLog(program));
        }
    }

    private static final String getTextFromFile(String path) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
            StringBuilder output = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                output
                    .append(line)
                    .append("\n");
            }

            reader.close();
        
            return output.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
      
        }
    } 

    private static final int setupUniform(int program, String name) {
        int uniformPtr = glGetUniformLocation(program, name); 
        
        if (uniformPtr == -1) {
            throw new IllegalStateException("Unable to get uniform location (-1) for " + name + " in program (" + program + ")");
        }

        return uniformPtr;
    }

    private static final float[] getFloatsFromMat(Matrix4f in) {
        float[] out = new float[16];
        in.get(out);
        return out;
    }

    // Constructors
    public static final Shader fromFiles(String vPath, String fPath) {
        return fromText(
            getTextFromFile(vPath), 
            getTextFromFile(fPath)
        );
    }

    public static final Shader fromFiles(String path) {
        return fromFiles(path + ".vs", path + ".fs");
    }

    public static final Shader fromText(String vShader, String fShader) {
        return new Shader(vShader, fShader);
    }

    // Shader class
    private final int vertexShader, fragmentShader, program;

    private final int uProject, uTransform, uCamera;
    private final int uColor;

    private Shader(String vertexText, String fragText) {
        Window.addObject(this);

        vertexShader = createShader(vertexText, GL_VERTEX_SHADER);
        fragmentShader = createShader(fragText, GL_FRAGMENT_SHADER);

        // create program
        program = glCreateProgram();
        glAttachShader(program, vertexShader);
        glAttachShader(program, fragmentShader);

        glLinkProgram(program);
        handleProgramError(program, GL_LINK_STATUS);
        glValidateProgram(program);
        handleProgramError(program, GL_VALIDATE_STATUS);

        uProject = setupUniform(program, "projection");
        uTransform = setupUniform(program, "transform");
        uCamera = setupUniform(program, "view");

        uColor = setupUniform(program, "surfaceColor");
    }

    public void destroy() {
        glDetachShader(program, vertexShader);
        glDetachShader(program, fragmentShader);
        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
        glDeleteProgram(program);
    }

    // Setup Shader


    private final static boolean TRANSPOSE = false;

    protected void setColor(float r, float g, float b) {
        glUniform3fv(uColor, new float[] { r, g, b });
    }

    protected void setColor(int r, int g, int b) {
        glUniform3fv(
            uColor,
            new float[] {
                (float) SLMath.limit(r/255.0, 0, 1),
                (float) SLMath.limit(r/255.0, 0, 1),
                (float) SLMath.limit(r/255.0, 0, 1)
            }
        );
    }

    protected void useCamera(Camera camera) {
        glUniformMatrix4fv(
            uProject, 
            TRANSPOSE, 
            getFloatsFromMat(camera.getProjection())
        );

        glUniformMatrix4fv(
            uCamera, 
            TRANSPOSE, 
            getFloatsFromMat(camera.getView())
        );
    }
    
    protected void useTransform(Transform transform) {
        glUniformMatrix4fv(
            uTransform,
            TRANSPOSE,
            getFloatsFromMat(transform.getTransform())
        );
    }

    protected void use() {
        glUseProgram(program);
    }

}
