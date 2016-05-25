package com.gravisim3d.ui;

import java.util.List;

import com.gravisim3d.core.PVectorD;

/**
 * Physical object class
 * 
 * @author Ethem Kurt
 *
 */
public abstract class APhysical extends ADrawable {

	/**
	 * Mass
	 */
	private double mass;

	/**
	 * Velocity
	 */
	private PVectorD velocity;

	/**
	 * Constructor
	 * 
	 * @param x
	 *            X
	 * @param y
	 *            Y
	 * @param z
	 *            Z
	 * @param size_x
	 *            Size X
	 * @param size_y
	 *            Size Y
	 * @param size_z
	 *            SIze Z
	 * @param mass
	 *            Mass
	 * @param velocity_x
	 *            Velocity X
	 * @param velocity_y
	 *            Velocity Y
	 * @param velocity_z
	 *            Velocity Z
	 * @param color
	 *            Color
	 */
	public APhysical(double x, double y, double z, double size_x, double size_y, double size_z, double mass,
			double velocity_x, double velocity_y, double velocity_z, int color) {
		super(x, y, z, size_x, size_y, size_z, EHorizontalAlignment.LEFT, EVerticalAlignment.TOP, color);
		setMass(mass);
		setVelocity(velocity_x, velocity_y, velocity_z);
	}

	/**
	 * Emulate physics
	 * 
	 * @param arbitary_pos
	 *            Arbitary position
	 * @param arbitary_velocity
	 *            Arbitary velocity
	 * @param physical_objects
	 *            Physical objects to interact with
	 * @param delta_millis
	 *            Delta milliseconds
	 * @param gravitational_constant
	 *            Gravitational constant
	 * @return Computed position and velocity
	 */
	public Rectangle<PVectorD> emulatePhysics(PVectorD arbitary_pos, PVectorD arbitary_velocity,
			List<APhysical> physical_objects, long delta_millis, double gravitational_constant) {
		PVectorD new_pos = new PVectorD(arbitary_pos.x + (arbitary_velocity.x * ((double) delta_millis) * 0.001),
				arbitary_pos.y + (arbitary_velocity.y * ((double) delta_millis) * 0.001),
				arbitary_pos.z + (arbitary_velocity.z * ((double) delta_millis) * 0.001));
		PVectorD delta_vector;
		double mag_sq;
		double mag;
		PVectorD speed_vector;
		double force;
		double delta_speed;
		for (APhysical i : physical_objects) {
			if (i != this) {
				delta_vector = new PVectorD(i.getPos().x - arbitary_pos.x, i.getPos().y - arbitary_pos.y,
						i.getPos().z - arbitary_pos.z);
				mag_sq = delta_vector.magSq();
				mag = Math.sqrt(mag_sq);
				// force = 0.0001 * i.getMass() * mag_sq;
				force = (gravitational_constant * i.getMass()) / mag_sq;
				delta_speed = force * delta_millis * 0.001;
				speed_vector = new PVectorD((delta_vector.x / mag) * delta_speed, (delta_vector.y / mag) * delta_speed,
						(delta_vector.z / mag) * delta_speed);
				arbitary_velocity = new PVectorD(arbitary_velocity.x + speed_vector.x,
						arbitary_velocity.y + speed_vector.y, arbitary_velocity.z + speed_vector.z);
			}
		}
		return new Rectangle<PVectorD>(new_pos, arbitary_velocity);
	}

	/**
	 * Apply physics
	 * 
	 * @param physical_objects
	 *            Physical objects to interact with
	 * @param delta_millis
	 *            Delta milliseconds
	 * @param gravitational_constant
	 *            Gravitational constant
	 */
	public void applyPhysics(List<APhysical> physical_objects, long delta_millis, double gravitational_constant) {
		Rectangle<PVectorD> pos_velocity = emulatePhysics(getPos(), velocity, physical_objects, delta_millis,
				gravitational_constant);
		setPos(pos_velocity.getX().x, pos_velocity.getX().y, pos_velocity.getX().z);
		velocity = pos_velocity.getY();
	}

	/**
	 * Set mass
	 * 
	 * @param mass Mass
	 */
	public void setMass(double mass) {
		this.mass = mass;
	}

	/**
	 * Get mass
	 * 
	 * @return Mass
	 */
	public double getMass() {
		return mass;
	}

	/**
	 * Set velocity
	 * 
	 * @param velocity_x
	 *            Velocity X
	 * @param velocity_y
	 *            Velocity Y
	 * @param velocity_z
	 *            Velocity Z
	 */
	public void setVelocity(double velocity_x, double velocity_y, double velocity_z) {
		velocity = new PVectorD(velocity_x, velocity_y, velocity_z);
	}

	/**
	 * Get velocity
	 * 
	 * @return Velocity
	 */
	public PVectorD getVelocity() {
		return velocity;
	}
}