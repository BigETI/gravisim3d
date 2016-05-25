package com.gravisim3d.core;

import java.util.HashMap;

import com.gravisim3d.ui.GraviSim3D;

import processing.core.PFont;

public class FontLoader {

	private static HashMap<String, PFont> fonts = new HashMap<>();

	public static PFont get(String font_name) {
		PFont ret = null;
		if (fonts.containsKey(font_name))
			ret = fonts.get(font_name);
		else {
			ret = GraviSim3D.getApplet().createFont(font_name + ".ttf", 120);
			fonts.put(font_name, ret);
		}
		return ret;
	}
}
