
package model;

import java.awt.Image;
import java.util.Date;
import javax.swing.Timer;

public class Bomb extends ActiveObject {
    /*
        
        x, y
    */
    public boolean hasExploded;
    public Date droppedTime;
    public Bomb(int x, int y, int widt, int height, Image image) {
        
        super(x, y, widt, height, image);
        hasExploded = false;
    }
    
    
    
    public boolean isExploded(){
        
        if(droppedTime != null){
            Date currentTime = new Date();
            System.out.println(droppedTime);
            long elapsedTime = currentTime.getTime() - droppedTime.getTime();

            // If more than 3 seconds have passed, the bomb explodes
            if (elapsedTime >= 3000) {
                explode();
                hasExploded = true;
                return true;
            }
                return false;
            }
        // Calculate the current time and the elapsed time since creation
        return false;
    }
    
    public void explode(){
            Date currentTime = new Date();
            //System.out.println(currentTime.getTime() - createdTime.getTime() );
            System.out.println("BOOOM!");
        
    }
        
    

}
    

