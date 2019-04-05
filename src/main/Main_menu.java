package main;

import gui.Button;
import gui.Texture;

/**
 * @author Team 62
 * 
 * Oliver Legg - sgolegg - 201244658
 *
 */
public class Main_menu {

	public static final int MAIN = 0;
	public static final int NEW_GAME = 1;
	public static final int LOAD_GAME = 2;
	public static final int SETTINGS = 3;
	public static final int HELP = 4;
	public static final int ABOUT = 5;
	public static final int EXIT = 6;
	public static int state = 0;
	private static Button[] buttons = new Button[4];
	private static Texture title, background;
	
	public static void create(){
		background = new Texture("background_small.jpg", 0, 0);
		title = new Texture("turris_text.png", 283, 25);
		
		int b_x = 300;
		int b_y = 150;
		int b_w = 200;
		int b_h = 50;
		int b_yIncrement = 60;
		
		String[] names  = {"New Game", "Load Game", "Settings", "Exit"};
		int[]    states = { NEW_GAME,   LOAD_GAME,   SETTINGS,   EXIT};
		for (int i = 0; i < 4; i++){
			buttons[i] = new Button(names[i], b_x, b_y, b_w, b_h, states[i]);
			int text_w= (int) buttons[i].getText().getFont().getTextWidth(
					buttons[i].getText().getText());
			int text_h= (int) buttons[i].getText().getFont().getCharHeight();
			buttons[i].getText().setPosition( 
					((b_x+(b_w/2))-(text_w/2)),  // x
			        ((b_y+(b_h/2))-(text_h/2))); // y
			b_y += b_yIncrement;
		}		
	}
	
	public static void update()
	{
		for (Button button : buttons) {
			if (button.updateClick()) {
				state = button.getState();
			}
		}
	}
	
	public static void draw()
	{
		background.draw();
		for (Button button : buttons) {
			button.draw();
		}
		title.draw();
	}
	
}
