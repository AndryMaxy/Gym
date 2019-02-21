package dao;

import connection.ConnectionPool;
import connection.ProxyConnection;
import dao.exception.ExecutorException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Executor {

    private static class ExecutorHolder {
        static final Executor INSTANCE = new Executor();
    }

    private Executor(){}

    public static Executor getInstance() {
        return ExecutorHolder.INSTANCE;
    }

    private ConnectionPool pool = ConnectionPool.getInstance();
                                                                    //TODO MB ALWAYS THROWS EXECUTOR EXC
    public void execute(String query, StatementHandler stmtHandler) throws SQLException {
        ProxyConnection connection = pool.getConnection();
        PreparedStatement stmt = connection.prepareStatement(query);
        stmtHandler.setStatement(stmt);
        stmt.execute();
        stmt.close();
        connection.close();
    }

    public <T> T executeQuery(String query, StatementHandler stmtHandler, ResultHandler<T> resHandler) throws SQLException {
        ProxyConnection connection = pool.getConnection();
        PreparedStatement stmt = connection.prepareStatement(query);
        stmtHandler.setStatement(stmt);
        stmt.executeQuery();
        ResultSet result = stmt.getResultSet();
        T value = resHandler.handle(result);
        result.close();
        stmt.close();
        connection.close();
        return value;
    }

    public void executeTransaction(String[] query, StatementHandler[] stmtHandler) throws ExecutorException {
        ProxyConnection connection = pool.getConnection();
        List<PreparedStatement> statements = new ArrayList<>();
        try {
            connection.setAutoCommit(false);
            for (int i = 0; i < query.length; i++) {
                PreparedStatement statement = connection.prepareStatement(query[i]);
                stmtHandler[i].setStatement(statement);
                statement.executeUpdate();
                statements.add(statement);
            }
            connection.commit();
            connection.setAutoCommit(true);
            for (PreparedStatement st : statements) {
                st.close();
            }
            connection.close();
        } catch (SQLException e) {
            try {
                connection.setAutoCommit(true);
                connection.rollback();
            } catch (SQLException e1) {
                throw new ExecutorException(e1);
            }
        }
    }
}
