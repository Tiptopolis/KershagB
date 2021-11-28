
#version 120
#ifdef GL_ES
precision mediump float;
#endif

#define PI 3.14159265359
#define TWO_PI 6.28318530718

const float EPS=0.001;

varying vec4 v_color;   
varying vec2 v_texCoords;
varying vec3 c_position; //camera-fragment position

uniform vec3 u_camP; //camera-world position
uniform vec3 u_camD; //camera-world direction
uniform mat4 u_projTrans; //camera combined matrix
uniform mat4 u_invPrj;
uniform mat4 u_viewPrj;
uniform vec2 u_resolution;
uniform vec3 u_mouse; //mouse position
uniform float u_time;



//////////////////////////////////////////////////
vec2 mouseAt;
vec3 camAt;
vec2 scrnOrig;

//BS

uniform int shpCnt;
uniform vec4 posAr[500];

//////

struct Ray{
vec3 origin;
vec3 direction;
};


Ray castRay(vec3 pos, vec3 dir)
{
	return Ray(pos,dir);
}

vec3 prj(vec3 p, mat4 matrix)
{
	//vec3 res = p*mat4x3(matrix);
	//vec3 res = (vec4(p,1)*(matrix)).xyz;
	
	float l_w = 1f / (p.x * matrix[3][0] + p.y * matrix[3][1] + p.z * matrix[3][2] + matrix[3][3]);
	
	float x =(p.x * matrix[0][0] + p.y * matrix[0][1] + p.z * matrix[0][2] + matrix[0][3]) * l_w;
	float y = (p.x * matrix[1][0] + p.y * matrix[1][1] + p.z * matrix[1][2] + matrix[1][3]) * l_w;
	float z = (p.x * matrix[2][0] + p.y * matrix[2][1] + p.z * matrix[2][2] + matrix[2][3]) * l_w;
	
	vec3 res = vec3(x,y,z);
	
	return res;
}

vec3 prj(vec3 p)
{
	
	
	return prj(p,u_projTrans);
}

vec3 project (vec3 worldCoords, float viewportX, float viewportY, float viewportWidth, float viewportHeight) {
		prj(worldCoords, u_projTrans);
		worldCoords.x = viewportWidth * (worldCoords.x + 1) / 2 + viewportX;
		worldCoords.y = viewportHeight * (worldCoords.y + 1) / 2 + viewportY;
		worldCoords.z = (worldCoords.z + 1) / 2;
		return worldCoords;
	}

vec3 project (vec3 worldCoords) {
		project(worldCoords, 0, 0, u_resolution.x, u_resolution.y);
		return worldCoords;
	}

vec3 unproject (vec3 screenCoords, float viewportX, float viewportY, float viewportWidth, float viewportHeight) {
		float x = screenCoords.x, y = screenCoords.y;
		x = x - viewportX;
		y = u_resolution.y - y;
		y = y - viewportY;
		screenCoords.x = (2 * x) / viewportWidth - 1;
		screenCoords.y = (2 * y) / viewportHeight - 1;
		screenCoords.z = 2 * screenCoords.z - 1;
		prj(screenCoords, u_invPrj);
		return screenCoords;
	}
	
vec3 unproject (vec3 screenCoords) {
		unproject(screenCoords, 0, 0, u_resolution.x, u_resolution.y);
		return screenCoords;
	}
	
//////

float smin( float a, float b, float k )
{
    float h = clamp( 0.5+0.5*(b-a)/k, 0.0, 1.0 );
    return mix( b, a, h ) - k*h*(1.0-h);
}

//signed dst to scene
float sDTS(vec2 p)//uv lol
{
	float dstToScn = 1;
	for(int i = 0; i <= shpCnt-1; i++)
	{
		
		
		
		
		
		////float d =  distance(p,posAr[i].xy/u_resolution)-0.1;	
	vec3 pA = vec3(posAr[i].xy/u_resolution,1);
	pA.x *= u_resolution.x/u_resolution.y; //map to resolution
		////float d =  distance(vec3(p,1),pA)-(posAr[i].w/(u_resolution.x*u_resolution.y));	
		////float d =  distance(vec3(p,1),pA)-(posAr[i].w/(u_resolution.x*u_resolution.y));	//z-scalar not quite accurate?
		
		//vec3 pA = prj(vec3(posAr[i].xyz));
		//pA.x *= u_resolution.x/u_resolution.y; //map to resolution
		
		float distance = distance(vec3(p,1),pA);
			
		//float d = distance - (posAr[i].w/(u_resolution.x*u_resolution.y));
		float d = distance - (posAr[i].w);
		
		
		//if(d>0) //standard threshold
		if(d>0.1)
		//if(d>posAr[i].w)
		//comment this block out for distance fields	
		{
			d=1;
		}
		
		
		//hard vs soft edges
		//dstToScn=min(dstToScn,d);		
		dstToScn=smin(dstToScn,d,.1);//regular min & field strength
		
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
	if(d>1.5)
	{
		c.w=0;
	}

	
	
	
	//c = vec4(d);
	
	return c;
}


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
	camAt = vec3(st,1);
	scrnOrig = (gl_FragCoord.xy/u_resolution);
	
	//posAr[0] = vec3(mouseAt.xy,1);//works
}


void main(){



	

	
	//vec2 st = (gl_FragCoord.xy/u_resolution);
	//st = st*2.-1.; //use for scrn prj
	//vec2 scrAt = vec2(c_position.xy/u_resolution);	
	//scrAt.x *= u_resolution.x/u_resolution.y;
	//camAt = ((c_position)*mat4x3(u_projTrans)).xyz;
	
	setMoCam();
	
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
	//gl_FragColor = vec4(1-color);
	//gl_FragColor = vec4(vec3(fract(D*10.0)),1.0);
	//gl_FragColor = vec4(vec3(fract(color.xyz*10.0)),1.0);
		
	//switch these for fuzzy fields or contour fields

}