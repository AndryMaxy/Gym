package command.trainer;

import command.Command;
import entity.Booking;
import entity.Response;
import entity.Constants;
import entity.ExerciseAppointment;
import entity.ProductAppointment;
import service.AppointmentService;
import service.BookingService;
import service.exception.ServiceException;
import service.impl.AppointmentServiceImpl;
import service.impl.BookingServiceImpl;
import validator.ParameterValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

public class AppointCommand extends Command {

    private ParameterValidator validator = ParameterValidator.getInstance();
    private AppointmentService appointmentService = AppointmentServiceImpl.getInstance();

    public AppointCommand(HttpServletRequest request) {
        super(request);
    }

    @Override
    public Response execute() throws ServiceException {
        String userIdStr = request.getParameter(Constants.Parameter.USER_ID);
        Enumeration enumeration = request.getParameterNames();
        enumeration.nextElement();
        enumeration.nextElement();
        List<ExerciseAppointment> exerciseAppointments = new ArrayList<>();
        List<ProductAppointment> productAppointments = new ArrayList<>();
        ExerciseAppointment exerciseAppointment = null;
        ProductAppointment productAppointment;
        while (enumeration.hasMoreElements()) {
            String element = (String) enumeration.nextElement();
            String[] split = element.split("-");
            String name = split[0];
            int id = Integer.parseInt(split[1]);
            String valueStr = request.getParameter(element);
            if (valueStr == null || valueStr.equals("") || !validator.validateNumber(valueStr)) {
                continue;
            }
            int value = Integer.parseInt(valueStr);
            switch (name) {
                case "repCount":
                    exerciseAppointment = new ExerciseAppointment();
                    exerciseAppointment.setId(id);
                    exerciseAppointment.setRepCount(value);
                    break;
                case "setCount":
                    Optional.ofNullable(exerciseAppointment)
                            .ifPresent(ex -> ex.setSetCount(value));
                    break;
                case "weight":
                    Optional.ofNullable(exerciseAppointment)
                            .ifPresent(ex -> ex.setWeight(value));
                    exerciseAppointments.add(exerciseAppointment);
                    break;
                case "gram":
                    productAppointment = new ProductAppointment();
                    productAppointment.setId(id);
                    productAppointment.setGramInDay(value);
                    productAppointments.add(productAppointment);
            }
        }
        BookingService service = BookingServiceImpl.getInstance();
        Booking booking = service.getBookingByUserId(userIdStr);
        appointmentService.addAppointment(booking.getId(), exerciseAppointments, productAppointments);
        return new Response(Constants.URL.HOME, true);
    }
}
