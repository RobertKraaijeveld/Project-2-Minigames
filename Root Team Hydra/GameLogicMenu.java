import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
import javax.swing.*;
import java.*;

/**
 * Write a description of class GameLogicMenu here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameLogicMenu extends Actor
{
   
    
    //Deze vars zijn voor het pauzemenu.
    hoofdmenu.Texture2D menuIcoonAppearance;
    hoofdmenu.Vector2 menuIcoonPosition;
    
    hoofdmenu.Vector2 pauzeAchtergrondPositie;
    List<hoofdmenu.Vector2> KnopPosities;
    hoofdmenu.Vector2 knopPositie1;
    hoofdmenu.Vector2 knopPositie2;
    hoofdmenu.Vector2 knopPositie3;

    hoofdmenu.Texture2D pauzeAchtergrondAppearance;
    hoofdmenu.Texture2D knop1Appearance;
    hoofdmenu.Texture2D knop2Appearance;
    hoofdmenu.Texture2D knop3Appearance;
    
    hoofdmenu.Vector2 muisPositieMenu;
    
    boolean Pauze = false;
    
    //Dit zijn de lege vars voor de verschillende startknoppen
    hoofdmenu.Vector2 logoPosition;
    hoofdmenu.Texture2D logoAppearance;
    
    //loodsen
    hoofdmenu.Texture2D loodsvlagAppearance;
    hoofdmenu.Vector2 loodsvlagPosition;
    hoofdmenu.Vector2 loodsvlagscorePosition;
    List<hoofdmenu.Vector2> loodsvlagscorestreepPositions;
    hoofdmenu.Texture2D loodsvlagscorestreepAppearance;
    hoofdmenu.Texture2D loodsvlagscoreAppearance;  
    
    //xray
    hoofdmenu.Vector2 invoervlagscorePosition;
    List<hoofdmenu.Vector2> invoervlagscorestreepPositions;
    hoofdmenu.Texture2D invoervlagscorestreepAppearance;
    hoofdmenu.Texture2D invoervlagscoreAppearance;
    hoofdmenu.Texture2D invoervlagAppearance;
    hoofdmenu.Vector2 invoervlagPosition;

    //vrachtoverslaan
    hoofdmenu.Vector2 vrachtvlagPosition;
    hoofdmenu.Vector2 vrachtvlagscorePosition;
    List<hoofdmenu.Vector2> vrachtvlagscorestreepPositions;
    hoofdmenu.Texture2D vrachtvlagscorestreepAppearance;
    hoofdmenu.Texture2D vrachtvlagscoreAppearance;
    hoofdmenu.Texture2D vrachtvlagAppearance;
    
    public GameLogicMenu()
    {
        //Hier bepalen we de positie van het menuicoon.
        menuIcoonPosition = new hoofdmenu.Vector2(952-20,17);
        menuIcoonAppearance = new hoofdmenu.Texture2D("menuicoontje.png");
    }
    
    public void act() 
    {
        if(Pauze != true)
        {
            //Hier beginnen we met tekenen
            hoofdmenu.DrawingContext drawingContext = hoofdmenu.DrawingContext;
            drawingContext.BeginDraw();
          
            //Deze code tekent het menuicoontje inc logica.
            drawingContext.Draw(menuIcoonAppearance, menuIcoonPosition);
            
            if (Greenfoot.getMouseInfo() != null)
            {
                    muisPositieMenu = new hoofdmenu.Vector2(Greenfoot.getMouseInfo().getX(), Greenfoot.getMouseInfo().getY());
                    if (hoofdmenu.Vector2.Distance(muisPositieMenu, menuIcoonPosition) < 16.0 && Greenfoot.getMouseInfo().getButton() == 1) {
                        Pauze = true;
                    }
            }
                
                //Alle code voor de loodsvlag, inc. score
            loodsvlagPosition = new hoofdmenu.Vector2(455, 98);
            loodsvlagscorePosition = new hoofdmenu.Vector2(460, 37);
            loodsvlagscorestreepPositions = new ArrayList<hoofdmenu.Vector2>();
            loodsvlagscorestreepPositions.clear();
            for (int i = 0; i < Scorewereld.scoreloodsroep(); i++) {
                
                hoofdmenu.Vector2 nieuweScoreStreepPositie = hoofdmenu.Vector2.Zero;
                nieuweScoreStreepPositie = new hoofdmenu.Vector2(359 + i * 1 ,37);
                loodsvlagscorestreepPositions.add(nieuweScoreStreepPositie);
            }
            loodsvlagscorestreepAppearance = new hoofdmenu.Texture2D("start_progress_groen.png");
            loodsvlagAppearance = new hoofdmenu.Texture2D("start_loods.png");
            loodsvlagscoreAppearance = new hoofdmenu.Texture2D("start_progress_rood.png");
            
            //vrachtoverslaan code
            vrachtvlagscorePosition = new hoofdmenu.Vector2(275, 157);
            vrachtvlagPosition = new hoofdmenu.Vector2(280, 218);            
            vrachtvlagscorestreepPositions = new ArrayList<hoofdmenu.Vector2>();
            vrachtvlagscorestreepPositions.clear();
            for (int i = 0; i < Scorewereld.scorecontainerroep(); i++) {
                
                hoofdmenu.Vector2 nieuweScoreStreepPositie = hoofdmenu.Vector2.Zero;
                nieuweScoreStreepPositie = new hoofdmenu.Vector2(174 + i * 1 ,157);
                vrachtvlagscorestreepPositions.add(nieuweScoreStreepPositie);
            }
            vrachtvlagscorestreepAppearance = new hoofdmenu.Texture2D("start_progress_groen.png");
            vrachtvlagAppearance = new hoofdmenu.Texture2D("start_overslaan.png");
            vrachtvlagscoreAppearance = new hoofdmenu.Texture2D("start_progress_rood.png");
            
            //xray code
            invoervlagscorePosition = new hoofdmenu.Vector2(654, 380);
            invoervlagPosition = new hoofdmenu.Vector2(660, 440);
            invoervlagscorestreepPositions = new ArrayList<hoofdmenu.Vector2>();
            invoervlagscorestreepPositions.clear();
            for (int i = 0; i < Scorewereld.scorexrayroep(); i++) {
                
                hoofdmenu.Vector2 nieuweScoreStreepPositie = hoofdmenu.Vector2.Zero;
                nieuweScoreStreepPositie = new hoofdmenu.Vector2(553 + i * 1 ,380);
                invoervlagscorestreepPositions.add(nieuweScoreStreepPositie);
            }
            invoervlagscorestreepAppearance = new hoofdmenu.Texture2D("start_progress_groen.png");
            invoervlagAppearance = new hoofdmenu.Texture2D("start_invoer.png");
            invoervlagscoreAppearance = new hoofdmenu.Texture2D("start_progress_rood.png");

            //De positie en texture voor het port of rotterdam logo
            logoPosition = new hoofdmenu.Vector2(76, 52);
            logoAppearance = new hoofdmenu.Texture2D("POR-logo.png");
            
            //tekene van alle items voor het hoofdmenu
            hoofdmenu.DrawingContext.Draw(logoAppearance, logoPosition);
            hoofdmenu.DrawingContext.Draw(loodsvlagAppearance, loodsvlagPosition);
            hoofdmenu.DrawingContext.Draw(vrachtvlagAppearance, vrachtvlagPosition);
            hoofdmenu.DrawingContext.Draw(invoervlagAppearance, invoervlagPosition);
            hoofdmenu.DrawingContext.Draw(loodsvlagscoreAppearance, loodsvlagscorePosition);
            
            for (hoofdmenu.Vector2 a : loodsvlagscorestreepPositions) {
                drawingContext.Draw(loodsvlagscorestreepAppearance, a);            
            }
            hoofdmenu.DrawingContext.Draw(vrachtvlagscoreAppearance, vrachtvlagscorePosition);
            for (hoofdmenu.Vector2 a : vrachtvlagscorestreepPositions) {
                drawingContext.Draw(vrachtvlagscorestreepAppearance, a);
            }
            hoofdmenu.DrawingContext.Draw(invoervlagscoreAppearance, invoervlagscorePosition);
            for (hoofdmenu.Vector2 a : invoervlagscorestreepPositions) {
                drawingContext.Draw(invoervlagscorestreepAppearance, a);
            }
            
            //toevoegen van de startknoppen
            getWorld().addObject(new startknop_xray(), 654, 458);
            getWorld().addObject(new Startknop_loods(), 460, 116);
            getWorld().addObject(new Startknop_vracht(), 274, 236);
        }
        /*
         * PAUZE-MENU CODE
         */
        
        //Als de menuknop is aangeklikt, stoppen we de timer en tekenen we het menu, inc. functies.
        if (Pauze == true) {
           Clock.stopClock();
           pauzeAchtergrondPositie = new hoofdmenu.Vector2(475, 275);
           KnopPosities = new ArrayList<hoofdmenu.Vector2>();
           knopPositie1 = new hoofdmenu.Vector2(450,150);
           knopPositie2 = new hoofdmenu.Vector2(450, 300);
           knopPositie3 = new hoofdmenu.Vector2(450, 450);
           KnopPosities.add(knopPositie1);
           KnopPosities.add(knopPositie2);
           KnopPosities.add(knopPositie3);
           
           pauzeAchtergrondAppearance = new hoofdmenu.Texture2D("options_menu_bg.png");
           knop1Appearance = new hoofdmenu.Texture2D("options_menu_startscherm.png");
           knop2Appearance = new hoofdmenu.Texture2D("options_menu_reset.png");
           knop3Appearance = new hoofdmenu.Texture2D("options_menu_verder.png");
           if(Greenfoot.getMouseInfo() != null)
           {
                //Deze var bevat de positie v.d. muis.
                muisPositieMenu = new hoofdmenu.Vector2(Greenfoot.getMouseInfo().getX(), Greenfoot.getMouseInfo().getY());
                //Afsluiten
                if(hoofdmenu.Vector2.Distance(knopPositie1, muisPositieMenu) <= 110.0 && Greenfoot.getMouseInfo().getButton() == 1)
                {  
                     Greenfoot.setWorld(new hoofdmenu());
                }
                //Spel resetten
                if(hoofdmenu.Vector2.Distance(knopPositie2, muisPositieMenu) <= 110.0 && Greenfoot.getMouseInfo().getButton() == 1)
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
                if(hoofdmenu.Vector2.Distance(knopPositie3, muisPositieMenu) <= 110.0 && Greenfoot.getMouseInfo().getButton() == 1)
                {
                     Pauze = false;
                }
           }
           hoofdmenu.DrawingContext.Draw(pauzeAchtergrondAppearance, pauzeAchtergrondPositie);
           hoofdmenu.DrawingContext.Draw(knop1Appearance, KnopPosities.get(0));
           hoofdmenu.DrawingContext.Draw(knop2Appearance, KnopPosities.get(1));
           hoofdmenu.DrawingContext.Draw(knop3Appearance, KnopPosities.get(2));
        }
    }    
    
}
