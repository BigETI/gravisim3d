package com.gravisim3d.core;

public abstract class Quaternion<T extends Number> {

	/**
	 * W
	 */
	private T w;

	/**
	 * X
	 */
	private T x;

	/**
	 * Y
	 */
	private T y;

	/**
	 * Z
	 */
	private T z;

	/**
	 * Constructor
	 * 
	 * @param w
	 *            W
	 * @param x
	 *            X
	 * @param y
	 *            Y
	 * @param z
	 *            Z
	 */
	public Quaternion(T w, T x, T y, T z) {
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Get W
	 * 
	 * @return W
	 */
	public T getW() {
		return w;
	}

	/**
	 * Get X
	 * 
	 * @return X
	 */
	public T getX() {
		return x;
	}

	/**
	 * Get Y
	 * 
	 * @return Y
	 */
	public T getY() {
		return y;
	}

	/**
	 * Get Z
	 * 
	 * @return Z
	 */
	public T getZ() {
		return z;
	}

	/**
	 * Get magnitude squared
	 * 
	 * @return Magnitude squared
	 */
	public abstract T getMagnitudeSquared();

	/**
	 * Get Magnitude
	 * 
	 * @return Magnitude
	 */
	public abstract T getMagnitude();

	/**
	 * Multiplicate
	 * 
	 * @param q
	 *            Quaternion
	 * @return Result
	 */
	public abstract Quaternion<T> mult(Quaternion<T> q);

	/**
	 * Conjugate
	 * 
	 * @return Result
	 */
	public abstract Quaternion<T> conjugate();

	/**
	 * Reprical
	 * 
	 * @return Result
	 */
	public abstract Quaternion<T> reciprical();

	/**
	 * Normalize
	 * 
	 * @return Result
	 */
	public abstract Quaternion<T> normalize();

	/**
	 * From axis
	 * 
	 * @param angle
	 *            Angle
	 * @param x
	 *            X
	 * @param y
	 *            Y
	 * @param z
	 *            Z
	 * @return Result
	 */
	public abstract Quaternion<T> fromAxis(T angle, T x, T y, T z);

	/**
	 * From angle
	 * 
	 * @param angle
	 *            ANgle
	 * @param axis
	 *            Axis
	 * @return Result
	 */
	public abstract Quaternion<T> fromAxis(T angle, PVector<T> axis);

	/**
	 * Slerp
	 * 
	 * @param a
	 *            A
	 * @param b
	 *            B
	 * @param t
	 *            T
	 * @return Result
	 */
	public abstract Quaternion<T> slerp(Quaternion<T> a, Quaternion<T> b, T t);

	/**
	 * Exponential
	 * 
	 * @return Result
	 */
	public abstract Quaternion<T> exp();

	/**
	 * Logarithmic
	 * 
	 * @return Result
	 */
	public abstract Quaternion<T> log();

}
