import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
import javax.swing.*;
import java.*;

/**
 *
 * EPIC 3: INVOERCONTROLE
 * -------------------------
 * -------------------------
 * De speler krijgt een lopende band met in het midden een xraymachine te zien.
 * Er komen paketten voorbij. Als de paketten langs de xraymachine komen kan de speler kort hun inhoud zien.
 * Aan de hand van de verboden voorwerpenlijst moet de speler bepalen of de inhoud toegestaan (groene knop),
 * of niet toegestaan (rode knop) is.
 * 
 * De speler krijgt na zijn beslissing in een alert te zien of hij de goede of foute keuze heeft gemaakt.
 * Daarna wordt de koffer in een verzegeld pakket gestopt, met een toegestaan/niet toegestaan stempel 
 * die gelijk is aan de eerdere keuze v.d. speler.
 * 
 * De speler mag maximaal 3x een fout maken, anders is het gameover.
 * 
 * Er zijn 4 levels. In het eerste level komen 4 paketten langs, in ieder volgend level wordt
 * het aantal paketten verhoogd met 2.
 * -----------------------------------------
 * Controls:
 * 
 * LMB: Klikken op de toegestaan/niet toegestaan knop
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
 * * Voor de logica van het spel is veel gebruik gemaakt van wat wij de 0, 1, 2 methode noemen.
 * De 0, de 1 en de 2 staan hierbij voor 3 verschillende statussen die een bepaalde entiteit,
 * zoals een container of een truck in kunnen nemen.
 * 
 * Ook hebben wij enkele losse methodes voor bv. het uitkiezen van de koffers en hun inhoud.
 * die aangeroepen worden in combinatie met de 0 1 2 methode. In dit spel staan deze methodes
 * wat meer op de voorgrond dan in bv. Epic 2.
 * 
 * Zie de comments voor specifiekere informatie.
 * 
 */
public class GameLogicXray extends Actor
{
    //Deze vars zijn voor het pauzemenu.
    minigamexrayworld.Texture2D menuIcoonAppearance;
    minigamexrayworld.Vector2 menuIcoonPosition;    
    minigamexrayworld.Vector2 pauzeAchtergrondPositie;
    List<minigamexrayworld.Vector2> KnopPosities;
    minigamexrayworld.Vector2 knopPositie1;
    minigamexrayworld.Vector2 knopPositie2;
    minigamexrayworld.Vector2 knopPositie3;
    minigamexrayworld.Texture2D pauzeAchtergrondAppearance;
    minigamexrayworld.Texture2D knop1Appearance;
    minigamexrayworld.Texture2D knop2Appearance;
    minigamexrayworld.Texture2D knop3Appearance;
    minigamexrayworld.Vector2 muisPositieMenu;
    boolean Pauze = false;

    //Deze vars gaan alle informatie over de scorebalk bevatten.
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
    
    //xray monitor vars
    minigamexrayworld.Vector2 machinePosition;
    minigamexrayworld.Texture2D machineAppearance;
    
    //Deze arrays bevatten de xrayplaatjes/pakketposities en de posities van de verzegelde paketten.
    ArrayList<minigamexrayworld.Vector2> XrayKofferSlecht; 
    ArrayList<minigamexrayworld.Vector2> XrayKofferGoed;
    ArrayList<minigamexrayworld.Vector2> weggaanGoedKoffer;
    ArrayList<minigamexrayworld.Vector2> weggaanFoutKoffer;
    ArrayList<minigamexrayworld.Vector2> timerPositions;
    
    minigamexrayworld.Vector2 kofferpositie1;
    minigamexrayworld.Vector2 balkjepositie;
    minigamexrayworld.Vector2 centerXray;
    
    //Deze vars gaan de appearance van verschillende soorten koffers en xrayplaatjes bevatten.
    minigamexrayworld.Texture2D kofferAppearance;
    minigamexrayworld.Texture2D inhoudGoedAppearance;
    minigamexrayworld.Texture2D inhoudFoutAppearance;
    minigamexrayworld.Texture2D KofferVerkeerdAppearance;
    minigamexrayworld.Texture2D KofferGoedAppearance;
    minigamexrayworld.Texture2D tijdbalk;
    minigamexrayworld.Texture2D timerAppearance;

    //al deze vars worden straks gebruikt bij het spawnen en despawnen van de paketten.
    Random randomGeneratorKoffers;
    int randomgetal;
    int aantalGoedeKoffers;
    int aantalSlechteKoffers;
    int inkomenGoed;
    int inkomenSlecht;
    boolean gespawnd;
    int weggaanFout;
    int weggaanGoed;
    int nieuweKoffer = 1;
    int randomtexturegetal;
    int aantalTimerPos;
    int aantalTimer;
    
    //Deze class bevat de vars voor de levensmeter
    public static class levensMeter
    {
        int levens = 3;
        minigamexrayworld.Texture2D levensMeterAppearance;
        List<minigamexrayworld.Vector2> levensMeterPositions;
    }
    levensMeter levensMeterObject;
    
