package com.gravisim3d.ui;

public enum EGraviSim3DAction {

	CREATE_PLANET("Insert planet",
			"Hold the right mouse button\nto create a planet.\n\nRelease the mouse to\nfire the planet.\nChanging the mouse position\nchanges the velocity vector."),

	SELECT_PLANET("Select planet", "Select a planet to change\nits attributes."),

	ROTATE_CAMERA("Rotate camera", "Use your mouse to rotate\nyour camera."),

	TRANSLATE_CAMERA("Translate camera", "Use your mouse to translate\nyour camera.");

	private String title;

	private String description;

	EGraviSim3DAction(String title, String description) {
		this.title = title;
		this.description = description;
	}

	public Button createMenuItem(Menu menu) {
		Button ret = menu.createMenuItem("");
		ret.setHint(title + "\n\n" + description);
		return ret;
	}
}