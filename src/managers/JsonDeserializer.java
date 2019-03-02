package managers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import model.Genre;
import model.Movie;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


// Κλάση που χρησιμοποιούμε για να μετατρέψουμε τα αποτελέσματα
// του API που είναι σε JSON μορφή στα JPA models που χρησιμοποιούμε
// στην εφαρμογή
public final class JsonDeserializer {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final int[] ACCEPTABLE_GENRES = {28, 10749, 878};

    public static Boolean isAcceptable(Object genreId) {
        return Arrays.stream(ACCEPTABLE_GENRES).anyMatch(x -> x == parseInt(genreId));
    }

    private static Integer parseInt(Object value) {
        if (value.getClass().getName().equals("java.lang.Integer")) {
            return (int) value;
        }
        return (int) (long) value;
    }

    private static String parseString(Object value) {
        return (String) value;
    }

    private static Float parseFloat(Object value) {
        if (value.getClass().getName().equals("java.lang.Long")) {
            long lv = (long) value;
            float f = lv;
            return f;
        }

        return (float) (double) value;
    }

    private static Date parseDate(Object json) {
        try {
            return DATE_FORMAT.parse((String) json);
        } catch (ParseException ex) {
            return null;
        }
    }

    public static Genre genreFromJson(JSONObject json) {
        return new Genre(parseInt(json.get("id")), parseString(json.get("name")));
    }

    public static List<Genre> genresFromJson(JSONArray array) {
        return (List<Genre>) array.stream()
                .map(json -> genreFromJson((JSONObject) json))
                .collect(Collectors.toList());
    }

    public static Movie movieFromJson(JSONObject json, List<Genre> genres) {
        Movie movie = new Movie(parseInt(json.get("id")), parseString(json.get("title")), parseFloat(json.get("vote_average")));
        
        Date releaseDate = parseDate(json.get("release_date"));
        if (releaseDate != null) {
            movie.setReleaseDate(releaseDate);
        }
        
        JSONArray genreIds = (JSONArray) json.get("genre_ids");
        
        // βρίσκουμε το πρώτο genre από τη λίστα των genre_ids που περιέχει το json
        // το οποίο να ανήκει στα αποδεκτά από τις απαιτήσεις της εργασίας genres
        int gid = parseInt(genreIds.stream().filter(g -> isAcceptable(g)).findFirst().get());
        movie.setGenreId(genres.stream().filter(g -> g.getId() == gid).findFirst().get());

        String overview = parseString(json.get("overview"));
        // φροντίζουμε το overview να μην ξεπερνάει σε μήκος τους 500 χαρακτήρες
        if (overview.length() > 500) {
            overview = overview.substring(0, 497) + "...";
        }
        movie.setOverview(overview);

        return movie;
    }

    public static List<Movie> moviesFromJson(JSONArray array, List<Genre> genres) {
        return (List<Movie>) array.stream()
                .map(json -> movieFromJson((JSONObject) json, genres))
                .collect(Collectors.toList());
    }
}
