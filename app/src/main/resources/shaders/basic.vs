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

out vec3 newNormal;
out vec3 lightDir;

void main() {

    // replace with uniform
    vec3 lightDirection = vec3(0, 10, -1);

    newNormal = (view * transform * vec4(normal, 1)).xyz;
    lightDir = lightDirection;

    gl_Position = projection * view * transform * vec4(position, 1);

}