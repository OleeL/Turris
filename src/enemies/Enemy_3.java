package enemies;

import gui.Texture;

/**
 * @author Team 62
 * 
 * Oliver Legg - sgolegg - 201244658
 * Thomas Coupe - sgtcoupe - 201241037
 * 
 */
public class Enemy_3 extends Enemy{
	
	static {
		for (int i = 0; i < 3; i++) {
			Texture texture = new Texture("enemies/enemy_3/"+i+".png",0,0,1,1);
			textures.put("3"+i, texture);
		}
	}
	
	public Enemy_3(float x, float y, float grid_size) {
		super((byte) 3, x, y, grid_size);
		health = 200;
		value = 10;
		speed = 3;
	}
	
}