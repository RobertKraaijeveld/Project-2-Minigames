import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
import javax.swing.*;
import java.*;

/**
 * Write a description of class startknop_xray here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class startknop_xray extends Actor
{
   

    /**
     * Act - do whatever the startknop_xray wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
   
    public void act() 
    {
                 /// als er op de knop geklikt wordt start de desbetreffende minigame
       if (Greenfoot.mouseClicked(this)) {
           Greenfoot.setWorld(new minigamexrayworld());
        }
    }
    }

