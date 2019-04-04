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
	private static Button[] buttons = new Button[1];
	private static Texture background;
	public static void create()
	{
		background = new Texture("turris_text", 0, 500);
		int button_x = 300;
		int button_y = 50;
		int button_w = 200;
		int button_h = 50;
		buttons[0] = new Button("New Game", button_x, button_y, button_w, button_h, NEW_GAME);
		int text_w= (int) buttons[0].getText().getFont().getFontImageWidth();
		int text_h= (int) buttons[0].getText().getFont().getFontImageHeight();
		buttons[0].getText().setPosition( 
				((button_x+(button_w/2))-(text_w/8)),  // x
		        ((button_y+(button_h/2))-(text_h/8))); // y
				
	}
	
	public static void update()
	{
		for (Button button : buttons)
			if (button.updateClick())
				state = button.getState();
	}
	
	public static void draw()
	{
		
		for (Button button : buttons)
			button.draw();
		background.draw();
	}
	
}
