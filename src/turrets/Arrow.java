package turrets;

import gui.Texture;
import main.Main;

public class Arrow {

	public float x, y;
	private boolean destroy = false;
	private int radius = 5;
	private float xvel;
	private float yvel;
	private float damage;
	private Texture texture;
	private float texture_rotation;
	
	public Arrow( 
			float x, 
			float y, 
			float tx, 
			float ty, 
			float scale, 
			float damage, 
			float speed) {

        texture = new Texture( "turrets/arrow.png", x, y, scale, scale);
        
        // Gets the centre coordinates of the texture
        x -= texture.getWidth()/2;
        y -= texture.getHeight()/2;
        float cx = x + (texture.getWidth()/2);
        float cy = y + (texture.getHeight()/2);
        
        // Moves the texture of the arrow
		texture.setX(cx);
		texture.setY(cy);
		
		// Assigning variables
		this.x = cx;
		this.y = cy;
		this.damage = damage;
		
		// Marks the direction of the arrow
		float direction = (float) Math.toDegrees(Math.atan2(ty-cy, tx-cx));
        
        // Sends the enemy in the correct direction given the right speed
        float direction_x = (float) Math.cos(direction * Math.PI / 180);
        float direction_y = (float) Math.sin(direction * Math.PI / 180);
        
        // Moves the enemy
        xvel = (direction_x * speed);
        yvel = (direction_y * speed);
        
        // Sets the rotation of the arrow so it's facing the right way
        texture_rotation = (float) Math.atan2( tx-cx, ty-cy);

	}
	
	public void update( double dt ) {
		// if the arrow goes off the screen, it is then removed
		if (x < 0 || y < 0 || x > Main.window.getWidth() || y > Main.window.getHeight()) {
			destroy = true;
		}
        
		// Moves the arrow based on it's current velocity
		x += xvel;
        y += yvel;
        

        // Moves the texture of the arrow
		texture.setX(x);
		texture.setY(y);
		
	}
	
	public void draw() {
		// Draw the enemy (at a rotation too)
		texture.draw(-((float) Math.toDegrees(texture_rotation)));
	}
	
	public boolean collidesWith(float ex, float ey, float er) {
		if (radius+er > Math.sqrt(Math.pow(y - ey, 2)+Math.pow(x - ex, 2))){
			destroy = true;
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isDestroyed() {
		return destroy;
	}
	
	public float getDamage() {
		return damage;
	}

}
