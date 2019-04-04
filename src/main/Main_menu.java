package main;

import gui.Button;

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
	
	public static void create()
	{
		
		buttons[0] = new Button("New Game", 300, 50, 200, 50, NEW_GAME);
		int width = (int) buttons[0].getText().getFont().getFontImageWidth();
		int height = (int) buttons[0].getText().getFont().getFontImageHeight();
		buttons[0].getText().setPosition( 300+(width/2), 50+(height/2));
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
	}
	
}
