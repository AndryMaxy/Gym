package connection;

import entity.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectorDB {

    public Connection getConnection() throws SQLException {
        DBResourceManager manager = DBResourceManager.getInstance();
        String user = manager.getString(Constants.DBKey.USER);
        String password = manager.getString(Constants.DBKey.PASSWORD);
        String url = manager.getString(Constants.DBKey.URL);
        return DriverManager.getConnection(url, user, password);
    }
}
