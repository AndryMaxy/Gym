package dao.connection;

import dao.connection.exception.ConnectionException;
import dao.connection.exception.DBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;

public class ConnectionPool {

    private static final ConnectionPool INSTANCE = new ConnectionPool();
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class.getSimpleName());

    private ArrayBlockingQueue<ProxyConnection> connections;
    private ArrayBlockingQueue<ProxyConnection> takenConnections;

    private ConnectionPool(){}

    public static ConnectionPool getInstance() {
        return INSTANCE;
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
            LOGGER.fatal("Can't init dao.connection pool", e);
            throw new DBException();
        }
    }

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
