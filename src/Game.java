
import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWKeyCallback;



import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;



public abstract class Game {
	
	public static final UI ui = new UI();
	protected static Scene currScene;
	
	public void initUI(int width, int height, String title)
	{
		ui.init(width, height, title);
	}
	
	public void setScene(Scene s) 
	{
		currScene = s;
	}
	
	public void gameLoop()
	{
		if (currScene==null) { currScene = (Scene)this; }

		float time = (float)glfwGetTime();
		// Run the rendering loop until the user has attempted to close
		// the window
		while (  currScene != null && !glfwWindowShouldClose(ui.getWindow()) ) {

			glfwPollEvents();
			float time2=(float)glfwGetTime();
			currScene = currScene.drawFrame((int)(1000*(time2-time)));
			glfwSwapBuffers(ui.getWindow());
			time=time2;

		}
		ui.destroy();
	}
	
	// call this function on the game that manages gameloop() to catch events in the current scene
	public void registerGlobalCallbacks()
	{
		GLFW.glfwSetMouseButtonCallback(Game.ui.getWindow(), 
				new GLFWMouseButtonCallback()
				{
					public void invoke(long window, int button, int action, int mods)
					{
						currScene.onMouseEvent(button,  action,  mods);
					}
				});

		GLFW.glfwSetKeyCallback(Game.ui.getWindow(),
				new GLFWKeyCallback()
				{
					public void invoke(long window, int key, int scancode, int action, int mods)
					{
						currScene.onKeyEvent(key, scancode, action, mods);
					}
				});
				
		
	}

}