    //Dit is de methode die de koffers inc. inhoud uitkiest en spawnt
    public void SpawnKoffer()
    {
        randomgetal = randomGeneratorKoffers.nextInt(2)+ 1;
        gespawnd = false;
        //Als gespawnd false is, (dus er wordt op dit moment geen koffer gespawnd):
         while (gespawnd == false) {
             //Kijken we of het randomgetal 1 of 2 is.
             if (randomgetal == 1) {
                 //als er nog koffers zijn
                 if (aantalGoedeKoffers > 0) {
                     //Als het randomgetal 1 is voegen we een koffer met toegestane inhoud aan het spel toe.
                     aantalGoedeKoffers--;
                     //Gespawnd is nu true, zodat er niet meerdere koffers tegelijk komen.
                     gespawnd = true;
                     //We roepen deze methode aan om de texture v.d. gemaakte paketten te bepalen.
                     TextureKoffer();
                     //Deze var wordt later in de logic gebruikt.
                     inkomenGoed = 1;
                  }
                  else 
                 {
                     randomgetal = randomGeneratorKoffers.nextInt(2)+ 1;
                 }
             }
         
             else if (randomgetal == 2) {
                 //als er nog koffers zijn
                 if (aantalSlechteKoffers > 0) {
                     //Als het randomgetal 1 is voegen we een koffer met  niet toegestane inhoud aan het spel toe.
                     aantalSlechteKoffers--;
                     //Gespawnd is nu true, zodat er niet meerdere koffers tegelijk komen.
                     gespawnd = true;
                     //We roepen deze methode aan om de texture v.d. gemaakte pakketen te bepalen.
                     TextureKoffer();
                     //Deze var wordt later in de logic gebruikt.
                     inkomenSlecht = 1;
                 }
                 else 
                 {
                     randomgetal = randomGeneratorKoffers.nextInt(2)+ 1;
                 }
             }
        }
    }
       
    //Deze methode bepaalt aan de hand van een random getal een texture voor de paketten.
    public void TextureKoffer() {
        randomtexturegetal = randomGeneratorKoffers.nextInt(3)+ 1;
        if (randomtexturegetal == 1) 
        {
                 kofferAppearance = new minigamexrayworld.Texture2D("Box1.png");
        }
        else if (randomtexturegetal == 2) 
        {
                 kofferAppearance = new minigamexrayworld.Texture2D("Box2.png");
        }
        else if (randomtexturegetal == 3) 
        {
                 kofferAppearance = new minigamexrayworld.Texture2D("Box3.png");
        }
    }
    
    //Deze methode doet hetzelfde als TextureKoffer(),
    //maar wordt voor de inhoud van het pakket gebruikt als deze "goed" is.
    public void TextureInhoudGoed() {
        if (GameLogicXrayvoortgangsPunten < 100) {
            randomtexturegetal = randomGeneratorKoffers.nextInt(7)+ 1;
        }
        else if (GameLogicXrayvoortgangsPunten >= 100) {
            randomtexturegetal = randomGeneratorKoffers.nextInt(15)+ 7;
        }
        
        if (randomtexturegetal == 1) 
        {
                 inhoudGoedAppearance = new minigamexrayworld.Texture2D("Koffergoed1.png");
        }
        else if (randomtexturegetal == 2) 
        {
                 inhoudGoedAppearance = new minigamexrayworld.Texture2D("Koffergoed2.png");
        }
        else if (randomtexturegetal == 3) 
        {
                 inhoudGoedAppearance = new minigamexrayworld.Texture2D("Koffergoed3.png");
        }
        else if (randomtexturegetal == 4) 
        {
                 inhoudGoedAppearance = new minigamexrayworld.Texture2D("Koffergoed4.png");
        }
        else if (randomtexturegetal == 5) 
        {
                 inhoudGoedAppearance = new minigamexrayworld.Texture2D("Koffergoed5.png");
        }
        else if (randomtexturegetal == 6) 
        {
                 inhoudGoedAppearance = new minigamexrayworld.Texture2D("Koffergoed6.png");
        }
        else if (randomtexturegetal == 7) 
        {
                 inhoudGoedAppearance = new minigamexrayworld.Texture2D("Koffergoed7.png");
        }
        else if (randomtexturegetal == 8) 
        {
                 inhoudGoedAppearance = new minigamexrayworld.Texture2D("Koffergoed8.png");
        }
        else if (randomtexturegetal == 9) 
        {
                 inhoudGoedAppearance = new minigamexrayworld.Texture2D("Koffergoed9.png");
        }
        else if (randomtexturegetal == 10) 
        {
                 inhoudGoedAppearance = new minigamexrayworld.Texture2D("Koffergoed10.png");
        }
        else if (randomtexturegetal == 11) 
        {
                 inhoudGoedAppearance = new minigamexrayworld.Texture2D("Koffergoed11.png");
        }
        else if (randomtexturegetal == 12) 
        {
                 inhoudGoedAppearance = new minigamexrayworld.Texture2D("Koffergoed12.png");
        }
        else if (randomtexturegetal == 13) 
        {
                 inhoudGoedAppearance = new minigamexrayworld.Texture2D("Koffergoed13.png");
        }
        else if (randomtexturegetal == 14) 
        {
                 inhoudGoedAppearance = new minigamexrayworld.Texture2D("Koffergoed14.png");
        }
        else if (randomtexturegetal == 15) 
        {
                 inhoudGoedAppearance = new minigamexrayworld.Texture2D("Koffergoed15.png");
        }
    }
    
