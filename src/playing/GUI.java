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
import main.Main;
import main.Main_menu;
import turrets.Turret_1;
import turrets.Turret_2;
import turrets.Turret_3;

/**
 * @author Oliver Legg - sgolegg - 201244658
 *
 */
public class GUI {
	
	// x, y, w and h of the side panel
	private float x = Main.window.getWidth();
	private float y = 0;
	private float w = 100;
	private float h = Main.window.getHeight();
	
	// Button that opens and closes the GUI
	private float open_button_w = 25;
	private float open_button_h = 50;
	private float open_button_x = Main.window.getWidth() - open_button_w;
	private float open_button_y;
	private float open_button_radius = 6;
	
	// Final screen (winning or losing)
	private float final_w = 500;
	private float final_h = 500;
	private float final_x = (Main.window.getWidth()/2)-(final_w/2);
	private float final_y = (Main.window.getHeight()/2)-(final_h/2);
	private float final_r = 6;
	
	private Text turret_info = new Text("Price:", 650,520,18);
	
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
	private float stats_w = 200f;
	private float stats_h = 100f;
	private float stats_x = Main.window.getWidth() - stats_w;
	private float stats_y = Main.window.getHeight() - stats_h;
	private Text text_coins;
	private Text text_round;
	private Text text_lives;
	private int text_size = 20;

	// Button variables
	private final float BUTTON_SIZE = 50;
	private GUIButton[] buttons;

	private boolean closed = true;
	private boolean guiClicked = false;
	
	// Colours
	private static final float[] LINE_COLOUR    = {1.0f, 1.0f, 1.0f, 0.7f};
	private static final float[] FONT_COLOUR    = {1.0f, 1.0f, 1.0f, 1.0f};
	private static final float[] DEFAULT_COLOUR = {0.0f, 0.0f, 0.0f, 0.5f};
	private static final float[] HOVER_COLOUR   = {0.4f, 0.4f, 0.4f, 0.5f};
	private static float[]       colour         = DEFAULT_COLOUR;
	
	public GUI(){
		// Creating the buttons on the opening and closing GUI panel
		buttons = new GUIButton[4];
		buttons[0] = new GUIButton(
				"Tower 1",  BUTTON_SIZE, BUTTON_SIZE, Playing.TOWER_1);
		buttons[1] = new GUIButton(
				"Tower 2", BUTTON_SIZE, BUTTON_SIZE, Playing.TOWER_2);
		buttons[2] = new GUIButton(
				"Tower 3",BUTTON_SIZE, BUTTON_SIZE, Playing.TOWER_3);
		buttons[3] = new GUIButton(
				"Quit",     BUTTON_SIZE, BUTTON_SIZE, Playing.QUIT);
		
		// Repositioning the buttons on the GUI panel
		int btn_len = (int) Math.ceil(buttons.length/2.0);
		for (int by = 0; by < btn_len; by++) {
			for (int bx = 0; bx <= 2; bx++){
				if ( by*2+bx == buttons.length) break; 
				buttons[by*2+bx].setPosition(
						x-w+(bx*BUTTON_SIZE), by*BUTTON_SIZE);
			}
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
		
		// Updates tower buttons, pause buttons
		if (!closed) {
			String temp_info = "";
			for (GUIButton button : buttons){
				int temp_state = button.update();
				if (temp_state > -1) {
					guiClicked = true;
					state = temp_state;
				}
				if (button.getHover() && button.getText().text != "Quit") {
					switch (button.getText().text) {
					case "Tower 1":
						temp_info = String.valueOf(Turret_1.TURRET_COST);
						break;
					case "Tower 2":
						temp_info = String.valueOf(Turret_2.TURRET_COST);
						break;
					case "Tower 3":
						temp_info = String.valueOf(Turret_3.TURRET_COST);
						break;
					}
					
				}
				if (temp_info != "") {
					if (Playing.coins < Integer.parseInt(temp_info)) {
						turret_info.text = "xCost: " + temp_info + "c";
					} else {
						turret_info.text = "Cost: " + temp_info + "c";
					}
					
				} else {
					turret_info.text = "";
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
		
		if (button_speed.updateClick()) {
			if (Playing.speed_modifier == 1) {
				Playing.speed_modifier = 2;
			}
			else if (Playing.speed_modifier == 2) {
				Playing.speed_modifier = 4;
			}
			else if (Playing.speed_modifier == 4) {
				Playing.speed_modifier = 8;
			}
			else {
				Playing.speed_modifier = 1;
			}
			button_speed.setName((int) Playing.speed_modifier+"x");
		}
		
		// Updating the winning and losing state
		if (Playing.state == Playing.WIN || Playing.state == Playing.LOSE) {
			Audio.stop();
			if (button_quit.updateClick()) {
				Playing.state = Playing.PAUSED;
				Main.state = Main.MAIN_MENU;
				Main_menu.state = Main_menu.MAIN;

			}
		}
		
		// Respositions the statistics box
		stats_x = (screenWidth - stats_w) - (screenWidth - x);
		return state;
	}
	
	public void draw(){
		
		switch (Playing.state) {
			// Draws for the winning state
			case Playing.PAUSED:
			case Playing.ROUND_END:
			case Playing.PLAYING:
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
		final_stats[1].text = "You made it to round: "+Playing.round;
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
		Main.window.drawLine(stats_x, stats_y, stats_x+stats_w, stats_y);
		Main.window.drawLine(stats_x, stats_y, stats_x, stats_y+stats_h);
		
		// Statistics
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
		}
		else {
			x = Main.window.getWidth();
			open_button_x = Main.window.getWidth() - open_button_w;
		}
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
}