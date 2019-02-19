package command.trainer;

import command.Command;
import command.Response;
import entity.Appointment;
import entity.Constants;
import entity.Exercise;
import entity.Product;
import service.AppointmentService;
import service.exception.ServiceException;
import service.impl.AppointmentServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

public class AppointCommand extends Command {

    public AppointCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public Response execute() throws ServiceException {
        String userIdStr = request.getParameter(Constants.Parameter.USER_ID);
        int userId = Integer.parseInt(userIdStr);
        Enumeration enumeration = request.getParameterNames();
        enumeration.nextElement();
        enumeration.nextElement();
        List<Exercise> exercises = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        Exercise exercise = null;
        Product product;
        while (enumeration.hasMoreElements()) {
            String element = (String) enumeration.nextElement();
            String[] split = element.split("-");
            String name = split[0];
            int id = Integer.parseInt(split[1]);
            String value = request.getParameter(element);
            switch (name) {
                case "repCount":
                    exercise = new Exercise();
                    exercise.setId(id);
                    int repCount = Integer.parseInt(value);
                    exercise.setRepCount(repCount);
                    break;
                case "setCount":
                    int setCount = Integer.parseInt(value);
                    Optional.ofNullable(exercise)
                            .ifPresent(ex -> ex.setSetCount(setCount));
                    break;
                case "weight":
                    int weight = Integer.parseInt(value);
                    Optional.ofNullable(exercise)
                            .ifPresent(ex -> ex.setWeight(weight));
                    exercises.add(exercise);
                    break;
                case "gram":
                    product = new Product();
                    product.setId(id);
                    int gram = Integer.parseInt(value);
                    product.setGramInDay(gram);
                    products.add(product);
            }
        }
        Appointment appointment = new Appointment();
        appointment.setExercises(exercises);
        appointment.setProducts(products);
        AppointmentService service = AppointmentServiceImpl.getInstance();
        service.addAppointment(userId, appointment);
        return new Response(Constants.URL.HOME, true);
    }
}
