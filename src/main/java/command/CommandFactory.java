package command;

import command.admin.ChangeDiscountCommand;
import command.admin.ChangeRoleCommand;
import command.admin.DeleteUserCommand;
import command.admin.OpenOrderCommand;
import command.admin.ReduceVisitsCommand;
import command.common.AboutPageCommand;
import command.common.ChangeLocaleCommand;
import command.common.FeedbackPageCommand;
import command.guest.LogInCommand;
import command.common.LogOutCommand;
import command.common.HomePageCommand;
import command.guest.RegisterCommand;
import command.guest.SignInCommand;
import command.exception.NoCommandException;
import command.trainer.AppointCommand;
import command.trainer.DoAppointCommand;
import command.visitor.AddFeedbackCommand;
import command.visitor.BuyMembershipCommand;
import entity.Constants;

import javax.servlet.http.HttpServletRequest;

public enum CommandFactory {
    INSTANCE;

    public Command getCommand(String command, HttpServletRequest request) throws NoCommandException {
        switch (command) {
            case Constants.Command.REGISTER:
                return new RegisterCommand(request);
            case Constants.Command.SIGN_IN:
                return new SignInCommand(request);
            case Constants.Command.LOG_IN:
                return new LogInCommand(request);
            case Constants.Command.LOG_OUT:
                return new LogOutCommand(request);
            case Constants.Command.DELETE_USER:
                return new DeleteUserCommand(request);
            case Constants.Command.CHANGE_ROLE:
                return new ChangeRoleCommand(request);
            case Constants.Command.HOME:
                return new HomePageCommand(request);
            case Constants.Command.BUY_MEMBERSHIP:
                return new BuyMembershipCommand(request);
            case Constants.Command.CHANGE_LOCALE:
                return new ChangeLocaleCommand(request);
            case Constants.Command.DO_APPOINT:
                return new DoAppointCommand(request);
            case Constants.Command.APPOINT:
                return new AppointCommand(request);
            case Constants.Command.OPEN_ORDER:
                return new OpenOrderCommand(request);
            case Constants.Command.REDUCE_VISITS:
                return new ReduceVisitsCommand(request);
            case Constants.Command.FEEDBACK:
                return new FeedbackPageCommand(request);
            case Constants.Command.ADD_FEEDBACK:
                return new AddFeedbackCommand(request);
            case Constants.Command.CHANGE_DISCOUNT:
                return new ChangeDiscountCommand(request);
            case Constants.Command.ABOUT:
                return new AboutPageCommand(request);
            default:
                throw new NoCommandException();
        }
    }
}
