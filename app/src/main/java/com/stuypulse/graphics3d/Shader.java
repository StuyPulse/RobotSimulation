package com.stuypulse.graphics3d;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

// I don't really like this class 
public final class Shader {

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

    private Shader(String vertexText, String fragText) {

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

        // hmmm...
        // glUseProgram(program);
    }

    protected void use() {
        glUseProgram(program);
    }

    public void destroy() {
        glDetachShader(program, vertexShader);
        glDetachShader(program, fragmentShader);
        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
        glDeleteProgram(program);
    }

}
