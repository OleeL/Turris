package playing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import enemies.*;

/*
 * @author Oliver Legg - 201244658 - sgolegg
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
	
	private Enemy[] enemies;
	private double[] spawn_delays;

	public void produceWave(int round, String difficulty) {
		String[] string_enemies = null;
		String[] string_spawn_delays = null;
		try {
			File file = new File(difficulty);
			Scanner inputStream;
			inputStream = new Scanner(file);
			while (inputStream.hasNext()) {
				String raw_data = inputStream.next();
				if (raw_data == "round"+round) {
					raw_data = inputStream.next();
					string_enemies = raw_data.split(",");
					raw_data = inputStream.next();
					string_spawn_delays = raw_data.split(",");
					break;
				}
			}
			inputStream.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		enemies = new Enemy[enemies.length];
		float spawn_x = Playing.grid.spawn_x;
		float spawn_y = Playing.grid.spawn_y;
		float tile_size = Playing.grid.getTileSize();
		for (int i = 0; i < string_enemies.length; i++){
			if (string_enemies[i] == "1") {
				enemies[i] = new Enemy_1(
						spawn_x, 
						spawn_y, 
						tile_size);
			}
			if (string_enemies[i] == "2") {
				enemies[i] = new Enemy_2(
						spawn_x, 
						spawn_y, 
						tile_size);
			}
			if (string_enemies[i] == "3") {
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
