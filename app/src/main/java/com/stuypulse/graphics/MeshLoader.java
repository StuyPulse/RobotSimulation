package com.stuypulse.graphics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static com.stuypulse.Constants.WindowSettings.*;

import com.stuypulse.graphics3d.math3d.Triangle;
import com.stuypulse.graphics3d.math3d.Vertex;
import com.stuypulse.graphics3d.render.Mesh;
import com.stuypulse.stuylib.math.Angle;

import org.joml.Vector3f;

public final class MeshLoader {
    

    /**
     * May be easiest to load meshes during runtime
     */

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
}
