package playing.waves;

/**
 * @author Kieran Baker - 201234727 - sgkbaker
 * 
 */

public class BurstWave extends WaveStyle{
	float[] bursts;
	
	public BurstWave(float[] ratios, int total_enemies) {
		super(ratios, total_enemies);
		bursts = new float[] {0.25f,0.25f,0.5f};
	}
	
	public void generate() {
		int index = 0;
		int[] split = new int[3];
		for (int n = 0;n < bursts.length;n++) {
			for (int i = 0; i<split.length;i++) {
				split[i] = (int) Math.ceil(enemy_split[i] * bursts[n]);
				available[i] -= split[i];
				if (available[i] < 0) {
					split[i] += available[i];
				}
				add(String.valueOf(i + 1), split[i], index);
				index += split[i];
			}
		}


	}
}
