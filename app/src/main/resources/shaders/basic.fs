#version 330

// OUTPUT

out vec4 fragColor;

// INPUTS

in vec3 newNormal;
in vec3 lightDir;

// UNIFORMS

uniform vec4 surfaceColor;

void main() {

    float illumination = clamp( abs ( dot(lightDir, normalize(newNormal)) ), 0, 1);

    fragColor = vec4(illumination, illumination, illumination, 1) * surfaceColor;

}