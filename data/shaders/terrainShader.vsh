#version 130

float size = 512;

void main(void){
 	gl_TexCoord[0]  = gl_MultiTexCoord0;
  	gl_Position = gl_ProjectionMatrix * gl_ModelViewMatrix * vec4(gl_Vertex.x/2+size, gl_Vertex.yzw);
}
