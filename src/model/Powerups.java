package model;

import java.awt.Image;

class Powerups extends ActiveObject{
    public Powerups(int x, int y, int width, int height, Image image) {
        super(x, y, width, height, image);
    }
}

class MoreBombPowerup extends Powerups{
    
    public int bombCnt;
    
    public MoreBombPowerup(int x, int y, int width, int height, Image image, int bombCnt) {
        super(x, y, width, height, image);
        this.bombCnt = bombCnt;
    }
}

class RangePowerup extends Powerups{
    
    public int range;
    
    public RangePowerup(int x, int y, int width, int height, Image image, int range) {
        super(x, y, width, height, image);
        this.range = range;
    }
}