    //Deze methode doet hetzelfde als TextureKoffer(),
    //maar wordt voor de inhoud van het pakket gebruikt als deze "fout" is.
    public void TextureInhoudFout() {
        if (GameLogicXrayvoortgangsPunten < 100) {
            randomtexturegetal = randomGeneratorKoffers.nextInt(7)+ 1;
            
        }
        else if (GameLogicXrayvoortgangsPunten >= 100) {
            randomtexturegetal = randomGeneratorKoffers.nextInt(15)+ 7;
        }
        if (randomtexturegetal == 1) 
        {
                 inhoudFoutAppearance = new minigamexrayworld.Texture2D("Kofferfout1.png");
        }
        else if (randomtexturegetal == 2) 
        {
                 inhoudFoutAppearance = new minigamexrayworld.Texture2D("Kofferfout2.png");
        }
        else if (randomtexturegetal == 3) 
        {
                 inhoudFoutAppearance = new minigamexrayworld.Texture2D("Kofferfout3.png");
        }
        else if (randomtexturegetal == 4) 
        {
                 inhoudFoutAppearance = new minigamexrayworld.Texture2D("Kofferfout4.png");
        }
        else if (randomtexturegetal == 5) 
        {
                 inhoudFoutAppearance = new minigamexrayworld.Texture2D("Kofferfout5.png");
        }
        else if (randomtexturegetal == 6) 
        {
                 inhoudFoutAppearance = new minigamexrayworld.Texture2D("Kofferfout6.png");
        }
        else if (randomtexturegetal == 7) 
        {
                 inhoudFoutAppearance = new minigamexrayworld.Texture2D("Kofferfout7.png");
        }
        else if (randomtexturegetal == 8) 
        {
                 inhoudGoedAppearance = new minigamexrayworld.Texture2D("Kofferfout8.png");
        }
        else if (randomtexturegetal == 9) 
        {
                 inhoudGoedAppearance = new minigamexrayworld.Texture2D("Kofferfout9.png");
        }
        else if (randomtexturegetal == 10) 
        {
                 inhoudGoedAppearance = new minigamexrayworld.Texture2D("Kofferfout10.png");
        }
        else if (randomtexturegetal == 11) 
        {
                 inhoudGoedAppearance = new minigamexrayworld.Texture2D("Kofferfout11.png");
        }
        else if (randomtexturegetal == 12) 
        {
                 inhoudGoedAppearance = new minigamexrayworld.Texture2D("Kofferfout12.png");
        }
        else if (randomtexturegetal == 13) 
        {
                 inhoudGoedAppearance = new minigamexrayworld.Texture2D("Kofferfout13.png");
        }
        else if (randomtexturegetal == 14) 
        {
                 inhoudGoedAppearance = new minigamexrayworld.Texture2D("Kofferfout14.png");
        }
        else if (randomtexturegetal == 15) 
        {
                 inhoudGoedAppearance = new minigamexrayworld.Texture2D("Kofferfout15.png");
        }
    }
    
    //Deze var gaat de positie vd. muis bevatten.
    minigamexrayworld.Vector2 muisPositie;

    //Deze textures worden bij het bijhouden vd score gebruikt.
    minigamexrayworld.Texture2D voortgangsbalkstreepAppearance;
    minigamexrayworld.Texture2D voortgangsbalkAppearance;
    minigamexrayworld.Texture2D sterAppearance;
    minigamexrayworld.Texture2D sterLeegAppearance;
    minigamexrayworld.Texture2D goalsAppearance;
 
    minigamexrayworld.Texture2D bootAppearance;

    //Dit is de texture voor de groene knop
    minigamexrayworld.Vector2 AcceptButtonPosition;
    minigamexrayworld.Texture2D AcceptButtonAppearance;
    
    //Dit is de texture voor de rode knop
    minigamexrayworld.Vector2 DeclineButtonPosition;
    minigamexrayworld.Texture2D DeclineButtonAppearance;
   
    //voorwerpen lijst 
    minigamexrayworld.Texture2D VoorwerplijstkleinAppearance;
    minigamexrayworld.Texture2D VoorwerplijstAppearance;
    minigamexrayworld.Vector2 VoorwerplijstkleinPosition;
    minigamexrayworld.Vector2 VoorwerplijstPosition;
    boolean voorwerplijstshow = false;
    
