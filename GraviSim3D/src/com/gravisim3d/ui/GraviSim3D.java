package com.gravisim3d.ui;

import java.util.Random;
import java.lang.Math;
import com.gravisim3d.core.PVectorD;
import com.gravisim3d.event.ClickEventArgs;
import com.gravisim3d.event.IClickListener;
import com.gravisim3d.event.IDrawableListener;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.event.MouseEvent;

/**
 * GraviSim3D main class
 * 
 * @author Ethem Kurt
 *
 */
public class GraviSim3D extends PApplet {

	/**
	 * Applet
	 */
	private static GraviSim3D applet;

	/**
	 * Background scene
	 */
	private UI bg;

	/**
	 * Universe scene
	 */
	private Universe3D universe;

	/**
	 * UI
	 */
	private UI ui;

	/**
	 * Zoom text
	 */
	private Text zoom_text;

	/**
	 * Main menu
	 */
	private Menu main_menu;

	/**
	 * Actions menu
	 */
	private Menu actions_menu;

	/**
	 * Mode menu
	 */
	private Menu mode_menu;

	/**
	 * Main menu button
	 */
	private Button main_menu_button;

	/**
	 * Action menu items
	 */
	private Button[] action_menu_items = new Button[EGraviSim3DAction.values().length];

	/**
	 * Mode menu items
	 */
	private Button[] mode_menu_items = new Button[EGraviSim3DMode.values().length];

	/**
	 * Hint controller
	 */
	private HintController hint_controller;

	/**
	 * Random generator
	 */
	private Random rand = new Random();

	/**
	 * Planet grow position
	 */
	private PVectorD grow_pos = null;

	/**
	 * Planet growing scale
	 */
	private double growing = 0.0;

	/**
	 * Growing planet color
	 */
	private int grow_color;

	/**
	 * Volume factor
	 */
	public static final double VOLUME_FACTOR = (4.0 / 3.0) * Math.PI;

	// private PVector modify_rotation_point;

	/**
	 * Show create planet
	 */
	private boolean show_create_planet = false;

	/**
	 * Show prediction
	 */
	private boolean show_prediction = false;

	/**
	 * Show effecting lines
	 */
	private boolean show_effecting_lines = false;

	/**
	 * GraviSIm3D action
	 */
	private EGraviSim3DAction action = EGraviSim3DAction.CREATE_PLANET;

	/**
	 * Main entry point
	 * 
	 * @param args
	 *            Command line arguments
	 */
	public static void main(String[] args) {
		applet = new GraviSim3D();
		applet.runSketch(args);
	}

