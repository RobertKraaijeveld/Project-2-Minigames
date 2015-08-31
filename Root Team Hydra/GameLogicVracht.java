import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*; //Hiermee importeren we diverse native-java functies en libraries.
import javax.swing.*; 
import java.io.*;
import java.awt.Color;
import java.awt.Font;


/**
 * EPIC 2: VRACHT OVERSLAAN
 * -------------------------
 * -------------------------
 * In dit spel moet de speler containers op vrachtwagens laden.
 * De containers en de vrachtwagens en de containers hebben verschillende kleuren.
 * De vrachtwagens worden random uitgekozen en komen 1 voor 1 binnen.
 * De speler moet dan met behulp van de kraan de container met de corresponderende kleur pakken, 
 * en op de vrachtwagen laden.
 * 
 * De speler kan als hij een container vastgepakt heeft deze weghalen in een "prullenbak".
 * Door deze met WASD en spatiebalk op het prullenbak symbool neer te leggen.
 * Dit houdt echter ook in dat de speler misschien de maximumscore niet meer kan bereiken.
 * 
 * Er loopt echter een klok van 2 minuten: als de speler binnen die tijd niet alle containers heeft
 * ingeladen is het game over.
 * Ook is er een silhouet van een schip zichtbaar rechtsbovenin, wat de balans van het schip voorstelt.
 * De speler moet dus ook door zorgvuldig te kiezen welke containers hij pakt het schip in balans houden.
 * Lukt dit hem niet, dan worden er punten afgetrokken.
 * 
 * -----------------------------------------
 * Controls:
 * 
 * Omhoog: Binnenframe kraan beweegt omhoog.
 * Omlaag: Binnenframe kraan beweegt omlaag.
 * Links: Buitenframe kraan beweegt naar links.
 * Rechts: Buitenframe kraan beweegt naar rechts.
 * Spatiebalk: Container vastpakken.
 * 
 * ------------------------------------------
 * Technische opzet:
 * Bovenin de code worden de variabelen en methodes gedeclareerd.
 * De Act() methode bevat de actiecode.
 * Onderin staat alle teken-code, plus enkele losse methodes.
 * 
 * Wij hebben gebruik gemaakt van de Bibliotheek van Dr. Maggiore.
 * Deze library maakt gebruik van Vectors en Textures: Een vector is een coordinaat in de spelwereld,
 * een texture is zoals de naam het zegt een afbeelding, die op de positie van een Vector getekend kan worden.
 * De Act() code specificeert als het ware de condities waaraan de teken-code zich moet houden.
 * 
 * Voor de logica van het spel is veel gebruik gemaakt van wat wij de 0, 1, 2 methode noemen.
 * De 0, de 1 en de 2 staan hierbij voor 3 verschillende statussen die een bepaalde entiteit,
 * zoals een container of een truck in kunnen nemen.
 * 
 * Ook hebben wij enkele losse methodes voor bv. het laten aanrijden van de trucks,
 * die aangeroepen worden in combinatie met de 0 1 2 methode.
 * 
 * Zie de comments voor specifiekere informatie.
 * -------------------------------------------
 * 
 * @author: Team Hydra 
 * @version: Final
 */



 public class GameLogicVracht extends Actor
 {
    //Deze vars zijn voor het pauzemenu.
    EpicVrachtOverslaan.Texture2D menuIcoonAppearance;
    EpicVrachtOverslaan.Vector2 menuIcoonPosition;
    
    EpicVrachtOverslaan.Vector2 pauzeAchtergrondPositie;
    List<EpicVrachtOverslaan.Vector2> KnopPosities;
    EpicVrachtOverslaan.Vector2 knopPositie1;
    EpicVrachtOverslaan.Vector2 knopPositie2;
    EpicVrachtOverslaan.Vector2 knopPositie3;

    EpicVrachtOverslaan.Texture2D pauzeAchtergrondAppearance;
    EpicVrachtOverslaan.Texture2D knop1Appearance;
    EpicVrachtOverslaan.Texture2D knop2Appearance;
    EpicVrachtOverslaan.Texture2D knop3Appearance;
    
    EpicVrachtOverslaan.Vector2 muisPositie;
    
    boolean Pauze = false;
    
    //Deze vars zijn voor de score.
    EpicVrachtOverslaan.Vector2 voortgangsbalkPosition;
    List<EpicVrachtOverslaan.Vector2> sterPositions;
    EpicVrachtOverslaan.Vector2 sterpositie1;
    EpicVrachtOverslaan.Vector2 sterpositie2;
    EpicVrachtOverslaan.Vector2 sterpositie3;
    EpicVrachtOverslaan.Vector2 sterpositie4;
    EpicVrachtOverslaan.Vector2 sterpositie5;
    EpicVrachtOverslaan.Vector2 sterpositie6;   
    EpicVrachtOverslaan.Vector2 goalsPosition;    
    List<EpicVrachtOverslaan.Vector2> balkjePositions;
    EpicVrachtOverslaan.Texture2D voortgangsbalkstreepAppearance;
    EpicVrachtOverslaan.Texture2D voortgangsbalkAppearance;
    EpicVrachtOverslaan.Texture2D sterAppearance;
    EpicVrachtOverslaan.Texture2D sterLeegAppearance;
    EpicVrachtOverslaan.Texture2D goalsAppearance;
    EpicVrachtOverslaan.Texture2D bootAppearance;
    
    
    //Deze vars zijn voor de timer/klok.
    private int startingTime;
    private int seconds = 180;
    private int clockType = 0;
    private long lastCurrentSecond;
    private long timeSaved = 0;
    private boolean timeUp = false;
    private boolean count = false;
    private boolean displayTime;
    private boolean countDown;
    private int truckNummer;
    private String text;
   
    //Deze method verandert de scorebalk en wordt aangeroepen indien er een container is afgeleverd.
    public int GameLogicVrachtUpdateScore()
    {
        lengtearray = balkjePositions.size();
        GameLogicVrachtvoortgangsPunten += 15;
        for (int i = 0; i < GameLogicVrachtvoortgangsPunten; i++)
        {
            EpicVrachtOverslaan.Vector2 newVoortangsPositie = EpicVrachtOverslaan.Vector2.Zero;
            newVoortangsPositie = new  EpicVrachtOverslaan.Vector2(377 + 1* i, 18);
            balkjePositions.add(newVoortangsPositie);
        }
        Scorewereld.scoreContainer(GameLogicVrachtvoortgangsPunten);
        return GameLogicVrachtvoortgangsPunten;
    }
    //Deze methode met bijbehorende vars laat de trucks 1 voor 1 rijden.
    boolean gespawnd = false;
    Random rand = new Random();
    
    public void KrijgNieuweTruck(){
        randomgetal = rand.nextInt(4)+ 1;
        gespawnd = false;
        while (gespawnd == false) {
           
            if (randomgetal == 1) {
               
                if (containerRoodPosition.size() > 0) {
                    gespawnd = true;
                    inrijdenRood = 1;
                }
                 
                else {
                    randomgetal = rand.nextInt(4)+ 1;
                }
            }
            
            else if (randomgetal == 2) {
                if (containerBlauwPosition.size() > 0) { 
                    gespawnd = true;
                    inrijdenBlauw = 1;
                }
                else {
                    randomgetal = rand.nextInt(4)+ 1;
                }
            }
            else if (randomgetal == 3) {
                if (containerGroenPosition.size() > 0) {
                    gespawnd = true;
                    inrijdenGroen = 1;
                }
                else {
                    randomgetal = rand.nextInt(4)+ 1;
                }
            }
            else if (randomgetal == 4) {
                if (containerGeelPosition.size() > 0) {
                    gespawnd = true;
                    inrijdenGeel = 1;
                }
                else {
                    randomgetal = rand.nextInt(4)+ 1;
                }
            }      
      }
    }
    int lengtearray;
    int GameLogicVrachtvoortgangsPunten = 0;
    int TotaalAantalContainers;
    int randomgetal;
    int inrijdenRood;
    int inrijdenBlauw;
    int inrijdenGeel;
    int inrijdenGroen;
    EpicVrachtOverslaan.Vector2 stopPosition;
    
    //Deze vars gaan straks de posities van de de verschillende "objecten" bevatten.
    List<EpicVrachtOverslaan.Vector2> containerGeelPosition;
    List<EpicVrachtOverslaan.Vector2> containerBlauwPosition;
    List<EpicVrachtOverslaan.Vector2> containerGroenPosition;
    List<EpicVrachtOverslaan.Vector2> containerRoodPosition;

    List<EpicVrachtOverslaan.Vector2> truckGeelPosition;
    List<EpicVrachtOverslaan.Vector2> truckBlauwPosition;
    List<EpicVrachtOverslaan.Vector2> truckGroenPosition;
    List<EpicVrachtOverslaan.Vector2> truckRoodPosition;
    
    List<EpicVrachtOverslaan.Vector2> truckGeelVolPosition;
    List<EpicVrachtOverslaan.Vector2> truckBlauwVolPosition;
    List<EpicVrachtOverslaan.Vector2> truckGroenVolPosition;
    List<EpicVrachtOverslaan.Vector2> truckRoodVolPosition;
    
    EpicVrachtOverslaan.Vector2 binnenFramePosition;
    EpicVrachtOverslaan.Vector2 buitenFramePosition;
    
    EpicVrachtOverslaan.Vector2 voortangsbalkPosition;
    EpicVrachtOverslaan.Vector2 indicatiebalkPosition;
    
    EpicVrachtOverslaan.Vector2 schipPosition;
    
    //Dit is de var die de achtergrond gaat bevatten.
    EpicVrachtOverslaan.Vector2 backgroundPosition;
    
    //Deze vars zijn voor de balanscounter.
    EpicVrachtOverslaan.Vector2 bovenBalansPunt;
    EpicVrachtOverslaan.Vector2 onderBalansPunt;
    EpicVrachtOverslaan.Vector2 balansIndicatorPunt;
    
    int bovenBalans;
    int onderBalans;
    
    boolean verlaagScore;
    
    //Deze vars zijn voor de prullenbak.
    EpicVrachtOverslaan.Texture2D prullenBakAppearance;
    EpicVrachtOverslaan.Vector2 prullenBakPosition;
      
    //Dit zijn de vars waar we de textures voor de verschillende "objecten" in gaan gooien.
    EpicVrachtOverslaan.Texture2D truckGeelAppearance;
    EpicVrachtOverslaan.Texture2D truckBlauwAppearance;
    EpicVrachtOverslaan.Texture2D truckGroenAppearance;
    EpicVrachtOverslaan.Texture2D truckRoodAppearance;
    EpicVrachtOverslaan.Texture2D truckRoodVolAppearance;
    EpicVrachtOverslaan.Texture2D truckGeelVolAppearance;
    EpicVrachtOverslaan.Texture2D truckBlauwVolAppearance;
    EpicVrachtOverslaan.Texture2D truckGroenVolAppearance;
    
    EpicVrachtOverslaan.Texture2D containerGeelAppearance;
    EpicVrachtOverslaan.Texture2D containerBlauwAppearance;
    EpicVrachtOverslaan.Texture2D containerGroenAppearance;
    EpicVrachtOverslaan.Texture2D containerRoodAppearance;
 
    EpicVrachtOverslaan.Texture2D buitenFrameAppearance;
    EpicVrachtOverslaan.Texture2D binnenFrameAppearance;
    
    EpicVrachtOverslaan.Texture2D voortgansbalkAppearance;
    EpicVrachtOverslaan.Texture2D indicatiebalkAppearance;
    
    EpicVrachtOverslaan.Texture2D schipAppearance;
    
    //Deze vars zijn voor de textures van balanscounter.
    EpicVrachtOverslaan.Texture2D balansIndicatorAppearance;
    EpicVrachtOverslaan.Vector2 BalansOnder;
    EpicVrachtOverslaan.Vector2 BalansBoven;
    
    //Deze ints worden gebruikt in de collision-detection tussen de trucks en de containers.
    int collisionrood;
    int collisiongeel;
    int collisionblauw;
    int collisiongroen;
    int hangenRood = 0;
    int hangenGeel = 0;
    int hangenBlauw = 0;
    int hangenGroen = 0;
    int containernummer;
    int rijdenRood = 0;
    int rijdenGroen = 0;
    int rijdenGeel = 0;
    int rijdenBlauw = 0;
    int aantalVolleRood;
    int aantalVolleGeel;
    int aantalVolleBlauw;
    int aantalVolleGroen;
    
    int nieuweTruck;
    //Deze ints worden gebruikt bij het spawnen van de containers, en geven zoals de naam al zegt het aantal containers dat per kleur gespawned wordt aan.

    
    float dt = 1.0f / 60.0f;
    
   public GameLogicVracht()
   {
        //Eerst geven we een message weer, die de bedoeling van het spel aan de gebruiker uitlegd.\
        JOptionPane.showMessageDialog(new JInternalFrame(), "Verplaats alle containers naar de wagens met de juiste kleur. \nGebruik de pijltjestoetsen om de kraan te besturen, maar let op de tijd en de balans van het schip! \n Als je de verkeerde container hebt gepakt, kan je deze in de prullenbak weggooien. \n Doe dit niet te vaak!", "Spelregels", JOptionPane.INFORMATION_MESSAGE);
        
        GreenfootImage img = null;
        setImage(img);
        
        //hier het schip van de balans
        balansIndicatorAppearance = new EpicVrachtOverslaan.Texture2D("Schip_Center.png");
        
        //Hier bepalen we de inhoud van de vector vars hierboven, we geven de "objecten" dus een positie.
             
        //Deze var is voor het stoppunt dat gebruikt wordt bij het spawnen v.d. vrachtwagens.
        stopPosition= new EpicVrachtOverslaan.Vector2(415,328);
        
        //Dit is de positie v.d. onderdelen v.d. kraan aan het begin van het spel.
        binnenFramePosition = new EpicVrachtOverslaan.Vector2(400,400);
        buitenFramePosition = new EpicVrachtOverslaan.Vector2(400,320);
        
        //De arrays voor de verschillende trucks die we gaan spawnen.
        truckGeelPosition = new ArrayList<EpicVrachtOverslaan.Vector2>();
        truckBlauwPosition = new ArrayList<EpicVrachtOverslaan.Vector2>();
        truckGroenPosition = new ArrayList<EpicVrachtOverslaan.Vector2>();
        truckRoodPosition = new ArrayList<EpicVrachtOverslaan.Vector2>();
        
        truckGeelVolPosition = new ArrayList<EpicVrachtOverslaan.Vector2>();
        truckBlauwVolPosition = new ArrayList<EpicVrachtOverslaan.Vector2>();
        truckGroenVolPosition = new ArrayList<EpicVrachtOverslaan.Vector2>();
        truckRoodVolPosition = new ArrayList<EpicVrachtOverslaan.Vector2>();
        
        
        //Die we hier vullen
        truckRoodPosition.add(new EpicVrachtOverslaan.Vector2(1100,328));
        truckRoodPosition.add(new EpicVrachtOverslaan.Vector2(1100,328));
        truckRoodPosition.add(new EpicVrachtOverslaan.Vector2(1100,328));
        truckRoodPosition.add(new EpicVrachtOverslaan.Vector2(1100,328));
        
        truckGroenPosition.add(new EpicVrachtOverslaan.Vector2(415,328));
        truckGroenPosition.add(new EpicVrachtOverslaan.Vector2(1100,328));
        truckGroenPosition.add(new EpicVrachtOverslaan.Vector2(1100,328));
        truckGroenPosition.add(new EpicVrachtOverslaan.Vector2(1100,328));
                
        truckBlauwPosition.add(new EpicVrachtOverslaan.Vector2(1100,328));
        truckBlauwPosition.add(new EpicVrachtOverslaan.Vector2(1100,328));
        truckBlauwPosition.add(new EpicVrachtOverslaan.Vector2(1100,328));
        truckBlauwPosition.add(new EpicVrachtOverslaan.Vector2(1100,328));
        
        truckGeelPosition.add(new EpicVrachtOverslaan.Vector2(1100,328));
        truckGeelPosition.add(new EpicVrachtOverslaan.Vector2(1100,328));
        truckGeelPosition.add(new EpicVrachtOverslaan.Vector2(1100,328));
        truckGeelPosition.add(new EpicVrachtOverslaan.Vector2(1100,328));
       
        //Dit zijn de arrays voor de posities van de containers.
        containerGeelPosition = new ArrayList<EpicVrachtOverslaan.Vector2>();
        containerBlauwPosition = new ArrayList<EpicVrachtOverslaan.Vector2>();
        containerGroenPosition = new ArrayList<EpicVrachtOverslaan.Vector2>();
        containerRoodPosition = new ArrayList<EpicVrachtOverslaan.Vector2>();
        
        //Hier bepalen we de posities van de containers, per kleur.
        containerRoodPosition.add(new EpicVrachtOverslaan.Vector2(460,213));
        containerRoodPosition.add(new EpicVrachtOverslaan.Vector2(383,243));
        containerRoodPosition.add(new EpicVrachtOverslaan.Vector2(306,243));
        containerRoodPosition.add(new EpicVrachtOverslaan.Vector2(229,213));
        
        containerGeelPosition.add(new EpicVrachtOverslaan.Vector2(460,183));
        containerGeelPosition.add(new EpicVrachtOverslaan.Vector2(383,155));
        containerGeelPosition.add(new EpicVrachtOverslaan.Vector2(306,183));
        containerGeelPosition.add(new EpicVrachtOverslaan.Vector2(229,243));
        
        containerBlauwPosition.add(new EpicVrachtOverslaan.Vector2(460,243));
        containerBlauwPosition.add(new EpicVrachtOverslaan.Vector2(383,183));
        containerBlauwPosition.add(new EpicVrachtOverslaan.Vector2(306,213));
        containerBlauwPosition.add(new EpicVrachtOverslaan.Vector2(229,155));
        
        containerGroenPosition.add(new EpicVrachtOverslaan.Vector2(460,155));
        containerGroenPosition.add(new EpicVrachtOverslaan.Vector2(383,213));
        containerGroenPosition.add(new EpicVrachtOverslaan.Vector2(306,155));
        containerGroenPosition.add(new EpicVrachtOverslaan.Vector2(229,183)); 
           
        //346, 317 | 347 80
        BalansOnder = new EpicVrachtOverslaan.Vector2(346, 317);
        BalansBoven = new EpicVrachtOverslaan.Vector2(347, 80);
        
        
        //En deze vector uiteraard de positie v.d. achtergrond.
        backgroundPosition = EpicVrachtOverslaan.Vector2.Zero;
        
        //En deze vector de positie van het schip.
        schipPosition = new EpicVrachtOverslaan.Vector2(300, 200);
        
        //Deze vectors zijn voor de balanspunten v.d. balanspunten.
        bovenBalansPunt = new EpicVrachtOverslaan.Vector2(460,121);
        onderBalansPunt = new EpicVrachtOverslaan.Vector2(230,121);
        
        //Deze vector bepaalt de plek van de prullenbak.
        prullenBakPosition  = new EpicVrachtOverslaan.Vector2(840, 435);
        
        //Deze vector bepaalt de plek van de balans-indicator.
        balansIndicatorPunt = new EpicVrachtOverslaan.Vector2(700, 50);
        
        //Hier doen we hetzelfde, maar dan voor de texture-vars.
        truckGeelAppearance = new EpicVrachtOverslaan.Texture2D("Truck Geel Leeg.png");
        truckBlauwAppearance = new EpicVrachtOverslaan.Texture2D("Truck Blauw Leeg.png");
        truckGroenAppearance = new EpicVrachtOverslaan.Texture2D("Truck Groen Leeg.png");
        truckRoodAppearance = new EpicVrachtOverslaan.Texture2D("Truck Rood Leeg.png");
        
        truckGeelVolAppearance = new EpicVrachtOverslaan.Texture2D("Truck Geel Vol.png");
        truckBlauwVolAppearance = new EpicVrachtOverslaan.Texture2D("Truck Blauw Vol.png");
        truckGroenVolAppearance = new EpicVrachtOverslaan.Texture2D("Truck Groen Vol.png");
        truckRoodVolAppearance = new EpicVrachtOverslaan.Texture2D("Truck Rood Vol.png");
        
        containerGeelAppearance = new EpicVrachtOverslaan.Texture2D("gele container.png");
        containerBlauwAppearance = new EpicVrachtOverslaan.Texture2D("blauwe container.png");
        containerGroenAppearance = new EpicVrachtOverslaan.Texture2D("groene container.png");
        containerRoodAppearance = new EpicVrachtOverslaan.Texture2D("rode container.png");
        
        binnenFrameAppearance = new EpicVrachtOverslaan.Texture2D("hijskraan_binnenrand.png");
        buitenFrameAppearance = new EpicVrachtOverslaan.Texture2D("hijskraan_buitenrand.png");
      
        indicatiebalkAppearance = new EpicVrachtOverslaan.Texture2D("indicatiebalk.png"); 
        
        prullenBakAppearance = new EpicVrachtOverslaan.Texture2D("prullenbak.png");
        
        schipAppearance = new EpicVrachtOverslaan.Texture2D("containerschip.png");
        
        //Deze vars zijn voor de voortgangs-scorebalk
        indicatiebalkPosition = new EpicVrachtOverslaan.Vector2(10, 300);
        voortgangsbalkPosition = new EpicVrachtOverslaan.Vector2(476, 18);
        sterPositions = new ArrayList<EpicVrachtOverslaan.Vector2>();
                
        sterpositie1 = new EpicVrachtOverslaan.Vector2(476, 38);
        sterpositie2 = new EpicVrachtOverslaan.Vector2(520, 38);
        sterpositie3 = new EpicVrachtOverslaan.Vector2(530, 38);
        sterpositie4 = new EpicVrachtOverslaan.Vector2(565, 38);
        sterpositie5 = new EpicVrachtOverslaan.Vector2(575, 38);
        sterpositie6 = new EpicVrachtOverslaan.Vector2(585, 38);
                
        sterPositions.add(sterpositie1);
        sterPositions.add(sterpositie2);
        sterPositions.add(sterpositie3);
        sterPositions.add(sterpositie4);
        sterPositions.add(sterpositie5);
        sterPositions.add(sterpositie6);
               
        goalsPosition = new EpicVrachtOverslaan.Vector2(527, 12);
                
        voortgangsbalkstreepAppearance = new EpicVrachtOverslaan.Texture2D("loods_progress_fill.png");
        voortgangsbalkAppearance = new EpicVrachtOverslaan.Texture2D("loods_balk_bg.png");
        balkjePositions = new ArrayList<EpicVrachtOverslaan.Vector2>();
        sterAppearance = new EpicVrachtOverslaan.Texture2D("loods_ster_active.png");
        sterLeegAppearance = new EpicVrachtOverslaan.Texture2D("loods_ster_inactive.png");
        goalsAppearance = new EpicVrachtOverslaan.Texture2D("loods_goals.png");
        
        menuIcoonPosition = new EpicVrachtOverslaan.Vector2(952-20,17);
        menuIcoonAppearance = new EpicVrachtOverslaan.Texture2D("menuicoontje.png");
   }  
   /**
     * Act - do whatever the GameLogicVracht wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    public void act() 
    {
    //We wrappen alle code hierin om te kijken of er niet op pauze gedrukt is.
    if (Pauze != true) {
            /*
             * SPAWN-CODE
             */
            
            //Deze code gebruikt de methode KrijgNieuweTruck hierboven, om random trucks van een bepaalde kleur te laten rijden.
            if (nieuweTruck == 1) {
                KrijgNieuweTruck();
                nieuweTruck = 2;
            }
            //Zie de methode voor terugkoppeling.
            if (inrijdenRood == 1) {
                truckRoodPosition.set(0, truckRoodPosition.get(0).sub(EpicVrachtOverslaan.Vector2.UnitX.mul(dt).mul(200.0f)) );
                if(EpicVrachtOverslaan.Vector2.Distance(stopPosition, truckRoodPosition.get(0)) <= 5.0)
                {
                    nieuweTruck = 0;
                    inrijdenRood = 0;
                   
                    truckRoodPosition.set(0, stopPosition);
                }
            }
            if (inrijdenBlauw == 1) {
                truckBlauwPosition.set(0, truckBlauwPosition.get(0).sub(EpicVrachtOverslaan.Vector2.UnitX.mul(dt).mul(200.0f)) );
                if(EpicVrachtOverslaan.Vector2.Distance(stopPosition, truckBlauwPosition.get(0)) <= 5.0)
                {
                    nieuweTruck = 0;
                    inrijdenBlauw = 0;
                   
                    truckBlauwPosition.set(0, stopPosition);
                }
            }
            if (inrijdenGroen == 1) {
               truckGroenPosition.set(0, truckGroenPosition.get(0).sub(EpicVrachtOverslaan.Vector2.UnitX.mul(dt).mul(200.0f)) );
               if(EpicVrachtOverslaan.Vector2.Distance(stopPosition, truckGroenPosition.get(0)) <= 5.0)
                {
                    nieuweTruck = 0;
                    inrijdenGroen = 0;
                    truckGroenPosition.set(0, stopPosition);
                }
            }
            if (inrijdenGeel == 1) {
                truckGeelPosition.set(0, truckGeelPosition.get(0).sub(EpicVrachtOverslaan.Vector2.UnitX.mul(dt).mul(200.0f)) );
                if(EpicVrachtOverslaan.Vector2.Distance(stopPosition, truckGeelPosition.get(0)) <= 5.0)
                {
                    nieuweTruck = 0;
                    inrijdenGeel = 0;
                    
                    truckGeelPosition.set(0, stopPosition);
                }
            }
            EpicVrachtOverslaan.DrawingContext drawingContext = EpicVrachtOverslaan.DrawingContext;
            drawingContext.BeginDraw();
            
            /*
             * GRIJPER EN TRUCK CODE
             */   
            
            //Rood
            //for(int j = 0; j < truckRoodPosition.size(); j++)
            
                if (hangenRood != 2) { //als er geen container hangt
                    for(int i = 0; i < containerRoodPosition.size(); i++)
                    {
                        if(EpicVrachtOverslaan.Vector2.Distance(binnenFramePosition, containerRoodPosition.get(i)) <=15.0 && Greenfoot.isKeyDown(" "))
                        {
                            collisionrood = 0;
                            //Als de grijper binnen bereik van de container is en spatie gedrukt word, dan wordt de pos. van de container en de grijper gelijk.                
                            containerRoodPosition.set(i, binnenFramePosition);
                            hangenRood = 1;
                            hangenBlauw = 2;
                            hangenGroen = 2;
                            hangenGeel = 2;
                            containernummer = i;
                        }           
                    }
                }
                if (hangenRood == 1)
                {
                    containerRoodPosition.set(containernummer, binnenFramePosition);
                    //Deze code update de positie van iedere container als hij aan de grijper hang.
                
                    for(int j = 0; j < truckRoodPosition.size(); j++) {
                        if(EpicVrachtOverslaan.Vector2.Distance(truckRoodPosition.get(j), containerRoodPosition.get(containernummer)) <= 15.0 & Greenfoot.isKeyDown(" "))
                        {
                            //Als de container de vrachtwagen aangeraakt heeft, is er collision. Dan doen we:
                            collisionrood = 1;
                            truckNummer = j;
                            
                            //En rijdt de truck weg.
                            truckRoodVolPosition.add(truckRoodPosition.get(j));
                            truckRoodPosition.remove(j);
                            aantalVolleRood = truckRoodVolPosition.size();
                            
                        }
                    }                    
                    //Deze code handelt de prullenbakcollision af.
                    if(EpicVrachtOverslaan.Vector2.Distance(containerRoodPosition.get(containernummer), prullenBakPosition) <= 30 && Greenfoot.isKeyDown(" "))
                    {
                       //Zo ja, dan verwijderen we de container en zetten we hangen op 0.
                       BalansCheck();
                       containerRoodPosition.remove(containernummer);
                       hangenRood = 0;
                        hangenBlauw = 0;
                        hangenGeel = 0;
                        hangenGroen = 0;
                   }                    
                    if(collisionrood == 1)
                    {
                        //Dan rijdt de truck weg.
                        rijdenRood = 1;
                        //en wordt een nieuwe truck gespawned
                        containerRoodPosition.remove(containernummer);
                        if (containerRoodPosition.size() > 0 || containerGroenPosition.size() > 0 || containerGeelPosition.size() > 0 || containerBlauwPosition.size() > 0) {
                            nieuweTruck = 1;
                        }
                        //En wordt de score bijgewerkt.
                        if (GameLogicVrachtvoortgangsPunten < 200)
                        {
                            GameLogicVrachtUpdateScore();
                        }
                        
                        
                        hangenRood = 0;
                        hangenBlauw = 0;
                        hangenGeel = 0;
                        hangenGroen = 0;
                      }     
                }          
                if (rijdenRood == 1)
                {
                     //Hier rijdt de truck daadwerkelijk weg.
                    for (int h = 0; h < truckRoodVolPosition.size(); h++) {
                        truckRoodVolPosition.set(h, truckRoodVolPosition.get(h).sub(EpicVrachtOverslaan.Vector2.UnitX.mul(dt).mul(200.0f)));
                    }
                    collisionrood = 0;
                }
            
            //Blauw 
            
                if (hangenBlauw != 2) { //als er geen container hangt
                    for(int i = 0; i <  containerBlauwPosition.size(); i++)
                    {
                        if(EpicVrachtOverslaan.Vector2.Distance(binnenFramePosition, containerBlauwPosition.get(i)) <=15.0 && Greenfoot.isKeyDown(" "))
                        {
                            collisionblauw = 0;
                            //Als de grijper binnen bereik van de container is en spatie gedrukt word, dan wordt de pos. van de container en de grijper gelijk.                
                            containerBlauwPosition.set(i, binnenFramePosition);
                            hangenBlauw = 1;
                            hangenRood = 2;
                            hangenGeel = 2;
                            hangenGroen = 2;
                            containernummer = i;
                        }                
                    }
                }
                 if (hangenBlauw == 1)
                {
                    containerBlauwPosition.set(containernummer, binnenFramePosition);
                    //Deze code update de positie van iedere container als hij aan de grijper hang.
                
                    for(int j = 0; j < truckBlauwPosition.size(); j++) {
                        if(EpicVrachtOverslaan.Vector2.Distance(truckBlauwPosition.get(j), containerBlauwPosition.get(containernummer)) <= 15.0 & Greenfoot.isKeyDown(" "))
                        {
                            //Als de container de vrachtwagen aangeraakt heeft, is er collision. Dan doen we:
                            collisionblauw = 1;
                            truckNummer = j;
                            //En wordt de truck getekend met veranderde texture.
                            //En dan rijdt de truck weg.
                            truckBlauwVolPosition.add(truckBlauwPosition.get(j));
                            truckBlauwPosition.remove(j);
                            aantalVolleBlauw = truckBlauwVolPosition.size();
                        }
                    }
                    
                    //Deze code handelt de prullenbakcollision af.
                    if(EpicVrachtOverslaan.Vector2.Distance(containerBlauwPosition.get(containernummer), prullenBakPosition) <= 30 && Greenfoot.isKeyDown(" "))
                    {
                       //Zo ja, dan verwijderen we de container en zetten we hangen op 0.
                       containerBlauwPosition.remove(containernummer);
                       BalansCheck();
                       hangenRood = 0;
                        hangenBlauw = 0;
                        hangenGeel = 0;
                        hangenGroen = 0;
                    }
                   
                    if(collisionblauw == 1)
                    {
                        //Dan rijdt de truck weg.
                        rijdenBlauw = 1;
                        //en wordt een nieuwe truck gespawned
                        containerBlauwPosition.remove(containernummer);
                        if (containerRoodPosition.size() > 0 || containerGroenPosition.size() > 0 || containerGeelPosition.size() > 0 || containerBlauwPosition.size() > 0) {
                            nieuweTruck = 1;
                        }
                        //En wordt de score bijgewerkt.
                        if (GameLogicVrachtvoortgangsPunten < 200)
                        {
                            GameLogicVrachtUpdateScore();
                        }
                        hangenRood = 0;
                        hangenBlauw = 0;
                        hangenGeel = 0;
                        hangenGroen = 0;
                    }     
                }          
                if (rijdenBlauw ==1)
                {
                    //Hier rijdt de truck daadwerkelijk weg
                    for (int h = 0; h < truckBlauwVolPosition.size(); h++) {
                        truckBlauwVolPosition.set(h, truckBlauwVolPosition.get(h).sub(EpicVrachtOverslaan.Vector2.UnitX.mul(dt).mul(200.0f)));
                    }                   
                    collisionblauw = 0;
                }
            
            
            
            //Geel
            
                if (hangenGeel != 2) { //als er geen container hangt
                    for(int i = 0; i <  containerGeelPosition.size(); i++)
                    {
                        if(EpicVrachtOverslaan.Vector2.Distance(binnenFramePosition, containerGeelPosition.get(i)) <=15.0 && Greenfoot.isKeyDown(" "))
                        {
                            collisiongeel = 0;
                            //Als de grijper binnen bereik van de container is en spatie gedrukt word, dan wordt de pos. van de container en de grijper gelijk.                
                            containerGeelPosition.set(i, binnenFramePosition);
                            hangenGeel = 1;
                            hangenRood = 2;
                            hangenBlauw = 2;
                            hangenGroen = 2;
                            containernummer = i;                            
                            collisionblauw = 0;                            
                        }
                    }
                }
                if (hangenGeel == 1)
                {
                    containerGeelPosition.set(containernummer, binnenFramePosition);
                    //Deze code update de positie van iedere container als hij aan de grijper hang.                
                    for(int j = 0; j < truckGeelPosition.size(); j++) {
                        if(EpicVrachtOverslaan.Vector2.Distance(truckGeelPosition.get(j), containerGeelPosition.get(containernummer)) <= 15.0 & Greenfoot.isKeyDown(" "))
                        {
                            //Als de container de vrachtwagen aangeraakt heeft, is er collision. Dan doen we:
                            collisiongeel = 1;
                            truckNummer = j;
                            //En wordt de truck getekend met veranderde texture.
                            //En rijdt de truck weg.
                            
                            truckGeelVolPosition.add(truckGeelPosition.get(j));
                            truckGeelPosition.remove(j);
                            aantalVolleGeel = truckGeelVolPosition.size();                                                  
                        }
                    }
                    
                    //Deze code handelt de prullenbakcollision af.
                    if(EpicVrachtOverslaan.Vector2.Distance(containerGeelPosition.get(containernummer), prullenBakPosition) <= 30 && Greenfoot.isKeyDown(" "))
                    {
                       //Zo ja, dan verwijderen we de container en zetten we hangen op 0.
                       containerGeelPosition.remove(containernummer);
                       BalansCheck();
                       hangenRood = 0;
                        hangenBlauw = 0;
                        hangenGeel = 0;
                        hangenGroen = 0;
                    }
                    
                    if(collisiongeel == 1)
                    {
                        //Dan rijdt de truck weg.
                        rijdenGeel = 1;
                        //en wordt een nieuwe truck gespawned
                        containerGeelPosition.remove(containernummer);
                        if (containerRoodPosition.size() > 0 || containerGroenPosition.size() > 0 || containerGeelPosition.size() > 0 || containerBlauwPosition.size() > 0) {
                            nieuweTruck = 1;
                        }
                        
                        //En wordt de score bijgewerkt.
                        if (GameLogicVrachtvoortgangsPunten < 200)
                        {
                            GameLogicVrachtUpdateScore();
                        }                                                
                        hangenRood = 0;
                        hangenBlauw = 0;
                        hangenGeel = 0;
                        hangenGroen = 0;
                      
                      }     
                }          
                if (rijdenGeel ==1)
                {
                     //Hier rijdt de truck daadwerkelijk weg.
                    for (int h = 0; h < truckGeelVolPosition.size(); h++) {
                        truckGeelVolPosition.set(h, truckGeelVolPosition.get(h).sub(EpicVrachtOverslaan.Vector2.UnitX.mul(dt).mul(200.0f)));
                    }                                        
                    collisiongeel = 0;
                }
       
            //Groen             
                if (hangenGroen != 2)
                {
                    for(int i = 0; i <  containerGroenPosition.size(); i++)
                    {
                        if(EpicVrachtOverslaan.Vector2.Distance(binnenFramePosition, containerGroenPosition.get(i)) <=15.0 && Greenfoot.isKeyDown(" "))
                        {
                            collisiongroen = 0;
                            //Als de grijper binnen bereik van de container is en spatie gedrukt word, dan wordt de pos. van de container en de grijper gelijk.                
                            containerGroenPosition.set(i, binnenFramePosition);
                            hangenGroen = 1;
                            hangenRood = 2;
                            hangenGeel = 2;
                            hangenBlauw = 2;
                            containernummer = i;
                        }                
                    }
                }
                if (hangenGroen == 1)
                {
                    containerGroenPosition.set(containernummer, binnenFramePosition);
                    //Deze code update de positie van iedere container als hij aan de grijper hangt.                
                        for(int j = 0; j < truckGroenPosition.size(); j++) {
                            if(EpicVrachtOverslaan.Vector2.Distance(truckGroenPosition.get(j), containerGroenPosition.get(containernummer)) <= 15.0 & Greenfoot.isKeyDown(" "))
                            {
                            //Als de container de vrachtwagen aangeraakt heeft, is er collision. Dan doen we:
                            collisiongroen = 1;
                            truckNummer = j;
                            //En wordt de truck getekend met veranderde texture.                       
                            truckGroenVolPosition.add(truckGroenPosition.get(j));
                            truckGroenPosition.remove(j);
                            aantalVolleGroen = truckGroenVolPosition.size();                                                        
                        }
                    }
                    
                    //Deze code handelt de prullenbakcollision af.
                    if(EpicVrachtOverslaan.Vector2.Distance(containerGroenPosition.get(containernummer), prullenBakPosition) <= 30 && Greenfoot.isKeyDown(" "))
                    {
                       //Zo ja, dan verwijderen we de container en zetten we hangen op 0.
                       containerGroenPosition.remove(containernummer);
                       BalansCheck();
                       hangenRood = 0;
                        hangenBlauw = 0;
                        hangenGeel = 0;
                        hangenGroen = 0;
                    }
                    
                    if(collisiongroen == 1)
                    {
                        //Dan rijdt de truck weg.
                        rijdenGroen = 1;
                        //en wordt een nieuwe truck gespawned
                        containerGroenPosition.remove(containernummer);
                        if (containerRoodPosition.size() > 0 || containerGroenPosition.size() > 0 || containerGeelPosition.size() > 0 || containerBlauwPosition.size() > 0) {
                            nieuweTruck = 1;
                        }
                        
                        //En wordt de score bijgewerkt.
                        if (GameLogicVrachtvoortgangsPunten < 200) {
                            GameLogicVrachtUpdateScore();
                        }                                                
                        hangenRood = 0;
                        hangenBlauw = 0;
                        hangenGeel = 0;
                        hangenGroen = 0;
                      }     
                }          
                if (rijdenGroen == 1)
                {
                    //Hier rijdt de truck daadwerkelijk weg, en checken we de balans.
                    for (int h = 0; h < truckGroenVolPosition.size(); h++) {
                        truckGroenVolPosition.set(h, truckGroenVolPosition.get(h).sub(EpicVrachtOverslaan.Vector2.UnitX.mul(dt).mul(200.0f)));
                    }                                              
                    collisiongroen = 0;
                }
            
                                            
            /*
             * 
             * Control CODE
            */
           
            //Met deze code word het bewegen v.d. grijper met de Pijltjes toetsen geregeld. als spatie eveneens is ingedrukt is boost mode geactiveerd
            if (Greenfoot.isKeyDown("left"))
            {
                if (Greenfoot.isKeyDown("shift")) {
                 buitenFramePosition = buitenFramePosition.sub(EpicVrachtOverslaan.Vector2.UnitX.mul(dt).mul(120.0f));
                 binnenFramePosition = binnenFramePosition.sub(EpicVrachtOverslaan.Vector2.UnitX.mul(dt).mul(120.0f));
                }
                buitenFramePosition = buitenFramePosition.sub(EpicVrachtOverslaan.Vector2.UnitX.mul(dt).mul(60.0f));
                binnenFramePosition = binnenFramePosition.sub(EpicVrachtOverslaan.Vector2.UnitX.mul(dt).mul(60.0f));
            }
           
            if (Greenfoot.isKeyDown("right")){
                if (Greenfoot.isKeyDown("shift")) {
                 buitenFramePosition = buitenFramePosition.add(EpicVrachtOverslaan.Vector2.UnitX.mul(dt).mul(120.0f));
                 binnenFramePosition = binnenFramePosition.add(EpicVrachtOverslaan.Vector2.UnitX.mul(dt).mul(120.0f));
                }
                buitenFramePosition = buitenFramePosition.add(EpicVrachtOverslaan.Vector2.UnitX.mul(dt).mul(60.0f));
                binnenFramePosition = binnenFramePosition.add(EpicVrachtOverslaan.Vector2.UnitX.mul(dt).mul(60.0f));
            }
            if (Greenfoot.isKeyDown("up")){
                if (Greenfoot.isKeyDown("shift")) {
                    binnenFramePosition = binnenFramePosition.sub(EpicVrachtOverslaan.Vector2.UnitY.mul(dt).mul(120.0f));
                }
                binnenFramePosition = binnenFramePosition.sub(EpicVrachtOverslaan.Vector2.UnitY.mul(dt).mul(60.0f));
            }
            if (Greenfoot.isKeyDown("down")){
                if (Greenfoot.isKeyDown("shift")) {
                    binnenFramePosition = binnenFramePosition.add(EpicVrachtOverslaan.Vector2.UnitY.mul(dt).mul(120.0f));
                }
                binnenFramePosition = binnenFramePosition.add(EpicVrachtOverslaan.Vector2.UnitY.mul(dt).mul(60.0f));
            }
             
            
            /*
            * DRAW-CODE
            */
           
            //Alle code hieronder doet het daadwerkelijke "plaatsen" v.d. objecten onder de door ons hierboven gegeven voorwaarden.                    
            //Hier wordt het schip getekend. Als eerste, aangezien deze containers op zich krijgt.
            EpicVrachtOverslaan.DrawingContext.Draw(schipAppearance, schipPosition);            
            //Hier worden de trucks uit de arrays getekend dmv. een for loop.            
            for(EpicVrachtOverslaan.Vector2 a : truckRoodPosition)
            {
                drawingContext.Draw(truckRoodAppearance, a);
            }            
            for(EpicVrachtOverslaan.Vector2 a : truckGroenPosition)
            {
                drawingContext.Draw(truckGroenAppearance, a);
            }            
            for(EpicVrachtOverslaan.Vector2 a : truckBlauwPosition)
            {
                drawingContext.Draw(truckBlauwAppearance, a);
            }            
            for(EpicVrachtOverslaan.Vector2 a : truckGeelPosition)
            {
                drawingContext.Draw(truckGeelAppearance, a);
            }            
            for(EpicVrachtOverslaan.Vector2 a : truckRoodVolPosition)
            {
                drawingContext.Draw(truckRoodVolAppearance, a);
            }            
            for(EpicVrachtOverslaan.Vector2 a : truckGroenVolPosition)
            {
                drawingContext.Draw(truckGroenVolAppearance, a);
            }            
            for(EpicVrachtOverslaan.Vector2 a : truckBlauwVolPosition)
            {
                drawingContext.Draw(truckBlauwVolAppearance, a);
            }            
            for(EpicVrachtOverslaan.Vector2 a : truckGeelVolPosition)
            {
                drawingContext.Draw(truckGeelVolAppearance, a);
            }            
            //Hier worden de containers getekend.
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
            
            //Hier tekenen we de prullenbak.
            EpicVrachtOverslaan.DrawingContext.Draw(prullenBakAppearance, prullenBakPosition);
                   
            //Hier worden de 3 verschillende vormen v.d. balansindicator getekend, afhankelijk v.d. balans van het schip.
            EpicVrachtOverslaan.DrawingContext.Draw(balansIndicatorAppearance, balansIndicatorPunt);
           
            //Hier worden de kraanonderdelen getekend.
            EpicVrachtOverslaan.DrawingContext.Draw(binnenFrameAppearance, binnenFramePosition);
            EpicVrachtOverslaan.DrawingContext.Draw(buitenFrameAppearance, buitenFramePosition);
                            
            //Hier wordt de scorebalk getekend met desbetreffende condities.
            EpicVrachtOverslaan.DrawingContext.Draw(voortgangsbalkAppearance, voortgangsbalkPosition);
            
            //Afhankelijk van het aantal punten tekenen we de scorebalk inc. sterren. 
            //Als het puntenaantal hoger of gelijk aan 200 is, heeft de speler gewonnen.
            if (GameLogicVrachtvoortgangsPunten >= 100) {
               EpicVrachtOverslaan.DrawingContext.Draw(sterAppearance, sterPositions.get(1-1));  
            }
            else if (GameLogicVrachtvoortgangsPunten < 100){
                EpicVrachtOverslaan.DrawingContext.Draw(sterLeegAppearance, sterPositions.get(1-1));                
            }
            if (GameLogicVrachtvoortgangsPunten >= 150) {
               EpicVrachtOverslaan.DrawingContext.Draw(sterAppearance, sterPositions.get(2-1));  
               EpicVrachtOverslaan.DrawingContext.Draw(sterAppearance, sterPositions.get(3-1)); 
            }
            else if (GameLogicVrachtvoortgangsPunten < 150){
                EpicVrachtOverslaan.DrawingContext.Draw(sterLeegAppearance, sterPositions.get(2-1));
                EpicVrachtOverslaan.DrawingContext.Draw(sterLeegAppearance, sterPositions.get(3-1));
            }
            if (GameLogicVrachtvoortgangsPunten >= 200) {
               EpicVrachtOverslaan.DrawingContext.Draw(sterAppearance, sterPositions.get(4-1));
               EpicVrachtOverslaan.DrawingContext.Draw(sterAppearance, sterPositions.get(5-1)); 
               EpicVrachtOverslaan.DrawingContext.Draw(sterAppearance, sterPositions.get(6-1)); 
               JOptionPane.showMessageDialog(new JInternalFrame(), "Je hebt gewonnen! Klik op OK om terug naar het hoofdmenu te gaan.","Gefeliciteerd!", JOptionPane.INFORMATION_MESSAGE);
               Greenfoot.setWorld(new hoofdmenu());
            }
            else if (GameLogicVrachtvoortgangsPunten < 200){
                EpicVrachtOverslaan.DrawingContext.Draw(sterLeegAppearance, sterPositions.get(4-1));
                EpicVrachtOverslaan.DrawingContext.Draw(sterLeegAppearance, sterPositions.get(5-1));
                EpicVrachtOverslaan.DrawingContext.Draw(sterLeegAppearance, sterPositions.get(6-1));               
            }
           
            for (int i = lengtearray; i < balkjePositions.size();  i++) {                
                drawingContext.Draw(voortgangsbalkstreepAppearance, balkjePositions.get(i)); 
            }                         
            EpicVrachtOverslaan.DrawingContext.Draw(goalsAppearance, goalsPosition);
            
            //Deze code is voor het menuicoontje.
            EpicVrachtOverslaan.DrawingContext.Draw(menuIcoonAppearance, menuIcoonPosition);            
            if (Greenfoot.getMouseInfo() != null) {
                muisPositie = new EpicVrachtOverslaan.Vector2(Greenfoot.getMouseInfo().getX(), Greenfoot.getMouseInfo().getY());
                if (EpicVrachtOverslaan.Vector2.Distance(muisPositie, menuIcoonPosition) < 16.0 && Greenfoot.getMouseInfo().getButton() == 1) {
                    Pauze = true;
                }
            }
        }
   
    /*
     * PAUZE-MENU CODE
     */
    
    //Als de menuknop is aangeklikt, stoppen we de timer en tekenen we het menu, inc. functies.
    if (Pauze == true) {
       Clock.stopClock();
       pauzeAchtergrondPositie = new EpicVrachtOverslaan.Vector2(475, 275);
       KnopPosities = new ArrayList<EpicVrachtOverslaan.Vector2>();
       knopPositie1 = new EpicVrachtOverslaan.Vector2(450,150);
       knopPositie2 = new EpicVrachtOverslaan.Vector2(450, 300);
       knopPositie3 = new EpicVrachtOverslaan.Vector2(450, 450);
       KnopPosities.add(knopPositie1);
       KnopPosities.add(knopPositie2);
       KnopPosities.add(knopPositie3);
       
       pauzeAchtergrondAppearance = new EpicVrachtOverslaan.Texture2D("options_menu_bg.png");
       knop1Appearance = new EpicVrachtOverslaan.Texture2D("options_menu_startscherm.png");
       knop2Appearance = new EpicVrachtOverslaan.Texture2D("options_menu_reset.png");
       knop3Appearance = new EpicVrachtOverslaan.Texture2D("options_menu_verder.png");
       if(Greenfoot.getMouseInfo() != null)
       {
            //Deze var bevat de positie v.d. muis.
            muisPositie = new EpicVrachtOverslaan.Vector2(Greenfoot.getMouseInfo().getX(), Greenfoot.getMouseInfo().getY());
            //Afsluiten
            if(EpicVrachtOverslaan.Vector2.Distance(knopPositie1, muisPositie) <= 110.0 && Greenfoot.getMouseInfo().getButton() == 1)
            {                   
                 Greenfoot.setWorld(new hoofdmenu());
            }
            //Spel resetten
            if(EpicVrachtOverslaan.Vector2.Distance(knopPositie2, muisPositie) <= 110.0 && Greenfoot.getMouseInfo().getButton() == 1)
            {  
                 int result = JOptionPane.showConfirmDialog(new JInternalFrame(), "Weet u zeker dat u de score wilt resetten? \ndit kan niet ongedaan gemaakt worden. \nHierna keert u terug naar het hoofdmenu.","Score resetten", JOptionPane.WARNING_MESSAGE); 
                     if (result == JOptionPane.OK_OPTION){                        
                        Scorewereld.scoreLoods(0);
                        Scorewereld.scoreContainer(0);
                        Scorewereld.scoreXray(0);
                        JOptionPane.showMessageDialog(new JInternalFrame(), "Score succesvol gereset! \nVeel succes!","Score gereset", JOptionPane.PLAIN_MESSAGE);
                        Greenfoot.setWorld(new hoofdmenu());
                        }
                     else {
                        
                        }                 
            }
            //Verder spelen
            if(EpicVrachtOverslaan.Vector2.Distance(knopPositie3, muisPositie) <= 110.0 && Greenfoot.getMouseInfo().getButton() == 1)
            {
                 Pauze = false;                
            }
       }
       EpicVrachtOverslaan.DrawingContext.Draw(pauzeAchtergrondAppearance, pauzeAchtergrondPositie);
       EpicVrachtOverslaan.DrawingContext.Draw(knop1Appearance, KnopPosities.get(0));
       EpicVrachtOverslaan.DrawingContext.Draw(knop2Appearance, KnopPosities.get(1));
       EpicVrachtOverslaan.DrawingContext.Draw(knop3Appearance, KnopPosities.get(2));
    }
  }
  
  //Deze methode verlaagt de score.
  public void BalansCounterConsequentie()
  {
       GameLogicVrachtvoortgangsPunten -= 2;
  }
   public void BalansCheck()
           {
            onderBalans = 0;
            bovenBalans = 0;            
                 //Rood
                 for(int i = 0; i < containerRoodPosition.size(); i++)
                 {                     
                    //Als er containers in de buurt van de rechterkant/onderste kant van het schip zijn, verhogen we de onderBalanscounter.
                    if
                    (EpicVrachtOverslaan.Vector2.Distance(onderBalansPunt, containerRoodPosition.get(i)) <=125.0)
                    {
                        onderBalans++;
                    }
                    //Als ze dat niet zijn, verlagen we de balanscounter.
                    else if(EpicVrachtOverslaan.Vector2.Distance(onderBalansPunt, containerRoodPosition.get(i)) > 125.0)
                    {
                         bovenBalans++;
                    }                                                                   
                 }                        
            
            //Blauw   
                 for(int i = 0; i < containerBlauwPosition.size(); i++)
                 {
                    
                    //Als er containers in de buurt van de rechterkant/onderste kant van het schip zijn, verhogen we de onderBalanscounter.
                    if
                    (EpicVrachtOverslaan.Vector2.Distance(onderBalansPunt, containerBlauwPosition.get(i)) <=125.0)
                    {
                         onderBalans++;
                    }
                    //Als ze dat niet zijn, verlagen we de balanscounter.
                    else if(EpicVrachtOverslaan.Vector2.Distance(onderBalansPunt, containerBlauwPosition.get(i)) > 125.0)
                    {
                        bovenBalans++;
                    }                                               
                }
            
                //Geel             
                 for(int i = 0; i < containerGeelPosition.size(); i++)
                 {                     
                    //Als er containers in de buurt van de rechterkant/onderste kant van het schip zijn, verhogen we de onderBalanscounter.
                    if
                    (EpicVrachtOverslaan.Vector2.Distance(onderBalansPunt, containerGeelPosition.get(i)) <=125.0)
                    {
                         onderBalans++;
                    }
                    //Als ze dat niet zijn, verlagen we de balanscounter.
                    else if(EpicVrachtOverslaan.Vector2.Distance(onderBalansPunt, containerGeelPosition.get(i)) > 125.0)
                    {
                        bovenBalans++;
                    }                          
                 }
           
            //groen
                 for(int i = 0; i < containerGroenPosition.size(); i++)
                 {                    
                    //Als er containers in de buurt van de rechterkant/onderste kant van het schip zijn, verhogen we de onderBalanscounter.
                    if
                    (EpicVrachtOverslaan.Vector2.Distance(onderBalansPunt, containerGroenPosition.get(i)) <=125.0)
                    {
                        onderBalans++;                        
                    }
                    //Als ze dat niet zijn, verlagen we de balanscounter.
                    else if(EpicVrachtOverslaan.Vector2.Distance(onderBalansPunt, containerGroenPosition.get(i)) > 125.0)
                    {                        
                        bovenBalans++;
                    }                                           
                 }
             //Hier kijken we welke van de 2 counters groter is. Als 1 counter groter is dan de andere helt het schip naar die kant, en wordt het counterplaatje aangepast.
                    //CONSEQUENTIE
                    if(bovenBalans > onderBalans)
                    {
                        balansIndicatorAppearance = new EpicVrachtOverslaan.Texture2D("Schip_Rechts.png");
                        BalansCounterConsequentie();
                    }
                    else if(onderBalans > bovenBalans)
                    {
                        balansIndicatorAppearance = new EpicVrachtOverslaan.Texture2D("Schip_Links.png");
                        BalansCounterConsequentie();
                    }
                    else
                    {
                        balansIndicatorAppearance = new EpicVrachtOverslaan.Texture2D("Schip_Center.png");                        
                    }
           }
}
