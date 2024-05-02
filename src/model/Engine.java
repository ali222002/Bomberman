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
import model.Player.Direction;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.imageio.ImageIO;

import org.json.JSONObject;
import org.json.JSONArray;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import org.json.JSONException;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

public class Engine extends JPanel {

    private int player_count;
    private int round_count;
    private int id_level;
    private int r_movement = 3;
    private boolean game_paused = false;

    ActiveObject gameObj;
    ActiveObject playerObj;

    public ArrayList<ActiveObject> playersUI = new ArrayList();

    public int point = 0;

    private Level _level;
    private Timer _timer;

    // private Bomb _bomb;
    private int bombcnt = 0;

    private static Clip musicClip;
    private JButton toggleMusicButton;
    private boolean musicPlaying = false;

    private ArrayList<Player> players = new ArrayList();
    private ArrayList<Monster> monsters = new ArrayList();
    private int monster_count = 3;

    private ArrayList<Bomb> DroppedBombs = new ArrayList();
    private ArrayList<Explosion> _explosions = new ArrayList();

    private boolean spaceButtonPressed;

    private Monster _monster;

    private Image[] loadPlayerFrames(String string, char c) {
        int numFrames = 4;
        Image[] frames = new Image[numFrames];
        int cnt = 0;
        for (int j = 0; j < numFrames; j++) {
            try {
                String filePath = "src/media/players/player" + c + "/" + string + "/frame-" + (j + 1) + ".png";
                System.out.println(filePath);
                frames[j] = ImageIO.read(new File(filePath));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return frames;
    }

    public Image[] loadMonsterFrames(String direction) {
        int numFrames = 12;
        Image[] frames = new Image[numFrames];
        int cnt = 0;
        for (int j = 0; j < numFrames; j++) {
            try {
                String filePath = "src/media/monster/" + direction + "/frame-" + (j + 1) + ".png";
                frames[cnt] = ImageIO.read(new File(filePath));
                cnt++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return frames;
    }

    public Image[] loadExplosionFrames() {
        int numFrames = 8;
        Image[] frames = new Image[numFrames * numFrames];
        int cnt = 0;
        for (int j = 0; j < numFrames; j++) {
            for (int i = 0; i < numFrames; i++) {
                try {
                    // System.out.println("src/media/explosions/row-" + (j+1) + "-column-" + (i+1) +
                    // ".png");
                    frames[cnt] = ImageIO
                            .read(new File("src/media/explosions/row-" + (j + 1) + "-column-" + (i + 1) + ".png"));
                    cnt++;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }

        return frames;
    }

    public void displayUIELements() {

    }

    public Engine(int player_count, int round_count, int id_level) throws IOException, JSONException {
        super();

        this.id_level = id_level;
        this.player_count = player_count;
        this.round_count = round_count;

        Image image1 = new ImageIcon("src/media/black.png").getImage();
        gameObj = new ActiveObject(760, 0, 250, 800, image1);

        _level = new Level("src/levels/level" + id_level + ".txt");
        String jsonData = new String(Files.readAllBytes(Paths.get("src/database/controls.json")));
        JSONObject obj = new JSONObject(jsonData);

        for (int i = 0; i < monster_count; i++) {
            generate_Monsters(_level);
        }
        int cc = 10;
        for (int i = 1; i <= player_count; i++) {

            Image playerImage = new ImageIcon("src/media/player" + i + ".png").getImage();
            playerObj = new ActiveObject(770, cc, 150, 100, playerImage);
            playersUI.add(playerObj);
            cc += 110;
            JSONObject playerControls = obj.getJSONObject("player" + i);
            String up = playerControls.getString("up");
            String down = playerControls.getString("down");
            String left = playerControls.getString("left");
            String right = playerControls.getString("right");
            String bomb = playerControls.getString("bomb");

            // Load player frames
            Image[] playerFramesUp = loadPlayerFrames("up", (char) ('0' + i));
            Image[] playerFramesDown = loadPlayerFrames("down", (char) ('0' + i));
            Image[] playerFramesLeft = loadPlayerFrames("left", (char) ('0' + i));
            Image[] playerFramesRight = loadPlayerFrames("right", (char) ('0' + i));

            int posX = 0;
            int posY = 0;
            if (i == 1) {
                posX = 40;
                posY = 40;
            } else if (i == 2) {
                posX = 40;
                posY = 680;
            } else if (i == 3) {
                posX = 680;
                posY = 680;
            }

            Player _player = new Player(posX, posY, 35, 35, playerFramesUp, playerFramesDown, playerFramesLeft,
                    playerFramesRight);
            players.add(_player);
            setControls(_player, up, down, left, right, bomb);
        }

        // restart_game();

        // ANIMATION

        // player animations are so fast that they are not visible
        // this is why we need to slow them down
        int frameRate = 25;
        int delay = 1000 / frameRate;
        _timer = new Timer(delay, new FrameUpdate(this));
        _timer.start();

        initMusic("src/sounds/music.wav", 0.05f);
        addToggleMusicButton();
        setLayout(null);

    }

    private void initMusic(String filePath, float volume) {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(audioFile);
            musicClip = AudioSystem.getClip();
            musicClip.open(audioIn);
            setVolume(volume); // Sets volume after opening the clip
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
            musicPlaying = true; // Assuming music starts playing automatically
        } catch (Exception e) {
            e.printStackTrace();
            musicPlaying = false;
        }
    }

    private void setVolume(float volume) {
        if (musicClip != null && musicClip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0); // Convert volume from linear scale to dB
            gainControl.setValue(dB);
        } else {
            System.out.println("Volume control not supported or clip not initialized.");
        }
    }

    private void addToggleMusicButton() {
        ImageIcon icon = new ImageIcon("src/media/sound.png");
        toggleMusicButton = new JButton(icon);
        toggleMusicButton.setBorderPainted(false); // No border painting
        toggleMusicButton.setContentAreaFilled(false); // No background fill
        toggleMusicButton.setFocusPainted(false); // No focus border
        toggleMusicButton.setOpaque(false);
        toggleMusicButton.addActionListener(e -> {
            new Thread(() -> {
                if (musicPlaying) {
                    musicClip.stop();
                    musicPlaying = false;
                } else {
                    musicClip.start();
                    musicClip.loop(Clip.LOOP_CONTINUOUSLY);
                    musicPlaying = true;
                }
                SwingUtilities.invokeLater(() -> {
                    Engine.this.requestFocus();
                });
            }).start();
        });

        // position and size of the button
        toggleMusicButton.setBounds(900, 650, 80, 80);
        add(toggleMusicButton); // Adds the button to the panel
    }

    private void playSound(String filePath) {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Error with playing sound.");
            e.printStackTrace();
        }
    }

    public void setControls(Player player, String up, String down, String left, String right, String bombdrop) {

        this.getInputMap().put(KeyStroke.getKeyStroke("pressed " + left), "pressed " + left);
        this.getActionMap().put("pressed " + left, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                player.set_x_speed(-r_movement);
                player.direction = Direction.LEFT;
            }
        });

        this.getInputMap().put(KeyStroke.getKeyStroke("released " + left), "released " + left);
        this.getActionMap().put("released " + left, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                player.set_x_speed(0);
                player.direction = null;
            }
        });

