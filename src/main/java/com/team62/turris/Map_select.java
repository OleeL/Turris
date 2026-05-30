package com.team62.turris;

import com.team62.turris.engine.io.Audio;
import com.team62.turris.gui.Button;
import com.team62.turris.gui.Text;
import com.team62.turris.gui.Texture;
import com.team62.turris.playing.Playing;

/**
 * @author Team 62
 * 
 * Kieran Baker - sgkbaker - 201234727
 */

public class Map_select {
	
	//Setup constants for ui
	private static final int BTN_SIZE_X = 200;
	private static final int BTN_SIZE_Y = 50;
	
	private static final int FONT_SIZE = 32;
	private static final int OUTLINE_THICKNESS = 5;
	
	//Represents when a UI element is clicked or hovered over
	private static final int ACTION_CLICK = 1;
	private static final int ACTION_HOVER = 2;	
	
	//Stores the names and textures of the maps
	private static Texture map_images[];
	private static String map_names[];
	
	//Used to display info about the currently selected map
	private static Text hint_text;
	
	//Game background
	private static Texture background;
	
	//Buttons
	private static Button btn_standard;
	private static Button btn_continuous;
	private static Button btn_back;
	
	//Stores size/positional data for a rectangle
	private static float[] rect;
	
	private static int selected_map;
		
	//Initialise needed objects and values
	public static void create() {
		selected_map = -1;
		map_images = new Texture[3];
		map_names = new String[3];
		
		background = new Texture("background_small.jpg", 0, 0, 1f, 1f);
		
		//Set the hint text and align it
		hint_text = new Text("Select map", 0, 0, FONT_SIZE);
		hint_text.setPosition((Main.window.getWidth() - hint_text.getFont().getTextWidth(hint_text.text)) / 2, 50);
		
		//Create the buttons
		btn_standard = new Button("Standard", 0,0,BTN_SIZE_X, BTN_SIZE_Y, 1);
		btn_continuous = new Button("Continuous", 0,0,BTN_SIZE_X,BTN_SIZE_Y,1);
		btn_back = new Button("Back", 5,530, BTN_SIZE_X / 2,BTN_SIZE_Y, 1);
		
		int offset_x = 5;
		int offset_y = 180;
		
		//Store the map textures
		map_images[0] = new Texture("maps/level_1.png", offset_x,offset_y,1f,1f);
		map_images[1] = new Texture("maps/level_2.png", offset_x + map_images[0].getWidth() + 20, offset_y, 1f,1f);
		map_images[2] = new Texture("maps/level_3.png", offset_x + ((map_images[0].getWidth() + 20) * 2), offset_y, 1f,1f);
		
		map_names[0] = "Grassy Greens";
		map_names[1] = "Sand Dunes";
		map_names[2] = "Tricky track";		
	}
	
	//Update
	public static void update() {
		//Iterate through the map images
		for (int i = 0;i<map_images.length;i++) {
			Texture tex = map_images[i];
			//Checks to see if the user is hovering over one of the map textures
			if (check_texture_status(tex) == ACTION_HOVER) {
				selected_map = i;
				int new_x = (int)(map_images[selected_map].getX() + (map_images[selected_map].getWidth() / 2) - (btn_standard.getWidth() / 2));
				int new_y = (int)(map_images[selected_map].getY() + (map_images[selected_map].getHeight() / 2) - (btn_standard.getHeight() / 2));
				
				//Move the buttons to the texture the user is hovering over
				btn_standard.setPosition(new_x,new_y - 40);
				btn_continuous.setPosition(new_x, new_y + 40);
				
				rect = new float[] {tex.getX() - OUTLINE_THICKNESS, tex.getY() - OUTLINE_THICKNESS, tex.getWidth() + OUTLINE_THICKNESS * 2, tex.getHeight() + OUTLINE_THICKNESS * 2};
				hint_text = new Text(map_names[i], 0, 0, FONT_SIZE);
				

			}
		}
		//Reset when user is not hovering over one of the maps
		if (selected_map == -1 || check_texture_status(map_images[selected_map]) == 0) {
			selected_map = -1;
			rect = null;
			hint_text = new Text("Select map", 0, 0, FONT_SIZE);
		}
		
		hint_text.setPosition((Main.window.getWidth() - hint_text.getFont().getTextWidth(hint_text.text)) / 2, 50);
		
		
		//Start the game on the selected map when button clicked
		if (btn_standard.updateClick()) {
			startSelectedMap(false);
		} else if (btn_continuous.updateClick()) {
			startSelectedMap(true);
		}
		
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
			btn_standard.draw();
			btn_continuous.draw();
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
	
	public static int getSelectedMap() {
		return selected_map;
	}
	
	//Takes the currently selected map and starts a new game on that map
	public static void startSelectedMap(boolean continuous) {

		Main.state = Main.PLAYING;
		
		Audio.stop(false);		
		Audio.playLoop(Audio.MSC_GAME);
		
		String name = Playing.LEVEL_1;
		int difficulty = Playing.EASY;
		
		switch(selected_map) {
		case 1:
			name = Playing.LEVEL_2;
			difficulty = Playing.MEDIUM;
			break;
		case 2:
			name = Playing.LEVEL_3;
			difficulty = Playing.HARD;
			break;
		}
		Playing.continuous = continuous;
		Playing.create(difficulty, 1, name);
		
	}
	
}
