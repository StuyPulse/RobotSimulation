package com.stuypulse.graphics3d.globject;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import com.stuypulse.graphics3d.Window;

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
    }

    public void destroy() {
        glDetachShader(program, vertexShader);
        glDetachShader(program, fragmentShader);
        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
        glDeleteProgram(program);
    }

    // Setup Shader


    // made for testing
    private final static boolean TRANSPOSE = false;

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
    
    // add transform class later
    protected void useTransform() {
        glUniformMatrix4fv(
            uTransform,
            TRANSPOSE,
            getFloatsFromMat(new Matrix4f().identity())
        );
    }

    protected void use() {
        glUseProgram(program);
    }

}
