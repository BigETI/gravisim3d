package com.gravisim3d.ui;

import java.util.ArrayList;
import java.util.List;

import com.gravisim3d.core.PVectorD;

import processing.core.PGraphics;

/**
 * Drawable menu class
 * 
 * @author Ethem Kurt
 *
 */
public class Menu extends Panel {

	/**
	 * Buttons
	 */
	private ArrayList<Button> buttons = new ArrayList<Button>();

	/**
	 * Button color
	 */
	private int button_color;

	/**
	 * Button hover color
	 */
	private int button_hover_color;

	/**
	 * Button click color
	 */
	private int button_click_color;

	/**
	 * Button glow color
	 */
	private int button_glow_color;

	/**
	 * Button size
	 */
	private double button_size;

	/**
	 * Menu size
	 */
	private double menu_size;

	/**
	 * Padding
	 */
	private double padding = 5.0;

	/**
	 * Menu alignment
	 */
	private EMenuAlignment menu_alignment;

	/**
	 * Constructor
	 * 
	 * @param x
	 *            X
	 * @param y
	 *            Y
	 * @param z
	 *            Z
	 * @param menu_size
	 *            Menu size
	 * @param button_size
	 *            Button size
	 * @param horizontal_alignment
	 *            Horizontal alignment
	 * @param vertical_alignment
	 *            Vertical alignment
	 * @param color
	 *            Color
	 * @param button_color
	 *            Button color
	 * @param button_hover_color
	 *            Button hover color
	 * @param button_click_color
	 *            Button click color
	 * @param button_glow_color
	 *            Button glow color
	 * @param menu_alignment
	 *            Menu alignment
	 */
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

	/**
	 * Fix menu size
	 */
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

	/**
	 * Arrange buttons
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.ui.ADrawable#processInput(double, double, boolean,
	 * com.gravisim3d.core.PVectorD)
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.ui.ADrawable#drawSiblings(processing.core.PGraphics)
	 */
	@Override
	protected void drawSiblings(PGraphics graphics) {
		super.drawSiblings(graphics);
		if (isVisible()) {
			for (Button i : buttons)
				i.drawComponent(graphics, getSize());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.ui.ADrawable#setSize(double, double, double)
	 */
	@Override
	public void setSize(double size_x, double size_y, double size_z) {
		super.setSize(0.0, 0.0, 1.0);
		arrangeButtons();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.ui.ADrawable#setPos(double, double, double)
	 */
	@Override
	public void setPos(double x, double y, double z) {
		super.setPos(x, y, z);
		arrangeButtons();
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
		arrangeButtons();
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
		arrangeButtons();
	}

	/**
	 * Create menu item
	 * 
	 * @param text
	 *            Hint text
	 * @return Menu item
	 */
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

	/**
	 * Set button size
	 * 
	 * @param button_size
	 *            Button size
	 */
	public void setButtonSize(double button_size) {
		this.button_size = button_size;
		arrangeButtons();
	}

	/**
	 * Set padding
	 * 
	 * @param padding
	 *            Padding
	 */
	public void setPadding(double padding) {
		this.padding = padding;
		setButtonSize(button_size);
	}

	/**
	 * Get menu items
	 * 
	 * @return Menu items
	 */
	public List<Button> getButtons() {
		return buttons;
	}

	/**
	 * Create menu items
	 * 
	 * @param menu_items
	 *            Menu items
	 * @return Menu items
	 */
	public Button[] createMenuItems(IMenuItem[] menu_items) {
		Button[] ret = new Button[menu_items.length];
		for (int i = 0; i < menu_items.length; i++) {
			ret[i] = createMenuItem("");
			ret[i].setHint(menu_items[i].getTitle() + "\n\n" + menu_items[i].getDescription());
		}
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.ui.ADrawable#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
		for (Button i : buttons)
			i.dispose();
		buttons.clear();
	}
}