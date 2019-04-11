package turrets;

/**
 * @author Team 62
 * Oliver Legg - sgolegg - 201244658
 * 
 */

import gui.Texture;
import playing.Entity;

public abstract class Turret extends Entity {

	protected float rateOfFire, range, damage;
	protected int level;
	public final int MAX_LEVEL = 3;
	
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
	}

	public abstract void upgrade();
	
}
