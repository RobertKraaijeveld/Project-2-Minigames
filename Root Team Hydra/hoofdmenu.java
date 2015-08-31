import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import javax.swing.*;
import java.util.*;


/**
 * Write a description of class hoofdmenu here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */



public class hoofdmenu extends World
{
    public static DrawingContext DrawingContext;
    //Dit zijn de Dr. Maggiore Libraries.
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
    
    public hoofdmenu()
    {
        // Hier bepalen we de grootte van de nieuwe wereld.
        super(952, 551, 1);
        addObject(new GameLogicMenu(), 10, 10);
        
        //Hier maken we enkele actors aan die meteen bij het opstarten van het spel er moeten zijn.
        addObject(new minigameloods(), 350, 115);
        addObject(new minigamexray(), 765, 457);
        addObject(new minigamecontainer(), 388, 235);
        
        DrawingContext = new DrawingContext();
        entities = new ArrayList<BasicEntity>();
    }
}