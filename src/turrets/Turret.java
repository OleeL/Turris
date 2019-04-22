package turrets;

import engine.io.Audio;
import gui.Text;

/**
 * @author Team 62
 * Oliver Legg - sgolegg - 201244658
 * 
 */

import gui.Texture;
import main.Main;
import playing.Entity;
import playing.Playing;

public abstract class Turret extends Entity {

	protected float range, damage;
	protected int level, cost, upgrade_cost;
	public final int MAX_LEVEL = 3;
	private float game_x, game_y;
	
	protected float arrowSpeed = 5;
	protected long rateOfFire;
	private long time, end;
	private boolean arrowReady;
	private float grid_size;
	
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
		this.grid_size = grid_size;
		arrowReady = true;
	}

	public abstract int upgrade();
	
	// Pass the target x and target y
	public Arrow shootAt(float tx, float ty) {
		if (inRange(tx, ty)){
			return fire_at(tx, ty);
		}
		
		return null;
	}
	
	// If the enemy tx and ty is in range of the turret and the arrow is ready,
	// this function then returns true
	private boolean inRange(float tx, float ty) {
		double dist = Math.sqrt(
				Math.pow(tx-(game_x+(w/2)),2) + 
				Math.pow(ty-(game_y+(h/2)),2));
		if (dist <= range && arrowReady) {
			return true;
		}
		else {
			return false;
			}
	}
	
	// fires an arrow at the tx and ty
	private Arrow fire_at(float tx, float ty) {
		// Arrows are no longer made ready and the reload timer has started
		arrowReady = false;
		Audio.play(Audio.SND_TURRET_SHOOT);
		end = (long) (System.currentTimeMillis( ) + rateOfFire);
		return new Arrow(
				game_x+(w/2), 
				game_y+(h/2), 
				tx,
				ty,
				grid_size/100,
				damage, 
				arrowSpeed);
	}
	
	@Override
	public void draw() {
		// finding the time before the operation is executed
		time = (long) System.currentTimeMillis( );
		
		// finding the time after the operation is executed
		if (time > end) {
			arrowReady = true;
		}
		
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
			
			int font_size = 24;
			int offset_y = -50;
			
			if (level < MAX_LEVEL) {
				Text upgrade_text = new Text(String.valueOf(this.cost), 0,0, font_size);
				upgrade_text.setPosition(center_x - (upgrade_text.getFont().getTextWidth(upgrade_text.text) / 2), center_y + offset_y);
				
				if (Playing.coins >= cost) {
					Main.window.setColour(0f,1f,0f, 1f);
				} else {
					Main.window.setColour(1f, 0f, 0f, 1f);
				}
				
				upgrade_text.draw();
			} else {
				Text info = new Text("Max level",0,0,font_size);
				info.setPosition(center_x - (info.getFont().getTextWidth(info.text) / 2), center_y + offset_y);
				
				Main.window.setColour(1f,0f,0f,1f);
				
				info.draw();
			}

			
		}
	}
	
	// Gets the cost of building the turret.
	public int getCost() {
		return cost;
	}
	
	public float getRange() {
		return range;
	}
	
	public int getMaxLevel() {
		return MAX_LEVEL;
	}
	
	public int getUpgradeCost() {
		return upgrade_cost;
	}
	
	
}
