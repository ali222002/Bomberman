
package view;

import model.Engine;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.json.JSONException;

class StartGame {
        JFrame frame;

        Container con;
        Engine map;
        JPanel titleNamePanel, startButtonPanel, exitButtonPanel, settingsButtonPanel;
        JLabel titleNameLabel;
        Font titleFont = new Font("Arial", Font.BOLD, 90);
        Font normalFont = new Font("Arial", Font.BOLD, 28);
        JButton startButton, exitButton, settingsButton;
        
    public StartGame(int playerCNT, int roundCNT, int mapId, int monstercnt) throws IOException, JSONException {        
        frame = new JFrame("BOMBERMAN");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Engine map = new Engine(playerCNT, roundCNT, mapId, monstercnt);
        frame.getContentPane().add(map);
        
        
        
        
        
        frame.setPreferredSize(new Dimension(1000, 800));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}
