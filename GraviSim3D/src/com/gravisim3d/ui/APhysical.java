package com.gravisim3d.ui;

import java.util.List;

import com.gravisim3d.core.PVectorD;

public abstract class APhysical extends ADrawable {

	private double mass;

	private PVectorD velocity;

	public APhysical(double x, double y, double z, double size_x, double size_y, double size_z, double mass,
			double velocity_x, double velocity_y, double velocity_z, int color) {
		super(x, y, z, size_x, size_y, size_z, EHorizontalAlignment.LEFT, EVerticalAlignment.TOP, color);
		setMass(mass);
		setVelocity(velocity_x, velocity_y, velocity_z);
	}

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

	public void applyPhysics(List<APhysical> physical_objects, long delta_millis, double gravitational_constant) {
		Rectangle<PVectorD> pos_velocity = emulatePhysics(getPos(), velocity, physical_objects, delta_millis,
				gravitational_constant);
		setPos(pos_velocity.getX().x, pos_velocity.getX().y, pos_velocity.getX().z);
		velocity = pos_velocity.getY();
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	public double getMass() {
		return mass;
	}

	public void setVelocity(double velocity_x, double velocity_y, double velocity_z) {
		velocity = new PVectorD(velocity_x, velocity_y, velocity_z);
	}

	public PVectorD getVelocity() {
		return velocity;
	}
}