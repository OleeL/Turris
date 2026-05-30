package com.team62.turris.playing.waves;

/**
 * @author Kieran Baker - 201234727 - sgkbaker
 * 
 */

public class ContinuousWave extends WaveStyle{
	public ContinuousWave(float[] ratios, int total_enemies, int round, float multiplier) {
		super(ratios, total_enemies, round, multiplier);
	}
	
	public void generate() {
		int index = 0;
		for (int i = 0; i<enemy_split.length;i++) {
			add(String.valueOf(i + 1), enemy_split[i], index);
			index += enemy_split[i];
		}
	}

}
