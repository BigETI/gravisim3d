package com.gravisim3d.ui;

import com.gravisim3d.core.FontLoader;
import com.gravisim3d.core.PVectorD;

import processing.core.PFont;
import processing.core.PGraphics;

/**
 * Drawable text class
 * 
 * @author Ethem Kurt
 *
 */
public class Text extends ADrawable {

	/**
	 * Text
	 */
	private String text;

	/**
	 * Text size
	 */
	private double text_size;

	/**
	 * Font
	 */
	private PFont font;

	/**
	 * Constructor
	 * 
	 * @param text
	 *            Text
	 * @param x
	 *            X
	 * @param y
	 *            Y
	 * @param z
	 *            Z
	 * @param text_size
	 *            Text size
	 * @param horizontal_alignment
	 *            Horizontal alignment
	 * @param vertical_alignment
	 *            Vertical alignment
	 * @param color
	 *            Color
	 */
	public Text(String text, double x, double y, double z, double text_size, EHorizontalAlignment horizontal_alignment,
			EVerticalAlignment vertical_alignment, int color) {
		super(x, y, z, 0.0, 0.0, 1.0, horizontal_alignment, vertical_alignment, color);
		setText(text);
		setTextSize(text_size);
		setFont("Ubuntu-C");
	}

	/**
	 * Set text
	 * 
	 * @param text
	 *            Text
	 */
	public void setText(String text) {
		if (text == null)
			this.text = "";
		else
			this.text = text;
		updateSizeFromText();
	}

	/**
	 * Set font by name
	 * 
	 * @param font_name
	 *            Font by name
	 */
	public void setFont(String font_name) {
		font = FontLoader.get(font_name);
	}

	/**
	 * Get font
	 * 
	 * @return Font
	 */
	public PFont getFont() {
		return font;
	}

	/*
	 * @Override public void setSize(float size_x, float size_y, float size_z) {
	 * //super }
	 */

	/**
	 * Update size from text
	 */
	private void updateSizeFromText() {
		PVectorD new_size;
		int col = 0, row = 1, curr_col = 0;
		if (text != null) {
			for (char c : text.toCharArray()) {
				if (c == '\n') {
					curr_col = 0;
					row++;
				} else if (Character.isLetterOrDigit(c) || Character.isSpaceChar(c))
					curr_col++;
				if (curr_col > col)
					col = curr_col;
			}
			new_size = new PVectorD(text_size * (float) col, text_size * 1.5 * (float) row, 1.0);
		} else
			new_size = new PVectorD(0.0, 0.0, 1.0);
		setSize(new_size.x, new_size.y, new_size.z);
	}

	/**
	 * Set text size
	 * 
	 * @param text_size
	 *            Text size
	 */
	public void setTextSize(double text_size) {
		this.text_size = text_size;
		updateSizeFromText();
	}

	/**
	 * Get text size
	 * 
	 * @return Text size
	 */
	public double getTextSize() {
		return text_size;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.ui.ADrawable#draw(processing.core.PGraphics)
	 */
	@Override
	protected void draw(PGraphics graphics) {
		/*graphics.pushMatrix();
		graphics.rect(0.0f, 0.0f, 1.0f, 1.0f);
		graphics.popMatrix();*/
		
		graphics.pushMatrix();
		//graphics.scale((float) (3.0 / getSize().x.doubleValue()), (float) (2.0 / getSize().y.doubleValue()),
				//(float) (1.0 / getSize().z.doubleValue()));
		graphics.scale((float) (1.5 / getSize().x.doubleValue()), (float) (1.0 / getSize().y.doubleValue()),
				(float) (1.0 / getSize().z.doubleValue()));
		//System.out.println("Text size: " + getSize() + "\n\t\"" + text + "\"");
		graphics.translate(0.0f, (float) text_size, 0.0f);
		graphics.textFont(font, (float) text_size);
		graphics.text(text, 0.0f, 0.0f);
		graphics.popMatrix();
		// System.out.println("Drawing text " + text + " at " + getPos().x + ";
		// " + getPos().y);
	}
}