    //Deze posities geven aan wanneer een pakket het eind van de lopende band heeft bereikt.
    minigamexrayworld.Vector2 stopPosition;
    minigamexrayworld.Vector2 xrayeindPosition;
    
    //Spreekt voor zich.
    Random randomGenerator;
    
    //enkele misc. vars die in de act() gebruikt gaan worden
    int lengtearray;
    int GameLogicXrayvoortgangsPunten = 0;
    float dt = 1.0f / 60.0f;
   
    public GameLogicXray() 
    {
        //Eerst geven we een message weer, die de bedoeling van het spel aan de gebruiker uitlegd.\
        JOptionPane.showMessageDialog(new JInternalFrame(), "Constant komen er koffers langs en worden gescant met röntgenstralen, \njij moet deze koffers controleren op verboden voorwerpen.\nDe lijst met verboden voorwerpen kan je bekijken door rechts op het lijste op de tafel te klikken.", "Spelregels", JOptionPane.INFORMATION_MESSAGE);
        
        //De vars voor de scorebalk worden hier gevuld, hun positie wordt bepaald.
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
       
        //voorwerpenlijst   
        VoorwerplijstAppearance = new minigamexrayworld.Texture2D("voorwerpen_lijst_groot.png");
        VoorwerplijstkleinAppearance = new minigamexrayworld.Texture2D("voorwerpen_lijst.png");
        VoorwerplijstPosition = new minigamexrayworld.Vector2(476, 275);
        VoorwerplijstkleinPosition = new minigamexrayworld.Vector2(910, 190);
        
        //Hier maken we arrays aan voor het spawnen van de paketten.
        weggaanGoedKoffer = new ArrayList<minigamexrayworld.Vector2>();
        weggaanFoutKoffer = new ArrayList<minigamexrayworld.Vector2>();
        XrayKofferSlecht = new ArrayList<minigamexrayworld.Vector2>();
        XrayKofferGoed = new ArrayList<minigamexrayworld.Vector2>();
        randomGeneratorKoffers = new Random();
      
        //Dit is de positie voor de allereerste pakket.
        kofferpositie1 = new minigamexrayworld.Vector2(-100, 310);
       
        //Hier maken we een nieuw levensmeterobject aan
        levensMeterObject = new levensMeter();
        levensMeterObject.levensMeterPositions = new ArrayList<minigamexrayworld.Vector2>();
        levensMeterObject.levensMeterAppearance = new minigamexrayworld.Texture2D("hart.png");
        
        //Dit is de groene knop positie/texture.
        AcceptButtonPosition = new minigamexrayworld.Vector2(362, 140);
        AcceptButtonAppearance = new minigamexrayworld.Texture2D("xray-goedkeuren.png");
        
        //Dit is derode knop positie/texture.
        DeclineButtonPosition = new minigamexrayworld.Vector2(562, 140);
        DeclineButtonAppearance = new minigamexrayworld.Texture2D("xray-afwijzen.png");
        
        //Hier worden de textures voor de voortgangsbalk bepaalt.
        voortgangsbalkstreepAppearance = new minigamexrayworld.Texture2D("loods_progress_fill.png");
        voortgangsbalkAppearance = new minigamexrayworld.Texture2D("loods_balk_bg.png");
        balkjePositions = new ArrayList<minigamexrayworld.Vector2>();
        sterAppearance = new minigamexrayworld.Texture2D("loods_ster_active.png");
        sterLeegAppearance = new minigamexrayworld.Texture2D("loods_ster_inactive.png");
        goalsAppearance = new minigamexrayworld.Texture2D("loods_goals.png");
        
        //Een nieuwe randomobject
        randomGenerator = new Random();
        
        //Dit is de texture voor de xraymachine
        machineAppearance = new minigamexrayworld.Texture2D("xray_monitor_groot.png");
        //En zijn positie.
        machinePosition = new minigamexrayworld.Vector2(460,334);
      
        //Deze var is voor het stoppunt dat gebruikt wordt bij het spawnen v.d. pakketten.
        stopPosition = new minigamexrayworld.Vector2(400,310);
        xrayeindPosition = new minigamexrayworld.Vector2(581,310);
        
        //Deze var bepaalt waar de inhoud van ieder pakket getekent wordt
        centerXray = new minigamexrayworld.Vector2(460,320);
        
        //Dit is de texture voor de verzegelde pakketten na beoordeling.
        KofferGoedAppearance = new minigamexrayworld.Texture2D("goede_koffer.png");
        KofferVerkeerdAppearance = new minigamexrayworld.Texture2D("verkeerde_koffer.png");
        tijdbalk = new minigamexrayworld.Texture2D("balkjevul.png");
        timerAppearance = new minigamexrayworld.Texture2D("xray_timer_groen.png");
        timerPositions = new ArrayList<minigamexrayworld.Vector2>();
      
        //Hier bepalen we de positie van het menuicoon.
        menuIcoonPosition = new minigamexrayworld.Vector2(952-20,17);
        menuIcoonAppearance = new minigamexrayworld.Texture2D("menuicoontje.png");
        
        //Hier roepen we de methode aan die het level van het spel en de bijbehorende hoeveelheid paketten bepaalt.
        MaakLevel();
    }
    /**
     * Act - do whatever the GameLogicXray wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
   public void act() 
   {
          if(Pauze != true)
          {  
            //We beginnen meteen met het drawen.
            minigamexrayworld.DrawingContext drawingContext = minigamexrayworld.DrawingContext;
            drawingContext.BeginDraw();
            
            /*
             * SPAWN EN CHECK-CODE
             */
            
