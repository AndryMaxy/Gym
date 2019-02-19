package service.impl;

import dao.AdminDAO;
import dao.BookingDAO;
import dao.TrainerDAO;
import dao.VisitorDAO;
import dao.exception.DAOException;
import dao.impl.AdminDAOImpl;
import dao.impl.BookingDAOImpl;
import dao.impl.TrainerDAOImpl;
import dao.impl.VisitorDAOImpl;
import entity.Appointment;
import entity.Exercise;
import entity.Membership;
import entity.Product;
import entity.User;
import service.UserService;
import service.exception.ServiceException;
import util.Encoder;
import util.exception.EncoderException;

import java.util.List;
//TODO MB SEPARATE BY ROLE
public class UserServiceImpl implements UserService {

    private final AdminDAO adminDAO = AdminDAOImpl.getInstance();
    private final TrainerDAO trainerDAO = TrainerDAOImpl.getInstance();
    private final VisitorDAO visitorDAO = VisitorDAOImpl.getInstance();

    private static class UserServiceImplHolder {
        static final UserServiceImpl INSTANCE = new UserServiceImpl();
    }

    public static UserServiceImpl getInstance() {
        return UserServiceImplHolder.INSTANCE;
    }

    private UserServiceImpl(){}

    @Override
    public boolean isUserLoginExist(String login) throws ServiceException {
        try {
            return visitorDAO.isUserLoginExist(login);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User getUserByLogin(String login) throws ServiceException {
        try {
            return visitorDAO.getByLogin(login);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User getUser(int id) throws ServiceException {
        try {
            return visitorDAO.getById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int logIn(String login, char[] password) throws ServiceException, EncoderException {
        try {
            User user = visitorDAO.getByLogin(login);
            if (user == null) {
                return 0;
            }
            String hash = user.getHash();
            String salt = user.getSalt();
            boolean correct = Encoder.check(password, hash.getBytes(), salt.getBytes());
            if (correct){
                return user.getId();
            } else {
                return 0;
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> getVisitors() throws ServiceException {
        try {
            return trainerDAO.getVisitors();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User getVisitor(int id) throws ServiceException {
        try {
            return trainerDAO.getVisitor(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean add(User user) throws ServiceException {
        try {
            return visitorDAO.add(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
