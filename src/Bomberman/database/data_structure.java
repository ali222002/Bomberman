package bomberman.database;

//HELPER CLASS TO EASILY REPRESENT HOW OUR DATA STRUCTURE
public class data_structure {
    
    //DECLATARIONS
                private String username;
                private int score ;
    //CONSTRUCCTOR
    
            public data_structure(String name ,int point){
                this.username = name;
                this.score = point;
            }
    
    // GETTERS
            public int getPoints(){

                return  this.score;
            }
            public String getName(){

                return  this.username;
            }
}
