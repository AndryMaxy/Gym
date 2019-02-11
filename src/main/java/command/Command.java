package command;

import javax.servlet.http.HttpServletRequest;

public interface Command {

    Status execute(HttpServletRequest request);
}
