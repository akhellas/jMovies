package mymovies;

import java.util.HashMap;
import managers.ApiManager;
import org.json.simple.JSONObject;

public class MyMovies {

    public static void main(String[] args) {

        ApiManager api = new ApiManager();
        
        JSONObject genres = api.getGenres();
        System.out.println(genres.toJSONString()); 
        
        JSONObject movies = api.getMovies();
        System.out.println(movies.toJSONString()); 
    }
    
}
