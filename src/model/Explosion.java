package model;

import java.awt.Graphics;
import java.awt.Image;

public class Explosion {
    private int _x, _y, _width, _height;
    private Image[] _frames;
    private int _currentFrame;

    public Explosion(int x, int y, int width, int height, Image[] frames) {
        _x = x;
        _y = y;
        _width = width;
        _height = height;
        _frames = frames;
        _currentFrame = 0;
    }

    public void update() {
        _currentFrame++;
        if (_currentFrame >= _frames.length) {
            // The explosion is finished
            _currentFrame = -1;
        }
    }

    public void draw(Graphics grphcs) {
        if (_currentFrame >= 0) {
            grphcs.drawImage(_frames[_currentFrame], _x, _y, _width, _height, null);
        }
    }

    public boolean isFinished() {
        return _currentFrame < 0;
    }
}