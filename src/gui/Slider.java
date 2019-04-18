package gui;

import main.Main;
/**
 * @author Team 62; 
 * 
 * Thomas Coupe - sgtcoupe- 201241037
 * Oliver Legg - sgolegg - 201244658
 */
public class Slider {
	private int xPos;
	private int yPos;
	private String name;
	private int width;
	private int height;
	private int min;
	private int max;
	//sliderWidth = current position of slider along width.
	private int sliderWidth;
	private boolean interacted = false;
	
	public Slider(String name, int width, int height, int xPos, int yPos, int sliderWidth) {
		this.name = name;
		this.width = width;
		this.height = height;
		this.xPos = xPos;
		this.yPos = yPos;
		this.sliderWidth = sliderWidth;
		min = 0;
		max = width;
	}
	
	public void updateSlider() {

		if (Main.window.isMouseReleased(Main.window.LEFT_MOUSE)) {
			interacted = false;
		}
		if(Main.window.getMouseX() > xPos &&
		   Main.window.getMouseX() < xPos+width &&
		   Main.window.getMouseY() > yPos &&
		   Main.window.getMouseY() < yPos+height || interacted) {
			if(Main.window.isMousePressed(Main.window.LEFT_MOUSE) || interacted) {
				double mx = Main.window.getMouseX();
				interacted = true;
				sliderWidth = Math.abs((int) mx - xPos);
				if (mx < xPos) sliderWidth = 0;
				//if (sliderWidth > max) sliderWidth = max; else if(sliderWidth < min) sliderWidth = min;
				sliderWidth = Math.min(sliderWidth, max); sliderWidth = Math.max(sliderWidth, min);
			}
		}
	}
	public void draw() {
		
		Main.window.rectangle(xPos, yPos, width, height);
		Main.window.rectangle(xPos, yPos, sliderWidth, height);
	}

	
	public void setPosition(int xPos, int yPos) {;
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public void setSliderWidth(int sliderWidth) {
		this.sliderWidth = sliderWidth;
	}

}
