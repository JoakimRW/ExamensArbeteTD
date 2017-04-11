#version 120
#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
varying vec2 v_lightPos;
varying vec4 v_position;
varying vec3 v_lightColor;
varying float v_dist;

uniform sampler2D u_texture;

void main() {
    float dropoffDistance = v_dist;
    float pixDist = 0;
    pixDist = pow(abs(v_lightPos.x - v_position.x),2);
    pixDist += pow(abs(v_lightPos.y - v_position.y),2);
    pixDist = sqrt(pixDist);
    gl_FragColor = v_color * texture2D(u_texture,v_texCoords) * min(1.35, (dropoffDistance / max(1.6,pixDist)));
    gl_FragColor *= (gl_FragColor + vec4(v_lightColor , 1));
}
