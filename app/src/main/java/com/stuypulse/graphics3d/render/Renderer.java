package com.stuypulse.graphics3d.render;

// Small struct wrapper for a shader and a drawing function
// just so that the window doesnt contain a shader, camera, etc.
public final class Renderer {
    
    private Shader shader;

    private Camera camera;

    public Renderer(Camera camera) {
        this.shader = null;
        this.camera = camera;
    }

    public Camera getCamera() {
        return camera;
    }

    public Renderer setShader(Shader shader) {
        this.shader = shader;
        if (shader != null)
            shader.use();
        return this;
    }

    public Renderer setColor(float r, float g, float b) {
        if (shader != null)
            shader.setColor(r,g,b);
        return this;
    }

    public Renderer setColor(int r, int g, int b) {
        if (shader != null)
            shader.setColor(r,g,b);
        return this;
    }

    public Renderer updateCamera() {
        if (shader != null)
            shader.useCamera(camera);
        return this;
    }

    public Renderer setTransform(Transform transform) {
        if (shader != null)
            shader.useTransform(transform);
        return this;
    }

    public void draw(Mesh mesh) {
        mesh.draw();
    }

    public void draw(Skybox skybox) {
        if(shader != null)
            shader.setSkybox(0);
            
        skybox.draw(); 
    }

}
