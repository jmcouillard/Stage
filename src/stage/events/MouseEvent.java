package stage.events;

public class MouseEvent extends Event {

	public static String MOUSE_MOVE = "mousemouve";
	public static String MOUSE_CLICK = "mouseclick";
	public static String MOUSE_DOWN = "mousedown";
	
	public MouseEvent(String type, int mouseX, int mouseY) {
		super(type);
		this.screenX = mouseX;
		this.screenY = mouseY;
	}
	
	static {
		addEventClass(MOUSE_MOVE, MouseEvent.class);
		addEventClass(MOUSE_CLICK, MouseEvent.class);
		addEventClass(MOUSE_DOWN, MouseEvent.class);
	}
	
}
