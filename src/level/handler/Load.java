package level.handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import playing.Entity;
import turrets.*;

/**
 * @author Team 62
 * 
 * Oliver Legg - 201244658 - sgolegg
 *
 */
public class Load {

	private static String FILENAME = "./assets/saves/save.csv";
	
	// Game / Level Type
	public int difficulty, round, grid_size;
	public String level;
	public boolean continuousMode;
	
	// Game Dependent Stats
	public int coins, lives;
	
	// Statistics
	public int revenue, kills, arrows_fired, b_upgraded, b_built;
	
	// Turrets
	public ArrayList<Entity> turrets = new ArrayList<Entity>();
	
	// For loading the game
	public void load() {
		try {
			File file = new File(FILENAME);
			Scanner inputStream;
			inputStream = new Scanner(file);
			String data;
			String[] values;
			
			// Line 1 (Game / Level Type)
			data = inputStream.next();
			values = data.split(",");
			difficulty = Integer.parseInt(values[0]);
			round = Integer.parseInt(values[1]);
			level = values[2];
			grid_size = Integer.parseInt(values[3]);
			continuousMode = Boolean.parseBoolean(values[4]);
			
			// Line 2 (Game Dependent Stats)
			data = inputStream.next();
			values = data.split(",");
			coins = Integer.parseInt(values[0]);
			lives = Integer.parseInt(values[1]);
			
			// Line 3 (Statistics)	
			data = inputStream.next();
			values = data.split(",");
			revenue = Integer.parseInt(values[0]);
			kills = Integer.parseInt(values[1]);
			arrows_fired = Integer.parseInt(values[2]);
			b_upgraded = Integer.parseInt(values[3]);
			b_built = Integer.parseInt(values[4]);			
			
			// Until EOF (Turrets)
			while (inputStream.hasNext()) {
				data = inputStream.next();
				values = data.split(",");
				int turret = Integer.parseInt(values[0]);
				int level  = Integer.parseInt(values[1]);
				int x = Integer.parseInt(values[2]);
				int y = Integer.parseInt(values[3]);
				Entity t = null;
				switch (turret) {
					case 1:
						t = new Turret_1(x, y, grid_size);
						break;
					case 2:
						t = new Turret_2(x, y, grid_size);
						break;
					case 3:
						t = new Turret_3(x, y, grid_size);
						break;
				}
				turrets.add(t);
				for (int i = 0; i < level; i++)
					((Turret) turrets.get(turrets.size()-1)).upgrade();
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
