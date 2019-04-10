/**
 * @author Team 62
 * 
 * Oliver Legg - sgolegg - 201244658
 *
 */
package gui;

import static org.lwjgl.opengl.GL11.*;

import engine.io.Image;

public class Texture {
	private int id;
	private float x, y, w, h;;
	private Image texture;
	private String filename;
	
	/**
	 * The following parameters to enter:
	 * @param Texture texture 
	 * @param integer x position 
	 * @param integer y position 
	 * @param float x scale position (0.0 - 1.0)
	 * @param float y scale position (0.0 - 1.0)
	 */
	public Texture(String filename, int x, int y, float w, float h) {
		this.filename = filename;
		id = glGenTextures();
		texture = Image.loadImage("./assets/images/"+filename);
		this.x = x;
		this.y = y;
		this.w = texture.getWidth() * w;
		this.h = texture.getHeight() * h;
		
		// Binds the texture to the id.
		glBindTexture(GL_TEXTURE_2D, id);
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, 
				texture.getWidth(), texture.getHeight(), 
				0, GL_RGBA, GL_UNSIGNED_BYTE, texture.getImage());
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

		// Binds the texture to the id.
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	public float getWidth() {
		return w;
	}

	public float getHeight() {
		return h;
	}
	
	public void setX(float x){
		this.x = x;
	}
	
	public void setY(float y){
		this.y = y;
	}	
	
	public float getX(){
		return x;
	}
	
	public float getY()	{
		return y;
	}

	public void draw(){
		//Makes sure that the current colour doesn't change the texture drawing
		float colour[] = new float[4];
		glGetFloatv(GL_CURRENT_COLOR, colour);
		glColor4f(1, 1, 1, 1);
		
		// Binds the texture to the id.
		glBindTexture(GL_TEXTURE_2D, id);
		
		// Draws the texture
		//glTranslatef(x, y, 0);
		glBegin(GL_QUADS);
			
			// Top Left
			glTexCoord2f(0, 0);
	        glVertex2f(x, y);
	
	        // Top right
	        glTexCoord2f(1, 0);
	        glVertex2f(x + w, y);
	
	        // Bottom right
	        glTexCoord2f(1,1);
	        glVertex2f(x + w, y + h);
	
	        // Bottom Left
	        glTexCoord2f(0,1);
	        glVertex2f(x, y + h);
	        
	        glLoadIdentity();
		glEnd();

		// Binds the texture to the id.
		glBindTexture(GL_TEXTURE_2D, 0);
		
		// Sets it back to it's original colour
		glColor4f(colour[0], colour[1], colour[2], colour[3]);
	}

}
