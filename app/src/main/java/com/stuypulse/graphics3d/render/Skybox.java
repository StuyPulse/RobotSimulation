package com.stuypulse.graphics3d.render;

import com.stuypulse.graphics3d.Window;

import org.lwjgl.stb.STBImage;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.ByteBuffer;

public final class Skybox implements GlObject {
    
    // These vertices stay the same for every skybox
    private final static float[] SKYBOX_VERTICES = new float[] {
        -1.0f,  1.0f, -1.0f,
        -1.0f, -1.0f, -1.0f,
         1.0f, -1.0f, -1.0f,
         1.0f, -1.0f, -1.0f,
         1.0f,  1.0f, -1.0f,
        -1.0f,  1.0f, -1.0f,
    
        -1.0f, -1.0f,  1.0f,
        -1.0f, -1.0f, -1.0f,
        -1.0f,  1.0f, -1.0f,
        -1.0f,  1.0f, -1.0f,
        -1.0f,  1.0f,  1.0f,
        -1.0f, -1.0f,  1.0f,
    
         1.0f, -1.0f, -1.0f,
         1.0f, -1.0f,  1.0f,
         1.0f,  1.0f,  1.0f,
         1.0f,  1.0f,  1.0f,
         1.0f,  1.0f, -1.0f,
         1.0f, -1.0f, -1.0f,
    
        -1.0f, -1.0f,  1.0f,
        -1.0f,  1.0f,  1.0f,
         1.0f,  1.0f,  1.0f,
         1.0f,  1.0f,  1.0f,
         1.0f, -1.0f,  1.0f,
        -1.0f, -1.0f,  1.0f,
    
        -1.0f,  1.0f, -1.0f,
         1.0f,  1.0f, -1.0f,
         1.0f,  1.0f,  1.0f,
         1.0f,  1.0f,  1.0f,
        -1.0f,  1.0f,  1.0f,
        -1.0f,  1.0f, -1.0f,
    
        -1.0f, -1.0f, -1.0f,
        -1.0f, -1.0f,  1.0f,
         1.0f, -1.0f, -1.0f,
         1.0f, -1.0f, -1.0f,
        -1.0f, -1.0f,  1.0f,
         1.0f, -1.0f,  1.0f
    };

    private final int textureId;
    private final int skyboxVao, skyboxVbo;

    public Skybox(String... faces) {
        if (faces.length != 6 && faces.length != 1) {
            throw new IllegalArgumentException("Must provide 1 or 6 faces");
        }
        if (faces.length == 1) {
            String face = faces[0];

            faces = new String[] {
                face, face, face, 
                face, face, face
            };
        }

        Window.addObject(this);

        textureId = glGenTextures();
        glBindTexture(GL_TEXTURE_CUBE_MAP, textureId);

        // Use stbi to load each face
        int[] width = new int[1];
        int[] height = new int[1];
        int[] nrChannels = new int[1];
        for (int i = 0; i < faces.length; ++i) {
            ByteBuffer image = STBImage.stbi_load(
                faces[i], 
                width, 
                height, 
                nrChannels, 
                0
            );

            glTexImage2D(
                GL_TEXTURE_CUBE_MAP_POSITIVE_X + i,
                0, 
                GL_RGB, 
                width[0], height[0], 
                0, 
                GL_RGB, GL_UNSIGNED_BYTE, 
                image
            );

        }

        // Set parameters for the texture cube map
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);

        // Setup vertex array and vertex buffer
        skyboxVao = glGenVertexArrays();
        glBindVertexArray(skyboxVao);

        skyboxVbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, skyboxVbo);
        glBufferData(
            GL_ARRAY_BUFFER, 
            SKYBOX_VERTICES, 
            GL_STATIC_DRAW
        );

        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
    }

    protected void draw() {
        glDepthMask(false);
        glBindVertexArray(skyboxVao);

        glActiveTexture(GL_TEXTURE0);
        
        glBindTexture(GL_TEXTURE_CUBE_MAP, textureId);
        glDepthFunc(GL_LEQUAL);
        glDrawArrays(GL_TRIANGLES, 0, 36);
        glDepthFunc(GL_LESS); 
        
        glBindVertexArray(0);
        glDepthMask(true);
    }

    public void destroy() {
        glDeleteBuffers(skyboxVbo);
        glDeleteVertexArrays(skyboxVao);
        glDeleteTextures(textureId);
    }

}
