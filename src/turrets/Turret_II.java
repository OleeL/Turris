package turrets;

/**
 * @author Team 62
 * 
 * Oliver Legg - sgolegg - 201244658
 *
 */
public class Turret_II extends Turret {

	
	/**
	 * @param float x position 
	 * @param float y position 
	 * @param float grid_size
	 */
	public Turret_II(float x, float y, float grid_size) {
		super("tiles/tower_2", x, y, grid_size);
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
