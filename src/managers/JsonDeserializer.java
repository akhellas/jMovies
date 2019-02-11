package managers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import model.Genre;
import model.Movie;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public final class JsonDeserializer {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final int[] ACCEPTABLE_GENRES = {28, 10749, 878};

    private static Boolean isAcceptable(Object genreId)
    {
        return Arrays.stream(ACCEPTABLE_GENRES).anyMatch(x -> x == parseInt(genreId));
    }
    
    private static Integer parseInt(Object value)
    {
        return (int) (long) value;
    }
    
    private static String parseString(Object value)
    {
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

    private static Date parseDate(Object json) throws ParseException {
        return DATE_FORMAT.parse((String) json);
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
        try {
            Movie movie = new Movie(parseInt(json.get("id")), parseString(json.get("title")), parseFloat(json.get("vote_average")));
            movie.setReleaseDate(parseDate(json.get("release_date")));
            
            JSONArray genreIds = (JSONArray)json.get("genre_ids");
            int gid = parseInt(genreIds.stream().filter(g -> isAcceptable(g)).findFirst().get());
            movie.setGenreId(genres.stream().filter(g -> g.getId() == gid).findFirst().get());
            
            movie.setOverview(parseString(json.get("overview")));

            return movie;
        } catch (ParseException exception) {
            // TODO: add error messages
            System.out.println(exception);
            return null;
        }
    }

    public static List<Movie> moviesFromJson(JSONArray array, List<Genre> genres) {
        return (List<Movie>) array.stream()
                .map(json -> movieFromJson((JSONObject) json, genres))
                .collect(Collectors.toList());
    }
}
