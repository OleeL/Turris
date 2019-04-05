package turrets;

import gui.Texture;

/**
 * @author Team 62
 * 
 * Oliver Legg - sgolegg - 201244658
 *
 */
public class Turret_II extends Turret {

	
	/**
	 * @param integer x position 
	 * @param integer y position 
	 * @param Texture texture 
	 */
	public Turret_II(Texture texture, float x, float y) {
		super("Turret_II", texture, x, y);
		range = 12;
		damage = 40;
		rateOfFire = 5;
	}

	public void upgrade() {
		if (level < MAX_LEVEL) {
			level++;
			range += 0.2;
			damage += 2;
			rateOfFire -= 0.2;
		}
	}

}
