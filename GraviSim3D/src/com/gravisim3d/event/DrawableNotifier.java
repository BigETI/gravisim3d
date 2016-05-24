package com.gravisim3d.event;

import processing.core.PGraphics;

public class DrawableNotifier extends Notifier<IDrawableListener> {

	public void setOnDrawComponent(PGraphics graphics) {
		for (IDrawableListener i : getListeners()) {
			i.onDrawComponent(graphics);
		}
	}

	public void setOnDrawSiblings(PGraphics graphics) {
		for (IDrawableListener i : getListeners()) {
			i.onDrawSiblings(graphics);
		}
	}
}