            //Als nieuwekoffer == 1 spawnen we een nieuwe pakket
            if (nieuweKoffer == 1) {
                    SpawnKoffer();
                    nieuweKoffer = 2;
            }
            
            //Als inkomengoed 1 is (wat bepaalt wordt door de randomgenerators in de SpawnKoffer())
            if (inkomenGoed == 1 ) {
                //Laten we een pakket van type goed naar rechts lopen/
                XrayKofferGoed.set(0, XrayKofferGoed.get(0).add(minigamexrayworld.Vector2.UnitX.mul(dt).mul(150.0f)) );
                if (minigamexrayworld.Vector2.Distance(stopPosition, XrayKofferGoed.get(0)) <= 70.0) {
                    //Als het pakket bij de xraymachine is, wordt de texture van de binnenkant bepaalt.
                    TextureInhoudGoed();
                    inkomenGoed = 2;
                }
            }
            
            if (inkomenGoed == 2) {
                //Als het vorige pakket onder de xray ligt, spawnen we de volgende.
                XrayKofferGoed.set(0, XrayKofferGoed.get(0).add(minigamexrayworld.Vector2.UnitX.mul(dt).mul(90.0f)) );
                TimerBalk();
                if (minigamexrayworld.Vector2.Distance(xrayeindPosition, XrayKofferGoed.get(0)) <= 2.0) { 
                    //Als de huidige koffer bij het eind van de xray is gekomen en er is geen beslissing genomen, gaat er 1 leven af.
                    inkomenGoed = 0;
                    //geven we een error
                    JOptionPane.showMessageDialog(new JInternalFrame(), "Helaas! u was te laat met uw beoordeling geven. \n zorg voor de volgende keer dat u snel uw beslissing maakt", "Te late beoordeling!", JOptionPane.INFORMATION_MESSAGE);
                    //Verlagen we de levens
                    levensMeterObject.levens --;
                    levensMeterObject.levensMeterPositions.clear();
                    //En laten we het pakket wegrollen    
                    weggaanGoed = 1;
                    //En voegen we de pakket aan de "prullenbak-array toe"
                    weggaanGoedKoffer.add(XrayKofferGoed.get(0));
                    XrayKofferGoed.remove(0);
                    
                    aantalTimer = 0;
                    timerPositions.clear();
                    //En spawnen we een nieuwe koffer
                    nieuweKoffer = 1;
                 }
                if(Greenfoot.getMouseInfo() != null) {
                    //Eerst maken we deze deze var. Deze var bevat de positie v.d. muis.
                    muisPositie = new minigamexrayworld.Vector2(Greenfoot.getMouseInfo().getX(), Greenfoot.getMouseInfo().getY());                    
                    if(minigamexrayworld.Vector2.Distance(muisPositie, AcceptButtonPosition) <= 90.0 && Greenfoot.getMouseInfo().getButton() == 1 ){
                        //MAAR als de goede knop ingedrukt word bij een pakket van type goed, 
                        inkomenGoed = 0;
                        //Geven we dit weer
                        JOptionPane.showMessageDialog(new JInternalFrame(), "Goed zo! \n De koffer bevatte geen verboden voorwerpen", "Goede beoordeling!", JOptionPane.INFORMATION_MESSAGE);
                        //En laten we het pakket wegrollen    
                        weggaanGoed = 1;
                        //En voegen we de pakket aan de "prullenbak-array toe"
                        weggaanGoedKoffer.add(XrayKofferGoed.get(0));
                        XrayKofferGoed.remove(0);
                        
                        //Voegen we score toe
                        GameLogicXrayUpdateScore();
                        //reseten we de timer
                        aantalTimer = 0;
                        timerPositions.clear();
                        //En spawnen we een nieuwe koffer
                        nieuweKoffer = 1;
                    }
                    if(minigamexrayworld.Vector2.Distance(muisPositie, DeclineButtonPosition) <= 90.0 && Greenfoot.getMouseInfo().getButton() == 1 ){
                        //MAAR als de foute knop ingedrukt word bij een pakket van type goed, 
                        inkomenGoed = 0;
                        //Geven we dit weer
                        JOptionPane.showMessageDialog(new JInternalFrame(), "De koffer bevatte GEEN verboden voorwerpen \n u heeft deze verkeerd beoordeeld, Helaas!", "Foute beoordeling!", JOptionPane.INFORMATION_MESSAGE);
                        //Verlagen we de levens
                        levensMeterObject.levens --;
                        levensMeterObject.levensMeterPositions.clear();
                        //En laten we het pakket wegrollen    
                        weggaanGoed = 1;
                        //En voegen we de pakket aan de "prullenbak-array toe"
                        weggaanGoedKoffer.add(XrayKofferGoed.get(0));
                        XrayKofferGoed.remove(0);
                        //reseten we de timer
                        aantalTimer = 0;
                        timerPositions.clear();
                        //En spawnen we een nieuwe koffer
                        nieuweKoffer = 1;                        
                    }
                }                
            }
            
