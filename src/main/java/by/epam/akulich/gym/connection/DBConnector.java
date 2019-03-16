package by.epam.akulich.gym.connection;

import by.epam.akulich.gym.entity.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The class is used for taking connection to database.
 *
 * @author Andrey Akulich
 */
public class DBConnector {

    /**
     * This class represents initialization-on-demand holder idiom for {@link DBConnector}
     */
    private static class DBConnectorHolder{
        static final DBConnector INSTANCE = new DBConnector();
    }

    /**
     * Constructs DBConnector.
     */
    private DBConnector(){}

    /**
     * @return DBConnector instance.
     */
    public static DBConnector getInstance(){
        return DBConnectorHolder.INSTANCE;
    }
    /**
     * Takes connection to database.
     *
     * @return a connection to the URL
     * @throws SQLException if a database access error occurs or the url is {@code null}
     */
    public Connection getConnection() throws SQLException {
        DBResourceManager manager = DBResourceManager.getInstance();
        String user = manager.getString(Constants.DBKey.USER);
        String password = manager.getString(Constants.DBKey.PASSWORD);
        String url = manager.getString(Constants.DBKey.URL);
        return DriverManager.getConnection(url, user, password);
    }
}
