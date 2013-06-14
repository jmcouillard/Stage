package stage.display;

import processing.core.PGraphics;

public class BoundsDisplayObject extends DisplayObject {

	protected Bounds bounds;
	protected boolean showBounds;

	protected int width;
	protected int height;

	public BoundsDisplayObject(int w, int h) {
		super();
		invalidate();

		width = w;
		height = h;
		showBounds = false;
		bounds = new Bounds(w, h);
	}

	public BoundsDisplayObject() {
		this(0, 0);
		invalidate();
	}

	public void update() {
		if (isInvalid()) {
			bounds.refreshBounds(getProcessedFinalPos(), ax, ay, getFinalRotation(), getFinalScaleX(), getFinalScaleY(), width, height);
			invalid = false;
		}

		super.update();
	}

	public void drawBounds(PGraphics dest) {

		if (showBounds) {

			// Draw stroke
			dest.noTint();
			dest.fill(255, 100);
			dest.stroke(255, 20, 255, 255);
			dest.strokeWeight(2f);
			dest.beginShape();
			for (int i = 0; i < bounds.p.length; i++) {
				dest.vertex(bounds.p[i].x, bounds.p[i].y);
			}
			dest.endShape(PGraphics.CLOSE);

			// Draw anchor point
			dest.fill(255, 100);
			dest.ellipse(getProcessedFinalPos().x, getProcessedFinalPos().y, 5, 5);
		}

		for (DisplayObject child : children) {
			if (child instanceof BoundsDisplayObject) {
				((BoundsDisplayObject) child).drawBounds(dest);
			}
		}
	}

	public void showBounds(boolean val) {
		this.showBounds = val;
	}

	public Bounds getBounds() {
		if (bounds == null) return null;
		return bounds;
	}

	public float getWidth() {
		return width * getFinalScaleX();
	}

	public float getHeight() {
		return height * getFinalScaleY();
	}

	public void centerAnchor() {
		setAnchor(width / 2, height / 2);
	}

}
