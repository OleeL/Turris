package enemies;

import gui.Texture;
import main.Main;

public abstract class Enemy {
	protected int healthpoints;
	protected float x, y, w, h, scale, speed, xvel, yvel, end_time;
	private float time = 90644600;
	protected Texture texture;
	private int t_num = 0; // Texture number
	private int prev_t_num = 0;
	private String path;
	
	public Enemy(String path, float x, float y, float grid_size) {
		this.x = x;
		this.y = y;
		this.speed = 1;
		this.xvel = 0;
		this.yvel = 0;
		this.scale = grid_size / 100;
		this.path = path;
		this.texture = new Texture(path+t_num+".png", x, y, scale, scale);
		this.w = texture.getWidth();
		this.h = texture.getHeight();
		this.end_time = 0;
	}
	
	public abstract void update();
	
	public void physics() {
		
	}

	public void draw() {
		
		// Walking animation
		if (System.nanoTime() > end_time) {
			if (t_num == 0) {
				if (prev_t_num == 1){
					t_num = 2;
					prev_t_num = 2;
				}
				else{
					t_num = 1;
					prev_t_num = 1;
				}
			}
			else{
				t_num = 0;
			}
			
			end_time = System.nanoTime()+time;
			texture.setTexture(path+t_num+".png");
		}
		// Defining the mouse positions
		float mx = (float) Main.window.getMouseX();
		float my = (float) Main.window.getMouseY();
		float cx = x+(w/2);
		float cy = y+(h/2);
		// Gets the direction of the enemy to the mouse
        float direction = (float) Math.toDegrees( Math.atan2(my-cy, mx-cx));
        
        // Sends the enemy in the correct direction given the right speed
        float direction_x = (float) Math.cos(direction * Math.PI / 180);
        float direction_y = (float) Math.sin(direction * Math.PI / 180);
        
        // Sets the correct velocity of the enemy
        double dist = Math.sqrt(Math.pow(mx-cx,2) + Math.pow(my-cy,2));
        
        // If the enemy is there, stop moving
        if ( dist < 5 && dist > -5) {
        	xvel = 0;
	        yvel = 0;
	        texture.setTexture(path+0+".png"); // Enemy stands still
        }
        else { // Otherwise, set their direction
	        xvel = (speed * direction_x);
	        yvel = (speed * direction_y);
        }
        
        // Sets the rotation of the enemy so they're facing 
        float rotation = (float) Math.atan2( mx-cx, my-cy);
        
        // Moves the enemy
        x += xvel;
        y += yvel;
		texture.setX(x);
		texture.setY(y);
		
		// Draw the
		texture.draw(-((float) Math.toDegrees(rotation)));
	}
}
