package model;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

public class Monster extends ActiveObject {

    // ANIMATION

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public Direction direction = Direction.RIGHT;
    private Image[] animationFramesUp;
    private Image[] animationFramesDown;
    private Image[] animationFramesLeft;
    private Image[] animationFramesRight;
    private int currentFrame = 0;
    // ATTRIBUTES
    public double x_speed = 1;
    public double y_speed;
    private boolean if_x = true;
    private boolean if_y = false;
    // PUBLIC ATTRIBUTES
    public String tend_to_move_directory = "x";
    public double tend_value = 0;
    // CONSTRUCTOR

    public Monster(int x, int y, int w, int h, Image[] animationFramesUp, Image[] animationFramesDown,
            Image[] animationFramesLeft, Image[] animationFramesRight) {
        super(x, y, w, h, animationFramesRight[0]);
        this.animationFramesUp = animationFramesUp;
        this.animationFramesDown = animationFramesDown;
        this.animationFramesLeft = animationFramesLeft;
        this.animationFramesRight = animationFramesRight;
    }

    // MOVEMENT
    public void move_x() {
        if ((x_speed > 0 && _x + _width <= 780) || (0 > x_speed && _x > 0))
            _x += x_speed;
        else
            ChangeDirection();

    }

    public void move_y() {
        if ((y_speed < 0 && _y > 0) || (y_speed > 0 && _y + _height <= 740))
            _y += y_speed;
        else
            ChangeDirection();
    }
    
    private int moveCounter = 0;
    private int changeDirectionInterval = 10; // Change direction every 30 moves
    private Random random = new Random();
    
    public void move() {
        if (if_x) {
            move_x();
        } else {
            move_y();
        }
        updateAnimationFrame();

        // Increment counter and randomly change direction
        moveCounter++;
        if (moveCounter >= changeDirectionInterval) {
            if (random.nextInt(100) < 30) {  // 30% chance to change direction
                ChangeDirection();
            }
            moveCounter = 0; // Reset counter after changing direction or continuing
        }
    }

    private void updateAnimationFrame() {
        currentFrame = (currentFrame + 1) % animationFramesRight.length; // Default length
        switch (direction) {
            case UP:
                _image = animationFramesUp[currentFrame];
                break;
            case DOWN:
                _image = animationFramesDown[currentFrame];
                break;
            case LEFT:
                _image = animationFramesLeft[currentFrame];
                break;
            case RIGHT:
                _image = animationFramesRight[currentFrame];
                break;
        }
    }

    // DIRECTION CHANGERS
    public void ChangeDirection() {
        Random r = new Random();
        int n = r.nextInt(4);
        switch (n) {
            case 0 -> {
                y_speed = -1;
                tend_value = y_speed;
                tend_to_move_directory = "y";
                if_x = false;
                direction = Direction.UP;
            }
            case 1 -> {
                y_speed = 1;
                tend_value = y_speed;
                tend_to_move_directory = "y";
                if_x = false;
                direction = Direction.DOWN;
            }
            case 2 -> {
                x_speed = -1;
                tend_value = x_speed;
                tend_to_move_directory = "x";
                if_x = true;
                direction = Direction.LEFT;
            }
            default -> {
                x_speed = 1;
                tend_value = x_speed;
                tend_to_move_directory = "x";
                if_x = true;
                direction = Direction.RIGHT;
            }
        }
    }

