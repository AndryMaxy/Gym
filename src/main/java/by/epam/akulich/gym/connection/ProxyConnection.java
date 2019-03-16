package by.epam.akulich.gym.connection;

import by.epam.akulich.gym.connection.exception.DBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The class is wrapper for {@link Connection}.
 *
 * @author Andrey Akulich
 * @see Connection
 * @see AutoCloseable
 */
public class ProxyConnection implements AutoCloseable {

    /**
     * A constant has an instance of {@link Logger} to logs this class.
     */
    private static final Logger LOGGER = LogManager.getLogger(ProxyConnection.class.getSimpleName());

    /**
     * {@link Connection} to database instance
     */
    private Connection connection;

    /**
     * Instantiates new ProxyConnection.
     *
     * @param connection {@link Connection} to database instance
     */
    public ProxyConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Returns this instance to {@link ConnectionPool} connection pool
     */
    @Override
    public void close() {
        ConnectionPool pool = ConnectionPool.getInstance();
        pool.returnConnection(this);
    }

    /**
     * Closes this connection.
     */
    public void finish() {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.fatal("Can't close connection.", e);
            throw new DBException();
        }
    }

    /**
     * Creates a <code>PreparedStatement</code> object for sending
     * parameterized SQL statements to the database.
     *
     * @param sql an SQL statement that may contain one or more '?'
     * parameter placeholders
     * @return the native form of this statement
     * @throws SQLException if a database access error occurs
     */
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);

    }

    /**
     * Makes all changes made since the previous
     * commit/rollback permanent and releases any database locks
     * currently held by this <code>Connection</code> object.
     * This method should be
     * used only when auto-commit mode has been disabled.
     *
     * @exception SQLException if a database access error occurs,
     * this method is called while participating in a distributed transaction,
     * if this method is called on a closed connection or this
     *            <code>Connection</code> object is in auto-commit mode
     * @see #setAutoCommit
     */
    public void commit() throws SQLException {
        connection.commit();
    }

    /**
     * Sets this connection's auto-commit mode to the given state.
     * If a connection is in auto-commit mode, then all its SQL
     * statements will be executed and committed as individual
     * transactions.  Otherwise, its SQL statements are grouped into
     * transactions that are terminated by a call to either
     * the method {@link #commit()}  or the method {@link #rollback()}.
     * By default, new connections are in auto-commit
     * mode.
     *
     * @param autoCommit {@code true} to enable auto-commit mode;
     * {@code false} to disable it
     */
    public void setAutoCommit(boolean autoCommit) {
        try {
            connection.setAutoCommit(autoCommit);
        } catch (SQLException e) {
            LOGGER.error("Can't set autocommit transaction", e);
        }
    }

    /**
     * Undoes all changes made in the current transaction
     * and releases any database locks currently held
     * by this {@link ProxyConnection} object. This method should be
     * used only when auto-commit mode has been disabled.
     *
     * @see #setAutoCommit
     */
    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOGGER.error("Can't rollback transaction", e);
        }
    }
}
