package enemies;

/**
 * @author Team 62
 * 
 * Oliver Legg - sgolegg - 201244658
 * Thomas Coupe - sgtcoupe - 201241037
 *
 */
public class Enemy_2 extends Enemy{
	
	public Enemy_2(float x, float y, float grid_size) {
		super("enemies/enemy_2/", x, y, grid_size);
		health = 5;
		value = 10;
		speed = 250f;
	}
	
}