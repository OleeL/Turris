package playing.waves;

/**
 * @author Kieran Baker - 201234727 - sgkbaker
 * 
 */

public class PairedWave extends WaveStyle{
	private static final int size = 2;
	
	public PairedWave(float[] ratios, int total_enemies, int round, float multiplier) {
		super(ratios, total_enemies, round, multiplier);
	}
	
	public void generate() {
		int index = 0;
		while (enemies.length - index > 0) {
			for (int i = 0; i<enemy_split.length;i++) {
				if (enemy_split[i] != 0) {
					if (enemy_split[i] - size < 0) {
						add(String.valueOf(i + 1), enemy_split[i], index);
						index += enemy_split[i];
						enemy_split[i] = 0;
					} else {
						add(String.valueOf(i + 1), size, index);
						index += size;
						enemy_split[i] -= size;
					}
				}
			}
		}


	}

}
