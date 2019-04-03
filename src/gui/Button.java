package gui;

import main.Main;

public class Button {
	private String name;
	private int x;
	private int y;
	private int width;
	private int height;
	private int state;
	private float r = 255;
	private float g = 255;
	private float b = 255;
	private final static float ALPHA = 0.33f;
	private int corner_radius = 9;
	
	public Button(String name, int x, int y, int width, int height, int state)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.state = state;
	}
	
	public int getState()
	{
		return state;
	}
	
	public boolean updateClick()
	{
		if (Main.window.getMouseX() > x &&
				Main.window.getMouseX() < x+width &&
				Main.window.getMouseY() > y &&
				Main.window.getMouseY() < y+height)
		{
			r = 0.3f;
			g = 0.3f;
			b = 0.3f;
			if (Main.window.isKeyPressed(Main.window.LEFT_MOUSE))
				return true;
		}
		else
		{
			r = 0.7f;
			g = 0.7f;
			b = 0.7f;
		}
		return false;
	}
	
	public void draw()
	{
		Main.window.setColour(r, g, b, ALPHA);
		Main.window.DrawRoundRect(x, y, width, height, corner_radius);
	}
}
