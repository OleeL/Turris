package turrets;

/**
 * @author Team 62
 * 
 * Oliver Legg - sgolegg - 201244658
 *
 */
public class Turret_2 extends Turret {

	public static float TURRET_RANGE = 135;
	public static float TURRET_DAMAGE = 40;
	public static long TURRET_RATE_OF_FIRE = 25;
	public static int TURRET_COST = 200;	
	public static float TURRET_ARROW_SPEED = 15f;	
	public static int TURRET_UPGRADE_COST = 80;

	/**
	 * @param float x position 
	 * @param float y position 
	 * @param float grid_size
	 */
	public Turret_2(float x, float y, float grid_size) {
		super("turrets/turret_20", x, y, grid_size);
		range = TURRET_RANGE;
		damage = TURRET_DAMAGE;
		rateOfFire = TURRET_RATE_OF_FIRE;
		cost = TURRET_COST;
		arrowSpeed = TURRET_ARROW_SPEED;
		upgrade_cost = TURRET_UPGRADE_COST;
	}

	public boolean upgrade() {
		if (level < MAX_LEVEL) {
			level++;
			range *= 1.2;
			damage += 2;
			rateOfFire /= 1.2;
			upgrade_cost *= 1.8;
			arrowSpeed += 0.8f;
			return true;
		}
		else {
			return false;
		}
	}

}
