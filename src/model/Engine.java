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
import database.DB;
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
    
    
    //private Bomb _bomb;
    private int bombcnt = 0;
    
    

    private ArrayList<Player> players = new ArrayList();
    private ArrayList<Monster> monsters = new ArrayList();
    private int monster_count = 7;
    
    private ArrayList<Bomb> DroppedBombs = new ArrayList();
    
    private boolean spaceButtonPressed;
    
    private Monster _monster;
    
    public Engine(int player_count, int round_count)
    {   
        super();
        for(int i = 0; i < player_count; i++){
            Image runner_img = new ImageIcon("src/media/player1.png").getImage();
            Player _player = new Player(40, 680, 33, 33, runner_img);
            players.add(_player);
        }

        //bg = new ImageIcon("src/media/bg.png").getImage();//setting the backgroung picture to the game
        //MOVE LEFT
        this.getInputMap().put(KeyStroke.getKeyStroke("pressed A"), "pressed a");
        this.getActionMap().put("pressed a", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                players.get(0).set_x_speed(-r_movement);
            }
        });

        this.getInputMap().put(KeyStroke.getKeyStroke("released A"), "released a");
        this.getActionMap().put("released a", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                players.get(0).set_x_speed(0);
            }
        });
       
        //MOVE RIGHT
        this.getInputMap().put(KeyStroke.getKeyStroke("pressed D"), "pressed d");
        this.getActionMap().put("pressed d", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                players.get(0).set_x_speed(+r_movement);
            }        
        });

        this.getInputMap().put(KeyStroke.getKeyStroke("released D"), "released d");
        this.getActionMap().put("released d", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                players.get(0).set_x_speed(0);
            }
        });
        //MOVE DOWN
        this.getInputMap().put(KeyStroke.getKeyStroke("pressed S"), "pressed s");
        this.getActionMap().put("pressed s", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                players.get(0).set_y_speed(r_movement);
            }
        });

        this.getInputMap().put(KeyStroke.getKeyStroke("released S"), "released s");
        this.getActionMap().put("released s", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                players.get(0).set_y_speed(0);
            }
        });
        //MOVE UP
        this.getInputMap().put(KeyStroke.getKeyStroke("pressed W"), "pressed w");
        this.getActionMap().put("pressed w", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                players.get(0).set_y_speed(-r_movement);
            }
        });

        this.getInputMap().put(KeyStroke.getKeyStroke("released W"), "released w");
        this.getActionMap().put("released w", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                players.get(0).set_y_speed(0);
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
                
            for(int i = 0; i < players.size(); i++){
                if(players.get(i).canDropbomb()){
                    
                    players.get(i).bomb.set_X(players.get(i).get_X());
                    players.get(i).bomb.set_Y(players.get(i).get_Y());
                    
                    spaceButtonPressed = true; 
                    
                    DroppedBombs.add(players.get(i).bomb);
            //System.out.println(DroppedBombs.get(0).droppedTime);
                    
                    players.get(i).DropBomb();
                    temp2 = tempcnt;
                    
                }else{
                    System.out.println("You Ran out of BOMBS");
                }
            }
            }
        });
        restart_game();
        
        //ANIMATION
        _timer = new Timer(16, new FrameUpdate(this));
        _timer.start();
    
        
        
    }
    
    public void restart_game(){
        try {
            _level = new Level("src/levels/level" + id_level + ".txt");
        }catch (IOException ex) {
            Logger.getLogger(Engine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        for(int i = 0; i < monster_count; i++){
            generate_Monsters(_level);
        }
    }
    
    public void generate_Monsters(Level currentLevel) {
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
                Monster _monster = new Monster(x,y,40, 40,dragonImage);

                monsters.add(_monster);
                isfound = true;
            }
            
        }
    }
    
    @Override
    protected void paintComponent(Graphics grphcs) {
        
            super.paintComponent(grphcs);
            //grphcs.drawImage(bg, 0, 0, 800, 800, null);
            _level.placeGrounds(grphcs);
            _level.placeWalls(grphcs);
            
            _level.placeBoxes(grphcs);

            for(int i = 0; i < players.size(); i++){
                players.get(i).drawObject(grphcs);
            }
            
            for(int i = 0; i < monsters.size(); i++){
                monsters.get(i).drawObject(grphcs);
            }

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
                for(int q = 0; q < monsters.size(); q++){
                    
                
                monsters.get(q).move();
                for(int i = 0; i < players.size(); i++){
                    if (monsters.get(q).did_hit(players.get(i))) {
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
                     if(players.get(i).did_win())
                     {
                         point = point + 1;
                         JOptionPane.showMessageDialog(panel, "LEVEL PASSED!, YOU HAVE " + point + " points","GOOD JOB", JOptionPane.PLAIN_MESSAGE);
                         
                         if(id_level <= 9) id_level =  id_level + 1;
                         else id_level = 1;
                         
                         restart_game();
                     }
     
     
                     
                     if (_level.did_hit(monsters.get(q))) {
                         monsters.get(q).ChangeDirection(_level);
                     }
     
                     if (_level.did_hit(players.get(i))) {
                         players.get(i).set_x_speed(0);
                         players.get(i).set_y_speed(0);
                     }
                     
                     for(int j = 0; j < DroppedBombs.size(); j++){
                         if (tempcnt- temp2 == 90 && spaceButtonPressed) {
                             spaceButtonPressed = false;
                             players.get(j).canDropBomb = true;
     
                             DroppedBombs.get(0).explode(_level.boxes, players, monsters);
                             DroppedBombs.remove(0);
     
                         }
                     }
                         
                     
     
                     players.get(i).move();
                }
            }
                
            }

            repaint();
        }
    
    }     
 }