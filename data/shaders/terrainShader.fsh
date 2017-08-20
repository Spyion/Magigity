#version 130

#ifdef GL_ES
precision highp float;
#endif

uniform sampler2D tex;
uniform sampler2D backTex;
uniform sampler2D rTex;
uniform sampler2D gTex;
uniform sampler2D bTex;

uniform int backSize;
uniform int rSize;
uniform int gSize;
uniform int bSize;

float valueBetween(float value, float lower, float upper);
vec2 vectorBetween(vec2 vector, float l1, float u1, float l2, float u2);

//uniform float size;
//uniform float totalWidth;
//uniform float totalHeight;

uniform vec3 sunVector;
uniform vec4 sunColor;
uniform vec3 moonVector;
uniform vec4 moonColor;

in vec3 surfaceNormal;

float tileFactor = 50000;

void main(){

//BlendMap
  	vec2 loc = gl_TexCoord[0].st;

	vec4 blendColor = texture2D(tex, loc);

	vec4 backgroundColor = (1-blendColor.r-blendColor.g-blendColor.b)*texture2D(backTex, loc*tileFactor/backSize);
	vec4 rColor = blendColor.r * texture2D(rTex, loc*tileFactor/rSize);
	vec4 gColor = blendColor.g * texture2D(gTex, loc*tileFactor/gSize);
	vec4 bColor = blendColor.b * texture2D(bTex, loc*tileFactor/bSize);

	blendColor = backgroundColor + rColor + gColor + bColor;
//Lighting
	vec3 sun = vec3(sunVector.x*-1,sunVector.y*-1,sunVector.z*-1);
 	float sunDotProduct = sqrt(dot(normalize(surfaceNormal), normalize(sun)));
	float sunBrightness = max(sunDotProduct, 0.2);
	vec3 moon = vec3(moonVector.x*-1,moonVector.y*-1,moonVector.z*-1);
 	float moonDotProduct = sqrt(dot(normalize(surfaceNormal), normalize(moon)));
	float moonBrightness = max(moonDotProduct, 0.2);


	vec3 sunDiffuse = vec3(sunBrightness*sunColor);
	vec3 moonDiffuse = vec3(moonBrightness*moonColor);



	vec4 onlySun = vec4(sunDiffuse,1) * blendColor;
  	vec4 onlyMoon = vec4(moonDiffuse,1) * blendColor;
  	gl_FragColor = vec4((onlySun.r+onlyMoon.r), (onlySun.g+onlyMoon.g),
						(onlySun.b+onlyMoon.b), (onlySun.a+onlyMoon.a)/2);
}
