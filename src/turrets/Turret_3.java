package turrets;

/**
 * @author Team 62
 * Oliver Legg - sgolegg - 201244658
 *
 */
public class Turret_3 extends Turret {

	public static float TURRET_RANGE = 225;
	public static float TURRET_DAMAGE = 5;
	public static long TURRET_RATE_OF_FIRE = 100;
	public static int TURRET_COST = 500;	
	public static float TURRET_ARROW_SPEED = 10f;	
	public static int TURRET_UPGRADE_COST = 250;
	
	/**
	 * @param float x position 
	 * @param float y position 
	 * @param float grid_size
	 */
	public Turret_3(float x, float y, float grid_size){
		super("turrets/turret_3", x, y, grid_size);
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
			rateOfFire -= 0.002;
			cost = upgrade_cost;
			upgrade_cost *= 1.8;
		}
		return cost;
	}

}
