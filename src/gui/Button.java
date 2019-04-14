/**
 * @author Team 62
 * 
 * Oliver Legg - sgolegg - 201244658
 *
 */

package gui;

import main.Main;

public class Button {
	private String name;
	private int x;
	private int y;
	private int width;
	private int height;
	private int state;
	private float r = 0f;
	private float g = 0f;
	private float b = 0f;
	private int corner_radius = 9;
	private final static float BOX_ALPHA = 0.5f;
	private final static float FONT_ALPHA = 1f;
	private Text text;
	private float font_r = 1f;
	private float font_g = 1f;
	private float font_b = 1f;
	
	public Button(String name, int x, int y, int width, int height, int state)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.state = state;
		
		// Creates text to go on the button
		text = new Text(name, x, y, 16);
	}
	
	// Returns the number put into the button on creation
	// The number corresponds to the action that you want carried out next
	public int getState()
	{
		return state;
	}
	
	// Gets the text on the button
	public Text getText()
	{
		return text;
	}
	
	// Sets the button colour
	public void setButtonColour(float r, float g, float b)
	{
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	// Sets the fonts colour
	public void setFontColour(float r, float g, float b)
	{
		this.font_r = r;
		this.font_g = g;
		this.font_b = b;
	}
	
	// Sets the position of the button
	public void setPosition(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	// Checks if the button is pressed (update this every frame)
	public boolean updateClick()
	{
		if (Main.window.getMouseX() > x &&
				Main.window.getMouseX() < x+width &&
				Main.window.getMouseY() > y &&
				Main.window.getMouseY() < y+height)
		{
			r = 0.4f;
			g = 0.4f;
			b = 0.4f;
			
			if (Main.window.isMousePressed(0)){
				return true;
			}
		}
		else
		{
			r = 0f;
			g = 0f;
			b = 0f;
		}
		return false;
	}
	
	// draws the button (update this every frame in the draw function)
	public void draw()
	{
		Main.window.setColour(r, g, b, BOX_ALPHA);
		Main.window.rectangle(x, y, width, height, corner_radius);
		Main.window.setColour(font_r, font_g, font_b, FONT_ALPHA);
		text.draw();
	}

	// Gets the name of the button
	public String getName() {
		return name;
	}

	// Sets the name of the button.
	public void setName(String name) {
		this.name = name;
	}
}
