package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectorDB {

    public Connection getConnection() throws SQLException {
        DBResourceManager manager = DBResourceManager.getInstance();
        String user = manager.getString(DBKey.USER);
        String password = manager.getString(DBKey.PASSWORD);
        String url = manager.getString(DBKey.URL);
        return DriverManager.getConnection(url, user, password);
    }
}
