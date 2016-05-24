package com.gravisim3d.ui;

import com.gravisim3d.core.PVectorD;
import com.gravisim3d.event.ClickEventArgs;
import com.gravisim3d.event.ClickNotifier;

import processing.core.PGraphics;

public class Button extends Panel {

	private int hover_color;

	private int click_color;

	private int glow_color;

	private boolean glowing = false;

	private boolean pressing = false;

	private Text caption;

	private ClickNotifier click_notifier = new ClickNotifier();

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

	@Override
	protected void drawSiblings(PGraphics graphics) {
		super.drawSiblings(graphics);
		caption.drawComponent(graphics, getSize());
	}

	public ClickNotifier getClickNotifier() {
		return click_notifier;
	}

	public Text getCaption() {
		return caption;
	}

	public void setGlowing(boolean glowing) {
		this.glowing = glowing;
	}

	public boolean getGlowing() {
		return glowing;
	}

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

	@Override
	public void dispose() {
		super.dispose();
		click_notifier.removeAllListeners();
		caption.dispose();
		caption = null;
	}
}