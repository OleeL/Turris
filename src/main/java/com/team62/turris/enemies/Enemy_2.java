package com.team62.turris.enemies;

import com.team62.turris.gui.Texture;

/**
 * @author Team 62
 * 
 * Oliver Legg - sgolegg - 201244658
 * Thomas Coupe - sgtcoupe - 201241037
 *
 */
public class Enemy_2 extends Enemy{
	
	static {
		for (int i = 0; i < 3; i++) {
			Texture texture = new Texture("enemies/enemy_2/"+i+".png",0,0,1,1);
			textures.put("2"+i, texture);
		}
	}
	
	public Enemy_2(float x, float y, float grid_size) {
		super((byte) 2, x, y, grid_size);
		health = 5;
		value = 5;
		speed = 2.5f;
	}
	
}