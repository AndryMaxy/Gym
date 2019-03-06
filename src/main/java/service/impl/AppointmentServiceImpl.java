package service.impl;

import dao.AppointmentDAO;
import dao.exception.DAOException;
import dao.impl.ExerciseAppointmentDAOImpl;
import dao.impl.ProductAppointmentDAOImpl;
import entity.Appointment;
import entity.ExerciseAppointment;
import entity.ProductAppointment;
import service.AppointmentService;
import service.ParameterValidator;
import service.exception.InvalidInputException;
import service.exception.ServiceException;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

public class AppointmentServiceImpl implements AppointmentService {

    private static final String EXERCISE = "exercise";
    private static final String PRODUCT = "product";

    private static class AppointmentServiceHolder {
        static final AppointmentServiceImpl INSTANCE = new AppointmentServiceImpl();
    }

    private AppointmentDAO<ExerciseAppointment> exerciseDAO = ExerciseAppointmentDAOImpl.getInstance();
    private AppointmentDAO<ProductAppointment> productDAO = ProductAppointmentDAOImpl.getInstance();
    private ParameterValidator validator = ParameterValidator.getInstance();

    private AppointmentServiceImpl(){}

    private AppointmentServiceImpl(ExerciseAppointmentDAOImpl exerciseDAO, ProductAppointmentDAOImpl productDAO) {
        this.exerciseDAO = exerciseDAO;
        this.productDAO = productDAO;
    }

    public static AppointmentServiceImpl getInstance() {
        return AppointmentServiceHolder.INSTANCE;
    }

    public Appointment getAppointment(int bookingId) throws ServiceException {
        try {
            List<ExerciseAppointment> exerciseAppointments = exerciseDAO.getByBookingId(bookingId);
            List<ProductAppointment> productAppointments = productDAO.getByBookingId(bookingId);
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
    public void addAppointment(int bookingId, Appointment appointment) throws ServiceException {
        try {
            exerciseDAO.addAppointment(bookingId, appointment.getExerciseAppointments());
            productDAO.addAppointment(bookingId, appointment.getProductAppointments());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Appointment parseAppointments(Map<String, String[]> parameterMap, Enumeration parameterEnumeration) throws InvalidInputException {
        List<ExerciseAppointment> exerciseAppointments = new ArrayList<>();
        List<ProductAppointment> productAppointments = new ArrayList<>();
        ExerciseAppointment exerciseAppointment;
        ProductAppointment productAppointment;
        while (parameterEnumeration.hasMoreElements()) {
            String element = (String) parameterEnumeration.nextElement();
            if (!element.startsWith(EXERCISE) && !element.startsWith(PRODUCT)) {
                continue;
            }
            String[] split = element.split("-");
            String name = split[0];
            int id = Integer.parseInt(split[1]);
            int[] values = getValues(parameterMap, element);
            switch (name) {
                case EXERCISE :
                    exerciseAppointment = new ExerciseAppointment();
                    exerciseAppointment.setId(id);
                    exerciseAppointment.setRepCount(values[0]);
                    exerciseAppointment.setSetCount(values[1]);
                    exerciseAppointment.setWeight(values[2]);
                    exerciseAppointments.add(exerciseAppointment);
                    break;
                case PRODUCT :
                    productAppointment = new ProductAppointment();
                    productAppointment.setId(id);
                    productAppointment.setGramInDay(values[0]);
                    productAppointments.add(productAppointment);
                    break;
            }
        }
        Appointment appointment = new Appointment();
        appointment.setExerciseAppointments(exerciseAppointments);
        appointment.setProductAppointments(productAppointments);
        return appointment;
    }

    private int[] getValues(Map<String, String[]> parameterMap, String element) throws InvalidInputException {
        String[] values = parameterMap.get(element);
        int[] params = new int[values.length];
        int i = 0;
        for (String valueStr : values) {
            if (valueStr == null || valueStr.equals("") || !validator.validateNumber(valueStr)) {
                throw new InvalidInputException();
            }
            params[i] = Integer.parseInt(valueStr);
            i++;
        }
        return params;
    }
}
