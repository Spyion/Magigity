#version 130

#ifdef GL_ES
precision highp float;
#endif

uniform sampler2D tex;

in vec3 surfaceNormal;
uniform vec3 sunVector;
uniform vec4 sunColor;
uniform vec3 moonVector;
uniform vec4 moonColor;

void main(){


	vec3 sun = vec3(sunVector.x*-1,sunVector.y*-1,sunVector.z*-1);
 	float sunDotProduct = dot(normalize(surfaceNormal), normalize(sun));
	float sunBrightness = max(sunDotProduct, 0.2);
	vec3 moon = vec3(moonVector.x*-1,moonVector.y*-1,moonVector.z*-1);
 	float moonDotProduct = dot(normalize(surfaceNormal), normalize(moon));
	float moonBrightness = max(moonDotProduct, 0.2);


	vec3 sunDiffuse = vec3(sunBrightness*sunColor);
	vec3 moonDiffuse = vec3(moonBrightness*moonColor);
	
	//vec3 toCamera = vec3(0,0,1);
	//vec3 reflectedLight = reflect(sunVector, surfaceNormal);

	//float specularFactor = dot(reflectedLight, toCamera);
	//float dampedFactor = pow(specularFactor, shineDamper);
	//vec3 finalSpecular = sunColor * reflectivity;	

 	vec2 loc = gl_TexCoord[0].st;
  	vec4 onlySun = vec4(sunDiffuse,1) * texture2D(tex,loc.xy);
  	vec4 onlyMoon = vec4(moonDiffuse,1) * texture2D(tex,loc.xy);
  	gl_FragColor = vec4((onlySun.r+onlyMoon.r), (onlySun.g+onlyMoon.g),
						(onlySun.b+onlyMoon.b), (onlySun.a+onlyMoon.a)/2);
	//gl_FragColor = onlySun;
}