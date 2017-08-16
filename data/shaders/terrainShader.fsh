#version 130

#ifdef GL_ES
precision highp float;
#endif

uniform sampler2D tex;

float valueBetween(float value, float lower, float upper);
vec2 vectorBetween(vec2 vector, float l1, float u1, float l2, float u2);

float size = 512;
float totalWidth = 2048;
float totalHeight = 1024;

float tileFactor = 5;

void main(){
	vec2 ratio = vec2((size)/totalWidth, (size)/totalHeight);

	float bugR = 0.0005f;

  	vec2 loc = gl_TexCoord[0].st;
 	vec4 blendColor = texture2D(tex,vec2(loc.x*(totalWidth-2*size)/totalWidth, loc.y));
	loc = vec2(loc.x, loc.y*2);
	vec4 backgroundColor = (1-blendColor.r-blendColor.g-blendColor.b)*texture2D(tex, vectorBetween(loc*tileFactor, 1-2*ratio.x+bugR, 1-ratio.x-bugR, bugR, 1-ratio.y-bugR));
	vec4 rColor = blendColor.r * texture2D(tex,vectorBetween(loc*tileFactor, 1-ratio.x+bugR, 1-bugR, bugR, 1-ratio.y-bugR));
	vec4 gColor = blendColor.g * texture2D(tex,vectorBetween(loc*tileFactor, 1-2*ratio.x+bugR, 1-ratio.x-bugR, 1-ratio.y+bugR, 1-bugR));
	vec4 bColor = blendColor.b * texture2D(tex,vectorBetween(loc*tileFactor, 1-ratio.x+bugR, 1-bugR, 1-ratio.y+bugR, 1-bugR));

	vec4 color = backgroundColor + rColor + gColor + bColor;
  	gl_FragColor = color;
}

float valueBetween(float value, float lower, float upper){
	float val = value/ abs(upper-lower);
	float m = fract(val)*abs(upper-lower);
	
	return m+lower;
}
vec2 vectorBetween(vec2 vector, float l1, float u1, float l2, float u2){
	return vec2(valueBetween(vector.x, l1, u1),valueBetween(vector.y, l2, u2));
}