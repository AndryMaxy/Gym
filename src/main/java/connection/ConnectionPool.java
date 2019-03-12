package connection;

import connection.exception.ConnectionException;
import connection.exception.DBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Thread safe connection pool.
 *
 * @author Andrey Akulich
 */
public class ConnectionPool {

    /**
     * A constant has an instance of {@link Logger} to logs this class.
     */
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class.getSimpleName());

    /**
     * Trade safe queue of free connections.
     */
    private ArrayBlockingQueue<ProxyConnection> connections;

    /**
     * Trade safe queue of taken connections.
     */
    private ArrayBlockingQueue<ProxyConnection> takenConnections;

    /**
     * This class represents initialization-on-demand holder idiom for {@link ConnectionPool}
     */
    private static class ConnectionPoolHolder {
        private static final ConnectionPool INSTANCE = new ConnectionPool();
    }

    /**
     * Instantiates new ConnectionPool
     */
    private ConnectionPool(){}

    /**
     * @return {@code ConnectionPool} instance
     */
    public static ConnectionPool getInstance() {
        return ConnectionPoolHolder.INSTANCE;
    }

    /**
     * Initialize connection pool with the input size.
     *
     * @param poolSize a size of connection pool
     */
    public void init(int poolSize) {
        connections = new ArrayBlockingQueue<>(poolSize);
        takenConnections = new ArrayBlockingQueue<>(poolSize);
        ConnectorDB connectorDB = new ConnectorDB();
        try {
            for (int i = 0; i < poolSize; i++) {
                Connection connection = connectorDB.getConnection();
                connections.add(new ProxyConnection(connection));
            }
        } catch (SQLException e) {
            LOGGER.fatal("Can't init connection pool", e);
            throw new DBException();
        }
    }

    /**
     * Returns free {@link ProxyConnection} instance from queue.
     *
     * @return free {@link ProxyConnection} instance
     * @throws ConnectionException if thread is interrupted
     */
    public ProxyConnection getConnection() throws ConnectionException {
        ProxyConnection connection;
        try {
            connection = connections.take();
        } catch (InterruptedException e) {
            throw new ConnectionException("Can't take connection", e);
        }
        takenConnections.add(connection);
        return connection;
    }

    /**
     * Moves {@link ProxyConnection} instance from {@link #takenConnections} to {@link #connections}
     *
     * @param connection taken connection
     */
    public void returnConnection(ProxyConnection connection){
        takenConnections.remove(connection);
        connections.add(connection);
    }

    /**
     * Closes all connections.
     */
    public void finish(){
        for (ProxyConnection connection : connections) {
            connection.finish();
        }
        for (ProxyConnection connection : takenConnections) {
            connection.finish();
        }
    }
}
