package com.gravisim3d.ui;

import processing.core.PGraphics;

/**
 * Drawable star class
 * 
 * @author Ethem Kurt
 *
 */
public class Star extends ADrawable {

	/**
	 * Constructor
	 * 
	 * @param x
	 *            X
	 * @param y
	 *            Y
	 * @param z
	 *            Z
	 * @param color
	 *            Color
	 */
	public Star(double x, double y, double z, int color) {
		super(x, y, z, 1.0, 1.0, 1.0, EHorizontalAlignment.LEFT, EVerticalAlignment.TOP, color);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.ui.ADrawable#draw(processing.core.PGraphics)
	 */
	@Override
	protected void draw(PGraphics graphics) {
		graphics.stroke(getColor());
		graphics.fill(getColor());
		graphics.point(0.0f, 0.0f);
	}
}