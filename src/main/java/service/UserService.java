package service;

import entity.Appointment;
import entity.User;
import service.exception.ServiceException;
import util.exception.EncoderException;

import javax.servlet.ServletException;
import java.util.List;

public interface UserService {

    boolean isUserLoginExist(String login) throws ServiceException;
    User getByLogin(String login) throws ServiceException;
    User getById(int id) throws ServiceException;
    boolean buyMembership(int userId, String membershipStr) throws ServiceException;
    boolean checkUser(String login, char[] password) throws ServiceException, EncoderException;
    boolean add(User entity) throws ServiceException;
    Appointment getAppointment(int id) throws ServiceException;
}
