package com.gravisim3d.core;

public class PVector<T extends Number> {

	public T x;

	public T y;

	public T z;

	public PVector(T x, T y, T z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double magSq() {
		return (x.doubleValue() * x.doubleValue()) + (y.doubleValue() * y.doubleValue())
				+ (z.doubleValue() * z.doubleValue());
	}

	public double mag() {
		return Math.sqrt(magSq());
	}
}
