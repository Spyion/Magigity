#version 130

in vec3 position;
in vec3 normal;
in vec2 textureCoordinates;


out vec3 pass_normal;
out vec2 pass_textureCoordinates;

out vec3 light[6];
out vec4 lightColor[6];

uniform mat4 transformationMatrix;
uniform mat4 viewMatrix;

uniform vec3 dirLight[2];
uniform vec4 dirColor[2];
uniform vec3 pLight[4];
uniform vec4 pColor[4];
uniform vec2 center;

void main(void){
	//pass
	pass_normal = normal;
	pass_textureCoordinates = textureCoordinates;


 	//gl_TexCoord[0]  = gl_MultiTexCoord0;
	vec4 worldPosition = vec4(position,0) * transformationMatrix;
  	gl_Position = worldPosition;

	//surfaceNormal = normalize(vec3(gl_ModelViewMatrix*vec4(gl_Vertex.x-center.x, gl_Vertex.y-center.y, 0 ,0)));
	//surfaceNormal = vec3(surfaceNormal.xy,1);

	//transform pointLight and add them both to an Array which is output;
	for(int i = 0; i < dirLight.length; i++){
		light[i] = dirLight[i];
		lightColor[i] = dirColor[i];
	}
	for(int i = 2; i < pLight.length+2; i++){
		light[i] = vec3(vec4(pLight[i-2],0));
		lightColor[i] = pColor[i-2];
	}
}
