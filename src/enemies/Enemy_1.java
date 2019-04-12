package enemies;

public class Enemy_1 extends Enemy{
	private static final String IDLE = "enemy/enemy_1";
	private static final String RFF  = "enemy/enemy_2";
	private static final String LFF  = "enemy/enemy_3";
	
	public Enemy_1(float x, float y, float grid_size) {
		super(IDLE, x, y, grid_size);
	}
	
	
	public void update() {
		
	}	
}
