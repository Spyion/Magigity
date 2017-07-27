#ifdef GL_ES
precision highp float;
#endif

uniform sampler2D tex;
uniform int halfDisplayWidth;
uniform int halfDisplayHeight;

void main(){
  vec2 loc = gl_TexCoord[0].st;
  vec4 color = texture2D(tex,loc.xy);
	vec2 vignettloc = vec2(abs(loc.x-halfDisplayWidth), abs(loc.y-halfDisplayHeight));
float distance = sqrt(pow(vignettloc.x, 2)+pow(vignettloc.y, 2));
color = vec4(color.xyz/distance, color.a);
  gl_FragColor = color;
}