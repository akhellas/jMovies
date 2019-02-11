package mymovies;

import managers.ApiManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class MyMovies {

    public static void main(String[] args) {

        ApiManager api = new ApiManager();
        
        JSONObject genres = api.getGenres();
        System.out.println(genres.toJSONString()); 
        
        JSONArray movies = api.getMovies();
        System.out.println(movies.toJSONString()); 
    }
    
}
