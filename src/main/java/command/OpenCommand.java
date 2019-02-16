package command;

import service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class OpenCommand extends Command {

    public OpenCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }


    @Override
    public Response execute() throws ServiceException {
        HttpSession session = request.getSession();
        String uri = (String) session.getAttribute("open");
        System.out.println(uri);
        return new Response(uri, false);
    }
}
