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
	private float r = 0.33f;
	private float g = 0.33f;
	private float b = 0.33f;
	private int corner_radius = 9;
	private final static float BOX_ALPHA = 255f;
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
		text = new Text(name, x, y, 12);
	}
	
	public int getState()
	{
		return state;
	}
	
	public Text getText()
	{
		return text;
	}
	
	public void setButtonColour(float r, float g, float b)
	{
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public void setFontColour(float r, float g, float b)
	{
		this.font_r = r;
		this.font_g = g;
		this.font_b = b;
	}
	
	public void setPosition(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public boolean updateClick()
	{
		if (Main.window.getMouseX() > x &&
				Main.window.getMouseX() < x+width &&
				Main.window.getMouseY() > y &&
				Main.window.getMouseY() < y+height)
		{
			r = 0.2f;
			g = 0.2f;
			b = 0.2f;
			if (Main.window.isKeyPressed(Main.window.LEFT_MOUSE))
				return true;
		}
		else
		{
			r = 0.4f;
			g = 0.4f;
			b = 0.4f;
		}
		return false;
	}
	
	public void draw()
	{
		Main.window.setColour(r, g, b, BOX_ALPHA);
		Main.window.roundRectangle(x, y, width, height, corner_radius);
		Main.window.setColour(font_r, font_g, font_b, FONT_ALPHA);
		text.draw();
	}
}
