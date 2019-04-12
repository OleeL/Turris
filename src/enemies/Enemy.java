package enemies;

import gui.Texture;
import main.Main;

public abstract class Enemy {
	protected int healthpoints;
	protected float x, y, w, h, scale, speed, xvel, yvel;
	protected Texture texture;
	
	public Enemy(String filename, float x, float y, float grid_size) {
		this.x = x;
		this.y = y;
		this.speed = 5;
		this.xvel = 0;
		this.yvel = 0;
		this.scale = grid_size / 100;
		this.texture = new Texture(filename+".png", x, y, scale, scale);
		this.w = texture.getWidth();
		this.h = texture.getHeight();
	}
	
	public abstract void update();
	
	public void physics() {
		
	}

	public void draw() {
		float mx = (float) Main.window.getMouseX();
		float my = (float) Main.window.getMouseY();

        float direction = (float) Math.toDegrees( Math.atan2((my-y), (mx-x)));
        
        // Sends the ball in the correct direction given the right speed
        float direction_x = (float) Math.cos(direction * Math.PI / 180);
        float direction_y = (float) Math.sin(direction * Math.PI / 180);
        xvel = (speed * direction_x);
        yvel = (speed * direction_y);
        
        float rotation = (float) Math.atan2(
                (mx-(x+(w/2))),
                (my-(y+(h/2))));
        
        x += xvel;
        y += yvel;
        
		texture.setX(x);
		texture.setY(y);
		
		
		texture.draw_2(-(float) Math.toDegrees(rotation));
	}
}
