package com.gravisim3d.ui;

import java.util.LinkedHashSet;

import com.gravisim3d.core.PVectorD;
import com.gravisim3d.event.DrawableNotifier;
import com.gravisim3d.event.HoverEventArgs;
import com.gravisim3d.event.HoverNotifier;

import processing.core.*;

/**
 * Abstract drawable class
 * 
 * @author Ethem Kurt
 *
 */
public abstract class ADrawable {

	/**
	 * Position
	 */
	private PVectorD pos;

	/**
	 * Size
	 */
	private PVectorD size;

	/**
	 * Zoom
	 */
	private double zoom = 1.0;

	/**
	 * Rotation
	 */
	private PVectorD rot = new PVectorD();

	/**
	 * Horizontal alignment
	 */
	private EHorizontalAlignment horizontal_alignment;

	/**
	 * Vertical alignment
	 */
	private EVerticalAlignment vertical_alignment;

	/**
	 * Color
	 */
	private int color;

	/**
	 * Hover notifier
	 */
	private HoverNotifier hover_notifier = new HoverNotifier();

	/**
	 * Drawables
	 */
	private LinkedHashSet<ADrawable> drawables = new LinkedHashSet<ADrawable>();

	/**
	 * Hover entered
	 */
	protected boolean hover_entered = false;

	/**
	 * Is visible
	 */
	private boolean visible = true;

	/**
	 * Hint
	 */
	protected Hint hint = null;

	/**
	 * Drawable notifier
	 */
	private DrawableNotifier drawable_notifier = new DrawableNotifier();

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
	 *            Size Z
	 * @param horizontal_alignment
	 *            Horizontal alignment
	 * @param vertical_alignment
	 *            Vertical alignment
	 * @param color
	 *            Color
	 */
	public ADrawable(double x, double y, double z, double size_x, double size_y, double size_z,
			EHorizontalAlignment horizontal_alignment, EVerticalAlignment vertical_alignment, int color) {
		setPos(x, y, z);
		setSize(size_x, size_y, size_z);
		this.horizontal_alignment = horizontal_alignment;
		this.vertical_alignment = vertical_alignment;
		this.color = color;
	}

	/**
	 * Is in box?
	 * 
	 * @param pos_x
	 *            Position X
	 * @param pos_y
	 *            Position Y
	 * @return Is in box
	 */
	public boolean inBox(double pos_x, double pos_y) {
		return ((pos_x >= 0.0) && (pos_x <= size.x) && (pos_y >= 0.0) && (pos_y <= size.y));
	}

	/**
	 * Process input
	 * 
	 * @param pos_x
	 *            Position X
	 * @param pos_y
	 *            Poition Y
	 * @param pressed
	 *            Mouse pressed
	 * @param constraint
	 *            Constraint
	 */
	public void processInput(double pos_x, double pos_y, boolean pressed, PVectorD constraint) {
		if (visible) {
			switch (horizontal_alignment) {
			case LEFT:
				pos_x -= pos.x;
				break;
			case RIGHT:
				pos_x -= (constraint.x - size.x) - pos.x;
				break;
			case CENTER:
				pos_x -= ((constraint.x - size.x) * 0.5) + pos.x;
				break;
			}
			switch (vertical_alignment) {
			case TOP:
				pos_y -= pos.y;
				break;
			case BOTTOM:
				pos_y -= (constraint.y - size.y) - pos.y;
				break;
			case CENTER:
				pos_y -= ((constraint.y - size.y) * 0.5) + pos.y;
				break;
			}
			for (ADrawable i : drawables)
				i.processInput(pos_x, pos_y, pressed, size);
			if (inBox(pos_x, pos_y)) {
				if (!hover_entered) {
					hover_entered = true;
					if (hint != null)
						GraviSim3D.getApplet().getHintController().addDrawableHint(this);
					hover_notifier.setOnEnter(new HoverEventArgs(pos_x, pos_y));
				}
				hover_notifier.setOnHover(new HoverEventArgs(pos_x, pos_y));
			} else {
				if (hover_entered) {
					hover_entered = false;
					GraviSim3D.getApplet().getHintController().removeDrawableHint(this);
					hover_notifier.setOnLeave(new HoverEventArgs(pos_x, pos_y));
				}
			}
		}
	}

