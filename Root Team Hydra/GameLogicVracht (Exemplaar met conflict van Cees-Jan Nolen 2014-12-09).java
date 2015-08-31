import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*; //Hiermee importeren we diverse native-java functies en libraries.
import javax.swing.*; 

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
    EpicVrachtOverslaan.Vector2 indicatiebalkPosition;
    
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
    EpicVrachtOverslaan.Texture2D indicatiebalkAppearance;
     
    //Deze ints worden gebruikt in de collision-detection tussen de trucks en de containers.
    int collisionrood;
    int collisiongeel;
    int collisionblauw;
    int collisiongroen;
    
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
        truckBlauwPosition = new EpicVrachtOverslaan.Vector2(500, 200);
        truckGroenPosition = new EpicVrachtOverslaan.Vector2(700, 200);
        truckRoodPosition = new EpicVrachtOverslaan.Vector2(800, 200);
        
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
        indicatiebalkPosition = new EpicVrachtOverslaan.Vector2(10, 300);
        
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
        indicatiebalkAppearance = new EpicVrachtOverslaan.Texture2D("indicatiebalk.png");
        
        backgroundAppearance = new EpicVrachtOverslaan.Texture2D("space.jpg");
    }
    
    /**
     * Act - do whatever the GameLogicVracht wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        float dt = 1.0f / 60.0f;
                     
        //Deze code spawnt de containers en checkt of er collision is tussen containers en trucks met bijbehorende kleuren. Zo ja, dan worden er acties uitgevoerd.
        //Gele truck
        for(int i = 0; i < AantalContainersGeel; i++)
        {
            if(EpicVrachtOverslaan.Vector2.Distance(truckGeelPosition, containerGeelPosition.get(i)) <= 50.0)
            {
                //Als de container de vrachtwagen aangeraakt heeft, is er collision. Dan doen we:
                collisiongeel = 1;
            }

            if(collisiongeel == 1)
            {
            //Eerst veranderen we de sprite naar de volle versie.    
            truckGeelAppearance = new EpicVrachtOverslaan.Texture2D("Truck Geel Vol.png");
            //Dan rijdt de truck weg.
            truckGeelPosition = truckGeelPosition.sub(EpicVrachtOverslaan.Vector2.UnitX.mul(dt).mul(300.0f));
            //eventueel kunnen we later nog toevoegen dat de trucks elkaars plaats innemen.
                     
            }
        }
            
            
        //Blauwe truck
        for(int i = 0; i <  AantalContainersBlauw; i++)
        {
            if(EpicVrachtOverslaan.Vector2.Distance(truckBlauwPosition, containerBlauwPosition.get(i)) <= 50.0)
            {
                //Als de container de vrachtwagen aangeraakt heeft, is er collision. Dan doen we:
                collisionblauw = 1;
            }
         
            if(1 == 1)
            {
            //Eerst veranderen we de sprite naar de volle versie.    
            truckBlauwAppearance = new EpicVrachtOverslaan.Texture2D("Truck Blauw Vol.png");
            //Dan rijdt de truck weg.
            truckBlauwPosition = truckBlauwPosition.sub(EpicVrachtOverslaan.Vector2.UnitX.mul(dt).mul(28.0f));
            //eventueel kunnen we later nog toevoegen dat de trucks elkaars plaats innemen.
            }
        }
            
        //Groene truck
        for(int i = 0; i <  AantalContainersGroen; i++)
        {
            if(EpicVrachtOverslaan.Vector2.Distance(truckGroenPosition, containerGroenPosition.get(i)) <= 50.0)
            {
                //Als de container de vrachtwagen aangeraakt heeft, is er collision. Dan doen we:
                collisiongroen = 1;
            }
         
            if(collisiongroen == 1)
            {
            //Eerst veranderen we de sprite naar de volle versie.    
            truckGroenAppearance = new EpicVrachtOverslaan.Texture2D("Truck Groen Vol.png");
            //Dan rijdt de truck weg.            
            truckGeelPosition = truckGeelPosition.sub(EpicVrachtOverslaan.Vector2.UnitX.mul(dt).mul(300.0f));
            //eventueel kunnen we later nog toevoegen dat de trucks elkaars plaats innemen.
            }
        }
        
        
        //Rood
        for(int i = 0; i <  AantalContainersRood; i++)
        {
          
            if(EpicVrachtOverslaan.Vector2.Distance(truckRoodPosition, containerRoodPosition.get(i)) <= 50.0)
            {
            //Als de container de vrachtwagen aangeraakt heeft, is er collision. Dan doen we:

            collisionrood = 1;
            }
         
            if(collisionrood == 1)
            {
            //Eerst veranderen we de sprite naar de volle versie.    
            truckRoodAppearance = new EpicVrachtOverslaan.Texture2D("Truck Rood Vol.png");
            //Dan rijdt de truck weg.
            truckRoodPosition = truckRoodPosition.sub(EpicVrachtOverslaan.Vector2.UnitX.mul(dt).mul(300.0f));   
            }
        }
        
        for(int i = 0; i <  AantalContainersRood; i++)
        {
            if(EpicVrachtOverslaan.Vector2.Distance(grijperPosition, containerRoodPosition.get(i)) <=50.0 && Greenfoot.isKeyDown(" "))
            {
                JOptionPane.showMessageDialog(new JInternalFrame(), "Message content","Message title", JOptionPane.INFORMATION_MESSAGE);  
                
                containerRoodPosition[i];
            }
        }
        
        
        
        
       
        //Met deze code word het bewegen v.d. grijper met de w-a-s-d toetsen geregeld.
        if (Greenfoot.isKeyDown("A"))
          grijperPosition = grijperPosition.sub(EpicVrachtOverslaan.Vector2.UnitX.mul(dt).mul(300.0f));
        if (Greenfoot.isKeyDown("D"))
          grijperPosition = grijperPosition.add(EpicVrachtOverslaan.Vector2.UnitX.mul(dt).mul(300));
        if (Greenfoot.isKeyDown("W"))
          grijperPosition = grijperPosition.sub(EpicVrachtOverslaan.Vector2.UnitY.mul(dt).mul(300.0f));
        if (Greenfoot.isKeyDown("S"))
          grijperPosition = grijperPosition.add(EpicVrachtOverslaan.Vector2.UnitY.mul(dt).mul(300.0f));
        
        
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
        
        EpicVrachtOverslaan.DrawingContext.Draw(voortgansbalkAppearance, voortangsbalkPosition);
        EpicVrachtOverslaan.DrawingContext.Draw(indicatiebalkAppearance, indicatiebalkPosition);
        
        
        

    }    
    
    
}

