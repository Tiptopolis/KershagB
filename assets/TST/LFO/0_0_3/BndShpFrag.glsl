#ifdef GL_ES
precision mediump float;
#endif

#define PI 3.14159265359
#define TWO_PI 6.28318530718

varying vec4 v_color;   
varying vec2 v_texCoords;
varying vec3 c_position; //camera-fragment position

uniform float u_camP; //camera-world position
uniform mat4 u_projTrans; //camera combined matrix
uniform vec2 u_resolution;
uniform vec3 u_mouse; //mouse position
uniform float u_time;



//////////////////////////////////////////////////
vec2 mouseAt;
vec2 camAt;
vec2 scrnOrig;

//BS
uniform int shpCnt;
uniform vec3 posAr[1000];

void setMoCam()
{
	//vec2 st = (gl_FragCoord.xy/u_resolution);
	//vec2 m = u_mouse/u_resolution;
	
	
	//camera & mouse positions
	vec3 c = c_position;
	vec2 cPos = c.xy/u_resolution;
	
	vec2 st = cPos;		
	vec2 m = u_mouse.xy/u_resolution;
	
	//adjust to aspect ratio
	st.x *= u_resolution.x/u_resolution.y;
	m.x *= u_resolution.x/u_resolution.y;
	
	mouseAt = st-m;
	camAt = st;
	scrnOrig = (gl_FragCoord.xy/u_resolution);
	
	//posAr[0] = vec3(mouseAt.xy,1);//works
}

float smin( float a, float b, float k )
{
    float h = clamp( 0.5+0.5*(b-a)/k, 0.0, 1.0 );
    return mix( b, a, h ) - k*h*(1.0-h);
}

//signed dst to scene
float sDTS(vec2 p)
{
	float dstToScn = 1;
	for(int i = 0; i <= shpCnt; i++)
	{
		
		//float d =  distance(p,posAr[i].xy/u_resolution)-0.1;	
		vec3 pA = vec3(posAr[i].xy/u_resolution,1);
		pA.x *= u_resolution.x/u_resolution.y; //map to resolution
		float d =  distance(p,pA)-(0.03*posAr[i].z);	
		
		
		//if(d>0) //standard threshold
		//comment this block out for distance fields			
		//if(d>0)
		//{
			//d=1;
		//}
		
		
		//hard vs soft edges
		dstToScn=min(dstToScn,d);		
		//dstToScn=smin(dstToScn,d,.01);//regular min & field strength
		
	}
	

	return dstToScn;

}


vec4 cDTS(vec2 p)
{

	float d = sDTS(p);	
	//vec4 c = vec4(vec3(d),1);
	vec4 c = vec4(d);
	
		//if(d<0.214)
		//{
			//c.r = 1;
			//c.g = 1;
			//c.b = 1;
			//c.a = (1-(d*5));
		//}
		//if(d<.01)
		//{
		 	// c.r = 1;			
		 	// c.g = 1-(d*2);
		 	// c.b = d*10;			
			// c.w = (1-(d*50));
		//}
	
	

	
	if(d<.01)
		{
		 	 c.r = 1;	
		 	  c.g = d*10;			
		 	 c.b = 1-(d*2);		 			
			 c.w = (1-(d*50));
		}
		if(d>=0.1)
		{
		c.b=1;
		c.w=0;
		}
	
	//c = vec4(d);
	
	return c;
}



void main(){



setMoCam();
	
	//float d = max(length(camAt),length(mouseAt.xy)-1);
	//float d = length(mouseAt.xy)-0.01;
	//float d = smin(1,length(mouseAt.xy)-0.05,0.1);
	//float d = min(1,length(mouseAt.xy)-0.05);
	
	
	
	//if(d>0)
	//{
		//d=1;
	//}
	//float d = sDTS(camAt.xy);
	//vec4 color = vec4(vec3(d),1);
	
	
	

	

     setMoCam();
	
	vec2 st = (gl_FragCoord.xy/u_resolution);
	
	vec4 color = vec4(0);
	float pct = 0.0;
	vec2 size = vec2(32,32)/u_resolution;		
	float d =0.0;
	
	
	
	//float D =sDTS(camAt.xy);
	float D =sDTS(camAt.xy);
			

	pct = D;
 	//pct = length(mouseAt)-0.1f;
	vec3 c = vec3(pct);
	//color = vec4(c,1);
	color = cDTS(camAt.xy);
	gl_FragColor = vec4(color);
	//gl_FragColor = vec4(vec3(fract(D*10.0)),1.0);
		
	//switch these for fuzzy fields or contour fields

}