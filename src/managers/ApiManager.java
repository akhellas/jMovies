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

// Κλάση για τη διαχείριση των κλήσεων προς το API του themoviedb.org
public final class ApiManager {

    private static final String BASE_URL = "https://api.themoviedb.org/3";
    private static final String API_KEY = "a84e0b9280822246eae80170242fecd0";
    // ΠΑΡΑΔΟΧΗ: επειδή το API μας κάνει timeout, βάλαμε αυθαίρετα το όριο των 39 σελίδων αποτελεσμάτων
    private static final Integer API_LIMIT = 39; 

    // μέθοδος που "χτίζει" το url για τις κλήσεις στο API ανάλογα με τα key-value pairs παραμέτρων που της περνάμε
    private static String buildUrlString(String path, Map<String, String> parameters) {
        String query = BASE_URL + path + "?api_key=" + API_KEY;
        query = parameters.entrySet()
                .stream()
                .map((entry) -> "&" + entry.getKey() + "=" + entry.getValue())
                .reduce(query, String::concat);

        return query;
    }

    // ο κώδικας για την εκτέλεση της κλήσης είναι πανομοιότυπος και δεν χρειάζεται να επαναλαμβάνεται σε κάθε μέθοδο
    private static JSONObject request(String path, Map<String, String> parameters) throws IOException, ParseException {
        URL url = new URL(buildUrlString(path, parameters));

        InputStream stream = url.openStream();
        InputStreamReader reader = new InputStreamReader(stream);
        JSONParser parser = new JSONParser();

        JSONObject json = (JSONObject) parser.parse(reader);

        return json;
    }

    // επιστρέφει τα είδη ταινιών
    public static JSONArray getGenres() throws IOException, ParseException {
        JSONObject json = request("/genre/movie/list", new HashMap<>());
        return (JSONArray) json.get("genres");
    }

    // μέθοδος που επιστρέφει μια σελίδα με ταινίες με τα συγκεκριμένα κριτήρια
    public static JSONObject getMoviesPage(int page) throws IOException, ParseException {
        Map<String, String> params = new HashMap<>();
        params.put("primary_release_year.gte", "2000");
        params.put("with_genres", "28|10749|878");
        params.put("page", Integer.toString(page));

        return request("/discover/movie", params);
    }

    // μέθοδος για την επιστροφή όλων των σελίδων ταινιών με τα συγκεκριμένα κριτήρια
    public static JSONArray getMovies() throws IOException, ParseException {
        JSONArray movies = new JSONArray();
        for (int i = 1; i <= API_LIMIT; i++) {
            JSONObject page = getMoviesPage(i);
            movies.addAll((JSONArray) page.get("results"));
        }
        return movies;
    }
}
