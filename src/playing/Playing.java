/**
 * @author Team 62
 * 
 * Oliver Legg - sgolegg - 201244658
 *
 */
package playing;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;

import enemies.Enemy;
import main.Main;
import main.Main_menu;
import turrets.*;

public class Playing {
	
	// Different Levels
	public static final String LEVEL_1 = "assets/levels/level_1.csv";
	public static final String LEVEL_2 = "assets/levels/level_2.csv";
	public static final String LEVEL_3 = "assets/levels/level_3.csv";

	// Playing states
	public static final int PLAYING   = 0;
	public static final int PAUSED    = 1;
	public static final int ROUND_END = 2;
	public static final int WIN       = 3;
	public static final int LOSE      = 4;
	public static int state;
	
	// Graphics
	public static Grid grid;
	public static GUI gui;
	
	// Buttons
	public static final int UNSELECTED = -1;
	public static final int PAUSE      = 0;
	public static final int TOWER_1    = 1;
	public static final int TOWER_2    = 2;
	public static final int TOWER_3    = 3;
	public static final int QUIT       = 99;
	
	// Difficulty
	public static final int EASY   = 1;
	public static final int MEDIUM = 2;
	public static final int HARD   = 3;
	public static String difficulty;
	public static String difficulty_visual;
	public static int selected;
	
	// Player stats (Game Dependent)
	public static int coins;
	public static int round;
	public static int lives;
	
	// Player stats (Information)
	public static int coins_revenue;
	public static int kills;
	public static int arrows_fired;
	public static int buildings_upgraded;
	public static int buildings_built;
	
	// Waves | Rounds
	private static Wave wave;
	private static long time_end;
	private static int spawn_num;
	public static boolean roundEnded;
	
	private static ArrayList<Enemy> enemies;
	private static ArrayList<Arrow> arrows;
	
	private static float selected_range;
		
	public static void create(int difficulty, int round, String level){
		
		Playing.round = round;
		selected = UNSELECTED;
		state = ROUND_END;
		// Coins and lives depends on the difficulty
		switch (difficulty) {
			case HARD:
				coins = 100;
				lives = 1;
				Playing.difficulty = Wave.HARD;
				Playing.difficulty_visual = "Hard";
				break;
			case MEDIUM:
				coins = 250;
				lives = 10;
				Playing.difficulty = Wave.MEDIUM;
				Playing.difficulty_visual = "Medium";
				break;
			default:
				coins = 300;
				lives = 1;
				Playing.difficulty = Wave.EASY;
				Playing.difficulty_visual = "Easy";
				break;
		}
		// Non difficulty specific attributes
		coins_revenue = 0;
		kills = 0;
		arrows_fired = 0;
		buildings_upgraded = 0;
		buildings_built = 0;
		
		// Instantiating for the GUI and grid interface
		grid = new Grid(level);
		gui  = new GUI();

		// Instantiates dependencies for the enemy
		Enemy.find_path(grid.start_tileX, grid.start_tileY);
		enemies = new ArrayList<Enemy>(); // Enemy list
		arrows = new ArrayList<Arrow>();  // Arrow list
		
		// Initialising for the rounds
		wave = new Wave();
		roundEnded = true;
		start_new_round(round);
	}
	
