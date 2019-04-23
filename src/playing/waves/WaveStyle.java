package playing.waves;

/**
 * @author Kieran Baker - 201234727 - sgkbaker
 * 
 */

abstract class WaveStyle {
	protected int total_enemies;
	protected float[] ratios;
	protected int round;
	protected float multiplier;
	protected int[] enemy_split;
	protected int[] available;
	protected String[] enemies;
	protected String[] timings;
	
	public WaveStyle(float[] ratios,int total_enemies, int round, float multiplier) {
		this.ratios = ratios;
		this.enemy_split = new int[3];
		this.round = round;
		this.multiplier = multiplier;
		available = new int[3];
		int count = 0;
		for (int i = 0;i<enemy_split.length;i++) {
			enemy_split[i] = (int) Math.ceil(ratios[i] * total_enemies);
			count += enemy_split[i];
			available[i] = enemy_split[i];
		}
		this.total_enemies = count;
		enemies = new String[count];
		timings = new String[count];
	}
	
	public abstract void generate();
	
	public void add(String enemy, int amount, int start_index) {
		for (int i = start_index;i<start_index + amount;i++) {
			enemies[i] = enemy;
			switch(enemy) {
			case "1":
				timings[i] = String.valueOf(Math.max(0.05, 0.2 * 1/multiplier - round/1000));
				break;
			case "2":
				timings[i] = String.valueOf(Math.max(0.05, 0.3 * 1/multiplier - round/1000));
				break;
			case "3":
				timings[i] = String.valueOf(Math.max(0.05, 0.4 * 1/multiplier - round/1000));
				break;
			}
		}
	}
	
	public String[] getEnemies() {
		return enemies;
	}
	
	public String[] getTimings() {
		return timings;
	}
}
