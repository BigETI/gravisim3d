package com.gravisim3d.core;

public class PVectorD extends PVector<Double> {
	
	public PVectorD() {
		super(0.0, 0.0, 0.0);
	}
	
	public PVectorD(double x, double y) {
		super(x, y, 0.0);
	}
	
	public PVectorD(double x, double y, double z) {
		super(x, y, z);
	}
}
