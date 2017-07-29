attribute vec3 aVertexPosition;

out varying vec3 surfaceNormal;

uniform vec2 center;

void main(void){
 	gl_TexCoord[0]  = gl_MultiTexCoord0;
  	gl_Position = ftransform();
	
	surfaceNormal = vec4(gl_Vertex.x-center.x, gl_Vertex.y-center.y, 100, 0) * gl_ModelViewMatrix;
}
