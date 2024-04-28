
package model;

import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javax.swing.Timer;

public class Bomb extends ActiveObject {
    /*
        
        x, y
    */
    public long timestamp;
    public boolean hasExploded;
    public Date droppedTime;
    public Ground droppedGround;// if null then bomb is not dropped

    public Player owner;

    private int explosionRadius;
    private char[] fourdirections;

    public Bomb(int x, int y, int widt, int height, Image image, Player owner) {
        
        super(x, y, widt, height, image);
        hasExploded = false;
        explosionRadius = 20;
        this.timestamp = System.currentTimeMillis();
        this.owner = owner;
    }
    
    
    
    public boolean isExploded(){
        
        if(droppedTime != null){
            Date currentTime = new Date();
            System.out.println(droppedTime);

            }

        return false;
    }
    
     public Image[] loadExplosionFrames() {
        int numFrames = 8; // Replace with the number of frames in your animation
        Image[] frames = new Image[numFrames*numFrames];
        int cnt = 0;
    for (int j = 0; j < numFrames; j++) {
        for (int i = 0; i < numFrames; i++) {
            try {
                //System.out.println("src/media/explosions/row-" + (j+1) + "-column-" + (i+1) + ".png");
                frames[cnt] = ImageIO.read(new File("src/media/explosions/row-" + (j+1) + "-column-" + (i+1) + ".png"));
                cnt++;
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }
          }

        return frames;
    }
    
   public void explode(ArrayList<Box> boxes, ArrayList<Player> players, ArrayList<Monster> monsters, ArrayList<Wall> walls, ArrayList<Explosion> effects) {
    int up_x = _x;
    int up_y = _y - 40;
    int bottom_x = _x;
    int bottom_y = _y + 40;
    int left_x = _x - 40;
    int left_y = _y;
    int right_x = _x + 40;
    int right_y = _y;
    Image[] frames = loadExplosionFrames();
    // Remove boxes
    if(removeObjectsAt(boxes, up_x, up_y, bottom_x, bottom_y, left_x, left_y, right_x, right_y)){
        effects.add(new Explosion(up_x, up_y, 40, 40, frames));
    effects.add(new Explosion(bottom_x, bottom_y, 40, 40, frames));
    effects.add(new Explosion(left_x, left_y, 40, 40, frames));
    effects.add(new Explosion(right_x, right_y, 40, 40, frames));
    }

    // Remove players
    removeObjectsAt(players, up_x, up_y, bottom_x, bottom_y, left_x, left_y, right_x, right_y);

    // Remove monsters
    removeObjectsAt(monsters, up_x, up_y, bottom_x, bottom_y, left_x, left_y, right_x, right_y);
    
    playMusic("src/sounds/explosion.wav");
    
    
    }

    private <T extends ActiveObject> boolean removeObjectsAt(ArrayList<T> objects, int up_x, int up_y, int bottom_x, int bottom_y, int left_x, int left_y, int right_x, int right_y) {
        Rectangle explosionAreaUp = new Rectangle(up_x, up_y, 40, 40);
        Rectangle explosionAreaDown = new Rectangle(bottom_x, bottom_y, 40, 40);
        Rectangle explosionAreaLeft = new Rectangle(left_x, left_y, 40, 40);
        Rectangle explosionAreaRight = new Rectangle(right_x, right_y, 40, 40);
    
        boolean objectRemoved = false;
        Iterator<T> iterator = objects.iterator();
        while (iterator.hasNext()) {
            T object = iterator.next();
            Rectangle objectRect = new Rectangle(object._x, object._y, object._width, object._height);
    
            if (explosionAreaUp.intersects(objectRect) || explosionAreaDown.intersects(objectRect) || 
                explosionAreaLeft.intersects(objectRect) || explosionAreaRight.intersects(objectRect)) {
                iterator.remove();
                objectRemoved = true;
            }
        }
        return objectRemoved;
    }
    
    private void playMusic(String filePath) {
    try {
        // Directly use a file path
        File audioFile = new File(filePath);
        if (!audioFile.exists()) {
            System.out.println("File not found: " + filePath);
            return;
        }
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(audioFile);
        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        clip.start();
    } catch (UnsupportedAudioFileException e) {
        System.out.println("Unsupported audio file: " + e);
        e.printStackTrace();
    } catch (IOException e) {
        System.out.println("IO error: " + e);
        e.printStackTrace();
    } catch (LineUnavailableException e) {
        System.out.println("Line unavailable: " + e);
        e.printStackTrace();
    }
}
        
}


