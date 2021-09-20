

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainHandler {

    static final String TABLE_NAME = "Movies";
    static final String MOVIE_NAME = "moviename";
    static final String LEAD_ACTOR = "leadactor";
    static final String LEAD_ACTRESS = "leadactress";
    static final String YEAR_RELEASE = "yearofrelease";
    static final String DIRECTOR_NAME =  "directorname";


    public static void main(String[] args) {
        //inserting Movies
         //AddMovies();


        //getting all rows from Table
        List<MovieModel> data = getAllData();
        PrintTable(data);

        //Searching for Movie Names with actor
        String actor = "Zendaya";
        List<String> movies = getMoviesOfActor(actor);
        System.out.println("movies of "+actor+":");
        printList(movies);
        //Searching for Movie Names with actor
        String actor2 = "Robert Downey Jr.";
        List<String> movies2 = getMoviesOfActor(actor2);
        System.out.println("movies of "+actor2+":");
        printList(movies2);




    }
    private static  void printList(List<String> movies){
        int len = movies.size();
        if(len==0){
            System.out.println("NO MOVIES FOUND");
            return;
        }
        for(String movie:movies){
            System.out.println(movie);
        }
    }
    //Print Table
    private static  void PrintTable( List<MovieModel> data){
        System.out.println("");

        System.out.println("+------------------------+------------------------+------------------------+------------------------+------------------------+");
        System.out.println("|      MOVIE NAME        |       LEAD ACTOR       |      LEAD ACTRESS      |    YEAR OF RELEASE     |    DIRECTOR            |");
        System.out.println("       __________                __________              __________                __________            __________          ");
        for(MovieModel movieModel:data){
            System.out.println("| "+getFormat(movieModel.name)+" | "+getFormat(movieModel.leadActor)+" | "+ getFormat(movieModel.leadActress)+" | "+ getFormat(movieModel.year_released+"")+" | "+getFormat(movieModel.directorName)+" | ");

        }
        System.out.println("+------------------------+------------------------+------------------------+------------------------+------------------------+");

    }
    //get Movies based on actor
    private static List<String> getMoviesOfActor(String actor){
        String query = "SELECT "+MOVIE_NAME+" FROM "+TABLE_NAME+" WHERE "+LEAD_ACTOR+"="+"(?)"+"OR "+LEAD_ACTRESS+"=(?)";
        ResultSet  resultSet= null;
        Connection connection =  Connectiondb.connection();
        List<String> res = new ArrayList<>();
        try{
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,actor);
            ps.setString(2,actor);
            resultSet = ps.executeQuery();
            while (resultSet.next()){
                res.add(resultSet.getString(MOVIE_NAME));
            }

        }catch (SQLException e){
            System.out.println(e+"");
        }
        return res;
    }
    private static String getFormat(String s){
        String spaces = "                           ";
        int String_size = 22;
        int len = s.length();
        if(len==0)return "UNKNOWN"+spaces.substring(0,15);;
        int num_spaces = String_size-len;
        return  s+spaces.substring(0,num_spaces);

    }
    //ADD movies
    private static void AddMovies(){
        insert("spider man homecoming","Tom Holland","Zendaya",2017,"Jon Watts");
        insert("Black Panther","Chadwick Boseman","",2018, "Ryan Cooler");
        insert("iron man","Robert Downey Jr.","Gwyneth Paltrow",2008,"jon Favreau");
        insert("Venom","Tom Hardy","Michelle Williams",2018,"Ruben Fleischer");
        insert("Doctor Strange", "Benedict Cumberbatch", "Chiwetel Ejiofor",2016,"Scott Derrickson");

    }
    //inserting Data
    private  static  void insert(String name,String leadActor,String leadActress,int year_released,String directorName){
        Connection connection =  Connectiondb.connection();
        String insert_row = "INSERT INTO "+TABLE_NAME+
                               "( "+MOVIE_NAME+", "+LEAD_ACTOR+","+LEAD_ACTRESS+", "+YEAR_RELEASE+", "+DIRECTOR_NAME+" )"+
                                " VALUES (?,?,?,?,?) ";

        try{
            PreparedStatement query = connection.prepareStatement(insert_row);
            query.setString(1,name);
            query.setString(2,leadActor);
            query.setString(3,leadActress);
            query.setInt(4,year_released);
            query.setString(5,directorName);
            query.execute();

        }catch (SQLException e){
            System.out.println(e+"");
        }
    }
    //get All Data from Table
    private static List<MovieModel> getAllData(){
        Connection connection =  Connectiondb.connection();
        PreparedStatement ps = null;
        ResultSet  resultSet= null;
        String get_table = "SELECT * FROM "+TABLE_NAME;
        List<MovieModel> res = new ArrayList<>();
        try{
            ps = connection.prepareStatement(get_table);
            resultSet = ps.executeQuery();
            MovieModel movieModel;
            while(resultSet.next()){
                String name  = resultSet.getString(MOVIE_NAME);
                String leadActor = resultSet.getString(LEAD_ACTOR);
                String leadActress = resultSet.getString(LEAD_ACTRESS);
                int year_released = resultSet.getInt(YEAR_RELEASE);
                String directorName = resultSet.getString(DIRECTOR_NAME);
                 movieModel = new MovieModel(name,leadActor,leadActress,year_released,directorName);
                 res.add(movieModel);
            }

        }
        catch (SQLException e){
            System.out.println(e+"");
        }
        finally {
            try {

                connection.close();
                ps.close();
                resultSet.close();
            }catch (SQLException e){
                System.out.println(e+"");
            }
        }
        return res;
    }

    //Reset Database
    public static void DeleteAllRow(){
        Connection connection = Connectiondb.connection();
        String delete_query = "DELETE FROM "+TABLE_NAME;
        PreparedStatement ps = null;
       try{
           ps = connection.prepareStatement(delete_query);
           ps.execute();
       }catch (SQLException e){
            System.out.println("RESET ERROR"+e);
       }


    }
}