            //Als inkomenslecht 1 is (wat bepaalt wordt door de randomgenerators in de SpawnKoffer())
            if (inkomenSlecht == 1) {
                XrayKofferSlecht.set(0, XrayKofferSlecht.get(0).add(minigamexrayworld.Vector2.UnitX.mul(dt).mul(150.0f)) );
                //Als het pakket bij de xraymachine is, wordt de texture van de binnenkant bepaalt.
                if (minigamexrayworld.Vector2.Distance(stopPosition, XrayKofferSlecht.get(0)) <= 70.0) {
                    TextureInhoudFout();
                    inkomenSlecht = 2;
                }
            }
            
            if (inkomenSlecht == 2) {
                XrayKofferSlecht.set(0, XrayKofferSlecht.get(0).add(minigamexrayworld.Vector2.UnitX.mul(dt).mul(90.0f)) );
                TimerBalk();
                //Als het vorige pakket onder de xray ligt, spawnen we de volgende.
                if (minigamexrayworld.Vector2.Distance(xrayeindPosition, XrayKofferSlecht.get(0)) <= 2.0) { 
                    //Als de huidige koffer bij het eind van de xray is gekomen en er is geen beslissing genomen, gaat e
                    inkomenSlecht = 0;
                    //geven we een error
                    JOptionPane.showMessageDialog(new JInternalFrame(), "Helaas! u was te laat met uw beoordeling geven. \n zorg voor de volgende keer dat u snel uw beslissing maakt", "Te late beoordeling!", JOptionPane.INFORMATION_MESSAGE);
                    //Verlagen we de levens
                    levensMeterObject.levens --;
                    levensMeterObject.levensMeterPositions.clear();
                    //En laten we het pakket wegrollen    
                    weggaanFout = 1;
                    //En voegen we de pakket aan de "prullenbak-array toe"
                    weggaanFoutKoffer.add(XrayKofferSlecht.get(0));
                    XrayKofferSlecht.remove(0);
                    aantalTimer = 0;
                    timerPositions.clear();
                    //En spawnen we een nieuwe koffer
                    nieuweKoffer = 1;
                }
                if(Greenfoot.getMouseInfo() != null) {
                    //Maken we deze deze var. Deze var bevat de positie v.d. muis.
                    muisPositie = new minigamexrayworld.Vector2(Greenfoot.getMouseInfo().getX(), Greenfoot.getMouseInfo().getY());
                    if(minigamexrayworld.Vector2.Distance(muisPositie, AcceptButtonPosition) <= 90.0 && Greenfoot.getMouseInfo().getButton() == 1 ){
                       //MAAR als de goede knop ingedrukt word bij een pakket van type slecht, 
                       inkomenSlecht = 0;
                       //Geven we een error
                       JOptionPane.showMessageDialog(new JInternalFrame(), "De koffer bevatte wel degelijk verboden voorwerpen \n u heeft deze verkeerd beoordeeld, Helaas!", "Foute beoordeling!", JOptionPane.INFORMATION_MESSAGE);
                       //Verlagen we de levens
                       levensMeterObject.levens --;
                       levensMeterObject.levensMeterPositions.clear();
                       //En laten we het pakket wegrollen    
                       weggaanFout = 1;
                       //En voegen we de pakket aan de "prullenbak-array toe"
                       weggaanFoutKoffer.add(XrayKofferSlecht.get(0));
                       XrayKofferSlecht.remove(0);
                       //reseten we de timer
                        aantalTimer = 0;
                        timerPositions.clear();
                       //En spawnen we een nieuwe koffer
                       nieuweKoffer = 1;
                        
                    }
                    if(minigamexrayworld.Vector2.Distance(muisPositie, DeclineButtonPosition) <= 90.0 && Greenfoot.getMouseInfo().getButton() == 1 ){
                        //MAAR als de slechte knop ingedrukt word bij een pakket van type slecht, 
                        inkomenSlecht = 0;
                        //geven we dit weer
                        JOptionPane.showMessageDialog(new JInternalFrame(), "Goed zo! \n De koffer bevatte verboden voorwerpen", "Goede beoordeling!", JOptionPane.INFORMATION_MESSAGE);
                        //En laten we het pakket wegrollen    
                        weggaanFout = 1;
                        //En voegen we de pakket aan de "prullenbak-array toe"
                        weggaanFoutKoffer.add(XrayKofferSlecht.get(0));
                        XrayKofferSlecht.remove(0);
                        //En verhogen we de score
                        GameLogicXrayUpdateScore();
                        //reseten we de timer
                        aantalTimer = 0;
                        timerPositions.clear();
                        //En spawnen nieuwe koffer
                        nieuweKoffer = 1;                        
                    }
                }                
            }           
           
