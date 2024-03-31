package Bomberman;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

class ActiveObject {


    protected int _width;  
    protected int _height; 
    //COORDINATES 
    protected int _x;
    protected int _y;
    
    protected Image _image;

    //CONSTRUCTOR
    public ActiveObject( int x, int y, int w, int h, Image image) {
        
        _width = w;
        _height = h;
        
        _x = x;
        _y = y;
        
        _image = image;
    }
    public void drawObject(Graphics g) {
        g.drawImage( _image, _x, _y, _width, _height , null);
    }
    
    // 
    public boolean did_hit(ActiveObject hostileObject) {

        Rectangle shape = new Rectangle(_x, _y, _width, _height);
        Rectangle hostile_shape = new Rectangle(hostileObject._x, hostileObject._y, hostileObject._width, hostileObject._height); 
        
        
        return shape.intersects(hostile_shape);
    }
    
    //GETTERS SETTERS
       
    public int getWidth() {return _width;}
    public int getHeight() { return _height;}
    public int get_X() {return _x;}
    public int get_Y() {return _y;}

    public void setWidth(int width) {_width = width;}
    public void setHeight(int height) {_height = height;}
    public void set_X(int x) {_x = x;}
    public void set_Y(int y) { _y = y;}

    

    

}


