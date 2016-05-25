package com.gravisim3d.core;

/**
 * Class for Double PVector type
 * 
 * @author Ethem Kurt
 *
 */
public class PVectorD extends PVector<Double> {

	/**
	 * Default constructor
	 */
	public PVectorD() {
		super(0.0, 0.0, 0.0);
	}

	/**
	 * Constructor
	 * 
	 * @param x
	 *            X
	 * @param y
	 *            Y
	 */
	public PVectorD(double x, double y) {
		super(x, y, 0.0);
	}

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
	public PVectorD(double x, double y, double z) {
		super(x, y, z);
	}
}
