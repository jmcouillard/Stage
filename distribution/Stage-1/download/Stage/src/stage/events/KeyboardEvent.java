package stage.events;

public class KeyboardEvent extends Event {

	public static String KEY_DOWN = "keydown";
	private int keyCode;
	private char key;
	
	public KeyboardEvent(String type, int keyCode, char key) {
		super(type);
		this.keyCode = keyCode;
		this.key = key;
	}
	
	public int getKeyCode() {
		return keyCode;
	}
	
	public char getKey() {
		return key;
	}
	
	static {
		addEventClass(KEY_DOWN, KeyboardEvent.class);
	}
	
}
