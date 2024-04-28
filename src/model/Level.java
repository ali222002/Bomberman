package model;

import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import java.util.Random;


class Level {
    // ATTRIBUTES
    private final int w_height = 40;
    private final int w_width = 40;
    
    public ArrayList<Wall> walls;
    public ArrayList<Box> boxes;
    public ArrayList<Ground> grounds;
    public ArrayList<Powerups> powerups;
    
    //CONSTRUCTOR
    public Level(String levelPath) throws IOException {
        load_level(levelPath);
    }
    // METHODS
    private void load_level(String levelPath) throws IOException {
        int y_cnt = 0;
        String line;
        BufferedReader br;
        
        br = new BufferedReader(new FileReader(levelPath));
        walls = new ArrayList<>();
        boxes = new ArrayList<>();
        grounds = new ArrayList<>();
        powerups = new ArrayList<>();
        Random random = new Random();
        int randomNumber;
        /////////////////////////// placing walls according to a txt file//////////////////////
        while ((line = br.readLine()) != null) {
            int x_cnt = 0;
            for (char i : line.toCharArray()) {
                if (Character.isDigit(i)) {
                    int index = Character.getNumericValue(i);

                    Image image1 = new ImageIcon("src/media/ground.png").getImage();
                    grounds.add(new Ground(x_cnt * w_width, y_cnt * w_height, w_width, w_height, image1));

                    if(index == 1){
                    Image image = new ImageIcon("src/media/wall.png").getImage();
                    
                    walls.add(new Wall(x_cnt * w_width, y_cnt * w_height, w_width, w_height, image));
                    }
                    if(index == 2){
                        randomNumber = random.nextInt(100);
                        if (randomNumber < 7){
                            randomNumber = random.nextInt(2);
                            if(randomNumber == 1){
                                Image image = new ImageIcon("src/media/moreB.png").getImage();
                                powerups.add(new MoreBombPowerup(x_cnt * w_width, y_cnt * w_height, w_width, w_height, image, 2));
                            }else{
                                Image image = new ImageIcon("src/media/moreR.png").getImage();
                                powerups.add(new RangePowerup(x_cnt * w_width, y_cnt * w_height, w_width, w_height, image, 3));
                            }
                            
                        }
                        
                       Image image2 = new ImageIcon("src/media/box.png").getImage();
                       boxes.add(new Box(x_cnt * w_width, y_cnt * w_height, w_width, w_height, image2));
                        
                    }
                    
                    
                    
                }
                x_cnt++;
            }
            y_cnt++;
        }
        
    }
    //DISPLAY GROUNDS ON MAP
    public void placeGrounds(Graphics g) {
        for (Ground ground : grounds) {
            ground.drawObject(g);
        }
    }
    //DISPLAY WALLS ON MAP
    public void placeWalls(Graphics g) {
        for (Wall wall : walls) {
            wall.drawObject(g);
        }
    }
    //DISPLAY BOXES ON MAP
    public void placeBoxes(Graphics g) {
        for (Box box : boxes) {
            box.drawObject(g);
        }
    }
    public void placePowerups(Graphics g) {
        for (Powerups powerup : powerups) {
            powerup.drawObject(g);
        }
    }
    
    //DID PLAYER ENCOUNTER A WALL ror BOX???
    public boolean did_hit(Player player) {
        Wall whits = null;
        for (Wall wall : walls){
            if (player.did_hit(wall)) {
                whits = wall;
                break;
            }
        }
        Box bhits = null;
        for (Box box : boxes){
            if (player.did_hit(box)) {
                bhits = box;
                break;
            }
        }
        if (whits != null || bhits != null) return true; 
        else return false;
    }
    
    //DID MONSTER ENCOUNTER A WALL or BOX??
    public boolean did_hit(Monster monster) {
        Wall whits = null;
        for (Wall wall : walls){
            if (monster.did_hit(wall)) {
                whits = wall;
                break;
            }
        }
        Box bhits = null;
        for (Box box : boxes){
            if (monster.did_hit(box)) {
                bhits = box;
                break;
            }
        }
        if (whits != null || bhits != null) return true; 
        else return false;
    }

    public boolean did_hit(Bomb bomb) {
       
       
        Box bhits = null;
        for (Box box : boxes){
            if (bomb.did_hit(box)){
                bhits = box;
                break;
            }
        }
        if (bhits != null) return true; 
        else return false;
    }
    
    
}
