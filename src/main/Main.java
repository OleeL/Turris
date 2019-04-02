package main;

import org.lwjgl.glfw.GLFW;
import engine.io.Window;

/**
 * @author Team 62
 * 
 * Oliver Legg - sgolegg - 201244658
 *
 */
public class Main {
	
	public static void main(String[] args) {
		// Setting the states:
		final int MAIN_MENU = 0;
		final int PLAYING = 1;
		int state = PLAYING;
		
		// Setting up window settings
		int WIDTH = 800;              // Screen Width
		int HEIGHT = 600;             // Screen Height
		int FPS = 60;                 // Max Frame Rate
		String windowName = "Turris"; // Name of the window
		
		// Creates the game window
		Window window = new Window(WIDTH, HEIGHT, FPS, windowName);
		window.create();
		
		// While the windows isn't closed print to the screen
		while (!window.closed())
		{
			if (window.isUpdating())
			{
				
				window.clear();  // Clears the previous frame
				window.update(); // Start update
				
				// Organises the updating within the states
				switch (state)
				{
					case MAIN_MENU : 
						break;
					case PLAYING :
						// Settings the colour to a transparent white
						window.setColour(255, 255, 255, 0.4f);
						
						// Drawing a grid
						for (int x = 0; x < 80; x++)
							for (int y = 0; y < 60; y++)
								window.rectangle(x*10, y*10, 9, 9);	
						break;
				}
				
				// Finish update
				window.swapBuffers();
			}
		}
	}
}
