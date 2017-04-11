#version 120

attribute vec4 a_position;
attribute vec4 a_color;
attribute vec2 a_texCoord0;

uniform mat4 u_projTrans;
uniform vec2 u_lightPos;
uniform vec3 u_lightColor;
uniform float u_dist;

varying vec4 v_color;
varying vec2 v_texCoords;
varying vec2 v_lightPos;
varying vec4 v_position;
varying vec3 v_lightColor;
varying float v_dist;

void main() {
    v_color = a_color;
    v_dist = u_dist;
    v_position = a_position;
    v_lightPos = u_lightPos;
    v_lightColor = u_lightColor;
    v_texCoords = a_texCoord0;
    gl_Position = u_projTrans * a_position;
}
