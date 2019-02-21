package command;

import command.admin.ChangeRoleCommand;
import command.admin.DeleteUserCommand;
import command.admin.OpenOrderCommand;
import command.admin.ReduceVisitsCommand;
import command.common.ChangeLocaleCommand;
import command.common.LogInCommand;
import command.common.LogOutCommand;
import command.common.MainPageCommand;
import command.common.RegisterCommand;
import command.common.SignInCommand;
import command.trainer.AppointCommand;
import command.trainer.DoAppointCommand;
import command.visitor.BuyMembershipCommand;
import entity.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public enum CommandFactory {
    INSTANCE;

    public Command getCommand(String command, HttpServletRequest request, HttpServletResponse response){
        switch (command) {
            case Constants.Command.REGISTER:
                return new RegisterCommand(request, response);
            case Constants.Command.SIGN_IN:
                return new SignInCommand(request, response);
            case Constants.Command.LOG_IN:
                return new LogInCommand(request, response);
            case Constants.Command.LOG_OUT:
                return new LogOutCommand(request, response);
            case Constants.Command.DELETE_USER:
                return new DeleteUserCommand(request, response);
            case Constants.Command.CHANGE_ROLE:
                return new ChangeRoleCommand(request, response);
            case Constants.Command.HOME:
                return new MainPageCommand(request, response);
            case Constants.Command.BUY_MEMBERSHIP:
                return new BuyMembershipCommand(request, response);
            case Constants.Command.CHANGE_LOCALE:
                return new ChangeLocaleCommand(request, response);
            case Constants.Command.DO_APPOINT:
                return new DoAppointCommand(request, response);
            case Constants.Command.APPOINT:
                return new AppointCommand(request, response);
            case Constants.Command.OPEN_ORDER:
                return new OpenOrderCommand(request, response);
            case Constants.Command.REDUCE_VISITS:
                return new ReduceVisitsCommand(request, response);
            default:
                throw new IllegalArgumentException(); //TODO MB REDIRECT HOME?
        }
    }
}
