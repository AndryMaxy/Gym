package by.epam.akulich.gym.dao;

import by.epam.akulich.gym.connection.ConnectionPool;
import by.epam.akulich.gym.connection.ProxyConnection;
import by.epam.akulich.gym.connection.exception.ConnectionException;
import by.epam.akulich.gym.dao.exception.ExecutorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The class is used for executing sql queries.
 *
 * @author Andrey Akulich
 */
public class Executor {

    /**
     * A constant has an instance of {@link Logger} to logs this class.
     */
    private static final Logger LOGGER = LogManager.getLogger(Executor.class.getSimpleName());

    /**
     * This class represents initialization-on-demand holder idiom for {@link Executor}
     */
    private static class ExecutorHolder {
        static final Executor INSTANCE = new Executor();
    }

    /**
     * Instantiates new Executor
     */
    private Executor(){}

    /**
     * @return {@link Executor} instance
     */
    public static Executor getInstance() {
        return ExecutorHolder.INSTANCE;
    }

    /**
     * Instance of {@link ConnectionPool}
     */
    private ConnectionPool pool = ConnectionPool.getInstance();

    /**
     * Executes the SQL statement in {@link PreparedStatement} object,
     * which may be any kind of SQL statement.
     * This method should be used for inserting or updating information
     * in database.
     *
     * @param query an SQL statement that may contain one or more '?',
     * parameter placeholders
     * @param stmtHandler functional interface which sets the designated
     * parameter to SQL.
     * @throws ExecutorException if the query failed or connection can't be taken
     */
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

    /**
     * Executes the SQL query in this {@link PreparedStatement} object
     * and returns the object generated by the query.
     * This method should be used for selecting information from database.
     *
     * @param query an SQL statement that may contain one or more '?',
     * parameter placeholders
     * @param stmtHandler functional interface which sets the designated
     * parameter to SQL.
     * @param resHandler functional interface which handles {@link ResultSet}
     * @param <T> type of query result
     * @return the object generated by the query
     * @throws ExecutorException if the query failed or connection can't be taken
     */
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

    /**
     * Executes the SQL query in this {@link PreparedStatement} object
     * and returns the object generated by the query.
     * This method should be used for selecting information from database.
     *
     * @param query an SQL statement that may contain one or more '?',
     * parameter placeholders
     * @param resHandler functional interface which handles {@link ResultSet}
     * @param <T> type of query result
     * @return the object generated by the query
     * @throws ExecutorException if the query failed or connection can't be taken
     */
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

    /**
     * Executes the transaction of SQL queries in this {@link PreparedStatement} object,
     * which may be any kind of SQL statement.
     *
     * @param query query array of SQL statements that may contain one or more '?',
     * parameter placeholders
     * @param stmtHandler array of functional interface which sets the designated
     * parameter to SQL.
     * @throws ExecutorException if the query failed or connection can't be taken
     */
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