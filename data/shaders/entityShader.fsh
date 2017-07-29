#ifdef GL_ES
precision highp float;
#endif

uniform sampler2D tex;

in varying vec3 surfaceNormal;
uniform vec3 sunVector;

void main(){

	vec3 sun = vec3(sunVector.x*-1,sunVector.y*-1,sunVector.z*-1);
 	float dotProduct = dot(normalize(surfaceNormal), normalize(sun));
	float brightness = max(dotProduct, 0.2);
	
 	vec2 loc = gl_TexCoord[0].st;
  	vec4 color = vec4(brightness, brightness, brightness, 1.0) * texture2D(tex,loc.xy);
  	gl_FragColor = color;
}