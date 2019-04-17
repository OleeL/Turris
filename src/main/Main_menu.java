package main;

import org.lwjgl.glfw.GLFW;

import gui.Button;
import gui.Cloud;
import gui.Texture;
import playing.Playing;

/**
 * @author Team 62
 * 
 * Oliver Legg - sgolegg - 201244658
 * Thomas Coupe - sgtcoupe - 201241037
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
	private static Button[] buttons = new Button[5];
	private static Texture title, background, howtoplay;
	private static Cloud clouds[] = new Cloud[2];

	private static float boxW = 580;
	private static float boxH = 580; 
	private static float boxX = (Main.window.getWidth() / 2) - (boxW/2);
	private static float boxY = (Main.window.getHeight() / 2) - (boxH/2);
	private static Button backToMenu = new Button("Back", 580, 535, 100, 50, MAIN);
	
	
	public static void create(){		
		background = new Texture("background_small.jpg", 0, 0, 1f, 1f);
		title = new Texture("turris_text.png", 283, 25, 1f, 1f);
		howtoplay = new Texture("howtoplay.png", boxX, boxY, 1f, 1f);
		for (int i = 0; i < clouds.length; i++){
			clouds[i] = new Cloud(i+1);
		}
		float b_x = 300;
		float b_y = 150;
		float b_w = 200;
		float b_h = 50;
		float b_yIncrement = 60;
		
		String[] names  = {"New Game", "Load Game","Settings", "How To Play", "Exit"};
		int[]    states = { NEW_GAME,   LOAD_GAME,  SETTINGS,   HELP,          EXIT};
		for (int i = 0; i < buttons.length; i++){
			buttons[i] = new Button(names[i], b_x, b_y, b_w, b_h, states[i]);
			int text_w= (int) buttons[i].getText().getFont().getTextWidth(
					buttons[i].getText().getText());
			int text_h= (int) buttons[i].getText().getFont().getCharHeight();
			buttons[i].getText().setPosition( 
					((b_x+(b_w/2))-(text_w/2)),  // x
			        ((b_y+(b_h/2))-(text_h/2))); // y
			b_y += b_yIncrement;
		}
		
		// Sets the correct position for 'back' button
		b_x = backToMenu.getX()+(backToMenu.getWidth()/2);
		b_y = backToMenu.getY()+(backToMenu.getHeight()/2);
		b_w = backToMenu.getText().getFont().getTextWidth(backToMenu.getName());
		b_h = backToMenu.getText().getFont().getCharHeight();
		backToMenu.getText().setPosition((b_x-(b_w/2)), (b_y-(b_h/2)));
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
				if (Main.window.isKeyReleased(GLFW.GLFW_KEY_ESCAPE)) {
					state = MAIN;
				}
				break;
			case LOAD_GAME:
				if (Main.window.isKeyReleased(GLFW.GLFW_KEY_ESCAPE)) {
					state = MAIN;
				}
				break;
			case HELP:
				if (backToMenu.updateClick()) {
					state = MAIN;
				}
				break;
			case ABOUT:
				if (Main.window.isKeyReleased(GLFW.GLFW_KEY_ESCAPE)) {
					state = MAIN;
				}
				break;
			case SETTINGS:
				if (backToMenu.updateClick()) {
					state = MAIN;
				}
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
				state = MAIN;
				break;
				
			case LOAD_GAME:
				break;
				
			case SETTINGS:
				Main.window.setColour(0f, 0f, 0f, 0.5f);
				Main.window.rectangle(boxX, boxY, boxW, boxH, 20);
				backToMenu.setFontColour(1f, 1f, 1f);
				backToMenu.draw();
				
				break;
				
			case HELP:
				Main.window.setColour(0f, 0f, 0f, 0.5f);
				Main.window.rectangle(boxX, boxY, boxW, boxH, 20);
				howtoplay.draw();
				backToMenu.setFontColour(1f, 1f, 1f);
				backToMenu.draw();
				break;
				
				
		}
	}
	
	public static void button_push(int button)
	{
		state = button;
		switch (button){
			case NEW_GAME:
				Playing.create(Playing.EASY, 1, Playing.LEVEL_1);
				break;
			case EXIT:
				System.exit(-1);
				break;	
		}
	}
	
}
