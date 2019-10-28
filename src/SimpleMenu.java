

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import java.util.LinkedList;

public class SimpleMenu implements Scene {

	public static interface SelectableObject
	{
		void select();
		void deselect();
		void update(int delta);
		void draw();
	}

	public static class SelectableText  extends Text implements SelectableObject
	{
		private float activeR, activeG, activeB;
		private float inactiveR, inactiveG, inactiveB;

		public SelectableText(int x, int y, int w, int h, String text, 
				float aR, float aG, float aB, float iR, float iG, float iB)
		{
			super(x,y,w,h,text);
			activeR=aR;
			activeG=aG;
			activeB=aB;
			inactiveR=iR;
			inactiveG=iG;
			inactiveB=iB;
		}

		public void select()
		{
			this.setColor(activeR, activeG, activeB);
		}

		public void deselect()
		{
			this.setColor(inactiveR, inactiveG, inactiveB);
		}


	}

	public void Title()
	{
		
	}
	
	private enum colorStates {TO_WHITE, TO_RED, TO_GREEN, TO_BLUE};

    public class ColorChangeText extends Text
    {
        colorStates color = colorStates.TO_RED;
        private boolean reddening = false;
        public ColorChangeText(int x, int y, int w, int h, String text){
            super(x,y,w,h, text);
        }

        public void update(int delta){
            float rate = 1.5f;
            switch (color){
                case TO_WHITE:
                    if (this.r >= 0.9 && this.g >= 0.9 && this.b >= 0.9){
                        this.color = colorStates.TO_RED;
                    }
                    else {
                        this.r += (delta*rate)/255f;
                        this.g += (delta*rate)/255f;
                    }
                    break;
                case TO_RED:
                    if (this.r >= 0.9 && this.g <= 0.1 && this.b <= 0.1){
                        this.color = colorStates.TO_GREEN;
                    }
                    else {
                        this.b -= (delta*rate)/255f;
                        this.g -= (delta*rate)/255f;
                    }
                    break;
                case TO_GREEN:
                    if (this.g >= 0.9 && this.r <= 0.1 && this.b <= 0.1){
                        this.color = colorStates.TO_BLUE;
                    }
                    else {
                        this.r -= (delta*rate)/255f;
                        this.g += (delta*rate)/255f;
                    }
                    break;
                case TO_BLUE:
                    if (this.b >= 0.9 && this.r <= 0.1 && this.g <= 0.1){
                        this.color = colorStates.TO_WHITE;
                    }
                    else {
                        this.b += (delta*rate)/255f;
                        this.g -= (delta*rate)/255f;
                    }
                    break;
                default:
                    color = colorStates.TO_WHITE;
                    break;
            }
        }
    }
	
	private class Item
	{
		public SelectableObject label;
		public Scene scene;

		public Item(SelectableObject label, Scene scene)
		{
			this.label=label;
			this.scene=scene;
		}

	}
	
	private class Background extends GameObject
	{
		private Texture bg = new Texture("res/kotblini.png",0.1f);
		
		public Background(int width, int height, int x, int y)
		{
			this.hitbox.setSize(width,height);
			this.hitbox.setLocation(x, y);
			this.setColor(0f,0f,0f,.5f);
		}
		
		public void draw()
		{
			bg.draw(this);
		}
		
	}

	private LinkedList<Item> items;
	private Background menuBackground;
	private ColorChangeText menuTitle;
	private int selected;
	private boolean go=false;

	public SimpleMenu()
	{
		items=new LinkedList<>();
		menuTitle = new ColorChangeText(185, 280, 40, 40, "Kotrruption");
		menuBackground = new Background(900,900, -130, -100);
		selected=0;
		go=false;
	}

	public void reset()
	{
		go=false;
		select(0);
	}

	public void addItem(SelectableObject label, Scene scene)
	{
		items.add(new Item(label, scene));
	}

	public void select(int p)
	{
		items.get(selected).label.deselect();
		items.get(p).label.select();
		selected=p;
	}

	public void go()
	{
		go=true;
	}
	
	public void onKeyEvent(int key, int scancode, int action, int mods)  
	{
		if (action==org.lwjgl.glfw.GLFW.GLFW_PRESS)
		{
			if (key == org.lwjgl.glfw.GLFW.GLFW_KEY_UP)
			{
				select((selected+items.size()-1)%items.size());
			}
			else if (key == org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN)
			{
				select((selected+1)%items.size());
			}
			else if (key == org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER)
			{
				go();
			}
		}
		
	};

	

	public Scene drawFrame(int delta)
	{
		glClearColor(.0f, .0f, .0f, .0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

		if (go) { return items.get(selected).scene; }

		for (Item item : items)
		{	
			item.label.update(delta);
			item.label.draw();
		}
		menuTitle.update(delta);
		menuTitle.draw();
		menuBackground.draw();

		return this;

	}

}
