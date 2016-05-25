package com.gravisim3d.ui;

import processing.core.PGraphics;
import processing.core.PImage;

/**
 * Drawable image box class
 * 
 * @author Ethem Kurt
 *
 */
public class ImageBox extends Panel {

	/**
	 * Image
	 */
	private PImage image;

	/**
	 * COnstructor
	 * 
	 * @param image_resource
	 *            Image resource
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
	 */
	public ImageBox(String image_resource, double x, double y, double z, double size_x, double size_y, double size_z,
			EHorizontalAlignment horizontal_alignment, EVerticalAlignment vertical_alignment) {
		super(x, y, z, size_x, size_y, size_z, horizontal_alignment, vertical_alignment, 0xFF000000);
		image = GraviSim3D.getApplet().loadImage(image_resource);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.ui.Panel#draw(processing.core.PGraphics)
	 */
	@Override
	protected void draw(PGraphics graphics) {
		graphics.image(image, 0.0f, 0.0f, 1.0f, 1.0f);
		// System.out.println(getSize() + "; " + image.width + "; " +
		// image.height);
	}
}