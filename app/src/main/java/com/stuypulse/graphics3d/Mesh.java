package com.stuypulse.graphics3d;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import com.stuypulse.graphics3d.math3d.Vector3D;

public final class Mesh {
    
    // Helper functions

    private static final float[] toArrayBuffer(Triangle[] triangles) {
        final int triangleSize = 9;
        float[] data = new float[triangles.length * triangleSize];
        int top = 0;

        for (Triangle triangle : triangles) {
            for (Vector3D point : triangle.points) {
                data[top++] = (float) point.x;
                data[top++] = (float) point.y;
                data[top++] = (float) point.z;
            }
        }

        return data;
    }

    // Generator functions
    public static final Mesh of(Triangle... triangles) {
        return new Mesh(triangles);
    }

    // Rest of class
    private final int vertexArray;
    private final int vertexBuffer;

    private final int count;

    public Mesh(Triangle... triangles) {
        this.count = triangles.length * 3; // a triangle contains 3 points

        vertexArray = glGenVertexArrays();
		glBindVertexArray(vertexArray);
		
		vertexBuffer = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
        glBufferData(
            GL_ARRAY_BUFFER, 
            toArrayBuffer(triangles), 
            GL_STATIC_DRAW // static -> gpu, dynamic -> ram
        );

        /*
        Currently, a triangle only contains a position.
        When a color is added to a triangle, it would also have
        to be implemented here
        */
        glEnableVertexAttribArray(0);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		
		glBindVertexArray(0);
    }

    public void destroy() {
        glDeleteBuffers(vertexBuffer);
		glDeleteVertexArrays(vertexArray);
    }

    // test code
    protected void draw() {
        glBindVertexArray(vertexArray);
		
		// glEnableVertexAttribArray(0);
		
		glDrawArrays(GL_TRIANGLES, 0, count);

		// glDisableVertexAttribArray(0);
		
		glBindVertexArray(0);
    }

}
