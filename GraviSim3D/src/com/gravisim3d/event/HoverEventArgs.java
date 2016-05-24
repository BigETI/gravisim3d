package com.gravisim3d.event;

import com.gravisim3d.core.PVectorD;

public class HoverEventArgs {
	private PVectorD pos;

	public HoverEventArgs(double x, double y) {
		pos = new PVectorD(x, y);
	}

	public PVectorD getPos() {
		return pos;
	}
}