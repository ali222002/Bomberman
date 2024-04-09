
package view;

import model.Engine;
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

class StartGame {
        JFrame frame;

        Container con;
        Engine map;
        JPanel titleNamePanel, startButtonPanel, exitButtonPanel, settingsButtonPanel;
        JLabel titleNameLabel;
        Font titleFont = new Font("Arial", Font.BOLD, 90);
        Font normalFont = new Font("Arial", Font.BOLD, 28);
        JButton startButton, exitButton, settingsButton;
    public StartGame() {        
        frame = new JFrame("BOMBERMAN");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Engine map = new Engine(3, 2);
        frame.getContentPane().add(map);
        
        
        
        
        
        frame.setPreferredSize(new Dimension(1000, 800));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}
