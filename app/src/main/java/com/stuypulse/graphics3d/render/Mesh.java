package com.stuypulse.graphics3d.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import com.stuypulse.graphics3d.Window;
import com.stuypulse.graphics3d.math3d.*;

public final class Mesh implements GlObject {
    
    private static final class BufferArray {

        private final static int POINT_SIZE  = 3 * 3;
        private final static int NORMAL_SIZE = 3 * 3;

        private float[] points;
        private int pointsTop;

        private float[] normals;
        private int normalsTop;

        public BufferArray(Triangle[] triangles) {
            this.points = new float[triangles.length * POINT_SIZE];
            this.pointsTop = 0;

            this.normals = new float[triangles.length * NORMAL_SIZE];
            this.normalsTop = 0;
            
            for (Triangle t : triangles)
                for (Vertex vertex : t.vertices)
                    this.addVertex(vertex);

        }

        private void addVertex(Vertex in) {
            this.points[pointsTop++] = in.getPosition().x;
            this.points[pointsTop++] = in.getPosition().y;
            this.points[pointsTop++] = in.getPosition().z;
            
            this.normals[normalsTop++] = in.getNormal().x;
            this.normals[normalsTop++] = in.getNormal().y;
            this.normals[normalsTop++] = in.getNormal().z;
        }

        public float[] getPoints() {
            return this.points;
        }

        public float[] getNormals() {
            return this.normals;
        }

    }

    private final int vertexArray;

    private final int vertexBuffer;
    private final int normalBuffer;

    private final int count;

    public Mesh(Triangle... triangles) {
        // Add to object manager
        Window.addObject(this);

        this.count = triangles.length * 3; // a triangle contains 3 points

        BufferArray triangleData = new BufferArray(triangles);

        vertexArray = glGenVertexArrays();
		glBindVertexArray(vertexArray);
        
        // position
		vertexBuffer = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
        glBufferData(
            GL_ARRAY_BUFFER, 
            triangleData.getPoints(), 
            GL_STATIC_DRAW // static -> gpu, dynamic -> ram
        );

        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        // normals
        normalBuffer = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, normalBuffer);
        glBufferData(
            GL_ARRAY_BUFFER,
            triangleData.getNormals(),
            GL_STATIC_DRAW
        );

        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);

		glBindVertexArray(0);
    }

    public void destroy() {
        glDeleteBuffers(vertexBuffer);
        glDeleteBuffers(normalBuffer);
        glDeleteVertexArrays(vertexArray);
    }

    // test code
    protected void draw() {
        glBindVertexArray(vertexArray);

		glDrawArrays(GL_TRIANGLES, 0, count);
        
        glBindVertexArray(0);
    }

}
