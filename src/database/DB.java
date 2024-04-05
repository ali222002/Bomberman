package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Properties;

public class DB {

    int maxScores;
    PreparedStatement insertStatement;
    PreparedStatement deleteStatement;
    Connection connection;

    public DB() throws SQLException {
        //SETUP OF THE DB CONNECTION
        this.maxScores = 10;
        Properties connectionProps = new Properties();
        connectionProps.put("user", "root");
        connectionProps.put("password", "root");
        connectionProps.put("serverTimezone", "UTC");
        String dbURL = "jdbc:mysql://localhost:3306/themaze";
        connection = DriverManager.getConnection(dbURL, connectionProps);
        //CREATING PLAYERS TABLE IN DB
        String insertQuery = "INSERT INTO PLAYERS (USERNAME, SCORE) VALUES (?, ?)";
        insertStatement = connection.prepareStatement(insertQuery);
        
        String deleteQuery = "DELETE FROM PLAYERS WHERE SCORE=?";
        deleteStatement = connection.prepareStatement(deleteQuery);
         
    }
   //RETURNS ALL POINTS FROM TABLE IN OUR DATABASE
    public ArrayList<data_structure> getHighScores() throws SQLException {
        String query = "SELECT * FROM PLAYERS";
        ArrayList<data_structure> Points = new ArrayList<>();
        Statement SQL_statement = connection.createStatement();
        ResultSet results = SQL_statement.executeQuery(query);
        while (results.next()) {
            String name = results.getString("USERNAME");
            int point = results.getInt("SCORE");
            
            Points.add(new data_structure(name, point));
        }
        sortHighScores(Points);
        return Points;
    }
    //SORTING HIGHSCORES
      private void sortHighScores(ArrayList<data_structure> Points) {
        Collections.sort(Points, new Comparator<data_structure>() {
 
            @Override
            
            public int compare(data_structure t, data_structure t1) {
                return t1.getPoints() - t.getPoints();
            }
        });
    }
      
      
      
      public void insertScore(String name ,int point) throws SQLException {

        insertStatement.setString(1, name);
        insertStatement.setInt(2, point);
        insertStatement.executeUpdate();
    }
      
      private void deleteScores(int score) throws SQLException {
        deleteStatement.setInt(1, score);
        deleteStatement.executeUpdate();
    }
      
     public void putHighScore(String name, int score) throws SQLException {
        ArrayList<data_structure> highScores = getHighScores();
        if (highScores.size() < maxScores) {
            insertScore(name, score);
        } else {
            int leastScore = highScores.get(highScores.size() - 1).getPoints();
            if (leastScore < score) {
                deleteScores(leastScore);
                insertScore(name, score);
            }
        }
    }
}