            /*
             * DRAW-CODE
             * 
             */
            
            //Hieronder tekenen we alle vectors met de desbetreffende textures
            //Aan de hand van de condities in de code hierboven
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
            for (int i = lengtearray; i < balkjePositions.size();  i++) {
                minigamexrayworld.DrawingContext.Draw(voortgangsbalkstreepAppearance, balkjePositions.get(i)); 
            }
            minigamexrayworld.DrawingContext.Draw(goalsAppearance, goalsPosition);
           
            //Dit is de draw code voor de koffers in verschillende situaties.
               if (weggaanGoed == 1) {
                for (int i = 0; i < weggaanGoedKoffer.size(); i++ ) {
                    weggaanGoedKoffer.set(i, weggaanGoedKoffer.get(i).add(minigamexrayworld.Vector2.UnitX.mul(dt).mul(200.0f)) );
                }
                for (minigamexrayworld.Vector2 a : weggaanGoedKoffer) {
                  drawingContext.Draw(KofferGoedAppearance, a);
                }
            }        
            if (weggaanFout == 1) {
                for (int i = 0; i < weggaanFoutKoffer.size(); i++ ) {
                    weggaanFoutKoffer.set(i, weggaanFoutKoffer.get(i).add(minigamexrayworld.Vector2.UnitX.mul(dt).mul(200.0f)) );
                    drawingContext.Draw(KofferVerkeerdAppearance, weggaanFoutKoffer.get(i));
                }
                
            }
            for (minigamexrayworld.Vector2 a : XrayKofferSlecht) {
                drawingContext.Draw(kofferAppearance, a);
            }
            for (minigamexrayworld.Vector2 a : XrayKofferGoed) {
                drawingContext.Draw(kofferAppearance, a);
            }
            
            //Deze code tekent de xraymachine.
            drawingContext.Draw(machineAppearance, machinePosition);
            if (inkomenGoed == 2) {
               drawingContext.Draw(inhoudGoedAppearance, centerXray);
            }
            for (int i = aantalTimerPos; i < timerPositions.size();  i++) {
                minigamexrayworld.DrawingContext.Draw(timerAppearance, timerPositions.get(i)); 
            }
            if (inkomenSlecht == 2) {
                drawingContext.Draw(inhoudFoutAppearance, centerXray);
            }
            drawingContext.Draw(AcceptButtonAppearance, AcceptButtonPosition);
            drawingContext.Draw(DeclineButtonAppearance, DeclineButtonPosition);
            
            //Dit is de draw code voor de levens. Als de int levens omlaag gaat, worden er minder levens getekend.
            for(int i = 0; i < levensMeterObject.levens; i++)
            {
                minigamexrayworld.Vector2 newLevenPosition = minigamexrayworld.Vector2.Zero;
                newLevenPosition = new minigamexrayworld.Vector2(170 + 60 * i, 40);
                levensMeterObject.levensMeterPositions.add(newLevenPosition);
            }      
            for(minigamexrayworld.Vector2 a : levensMeterObject.levensMeterPositions)
            {
                drawingContext.Draw(levensMeterObject.levensMeterAppearance, a);
            }
            drawingContext.Draw(VoorwerplijstkleinAppearance, VoorwerplijstkleinPosition);
            
            //Deze code tekent het menuicoontje inc logica.
            drawingContext.Draw(menuIcoonAppearance, menuIcoonPosition);
            
            if (Greenfoot.getMouseInfo() != null)
            {
                    muisPositieMenu = new minigamexrayworld.Vector2(Greenfoot.getMouseInfo().getX(), Greenfoot.getMouseInfo().getY());
                    if (minigamexrayworld.Vector2.Distance(muisPositieMenu, menuIcoonPosition) < 16.0 && Greenfoot.getMouseInfo().getButton() == 1) {
                        Pauze = true;
                    }
                    if (minigamexrayworld.Vector2.Distance(muisPositieMenu, VoorwerplijstkleinPosition) < 47.0 && Greenfoot.getMouseInfo().getButton() == 1) {
                        
                        voorwerplijstshow = true;
                        Pauze = true;
                        
                    }
            }
            
