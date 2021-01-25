package com.stuypulse.graphics;

import java.util.function.Supplier;

import com.stuypulse.graphics3d.render.Mesh;
import com.stuypulse.stuylib.math.Angle;

/**
 * This class is used to create a MeshInstance statically
 * but then load it at runtime. It can be used in place of a singleton
 * and becomes a much cleaner solution. 
 */
public final class MeshInstance {

    // this class is technically a Suppler<Mesh> itself

    private Mesh mesh;

    private final Supplier<Mesh> meshSupplier;

    public MeshInstance(Supplier<Mesh> meshSupplier) {
        this.mesh = null;
        this.meshSupplier = meshSupplier;
    }

    public MeshInstance(String path, Angle yaw, Angle pitch, Angle roll) {
        this(() -> MeshLoader.getMeshFromObj(path, yaw, pitch, roll));
    }

    public MeshInstance(String path) {
        this(path, Angle.kZero, Angle.kZero, Angle.kZero);
    }

    public Mesh get() {

        if (mesh == null) {
            mesh = meshSupplier.get();
        }

        return this.mesh;

    }

}
