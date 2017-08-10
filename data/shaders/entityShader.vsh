attribute vec3 aVertexPosition;

out varying vec3 surfaceNormal;

uniform vec2 center;

void main(void){
 	gl_TexCoord[0]  = gl_MultiTexCoord0;
  	gl_Position = ftransform();

	surfaceNormal = normalize(vec3(gl_ModelViewMatrix*vec4(gl_Vertex.x-center.x, gl_Vertex.y-center.y, 0 ,0)));
	surfaceNormal = vec3(surfaceNormal.xy,0.5);
}
