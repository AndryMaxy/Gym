package connection;

import entity.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The class is used for taking connection to database.
 *
 * @author Andrey Akulich
 */
public class ConnectorDB {

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
