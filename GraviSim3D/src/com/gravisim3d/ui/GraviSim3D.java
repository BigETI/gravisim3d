package com.gravisim3d.ui;

import java.util.Random;
import java.lang.Math;
import com.gravisim3d.core.FontLoader;
import com.gravisim3d.core.PVectorD;
import com.gravisim3d.event.ClickEventArgs;
import com.gravisim3d.event.IClickListener;
import com.gravisim3d.event.IDrawableListener;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.event.MouseEvent;

public class GraviSim3D extends PApplet {

	private static GraviSim3D applet;

	// UI
	private UI bg;
	private Universe3D universe;
	private UI ui;

	private Text zoom_text;
	private Menu menu;
	private Menu actions_menu;
	private Menu mode_menu;
	private Button menu_button;
	private Button[] action_menu_item = new Button[EGraviSim3DAction.values().length];

	// Hint controller
	private HintController hint_controller;

	private Random rand = new Random();

	private PVectorD grow_pos = null;

	private double growing = 0.0;

	private int grow_color;

	private double volume_factor = (4.0 / 3.0) * Math.PI;

	// private PVector modify_rotation_point;

	private boolean show_create_planet = false;

	private boolean show_prediction = false;

	private boolean show_effecting_lines = false;

	private EGraviSim3DAction mode = EGraviSim3DAction.CREATE_PLANET;

	// private PGraphics temporary_graphics;

	public static void main(String[] args) {
		applet = new GraviSim3D();
		applet.runSketch(args);
	}

	public static GraviSim3D getApplet() {
		return applet;
	}

	@Override
	public void settings() {
		super.settings();
		fullScreen(P3D);
	}

