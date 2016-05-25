package com.gravisim3d.ui;

import processing.core.PApplet;
import processing.core.PGraphics;

public class UI extends ADrawable {

	private PGraphics graphics;

	public UI(double x, double y) {
		super(0.0, 0.0, 0.0, x, y, 1.0, EHorizontalAlignment.LEFT, EVerticalAlignment.TOP, 0xFF000000);
		allowDepthBuffer(true);
		graphics = GraviSim3D.getApplet().createGraphics((int) x, (int) y, PApplet.P3D);
	}

	@Override
	protected void draw(PGraphics graphics) {
		//
	}

	public PGraphics getGraphics() {
		return graphics;
	}

	public void drawMain() {
		processInput(GraviSim3D.getApplet().mouseX, GraviSim3D.getApplet().mouseY, GraviSim3D.getApplet().mousePressed, getSize());
		graphics.beginDraw();
		// temporary_graphics.beginDraw();
		graphics.background(0x00FFFFFF);
		// temporary_graphics.background(0x00FFFFFF);
		drawComponent(graphics, getSize());
		graphics.endDraw();
		// temporary_graphics.endDraw();
		GraviSim3D.getApplet().image(graphics, 0, 0);
	}
}