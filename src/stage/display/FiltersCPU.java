package stage.display;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * Filter to be used at setup, because they are not optimised for performance.
 * If you need real-time filters, always use shaders.
 * 
 * @author jmcouillard
 * 
 */
public class FiltersCPU {

	public static void fastBlur(Sprite sprite, int radius) {
		PImage img = sprite.graphic;
		FiltersCPU.fastBlur(img, radius);
	}

	public static void fastBlur(PImage img, int radius) {

		if (radius < 1) {
			return;
		}

		img.loadPixels();

		int w = img.width;
		int h = img.height;
		int wm = w - 1;
		int hm = h - 1;
		int wh = w * h;
		int div = radius + radius + 1;
		int a[] = new int[wh]; // i've added this
		int r[] = new int[wh];
		int g[] = new int[wh];
		int b[] = new int[wh];
		int asum, rsum, gsum, bsum, x, y, i, p, p1, p2, yp, yi, yw; // and the
																	// asum here
		int vmin[] = new int[PApplet.max(w, h)];
		int vmax[] = new int[PApplet.max(w, h)];
		int[] pix = img.pixels;
		int dv[] = new int[256 * div];
		for (i = 0; i < 256 * div; i++) {
			dv[i] = (i / div);
		}

		yw = yi = 0;

		for (y = 0; y < h; y++) {
			asum = rsum = gsum = bsum = 0; // asum
			for (i = -radius; i <= radius; i++) {
				p = pix[yi + PApplet.min(wm, PApplet.max(i, 0))];
				asum += (p >> 24) & 0xff;
				rsum += (p & 0xff0000) >> 16;
				gsum += (p & 0x00ff00) >> 8;
				bsum += p & 0x0000ff;
			}
			for (x = 0; x < w; x++) {
				a[yi] = dv[asum];
				r[yi] = dv[rsum];
				g[yi] = dv[gsum];
				b[yi] = dv[bsum];

				if (y == 0) {
					vmin[x] = PApplet.min(x + radius + 1, wm);
					vmax[x] = PApplet.max(x - radius, 0);
				}
				p1 = pix[yw + vmin[x]];
				p2 = pix[yw + vmax[x]];

				asum += ((p1 >> 24) & 0xff) - ((p2 >> 24) & 0xff); // asum
				rsum += ((p1 & 0xff0000) - (p2 & 0xff0000)) >> 16;
				gsum += ((p1 & 0x00ff00) - (p2 & 0x00ff00)) >> 8;
				bsum += (p1 & 0x0000ff) - (p2 & 0x0000ff);
				yi++;
			}
			yw += w;
		}

		for (x = 0; x < w; x++) {
			asum = rsum = gsum = bsum = 0;
			yp = -radius * w;
			for (i = -radius; i <= radius; i++) {
				yi = PApplet.max(0, yp) + x;
				asum += a[yi]; // asum
				rsum += r[yi];
				gsum += g[yi];
				bsum += b[yi];
				yp += w;
			}
			yi = x;
			for (y = 0; y < h; y++) {
				pix[yi] = (dv[asum] << 24) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];
				if (x == 0) {
					vmin[y] = PApplet.min(y + radius + 1, hm) * w;
					vmax[y] = PApplet.max(y - radius, 0) * w;
				}
				p1 = x + vmin[y];
				p2 = x + vmax[y];

				asum += a[p1] - a[p2]; // asum
				rsum += r[p1] - r[p2];
				gsum += g[p1] - g[p2];
				bsum += b[p1] - b[p2];

				yi += w;
			}
		}

		img.updatePixels();
	}
}
