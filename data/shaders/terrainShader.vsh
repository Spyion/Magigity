#version 130

float size = 512;
float totalWidth = 2048;

void main(void){
 	gl_TexCoord[0]  = gl_MultiTexCoord0;
  	gl_Position = gl_ProjectionMatrix * gl_ModelViewMatrix * vec4(gl_Vertex.x*(totalWidth-2*size)/totalWidth, gl_Vertex.yzw);
}
