package com.gravisim3d.ui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.gravisim3d.core.PVectorD;
import com.gravisim3d.event.HoverEventArgs;

import processing.core.PGraphics;

/**
 * Drawable universe class
 * 
 * @author Ethem Kurt
 *
 */
public class Universe3D extends UI {

	/**
	 * Gravitational constant
	 */
	private double gravitational_constant;

	/**
	 * Physical objects
	 */
	private LinkedList<APhysical> physical_objects = new LinkedList<APhysical>();

	/**
	 * Last milliseconds
	 */
	private long last_millis = System.currentTimeMillis();

	/**
	 * Text graphics
	 */
	private PGraphics text_graphics;

	/**
	 * Constructor
	 * 
	 * @param size_x
	 *            Size X
	 * @param size_y
	 *            Size y
	 * @param gravitational_constant
	 *            Gravitational constant
	 */
	public Universe3D(double size_x, double size_y, double gravitational_constant) {
		super(size_x, size_y);
		text_graphics = GraviSim3D.getApplet().createGraphics((int) size_x, (int) size_y);
		this.gravitational_constant = gravitational_constant;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.ui.UI#draw(processing.core.PGraphics)
	 */
	@Override
	protected void draw(PGraphics graphics) {
		//
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.ui.ADrawable#processInput(double, double, boolean,
	 * com.gravisim3d.core.PVectorD)
	 */
	@Override
	public void processInput(double pos_x, double pos_y, boolean pressed, PVectorD constraint) {
		super.processInput(pos_x, pos_y, pressed, constraint);
		if (isVisible()) {
			switch (getHorizontalAlignment()) {
			case LEFT:
				pos_x -= getPos().x;
				break;
			case RIGHT:
				pos_x -= (constraint.x - getSize().x) - getPos().x;
				break;
			case CENTER:
				pos_x -= ((constraint.x - getSize().x) * 0.5) + getPos().x;
				break;
			}
			switch (getVerticalAlignment()) {
			case TOP:
				pos_y -= getPos().y;
				break;
			case BOTTOM:
				pos_y -= (constraint.y - getSize().y) - getPos().y;
				break;
			case CENTER:
				pos_y -= ((constraint.y - getSize().y) * 0.5) + getPos().y;
				break;
			}
			for (APhysical i : physical_objects)
				i.processInput(pos_x, pos_y, pressed, getSize());
			if (inBox(pos_x, pos_y)) {
				if (!hover_entered) {
					hover_entered = true;
					if (hint != null)
						GraviSim3D.getApplet().getHintController().addDrawableHint(this);
					getHoverNotifier().setOnEnter(new HoverEventArgs(pos_x, pos_y));
				}
				getHoverNotifier().setOnHover(new HoverEventArgs(pos_x, pos_y));
			} else {
				if (hover_entered) {
					hover_entered = false;
					GraviSim3D.getApplet().getHintController().removeDrawableHint(this);
					getHoverNotifier().setOnLeave(new HoverEventArgs(pos_x, pos_y));
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.ui.ADrawable#drawSiblings(processing.core.PGraphics)
	 */
	@Override
	protected void drawSiblings(PGraphics graphics) {
		super.drawSiblings(graphics);
		ArrayList<APhysical> backup_physical_objects = new ArrayList<APhysical>(physical_objects);
		long curr_millis = System.currentTimeMillis();
		long delta_millis = curr_millis - last_millis;
		last_millis = curr_millis;
		for (APhysical i : physical_objects) {
			if (i != null) {
				i.applyPhysics(backup_physical_objects, delta_millis, gravitational_constant);
				if (i instanceof Planet3D) {
					// ((Planet3D) i).showPlanetInfo(show_planets_info);
					// ((Planet3D) i).showPrediction(show_prediction);
				}
				i.drawComponent(graphics, getSize());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.ui.UI#drawMain()
	 */
	@Override
	public void drawMain() {
		super.drawMain();
		text_graphics.beginDraw();
		for (APhysical i : physical_objects) {
			if (i instanceof Planet3D) {

				// i.drawText(text_graphics);
			}
		}
		text_graphics.endDraw();
	}

	/**
	 * Add physical object
	 * 
	 * @param p
	 */
	public void addPhysicalObject(APhysical p) {
		if (p != null)
			physical_objects.add(p);
	}

	/**
	 * Remove all physical objects
	 */
	public void clear() {
		for (APhysical i : physical_objects)
			i.dispose();
		physical_objects.clear();
	}

	/**
	 * Remove last physical object
	 */
	public void removeLast() {
		APhysical physical_object;
		if (physical_objects.size() > 0) {
			physical_object = physical_objects.getLast();
			physical_object.dispose();
			physical_objects.remove(physical_object);
		}
	}

	/**
	 * Get gravitational constant
	 * 
	 * @return Gravitational constant
	 */
	public double getGravitationalConstant() {
		return gravitational_constant;
	}

	/**
	 * Get physical objects
	 * 
	 * @return Physical objects
	 */
	public List<APhysical> getPhysicalObjects() {
		return physical_objects;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gravisim3d.ui.ADrawable#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
		for (APhysical i : physical_objects)
			i.dispose();
		physical_objects.clear();
	}
}