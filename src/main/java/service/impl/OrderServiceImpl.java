package service.impl;

import dao.OrderDAO;
import dao.exception.DAOException;
import dao.impl.OrderDAOImpl;
import entity.Order;
import service.OrderService;
import service.exception.ServiceException;

public class OrderServiceImpl implements OrderService {

    private static class OrderServiceImplHolder {
        static final OrderServiceImpl INSTANCE = new OrderServiceImpl();
    }

    public static OrderServiceImpl getInstance(){
        return OrderServiceImplHolder.INSTANCE;
    }

    private OrderDAO orderDAO = OrderDAOImpl.getInstance(); //TODO MB STATIC FINAL

    @Override
    public Order get(int userId) throws ServiceException {
        try {
            return orderDAO.get(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
