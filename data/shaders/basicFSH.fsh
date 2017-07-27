#ifdef GL_ES
precision highp float;
#endif

uniform sampler2D tex;

uniform float offset;

void main(){
  	vec2 loc = gl_TexCoord[0].st;
 	vec4 color = texture2D(tex,loc.xy);
  	gl_FragColor = color;
}