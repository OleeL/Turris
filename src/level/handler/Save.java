package level.handler;

import java.io.*;
import java.util.ArrayList;

import turrets.Turret;

/**
 * @author Team 62
 * 
 * Oliver Legg - 201244658 - sgolegg
 *
 */
public class Save {

	private static String FILENAME = "./assets/saves/save.csv";
	private static char COMMA = ',';
	private static char NEWLINE = '\n';
	
	// Game / Level Type
	private int difficulty, round;
	private float grid_size;
	private String level;
	private boolean continuousMode;
	
	// Game Dependent Stats
	private int coins, lives;
	
	// Statistics
	private int revenue, kills, arrows_fired, b_upgraded, b_built;
	
	// Turrets
	private ArrayList<Turret> turrets = new ArrayList<Turret>();
	
	public Save(float grid_size, int difficulty, String level, boolean cont) {
		this.grid_size = grid_size;
		this.difficulty = difficulty;
		this.level = level;
		this.continuousMode = cont;
	}

	public void keepVariables(
			int round,
			int coins,
			int lives,
			int coins_revenue,
			int kills,
			int arrows_fired,
			int buildings_upgraded,
			int buildings_built,
			ArrayList<Turret> turrets 
			) {
		this.round = round;
		this.coins = coins;
		this.lives = lives;
		this.revenue = coins_revenue;
		this.kills = kills;
		this.arrows_fired = arrows_fired;
		this.b_upgraded = buildings_upgraded;
		this.b_built = buildings_built;		
		this.turrets = turrets;
	}
	
	public void write() {
	    try (PrintWriter writer = new PrintWriter(new File(FILENAME))) {

	        StringBuilder sb = new StringBuilder();
	        sb.append(difficulty);
	        sb.append(COMMA);
	        sb.append(round);
	        sb.append(COMMA);
	        sb.append(NEWLINE);

	        sb.append("1");
	        sb.append(COMMA);
	        sb.append("Prashant Ghimire");
	        sb.append(NEWLINE);

	        writer.write(sb.toString());

	        System.out.println("done!");

	      } catch (FileNotFoundException e) {
	        System.out.println(e.getMessage());
	      }  
	}
}
