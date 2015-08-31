import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
import javax.swing.*;
import java.*;

/**
 * ----------------------
 * EPIC 1: SCHEPEN LOODSEN
 * ------------------------
 * De speler ziet een grote monding van een haven.
 * Van de rechterkant van het beeld komen er om de 4 seconden schepen binnenvaren,
 * die uitzichzelf langzaam bewegen.
 * De speler kan met de linkermuisknop een schip tegelijk selecteren, en dit geselecteerde schip
 * vervolgens besturen met de WASD knoppen.
 * De speler moet de schepen naar de dokken leiden, die aangegeven zijn door grijze stukken aan de kade.
 * Als boten op elkaar of op het land botsen is het game over.
 * 
 * ---------------
 * Controls
 * ---------------
 * LMB: Boot selecteren.
 * RMB: Geselecteerde boot de-selecteren.
 * W: Geselecteerde boot naar boven besturen.
 * S: Geselecteerde boot naar onder besturen.
 * A: Geselecteerde boot naar rechts besturen.
 * D: Geselecteerde boot naar links besturen.
 * 
 * ---------------
 * Technische opzet:
 * ----------------
 * Bovenin de code worden de variabelen en methodes gedeclareerd.
 * De Act() methode bevat de actiecode.
 * Onderin staat alle teken-code, plus enkele losse methodes.
 * 
 * Wij hebben gebruik gemaakt van de Bibliotheek van Dr. Maggiore.
 * Deze library maakt gebruik van Vectors en Textures: Een vector is een coordinaat in de spelwereld,
 * een texture is zoals de naam het zegt een afbeelding, die op de positie van een Vector getekend kan worden.
 * De Act() code specificeert als het ware de condities waaraan de teken-code zich moet houden.
 * 
 * Voor de logica van het spel is deels gebruik gemaakt van wat wij de 0, 1, 2 methode noemen.
 * De 0, 1 en 2 waarden van een bepaalde variabele bepalen dan de status van een bepaalde entiteit.
 * Voor het grootste gedeelte zijn de verschillende functies van het spel echter relatief gescheiden
 * van elkaar uitgevoerd, zoals het spawnen en het selecteren v.d. schepen etc.
 * 
 * Ook hebben wij zoveel mogelijk eigenschappen van de spelwereld in arrays gestopt.
 * 
 * Zie voor uitgebreidere informatie de comments per codeblok.
 * 
 * @author Team Hydra
 * @version final
 */
public class GameLogicLoods extends Actor
{
    //Deze vars zijn voor het pauzemenu.
    minigameloodsworld.Texture2D menuIcoonAppearance;
    minigameloodsworld.Vector2 menuIcoonPosition;
    
    minigameloodsworld.Vector2 pauzeAchtergrondPositie;
    List<minigameloodsworld.Vector2> KnopPosities;
    minigameloodsworld.Vector2 knopPositie1;
    minigameloodsworld.Vector2 knopPositie2;
    minigameloodsworld.Vector2 knopPositie3;

    minigameloodsworld.Texture2D pauzeAchtergrondAppearance;
    minigameloodsworld.Texture2D knop1Appearance;
    minigameloodsworld.Texture2D knop2Appearance;
    minigameloodsworld.Texture2D knop3Appearance;
    
    minigameloodsworld.Vector2 muisPositieMenu;
    
    boolean Pauze = false;

    //Deze vars gaan de score bevatten.         
    minigameloodsworld.Vector2 voortgangsbalkPosition;
    List<minigameloodsworld.Vector2> sterPositions;
    minigameloodsworld.Vector2 sterpositie1;
    minigameloodsworld.Vector2 sterpositie2;
    minigameloodsworld.Vector2 sterpositie3;
    minigameloodsworld.Vector2 sterpositie4;
    minigameloodsworld.Vector2 sterpositie5;
    minigameloodsworld.Vector2 sterpositie6;
       
    minigameloodsworld.Vector2 goalsPosition;
    
    List<minigameloodsworld.Vector2> balkjePositions;
   
    //Deze vars gaan voor het docken gebruikt worden.
    List<minigameloodsworld.Vector2> dockPositions;
    minigameloodsworld.Texture2D dockAppearance;
    boolean isDocked;
    int docknummer;
    
