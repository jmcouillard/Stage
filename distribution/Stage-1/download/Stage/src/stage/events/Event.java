package stage.events;

import java.util.HashMap;

public class Event {

	/** Map of registered methods, stored by name. */
	public static HashMap<String, Class> registerEventClass = new HashMap<String, Class>();

	private String type = "";
	private String action = "";
	private boolean grabbed = false;
	
	private Object target;
	
	public int screenX = 0;
	public int screenY = 0;
	
	public Event(String type) {
		this.type = type;
		this.grabbed = false;
	}

	public String getType() {
		return type;
	}

	public String getAction() {
		return action;
	}
	
	public boolean grabbed() {
		return grabbed;
	}

	public void grab() {
		grabbed = true;
	}
	
	public void setTarget(Object val) {
		target = val;
	}
	
	public Object getTarget() {
		return target;
	}

	public static void addEventClass(String listener, Class c) {		
		if(!registerEventClass.containsKey(listener)) registerEventClass.put(listener, c);
	}

	public static Class getEventClass(String listener) {
		return registerEventClass.containsKey(listener) ? registerEventClass.get(listener) : Event.class;
	}

}
