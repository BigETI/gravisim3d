package com.gravisim3d.ui;

import com.gravisim3d.core.FontLoader;
import com.gravisim3d.core.PVectorD;

import processing.core.PFont;
import processing.core.PGraphics;

public class Text extends ADrawable {

	private String text;

	private double text_size;

	private PFont font;

	public Text(String text, double x, double y, double z, double text_size, EHorizontalAlignment horizontal_alignment,
			EVerticalAlignment vertical_alignment, int color) {
		super(x, y, z, 1.0, 1.0, 1.0, horizontal_alignment, vertical_alignment, color);
		setText(text);
		setTextSize(text_size);
		setFont("Ubuntu-C");
	}

	public void setText(String text) {
		if (text == null)
			this.text = "";
		else
			this.text = text;
		updateSizeFromText();
	}
	
	public void setFont(String font_name) {
		font = FontLoader.get(font_name);
	}
	
	public PFont getFont() {
		return font;
	}

	/*
	 * @Override public void setSize(float size_x, float size_y, float size_z) {
	 * //super }
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
			new_size = new PVectorD(text_size * (float) col, (text_size * 1.5) * (float) row, 1.0);
		} else
			new_size = new PVectorD(0.0, 0.0, 1.0);
		setSize(new_size.x, new_size.y, new_size.z);
	}

	public void setTextSize(double text_size) {
		this.text_size = text_size;
		updateSizeFromText();
	}

	public double getTextSize() {
		return text_size;
	}

	@Override
	protected void draw(PGraphics graphics) {
		graphics.pushMatrix();
		graphics.scale(1.0f / getSize().x.floatValue(), 1.0f / getSize().y.floatValue(),
				1.0f / getSize().z.floatValue());
		graphics.translate(0.0f, (float) text_size, 0.0f);
		graphics.textFont(font, (float) text_size);
		graphics.text(text, 0.0f, 0.0f);
		graphics.popMatrix();
		// System.out.println("Drawing text " + text + " at " + getPos().x + ";
		// " + getPos().y);
	}
}