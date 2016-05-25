package com.gravisim3d.event;

import com.gravisim3d.core.PVectorD;

/**
 * Click event arguments class
 * 
 * @author Ethem Kurt
 *
 */
public class ClickEventArgs {

	/**
	 * Click position
	 */
	private PVectorD pos;

	/**
	 * Constructor
	 * 
	 * @param pos_x
	 *            Click position X
	 * @param pos_y
	 *            Click position y
	 */
	public ClickEventArgs(double pos_x, double pos_y) {
		pos = new PVectorD(pos_x, pos_y);
	}

	/**
	 * Get click position
	 * 
	 * @return CLick position
	 */
	public PVectorD getPos() {
		return pos;
	}
}