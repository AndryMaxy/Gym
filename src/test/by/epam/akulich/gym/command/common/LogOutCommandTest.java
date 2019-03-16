package by.epam.akulich.gym.command.common;

import by.epam.akulich.gym.entity.Constants;
import by.epam.akulich.gym.entity.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
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
        //given
        Response expected = new Response(Constants.URL.ROOT, Constants.ResponseStatus.REDIRECT);

        //when
        Response actual = command.execute();

        //then
        verify(session).invalidate();
        assertEquals(expected, actual);
    }
}