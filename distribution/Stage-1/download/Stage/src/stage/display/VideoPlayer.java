package stage.display;

import java.lang.reflect.Method;

import org.gstreamer.Bus;
import org.gstreamer.GstObject;

import stage.display.Bounds;
import stage.display.BoundsDisplayObject;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.video.Movie;
import stage.events.CustomEvent;

public class VideoPlayer extends BoundsDisplayObject {

	Movie mov;

	private PApplet p;
	private boolean firstFrame = true;
	private int blendMode = PGraphics.BLEND;

	private Object callbackTarget;
	private Method callbackMethod;
	private boolean customLoop;

	public VideoPlayer(PApplet p) {
		this.p = p;
		this.customLoop = false;
		if (mov != null) mov.dispose();
	}

	public VideoPlayer(PApplet p, String file) {
		this(p);
		loadFile(file);
	}

	public void loadFile(String file) {
		if (mov != null) {
			mov.stop();
			mov.dispose();
		}
		callbackMethod = null;
		callbackTarget = null;
		mov = new Movie(p, file);
	}

	public void draw(PGraphics dest) {
		
		dest.tint(255, getFinalAlpha() * 255f);
		dest.pushMatrix();
		dest.translate(x, y);
		dest.rotate(angle);
		dest.scale(scaleX, scaleY, scaleZ);
		dest.pushMatrix();
		dest.translate(-ax * scaleX, -ay * scaleY, -az * scaleZ);
		super.draw(dest);
		dest.blendMode(blendMode);
		dest.image(getImage(), 0, 0, width, height);
		dest.popMatrix();
		dest.popMatrix();
	}

	public PImage getImage() {

		if (mov == null) return null;

		if (mov.available() == true) {
			mov.read();

			if (firstFrame) {
				width = mov.width;
				height = mov.height;
				bounds = new Bounds(width, height);
				invalidate();
				firstFrame = false;
				dispatchEvent(new CustomEvent("firstFrame"));
			}
		}
		return mov;
	}

	public void setBlendMode(int val) {
		blendMode = val;
	}

	public void play() {
		mov.play();

		// Add end of stream callback
		Bus bus = mov.playbin.getBus();
		bus.connect(new Bus.EOS() {
			public void endOfStream(GstObject element) {
				movieEOSEvent();
			}
		});
	}

	public void loop() {
		mov.loop();
	}
	
	public void customLoop() {
		customLoop = true;
		play();
	}

	public void stop() {
		mov.stop();
	}

	public void pause() {
		mov.pause();
	}
	
	public void restart() {
		mov.stop();
		mov.jump(0);
		mov.play();
	}

	public void setCallback(Object target, Method callback) {
		callbackTarget = target;
		callbackMethod = callback;
	}
	
	public void setCallback(Object target, String callback) {
		callbackTarget = target;
		try {
			callbackMethod = target.getClass().getMethod(callback);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void movieEOSEvent() {

		// Get target
		// Class<?> c = callbackTarget.getClass();

		if (callbackMethod != null) {
			try {
				if (callbackMethod.getParameterTypes().length == 0) callbackMethod.invoke(callbackTarget, new Object[] {});
				else callbackMethod.invoke(callbackTarget, new Object[] { null });
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		if (customLoop) {
			println("custom loop eos");
			restart();
		}
	}

}
