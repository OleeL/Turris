/**
 * @author Team 62
 * 
 * Oliver Legg - sgolegg - 201244658
 *
 */

package gui;

import engine.io.Font;

public class Text {
	
	public static final int ALIGN_CENTRE = 0;
	public static final int ALIGN_LEFT = 1;
	public static final int ALIGN_RIGHT = 2;

	public String text;
	public float x, y;
	public int size;
	private Font font;
	
	public Text(String text, float x, float y, int size)
	{
		this.text = text;
		this.x = x;
		this.y = y;
		
		// Creating the font        
        try {
			font = new Font("./assets/fonts/monof55.ttf", size);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}	
	
	public Font getFont() {
		return font;
	}
	
	public String getText()
	{
		return text;
	}

	public void setFont(String fontName, int fontStyle, int size)
	{
		try {
			try {
				font = new Font(fontName, size);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public void draw()
	{
		font.drawText(text, x, y);
	}
	
	public void setPosition(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
}
