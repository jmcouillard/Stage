package stage.display;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.opengl.PShader;

public class TextField extends Sprite {

	// Font and text settings
	private String text;
	private PFont font;
	private int color;
	private int fontSize;
	private int alignH;
	private int alignV;
	private int lineSpacing = -1;

	// PApplet reference
	private PApplet p;

	// Metrics
	private float tw;
	private float th;
	private int linesCount;

	// Invalidate
	private boolean textInvalid;

	public TextField(PApplet p, int w, int h) {
		this(p, w, h, true);
	}

	public TextField(PApplet p, int w, int h, boolean initGraphic) {
		super(p, w, h, PApplet.P2D, initGraphic);

		this.p = p;

		color = 0xFF000000;
		bgColor = 0xFF000000;
		fontSize = 12;
		text = "";
		tw = -1;
		th = -1;
		linesCount = -1;

		alignH = PGraphics.CENTER;
		alignV = PGraphics.TOP;
		textInvalid = true;
	}

	public void initGraphic(PApplet p) {
		super.initGraphic(p);

		graphic.textMode(PGraphics.MODEL);
		graphic.smooth(8);
	}

	public void update() {

		if (!initiated) initGraphic(p);

		if (isTextInvalid()) {

			if (font == null) {
				Utils.error("Stage : Can't render TextField without defining the font.");
				return;
			}

			// PGraphics dest = hasShader() ? shaderGraphic : graphic;

			PGraphics dest = graphic;

			// Settings
			dest.textFont(font);
			dest.textSize(fontSize);
			dest.textAlign(alignH, alignV);
			if (lineSpacing != -1) dest.textLeading(lineSpacing);

			// Draw
			dest.beginDraw();
			dest.clear();
			dest.background(bgColor, 0f);
			dest.fill(color);
			dest.text(text, 0, 0, width, height - dest.textDescent() - dest.textDescent());
			dest.endDraw();
			textInvalid = false;

			// if (hasShader()) {
			// graphic.beginDraw();
			// // dest.clear();
			// graphic.background(color, 220f);
			// graphic.endDraw();
			// }

			// Reset text width
			tw = -1;
			th = -1;
			linesCount = -1;
		}

		super.update();

	}

	public void setLineSpacing(int val) {
		this.lineSpacing = val;
		invalidateText();
	}

	public void setText(String text) {

		text = text.replace("\\n", "\n");

		if (this.text.compareTo(text) != 0) {
			this.text = text;
			invalidateText();

			// Updating here cause concurent errors
			// update();
		}
	}

	public String getText() {
		return text;
	}

	public void addPass(PShader shader, PApplet p) {
		super.addPass(shader, p);
		invalidateText();
	}

	public void setAlign(int val) {
		this.alignH = val;
		invalidateText();
	}

	public void setAlign(int valH, int valV) {
		this.alignH = valH;
		this.alignV = valV;
		invalidateText();
	}

	public void setFont(String fontName, int fontSize) {
		font = FontsManager.get(fontName, fontSize);
		this.fontSize = fontSize;
		if (this.lineSpacing == -1) lineSpacing = fontSize;
		invalidateText();
	}

	public void setColor(int r, int g, int b) {
		setColor(Utils.color(r, g, b));
	}

	public void setColor(int color) {

		// int r = (color)&0xFF;
		// int g = (color>>8)&0xFF;
		// int b = (color>>16)&0xFF;
		// int a = (color>>24)&0xFF;
		// println(r + " / " + g + " / " + + b + " / " + a);

		this.color = color;
		bgColor = color;
		invalidateText();
	}

	public void draw(PGraphics dest) {
		super.draw(dest);
	}

	public void setX(float val) {
		if (val % 1 == 0) val += 0.1f;
		super.setX(val);
	}

	public void setY(float val) {
		if (val % 1 == 0) val += 0.1f;
		super.setY(val);
	}

	public float getTextWidth() {
		if (tw < 0) tw = graphic.textWidth(text);
		return tw;
	}

	public float getTextHeight() {
		if (th < 0) th = (graphic.textAscent() + graphic.textDescent() + (lineSpacing - (graphic.textAscent() + graphic.textDescent()))) * getLineCount();
		return th;
	}

	public float getLineCount() {
		if (linesCount < 0) {
			String[] words = PApplet.split(text, " ");
			// int breaklines = PApplet.split(text, "\n").length;
			linesCount = 1;
			float x = 0;
			for (int i = 0; i < words.length; i++) {
				float tw = graphic.textWidth(words[i] + " ");
				if (x + tw > width || words[i].contains("\n")) {
					x = tw;
					linesCount++;
				} else {
					x += tw;
				}
			}
		}
		return linesCount;
	}

	public void invalidateText() {
		textInvalid = true;
		tw = -1;
		th = -1;
		linesCount = -1;
	}

	public boolean isTextInvalid() {
		return textInvalid;
	}

}
