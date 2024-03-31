package Bomberman;

import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;


class Level {
    // ATTRIBUTES
    private final int w_height = 40;
    private final int w_width = 40;
    
    public ArrayList<Wall> walls;
    public ArrayList<Box> boxes;
   
    
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
        
        /////////////////////////// placing walls according to a txt file//////////////////////
        while ((line = br.readLine()) != null) {
            int x_cnt = 0;
            for (char i : line.toCharArray()) {
                if (Character.isDigit(i)) {
                    int index = Character.getNumericValue(i);
                    if(index == 1){
                    Image image = new ImageIcon("src/media/wall.png").getImage();
                    
                    walls.add(new Wall(x_cnt * w_width, y_cnt * w_height, w_width, w_height, image));
                    }
                     if(index == 2){
                         System.out.println("xexe");
                        Image image = new ImageIcon("src/media/box.png").getImage();
                        boxes.add(new Box(x_cnt * w_width, y_cnt * w_height, w_width, w_height, image));
                    }
                    
                }
                x_cnt++;
            }
            y_cnt++;
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
    
    
}
