package com.gravisim3d.ui;

import processing.core.PGraphics;

public class Panel extends ADrawable {

	public Panel(double x, double y, double z, double size_x, double size_y, double size_z,
			EHorizontalAlignment horizontal_alignment, EVerticalAlignment vertical_alignment, int color) {
		super(x, y, z, size_x, size_y, size_z, horizontal_alignment, vertical_alignment, color);
	}

	@Override
	protected void draw(PGraphics graphics) {
		graphics.rect(0.0f, 0.0f, 1.0f, 1.0f);
	}
}