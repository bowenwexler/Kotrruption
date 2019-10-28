
import org.lwjgl.opengl.GL11;
import java.awt.Rectangle;

public  class GameObject {
	
	private boolean active=true;
	
	protected float r;
	protected float g;
	protected float b;
	protected float a;
	
	protected Rectangle hitbox=new Rectangle();

	protected void setColor(float r, float g, float b, float a) {
		this.r=r;
		this.g=g;
		this.b=b;
		this.a=a;
	}
	
	protected void setTexture(String path) {
		
	}
	
	public boolean isActive() { return active; }
	public void deactivate() { active=false; }
	
	public boolean intersects(GameObject other)
	{
		return hitbox.intersects(other.hitbox);
	}
	
	public Rectangle intersection(GameObject other)
	{
		return hitbox.intersection(other.hitbox);
	}
	
	public Rectangle getHitbox()
	{
		return hitbox;
	}
	
    public void update(int delta) { }
    
    public void draw() { 
    	
    	GL11.glColor4f(r,g,b,a);
    	
    	float x=(float)hitbox.getX();
    	float y=(float)hitbox.getY();
    	float width=(float)hitbox.getWidth();
    	float height=(float)hitbox.getHeight();
    	

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x+width, y);
        GL11.glVertex2f(x+width, y+height);
        GL11.glVertex2f(x, y+height);
        GL11.glEnd();
    }
     
}
