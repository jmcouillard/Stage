package stage.display;

import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

public class Image extends BoundsDisplayObject {

	public static HashMap<String, PImage> imageLibrary = new HashMap<String, PImage>();
	private String file;
	private PApplet p;

	private int blendMode = PGraphics.BLEND;
	private int tint = 0xFFFFFFFF;
	private int padding = 0;

	public Image(PApplet p) {
		super();
		this.p = p;
	}

	public Image(PApplet p, String file) {
		super();
		this.p = p;
		loadImage(file, false);
	}

	public Image(PApplet p, String file, boolean async) {
		super();
		this.p = p;
		loadImage(file, async);
	}

	public void loadImage(String file, boolean async) {

		if (!Image.imageLibrary.containsKey(file)) {
			if (async) {
				//	println("Loading async image : " + file);
				Image.imageLibrary.put(file, p.requestImage(file));
				this.file = file;
			} else {
				//	println("Loading image : " + file);
				Image.imageLibrary.put(file, p.loadImage(file));
				this.file = file;
				width = getImage().width + padding * 2;
				height = getImage().height + padding * 2;
				invalidate();
			}
		} else {
			this.file = file;
			width = getImage().width + padding * 2;
			height = getImage().height + padding * 2;
		}
	}

	public void update() {

		if (width == 0 && getImage() != null && getImage().width != 0) {
			width = getImage().width + padding * 2;
			height = getImage().height + padding * 2;
			invalidate();
		}

		super.update();
	}

	public void draw(PGraphics dest) {

		if (isVisible() && file != null) {
			dest.tint(tint, getFinalAlpha() * 255);
			dest.pushMatrix();
			dest.translate(x, y, z);
			if (angleX != 0) dest.rotateY(angleX);
			if (angleY != 0) dest.rotateY(angleY);
			if (angle != 0) dest.rotateZ(angle);
			dest.pushMatrix();
			dest.translate(-ax * scaleX, -ay * scaleY, -az * scaleZ);
			dest.scale(scaleX, scaleY, scaleZ);
			dest.blendMode(blendMode);
			// if(isMasking()) dest.mask(mask);
			dest.image(getImage(), padding, padding);
			super.draw(dest);
			dest.popMatrix();
			dest.popMatrix();
		}

	}

	public PImage getImage() {
		return Image.imageLibrary.get(file);
	}

	public void setBlendMode(int val) {
		blendMode = val;
	}

	public void setTint(int r, int g, int b) {
		tint = Utils.color(r, g, b);
	}
}
