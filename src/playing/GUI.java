/**
 * 
 */
package playing;

import static org.lwjgl.opengl.GL11.GL_POLYGON;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import engine.io.Audio;
import gui.Button;
import gui.Text;
import gui.Texture;
import main.Main;
import main.Main_menu;
import settings.io.Save;
import turrets.Turret_1;
import turrets.Turret_2;
import turrets.Turret_3;

/**
 * @author Team 62
 * Oliver Legg - sgolegg - 201244658
 * Kieran Baker - sgkbaker - 201234727
 * Thomas Coupe - sgtcoupe - 201241037
 *
 */
public class GUI {

	// x, y, w and h of the side panel
	private float x = Main.window.getWidth();
	private float y = 0;
	private float w = 85.7142857f;
	private float h = Main.window.getHeight();
	
	// Button that opens and closes the GUI
	private float open_button_w = 25;
	private float open_button_h = 50;
	private float open_button_x = Main.window.getWidth() - open_button_w;
	private float open_button_y;
	private float open_button_radius = 6;
	private float open_button_image_rotation = 180;
	private float open_button_xmargin = 5;
	private float open_button_ymargin = 13;
	private Texture open_button_image = new Texture(
			"gui/open_button.png",
			open_button_x + open_button_xmargin,
			open_button_y + open_button_ymargin,
			.08f,
			.08f),
					game_saved = new Texture(
			"game_saved.png",
			(x/2)-(400/2)+20,//200
			250, 
			1f,
			1f), 
					save_progress = new Texture(
			"save_progress.png",
			220,
			250,
			1f,
			1f);
	
	// Final screen (winning or losing)
	private float final_w = 500;
	private float final_h = 500;
	private float final_x = (Main.window.getWidth()/2)-(final_w/2);
	private float final_y = (Main.window.getHeight()/2)-(final_h/2);
	private float final_r = 6;
	
	private Text turret_info = new Text("Price:", 596,480,18);
	private boolean showCost = false;
	
	// Winning Text & Losing Text
	private Text win_text  = new Text("Congratulations! You win!", 0, 0, 18);
	private Text lose_text = new Text("Unlucky! You lose!", 0, 0, 18);
	private Text[] final_stats = {
			new Text("Difficulty: ", 0, 0, 15),
			new Text("You made it to round: ", 0, 0, 15),
			new Text("Lives remaining: ", 0, 0, 15),
			new Text("Total revenue: ", 0, 0, 15),
			new Text("Kills: ",0,0,15),
			new Text("Arrows Fired: ", 0, 0, 15),
			new Text("Buildings Built: ", 0, 0, 15),
			new Text("Buildings Upgrades: ", 0, 0, 15)};
	
	// Button that returns to the main menu after winning or losing
	private Button button_quit = new Button(
			"Quit",
			(Main.window.getWidth()/2)-(100/2),
			(Main.window.getHeight()/2) + (final_h/2) - 100,
			100,
			35,
			Playing.QUIT);
	
	// Button that stops and starts the rounds
	public Button button_round = new Button(
			"Start",
			10f, 
			Main.window.getHeight()-30f, 
			100f, 
			25f, 
			1);
	
	// Button to change speed of game
	private Button button_speed = new Button(
			"1x",
			0f,
			0f,
			35f,
			45f,
			2);
	
	// Statistics GUI panel variables
	private float stats_w = 150f;
	private float stats_h = 100f;
	private float stats_x = Main.window.getWidth() - stats_w;
	private float stats_y = Main.window.getHeight() - stats_h;
	private Text text_coins;
	private Text text_round;
	private Text text_lives;
	private int text_size = 20;

	// Button variables
	private final float BUTTON_SIZE = w;
	private GUIButton[] buttons;
	private boolean showSettings = false, showSavePrompt = false, showQuitPrompt = false;
	public Button quit = new Button("Quit", 200, 340, 100, 50, 1);
	public Button cont = new Button("Continue", 400, 340, 100, 50, 1);
	public Button saveQuit = new Button("Save & Quit", 435, 340, 100, 50, 1); 

	private boolean closed = true;
	private boolean guiClicked = false;
	
