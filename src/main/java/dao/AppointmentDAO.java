package dao;

import connection.ConnectionPool;
import connection.ProxyConnection;
import connection.exception.ConnectionException;
import dao.exception.DAOException;
import dao.exception.ExecutorException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AppointmentDAO<T> {

    private Executor executor = Executor.getInstance();

    protected abstract String getAllQuery();
    protected abstract String getByUserIdQuery();
    protected abstract String addAppointmentQuery();
    protected abstract void handleAllResult(List<T> list, ResultSet resultSet) throws SQLException;
    protected abstract void handleByUserIdResult(List<T> list, ResultSet resultSet) throws SQLException;
    protected abstract void handleAdd(int bookingId, T t, PreparedStatement statement) throws SQLException;

    public List<T> getAll() throws DAOException {
        try {
            return executor.executeQuery(getAllQuery(), resultSet -> {
                List<T> ts = new ArrayList<>();
                while (resultSet.next()) {
                    handleAllResult(ts, resultSet);
                }
                return ts;
            });
        } catch (ExecutorException e) {
            throw new DAOException(e);
        }
    }

    public List<T> getByUserId(int userId) throws DAOException {
        try {
            return executor.executeQuery(getByUserIdQuery(), statement -> {
                statement.setInt(1, userId);
            }, resultSet -> {
                List<T> ts = new ArrayList<>();
                while (resultSet.next()){
                    handleByUserIdResult(ts, resultSet);
                }
                return ts;
            });
        } catch (ExecutorException e) {
            throw new DAOException(e);
        }
    }

    public void addAppointment(int bookingId, List<T> list) throws DAOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        try {
            connection = pool.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(addAppointmentQuery());
            for (T t : list) {
                handleAdd(bookingId, t, statement);
            }
            statement.executeBatch();
        } catch (SQLException | ConnectionException e) {
            if (connection != null) {
                connection.rollback();
            }
            throw new DAOException(e);
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }
}
