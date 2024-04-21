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
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.imageio.ImageIO;


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
    private int monster_count = 1;
    
    private ArrayList<Bomb> DroppedBombs = new ArrayList();
    private ArrayList<Explosion> _explosions = new ArrayList();
    
    private boolean spaceButtonPressed;
    
    private Monster _monster;
    
    public Image[] loadExplosionFrames() {
        int numFrames = 8; // Replace with the number of frames in your animation
        Image[] frames = new Image[numFrames*numFrames];
        int cnt = 0;
    for (int j = 0; j < numFrames; j++) {
        for (int i = 0; i < numFrames; i++) {
            try {
                //System.out.println("src/media/explosions/row-" + (j+1) + "-column-" + (i+1) + ".png");
                frames[cnt] = ImageIO.read(new File("src/media/explosions/row-" + (j+1) + "-column-" + (i+1) + ".png"));
                cnt++;
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }
        
    }

        return frames;
    }
    
    public Engine(int player_count, int round_count, int id_level) throws IOException
    {   
        super();

        _level = new Level("src/levels/level" + id_level + ".txt");

        for(int i = 0; i < monster_count; i++){
            generate_Monsters(_level);
        }


        if (player_count == 1) {
            Image runner_img = new ImageIcon("src/media/player1.png").getImage();
            Player _player = new Player(40, 40, 33, 33, runner_img);
            players.add(_player);
            setControls(players.get(0),"T", "G", "F", "H", " SPACE");
        }
        else if (player_count == 2) {
            Image runner_img = new ImageIcon("src/media/player1.png").getImage();
            Player _player = new Player(40, 40, 33, 33, runner_img);
            players.add(_player);
            runner_img = new ImageIcon("src/media/player2.png").getImage();
            _player = new Player(40, 680, 33, 33, runner_img);
            players.add(_player);

            setControls(players.get(0),"T", "G", "F", "H", " SPACE");
            setControls(players.get(1),"W", "A", "S", "D", " C");
        }
        else if (player_count == 3) {
            Image runner_img = new ImageIcon("src/media/player1.png").getImage();
            Player _player = new Player(40, 40, 33, 33, runner_img);
            players.add(_player);
            runner_img = new ImageIcon("src/media/player2.png").getImage();
            _player = new Player(40, 680, 33, 33, runner_img);
            players.add(_player);
            runner_img = new ImageIcon("src/media/player3.png").getImage();
            _player = new Player(680, 680, 33, 33, runner_img);
            players.add(_player);
            setControls(players.get(0),"T", "G", "F", "H", " SPACE");
            setControls(players.get(1),"W", "A", "S", "D", " C");
            setControls(players.get(2),"O", "L", "K", ";", " M");
        }
        //PAUSE
        this.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "escape");
        this.getActionMap().put("escape", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                game_paused = !game_paused;
            }
        });
        
        
        restart_game();
        
        //ANIMATION
        _timer = new Timer(16, new FrameUpdate(this));
        _timer.start();
    
        
        
    } 

    public void setControls(Player player, String up, String down, String left, String right, String bombdrop) {
        
        this.getInputMap().put(KeyStroke.getKeyStroke("pressed " + left), "pressed " + left);
        this.getActionMap().put("pressed " + left, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                player.set_x_speed(-r_movement);
            }
        });

        this.getInputMap().put(KeyStroke.getKeyStroke("released " + left), "released " + left);
        this.getActionMap().put("released " + left, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                player.set_x_speed(0);
            }
        });
       
        //MOVE RIGHT
        this.getInputMap().put(KeyStroke.getKeyStroke("pressed " + right), "pressed " + right);
        this.getActionMap().put("pressed " + right, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                player.set_x_speed(+r_movement);
            }        
        });

        this.getInputMap().put(KeyStroke.getKeyStroke("released " + right), "released " + right);
        this.getActionMap().put("released " + right, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                player.set_x_speed(0);
            }
        });
        //MOVE DOWN
        this.getInputMap().put(KeyStroke.getKeyStroke("pressed " + down), "pressed " + down  );
        this.getActionMap().put("pressed " + down, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                player.set_y_speed(r_movement);
            }
        });

        this.getInputMap().put(KeyStroke.getKeyStroke("released " + down), "released " + down);
        this.getActionMap().put("released " + down, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                player.set_y_speed(0);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("pressed " + up), "pressed " + up);
        this.getActionMap().put("pressed " + up, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                player.set_y_speed(-r_movement);
            }
        });

        this.getInputMap().put(KeyStroke.getKeyStroke("released " + up), "released " + up);
        this.getActionMap().put("released " + up, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                player.set_y_speed(0);
            }
        });

        this.getInputMap().put(KeyStroke.getKeyStroke(bombdrop), bombdrop);
        this.getActionMap().put(bombdrop, new AbstractAction() {
        
        @Override
        public void actionPerformed(ActionEvent ae) {
                
                if(player.canDropbomb()){
                    
                    Ground gr = player.whereAmI(_level);
                    player.bomb.set_X(gr.get_X());
                    player.bomb.set_Y(gr.get_Y());
                    //System.out.println(player.get_X() + " " + player.get_Y());
                    spaceButtonPressed = true; 
                    
                    DroppedBombs.add(player.bomb);
                    //System.out.println(DroppedBombs.get(0).droppedTime);
                    
                    player.DropBomb();
                    temp2 = tempcnt;
                    
                }else{
                    System.out.println("You Ran out of BOMBS");
                }
            
            }
        });
        
    }
    
    public void restart_game(){

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

            Iterator<Explosion> iterator;
            iterator = _explosions.iterator();
            while (iterator.hasNext()) {
                Explosion explosion = iterator.next();
                explosion.draw(grphcs);
                if (explosion.isFinished()) {
                    iterator.remove();
                }
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
                             Image[] explosionFrames = loadExplosionFrames();
                             _explosions.add(new Explosion(DroppedBombs.get(0)._x, DroppedBombs.get(0)._y, 40, 40, explosionFrames));
                             DroppedBombs.get(0).explode(_level.boxes, players, monsters, _level.walls, _explosions);
                             DroppedBombs.remove(0);
                             
     
                         }
                     }
                         
                     
                    if (players != null && !players.isEmpty()) {
                        players.get(i).move();
                    }
                    
                    for (Explosion explosion : _explosions) {
                        explosion.update();
                    }
                }
            }
                
            }

            repaint();
        }
    
    }     
 }