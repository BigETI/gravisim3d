package com.gravisim3d.ui;

import java.util.LinkedHashSet;

import com.gravisim3d.core.PVectorD;
import com.gravisim3d.event.DrawableNotifier;
import com.gravisim3d.event.HoverEventArgs;
import com.gravisim3d.event.HoverNotifier;

import processing.core.*;

public abstract class ADrawable {

	private static GraviSim3D applet = null;

	private PVectorD pos;

	private PVectorD size;

	private double zoom = 1.0;

	private PVectorD rot = new PVectorD();

	private EHorizontalAlignment horizontal_alignment;

	private EVerticalAlignment vertical_alignment;

	private int color;

	private boolean allow_depth_buffer = true;

	private HoverNotifier hover_notifier = new HoverNotifier();

	private LinkedHashSet<ADrawable> drawables = new LinkedHashSet<ADrawable>();

	protected boolean hover_entered = false;

	private boolean visible = true;

	protected Hint hint = null;

	private DrawableNotifier drawable_notifier = new DrawableNotifier();

	public ADrawable(double x, double y, double z, double size_x, double size_y, double size_z,
			EHorizontalAlignment horizontal_alignment, EVerticalAlignment vertical_alignment, int color) {
		setPos(x, y, z);
		setSize(size_x, size_y, size_z);
		this.horizontal_alignment = horizontal_alignment;
		this.vertical_alignment = vertical_alignment;
		this.color = color;
	}

	public static void init(GraviSim3D applet) {
		ADrawable.applet = applet;
	}

	public static GraviSim3D getApplet() {
		return applet;
	}

	public boolean inBox(double pos_x, double pos_y) {
		return ((pos_x >= 0.0) && (pos_x <= size.x) && (pos_y >= 0.0) && (pos_y <= size.y));
	}

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
						applet.getHintController().addDrawableHint(this);
					hover_notifier.setOnEnter(new HoverEventArgs(pos_x, pos_y));
				}
				hover_notifier.setOnHover(new HoverEventArgs(pos_x, pos_y));
			} else {
				if (hover_entered) {
					hover_entered = false;
					applet.getHintController().removeDrawableHint(this);
					hover_notifier.setOnLeave(new HoverEventArgs(pos_x, pos_y));
				}
			}
		}
	}

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

	protected abstract void draw(PGraphics graphics);

	protected void drawSiblings(PGraphics graphics) {
		for (ADrawable i : drawables) {
			i.drawComponent(graphics, size);
		}
		drawable_notifier.setOnDrawSiblings(graphics);
	}

	public void setPos(double d, double e, double f) {
		pos = new PVectorD(d, e, f);
	}

	public PVectorD getPos() {
		return pos;
	}

	public void setSize(double x, double y, double z) {
		size = new PVectorD(x, y, z);
	}

	public PVectorD getSize() {
		return size;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getColor() {
		return color;
	}

	public void setZoom(double zoom) {
		this.zoom = zoom;
	}

	public double getZoom() {
		return zoom;
	}

	public void setRot(double x, double y, double z) {
		rot = new PVectorD(x, y, z);
	}

	public PVectorD getRot() {
		return rot;
	}

	public void setHorizontalAlignment(EHorizontalAlignment horizontal_alignment) {
		this.horizontal_alignment = horizontal_alignment;
	}

	public EHorizontalAlignment getHorizontalAlignment() {
		return horizontal_alignment;
	}

	public void setVerticalAlignment(EVerticalAlignment vertical_alignment) {
		this.vertical_alignment = vertical_alignment;
	}

	public EVerticalAlignment getVerticalAlignment() {
		return vertical_alignment;
	}

	public void addDrawable(ADrawable drawable) {
		if (drawable != null)
			drawables.add(drawable);
	}

	public void removeDrawable(ADrawable drawable) {
		if (drawables.contains(drawable)) {
			drawables.remove(drawable);
		}
	}

	public void removeAll() {
		drawables.clear();
	}

	public void allowDepthBuffer(boolean b) {
		allow_depth_buffer = b;
	}

	public boolean isDepthBufferAllowed() {
		return allow_depth_buffer;
	}

	public boolean isHovering() {
		return hover_entered;
	}

	public void setVisible(boolean b) {
		visible = b;
	}

	public boolean isVisible() {
		return visible;
	}

	public HoverNotifier getHoverNotifier() {
		return hover_notifier;
	}

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

	public Hint getHint() {
		return hint;
	}

	public DrawableNotifier getDrawableNotifier() {
		return drawable_notifier;
	}

	public void dispose() {
		drawables.clear();
		hover_notifier.removeAllListeners();
		getApplet().getHintController().removeDrawableHint(this);
		if (hint != null) {
			hint.dispose();
			hint = null;
		}
	}
}