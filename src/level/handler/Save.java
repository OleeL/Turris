package level.handler;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import turrets.*;

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

	// Keeps the following variables at the start of the round
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
	
	// Writes the variables to save.csv
	public void write() {
		try (PrintWriter writer = new PrintWriter(new File(FILENAME))) {
	    	Pattern p = Pattern.compile("-?\\d+");
	    	Matcher level_compress = p.matcher(level);
	    	level_compress.find();
	        StringBuilder sb = new StringBuilder();
	        sb.append(difficulty);
	        sb.append(COMMA);
	        sb.append(round);
	        sb.append(COMMA);
	        sb.append(grid_size);
	        sb.append(COMMA);
	        sb.append("level_"+level_compress.group());
	        sb.append(COMMA);
	        sb.append(continuousMode);
	        sb.append(NEWLINE);
	        
	        sb.append(coins);
	        sb.append(COMMA);
	        sb.append(lives);
	        sb.append(NEWLINE);
	        
	        sb.append(revenue);
	        sb.append(COMMA);
	        sb.append(kills);
	        sb.append(COMMA);
	        sb.append(arrows_fired);
	        sb.append(COMMA);
	        sb.append(b_upgraded);
	        sb.append(COMMA);
	        sb.append(b_built);
	        sb.append(NEWLINE);
	        
	        for (int i = 0; i < turrets.size(); i++) {
	        	if (turrets.get(i) instanceof Turret_1) {
	    	        sb.append(1);
	        	}
	        	else if (turrets.get(i) instanceof Turret_2) {
	    	        sb.append(2);
	        	}
	        	else if (turrets.get(i) instanceof Turret_3) {
	    	        sb.append(3);
	        	}
    	        sb.append(COMMA);
    	        sb.append(turrets.get(i).getTurretLevel());
    	        sb.append(COMMA);
    	        sb.append(turrets.get(i).getX());
    	        sb.append(COMMA);
    	        sb.append(turrets.get(i).getY());
    	        sb.append(NEWLINE);
	        }	        

	        writer.write(sb.toString());
	      } catch (FileNotFoundException e) {
	        System.out.println(e.getMessage());
	      }  
	}
	
	// When you die, this should be called as it saves the fact that you lost.
	public void delete() {
		// Coins and lives depends on the difficulty
		lives = 0;
	}
}
