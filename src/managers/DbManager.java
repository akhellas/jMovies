package managers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.FavoriteList;
import model.Genre;
import model.Movie;

// Κλάση για την διαχείριση των κλήσεων προς τη Βάση Δεδομένων (ΒΔ)
public final class DbManager {

    private static final String DB_CONNECTION_ERROR = "Αποτυχία σύνδεσης με τη Βάση Δεδομένων! \n\n(Σιγουρευτείτε ότι έχετε συνδεθεί μέσω του NetBeans IDE)";
    private static final String DB_INITIALIZATION_ERROR = "Αποτυχία αρχικοποίησης της Βάσης Δεδομένων!";
    private static final String PU_NAME = "MyMoviesPU";

    private static EntityManagerFactory factory;
    private static EntityManager manager;

    // μέθοδος που αρχικοποιεί τον EntityManager αν δεν έχει αρχικοποιηθεί ήδη
    // και επιστρέφει το static instance που χρησιμοποιείται από την εφαρμογή
    public static EntityManager getManager() {
        try {
            if (factory == null) {
                factory = Persistence.createEntityManagerFactory(PU_NAME);
            }
            if (manager == null) {
                manager = factory.createEntityManager();
            }
        } catch (Exception exception) {
            UIHelper.showError(null, DB_CONNECTION_ERROR);
        }

        return manager;
    }

    public static EntityTransaction getTransaction() {
        return getManager().getTransaction();
    }

    // Διαδικασία αρχικοποίησης ΒΔ
    public static void initializeDb(List<Genre> genres, List<Movie> movies) throws Exception {
        try {
            Query clearMovies = getManager().createQuery("DELETE FROM Movie");
            Query clearGenres = getManager().createQuery("DELETE FROM Genre");
            Query clearFavoriteLists = getManager().createQuery("DELETE FROM FavoriteList");

            EntityTransaction transaction = getTransaction();
            transaction.begin();

            clearMovies.executeUpdate();
            clearGenres.executeUpdate();
            clearFavoriteLists.executeUpdate();

            genres.forEach(genre -> DbManager.getManager().persist(genre));
            movies.forEach(movie -> DbManager.getManager().persist(movie));

            transaction.commit();
        } catch (Exception exception) {
            throw new Exception(DB_INITIALIZATION_ERROR);
        }
    }

    public static List<Genre> getGenres() {
        return getManager().createNamedQuery("Genre.findAll").getResultList();
    }

    public static List<FavoriteList> getFavoriteLists() {
        return getManager().createNamedQuery("FavoriteList.findAll").getResultList();
    }

    public static List<Movie> getMoviesByFavoriteList(FavoriteList list) {
        return getManager().createQuery("SELECT m FROM Movie m WHERE m.favoriteListId = :favoriteList", Movie.class)
                .setParameter("favoriteList", list)
                .getResultList();
    }

    public static List<Movie> getBestMoviesByRating(int count) {
        return getManager().createQuery("SELECT m FROM Movie m ORDER BY m.rating DESC", Movie.class)
                .setMaxResults(count)
                .getResultList();
    }

    public static List<Movie> getBestMoviesByList() {
        Query query = getManager().createQuery("SELECT m FROM Movie m WHERE m.favoriteListId = :favoriteList ORDER BY m.rating DESC", Movie.class)
                .setMaxResults(1);

        return getFavoriteLists().stream()
                .map(list -> {
                    try {
                        Movie mv = (Movie) query.setParameter("favoriteList", list).getSingleResult();
                        System.out.println(mv.getTitle());
                        return mv;
                    } catch (Exception exception) {
                        return null;
                    }
                })
                .filter(movie -> movie != null)
                .collect(Collectors.toList());
    }

    public static List<Movie> getMoviesByGenreAndYear(Genre genre, int year) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return getManager().createQuery("SELECT m FROM Movie m WHERE m.genreId = :genre AND m.releaseDate >= :yearStart AND m.releaseDate <= :yearEnd", Movie.class)
                    .setParameter("genre", genre)
                    .setParameter("yearStart", format.parse(year + "-01-01"))
                    .setParameter("yearEnd", format.parse(year + "-12-31"))
                    .getResultList();
        } catch (ParseException ex) {
            Logger.getLogger(DbManager.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<>();
        }
    }

    public static void updateMovie(Movie movie) {
        EntityTransaction transaction = getTransaction();
        transaction.begin();
        getManager().persist(movie);
        transaction.commit();
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

        // ζητάμε από τη ΒΔ όλες τις ταινίες που ανήκουν στη λίστα
        // που θέλουμε να σβήσουμε και πριν τη σβήσουμε τις
        // αποσυσχετίζουμε
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
