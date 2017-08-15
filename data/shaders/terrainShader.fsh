#version 130

#ifdef GL_ES
precision highp float;
#endif

uniform sampler2D tex;


float size = 512;

void main(){
  	vec2 loc = gl_TexCoord[0].st;
 	vec4 blendColor = texture2D(tex,vec2(loc.x/2, loc.y));
	vec4 backgroundColor = (1-blendColor.r-blendColor.g-blendColor.b)*texture2D(tex,vec2(loc.x/4f+0.5f, loc.y/2f));
	vec4 rColor = blendColor.r * texture2D(tex,vec2(loc.x/4f+0.75f, loc.y/2f));
	vec4 gColor = blendColor.g * texture2D(tex,vec2(loc.x/4f+0.5f, loc.y/2f+0.5f));
	vec4 bColor = blendColor.b * texture2D(tex,vec2(loc.x/4f+0.75f, loc.y/2f+0.5f));

	vec4 color = 
backgroundColor + 
rColor 
+ 
gColor 
+ 
bColor
;
  	gl_FragColor = color;
}