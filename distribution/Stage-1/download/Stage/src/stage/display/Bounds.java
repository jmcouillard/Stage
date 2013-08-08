package stage.display;

import processing.core.PVector;
import stage.geom.Vector2D;

public class Bounds {

	public PVector[] p;

	// private int width;
	// private int height;

	public Bounds(int w, int h) {
		// width = w;
		// height = h;

		p = new PVector[4];
		for (int i = 0; i < 4; i++) {
			p[i] = new PVector();
		}
	}

	// public void setWidth(int val) {
	// width = val;
	// }
	//
	// public void setHeight(int val) {
	// height = val;
	// }

	public void refreshBounds(PVector pos, float ax, float ay, float rotation, float scaleX, float scaleY, float width, float height) {
		refreshBounds(pos.x, pos.y, ax, ay, rotation, scaleX, scaleY, width, height);
	}

	public void refreshBounds(float x, float y, float ax, float ay, float rotation, float scaleX, float scaleY, float width, float height) {

		ax *= scaleX;
		ay *= scaleY;

		p[0] = rotatePoint(0, 0, ax, ay, rotation);
		p[1] = rotatePoint(width * scaleX, 0, ax, ay, rotation);
		p[2] = rotatePoint(width * scaleX, height * scaleY, ax, ay, rotation);
		p[3] = rotatePoint(0, height * scaleY, ax, ay, rotation);

		for (int i = 0; i < 4; i++) {
			p[i].add(x, y, 0);
		}
	}

	public static PVector rotatePoint(float x, float y, float cx, float cy, float angle) {
		PVector p = new PVector(x - cx, y - cy);

		// Rotation factors
		float s = (float) Math.sin(angle);
		float c = (float) Math.cos(angle);

		// rotate point
		float xnew = p.x * c - p.y * s;
		float ynew = p.x * s + p.y * c;

		// translate point back:
		p.x = xnew;
		p.y = ynew;

		return p;
	}

	public boolean isOver(Vector2D point) {
		return this.isOver(point.x, point.y);
	}

	public boolean isOver(PVector point) {
		return this.isOver(point.x, point.y);
	}

	// public int getWidth() {
	// return this.width;
	// }
	//
	// public int getHeight() {
	// return this.height;
	// }

	public String toString() {
		return ("(" + p[0].x + ", " + p[0].y + "), (" + p[1].x + ", " + p[1].y + "), (" + p[2].x + ", " + p[2].y + "), (" + p[3].x + ", " + p[3].y + ")");
	}

	public boolean isOver(float x, float y) {

		if (p[0].equals(p[1]) || p[2].equals(p[3])) return false;

		for (int i = 0; i < 4; i++) {
			float x1 = p[i].x;
			float y1 = p[i].y;
			float x2 = p[(i + 1) % 4].x;
			float y2 = p[(i + 1) % 4].y;
			float D = (-(y2 - y1) * x) + ((x2 - x1) * y) + (-(-(y2 - y1) * x1 + (x2 - x1) * y1));
			if (D < 0) return false;
		}
		return true;
	}

}