	@Override
	public void setup() {
		super.setup();
		Button settings_menu_item;
		Button about_menu_item;
		Button exit_menu_item;
		PVectorD constraint = new PVectorD((double) pixelWidth, (double) pixelHeight, 1.0);
		// temporary_graphics = createGraphics((int) constraint.x, (int)
		// constraint.y, P3D);
		universe = new Universe3D(constraint.x, constraint.y, 10.0);
		universe.getDrawableNotifier().addListener(new IDrawableListener() {

			@Override
			public void onDrawComponent(PGraphics graphics) {
				//
			}

			@Override
			public void onDrawSiblings(PGraphics graphics) {
				if (show_prediction || show_effecting_lines) {
					for (APhysical i : universe.getPhysicalObjects()) {
						if (i instanceof Planet3D) {
							if (show_prediction)
								((Planet3D) i).drawPrediction(graphics, universe.getPhysicalObjects(),
										universe.getGravitationalConstant());
							if (show_effecting_lines)
								((Planet3D) i).drawEffectingLines(graphics, universe.getPhysicalObjects());
						}
					}
				}
				if (show_create_planet) {
					/*
					 * PVector display_pos = new PVector((grow_pos.x *
					 * universe.getZoom()) + universe.getPos().x, (grow_pos.y *
					 * universe.getZoom()) + universe.getPos().y, (grow_pos.z *
					 * universe.getZoom()) + universe.getPos().z); //display_pos
					 * = new PVector(grow_pos.x + universe.getPos().x,
					 * grow_pos.y + universe.getPos().y, grow_pos.z +
					 * universe.getPos().z); PVector velocity = new
					 * PVector(-((mouseX - display_pos.x - universe.getPos().x)
					 * / universe.getZoom()), -((mouseY - display_pos.y -
					 * universe.getPos().y) / universe.getZoom()), 0.0);
					 * Planet3D p = new Planet3D(display_pos.x, display_pos.y,
					 * display_pos.z, growing, volume_factor * growing * growing
					 * * growing, velocity.x, velocity.y, velocity.z,
					 * grow_color); p.setSize(growing * universe.getZoom(),
					 * growing * universe.getZoom(), growing *
					 * universe.getZoom());
					 * //p.showPlanetInfo(universe.showingPlanetsInfo());
					 * //p.showPrediction(universe.showingPrediction());
					 * universe.getGraphics().beginDraw();
					 * p.drawPrediction(universe.getGraphics(),
					 * universe.getPhysicalObjects(),
					 * universe.getGravitationalConstant());
					 * p.drawComponent(universe.getGraphics(),
					 * universe.getSize()); universe.getGraphics().noStroke();
					 * universe.getGraphics().fill(0xFF);
					 * universe.getGraphics().line(display_pos.x, display_pos.y,
					 * display_pos.z, mouseX, mouseY, 0.0);
					 * image(universe.getGraphics(), 0, 0);
					 * universe.getGraphics().endDraw();
					 */

					PVectorD velocity = new PVectorD(
							-((mouseX - grow_pos.x - universe.getPos().x) / universe.getZoom()),
							-((mouseY - grow_pos.y - universe.getPos().y) / universe.getZoom()), 0.0);
					Planet3D p = new Planet3D(grow_pos.x, grow_pos.y, grow_pos.z, growing,
							volume_factor * growing * growing * growing, velocity.x, velocity.z, velocity.z,
							grow_color);
					p.drawPrediction(graphics, universe.getPhysicalObjects(), universe.getGravitationalConstant());
					p.drawComponent(graphics, universe.getSize());
				}
			}
		});
		bg = new UI(constraint.x, constraint.y);
		ui = new UI(constraint.x, constraint.y);
		hint_controller = new HintController();
		ui.addDrawable(hint_controller);
		zoom_text = new Text("", 20.0, 20.0, 0.0, 12.0, EHorizontalAlignment.LEFT, EVerticalAlignment.TOP, 0xFFFFFF00);
		// ui.addDrawable(universe);
		ui.addDrawable(zoom_text);
		menu_button = new Button("Menu", 0.0, 0.0, 0.0, 100.0, 40.0, 1.0, EHorizontalAlignment.RIGHT,
				EVerticalAlignment.TOP, 0x7F3F3FFF, 0xFF3F3FFF, 0xFF7F7FFF, 0x7FFF3F3F);
		menu_button.setHint("Show menu");
		menu_button.getCaption().setPos(35.0, 0.0, 0.1);
		menu_button.getCaption().setTextSize(20.0);
		menu_button.addDrawable(new ImageBox("Titros_icon.png", 5.0, 0.0, 1.0, 24.0, 24.0, 1.0,
				EHorizontalAlignment.LEFT, EVerticalAlignment.CENTER));

		// menu_button.addDrawable(new Button(20.0, 0.0, 0.0, 100.0, 40.0, 1.0,
		// EHorizontalAlignment.LEFT, EVerticalAlignment.TOP, 0x7F3F3FFF,
		// 0xFF3F3FFF, 0xFF7F7FFF));
		menu_button.getClickNotifier().addListener(new IClickListener() {

			@Override
			public void onClick(ClickEventArgs args) {
				menu.setVisible(!menu.isVisible());
				menu_button.setGlowing(menu.isVisible());
			}

			@Override
			public void onPress(ClickEventArgs args) {
				// System.out.println("press");
			}

			@Override
			public void onRelease(ClickEventArgs args) {
				// System.out.println("release");
			}
		});
		ui.addDrawable(menu_button);

		menu = new Menu(0.0, 40.0, 0.0, 150.0, 40.0, EHorizontalAlignment.RIGHT, EVerticalAlignment.TOP, 0x3F3F3F7F,
				0x7F3F3FFF, 0xFF3F3FFF, 0xFF7F7FFF, 0x7FFF3F3F, EMenuAlignment.VERTICAL);
		menu.setHint("This is the main menu.");
		settings_menu_item = menu.createMenuItem("Settings");
		settings_menu_item.setHint("Show settings.\n\nThere are no settings yet.");
		about_menu_item = menu.createMenuItem("About");
		about_menu_item.setHint("Show about.\n\nThere is no function yet.");
		exit_menu_item = menu.createMenuItem("Exit");
		exit_menu_item.setHint("Close this simulation.");
		exit_menu_item.getClickNotifier().addListener(new IClickListener() {

			@Override
			public void onClick(ClickEventArgs args) {
				exit();
			}

			@Override
			public void onPress(ClickEventArgs args) {
				//
			}

			@Override
			public void onRelease(ClickEventArgs args) {
				//

			}
		});
		menu.setVisible(false);

		ui.addDrawable(menu);

		actions_menu = new Menu(0.0, 0.0, 0.0, 50.0, 40.0, EHorizontalAlignment.CENTER, EVerticalAlignment.BOTTOM,
				0x3F3F3F7F, 0x7F3F3FFF, 0xFF3F3FFF, 0xFF7F7FFF, 0x7FFF3F3F, EMenuAlignment.HORIZONTAL);
		actions_menu.setHint("This is the action menu.\n\nSelect any action you want to execute.");
		EGraviSim3DAction[] vals = EGraviSim3DAction.values();
		for (int i = 0; i < vals.length; i++)
			action_menu_item[i] = vals[i].createMenuItem(actions_menu);
		Planet3D planet1 = new Planet3D(0.0, 0.0, 0.0, 8.0, 0.0, 0.0, 0.0, 0.0, 0xFFFFFF3F);
		planet1.setHorizontalAlignment(EHorizontalAlignment.CENTER);
		planet1.setVerticalAlignment(EVerticalAlignment.CENTER);
		Planet3D planet2 = new Planet3D(10.0, 10.0, 0.0, 4.0, 0.0, 0.0, 0.0, 0.0, 0xFFFF7F00);
		planet2.setHorizontalAlignment(EHorizontalAlignment.CENTER);
		planet2.setVerticalAlignment(EVerticalAlignment.CENTER);
		planet1.addDrawable(planet2);
		int cp_id = EGraviSim3DAction.CREATE_PLANET.compareTo(EGraviSim3DAction.CREATE_PLANET);
		action_menu_item[cp_id].addDrawable(planet1);
		action_menu_item[cp_id].getClickNotifier().addListener(new IClickListener() {

			@Override
			public void onClick(ClickEventArgs args) {
				unglowActionsMenu();
				action_menu_item[EGraviSim3DAction.CREATE_PLANET.compareTo(EGraviSim3DAction.CREATE_PLANET)]
						.setGlowing(true);
				mode = EGraviSim3DAction.CREATE_PLANET;
			}

			@Override
			public void onPress(ClickEventArgs args) {
				//
			}

			@Override
			public void onRelease(ClickEventArgs args) {
				//

			}
		});
		action_menu_item[EGraviSim3DAction.SELECT_PLANET.compareTo(EGraviSim3DAction.CREATE_PLANET)].getClickNotifier()
				.addListener(new IClickListener() {

					@Override
					public void onClick(ClickEventArgs args) {
						unglowActionsMenu();
						action_menu_item[EGraviSim3DAction.SELECT_PLANET.compareTo(EGraviSim3DAction.CREATE_PLANET)]
								.setGlowing(true);
						mode = EGraviSim3DAction.SELECT_PLANET;
					}

					@Override
					public void onPress(ClickEventArgs args) {
						//
					}

					@Override
					public void onRelease(ClickEventArgs args) {
						//

					}
				});
		action_menu_item[EGraviSim3DAction.ROTATE_CAMERA.compareTo(EGraviSim3DAction.CREATE_PLANET)].getClickNotifier()
				.addListener(new IClickListener() {

					@Override
					public void onClick(ClickEventArgs args) {
						unglowActionsMenu();
						action_menu_item[EGraviSim3DAction.ROTATE_CAMERA.compareTo(EGraviSim3DAction.CREATE_PLANET)]
								.setGlowing(true);
						mode = EGraviSim3DAction.ROTATE_CAMERA;
					}

					@Override
					public void onPress(ClickEventArgs args) {
						//
					}

					@Override
					public void onRelease(ClickEventArgs args) {
						//

					}
				});
		action_menu_item[EGraviSim3DAction.TRANSLATE_CAMERA.compareTo(EGraviSim3DAction.CREATE_PLANET)]
				.getClickNotifier().addListener(new IClickListener() {

					@Override
					public void onClick(ClickEventArgs args) {
						unglowActionsMenu();
						action_menu_item[EGraviSim3DAction.TRANSLATE_CAMERA.compareTo(EGraviSim3DAction.CREATE_PLANET)]
								.setGlowing(true);
						mode = EGraviSim3DAction.TRANSLATE_CAMERA;
					}

					@Override
					public void onPress(ClickEventArgs args) {
						//
					}

					@Override
					public void onRelease(ClickEventArgs args) {
						//

					}
				});
		/*
		 * Button button = actions_menu.createMenuItem(""); button.setHint("1");
		 * button = actions_menu.createMenuItem(""); button.setHint("2"); button
		 * = actions_menu.createMenuItem(""); button.setHint("3"); button =
		 * actions_menu.createMenuItem(""); button.setHint("4"); button =
		 * actions_menu.createMenuItem(""); button.setHint("5"); button =
		 * actions_menu.createMenuItem(""); button.setHint("6"); button =
		 * actions_menu.createMenuItem(""); button.setHint("7"); button =
		 * actions_menu.createMenuItem(""); button.setHint("8"); button =
		 * actions_menu.createMenuItem(""); button.setHint("9"); button =
		 * actions_menu.createMenuItem(""); button.setHint("10");
		 */

		ui.addDrawable(actions_menu);

		mode_menu = new Menu(0.0, 0.0, 0.0, 50.0, 40.0, EHorizontalAlignment.LEFT, EVerticalAlignment.CENTER,
				0x3F3F3F7F, 0x7F3F3FFF, 0xFF3F3FFF, 0xFF7F7FFF, 0x7FFF3F3F, EMenuAlignment.VERTICAL);
		mode_menu.setHint("This is the mode menu\n\nSelect any mode for your needs");
		/*
		 * EGraviSim3DMode[] m_vals = EGraviSim3DMode.values(); for (int i = 0;
		 * i < m_vals.length; i++) action_menu_item[i] =
		 * m_vals[i].createMenuItem(mode_menu);
		 */

		ui.addDrawable(mode_menu);

		// Random rand = new Random();
		// universe.addPhysicalObject(new Planet3D(200.0, 200.0, 0.0, 10.0,
		// 100.0, -10.0, 0.0, 0.0, 0xFF0000FF));
		// universe.addPhysicalObject(new Planet3D(200.0, 300.0, 0.0, 10.0,
		// 10.0, 100.0, 0.0, 0.0, 0xFFFF00FF));

		createRandomStars();
	}

