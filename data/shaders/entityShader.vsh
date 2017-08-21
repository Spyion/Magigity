#version 130

out vec3 surfaceNormal;
out vec3 light[6];
out vec4 lightColor[6];

uniform vec3 dirLight[2];
uniform vec4 dirColor[2];
uniform vec3 pLight[4];
uniform vec4 pColor[4];
uniform vec2 center;

void main(void){
 	gl_TexCoord[0]  = gl_MultiTexCoord0;
  	gl_Position = ftransform();

	surfaceNormal = normalize(vec3(gl_ModelViewMatrix*vec4(gl_Vertex.x-center.x, gl_Vertex.y-center.y, 0 ,0)));
	surfaceNormal = vec3(surfaceNormal.xy,1);

	//transform pointLight and add them both to an Array which is output;
	for(int i = 0; i < dirLight.length; i++){
		light[i] = dirLight[i];
		lightColor[i] = dirColor[i];
	}
	for(int i = 2; i < pLight.length+2; i++){
		light[i] = vec3(vec4(pLight[i-2],0)*gl_ModelViewMatrix-gl_Position);
		lightColor[i] = pColor[i-2];
	}
}
