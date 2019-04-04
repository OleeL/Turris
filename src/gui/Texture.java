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
	private int id, x, y, w, h;
	
	public Texture(String filename, int x, int y) {
		id = glGenTextures();
		Image texture = Image.loadImage("./assets/images/"+filename+".png");
		this.x = x;
		this.y = y;
		this.w = texture.getWidth();
		this.h = texture.getHeight();
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, 
				texture.getWidth(), texture.getHeight(), 
				0, GL_RGBA, GL_UNSIGNED_BYTE, texture.getImage());
		// Binds the texture to the id.
		glBindTexture(GL_TEXTURE_2D, id);
	}
	
	

	public void draw()
	{
		// Binds the texture to the id.
		glBindTexture(GL_TEXTURE_2D, id);
		
		// Draws the texture
		glBegin(GL_QUADS);
			// Top Left
	        glTexCoord2f(x, y);
	        glVertex3f(x, y, 0);
	
	        // Top right
	        glTexCoord2f(x + w, y);
	        glVertex3f(x + w, y, 0);
	
	        // Bottom right
	        glTexCoord2f(x + w, y + h);
	        glVertex3f(x + w, y + h, 0);
	
	        // Bottom Left
	        glTexCoord2f(x, y + h);
	        glVertex3f(x, y + h, 0);
		glEnd();
	}
}
