package stage.display;

import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PFont;

public class FontsManager {

	private static HashMap<String, PFont> fontsLibrary;
	private static PApplet p;
	private static char[] mCharset;
	
	public FontsManager() {
	}

	public static void init(PApplet p){
		FontsManager.fontsLibrary = new HashMap<String, PFont>();
		FontsManager.p = p;
		createCharset();
	}
	
	public static PFont get(String fontName, int fontSize) {
		
		String key = fontName.toLowerCase()+Integer.toString(fontSize);
		
		if(FontsManager.fontsLibrary.containsKey(key)) {
			return FontsManager.fontsLibrary.get(key);				
		} else {
			PFont font = p.createFont(fontName, fontSize, true, mCharset);
			FontsManager.fontsLibrary.put(key, font);
			return font;
		}
	}

	private static void createCharset() {

		int count = 0;
		int index = 0;
		char specials[] = new char[] { 'é', 'É', 'è', 'È', 'à', 'À', 'Ù', 'ù', 'Ê', 'ê', 'â', 'Â', '₂', '₄', 'º', '«', '»', 'ï', 'Ï', '’' };

		count += (0x007F - 0x0000 + 1);
		count += specials.length;

		FontsManager.mCharset = new char[count];

		// loading basic Latin characters
		for (int code = 0x0000; code <= 0x007F; code++) {
			FontsManager.mCharset[index] = Character.toChars(code)[0];
			index++;
		}

		// Load specials
		for (int code = 0; code < specials.length; code++) {
			FontsManager.mCharset[index] = specials[code];
			index++;
		}
	}
}