	/**
	 * Draw component
	 * 
	 * @param graphics
	 *            Graphics
	 * @param constraint
	 *            Constraint
	 */
	public void drawComponent(PGraphics graphics, PVectorD constraint) {
		if (visible) {
			double d_x = 0.0, d_y = 0.0;
			switch (horizontal_alignment) {
			case LEFT:
				d_x = pos.x;
				break;
			case RIGHT:
				d_x = (constraint.x - size.x) - pos.x;
				break;
			case CENTER:
				d_x = ((constraint.x - size.x) * 0.5) + pos.x;
				break;
			}
			switch (vertical_alignment) {
			case TOP:
				d_y = pos.y;
				break;
			case BOTTOM:
				d_y = (constraint.y - size.y) - pos.y;
				break;
			case CENTER:
				d_y = ((constraint.y - size.y) * 0.5) + pos.y;
				break;
			}
			graphics.pushMatrix();
			graphics.translate((float) d_x, (float) d_y, pos.z.floatValue());
			graphics.pushMatrix();
			graphics.scale(size.x.floatValue(), size.y.floatValue(), size.z.floatValue());
			graphics.rotateX(rot.x.floatValue());
			graphics.rotateY(rot.y.floatValue());
			graphics.rotateZ(rot.z.floatValue());

			/*
			 * temporary_graphics.pushMatrix();
			 * temporary_graphics.translate(d_x, d_y, pos.z);
			 * temporary_graphics.pushMatrix(); temporary_graphics.scale(size.x,
			 * size.y, size.z); temporary_graphics.rotateX(rot.x);
			 * temporary_graphics.rotateY(rot.y);
			 * temporary_graphics.rotateZ(rot.z);
			 */

			graphics.noStroke();
			graphics.fill(color);
			draw(graphics);
			drawable_notifier.setOnDrawComponent(graphics);
			/*
			 * if (allow_depth_buffer) draw(graphics); else {
			 * temporary_graphics.background(0x00FFFFFF);
			 * draw(temporary_graphics); graphics.image(temporary_graphics, 0,
			 * 0); }
			 */
			graphics.popMatrix();
			graphics.scale((float) zoom, (float) zoom, (float) zoom);
			/*
			 * temporary_graphics.popMatrix(); temporary_graphics.scale(zoom,
			 * zoom, zoom);
			 */
			drawSiblings(graphics);
			/*
			 * if (allow_depth_buffer) drawSiblings(graphics); else {
			 * temporary_graphics.background(0x00FFFFFF);
			 * drawSiblings(temporary_graphics);
			 * graphics.image(temporary_graphics, 0, 0); }
			 */
			graphics.popMatrix();
			// temporary_graphics.popMatrix();
		}
	}

	/**
	 * Draw
	 * 
	 * @param graphics
	 *            Graphics
	 */
	protected abstract void draw(PGraphics graphics);

	/**
	 * Draw siblings
	 * 
	 * @param graphics
	 *            Graphics
	 */
	protected void drawSiblings(PGraphics graphics) {
		for (ADrawable i : drawables) {
			i.drawComponent(graphics, size);
		}
		drawable_notifier.setOnDrawSiblings(graphics);
	}

	/**
	 * Set position
	 * 
	 * @param pos_x
	 *            Position X
	 * @param pos_y
	 *            Position Y
	 * @param pos_z
	 *            Position Z
	 */
	public void setPos(double pos_x, double pos_y, double pos_z) {
		pos = new PVectorD(pos_x, pos_y, pos_z);
	}

	/**
	 * Get position
	 * 
	 * @return Position
	 */
	public PVectorD getPos() {
		return pos;
	}

	/**
	 * Set size
	 * 
	 * @param x
	 *            Size X
	 * @param y
	 *            Size Y
	 * @param z
	 *            Size Z
	 */
	public void setSize(double x, double y, double z) {
		size = new PVectorD(x, y, z);
	}

