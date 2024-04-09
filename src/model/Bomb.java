
package model;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.swing.Timer;

// q: I need to implement functionality for the bomb to destroy any nearby box, player, or monster but not walls, how can I do that?
// a: I can create a method that checks if the bomb has exploded, if it has, then I can check if the bomb has hit any of the objects that I want to destroy
// q: How can I check if the bomb has hit any of the objects that I want to destroy?
// a: I can create a method that checks if the bomb has hit any of the objects that I want to destroy





public class Bomb extends ActiveObject {
    /*
        
        x, y
    */
    public boolean hasExploded;
    public Date droppedTime;

    private int explosionRadius;

    public Bomb(int x, int y, int widt, int height, Image image) {
        
        super(x, y, widt, height, image);
        hasExploded = false;
        explosionRadius = 50;
    }
    
    
    
    public boolean isExploded(){
        
        if(droppedTime != null){
            Date currentTime = new Date();
            System.out.println(droppedTime);
//            long elapsedTime = currentTime.getTime() - droppedTime.getTime();

            // If more than 3 seconds have passed, the bomb explodes
//            if (elapsedTime >= 3000) {
//                explode();
//                hasExploded = true;
//                return true;
//            }
//                return false;
            }
        // Calculate the current time and the elapsed time since creation
        return false;
    }
    
    public void explode(ArrayList<Box> boxes, ArrayList<Player> players, ArrayList<Monster> monsters){
        Rectangle explosionRect = new Rectangle(this.get_X() - explosionRadius, this.get_Y() - explosionRadius, this.explosionRadius * 2, this.explosionRadius * 2);

        // Check for collision with boxes
        //Rectangle explosionRect = new Rectangle(this.x - explosionRadius, this.y - explosionRadius, this.explosionRadius * 2, this.explosionRadius * 2);

        // Check for collision with boxes
        for (Iterator<Box> iterator = boxes.iterator(); iterator.hasNext();) {
            Box box = iterator.next();
            Rectangle boxRect = new Rectangle(box.get_X(), box.get_Y(), box.getWidth(), box.getHeight());
            if (explosionRect.intersects(boxRect)) {
                // Remove the box that got hit by the explosion
                iterator.remove();
            }
        }

        // Check for collision with players
        for (Iterator<Player> iterator = players.iterator(); iterator.hasNext();) {
            Player player = iterator.next();
            Rectangle playerRect = new Rectangle(player.get_X(), player.get_Y(), player.getWidth(), player.getHeight());
            if (explosionRect.intersects(playerRect)) {
                // Remove the player that got hit by the explosion
                iterator.remove();
            }
        }

        // Check for collision with monsters
            for (Iterator<Monster> iterator = monsters.iterator(); iterator.hasNext();) {
                Monster monster = iterator.next();
                Rectangle monsterRect = new Rectangle(monster.get_X(), monster.get_Y(), monster.getWidth(), monster.getHeight());
                if (explosionRect.intersects(monsterRect)) {
                // Remove the monster that got hit by the explosion
                iterator.remove();
            }
        }
        
    }
        
    

}
    

