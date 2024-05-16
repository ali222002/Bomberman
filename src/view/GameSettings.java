package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
class GameSettings{
    
    JFrame frame;
    
    Container con;
    JPanel titleNamePanel, startButtonPanel, exitButtonPanel, settingsButtonPanel, levelEditorButtonPanel;
    JLabel titleNameLabel;
    Font titleFont = new Font("Arial", Font.BOLD, 90);
    Font normalFont = new Font("Arial", Font.BOLD, 28);
    JButton startButton, exitButton, settingsButton, levelEditorButton;
    
class ControlSettingsFrame extends JFrame {
    Font controlFont = new Font("Arial", Font.PLAIN, 18);
    GameSettings gameSettings; // Reference to the main game settings

     public ControlSettingsFrame(GameSettings parent) throws JSONException {
        this.gameSettings = parent;
        setTitle("Control Settings");
        setSize(600, 800);
        setLayout(new GridLayout(4, 1));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JSONObject controls = GameSettings.loadControls();
        addPlayerControls("Player 1", controls.getJSONObject("player1"));
        addPlayerControls("Player 2", controls.getJSONObject("player2"));
        addPlayerControls("Player 3", controls.getJSONObject("player3"));

        JButton goBackButton = new JButton("Go Back");
        goBackButton.setFont(controlFont);
        goBackButton.addActionListener(e -> {
            GameSettings.saveControls(controls); // Save when going back
            this.dispose();
            gameSettings.frame.setVisible(true);
        });
        add(goBackButton);

        setVisible(true);
    }

    private void addPlayerControls(String playerName, JSONObject playerControls) throws JSONException {
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new GridLayout(5, 1));
        playerPanel.setBorder(BorderFactory.createTitledBorder(playerName));

        // Control descriptions
        String[] actions = {"up", "down", "left", "right", "bomb"};
        String[] descriptions = {"Up - ", "Down - ", "Left - ", "Right - ", "Drop Bomb - "};
        for (int i = 0; i < actions.length; i++) {
            String action = actions[i];
            JButton button = new JButton(descriptions[i] + playerControls.getString(action));
            button.setFont(controlFont);
            int x = i;
            button.addActionListener(e -> {
                String newKey = JOptionPane.showInputDialog(this, "Enter new key for " + descriptions[x].trim() + ":");
                if (newKey != null && !newKey.isEmpty()) {
                    try {
                        playerControls.put(action, newKey.toUpperCase());
                    } catch (JSONException ex) {
                        Logger.getLogger(GameSettings.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    button.setText(descriptions[x] + newKey.toUpperCase());
                }
            });

            playerPanel.add(button);
        }

        add(playerPanel);
    }

}
        public static JSONObject loadControls() {
        try {
            String content = new String(Files.readAllBytes(Paths.get("src/database/controls.json")));
            return new JSONObject(content);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void saveControls(JSONObject controls) {
        try {
            Files.write(Paths.get("src/database/controls.json"), controls.toString(4).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public GameSettings() {        
        frame = new JFrame("BOMBERMAN");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        frame.getContentPane().setBackground(new Color(27, 102, 49));
        frame.setLayout(null);
        frame.setVisible(true);

        con = frame.getContentPane();

        titleNamePanel = new JPanel();
        titleNamePanel.setBounds(100, 100, 800, 200);
        titleNamePanel.setBackground(new Color(27, 102, 49));
        titleNameLabel = new JLabel("BOMBERMAN");
        titleNameLabel.setForeground(Color.black);
        titleNameLabel.setFont(titleFont);

        // start button

        startButtonPanel = new JPanel();
        startButtonPanel.setBounds(400, 400, 200, 100);
        startButtonPanel.setBackground(new Color(27, 102, 49));

        startButton = new JButton("controls");
        startButton.setBackground(Color.black);
        startButton.setForeground(Color.white);
        startButton.setFont(normalFont);
        startButton.setFocusPainted(false);
        
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    new ControlSettingsFrame(GameSettings.this);
                } catch (JSONException ex) {
                    Logger.getLogger(GameSettings.class.getName()).log(Level.SEVERE, null, ex);
                }
                frame.dispose(); 
            }
        });
        
        //Settings
        settingsButtonPanel = new JPanel();
        settingsButtonPanel.setBounds(400, 600, 200, 100);
        settingsButtonPanel.setBackground(new Color(27, 102, 49));

        settingsButton = new JButton("sound");
        settingsButton.setBackground(Color.black);
        settingsButton.setForeground(Color.white);
        settingsButton.setFont(normalFont);
        settingsButton.setFocusPainted(false);
        
        settingsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //System.out.println("sljdahnkj");
                GameSettings start = new GameSettings();
                frame.dispose(); 
            }
        });
        
       // level editor butrton
       levelEditorButtonPanel = new JPanel();
       levelEditorButtonPanel.setBounds(400, 500, 200, 100);
       levelEditorButtonPanel.setBackground(new Color(27, 102, 49));

       levelEditorButton = new JButton("level editor");
       levelEditorButton.setBackground(Color.black);
       levelEditorButton.setForeground(Color.white);
       levelEditorButton.setFont(normalFont);
       levelEditorButton.setFocusPainted(false);

        levelEditorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MapSelector start = new MapSelector();
                frame.dispose();
            }
        });

        // Exit button

        exitButtonPanel = new JPanel();
        exitButtonPanel.setBounds(400, 700, 200, 100);
        exitButtonPanel.setBackground(new Color(27, 102, 49));

        exitButton = new JButton("back");
        exitButton.setBackground(Color.black);
        exitButton.setForeground(Color.white);
        exitButton.setFont(normalFont);
        exitButton.setFocusPainted(false);

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UI start = new UI();
                frame.dispose();
            }
        });
        
        

        
        titleNamePanel.add(titleNameLabel);
        startButtonPanel.add(startButton);
        exitButtonPanel.add(exitButton);
        //settingsButtonPanel.add(settingsButton);
        levelEditorButtonPanel.add(levelEditorButton);

        con.add(titleNamePanel);
        con.add(startButtonPanel);
        con.add(exitButtonPanel);
        //con.add(settingsButtonPanel);
        con.add(levelEditorButtonPanel);
        
        
        
        
        
        frame.setPreferredSize(new Dimension(1000, 800));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
    
    
    
        public static void main(String[] args) {
        new GameSettings();
    }
   
}