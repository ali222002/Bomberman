package Bomberman;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import bomberman.database.DB;
import bomberman.database.data_structure;

class Interface {
        //ATTRIBUTES
       private JFrame game_frame;
       private Engine map;
       //constructor for interface of the game
       public Interface() {
       //game frame
        game_frame = new JFrame("THE MAZE GAME");
        map = new Engine();
        
        game_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game_frame.getContentPane().add(map);
        game_frame.setPreferredSize(new Dimension(800, 800));

        //menu 
        JMenuBar menuBar = new JMenuBar();
        JMenu m_game = new JMenu("Game");
        JMenuItem m_new = new JMenuItem("Restart");
        
        game_frame.setJMenuBar(menuBar);
        menuBar.add(m_game);
        m_game.add(m_new);
        
        m_new.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                map.id_level = 1;
                map.point = 0;
                map.restart_game();

            }
        });
        
       
        JMenuItem Ranking = new JMenuItem("Ranking");
        m_game.add(Ranking);
       
        Ranking.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                try{
                    
                    DB db = new DB();
                    
                    ArrayList<data_structure> Points = db.getHighScores();
                    
                    StringBuilder content = new StringBuilder("Ranking\n");
                    for (int i = 0; i < Points.size(); i++) {
                        if (i < 10) {
                            content.append(i + 1).append(". Name: ").append(Points.get(i).getName())
                                   .append("  Point: ").append(Points.get(i).getPoints()).append("\n");
                        }
                    }
                    JOptionPane.showMessageDialog(map, content, "Best Players", JOptionPane.PLAIN_MESSAGE);
                    
                }
                catch(SQLException ex){Logger.getLogger(DB.class.getName()); }

            }
            }); 
            
        
        
        game_frame.setResizable(false);
        game_frame.pack();
        game_frame.setVisible(true);
    
}
       
}
