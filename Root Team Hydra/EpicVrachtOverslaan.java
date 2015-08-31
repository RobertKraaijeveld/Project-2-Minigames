import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
import java.awt.Font;
import java.awt.Color;

/**
 * Write a description of class EpicVrachtOverslaan here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EpicVrachtOverslaan extends World
{
    public static DrawingContext DrawingContext;
    /**
     * Constructor for objects of class EpicVrachtOverslaan.
     * 
     */
    public EpicVrachtOverslaan()
    {    
        // Create a new world with 900x600 cells with a cell size of 1x1 pixels.
        super(952, 551, 1, false); 
        setBackground("overslaan_bg.png");
            
        GameLogicVracht w = new GameLogicVracht();
        addObject(w, 0, 0);
        
        DrawingContext = new DrawingContext();
        entities = new ArrayList<BasicEntity>();
       
        //Hier de code voor de timer.
        getBackground().setFont(new Font("", 0, 30));
        getBackground().setColor(Color.black);
        addObject(new Clock(true, true, 120, "Tijd:"), 50, 40);
    }
    
    //Dit zijn Dr. Maggiore's libraries.
    public static class Vector2
    {
        public float X, Y;
        
        public static final Vector2 Zero = new Vector2(0,0);
        public static final Vector2 UnitX = new Vector2(1,0);
        public static final Vector2 UnitY = new Vector2(0,1);
        
        public Vector2(float x, float y) {
            X = x; Y = y;
        }
        
        public Vector2 mul(float k) 
        {
            return new Vector2(X * k, Y * k);
        }

        public Vector2 add(Vector2 v) 
        {
            return new Vector2(X + v.X, Y + v.Y);
        }

        public Vector2 sub(Vector2 v) 
        {
            return this.add(v.min());
        }

        public Vector2 min() 
        {
            return new Vector2(-X, -Y);
        }
        
        public static float DistanceY(Vector2 a, Vector2 b)
        {
            Vector2 ab = a.sub(b);
            return (float)Math.sqrt(ab.Y * ab.Y);
        }
        
        public static float Distance(Vector2 a, Vector2 b) 
        {
            Vector2 ab = a.sub(b);
            return (float)Math.sqrt(ab.X * ab.X + ab.Y * ab.Y);
        }
    }
    
    public static class Texture2D
    {
        public String Path;
        GreenfootImage Image;
        
        public int getWidth() { return Image.getWidth(); }
        public int getHeight() { return Image.getHeight(); }
        
        public Texture2D(String path) {
            Path = path;
            Image = new GreenfootImage(path);
        }
    }

    List<BasicEntity> entities;
    public class DrawingContext {        
        public void BeginDraw()
        {
            for(BasicEntity e : entities)
                removeObject(e);
            entities.clear();
        }
        
        public void Draw(Texture2D t, Vector2 p)
        {
            BasicEntity e = new BasicEntity(t.Path);
            entities.add(e);
            addObject(e, (int)p.X, (int)p.Y);
            //SpriteBatch.Draw(t, p - new Vector2(t.Width, t.Height) / 2, c);
        }
    }
    
    private static class BasicEntity extends Actor
    {
        public BasicEntity(String image)
        {
            setImage(new GreenfootImage(image));
        }
    }   
}
