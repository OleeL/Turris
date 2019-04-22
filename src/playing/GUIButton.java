package playing;

import gui.Text;
import main.Main;

/**
 * @author Team 62
 * 
 * Oliver Legg - sgolegg - 201244658
 *
 */
public class GUIButton {
	private Text text;
	public float x, y, w, h;
	private int code;
	public final float[] DEFAULT_BUTTON_COLOUR = { 0.0f, 0.0f, 0.0f, 0.5f };
	public final float[] FONT_COLOUR           = { 1.0f, 1.0f, 1.0f, 1.0f };
	public final float[] DEFAULT_HOVER_COLOUR  = { 0.3f, 0.3f, 0.3f, 0.5f };
	private float[] button_colour = DEFAULT_BUTTON_COLOUR;
	private float[] hover_colour  = DEFAULT_HOVER_COLOUR;
	private boolean hover;
	
	public GUIButton(String t, float w, float h, int code) {
		this.w = w;
		this.h = h;
		this.code = code;
		hover = false;
		text = new Text(t, (int)x, (int)y, 11);
	}
	
	public int update(){
		double mx = Main.window.getMouseX();
		double my = Main.window.getMouseY();
		
		// If mouse hovers over the button
		if (mx > x && mx < x+w && my > y && my < y+h){
			button_colour = hover_colour;
			hover = true;
			// If mouse is clicked
			if (Main.window.isMousePressed(Main.window.LEFT_MOUSE)){
				return code;
			}
		}
		else
		{
			hover_colour  = DEFAULT_HOVER_COLOUR;
			button_colour = DEFAULT_BUTTON_COLOUR;
			hover = false;
		}
		
		return -1;
	}
	
	public void draw() {
		Main.window.setColour(button_colour);
		Main.window.rectangle(x, y, w, h);
		Main.window.setColour(FONT_COLOUR);
		text.draw();
	}
	
	public void setPosition(float x, float y){
		this.x = x;
		this.y = y;
		float text_w = text.getFont().getTextWidth(text.getText());
		float text_h = text.getFont().getCharHeight();
		text.setPosition(
				(int) ((x+(w/2)) - (text_w/2)), 
				(int) ((y+(h/2)) - (text_h/2)));
	}
	
	public boolean getHover() {
		return hover;
	}
	
	public Text getText() {
		return text;
	}
	
	public void setTempColour(float[] rgba) {
		button_colour = rgba;
	}
	
	public void setTempColour(float r, float g, float b, float a) {
		button_colour[0] = r;
		button_colour[1] = g;
		button_colour[2] = b;
		button_colour[3] = a;
	}	
	
	public void setTempHoverColour(float r, float g, float b, float a) {
		hover_colour[0] = r;
		hover_colour[1] = g;
		hover_colour[2] = b;
		hover_colour[3] = a;
	}
	
	public void setTempHoverColour(float[] rgba) {
		hover_colour = rgba;
	}

}
