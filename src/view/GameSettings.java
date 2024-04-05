package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class GameSettings{
    
    JFrame frame;
    
    Container con;
    JPanel titleNamePanel, startButtonPanel, exitButtonPanel, settingsButtonPanel;
    JLabel titleNameLabel;
    Font titleFont = new Font("Arial", Font.BOLD, 90);
    Font normalFont = new Font("Arial", Font.BOLD, 28);
    JButton startButton, exitButton, settingsButton;
    
    
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
                //System.out.println("sljdahnkj");
                
                frame.dispose(); 
            }
        });
        
        //Settings
        settingsButtonPanel = new JPanel();
        settingsButtonPanel.setBounds(400, 500, 200, 100);
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

        // Exit button

        exitButtonPanel = new JPanel();
        exitButtonPanel.setBounds(400, 600, 200, 100);
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
        settingsButtonPanel.add(settingsButton);

        con.add(titleNamePanel);
        con.add(startButtonPanel);
        con.add(exitButtonPanel);
        con.add(settingsButtonPanel);

        
        
        
        
        frame.setPreferredSize(new Dimension(1000, 800));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
   
}