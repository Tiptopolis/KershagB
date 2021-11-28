#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform sampler2D u_mask;
uniform vec3 u_color;
//uniform sampler2D u_color;
uniform mat4 u_projTrans;

void main() {

   	vec4 color = texture2D(u_texture, v_texCoords);
   	vec4 colorMask = texture2D(u_mask, v_texCoords);
   	vec4 colorMult = vec4(u_color, 1);

	//brown coat
	vec3 tempCol = texture2D(u_texture, v_texCoords).rgb;
	       	vec3 multResult = colorMult * tempCol;

	float subAlpha = color.a-colorMask.r; //scalar op: main.a - mask.r


	vec3 overlay = mix(multResult, color.rgb, subAlpha);


	gl_FragColor = vec4(overlay, color.a);

	//!!! FUCKING WORKS !!!
	//now need to pass color as either a straight color or a solid-color uniform material

}
