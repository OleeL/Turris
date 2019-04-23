package playing.waves;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import enemies.*;
import playing.Playing;

/**
 * @author Oliver Legg - 201244658 - sgolegg
 * Kieran Baker - 201234727 - sgkbaker
 * 
 */

public class Wave {

	// File names for difficulty of rounds
	/*
	 * USAGE:
	 * 
	 * Create use produceWave(int round, String difficulty) to produce a wave of
	 * enemies and then access the enemies by doing enemies[] and spawn_delays[]
	 * 
	 * We should use this class by calling an enemy from the "enemies" list 
	 * and add it into the game. You would then wait for the number of seconds 
	 * in the same index in the "spawn_delays" list and then add the next enemy
	 * in the "enemies" list.
	 * 
	 */
	public static final String EASY    = "assets/rounds/easy.csv";
	public static final String MEDIUM  = "assets/rounds/medium.csv";
	public static final String HARD    = "assets/rounds/hard.csv";
	
	public Enemy[] enemies = null;
	public double[] spawn_delays;
	public boolean win;
	
	public Wave() {
		win = false;
	}

	public void produceWave(int round, String difficulty, boolean continuous) {
		
		try {
			String[] string_enemies = null;
			String[] string_spawn_delays = null;
			File file = new File(difficulty);
			Scanner inputStream;
			inputStream = new Scanner(file);
			while (inputStream.hasNext()) {
				String raw_data = inputStream.next();
				if (raw_data.split(",")[0].equals("round"+round)) {
					raw_data = inputStream.next();
					string_enemies = raw_data.split(",");
					raw_data = inputStream.next();
					string_spawn_delays = raw_data.split(",");
					break;
				}
			}
			if (string_enemies == null && !continuous) {
				win = true;
			} else if (string_enemies == null && continuous) {
				inputStream.close();
				//String[][] wave = ContinuousWave.create(round,difficulty);
				float multiplier = 1;
				float[] ratios = new float[] {0.4f, 0.3f, 0.3f};
				switch (difficulty) {
				case Wave.MEDIUM:
					multiplier = 1.2f;
					ratios[0] -= 0.1f;
					ratios[1] += 0.1f;
					break;
				case Wave.HARD:
					multiplier = 1.5f;
					ratios[0] -= 0.3f;
					ratios[1] += 0.2f;
					ratios[2] += 0.1f;
					break;
				}
				int total_enemies = (int) Math.ceil(3 * round * multiplier);
				
				WaveStyle wave;
				if (round % 5 == 0) {
					wave = new ContinuousWave(ratios,total_enemies, round, multiplier);
				} else if (round % 3 == 0) {
					wave = new PairedWave(ratios, total_enemies, round, multiplier);
				} else {
					wave = new BurstWave(ratios, total_enemies, round, multiplier);
				}

				wave.generate();
				spawnWave(wave.getEnemies(), wave.getTimings());
			}
			else
			{
				inputStream.close();
				spawnWave(string_enemies, string_spawn_delays);
			}
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	private void spawnWave(String[] string_enemies, String[] string_spawn_delays) {
		enemies = new Enemy[string_enemies.length];
		float spawn_x = Playing.grid.spawn_x;
		float spawn_y = Playing.grid.spawn_y;
		float tile_size = Playing.grid.getTileSize();
		for (int i = 0; i < string_enemies.length; i++){
			if (string_enemies[i].equals("1")) {
				enemies[i] = new Enemy_1(
						spawn_x, 
						spawn_y, 
						tile_size);
			}
			if (string_enemies[i].equals("2")) {
				enemies[i] = new Enemy_2(
						spawn_x, 
						spawn_y, 
						tile_size);
			}
			if (string_enemies[i].equals("3")) {
				enemies[i] = new Enemy_3(
						spawn_x, 
						spawn_y, 
						tile_size);
			}
		}
		
		spawn_delays = new double[string_spawn_delays.length];
		for (int i = 0; i < string_spawn_delays.length; i++){
			spawn_delays[i] =  Float.parseFloat(string_spawn_delays[i]);
		}
	}

}
