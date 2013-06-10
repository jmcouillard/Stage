
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import processing.core.PApplet;
import processing.opengl.PShader;
import processing.video.Movie;

public class Stage extends PApplet implements WindowFocusListener {

	private static final long serialVersionUID = 1;
	private static final String outputRoot = "/Volumes/Trou Noir/output/";

//	public static final int WIDTH = 1920;
//	public static final int HEIGHT = 1080;

	 public static final int WIDTH = 960/2;
	 public static final int HEIGHT = 540/2;

	public static int screenWidth = WIDTH;
	public static int screenHeight = HEIGHT;

//	public Stage stage;
//	public GLSLSprite sprite;

	public String folder = "";
	public boolean saving = false;
	public boolean drawing = true;

	private PShader shader;

	private String[] shaderFiles;
	private int currentShader;
	

	public void setup() {

		// Setup
		size(screenWidth, screenHeight, OPENGL);
		hint(ENABLE_OPENGL_ERRORS);
		hint(DISABLE_TEXTURE_MIPMAPS);

		// Focus listener
		frame.addWindowFocusListener(this);
	}

	public void draw() {
		
		background(random(0, 255));

	}

	public void windowGainedFocus(WindowEvent e) {
		// println("WindowFocusListener method called: windowGainedFocus.");
		drawing = true;
	}

	public void windowLostFocus(WindowEvent e) {
		// println("WindowFocusListener method called: windowLostFocus.");
		drawing = false;
	}

	public void mouseMoved() {
		super.mouseMoved();
	}

	public void keyPressed() {
		super.keyPressed();
	}

	public void mousePressed() {
		super.mousePressed();
	}

	public void movieEvent(Movie m) {
		m.read();
	}

	public static void main(String[] args) {
		PApplet.main(new String[] { "--bgcolor=#000000", Stage.class.getName() });
		
	}

}
