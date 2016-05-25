package com.gravisim3d.ui;

import processing.core.PGraphics;

/**
 * Drawable hint class
 * 
 * @author Ethem Kurt
 *
 */
public class Hint extends Panel {

	/**
	 * Hint text
	 */
	private Text hint_text;

	/**
	 * Padding
	 */
	private double padding = 5.0;

	/**
	 * Constructor
	 * 
	 * @param hint_text
	 *            Hint text
	 * @param size_x
	 *            Size X
	 * @param size_y
	 *            Size Y
	 * @param size_z
	 *            Size Z
	 * @param color
	 *            COlor
	 */
	public Hint(String hint_text, double size_x, double size_y, double size_z, int color) {
		super(0.0, 0.0, 0.0, size_x, size_y, size_z, EHorizontalAlignment.LEFT, EVerticalAlignment.TOP, color);
		this.hint_text = new Text(hint_text, 0.0, 0.0, 0.0, 12.0, EHorizontalAlignment.LEFT, EVerticalAlignment.TOP,
				0xFFFFFFFF);
	}

	/**
	 * Get hint text
	 * 
	 * @return Hint text
	 */
	public Text getHintText() {
		return hint_text;
	}

	/**
	 * Set padding
	 * 
	 * @param padding
	 *            Padding
	 */
	public void setPadding(double padding) {
		this.padding = padding;
		hint_text.setPos(padding, padding, hint_text.getPos().z);
		setSize(hint_text.getSize().x + (padding * 2.0), hint_text.getSize().y + (padding * 2.0), getSize().z);
	}

	/**
	 * Get padding
	 * 
	 * @return Padding
	 */
	public double getPadding() {
		return padding;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.ui.ADrawable#drawSiblings(processing.core.PGraphics)
	 */
	@Override
	protected void drawSiblings(PGraphics graphics) {
		super.drawSiblings(graphics);
		hint_text.drawComponent(graphics, getSize());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gravisim3d.ui.ADrawable#setHorizontalAlignment(com.gravisim3d.ui.
	 * EHorizontalAlignment)
	 */
	@Override
	public void setHorizontalAlignment(EHorizontalAlignment horizontal_alignment) {
		super.setHorizontalAlignment(horizontal_alignment);
		if (hint_text != null)
			hint_text.setHorizontalAlignment(horizontal_alignment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.ui.ADrawable#setVerticalAlignment(com.gravisim3d.ui.
	 * EVerticalAlignment)
	 */
	@Override
	public void setVerticalAlignment(EVerticalAlignment vertical_alignment) {
		super.setVerticalAlignment(vertical_alignment);
		if (hint_text != null)
			hint_text.setVerticalAlignment(vertical_alignment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.ui.ADrawable#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
		hint_text.dispose();
		hint_text = null;
	}
}