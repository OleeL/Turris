package engine.io;

import java.nio.DoubleBuffer;

import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.*;
/**
 * @author Team 62
 * 
 * Oliver Legg - sgolegg - 201244658
 *
 */
public class Window {
	private int width, height;
	private String title;
	private long window;
	private double fps_cap, time, processedTime = 0, fps = 0;
	private boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
	private boolean[] mouseButtons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
	
	public Window(int width, int height, int fps, String title)
	{
		this.width = width;
		this.height = height;
		this.title = title;
		fps_cap = fps;
	}
	
	public void create()
	{
		
		// Closes the program if GLFW didn't initialise correctly
		if (!GLFW.glfwInit())
		{
			System.err.println("Error: Couldn't initialise GLFW");
			System.exit(-1);
		}
		
		// Stops the window from being resizable
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
		
		// Creates the window with the given width, height, title, the monitor
		// it goes on and the share
		window = GLFW.glfwCreateWindow(width, height, title, 0, 0);
		
		// Error handling if the OS doesn't make the window right.
		if (window == 0)
		{
			System.out.println("Error: Window couldn't be created");
			System.exit(-1);
		}
		
		GLFW.glfwMakeContextCurrent(window);
		GL.createCapabilities();
		
		// Initialises the window in the middle of the monitor
		GLFWVidMode videoMode;
		videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwSetWindowPos(window, (videoMode.width() - width)/2, 
		                              (videoMode.height() - height)/2);
	
		// Shows the window when done
		GLFW.glfwShowWindow(window);
		
		// Sets the time
		time = getTime();
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity(); // Resets any previous projection matrices
		glOrtho(0, width, height, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glClear(GL_COLOR_BUFFER_BIT);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	// Returns whether the window should be closed
	public boolean closed()
	{
		return GLFW.glfwWindowShouldClose(window);
	}
	
	// The update loop where all the game processing will be handled
	public void update()
	{
		// Checks if all keys and mouse buttons are down
		for (int i = 0; i < GLFW.GLFW_KEY_LAST; i++) 
			keys[i] = isKeyDown(i);
		for (int i = 0; i < GLFW.GLFW_MOUSE_BUTTON_LAST; i++) 
			mouseButtons[i] = isMouseDown(i);
		
		// Removes images from the previous frame
		GLFW.glfwPollEvents(); 
	}
	
	// Renders all info to the screen
	public void swapBuffers()
	{
		GLFW.glfwSwapBuffers(window);
	}
	
	// Gets the time that this is processed
	public double getTime()
	{
		return (double) System.nanoTime() / (double) 1000000000;
	}
	
	// Returns whether
	public boolean isUpdating()
	{
		double nextTime = getTime();
		double passedTime = nextTime - time;
		processedTime += passedTime;
		time = nextTime;
		while (processedTime > 1.0 / fps_cap)
		{
			processedTime -= 1.0 / fps_cap;
			return true;
		}
		return false;
	}
	
	// Gets the width of the screen
	public int getWidth() {
		return width;
	}

	// Gets the height of the screen
	public int getHeight() {
		return height;
	}

	// Gets the title of the game
	public String getTitle() {
		return title;
	}

	// Gets the window
	public long getWindow() {
		return window;
	}

	// Gets the max frame rate
	public double getMaxFps() {
		return fps_cap;
	}
	
	// Gets the frame rate
	public double getFPS()
	{
		return processedTime;
	}

	// Returns true if a certain key is down
	public boolean isKeyDown(int keycode)
	{
		return GLFW.glfwGetKey(window, keycode) == 1;
	}
	
	// Clears the screen of open GL graphics
	public void clear()
	{
		glClear(GL_COLOR_BUFFER_BIT);
	}
	
	// Sets the colour of drawing vector graphics
	public void setColour(int r, int g, int b, float a)
	{
		GL11.glColor4f(r, g, b, a);
	}
	
	// Returns true if a certain mouse button is down
	// 0 = lmb
	// 1 = rmb
	public boolean isMouseDown(int mouseButton)
	{
		return GLFW.glfwGetMouseButton(window, mouseButton) == 1;
	}
	
	// If a certain key is down, return true
	public boolean isKeyPressed(int keyCode)
	{
		return isKeyDown(keyCode) && !keys[keyCode];
	}
	
	// If a certain key is just released, return true
	public boolean isKeyReleased(int keyCode)
	{
		return !isKeyDown(keyCode) && keys[keyCode];
	}
	
	// If a mouse btn is down, return true
	public boolean isMousePressed(int mouseButton)
	{
		return isMouseDown(mouseButton) && !mouseButtons[mouseButton];
	}
	
	// If a mouse btn is just released, return true
	public boolean isMouseReleased(int mouseButton)
	{
		return !isMouseDown(mouseButton) && mouseButtons[mouseButton];
	}
	
	// Gets the mouse x position
	public double getMouseX()
	{
		DoubleBuffer buffer = BufferUtils.createDoubleBuffer(1);
		GLFW.glfwGetCursorPos(window, buffer, null);
		return buffer.get(0);
	}
	
	// Gets the mouse y position
	public double getMouseY()
	{
		DoubleBuffer buffer = BufferUtils.createDoubleBuffer(1);
		GLFW.glfwGetCursorPos(window, null, buffer);
		return buffer.get(0);
	}
	
	public void rectangle(float x, float y, float w, float h) {
        glRectf(x, y, x+w, y+h);
	}

	public void DrawRoundRect( 
			float x, 
			float y, 
			float width, 
			float height,
			float radius )
	{
		glBegin(GL_POLYGON);

		// top-left corner
		DrawGLRoundedCorner(x, y + radius, 3 * Math.PI / 2, Math.PI / 2, radius);

		// top-right
		DrawGLRoundedCorner(x + width - radius, y, 0.0, Math.PI / 2, radius);

		// bottom-right
		DrawGLRoundedCorner(x + width, y + height - radius, Math.PI / 2, Math.PI / 2, radius);

		// bottom-left
		DrawGLRoundedCorner(x + radius, y + height, Math.PI, Math.PI / 2, radius);

		glEnd();
	}
	
	public void DrawGLRoundedCorner(float x, float y, double sa, double arc, float r) {
	    // centre of the arc, for clockwise sense
	    float cent_x = (float) (x + r * Math.cos(sa + Math.PI / 2));
	    float cent_y = (float) (y + r * Math.sin(sa + Math.PI / 2));

	    int N_ROUNDING_PIECES = 64;
	    // build up piecemeal including end of the arc
	    int n = (int) Math.ceil(N_ROUNDING_PIECES * arc / Math.PI * 2);
	    for (int i = 0; i <= n; i++) {
	        double ang = sa + arc * (double)i  / (double)n;

	        // compute the next point
	        float next_x = (float) (cent_x + r * Math.sin(ang));
	        float next_y = (float) (cent_y - r * Math.cos(ang));
	        glVertex2f(next_x, next_y);
	    }
	}
}