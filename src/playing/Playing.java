/**
 * @author Team 62
 * 
 * Oliver Legg - sgolegg - 201244658
 *
 */
package playing;

import java.util.ArrayList;
import enemies.Enemy;
import main.Main;
import turrets.*;

public class Playing {
	
	// Different Levels
	public static final String LEVEL_1 = "assets/levels/level_1.csv";
	public static final String LEVEL_2 = "assets/levels/level_2.csv";
	public static final String LEVEL_3 = "assets/levels/level_3.csv";

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
	
	// Difficulty
	public static final int EASY   = 1;
	public static final int MEDIUM = 2;
	public static final int HARD   = 3;
	
	public static int selected = UNSELECTED;
	
	// Player stats
	public static int coins;
	public static int round;
	public static int lives;
	
	// Waves | Rounds
	private static Wave wave;
	private static long time_end;
	private static int spawn_num;
	
	private static ArrayList<Enemy> enemies;
	private static ArrayList<Arrow> arrows;
		
	public static void create(int difficulty, int round, String level){
		
		Playing.round = round;

		// Coins and lives depends on the difficulty
		switch (difficulty) {
			case HARD:
				coins = 100;
				lives = 1;
				break;
			case MEDIUM:
				coins = 250;
				lives = 10;
				break;
			default:
				coins = 300;
				lives = 20;
				break;
		}
		
		// Instantiating for the GUI and grid interface
		grid = new Grid(level);
		gui  = new GUI();

		// Instantiates dependencies for the enemy
		Enemy.find_path(grid.start_tileX, grid.start_tileY);
		enemies = new ArrayList<Enemy>(); // Enemy list
		arrows = new ArrayList<Arrow>();  // Arrow list
		
		// Initialising for the rounds
		wave = new Wave();
		time_end = 0;
		spawn_num = 0;
		wave.produceWave(round, Wave.EASY);
	}
	
	public static void update(){
		
		//Spawning
		long time = System.currentTimeMillis();
		if ( time > time_end && spawn_num < wave.enemies.length) {
			enemies.add(wave.enemies[spawn_num]);
			time_end = time + ((long) (wave.spawn_delays[spawn_num++]*1000F));
		}
		// Updates the gui and gets the button if it is pushed
		int btn = gui.update();
		grid.update(); // Updates the grid

		// Updates the enemies
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).update();
			if (enemies.get(i).reached) {
				lives--;
				enemies.remove(i);
			}
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
				float x = enemies.get(enemy).getCX();
				float y = enemies.get(enemy).getCY();
				float r = enemies.get(enemy).getRadius();
				if (arrows.get(arrow).collidesWith(x, y, r)) {
					if (enemies.get(enemy).health < 0) {
						coins += enemies.get(enemy).getReward();
						enemies.remove(enemy);
					}
					else {
						enemies.get(enemy).health -= arrows.get(arrow).getDamage();
					}
					arrows.remove(arrow);
					break;
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
		gui.draw();
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
				 turret = new Turret_1(
							(float)(temp_tile_x / grid.getTileSize()),
							(float)(temp_tile_y / grid.getTileSize()),
							grid.getTileSize());
				break;
			case TOWER_2:
				 turret = new Turret_2(
							(float)(temp_tile_x / grid.getTileSize()),
							(float)(temp_tile_y / grid.getTileSize()),
							grid.getTileSize());
				break;
			case TOWER_3:
				 turret = new Turret_3(
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