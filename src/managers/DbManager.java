package managers;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JOptionPane;

import messages.Errors;

public final class DbManager {
    private static final String PU_NAME = "MyMoviesPU";
    private static EntityManagerFactory factory;
    private static EntityManager manager;
    
    public static EntityManager getManager()
    {
        return manager;
    }

    public static void connect()
    {
        if (factory != null)
        {
            return;
        }
        try
        {
            factory = Persistence.createEntityManagerFactory(PU_NAME);
            manager = factory.createEntityManager();
        }
        catch(Exception exception)
        {
            System.out.println(exception);
            JOptionPane.showMessageDialog(null, Errors.DB_CONNECTION_ERROR, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void initializeDb()
    {
        try
        {
            if (getManager() == null)
            {
                connect();
            }
            
            Query clearMovies = getManager().createQuery("DELETE FROM Movie");
            Query clearGenres = getManager().createQuery("DELETE FROM Genre");
            Query clearFavoriteLists = getManager().createQuery("DELETE FROM FavoriteList");
            
            EntityTransaction transaction = getManager().getTransaction();
            transaction.begin();
            
            clearMovies.executeUpdate();
            clearGenres.executeUpdate();
            clearFavoriteLists.executeUpdate();
            transaction.commit();
        }
        catch (Exception exception)
        {
            // TODO: add error messages
            System.out.println(exception);
        }
    }
}
