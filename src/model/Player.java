package model;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.ImageIcon;

class Player extends ActiveObject{
    
    //ATTRIBUTES
    private double speed_on_x_axis = 0;
    private double speed_on_y_axis = 0;
    
    private boolean player_moves_on_x = false;
    private boolean player_moves_on_y = false;
    
    
    public String tends_to_move_direction = "x";
    public double tend_value =0;
    
    
    
    public boolean canDropBomb = true;
    
    public Bomb bomb;
    //public ArrayList<Bomb> playersBombs = new ArrayList();
    
    // METHODS
   
    
    public Player(int x, int y, int w, int h, Image image) {
        super(x, y, w, h, image);

        Image img = new ImageIcon("src/media/Bomb.png").getImage();
        bomb = new Bomb(5, 5, 30, 30, img);
     
    }
    //GETTERS AND SETTERS
    public void set_x_speed(double speed_to_set) { 
        
        tend_value = speed_to_set;
        speed_on_x_axis = speed_to_set;
        
        tends_to_move_direction = "x";
        
        player_moves_on_x = true;
        player_moves_on_y = false;
    }
    public void set_y_speed(double speed_to_set) { 
        
        tend_value = speed_to_set;
        speed_on_y_axis = speed_to_set;
        
        tends_to_move_direction = "y";
        
        player_moves_on_y = true;
        player_moves_on_x = false;
    }
    
    public double get_x_speed() { return speed_on_x_axis; }
    public double get_y_speed() { return speed_on_y_axis; }
    
    
    //MOVEMENT
    public void move_x(){
        if ((speed_on_x_axis > 0 && _x + _width <= 780) || (0 > speed_on_x_axis && _x > 0)) _x += speed_on_x_axis;
        
    }
    public void move_y(){
        if ((speed_on_y_axis > 0 && _y + _height  <= 780) || (0 > speed_on_y_axis && _y > 0)) _y += speed_on_y_axis;
    }
    public void move(){
        if(player_moves_on_x == true) move_x();
        else move_y();
    }
  
    public boolean did_win(){
        
        return false;
    }
    
    @Override
    public boolean did_hit(ActiveObject hostileObject){
        if(tends_to_move_direction.equals("x"))
        {
            
            Rectangle player;
            Rectangle hostile_obj;

            
            player = new Rectangle(_x + (int)speed_on_x_axis , _y, _width,_height);
            hostile_obj = new Rectangle(hostileObject._x, hostileObject._y, hostileObject._width, hostileObject._height);    
            
            return player.intersects(hostile_obj);
        }else
        {
            Rectangle player = new Rectangle(_x , _y+ (int) speed_on_y_axis, _width, _height);
            Rectangle hostile_obj = new Rectangle(hostileObject._x, hostileObject._y, hostileObject._width, hostileObject._height);        
            return player.intersects(hostile_obj);
        }
    }
    
    
    public boolean canDropbomb(){
        return canDropBomb;
        
    }

    public void DropBomb() {
     
        canDropBomb = false;
    }

    public Ground whereAmI(Level _level) {

        for (Ground ground : _level.grounds) {

            boolean isGroundFree = false;

            for (int i = 0; i < _level.boxes.size(); i++) {
                if (ground.get_X() == _level.boxes.get(i).get_X() && ground.get_Y() == _level.boxes.get(i).get_Y()) {
                    isGroundFree = false;
                    break;
                } else {
                    isGroundFree = true;
                }
            }

            if (this.did_hit(ground) && isGroundFree) {
               return ground;
            }
        }
        return null;
    }
    
}
