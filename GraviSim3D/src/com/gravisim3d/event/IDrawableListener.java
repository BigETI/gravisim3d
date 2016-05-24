package com.gravisim3d.event;

import processing.core.PGraphics;

public interface IDrawableListener extends IListener {
	public void onDrawComponent(PGraphics graphics);

	public void onDrawSiblings(PGraphics graphics);
}