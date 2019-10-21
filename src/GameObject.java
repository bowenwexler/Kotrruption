
import org.lwjgl.opengl.GL11;
import java.awt.Rectangle;

public  class GameObject {
	
	private boolean active=true;
	
	protected float r;
	protected float g;
	protected float b;
	
	public Rectangle hitbox=new Rectangle();

	protected void setColor(float r, float g, float b) {
		this.r=r;
		this.g=g;
		this.b=b;
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
    	
    	GL11.glColor3f(r,g,b);
    	
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
    
    public double getX()
    {
    	return hitbox.getX();
    }

    public double getY()
    {
    	return hitbox.getY();
    }
     
}