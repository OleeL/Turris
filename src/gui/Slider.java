package gui;

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
	
	public void setPosition(int xPos, int yPos) {;
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public void setSliderWidth(int sliderWidth) {
		this.sliderWidth = sliderWidth;
	}
	

}
