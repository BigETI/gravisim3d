package com.gravisim3d.ui;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import processing.core.PGraphics;

public class HintController extends Panel {

	private double padding = 10.0;

	private EHintAlignment hint_alignment = EHintAlignment.TOP_LEFT;

	private LinkedHashSet<ADrawable> drawables = new LinkedHashSet<ADrawable>();

	public HintController() {
		super(0.0, 0.0, 0.0, 0.0, 0.0, 200.0, EHorizontalAlignment.LEFT, EVerticalAlignment.TOP, 0x00FFFFFF);
	}

	public void addDrawableHint(ADrawable drawable) {
		if (!drawables.contains(drawable))
			drawables.add(drawable);
	}

	public void removeDrawableHint(ADrawable drawable) {
		if (drawables.contains(drawable))
			drawables.remove(drawable);
	}

	public void removeDrawableHints() {
		drawables.clear();
	}

	public void setPadding(float padding) {
		this.padding = padding;
	}

	public double getPadding() {
		return padding;
	}

	private void updateAlignment(EHintAlignment hint_alignment) {
		Hint hint;
		// if (this.hint_alignment != hint_alignment) {
		this.hint_alignment = hint_alignment;
		for (ADrawable i : drawables) {
			hint = i.getHint();
			if (hint != null) {
				hint.setHorizontalAlignment(hint_alignment.getHorizontalAlignment());
				hint.setVerticalAlignment(hint_alignment.getVerticalAlignment());
			}
		}
		// }
	}

	@Override
	protected void drawSiblings(PGraphics graphics) {
		super.drawSiblings(graphics);
		if ((GraviSim3D.getApplet().pixelWidth / 2) >= GraviSim3D.getApplet().mouseX) {
			if ((GraviSim3D.getApplet().pixelHeight / 2) >= GraviSim3D.getApplet().mouseY)
				updateAlignment(EHintAlignment.TOP_LEFT);
			else
				updateAlignment(EHintAlignment.BOTTOM_LEFT);
		} else {
			if ((GraviSim3D.getApplet().pixelHeight / 2) >= GraviSim3D.getApplet().mouseY)
				updateAlignment(EHintAlignment.TOP_RIGHT);
			else
				updateAlignment(EHintAlignment.BOTTOM_RIGHT);
		}
		Hint hint;
		double last_y = 0.0;
		graphics.pushMatrix();
		LinkedList<ADrawable> list = new LinkedList<ADrawable>(drawables);
		Iterator<ADrawable> itr = list.descendingIterator();
		while (itr.hasNext()) {
			ADrawable i = itr.next();
			hint = i.getHint();
			if (hint != null) {
				if (hint_alignment.getVerticalAlignment() == EVerticalAlignment.TOP)
					graphics.translate((float) ((hint_alignment.getHorizontalAlignment() == EHorizontalAlignment.LEFT)
							? padding : -padding), (float) (last_y + padding), 0.0f);
				else
					graphics.translate((float) ((hint_alignment.getHorizontalAlignment() == EHorizontalAlignment.LEFT)
							? padding : -padding), (float) (-(last_y + padding)), 0.0f);
				hint.drawComponent(graphics, getSize());
				last_y = hint.getSize().y;
			}
		}
		graphics.popMatrix();
	}

	@Override
	public void dispose() {
		super.dispose();
		for (ADrawable i : drawables)
			i.dispose();
		drawables.clear();
	}
}