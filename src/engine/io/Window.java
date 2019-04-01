package engine.io;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

/**
 * @author Team 62
 * 
 * Oliver Legg
 *
 */
public class Window {
	private int width, height;
	private String title;
	private long window;
	private boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
	private boolean[] mouseButtons = new boolean[
	                                              GLFW.GLFW_MOUSE_BUTTON_LAST];
	
	public Window(int width, int height, String title)
	{
		this.width = width;
		this.height = height;
		this.title = title;
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
		
		// Initialises the window in the middle of the monitor
		GLFWVidMode videoMode;
		videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwSetWindowPos(window, (videoMode.width() - width)/2, 
		                              (videoMode.height() - height)/2);
	
		GLFW.glfwShowWindow(window);
	}
	
	// Returns whether the window should be closed
	public boolean closed()
	{
		return GLFW.glfwWindowShouldClose(window);
	}
	
	// The update loop where all the game processing will be handled
	public void update()
	{
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
	
	// Returns true if a certain key is down
	public boolean isKeyDown(int keycode)
	{
		return GLFW.glfwGetKey(window, keycode) == 1;
	}
	
	// Returns true if a certain mouse button is down
	// 0 = lmb
	// 1 = rmb
	public boolean isMouseDown(int mouseButton)
	{
		return GLFW.glfwGetMouseButton(window, mouseButton) == 1;
	}
	
	public boolean isKeyPressed(int keyCode)
	{
		return isKeyDown(keyCode) && !keys[keyCode];
	}
	
	public boolean isKeyReleased(int keyCode)
	{
		return !isKeyDown(keyCode) && keys[keyCode];
	}
	
	public boolean isMousePressed(int mouseButton)
	{
		return isMouseDown(mouseButton) && !mouseButtons[mouseButton];
	}
	
	public boolean isMouseReleased(int mouseButton)
	{
		return !isMouseDown(mouseButton) && mouseButtons[mouseButton];
	}
	
	public double getMouseX()
	{
		DoubleBuffer buffer = BufferUtils.createDoubleBuffer(1);
		GLFW.glfwGetCursorPos(window, buffer, null);
		return buffer.get(0);
	}
	
	public double getMouseY()
	{
		DoubleBuffer buffer = BufferUtils.createDoubleBuffer(1);
		GLFW.glfwGetCursorPos(window, null, buffer);
		return buffer.get(0);
	}
}
