package managers;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ApiManager {
    private static final String BASE_URL = "https://api.themoviedb.org/3";
    private static final String API_KEY = "a84e0b9280822246eae80170242fecd0";
    private static final Integer API_LIMIT = 10;
    
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
    
    public JSONArray getGenres()
    {
        JSONObject json = request("/genre/movie/list", new HashMap<>());
        return (JSONArray) json.get("genres");
    }
    
    public JSONObject getMoviesPage(int page)
    {
        Map<String,String> params = new HashMap<>();
        params.put("primary_release_year.gte", "2000");
        params.put("with_genres", "28|10749|878");
        params.put("page", Integer.toString(page));
        
        return request("/discover/movie", params);
    }
    
    public JSONArray getMovies()
    {
        JSONArray movies = new JSONArray();
        for(int i = 1;  i <= API_LIMIT; i++)
        {
            JSONObject page = getMoviesPage(i);
            movies.addAll((JSONArray) page.get("results"));
        }
        return movies;
    }
}
