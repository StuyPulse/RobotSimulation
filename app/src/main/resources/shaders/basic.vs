#version 330

// VERTEX INPUTS

layout (location = 0) in vec3 position;
layout (location = 1) in vec3 normal;

// MATRICES

uniform mat4 projection; // projection matrix
uniform mat4 transform;  // transformation of the vertex
uniform mat4 view;       // transformation into camera space

// unfirom vec3 lightDirection;

// OUTPUTS

out vec3 lightDirection;
out vec3 surfaceNormal;

void main() {
    
    // light position
    vec3 lightPosition = vec3(2, 2, 2);
    lightPosition = (vec4(lightPosition, 1.0)).xyz;

    // surface normal
    surfaceNormal = (transform * vec4(normal, 1.0)).xyz;

    // position on the screen 
    vec4 worldPosition = transform * vec4(position, 1.0);
    gl_Position = projection * view * worldPosition;

    // light direction    
    lightDirection = lightPosition - worldPosition.xyz;


}