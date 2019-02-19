package dao;

import connection.ConnectionPool;
import connection.ProxyConnection;
import dao.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AppointmentDAO<T> {

    private Executor executor = Executor.getInstance();

    protected abstract String getAllQuery();
    protected abstract String getByUserIdQuery();
    protected abstract String addAppointment();
    protected abstract void handleAllResult(List<T> list, ResultSet resultSet) throws SQLException;
    protected abstract void handleByUserIdResult(List<T> list, ResultSet resultSet) throws SQLException;
    protected abstract void handleAdd(int userId, T t, PreparedStatement statement) throws SQLException;

    public List<T> getAll() throws DAOException {
        try {
            return executor.executeQuery(getAllQuery(), statement -> {}, resultSet -> {
                List<T> ts = new ArrayList<>();
                while (resultSet.next()) {
                    handleAllResult(ts, resultSet);
                }
                return ts;
            });
        } catch (SQLException e) {
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
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

//    public boolean addAppointment(int userId, List<T> list) throws DAOException {
//        try {
//            return executor.execute(addAppointment(), statement -> {
//                for (T t : list) {
//                    handleAdd(userId, t);
//                }
//            });
//        } catch (SQLException e) {
//            throw new DAOException(e);
//        }
//    }

    public boolean addAppointment(int userId, List<T> list) throws DAOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = pool.getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(addAppointment());
            for (T t : list) {
                handleAdd(userId, t, statement);
            }
            int size = list.size();
            int[] batch = statement.executeBatch();
            boolean result = size == batch.length;
            if (result) {
                connection.commit();
            } else {
                connection.rollback();
            }
            connection.setAutoCommit(true);
            connection.close();
            return result;
        } catch (SQLException e) {  //TODO NOT GOOD
            connection.rollback();
            throw new DAOException(e);
        }
    }
}
