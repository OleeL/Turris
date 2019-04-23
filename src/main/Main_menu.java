package main;

import org.lwjgl.glfw.GLFW;
import engine.io.Audio;
import gui.Button;
import gui.Cloud;
import gui.Texture;
import level.handler.Load;
import playing.Playing;
import gui.Slider;

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
	private static int counter = 0;

	private static float boxW = 580;
	private static float boxH = 580; 
	private static float boxX = (Main.window.getWidth() / 2) - (boxW/2);
	private static float boxY = (Main.window.getHeight() / 2) - (boxH/2);
	private static Button backToMenu = new Button("Back", 580, 535, 100, 50, MAIN);
	private static Button vsync = new Button("Vsync", 150, 400, 170, 60, MAIN);
	private static Button mute = new Button("Mute", 480 ,400, 170, 60, MAIN);
	public static Slider volume_sfx = new Slider("Sound Effects:", 500, 50, 150, 125, 50);
	public static Slider volume_music = new Slider("Music: ",500, 50, 150, 225, 50);
	public static Slider max_fps = new Slider("FPS:", 500, 50, 150, 325, 500);
	
	
	public static void create() {
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
		
		String[]names ={"New Game","Load Game","Settings","How To Play","Exit"};
		int[]   states={ NEW_GAME,  LOAD_GAME,  SETTINGS,  HELP,         EXIT };
		
		for (int i = 0; i < buttons.length; i++){
			buttons[i] = new Button(names[i], b_x, b_y, b_w, b_h, states[i]);
			b_y += b_yIncrement;
		}
		
		Map_select.create();
		
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
				Map_select.update();
				if (Main.window.isKeyReleased(GLFW.GLFW_KEY_ESCAPE)) {
					state = MAIN;
				}
				break;
			case LOAD_GAME:
				load_game();
				state = MAIN;
				Main.state = Main.PLAYING;
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
				volume_music.setEnabled(true);
				volume_sfx.setEnabled(true);
				max_fps.setEnabled(true);
				
				// Vsync
				if(vsync.updateClick()) {
					Main.window.toggleVsync();
				}
				if (Main.window.getVsync()){
					vsync.setButtonColour(0.0f, 1.0f, 0.0f);
				}
				else{
					vsync.setButtonColour(0.0f, 0.0f, 0.0f);
				}
				
				// Back button
				if (backToMenu.updateClick()) {
					state = MAIN;
				}
				
				// Updates the sliders
				updateSliders();
				break;
		}
		
	}
	
	public static void updateSliders() {
		// Updating the text in the slider (and the interaction)
		float sw = volume_sfx.getSliderWidth();
		float sm = volume_sfx.getMaxWidth();
		
		volume_sfx.update("Sound Effects: "+(int)((sw/sm)*100));
		sw = volume_music.getSliderWidth();
		sm = volume_music.getMaxWidth();
		volume_music.update("Music: "+(int)((sw/sm)*100));
		Audio.updateVolume();
		sw = max_fps.getSliderWidth();
		sm = 250;
		int range = 220;
		int fps = (int)(range*(sw/(2*sm)) + 30);
		max_fps.update("FPS: "+fps);
		Main.window.setFPS(fps);
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
				Map_select.draw();
				break;
				
			case LOAD_GAME:
				Main.window.setColour(0f, 0f, 0f, 0.5f);
				Main.window.rectangle(boxX, boxY, boxW, boxH, 20);
				backToMenu.setFontColour(1f, 1f, 1f);
				backToMenu.draw();
				break;
				
			case SETTINGS:
				Main.window.setColour(0f, 0f, 0f, 0.5f);
				Main.window.rectangle(boxX, boxY, boxW, boxH, 20);
				volume_sfx.draw();
				volume_music.draw();
				max_fps.draw();
				backToMenu.setFontColour(1f, 1f, 1f);
				vsync.setFontColour(1f, 1f, 1f);
				mute.setFontColour(1f, 1f, 1f);
				mute.draw();
				vsync.draw();
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
			case EXIT:
				Audio.destroy();
				System.exit(-1);
				break;	
		}
	}
	
	public static void load_game() {
		Load game = new Load();
		game.load();
		String level = "assets/levels/"+game.level+".csv";
		Playing.load(
				game.difficulty, 
				game.round, 
				level,
				game.continuousMode,
				game.turrets,
				game.coins,
				game.lives,
				game.revenue,
				game.kills,
				game.arrows_fired,
				game.b_upgraded,
				game.b_built);
	}
	
}
