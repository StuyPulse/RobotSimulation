#version 330

// OUTPUT

out vec3 fragColor;

// INPUTS

in vec3 lightSource;
in vec3 surfaceNormal;

// UNIFORMS

uniform vec3 surfaceColor;

void main() {

    float brightness = dot(
        normalize(lightSource),
        normalize(surfaceNormal)
    );
    brightness = clamp(brightness, 0.1, 1.0);

    fragColor = brightness * surfaceColor;

}