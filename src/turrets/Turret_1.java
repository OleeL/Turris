package turrets;

/**
 * @author Team 62
 * 
 * Oliver Legg - sgolegg - 201244658
 *
 */
public class Turret_1 extends Turret {
	
	public static float TURRET_RANGE = 150;
	public static float TURRET_DAMAGE = 20;
	public static long TURRET_RATE_OF_FIRE = 500;
	public static int TURRET_COST = 50;	
	public static float TURRET_ARROW_SPEED = 1200f;	
	public static int TURRET_UPGRADE_COST = 30;

	
	/**
	 * @param float x position 
	 * @param float y position 
	 * @param float grid_size
	 */
	public Turret_1(float x, float y, float grid_size) {
		super("turrets/turret_1", x, y, grid_size);
		range = TURRET_RANGE;
		damage = TURRET_DAMAGE;
		rateOfFire = TURRET_RATE_OF_FIRE;
		cost = TURRET_COST;
		arrowSpeed = TURRET_ARROW_SPEED;
		upgrade_cost = TURRET_UPGRADE_COST;
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
