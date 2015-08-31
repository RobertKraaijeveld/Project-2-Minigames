import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*; //Hiermee importeren we diverse native-java functies en libraries.
import javax.swing.*; 
import java.io.*;

/**
 * Write a description of class GameLogicVracht here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameLogicVracht extends Actor
{
    /**
     * Act - do whatever the GameLogicVracht wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    //Deze vars zijn voor de voortgangsbalk/score.
    EpicVrachtOverslaan.Vector2 voortgangsbalkPosition;
    
    EpicVrachtOverslaan.Vector2 ster1Position;
    EpicVrachtOverslaan.Vector2 ster2Position;
    EpicVrachtOverslaan.Vector2 ster3Position;
    EpicVrachtOverslaan.Vector2 ster4Position;
    EpicVrachtOverslaan.Vector2 ster5Position;
    EpicVrachtOverslaan.Vector2 ster6Position;
    
    EpicVrachtOverslaan.Vector2 sterLeeg1Position;
    EpicVrachtOverslaan.Vector2 sterLeeg2Position;
    EpicVrachtOverslaan.Vector2 sterLeeg3Position;
    EpicVrachtOverslaan.Vector2 sterLeeg4Position;
    EpicVrachtOverslaan.Vector2 sterLeeg5Position;
    EpicVrachtOverslaan.Vector2 sterLeeg6Position;
    
    EpicVrachtOverslaan.Vector2 linkerrandPosition;
    
    List<EpicVrachtOverslaan.Vector2> balkjePositions;
    
    EpicVrachtOverslaan.Texture2D voortgangsbalkstreepAppearance;
    EpicVrachtOverslaan.Texture2D voortgangsbalkAppearance;
    EpicVrachtOverslaan.Texture2D sterAppearance;
    EpicVrachtOverslaan.Texture2D sterLeegAppearance;
    
    int dada;
    int voortgangsPunten = 0;
    
    //Deze vars gaan straks de posities van de de verschillende "objecten" bevatten.
     EpicVrachtOverslaan.Vector2 truckGeelPosition;
     EpicVrachtOverslaan.Vector2 truckBlauwPosition;
     EpicVrachtOverslaan.Vector2 truckGroenPosition;
     EpicVrachtOverslaan.Vector2 truckRoodPosition;
    
    List<EpicVrachtOverslaan.Vector2> containerGeelPosition;
    List<EpicVrachtOverslaan.Vector2> containerBlauwPosition;
    List<EpicVrachtOverslaan.Vector2> containerGroenPosition;
    List<EpicVrachtOverslaan.Vector2> containerRoodPosition;
    
    EpicVrachtOverslaan.Vector2 grijperPosition;
    
    EpicVrachtOverslaan.Vector2 voortangsbalkPosition;
    
    EpicVrachtOverslaan.Vector2 schipPosition;
    
    //Deze var is voor de array die straks de grenzen van het level gaat bevatten, zodat we de trucks kunnen 
    //removen.
    List<EpicVrachtOverslaan.Vector2> LevelGrens;
    
    //Dit is de var die de achtergrond gaat bevatten.
    EpicVrachtOverslaan.Vector2 backgroundPosition;
    
       
    //Dit zijn de vars waar we de textures voor de verschillende "objecten" in gaan gooien.
    EpicVrachtOverslaan.Texture2D backgroundAppearance;
    EpicVrachtOverslaan.Texture2D truckGeelAppearance;
    EpicVrachtOverslaan.Texture2D truckBlauwAppearance;
    EpicVrachtOverslaan.Texture2D truckGroenAppearance;
    EpicVrachtOverslaan.Texture2D truckRoodAppearance;
    
    EpicVrachtOverslaan.Texture2D containerGeelAppearance;
    EpicVrachtOverslaan.Texture2D containerBlauwAppearance;
    EpicVrachtOverslaan.Texture2D containerGroenAppearance;
    EpicVrachtOverslaan.Texture2D containerRoodAppearance;
    
    EpicVrachtOverslaan.Texture2D grijperAppearance;
    
    EpicVrachtOverslaan.Texture2D voortgansbalkAppearance;
   
    
    EpicVrachtOverslaan.Texture2D schipAppearance;
     
    //Deze ints worden gebruikt in de collision-detection tussen de trucks en de containers.
    int collisionrood;
    int collisiongeel;
    int collisionblauw;
    int collisiongroen;
    int hangenRood = 0;
    int hangenGeel = 0;
    int hangenBlauw = 0;
    int hangenGroen = 0;
    int containernummer = 0;
    int rijdenRood = 0;
    int rijdenGroen = 0;
    int rijdenGeel = 0;
    int rijdenBlauw = 0;
    
    //Deze ints worden gebruikt bij het spawnen van de containers, en geven zoals de naam al zegt het aantal containers dat per kleur gespawned wordt aan.
    int AantalContainersRood = 4;
    int AantalContainersBlauw = 4;
    int AantalContainersGeel = 4;
    int AantalContainersGroen = 4;
    
    public GameLogicVracht()
    {
        GreenfootImage img = null;
        setImage(img);
        
        //Hier bepalen we de inhoud van de vector vars hierboven, we geven de "objecten" dus een positie.
        truckGeelPosition = new EpicVrachtOverslaan.Vector2(300, 200);
        truckBlauwPosition = new EpicVrachtOverslaan.Vector2(400, 175);
        truckGroenPosition = new EpicVrachtOverslaan.Vector2(500, 150);
        truckRoodPosition = new EpicVrachtOverslaan.Vector2(600, 125);
        
        linkerrandPosition = new EpicVrachtOverslaan.Vector2(300, 0);
        
        //Dit is de positie v.d. grijper aan het begin van het spel.
        grijperPosition = new EpicVrachtOverslaan.Vector2(700,500);
               
        //Dit zij de arrays voor de posities van de containers.
        containerGeelPosition = new ArrayList<EpicVrachtOverslaan.Vector2>();
        containerBlauwPosition = new ArrayList<EpicVrachtOverslaan.Vector2>();
        containerGroenPosition = new ArrayList<EpicVrachtOverslaan.Vector2>();
        containerRoodPosition = new ArrayList<EpicVrachtOverslaan.Vector2>();
        
        //Hier bepalen we de posities van de containers, per kleur.
        containerRoodPosition.add(new EpicVrachtOverslaan.Vector2(500,300));
        containerRoodPosition.add(new EpicVrachtOverslaan.Vector2(500,250));
        containerRoodPosition.add(new EpicVrachtOverslaan.Vector2(500,200));
        containerRoodPosition.add(new EpicVrachtOverslaan.Vector2(500,150));
        
        containerGeelPosition.add(new EpicVrachtOverslaan.Vector2(400,300));
        containerGeelPosition.add(new EpicVrachtOverslaan.Vector2(400,250));
        containerGeelPosition.add(new EpicVrachtOverslaan.Vector2(400,200));
        containerGeelPosition.add(new EpicVrachtOverslaan.Vector2(400,150));
        
        containerBlauwPosition.add(new EpicVrachtOverslaan.Vector2(300,300));
        containerBlauwPosition.add(new EpicVrachtOverslaan.Vector2(300,250));
        containerBlauwPosition.add(new EpicVrachtOverslaan.Vector2(300,200));
        containerBlauwPosition.add(new EpicVrachtOverslaan.Vector2(300,150));
        
        containerGroenPosition.add(new EpicVrachtOverslaan.Vector2(200,300));
        containerGroenPosition.add(new EpicVrachtOverslaan.Vector2(200,250));
        containerGroenPosition.add(new EpicVrachtOverslaan.Vector2(200,200));
        containerGroenPosition.add(new EpicVrachtOverslaan.Vector2(200,150)); 
        
        voortangsbalkPosition = new EpicVrachtOverslaan.Vector2(650, 10);
        
        
        schipPosition = new EpicVrachtOverslaan.Vector2(300, 185);
        
        backgroundPosition = EpicVrachtOverslaan.Vector2.Zero;
        
       
        
        //Hier doen we hetzelfde, maar dan voor de texture-vars.
        truckGeelAppearance = new EpicVrachtOverslaan.Texture2D("Truck Geel Leeg.png");
        truckBlauwAppearance = new EpicVrachtOverslaan.Texture2D("Truck Blauw Leeg.png");
        truckGroenAppearance = new EpicVrachtOverslaan.Texture2D("Truck Groen Leeg.png");
        truckRoodAppearance = new EpicVrachtOverslaan.Texture2D("Truck Rood Leeg.png");
        
        containerGeelAppearance = new EpicVrachtOverslaan.Texture2D("gele container.png");
        containerBlauwAppearance = new EpicVrachtOverslaan.Texture2D("blauwe container.png");
        containerGroenAppearance = new EpicVrachtOverslaan.Texture2D("groene container.png");
        containerRoodAppearance = new EpicVrachtOverslaan.Texture2D("rode container.png");
        
        grijperAppearance = new EpicVrachtOverslaan.Texture2D("beeper.png");
        
        voortgansbalkAppearance = new EpicVrachtOverslaan.Texture2D("voortgangsbalk.png");
       
        
        schipAppearance = new EpicVrachtOverslaan.Texture2D("ContainerSchip.png");
        
        backgroundAppearance = new EpicVrachtOverslaan.Texture2D("overslaan.png");
        
        //Deze vars zijn voor de voortgangs-scorebalk
        voortgangsbalkPosition = new EpicVrachtOverslaan.Vector2(476, 18);
        ster1Position = new EpicVrachtOverslaan.Vector2(476, 38);
        ster2Position = new EpicVrachtOverslaan.Vector2(520, 38);
        ster3Position = new EpicVrachtOverslaan.Vector2(530, 38);
        ster4Position = new EpicVrachtOverslaan.Vector2(565, 38);
        ster5Position = new EpicVrachtOverslaan.Vector2(575, 38);
        ster6Position = new EpicVrachtOverslaan.Vector2(585, 38);
        
        sterLeeg1Position = new EpicVrachtOverslaan.Vector2(476, 38);
        sterLeeg2Position = new EpicVrachtOverslaan.Vector2(520, 38);
        sterLeeg3Position = new EpicVrachtOverslaan.Vector2(530, 38);
        sterLeeg4Position = new EpicVrachtOverslaan.Vector2(565, 38);
        sterLeeg5Position = new EpicVrachtOverslaan.Vector2(575, 38);
        sterLeeg6Position = new EpicVrachtOverslaan.Vector2(585, 38);
        
        balkjePositions = new ArrayList<EpicVrachtOverslaan.Vector2>();
        
               
        voortgangsbalkstreepAppearance = new EpicVrachtOverslaan.Texture2D("loods_progress_fill.png");
        voortgangsbalkAppearance = new EpicVrachtOverslaan.Texture2D("loods_progress_bar_bg.png");
        sterAppearance = new EpicVrachtOverslaan.Texture2D("loods-ster.png");
        sterLeegAppearance = new EpicVrachtOverslaan.Texture2D("loods-ster-leeg.png");
    }
    
    /**
     * Act - do whatever the GameLogicVracht wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        float dt = 1.0f / 60.0f;
        //System.out.println("...");
        
        /*
         * GRIJPER EN TRUCK CODE
         */   
        //Rood
        if (hangenRood != 2) { //als er geen container hangt
            for(int i = 0; i <  AantalContainersRood; i++)
            {
                if(EpicVrachtOverslaan.Vector2.Distance(grijperPosition, containerRoodPosition.get(i)) <=50.0 && Greenfoot.isKeyDown(" "))
                {
                    
                    //Als de grijper binnen bereik van de container is en spatie gedrukt word, dan wordt de pos. van de container en de grijper gelijk.                
                    containerRoodPosition.set(i, grijperPosition);
                    hangenRood = 1;
                    hangenBlauw = 2;
                    hangenGroen = 2;
                    hangenGeel = 2;
                    containernummer = i;
                    AantalContainersRood--;
                }
            
            }
        }
        if (hangenRood == 1) {
            containerRoodPosition.set(containernummer, grijperPosition);
            //Deze code update de positie van iedere container als hij aan de grijper hang.
            
          
            if(EpicVrachtOverslaan.Vector2.Distance(truckRoodPosition, containerRoodPosition.get(containernummer)) <= 50.0 & Greenfoot.isKeyDown(" "))
            {
                //Als de container de vrachtwagen aangeraakt heeft, is er collision. Dan doen we:
                collisionrood = 1;
                //En wordt de container verwijderd, deze staat nu immers op de vrachtwagen.
                //containerRoodPosition.set(containernummer, grijperPosition);
            }
                //Deze code geeft een foutmelding als de verkeerde containers gekoppeld worden.
            if(EpicVrachtOverslaan.Vector2.Distance(truckGeelPosition, containerRoodPosition.get(containernummer)) <=1.0 & Greenfoot.isKeyDown(" "))
             {
                  Greenfoot.playSound("containererror.mp3");
             }
            if(EpicVrachtOverslaan.Vector2.Distance(truckGroenPosition, containerRoodPosition.get(containernummer)) <=1.0 & Greenfoot.isKeyDown(" "))
             {
                  Greenfoot.playSound("containererror.mp3");
             }
            if(EpicVrachtOverslaan.Vector2.Distance(truckBlauwPosition, containerRoodPosition.get(containernummer)) <=1.0 & Greenfoot.isKeyDown(" "))
             {
                 Greenfoot.playSound("containererror.mp3");
             }
            
   
            if(collisionrood == 1)
            {
                //Eerst veranderen we de sprite naar de volle versie.    
                truckRoodAppearance = new EpicVrachtOverslaan.Texture2D("Truck Rood Vol.png");
                //Dan rijdt de truck weg.
                rijdenRood = 1;
                //En wordt de score bijgewerkt.
                 if (voortgangsPunten < 200)
                 {
                  voortgangsPunten += 12.5;
                 }
                    
                containerRoodPosition.remove(containernummer);
                hangenRood = 0;
                hangenBlauw = 0;
                hangenGeel = 0;
                hangenGroen = 0;
            }     
        }          
        if (rijdenRood ==1)
        {
            truckRoodPosition = truckRoodPosition.sub(EpicVrachtOverslaan.Vector2.UnitX.mul(dt).mul(200.0f));
            
        }
        
        
        
        //Blauw
        if (hangenBlauw != 2) { //als er geen container hangt
            for(int i = 0; i <  AantalContainersBlauw; i++)
            {
                if(EpicVrachtOverslaan.Vector2.Distance(grijperPosition, containerBlauwPosition.get(i)) <=50.0 && Greenfoot.isKeyDown(" "))
                {
                    
                    //Als de grijper binnen bereik van de container is en spatie gedrukt word, dan wordt de pos. van de container en de grijper gelijk.                
                    containerBlauwPosition.set(i, grijperPosition);
                    hangenBlauw = 1;
                    hangenRood = 2;
                    hangenGeel = 2;
                    hangenGroen = 2;
                    containernummer = i;
                    AantalContainersBlauw--;
                }
            
            }
        }
        if (hangenBlauw == 1) {
            containerBlauwPosition.set(containernummer, grijperPosition);
            //Deze code update de positie van iedere container als hij aan de grijper hang.
            
          
            if(EpicVrachtOverslaan.Vector2.Distance(truckBlauwPosition, containerBlauwPosition.get(containernummer)) <= 50.0 & Greenfoot.isKeyDown(" "))
            {
                //Als de container de vrachtwagen aangeraakt heeft, is er collision. Dan doen we:
                collisionblauw = 1;
                //En wordt de container verwijderd, deze staat nu immers op de vrachtwagen.
                //containerRoodPosition.set(containernummer, grijperPosition);
            }
                //Deze code geeft een foutmelding als de verkeerde containers gekoppeld worden.
            if(EpicVrachtOverslaan.Vector2.Distance(truckGeelPosition, containerBlauwPosition.get(containernummer)) <=1.0 & Greenfoot.isKeyDown(" "))
             {
                  Greenfoot.playSound("containererror.mp3");
             }
            if(EpicVrachtOverslaan.Vector2.Distance(truckGroenPosition, containerBlauwPosition.get(containernummer)) <=1.0 & Greenfoot.isKeyDown(" "))
             {
                  Greenfoot.playSound("containererror.mp3");
             }
            if(EpicVrachtOverslaan.Vector2.Distance(truckBlauwPosition, containerBlauwPosition.get(containernummer)) <=1.0 & Greenfoot.isKeyDown(" "))
             {
                 Greenfoot.playSound("containererror.mp3");
             }
            
            
            if(collisionblauw == 1)
            {
                //Eerst veranderen we de sprite naar de volle versie.    
                truckBlauwAppearance = new EpicVrachtOverslaan.Texture2D("Truck Blauw Vol.png");
                //Dan rijdt de truck weg.
                rijdenBlauw = 1;
                //En wordt de score bijgewerkt.
                 if (voortgangsPunten < 200)
                 {
                  voortgangsPunten += 12.5;
                 }
                    
                containerBlauwPosition.remove(containernummer);
                hangenBlauw = 0;
                hangenRood = 0;
                hangenGeel = 0;
                hangenGroen = 0;
            }
        }             
        if (rijdenBlauw ==1)
        {
            truckBlauwPosition = truckBlauwPosition.sub(EpicVrachtOverslaan.Vector2.UnitX.mul(dt).mul(200.0f));
            
        }
        
        
        
        //Geel
        if (hangenGeel != 2) { //als er geen container hangt
            for(int i = 0; i <  AantalContainersGeel; i++)
            {
                if(EpicVrachtOverslaan.Vector2.Distance(grijperPosition, containerGeelPosition.get(i)) <=50.0 && Greenfoot.isKeyDown(" "))
                {
                    
                    //Als de grijper binnen bereik van de container is en spatie gedrukt word, dan wordt de pos. van de container en de grijper gelijk.                
                    containerGeelPosition.set(i, grijperPosition);
                    hangenGeel = 1;
                    hangenRood = 2;
                    hangenBlauw = 2;
                    hangenGroen = 2;
                    containernummer = i;
                    AantalContainersGeel--;
                }
            
            }
        }
        if (hangenGeel == 1) {
            containerGeelPosition.set(containernummer, grijperPosition);
            //Deze code update de positie van iedere container als hij aan de grijper hang.
            
          
            if(EpicVrachtOverslaan.Vector2.Distance(truckGeelPosition, containerGeelPosition.get(containernummer)) <= 50.0 & Greenfoot.isKeyDown(" "))
            {
                //Als de container de vrachtwagen aangeraakt heeft, is er collision. Dan doen we:
                collisiongeel = 1;
                //En wordt de container verwijderd, deze staat nu immers op de vrachtwagen.
                
            }
                //Deze code geeft een foutmelding als de verkeerde containers gekoppeld worden.
            if(EpicVrachtOverslaan.Vector2.Distance(truckGeelPosition, containerGeelPosition.get(containernummer)) <=1.0 & Greenfoot.isKeyDown(" "))
             {
                  Greenfoot.playSound("containererror.mp3");
             }
            if(EpicVrachtOverslaan.Vector2.Distance(truckGroenPosition, containerGeelPosition.get(containernummer)) <=1.0 & Greenfoot.isKeyDown(" "))
             {
                  Greenfoot.playSound("containererror.mp3");
             }
            if(EpicVrachtOverslaan.Vector2.Distance(truckBlauwPosition, containerGeelPosition.get(containernummer)) <=1.0 & Greenfoot.isKeyDown(" "))
             {
                 Greenfoot.playSound("containererror.mp3");
             }
            if(collisiongeel == 1)
            {
                //Eerst veranderen we de sprite naar de volle versie.    
                truckGeelAppearance = new EpicVrachtOverslaan.Texture2D("Truck Geel Vol.png");
                //Dan wordt de rijden var bijgewerkt.
                rijdenGeel = 1;
                 //En wordt de score bijgewerkt.
                 if (voortgangsPunten < 200)
                 {
                  voortgangsPunten += 12.5;
                 }
                //En daarna wordt de container verwijderd.    
                containerGeelPosition.remove(containernummer);
                hangenGeel = 0;
                hangenGroen = 0;
                hangenRood = 0;
                hangenBlauw = 0;
            }
        }             
        //Als de eerder veranderde rijden-var 1 is, gaat de truck rijden.
        if (rijdenGeel ==1) {
            truckGeelPosition = truckGeelPosition.sub(EpicVrachtOverslaan.Vector2.UnitX.mul(dt).mul(200.0f));
            
        }
        
        
        
           
        
        
        //Groen
        if (hangenGroen != 2) {
            for(int i = 0; i <  AantalContainersGroen; i++)
            {
                if(EpicVrachtOverslaan.Vector2.Distance(grijperPosition, containerGroenPosition.get(i)) <=50.0 && Greenfoot.isKeyDown(" "))
                {
                    
                    //Als de grijper binnen bereik van de container is en spatie gedrukt word, dan wordt de pos. van de container en de grijper gelijk.                
                    containerGroenPosition.set(i, grijperPosition);
                    hangenGroen = 1;
                    hangenRood = 2;
                    hangenGeel = 2;
                    hangenBlauw = 2;
                    containernummer = i;
                    AantalContainersGroen--;
                }
            
            }
        }
        if (hangenGroen == 1) { //als er geen container hangt
            containerGroenPosition.set(containernummer, grijperPosition);
            //Deze code update de positie van iedere container als hij aan de grijper hang.
            
          
            if(EpicVrachtOverslaan.Vector2.Distance(truckGroenPosition, containerGroenPosition.get(containernummer)) <= 50.0 & Greenfoot.isKeyDown(" "))
            {
                //Als de container de vrachtwagen aangeraakt heeft, is er collision. Dan doen we:
                collisiongroen = 1;
                //En wordt de container verwijderd, deze staat nu immers op de vrachtwagen.
                //containerRoodPosition.set(containernummer, grijperPosition);
            }
                //Deze code geeft een foutmelding als de verkeerde containers gekoppeld worden.
            if(EpicVrachtOverslaan.Vector2.Distance(truckGeelPosition, containerGroenPosition.get(containernummer)) <=1.0 & Greenfoot.isKeyDown(" "))
             {
                  Greenfoot.playSound("containererror.mp3");
             }
            if(EpicVrachtOverslaan.Vector2.Distance(truckGroenPosition, containerGroenPosition.get(containernummer)) <=1.0 & Greenfoot.isKeyDown(" "))
             {
                  Greenfoot.playSound("containererror.mp3");
             }
            if(EpicVrachtOverslaan.Vector2.Distance(truckBlauwPosition, containerGroenPosition.get(containernummer)) <=1.0 & Greenfoot.isKeyDown(" "))
             {
                 Greenfoot.playSound("containererror.mp3");
             }
            
            
            if(collisiongroen == 1)
            {
                //Eerst veranderen we de sprite naar de volle versie.    
                truckGroenAppearance = new EpicVrachtOverslaan.Texture2D("Truck Groen Vol.png");
                //Dan rijdt de truck weg.
                rijdenGroen = 1;
                 //En wordt de score bijgewerkt.
                 if (voortgangsPunten < 200)
                 {
                  voortgangsPunten += 12.5;
                 }
                
                    
                containerGroenPosition.remove(containernummer);
                hangenGroen = 0;
                hangenGeel = 0;
                hangenRood = 0;
                hangenBlauw = 0;
            }
        
  
            
        }          
        if (rijdenGroen ==1) {
            truckGroenPosition = truckGroenPosition.sub(EpicVrachtOverslaan.Vector2.UnitX.mul(dt).mul(200.0f));
            
        }
        
            
        
        /*
        * W-A-S-D CODE
        */
        //Met deze code word het bewegen v.d. grijper met de w-a-s-d toetsen geregeld.
        if (Greenfoot.isKeyDown("A"))
          grijperPosition = grijperPosition.sub(EpicVrachtOverslaan.Vector2.UnitX.mul(dt).mul(300.0f));
        if (Greenfoot.isKeyDown("D"))
          grijperPosition = grijperPosition.add(EpicVrachtOverslaan.Vector2.UnitX.mul(dt).mul(300));
        if (Greenfoot.isKeyDown("W"))
          grijperPosition = grijperPosition.sub(EpicVrachtOverslaan.Vector2.UnitY.mul(dt).mul(300.0f));
        if (Greenfoot.isKeyDown("S"))
          grijperPosition = grijperPosition.add(EpicVrachtOverslaan.Vector2.UnitY.mul(dt).mul(300.0f));
        
          
        /*
        * SCORE-CODE
        */
              
        
        for (int i = 0; i < voortgangsPunten; i++){
            EpicVrachtOverslaan.Vector2 newVoortangsPositie = EpicVrachtOverslaan.Vector2.Zero;
            newVoortangsPositie = new EpicVrachtOverslaan.Vector2(377 + 1* i, 18);
            balkjePositions.add(newVoortangsPositie);
        }
                  
        /*
        * DRAW-CODE
        */
       
        //Alle code hieronder doet het daadwerkelijke "plaatsen" v.d. objecten onder de door ons hierboven gegeven voorwaarden.
        EpicVrachtOverslaan.DrawingContext drawingContext = EpicVrachtOverslaan.DrawingContext;
        drawingContext.BeginDraw();
        EpicVrachtOverslaan.DrawingContext.Draw(truckGeelAppearance, truckGeelPosition);
        EpicVrachtOverslaan.DrawingContext.Draw(truckRoodAppearance, truckRoodPosition);
        EpicVrachtOverslaan.DrawingContext.Draw(truckGroenAppearance, truckGroenPosition);
        EpicVrachtOverslaan.DrawingContext.Draw(truckBlauwAppearance, truckBlauwPosition);
       
        for(EpicVrachtOverslaan.Vector2 a : containerGeelPosition)
        {
            drawingContext.Draw(containerGeelAppearance, a);
        }
        
        for(EpicVrachtOverslaan.Vector2 a : containerRoodPosition)
        {
            drawingContext.Draw(containerRoodAppearance, a);
        }
        
        for(EpicVrachtOverslaan.Vector2 a : containerGroenPosition)
        {
            drawingContext.Draw(containerGroenAppearance, a);
        }
        
        for(EpicVrachtOverslaan.Vector2 a : containerBlauwPosition)
        {
            drawingContext.Draw(containerBlauwAppearance, a);
        }
        
        EpicVrachtOverslaan.DrawingContext.Draw(grijperAppearance, grijperPosition);
        
        EpicVrachtOverslaan.DrawingContext.Draw(schipAppearance, schipPosition);
        
        EpicVrachtOverslaan.DrawingContext.Draw(voortgansbalkAppearance, voortangsbalkPosition);
        
        
        //Deze code is voor het drawen v.d. voortgangsblak, die heeft meer aandacht nodig.
        EpicVrachtOverslaan.DrawingContext.Draw(voortgangsbalkAppearance, voortgangsbalkPosition);
        if (voortgangsPunten >= 100) {
           EpicVrachtOverslaan.DrawingContext.Draw(sterAppearance, ster1Position);  
        }
        else if (voortgangsPunten < 100){
            EpicVrachtOverslaan.DrawingContext.Draw(sterLeegAppearance, sterLeeg1Position);
            
        }
        if (voortgangsPunten >= 150) {
           EpicVrachtOverslaan.DrawingContext.Draw(sterAppearance, ster2Position);  
           EpicVrachtOverslaan.DrawingContext.Draw(sterAppearance, ster3Position); 
        }
        else if (voortgangsPunten < 150){
            EpicVrachtOverslaan.DrawingContext.Draw(sterLeegAppearance, sterLeeg2Position);
            EpicVrachtOverslaan.DrawingContext.Draw(sterLeegAppearance, sterLeeg3Position);
        }
        if (voortgangsPunten >= 200) {
           EpicVrachtOverslaan.DrawingContext.Draw(sterAppearance, ster4Position);
           EpicVrachtOverslaan.DrawingContext.Draw(sterAppearance, ster5Position); 
           EpicVrachtOverslaan.DrawingContext.Draw(sterAppearance, ster6Position); 
        }
        else if (voortgangsPunten < 200){
            EpicVrachtOverslaan.DrawingContext.Draw(sterLeegAppearance, sterLeeg4Position);
            EpicVrachtOverslaan.DrawingContext.Draw(sterLeegAppearance, sterLeeg5Position);
            EpicVrachtOverslaan.DrawingContext.Draw(sterLeegAppearance, sterLeeg6Position);
        }
        
        
        
        
        for(EpicVrachtOverslaan.Vector2 a : balkjePositions){
          drawingContext.Draw(voortgangsbalkstreepAppearance, a); 
        }
        
        
        

    }    
    
    
}

