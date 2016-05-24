package com.gravisim3d.ui;

import java.util.ArrayList;
import java.util.List;

import com.gravisim3d.core.PVectorD;

import processing.core.PGraphics;

public class Menu extends Panel {

	private ArrayList<Button> buttons = new ArrayList<Button>();

	private int button_color;
	private int button_hover_color;
	private int button_click_color;
	private int button_glow_color;

	private double button_size;

	private double menu_size;

	private double padding = 5.0;

	private EMenuAlignment menu_alignment;

	public Menu(double x, double y, double z, double menu_size, double button_size,
			EHorizontalAlignment horizontal_alignment, EVerticalAlignment vertical_alignment, int color,
			int button_color, int button_hover_color, int button_click_color, int button_glow_color,
			EMenuAlignment menu_alignment) {
		super(x, y, z, 0.0, 0.0, 1.0, horizontal_alignment, vertical_alignment, color);
		this.menu_size = menu_size;
		this.button_size = button_size;
		this.button_color = button_color;
		this.button_hover_color = button_hover_color;
		this.button_click_color = button_click_color;
		this.button_glow_color = button_glow_color;
		this.menu_alignment = menu_alignment;
		arrangeButtons();
	}

	private void fixMenuSize() {
		if (menu_alignment != null) {
			switch (menu_alignment) {
			case HORIZONTAL:
				super.setSize((buttons == null) ? 0.0 : ((float) buttons.size() * (button_size + padding)) + padding,
						menu_size, 1.0);
				break;
			case VERTICAL:
				super.setSize(menu_size,
						(buttons == null) ? 0.0 : ((float) buttons.size() * (button_size + padding)) + padding, 1.0);
				break;
			}
		}
	}

	private void arrangeButtons() {
		double offset;
		fixMenuSize();
		if (buttons != null) {
			for (Button i : buttons) {
				i.setHorizontalAlignment((getHorizontalAlignment() == EHorizontalAlignment.CENTER)
						? EHorizontalAlignment.LEFT : getHorizontalAlignment());
				i.setVerticalAlignment((getVerticalAlignment() == EVerticalAlignment.CENTER) ? EVerticalAlignment.TOP
						: getVerticalAlignment());
			}
			switch (menu_alignment) {
			case HORIZONTAL:
				offset = padding;
				for (Button i : buttons) {
					i.setPos(offset, padding, 1.0);
					i.setSize(button_size, getSize().y - (padding * 2.0), 1.0);
					offset += button_size + padding;
				}
				break;
			case VERTICAL:
				offset = padding;
				for (Button i : buttons) {
					i.setPos(padding, offset, 1.0);
					i.setSize(getSize().x - (padding * 2.0), button_size, 1.0);
					offset += button_size + padding;
				}
				break;
			}
		}
	}

	@Override
	public void processInput(double pos_x, double pos_y, boolean pressed, PVectorD constraint) {
		super.processInput(pos_x, pos_y, pressed, constraint);
		if (isVisible()) {
			switch (getHorizontalAlignment()) {
			case LEFT:
				pos_x -= getPos().x;
				break;
			case RIGHT:
				pos_x -= (constraint.x - getSize().x) - getPos().x;
				break;
			case CENTER:
				pos_x -= ((constraint.x - getSize().x) * 0.5) + getPos().x;
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
				pos_y -= ((constraint.y - getSize().y) * 0.5) + getPos().y;
				break;
			}
			for (Button i : buttons)
				i.processInput(pos_x, pos_y, pressed, getSize());
		}
	}

	@Override
	protected void drawSiblings(PGraphics graphics) {
		super.drawSiblings(graphics);
		if (isVisible()) {
			for (Button i : buttons)
				i.drawComponent(graphics, getSize());
		}
	}

	@Override
	public void setSize(double size_x, double size_y, double size_z) {
		super.setSize(0.0, 0.0, 1.0);
		arrangeButtons();
	}

	@Override
	public void setPos(double x, double y, double z) {
		super.setPos(x, y, z);
		arrangeButtons();
	}

	@Override
	public void setHorizontalAlignment(EHorizontalAlignment horizontal_alignment) {
		super.setHorizontalAlignment(horizontal_alignment);
		arrangeButtons();
	}

	@Override
	public void setVerticalAlignment(EVerticalAlignment vertical_alignment) {
		super.setVerticalAlignment(vertical_alignment);
		arrangeButtons();
	}

	public Button createMenuItem(String text) {
		Button ret = new Button(text, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0,
				(getHorizontalAlignment() == EHorizontalAlignment.CENTER) ? EHorizontalAlignment.LEFT
						: getHorizontalAlignment(),
				(getVerticalAlignment() == EVerticalAlignment.CENTER) ? EVerticalAlignment.TOP : getVerticalAlignment(),
				button_color, button_hover_color, button_click_color, button_glow_color);
		buttons.add(ret);
		arrangeButtons();
		return ret;
	}

	public void setButtonSize(double button_size) {
		this.button_size = button_size;
		arrangeButtons();
	}

	public void setPadding(double padding) {
		this.padding = padding;
		setButtonSize(button_size);
	}

	public List<Button> getButtons() {
		return buttons;
	}

	@Override
	public void dispose() {
		super.dispose();
		for (Button i : buttons)
			i.dispose();
		buttons.clear();
	}
}