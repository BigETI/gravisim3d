package com.gravisim3d.ui;

import com.gravisim3d.core.PVectorD;
import com.gravisim3d.event.ClickEventArgs;
import com.gravisim3d.event.ClickNotifier;

import processing.core.PGraphics;

/**
 * Drawable button class
 * 
 * @author Ethem Kurt
 *
 */
public class Button extends Panel {

	/**
	 * Hover color
	 */
	private int hover_color;

	/**
	 * Click color
	 */
	private int click_color;

	/**
	 * Glow color
	 */
	private int glow_color;

	/**
	 * Glowing
	 */
	private boolean glowing = false;

	/**
	 * Pressing
	 */
	private boolean pressing = false;

	/**
	 * Caption
	 */
	private Text caption;

	/**
	 * Click notifier
	 */
	private ClickNotifier click_notifier = new ClickNotifier();

	/**
	 * Constructor
	 * 
	 * @param caption
	 *            Caption
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
	 * @param hover_color
	 *            Hover color
	 * @param click_color
	 *            Click color
	 * @param glow_color
	 *            Glow color
	 */
	public Button(String caption, double x, double y, double z, double size_x, double size_y, double size_z,
			EHorizontalAlignment horizontal_alignment, EVerticalAlignment vertical_alignment, int color,
			int hover_color, int click_color, int glow_color) {
		super(x, y, z, size_x, size_y, size_z, horizontal_alignment, vertical_alignment, color);
		this.hover_color = hover_color;
		this.click_color = click_color;
		this.glow_color = glow_color;
		this.caption = new Text(caption, 15.0, 0.0, 0.1, 20.0, EHorizontalAlignment.LEFT, EVerticalAlignment.CENTER,
				0xFFFFFFFF);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.ui.ADrawable#processInput(double, double, boolean,
	 * com.gravisim3d.core.PVectorD)
	 */
	@Override
	public void processInput(double pos_x, double pos_y, boolean pressed, PVectorD constraint) {
		super.processInput(pos_x, pos_y, pressed, constraint);
		switch (getHorizontalAlignment()) {
		case LEFT:
			pos_x -= getPos().x;
			break;
		case RIGHT:
			pos_x -= (constraint.x - getSize().x) - getPos().x;
			break;
		case CENTER:
			pos_x -= ((constraint.x - getSize().x) / 2) + getPos().x;
			break;
		}
		switch (getVerticalAlignment()) {
		case TOP:
			pos_y -= getPos().y;
			break;
		case BOTTOM:
			pos_y -= (constraint.y - getSize().y) - getPos().y;
			break;
		case CENTER:
			pos_y -= ((constraint.y - getSize().y) / 2) + getPos().y;
			break;
		}
		if (inBox(pos_x, pos_y)) {
			if (pressed && (!pressing)) {
				pressing = true;
				click_notifier.setOnPress(new ClickEventArgs(pos_x, pos_y));
			} else if ((!pressed) && pressing) {
				pressing = false;
				click_notifier.setOnRelease(new ClickEventArgs(pos_x, pos_y));
				click_notifier.setOnClick(new ClickEventArgs(pos_x, pos_y));
			}
		} else {
			if (pressing)
				pressing = false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.ui.ADrawable#drawSiblings(processing.core.PGraphics)
	 */
	@Override
	protected void drawSiblings(PGraphics graphics) {
		super.drawSiblings(graphics);
		caption.drawComponent(graphics, getSize());
	}

	/**
	 * Get click notifier
	 * 
	 * @return Click notifier
	 */
	public ClickNotifier getClickNotifier() {
		return click_notifier;
	}

	/**
	 * Get caption
	 * 
	 * @return Caption
	 */
	public Text getCaption() {
		return caption;
	}

	/**
	 * Set glowing
	 * 
	 * @param glowing
	 *            Glowing
	 */
	public void setGlowing(boolean glowing) {
		this.glowing = glowing;
	}

	/**
	 * Is glowing
	 * 
	 * @return Glowing
	 */
	public boolean isGlowing() {
		return glowing;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.ui.Panel#draw(processing.core.PGraphics)
	 */
	@Override
	protected void draw(PGraphics graphics) {
		if (pressing)
			graphics.fill(click_color);
		else if (isHovering())
			graphics.fill(hover_color);
		else if (glowing)
			graphics.fill(glow_color);
		super.draw(graphics);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.ui.ADrawable#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
		click_notifier.removeAllListeners();
		caption.dispose();
		caption = null;
	}
}