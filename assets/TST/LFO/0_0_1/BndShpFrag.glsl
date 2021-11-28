#ifdef GL_ES
precision mediump float;
#endif

#define PI 3.14159265359
#define TWO_PI 6.28318530718

varying vec4 v_color;   
varying vec2 v_texCoords;
varying vec3 c_position; //camera position

uniform mat4 u_projTrans; //camera combined matrix
uniform vec2 u_resolution; //screen size
uniform vec3 u_mouse; //mouse position
uniform float u_time;



//////////////////////////////////////////////////

vec3 camDir;
vec3 camAt;
vec2 scrAt;

//BS
uniform int numShapes;
uniform vec3 posAr[500];
uniform vec3 sclAr[500];



float smin( float a, float b, float k )
{
    float h = clamp( 0.5+0.5*(b-a)/k, 0.0, 1.0 );
    return mix( b, a, h ) - k*h*(1.0-h);
}

//signed dst to scene
float sDTS(vec2 p)
{
	float dstToScn = 1;
	for(int i = 0; i <= numShapes; i++)
	{
		
		//float d =  distance(p,posAr[i].xy/u_resolution)-0.1;	
		vec3 pA = vec3(posAr[i].xy/u_resolution,1);
		pA.x *= u_resolution.x/u_resolution.y; //map to resolution
		float d =  distance(p,pA)-(sclAr[i].z/3);	
		
		//if(d>0) //standard threshold
		//comment this block out for distance fields			
		//if(d<0)
		//{
			//d=1;
		
		//}
		
		
		//hard vs soft edges
		//dstToScn=min(dstToScn,d);		
		dstToScn=smin(dstToScn,d,.2);//regular min & field strength
		
	}
	

	return dstToScn;

}




float map(float value, float min1, float max1, float min2, float max2) {
  return min2 + (value - min1) * (max2 - min2) / (max1 - min1);
}

vec4 cDTS(vec2 p)
{

	float d = sDTS(p);	
	//vec4 c = vec4(vec3(d),1);
	vec4 c = vec4(d);
	

		if(d<0.214)
		{
			c.r = 1;
			c.g = 1;
			c.b = 1;
			c.a = (1-(d*5));
		}
		if(d<.01)
		{
		    c.r = 1;
			//c.g = d;
			c.g = 1-(d*2);
			c.b = d*10;
			//c.w = 1-d;
			c.a = (1-(d*50));
		}
		if(d>0.02)
		{
			c.r = 1/d;
			c.g = 1/d;
			c.b = 1/d;
			c.a = (1-(d*5));
		}

		
		
	return c;
}


////////////////


void main(){


	

     
	
	scrAt = vec2(c_position.xy/u_resolution);
	scrAt = scrAt;
	scrAt.x *= u_resolution.x/u_resolution.y;
	camAt = c_position*mat4x3(u_projTrans);
	
	
	float c=1;
	
	c = sDTS(scrAt);
	float D = c;
	//vec4 color = vec4(c);	
	//vec4 color = vec4(vec3(c),1);	
	vec4 color = cDTS(scrAt);

	gl_FragColor = vec4(color);
	//gl_FragColor = vec4(vec3(fract(D*10.0)),1.0);
		
	//switch these for fuzzy fields or contour fields

}