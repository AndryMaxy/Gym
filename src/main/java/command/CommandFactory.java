package command;

import command.forward.MainPageCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public enum CommandFactory {
    INSTANCE;

    public Command getCommand(String command, HttpServletRequest request, HttpServletResponse response){
        switch (command) {
            case Commands.SIGN_IN:
                return new SignInCommand(request, response);
            case Commands.LOG_IN:
                return new LogInCommand(request, response);
            case Commands.LOG_OUT:
                return new LogOutCommand(request, response);
            case Commands.DELETE_USER:
                return new DeleteUserCommand(request, response);
            case Commands.OPEN:
                return new OpenCommand(request, response);
            case Commands.MAIN:
                return new MainPageCommand(request, response);
            case Commands.BUY_MEMBERSHIP:
                return new BuyMembershipCommand(request, response);
            default:
                throw new IllegalArgumentException();
        }
    }
}
