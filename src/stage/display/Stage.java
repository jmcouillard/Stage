package stage.display;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class Stage extends DisplayObject {

	public static long frameCount;
	public PGraphics graphic;

	private int width;
	private int height;
	private int bgColor = 0x00000000;

	public Stage(PApplet p, int w, int h) {
		width = w;
		height = h;
		graphic = p.createGraphics(width, height, PApplet.P3D);
		graphic.beginDraw();
		graphic.smooth(8);
		graphic.endDraw();

		// Events
		p.registerMethod("mouseEvent", this);
		p.registerMethod("keyEvent", this);
	}

	public void update() {
		super.update();

		graphic.beginDraw();
		graphic.background(bgColor);
		if (frameCount > 1) {
			super.draw(graphic);
			drawBounds();
		}
		graphic.endDraw();

		frameCount++;
	}

	public void drawBounds() {
		for (DisplayObject child : children) {
			if (child instanceof BoundsDisplayObject) {
				((BoundsDisplayObject) child).drawBounds(graphic);
			}
		}
	}

	public void draw(PApplet p) {
		p.pushMatrix();
		p.translate(width / 2, height / 2);
		p.pushMatrix();
		p.rotate(angle);
		p.translate(-width / 2, -height / 2);
		p.image(graphic, 0, 0);
		p.popMatrix();
		p.popMatrix();

	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setZSort(boolean val) {
		super.setZSort(val);
		if (val) graphic.hint(PGraphics.DISABLE_DEPTH_MASK);
		else graphic.hint(PGraphics.ENABLE_DEPTH_MASK);
	}
	
	public void setBgColor(int bgColor) {
		this.bgColor = bgColor;
	}

	public void mouseEvent(MouseEvent e) {
		if (e.getAction() == MouseEvent.MOVE) dispatchEvent(new stage.events.MouseEvent(stage.events.MouseEvent.MOUSE_MOVE, e.getX(), e.getY()));
		if (e.getAction() == MouseEvent.PRESS) dispatchEvent(new stage.events.MouseEvent(stage.events.MouseEvent.MOUSE_DOWN, e.getX(), e.getY()));
		if (e.getAction() == MouseEvent.CLICK) dispatchEvent(new stage.events.MouseEvent(stage.events.MouseEvent.MOUSE_CLICK, e.getX(), e.getY()));
	}

	public void keyEvent(KeyEvent e) {
		if (e.getAction() == KeyEvent.PRESS) dispatchEvent(new stage.events.KeyboardEvent(stage.events.KeyboardEvent.KEY_DOWN, e.getKeyCode(), e.getKey()));
	}

}
