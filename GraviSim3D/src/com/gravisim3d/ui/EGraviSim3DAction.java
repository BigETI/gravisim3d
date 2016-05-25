package com.gravisim3d.ui;

import com.gravisim3d.event.IClickListener;
import com.gravisim3d.event.IHoverListener;

/**
 * GraviSim3D action enumerator
 * 
 * @author Ethem Kurt
 *
 */
public enum EGraviSim3DAction implements IMenuItem {

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
	 * Click listener
	 */
	private IClickListener click_listener = null;

	/**
	 * Hover listener
	 */
	private IHoverListener hover_listener = null;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.ui.IMenuItem#getTitle()
	 */
	@Override
	public String getTitle() {
		return title;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.ui.IMenuItem#getDescription()
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.ui.IMenuItem#setClickListener(com.gravisim3d.event.
	 * IClickListener)
	 */
	@Override
	public void setClickListener(IClickListener click_listener) {
		this.click_listener = click_listener;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.ui.IMenuItem#getClickListener()
	 */
	@Override
	public IClickListener getClickListener() {
		return click_listener;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.ui.IMenuItem#setHoverListener(com.gravisim3d.event.
	 * IHoverListener)
	 */
	@Override
	public void setHoverListener(IHoverListener hover_listener) {
		this.hover_listener = hover_listener;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.ui.IMenuItem#getHoverListener()
	 */
	@Override
	public IHoverListener getHoverListener() {
		return hover_listener;
	}
}