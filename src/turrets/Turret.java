package turrets;

/**
 * @author Team 62
 * 
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
	 * @param integer x position 
	 * @param integer y position 
	 * @param Texture texture 
	 */
	public Turret(String name, Texture texture, float x, float y) {
		super(name, texture, x, y);
		
	}

	public abstract void upgrade();
	
}
