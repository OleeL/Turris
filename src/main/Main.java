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
		// Screen width and screen height
		int WIDTH = 800;
		int HEIGHT = 600;
		
		// Max frame rate
		int FPS = 60; 
		
		// Name of the window
		String windowName = "Turris";
		// Creates the game window
		Window window = new Window(WIDTH, HEIGHT, FPS, windowName);
		window.create();
		
		// While the windows isn't closed print to the screen
		while (!window.closed())
		{
			if (window.isUpdating())
			{
				
				window.clear(); // Clears the previous frame
				window.update(); // Start update
				window.setColour(255, 255, 255, 0.4f);
				for (int x = 0; x < 80; x++)
					for (int y = 0; y < 60; y++)
						window.rectangle(x*10, y*10, 9, 9);
				
				// Testing to see if input is working
				if (window.isKeyPressed(GLFW.GLFW_KEY_SPACE))
					System.out.println("Hey");
				
				// Printing x and y coordinate on mouse clicks for testing
				if (window.isMousePressed(GLFW.GLFW_MOUSE_BUTTON_LEFT))
				{
					System.out.println(
							window.getMouseX()+", "+window.getMouseY()
					);
				}
				
				// Finish update
				window.swapBuffers();
			}
		}
	}
}
