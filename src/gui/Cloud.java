package gui;

import engine.io.Random;

public class Cloud {

	private Texture texture;
	private double speed = Random.decimal(0.2, 1);
	public Cloud(int cloud) {
		int random_x = Random.integer(0, 900);
		int random_y = Random.integer(0, 200);
		texture = new Texture("clouds/cloud_"+cloud+".png", random_x, random_y);
	}
	
	public void update(double dt)
	{
		texture.setX((float) (texture.getX() - speed));
	}
	
	public void draw()
	{
		if (texture.getX()+texture.getWidth() < 0) {
			texture.setX(800);
			texture.setY(Random.integer(0, 200));
			speed = Random.decimal(0.2, 1);
		}
		texture.draw();
	}
}
