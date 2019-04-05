/**
 * @author Team 62
 * 
 * Oliver Legg - sgolegg - 201244658
 *
 */
package playing;

public class Playing {
	public static Grid grid;
	
	public static void create(){
		grid = new Grid(8,6,100);
	}
	
	public static void update(){
		
	}
	
	public static void draw() {
		grid.draw();
	}
}
