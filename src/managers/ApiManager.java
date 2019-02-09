package managers;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ApiManager {
    private static final String BASE_URL = "https://api.themoviedb.org/3";
    private static final String API_KEY = "9bf6edc1bede20b62ad6955aa954f1d0";
    
    private String buildUrlString(String path, Map<String, String> parameters)
    {
        String query = BASE_URL + path + "?api_key=" + API_KEY;
        query = parameters.entrySet()
                          .stream()
                          .map((entry) -> "&" + entry.getKey() + "=" + entry.getValue())
                          .reduce(query, String::concat);
        
        return query;
    }
    
    private JSONObject request(String path, Map<String, String> parameters)
    {
        try
        {
            URL url = new URL(buildUrlString(path, parameters));
            
            InputStream stream = url.openStream();
            InputStreamReader reader = new InputStreamReader(stream);
            JSONParser parser = new JSONParser();
            
            JSONObject json = (JSONObject) parser.parse(reader);
            
            return json;
        }
        catch (IOException | ParseException exception)
        {
            // TODO: add separate catches, add error messages
            System.out.println(exception);
            return null;
        }
    }
    
    public JSONObject getGenres()
    {
        return request("/genre/movie/list", new HashMap<>());
    }
    
    public JSONObject getMoviesPage(int page)
    {
        Map<String,String> params = new HashMap<>();
        params.put("primary_release_year.gte", "2000");
        params.put("with_genres", "28|10749|878");
        params.put("page", Integer.toString(page));
        
        return request("/discover/movie", params);
    }
    
    public JSONObject getMovies()
    {
        JSONObject movies = new JSONObject();
        for(int i = 1;  i < 40; i++)
        {
            movies.put(i, getMoviesPage(i));
        }
        return movies;
    }
}
