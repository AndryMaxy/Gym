package by.epam.akulich.gym.command.visitor;

import by.epam.akulich.gym.command.Command;
import by.epam.akulich.gym.entity.Response;
import by.epam.akulich.gym.entity.Booking;
import by.epam.akulich.gym.entity.Constants;
import by.epam.akulich.gym.service.BookingService;
import by.epam.akulich.gym.service.exception.InvalidInputException;
import by.epam.akulich.gym.service.exception.ServiceException;
import by.epam.akulich.gym.service.impl.BookingServiceImpl;
import by.epam.akulich.gym.service.ParameterValidator;

import javax.servlet.http.HttpServletRequest;

/**
 * The class is used for adding feedback.
 *
 * @author Andrey Akulich
 * @see Command
 */
public class AddFeedbackCommand extends Command {

    /**
     * Booking service instance.
     */
    private BookingService bookingService;

    /**
     * Instantiates a new AddFeedbackCommand.
     *
     * @param request current http request
     */
    public AddFeedbackCommand(HttpServletRequest request) {
        super(request);
        this.bookingService = BookingServiceImpl.getInstance();
    }

    /**
     * Instantiates AddFeedbackCommand. This constructor uses for tests.
     *
     * @param request current http request
     * @param bookingService booking service instance
     */
    public AddFeedbackCommand(HttpServletRequest request, BookingService bookingService) {
        super(request);
        this.bookingService = bookingService;
    }

    /**
     * Adds visitor's feedback.
     *
     * @return the {@link Response} instance which contains information about next page
     * @throws ServiceException from the userService layer
     * @throws InvalidInputException  if user enter invalid data
     */
    @Override
    public Response execute() throws ServiceException, InvalidInputException {
        String userId = (String) session.getAttribute(Constants.Parameter.USER_ID);
        String feedbackStr = request.getParameter(Constants.Parameter.FEEDBACK_AREA);
        boolean result = bookingService.setFeedback(userId, feedbackStr);
        if (!result) {
            return new Response(Constants.URL.FEEDBACK, Constants.ResponseStatus.INCORRECT_INPUT);
        }
        return new Response(Constants.URL.FEEDBACK, Constants.ResponseStatus.REDIRECT);
    }
}
