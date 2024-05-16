package model;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.ImageIcon;

class Player extends ActiveObject {

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    // ATTRIBUTES
    
    //points
    
    
    
    private Image[] animationFramesUp;
    private Image[] animationFramesDown;
    private Image[] animationFramesLeft;
    private Image[] animationFramesRight;
    private int currentFrame = 0;

    public boolean isAlive;
    
    public String name;
    public Direction direction;
    private boolean onBomb;

    public double speed_on_x_axis = 0;
    public double speed_on_y_axis = 0;

    public boolean player_moves_on_x = false;
    public boolean player_moves_on_y = false;

    public String tends_to_move_direction = "x";
    public double tend_value = 0;

    public boolean canDropBomb = true;

    public Bomb bomb;
    public ArrayList<Powerups> powerups = new ArrayList<Powerups>();
    // public ArrayList<Bomb> playersBombs = new ArrayList();
    
    //private int startRange = 1;
    
    // METHODS
    //PowerUps
    private int AllowedBombCnt;
    private int currentBombsOnField;
    private int bombRange;
    
    public Player(int x, int y, int w, int h, Image[] animationFramesUp, Image[] animationFramesDown, Image[] animationFramesLeft, Image[] animationFramesRight, String name) {
        super(x, y, w, h, animationFramesRight[0]); // Default image
        this.animationFramesUp = animationFramesUp;
        this.animationFramesDown = animationFramesDown;
        this.animationFramesLeft = animationFramesLeft;
        this.animationFramesRight = animationFramesRight;
        this.AllowedBombCnt = 1;
        this.currentBombsOnField = 0;
        this.bombRange = 40;
        this.isAlive = true;
        this.name = name;
    }

    // GETTERS AND SETTERS
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

    public double get_x_speed() {
        return speed_on_x_axis;
    }

    public double get_y_speed() {
        return speed_on_y_axis;
    }

    // MOVEMENT
    public void move_x() {
        if ((speed_on_x_axis > 0 && _x + _width <= 780) || (0 > speed_on_x_axis && _x > 0))
            _x += speed_on_x_axis;

    }

    public void move_y() {
        if ((speed_on_y_axis > 0 && _y + _height <= 780) || (0 > speed_on_y_axis && _y > 0))
            _y += speed_on_y_axis;
    }

    public void move() {
        if (player_moves_on_x == true)
            move_x();
        else
            move_y();

        currentFrame = (currentFrame + 1) % animationFramesRight.length; // Default length
        if (direction == Direction.UP) {
            _image = animationFramesUp[currentFrame];
        } else if (direction == Direction.DOWN) {
            _image = animationFramesDown[currentFrame];
        } else if (direction == Direction.LEFT) {
            _image = animationFramesLeft[currentFrame];
        } else if (direction == Direction.RIGHT) {
            _image = animationFramesRight[currentFrame];
        }
    }

    public boolean did_win() {

        return false;
    }

    @Override
    public boolean did_hit(ActiveObject hostileObject) {
        if (tends_to_move_direction.equals("x")) {

            Rectangle player;
            Rectangle hostile_obj;

            player = new Rectangle(_x + (int) speed_on_x_axis, _y, _width, _height);
            hostile_obj = new Rectangle(hostileObject._x, hostileObject._y, hostileObject._width,
                    hostileObject._height);

            return player.intersects(hostile_obj);
        } else {
            Rectangle player = new Rectangle(_x, _y + (int) speed_on_y_axis, _width, _height);
            Rectangle hostile_obj = new Rectangle(hostileObject._x, hostileObject._y, hostileObject._width,
                    hostileObject._height);
            return player.intersects(hostile_obj);
        }
    }

    public boolean canDropbomb() {
        if (isAlive) return currentBombsOnField < AllowedBombCnt; 
        else return false;
    }

    public void DropBomb() {
        currentBombsOnField++; 
        this.onBomb = true;
    }
    
    public void moveOffBomb() {
        this.onBomb = false;
    }

    public boolean isOnBomb() {
        return this.onBomb;
    }

    public void bombExploded() {
        currentBombsOnField--; 
    }

    public void increaseAllowedBombs() {
        AllowedBombCnt++;
    }

    public void increaseBombRange() {
        bombRange += 40;
    }

    public int getBombRange() {
        return bombRange;
    }
    
    public void Die(){
        this._x = -1;
        this._y = -1;
        isAlive = false;
    }


    public Ground whereAmI(Level _level) {

        for (Ground ground : _level.grounds) {

            boolean isGroundFreeofB = false;
            boolean isGroundFreeofW = false;
            for (int i = 0; i < _level.boxes.size(); i++) {
                if (ground.get_X() == _level.boxes.get(i).get_X() && ground.get_Y() == _level.boxes.get(i).get_Y()) {
                    isGroundFreeofB = false;
                    break;
                } else {
                    isGroundFreeofB = true;
                }
            }
            for (int i = 0; i < _level.walls.size(); i++) {
                if (ground.get_X() == _level.walls.get(i).get_X() && ground.get_Y() == _level.walls.get(i).get_Y()) {
                    isGroundFreeofW = false;
                    break;
                } else {
                    isGroundFreeofW = true;
                }
            }

            if (this.did_hit(ground) && (isGroundFreeofB && isGroundFreeofW)) {
                return ground;
            }
        }
        return null;
    }

}
