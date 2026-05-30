/**
 * @author Team 62
 * 
 * Oliver Legg - sgolegg - 201244658
 *
 */
package com.team62.turris.playing;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;

import com.team62.turris.enemies.Enemy;
import com.team62.turris.engine.io.Audio;
import com.team62.turris.engine.io.Random;
import com.team62.turris.gui.Texture;
import com.team62.turris.level.handler.Save;
import com.team62.turris.Main;
import com.team62.turris.Main_menu;
import com.team62.turris.playing.waves.Wave;
import com.team62.turris.turrets.*;

/**
 * @author Team 62
 * 
 * Oliver Legg - sgolegg - 201244658
 * Kieran Baker - sgkbaker - 201234727
 * Thomas Coupe - sgtcoupe - 201241037
 */

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
	public static int previous_state;
	
	// Graphics
	public static Grid grid;
	public static GUI gui;
	
	// Buttons
	public static final int UNSELECTED = -1;
	public static final int PAUSE      = 0;
	public static final int TOWER_1    = 1;
	public static final int TOWER_2    = 2;
	public static final int TOWER_3    = 3;
	public static final int SELL       = 96;
	public static final int SAVE       = 97;
	public static final int SETTINGS   = 98;
	public static final int QUIT       = 99;
	public static int selected;
	
	// Difficulty
	public static final int EASY   = 1;
	public static final int MEDIUM = 2;
	public static final int HARD   = 3;
	public static String difficulty;
	public static String difficulty_visual;
	
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
	private static int difficulty_n;
	public static String level;
	private static Wave wave;
	private static int delay;
	private static int spawn_num;
	public static boolean roundEnded;
	public static boolean continuous;
	
	// Enemies and arrows
	private static ArrayList<Enemy> enemies;
	private static ArrayList<Arrow> arrows;
	
	// Gameplay
	private static float selected_range;
	public static double speed_modifier;
	
	// Used for saving the game
	public static Save save;
		
	public static void create(int difficulty, int round, String level){
		// Instantiating
		Playing.difficulty_n = difficulty;
		Playing.level = level;
		Playing.round = round;
		Playing.speed_modifier = 1;
		Main.window.setFPS(60);
		
		selected = UNSELECTED;
		state = ROUND_END;
		
		// Getting the selected level
		char selected_level = level.charAt(level.length()-5);
		
		// Coins and lives depends on the difficulty
		switch (difficulty) {
			case HARD:
				coins = 150;
				lives = 1;
				Playing.difficulty = Wave.HARD;
				Playing.difficulty_visual = "Hard";
				break;
			case MEDIUM:
				coins = 150;
				lives = 3;
				Playing.difficulty = Wave.MEDIUM;
				Playing.difficulty_visual = "Medium";
				break;
			default:
				coins = 150;
				lives = 4;
				Playing.difficulty = Wave.EASY;
				Playing.difficulty_visual = "Easy";
				break;
		}
		Main_menu.volume_sfx.setEnabled(false);
		Main_menu.volume_music.setEnabled(false);
		
		// Statistics reset
		coins_revenue = 0;
		kills = 0;
		arrows_fired = 0;
		buildings_upgraded = 0;
		buildings_built = 0;
		
		// Gameplay variables reset
		speed_modifier = 1;
		
		// Instantiating for the GUI and grid interface
		grid = new Grid(level, selected_level);
		gui  = new GUI();

		// Instantiates dependencies for the enemy
		Enemy.find_path(grid.start_tileX, grid.start_tileY);
		enemies = new ArrayList<Enemy>(); // Enemy list
		arrows = new ArrayList<Arrow>();  // Arrow list
		
		// Used for saving the game
		save = new Save(
				grid.getTileSize(),
				Playing.difficulty_n,
				Playing.level,
				Playing.continuous);
		
		// Initialising for the rounds
		wave = new Wave();
		roundEnded = true;
		start_new_round(round);
	}
	
	public static void update(double dt){
		if (state == PLAYING) {
			//Spawning
			delay--;
			if ( delay <= 0 && spawn_num < wave.enemies.length) {
				enemies.add(wave.enemies[spawn_num]);
				delay = (int) (wave.spawn_delays[spawn_num++]*100);
			}
			
			// Checks if round has ended
			if (spawn_num >= wave.enemies.length-1 &&
					!roundEnded &&
					enemies.isEmpty()) {
				state = ROUND_END;
				gui.rcomplete_fade_away = 2;
				gui.button_round.setName("Start");
				start_new_round(++round);
				save();
				roundEnded = true;
				Audio.play(Audio.SND_ROUND_COMPLETE);
			}
			
			// If lives <= 0 you lose
			if (lives <= 0) {
				Audio.play(Audio.SND_DEFEAT);
				state = LOSE;
				lives = 0;
				save.delete();
				save.write();
			}
			
			grid.update(); // Updates the grid
	
			// Updates the enemies
			for (int i = 0; i < enemies.size(); i++) {
				enemies.get(i).update(dt);
				if (enemies.get(i).reached) {
					
					lives--;
					if (lives > 0) {
						Audio.play(Audio.SND_DAMAGE_TAKEN);
					}
					enemies.remove(i);
				}
			}
			
			// Fires arrows at the enemies 
			for (int turret = 0; turret < grid.turrets.size(); turret++) {
				for (int e = 0; e < enemies.size(); e++) {
					Enemy enemy = enemies.get(e);
					Arrow arrow = grid.turrets.get(turret).aimAt(
							enemy.getCX(),
							enemy.getCY()
						);
					if (arrow != null) {
						arrows.add(arrow);
						arrows_fired++;
					}
				}
			}
		}
		
		if (state == PLAYING || state == ROUND_END) {
			// Updates the arrows & checks if the enemies are hit by the arrows
			for (int arrow = 0; arrow < arrows.size(); arrow++) {
				arrows.get(arrow).update();
				if (arrows.get(arrow).isDestroyed()) {
					arrows.remove(arrow);
				}
				else {
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
								int randomSound = Random.integer(1, 1000);
								if (randomSound == 1) {
									Audio.play(Audio.SND_ENEMY_DEATH);
								}
								else {
									randomSound = Random.integer(1, 2);
									if (randomSound == 1) {
										Audio.play(Audio.SND_ENEMY_DEATH_1);
									}
									else {
										Audio.play(Audio.SND_ENEMY_DEATH_2);
									}
								}
							}
							else {
								Audio.play(Audio.SND_ENEMY_HIT_2);
								enemies.get(e).health-=arrows.get(arrow).getDamage();
							}
							arrows.remove(arrow);
							break;
						}
					}
				}
			}
		}
		
		// Updates the gui and gets the button if it is pushed
		int btn = gui.update();
		
		if (Main.window.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
			selected = UNSELECTED;
		}
		
		// If the gui is clicked and there is something selected: unselect item
		if (gui.isClicked()) {
			if(selected != UNSELECTED) {
				selected = UNSELECTED;
			} else if(gui.saveOpen()) {
				gui.display_save_prompt();
			} else if(gui.quitOpen()) {
				gui.display_quit_prompt();
			}

		}
		
		// If there is something pressed on the GUI, make the new selection
		if ( btn > -1) selected = btn;
		
		if(gui.isClicked() && gui.settingsOpen() && selected != SETTINGS) {
				gui.close_settings_gui();
				state = previous_state;
			}
		
		switch (selected)
		{
			case UNSELECTED:
				grid.turnOffLines();
				break;
			case TOWER_1:
				if (Turret_1.TURRET_COST <= coins) {
					place(TOWER_1, 0);
					grid.turnOnLines();
				} else {
					selected = UNSELECTED;
				}

				break;
			case TOWER_2:
				if (Turret_2.TURRET_COST <= coins) {
					place(TOWER_2, 0);
					grid.turnOnLines();
				} else {
					selected = UNSELECTED;
				}

				break;
			case TOWER_3:
				if (Turret_3.TURRET_COST <= coins) {
					place(TOWER_3, 0);
					grid.turnOnLines();
				} else {
					selected = UNSELECTED;
				}
				break;
			case SELL:
				sell();
				break;
			case SAVE:
				if (!gui.saveOpen()) {
					previous_state = state;
					state = PAUSED;
				}
				else {
					state = previous_state;
				}
				save.write();
				gui.display_save_prompt();
				selected = UNSELECTED;
				break;
			case SETTINGS:
				if (!gui.settingsOpen()) {
					previous_state = state;
					state = PAUSED;
				}
				else {
					state = previous_state;
				}
				gui.toggle_settings_gui();
				selected = UNSELECTED;
				break;
			case QUIT:
				if (!gui.quitOpen()) {
					previous_state = state;
					state = PAUSED;
				}
				else {
					state = previous_state;
				}
				gui.display_quit_prompt();
				selected = UNSELECTED;
				//Audio.stop(false);
				break;

		}
	
		// If the player right clicks, then unselect everything
		if (Main.window.isMousePressed(Main.window.RIGHT_MOUSE)) {
			selected = UNSELECTED;
			float grid_size = grid.getTileSize();
			int x = (int) (grid.getCoordX(Main.window.getMouseX()) / grid_size);
			int y = (int)(grid.getCoordY(Main.window.getMouseY()) / grid_size);
			
			Entity tile = grid.getEntity(x, y);
			//Turret upgrade system
			if (tile instanceof Turret) {
				Turret turret = (Turret) tile;
				int turretLevel = turret.getTurretLevel()+1;
				int cost = turret.getUpgradeCost();
				if (coins >= cost) {
					if (turret.upgrade()) {
						String textureName = tile.getName().substring(
								0, tile.getName().length() - 1);
						turret.setTexture(
								new Texture(
										textureName + turretLevel + ".png", 
										(int)(x * grid_size), 
										(int)(y * grid_size), 
										(grid_size / 100),
										(grid_size / 100)));
						turret.setName(textureName + turretLevel);
						coins -= cost;
						buildings_upgraded += 1;
						if (state == ROUND_END) {
							save();
						}
						Audio.play(Audio.SND_TURRET_UPGRADE);
					}
				}
			
			}
		}
	}
	
	public static void draw() {
		double mx = Main.window.getMouseX();
		double my = Main.window.getMouseY();
		grid.draw();
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw();}
		
		for (int i = 0; i < arrows.size(); i++) {
			arrows.get(i).draw();}
		
		// Drawing for selling turrets
		if (selected == SELL) {
			if (!gui.isClosed()) gui.close();

			int x = (int) (grid.getCoordX(mx)/grid.getTileSize());
			int y = (int) (grid.getCoordY(my)/grid.getTileSize());
			
			// If you can sell the turret, show green
			if (grid.getEntity(x, y) instanceof Turret)
				Main.window.setColour(0f, 1f, 0f, 0.3f); //show green
			else 
				Main.window.setColour(1f, 0f, 0f, 0.5f); //show red
			// Outlines what is being deleted
			Main.window.rectangle(
					grid.getCoordX(mx),
					grid.getCoordY(my),
					grid.getTileSize(),
					grid.getTileSize());
			
		} // Drawing for placing turrets
		else if (selected != PAUSE && selected != UNSELECTED) {
			if (!gui.isClosed()) gui.close();
			
			// If you can place the tile in that place, then 
			if (canPlace())
				Main.window.setColour(0f, 1f, 0f, 0.3f);//show green
			else 
				Main.window.setColour(1f, 0f, 0f, 0.5f);//show red
			
			Main.window.circle(true, 
					grid.getCoordX(mx) + (grid.getTileSize() / 2), 
					grid.getCoordY(my) + (grid.getTileSize() / 2), 
					selected_range, 
					64);
			// Outlines of where the turret will go
			Main.window.rectangle(
					grid.getCoordX(mx),
					grid.getCoordY(my),
					grid.getTileSize(),
					grid.getTileSize());
			
		}
		gui.draw();
	}
	
	// Shows whether you can place a tile in that specific place or not.
	public static boolean canPlace() {
		double mx = Main.window.getMouseX();
		double my = Main.window.getMouseY();
		float temp_tile_x = grid.getCoordX(mx);
		float temp_tile_y = grid.getCoordY(my);
		String tile = grid.getTile((int)(temp_tile_x / grid.getTileSize()),(int)(temp_tile_y / grid.getTileSize()));
		if (tile.equals(Grid.BLANK) || tile.contains(Grid.DECORATION)) return true;
		return false;
	}
	
	// Place function for placing turrets
	public static void place(int tower_num, int level) {
		int turret_cost = 0;
		double mx = Main.window.getMouseX();
		double my = Main.window.getMouseY();
		float temp_tile_x = grid.getCoordX(mx);
		float temp_tile_y = grid.getCoordY(my);
		switch (tower_num) {
			case TOWER_1:
				selected_range = Turret_1.TURRET_RANGE;
				turret_cost = Turret_1.TURRET_COST;
				break;
			case TOWER_2:
				selected_range = Turret_2.TURRET_RANGE;
				turret_cost = Turret_2.TURRET_COST;
				break;
			case TOWER_3:
				selected_range = Turret_3.TURRET_RANGE;
				turret_cost = Turret_3.TURRET_COST;
				break;
		}
			
		if (Main.window.isMousePressed(Main.window.LEFT_MOUSE) && 
				!gui.isClicked() &&
				canPlace() &&
				coins >= turret_cost){

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
			coins -= ((Turret) turret).getCost();
			grid.insert((int)(temp_tile_x / grid.getTileSize()), 
					    (int)(temp_tile_y / grid.getTileSize()),
					    turret,
					    level);
			buildings_built++;
			selected = UNSELECTED;
			if (state == ROUND_END) {
				save();
			}
			Audio.play(Audio.SND_TURRET_PLACE);
		}
	}
	
	// Function for selling / removing turrets
	public static void sell() {
		double mx = Main.window.getMouseX();
		double my = Main.window.getMouseY();
		int x = (int) (grid.getCoordX(mx)/grid.getTileSize());
		int y = (int) (grid.getCoordY(my)/grid.getTileSize());
		if (Main.window.isMousePressed(Main.window.LEFT_MOUSE) && 
				!gui.isClicked() &&
				hasTurret(x, y)){

			int value = ((Turret) grid.getEntity(x, y)).getValue();
			if (grid.removeTurret(x, y)) {
				coins += value;
			}
			selected = UNSELECTED;
			if (state == ROUND_END) {
				save();
			}
			Audio.play(Audio.SND_TURRET_PLACE);
		}
	}
	
	public static boolean hasTurret(int x, int y) {
		if (grid.getEntity(x, y) instanceof Turret) {
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static void start_new_round(int round) {
		spawn_num = 0;
		wave.produceWave(round, difficulty, continuous);
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
	
	public static void save() {
		save.keepVariables(
				round,
				coins,
				lives,
				coins_revenue,
				kills,
				arrows_fired,
				buildings_upgraded,
				buildings_built,
				grid.turrets
				);
	}
	
	public static void load(
			int difficulty,
			int round,
			String level,
			boolean cont,
			ArrayList<Entity> turret,
			int coi,
			int liv,
			int rev,
			int k,
			int af,
			int bu,
			int bb) {
		continuous = cont;
		create(difficulty, round, level);
		for (int i = 0; i < turret.size(); i++) {
			grid.insert(
					(int) (turret.get(i).getX()),
					(int) (turret.get(i).getY()), 
					turret.get(i), 
					0);
		}
		coins = coi;
		lives = liv;
		coins_revenue = rev;
		kills = k;
		arrows_fired = af;
		buildings_upgraded = bu;
		buildings_built = bb;
		start_new_round(round);
		save();
		if (lives <= 0){
			state = LOSE;
		}
	}
}