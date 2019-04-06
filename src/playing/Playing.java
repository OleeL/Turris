/**
 * @author Team 62
 * 
 * Oliver Legg - sgolegg - 201244658
 *
 */
package playing;

import main.Main;

public class Playing {
	
	// Different Levels
	private static final String LEVEL_1 = "assets/levels/level_1.csv";
	private static final String LEVEL_2 = "assets/levels/level_2.csv";
	private static final String LEVEL_3 = "assets/levels/level_3.csv";

	// Playing states
	public static final int PLAYING = 0;
	public static final int PAUSED  = 1;
	public static int state = PLAYING;
	
	// Graphics
	public static Grid grid;
	public static GUI gui;
	
	// Buttons
	public static final int UNSELECTED = -1;
	public static final int PAUSE      = 0;
	public static final int TOWER_1    = 1;
	public static final int TOWER_2    = 2;
	public static final int TOWER_3    = 3;
	public static int selected = UNSELECTED;
		
	public static void create(){
		//grid = new Grid(16,12,50);
		grid = new Grid(LEVEL_1);
		gui  = new GUI();
	}
	
	public static void update(){
		grid.update();
		int btn = gui.update();
		if ( btn > -1) selected = btn;
		switch (selected)
		{
			case TOWER_1:
				break;
			case TOWER_2:
				break;
			case TOWER_3:
				break;
			case PAUSE:
				break;
		}
	
		if (Main.window.isMousePressed(Main.window.RIGHT_MOUSE)) {
			selected = UNSELECTED;
		}
	}
	
	public static void draw() {
		grid.draw();
		gui.draw();
		
		double mx = Main.window.getMouseX();
		double my = Main.window.getMouseY();
		if (selected != PAUSE && selected != UNSELECTED) {
			Main.window.rectangle(
					round((float)mx-(grid.getTileSize()/2), grid.getTileSize()),
					round((float)my-(grid.getTileSize()/2), grid.getTileSize()),
					grid.getTileSize(),
					grid.getTileSize());
		}
	}
	
	// This function is global because it's really useful in all parts of the game
	public static float round( float n, float p ) {
		float remainder = n % p;
		if    (remainder > p/2) return n + (p - remainder);
		else  return n - remainder;
	}
	
}
