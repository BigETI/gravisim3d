package com.gravisim3d.ui;

import processing.core.PGraphics;
import processing.core.PImage;

public class ImageBox extends Panel {

	private PImage image;

	public ImageBox(String image_resource, double x, double y, double z, double size_x, double size_y, double size_z,
			EHorizontalAlignment horizontal_alignment, EVerticalAlignment vertical_alignment) {
		super(x, y, z, size_x, size_y, size_z, horizontal_alignment, vertical_alignment, 0xFF000000);
		image = GraviSim3D.getApplet().loadImage(image_resource);
	}

	@Override
	protected void draw(PGraphics graphics) {
		graphics.image(image, 0.0f, 0.0f, 1.0f, 1.0f);
		// System.out.println(getSize() + "; " + image.width + "; " +
		// image.height);
	}
}