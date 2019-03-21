package by.epam.akulich.gym.service.impl;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import by.epam.akulich.gym.dao.UserDAO;
import by.epam.akulich.gym.dao.exception.DAOException;
import by.epam.akulich.gym.entity.User;
import by.epam.akulich.gym.entity.UserRole;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import by.epam.akulich.gym.service.exception.InvalidInputException;
import by.epam.akulich.gym.service.exception.ServiceException;
import by.epam.akulich.gym.util.Encoder;
import by.epam.akulich.gym.util.exception.EncoderException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(DataProviderRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserDAO userDAO;

    @Mock
    private Encoder encoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @DataProvider
    public static Object[][] add_User_Added_Data(){
        User user1 = new User();
        user1.setName("Petr");
        user1.setBalance(800);
        User user2 = new User();
        user2.setName("Маша");
        user2.setSurname("Шарапова");
        User user3 = new User();
        user3.setName("Valera");
        user3.setDiscount(25);
        return new Object[][]{
                {user1},
                {user2},
                {user3}
        };
    }

    @Test
    @UseDataProvider("add_User_Added_Data")
    public void add_User_Added(User expected) throws ServiceException, DAOException {
        //given
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        //when
        userService.add(expected);

        //then
        verify(userDAO, times(1)).add(userCaptor.capture());
        assertEquals(expected, userCaptor.getValue());
    }

    @Test(expected = ServiceException.class)
    public void add_User_ServiceException() throws ServiceException, DAOException {
        //given
        User user = new User();
        doThrow(new DAOException()).when(userDAO).add(user);

        //when
        userService.add(user);

        //then
        //expected ServiceException
    }

    @DataProvider
    public static Object[][] getAll_DAO_UserList_Data(){
        User user1 = new User();
        user1.setName("Petr");
        user1.setBalance(800);
        List<User> userList1 = new ArrayList<>();
        userList1.add(user1);
        User user2 = new User();
        user2.setName("Маша");
        user2.setSurname("Шарапова");
        List<User> userList2 = new ArrayList<>();
        userList2.add(user2);
        User user3 = new User();
        user3.setName("Valera");
        user3.setDiscount(25);
        List<User> userList3 = new ArrayList<>();
        userList3.add(user3);
        return new Object[][]{
                {userList1},
                {userList2},
                {userList3}
        };
    }

    @Test
    @UseDataProvider("getAll_DAO_UserList_Data")
    public void getAll_DAO_UserList(List<User> expected) throws ServiceException, DAOException {
        //given
        when(userDAO.getAll()).thenReturn(expected);

        //when
        List<User> actual = userService.getAll();

        //then
        assertEquals(expected, actual);
    }

    @Test(expected = ServiceException.class)
    public void getAll_DAO_ServiceException() throws ServiceException, DAOException {
        //given
        when(userDAO.getAll()).thenThrow(new DAOException());

        //when
        userService.getAll();

        //then
        //expected ServiceException
    }

    @DataProvider
    public static Object[][] isUserLoginExist_String_Data(){
        return new Object[][]{
                {"userName1", new User(), true},
                {"valera", new User(), true},
                {"petrovich", null, false},
                {"sergeq9", null, false}
        };
    }

    @Test
    @UseDataProvider("isUserLoginExist_String_Data")
    public void isUserLogin_Exist_String(String login, User user, boolean expected) throws ServiceException, DAOException, InvalidInputException {
        //given
        when(userDAO.getByLogin(login)).thenReturn(user);

        //when
        boolean actual = userService.isUserLoginExist(login);

        //then
        assertEquals(expected, actual);
    }

    @Test(expected = InvalidInputException.class)
    public void isUserLoginExist_String_InvalidInputException() throws ServiceException, DAOException, InvalidInputException {
        //given
        String login = "1";

        //when
        userService.isUserLoginExist(login);

        //then
        //expected InvalidInputException
    }

    @Test(expected = ServiceException.class)
    public void isUserLoginExist_String_ServiceException() throws ServiceException, DAOException, InvalidInputException {
        //given
        String login = "userName1";
        when(userDAO.getByLogin(login)).thenThrow(new DAOException());

        //when
        userService.isUserLoginExist(login);

        //then
        //expected ServiceException
    }

    @DataProvider
    public static Object[][] getUser_String_User_Data(){
        User user1 = new User();
        user1.setName("Petr");
        user1.setBalance(800);
        User user2 = new User();
        user2.setName("Маша");
        user2.setSurname("Шарапова");
        User user3 = new User();
        user3.setName("Valera");
        user3.setDiscount(25);
        return new Object[][]{
                {"10", 10,user1},
                {"243", 243, user2},
                {"5433", 5433, user3}
        };
    }

    @Test
    @UseDataProvider("getUser_String_User_Data")
    public void getUser_String_User(String idStr, int id, User expected) throws ServiceException, DAOException, InvalidInputException {
        //given
        when(userDAO.getById(id)).thenReturn(expected);

        //when
        User actual = userService.getUser(idStr);

        //then
        assertEquals(expected, actual);
    }

    @Test(expected = InvalidInputException.class)
    public void getUser_String_InvalidInputException() throws ServiceException, DAOException, InvalidInputException {
        //given
        String id = "ten";

        //when
        userService.getUser(id);

        //then
        //expected InvalidInputException

    }

    @Test(expected = ServiceException.class)
    public void getUser_String_ServiceException() throws ServiceException, DAOException, InvalidInputException {
        //given
        String id = "10";
        when(userDAO.getById(10)).thenThrow(new DAOException());

        //when
        userService.getUser(id);

        //then
        //expected ServiceException
    }

    @DataProvider
    public static Object[][] logIn_StringAndCharArray_UserId_Data(){
        User user1 = new User();
        int id1 = 10;
        user1.setId(id1);
        user1.setLogin("userName1");
        user1.setHash("hash1");
        user1.setSalt("salt1");
        char[] password1 = "password1".toCharArray();
        User user2 = new User();
        int id2 = 154;
        user2.setId(id2);
        user2.setLogin("user211");
        user2.setHash("hash1123");
        user2.setSalt("salt1123");
        char[] password2 = "password213".toCharArray();
        return new Object[][]{
                {id1, password1, user1},
                {id2, password2, user2},
        };
    }

    @Test
    @UseDataProvider("logIn_StringAndCharArray_UserId_Data")
    public void logIn_StringAndCharArray_UserId(int expected, char[] password, User user) throws ServiceException, DAOException, InvalidInputException, EncoderException {
        //given
        when(userDAO.getByLogin(user.getLogin())).thenReturn(user);
        when(encoder.check(password, user.getHash().getBytes(), user.getSalt().getBytes())).thenReturn(true);

        //when
        int actual = userService.logIn(user.getLogin(), password);

        //then
        assertEquals(expected, actual);
    }

    @DataProvider
    public static Object[][] logIn_StringAndCharArray_Zero_Data(){
        return new Object[][]{
                {0, "userName1", "password1".toCharArray()},
                {0, "user211", "password213".toCharArray()},
        };
    }

    @Test
    @UseDataProvider("logIn_StringAndCharArray_Zero_Data")
    public void logIn_StringAndCharArray_Zero(int expected, String login, char[] password) throws ServiceException, DAOException, InvalidInputException, EncoderException {
        //given
        when(userDAO.getByLogin(login)).thenReturn(null);

        //when
        int actual = userService.logIn(login, password);

        //then
        assertEquals(expected, actual);
    }

    @Test(expected = InvalidInputException.class)
    public void logIn_StringAndCharArray_InvalidInputException() throws ServiceException, DAOException, InvalidInputException, EncoderException {
        //given
        String login = "userName1";
        char[] password = "pass1".toCharArray();

        //when
        userService.logIn(login, password);

        //then
        //expected InvalidInputException
    }

    @Test(expected = ServiceException.class)
    public void logIn_StringAndCharArray_ServiceException() throws ServiceException, DAOException, InvalidInputException, EncoderException {
        //given
        int expected = 10;
        String login = "userName1";
        char[] password = "password1".toCharArray();
        String hash = "hash";
        String salt = "salt";
        User user = new User();
        user.setLogin(login);
        user.setHash(hash);
        user.setSalt(salt);
        user.setId(expected);
        when(userDAO.getByLogin(login)).thenThrow(new DAOException());
        when(encoder.check(password, hash.getBytes(), salt.getBytes())).thenReturn(true);

        //when
        userService.logIn(login, password);

        //then
        //expected ServiceException
    }

    @DataProvider
    public static Object[][] getVisitors_DAO_UserList_Data(){
        User user1 = new User();
        user1.setName("Petr");
        user1.setBalance(800);
        user1.setRole(UserRole.VISITOR);
        List<User> userList1 = new ArrayList<>();
        userList1.add(user1);
        User user2 = new User();
        user2.setName("Маша");
        user2.setSurname("Шарапова");
        user2.setRole(UserRole.VISITOR);
        List<User> userList2 = new ArrayList<>();
        userList2.add(user2);
        User user3 = new User();
        user3.setName("Valera");
        user3.setDiscount(25);
        user3.setRole(UserRole.VISITOR);
        List<User> userList3 = new ArrayList<>();
        userList3.add(user3);
        return new Object[][]{
                {userList1},
                {userList2},
                {userList3}
        };
    }

    @Test
    @UseDataProvider("getVisitors_DAO_UserList_Data")
    public void getVisitors_DAO_UserList(List<User> expected) throws ServiceException, DAOException {
        //given
        when(userDAO.getVisitorsWithoutApp()).thenReturn(expected);

        //when
        List<User> actual = userService.getNewVisitors();

        //then
        assertEquals(expected, actual);
    }

    @Test(expected = ServiceException.class)
    public void getVisitors_DAO_ServiceException() throws ServiceException, DAOException {
        when(userDAO.getVisitorsWithoutApp()).thenThrow(new DAOException());

        userService.getNewVisitors();

        //then
        //expected ServiceException
    }

    @DataProvider
    public static Object[][] getNewVisitor_String_UserData(){
        User user1 = new User();
        user1.setName("Petr");
        user1.setBalance(800);
        User user2 = new User();
        user2.setName("Маша");
        user2.setSurname("Шарапова");
        User user3 = new User();
        user3.setName("Valera");
        user3.setDiscount(25);
        return new Object[][]{
                {"10", 10,user1},
                {"243", 243, user2},
                {"5433", 5433, user3}
        };
    }

    @Test
    @UseDataProvider("getNewVisitor_String_UserData")
    public void getNewVisitor_String_User(String idStr, int id, User expected) throws ServiceException, InvalidInputException, DAOException {
        //given
        when(userDAO.getVisitorWithoutApp(id)).thenReturn(expected);

        //when
        User actual = userService.getNewVisitor(idStr);

        //then
        assertEquals(expected, actual);
    }

    @Test(expected = ServiceException.class)
    public void getNewVisitor_String_ServiceException() throws ServiceException, InvalidInputException, DAOException {
        //given
        String id = "25";
        when(userDAO.getVisitorWithoutApp(25)).thenThrow(new DAOException());

        //when
        userService.getNewVisitor(id);

        //then
        //expected ServiceException
    }

    @Test(expected = InvalidInputException.class)
    public void getNewVisitor_String_InvalidInputException() throws ServiceException, InvalidInputException, DAOException {
        //given
        String id = "25l";

        //when
        userService.getNewVisitor(id);

        //then
        //expected InvalidInputException
    }

    @DataProvider
    public static Object[][] delete_String_Deleted_Data(){
        return new Object[][] {
                {24, "24"},
                {487, "487"},
                {1527, "1527"}
        };
    }

    @Test
    @UseDataProvider("delete_String_Deleted_Data")
    public void delete_String_Deleted(int id, String idStr) throws ServiceException, DAOException, InvalidInputException {
        //given
        //form data provider

        //when
        userService.delete(idStr);

        //then
        verify(userDAO).delete(id);
    }

    @Test(expected = ServiceException.class)
    public void delete_String_ServiceException() throws ServiceException, DAOException, InvalidInputException {
        //given
        String id = "24";
        doThrow(new DAOException()).when(userDAO).delete(24);

        //when
        userService.delete(id);

        //then
        //expected ServiceException
    }

    @Test(expected = InvalidInputException.class)
    public void delete_String_InvalidInputException() throws ServiceException, InvalidInputException {
        //given
        String id = "twenty four";

        //when
        userService.delete(id);

        //then
        //expected InvalidInputException
    }

    @DataProvider
    public static Object[][] createUser_UserFields_User_Data() {
        User user1 = new User();
        user1.setName("valera");
        user1.setSurname("petrovich");
        user1.setLogin("userName1");
        String hash1 = "hash1";
        user1.setHash(hash1);
        String salt1 = "salt1";
        user1.setSalt(salt1);
        String[] encoded1 = {hash1, salt1};
        user1.setRole(UserRole.VISITOR);
        char[] password1 = "password1".toCharArray();

        User user2 = new User();
        user2.setName("Katya");
        user2.setSurname("Ivanova");
        user2.setLogin("user211");
        String hash2 = "hash1123";
        user2.setHash(hash2);
        String salt2 = "salt1123";
        user2.setSalt(salt2);
        String[] encoded2 = {hash2, salt2};
        user2.setRole(UserRole.VISITOR);
        char[] password2 = "password213".toCharArray();
        return new Object[][]{
                {password1, encoded1, user1},
                {password2, encoded2, user2}
        };
    }

    @Test
    @UseDataProvider("createUser_UserFields_User_Data")
    public void createUser_UserFields_User(char[] password, String[] encoded, User expected) throws ServiceException, InvalidInputException, EncoderException {
        //given
        String login = expected.getLogin();
        String name = expected.getName();
        String surname = expected.getSurname();
        when(encoder.encode(password)).thenReturn(encoded);

        //when
        User actual = userService.createUser(login, password, name, surname);

        //then
        assertEquals(expected, actual);
    }

    @Test(expected = ServiceException.class)
    public void createUser_UserFields_ServiceException() throws ServiceException, InvalidInputException, EncoderException {
        //given
        String login = "userName1";
        char[] password = "password1".toCharArray();
        String name = "Петр";
        String surname = "Игнатьев";
        when(encoder.encode(password)).thenThrow(new EncoderException());

        //when
        userService.createUser(login, password, name, surname);

        //then
        //expected ServiceException
    }

    @Test(expected = InvalidInputException.class)
    public void createUser_UserFields_InvalidInputException() throws ServiceException, InvalidInputException, EncoderException {
        //given
        String login = "userName1";
        char[] password = "password1".toCharArray();
        String name = "Петр1";
        String surname = "Игнатьев";

        //when
        userService.createUser(login, password, name, surname);

        //then
        //expected InvalidInputException
    }

    @DataProvider
    public static Object[][] changeRole_Strings_RoleChanged_Data(){
        User user1 = new User();
        user1.setId(14);
        user1.setRole(UserRole.VISITOR);
        User user2 = new User();
        user2.setId(48);
        user1.setRole(UserRole.ADMIN);
        User user3 = new User();
        user3.setId(578);
        user3.setRole(UserRole.TRAINER);
        return new Object[][] {
                {14, "14", "VISITOR", user1},
                {48, "48", "ADMIN", user2},
                {578, "578", "TRAINER", user3}
        };
    }

    @Test
    @UseDataProvider("changeRole_Strings_RoleChanged_Data")
    public void changeRole_Strings_RoleChanged(int id, String idStr, String roleStr, User expected) throws ServiceException, DAOException, InvalidInputException {
        //given
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        when(userDAO.getById(id)).thenReturn(expected);

        //when
        userService.changeRole(idStr, roleStr);

        //then
        verify(userDAO, times(1)).update(userCaptor.capture());
        assertEquals(expected, userCaptor.getValue());
    }

    @Test(expected = ServiceException.class)
    public void changeRole_Strings_ServiceException() throws ServiceException, DAOException, InvalidInputException {
        //given
        String id = "14";
        String roleStr = "ADMIN";
        when(userDAO.getById(14)).thenThrow(new DAOException());

        //when
        userService.changeRole(id, roleStr);

        //then
        //expected ServiceException
    }

    @Test(expected = InvalidInputException.class)
    public void changeRole_Strings_InvalidInputException() throws ServiceException, InvalidInputException {
        //given
        String id = "twelve";
        String roleStr = "ADMIN";

        //when
        userService.changeRole(id, roleStr);

        //then
        //expected InvalidInputException
    }

    @DataProvider
    public static Object[][] changeDiscount_Strings_ChangedDiscount_Data(){
        User user1 = new User();
        user1.setId(14);
        user1.setDiscount(25);
        User user2 = new User();
        user2.setId(48);
        user2.setDiscount(10);
        User user3 = new User();
        user3.setId(578);
        user3.setDiscount(0);
        return new Object[][] {
                {14, "14", "25", user1},
                {48, "48", "10", user2},
                {578, "578", "0", user3}
        };
    }

    @Test
    @UseDataProvider("changeDiscount_Strings_ChangedDiscount_Data")
    public void changeDiscount_Strings_ChangedDiscount(int id, String idStr, String discount, User expected) throws ServiceException, DAOException, InvalidInputException {
        //given
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        when(userDAO.getById(id)).thenReturn(expected);

        //when
        userService.changeDiscount(idStr, discount);

        //then
        verify(userDAO).update(userCaptor.capture());
        assertEquals(expected, userCaptor.getValue());
    }

    @Test(expected = ServiceException.class)
    public void changeDiscount_Strings_ServiceException() throws ServiceException, DAOException, InvalidInputException {
        //given
        String id = "14";
        String discount = "25";
        when(userDAO.getById(14)).thenThrow(new DAOException());

        //when
        userService.changeDiscount(id, discount);

        //then
        //expected ServiceException
    }

    @Test(expected = InvalidInputException.class)
    public void changeDiscount_Strings_InvalidInputException() throws ServiceException, DAOException, InvalidInputException {
        //given
        String id = "two";
        String discount = "25";

        //when
        userService.changeDiscount(id, discount);

        //then
        //expected InvalidInputException
    }

    @DataProvider
    public static Object[][] upBalance_Strings_UppedBalance_Data(){
        User user1 = new User();
        user1.setId(22);
        user1.setBalance(300);
        user1.setDiscount(20);
        User user1Expected = new User();
        user1Expected.setId(22);
        user1Expected.setBalance(600);
        user1Expected.setDiscount(20);
        User user2 = new User();
        user2.setId(33);
        user2.setBalance(500);
        user2.setDiscount(0);
        User user2Expected = new User();
        user2Expected.setId(33);
        user2Expected.setBalance(1800);
        user2Expected.setDiscount(5);
        User user3 = new User();
        user3.setId(43);
        user3.setBalance(1550);
        user3.setDiscount(10);
        User user3Expected = new User();
        user3Expected.setId(43);
        user3Expected.setBalance(2350);
        user3Expected.setDiscount(10);
        return new Object[][] {
                { "22", "300", "cash", user1, user1Expected},
                { "33", "1300", "card", user2, user2Expected},
                { "43", "800", "card", user3, user3Expected}
        };
    }

    @Test
    @UseDataProvider("upBalance_Strings_UppedBalance_Data")
    public void upBalance_Strings_UppedBalance(String userIdStr, String addBalanceStr, String payment, User user, User expected) throws DAOException, InvalidInputException, ServiceException {
        //given
        when(userDAO.getById(user.getId())).thenReturn(user);

        //when
        userService.upBalance(userIdStr, addBalanceStr, payment);

        //then
        verify(userDAO).update(expected);
    }

    @Test(expected = ServiceException.class)
    public void upBalance_Strings_ServiceException() throws DAOException, InvalidInputException, ServiceException {
        //given
        String userIdStr = "22";
        String addBalanceStr = "300";
        String payment = "card";
        when(userDAO.getById(22)).thenThrow(new DAOException());

        //when
        userService.upBalance(userIdStr, addBalanceStr, payment);

        //then
        //expected ServiceException
    }

    @Test(expected = InvalidInputException.class)
    public void upBalance_Strings_InvalidInputException() throws InvalidInputException, ServiceException {
        //given
        String userIdStr = "22..";
        String addBalanceStr = "300";
        String payment = "card";

        //when
        userService.upBalance(userIdStr, addBalanceStr, payment);

        //then
        //expected InvalidInputException
    }
}