package dao;

import connection.ConnectionPool;
import connection.ProxyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Executor {

    private static final Executor INSTANCE = new Executor();

    private Executor(){}

    public static Executor getInstance() {
        return INSTANCE;
    }

    private ConnectionPool pool = ConnectionPool.getInstance();

    public boolean execute(String query, StatementHandler stmtHandler) throws SQLException {
        ProxyConnection connection = pool.getConnection();
        PreparedStatement stmt = connection.prepareStatement(query);
        stmtHandler.setStatement(stmt);
        boolean result = stmt.execute();
        stmt.close();
        connection.close();
        return result;
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
}
