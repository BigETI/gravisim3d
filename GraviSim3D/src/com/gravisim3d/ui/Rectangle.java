package com.gravisim3d.ui;

public class Rectangle<T> {
	private T x;

	private T y;

	public Rectangle(T x, T y) {
		this.x = x;
		this.y = y;
	}

	public Rectangle(Rectangle<T> rect) {
		x = rect.x;
		y = rect.y;
	}

	public T getX() {
		return x;
	}

	public T getY() {
		return y;
	}
}