    int statusBalkPunten;
    int statusBalkLengteArray;
    List<minigameloodsworld.Vector2> statusBalkPositions;  //dit is de progress balk, het groene
    minigameloodsworld.Texture2D statusVoortgangsbalkstreepAppearance;
    minigameloodsworld.Texture2D statusVoortgangsbalkAppearance;
    List<minigameloodsworld.Vector2> statusVoortgangsbalkPositions; //dit is de gehele meter
    
    //Deze vars gaan voor de collision tussen de boten en de kust zorgen.
    List<minigameloodsworld.Vector2> kustPositions;
    
    //Deze vars gaan voor het spawnen v.d. boten gebruikt worden.
    float RespawnTijdBoot;
    boolean selected;
    int selectedNummer;
    List<minigameloodsworld.Vector2> bootPositions;
    //tijdelijk
    List<minigameloodsworld.Vector2> bootPositionsSelected;
    minigameloodsworld.Texture2D bootAppearanceSelected;
    // </tijdelijk>
    minigameloodsworld.Texture2D bootAppearance;
    
    //Deze vars bevatten straks de textures v.d. verschillende objecten.
    minigameloodsworld.Texture2D voortgangsbalkstreepAppearance;
    minigameloodsworld.Texture2D voortgangsbalkAppearance;
    minigameloodsworld.Texture2D sterAppearance;
    minigameloodsworld.Texture2D sterLeegAppearance;
    minigameloodsworld.Texture2D goalsAppearance;

    minigameloodsworld.Vector2 indicatiebalkPosition;
    minigameloodsworld.Texture2D indicatiebalkAppearance;
   
    Random randomGenerator;
     
    int lengtearray;
    int bootnummer;
   
    //Deze vars zijn voor het algemeen bijhouden vd. score.
    int GameLogicLoodsvoortgangsPunten = 0;
    