	// Colours
	private static final float[] LINE_COLOUR    = {1.0f, 1.0f, 1.0f, 0.7f};
	private static final float[] FONT_COLOUR    = {1.0f, 1.0f, 1.0f, 1.0f};
	private static final float[] DEFAULT_COLOUR = {0.0f, 0.0f, 0.0f, 0.5f};
	private static final float[] HOVER_COLOUR   = {0.4f, 0.4f, 0.4f, 0.5f};
	private static float[]       colour         = DEFAULT_COLOUR;
	
	// Text to show when you finish the round
	private Text txt_rcomplete = new Text("Round complete!",250, 10, 48);
	public float rcomplete_fade_away = 0.0f;
	private float rcomplete_fade_away_dec = 0.003f;
	
	public GUI(){
		// Creating the buttons on the opening and closing GUI panel
		buttons = new GUIButton[7];
		buttons[0] = new GUIButton(
				"Tower I",  BUTTON_SIZE, BUTTON_SIZE, Playing.TOWER_1);
		buttons[1] = new GUIButton(
				"Tower II", BUTTON_SIZE, BUTTON_SIZE, Playing.TOWER_2);
		buttons[2] = new GUIButton(
				"Tower III",BUTTON_SIZE, BUTTON_SIZE, Playing.TOWER_3);
		buttons[3] = new GUIButton(
				"Sell",BUTTON_SIZE, BUTTON_SIZE, Playing.SELL);
		buttons[4] = new GUIButton(
				"Save",     BUTTON_SIZE, BUTTON_SIZE, Playing.SAVE);
		buttons[5] = new GUIButton(
					"Settings", BUTTON_SIZE, BUTTON_SIZE, Playing.SETTINGS);
		buttons[6] = new GUIButton(
				"Quit",     BUTTON_SIZE, BUTTON_SIZE, Playing.QUIT);
		
		// Repositioning the buttons on the GUI panel
		// Used for having a 2 x n button array
//		int btn_len = (int) Math.ceil(buttons.length/2.0);
//		for (int by = 0; by < btn_len; by++) {
//			for (int bx = 0; bx <= 2; bx++){
//				if ( by*2+bx == buttons.length) break; 
//				buttons[by*2+bx].setPosition(
//					x-w+(bx*BUTTON_SIZE), by*BUTTON_SIZE);
//			}
//		}
		// Repositioning the buttons on the GUI panel
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].setPosition(x-BUTTON_SIZE, i*BUTTON_SIZE);
		}
		
		// Setting the position of the statistics text
		text_coins = new Text(
				"Coins: ", 
				(int) stats_x+text_size, (int) stats_y+text_size, text_size);
		text_round = new Text(
				"Level: ", 
				(int) stats_x+text_size, (int) stats_y+(text_size*2), text_size);
		text_lives = new Text(
				"Lives: ", 
				(int) stats_x+text_size, (int) stats_y+(text_size*3), text_size);
		
		// Repositioning the win text
		float txt_w = win_text.getFont().getTextWidth(win_text.getText());
		float txt_h = win_text.getFont().getCharHeight();
		win_text.setPosition(final_x+(final_w/2)-(txt_w/2), 
				final_y+50-(txt_h/2));
		
		// Respositioning the losing text
		txt_w = lose_text.getFont().getTextWidth(lose_text.getText());
		txt_h = lose_text.getFont().getCharHeight();
		lose_text.setPosition(final_x+(final_w/2)-txt_w/2,
				final_y+50-txt_h/2);
		
		// Respositioning the end game button
		float bx = button_quit.getX();
		float by = button_quit.getY();
		float bw = button_quit.getWidth();
		float bh = button_quit.getHeight();
		String txt = button_quit.getText().getText();
		txt_w =button_quit.getText().getFont().getTextWidth(txt);
		txt_h =button_quit.getText().getFont().getCharHeight();
		button_quit.getText().setPosition(
				bx+(bw/2)-(txt_w/2), by+(bh/2)-(txt_h/2));
		
		by = final_y+120;
		float by_incrementor = final_stats[0].getFont().getCharHeight();
		// Repositioning the end game function
		for (int i = 0; i < final_stats.length; i++) {
			txt = final_stats[i].getText();
			bx = Main.window.getWidth()/2;
			bx -= final_stats[i].getFont().getTextWidth(txt)/2;
			final_stats[i].setPosition(bx, by);
			by += by_incrementor;
		}
		
		// Respositioning the speed modifier button
		bx = button_speed.getX();
		by = button_speed.getY();
		bw = button_speed.getWidth();
		bh = button_speed.getHeight();
		txt = button_speed.getText().getText();
		txt_w =button_speed.getText().getFont().getTextWidth(txt);
		txt_h =button_speed.getText().getFont().getCharHeight();
		button_speed.getText().setPosition(
				bx+(bw/2)-(txt_w/2), by+(bh/2)-(txt_h/2));
		// Reset the position of text and buttons
		button_round_resetPosition();
	}
	
	public int update(){
		// Defining variables that are used later one
		// If nothing is pressed in the gui, this update function returns -1
		int state = -1; 
		double mx = Main.window.getMouseX();
		double my = Main.window.getMouseY();
		int screenWidth = Main.window.getWidth();
		
		// The settings menu when the settings are open 
		if (showSettings) {
			Main_menu.updateSliders();
			if (Main_menu.mute.updateClick()) {
				Audio.toggleMute();
			}
					
			if(Audio.isMuted()) {
				Main_menu.mute.setButtonColour(0.0f, 1.0f, 0.0f);
			}
			else {
				Main_menu.mute.setButtonColour(0.0f, 0.0f, 0.0f);
			}
			if (Main_menu.fullscreen.updateClick()) {
				Main.window.setFullscreen(!Main.window.isFullscreen());
			}
			if (Main.window.isFullscreen()){
				Main_menu.fullscreen.setButtonColour(0.0f, 1.0f, 0.0f);
			}
			else{
				Main_menu.fullscreen.setButtonColour(0.0f, 0.0f, 0.0f);
			}
		}

		
		// Updates tower buttons, pause buttons
		if (!closed) {
			boolean gotText = false;
			for (GUIButton button : buttons){
				boolean canAfford = true;
				String temp_info = "";
				int temp_state = button.update();
				if (temp_state > -1) {
					guiClicked = true;
					state = temp_state;
				}
				
				// Tells you if you can afford depending on which button it is.
				switch (button.getText().text) {
					case "Tower I":
						temp_info = String.valueOf(Turret_1.TURRET_COST);
						if (Playing.coins < Integer.parseInt(temp_info)) {
							canAfford = false;
						}
						break;
					case "Tower II":
						temp_info = String.valueOf(Turret_2.TURRET_COST);
						if (Playing.coins < Integer.parseInt(temp_info)) {
							canAfford = false;
						}
						break;
					case "Tower III":
						temp_info = String.valueOf(Turret_3.TURRET_COST);
						if (Playing.coins < Integer.parseInt(temp_info)) {
							canAfford = false;
						}
						break;
				}
				if (button.getHover() && temp_info != "") {
					gotText = true;
					showCost = true;
					if (!canAfford) {
						turret_info.text = "xCost: " + temp_info + "c";
					} else {
						turret_info.text = "Cost: " + temp_info + "c";
					}
				} 
				else if (!gotText){
					turret_info.text = "";
					showCost = false;
				}
				
				if (!canAfford) {
					button.setTempColour(0.8f, 0.0f, 0.0f, 0.5f);
					button.setTempHoverColour(0.8f, 0.0f, 0.0f, 0.5f);
				}
				else
				{
					button.setTempColour(0.0f, 0.0f, 0.0f, 0.5f );
					button.setTempHoverColour(0.3f, 0.3f, 0.3f, 0.5f );
				}
			}
		}
		
		// The button that open and closes the GUI
		if (mx > open_button_x &&
				mx < open_button_x+open_button_w &&
				my > open_button_y &&
				my < open_button_y+open_button_h)
		{
			colour = HOVER_COLOUR;
			if (Main.window.isMousePressed(Main.window.LEFT_MOUSE)
				&& !guiClicked){
				guiClicked = true;
				close();
			}
		}
		else
		{
			colour = DEFAULT_COLOUR;
		}
				
		// The button that starts and stops the rounds
		if (mx > open_button_x &&
				mx < open_button_x+open_button_w &&
				my > open_button_y &&
				my < open_button_y+open_button_h)
		{
			colour = HOVER_COLOUR;
			if (Main.window.isMousePressed(Main.window.LEFT_MOUSE)
				&& !guiClicked){
				close();
			}
		}
		else
		{
			colour = DEFAULT_COLOUR;
		}
		
		// What happens when you press the round button. (the one button that
		// handles pausing, playing and starting new rounds).
		if (Playing.state != Playing.WIN && Playing.state != Playing.LOSE ) {
			if (button_round.updateClick()){
				if (button_round.getName().equals("Start")) {
					button_round.setName("Pause");
					Playing.roundEnded = false;
					Playing.state = Playing.PLAYING;
				}
				else if (button_round.getName().equals("Pause")){
					button_round.setName("Play");
					Playing.toggle_pause();
				}
				else {
					button_round.setName("Pause");
					Playing.toggle_pause();
				}
				button_round_resetPosition();
			}
		}
		
		// Changes the speed modifier when you press the speed modifier button
		if (button_speed.updateClick()) {
			if (Playing.speed_modifier == 1) {
				Playing.speed_modifier = 2;
				Main.window.setFPS(120);
			}
			else if (Playing.speed_modifier == 2) {
				Playing.speed_modifier = 4;
				Main.window.setFPS(240);				
			}
			else {
				Playing.speed_modifier = 1;
				Main.window.setFPS(60);
			}
			button_speed.setName((int) Playing.speed_modifier+"x");
		}
		
		//if True save prompt == open.
		if(showSavePrompt) {
			if (quit.updateClick()) {
				Playing.state = Playing.QUIT;
				Main.state = Main.MAIN_MENU;
				Main_menu.state = Main_menu.MAIN;
				Audio.stop(false);
			}
			//showSavePrompt changed to false to close the prompt.
			if(cont.updateClick()) {
				//Playing.state = Playing.PLAYING;
				Main.state =Main.PLAYING;
				display_save_prompt();
				close();
			}
		}
		
		// When you try and quit the game, show the prompt
		if(showQuitPrompt) {
			if(quit.updateClick()){
				Playing.state = Playing.QUIT;
				Main.state = Main.MAIN_MENU;
				Main_menu.state = Main_menu.MAIN;
				Audio.stop(false);
			}
			if(cont.updateClick()) {
				Main.state = Main.PLAYING;
				display_quit_prompt();
				close();
			}
			if(saveQuit.updateClick()) {
				Playing.save.write();
				Playing.state = Playing.QUIT;
				Main.state = Main.MAIN_MENU;
				Main_menu.state = Main_menu.MAIN;
				Audio.stop(false);
			}
		}
		
		// Updating the winning and losing state
		if (Playing.state == Playing.WIN || Playing.state == Playing.LOSE) {
			Audio.stop(false);
			if (button_quit.updateClick()) {
				Playing.state = Playing.PAUSED;
				Main.state = Main.MAIN_MENU;
				Main_menu.state = Main_menu.MAIN;
				close_settings_gui();

			}
		}
		
		// Respositions the statistics box
		stats_x = (screenWidth - stats_w) - (screenWidth - x);
		return state;
	}
	
	public void draw() {
		switch (Playing.state) {
			// Draws for the winning state
			case Playing.PAUSED:
			case Playing.ROUND_END:
			case Playing.PLAYING:
				
				// Shows "round complete!" just after you complete a level
				if (rcomplete_fade_away >= 0) {
					Main.window.setColour(0f, 0f, 0f, 
							Math.min(0.5f*rcomplete_fade_away, 0.5f));
					Main.window.rectangle(175, 5, 483, 60, 10);
					Main.window.setColour(1f, 1f, 1f, rcomplete_fade_away);
					txt_rcomplete.draw();
					rcomplete_fade_away -= rcomplete_fade_away_dec; 
				}
				
				button_round.draw();
				guiClicked = false;
				
				draw_statistics();
				
				// Setting the colours for the open GUI button
				Main.window.setColour(colour);
				
				// Open button
				glBegin(GL_POLYGON);
				
					// top-left corner
					Main.window.DrawGLRoundedCorner(
							open_button_x, 
							open_button_y + open_button_radius, 
							3 * Math.PI / 2, 
							Math.PI / 2, 
							open_button_radius
							);
			
					// top-right corner
					glVertex2f(
							open_button_x+open_button_w, 
							open_button_y);
			
					// bottom-right corner 
					glVertex2f(
							open_button_x+open_button_w, 
							open_button_y+open_button_h);
					
					// bottom-left
					Main.window.DrawGLRoundedCorner(
							open_button_x + open_button_radius, 
							open_button_y + open_button_h, 
							Math.PI,
							Math.PI / 2,
							open_button_radius);
				glEnd();
				
				// If the GUI is open, show all of the GUI
				if (!closed)
				{
					// Setting the colours for the GUI
					Main.window.setColour(DEFAULT_COLOUR);

					// GUI side
					Main.window.rectangle(x, y, w, h);			
					
					// Drawing all of the buttons on towers and button on the GUI
					for (GUIButton button : buttons) {
						button.draw();
					}
					
					if (turret_info.getText().length() != 0) {
						if (turret_info.getText().contains("x")) {

							Main.window.setColour(1f,0,0,1f);
							turret_info.text = turret_info.getText().substring(1);
						} else {				
							Main.window.setColour(0,1f,0,1f);
						}
						
						turret_info.draw();
					}
					// Creating a border for the GUI.
					Main.window.setColour(LINE_COLOUR);
					Main.window.drawLine(x, y, x, (y+h)-stats_h);
				}
				button_speed.draw();
				open_button_image.draw(open_button_image_rotation);
				break;
			case Playing.WIN:
				draw_final_screen();
				Main.window.setColour(FONT_COLOUR);
				win_text.draw();
				break;
			// Draws for the losing state
			case Playing.LOSE:
				draw_final_screen();
				Main.window.setColour(FONT_COLOUR);
				lose_text.draw();
				break;
		}
		
		if (showSettings) {
			Main.window.setColour(0f,0f,0f,0.5f);
			Main.window.rectangle(50, 50, 600, 450,20);
			Main_menu.volume_sfx.draw();
			Main_menu.volume_music.draw();
			Main_menu.mute.draw();
			Main_menu.fullscreen.draw();
		}
		if(showSavePrompt) {
			Main.window.setColour(0f,0f,0f,0.5f);
			Main.window.rectangle((x/2)-(400/2),(h/2)-(200/2), 400, 200, 20);
			//340 100
			cont.setPosition(400,340);
			quit.setPosition(200,340);
			quit.draw();
			game_saved.draw();
			cont.draw();
		}
		if(showQuitPrompt) {
			Main.window.setColour(0f,0f,0f,0.5f);
			Main.window.rectangle((x/2)-(400/2),(h/2)-(200/2), 400, 200, 20);
			quit.setPosition(165, 340);
			quit.draw();
			save_progress.draw();
			cont.setPosition(300, 340);
			cont.draw();
			saveQuit.draw();
		}
	}
	
	// Draws for the winning and losing state
	public void draw_final_screen() {
		float text_box_w = 400;
		float text_box_h = 40;
		float text_box_x = Main.window.getWidth()/2 - (text_box_w/2);
		float text_box_y = win_text.y+(win_text.getFont().getCharHeight()/2)-20;

		Main.window.setColour(DEFAULT_COLOUR);
		
		Main.window.rectangle(final_x, final_y, final_w, final_h, final_r);
		
		Main.window.rectangle(text_box_x, text_box_y, text_box_w, text_box_h, 
				final_r);
		button_quit.draw();
		
		// Statistics:
		final_stats[0].text = "Difficulty: "+Playing.difficulty_visual;
		final_stats[1].text = "You made it to round: "+(Playing.round);
		final_stats[2].text = "Lives remaining: "+Playing.lives;
		final_stats[3].text = "Total revenue: "+Playing.coins_revenue;
		final_stats[4].text = "Kills: "+Playing.kills;
		final_stats[5].text = "Arrows Fired: "+Playing.arrows_fired;
		final_stats[6].text = "Buildings built: "+Playing.buildings_built;
		final_stats[7].text = "Buildings upgraded: "+Playing.buildings_upgraded;
		
		for (int i = 0; i < final_stats.length; i++) {
			final_stats[i].draw();
		}
	}
	
	// Draws coins, level, lives during gameplay
	public void draw_statistics(){
		// Drawing the stats box
		Main.window.setColour(DEFAULT_COLOUR);
		Main.window.rectangle(stats_x, stats_y, stats_w, stats_h);
		Main.window.setColour(LINE_COLOUR);
		Main.window.drawLine(stats_x, stats_y, stats_x, stats_y+stats_h);

		Main.window.drawLine(stats_x, stats_y, stats_x+stats_w, stats_y);
		if (showCost) {
			Main.window.setColour(DEFAULT_COLOUR);
			Main.window.rectangle(stats_x,stats_y-20,stats_w,20);

			Main.window.setColour(LINE_COLOUR);
			Main.window.drawLine(stats_x, stats_y-20, stats_x, stats_y);
			Main.window.drawLine(stats_x, stats_y-20, stats_x+stats_w, stats_y-20);
		}
		
		// Statistics
		Main.window.setColour(LINE_COLOUR);
		text_coins.text = "Coins: "+Playing.coins;
		text_round.text = "Level: "+Playing.round;
		text_lives.text = "Lives: "+Playing.lives;
		text_coins.setPosition(stats_x+text_size, stats_y+text_size);
		text_round.setPosition(stats_x+text_size, stats_y+text_size*2);
		text_lives.setPosition(stats_x+text_size, stats_y+text_size*3);
		text_coins.draw();
		text_round.draw();
		text_lives.draw();
	}
	
	public boolean isClicked() {
		return guiClicked;
	}
	
	public void close() {
		if (closed) {
			x = Main.window.getWidth() - w;
			open_button_x = (Main.window.getWidth() - w) - open_button_w;
			open_button_image_rotation = 0;
		}
		else {
			x = Main.window.getWidth();
			open_button_x = Main.window.getWidth() - open_button_w;
			open_button_image_rotation = 180;
			showCost = false;
			close_settings_gui();
			if(button_round.getName().equals("Pause")) {
				Playing.state = Playing.PLAYING;
			}
		}

		open_button_image.setX(open_button_x + open_button_xmargin);
		open_button_image.setY(open_button_y + open_button_ymargin);
		closed = !closed;
	}
	
	// Returns whether the side panel GUI is open or not
	public boolean isClosed() {
		return closed;
	}
	
	public void button_round_resetPosition() {
		String bname = button_round.getName();
		float btw = button_round.getText().getFont().getTextWidth(bname)/2;
		float bw = button_round.getWidth()/2;
		float bx = button_round.getX();
		float bth = button_round.getText().getFont().getCharHeight()/2;
		float bh = button_round.getHeight()/2;
		float by = button_round.getY();
		button_round.getText().setPosition((bx+(bw))-(btw), (by+(bh))-(bth));
	}
	
	public void toggle_settings_gui() {
		int offset = -50;
		if(showSettings) {
			offset = 50;
		}
		Main_menu.volume_sfx.toggleEnabled();
		Main_menu.volume_music.toggleEnabled();
		Main_menu.volume_sfx.setPosition(Main_menu.volume_sfx.getPosition()[0] + offset, Main_menu.volume_sfx.getPosition()[1]);
		Main_menu.volume_sfx.getText().setPosition(Main_menu.volume_sfx.getText().x + offset, Main_menu.volume_sfx.getText().y);
		
		Main_menu.volume_music.setPosition(Main_menu.volume_music.getPosition()[0] + offset, Main_menu.volume_music.getPosition()[1]);
		Main_menu.volume_music.getText().setPosition(Main_menu.volume_music.getText().x + offset, Main_menu.volume_music.getText().y);
		
		Main_menu.mute.setPosition((int)Main_menu.mute.getX() + offset, (int)Main_menu.mute.getY());
		Main_menu.fullscreen.setPosition((int)Main_menu.fullscreen.getX() + offset, (int)Main_menu.fullscreen.getY());
		showSettings = !showSettings;
		Save.write();
	}
	public void display_save_prompt() {
		showSavePrompt = !showSavePrompt;
	}
	public void display_quit_prompt() {
		showQuitPrompt = !showQuitPrompt;
	}
	
	public void close_settings_gui() {
		if (showSettings) {
			toggle_settings_gui();
		}
	}
	
	public boolean settingsOpen() {
		return showSettings;
	}
	
	public boolean saveOpen() {
		return showSavePrompt;
	}
	
	public boolean quitOpen() {
		return showQuitPrompt;
	}
}