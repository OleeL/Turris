/**
 * @author Team 62
 * 
 * Oliver Legg - sgolegg - 201244658
 *
 */
package playing;

import main.Main;

public class Playing {
	
	private static final String LEVEL_1 = "assets/levels/level_1.csv";
	private static final String LEVEL_2 = "assets/levels/level_2.csv";
	private static final String LEVEL_3 = "assets/levels/level_3.csv";

	public static final int PLAYING = 0;
	public static final int PAUSED  = 1;
	public static int state = PLAYING;
	public static Grid grid;
	public static float gui_open_button_x;
	public static float gui_open_button_y;
	public static float gui_close_button_y;
	
	public static void create(){
		//grid = new Grid(16,12,50);
		grid = new Grid(LEVEL_1);
	}
	
	public static void update(){
		grid.update();		
	}
	
	public static void draw() {
		grid.draw();
	}
	
	public static void gui_create(){
		
	}
	public static void gui_update(){
		
	}
	
	public static void gui_draw(){
		// Open button
		Main.window.setColour(1f, 1f, 1f, 1f);
	}
}
