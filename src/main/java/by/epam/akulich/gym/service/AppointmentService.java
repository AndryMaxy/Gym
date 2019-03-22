package by.epam.akulich.gym.service;

import by.epam.akulich.gym.entity.Appointment;
import by.epam.akulich.gym.entity.Booking;
import by.epam.akulich.gym.service.exception.InvalidInputException;
import by.epam.akulich.gym.service.exception.ServiceException;

import java.util.Enumeration;
import java.util.Map;

/**
 * Service layer interface which contains methods with business logic for appointment.
 *
 * @author Andrey Akulich
 */
public interface AppointmentService {

    /**
     * Returns {@link Appointment} according {@link Booking} identifier.
     *
     * @param bookingId {@link Booking} identifier
     * @return {@link Appointment} instance
     * @throws ServiceException if exception in dao layer occurs
     */
    Appointment getAppointment(int bookingId) throws ServiceException;

    /**
     * Returns appointment {@link Appointment} with all appointments.
     *
     * @return {@link Appointment} instance
     * @throws ServiceException if exception in dao layer occurs
     */
    Appointment getAll() throws ServiceException;

    /**
     * Adds user appointment {@link Appointment} to database.
     *
     * @param bookingId {@link Booking} identifier
     * @param appointment {@link Appointment} instance
     * @throws ServiceException if exception in dao layer occurs
     */
    void addAppointment(int bookingId, Appointment appointment) throws ServiceException;

    /**
     * Parses appointment {@link Appointment} from Map and
     * returns its instance.
     *
     * @param parameterMap a map with parameters
     * @param parameterEnumeration an enumeration with parameter names
     * @return parsed {@link Appointment} instance
     * @throws InvalidInputException if user input invalid parameter
     */
    Appointment parseAppointment(Map<String, String[]> parameterMap, Enumeration parameterEnumeration) throws InvalidInputException;
}
