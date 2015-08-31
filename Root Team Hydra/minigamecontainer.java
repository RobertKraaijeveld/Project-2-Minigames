import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class minigamecontainer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class minigamecontainer extends Actor
{
    /**
     * Act - do whatever the minigamecontainer wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
            /// als er op de knop geklikt wordt start de desbetreffende minigame
        if (Greenfoot.mouseClicked(this)) {
             Greenfoot.setWorld(new EpicVrachtOverslaan());
        }
    }    
}
