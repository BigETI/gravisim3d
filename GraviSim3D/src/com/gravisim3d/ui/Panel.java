package com.gravisim3d.ui;

import processing.core.PGraphics;

/**
 * Drawable panel class
 * 
 * @author Ethem Kurt
 *
 */
public class Panel extends ADrawable {

	/**
	 * COnstructor
	 * 
	 * @param x
	 *            X
	 * @param y
	 *            Y
	 * @param z
	 *            Z
	 * @param size_x
	 *            Size X
	 * @param size_y
	 *            Size Y
	 * @param size_z
	 *            Size Z
	 * @param horizontal_alignment
	 *            Horizontal alignment
	 * @param vertical_alignment
	 *            Vertical alignment
	 * @param color
	 *            Color
	 */
	public Panel(double x, double y, double z, double size_x, double size_y, double size_z,
			EHorizontalAlignment horizontal_alignment, EVerticalAlignment vertical_alignment, int color) {
		super(x, y, z, size_x, size_y, size_z, horizontal_alignment, vertical_alignment, color);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.ui.ADrawable#draw(processing.core.PGraphics)
	 */
	@Override
	protected void draw(PGraphics graphics) {
		graphics.rect(0.0f, 0.0f, 1.0f, 1.0f);
	}
}