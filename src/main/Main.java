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
						window.setColour(255, 255, 255, .25f);

						float margin_y = 25;
						float box_w = 500;
						float box_h = 75;
						float box_x = (WIDTH/2)-(box_w/2);
						float box_y = 100;
						float box_corner = 10;
						
						for (int i = 0; i < 4; i++)
						{
							window.DrawRoundRect(
									box_x, 
									margin_y + (box_y*i),
									box_w,
									box_h,
									box_corner);
						}
						
						// Drawing a grid
						//for (int x = 0; x < 80; x++)
							//for (int y = 0; y < 60; y++)
								//window.rectangle(x*10, y*10, 9, 9);	
						break;
				}
				if (window.isMousePressed(0))
					System.out.println("("+window.getMouseX()
					+", "+window.getMouseY()+")");
				// Finish update
				window.swapBuffers();
			}
		}
	}
}