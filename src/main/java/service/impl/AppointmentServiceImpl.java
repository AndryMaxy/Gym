package service.impl;

import dao.AppointmentDAO;
import dao.exception.DAOException;
import dao.impl.ExerciseAppointmentDAOImpl;
import dao.impl.ProductAppointmentDAOImpl;
import entity.Appointment;
import entity.ExerciseAppointment;
import entity.ProductAppointment;
import service.AppointmentService;
import service.exception.ServiceException;
import validator.ParameterValidator;

import java.util.List;

public class AppointmentServiceImpl implements AppointmentService {

    private static class AppointmentServiceHolder {
        static final AppointmentServiceImpl INSTANCE = new AppointmentServiceImpl();
    }

    private final AppointmentDAO<ExerciseAppointment> exerciseDAO = ExerciseAppointmentDAOImpl.getInstance();
    private final AppointmentDAO<ProductAppointment> productDAO = ProductAppointmentDAOImpl.getInstance();

    private AppointmentServiceImpl(){}

    public static AppointmentServiceImpl getInstance() {
        return AppointmentServiceHolder.INSTANCE;
    }

    public Appointment getAppointment(int bookingId) throws ServiceException {
        try {
            List<ExerciseAppointment> exerciseAppointments = exerciseDAO.getByUserId(bookingId);
            List<ProductAppointment> productAppointments = productDAO.getByUserId(bookingId);
            return get(exerciseAppointments, productAppointments);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public Appointment getAll() throws ServiceException {
        try {
            List<ExerciseAppointment> exerciseAppointments = exerciseDAO.getAll();
            List<ProductAppointment> productAppointments = productDAO.getAll();
            return get(exerciseAppointments, productAppointments);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    private Appointment get(List<ExerciseAppointment> exerciseAppointments, List<ProductAppointment> productAppointments){
        if (exerciseAppointments.isEmpty() && productAppointments.isEmpty()) {
            return null;
        }
        Appointment appointment = new Appointment();
        appointment.setExerciseAppointments(exerciseAppointments);
        appointment.setProductAppointments(productAppointments);
        return appointment;
    }

    @Override
    public boolean addAppointment(int bookingId, List<ExerciseAppointment> exerciseAppointments, List<ProductAppointment> productAppointments) throws ServiceException {
        try {
            Appointment appointment = new Appointment();
            appointment.setExerciseAppointments(exerciseAppointments);
            appointment.setProductAppointments(productAppointments);
            exerciseDAO.addAppointment(bookingId, appointment.getExerciseAppointments());
            productDAO.addAppointment(bookingId, appointment.getProductAppointments());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return true;
    }
}
