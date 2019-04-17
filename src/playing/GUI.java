/**
 * 
 */
package playing;

import static org.lwjgl.opengl.GL11.GL_POLYGON;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import gui.Text;
import main.Main;

/**
 * @author Oliver Legg - sgolegg - 201244658
 *
 */
public class GUI {
	
	private float x = Main.window.getWidth();
	private float y = 0;
	private float w = 100;
	private float h = Main.window.getHeight();
	
	private float open_button_w = 25;
	private float open_button_h = 50;
	private float open_button_x = Main.window.getWidth() - open_button_w;
	private float open_button_y;
	private float open_button_radius = 6;
	
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
	private static final float[] DEFAULT_COLOUR = {0.0f, 0.0f, 0.0f, 0.5f};
	private static final float[] HOVER_COLOUR   = {0.4f, 0.4f, 0.4f, 0.5f};
	private static float[]       colour         = DEFAULT_COLOUR;
	
	public GUI(){
		final int TOWER_1 = 1;
		final int TOWER_2 = 2;
		final int TOWER_3 = 3;
		final int QUIT    = 98;
		final int PAUSE   = 0;
		buttons = new GUIButton[5];
		buttons[0] = new GUIButton("Tower I",  BUTTON_SIZE, BUTTON_SIZE, TOWER_1);
		buttons[1] = new GUIButton("Tower II", BUTTON_SIZE, BUTTON_SIZE, TOWER_2);
		buttons[2] = new GUIButton("Tower III",BUTTON_SIZE, BUTTON_SIZE, TOWER_3);
		buttons[3] = new GUIButton("Pause",    BUTTON_SIZE, BUTTON_SIZE, PAUSE);
		buttons[4] = new GUIButton("Quit",     BUTTON_SIZE, BUTTON_SIZE, QUIT);
		int btn_len = (int) Math.ceil(buttons.length/2.0);
		for (int by = 0; by < btn_len; by++) {
			for (int bx = 0; bx <= 2; bx++){
				if ( by*2+bx == buttons.length) break; 
				buttons[by*2+bx].setPosition(
						x-w+(bx*BUTTON_SIZE), by*BUTTON_SIZE);
			}
		}
		text_coins = new Text(
				"Coins: ", 
				(int) stats_x+text_size, (int) stats_y+text_size, text_size);
		text_round = new Text(
				"Level: ", 
				(int) stats_x+text_size, (int) stats_y+(text_size*2), text_size);
		text_lives = new Text(
				"Lives: ", 
				(int) stats_x+text_size, (int) stats_y+(text_size*3), text_size);
	}
	
	public int update(){
		int state = -1;
		double mx = Main.window.getMouseX();
		double my = Main.window.getMouseY();
		int screenWidth = Main.window.getWidth();
		
		// Updates tower buttons, pause buttons
		if (!closed) {
			for (GUIButton button : buttons){
				int temp_state = button.update();
				if (temp_state > -1) {
					guiClicked = true;
					state = temp_state;
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
		
		stats_x = (screenWidth - stats_w) - (screenWidth - x);
		return state;
	}
	
	public void draw(){
		
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
			
			// Creating a border for the GUI.
			Main.window.setColour(LINE_COLOUR);
			Main.window.drawLine(x, y, x, (y+h)-stats_h);
		}
		
	}
	
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
	
	public boolean isClosed() {
		return closed;
	}
}