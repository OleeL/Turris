package turrets;

import gui.Texture;

/**
 * @author Team 62
 * 
 * Oliver Legg - sgolegg - 201244658
 *
 */
public class Turret_III extends Turret {

	
	/**
	 * @param integer x position 
	 * @param integer y position 
	 * @param Texture texture 
	 */
	public Turret_III(Texture texture, float x, float y){
		super("Turret_III", texture, x, y);
		range = 60;
		damage = 5;
		rateOfFire = 0.5f;
	}

	public void upgrade() {
		if (level < MAX_LEVEL) {
			level++;
			range += 0.2;
			damage += 2;
			rateOfFire -= 0.002;
		}
	}

}
