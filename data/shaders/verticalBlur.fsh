#ifdef GL_ES
precision highp float;
#endif

uniform sampler2D tex;
uniform float displayWidth;
void main(){
  	vec2 loc = gl_TexCoord[0].st;

	vec4 colors[11];
	for(int i = -5; i < 5; i++){
		colors[i] = texture2D(tex, vec2(loc.x+i/displayWidth, loc.y));
	}
	vec4 color;
	for(int i = 0; i < 11; i++){
		color + colors[i];
	}
	color = vec4(color.rgba/11);
	
  	gl_FragColor = color;
}