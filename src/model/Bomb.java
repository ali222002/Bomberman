
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
     * 
     * x, y
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

    public boolean isExploded() {

        if (droppedTime != null) {
            Date currentTime = new Date();
            System.out.println(droppedTime);

        }

        return false;
    }

    public Image[] loadExplosionFrames() {
        int numFrames = 8; // Replace with the number of frames in your animation
        Image[] frames = new Image[numFrames * numFrames];
        int cnt = 0;
        for (int j = 0; j < numFrames; j++) {
            for (int i = 0; i < numFrames; i++) {
                try {
                    // System.out.println("src/media/explosions/row-" + (j+1) + "-column-" + (i+1) +
                    // ".png");
                    frames[cnt] = ImageIO
                            .read(new File("src/media/explosions/row-" + (j + 1) + "-column-" + (i + 1) + ".png"));
                    cnt++;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        return frames;
    }

    public void explode(ArrayList<Box> boxes, ArrayList<Player> players, ArrayList<Monster> monsters, ArrayList<Wall> walls, ArrayList<Explosion> effects) {
        int range = owner.getBombRange(); // Get the bomb range from the owner
    
        Image[] frames = loadExplosionFrames();
    
        // Define directions for iteration
        int[][] directions = { {0, -1}, {0, 1}, {-1, 0}, {1, 0} }; // Up, down, left, right
    
        for (int[] dir : directions) {
            int dx = dir[0] * 40; // x step
            int dy = dir[1] * 40; // y step
    
            for (int i = 1; i <= range / 40; i++) { // Iterate over each step within the range
                int newX = _x + dx * i;
                int newY = _y + dy * i;
    
                // Define the current explosion area
                Rectangle explosionArea = new Rectangle(newX, newY, 40, 40);
    
                // Check for walls
                boolean blockedByWall = checkForWalls(walls, explosionArea);
    
                // Check and remove boxes and other objects
                boolean blockedByBox = removeObjectsAt(boxes, explosionArea);
                removeObjectsAt(players, explosionArea);
                removeObjectsAt(monsters, explosionArea);
    
                effects.add(new Explosion(newX, newY, 40, 40, frames));
    
                if (blockedByWall || blockedByBox) break; // Stop further progression in this direction if blocked
            }
        }
    
        playMusic("src/sounds/explosion.wav");
    }
    
    private boolean checkForWalls(ArrayList<Wall> walls, Rectangle explosionArea) {
        for (Wall wall : walls) {
            Rectangle wallRect = new Rectangle(wall._x, wall._y, wall._width, wall._height);
            if (explosionArea.intersects(wallRect)) {
                return true; // Wall found, blocking the path
            }
        }
        return false; // No wall found
    }
    
    private <T extends ActiveObject> boolean removeObjectsAt(ArrayList<T> objects, Rectangle explosionArea) {
        boolean objectRemoved = false;
        Iterator<T> iterator = objects.iterator();
    
        while (iterator.hasNext()) {
            T object = iterator.next();
            Rectangle objectRect = new Rectangle(object._x, object._y, object._width, object._height);
            
            if (explosionArea.intersects(objectRect)) {
                if (object instanceof Player) {
                    ((Player) object).Die();
                }
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
