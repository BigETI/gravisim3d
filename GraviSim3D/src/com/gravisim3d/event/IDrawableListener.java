package com.gravisim3d.event;

import processing.core.PGraphics;

/**
 * Drawable listener interface
 * 
 * @author Ethem Kurt
 *
 */
public interface IDrawableListener extends IListener {

	/**
	 * Draw component event
	 * 
	 * @param graphics
	 *            Graphics
	 */
	public void onDrawComponent(PGraphics graphics);

	/**
	 * Draw siblings event
	 * 
	 * @param graphics
	 *            Graphics
	 */
	public void onDrawSiblings(PGraphics graphics);
}