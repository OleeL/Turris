/**
 * @author Team 62
 * 
 * Oliver Legg - sgolegg - 201244658
 *
 */
package playing;

import java.util.ArrayList;
import enemies.Enemy;
import enemies.Enemy_1;
import main.Main;
import main.Main_menu;
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
	public static final int QUIT         = 98;
	public static final int GRID_ELEMENT = 99;
	
	public static int selected = UNSELECTED;
	
	// Player stats
	public static int coins = 1000;
	public static int round = 1;
	

	private static ArrayList<Enemy> enemies;
	private static ArrayList<Arrow> arrows;
		
	public static void create(){
		grid  = new Grid(LEVEL_1);
		gui   = new GUI();

		Enemy.find_path(grid.start_tileX, grid.start_tileY);
		arrows = new ArrayList<Arrow>();
		enemies = new ArrayList<Enemy>();
		enemies.add(new Enemy_1(grid.spawn_x, grid.spawn_y, grid.getTileSize())); 
		
	}
	
	public static void update(){
		
		// Updates the gui and gets the button if it is pushed
		int btn = gui.update();
		grid.update(); // Updates the grid

		// Updates the enemies
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).update();
			if (enemies.get(i).reached) enemies.remove(i);
		}
		
		// Fires arrows at the enemies 
		for (int turret = 0; turret < grid.turrets.size(); turret++) {
			for (int enemy = 0; enemy < enemies.size(); enemy++) {
				Enemy e = enemies.get(enemy);
				Arrow arrow = grid.turrets.get(turret).shootAt(
						e.getCX(),
						e.getCY()
					);
				if (arrow != null) {
					arrows.add(arrow);
				}
			}
		}
		
		// Updates the arrows & checks if the enemies are hit by the arrows
		for (int arrow = 0; arrow < arrows.size(); arrow++) {
			arrows.get(arrow).update();
			for (int enemy = 0; enemy < enemies.size(); enemy++) {
				if (arrows.get(arrow).collidesWith(
						enemies.get(enemy).getCX(), 
						enemies.get(enemy).getCY(), 
						enemies.get(enemy).getRadius())) {
					if (enemies.get(enemy).health < 0) {
						enemies.remove(enemy);
					}
					else {
						enemies.get(enemy).health -= arrows.get(arrow).getDamage();
					}
					arrows.remove(arrow);
				}
			}
		}
				
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
				place(TOWER_1, 1);
				grid.turnOnLines();
				break;
			case TOWER_2:
				place(TOWER_2, 1);
				grid.turnOnLines();
				break;
			case TOWER_3:
				place(TOWER_3, 1);
				grid.turnOnLines();
				break;
			case QUIT:
				Main.state = Main.MAIN_MENU;
				selected = UNSELECTED;
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
		
		for (int i = 0; i < enemies.size(); i++)
			enemies.get(i).draw();
		for (int i = 0; i < arrows.size(); i++)
			arrows.get(i).draw();
		
		if (selected != PAUSE && selected != UNSELECTED) {
			if (!gui.isClosed()) gui.close();
			
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
	public static void place(int tower_num, int level) {
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
				canPlace() &&
				coins >= ((Turret) turret).getCost()){
			coins -= ((Turret) turret).getCost();
			grid.insert((int)(temp_tile_x / grid.getTileSize()), 
					    (int)(temp_tile_y / grid.getTileSize()), 
					    turret.getName(),
					    turret,
					    level);
			selected = UNSELECTED;
		}
	}
}