    //Deze var gaat de positie vd. muis bevatten.
    minigameloodsworld.Vector2 muisPositie;
    
    
    public GameLogicLoods() 
    {
        //Eerst geven we een message weer, die de bedoeling van het spel aan de gebruiker uitlegd.\
        JOptionPane.showMessageDialog(new JInternalFrame(), "Loods de boten naar de droogdokken! \nSelecteer een boot met de linkermuisknop, deselecteer met de rechtermuisknop \nJe kan de boot bewegen met de pijltjestoetsen of WASD, \nmaar pas op dat je de kade niet raakt!", "Spelregels", JOptionPane.INFORMATION_MESSAGE);
        //hieronder alle vars voor de score(balk) die nu een waarde krijgen.
        indicatiebalkPosition = new minigameloodsworld.Vector2(10, 300);
        voortgangsbalkPosition = new minigameloodsworld.Vector2(476, 18);
        sterPositions = new ArrayList<minigameloodsworld.Vector2>();
                
        sterpositie1 = new minigameloodsworld.Vector2(476, 38);
        sterpositie2 = new minigameloodsworld.Vector2(520, 38);
        sterpositie3 = new minigameloodsworld.Vector2(530, 38);
        sterpositie4 = new minigameloodsworld.Vector2(565, 38);
        sterpositie5 = new minigameloodsworld.Vector2(575, 38);
        sterpositie6 = new minigameloodsworld.Vector2(585, 38);
                
        sterPositions.add(sterpositie1);
        sterPositions.add(sterpositie2);
        sterPositions.add(sterpositie3);
        sterPositions.add(sterpositie4);
        sterPositions.add(sterpositie5);
        sterPositions.add(sterpositie6);
               
        goalsPosition = new minigameloodsworld.Vector2(527, 12);
        
        //en hieronder alle textures.
        voortgangsbalkstreepAppearance = new minigameloodsworld.Texture2D("loods_progress_fill.png");
        voortgangsbalkAppearance = new minigameloodsworld.Texture2D("loods_balk_bg.png");
        balkjePositions = new ArrayList<minigameloodsworld.Vector2>();
        sterAppearance = new minigameloodsworld.Texture2D("loods_ster_active.png");
        sterLeegAppearance = new minigameloodsworld.Texture2D("loods_ster_inactive.png");
        goalsAppearance = new minigameloodsworld.Texture2D("loods_goals.png");
       
        //Het object dat we gaan gebruiken voor het random spawnen van de boten.
        randomGenerator = new Random();
        //Hier vullen we de dockPosition array met waardes.
        dockPositions = new ArrayList<minigameloodsworld.Vector2>();
        dockPositions.add(new minigameloodsworld.Vector2(330, 220));
        dockPositions.add(new minigameloodsworld.Vector2(450, 450));
        dockAppearance = new minigameloodsworld.Texture2D("uitroepteken.png");
        
        
        //Dit is voor de collision met de kust.
        kustPositions = new ArrayList<minigameloodsworld.Vector2>();
        kustPositions.add(new minigameloodsworld.Vector2(800, 280));
        kustPositions.add(new minigameloodsworld.Vector2(700, 220));
        kustPositions.add(new minigameloodsworld.Vector2(650, 200));
        kustPositions.add(new minigameloodsworld.Vector2(650, 300));
        kustPositions.add(new minigameloodsworld.Vector2(400, 230));
        kustPositions.add(new minigameloodsworld.Vector2(50, 300));
        kustPositions.add(new minigameloodsworld.Vector2(250, 50));
        
        //Dit is voor de dockingprogress meter
        int statusBalkPunten = 0;
        int statusBalkLengteArray;
        statusBalkPositions = new ArrayList<minigameloodsworld.Vector2>();  //dit is de progress balk, het groene
        statusVoortgangsbalkstreepAppearance = new minigameloodsworld.Texture2D("loods_progress_fill.png");
        statusVoortgangsbalkAppearance = new minigameloodsworld.Texture2D("loods_balk_bg.png");
        statusVoortgangsbalkPositions = new ArrayList<minigameloodsworld.Vector2>(); //dit is de gehele meter  
       
        //Deze vars worden gebruikt bij het spawnen v.d. boten, en krijgen nu een waarde.
        RespawnTijdBoot = 0.0f;
        bootAppearance = new minigameloodsworld.Texture2D("schip_default.png");
        bootAppearanceSelected = new minigameloodsworld.Texture2D("schip_selected.png");
        bootPositions = new ArrayList<minigameloodsworld.Vector2>();
        bootPositions.add(new minigameloodsworld.Vector2(951,200)); 
        bootPositionsSelected = new ArrayList<minigameloodsworld.Vector2>();
        
        //Deze vars zijn voor de laadbalk, en krijgen nu een waarde.
        indicatiebalkAppearance = new minigameloodsworld.Texture2D("indicatiebalk.png");
        
        //Hier bepalen we de positie van het menuicoon.
        menuIcoonPosition = new minigameloodsworld.Vector2(952-20,17);
        menuIcoonAppearance = new minigameloodsworld.Texture2D("menuicoontje.png");
       
    }
    /**
     * Act - do whatever the GameLogicLoods wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(Pauze != true)
        {    // Add your action code here.
            float dt = 1.0f / 60.0f;
           
            /*
             * SPAWN-CODE  
             *
             */
           
            //Deze code spawnt de boten, en let daarbij op dat geen 2 boten elkaar raken.
            //De tijd tussen het spawnen wordt random bepaald.
            if (RespawnTijdBoot >= (randomGenerator.nextDouble() * 1.5 + 4 )) {
                //daarna wordt gekeken of er 5 boten of minder gespawned zijn.
                if (bootPositions.size() < 5) {
                    minigameloodsworld.Vector2 nieuweBootPositie = minigameloodsworld.Vector2.Zero;
                    boolean botsing;
                    //Zo ja, dan worden er nieuwe boten gespawnd op een random positie.
                    do {
                        nieuweBootPositie = new minigameloodsworld.Vector2(951, randomGenerator.nextInt(283)); 
                        botsing = false;
                        for (minigameloodsworld.Vector2 a : bootPositions)
                        {
                            //Als de plaats waar een nieuwe boot gespawned gaat worden te dichtbij een oude bootpositie is, is botsing true.
                            if (minigameloodsworld.Vector2.Distance(a, nieuweBootPositie) <= 50.0) {
                                botsing = true;
                                break;
                            }
                        }
                        //Als botsing true is, beginnen we overnieuw, net zolang tot er geen botsing meer is.
                    } while (botsing);
                    bootPositions.add(nieuweBootPositie);
                }             
                RespawnTijdBoot = 0.0f;   
            }
            RespawnTijdBoot += dt;
            
