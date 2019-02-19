package service;

import entity.Appointment;
import service.exception.ServiceException;

public interface AppointmentService {

    Appointment getAppointment(int id) throws ServiceException;
    Appointment getAll() throws ServiceException;
    boolean addAppointment(int userId, Appointment appointment) throws ServiceException;
}
