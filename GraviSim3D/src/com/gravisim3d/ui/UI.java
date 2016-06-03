package com.gravisim3d.ui;

import processing.core.PApplet;
import processing.core.PGraphics;

/**
 * Drawable UI class
 * 
 * @author Ethem Kurt
 *
 */
public class UI extends ADrawable {

	/**
	 * Graphics
	 */
	private PGraphics graphics;

	/**
	 * Constructor
	 * 
	 * @param size_x
	 *            Size X
	 * @param size_y
	 *            Size Y
	 */
	public UI(double size_x, double size_y) {
		super(0.0, 0.0, 0.0, size_x, size_y, 1.0, EHorizontalAlignment.LEFT, EVerticalAlignment.TOP, 0xFF000000);
		graphics = GraviSim3D.getApplet().createGraphics((int) size_x, (int) size_y, PApplet.P3D);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.ui.ADrawable#draw(processing.core.PGraphics)
	 */
	@Override
	protected void draw(PGraphics graphics) {
		//
	}

	/**
	 * Get graphics
	 * 
	 * @return Graphics
	 */
	public PGraphics getGraphics() {
		return graphics;
	}

	/**
	 * Main draw method
	 */
	public void drawMain() {
		processInput(GraviSim3D.getApplet().mouseX, GraviSim3D.getApplet().mouseY, GraviSim3D.getApplet().mousePressed,
				getSize());
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