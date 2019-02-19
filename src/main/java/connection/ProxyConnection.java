package connection;

import connection.exception.DBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProxyConnection implements AutoCloseable {

    private static final Logger LOGGER = LogManager.getLogger(ProxyConnection.class.getSimpleName());
    private Connection connection;

    public ProxyConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void close() {
        ConnectionPool pool = ConnectionPool.getInstance();
        pool.returnConnection(this);
    }

    public void finish() {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.fatal("Can't close connection.", e);
            throw new DBException();
        }
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);

    }

    public void setAutoCommit(boolean autoCommit) throws SQLException {
        connection.setAutoCommit(autoCommit);
    }

    public void commit() throws SQLException {
        connection.commit();
    }

    public void rollback() {
        try {
            connection.rollback();
            close();
        } catch (SQLException e) {
            LOGGER.error("Can't rollback transaction");
            close();
        }
    }
}
