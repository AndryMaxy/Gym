package service.impl;

import dao.OrderDAO;
import dao.UserDAO;
import dao.exception.DAOException;
import dao.impl.OrderDAOImpl;
import dao.impl.UserDAOImpl;
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

    private final UserDAO userDAO = UserDAOImpl.getInstance();

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
            return userDAO.isUserLoginExist(login);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public User getByLogin(String login) throws ServiceException {
        try {
            return userDAO.getByLogin(login);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User getById(int id) throws ServiceException {
        try {
            return userDAO.getById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public boolean checkUser(String login, char[] password) throws ServiceException, EncoderException {
        try {
            User user = userDAO.getByLogin(login);
            if (user == null) {
                return false;
            }
            String hash = user.getHash();
            String salt = user.getSalt();
            return Encoder.check(password, hash.getBytes(), salt.getBytes());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean add(User user) throws ServiceException {
        try {
            return userDAO.add(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Appointment getAppointment(int id) throws ServiceException {
        try {
            List<Exercise> exercises = userDAO.getExercises(id);
            List<Product> products = userDAO.getProducts(id);
            if (exercises.isEmpty() && products.isEmpty()) {
                return null;
            }
            Appointment appointment = new Appointment();
            appointment.setExercises(exercises);
            appointment.setProducts(products);
            return appointment;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
    //TODO MB MOVE TO ORDER SERVICE
    @Override
    public boolean buyMembership(int userId, String membershipStr) throws ServiceException {
        try {
            User user = userDAO.getById(userId);
            Membership membership = Membership.valueOf(membershipStr);
            int price = membership.getPrice();
            int cost = price / 100 * (100 - user.getDiscount());
            int balance = user.getBalance();
            if (balance < cost) {
                return false;
            }
            user.setBalance(balance - cost);
            userDAO.update(user);
            OrderDAO orderDAO = OrderDAOImpl.getInstance();
            return orderDAO.add(userId, membership.getCount());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
