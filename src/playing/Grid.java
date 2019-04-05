/**
 * @author Team 62
 * 
 * Oliver Legg - sgolegg - 201244658
 *
 */

package playing;

import gui.Texture;
import main.Main;

import org.json.JSONArray;
 

public class Grid {


	private static final String PNG             = ".png";
	private static final String PATH            = "/tiles/";
	private static final String BLANK           = PATH + "blank" + PNG;
	private static final String ACROSS          = PATH + "path_as" + PNG;
	private static final String DOWN            = PATH + "path_ds" + PNG;
	private static final String STOP_BOTTOM     = PATH + "path_sb" + PNG;
	private static final String STOP_TOP        = PATH + "path_st" + PNG;
	private static final String BOTTOM_TO_LEFT  = PATH + "path_bl" + PNG;
	private static final String BOTTOM_TO_RIGHT = PATH + "path_br" + PNG;
	private static final String TOP_TO_LEFT     = PATH + "path_tl" + PNG;
	private static final String TOP_TO_RIGHT    = PATH + "path_tr" + PNG; 
			
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
								"/tiles/blank.png", 
								(int) (x*grid_size), 
								(int) (y*grid_size)), 
						x*grid_size, 
						y*grid_size);
			}
		}
	}
	
	public void draw() {
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				grid[y][x].getTexture().draw();
			}
		}
		
		if (draw_lines) {
			Main.window.setColour(1, 1, 1, 0.4f);
			for (int x = 0; x < grid[0].length+1; x++) {
				float temp_x = Math.min(800, (x*grid_size)+1);
				Main.window.drawLine(temp_x, 0, temp_x, 600);
			}
			for (int y = 0; y < grid.length+1; y++) {
				float temp_y = Math.max(0, (y*grid_size)-1);
				Main.window.drawLine(0, temp_y, 800, temp_y);
			}
		}
	}
	
	public void insert() {
		
	}
	
	
}
