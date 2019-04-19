package gui;

import main.Main;
/**
 * @author Team 62; 
 * 
 * Thomas Coupe - sgtcoupe- 201241037
 * Oliver Legg - sgolegg - 201244658
 */
public class Slider {
	private int x;
	private int y;
	private int width;
	private int height;
	private int min;
	private int max;
	
	//sliderWidth = current position of slider along width.
	private int sliderWidth;
	private boolean interacted = false;
	private Text text;
	
	private float[] SLIDER_COLOUR = {0.0f, 0.0f, 0.0f, 0.5f};
	private float[] FONT_COLOUR   = {1.0f, 1.0f, 1.0f, 1.0f};
	
	public Slider(String name, int width, int height, int x, int y, int sliderWidth) {
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.sliderWidth = sliderWidth;
		this.min = 0;
		this.max = width;
		this.text = new Text(name,0,0,32);
		float txt_w = text.getFont().getTextWidth(name);
		float txt_h = text.getFont().getCharHeight();
		text.setPosition(x+(width/2)-(txt_w/2), y+(height/2)-(txt_h/2));
	}
	
	public void update(String name) {
		text.text = name;
		if (Main.window.isMouseReleased(Main.window.LEFT_MOUSE)) {
			interacted = false;
		}
		if(Main.window.getMouseX() > x &&
		   Main.window.getMouseX() < x+width &&
		   Main.window.getMouseY() > y &&
		   Main.window.getMouseY() < y+height || interacted) {
			if(Main.window.isMousePressed(Main.window.LEFT_MOUSE) || interacted) {
				double mx = Main.window.getMouseX();
				interacted = true;
				sliderWidth = Math.abs((int) mx - x);
				if (mx < x) sliderWidth = 0;
				//if (sliderWidth > max) sliderWidth = max; else if(sliderWidth < min) sliderWidth = min;
				sliderWidth = Math.min(sliderWidth, max); sliderWidth = Math.max(sliderWidth, min);
			}
		}
	}
	public void draw() {
		Main.window.setColour(SLIDER_COLOUR);
		Main.window.rectangle(x, y, width, height);
		Main.window.rectangle(x, y, sliderWidth, height);
		Main.window.setColour(FONT_COLOUR);
		text.draw();
	}

	
	public void setPosition(int x, int y) {;
		this.x = x;
		this.y = y;
	}
	
	public void setSliderWidth(int sliderWidth) {
		this.sliderWidth = sliderWidth;
	}
	
	public float getMaxWidth() {
		return max;
	}
	
	public float getSliderWidth() {
		return sliderWidth;
	}

}