	public static void update(){
		if (state == PLAYING) {
			//Spawning
			long time = System.currentTimeMillis();
			if ( time > time_end && spawn_num < wave.enemies.length) {
				enemies.add(wave.enemies[spawn_num]);
				time_end = time+(long)(wave.spawn_delays[spawn_num++]*1000F);
			}
			if (enemies.isEmpty() && !roundEnded) {
				state = ROUND_END;
				gui.button_round.setName("Start");
				start_new_round(++round);
				roundEnded = true;
			}
			
			// If lives <= 0 you lose
			if (lives <= 0) {
				state = LOSE;
				lives = 0;
			}
			
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
				for (int e = 0; e < enemies.size(); e++) {
					Enemy enemy = enemies.get(e);
					Arrow arrow = grid.turrets.get(turret).shootAt(
							enemy.getCX(),
							enemy.getCY()
						);
					if (arrow != null) {
						arrows.add(arrow);
						arrows_fired++;
					}
				}
			}
			
			// Updates the arrows & checks if the enemies are hit by the arrows
			for (int arrow = 0; arrow < arrows.size(); arrow++) {
				arrows.get(arrow).update();
				for (int e = 0; e < enemies.size(); e++) {
					float x = enemies.get(e).getCX();
					float y = enemies.get(e).getCY();
					float r = enemies.get(e).getRadius();
					if (arrows.get(arrow).collidesWith(x, y, r)) {
						if (enemies.get(e).health < 0) {
							coins += enemies.get(e).getReward();
							coins_revenue += enemies.get(e).getReward();
							kills++;
							enemies.remove(e);
						}
						else {
							enemies.get(e).health-=arrows.get(arrow).getDamage();
						}
						arrows.remove(arrow);
						break;
					}
				}
			}
		}
		// Updates the gui and gets the button if it is pushed
		int btn = gui.update();
		
		if (Main.window.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) selected = UNSELECTED;
		
		// If the gui is clicked and there is something selected: unselect item
		if (gui.isClicked() && selected != UNSELECTED) selected = UNSELECTED;
		
		// If there is something pressed on the GUI, make the new selection
		if ( btn > -1) selected = btn;
		
		// If there is nothing selected
		switch (selected)
		{
			case UNSELECTED:
				grid.turnOffLines();
				break;
			case TOWER_1:
				if (Turret_1.TURRET_COST <= coins) {
					place(TOWER_1, 1);
					grid.turnOnLines();
				} else {
					selected = UNSELECTED;
				}

				break;
			case TOWER_2:
				if (Turret_2.TURRET_COST <= coins) {
					place(TOWER_2, 1);
					grid.turnOnLines();
				} else {
					selected = UNSELECTED;
				}

				break;
			case TOWER_3:
				if (Turret_3.TURRET_COST <= coins) {
					place(TOWER_3, 1);
					grid.turnOnLines();
				} else {
					selected = UNSELECTED;
				}

				break;
			case QUIT:
				Main.state = Main.MAIN_MENU;
				Main_menu.state = Main_menu.MAIN;
				selected = UNSELECTED;
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
			if (canPlace())Main.window.setColour(0f, 1f, 0f, 0.3f);//show green
			else Main.window.setColour(1f, 0f, 0f, 0.5f); //show red
			
			Main.window.circle(true, grid.getCoordX(Main.window.getMouseX()) + (grid.getTileSize() / 2), grid.getCoordY(Main.window.getMouseY()) + (grid.getTileSize() / 2), selected_range, 64);
			// Outlines of where the turret will go
			Main.window.rectangle(
					grid.getCoordX(Main.window.getMouseX()),
					grid.getCoordY(Main.window.getMouseY()),
					grid.getTileSize(),
					grid.getTileSize());
			
		}
		gui.draw();
	}
	
	// Shows wether you can place a tile in that specific place or not.
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
				selected_range = ((Turret) turret).getRange();
				break;
			case TOWER_2:
				turret = new Turret_2(
							(float)(temp_tile_x / grid.getTileSize()),
							(float)(temp_tile_y / grid.getTileSize()),
							grid.getTileSize());
				selected_range = ((Turret) turret).getRange();
				break;
			case TOWER_3:
				turret = new Turret_3(
							(float)(temp_tile_x / grid.getTileSize()),
							(float)(temp_tile_y / grid.getTileSize()),
							grid.getTileSize());
				selected_range = ((Turret) turret).getRange();
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
			buildings_built++;
			selected = UNSELECTED;
		}
	}
	
	public static void start_new_round(int round) {
		time_end = 0;
		spawn_num = 0;
		wave.produceWave(round, difficulty);
		if (wave.win) {
			state = WIN;
		}
		roundEnded = false;
	}
	
	public static void toggle_pause() {
		switch (state) {
			case PAUSED:
				state = PLAYING;
				break;
			case PLAYING:
				state = PAUSED;
				break;			
		}
	}
}