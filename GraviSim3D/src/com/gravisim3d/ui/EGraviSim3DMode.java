package com.gravisim3d.ui;

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
}