#ifdef GL_ES
precision mediump float;
#endif

#define PI 3.14159265359
#define TWO_PI 6.28318530718

varying vec4 v_color;   
varying vec2 v_texCoords;
varying vec3 c_position; //camera-fragment position

uniform vec3 u_camP; //camera-world position
uniform vec3 u_camD; //camera-world direction
uniform mat4 u_projTrans; //camera combined matrix
uniform vec2 u_resolution;
uniform vec3 u_mouse; //mouse position
uniform float u_time;



//////////////////////////////////////////////////



const float EPS=0.001;

//BS
uniform int shpCnt;
uniform vec3 posAr[512];

struct ray {
    vec3 pos;
    vec3 dir;
};
//Create the camera ray
ray create_camera_ray(vec2 uv, vec3 camPos, vec3 lookAt, float zoom){
    vec3 f = normalize(lookAt - camPos);
    vec3 r = cross(vec3(0.0,1.0,0.0),f);
    vec3 u = cross(f,r);
    vec3 c=camPos+f*zoom;
    vec3 i=c+uv.x*r+uv.y*u;
    vec3 dir=i-camPos;
    return ray(camPos,normalize(dir));
}
//Distance to scene at point
float distToScene(vec3 p){
    return min(p.z,min(p.x,min(p.y,length(p-vec3(0.3,0.0,0.4))-0.3)));
}
//Estimate normal based on distToScene function

vec3 estimateNormal(vec3 p){
    float xPl=distToScene(vec3(p.x+EPS,p.y,p.z));
    float xMi=distToScene(vec3(p.x-EPS,p.y,p.z));
    float yPl=distToScene(vec3(p.x,p.y+EPS,p.z));
    float yMi=distToScene(vec3(p.x,p.y-EPS,p.z));
    float zPl=distToScene(vec3(p.x,p.y,p.z+EPS));
    float zMi = distToScene(vec3(p.x,p.y,p.z-EPS));
    float xDiff = xPl-xMi;
    float yDiff = yPl-yMi;
    float zDiff = zPl-zMi;
    return normalize(vec3(xDiff,yDiff,zDiff));
}
void main(){
    //vec2 uv=gl_FragCoord.xy/u_resolution.xy;
    vec2 uv=c_position.xy/u_resolution.xy;
    uv-=vec2(0.5);//offset, so center of screen is origin
    uv.x*=u_resolution.x/u_resolution.y;//scale, so there is no rectangular distortion
   
    //vec3 camPos=vec3(2.0,1.0,0.5);
    //vec3 lookAt=vec3(0.0);
    vec3 camPos = u_camP;
    vec3 lookAt = u_camD;
    float zoom=1.0;
    
    ray camRay=create_camera_ray(uv,camPos,lookAt,zoom);
    
    float totalDist=0.0;
    float finalDist=distToScene(camRay.pos);
    int iters=0;
    int maxIters=20;
    for(iters=0;iters<maxIters&&finalDist>0.01;iters++){
        camRay.pos+=finalDist*camRay.dir;
        totalDist+=finalDist;
        finalDist=distToScene(camRay.pos);
    }
    vec3 normal=estimateNormal(camRay.pos);
    
    vec3 lightPos=vec3(2.0,1.0,1.0);
    
    float dotSN=dot(normal,normalize(lightPos-camRay.pos));
    
    gl_FragColor=vec4(0.5+0.5*normal,1.0)*dotSN;
}