            /*
             * BEWEGINGS-CODE
             */
            
            //deze code laat de boten NA het spawnen (langzaam) uit zichzelf bewegen naar links.
             
                for (int i = 0; i < bootPositions.size(); i++) {
                    bootPositions.set(i, bootPositions.get(i).sub(minigameloodsworld.Vector2.UnitX.mul(dt).mul(10.0f)));
                }        
                //Hetzelfde geldt voor de geselecteerde boten.
                //Tevens voegen we hier de code toe om de boten te laten reageren op de WASD toetsen.
                for (int j = 0; j < bootPositionsSelected.size(); j++) {
                    bootPositionsSelected.set(j, bootPositionsSelected.get(j).sub(minigameloodsworld.Vector2.UnitX.mul(dt).mul(10.0f)));
                    bootAppearanceSelected = new minigameloodsworld.Texture2D("schip_selected.png");
                    if(Greenfoot.isKeyDown("D") || Greenfoot.isKeyDown("right"))
                    {
                        bootPositionsSelected.set(j, bootPositionsSelected.get(j).add(minigameloodsworld.Vector2.UnitX.mul(dt).mul(60.0f)));
                        bootAppearanceSelected = new minigameloodsworld.Texture2D("schip_selected_rechts.png");
                    }
                    if(Greenfoot.isKeyDown("A") || Greenfoot.isKeyDown("left") )
                    {
                        bootAppearanceSelected = new minigameloodsworld.Texture2D("schip_selected.png");
                        bootPositionsSelected.set(j, bootPositionsSelected.get(j).sub(minigameloodsworld.Vector2.UnitX.mul(dt).mul(60.0f)));
                    }
                    if (Greenfoot.isKeyDown("W") || Greenfoot.isKeyDown("up"))
                    {
                        bootAppearanceSelected = new minigameloodsworld.Texture2D("schip_selected_boven.png");
                        bootPositionsSelected.set(j, bootPositionsSelected.get(j).sub(minigameloodsworld.Vector2.UnitY.mul(dt).mul(60.0f)));
                    }
                    if (Greenfoot.isKeyDown("S") || Greenfoot.isKeyDown("down"))
                    {
                        bootAppearanceSelected = new minigameloodsworld.Texture2D("schip_selected_onder.png");
                         bootPositionsSelected.set(j, bootPositionsSelected.get(j).add(minigameloodsworld.Vector2.UnitY.mul(dt).mul(60.0f)));
                    }
                    //Code voor de schuine posities
                    //Boven
                    if((Greenfoot.isKeyDown("A") && Greenfoot.isKeyDown("W")) || (Greenfoot.isKeyDown("left") && Greenfoot.isKeyDown("up")))
                    {
                       bootAppearanceSelected = new minigameloodsworld.Texture2D("schip_selected_linksboven.png"); 
                    }
                    if((Greenfoot.isKeyDown("D") && Greenfoot.isKeyDown("W")) || (Greenfoot.isKeyDown("right") && Greenfoot.isKeyDown("up")))
                    {
                       bootAppearanceSelected = new minigameloodsworld.Texture2D("schip_selected_rechtsboven.png"); 
                    }
                    //Onder
                    if((Greenfoot.isKeyDown("A")  && Greenfoot.isKeyDown("S")) || (Greenfoot.isKeyDown("left") && Greenfoot.isKeyDown("down")))
                    {
                       bootAppearanceSelected = new minigameloodsworld.Texture2D("schip_selected_linksonder.png"); 
                    }
                    if((Greenfoot.isKeyDown("D") && Greenfoot.isKeyDown("S")) || (Greenfoot.isKeyDown("right") && Greenfoot.isKeyDown("down")))
                    {
                       bootAppearanceSelected = new minigameloodsworld.Texture2D("schip_selected_rechtsonder.png"); 
                    }
                }
                
                                
            /*
             * SELECTEER-CODE
             */
           
