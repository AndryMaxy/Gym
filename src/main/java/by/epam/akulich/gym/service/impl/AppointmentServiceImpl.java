package by.epam.akulich.gym.service.impl;

import by.epam.akulich.gym.dao.AppointmentDAO;
import by.epam.akulich.gym.dao.exception.DAOException;
import by.epam.akulich.gym.dao.impl.ExerciseAppointmentDAOImpl;
import by.epam.akulich.gym.dao.impl.ProductAppointmentDAOImpl;
import by.epam.akulich.gym.entity.Appointment;
import by.epam.akulich.gym.entity.Booking;
import by.epam.akulich.gym.entity.ExerciseAppointment;
import by.epam.akulich.gym.entity.ProductAppointment;
import by.epam.akulich.gym.service.AppointmentService;
import by.epam.akulich.gym.service.ParameterValidator;
import by.epam.akulich.gym.service.exception.InvalidInputException;
import by.epam.akulich.gym.service.exception.ServiceException;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * The class contains methods with business logic for appointment.
 *
 * @author Andrey Akulich
 */
public class AppointmentServiceImpl implements AppointmentService {

    private static final String EXERCISE = "exercise";
    private static final String PRODUCT = "product";

    /**
     * This class represents initialization-on-demand holder idiom for {@link AppointmentServiceImpl}
     */
    private static class AppointmentServiceHolder {
        static final AppointmentServiceImpl INSTANCE = new AppointmentServiceImpl();
    }

    /**
     * AppointmentDAO<ExerciseAppointment> instance.
     */
    private AppointmentDAO<ExerciseAppointment> exerciseDAO = ExerciseAppointmentDAOImpl.getInstance();

    /**
     * AppointmentDAO<ProductAppointment> instance.
     */
    private AppointmentDAO<ProductAppointment> productDAO = ProductAppointmentDAOImpl.getInstance();

    /**
     * ParameterValidator instance.
     */
    private ParameterValidator validator = ParameterValidator.getInstance();

    /**
     * Constructs AppointmentServiceImpl.
     */
    private AppointmentServiceImpl(){}

    /**
     * Constructs AppointmentServiceImpl.
     * This constructor is used for tests.
     *
     * @param exerciseDAO {@link ExerciseAppointmentDAOImpl} instance
     * @param productDAO {@link ProductAppointmentDAOImpl} instance
     */
    private AppointmentServiceImpl(ExerciseAppointmentDAOImpl exerciseDAO, ProductAppointmentDAOImpl productDAO) {
        this.exerciseDAO = exerciseDAO;
        this.productDAO = productDAO;
    }

    /**
     * @return AppointmentServiceImpl instance.
     */
    public static AppointmentServiceImpl getInstance() {
        return AppointmentServiceHolder.INSTANCE;
    }

    /**
     * Returns {@link Appointment} according {@link Booking} identifier.
     *
     * @param bookingId {@link Booking} identifier
     * @return {@link Appointment} instance
     * @throws ServiceException if exception in dao layer occurs
     */
    public Appointment getAppointment(int bookingId) throws ServiceException {
        try {
            List<ExerciseAppointment> exerciseAppointments = exerciseDAO.getByBookingId(bookingId);
            List<ProductAppointment> productAppointments = productDAO.getByBookingId(bookingId);
            return get(exerciseAppointments, productAppointments);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Returns appointment {@link Appointment} with all appointments.
     *
     * @return {@link Appointment} instance
     * @throws ServiceException if exception in dao layer occurs
     */
    public Appointment getAll() throws ServiceException {
        try {
            List<ExerciseAppointment> exerciseAppointments = exerciseDAO.getAll();
            List<ProductAppointment> productAppointments = productDAO.getAll();
            return get(exerciseAppointments, productAppointments);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Sets appointments to {@link Appointment} object and returns its.
     * @param exerciseAppointments a list of {@link ExerciseAppointment}
     * @param productAppointments a list of {@link ProductAppointment}
     * @return {@link Appointment} instance
     */
    private Appointment get(List<ExerciseAppointment> exerciseAppointments, List<ProductAppointment> productAppointments){
        if (exerciseAppointments.isEmpty() && productAppointments.isEmpty()) {
            return null;
        }
        Appointment appointment = new Appointment();
        appointment.setExerciseAppointments(exerciseAppointments);
        appointment.setProductAppointments(productAppointments);
        return appointment;
    }

    /**
     * Adds user appointment {@link Appointment} to database.
     *
     * @param bookingId {@link Booking} identifier
     * @param appointment {@link Appointment} instance
     * @throws ServiceException if exception in dao layer occurs
     */
    @Override
    public void addAppointment(int bookingId, Appointment appointment) throws ServiceException {
        try {
            exerciseDAO.addAppointment(bookingId, appointment.getExerciseAppointments());
            productDAO.addAppointment(bookingId, appointment.getProductAppointments());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Parses appointment {@link Appointment} from Map and
     * returns its instance.
     *
     * @param parameterMap a map with parameters
     * @param parameterEnumeration an enumeration with parameter names
     * @return parsed {@link Appointment} instance
     * @throws InvalidInputException if user input invalid parameter
     */
    @Override
    public Appointment parseAppointment(Map<String, String[]> parameterMap, Enumeration parameterEnumeration) throws InvalidInputException {
        List<ExerciseAppointment> exerciseAppointments = new ArrayList<>();
        List<ProductAppointment> productAppointments = new ArrayList<>();
        ExerciseAppointment exerciseAppointment;
        ProductAppointment productAppointment;
        while (parameterEnumeration.hasMoreElements()) {
            String appType = (String) parameterEnumeration.nextElement();
            if (!appType.startsWith(EXERCISE) && !appType.startsWith(PRODUCT)) {
                continue;
            }
            String[] split = appType.split("-");
            String name = split[0];
            int id = Integer.parseInt(split[1]);
            int[] values = getValues(parameterMap, appType);
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

    /**
     * Returns {@code int} array which contains values for appointment.
     *
     * @param parameterMap a map with parameters
     * @param appType type of appointment
     * @return {@code int} array which contains values for appointment
     * @throws InvalidInputException if user input invalid parameter
     */
    private int[] getValues(Map<String, String[]> parameterMap, String appType) throws InvalidInputException {
        String[] values = parameterMap.get(appType);
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
