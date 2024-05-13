package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


class GameSetup {
    private JFrame frame;
    private JLabel titleNameLabel, title_mapNameLabel, title_playerNameLabel, title_rNameLabel;
    private JPanel titleNamePanel, title_mapNamePanel, title_playerNamePanel, title_rNamePanel, mapSelectorPanel, playerSelectorPanel, roundsSelectorPanel, backButtonPanel, playButtonPanel;
    private JComboBox<String> mapSelector;
    private JComboBox<Integer> playerSelector;
    private JComboBox<Integer> roundsSelector;
    private JButton backButton, playButton;
    private final Font title1Font = new Font("Arial", Font.BOLD, 70);
    private final Font titleFont = new Font("Arial", Font.BOLD, 28);
    private final Font normalFont = new Font("Arial", Font.BOLD, 20);
    private final Color backgroundColor = new Color(27, 102, 49);
    private final Color backgroundColor2 = new Color(27, 102, 49);
    public GameSetup() {
        // Frame setup
        frame = new JFrame("BOMBERMAN");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(backgroundColor2);
        frame.setLayout(null);
        frame.setSize(new Dimension(1000, 800));
        
        // Title
        titleNamePanel = new JPanel();
        titleNamePanel.setBounds(100, 100, 800, 100);
        titleNamePanel.setBackground(backgroundColor);
        titleNameLabel = new JLabel("GAME SETUP");
        titleNameLabel.setForeground(Color.black);
        titleNameLabel.setFont(title1Font);
        
        
        
        // Map Selector
        title_mapNamePanel = new JPanel();
        title_mapNamePanel.setBounds(200, 250, 200, 50);
        title_mapNamePanel.setBackground(backgroundColor);
        title_mapNameLabel = new JLabel("select map:");
        title_mapNameLabel.setForeground(Color.black);
        title_mapNameLabel.setFont(titleFont);
        
        String[] maps = {"1", "2", "3", "4"};
        mapSelector = new JComboBox<>(maps);
        mapSelector.setFont(normalFont);
        mapSelectorPanel = new JPanel();
        mapSelectorPanel.setBounds(400, 250, 200, 50);
        mapSelectorPanel.setBackground(backgroundColor);
        mapSelectorPanel.add(mapSelector);

        // Player Selector
        title_playerNamePanel = new JPanel();
        title_playerNamePanel.setBounds(200, 350, 200, 50);
        title_playerNamePanel.setBackground(backgroundColor);
        title_playerNameLabel = new JLabel("players count:");
        title_playerNameLabel.setForeground(Color.black);
        title_playerNameLabel.setFont(titleFont);
        
        Integer[] playerNumbers = {2, 3};
        playerSelector = new JComboBox<>(playerNumbers);
        playerSelector.setFont(normalFont);
        playerSelectorPanel = new JPanel();
        playerSelectorPanel.setBounds(400, 350, 200, 50);
        playerSelectorPanel.setBackground(backgroundColor);
        playerSelectorPanel.add(playerSelector);

        // Rounds Selector
        title_rNamePanel = new JPanel();
        title_rNamePanel.setBounds(200, 450, 200, 50);
        title_rNamePanel.setBackground(backgroundColor);
        title_rNameLabel = new JLabel("win round:");
        title_rNameLabel.setForeground(Color.black);
        title_rNameLabel.setFont(titleFont);
        
        
        Integer[] roundNumbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        roundsSelector = new JComboBox<>(roundNumbers);
        roundsSelector.setFont(normalFont);
        roundsSelectorPanel = new JPanel();
        roundsSelectorPanel.setBounds(400, 450, 200, 50);
        roundsSelectorPanel.setBackground(backgroundColor);
        roundsSelectorPanel.add(roundsSelector);
        
        
        playButton = new JButton("Play");
        playButton.setBackground(Color.black);
        playButton.setForeground(Color.white);
        playButton.setFont(normalFont);
        playButton.setFocusPainted(false);
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int playerCount = (Integer) playerSelector.getSelectedItem();
                int roundCount = (Integer) roundsSelector.getSelectedItem();
                String m = mapSelector.getSelectedItem().toString();
                try {
                    StartGame startgame = new StartGame(playerCount, roundCount-1, Integer.parseInt(m));
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                frame.dispose();
                
            }
        });
        playButtonPanel = new JPanel();
        playButtonPanel.setBounds(400, 550, 200, 100);
        playButtonPanel.setBackground(backgroundColor);
        playButtonPanel.add(playButton);
        
        // Play Button
        backButton = new JButton("Back");
        backButton.setBackground(Color.black);
        backButton.setForeground(Color.white);
        backButton.setFont(normalFont);
        backButton.setFocusPainted(false);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UI ui = new UI();
                frame.dispose();
                
            }
        });
        backButtonPanel = new JPanel();
        backButtonPanel.setBounds(400, 650, 200, 100);
        backButtonPanel.setBackground(backgroundColor);
        backButtonPanel.add(backButton);
        

        // Adding components to frame
        title_playerNamePanel.add(title_playerNameLabel);
        frame.add(title_playerNamePanel);
        titleNamePanel.add(titleNameLabel);
        frame.add(titleNamePanel);
        title_mapNamePanel.add(title_mapNameLabel);
        title_rNamePanel.add(title_rNameLabel);
        frame.add(title_rNamePanel);
        frame.add (title_mapNamePanel);
        frame.add(mapSelectorPanel);
        frame.add(playerSelectorPanel);
        frame.add(roundsSelectorPanel);
        frame.add(playButtonPanel);
        frame.add(backButtonPanel);
        
        frame.setResizable(false);
        frame.setVisible(true);
    }
    
}
