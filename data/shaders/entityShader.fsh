#version 130

#ifdef GL_ES
precision highp float;
#endif

uniform sampler2D tex;

in vec3 surfaceNormal;
uniform vec3 sunVector;
uniform vec4 sunColor;

void main(){


	vec3 sun = vec3(vec4(sunVector.x*-1,sunVector.y*-1,sunVector.z*-1,0));
 	float dotProduct = dot(normalize(surfaceNormal), normalize(sun));
	float brightness = max(dotProduct, 0.2);
	vec3 diffuse = vec3(brightness*sunColor);
	
	//vec3 toCamera = vec3(0,0,1);
	//vec3 reflectedLight = reflect(sunVector, surfaceNormal);

	//float specularFactor = dot(reflectedLight, toCamera);
	//float dampedFactor = pow(specularFactor, shineDamper);
	//vec3 finalSpecular = sunColor * reflectivity;	

 	vec2 loc = gl_TexCoord[0].st;
  	vec4 color = vec4(diffuse,1) * texture2D(tex,loc.xy);
  	gl_FragColor = color;
}