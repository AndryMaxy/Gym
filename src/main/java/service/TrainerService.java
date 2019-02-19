package service;

import entity.User;
import service.exception.ServiceException;

import java.util.List;

public interface TrainerService {

    List<User> getVisitors() throws ServiceException;
}
