package playing;

import gui.Texture;

/**
 * @author Team 62
 * 
 * Oliver Legg - sgolegg - 201244658
 *
 */
public class Entity {

	protected Texture texture;
	protected float x, y, w, h;
	protected String name;
	
	/**
	 * @param String name
	 * @param Texture texture
	 * @param float x position 
	 * @param float y position 
	 */
	public Entity(String name, Texture texture, float x, float y){
		this.name = name;
		this.texture = texture;
		this.x = x;
		this.y = y;
		this.w = texture.getWidth();
		this.h = texture.getHeight();
		
	}
	
	public void draw() {
	}
	
	// Accessor methods:
	public float getX(){
		return x;
	}

	public float getY() {
		return y;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public String getName(){
		return name;
	}
	
}