	@Override
	public void mousePressed() {
		super.mousePressed();
		// universe.addPlanet(new Planet3D(10.0, 10.0, new PVector(mouseX,
		// mouseY), new PVector(0.0, 0.0), rand.nextInt() | 0xFF000000));
	}

	@Override
	public void mouseWheel(MouseEvent event) {
		super.mouseWheel(event);
		int c = event.getCount();
		if (c > 0) {
			for (int i = 0; i < event.getCount(); i++)
				universe.setZoom(universe.getZoom() + universe.getZoom());
		} else {
			c = -c;
			for (int i = 0; i < c; i++)
				universe.setZoom(universe.getZoom() * 0.5);
		}
		// System.out.println("" + universe.scale);
	}

	@Override
	public void keyPressed() {
		super.keyPressed();
		// System.out.println(keyCode);
		switch (keyCode) {
		case 147: // case DELETE: broken?
			universe.clear();
			break;
		case BACKSPACE:
			universe.removeLast();
			break;
		case 76: // L
			show_effecting_lines = !show_effecting_lines;
			break;
		case 73: // I
			// universe.togglePlanetsInfo();
			break;
		case 80: // P
			show_prediction = !show_prediction;
			break;
		case 97: // F1
			ui.setVisible(!ui.isVisible());
			break;
		}
	}

