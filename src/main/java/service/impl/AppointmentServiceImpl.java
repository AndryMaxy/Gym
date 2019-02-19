package service.impl;

import dao.AppointmentDAO;
import dao.exception.DAOException;
import dao.impl.ExerciseDAOImpl;
import dao.impl.ProductDAOImpl;
import entity.Appointment;
import entity.Exercise;
import entity.Product;
import service.AppointmentService;
import service.exception.ServiceException;

import java.util.List;

public class AppointmentServiceImpl implements AppointmentService {

    private static class AppointmentServiceHolder {
        static final AppointmentServiceImpl INSTANCE = new AppointmentServiceImpl();
    }

    private final AppointmentDAO<Exercise> exerciseDAO = ExerciseDAOImpl.getInstance();
    private final AppointmentDAO<Product> productDAO = ProductDAOImpl.getInstance();

    private AppointmentServiceImpl(){}

    public static AppointmentServiceImpl getInstance() {
        return AppointmentServiceHolder.INSTANCE;
    }

    public Appointment getAppointment(int id) throws ServiceException {
        try {
            List<Exercise> exercises = exerciseDAO.getByUserId(id);
            List<Product> products = productDAO.getByUserId(id);
            return get(exercises, products);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public Appointment getAll() throws ServiceException {
        try {
            List<Exercise> exercises = exerciseDAO.getAll();
            List<Product> products = productDAO.getAll();
            return get(exercises, products);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    private Appointment get(List<Exercise> exercises, List<Product> products){
        if (exercises.isEmpty() && products.isEmpty()) {
            return null;
        }
        Appointment appointment = new Appointment();
        appointment.setExercises(exercises);
        appointment.setProducts(products);
        return appointment;
    }

    @Override
    public boolean addAppointment(int userId, Appointment appointment) throws ServiceException {
        try {
            exerciseDAO.addAppointment(userId, appointment.getExercises());
            productDAO.addAppointment(userId, appointment.getProducts());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return true;
    }
}
