package com.gravisim3d.ui;

import com.gravisim3d.event.IClickListener;
import com.gravisim3d.event.IHoverListener;

/**
 * GraviSim3D mode enumerator
 * 
 * @author Ethem Kurt
 *
 */
public enum EGraviSim3DMode implements IMenuItem {

	/**
	 * Show lines
	 */
	SHOW_EFFECTING_LINES("Toggle effecting lines", "Toggle the effecting lines\nfor all the planets\nin this simulation."),

	/**
	 * Show prediction
	 */
	SHOW_PREDICTION("Toggle prediction", "Show prediction of\nall the planets\nin this simulation."),

	/**
	 * Show extended planetary information
	 */
	SHOW_EXTENDED_PLANETARY_INFORMATION("Toggle extended planetary information", "Show all the planetary information\nin this simulation.");

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
	EGraviSim3DMode(String title, String description) {
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