        // MOVE RIGHT
        this.getInputMap().put(KeyStroke.getKeyStroke("pressed " + right), "pressed " + right);
        this.getActionMap().put("pressed " + right, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                player.set_x_speed(+r_movement);
                player.direction = Direction.RIGHT;
            }
        });

        this.getInputMap().put(KeyStroke.getKeyStroke("released " + right), "released " + right);
        this.getActionMap().put("released " + right, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                player.set_x_speed(0);
                player.direction = null;
            }
        });
        // MOVE DOWN
        this.getInputMap().put(KeyStroke.getKeyStroke("pressed " + down), "pressed " + down);
        this.getActionMap().put("pressed " + down, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                player.set_y_speed(r_movement);
                player.direction = Direction.DOWN;
            }
        });

        this.getInputMap().put(KeyStroke.getKeyStroke("released " + down), "released " + down);
        this.getActionMap().put("released " + down, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                player.set_y_speed(0);
                player.direction = null;
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("pressed " + up), "pressed " + up);
        this.getActionMap().put("pressed " + up, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                player.set_y_speed(-r_movement);
                player.direction = Direction.UP;
            }
        });

        this.getInputMap().put(KeyStroke.getKeyStroke("released " + up), "released " + up);
        this.getActionMap().put("released " + up, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                player.set_y_speed(0);
                player.direction = null;
            }
        });

        this.getInputMap().put(KeyStroke.getKeyStroke(bombdrop), bombdrop);
        this.getActionMap().put(bombdrop, new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if (player.canDropbomb()) {
                    System.out.println("BOMB DROPPED");
                    Ground gr = player.whereAmI(_level);
                    Image img = new ImageIcon("src/media/Bomb.png").getImage();
                    Bomb bomb = new Bomb(gr.get_X(), gr.get_Y(), 30, 30, img, player); // Create a new Bomb object
                    // bomb = new Bomb(5, 5, 30, 30, img);
                    DroppedBombs.add(bomb);
                    player.DropBomb();
                } else {
                    System.out.println("You Ran out of BOMBS");
                }
            }
        });

    }

    public void restart_game() throws IOException {

        try {
            _level = new Level("src/levels/level" + id_level + ".txt");
        } catch (IOException e) {
            System.out.println("Level file not found");
            e.printStackTrace();
        }
        players.clear();
        monsters.clear();
        DroppedBombs.clear();
        _explosions.clear();
        round_count--;
        for (int i = 0; i < monster_count; i++) {
            generate_Monsters(_level);
        }
        for (int i = 1; i <= player_count; i++) {
            String jsonData = new String(Files.readAllBytes(Paths.get("src/database/controls.json")));
            JSONObject obj = new JSONObject(jsonData);
            JSONObject playerControls = obj.getJSONObject("player" + i);
            String up = playerControls.getString("up");
            String down = playerControls.getString("down");
            String left = playerControls.getString("left");
            String right = playerControls.getString("right");
            String bomb = playerControls.getString("bomb");

            // Load player frames
            Image[] playerFramesUp = loadPlayerFrames("up", (char) ('0' + i));
            Image[] playerFramesDown = loadPlayerFrames("down", (char) ('0' + i));
            Image[] playerFramesLeft = loadPlayerFrames("left", (char) ('0' + i));
            Image[] playerFramesRight = loadPlayerFrames("right", (char) ('0' + i));

            // Calculate initial positions based on player index (simplified version here)
            int posX = 40 + (i - 1) * 320; // Modify according to your game layout
            int posY = 40 + (i - 1) * 320; // Modify according to your game layout

            Player _player = new Player(posX, posY, 35, 35, playerFramesUp, playerFramesDown, playerFramesLeft,
                    playerFramesRight);
            players.add(_player);
            setControls(_player, up, down, left, right, bomb);
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
            if (tmp && tmp2) {
                Image[] monsterFramesUp = loadMonsterFrames("up");

                Image[] monsterFramesDown = loadMonsterFrames("down");
                Image[] monsterFramesLeft = loadMonsterFrames("left");
                Image[] monsterFramesRight = loadMonsterFrames("right");

                Monster _monster = new Monster(x, y, 40, 40, monsterFramesUp, monsterFramesDown, monsterFramesLeft,
                        monsterFramesRight);

                monsters.add(_monster);
                isfound = true;
            }

        }
    }

    @Override
    protected void paintComponent(Graphics grphcs) {

        super.paintComponent(grphcs);

        gameObj.drawObject(grphcs);

        // grphcs.drawImage(bg, 0, 0, 800, 800, null);
        _level.placeGrounds(grphcs);
        _level.placeWalls(grphcs);
        _level.placePowerups(grphcs);
        _level.placeBoxes(grphcs);

        for (int i = 0; i < players.size(); i++) {
            players.get(i).drawObject(grphcs);
        }

        for (int i = 0; i < monsters.size(); i++) {
            monsters.get(i).drawObject(grphcs);
        }

        for (int i = 0; i < DroppedBombs.size(); i++) {
            DroppedBombs.get(i).drawObject(grphcs);
        }

        for (int i = 0; i < playersUI.size(); i++) {
            playersUI.get(i).drawObject(grphcs);
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

                for (int q = 0; q < monsters.size(); q++) {

                    monsters.get(q).move();
                    for (int i = 0; i < players.size(); i++) {
                        if (monsters.get(q).did_hit(players.get(i))) {
                            // String user_name = JOptionPane.showInputDialog("MONSTER KILLED YOU!!!! " +
                            // (point) + " POINTS EARNED\n Please give your name:","");
                            players.remove(i);
                        }
                    }

                    for (int i = 0; i < DroppedBombs.size(); i++) {
                        if (monsters.get(q).did_hit(DroppedBombs.get(i))) {
                            monsters.get(q).ChangeDirection(_level);
                        }
                    }

                    if (_level.did_hit(monsters.get(q))) {
                        monsters.get(q).ChangeDirection(_level);
                    }

                    for (int i = 0; i < monsters.size(); i++) {
                        if (i != q) {
                            if (monsters.get(q).did_hit(monsters.get(i))) {
                                monsters.get(q).ChangeDirection(_level);
                            }
                        }
                    }

                }

                Iterator<Bomb> iterator = DroppedBombs.iterator();

                while (iterator.hasNext()) {
                    Bomb bomb = iterator.next();
                    long elapsedTime = System.currentTimeMillis() - bomb.timestamp;
                    if (elapsedTime >= 3000) { // 90 seconds in milliseconds
                        System.out.println(DroppedBombs.size());
                        // bomb.owner.canDropBomb = true; // Set canDropBomb back to true for the player who dropped the
                        //                                // bomb
                        bomb.owner.bombExploded();
                        Image[] explosionFrames = loadExplosionFrames();
                        _explosions.add(new Explosion(bomb._x, bomb._y, 40, 40, explosionFrames));
                        bomb.explode(_level.boxes, players, monsters, _level.walls, _explosions);
                        iterator.remove(); // Use the iterator's remove method
                    }
                }

                // System.out.println(tempcnt);
                for (int i = 0; i < players.size(); i++) {
                    if (_level.did_hit(players.get(i))) {
                        players.get(i).set_x_speed(0);
                        players.get(i).set_y_speed(0);
                    }
                    for (int j = 0; j < _level.powerups.size(); j++) {
                        if (players.get(i).did_hit(_level.powerups.get(j))) {

                            players.get(i).powerups.add(_level.powerups.get(j));
                            _level.powerups.remove(j);
                            playSound("src/sounds/boostUP.wav");
                            if (_level.powerups.get(j) instanceof MoreBombPowerup) {
                                players.get(i).increaseAllowedBombs(); 
                                System.out.println("Picked up a MoreBombPowerup!");
                            } if (_level.powerups.get(j) instanceof RangePowerup) {
                                System.out.println("Picked up a RangePowerup!");
                            } else {
                                System.out.println("Picked up a generic power-up!");
                            }
                        }
                    }
                    if (players != null && !players.isEmpty()) {
                        players.get(i).move();
                    }
                    // else if (round_count > 0) {
                    //
                    // JOptionPane.showMessageDialog(null,
                    // "GAME OVER rounds left: " + round_count + " points earned: " + point);
                    // try {
                    // restart_game();
                    // } catch (IOException ex) {
                    // Logger.getLogger(Engine.class.getName()).log(java.util.logging.Level.SEVERE,
                    // null, ex);
                    // }
                    // round_count--;
                    // } else if (round_count == 0) {
                    // JOptionPane.showMessageDialog(null, "GAME OVER points earned: NO MORE ROUNDS
                    // LEFT" + point);
                    // System.exit(0);
                    // }

                    for (Explosion explosion : _explosions) {
                        explosion.update();
                    }
                }

                if (round_count == 0) {
                    JOptionPane.showMessageDialog(null, "GAME OVER");
                    System.exit(0);
                } else if (players.size() == 0 && round_count > 0) {
                    JOptionPane.showMessageDialog(null, "Round OVER");
                    try {
                        restart_game();
                    } catch (IOException ex) {
                        Logger.getLogger(Engine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }
                }
                // if (players.get(0).powerups.size() >= 1){
                // System.out.println(players.get(0).powerups.size());
                // }
            }

            repaint();
        }

    }

}