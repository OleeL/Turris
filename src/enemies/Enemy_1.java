package enemies;

/**
 * @author Team 62
 * 
 * Oliver Legg - sgolegg - 201244658
 * Thomas Coupe - sgtcoupe - 201241037
 *
 */
public class Enemy_1 extends Enemy{
	
	public Enemy_1(float x, float y, float grid_size) {
		super("enemies/enemy_1/", x, y, grid_size);
		speed = 2.0f;
		health = 25;
		value = 5;
	}
	
}