	/**
	 * Get size
	 * 
	 * @return Size
	 */
	public PVectorD getSize() {
		return size;
	}

	/**
	 * Set color
	 * 
	 * @param color
	 *            Color
	 */
	public void setColor(int color) {
		this.color = color;
	}

	/**
	 * Get color
	 * 
	 * @return Color
	 */
	public int getColor() {
		return color;
	}

	/**
	 * Set zoom
	 * 
	 * @param zoom
	 *            Zoom
	 */
	public void setZoom(double zoom) {
		this.zoom = zoom;
	}

	/**
	 * Get zoom
	 * 
	 * @return Zoom
	 */
	public double getZoom() {
		return zoom;
	}

	/**
	 * Set rotation in degrees
	 * 
	 * @param x
	 *            X
	 * @param y
	 *            Y
	 * @param z
	 *            Z
	 */
	public void setRot(double x, double y, double z) {
		rot = new PVectorD(x, y, z);
	}

	/**
	 * Get rotation in degrees
	 * 
	 * @return Rotation in degrees
	 */
	public PVectorD getRot() {
		return rot;
	}

	/**
	 * Set horizontal alignment
	 * 
	 * @param horizontal_alignment
	 *            Horizontal alignment
	 */
	public void setHorizontalAlignment(EHorizontalAlignment horizontal_alignment) {
		this.horizontal_alignment = horizontal_alignment;
	}

	/**
	 * Get horizontal alignment
	 * 
	 * @return Horizontal alignment
	 */
	public EHorizontalAlignment getHorizontalAlignment() {
		return horizontal_alignment;
	}

	/**
	 * Set vertical alignment
	 * 
	 * @param vertical_alignment
	 *            Vertical alignment
	 */
	public void setVerticalAlignment(EVerticalAlignment vertical_alignment) {
		this.vertical_alignment = vertical_alignment;
	}

	/**
	 * Get vertical alignment
	 * 
	 * @return Vertical alignment
	 */
	public EVerticalAlignment getVerticalAlignment() {
		return vertical_alignment;
	}

	/**
	 * Add drawable
	 * 
	 * @param drawable
	 *            Drawable
	 */
	public void addDrawable(ADrawable drawable) {
		if (drawable != null)
			drawables.add(drawable);
	}

	/**
	 * Remove drawable
	 * 
	 * @param drawable
	 *            Drawable
	 */
	public void removeDrawable(ADrawable drawable) {
		if (drawables.contains(drawable)) {
			drawables.remove(drawable);
		}
	}

	/**
	 * Remove all drawables
	 */
	public void removeAll() {
		drawables.clear();
	}

	/**
	 * Is mouse hovering over this
	 * 
	 * @return Is mouse hovering over this
	 */
	public boolean isHovering() {
		return hover_entered;
	}

	/**
	 * Set visible
	 * 
	 * @param visible
	 *            Visible
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/**
	 * Is visible
	 * 
	 * @return Visible
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * Get hover notifier
	 * 
	 * @return Hover notifier
	 */
	public HoverNotifier getHoverNotifier() {
		return hover_notifier;
	}

	/**
	 * Set hint
	 * 
	 * @param hint_text
	 *            Hint text
	 */
	public void setHint(String hint_text) {
		if (hint_text == null)
			hint = null;
		else {
			hint_text = hint_text + "\n";
			if (hint == null)
				hint = new Hint(hint_text, 100.0, 100.0, 1.0, 0x3FFFFFFF);
			else
				hint.getHintText().setText(hint_text);
			hint.setPadding(hint.getPadding());
		}
	}

	/**
	 * Get hint
	 * 
	 * @return Hint
	 */
	public Hint getHint() {
		return hint;
	}

	/**
	 * Get drawable notifier
	 * 
	 * @return Drawable notifier
	 */
	public DrawableNotifier getDrawableNotifier() {
		return drawable_notifier;
	}

	/**
	 * Dispose this object
	 */
	public void dispose() {
		drawables.clear();
		hover_notifier.removeAllListeners();
		GraviSim3D.getApplet().getHintController().removeDrawableHint(this);
		if (hint != null) {
			hint.dispose();
			hint = null;
		}
	}
}