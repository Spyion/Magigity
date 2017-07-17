
#version 400 core
in vec3 position;
//in vec2 textureCoordinates;

uniform mat4 cameraTransform;
uniform mat4 objectTransform;

//varying vec2 pass_coords;
out vec3 color;

void main(void){
	gl_Position = cameraTransform * objectTransform * vec4(position ,1.);
	color = vec3(position.x,1., position.y);
}