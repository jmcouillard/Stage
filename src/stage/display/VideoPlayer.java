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

	private Object endCallbackTarget;
	private Method endCallbackMethod;

	private Object readyCallbackTarget;
	private Method readyCallbackMethod;

	private boolean looping;

	public VideoPlayer(PApplet p) {
		this.p = p;
		this.looping = false;
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
		endCallbackMethod = null;
		endCallbackTarget = null;
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
				readyEvent();
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

	//	public void loop() {
	//		mov.loop();
	//	}

	public void loop() {
		looping = true;
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

	public void setCallback(Object target, String callback) {
		endCallbackTarget = target;
		try {
			endCallbackMethod = target.getClass().getMethod(callback);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void setReadyCallback(Object target, String callback) {
		readyCallbackTarget = target;
		try {
			readyCallbackMethod = target.getClass().getMethod(callback);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void readyEvent() {
		if (readyCallbackMethod != null) {
			try {
				if (readyCallbackMethod.getParameterTypes().length == 0) readyCallbackMethod.invoke(readyCallbackTarget, new Object[] {});
				else readyCallbackMethod.invoke(endCallbackTarget, new Object[] { null });
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public void movieEOSEvent() {

		if (endCallbackMethod != null) {
			try {
				if (endCallbackMethod.getParameterTypes().length == 0) endCallbackMethod.invoke(endCallbackTarget, new Object[] {});
				else endCallbackMethod.invoke(endCallbackTarget, new Object[] { null });
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		if (looping) {
			restart();
		}
	}

}
