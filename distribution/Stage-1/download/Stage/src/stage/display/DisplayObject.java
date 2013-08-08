package stage.display;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import de.looksgood.ani.Ani;
import de.looksgood.ani.easing.Easing;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PMatrix3D;
import processing.core.PVector;
import stage.events.Event;
import stage.events.EventListener;

public class DisplayObject extends EventListener {

	public float dummy = 0f;

	protected boolean firstUpdate = true;
	protected boolean froceDraw = false;

	public String name;
	protected boolean zSortEnabled = false;
	private float zSortOffset = 0;

	protected DisplayObject parent;
	protected ArrayList<DisplayObject> children;

	protected PVector screenPos;

	protected float x = 0.0f;
	protected float y = 0.0f;
	protected float z = 0.0f;
	protected float ax = 0.0f;
	protected float ay = 0.0f;
	protected float az = 0.0f;
	protected float alpha = 1.0f;
	protected float angle = 0.0f;
	protected float angleX = 0.0f;
	protected float angleY = 0.0f;
	protected int tint = 0xFFFFFFFF;
	protected float scaleX = 1f;
	protected float scaleY = 1f;
	protected float scaleZ = 1f;
	protected boolean visible = true;
	protected boolean invalid = false;

	public DisplayObject() {
		children = new ArrayList<DisplayObject>();
	}

	public void update() {
		for (int i = 0; i < children.size(); i++) {
			children.get(i).update();
		}

		if (zSortEnabled) zSort();
		firstUpdate = false;
		froceDraw = false;
	}

	private void zSort() {
		Collections.sort(children, new zSortComparator());
		for (int i = 0; i < children.size(); i++) {
			// children.get(i).zSort();
		}
	}

	public void invalidate() {
		invalid = true;
		screenPos = null;

		for (int i = 0; i < children.size(); i++) {
			children.get(i).invalidate();
		}
	}

	public boolean isInvalid() {
		return invalid;
	}

	public boolean containsInvalid() {
		if (isInvalid()) return true;
		for (int i = 0; i < children.size(); i++) {
			if (children.get(i).containsInvalid()) return true;
		}
		return false;
	}

	public void draw(PGraphics dest) {
		for (int i = 0; i < children.size(); i++) {
			children.get(i).draw(dest);
		}
	}

	public void forceDraw() {
		froceDraw = true;
		for (int i = 0; i < children.size(); i++) {
			children.get(i).froceDraw = true;
		}
	}

	public void addChild(DisplayObject child) {
		if (child != null && child != this) {
			children.add(child);
			child.parent = this;
		}
	}

	public void removeChild(DisplayObject child) {
		if (child != null && child != this) {
			children.remove(child);
		}
	}

	public void getChildAt(int i) {
		if (i < children.size()) {
			children.get(i);
		}
	}

	public ArrayList<DisplayObject> getChildren() {
		return children;
	}

