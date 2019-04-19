package main;

import engine.io.Window;
import playing.Playing;

/**
 * @author Team 62
 * 
 * Oliver Legg - sgolegg - 201244658
 *
 */
public class Main {
	public static Window window;
	
	// Setting the states:
	public static final int MAIN_MENU = 0;
	public static final int PLAYING = 1;
	public static final int SELECT_MAP = 2;
	public static int state = MAIN_MENU;
	
	public static void main(String[] args) {
		
		// Setting up window settings
		int WIDTH = 800;              // Screen Width
		int HEIGHT = 600;             // Screen Height
		int FPS = 60;                 // Max Frame Rate
		String windowName = "Turris"; // Name of the window
		
		// Creates the game window
		window = new Window(WIDTH, HEIGHT, FPS, windowName);
		window.create();
		
		// Creates the main menu
		Main_menu.create();
		
		Map_select.create();
		
		// While the windows isn't closed print to the screen
		while (!window.closed()) {
			if (window.isUpdating()) {
				window.clear();  // Clears the previous frame
				window.update(); // Start update
				
				// Organises the updating within the states
				switch (state){
				
					case MAIN_MENU : 
						Main_menu.update(window.getTime());
						Main_menu.draw();

						break;
						
					case PLAYING :
						Playing.update();
						Playing.draw();
						break;
						
					case SELECT_MAP :
						Map_select.update();
						Map_select.draw();
						break;
				}
				//printMouseCoordsOnClick();
				
				// Finish update
				window.swapBuffers();
			}
		}
	}
	
	public static void printMouseCoordsOnClick() {
		if (window.isMousePressed(window.LEFT_MOUSE))
			System.out.println("("+window.getMouseX()
			+", "+window.getMouseY()+")");
	}
}