            //Eerst kijken we of de muis uberhaupt gebruikt is, zo ja:
            if(Greenfoot.getMouseInfo() != null)
            {
                //Maken we deze deze var. Deze var bevat de positie v.d. muis.
                muisPositie = new minigameloodsworld.Vector2(Greenfoot.getMouseInfo().getX(), Greenfoot.getMouseInfo().getY());
                //Als de positie van de muis gelijk is aan die van een boot en er geklikt wordt, is "selected" 1.
                if (selected == false) {
                    for(int i = 0; i < bootPositions.size(); i++)
                    {
                        //Als er met de rechtermuisknop op het schip geklikt wordt, wordt het schip selecteerd.
                        if(minigameloodsworld.Vector2.Distance(muisPositie, bootPositions.get(i)) <=20.0 && Greenfoot.getMouseInfo().getButton() == 1 && selectedNummer != 1)
                        {
                           selected = true;
                           //Als de boot geklikt is, is selected true en is dus de besturing op die boot actief.
                           selectedNummer = 1;
                           //Er mogen niet meer dan 1 boten geselecteerd zijn, selectedNummer houdt dit bij.
                        }
                        else 
                        {
                           selected = false;
                        }                
                        if(selected == true)
                        {
                           //Als een boot geselecteerd is wordt deze aan de selectedarray toegevoegd, waardoor zijn eigenschappen veranderen/
                           bootPositionsSelected.add(bootPositions.get(i));
                           bootPositions.remove(i);
                        }
                    }
                }
                //als ergens in beeld geklikt wordt (rmb), wordt de geselecteerde boot verwijderd.
                if (selected == true) {
                    if(Greenfoot.getMouseInfo().getButton() == 3 && selectedNummer == 1)
                    {
                       selectedNummer = 0;
                       selected = false;
                       //De geselecteerde boot wordt weer normaal
                       bootPositions.add(bootPositionsSelected.get(0));
                       bootPositionsSelected.clear(); 
                    }
                }
            } 
                
    
           /*
            * DOCK-CODE
            */
            
           //Als er schepen dicht in de buurt van een dockingpoint zijn, moeten ze stoppen, een timer laten beginnen en als deze afgelopen is verdwijnen en de score 
           //updaten       
               
               for(int i = 0; i < dockPositions.size(); i++)
               {
                   //DOCKING CODE voor geselecteerde boten
                  for(int j = 0; j < bootPositionsSelected.size(); j++)
                  {
                    if(minigameloodsworld.Vector2.Distance(dockPositions.get(i), bootPositionsSelected.get(j)) <=80.0)
                          {
                            //het schip is in de haven, dus isDocked wordt true
                            isDocked = true;                       
                            bootnummer = j;
                            docknummer = i;
                            //Hier doen we dat TERWIJL isDock true is, dus terwijl er een schip is, de laadbalk wordt gemaakt en geincreased 
                            //a.d. hand van het aantal frames: no need for timers:)
                            if(isDocked == true)
                            {
                                statusVoortgangsbalkPositions.add(dockPositions.get(i));
                                DockingBalkUpdate();             
                                //we vullen de statusbalk                      
                            }    
                            //Als het aantal statuspunten groter is dan 200 is de balk vol. Dan gaat de balk weg en wordt het aantal punten gereset.
                            //Tevens wordt de score bijgewerkt, isDocked op false gezet en last but not least het schip weggehaald.
                            if(statusBalkPunten >= 200)
                            {
                                selected = false;
                                selectedNummer = 0;
                                statusBalkPunten = 0;
                                //statusbalk weghalen
                                GameLogicLoodsUpdateScore();
                                //gamescore verhogen
                                bootPositionsSelected.remove(bootnummer);
                                //schip verwijderen
                                isDocked = false;
                                //isdocked op false zetten, zodat het schip niet 2x gedockt wordt
                                statusBalkPositions.clear();
                                statusVoortgangsbalkPositions.clear();
                                //we halen de status balk weg                            
                            }
                          }
                    }
                    //DOCKING CODE voor ongeselecteerde boten
                  for(int x = 0; x < bootPositions.size(); x++)
                      {
                          if(minigameloodsworld.Vector2.Distance(dockPositions.get(i), bootPositions.get(x)) <=80.0)
                          {
                            //het schip is in de haven, dus isDocked wordt true
                            isDocked = true;                        
                            bootnummer = x;
                            docknummer = i;
                            //Hier doen we dat TERWIJL isDock true is, dus terwijl er een schip is, de laadbalk wordt gemaakt en geincreased 
                            //a.d. hand van het aantal frames: no need for timers:)
                            if(isDocked == true)
                            {
                                statusVoortgangsbalkPositions.add(dockPositions.get(i));
                                DockingBalkUpdate();             
                                //we vullen de statusbalk
                            }    
                            //Als het aantal statuspunten groter is dan 200 is de balk vol. Dan gaat de balk weg en wordt het aantal punten gereset.
                            //Tevens wordt de score bijgewerkt, isDocked op false gezet en last but not least het schip weggehaald.
                            if(statusBalkPunten >= 200)
                            {
                                statusBalkPunten = 0;
                                statusVoortgangsbalkPositions.remove(dockPositions.get(i));
                                //statusbalk weghalen
                                GameLogicLoodsUpdateScore();
                                //gamescore verhogen
                                bootPositions.remove(bootnummer);
                                //schip verwijderen
                                isDocked = false;
                                //isdocked op false zetten, zodat het schip niet 2x gedockt wordt
                                statusBalkPositions.clear();
                                statusVoortgangsbalkPositions.clear();
                                //we halen de status balk weg
                            }
                          }
                      }              
               }
                 
