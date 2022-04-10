package com.team62.turris.turrets;

/**
 * @author Team 62
 * 
 * Oliver Legg - sgolegg - 201244658
 *
 */
public class Turret_1 extends Turret {
	
	public static float TURRET_RANGE = 150;
	public static float TURRET_DAMAGE = 20;
	public static long TURRET_RATE_OF_FIRE = 25;
	public static int TURRET_COST = 60;	
	public static float TURRET_ARROW_SPEED = 12f;	
	public static int TURRET_UPGRADE_COST = 50;
	
	/**
	 * @param float x position 
	 * @param float y position 
	 * @param float grid_size
	 */
	public Turret_1(float x, float y, float grid_size) {
		super("turrets/turret_10", x, y, grid_size);
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
			range *= 1.1;
			damage += 2;
			rateOfFire /= 1.25;
			upgrade_cost *= 1.8;
			arrowSpeed += 1.3f;
			return true;
		}
		else {
			return false;
		}
	}

}
