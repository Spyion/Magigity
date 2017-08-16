#version 130

#ifdef GL_ES
precision highp float;
#endif

uniform sampler2D tex;


float size = 512;
float totalWidth = 2048;
float totalHeight = 1024;

void main(){
	vec2 ratio = vec2(size/totalWidth, size/totalHeight);

  	vec2 loc = gl_TexCoord[0].st;
 	vec4 blendColor = texture2D(tex,vec2(loc.x*(totalWidth-2*size)/totalWidth), loc.y);
	vec4 backgroundColor = (1-blendColor.r-blendColor.g-blendColor.b)*texture2D(tex, vectorBetween(loc*10, 1-2*ratio.x, 1-ratio.x, 0, 1-ratio.y));
	vec4 rColor = blendColor.r * texture2D(tex,vectorBetween(loc*10, 1-2*ratio.x, 1-ratio.x, 0, 1-ratio.y));
	vec4 gColor = blendColor.g * texture2D(tex,vectorBetween(loc*10, 1-2*ratio.x, 1-ratio.x, 0, 1-ratio.y));
	vec4 bColor = blendColor.b * texture2D(tex,vectorBetween(loc*10, 1-2*ratio.x, 1-ratio.x, 0, 1-ratio.y));

	vec4 color = backgroundColor + rColor + gColor + bColor;
  	gl_FragColor = color;
}

float valueBetween(float value, float lower, float upper){
	return value%(abs(upper-lower))+lower;
}
float vectorBetween(vec2 vector, float l1, float u1, float l2, float u2){
	return vec2(valueBetween(vector.x, l1, u1), valueBetween(vector.y, l2, u2));
}