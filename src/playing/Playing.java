/**
 * @author Team 62
 * 
 * Oliver Legg - sgolegg - 201244658
 *
 */
package playing;

import main.Main;
import turrets.*;

public class Playing {
	
	// Different Levels
	private static final String LEVEL_1 = "assets/levels/level_1.csv";
	//private static final String LEVEL_2 = "assets/levels/level_2.csv";
	//private static final String LEVEL_3 = "assets/levels/level_3.csv";

	// Playing states
	public static final int PLAYING = 0;
	public static final int PAUSED  = 1;
	public static int state = PLAYING;
	
	// Graphics
	public static Grid grid;
	public static GUI gui;
	
	// Buttons
	public static final int UNSELECTED   = -1;
	public static final int PAUSE        = 0;
	public static final int TOWER_1      = 1;
	public static final int TOWER_2      = 2;
	public static final int TOWER_3      = 3;
	public static final int GRID_ELEMENT = 99;
	
	public static int selected = UNSELECTED;
	
	// Player stats
	public static int coins = 100;
	public static int round = 1;
		
	public static void create(){
		//grid = new Grid(16,12,50);
		grid = new Grid(LEVEL_1);
		gui  = new GUI();
	}
	
	public static void update(){
		
		// Updates the gui and gets the button if it is pushed
		int btn = gui.update();
		grid.update(); // Updates the grid
		
		// If the gui is clicked and there is something selected: unselect item
		if (gui.isClicked() && selected != UNSELECTED) selected = UNSELECTED;
		
		// If there is something pressed on the GUI, make that the new selection
		if ( btn > -1) selected = btn;
		
		// If there is nothing selected
		switch (selected)
		{
			case UNSELECTED:
				grid.turnOffLines();
				break;
			case TOWER_1:
				place("tower_1", TOWER_1, 1);
				grid.turnOnLines();
				break;
			case TOWER_2:
				place("tower_2", TOWER_2, 1);
				grid.turnOnLines();
				break;
			case TOWER_3:
				grid.turnOnLines();
				break;
			case PAUSE:
				break;
		}
	
		// If the player right clicks, then unselect everything
		if (Main.window.isMousePressed(Main.window.RIGHT_MOUSE)) {
			selected = UNSELECTED;
		}
	}
	
	public static void draw() {
		grid.draw();
		gui.draw();
		
		if (selected != PAUSE && selected != UNSELECTED) {
			if (!gui.isClosed()) {
				gui.close();}
			
			// If you can place the tile in that place, then 
			if (canPlace())Main.window.setColour(0f, 1f, 0f, 0.5f); //show green
			else Main.window.setColour(1f, 0f, 0f, 0.5f); //show red
			
			// Outlines of where the turret will go
			Main.window.rectangle(
					grid.getCoordX(Main.window.getMouseX()),
					grid.getCoordY(Main.window.getMouseY()),
					grid.getTileSize(),
					grid.getTileSize());
		}
	}
	
	public static boolean canPlace() {
		double mx = Main.window.getMouseX();
		double my = Main.window.getMouseY();
		float temp_tile_x = grid.getCoordX(mx);
		float temp_tile_y = grid.getCoordY(my);
		return grid.getTile((int)(temp_tile_x / grid.getTileSize()),
                (int)(temp_tile_y / grid.getTileSize())).equals(Grid.BLANK);
	}
	
	// Place function for placing turrets
	public static void place(String tower, int tower_num, int level) {
		double mx = Main.window.getMouseX();
		double my = Main.window.getMouseY();
		float temp_tile_x = grid.getCoordX(mx);
		float temp_tile_y = grid.getCoordY(my);
		
		Entity turret = null;
		switch (tower_num) {
			case TOWER_1:
				 turret = new Turret_I(
							(float)(temp_tile_x / grid.getTileSize()),
							(float)(temp_tile_y / grid.getTileSize()),
							grid.getTileSize());
				break;
			case TOWER_2:
				 turret = new Turret_II(
							(float)(temp_tile_x / grid.getTileSize()),
							(float)(temp_tile_y / grid.getTileSize()),
							grid.getTileSize());
				break;
			case TOWER_3:
				 turret = new Turret_III(
							(float)(temp_tile_x / grid.getTileSize()),
							(float)(temp_tile_y / grid.getTileSize()),
							grid.getTileSize());
				break;
		}
			
		if (Main.window.isMousePressed(Main.window.LEFT_MOUSE) && 
				!gui.isClicked() &&
				canPlace()) {
			coins -= ((Turret) turret).getCost();
			grid.insert((int)(temp_tile_x / grid.getTileSize()), 
					    (int)(temp_tile_y / grid.getTileSize()), 
					    tower,
					    turret,
					    level);
			selected = UNSELECTED;
		}
	}
}
