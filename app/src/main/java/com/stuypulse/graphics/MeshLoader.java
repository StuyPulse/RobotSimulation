package com.stuypulse.graphics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.stuypulse.graphics3d.math3d.Triangle;
import com.stuypulse.graphics3d.math3d.Vertex;
import com.stuypulse.graphics3d.render.Mesh;

import org.joml.Vector3f;

public final class MeshLoader {
    

    private static Mesh DEFAULT_MESH = null;
    
    public static final Mesh getDefaultMesh() {
        if (DEFAULT_MESH == null) {
            DEFAULT_MESH = new Mesh(Graphics.CUBE_TRIANGLES);
        }
        return DEFAULT_MESH;
    }

    public static final Mesh getMeshFromObj(String path) {

        try {
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
                    
                    points.add(new Vector3f(
                        (float) Double.parseDouble(parts[1]),
                        (float) Double.parseDouble(parts[2]),
                        (float) Double.parseDouble(parts[3])
                    ));
                } 
                
                else if (line.startsWith("vn ")) {
                    final String[] parts = line.split(" ");

                    normals.add(new Vector3f(
                        (float) Double.parseDouble(parts[1]),
                        (float) Double.parseDouble(parts[2]),
                        (float) Double.parseDouble(parts[3])
                    ));
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
}
