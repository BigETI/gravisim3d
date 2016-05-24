package com.gravisim3d.ui;

import processing.core.PGraphics;

public class Star extends ADrawable {

	public Star(double x, double y, double z, int color) {
		super(x, y, z, 1.0, 1.0, 1.0, EHorizontalAlignment.LEFT, EVerticalAlignment.TOP, color);
	}

	@Override
	protected void draw(PGraphics graphics) {
		graphics.stroke(getColor());
		graphics.fill(getColor());
		graphics.point(0.0f, 0.0f);
	}
}