package command;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import command.admin.ChangeDiscountCommand;
import command.admin.ChangeRoleCommand;
import command.admin.DeleteUserCommand;
import command.admin.LookOrderCommand;
import command.admin.ReduceVisitsCommand;
import command.common.ChangeLocaleCommand;
import command.common.FeedbackPageCommand;
import command.common.HomePageCommand;
import command.common.LogOutCommand;
import command.exception.NoCommandException;
import command.guest.LogInCommand;
import command.guest.SignInCommand;
import command.trainer.AppointCommand;
import command.trainer.DoAppointCommand;
import command.visitor.AddFeedbackCommand;
import command.visitor.BuyMembershipCommand;
import command.visitor.RefuseAppointmentsCommand;
import entity.Constants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.core.IsInstanceOf.instanceOf;

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
                {Constants.Command.REFUSE_APPOINTMENT, new RefuseAppointmentsCommand(request), request}
        };
    }

    @Test
    @UseDataProvider("getCommandStringCommandData")
    public void getCommand_String_Command(String commandName, Command expected, HttpServletRequest request) throws NoCommandException {
        Command actual = factory.getCommand(commandName, request);

        Assert.assertThat(actual, instanceOf(expected.getClass()));
    }
}