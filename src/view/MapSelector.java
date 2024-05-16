package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FilenameFilter;

public class MapSelector extends JFrame {
    public MapSelector() {
        
        this.setTitle("BOMBERMAN");
        this.setSize(1000, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(new Color(27, 102, 49));
        this.setLayout(new GridLayout(3, 1));

        // Create the buttons
        JButton newMapButton = new JButton("New Map");
        newMapButton.addActionListener(e -> new LevelEditor());
        JButton modifyMapButton = new JButton("Modify Map");
        
        File dir = new File("src/levels");
        File[] files = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".txt");
            }
        });

        // Create a button for each map file
        for (File file : files) {
            String str = file.getName();
            str = str.replace(".txt", "");
            String str2 = str.replace("level", "");
            JButton mapButton = new JButton("map " + str2);
            mapButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //System.out.println("sljdahnkj");
                LevelEditor start = new LevelEditor(file.getPath());
                MapSelector.this.dispose(); 
            }
        });

            this.add(mapButton);
        }
       
        JButton exitButton = new JButton("Back");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //System.out.println("sljdahnkj");
                GameSettings start = new GameSettings();
                MapSelector.this.dispose(); 
            }
        });

        // Add the buttons to the frame
        this.add(newMapButton);
        this.add(exitButton);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new MapSelector();
    }
}