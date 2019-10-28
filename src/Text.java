

import java.nio.IntBuffer;
import java.awt.Rectangle;
import java.nio.ByteBuffer;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Map;

import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.stb.STBImage.stbi_failure_reason;

import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

public class Text extends GameObject {

    //Map the desired character to its index in the texture
    private static Map<String, Integer> characterMap;

    //texture setup stuff
    private static int id;
    private static int imgWidth;
    private static int imgHeight;

    //X and Y origin of the text box (top-left)
    protected int x;
    protected int y;

    //Width and Height of each character box
    protected int w;
    protected int h;

    // Color
    protected float r;
    protected float g;
    protected float b;

    //Array of characters to be shown
    protected String[] textArray;

    //Set up the character map
    static {
        Text.characterMap = new java.util.HashMap<>();
        String[] characters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2",
                "3", "4", "5", "6", "7", "8", "9", ".", ",", ";", ":", "?", "!", "-", "#", "\"", "'", "&", "(", ")", "[", "]", "/", "\\", "+", "=", "*", "$",
                "<", ">", "%", " "};
        for (int i = 0; i < characters.length; i++) {
            Text.characterMap.put(characters[i], i);
        }
    }

    public Text(int x, int y, int w, int h, String text)
    {
        this.x = x;
        this.y = y;

        this.w = w;
        this.h = h;

        //default color will be white
        setColor(1f,1f,1f);

        this.textArray = text.split("");

        //Text texture setup
        try (MemoryStack stack= MemoryStack.stackPush())
        {
            IntBuffer W = stack.mallocInt(1);
            IntBuffer H = stack.mallocInt(1);
            IntBuffer comp = stack.mallocInt(1);

            ByteBuffer img=stbi_load("res/text.png", W, H, comp, 0);
            if (img == null)
            {
                throw new RuntimeException("failed to load texture: " + stbi_failure_reason());

            }

            imgWidth=W.get();
            imgHeight=H.get();


            id = GL11.glGenTextures();
            GL11.glBindTexture(GL11.GL_TEXTURE_2D,id);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, imgWidth, imgHeight, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, img);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D,0);
        }
    }

    public void setColor(float r, float g, float b)
    {
        this.r = r;
        this.g = g;
        this.b = b;
    }
    
    

    //So you can do dynamic things with the text
    public void update(int delta){

    }

    public void draw()
    {

        GL11.glColor3f(r,g,b);

        GL11.glBindTexture(GL11.GL_TEXTURE_2D,  id);
        int mapIndex;
        for (int index = 0; index < textArray.length; index++) {
            mapIndex = characterMap.get(textArray[index]);
            //Used to determine top and left edges of characters
            int row =  mapIndex/16;
            int col =  mapIndex%16;

            //Used to determine bottom and right edges of characters
            int nextC = (mapIndex+1)%16;
            int nextR = mapIndex/16 + 1;

            //using width / 2 works much better than the whole width
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glTexCoord2f(col/16f, row/16f);
            GL11.glVertex2f(x+index*w/2, y);
            GL11.glTexCoord2f(nextC/16f,row/16f);
            GL11.glVertex2f(x+index*w/2+w, y);
            GL11.glTexCoord2f(nextC/16f,nextR/16f);
            GL11.glVertex2f(x+index*w/2+w, y+h);
            GL11.glTexCoord2f(col/16f,nextR/16f);
            GL11.glVertex2f(x+index*w/2, y+h);
            GL11.glEnd();
        }
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,  0);

    }

}
