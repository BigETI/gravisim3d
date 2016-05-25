package com.gravisim3d.event;

import com.gravisim3d.core.PVectorD;

/**
 * Hover event arguments class
 * 
 * @author Ethem Kurt
 *
 */
public class HoverEventArgs {

	/**
	 * Hover position
	 */
	private PVectorD pos;

	/**
	 * Constructor
	 * 
	 * @param x
	 *            Hover position X
	 * @param y
	 *            Hover position Y
	 */
	public HoverEventArgs(double x, double y) {
		pos = new PVectorD(x, y);
	}

	/**
	 * Get hover position
	 * 
	 * @return Hover position
	 */
	public PVectorD getPos() {
		return pos;
	}
}