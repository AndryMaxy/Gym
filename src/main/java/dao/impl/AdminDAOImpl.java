package dao.impl;

import dao.AdminDAO;
import dao.Executor;
import dao.exception.DAOException;

import java.sql.SQLException;

public class AdminDAOImpl implements AdminDAO {

    private static final String SELECT_USERS =
            "SELECT u.UserId, u.Name, u.Surname, r.Role FROM User u JOIN UserRole r ON u.UserRoleId = r.UserRoleId";
    private static final String UPDATE_USER_ROLE =
            "UPDATE User SET UserRoleId = ? WHERE UserId = ?";
    private static final String UPDATE_VISITOR_VISITS =
            "UPDATE Booking SET VisitCountLeft = ? WHERE UserId = ?";
    private static final String DELETE_USER = "DELETE_USER FROM User WHERE UserId = ?";

    private static class AdminDAOImplHolder {
        static final AdminDAOImpl INSTANCE = new AdminDAOImpl();
    }

    private Executor executor = Executor.getInstance();

    public static AdminDAOImpl getInstance() {
        return AdminDAOImplHolder.INSTANCE;
    }

    @Override
    public boolean delete(int userId) throws DAOException {
        try {
            return executor.execute(DELETE_USER, statement -> {
                statement.setInt(1, userId);
            });
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
