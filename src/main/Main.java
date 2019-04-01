package main;

import org.lwjgl.glfw.GLFW;

import engine.io.Window;

/**
 * @author Team 62
 * 
 * Oliver Legg
 *
 */
public class Main {

	public static void main(String[] args) {
		
		// Creates a window which is 800 by 600.
		Window window = new Window(800, 600, "Turris");
		window.create();
		
		// While the windows isn't closed print to the screen
		while (!window.closed())
		{
			// Start update
			window.update();
			
			// Testing to see if input is working
			if (window.isKeyPressed(GLFW.GLFW_KEY_SPACE))
				System.out.println("Hey");
			
			// Printing x and y coordinate on mouse clicks for testing
			if (window.isMousePressed(GLFW.GLFW_MOUSE_BUTTON_LEFT))
				System.out.println(window.getMouseX()+", "+window.getMouseY());
			
			// Finish update
			window.swapBuffers();
		}

	}

}
