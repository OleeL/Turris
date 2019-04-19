package main;

import gui.Button;
import gui.Text;
import gui.Texture;
import playing.Playing;
/*TODO
 * Fix bug where starting a new game, exiting to menu and then starting a new game again on the same map will not give the chance to select mode
 * Check that main menu states are changed correctly when going to and from map select
 */

public class Map_select {
	
	private static Button buttons[];
	private static Texture map_images[];
	private static Text hint_text;
	
	private static float[] rect;
	
	private static int selected_map;
	
	
	//Initialise needed objects and values
	public static void create() {
		selected_map = -1;
		buttons = new Button[2];
		map_images = new Texture[3];
		
		hint_text = new Text("Select map", 0, 0, 32);
		hint_text.setPosition((Main.window.getWidth() / 2) - (hint_text.getFont().getTextWidth(hint_text.text) / 2), 50);
		
		buttons[0] = new Button("Standard", 0,0, 200,50, 1);
		buttons[1] = new Button("Continuous", 0,0,200,50,1);
		
		int start_x = 5;
		
		map_images[0] = new Texture("maps/level_1.png", start_x,180,1f,1f);
		map_images[1] = new Texture("maps/level_2.png", start_x + 270, 180, 1f,1f);
		map_images[2] = new Texture("maps/level_3.png", start_x + 540, 180, 1f,1f);
	}
	
	//Checks to see if a texture has been clicked/hovered over
	//0 = Nothing
	//1 = Click
	//2 = Hover
	private static int check_texture_status(Texture tex) {
		if (Main.window.getMouseX() > tex.getX() &&
			Main.window.getMouseX() < tex.getX() + tex.getWidth() &&
			Main.window.getMouseY() > tex.getY() &&
			Main.window.getMouseY() < tex.getY() + tex.getHeight()) {
			
			if (Main.window.isMousePressed(Main.window.LEFT_MOUSE)) {
				return 1;
			} else {
				return 2;
			}
			
		}
		return 0;
		
	}
	
	
	private static String convert_to_map_name(int selected) {
		switch(selected) {
		case 1:
			return Playing.LEVEL_2;
		case 2:
			return Playing.LEVEL_3;
		default:
			return Playing.LEVEL_1;
		}
	}
	
	//Update
	public static void update() {
		//Flag used to store if a map is being hovered over
		boolean flag = false;
		//Iterate through the map images
		for (int i = 0;i<map_images.length;i++) {
			Texture tex = map_images[i];
			switch(check_texture_status(tex)) {
			case 1:
				selected_map = i;
				flag = true;
				break;
			case 2:
				rect = new float[] {tex.getX(), tex.getY(), tex.getWidth(), tex.getHeight()};
				flag = true;
				break;
			}
		}
		if (!flag) {
			rect = null;
			selected_map = -1;
		}
		
		
		//Start the game on the selected map
		if (buttons[0].updateClick()) {
			Main.state = Main.PLAYING;
			Main_menu.state = Main_menu.MAIN;
			Playing.create(Playing.EASY, 1, convert_to_map_name(selected_map));
			selected_map = -1;
		}
		buttons[1].updateClick();
				
	}
	
	//Draws onto the window
	public static void draw() {
		float temp_w = hint_text.getFont().getTextWidth(hint_text.text);
		float temp_h = hint_text.getFont().getCharHeight();
		Main.window.setColour(0.0f, 0.0f, 0.0f, 0.5f);
		Main.window.rectangle(
				(Main.window.getWidth()/2)-(temp_w/2) -10,
				45,
				temp_w + 20,
				temp_h + 10,
				6);
		
		Main.window.setColour(1.0f, 1.0f, 1.0f, 1.0f);
		for (Texture tex : map_images) {
			tex.draw();
		}
		
		if (selected_map > -1) {
			int x = (int)(map_images[selected_map].getX() + (map_images[selected_map].getWidth() / 2) - (buttons[0].getWidth() / 2));
			int y = (int)(map_images[selected_map].getY() + (map_images[selected_map].getHeight() / 2) - (buttons[0].getHeight() / 2));
			
			buttons[0].setPosition(x,y - 40);
			buttons[1].setPosition(x, y + 40);
			buttons[0].draw();
			buttons[1].draw();
		}
		
		if (rect != null) {
			Main.window.setColour(0, 0, 0, 0.2f);
			Main.window.rectangle(rect[0], rect[1], rect[2], rect[3]);
			Main.window.setColour(1f, 1f, 1f, 1f);
		}
		
		hint_text.draw();

		

		
		
	}
}