           /*
            * COLLISION-CODE
            */
           
           for(int i = 0; i < bootPositions.size(); i++)
           {
             //hier checken we de kust tegen de ongeselecteerde boten
             for(int k = 0; k < kustPositions.size(); k++)
             {
                 if(minigameloodsworld.Vector2.Distance(bootPositions.get(i), kustPositions.get(k)) < 50.0)
                 {
                     //als geselecteerde boten met ongeselecteerde boten collision hebben
                     JOptionPane.showMessageDialog(new JInternalFrame(), "Helaas, één van de schepen is tegen de kust aan gevaren.","Game over!", JOptionPane.INFORMATION_MESSAGE);
                     Greenfoot.setWorld(new hoofdmenu());
                 }
             }
            }
           
            //hieronder de checks voor alles wat met de geselecteerde boten te maken heeft.
          for(int j = 0; j < bootPositionsSelected.size(); j++)
          {
             //collision tussen de 2 types
              for(int x = 0; x < bootPositions.size(); x++)
              {
               if(minigameloodsworld.Vector2.Distance(bootPositionsSelected.get(j), bootPositions.get(x)) < 50.0)
               {
                   //als geselecteerde boten met ongeselecteerde boten collision hebben
                   JOptionPane.showMessageDialog(new JInternalFrame(), "Helaas, één van de schepen is tegen een ander schip aan gevaren.","Game over!", JOptionPane.INFORMATION_MESSAGE);
                   Greenfoot.setWorld(new hoofdmenu());
               }
             }
             //Collision met kust en geselecteerd
             for(int k = 0; k < kustPositions.size(); k++)
             {
                   if(minigameloodsworld.Vector2.Distance(bootPositionsSelected.get(j), kustPositions.get(k)) < 60.0)
                   {
                       //als geselecteerde boten met ongeselecteerde boten collision hebben
                       JOptionPane.showMessageDialog(new JInternalFrame(), "Helaas, één van de schepen is tegen de kust aan gevaren.","Game over!", JOptionPane.INFORMATION_MESSAGE);
                       Greenfoot.setWorld(new hoofdmenu());
                   }
                }
          }
           
        
            /*
             * DRAW-CODE, SCORE-CODE
             */
    
            //de onderstaande score tekent de textures bij de desbetreffende vectors.
            minigameloodsworld.DrawingContext drawingContext = minigameloodsworld.DrawingContext;
            drawingContext.BeginDraw();
            
