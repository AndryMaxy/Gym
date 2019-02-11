package command;

import javax.servlet.http.HttpServletRequest;

public class LogOutCommand implements Command {

    @Override
    public Status execute(HttpServletRequest request) {
        return null;
    }
}
