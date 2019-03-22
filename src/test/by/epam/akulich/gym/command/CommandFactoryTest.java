package by.epam.akulich.gym.command;

import by.epam.akulich.gym.command.visitor.RefillPageCommand;
import by.epam.akulich.gym.command.visitor.UpBalanceCommand;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import by.epam.akulich.gym.command.admin.ChangeDiscountCommand;
import by.epam.akulich.gym.command.admin.ChangeRoleCommand;
import by.epam.akulich.gym.command.admin.DeleteUserCommand;
import by.epam.akulich.gym.command.admin.LookOrderCommand;
import by.epam.akulich.gym.command.admin.ReduceVisitsCommand;
import by.epam.akulich.gym.command.common.ChangeLocaleCommand;
import by.epam.akulich.gym.command.common.FeedbackPageCommand;
import by.epam.akulich.gym.command.common.HomePageCommand;
import by.epam.akulich.gym.command.common.LogOutCommand;
import by.epam.akulich.gym.command.exception.NoCommandException;
import by.epam.akulich.gym.command.guest.LogInCommand;
import by.epam.akulich.gym.command.guest.SignInCommand;
import by.epam.akulich.gym.command.trainer.AppointCommand;
import by.epam.akulich.gym.command.trainer.DoAppointCommand;
import by.epam.akulich.gym.command.visitor.AddFeedbackCommand;
import by.epam.akulich.gym.command.visitor.BuyMembershipCommand;
import by.epam.akulich.gym.command.visitor.RefuseAppointmentsCommand;
import by.epam.akulich.gym.entity.Constants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

@RunWith(DataProviderRunner.class)
public class CommandFactoryTest {

    private CommandFactory factory;

    @Before
    public void setUp() {
        factory = CommandFactory.INSTANCE;
    }

    @DataProvider
    public static Object[][] getCommandStringCommandData() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        return new Object[][]{
                {Constants.Command.SIGN_IN, new SignInCommand(request), request},
                {Constants.Command.LOG_IN, new LogInCommand(request), request},
                {Constants.Command.LOG_OUT, new LogOutCommand(request), request},
                {Constants.Command.DELETE_USER, new DeleteUserCommand(request), request},
                {Constants.Command.CHANGE_ROLE, new ChangeRoleCommand(request), request},
                {Constants.Command.HOME, new HomePageCommand(request), request},
                {Constants.Command.BUY_MEMBERSHIP, new BuyMembershipCommand(request), request},
                {Constants.Command.CHANGE_LOCALE, new ChangeLocaleCommand(request), request},
                {Constants.Command.DO_APPOINT, new DoAppointCommand(request), request},
                {Constants.Command.APPOINT, new AppointCommand(request), request},
                {Constants.Command.LOOK_ORDER, new LookOrderCommand(request), request},
                {Constants.Command.REDUCE_VISITS, new ReduceVisitsCommand(request), request},
                {Constants.Command.FEEDBACK, new FeedbackPageCommand(request), request},
                {Constants.Command.ADD_FEEDBACK, new AddFeedbackCommand(request), request},
                {Constants.Command.CHANGE_DISCOUNT, new ChangeDiscountCommand(request), request},
                {Constants.Command.REFUSE_APPOINTMENT, new RefuseAppointmentsCommand(request), request},
                {Constants.Command.REFILL, new RefillPageCommand(request), request},
                {Constants.Command.UP_BALANCE, new UpBalanceCommand(request), request}
        };
    }

    @Test
    @UseDataProvider("getCommandStringCommandData")
    public void getCommand_String_Command(String commandName, Command expected, HttpServletRequest request) throws NoCommandException {
        Command actual = factory.getCommand(commandName, request);

        assertThat(actual, instanceOf(expected.getClass()));
    }
}