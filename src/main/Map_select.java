package main;

import engine.io.Audio;
import gui.Button;
import gui.Text;
import gui.Texture;
import playing.Playing;


public class Map_select {
	
	private static final int BTN_SIZE_X = 200;
	private static final int BTN_SIZE_Y = 50;
	
	private static final int FONT_SIZE = 32;
	private static final int OUTLINE_THICKNESS = 5;
	
	private static final int ACTION_CLICK = 1;
	private static final int ACTION_HOVER = 2;	
	
	private static Button buttons[];
	private static Texture map_images[];
	private static String map_names[];
	private static Text hint_text;
	private static Texture background;
	
	private static Button btn_back;
	
	private static float[] rect;
	
	private static int selected_map;
		
	//Initialise needed objects and values
	public static void create() {
		selected_map = -1;
		buttons = new Button[2];
		map_images = new Texture[3];
		map_names = new String[3];
		
		background = new Texture("background_small.jpg", 0, 0, 1f, 1f);
		
		btn_back = new Button("Back", 5,530, BTN_SIZE_X / 2,BTN_SIZE_Y, 1);
		
		hint_text = new Text("Select map", 0, 0, FONT_SIZE);
		hint_text.setPosition((Main.window.getWidth() - hint_text.getFont().getTextWidth(hint_text.text)) / 2, 50);
		
		buttons[0] = new Button("Standard", 0,0, BTN_SIZE_X,BTN_SIZE_Y, 1);
		buttons[1] = new Button("Continuous", 0,0,BTN_SIZE_X,BTN_SIZE_Y,1);
		
		int offset_x = 5;
		int offset_y = 180;
		
		map_images[0] = new Texture("maps/level_1.png", offset_x,offset_y,1f,1f);
		map_images[1] = new Texture("maps/level_2.png", offset_x + map_images[0].getWidth() + 20, offset_y, 1f,1f);
		map_images[2] = new Texture("maps/level_3.png", offset_x + ((map_images[0].getWidth() + 20) * 2), offset_y, 1f,1f);
		
		map_names[0] = "Grassy Greens";
		map_names[1] = "Pipe down";
		map_names[2] = "Tricky track";		
	}
	
	//Update
	public static void update() {
		//Iterate through the map images
		for (int i = 0;i<map_images.length;i++) {
			Texture tex = map_images[i];
			if (check_texture_status(tex) == ACTION_HOVER) {
				selected_map = i;
				int new_x = (int)(map_images[selected_map].getX() + (map_images[selected_map].getWidth() / 2) - (buttons[0].getWidth() / 2));
				int new_y = (int)(map_images[selected_map].getY() + (map_images[selected_map].getHeight() / 2) - (buttons[0].getHeight() / 2));
				
				buttons[0].setPosition(new_x,new_y - 40);
				buttons[1].setPosition(new_x, new_y + 40);
				
				rect = new float[] {tex.getX() - OUTLINE_THICKNESS, tex.getY() - OUTLINE_THICKNESS, tex.getWidth() + OUTLINE_THICKNESS * 2, tex.getHeight() + OUTLINE_THICKNESS * 2};
				hint_text = new Text(map_names[i], 0, 0, FONT_SIZE);
				

			}
		}
		if (selected_map == -1 || check_texture_status(map_images[selected_map]) == 0) {
			selected_map = -1;
			rect = null;
			hint_text = new Text("Select map", 0, 0, FONT_SIZE);
		}
		
		hint_text.setPosition((Main.window.getWidth() - hint_text.getFont().getTextWidth(hint_text.text)) / 2, 50);
		
		
		//Start the game on the selected map when button clicked
		if (buttons[0].updateClick()) {

			Main.state = Main.PLAYING;
			
			Audio.stop();
			
			Audio.playLoop(Audio.MSC_GAME);
			Playing.create(Playing.EASY, 1, convert_to_map_name(selected_map));
			
		}
		buttons[1].updateClick();
		
		if (btn_back.updateClick()) {

			Main.state = Main.MAIN_MENU;
			Main_menu.state = Main_menu.MAIN;
		}
	}
		
	//Draws onto the window
	public static void draw() {		
		background.draw();
		
		btn_back.draw();
		
		if (rect != null) {
			Main.window.setColour(0, 0, 0, 1f);
			Main.window.rectangle(rect[0], rect[1], rect[2], rect[3]);
		}
		
		for (Texture tex : map_images) {
			tex.draw();
		}
		
		if (selected_map > -1) {
			buttons[0].draw();
			buttons[1].draw();
		}
		

		Main.window.setColour(0, 0, 0, 1f);
		hint_text.draw();		
	}
	
	//Checks to see if a texture has been clicked/hovered over
	private static int check_texture_status(Texture tex) {
		if (Main.window.getMouseX() > tex.getX() &&
			Main.window.getMouseX() < tex.getX() + tex.getWidth() &&
			Main.window.getMouseY() > tex.getY() &&
			Main.window.getMouseY() < tex.getY() + tex.getHeight()) {
			
			if (Main.window.isMousePressed(Main.window.LEFT_MOUSE)) {
				return ACTION_CLICK;
			} else {
				return ACTION_HOVER;
			}			
		}
		return 0;
		
	}
	
	//Takes a map number and returns the file name of that map
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
	
	public static int getSelectedMap() {
		return selected_map;
	}
	
}
