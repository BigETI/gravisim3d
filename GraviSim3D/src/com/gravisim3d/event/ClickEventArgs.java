package com.gravisim3d.event;

import com.gravisim3d.core.PVectorD;

public class ClickEventArgs {
	private PVectorD pos;

	public ClickEventArgs(double pos_x, double pos_y) {
		pos = new PVectorD(pos_x, pos_y);
	}

	public PVectorD getPos() {
		return pos;
	}
}