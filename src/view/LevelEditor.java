package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class LevelEditor extends JFrame {
    String str2 = "not workn";
    private int num;
    private JPanel grid;
    private String selectedElement = "Empty";
    public LevelEditor() {
        this.setTitle("BOMBERMAN");
    this.setSize(1000, 800);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.getContentPane().setBackground(new Color(27, 102, 49));
    this.setLayout(new BorderLayout());

    // Create the grid
    grid = new JPanel(new GridLayout(19, 19));
    grid.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add 10-pixel padding
    grid.setOpaque(false); // Make the grid panel transparent
    for (int i = 0; i < 19; i++) {
        for (int j = 0; j < 19; j++) {
            JPanel cell = new JPanel();
            cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            if (i == 0 || i == 18 || j == 0 || j == 18) { // First or last row or column
                cell.setBackground(Color.DARK_GRAY); // Wall
            } else {
                cell.setBackground(Color.GREEN); // Ground
                cell.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        if (selectedElement.equals("Wall")) {
                            cell.setBackground(Color.DARK_GRAY);
                        } else if (selectedElement.equals("Box")) {
                            cell.setBackground(Color.GRAY);
                        } else {
                            cell.setBackground(Color.GREEN);
                        }
                    }
                });
            }
            grid.add(cell);
        }
    }
    
        File dir = new File("src/levels");
        File[] files = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".txt");
            }
        });

        // CString str2reate a button for each map file
        for (File file : files) {
            String str = file.getName();
            str = str.replace(".txt", "");
            str2 = str.replace("level", "");
            num = Integer.valueOf(str2);
        }
        // Create the palette
        JPanel palette = new JPanel(new GridLayout(4, 1));
        palette.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add 10-pixel padding
        palette.setOpaque(false); // Make the palette panel transparent
        JButton wallButton = new JButton("Wall");
        wallButton.addActionListener(e -> selectedElement = "Wall");
        JButton boxButton = new JButton("Box");
        boxButton.addActionListener(e -> selectedElement = "Box");
        JButton emptyButton = new JButton("Empty");
        emptyButton.addActionListener(e -> selectedElement = "Ground");
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            num = num + 1;
            try (PrintWriter writer = new PrintWriter(new File("src/levels/level"+ num +".txt"))) {
                for (int i = 0; i < grid.getComponentCount(); i++) {
                    JPanel cell = (JPanel) grid.getComponent(i);
                    if (cell.getBackground() == Color.DARK_GRAY) {
                        writer.print('1');
                    } else if (cell.getBackground() == Color.GRAY) {
                        writer.print('2');
                    } else {
                        writer.print('3');
                    }
                    if ((i + 1) % 19 == 0) { // End of a row
                        writer.println();
                    }
                }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            System.out.println("Save button clicked");
        });
        palette.add(wallButton);
        palette.add(boxButton);
        palette.add(emptyButton);
        palette.add(saveButton);

        // Add the grid and palette to the frame
        this.add(grid, BorderLayout.CENTER);
        this.add(palette, BorderLayout.EAST);
        this.setVisible(true);
    }
    public LevelEditor(String mapFile) {
        this.setTitle("BOMBERMAN");
        this.setSize(1000, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(new Color(27, 102, 49));
        this.setLayout(new BorderLayout());

        // Create the grid
        grid = new JPanel(new GridLayout(19, 19));
        grid.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add 10-pixel padding
        grid.setOpaque(false); // Make the grid panel transparent

        // Read the map from the text file
                // Read the map from the text file
        try (Scanner scanner = new Scanner(new File(mapFile))) {
            for (int i = 0; i < 19; i++) {
                String line = scanner.nextLine();
                for (int j = 0; j < 19; j++) {
                    JPanel cell = new JPanel();
                    cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    switch (line.charAt(j)) {
                        case '1':
                            cell.setBackground(Color.DARK_GRAY); // Wall
                            break;
                        case '2':
                            cell.setBackground(Color.GRAY); // Box
                            break;
                        case '3':
                            cell.setBackground(Color.GREEN); // Ground
                            break;
                    }
                    if (!(i == 0 || i == 18 || j == 0 || j == 18)) { // Not in the first or last row or column
                        cell.addMouseListener(new MouseAdapter() {
                            public void mouseClicked(MouseEvent e) {
                                if (selectedElement.equals("Wall")) {
                                    cell.setBackground(Color.DARK_GRAY);
                                } else if (selectedElement.equals("Box")) {
                                    cell.setBackground(Color.GRAY);
                                } else {
                                    cell.setBackground(Color.GREEN);
                                }
                            }
                        });
                    }
                    grid.add(cell);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Create the palette
        JPanel palette = new JPanel(new GridLayout(4, 1));
        palette.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add 10-pixel padding
        palette.setOpaque(false); // Make the palette panel transparent
        JButton wallButton = new JButton("Wall");
        wallButton.addActionListener(e -> selectedElement = "Wall");
        JButton boxButton = new JButton("Box");
        boxButton.addActionListener(e -> selectedElement = "Box");
        JButton emptyButton = new JButton("Empty");
        emptyButton.addActionListener(e -> selectedElement = "Ground");
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            try (PrintWriter writer = new PrintWriter(new File(mapFile))) {
                for (int i = 0; i < grid.getComponentCount(); i++) {
                    JPanel cell = (JPanel) grid.getComponent(i);
                    if (cell.getBackground() == Color.DARK_GRAY) {
                        writer.print('1');
                    } else if (cell.getBackground() == Color.GRAY) {
                        writer.print('2');
                    } else {
                        writer.print('3');
                    }
                    if ((i + 1) % 19 == 0) { // End of a row
                        writer.println();
                    }
                }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            System.out.println("Save button clicked");
        });
        palette.add(wallButton);
        palette.add(boxButton);
        palette.add(emptyButton);
        palette.add(saveButton);

        // Add the grid and palette to the frame
        this.add(grid, BorderLayout.CENTER);
        this.add(palette, BorderLayout.EAST);
        this.setVisible(true);
    }

    public static void main(String[] args) {
//        new LevelEditor("path_to_your_map_file.txt");
    }
}