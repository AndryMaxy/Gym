package dao.connection;

import dao.connection.exception.DBException;
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

    public void commit() throws SQLException {
        connection.commit();
    }

    public void setAutoCommit(boolean autoCommit) {
        try {
            connection.setAutoCommit(autoCommit);
        } catch (SQLException e) {
            LOGGER.error("Can't set autocommit transaction", e);
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOGGER.error("Can't rollback transaction", e);
        }
    }
}
