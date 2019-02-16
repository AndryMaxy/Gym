package dao;

import dao.exception.DAOException;
import entity.Exercise;
import entity.Product;
import entity.User;

import java.util.List;

public interface UserDAO{

    boolean add(User entity) throws DAOException;
    boolean update(User user) throws DAOException;
    boolean delete(int id) throws DAOException;
    User getByLogin(String login) throws DAOException;
    User getById(int id) throws DAOException;
    boolean isUserLoginExist(String login) throws DAOException;
    List<Exercise> getExercises(int id) throws DAOException;
    List<Product> getProducts(int id) throws DAOException;
}
