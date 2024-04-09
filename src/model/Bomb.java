
package model;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.swing.Timer;

public class Bomb extends ActiveObject {
    /*
        
        x, y
    */
    public boolean hasExploded;
    public Date droppedTime;
    public Ground droppedGround;// if null then bomb is not dropped

    private int explosionRadius;
    private char[] fourdirections;

    public Bomb(int x, int y, int widt, int height, Image image) {
        
        super(x, y, widt, height, image);
        hasExploded = false;
        explosionRadius = 20;
    }
    
    
    
    public boolean isExploded(){
        
        if(droppedTime != null){
            Date currentTime = new Date();
            System.out.println(droppedTime);

            }

        return false;
    }
    
    public void explode(ArrayList<Box> boxes, ArrayList<Player> players, ArrayList<Monster> monsters){
        fourdirections = new char[]{'u', 'd', 'l', 'r'};
        
    }
        
    

}
    

