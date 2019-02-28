package dao;

import connection.ConnectionPool;
import connection.ProxyConnection;
import connection.exception.ConnectionException;
import dao.exception.ExecutorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Executor {

    private static final Logger LOGGER = LogManager.getLogger(Executor.class.getSimpleName());

    private static class ExecutorHolder {
        static final Executor INSTANCE = new Executor();
    }

    private Executor(){}

    public static Executor getInstance() {
        return ExecutorHolder.INSTANCE;
    }

    private ConnectionPool pool = ConnectionPool.getInstance();

    public void execute(String query, StatementHandler stmtHandler) throws ExecutorException {
        try (ProxyConnection connection = pool.getConnection()) {
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmtHandler.setStatement(stmt);
                stmt.execute();
            }
        } catch (ConnectionException | SQLException e) {
            throw new ExecutorException(e);
        }

    }

    public <T> T executeQuery(String query, StatementHandler stmtHandler, ResultHandler<T> resHandler) throws ExecutorException {
        try (ProxyConnection connection = pool.getConnection()) {
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmtHandler.setStatement(stmt);
                stmt.executeQuery();
                try (ResultSet result = stmt.getResultSet()) {
                    return resHandler.handle(result);
                }
            }
        } catch (ConnectionException | SQLException e) {
            throw new ExecutorException(e);
        }
    }

    public <T> T executeQuery(String query, ResultHandler<T> resHandler) throws ExecutorException {
        try (ProxyConnection connection = pool.getConnection()) {
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.executeQuery();
                try (ResultSet result = stmt.getResultSet()) {
                    return resHandler.handle(result);
                }
            }
        } catch (ConnectionException | SQLException e) {
            throw new ExecutorException(e);
        }
    }

    public void executeTransaction(String[] query, StatementHandler[] stmtHandler) throws ExecutorException {
        List<PreparedStatement> statements = new ArrayList<>();
        ProxyConnection connection = null;
        try {
            connection = pool.getConnection();
            connection.setAutoCommit(false);
            for (int i = 0; i < query.length; i++) {
                PreparedStatement statement = connection.prepareStatement(query[i]);
                stmtHandler[i].setStatement(statement);
                statement.executeUpdate();
                statements.add(statement);
            }
            connection.commit();
        } catch (SQLException | ConnectionException e) {
            if (connection != null) {
                connection.rollback();
            }
            throw new ExecutorException(e);
        } finally {
            for (PreparedStatement st : statements) {
                try {
                    st.close();
                } catch (SQLException e) {
                    LOGGER.error("Can't close statement", e);
                }
            }
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }
}
