package stage.config;

import java.io.File;
import java.util.HashMap;

import processing.core.PApplet;
import processing.data.XML;

public class Config {

	private static XML xml;
	private static boolean loaded = false;

	static {
		loaded = false;
	}

	public static void init(PApplet p) {

		if (p.sketchPath == null) {
			try {
				p.sketchPath = System.getProperty("user.dir");
			} catch (Exception ex) {
				PApplet.println("Config error setting sketchPath : " + ex);
			}
		}

		try {
			Config.xml = p.loadXML("config.xml");
			Config.loaded = true;
		} catch (Exception ex) {
			PApplet.println("Config error : " + ex);
		}
	}

	public static String getString(String key) {
		return (Config.xml.getChild(key) != null) ? Config.xml.getChild(key).getContent() : key + " not found";
	}

	public static int getInt(String key) {
		return (Config.xml.getChild(key) != null) ? Integer.parseInt(Config.xml.getChild(key).getContent()) : -1;
	}

	public static float getFloat(String key) {
		return (Config.xml.getChild(key) != null) ? Float.parseFloat(Config.xml.getChild(key).getContent()) : -1;
	}

	public static boolean getBool(String key) {
		return (Config.xml.getChild(key).getContent().compareTo("true") == 0) ? true : false;
	}

	public static XML getXML(String key) {
		return (Config.xml.getChild(key));
	}

	public static void set(String key, float val) {
		if (Config.xml.getChild(key) == null) Config.xml.addChild(key);
		Config.xml.getChild(key).setContent(((Float) val).toString());
	}

	public static void set(String key, int val) {
		set(key, val, null);
	}

	public static void set(String key, int val, HashMap<String, Float> params) {
		if (Config.xml.getChild(key) == null) Config.xml.addChild(key);
		Config.xml.getChild(key).setContent(((Integer) val).toString());
		if (params != null) Config.setParams(key, params);
	}

	public static void setParams(String key, HashMap<String, Float> params) {
		if (Config.xml.getChild(key) == null) Config.xml.addChild(key);
		for (String paramName : params.keySet()) {
			Config.xml.getChild(key).setFloat(paramName, params.get(paramName));
		}
	}

	public static HashMap<String, String> getObject(String key) {
		HashMap<String, String> hm = new HashMap<String, String>();

		for (String attr : Config.xml.getChild(key).listAttributes()) {
			hm.put(attr, Config.xml.getChild(key).getString(attr));
		}
		return hm;
	}

	public static void save(PApplet p) {
		try {
			File file = p.dataFile("config.xml");
			Config.xml.save(file, "");
			// PApplet.println("Config saved to '" + file.getAbsolutePath() + "'.");
			// PrintWriter pw = new PrintWriter(new FileOutputStream("Log"));
			// pw.println("Config saved to '" + file.getAbsolutePath() + "'.");
			// pw.close();

		} catch (Exception ex) {
			PApplet.println("Config error : " + ex);
		}
	}

	public static boolean isLoaded() {
		return loaded;
	}

	public static String getPath(PApplet p) {
		try {
			File file = p.dataFile("config.xml");
			return file.getAbsolutePath();
		} catch (Exception ex) {
			PApplet.println("Config error : " + ex);
		}

		return "<not found>";
	}
}