	public void dispatchEvent(Event e) {
		super.dispatchEvent(e);
		for (int i = 0; i < children.size(); i++) {
			children.get(i).dispatchEvent(e);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String val) {
		name = val;
	}

	public float getX() {
		return x;
	}

	public PMatrix3D getMatrix() {
		PMatrix3D mat = new PMatrix3D();
		// // mat.translate(-ax, -ay);
		// mat.rotate(parent.angle);
		// mat.translate(parent.x - parent.ax, parent.y - parent.ay);

		mat.reset();
		mat.translate(x, y, z);
		if (angleX != 0) mat.rotateX(angleX);
		if (angleY != 0) mat.rotateY(angleY);
		if (angle != 0) mat.rotateZ(angle);
		// drawMatrix.
		// drawMatrix.pushMatrix();
		mat.translate(-ax * scaleX, -ay * scaleY, -az * scaleZ);
		mat.scale(scaleX, scaleY, scaleZ);

		return mat;
	}

	public float getFinalX() {

		if (parent != null) {
			return parent.getFinalX() + (-parent.ax + x) * parent.getFinalScale();
		} else return x;
	}

	protected PVector getProcessedFinalPos() {

		if (screenPos != null) {
			return screenPos;
		} else {
			if (parent != null) {
				screenPos = PVector.add(parent.getProcessedFinalPos(), Bounds.rotatePoint(getFinalX(), getFinalY(), parent.getFinalX(), parent.getFinalY(), parent.getFinalRotation()));
			} else {
				screenPos = new PVector(x, y);
			}
		}
		return screenPos;
	}

	public void setX(float val) {
		invalidate();
		x = val;
	}

	public float getY() {
		return y;
	}

	public float getFinalY() {

		if (parent != null) {
			return parent.getFinalY() + (-parent.ay + y) * parent.getFinalScale();
		} else return y;
	}

	public void setY(float val) {
		invalidate();
		y = val;
	}

	public float getZ() {
		return z;
	}

	public float getFinalZ() {

		if (parent != null) {
			return parent.getFinalZ() + (-parent.az + z) * parent.getFinalScale();
		} else return y;
	}

	public void setZ(float val) {
		invalidate();
		z = val;
	}

	public void setZSort(boolean val) {
		zSortEnabled = val;
	}

	public void setZSortOffset(float val) {
		zSortOffset = val * 1000f;
	}

	public float getZSortOffset() {
		return zSortOffset;
	}

	public void setPos(float x, float y) {
		invalidate();
		this.x = x;
		this.y = y;
	}

	public void setPos(float x, float y, float z) {
		invalidate();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void setPos(PVector v) {
		invalidate();
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}

	public PVector getPos() {
		return new PVector(x, y, z);
	}

	public void setRotation(float val) {
		angle = val;
		invalidate();
	}

	public void setRotationX(float val) {
		angleX = val;
		invalidate();
	}

	public void setRotationY(float val) {
		angleY = val;
		invalidate();
	}

	public void setRotationDegrees(int val) {
		angle = (float) (val * (Math.PI / 180f));
	}

	public float getRotation() {
		return angle;
	}

	public float getFinalRotation() {
		if (parent != null) return parent.getFinalRotation() + angle;
		else return angle;
	}

	public void setAlpha(float val) {
		alpha = val;
	}

	public float getScale() {
		return scaleX;
	}

	public void setScale(float val) {
		invalidate();
		scaleX = scaleY = scaleZ = val;
	}

	public void setScaleX(float val) {
		invalidate();
		scaleX = val;
	}

	public void setScaleY(float val) {
		invalidate();
		scaleY = val;
	}

	public void setScaleZ(float val) {
		invalidate();
		scaleZ = val;
	}

	public float getFinalScale() {

		if (parent != null) {
			return parent.getFinalScale() * scaleX;
		} else return scaleX;
	}

	public float getFinalScaleX() {

		if (parent != null) {
			return parent.getFinalScaleX() * scaleX;
		} else return scaleX;
	}

	public float getFinalScaleY() {

		if (parent != null) {
			return parent.getFinalScaleY() * scaleY;
		} else return scaleY;
	}

	public float getFinalScaleZ() {

		if (parent != null) {
			return parent.getFinalScaleZ() * scaleZ;
		} else return scaleZ;
	}

	public PVector getScreenPos(PApplet p) {
		return new PVector(p.screenX(getFinalX(), getFinalY(), getFinalZ()), p.screenY(getFinalX(), getFinalY(), getFinalZ()));
	}

	public void setVisible(boolean val) {
		visible = val;
	}

	public void setAnchor(float ax, float ay) {
		setAnchor(ax, ay, this.az);
	}

	public void setAnchor(float ax, float ay, float az) {
		this.ax = ax;
		this.ay = ay;
		this.az = az;
		invalidate();
	}

	public float getAlpha() {
		return PApplet.constrain(this.alpha, 0f, 1f);
	}

	public boolean getFinalVisible() {
		if (parent == null) return this.visible;
		else return ((parent.getFinalVisible()) && (this.visible));
	}

	public float getFinalAlpha() {
		if (parent == null) return getAlpha();
		else return (float) (Math.max(0f, (getAlpha() - (1f - parent.getFinalAlpha()))));
	}

	public void setTint(int r, int g, int b) {
		tint = Utils.color(r, g, b);
		for (int i = 0; i < children.size(); i++) {
			children.get(i).setTint(tint);
		}
	}

	public void setTint(int val) {
		tint = val;
		for (int i = 0; i < children.size(); i++) {
			children.get(i).setTint(val);
		}
	}

	public float getAnchorX() {
		return this.ax;
	}

	public float getAnchorY() {
		return this.ay;
	}

	public float getAnchorZ() {
		return this.az;
	}

	public DisplayObject getParent() {
		return this.parent;
	}

	public boolean isVisible() {
		return ((this.getFinalAlpha() > 0.01f) && (getFinalVisible() == true)) || (firstUpdate);
	}

	public void dispose() {
		for (int i = 0; i < children.size(); i++) {
			DisplayObject item = children.get(i);
			item.dispose();
			removeChild(children.get(i));
			item = null;
		}
	}

	public void println(Object val) {
		PApplet.println(val);
	}

	public class zSortComparator implements Comparator<DisplayObject> {
		public int compare(DisplayObject o1, DisplayObject o2) {
			return Float.compare(o1.getZ() + o1.getZSortOffset(), o2.getZ() + o2.getZSortOffset());
		}
	}

	public void to(float duration, String properties, Easing ease) {
		DisplayObject.to(this, duration, 0f, properties, ease, "");
	}

	public void to(float duration, String properties) {
		DisplayObject.to(this, duration, 0f, properties, Easing.SINE_IN_OUT, "");
	}

	public void to(float duration, float delay, String properties) {
		DisplayObject.to(this, duration, delay, properties, Easing.SINE_IN_OUT, "");
	}

	public void to(float duration, float delay, String properties, Easing ease) {
		DisplayObject.to(this, duration, delay, properties, ease, "");
	}

	public void to(float duration, float delay, String properties, Easing ease, String callback) {
		DisplayObject.to(this, duration, delay, properties, ease, callback);
	}

	public static void to(Object target, float duration, float delay, String properties, Easing ease, String callback) {
		Ani.to(target, duration, delay, properties, ease, "onUpdate:invalidate," + callback);
	}

}
