package model;

import view.UI;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import bomberman.database.DB;
import java.util.ArrayList;
import java.util.Date;

public class Engine extends JPanel{
    

    
    private UI Menu;
    //ATTRIBUTES   
        //FPS
    private int fps = 144;
    private int tempcnt = 0;
    private int temp2= 0;
    private int r_movement = 1;
        //BOOLEAN IF GAME IS PAUSED
    private boolean game_paused = false;
        //BACKGROUND
    private Image bg;
    public int id_level = 1;//
    public int point = 0;
    
    private Level _level;
    private Timer _timer;
    
    private Player _player;
    //private Bomb _bomb;
    private int bombcnt = 0;
    
    
    
    
    private ArrayList<Bomb> DroppedBombs = new ArrayList();
    
    private boolean spaceButtonPressed;
    
    private Monster _monster;
    
    public Engine()
    {
        super();
        bg = new ImageIcon("src/media/bg.png").getImage();//setting the backgroung picture to the game
        //MOVE LEFT
        this.getInputMap().put(KeyStroke.getKeyStroke("pressed A"), "pressed a");
        this.getActionMap().put("pressed a", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                _player.set_x_speed(-r_movement);
            }
        });

        this.getInputMap().put(KeyStroke.getKeyStroke("released A"), "released a");
        this.getActionMap().put("released a", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                _player.set_x_speed(0);
            }
        });
       
        //MOVE RIGHT
        this.getInputMap().put(KeyStroke.getKeyStroke("pressed D"), "pressed d");
        this.getActionMap().put("pressed d", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                _player.set_x_speed(+r_movement);
            }        
        });

        this.getInputMap().put(KeyStroke.getKeyStroke("released D"), "released d");
        this.getActionMap().put("released d", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                _player.set_x_speed(0);
            }
        });
        //MOVE DOWN
        this.getInputMap().put(KeyStroke.getKeyStroke("pressed S"), "pressed s");
        this.getActionMap().put("pressed s", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                _player.set_y_speed(r_movement);
            }
        });

        this.getInputMap().put(KeyStroke.getKeyStroke("released S"), "released s");
        this.getActionMap().put("released s", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                _player.set_y_speed(0);
            }
        });
        //MOVE UP
        this.getInputMap().put(KeyStroke.getKeyStroke("pressed W"), "pressed w");
        this.getActionMap().put("pressed w", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                _player.set_y_speed(-r_movement);
            }
        });

        this.getInputMap().put(KeyStroke.getKeyStroke("released W"), "released w");
        this.getActionMap().put("released w", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                _player.set_y_speed(0);
            }
        });
        //PAUSE
        this.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "escape");
        this.getActionMap().put("escape", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                game_paused = !game_paused;
            }
        });
        
        
        
        this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "space");
        this.getActionMap().put("space", new AbstractAction() {
        
        @Override
        public void actionPerformed(ActionEvent ae) {
                
                if(_player.canDropbomb()){
                    
                    _player.bomb.set_X(_player.get_X());
                    _player.bomb.set_Y(_player.get_Y());
                    
                    spaceButtonPressed = true; 
                    
                    DroppedBombs.add(_player.bomb);
            //System.out.println(DroppedBombs.get(0).droppedTime);
                    
                    _player.DropBomb();
                    temp2 = tempcnt;
                    
                }else{
                    System.out.println("You Ran out of BOMBS");
                }
                
            }
        });
        restart_game();
        
        //ANIMATION
        _timer = new Timer(1000 / fps, new FrameUpdate(this));
        _timer.start();
        
        
    }
    
    public void restart_game(){
        try {
            _level = new Level("src/levels/level" + id_level + ".txt");
        }catch (IOException ex) {
            Logger.getLogger(Engine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        Image runner_img = new ImageIcon("src/media/player1.png").getImage();
        _player = new Player(50, 650, 33, 33, runner_img);
        
        generate_Monster(_level);
   
        
    }
    
    public Monster generate_Monster(Level currentLevel) {
        Random r = new Random();
        boolean isfound = false;
        while (!isfound) {
            int x = r.nextInt(741);
            int y = r.nextInt(321) + 40;
            boolean tmp = true;
            boolean tmp2 = true;
            for (Wall wall : _level.walls) {

                Rectangle rect = new Rectangle(x, y, 40, 40);
                Rectangle otherRect = new Rectangle(wall._x, wall._y, wall._width, wall._height);
                if (rect.intersects(otherRect)) {
                    tmp = false;
                }

            }
            for (Box box : _level.boxes) {

                Rectangle rect = new Rectangle(x, y, 40, 40);
                Rectangle otherRect = new Rectangle(box._x, box._y, box._width, box._height);
                if (rect.intersects(otherRect)) {
                    tmp2 = false;
                }

            }
            if(tmp && tmp2)
            {
                Image dragonImage = new ImageIcon("src/media/dragon.png").getImage();
                _monster = new Monster(x,y,40, 40,dragonImage);
                isfound = true;
                return _monster;
            }
            
        }
        return null;
    }
    
    @Override
    protected void paintComponent(Graphics grphcs) {
        
            super.paintComponent(grphcs);
            grphcs.drawImage(bg, 0, 0, 800, 800, null);
            _level.placeWalls(grphcs);
            _level.placeBoxes(grphcs);
            _player.drawObject(grphcs);
            _monster.drawObject(grphcs);
            for(int i = 0; i< DroppedBombs.size(); i++){
                DroppedBombs.get(i).drawObject(grphcs);
            }
        
        
    
    }
    
    class FrameUpdate implements ActionListener {

        public JPanel panel;
 
        FrameUpdate(JPanel panel) {
            this.panel = panel;
        }
        
        @Override       
        public void actionPerformed(ActionEvent ae) {
            if (!game_paused) {
                tempcnt++;
                //System.out.println(tempcnt);
                _monster.move();
                if (_monster.did_hit(_player)) {
                   String user_name = JOptionPane.showInputDialog("MONSTER KILLED YOU!!!! " + (point) + " POINTS EARNED\n      Please give your name:","");
                    if(user_name != null){
                    try{
                           DB db = new DB();
                            db.insertScore(user_name,point);
                            }catch (SQLException ex) {
                                Logger.getLogger(DB.class.getName());
                             }
                            point = 0;
                            id_level = 1;
                            restart_game();
                }
                }
                if(_player.did_win())
                {
                    point = point + 1;
                    JOptionPane.showMessageDialog(panel, "LEVEL PASSED!, YOU HAVE " + point + " points","GOOD JOB", JOptionPane.PLAIN_MESSAGE);
                    
                    if(id_level <= 9) id_level =  id_level + 1;
                    else id_level = 1;
                    
                    restart_game();
                }
                
                if (_level.did_hit(_monster)) {
                    _monster.ChangeDirection(_level);
                }

                if (_level.did_hit(_player)) {
                    _player.set_x_speed(0);
                    _player.set_y_speed(0);
                }
                //System.out.println("on hand" + _player.playersBombs.size());
                //System.out.println("dropped " + DroppedBombs.size());
                
                
                    //System.out.println("imhere");
                    for(int i = 0; i < DroppedBombs.size(); i++){

                        //System.out.println(i);
                        if (tempcnt- temp2 == 180 && spaceButtonPressed) {
                            spaceButtonPressed = false;
                            //System.out.println("AY BLE");
                            _player.canDropBomb = true;
                            DroppedBombs.remove(0);
                        }
                    }
                
                _player.move();
            }

            repaint();
        }
    
    }     
 }