            //En last but not least checken we hier of de speler game over is.
            if(levensMeterObject.levens <= 0)
            {
                JOptionPane.showMessageDialog(new JInternalFrame(), "Helaas, je hebt geen levens meer.", "Game Over!", JOptionPane.INFORMATION_MESSAGE);
                Greenfoot.setWorld(new hoofdmenu());
            }
        } 
      
        if (Pauze == true && voorwerplijstshow == true) {
            minigamexrayworld.DrawingContext.Draw(VoorwerplijstAppearance, VoorwerplijstPosition);
                if (Greenfoot.getMouseInfo() != null) {
                    muisPositieMenu = new minigamexrayworld.Vector2(Greenfoot.getMouseInfo().getX(), Greenfoot.getMouseInfo().getY());
                    if (Greenfoot.getMouseInfo().getButton() == 1 &&  minigamexrayworld.Vector2.Distance(muisPositieMenu, VoorwerplijstPosition) < 160.0) {
                        voorwerplijstshow = false;
                        Pauze = false;
                    }
                    if (Greenfoot.getMouseInfo().getButton() == 3) {
                        voorwerplijstshow = false;
                        Pauze = false;
                    }
                }
        }
     
         /*
         * PAUZE-MENU CODE
         */
        
        //Als de menuknop is aangeklikt, stoppen we de timer en tekenen we het menu, inc. functies.
        if (Pauze == true && voorwerplijstshow != true) {
           Clock.stopClock();
           pauzeAchtergrondPositie = new minigamexrayworld.Vector2(475, 275);
           KnopPosities = new ArrayList<minigamexrayworld.Vector2>();
           knopPositie1 = new minigamexrayworld.Vector2(450,150);
           knopPositie2 = new minigamexrayworld.Vector2(450, 300);
           knopPositie3 = new minigamexrayworld.Vector2(450, 450);
           KnopPosities.add(knopPositie1);
           KnopPosities.add(knopPositie2);
           KnopPosities.add(knopPositie3);
           
           pauzeAchtergrondAppearance = new minigamexrayworld.Texture2D("options_menu_bg.png");
           knop1Appearance = new minigamexrayworld.Texture2D("options_menu_startscherm.png");
           knop2Appearance = new minigamexrayworld.Texture2D("options_menu_reset.png");
           knop3Appearance = new minigamexrayworld.Texture2D("options_menu_verder.png");
           if(Greenfoot.getMouseInfo() != null)
           {
                //Deze var bevat de positie v.d. muis.
                muisPositie = new minigamexrayworld.Vector2(Greenfoot.getMouseInfo().getX(), Greenfoot.getMouseInfo().getY());
                //Afsluiten
                if(minigamexrayworld.Vector2.Distance(knopPositie1, muisPositie) <= 110.0 && Greenfoot.getMouseInfo().getButton() == 1)
                {  
                     Greenfoot.setWorld(new hoofdmenu());
                }
                //Spel resetten
                if(minigamexrayworld.Vector2.Distance(knopPositie2, muisPositie) <= 110.0 && Greenfoot.getMouseInfo().getButton() == 1)
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
                if(minigamexrayworld.Vector2.Distance(knopPositie3, muisPositie) <= 110.0 && Greenfoot.getMouseInfo().getButton() == 1)
                {
                     Pauze = false; 
                }
           }
           minigamexrayworld.DrawingContext.Draw(pauzeAchtergrondAppearance, pauzeAchtergrondPositie);
           minigamexrayworld.DrawingContext.Draw(knop1Appearance, KnopPosities.get(0));
           minigamexrayworld.DrawingContext.Draw(knop2Appearance, KnopPosities.get(1));
           minigamexrayworld.DrawingContext.Draw(knop3Appearance, KnopPosities.get(2));
        }
    }        
    
       /*
        * LOSSE METHODES
        */

        //score updaten
        public int GameLogicXrayUpdateScore()
        {
            lengtearray = balkjePositions.size();
            if ( GameLogicXrayvoortgangsPunten < 200) {
                GameLogicXrayvoortgangsPunten += 5;
            }
            else {
                
            }
            
            for (int i = 0; i < GameLogicXrayvoortgangsPunten; i++)
            {
                minigamexrayworld.Vector2 newVoortangsPositie = minigamexrayworld.Vector2.Zero;
                newVoortangsPositie = new  minigamexrayworld.Vector2(377 + 1* i, 18);
                balkjePositions.add(newVoortangsPositie);
            }
            Scorewereld.scoreXray(GameLogicXrayvoortgangsPunten);
            return GameLogicXrayvoortgangsPunten;
        }
       public int TimerBalk()
       {
            aantalTimerPos = timerPositions.size(); //groene binnenkant
            aantalTimer += 1;
            for (int i = 0; i < aantalTimer; i++)
            {
                minigamexrayworld.Vector2 newTimerPositie = minigamexrayworld.Vector2.Zero;
                newTimerPositie = new  minigamexrayworld.Vector2(357 + 1 * i, 454);
                timerPositions.add(newTimerPositie);
            }
            
            return aantalTimerPos;
        } 
        
        public void MaakLevel() {
                aantalGoedeKoffers = 20;
                aantalSlechteKoffers = 20;                
                for (int i = 0; i < 20; i ++ ) {
                   XrayKofferSlecht.add(kofferpositie1);
                }
                for (int i = 0; i < 20; i ++ ) {
                   XrayKofferGoed.add(kofferpositie1);
                }
            }  
        }
    
