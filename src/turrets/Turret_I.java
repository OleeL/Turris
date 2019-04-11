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
		super("tiles/tower_1", x, y, grid_size);
		range = 10;
		damage = 20;
		rateOfFire = 2;
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
