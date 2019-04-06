package playing;

import gui.Texture;

/**
 * @author Team 62
 * 
 * Oliver Legg - sgolegg - 201244658
 *
 */
public class Entity {

	private Texture texture;
	private float x;
	private float y;
	private String name;
	public Entity(String name, Texture texture, float x, float y){
		this.name = name;
		this.texture = texture;
		this.x = x;
		this.y = y;
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
	
	public String getName()
	{
		return name;
	}
	
}
