#version 330

// VERTEX INPUTS

layout (location = 0) in vec3 position;
layout (location = 1) in vec3 normal;

// MATRICES

uniform mat4 projection; // projection matrix
uniform mat4 transform;  // transformation of the vertex
uniform mat4 view;       // transformation into camera space

// OUTPUTS

out vec3 lightSource;
out vec3 surfaceNormal;

void main() {
    
    // surface normal
    surfaceNormal = (mat3(transform) * normal);

    // position on the screen 
    vec4 worldPosition = transform * vec4(position, 1);
    gl_Position = projection * view * worldPosition;

    // light direction    
    lightSource = vec3(-1, 0.5, -1);


}