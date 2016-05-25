package com.gravisim3d.core;

/**
 * Class for mathematical vectors (replaces processing.core.PVector)
 * 
 * @author Ethem Kurt
 *
 * @param <T>
 *            Numerical type
 */
public class PVector<T extends Number> {

	/**
	 * X
	 */
	public T x;

	/**
	 * Y
	 */
	public T y;

	/**
	 * Z
	 */
	public T z;

	/**
	 * Constructor
	 * 
	 * @param x
	 *            X
	 * @param y
	 *            Y
	 * @param z
	 *            Z
	 */
	public PVector(T x, T y, T z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Get the magnuitude squared
	 * 
	 * @return Magnitude squared
	 */
	public double magSq() {
		return (x.doubleValue() * x.doubleValue()) + (y.doubleValue() * y.doubleValue())
				+ (z.doubleValue() * z.doubleValue());
	}

	/**
	 * Get the magnitude
	 * 
	 * @return Magnitude
	 */
	public double mag() {
		return Math.sqrt(magSq());
	}
}
