package com.stuypulse.graphics3d.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stuypulse.graphics3d.Window;
import com.stuypulse.graphics3d.math3d.*;

import com.stuypulse.stuylib.math.Angle;

import org.joml.Vector3f;

public final class Mesh implements GlObject {
    
    // HELPER FUNCTIONS TO READ A MESH

    /**
     * Creates a mesh from an obj file
     * 
     * @param path
     * @return
     */
    public static final Mesh getMeshFromObj(String path) {
        return getMeshFromObj(path, Angle.kZero, Angle.kZero, Angle.kZero);
    }

    /**
     * Creates a mesh from an obj file
     * 
     * Requires:
     *  * f v/t/n v/t/n v/t/n
     *  * f v//n v//n v//n
     * 
     * Other formats are not supported
     * 
     * @param path path to the obj file
     * @param yaw rotate mesh on load
     * @param pitch rotate mesh on load
     * @param roll rotate mesh on load
     * @return the mesh
     */
    public static final Mesh getMeshFromObj(String path, Angle yaw, Angle pitch, Angle roll) {

        try {
            final float y = (float) yaw.toRadians();
            final float p = (float) pitch.toRadians();
            final float r = (float) roll.toRadians();

            BufferedReader input = new BufferedReader(new FileReader(new File(path)));

            List<Vector3f> points = new ArrayList<>();
            List<Vector3f> normals = new ArrayList<>();

            List<Triangle> triangles = new ArrayList<>();

            String line = null;
            while ((line = input.readLine()) != null) {

                // skip empty lines
                if (line.length() < 1) continue;

                line = line.trim().replaceAll("  ", " ");

                if (line.startsWith("v ")) {
                    final String[] parts = line.split(" ");
                    

                    Vector3f pt = new Vector3f(
                        (float) Double.parseDouble(parts[1]),
                        (float) Double.parseDouble(parts[2]),
                        (float) Double.parseDouble(parts[3])
                    );

                    pt = pt
                        .rotateAxis(y, 1, 0, 0)
                        .rotateAxis(p, 0, 1, 0)
                        .rotateAxis(r, 0, 0, 1);

                    points.add(pt);
                } 
                
                else if (line.startsWith("vn ")) {
                    final String[] parts = line.split(" ");

                    Vector3f nm = new Vector3f(
                        (float) Double.parseDouble(parts[1]),
                        (float) Double.parseDouble(parts[2]),
                        (float) Double.parseDouble(parts[3])
                    );
                    
                    nm = nm
                        .rotateAxis(y, 1, 0, 0)
                        .rotateAxis(p, 0, 1, 0)
                        .rotateAxis(r, 0, 0, 1);

                    normals.add(nm);
                }

                else if (line.startsWith("f ")) {
                    final String[] parts = line.split(" ");

                    final String[] vertex1 = parts[1].split("/");
                    final String[] vertex2 = parts[2].split("/");
                    final String[] vertex3 = parts[3].split("/");

                    triangles.add(new Triangle(
                        new Vertex(
                            points.get(Integer.parseInt(vertex1[0]) - 1),
                            normals.get(Integer.parseInt(vertex1[2]) - 1)
                        ),
                        new Vertex(
                            points.get(Integer.parseInt(vertex2[0]) - 1),
                            normals.get(Integer.parseInt(vertex2[2]) - 1)
                        ),
                        new Vertex(
                            points.get(Integer.parseInt(vertex3[0]) - 1),
                            normals.get(Integer.parseInt(vertex3[2]) - 1)
                        )
                    ));
                }

            }

            input.close();

            return new Mesh(triangles.toArray(new Triangle[0]));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    // HELPER FUNCTIONS TO INSTANTIATE A MESH

    private static final float[] vectorsToFloatArray(Vector3f[] arr) {
        float[] out = new float[arr.length * 3];

        for (int i = 0, top = 0; i < arr.length; ++i) {
            out[top++] = arr[i].x;
            out[top++] = arr[i].y;
            out[top++] = arr[i].z;
        }

        return out;
    }

    private static final class IndexedVertices {

        private int[] indices;
        private Vector3f[] positions;
        private Vector3f[] normals;

        public IndexedVertices(Vertex... vertices) {
            List<Integer> indexList = new ArrayList<>();
            
            Map<Vertex, Integer> map = new HashMap<>();
            int lastIdx = 0;

            for (Vertex vertex : vertices) {
                Integer mapVertex = map.get(vertex);

                // if vertex not in map yet
                if (mapVertex == null) {
                    
                    indexList.add(lastIdx);
                    map.put(vertex, lastIdx);

                    ++lastIdx;
                }

                // if vertex already exists
                else {
                    indexList.add(mapVertex);
                }
                
            }

            // Store the list of indices (it is as long as the input list of vertices)
            indices = indexList.stream().mapToInt(i -> i).toArray();
            
            // Create two arrays of positions and normals from the
            // unique set of vertices generated
            Vertex[] vertexArray = map.keySet().toArray(new Vertex[0]) ;
            Collections.sort(
                Arrays.asList(vertexArray), 
                (a, b) -> map.get(a).compareTo(map.get(b))
            );

            // It is possible to create a float[] here instead of later
            List<Vector3f> verticesTmp = new ArrayList<>();
            List<Vector3f> normalsTmp = new ArrayList<>();

            for (var entry : vertexArray) {
                verticesTmp.add(entry.getPosition());
                normalsTmp.add(entry.getNormal());
            }

            positions = verticesTmp.toArray(new Vector3f[0]);
            normals = normalsTmp.toArray(new Vector3f[0]);
        }

        public int[] getIndices() {
            return indices;
        }

        public Vector3f[] getPositions() {
            return this.positions;
        }

        public Vector3f[] getNormals() {
            return this.normals;
        }

    }

    private static final Vertex[] triangleArrayToVertex(Triangle... tris) {
        Vertex[] out = new Vertex[tris.length * 3];

        for (int i = 0, top = 0; i < tris.length; ++i) {
            out[top++] = tris[i].vertices[0];
            out[top++] = tris[i].vertices[1];
            out[top++] = tris[i].vertices[2]; 
        }

        return out;
    }


    // THE ACTUAL MESH CLASS

    private final int vertexArray;

    private final int vertexBuffer;
    private final int normalBuffer;

    private final int elementBuffer;

    private final int count;

    public Mesh(int[] indices, float[] vectors, float[] normals) {
        Window.addObject(this);

        this.count = indices.length;

        // create array for vertices
        vertexArray = glGenVertexArrays();
        glBindVertexArray(vertexArray);

        // setup position buffer
        vertexBuffer = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
        glBufferData(
            GL_ARRAY_BUFFER,
            vectors,
            GL_STATIC_DRAW  
        );

        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        // setup normal buffer
        normalBuffer = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, normalBuffer);
        glBufferData(
            GL_ARRAY_BUFFER, 
            normals, 
            GL_STATIC_DRAW
        );

        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);

        elementBuffer = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, elementBuffer);
        glBufferData(
            GL_ELEMENT_ARRAY_BUFFER,
            indices,
            GL_STATIC_DRAW
        );

        // unbind
        glBindVertexArray(0);

    }

    public Mesh(int[] indices, Vector3f[] vectors, Vector3f[] normals) {
        this(
            indices,
            vectorsToFloatArray(vectors),
            vectorsToFloatArray(normals)
        );
    }

    private Mesh(IndexedVertices indexedResult) {
        this(
            indexedResult.getIndices(),
            indexedResult.getPositions(),
            indexedResult.getNormals()
        );
    }

    public Mesh(Vertex... vertices) {
        this(new IndexedVertices(vertices));
    }

    public Mesh(Triangle... triangles) {
        this(triangleArrayToVertex(triangles));
    }

    public void destroy() {
        glDeleteBuffers(vertexBuffer);
        glDeleteBuffers(normalBuffer);
        glDeleteBuffers(elementBuffer);
        glDeleteVertexArrays(vertexArray);
    }

    // test code
    protected void draw() {
        glBindVertexArray(vertexArray);

        glDrawElements(
            GL_TRIANGLES,
            this.count,
            GL_UNSIGNED_INT,
            0
        );

        glBindVertexArray(0);
    }

}