            //Hier alle tekencode van het scoresysteem.
            minigameloodsworld.DrawingContext.Draw(voortgangsbalkAppearance, voortgangsbalkPosition);
            
            if (GameLogicLoodsvoortgangsPunten >= 100) {
               minigameloodsworld.DrawingContext.Draw(sterAppearance, sterPositions.get(1-1));  
            }
            else if (GameLogicLoodsvoortgangsPunten < 100){
                minigameloodsworld.DrawingContext.Draw(sterLeegAppearance, sterPositions.get(1-1));            
            }
            if (GameLogicLoodsvoortgangsPunten >= 150) {
               minigameloodsworld.DrawingContext.Draw(sterAppearance, sterPositions.get(2-1));  
               minigameloodsworld.DrawingContext.Draw(sterAppearance, sterPositions.get(3-1)); 
            }
            else if (GameLogicLoodsvoortgangsPunten < 150){
                minigameloodsworld.DrawingContext.Draw(sterLeegAppearance, sterPositions.get(2-1));
                minigameloodsworld.DrawingContext.Draw(sterLeegAppearance, sterPositions.get(3-1));
            }
            if (GameLogicLoodsvoortgangsPunten >= 200) {
               minigameloodsworld.DrawingContext.Draw(sterAppearance, sterPositions.get(4-1));
               minigameloodsworld.DrawingContext.Draw(sterAppearance, sterPositions.get(5-1)); 
               minigameloodsworld.DrawingContext.Draw(sterAppearance, sterPositions.get(6-1)); 
               JOptionPane.showMessageDialog(new JInternalFrame(), "Je hebt gewonnen! Klik op OK om terug naar het hoofdmenu te gaan.","Gefeliciteerd!", JOptionPane.INFORMATION_MESSAGE);
               Greenfoot.setWorld(new hoofdmenu());
            }
            else if (GameLogicLoodsvoortgangsPunten < 200){
                minigameloodsworld.DrawingContext.Draw(sterLeegAppearance, sterPositions.get(4-1));
                minigameloodsworld.DrawingContext.Draw(sterLeegAppearance, sterPositions.get(5-1));
                minigameloodsworld.DrawingContext.Draw(sterLeegAppearance, sterPositions.get(6-1));
            }
            
            for (minigameloodsworld.Vector2 a : dockPositions) {
                drawingContext.Draw(dockAppearance, a);
            }
            
            //Deze 2 loops tekenen de score/statusbalken, het binnenste gedeelte vd meter.
            //Score
            for (int i = lengtearray; i < balkjePositions.size();  i++) {            
                drawingContext.Draw(voortgangsbalkstreepAppearance, balkjePositions.get(i)); 
            }
            //Status(buiten)
            for (minigameloodsworld.Vector2 a : statusVoortgangsbalkPositions)
            {
                drawingContext.Draw(statusVoortgangsbalkAppearance, a);
            }
            //Status(binnen)
             for (int i = statusBalkLengteArray; i < statusBalkPositions.size();  i++) {            
                drawingContext.Draw(statusVoortgangsbalkstreepAppearance, statusBalkPositions.get(i)); 
            }
            
            minigameloodsworld.DrawingContext.Draw(goalsAppearance, goalsPosition);
            
             for (minigameloodsworld.Vector2 a : bootPositionsSelected) {
                drawingContext.Draw(bootAppearanceSelected, a);        
            }
            
            //Deze code tekent de boten.
            for (minigameloodsworld.Vector2 a : bootPositions) {
                drawingContext.Draw(bootAppearance, a);        
            }
             
            //Deze code tekent het menuicoontje.
            drawingContext.Draw(menuIcoonAppearance, menuIcoonPosition);
            
