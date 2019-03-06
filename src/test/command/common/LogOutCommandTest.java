package command.common;

import entity.Constants;
import entity.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Locale;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LogOutCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    private LogOutCommand command;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        when(request.getSession()).thenReturn(session);
        command = new LogOutCommand(request);
    }

    @Test
    public void execute_Request_Invalidated() {
        Response expected = new Response(Constants.URL.ROOT, Constants.ResponseStatus.REDIRECT);

        Response actual = command.execute();

        verify(session).invalidate();
        Assert.assertEquals(expected, actual);
    }
}