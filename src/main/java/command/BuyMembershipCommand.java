package command;

import entity.Membership;
import entity.User;
import service.UserService;
import service.exception.ServiceException;
import service.impl.UserServiceImpl;
import util.exception.EncoderException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BuyMembershipCommand extends Command {

    private String url = "/controller?command=main";

    public BuyMembershipCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public Response execute() throws ServiceException {
        String membershipStr = request.getParameter("membership");
        HttpSession session = request.getSession();
        int id = (int) session.getAttribute("userId");
        UserService userService = UserServiceImpl.getInstance();
        boolean result = userService.buyMembership(id, membershipStr);
        if (!result) {
            return new Response(url, true); //TODO НУЖЕН МЕССЕДЖ НА НЕУДАЧУ
        }
        return new Response(url, true);
    }
}