            if (Greenfoot.getMouseInfo() != null)
            {
                    muisPositieMenu = new minigameloodsworld.Vector2(Greenfoot.getMouseInfo().getX(), Greenfoot.getMouseInfo().getY());
                    if (minigameloodsworld.Vector2.Distance(muisPositieMenu, menuIcoonPosition) < 16.0 && Greenfoot.getMouseInfo().getButton() == 1) {
                        Pauze = true;
                    }
                }
        }
        
        /*
        * PAUZE-MENU CODE
        */
       
        //Als de menuknop is getekend, stoppen we de timer en tekenen we het menu, inc. functies.
        if(Pauze == true)
        {
           //de boten gaan nu nog door (?)
           Clock.stopClock();
           pauzeAchtergrondPositie = new minigameloodsworld.Vector2(475, 275);
           KnopPosities = new ArrayList<minigameloodsworld.Vector2>();
           knopPositie1 = new minigameloodsworld.Vector2(450,150);
           knopPositie2 = new minigameloodsworld.Vector2(450, 300);
           knopPositie3 = new minigameloodsworld.Vector2(450, 450);
           KnopPosities.add(knopPositie1);
           KnopPosities.add(knopPositie2);
           KnopPosities.add(knopPositie3);
           
           pauzeAchtergrondAppearance = new minigameloodsworld.Texture2D("options_menu_bg.png");
           knop1Appearance = new minigameloodsworld.Texture2D("options_menu_startscherm.png");
           knop2Appearance = new minigameloodsworld.Texture2D("options_menu_reset.png");
           knop3Appearance = new minigameloodsworld.Texture2D("options_menu_verder.png");       
           if(Greenfoot.getMouseInfo() != null)
            {
                //Deze var bevat de positie v.d. muis.
                muisPositieMenu = new minigameloodsworld.Vector2(Greenfoot.getMouseInfo().getX(), Greenfoot.getMouseInfo().getY());
                //Afsluiten
                if(minigameloodsworld.Vector2.Distance(knopPositie1, muisPositieMenu) <= 110.0 && Greenfoot.getMouseInfo().getButton() == 1)
                {                
                    Greenfoot.setWorld(new hoofdmenu());
                }
                //Reset van game
                if(minigameloodsworld.Vector2.Distance(knopPositie2, muisPositieMenu) <= 110.0 && Greenfoot.getMouseInfo().getButton() == 1)
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
                //Terug naar het spel
                if(minigameloodsworld.Vector2.Distance(knopPositie3, muisPositieMenu) <= 110.0 && Greenfoot.getMouseInfo().getButton() == 1)
                {
                    Pauze = false;                 
                }
           }
           minigameloodsworld.DrawingContext.Draw(pauzeAchtergrondAppearance, pauzeAchtergrondPositie);
           minigameloodsworld.DrawingContext.Draw(knop1Appearance, KnopPosities.get(0));
           minigameloodsworld.DrawingContext.Draw(knop2Appearance, KnopPosities.get(1));
           minigameloodsworld.DrawingContext.Draw(knop3Appearance, KnopPosities.get(2));
        }
    }
    
    /*
    * LOSSE METHODES
    * 
    */
    
    //Deze code bepaalt de dock-score.
    
    public int DockingBalkUpdate()
    {
        statusBalkLengteArray= statusBalkPositions.size(); //groene binnenkant
        statusBalkPunten+= 1;        
        for (int i = 0; i < statusBalkPunten; i++)
        {
            minigameloodsworld.Vector2 newVoortangsPositie = minigameloodsworld.Vector2.Zero;            
            newVoortangsPositie = new  minigameloodsworld.Vector2(dockPositions.get(docknummer).X - 100 + 1 * i, dockPositions.get(docknummer).Y);            
            statusBalkPositions.add(newVoortangsPositie);
        }        
        return statusBalkPunten;
    }
    //Deze code handelt het daadwerkelijk veranderen van de gamescore af.
    public int GameLogicLoodsUpdateScore()
    {
        statusBalkLengteArray = balkjePositions.size();
        GameLogicLoodsvoortgangsPunten += 20;
        for (int i = 0; i < GameLogicLoodsvoortgangsPunten; i++)
        {
            minigameloodsworld.Vector2 newVoortangsPositie = minigameloodsworld.Vector2.Zero;
            newVoortangsPositie = new  minigameloodsworld.Vector2(377 + 1* i, 18);
            balkjePositions.add(newVoortangsPositie);
        }
        Scorewereld.scoreLoods(GameLogicLoodsvoortgangsPunten);
        return GameLogicLoodsvoortgangsPunten;
    }
    
}
