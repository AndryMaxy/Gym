package command.admin;

import command.Command;
import entity.Response;
import entity.Constants;
import service.UserService;
import service.exception.ServiceException;
import service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;

/**
 * Class uses for changing user discount
 * @author Andrey Akulich
 * @see Command
 */
public class ChangeDiscountCommand extends Command {

    /**
     * Instantiates ChangeDiscountCommand
     * @param request current http request
     */
    public ChangeDiscountCommand(HttpServletRequest request) {
        super(request);
    }

    /**
     * Changes user discount
     * @return the {@link Response} instance which contains information about next page
     * @throws ServiceException from the service layer
     */

    @Override
    public Response execute() throws ServiceException {
        String userIdStr = request.getParameter(Constants.Parameter.USER_ID);
        String discountStr = request.getParameter(Constants.Parameter.DISCOUNT);
        UserService service = UserServiceImpl.getInstance();
        service.changeDiscount(userIdStr, discountStr);
        return new Response("/order?userId=" + userIdStr, true);
    }
}
