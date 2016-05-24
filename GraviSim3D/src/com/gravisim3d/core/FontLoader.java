package com.gravisim3d.core;

import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PFont;

public class FontLoader {

	public static PApplet applet = null;

	private static HashMap<String, PFont> fonts = new HashMap<>();

	public static void init(PApplet applet) {
		FontLoader.applet = applet;
	}

	public static PFont get(String font_name) {
		PFont ret = null;
		if (fonts.containsKey(font_name))
			ret = fonts.get(font_name);
		else {
			ret = applet.createFont(font_name + ".ttf", 120);
			fonts.put(font_name, ret);
		}
		return ret;
	}
}
