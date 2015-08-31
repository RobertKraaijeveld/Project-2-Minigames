import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
import javax.swing.*;
import java.*;

/**
 * Write a description of class GameLogicXray here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameLogicXray extends Actor
{
    
    
    minigamexrayworld.Vector2 voortgangsbalkPosition;
    List<minigamexrayworld.Vector2> sterPositions;
    minigamexrayworld.Vector2 sterpositie1;
    minigamexrayworld.Vector2 sterpositie2;
    minigamexrayworld.Vector2 sterpositie3;
    minigamexrayworld.Vector2 sterpositie4;
    minigamexrayworld.Vector2 sterpositie5;
    minigamexrayworld.Vector2 sterpositie6;
    
        
    minigamexrayworld.Vector2 goalsPosition;
    
    List<minigamexrayworld.Vector2> balkjePositions;
    
    //hierboven alles van score
    
    ArrayList<minigamexrayworld.Vector2> machinePosition;
    ArrayList<minigamexrayworld.Vector2> box2Position;
    ArrayList<minigamexrayworld.Vector2> xray1Position;
    
    int inrijdenBox2;
    
    //Deze var gaat de positie vd. muis bevatten.
    minigamexrayworld.Vector2 muisPositie;

    minigamexrayworld.Texture2D voortgangsbalkstreepAppearance;
    minigamexrayworld.Texture2D voortgangsbalkAppearance;
    minigamexrayworld.Texture2D sterAppearance;
    minigamexrayworld.Texture2D sterLeegAppearance;
    minigamexrayworld.Texture2D goalsAppearance;
 
    minigamexrayworld.Texture2D bootAppearance;
    
    minigamexrayworld.Texture2D box2Appearance;
    minigamexrayworld.Texture2D xray1Appearance;
    minigamexrayworld.Texture2D machineAppearance;
    
    //de groene knop
    minigamexrayworld.Vector2 AcceptButtonPosition;
    minigamexrayworld.Texture2D AcceptButtonAppearance;
    
    //de rode knop
    minigamexrayworld.Vector2 DeclineButtonPosition;
    minigamexrayworld.Texture2D DeclineButtonAppearance;
    
    minigamexrayworld.Vector2 stopPosition;
    
    
    Random randomGenerator;
    
    minigamexrayworld.Vector2 indicatiebalkPosition;
    minigamexrayworld.Texture2D indicatiebalkAppearance;
    
    float RespawnTijdBoot;
   
    int lengtearray;
    int dada;
    int selected = 0;
    int GameLogicXrayvoortgangsPunten = 0;
    int rijdenBox2;
    
     float dt = 1.0f / 60.0f;
    
    public GameLogicXray() 
    {
        //Eerst geven we een message weer, die de bedoeling van het spel aan de gebruiker uitlegd.\
        JOptionPane.showMessageDialog(new JInternalFrame(), "Constant komen er koffers langs en worden gescant met röntgenstralen, jij moet deze koffers controleren op verboden voorwerpen. /n De lijst met verboden voorwerpen kan je bekijken door rechts op het lijste op de tafel te klikken.", "Spelregels", JOptionPane.INFORMATION_MESSAGE);
        
        // de scorevars
        indicatiebalkPosition = new minigamexrayworld.Vector2(10, 300);
        voortgangsbalkPosition = new minigamexrayworld.Vector2(476, 18);
        sterPositions = new ArrayList<minigamexrayworld.Vector2>();
                
        sterpositie1 = new minigamexrayworld.Vector2(476, 38);
        sterpositie2 = new minigamexrayworld.Vector2(520, 38);
        sterpositie3 = new minigamexrayworld.Vector2(530, 38);
        sterpositie4 = new minigamexrayworld.Vector2(565, 38);
        sterpositie5 = new minigamexrayworld.Vector2(575, 38);
        sterpositie6 = new minigamexrayworld.Vector2(585, 38);
                
        sterPositions.add(sterpositie1);
        sterPositions.add(sterpositie2);
        sterPositions.add(sterpositie3);
        sterPositions.add(sterpositie4);
        sterPositions.add(sterpositie5);
        sterPositions.add(sterpositie6);
               
        goalsPosition = new minigamexrayworld.Vector2(527, 12);
        
        //hierboven alles vana score
        
        machinePosition = new ArrayList<minigamexrayworld.Vector2>();
        box2Position = new ArrayList<minigamexrayworld.Vector2>();
        xray1Position = new ArrayList<minigamexrayworld.Vector2>();
        
        //groene knop positie
        AcceptButtonPosition = new minigamexrayworld.Vector2(372, 118);
        AcceptButtonAppearance = new minigamexrayworld.Texture2D("Accept.png");
        
        //rode knop positie
        DeclineButtonPosition = new minigamexrayworld.Vector2(525, 118);
        DeclineButtonAppearance = new minigamexrayworld.Texture2D("Decline.png");
        
        voortgangsbalkstreepAppearance = new minigamexrayworld.Texture2D("loods_progress_fill.png");
        voortgangsbalkAppearance = new minigamexrayworld.Texture2D("loods_balk_bg.png");
        balkjePositions = new ArrayList<minigamexrayworld.Vector2>();
        sterAppearance = new minigamexrayworld.Texture2D("loods_ster_active.png");
        sterLeegAppearance = new minigamexrayworld.Texture2D("loods_ster_inactive.png");
        goalsAppearance = new minigamexrayworld.Texture2D("loods_goals.png");
        
        randomGenerator = new Random();
        
        RespawnTijdBoot = 0.0f;
        
        
        indicatiebalkAppearance = new minigamexrayworld.Texture2D("indicatiebalk.png");
        bootAppearance = new minigamexrayworld.Texture2D("boot.png");
        
        box2Appearance = new minigamexrayworld.Texture2D("Box2.png");
        xray1Appearance = new minigamexrayworld.Texture2D("X-Ray Level 1 Box 1.png");
        machineAppearance = new minigamexrayworld.Texture2D("xrayMachine.png");
        
        machinePosition.add(new minigamexrayworld.Vector2(462,315));
        box2Position.add(new minigamexrayworld.Vector2(-100,310));
        
        
        //Deze var is voor het stoppunt dat gebruikt wordt bij het spawnen v.d. vrachtwagens.
        stopPosition = new minigamexrayworld.Vector2(460,310);
        
        inrijdenBox2 = 1;
    
    }
    /**
     * Act - do whatever the GameLogicXray wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // Add your action code here.
        
        minigamexrayworld.DrawingContext drawingContext = minigamexrayworld.DrawingContext;
        drawingContext.BeginDraw();
        
        //hieronder alles van score
        minigamexrayworld.DrawingContext.Draw(voortgangsbalkAppearance, voortgangsbalkPosition);
        
        if (GameLogicXrayvoortgangsPunten >= 100) {
           minigamexrayworld.DrawingContext.Draw(sterAppearance, sterPositions.get(1-1));  
        }
        else if (GameLogicXrayvoortgangsPunten < 100){
            minigamexrayworld.DrawingContext.Draw(sterLeegAppearance, sterPositions.get(1-1));
            
        }
        if (GameLogicXrayvoortgangsPunten >= 150) {
           minigamexrayworld.DrawingContext.Draw(sterAppearance, sterPositions.get(2-1));  
           minigamexrayworld.DrawingContext.Draw(sterAppearance, sterPositions.get(3-1)); 
        }
        else if (GameLogicXrayvoortgangsPunten < 150){
            minigamexrayworld.DrawingContext.Draw(sterLeegAppearance, sterPositions.get(2-1));
            minigamexrayworld.DrawingContext.Draw(sterLeegAppearance, sterPositions.get(3-1));
        }
        if (GameLogicXrayvoortgangsPunten >= 200) {
           minigamexrayworld.DrawingContext.Draw(sterAppearance, sterPositions.get(4-1));
           minigamexrayworld.DrawingContext.Draw(sterAppearance, sterPositions.get(5-1)); 
           minigamexrayworld.DrawingContext.Draw(sterAppearance, sterPositions.get(6-1)); 
        }
        else if (GameLogicXrayvoortgangsPunten < 200){
            minigamexrayworld.DrawingContext.Draw(sterLeegAppearance, sterPositions.get(4-1));
            minigamexrayworld.DrawingContext.Draw(sterLeegAppearance, sterPositions.get(5-1));
            minigamexrayworld.DrawingContext.Draw(sterLeegAppearance, sterPositions.get(6-1));
        }
        
        
        
        
        if (Greenfoot.isKeyDown("w") && GameLogicXrayvoortgangsPunten <= 200) {
            GameLogicXrayUpdateScore();
        }
        
        for (int i = lengtearray; i < balkjePositions.size();  i++) {
            
            minigamexrayworld.DrawingContext.Draw(voortgangsbalkstreepAppearance, balkjePositions.get(i)); 
        }
        
        
        minigamexrayworld.DrawingContext.Draw(goalsAppearance, goalsPosition);
        
        //Hier wordt de snelheid aan de box gegeven.
        if (inrijdenBox2 == 1) {
            box2Position.set(0, box2Position.get(0).sub(minigamexrayworld.Vector2.UnitX.mul(dt).mul(-200.0f)) );
            if(minigamexrayworld.Vector2.Distance(stopPosition, box2Position.get(0)) <= 5.0)
            {
                //nieuweTruck = 0;
                inrijdenBox2 = 0;
                System.out.println("stop");
                box2Position.set(0, stopPosition);
                //xray1Position.add(new minigamexrayworld.Vector2(460,310));
            }
        }
    if(Greenfoot.getMouseInfo() != null) {
              //Maken we deze deze var. Deze var bevat de positie v.d. muis.
              muisPositie = new minigamexrayworld.Vector2(Greenfoot.getMouseInfo().getX(), Greenfoot.getMouseInfo().getY());
              
        if(minigamexrayworld.Vector2.Distance(muisPositie, AcceptButtonPosition) <= 50.0 && Greenfoot.getMouseInfo().getButton == 1 ){
            rijdenBox2 = 1;
            //xray1Position.remove(1);
        }
        if(minigamexrayworld.Vector2.Distance(muisPositie, DeclineButtonPosition) <= 50.0 && minigamexrayworld.Vector2.Distance(stopPosition, box2Position.get(0)) <= 5.0 ){
            rijdenBox2 = 1;
            //Hier geven we een message weer, die aangeeft dat de speler het fout heeft gedaan.
            JOptionPane.showMessageDialog(new JInternalFrame(), "De koffer bevatte wel degelijk verboden voorwerpen en u heeft deze fout beoordeeld!", "Foute beoordeling!", JOptionPane.INFORMATION_MESSAGE);
        }
        if(rijdenBox2 == 1){
            box2Position.set(0, box2Position.get(0).sub(minigamexrayworld.Vector2.UnitX.mul(dt).mul(-200.0f)) );
        }
        
        
        //Hier worden de boxen e.d. uit de arrays getekend dmv. een for loop
        
        for(minigamexrayworld.Vector2 a : box2Position)
        {
            drawingContext.Draw(box2Appearance, a);
        }
        
        
        
        //Hier wordt de machine uit de array gehaald en getekend.
        for(minigamexrayworld.Vector2 a : machinePosition)
        {
            drawingContext.Draw(machineAppearance, a);
        }
        
        //Hier worden de X-rays e.d. uit de arrays getekend dmv. een for loop
        for(minigamexrayworld.Vector2 a : xray1Position)
        {
            drawingContext.Draw(xray1Appearance, a);
        }
    }   
}
          
    
        //score updaten
        public int GameLogicXrayUpdateScore()
        {
    
        lengtearray = balkjePositions.size();
        GameLogicXrayvoortgangsPunten += 1;
        for (int i = 0; i < GameLogicXrayvoortgangsPunten; i++)
        {
            minigamexrayworld.Vector2 newVoortangsPositie = minigamexrayworld.Vector2.Zero;
            newVoortangsPositie = new  minigamexrayworld.Vector2(377 + 1* i, 18);
            balkjePositions.add(newVoortangsPositie);
        }
        Scorewereld.scoreLoods(GameLogicXrayvoortgangsPunten);
        return GameLogicXrayvoortgangsPunten;
        }
}
