package enemies;

import gui.Texture;

/**
 * @author Team 62
 * 
 * Oliver Legg - sgolegg - 201244658
 * Thomas Coupe - sgtcoupe - 201241037
 *
 */
public class Enemy_1 extends Enemy{
	
	static {
		for (int i = 0; i < 3; i++) {
			Texture texture = new Texture("enemies/enemy_1/"+i+".png",0,0,1,1);
			textures.put("1"+i, texture);
		}
	}
	
	public Enemy_1(float x, float y, float grid_size) {
		super((byte) 1, x, y, grid_size);
		speed = 2.0f;
		health = 25;
		value = 5;
	}
	
}