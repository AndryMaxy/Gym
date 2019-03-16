package by.epam.akulich.gym.command;

import by.epam.akulich.gym.command.admin.ChangeDiscountCommand;
import by.epam.akulich.gym.command.admin.ChangeRoleCommand;
import by.epam.akulich.gym.command.admin.DeleteUserCommand;
import by.epam.akulich.gym.command.admin.LookOrderCommand;
import by.epam.akulich.gym.command.admin.ReduceVisitsCommand;
import by.epam.akulich.gym.command.common.ChangeLocaleCommand;
import by.epam.akulich.gym.command.common.FeedbackPageCommand;
import by.epam.akulich.gym.command.guest.LogInCommand;
import by.epam.akulich.gym.command.common.LogOutCommand;
import by.epam.akulich.gym.command.common.HomePageCommand;
import by.epam.akulich.gym.command.guest.SignInCommand;
import by.epam.akulich.gym.command.exception.NoCommandException;
import by.epam.akulich.gym.command.trainer.AppointCommand;
import by.epam.akulich.gym.command.trainer.DoAppointCommand;
import by.epam.akulich.gym.command.visitor.AddFeedbackCommand;
import by.epam.akulich.gym.command.visitor.BuyMembershipCommand;
import by.epam.akulich.gym.command.visitor.RefuseAppointmentsCommand;
import by.epam.akulich.gym.entity.Constants;

import javax.servlet.http.HttpServletRequest;

/**
 * The class designed by Factory and Singleton patterns.
 * It is used for taking instance of {@link Command}.
 *
 * @author Andrey Akulich
 */
public enum CommandFactory {
    INSTANCE;

    /**
     * Takes instance of {@link Command}.
     * @param command command name
     * @param request current http request
     * @return {@link Command} instance
     * @throws NoCommandException if command does not exist
     */
    public Command getCommand(String command, HttpServletRequest request) throws NoCommandException {
        switch (command) {
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
            case Constants.Command.LOOK_ORDER:
                return new LookOrderCommand(request);
            case Constants.Command.REDUCE_VISITS:
                return new ReduceVisitsCommand(request);
            case Constants.Command.FEEDBACK:
                return new FeedbackPageCommand(request);
            case Constants.Command.ADD_FEEDBACK:
                return new AddFeedbackCommand(request);
            case Constants.Command.CHANGE_DISCOUNT:
                return new ChangeDiscountCommand(request);
            case Constants.Command.REFUSE_APPOINTMENT:
                return new RefuseAppointmentsCommand(request);
            default:
                throw new NoCommandException();
        }
    }
}
