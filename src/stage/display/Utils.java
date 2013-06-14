package stage.display;

public class Utils {

	public static int color(int v1, int v2, int v3) {
		
	    if (v1 > 255) v1 = 255; else if (v1 < 0) v1 = 0;
	    if (v2 > 255) v2 = 255; else if (v2 < 0) v2 = 0;
	    if (v3 > 255) v3 = 255; else if (v3 < 0) v3 = 0;
	
	    return 0xff000000 | (v1 << 16) | (v2 << 8) | v3;
	}
	
	public static int colorBlend (int c1, int c2, float ratio)
	  {
		float r1 = c1 >> 16 & 0xFF;
	    float g1 = c1 >> 8 & 0xFF;
	    float b1 = c1 & 0xFF;

	    float r2 = c2 >> 16 & 0xFF;
	    float g2 = c2 >> 8 & 0xFF;
	    float b2 = c2 & 0xFF;	    
		
	    float r  = (float) ratio;
	    float ir = (float) 1.0-r;

	    int color = color((int)(r1*r + r2*ir), (int)(g1*r + g2*ir), (int)(b1*r + b2*ir));
	    
	    return color;
	  }

	
	public static void error(String message) {
		System.err.println("FlashLike : " + message);
	}
}
