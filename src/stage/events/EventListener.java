package stage.events;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import stage.display.Utils;

public class EventListener {

	private HashMap<String, ArrayList<EventListenerEntry>> entries = new HashMap<String, ArrayList<EventListenerEntry>>();

	// private Method acceptEventMethod;

	public EventListener() {
		//
		// try {
		// acceptEventMethod = getClass().getMethod("acceptEvent", Event.class);
		// } catch (NoSuchMethodException nsme) {
		// Utils.error("There is no public acceptEvent() method in the class " +
		// getClass().getName());
		//
		// }
	}

	public void addEventListener(String listener, Object target, String callback) {

		// Get target
		Class<?> c = target.getClass();

		try {
			// Add to listeners
			Method method = c.getMethod(callback, Event.getEventClass(listener));

			// Init if key is empty
			if (!entries.containsKey(listener)) {
				entries.put(listener, new ArrayList<EventListenerEntry>());
			}

			// Add new listener
			entries.get(listener).add(new EventListenerEntry(target, method));

			// PApplet.println("listener " + listener + "  /method" + method +
			// "      / taget" + target);

		} catch (NoSuchMethodException nsme) {
			Utils.error("There is no public " + callback + "() method in the class " + target.getClass().getName() + " with argument " + Event.getEventClass(listener));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Add custom event listener
	public void addCustomEventListener(String listener, Object target, String callback) {
		Event.addEventClass(listener, CustomEvent.class);
		addEventListener(listener, target, callback);
	}

	// This methods allows classes to unregister for MyEvents
	public void removeEventListener(String listener, Object target) {
		if (!entries.containsKey(listener)) {
			for (EventListenerEntry entry : entries.get(listener)) {
				if (entry.target == target) {
					entries.get(listener).remove(entry);
				}
			}
		}
	}

	// Trigger callback
	public void dispatchEvent(Event e) {
		
		e.setTarget(this);

		if (entries.containsKey(e.getType())) {

			for (EventListenerEntry entry : entries.get(e.getType())) {

				// Callback only if event position is over object OR object has
				// no bounds
				// if (!(entry.target instanceof BoundsDisplayObject) ||
				// ((BoundsDisplayObject)
				// entry.target).getBounds().isOver(e.screenX, e.screenY)) {

				try {
					// Callback appends here
					entry.callback.invoke(entry.target, new Object[] { e });

				} catch (Exception ex) {

					// Check for wrapped exception
					Utils.error("Error handling callback " + entry.callback + " + () for " + entry.target + " : ");
					ex.printStackTrace();
				}

				// }

				// if (e.grabbed())
				// break;
			}
		}
	}

	class EventListenerEntry {

		public Object target;
		public Method callback;

		public EventListenerEntry(Object target, Method callback) {
			this.target = target;
			this.callback = callback;
		}

	}

}
