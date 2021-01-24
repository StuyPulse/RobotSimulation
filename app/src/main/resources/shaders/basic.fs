#version 330

// OUTPUT

out vec4 fragColor;

// INPUTS

in vec3 lightDirection;
in vec3 surfaceNormal;

// UNIFORMS

uniform vec4 surfaceColor;

void main() {

    float brightness = dot(
        normalize(lightDirection),
        normalize(surfaceNormal)
    );
    brightness = clamp(brightness, 0.0, 1.0);

    fragColor = brightness * surfaceColor;

}