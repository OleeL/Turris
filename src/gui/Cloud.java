package gui;

import engine.io.Random;

public class Cloud {

	private Texture texture;
	private double speed = Random.integer(1000, 10000);
	public Cloud(int cloud) {
		int random_x = Random.integer(0, 900);
		int random_y = Random.integer(0, 200);
		texture = new Texture("clouds/cloud_"+cloud+".png", random_x, random_y,
								1f, 1f);
	}
	
	public void update(double dt)
	{
		texture.setX((float) (texture.getX() - (speed * dt)));
	}
	
	public void draw()
	{
		if (texture.getX()+texture.getWidth() < 0) {
			texture.setX(800);
			texture.setY(Random.integer(0, 200));
			speed = Random.integer(30, 100);
		}
		texture.draw();
	}
}
