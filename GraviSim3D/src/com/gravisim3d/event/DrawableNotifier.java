package com.gravisim3d.event;

import processing.core.PGraphics;

/**
 * Drawable notifier class
 * 
 * @author Ethem Kurt
 *
 */
public class DrawableNotifier extends Notifier<IDrawableListener> {

	/**
	 * Notify "onDrawComponent" event
	 * 
	 * @param graphics
	 *            Graphics
	 */
	public void setOnDrawComponent(PGraphics graphics) {
		for (IDrawableListener i : getListeners()) {
			i.onDrawComponent(graphics);
		}
	}

	/**
	 * Notify "onDrawSiblings" event
	 * 
	 * @param graphics
	 *            Graphics
	 */
	public void setOnDrawSiblings(PGraphics graphics) {
		for (IDrawableListener i : getListeners()) {
			i.onDrawSiblings(graphics);
		}
	}
}