	void createPlanetFromCursor() {
		PVectorD display_pos = new PVectorD(grow_pos.x * universe.getZoom(), grow_pos.y * universe.getZoom(),
				grow_pos.z * universe.getZoom());
		PVectorD velocity = new PVectorD(-((mouseX - display_pos.x - universe.getPos().x) / universe.getZoom()),
				-((mouseY - display_pos.y - universe.getPos().y) / universe.getZoom()), 0.0);
		Planet3D p = new Planet3D(grow_pos.x, grow_pos.y, grow_pos.z, growing,
				volume_factor * growing * growing * growing, velocity.x, velocity.y, velocity.z, grow_color);
		p.setHint("I am a planet!");
		universe.addPhysicalObject(p);

		grow_pos = null;
		growing = 0.0;
		show_create_planet = false;
	}

	@Override
	public void draw() {
		super.draw();
		// Planet3D p;
		// background(0);
		hint_controller.setPos(mouseX, mouseY, 200.0);
		zoom_text.setText("Zoom: x" + universe.getZoom()
				+ "\n\nGenerate planet (growing): Keep mouse pressing\nRelease planet: Release mouse\nSet velocity vector: Move mouse while pressing\nRemove last planet: Backspace\nRemove all planets: Del\nToggle lines: L\nToggle planets info: I\nToggle orbit prediction: P"); // <>//
		// PVector display_pos;
		background(0);
		if (keyPressed) {
			switch (keyCode) {
			case UP:
				universe.setPos(universe.getPos().x, universe.getPos().y + 10.0, universe.getPos().z);
				break;
			case DOWN:
				universe.setPos(universe.getPos().x, universe.getPos().y - 10.0, universe.getPos().z);
				break;
			case LEFT:
				universe.setPos(universe.getPos().x + 10.0, universe.getPos().y, universe.getPos().z);
				break;
			case RIGHT:
				universe.setPos(universe.getPos().x - 10.0, universe.getPos().y, universe.getPos().z);
				break;
			}
		}
		if (mousePressed) {
			switch (mouseButton) {
			case RIGHT:
				switch (mode) {
				case CREATE_PLANET:
					if (grow_pos == null) {
						grow_pos = new PVectorD((mouseX - universe.getPos().x) / universe.getZoom(),
								(mouseY - universe.getPos().y) / universe.getZoom(), 0.0);
						grow_color = (rand.nextInt() & 0x00FFFFFF) | 0xFF000000;
					}
					growing += 0.1 / universe.getZoom();
					show_create_planet = true;
					break;
				case SELECT_PLANET:
					break;
				case ROTATE_CAMERA:
					break;
				case TRANSLATE_CAMERA:
					break;
				}
				break;
			case LEFT:
				if (grow_pos != null)
					createPlanetFromCursor();
				switch (mode) {
				case CREATE_PLANET:
					break;
				case SELECT_PLANET:
					break;
				case ROTATE_CAMERA:
					break;
				case TRANSLATE_CAMERA:
					break;
				}

				/*
				 * if (modify_rotation_point == null) { modify_rotation_point =
				 * new PVector(mouseX / universe.getZoom(), mouseY /
				 * universe.getZoom()); }
				 */
				break;
			}
		} else if (grow_pos != null)
			createPlanetFromCursor();
		bg.getGraphics().ortho();
		ui.getGraphics().ortho();
		bg.drawMain();
		universe.drawMain();
		/*
		 * if (show_prediction) { universe.getGraphics().beginDraw(); for
		 * (APhysical i : universe.getPhysicalObjects()) { if (i instanceof
		 * Planet3D) { ((Planet3D) i).drawPrediction(universe.getGraphics(),
		 * universe.getPhysicalObjects(), universe.getGravitationalConstant());
		 * } } universe.getGraphics().endDraw(); image(universe.getGraphics(),
		 * 0, 0); }
		 */
		ui.drawMain();
	}

	private void createRandomStars() {
		int brightnes;
		int c;
		bg.removeAll();
		for (int i = 0; i < 1000; i++) {
			brightnes = rand.nextInt(0x200);
			if (brightnes > 0xFF)
				c = 0xFF00 | (0x1FF - brightnes);
			else
				c = brightnes << 8;
			bg.addDrawable(new Star(rand.nextInt((int) bg.getSize().x.doubleValue()),
					rand.nextInt((int) bg.getSize().y.doubleValue()), 0.0, 0xFFFF0000 | c));
		}
	}

	private void unglowActionsMenu() {
		for (Button i : actions_menu.getButtons())
			i.setGlowing(false);
	}

	public HintController getHintController() {
		return hint_controller;
	}
}