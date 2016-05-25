package com.gravisim3d.ui;

public class MenuItemCreator {

	/**
	 * Title
	 */
	private String title;

	/**
	 * Description
	 */
	private String description;

	/**
	 * Menu item
	 */
	private Button menu_item;

	/**
	 * Constructor
	 * 
	 * @param title
	 *            Title
	 * @param description
	 *            Description
	 */
	MenuItemCreator(String title, String description) {
		this.title = title;
		this.description = description;
	}

	/**
	 * Get title
	 * 
	 * @return Title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Create menu item
	 * 
	 * @param menu
	 *            Menu
	 * @return Menu item
	 */
	private Button createMenuItem(Menu menu) {
		Button ret = menu.createMenuItem("");
		ret.setHint(title + "\n\n" + description);
		return ret;
	}
}
