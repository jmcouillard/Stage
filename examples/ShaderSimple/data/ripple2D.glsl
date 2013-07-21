#define PROCESSING_TEXTURE_SHADER

uniform sampler2D texture;

varying vec4 vertColor;
varying vec4 vertTexCoord;
uniform vec2 texOffset;

uniform float time; // Time uniform is passed by Processing!
uniform float scaledTime;

uniform vec2 rippleOrigin;
uniform float rippleHeight;
uniform float rippleQuantity;
uniform float rippleMaxDistance;

#define PI 3.1415926535897932384626433832795
#define PI_HALF 1.5707963267949

void main(void)
{
    
    vec2 normalizedCoords = vertTexCoord.xy;
    float distanceFromOrigin = distance(normalizedCoords, rippleOrigin);
    
    float distanceCoefficient = min(1.0, max(0.0, rippleMaxDistance - distanceFromOrigin));
      
    //Make the strength of the ripples taper off as they reach the edge
    float dampedHeight = rippleHeight * distanceCoefficient;

    float distortion = cos(((distanceFromOrigin * rippleQuantity) - time));
    
    vec2 sourcePixel = normalizedCoords + (distortion * dampedHeight);
        
    // gl_FragColor = texture2D(textureSampler, sourcePixel) *0.8 + texture2D(textureSampler, normalizedCoords) *0.2;
    gl_FragColor = texture2D(texture, sourcePixel);
}