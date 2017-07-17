#version 400 core
uniform mat4 cameraTransform;
uniform mat4 objectTransform;
in vec3 color;
//uniform sampler2D modelTexture;
//varying vec2 pass_coords;

out vec4 color2;
void main(void){
	color = vec4(color2.xyz, 1.);
	//color = texture(modelTexture, pass_coords);
}