    public void ChangeDirection(Level _level) {
        Random r = new Random();
        int n = r.nextInt(4);
        switch (n) {
            case 0 -> {
                boolean is_there_wall = false;
                boolean is_there_box = false;
                Rectangle moster_hitBox = new Rectangle(_x, _y - 1, _width, _height);
                for (Wall wall : _level.walls) {
                    Rectangle hostile_obj = new Rectangle(wall._x, wall._y, wall._width, wall._height);
                    if (moster_hitBox.intersects(hostile_obj))
                        is_there_wall = true;
                }
                for (Box box : _level.boxes) {
                    Rectangle hostile_obj = new Rectangle(box._x, box._y, box._width, box._height);
                    if (moster_hitBox.intersects(hostile_obj))
                        is_there_box = true;
                }
                if (!is_there_wall && !is_there_box) {
                    y_speed = -1;
                    tend_value = y_speed;
                    tend_to_move_directory = "y";
                    if_x = false;
                } else
                    ChangeDirection(_level);
            }
            case 1 -> {
                boolean is_there_wall = false;
                boolean is_there_box = false;
                Rectangle obj = new Rectangle(_x, _y + 1, _width, _height);
                for (Wall wall : _level.walls) {
                    Rectangle hostile_obj = new Rectangle(wall._x, wall._y, wall._width, wall._height);
                    if (obj.intersects(hostile_obj))
                        is_there_wall = true;
                }
                for (Box box : _level.boxes) {
                    Rectangle hostile_obj = new Rectangle(box._x, box._y, box._width, box._height);
                    if (obj.intersects(hostile_obj))
                        is_there_box = true;
                }
                if (!is_there_wall && !is_there_box) {
                    y_speed = 1;
                    tend_value = y_speed;
                    tend_to_move_directory = "y";
                    if_x = false;
                } else
                    ChangeDirection(_level);
            }
            case 2 -> {
                boolean is_there_wall = false;
                boolean is_there_box = false;
                Rectangle rect = new Rectangle(_x - 1, _y, _width, _height);
                for (Wall wall : _level.walls) {
                    Rectangle otherRect = new Rectangle(wall._x, wall._y, wall._width, wall._height);
                    if (rect.intersects(otherRect)) {
                        is_there_wall = true;
                    }
                }
                for (Box box : _level.boxes) {
                    Rectangle otherRect = new Rectangle(box._x, box._y, box._width, box._height);
                    if (rect.intersects(otherRect)) {
                        is_there_box = true;
                    }

                }
                if (!is_there_wall && !is_there_box) {
                    x_speed = -1;
                    tend_value = x_speed;
                    tend_to_move_directory = "x";
                    if_x = true;
                } else
                    ChangeDirection(_level);

            }
            default -> {
                boolean is_there_wall = false;
                boolean is_there_box = false;
                Rectangle rect = new Rectangle(_x + 1, _y, _width, _height);
                for (Wall wall : _level.walls) {
                    Rectangle otherRect = new Rectangle(wall._x, wall._y, wall._width, wall._height);
                    if (rect.intersects(otherRect)) {
                        is_there_wall = true;
                    }
                }

                for (Box box : _level.boxes) {
                    Rectangle otherRect = new Rectangle(box._x, box._y, box._width, box._height);
                    if (rect.intersects(otherRect)) {
                        is_there_box = true;
                    }
                }

                if (!is_there_wall && !is_there_box) {
                    x_speed = 1;
                    tend_value = x_speed;
                    tend_to_move_directory = "x";
                    if_x = true;
                } else
                    ChangeDirection(_level);
            }
        }
    }

    // CHECKS IF MONSTER HITS ANY OBJECT
    @Override
    public boolean did_hit(ActiveObject hostileObject) {
        if (tend_to_move_directory.equals("x")) {
            Rectangle obj = new Rectangle(_x + (int) x_speed, _y, _width, _height);
            Rectangle hostileOjbect = new Rectangle(hostileObject._x, hostileObject._y, hostileObject._width,
                    hostileObject._height);
            return obj.intersects(hostileOjbect);
        } else {
            Rectangle obj = new Rectangle(_x, _y + (int) y_speed, _width, _height);
            Rectangle hostileOjbect = new Rectangle(hostileObject._x, hostileObject._y, hostileObject._width,
                    hostileObject._height);
            return obj.intersects(hostileOjbect);
        }

    }
}
