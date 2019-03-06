package service;

import entity.Appointment;
import entity.ExerciseAppointment;
import entity.ProductAppointment;
import service.exception.InvalidInputException;
import service.exception.ServiceException;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;

public interface AppointmentService {

    Appointment getAppointment(int bookingId) throws ServiceException;
    Appointment getAll() throws ServiceException;
    void addAppointment(int id, Appointment appointment) throws ServiceException;
    Appointment parseAppointments(Map<String, String[]> parameterMap, Enumeration parameterEnumeration) throws ServiceException, InvalidInputException;
}
