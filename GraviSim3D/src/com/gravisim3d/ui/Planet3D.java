package com.gravisim3d.ui;

import java.util.List;

import com.gravisim3d.core.PVectorD;

import processing.core.PGraphics;

/**
 * Drawable 3D planet class
 * 
 * @author Ethem Kurt
 *
 */
public class Planet3D extends APhysical {

	/**
	 * Radius
	 */
	private double radius;

	/**
	 * Drawable with hint
	 */
	private ADrawable hint_drawable;

	/**
	 * Constructor
	 * 
	 * @param x
	 *            X
	 * @param y
	 *            Y
	 * @param z
	 *            Z
	 * @param radius
	 *            Radius
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
	public Planet3D(double x, double y, double z, double radius, double mass, double velocity_x, double velocity_y,
			double velocity_z, int color) {
		super(x, y, z, radius, radius, radius, mass, velocity_x, velocity_y, velocity_z, color);
		setRadius(radius);
		hint_drawable = new ADrawable(-radius, -radius, 200.0, radius * 2.0, radius * 2.0, 1.0,
				EHorizontalAlignment.LEFT, EVerticalAlignment.TOP, 0xFF000000) {

			/*
			 * (non-Javadoc)
			 * 
			 * @see com.gravisim3d.ui.ADrawable#draw(processing.core.PGraphics)
			 */
			@Override
			public void draw(PGraphics graphics) {
				//
			}
		};
		addDrawable(hint_drawable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.ui.APhysical#applyPhysics(java.util.List, long,
	 * double)
	 */
	@Override
	public void applyPhysics(List<APhysical> physical_objects, long delta_millis, double gravitational_constant) {
		super.applyPhysics(physical_objects, delta_millis, gravitational_constant);
		// drawPrediction(physical_objects, gravitational_constant); //<>//
	}

	/**
	 * Draw predicition
	 * 
	 * @param graphics
	 *            Graphics
	 * @param physical_objects
	 *            Physical objects interacting with
	 * @param gravitational_constant
	 *            Gravitational constant
	 */
	public void drawPrediction(PGraphics graphics, List<APhysical> physical_objects, double gravitational_constant) {
		Rectangle<PVectorD> d_pos_velocity = new Rectangle<PVectorD>(getPos(), getVelocity());
		// PVector last_pos = getPos();
		graphics.stroke((getColor() & 0x00FFFFFF) | 0x7F000000);
		graphics.noFill();
		graphics.beginShape();
		for (int i = 0; i < 1000; i++) {
			d_pos_velocity = emulatePhysics(d_pos_velocity.getX(), d_pos_velocity.getY(), physical_objects, 100,
					gravitational_constant);
			graphics.vertex(d_pos_velocity.getX().x.floatValue(), d_pos_velocity.getX().y.floatValue(),
					d_pos_velocity.getX().z.floatValue());
			// graphics.line(last_pos.x, last_pos.y, last_pos.z,
			// d_pos_velocity.getX().x, d_pos_velocity.getX().y,
			// d_pos_velocity.getX().z);
			// last_pos = d_pos_velocity.getX();
		}
		graphics.endShape();
	}

	/**
	 * Draw effecting lines
	 * 
	 * @param graphics
	 *            Graphics
	 * @param physical_objects
	 *            Physical objects interacting with
	 */
	public void drawEffectingLines(PGraphics graphics, List<APhysical> physical_objects) {
		for (APhysical i : physical_objects) {
			if ((i != null) && (i != this)) {
				graphics.stroke(0x3FFFFFFF);
				graphics.noFill();
				graphics.line(getPos().x.floatValue(), getPos().y.floatValue(), getPos().z.floatValue(),
						i.getPos().x.floatValue(), i.getPos().y.floatValue(), i.getPos().z.floatValue());
			}
		}
	}

	/*
	 * @Override public void setSize(float size_x, float size_y, float size_z) {
	 * setRadius((size_x + size_y + size_z) / 3.0); }
	 */

	/**
	 * Set radius
	 * 
	 * @param radius
	 *            Radius
	 */
	public void setRadius(double radius) {
		super.setSize(radius, radius, radius);
		this.radius = radius;
	}

	/**
	 * Get radius
	 * 
	 * @return Radius
	 */
	public double getRadius() {
		return radius;
	}

	/*
	 * @Override protected void drawSiblings(PGraphics graphics) {
	 * super.drawSiblings(graphics); }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.ui.ADrawable#draw(processing.core.PGraphics)
	 */
	@Override
	protected void draw(PGraphics graphics) {
		graphics.noStroke();
		graphics.lights();
		graphics.sphere(1.0f);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.ui.ADrawable#setHint(java.lang.String)
	 */
	@Override
	public void setHint(String hint_text) {
		hint_drawable.setHint(hint_text);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.ui.ADrawable#getHint()
	 */
	@Override
	public Hint getHint() {
		return hint_drawable.getHint();
	}

	/*
	 * public void drawText(PGraphics graphics) { status.setText("Radius: " +
	 * radius + "\nMass: " + getMass() + "\nVelocity: " + getVelocity().mag() +
	 * " km/s"); status.drawComponent(graphics, new PVector(0.0, 0.0, 0.0)); }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.ui.ADrawable#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
		hint_drawable.dispose();
		hint_drawable = null;
	}
}