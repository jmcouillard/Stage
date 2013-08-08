import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import processing.core.PApplet;
import processing.video.Movie;
import stage.display.Stage;

public class StageTest extends PApplet implements WindowFocusListener {

	private static final long serialVersionUID = 1;

	public static final int WIDTH = 960;
	public static final int HEIGHT = 540;

	public static int screenWidth = WIDTH;
	public static int screenHeight = HEIGHT;

	public Stage stage;

	public void setup() {

		// Setup
		size(screenWidth, screenHeight, OPENGL);
		hint(ENABLE_OPENGL_ERRORS);
		hint(DISABLE_TEXTURE_MIPMAPS);

		// Focus listener
		frame.addWindowFocusListener(this);
		
		// New stage
		stage = new Stage(this,width, height);
	}

	public void draw() {
		background(random(0, 255));
		stage.update();
		stage.draw(this);
	}

	public void windowGainedFocus(WindowEvent e) {
		println("WindowFocusListener method called: windowGainedFocus.");
	}

	public void windowLostFocus(WindowEvent e) {
		println("WindowFocusListener method called: windowLostFocus.");
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
		PApplet.main(new String[] { "--bgcolor=#000000", StageTest.class.getName() });

	}

}
