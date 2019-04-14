package turrets;

/**
 * @author Team 62
 * Oliver Legg - sgolegg - 201244658
 * 
 */

import gui.Texture;
import main.Main;
import playing.Entity;

public abstract class Turret extends Entity {

	protected float rateOfFire, range, damage;
	protected int level, cost, upgrade_cost;
	public final int MAX_LEVEL = 3;
	private float game_x, game_y;
	
	/**
	 * @param String filename
	 * @param float x position 
	 * @param float y position 
	 * @param float grid_size
	 */
	public Turret(String filename, float x, float y, float grid_size) {
		super(
			filename, 
			new Texture(
					filename+".png", 
					(int) (x * grid_size), 
					(int) (y * grid_size), 
					grid_size / 100, 
					grid_size / 100),
			x, 
			y);
		this.game_x = x * grid_size;
		this.game_y = y * grid_size;
	}

	public abstract int upgrade();
	
	// Pass the target x and target y
	public Arrow kill(float tx, float ty) {
		if (inRange(tx, ty)){
			return fire_at(tx, ty);
		}
		return null;
	}
	
	private boolean inRange(float tx, float ty) {
		double dist = Math.sqrt(Math.pow(tx-x,2) + Math.pow(ty-y,2));
		if (dist < range)
			return true;
		else
			return false;
	}
	
	private Arrow fire_at(float tx, float ty) {
		return new Arrow(x, y, tx, ty, damage);
	}
	
	@Override
	public void draw() {
		texture.draw();
		// Line segments on circle of visualised radius
		int line_segments = 64;
		double mx = Main.window.getMouseX();
		double my = Main.window.getMouseY();
		float center_x = game_x+(w/2);
		float center_y = game_y+(h/2);
		// If the mouse is hovering over the turrets, show the radius
		if (mx > game_x && mx < game_x + w && my > game_y && my < game_y + h){
			Main.window.setColour(0f,0f,0f,0.33f);
			Main.window.circle(true,  center_x, center_y, range, line_segments);
			Main.window.circle(false, center_x, center_y, range, line_segments);
			
		}
	}
	
	public int getCost() {
		return cost;
	}
	
}
