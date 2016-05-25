package com.gravisim3d.ui;

/**
 * Generic rectangle type class
 * 
 * @author Ethem Kurt
 *
 * @param <T>
 *            Generic type
 */
public class Rectangle<T> {

	/**
	 * X
	 */
	private T x;

	/**
	 * Y
	 */
	private T y;

	/**
	 * Constructor
	 * 
	 * @param x
	 *            X
	 * @param y
	 *            Y
	 */
	public Rectangle(T x, T y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Constructor
	 * 
	 * @param rect
	 *            Rectangle
	 */
	public Rectangle(Rectangle<T> rect) {
		x = rect.x;
		y = rect.y;
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
}