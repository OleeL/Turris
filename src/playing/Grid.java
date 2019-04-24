/**
 * @author Team 62
 * 
 * Oliver Legg - sgolegg - 201244658
 *
 */

package playing;

import gui.Texture;
import turrets.*;

import static main.Main.window;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Grid {

	// File names and defining
	public static final String PNG             = ".png";
	public static final String PATH            = "/tiles/";
	public static final String BLANK           = "x";
	public static final String DECORATION	   = "decor_";
	public static final String ACROSS          = "path_as";
	public static final String DOWN            = "path_ds";
	public static final String STOP_BOTTOM     = "path_sb";
	public static final String STOP_TOP        = "path_st";
	public static final String BOTTOM_TO_LEFT  = "path_bl";
	public static final String BOTTOM_TO_RIGHT = "path_br";
	public static final String TOP_TO_LEFT     = "path_tl";
	public static final String TOP_TO_RIGHT    = "path_tr";
	public static final String TOWER_1         = "tower_1";
	public static final String TOWER_2         = "tower_2";
	public static final String TOWER_3         = "tower_3";
	public static final String DOWN_BRIDGE	   = "path_ds_bridge";
	
	private Entity[][] grid;
	public ArrayList<Turret> turrets = new ArrayList<Turret>();
	private int x_tiles, y_tiles;
	private float grid_size;
	public float spawn_x, spawn_y, finish_x, finish_y;
	public int start_tileX, start_tileY;
	private boolean draw_lines = true;
	private char selected_level;
	
	public Grid(String level, char selected_level) {
		this.selected_level = selected_level;
		try {
			File file = new File(level);
			Scanner inputStream;
			inputStream = new Scanner(file);
			while (inputStream.hasNext()) {
					
				String data = inputStream.next();
				String[] values = data.split(",");
				x_tiles = Integer.parseInt(values[0]);
				y_tiles = Integer.parseInt(values[1]);
				grid_size = Float.parseFloat(values[2]);
				spawn_x = Float.parseFloat(values[3]);
				spawn_y = Float.parseFloat(values[4]);
				finish_x = Float.parseFloat(values[5]);
				finish_y = Float.parseFloat(values[6]);
				start_tileX = Integer.parseInt(values[7]);
				start_tileY = Integer.parseInt(values[8]);
				break;
			}
			// Setting up the grid
			grid = new Entity[y_tiles][x_tiles];
			int y = 0;
			while (inputStream.hasNext()) {
				String data = inputStream.next();
				String[] values = data.split(",");
				for(int x = 0; x < x_tiles; x++){
					insert(x, y, values[x]);
				}
				y++;
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		
	}
	
	public void draw() {
		// Drawing the tiles
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				grid[y][x].getTexture().draw();
			}
		}
		
		// Drawing the towers
		for (int turret = 0; turret < turrets.size(); turret++) {
			((Turret) turrets.get(turret)).draw();
		}
		
		// Drawing grid lines
		if (draw_lines) {
			window.setColour(0.2f, 0.2f, 0.2f, 0.5f);
			// Vertical Lines
			for (int x = 0; x < grid[0].length+1; x++) {
				float temp_x = (x*grid_size)+1;
				float y2 = (grid.length)*grid_size;
				window.drawLine(temp_x, 0, temp_x, y2);
			}
			// Horizontal Lines
			for (int y = 0; y < grid.length+1; y++) {
				float x2 = (grid[0].length)*grid_size;
				float temp_y = (y*grid_size);
				window.drawLine(0, temp_y, x2, temp_y);
			}
		}
	}
	
	// inserts non turrets into the board
	public void insert(int x, int y, String tile) {
		// Creates a new entity on the grid
		grid[y][x] = new Entity(
				tile, 
				new Texture(
						PATH+"/" + (selected_level) + "/" +tile+PNG, 
						(int) (x*grid_size), 
						(int) (y*grid_size),
						grid_size / 100,  // The tiles are 100 x 100
						grid_size / 100), // The tiles are 100 x 100
				x*grid_size, 
				y*grid_size);
	}
	
	// inserts a turret into the board
	public void insert(int x, int y, Entity turret, int level) {
		grid[y][x] = turret;
		turrets.add((Turret) turret);
		for (int i = 0; i < level; i++) ((Turret) grid[y][x]).upgrade();
	}
	
	public boolean removeTurret(int x, int y) {
		if (grid[y][x] instanceof Turret) {
			Turret turret = (Turret) getEntity(x, y);
			if (turrets.contains(turret)) {
				System.out.println(true);
				turrets.remove(turret);
				insert(x, y, "x");
				return true;
			}
		}
		return false;
	}

	// Hides the grid lines 
	public void turnOnLines() {
		draw_lines = true;
	}
	
	public void turnOffLines() {
		draw_lines = false;
	}
	
	public float getTileSize() {
		return grid_size;
	}
	
	public String getTile(int x, int y) {
		try {
			return grid[y][x].getName();
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			return "";
		}
	}
	
	public Entity getEntity(int x, int y) {
		try {
			return grid[y][x];
		} catch (Exception e) {
			return null;
		}
	}

	public float getCoordX(double x) {
		return round((float)x-(grid_size/2), grid_size);
	}
	
	public float getCoordY(double y) {
		return round((float)y-(grid_size/2), grid_size);
	}
	
	// rounds a number n to the nearest point p
	public static float round( float n, float p ) {
		float remainder = n % p;
		if    (remainder > p/2) return n + (p - remainder);
		else  return n - remainder;
	}
	
}
