#version 130

#ifdef GL_ES
precision highp float;
#endif

uniform sampler2D tex;



in vec3 pass_normal;
in vec2 pass_textureCoordinates;
in vec3 pass_pos;

in vec3 light[6];
in vec4 lightColor[6];

void main(){
	

	float shineDamper = 10;
	float reflectivity = 0.3;

	vec3 normal = normalize(pass_normal);

 	vec2 loc = pass_textureCoordinates.st;
	vec4 pixel = texture2D(tex,loc.xy);
	vec4 finalSpecular = vec4(0,0,0,0);
	vec4 finalDiffuse  = vec4(0,0,0,0);

	for(int i = 0; i < light.length; i++){
 		float dotProduct = dot(normal, normalize(light[i]*-1));
		float brightness = max(dotProduct, 0.0);
		finalDiffuse += brightness*lightColor[i];	

		vec3 reflectedLight = reflect(light[i], normal);

		float specularFactor = max(dot(reflectedLight, vec3(0,0,1)),0);
		float dampedFactor = pow(specularFactor, shineDamper);
		finalSpecular += dampedFactor * lightColor[i] * reflectivity;	
	}

	vec4 color = finalDiffuse*pixel+finalSpecular;
  	gl_FragColor = vec4(color.rgb, pixel.a);
  	gl_FragColor = pixel;
  		//gl_FragColor = vec4(normal,1);
  	//gl_FragColor = vec4(pass_pos.x+0.5,pass_pos.z,pass_pos.y+0.5,1);
  	
}