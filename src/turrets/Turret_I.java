package turrets;

/**
 * @author Team 62
 * 
 * Oliver Legg - sgolegg - 201244658
 *
 */
public class Turret_I extends Turret {

	
	/**
	 * @param float x position 
	 * @param float y position 
	 * @param float grid_size
	 */
	public Turret_I(float x, float y, float grid_size) {
		super("turrets/turret_1", x, y, grid_size);
		range = 150;
		damage = 20;
		rateOfFire = 500;
		cost = 50;
		arrowSpeed = 12f;
		upgrade_cost = 30;
	}

	public int upgrade() {
		int cost = 0;
		if (level < MAX_LEVEL) {
			level++;
			range += 0.2;
			damage += 2;
			rateOfFire -= 0.2;
			cost = upgrade_cost;
			upgrade_cost *= 1.8;
		}
		return cost;
	}

}
