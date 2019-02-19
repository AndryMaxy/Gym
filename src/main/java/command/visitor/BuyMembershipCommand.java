package command.visitor;

import command.Command;
import command.Response;
import entity.Constants;
import entity.Membership;
import entity.User;
import service.BookingService;
import service.UserService;
import service.exception.ServiceException;
import service.impl.BookingServiceImpl;
import service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BuyMembershipCommand extends Command {


    public BuyMembershipCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public Response execute() throws ServiceException {
        String membershipStr = request.getParameter(Constants.Parameter.MEMBERSHIP);
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute(Constants.Parameter.USER_ID);
        UserService userService = UserServiceImpl.getInstance();
        User user = userService.getUser(userId);
        int discount = user.getDiscount();
        int balance = user.getBalance();
        Membership membership = Membership.valueOf(membershipStr);
        int price = membership.getPrice();
        int cost = price / 100 * (100 - discount);
        if (balance < cost) {
            return new Response(Constants.URL.HOME, true); //TODO НУЖЕН МЕССЕДЖ НА НЕУДАЧУ
        }
        int newBalance = balance - cost;
        BookingService bookingService = BookingServiceImpl.getInstance();
        boolean result = bookingService.buyMembership(userId, newBalance, membership);
        if (!result) {
            return new Response(Constants.URL.HOME, true); //TODO НУЖЕН МЕССЕДЖ НА НЕУДАЧУ
        }
        return new Response(Constants.URL.HOME, true);
    }
}
