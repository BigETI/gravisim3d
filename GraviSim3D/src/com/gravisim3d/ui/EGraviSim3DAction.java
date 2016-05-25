package com.gravisim3d.ui;

/**
 * GraviSim3D action enumerator
 * 
 * @author Ethem Kurt
 *
 */
public enum EGraviSim3DAction {

	/**
	 * Create planet
	 */
	CREATE_PLANET("Insert planet", "Hold the right mouse button\nto create a planet.\n\nRelease the mouse to\nfire the planet.\nChanging the mouse position\nchanges the velocity vector."),

	/**
	 * Select planet
	 */
	SELECT_PLANET("Select planet", "Select a planet to change\nits attributes."),

	/**
	 * Rotate camera
	 */
	ROTATE_CAMERA("Rotate camera", "Use your mouse to rotate\nyour camera."),

	/**
	 * Translate camera
	 */
	TRANSLATE_CAMERA("Translate camera", "Use your mouse to translate\nyour camera.");

	/**
	 * Title
	 */
	private String title;

	/**
	 * Description
	 */
	private String description;

	/**
	 * Constructor
	 * 
	 * @param title
	 *            Title
	 * @param description
	 *            Description
	 */
	EGraviSim3DAction(String title, String description) {
		this.title = title;
		this.description = description;
	}

	/**
	 * Create menu item
	 * 
	 * @param menu
	 *            Menu
	 * @return Menu item
	 */
	public Button createMenuItem(Menu menu) {
		Button ret = menu.createMenuItem("");
		ret.setHint(title + "\n\n" + description);
		return ret;
	}
}