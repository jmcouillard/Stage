
#define PROCESSING_TEXTURE_SHADER

uniform sampler2D texture;
varying vec4 vertTexCoord;

uniform int BlendMode;
uniform vec2 TexelSize;
uniform int BlurAmount;
uniform float BlurScale;

uniform float Multiplicator;
uniform vec4 ColorOffset;

/// Gets the Gaussian value in the first dimension.
float Gaussian (float x, float deviation)
{
    return (1.0 / sqrt(2.0 * 3.141592 * deviation)) * exp(-((x * x) / (2.0 * deviation)));  
}


void main() {
    
    vec4 dst = texture2D(texture, vertTexCoord.st); // rendered scene
    vec4 colour = vec4(0.0);
    vec4 src = vec4(0.0);
    
    int Orientation  = 0;
    
        
    float halfBlur = float(BlurAmount) * 0.5;
   
    
    float deviation = halfBlur * 0.35;
    deviation *= deviation;
    float strength = 1.0;
    
    
    //float alpha = max(src.a, src.a);
    //float alpha = src.a - dst.a;
    
     for (int i = 0; i < 10; ++i) {
        if ( i >= BlurAmount )
        break;

        float offset = float(i) - halfBlur;

        if ( Orientation == 0 || Orientation == 2) {            
            src = texture2D(texture, vertTexCoord.st + vec2(offset * TexelSize.x * BlurScale, 0.0)) * Gaussian(offset * strength, deviation);
            colour += src;
        }
    }
    
   float finalAlpha = min(dst.a, (1.0-colour.a)*Multiplicator);



    if ( BlendMode == 0 )
    {    
        // Additive blending (strong result, high overexposure)
        gl_FragColor = min((colour+ColorOffset)*finalAlpha+dst, 1.0);
        gl_FragColor.a = dst.a;
    }
    else if ( BlendMode == 1 )
    {
        // Screen blending (mild result, medium overexposure)
        gl_FragColor = clamp((src + dst) - (src * dst), 0.0, 1.0);
        gl_FragColor.a = finalAlpha;
    }
    else if ( BlendMode == 2 )
    {
        gl_FragColor.a = dst.a;
    
        // Softlight blending (light result, no overexposure)
        // Due to the nature of soft lighting, we need to bump the black region of the glowmap
        // to 0.5, otherwise the blended result will be dark (black soft lighting will darken
        // the image).
        src = (src * 0.5) + 0.5;
        
        gl_FragColor.xyz = vec3((src.x <= 0.5) ? (dst.x - (1.0 - 2.0 * src.x) * dst.x * (1.0 - dst.x)) : (((src.x > 0.5) && (dst.x <= 0.25)) ? (dst.x + (2.0 * src.x - 1.0) * (4.0 * dst.x * (4.0 * dst.x + 1.0) * (dst.x - 1.0) + 7.0 * dst.x)) : (dst.x + (2.0 * src.x - 1.0) * (sqrt(dst.x) - dst.x))),
                    (src.y <= 0.5) ? (dst.y - (1.0 - 2.0 * src.y) * dst.y * (1.0 - dst.y)) : (((src.y > 0.5) && (dst.y <= 0.25)) ? (dst.y + (2.0 * src.y - 1.0) * (4.0 * dst.y * (4.0 * dst.y + 1.0) * (dst.y - 1.0) + 7.0 * dst.y)) : (dst.y + (2.0 * src.y - 1.0) * (sqrt(dst.y) - dst.y))),
                    (src.z <= 0.5) ? (dst.z - (1.0 - 2.0 * src.z) * dst.z * (1.0 - dst.z)) : (((src.z > 0.5) && (dst.z <= 0.25)) ? (dst.z + (2.0 * src.z - 1.0) * (4.0 * dst.z * (4.0 * dst.z + 1.0) * (dst.z - 1.0) + 7.0 * dst.z)) : (dst.z + (2.0 * src.z - 1.0) * (sqrt(dst.z) - dst.z))));
                    
    }
    else
    {
       
        
        // Show just the glow map
        gl_FragColor = colour;
        gl_FragColor.a = finalAlpha;
        
        //gl_FragColor = vec4(finalAlpha, finalAlpha, finalAlpha, 1.0);
    }    
    
}