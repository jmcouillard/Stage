package stage.events;

public class CustomEvent extends Event {
	
	public Object args[];

	public CustomEvent(String type, Object arg) {
		this(type, new Object[]{arg});
	}

	public CustomEvent(String type) {
		this(type, new Object[]{});
	}
	
	public CustomEvent(String type, Object args[]) {
		super(type);
		this.args = args;
	}

	public Object getArgument(int id) {
		return args[id];
	}
	
	public Object getArgument() {
		return args[0];
	}
	
	
}
