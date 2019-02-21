package managers;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JOptionPane;

import messages.Errors;
import model.FavoriteList;
import model.Genre;
import model.Movie;
import org.json.simple.JSONArray;

public final class DbManager {

    private static final String PU_NAME = "MyMoviesPU";
    private static EntityManagerFactory factory;
    private static EntityManager manager;

    public static EntityManagerFactory getFactory() {
        if (factory == null) {
            try {
                factory = Persistence.createEntityManagerFactory(PU_NAME);
            } catch (Exception exception) {
                System.out.println(exception);
                JOptionPane.showMessageDialog(null, Errors.DB_CONNECTION_ERROR, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
        return factory;
    }

    public static EntityManager getManager() {
        if (manager == null) {
            try {
                manager = getFactory().createEntityManager();
            } catch (Exception exception) {
                System.out.println(exception);
                JOptionPane.showMessageDialog(null, Errors.DB_CONNECTION_ERROR, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
        return manager;
    }

    public static EntityTransaction getTransaction() {
        return getManager().getTransaction();
    }

    public static void initializeDb() {
        try {

            Query clearMovies = getManager().createQuery("DELETE FROM Movie");
            Query clearGenres = getManager().createQuery("DELETE FROM Genre");
            Query clearFavoriteLists = getManager().createQuery("DELETE FROM FavoriteList");

            EntityTransaction transaction = getTransaction();
            transaction.begin();

            clearMovies.executeUpdate();
            clearGenres.executeUpdate();
            clearFavoriteLists.executeUpdate();

            ApiManager api = new ApiManager();

            JSONArray genresJson = api.getGenres();
            List<Genre> genres = JsonDeserializer.genresFromJson(genresJson);

            JSONArray moviesJson = api.getMovies();
            List<Movie> movies = JsonDeserializer.moviesFromJson(moviesJson, genres);

            genres.stream().filter(g -> JsonDeserializer.isAcceptable(g.getId())).forEach(genre -> DbManager.getManager().persist(genre));
            movies.forEach(movie -> DbManager.getManager().persist(movie));

            transaction.commit();
        } catch (Exception exception) {
            // TODO: add error messages
            System.out.println(exception);
        }
    }

    public static List<FavoriteList> getFavoriteLists() {
        return getManager().createNamedQuery("FavoriteList.findAll").getResultList();
    }

    public static List<Movie> getMoviesByFavoriteList(FavoriteList list) {
        Query query = getManager().createQuery("SELECT m FROM Movie m WHERE m.favoriteListId = :favoriteList", Movie.class)
                .setParameter("favoriteList", list);
        return query.getResultList();
    }

    public static FavoriteList createFavoriteList(String name) {
        EntityTransaction transaction = getTransaction();
        transaction.begin();
        FavoriteList newList = new FavoriteList();
        newList.setName(name);
        getManager().persist(newList);
        transaction.commit();
        return newList;
    }

    public static void updateFavoriteList(FavoriteList list) {
        EntityTransaction transaction = getTransaction();
        transaction.begin();
        getManager().persist(list);
        transaction.commit();
    }

    public static void deleteFavoriteList(FavoriteList list) {
        EntityTransaction transaction = getTransaction();
        transaction.begin();
        List<Movie> movies = getMoviesByFavoriteList(list);
        movies.forEach(movie -> {
            movie.setFavoriteListId(null);
            getManager().persist(movie);
        });
        getManager().remove(list);
        transaction.commit();
    }

    public static void deleteFavoriteLists(List<FavoriteList> lists) {
        lists.forEach(list -> deleteFavoriteList(list));
    }
}
