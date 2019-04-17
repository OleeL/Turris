package gui;

import main.Main;

public class Slider {
	private int xPos;
	private int yPos;
	private String name;
	private int width;
	private int height;
	private int min;
	private int max;
	private int sliderWidth;
	
	public Slider(String name, int min, int max, int width, int height, int xPos, int yPos, int sliderWidth) {
		this.name = name;
		this.min = min;
		this.max = max;
		this.width = width;
		this.height = height;
		this.xPos = xPos;
		this.yPos = yPos;
		this.sliderWidth = sliderWidth;
	}
	public void updateSlider() {
		if(Main.window.getMouseX() > xPos &&
		   Main.window.getMouseX() < xPos+width &&
		   Main.window.getMouseY() > yPos &&
		   Main.window.getMouseY() < yPos+height) {
				
			if(Main.window.isMouseDown(Main.window.LEFT_MOUSE)) {
				sliderWidth = (int) Main.window.getMouseX() - xPos;
				if(sliderWidth > max) sliderWidth = max;
				else if (sliderWidth < min) sliderWidth = min;
			}
		}
	}

	
	public void setPosition(int xPos, int yPos) {;
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public void setSliderWidth(int sliderWidth) {
		this.sliderWidth = sliderWidth;
	}

}
