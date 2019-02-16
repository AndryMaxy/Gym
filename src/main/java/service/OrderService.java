package service;

import entity.Order;
import service.exception.ServiceException;

public interface OrderService {

    Order get(int userId) throws ServiceException;
}
