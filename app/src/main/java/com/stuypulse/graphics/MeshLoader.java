package com.stuypulse.graphics;

import com.stuypulse.graphics3d.render.Mesh;

public final class MeshLoader {
    
    // temporary mesh file

    // Singleton Mesh
    // meshes cannot be created static, triangle arrays can
    private static Mesh DEFAULT_MESH = null;
    
    public static final Mesh getDefaultMesh() {
        if (DEFAULT_MESH == null) {
            DEFAULT_MESH = new Mesh(Graphics.CUBE_TRIANGLES);
        }
        return DEFAULT_MESH;
    }
}
