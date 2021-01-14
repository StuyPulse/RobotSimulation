#version 330

layout (location = 0) in vec3 position;

// MATRICES

uniform mat4 projection; // projection matrix
uniform mat4 transform;  // transformation of the vertex
uniform mat4 view;     // transformation into camera space

void main() {
    // vec4 tmp_pos = projection * transform * camera * vec4(position, 1.0);
    gl_Position = projection * view * transform * vec4(position, 1.0);
    // gl_Position /= gl_Position.w;
}