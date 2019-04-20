package main;

import org.lwjgl.glfw.GLFW;

import engine.io.Audio;
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
	public static int state = MAIN_MENU;
	
	public static void main(String[] args) {
		
		// Setting up window settings
		int width = 800;              // Screen Width
		int height = 600;             // Screen Height
		int fps = 250;                 // Max Frame Rate
		boolean vsync = false;        // Vsync settings
		String windowName = "Turris"; // Name of the window
		
		// Creates the game window
		window = new Window(width, height, fps, vsync, windowName);
		window.setIcon("TurrisIcon.png");
		window.create();
		
		// Creates the main menu
		Main_menu.create();
		
		
		
		// While the windows isn't closed print to the screen
		while (!window.closed()) {
			
			// Organises the updating within the states
			if (window.processingLimitReady()) {
				double dt = window.getDelta();
				window.clear();  // Clears the previous frame
				window.update(); // Start update
				switch (state){
				
					case MAIN_MENU : 
						Audio.playLoop(Audio.MSC_MENU);
						Main_menu.update(dt);
						Main_menu.draw();
						break;
				
					case PLAYING :
						Playing.update(dt);
						Playing.draw();
						break;
				}

				// Finish update
				window.swapBuffers();
			}
			
		}
		Audio.stop();
	}
	
	public static void printMouseCoordsOnClick() {
		if (window.isMousePressed(window.LEFT_MOUSE))
			System.out.println("("+window.getMouseX()
			+", "+window.getMouseY()+")");
	}
}