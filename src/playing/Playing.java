/**
 * @author Team 62
 * 
 * Oliver Legg - sgolegg - 201244658
 *
 */
package playing;

public class Playing {
	
	private static final String LEVEL_1 = "assets/levels/level_1.csv";
	private static final String LEVEL_2 = "assets/levels/level_2.csv";
	private static final String LEVEL_3 = "assets/levels/level_3.csv";

	public static final int PLAYING = 0;
	public static final int PAUSED  = 1;
	public static int state = PLAYING;
	public static Grid grid;
	public static GUI gui;
	
	public static void create(){
		//grid = new Grid(16,12,50);
		grid = new Grid(LEVEL_1);
		gui  = new GUI();
	}
	
	public static void update(){
		grid.update();
		gui.update();
	}
	
	public static void draw() {
		grid.draw();
		gui.draw();
	}
	
}
