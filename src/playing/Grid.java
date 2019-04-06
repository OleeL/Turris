/**
 * @author Team 62
 * 
 * Oliver Legg - sgolegg - 201244658
 *
 */

package playing;

import gui.Texture;
import static main.Main.window;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.lwjgl.glfw.GLFW;

public class Grid {

	private static final String PNG             = ".png";
	private static final String PATH            = "/tiles/";
	private static final String BLANK           = "x";
	private static final String ACROSS          = "path_as";
	private static final String DOWN            = "path_ds";
	private static final String STOP_BOTTOM     = "path_sb";
	private static final String STOP_TOP        = "path_st";
	private static final String BOTTOM_TO_LEFT  = "path_bl";
	private static final String BOTTOM_TO_RIGHT = "path_br";
	private static final String TOP_TO_LEFT     = "path_tl";
	private static final String TOP_TO_RIGHT    = "path_tr"; 
	
	private Entity[][] grid;
	private int x_tiles, y_tiles;
	private float grid_size;
	private boolean draw_lines = true;
	
	public Grid(int x_tiles, int y_tiles, float grid_size){
		this.x_tiles = x_tiles;
		this.y_tiles = y_tiles;
		this.grid_size = grid_size;
		// Setting up the grid
		grid = new Entity[y_tiles][x_tiles];
		
		// Creates an empty grid
		for (int y = 0; y < y_tiles; y++) {
			for (int x = 0; x < x_tiles; x++){
				grid[y][x] = new Entity(
						"blank", 
						new Texture(
								PATH+BLANK+PNG, 
								(int) (x*grid_size), 
								(int) (y*grid_size),
								grid_size / 100,  // The tiles are 100 x 100
								grid_size / 100), // The tiles are 100 x 100
						x*grid_size, 
						y*grid_size);
			}
		}
		insert(0,0, STOP_TOP);
	}
	
	public Grid(String level) {
		try {
			File file = new File(level);
			Scanner inputStream;
			inputStream = new Scanner(file);
			while (inputStream.hasNext()) {
					
				String data = inputStream.next();
				String[] values = data.split(",");
				x_tiles = Integer.parseInt(values[0]);
				y_tiles = Integer.parseInt(values[1]);
				grid_size = Integer.parseInt(values[2]);
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
		// Hides the grid lines if you press g
		if (window.isKeyReleased(GLFW.GLFW_KEY_G)) { // G
			draw_lines = !draw_lines;
		}
	}
	public void draw() {
		// Drawing the tiles
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				grid[y][x].getTexture().draw();
				
			}
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
	
	public void insert(int x, int y, String tile) {
		if (grid[y][x] == null)
		{
			grid[y][x] = new Entity(
					tile, 
					new Texture(
							PATH+tile+PNG, 
							(int) (x*grid_size), 
							(int) (y*grid_size),
							grid_size / 100,  // The tiles are 100 x 100
							grid_size / 100), // The tiles are 100 x 100
					x*grid_size, 
					y*grid_size);
		}
		else
		{
			grid[y][x].setTexture(
					new Texture(
						PATH+tile+PNG,
						x*((int)grid_size),
						y*((int)grid_size),
						grid_size / 100, // The tiles are 100 x 100
						grid_size / 100  // The tiles are 100 x 100
					)
				);
			grid[y][x].setName(tile);
		}
	}
	
}
