package connection;

import connection.exception.DBInitException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;

public class ConnectionPool {

    private static final ConnectionPool instance = new ConnectionPool();
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class.getSimpleName());

    private ArrayBlockingQueue<ProxyConnection> connections;
    private ArrayBlockingQueue<ProxyConnection> takenConnections;

    private ConnectionPool(){}

    public static ConnectionPool getInstance() {
        return instance;
    }

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
            throw new DBInitException();
        }
    }

    public ProxyConnection getConnection(){
        ProxyConnection connection = connections.poll();
        takenConnections.add(connection);
        return connection;
    }

    public void returnConnection(ProxyConnection connection){
        takenConnections.remove(connection);
        connections.add(connection);
    }

    public void finish(){
        for (ProxyConnection connection : connections) {
            connection.finish();
        }
        for (ProxyConnection connection : takenConnections) {
            connection.finish();
        }
    }
}