	/**
	 * Get current applet instance
	 * 
	 * @return Current applet instance
	 */
	public static GraviSim3D getApplet() {
		return applet;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see processing.core.PApplet#settings()
	 */
	@Override
	public void settings() {
		super.settings();
		fullScreen(P3D);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see processing.core.PApplet#setup()
	 */
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

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.gravisim3d.event.IDrawableListener#onDrawComponent(processing
			 * .core.PGraphics)
			 */
			@Override
			public void onDrawComponent(PGraphics graphics) {
				//
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.gravisim3d.event.IDrawableListener#onDrawSiblings(processing.
			 * core.PGraphics)
			 */
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
							VOLUME_FACTOR * growing * growing * growing, velocity.x, velocity.z, velocity.z,
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
		main_menu_button = new Button("Menu", 0.0, 0.0, 0.0, 100.0, 40.0, 1.0, EHorizontalAlignment.RIGHT,
				EVerticalAlignment.TOP, 0x7F3F3FFF, 0xFF3F3FFF, 0xFF7F7FFF, 0x7FFF3F3F);
		main_menu_button.setHint("Show menu");
		main_menu_button.getCaption().setPos(35.0, 0.0, 0.1);
		main_menu_button.getCaption().setTextSize(20.0);
		main_menu_button.addDrawable(new ImageBox("Titros_icon.png", 5.0, 0.0, 1.0, 24.0, 24.0, 1.0,
				EHorizontalAlignment.LEFT, EVerticalAlignment.CENTER));

		// menu_button.addDrawable(new Button(20.0, 0.0, 0.0, 100.0, 40.0, 1.0,
		// EHorizontalAlignment.LEFT, EVerticalAlignment.TOP, 0x7F3F3FFF,
		// 0xFF3F3FFF, 0xFF7F7FFF));
		main_menu_button.getClickNotifier().addListener(new IClickListener() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.gravisim3d.event.IClickListener#onClick(com.gravisim3d.event.
			 * ClickEventArgs)
			 */
			@Override
			public void onClick(ClickEventArgs args) {
				main_menu.setVisible(!main_menu.isVisible());
				main_menu_button.setGlowing(main_menu.isVisible());
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.gravisim3d.event.IClickListener#onPress(com.gravisim3d.event.
			 * ClickEventArgs)
			 */
			@Override
			public void onPress(ClickEventArgs args) {
				// System.out.println("press");
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.gravisim3d.event.IClickListener#onRelease(com.gravisim3d.
			 * event.ClickEventArgs)
			 */
			@Override
			public void onRelease(ClickEventArgs args) {
				// System.out.println("release");
			}
		});
		ui.addDrawable(main_menu_button);

		main_menu = new Menu(0.0, 40.0, 0.0, 150.0, 40.0, EHorizontalAlignment.RIGHT, EVerticalAlignment.TOP,
				0x3F3F3F7F, 0x7F3F3FFF, 0xFF3F3FFF, 0xFF7F7FFF, 0x7FFF3F3F, EMenuAlignment.VERTICAL);
		main_menu.setHint("This is the main menu.");
		settings_menu_item = main_menu.createMenuItem("Settings");
		settings_menu_item.setHint("Show settings.\n\nThere are no settings yet.");
		about_menu_item = main_menu.createMenuItem("About");
		about_menu_item.setHint("Show about.\n\nThere is no function yet.");
		exit_menu_item = main_menu.createMenuItem("Exit");
		exit_menu_item.setHint("Close this simulation.");
		exit_menu_item.getClickNotifier().addListener(new IClickListener() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.gravisim3d.event.IClickListener#onClick(com.gravisim3d.event.
			 * ClickEventArgs)
			 */
			@Override
			public void onClick(ClickEventArgs args) {
				exit();
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.gravisim3d.event.IClickListener#onPress(com.gravisim3d.event.
			 * ClickEventArgs)
			 */
			@Override
			public void onPress(ClickEventArgs args) {
				//
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.gravisim3d.event.IClickListener#onRelease(com.gravisim3d.
			 * event.ClickEventArgs)
			 */
			@Override
			public void onRelease(ClickEventArgs args) {
				//

			}
		});
		main_menu.setVisible(false);

		ui.addDrawable(main_menu);

		actions_menu = new Menu(0.0, 0.0, 0.0, 50.0, 40.0, EHorizontalAlignment.CENTER, EVerticalAlignment.BOTTOM,
				0x3F3F3F7F, 0x7F3F3FFF, 0xFF3F3FFF, 0xFF7F7FFF, 0x7FFF3F3F, EMenuAlignment.HORIZONTAL);
		actions_menu.setHint("This is the action menu.\n\nSelect any action you want to execute.");
		action_menu_items = actions_menu.createMenuItems(EGraviSim3DAction.values());
		Planet3D planet1 = new Planet3D(0.0, 0.0, 0.0, 8.0, 0.0, 0.0, 0.0, 0.0, 0xFFFFFF3F);
		planet1.setHorizontalAlignment(EHorizontalAlignment.CENTER);
		planet1.setVerticalAlignment(EVerticalAlignment.CENTER);
		Planet3D planet2 = new Planet3D(10.0, 10.0, 0.0, 4.0, 0.0, 0.0, 0.0, 0.0, 0xFFFF7F00);
		planet2.setHorizontalAlignment(EHorizontalAlignment.CENTER);
		planet2.setVerticalAlignment(EVerticalAlignment.CENTER);
		planet1.addDrawable(planet2);
		int cp_id = EGraviSim3DAction.CREATE_PLANET.compareTo(EGraviSim3DAction.CREATE_PLANET);
		action_menu_items[cp_id].addDrawable(planet1);
		action_menu_items[cp_id].getClickNotifier().addListener(new IClickListener() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.gravisim3d.event.IClickListener#onClick(com.gravisim3d.event.
			 * ClickEventArgs)
			 */
			@Override
			public void onClick(ClickEventArgs args) {
				unglowActionsMenu();
				action_menu_items[EGraviSim3DAction.CREATE_PLANET.compareTo(EGraviSim3DAction.CREATE_PLANET)]
						.setGlowing(true);
				action = EGraviSim3DAction.CREATE_PLANET;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.gravisim3d.event.IClickListener#onPress(com.gravisim3d.event.
			 * ClickEventArgs)
			 */
			@Override
			public void onPress(ClickEventArgs args) {
				//
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.gravisim3d.event.IClickListener#onRelease(com.gravisim3d.
			 * event.ClickEventArgs)
			 */
			@Override
			public void onRelease(ClickEventArgs args) {
				//

			}
		});
		action_menu_items[EGraviSim3DAction.SELECT_PLANET.compareTo(EGraviSim3DAction.CREATE_PLANET)].getClickNotifier()
				.addListener(new IClickListener() {

					/*
					 * (non-Javadoc)
					 * 
					 * @see com.gravisim3d.event.IClickListener#onClick(com.
					 * gravisim3d.event.ClickEventArgs)
					 */
					@Override
					public void onClick(ClickEventArgs args) {
						unglowActionsMenu();
						action_menu_items[EGraviSim3DAction.SELECT_PLANET.compareTo(EGraviSim3DAction.CREATE_PLANET)]
								.setGlowing(true);
						action = EGraviSim3DAction.SELECT_PLANET;
					}

					/*
					 * (non-Javadoc)
					 * 
					 * @see com.gravisim3d.event.IClickListener#onPress(com.
					 * gravisim3d.event.ClickEventArgs)
					 */
					@Override
					public void onPress(ClickEventArgs args) {
						//
					}

					/*
					 * (non-Javadoc)
					 * 
					 * @see com.gravisim3d.event.IClickListener#onRelease(com.
					 * gravisim3d.event.ClickEventArgs)
					 */
					@Override
					public void onRelease(ClickEventArgs args) {
						//

					}
				});
		action_menu_items[EGraviSim3DAction.ROTATE_CAMERA.compareTo(EGraviSim3DAction.CREATE_PLANET)].getClickNotifier()
				.addListener(new IClickListener() {

					/*
					 * (non-Javadoc)
					 * 
					 * @see com.gravisim3d.event.IClickListener#onClick(com.
					 * gravisim3d.event.ClickEventArgs)
					 */
					@Override
					public void onClick(ClickEventArgs args) {
						unglowActionsMenu();
						action_menu_items[EGraviSim3DAction.ROTATE_CAMERA.compareTo(EGraviSim3DAction.CREATE_PLANET)]
								.setGlowing(true);
						action = EGraviSim3DAction.ROTATE_CAMERA;
					}

					/*
					 * (non-Javadoc)
					 * 
					 * @see com.gravisim3d.event.IClickListener#onPress(com.
					 * gravisim3d.event.ClickEventArgs)
					 */
					@Override
					public void onPress(ClickEventArgs args) {
						//
					}

					/*
					 * (non-Javadoc)
					 * 
					 * @see com.gravisim3d.event.IClickListener#onRelease(com.
					 * gravisim3d.event.ClickEventArgs)
					 */
					@Override
					public void onRelease(ClickEventArgs args) {
						//

					}
				});
		action_menu_items[EGraviSim3DAction.TRANSLATE_CAMERA.compareTo(EGraviSim3DAction.CREATE_PLANET)]
				.getClickNotifier().addListener(new IClickListener() {

					/*
					 * (non-Javadoc)
					 * 
					 * @see com.gravisim3d.event.IClickListener#onClick(com.
					 * gravisim3d.event.ClickEventArgs)
					 */
					@Override
					public void onClick(ClickEventArgs args) {
						unglowActionsMenu();
						action_menu_items[EGraviSim3DAction.TRANSLATE_CAMERA.compareTo(EGraviSim3DAction.CREATE_PLANET)]
								.setGlowing(true);
						action = EGraviSim3DAction.TRANSLATE_CAMERA;
					}

					/*
					 * (non-Javadoc)
					 * 
					 * @see com.gravisim3d.event.IClickListener#onPress(com.
					 * gravisim3d.event.ClickEventArgs)
					 */
					@Override
					public void onPress(ClickEventArgs args) {
						//
					}

					/*
					 * (non-Javadoc)
					 * 
					 * @see com.gravisim3d.event.IClickListener#onRelease(com.
					 * gravisim3d.event.ClickEventArgs)
					 */
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
		mode_menu.createMenuItems(EGraviSim3DMode.values());
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see processing.core.PApplet#mousePressed()
	 */
	@Override
	public void mousePressed() {
		super.mousePressed();
		// universe.addPlanet(new Planet3D(10.0, 10.0, new PVector(mouseX,
		// mouseY), new PVector(0.0, 0.0), rand.nextInt() | 0xFF000000));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see processing.core.PApplet#mouseWheel(processing.event.MouseEvent)
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see processing.core.PApplet#keyPressed()
	 */
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

	/**
	 * Create planet from cursor
	 */
	private void createPlanetFromCursor() {
		PVectorD display_pos = new PVectorD(grow_pos.x * universe.getZoom(), grow_pos.y * universe.getZoom(),
				grow_pos.z * universe.getZoom());
		PVectorD velocity = new PVectorD(-((mouseX - display_pos.x - universe.getPos().x) / universe.getZoom()),
				-((mouseY - display_pos.y - universe.getPos().y) / universe.getZoom()), 0.0);
		Planet3D p = new Planet3D(grow_pos.x, grow_pos.y, grow_pos.z, growing,
				VOLUME_FACTOR * growing * growing * growing, velocity.x, velocity.y, velocity.z, grow_color);
		p.setHint("I am a planet!");
		universe.addPhysicalObject(p);

		grow_pos = null;
		growing = 0.0;
		show_create_planet = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see processing.core.PApplet#draw()
	 */
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
				switch (action) {
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
				switch (action) {
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

	/**
	 * Create random stars for background
	 */
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

	/**
	 * Unglow all menu items in the action menu
	 */
	private void unglowActionsMenu() {
		for (Button i : actions_menu.getButtons())
			i.setGlowing(false);
	}

	/**
	 * Get hint controller
	 * 
	 * @return Hint controller
	 */
	public HintController getHintController() {
		return hint_controller;
	}
}