package com.team62.turris.gui;

import com.team62.turris.engine.io.Random;

public class Cloud {

	private Texture texture;
	private double speed = Random.integer(1000, 10000);
	public Cloud(int cloud) {
		// Picks a random x coordinate off the screen or on
		int random_x = Random.integer(0, 900);
		int random_y = Random.integer(0, 200);
		texture = new Texture("clouds/cloud_"+cloud+".png", random_x, random_y,
								1f, 1f);
	}
	
	// Updates the cloud by moving it left always
	public void update(double dt){
		texture.setX((float) (texture.getX() - (speed * dt)));
		
		// If the texture is off the screen (on the left), reset it's position
		if (texture.getX()+texture.getWidth() < 0) {
			texture.setX(800);
			texture.setY(Random.integer(0, 200));
			speed = Random.integer(30, 100);
		}
	}
	
	// Draws the cloud
	public void draw(){
		texture.draw();
	}
}
