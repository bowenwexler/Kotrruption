

import org.lwjgl.glfw.GLFW;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALCCapabilities;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class UI {
	
	private long window;
	private long audioDevice;
	private int width;
	private int height;
	
	public void init(int width, int height, String title)
	{
		
		// initialize graphics
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");
		
		window = glfwCreateWindow(width, height, title, NULL, NULL);
		

		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
	
		
		//set up OpenGL
		glfwMakeContextCurrent(window);
		GL.createCapabilities();
		glfwSwapInterval(1);
		
		
		
		
		// set projection to dimensions of window
        // set viewport to entire window
        GL11.glViewport(0,0,width,height);
         
        // set up orthographic projection to map world pixels to screen
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, width, height, 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        
    	GL11.glEnable(GL11.GL_TEXTURE_2D);
    	GL11.glEnable(GL11.GL_BLEND);
    	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

    	// initialize audio system
    	audioDevice = ALC10.alcOpenDevice((ByteBuffer)null);
    	ALCCapabilities deviceCaps = ALC.createCapabilities(audioDevice);

    	long context = ALC10.alcCreateContext(audioDevice, (IntBuffer)null);
    	ALC10.alcMakeContextCurrent(context);
    	AL.createCapabilities(deviceCaps);    	
        this.width=width;
        this.height=height;
        
        glfwShowWindow(window);

	}
	
	public void showMouseCursor(boolean show)
	{
		if (show)
		{
			GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);	
		}
		else
		{
			GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_HIDDEN);
			
		}
	}
	
	public void enableMouseCursor(boolean enable)
	{
		if (enable)
		{
			GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);	
		}
		else
		{
			GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
			
		}
	}
	
	public XYPair<Integer> getMouseLocation()
	{
		double[] x = new double[1];
		double[] y = new double[1];
		GLFW.glfwGetCursorPos(window,  x,  y);
		return new XYPair<Integer>((int)x[0],(int)y[0]);
	}
	
	public boolean mouseButtonIsPressed(int button)
	{
		return GLFW.glfwGetMouseButton(window, button) == GLFW.GLFW_PRESS;
	}
	
	public long getWindow() { return window; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	
	public boolean keyPressed(int key) { 
		return glfwGetKey(window, key) == GLFW_PRESS;
	}
	
	public void destroy(){
		ALC10.alcCloseDevice(audioDevice);
		ALC.destroy();


		// these cleanup calls are supposedly desirable, but they crash in my tests
		org.lwjgl.glfw.Callbacks.glfwFreeCallbacks(window);
		GLFW.glfwDestroyWindow(window);
		GLFW.glfwTerminate();
	}
	
	
	

}
