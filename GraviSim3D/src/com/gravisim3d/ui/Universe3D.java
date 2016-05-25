package com.gravisim3d.ui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.gravisim3d.core.PVectorD;
import com.gravisim3d.event.HoverEventArgs;

import processing.core.PGraphics;

public class Universe3D extends UI {

	private double gravitational_constant;
	private LinkedList<APhysical> physical_objects = new LinkedList<APhysical>();
	private long last_millis = System.currentTimeMillis();
	private PGraphics text_graphics;

	public Universe3D(double x, double y, double d) {
		super(x, y);
		allowDepthBuffer(true);
		text_graphics = GraviSim3D.getApplet().createGraphics((int) x, (int) y);
		this.gravitational_constant = d;
	}

	@Override
	protected void draw(PGraphics graphics) {
		//
	}

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

	public void addPhysicalObject(APhysical p) {
		if (p != null)
			physical_objects.add(p);
	}

	public void clear() {
		for (APhysical i : physical_objects)
			i.dispose();
		physical_objects.clear();
	}

	public void removeLast() {
		APhysical physical_object;
		if (physical_objects.size() > 0) {
			physical_object = physical_objects.getLast();
			physical_object.dispose();
			physical_objects.remove(physical_object);
		}
	}

	public double getGravitationalConstant() {
		return gravitational_constant;
	}

	public List<APhysical> getPhysicalObjects() {
		return physical_objects;
	}

	@Override
	public void dispose() {
		super.dispose();
		for (APhysical i : physical_objects)
			i.dispose();
		physical_objects.clear();
	}
}