import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Scorewereld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Scorewereld extends World
{
    //variabelen die de score opslaan per minigame
    private static int scoreloods;  
    private static int scorecontainer;   
    private static int scorexray;   
       
    /**
     * Constructor for objects of class Scorewereld.
     * 
     */
    public Scorewereld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 400, 1); 
    }
    
    //functies voor punten
    //schepen loodsen
    public static void scoreLoods(int puntenloods) {
        scoreloods = puntenloods;
    }
    public static int scoreloodsroep() {
        return scoreloods;
    }
    
    //vrachtoverslaan
    public static void scoreContainer(int puntencontainer) {
        scorecontainer = puntencontainer;
    }
    public static int scorecontainerroep() {
        return scorecontainer;
    }
    
    //xray
    public static void scoreXray(int puntenxray) {
        scorexray = puntenxray;
    }
    public static int scorexrayroep() {
        return scorexray;
    }
    
    
    
}
