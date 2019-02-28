package service;

import entity.Appointment;
import entity.ExerciseAppointment;
import entity.ProductAppointment;
import service.exception.ServiceException;

import java.util.List;

public interface AppointmentService {

    Appointment getAppointment(int bookingId) throws ServiceException;
    Appointment getAll() throws ServiceException;
    boolean addAppointment(int id, List<ExerciseAppointment> exerciseAppointments, List<ProductAppointment> productAppointments) throws ServiceException;
}
