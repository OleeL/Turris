package main;

import gui.Button;
import gui.Cloud;
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
	private static Cloud clouds[] = new Cloud[2];
	
	
	public static void create(){
		background = new Texture("background_small.jpg", 0, 0, 1f, 1f);
		title = new Texture("turris_text.png", 283, 25, 1f, 1f);
		for (int i = 0; i < clouds.length; i++){
			clouds[i] = new Cloud(i+1);
		}
		int b_x = 300;
		int b_y = 150;
		int b_w = 200;
		int b_h = 50;
		int b_yIncrement = 60;
		
		String[] names  = {"New Game", "Load Game", "How To Play", "Exit"};
		int[]    states = { NEW_GAME,   LOAD_GAME,   HELP,          EXIT};
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
	
	public static void update(double dt)
	{
		for (Cloud cloud : clouds) cloud.update(dt);
		switch (state){
			case MAIN:
				for (Button button : buttons) {
					if (button.updateClick()) {
						button_push(button.getState());
					}
				}
				break;
				
			case NEW_GAME:
				break;
				
			case LOAD_GAME:
				break;
				
			case SETTINGS:
				break;
				
		}
		
	}
	
	public static void draw()
	{
		background.draw();
		for (Cloud cloud : clouds) cloud.draw();
		
		switch(state){
				
			case MAIN:
				title.draw();
				for (Button button : buttons) button.draw();
				title.draw();
				break;
				
			case NEW_GAME:
				Main.state = Main.PLAYING;
				break;
				
			case LOAD_GAME:
				break;
				
			case SETTINGS:
				break;
				
				
		}
	}
	
	public static void button_push(int button)
	{
		state = button;
		switch (button){
			case EXIT:
				System.exit(-1);			
		}
	}
	
}
