
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwTerminate;



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

}