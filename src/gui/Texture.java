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
	
	/**
	 * The following parameters to enter:
	 * @param Texture texture 
	 * @param integer x position 
	 * @param integer y position 
	 * @param float x scale position (0.0 - 1.0)
	 * @param float y scale position (0.0 - 1.0)
	 */
	public Texture(String filename, float x, float y, float w, float h) {
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

	// Sets the width of the texture
	public float getWidth() {
		return w;
	}

	// Sets the height of the texture
	public float getHeight() {
		return h;
	}
	
	// Sets the X coordinate
	public void setX(float x){
		this.x = x;
	}
	
	// Sets the Y coordinate
	public void setY(float y){
		this.y = y;
	}	
	
	// Gets the X coordinate
	public float getX(){
		return x;
	}
	
	// Gets the Y coordinate
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
	
	public void draw_2(float rotation) {
		//Makes sure that the current colour doesn't change the texture drawing
		float colour[] = new float[4];
		glGetFloatv(GL_CURRENT_COLOR, colour);
		glColor4f(1, 1, 1, 1);
		
		// Binds the texture to the id.
		glBindTexture(GL_TEXTURE_2D, id);
		glPushMatrix(); //Save the current matrix.
		//Change the current matrix.
		glTranslatef(x+(w/2), y+(h/2), 0);
		glRotatef(rotation, 0, 0, 1);
		
		glBegin(GL_QUADS);
		
		// Top Left
		glTexCoord2d(0,0);
		glVertex2f(-w/2, -h/2);

		// Top Right
		glTexCoord2d(1,0);
		glVertex2f(w/2, -h/2);

		// Bottom Right
		glTexCoord2d(1,1); 
		glVertex2f(w/2, h/2);

		// Bottom Left
		glTexCoord2d(0,1);
		glVertex2f(-w/2, h/2);

		glEnd();

		//Reset the current matrix to the one that was saved.
		glPopMatrix();
		// Binds the texture to the id.
		glBindTexture(GL_TEXTURE_2D, 0);
		
		// Sets it back to it's original colour
		glColor4f(colour[0], colour[1], colour[2], colour[3]);
	}

}
