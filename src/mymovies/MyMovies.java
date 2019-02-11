package mymovies;

import java.util.List;
import javax.persistence.EntityTransaction;
import managers.ApiManager;
import managers.DbManager;
import managers.JsonDeserializer;
import model.Genre;
import model.Movie;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class MyMovies {

    public static void main(String[] args) {

        ApiManager api = new ApiManager();
        
        JSONArray genresJson = api.getGenres();
        System.out.println(genresJson.toJSONString());
        
        List<Genre> genres = JsonDeserializer.genresFromJson(genresJson);
        System.out.println(genres.get(0).toString());
        
        JSONArray moviesJson = api.getMovies();
        
        List<Movie> movies = JsonDeserializer.moviesFromJson(moviesJson, genres);
        System.out.println(movies.get(0).toString());
        
        
        DbManager.initializeDb();
        
        System.out.println("init finished");
        EntityTransaction tx = DbManager.getManager().getTransaction();
        
        tx.begin();
        
        genres.stream().filter(g -> JsonDeserializer.isAcceptable(g.getId())).forEach(genre -> DbManager.getManager().persist(genre));
        movies.forEach(movie -> DbManager.getManager().persist(movie));
        
        tx.commit();
    }
    
}
