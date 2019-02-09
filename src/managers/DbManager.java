package managers;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
}
