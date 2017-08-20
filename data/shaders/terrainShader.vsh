#version 130

uniform float size;
uniform float totalWidth;
uniform float totalHeight;

out vec3 surfaceNormal;

void main(void){
 	gl_TexCoord[0]  = gl_MultiTexCoord0;
  	gl_Position = ftransform();

	surfaceNormal = vec3(0,0,1);
}
