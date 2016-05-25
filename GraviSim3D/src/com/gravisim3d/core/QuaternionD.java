package com.gravisim3d.core;

public class QuaternionD extends Quaternion<Double> {

	/**
	 * Default constructor
	 */
	public QuaternionD() {
		super(1.0, 0.0, 0.0, 0.0);
	}

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
	public QuaternionD(double w, double x, double y, double z) {
		super(w, x, y, z);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.core.Quaternion#getMagnitudeSquared()
	 */
	public Double getMagnitudeSquared() {
		return ((getW() * getW()) + (getX() * getX()) + (getY() * getY()) + (getZ() * getZ()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.core.Quaternion#getMagnitude()
	 */
	public Double getMagnitude() {
		return Math.sqrt(getMagnitudeSquared());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.core.Quaternion#mult(com.gravisim3d.core.Quaternion)
	 */
	@Override
	public QuaternionD mult(Quaternion<Double> q) {
		return new QuaternionD((getW() * q.getW()) - ((getX() * q.getX()) + (getY() * q.getY()) + (getZ() * q.getZ())),
				(getW() * q.getX()) + (q.getW() * getX()) + (getY() * q.getZ()) - (getZ() * q.getY()),
				(getW() * q.getY()) + (q.getW() * getY()) + (getZ() * q.getX()) - (getX() * q.getZ()),
				(getW() * q.getZ()) + (q.getW() * getZ()) + (getX() * q.getY()) - (getY() * q.getX()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.core.Quaternion#conjugate()
	 */
	@Override
	public QuaternionD conjugate() {
		return new QuaternionD(-getW(), -getX(), -getY(), -getZ());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.core.Quaternion#reciprical()
	 */
	@Override
	public QuaternionD reciprical() {
		double n = getMagnitude();
		if (n == 0.0)
			n = 1.0;
		double r = 1.0 / n;
		return new QuaternionD(getW() * r, -getX() * r, -getY() * r, -getZ() * r);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.core.Quaternion#normalize()
	 */
	@Override
	public QuaternionD normalize() {
		double n = getMagnitude();
		double w = getW();
		double x = getX();
		double y = getY();
		double z = getZ();
		if (n == 0.0) {
			w = 1.0;
			x = 0.0;
			y = 0.0;
			z = 0.0;
		} else {
			double r = 1.0 / n;
			w *= r;
			x *= r;
			y *= r;
			z *= r;
		}
		return new QuaternionD(w, x, y, z);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.core.Quaternion#fromAxis(java.lang.Number,
	 * java.lang.Number, java.lang.Number, java.lang.Number)
	 */
	@Override
	public QuaternionD fromAxis(Double angle, Double x, Double y, Double z) {
		double omega;
		double s;
		double c;
		double t_w = getW();
		double t_x = getX();
		double t_y = getY();
		double t_z = getZ();

		s = Math.sqrt(x * x + y * y + z * z);

		if (Math.abs(s) > Double.MIN_VALUE) {
			c = 1.0 / s;
			x *= c;
			y *= c;
			z *= c;
			omega = -0.5f * angle;
			s = Math.sin(omega);
			t_w = Math.cos(omega);
			t_x = s * x;
			t_y = s * y;
			t_z = s * z;
		} else {
			t_w = 1.0;
			t_x = 0.0;
			t_y = 0.0;
			t_z = 0.0;
		}
		return (new QuaternionD(t_w, t_x, t_y, t_z)).normalize();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.core.Quaternion#fromAxis(java.lang.Number,
	 * com.gravisim3d.core.PVector)
	 */
	@Override
	public QuaternionD fromAxis(Double angle, PVector<Double> axis) {
		return fromAxis(angle, axis.x, axis.y, axis.z);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.core.Quaternion#slerp(com.gravisim3d.core.Quaternion,
	 * com.gravisim3d.core.Quaternion, java.lang.Number)
	 */
	@Override
	public QuaternionD slerp(Quaternion<Double> a, Quaternion<Double> b, Double t) {
		double omega;
		double cosom;
		double sinom;
		double sclp;
		double sclq;
		double w = getW();
		double x = getX();
		double y = getY();
		double z = getZ();

		cosom = (a.getW() * b.getW()) + (a.getX() * b.getX()) + (a.getY() * b.getY()) + (a.getZ() * b.getZ());

		if ((1.0f + cosom) > Double.MIN_VALUE) {
			if ((1.0f - cosom) > Double.MIN_VALUE) {
				omega = Math.acos(cosom);
				sinom = Math.sin(omega);
				sclp = Math.sin((1.0 - t) * omega) / sinom;
				sclq = Math.sin(t * omega) / sinom;
			} else {
				sclp = 1.0 - t;
				sclq = t;
			}

			w = sclp * a.getW() + sclq * b.getW();
			x = sclp * a.getX() + sclq * b.getX();
			y = sclp * a.getY() + sclq * b.getY();
			z = sclp * a.getZ() + sclq * b.getZ();
		} else {
			w = a.getZ();
			x = -a.getY();
			y = a.getX();
			z = -a.getW();

			sclp = Math.sin((1.0 - t) * Math.PI * 0.5);
			sclq = Math.sin(t * Math.PI * 0.5);

			x = sclp * a.getX() + sclq * b.getX();
			y = sclp * a.getY() + sclq * b.getY();
			z = sclp * a.getZ() + sclq * b.getZ();
		}
		return new QuaternionD(w, x, y, z);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.core.Quaternion#exp()
	 */
	@Override
	public QuaternionD exp() {
		double m;
		double l = Math.sqrt((getX() * getX()) + (getY() * getY()) + (getZ() * getZ()));

		if (l > 1.0e-4)
			m = Math.sin(l) / l;
		else
			m = 1.0;
		return new QuaternionD(Math.cos(l), getX() * m, getY() * m, getZ() * m);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.core.Quaternion#log()
	 */
	public QuaternionD log() {
		double l = Math.sqrt((getX() * getX()) + (getY() * getY()) + (getZ() * getZ()));
		l = Math.atan(l / getW());
		return new QuaternionD(0.0, getX() * l, getY() * l, getZ() * l);
	}
}
