#ifdef GL_ES
precision mediump float;
#endif

#define PI 3.14159265359
#define TWO_PI 6.28318530718
#define EPS	0.001
#define MAX_STEPS 200
#define MAX_DIST 100.0

varying vec4 v_color;   
varying vec2 v_texCoords;
varying vec3 c_position; //camera position

uniform mat4 u_projTrans; //camera combined matrix
uniform mat4 u_projInv;
uniform mat4 u_projPrj;
uniform mat4 u_projView;
uniform vec2 u_resolution; //screen size
uniform vec3 u_mouse; //mouse position
uniform float u_time;



//////////////////////////////////////////////////

vec3 camDir;
vec3 camAt;
vec2 scrAt;
vec3 lightAt;


struct Ray {
    vec3 origin;
    vec3 direction;
};

Ray castRay(vec3 origin, vec3 direction)
{
	Ray ray;
	ray.origin = origin;
	ray.direction=direction;
	return ray;
}

Ray CreateCameraRay(vec2 uv) {
    vec3 origin = (u_projTrans*vec4(0,0,0,1)).xyz;
    vec3 direction = (u_projInv*vec4(uv,0,1)).xyz;
    direction = (u_projTrans* vec4(direction,0)).xyz;
    direction = normalize(direction);
    return castRay(origin,direction);
}


///////////////////////////////////////////////////
//BS
uniform int numShapes;
uniform vec3 posAr[500];
uniform vec3 sclAr[500];

vec3 lerp (vec3 at, vec3 target, vec3 alpha) {
	at.x += alpha * (target.x - at.x);
	at.y += alpha * (target.y - at.y);
	at.z += alpha * (target.z - at.z);
	return at;
}

vec3 dir(vec3 a, vec3 b)
{

	vec3 n = normalize(a-b);
	return n*-1.;
}

float smin( float a, float b, float k )
{
    float h = clamp( 0.5+0.5*(b-a)/k, 0.0, 1.0 );
    return mix( b, a, h ) - k*h*(1.0-h);
}

vec3 prj (vec3 p, mat4 matrix) {

	vec3 res = p*mat4x3(matrix);
	return res;
}
	

vec3 unprj(vec3 p, mat4 matrix)
{
		float x = p.x - scrAt.x;
		float y = u_resolution.y - p.y - scrAt.y;
		p.x = (2 * x) / u_resolution.x - 1;
		p.y = (2 * y) / u_resolution.y - 1;
		p.z = 2 * p.z - 1;
		p=prj(p,matrix);
		return p;
}



vec3 unproject(vec3 p)
{
return unprj(p,u_projInv);
}


vec3 project(vec3 p)
{

	p = prj(p,u_projTrans);
	p.x = u_resolution.x* (p.x + 1) / 2 + scrAt.x;
	p.y = u_resolution.y* (p.y + 1) / 2 + scrAt.y;
	p.z = (p.z + 1) / 2 ;
	return p;
}



float map(float value, float min1, float max1, float min2, float max2) {
  return min2 + (value - min1) * (max2 - min2) / (max1 - min1);
}


// polynomial smooth min (k = 0.1);
// from https://www.iquilezles.org/www/articles/smin/smin.htm
vec4 blend( float a, float b, vec3 colA, vec3 colB, float k )
{
    float h = clamp( 0.5+0.5*(b-a)/k, 0.0, 1.0 );
    float blendDst = lerp( b, a, h ) - k*h*(1.0-h);
    vec3 blendCol = lerp(colB,colA,h);
    return vec4(blendCol, blendDst);
}


vec4 combine(float dstA, float dstB, vec3 colourA, vec3 colourB, int operation, float blendStrength) {
    float dst = dstA;
    vec3 colour = colourA;

    if (operation == 0) {
        if (dstB < dstA) {
            dst = dstB;
            colour = colourB;
        }
    } 
    // Blend
    else if (operation == 1) {
        vec4 blend = blend(dstA,dstB,colourA,colourB, blendStrength);
        dst = blend.w;
        colour = blend.xyz;
    }
    // Cut
    else if (operation == 2) {
        // max(a,-b)
        if (-dstB > dst) {
            dst = -dstB;
            colour = colourB;
        }
    }
    // Mask
    else if (operation == 3) {
        // max(a,b)
        if (dstB > dst) {
            dst = dstB;
            colour = colourB;
        }
    }

    return vec4(colour,dst);
}

////////////////////////

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
		//dstToScn=smin(dstToScn,d,0.01);//regular min & field strength
		dstToScn=smin(dstToScn,d,0.05);//regular min & field strength
		
	}
	

	return dstToScn;

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
			c.b = (d*10);
			//c.w = 1-d;
			c.a = (1-(d*50));
		}
		if(d<0)
		{
		 c.r = 0;
			//c.g = d;
			c.g = 1-(d*2);
			c.b = (d*10);
			//c.w = 1-d;
			c.a = (d);
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


/////////




/////////////////////////////////////////


void main(){


	
	lightAt = vec3(gl_FragCoord.xy,camAt.z+1);
     
	
	scrAt = vec2(c_position.xy/u_resolution);	
	scrAt.x *= u_resolution.x/u_resolution.y;
	//scrAt = scrAt*2.-1.;
	camAt = c_position*mat4x3(u_projTrans);
	
	//Ray ray = castRay(camAt,camDir);
	//Ray ray = CreateCameraRay(scrAt);
	
	float d = sDTS(scrAt);
	
	
	//gl_FragColor = vec4(1);	
	//gl_FragColor = vec4(sDTS(ray.origin));
	//gl_FragColor = vec4(d);
	gl_FragColor = vec4(cDTS(scrAt));
	//gl_FragColor = vec4(vec3(fract(D*10.0)),1.0);
		
	//switch these for fuzzy fields or contour fields

}