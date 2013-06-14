package stage.display;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PMatrix3D;
import processing.core.PVector;
import processing.opengl.PGraphicsOpenGL;
import processing.opengl.PShader;

public class Sprite extends BoundsDisplayObject implements PConstants {

	public static int SHADER_IGNORE_TRANSFORM = 0;
	public static int SHADER_APPLY_TRANSFORM = 1;

	// PApplet reference
	private PApplet p;

	// Graphic variables
	private String renderer;
	protected boolean initiated;
	public PGraphicsOpenGL graphic;
	private PMatrix3D drawMatrix;

	// Shader variables
	protected ArrayList<PGraphicsOpenGL> passes;
	private boolean redrawOriginalOver;
	private int redrawCount;
	private int shaderBlendMode;
	private int shaderData;

	// Blend / Background color
	protected int blendMode = PGraphics.BLEND;
	public int bgColor;

	public Sprite(PApplet p, int w, int h) {
		this(p, w, h, P3D, true);
	}

	public Sprite(PApplet p, int w, int h, String renderer) {
		this(p, w, h, renderer, true);
	}

	public Sprite(PApplet p, int w, int h, boolean initGraphic) {
		this(p, w, h, P3D, initGraphic);
	}

	public Sprite(PApplet p, int w, int h, String renderer, boolean initGraphic) {
		super(w, h);

		this.p = p;
		this.renderer = renderer;
		width = w;
		height = h;
		redrawOriginalOver = false;
		redrawCount = 1;
		shaderData = SHADER_APPLY_TRANSFORM;
		shaderBlendMode = BLEND;
		drawMatrix = new PMatrix3D();

		if (initGraphic) initGraphic(p);
	}

	protected void initGraphic(PApplet p) {
		graphic = (PGraphicsOpenGL) p.createGraphics(width, height, renderer);
		graphic.setSize(width, height);
		graphic.beginDraw();
		graphic.background(0f, 0f);
		graphic.endDraw();
		initiated = true;
	}

	public void update() {

		super.update();

		if (!initiated) initGraphic(p);

		if (hasShader()) {
			PGraphicsOpenGL previousPass = null;
			for (PGraphicsOpenGL pass : passes) {

				pass.beginDraw();
				pass.background(bgColor, 0f);

				// pass.resetMatrix();
				pass.setMatrix(graphic.getMatrix());

				if (previousPass == null) {
					if (shaderData == SHADER_APPLY_TRANSFORM) {
						draw(pass, true);
					} else {
						forceDraw();
						super.draw(pass);
					}
				} else {
					pass.blendMode(shaderBlendMode);
					pass.image(previousPass, 0, 0);
				}

				pass.endDraw();
				previousPass = pass;
			}
		}
	}

	public void draw(PGraphics dest) {
		draw(dest, false);
	}

	public void draw(PGraphics dest, boolean drawFlat) {

		if (!isVisible() && !firstUpdate && !froceDraw) return;
		froceDraw = false;

		dest.tint(tint, getFinalAlpha() * 255);
		dest.pushMatrix();
		dest.translate(x, y, z);
		if (angleX != 0) dest.rotateX(angleX);
		if (angleY != 0) dest.rotateY(angleY);
		if (angle != 0) dest.rotateZ(angle);
		dest.pushMatrix();
		dest.translate(-ax * scaleX, -ay * scaleY, -az * scaleZ);
		dest.scale(scaleX, scaleY, scaleZ);
		dest.blendMode(blendMode);
		dest.noFill();
		if (hasShader() && !drawFlat) {
			dest.image(lastPass(), 0, 0);
			if (redrawOriginalOver) {
				int blendSave = blendMode;
				setBlendMode(BLEND);
				for (int i = 0; i < redrawCount; i++)
					super.draw(dest);
				setBlendMode(blendSave);
			}
		} else {
			dest.image(graphic, 0, 0);
			super.draw(dest);
		}
		dest.popMatrix();
		dest.popMatrix();
	}

	public PMatrix3D updateDrawMatrix() {

		drawMatrix.set(p.getMatrix());
		drawMatrix.translate(x, y, z);
		if (angleX != 0) drawMatrix.rotateX(angleX);
		if (angleY != 0) drawMatrix.rotateY(angleY);
		if (angle != 0) drawMatrix.rotateZ(angle);
		// drawMatrix.
		// drawMatrix.
		// drawMatrix.pushMatrix();
		// drawMatrix.translate(-ax * scaleX, -ay * scaleY, -az * scaleZ);
		// drawMatrix.scale(scaleX, scaleY, scaleZ);

		return drawMatrix;
	}

	public void shader(PShader shader) {
		graphic.shader(shader);
	}

	public void setBlendMode(int val) {
		blendMode = val;
	}

	public float getDistance(PVector p) {
		return PApplet.sqrt(PApplet.sq(x - p.x) + PApplet.sq(y - p.y));
	}

	public void addPass(PApplet p) {
		addPass(null, p);
	}

	public void addPass(PShader shader, PApplet p) {

		// Init if passes array is null
		if (passes == null) passes = new ArrayList<PGraphicsOpenGL>();

		// Init pass graphic
		passes.add((PGraphicsOpenGL) p.createGraphics(width, height, renderer));
		lastPass().setSize(width, height);

		// Add new pass shader
		if (shader != null) lastPass().shader(shader);

		// Sync z sort
		if (zSortEnabled) lastPass().hint(DISABLE_DEPTH_MASK);

	}

	public boolean hasShader() {
		return (passes != null);
	}

	public void setRedrawOriginalOver(boolean val) {
		redrawOriginalOver = val;
	}

	public void setRedrawOriginalOverCount(int val) {
		redrawCount = val;
	}

	public void setBgColor(int color) {
		this.bgColor = color;
	}

	public void setShaderData(int type) {
		this.shaderData = type;
	}

	public void setShaderBlendMode(int type) {
		this.shaderBlendMode = type;
	}

	public void setZSort(boolean val) {
		super.setZSort(val);

		if (hasShader()) {
			for (PGraphicsOpenGL pass : passes) {
				if (val) pass.hint(PGraphics.DISABLE_DEPTH_MASK);
				else pass.hint(PGraphics.ENABLE_DEPTH_MASK);
			}
		}
	}

	public PGraphicsOpenGL lastPass() {
		return passes.get(passes.size() - 1);
	}



}