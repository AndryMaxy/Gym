package service.impl;

import builder.UserBuilder;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import dao.UserDAO;
import dao.exception.DAOException;
import entity.User;
import entity.UserRole;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.exception.InvalidInputException;
import service.exception.ServiceException;
import util.Encoder;
import util.exception.EncoderException;

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
    public void setUp(){
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
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        userService.add(expected);

        verify(userDAO, times(1)).add(userCaptor.capture());
        assertEquals(expected, userCaptor.getValue());
    }

    @Test(expected = ServiceException.class)
    public void add_User_ServiceException() throws ServiceException, DAOException {
        User user = new User();
        doThrow(new DAOException()).when(userDAO).add(user);

        userService.add(user);
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
        when(userDAO.getAll()).thenReturn(expected);

        List<User> actual = userService.getAll();

        assertEquals(expected, actual);
    }

    @Test(expected = ServiceException.class)
    public void getAll_DAO_ServiceException() throws ServiceException, DAOException {
        List<User> expected = new ArrayList<>();
        when(userDAO.getAll()).thenThrow(new DAOException());

        List<User> actual = userService.getAll();

        assertEquals(expected, actual);
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
        when(userDAO.getByLogin(login)).thenReturn(user);

        boolean actual = userService.isUserLoginExist(login);

        assertEquals(expected, actual);
    }

    @Test(expected = InvalidInputException.class)
    public void isUserLoginExist_String_InvalidInputException() throws ServiceException, DAOException, InvalidInputException {
        String login = "1";
        when(userDAO.getByLogin(login)).thenReturn(null);

        boolean result = userService.isUserLoginExist(login);

        assertTrue(result);
    }

    @Test(expected = ServiceException.class)
    public void isUserLoginExist_String_ServiceException() throws ServiceException, DAOException, InvalidInputException {
        String login = "userName1";
        when(userDAO.getByLogin(login)).thenThrow(new DAOException());

        boolean result = userService.isUserLoginExist(login);

        assertTrue(result);
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
        when(userDAO.getById(id)).thenReturn(expected);

        User actual = userService.getUser(idStr);

        assertEquals(expected, actual);
    }

    @Test(expected = InvalidInputException.class)
    public void getUser_String_InvalidInputException() throws ServiceException, DAOException, InvalidInputException {
        User expected = new User();
        String id = "ten";
        when(userDAO.getById(10)).thenReturn(expected);

        User actual = userService.getUser(id);

        assertEquals(expected, actual);
    }

    @Test(expected = ServiceException.class)
    public void getUser_String_ServiceException() throws ServiceException, DAOException, InvalidInputException {
        User expected = new User();
        String id = "10";
        when(userDAO.getById(10)).thenThrow(new DAOException());

        User actual = userService.getUser(id);

        assertEquals(expected, actual);
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
        when(userDAO.getByLogin(user.getLogin())).thenReturn(user);
        when(encoder.check(password, user.getHash().getBytes(), user.getSalt().getBytes())).thenReturn(true);

        int actual = userService.logIn(user.getLogin(), password);

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
        when(userDAO.getByLogin(login)).thenReturn(null);

        int actual = userService.logIn(login, password);

        assertEquals(expected, actual);
    }

    @Test(expected = InvalidInputException.class)
    public void logIn_StringAndCharArray_InvalidInputException() throws ServiceException, DAOException, InvalidInputException, EncoderException {
        int expected = 10;
        String login = "userName1";
        char[] password = "pass1".toCharArray();
        String hash = "hash";
        String salt = "salt";
        User user = new User();
        user.setLogin(login);
        user.setHash(hash);
        user.setSalt(salt);
        user.setId(expected);
        when(userDAO.getByLogin(login)).thenReturn(user);
        when(encoder.check(password, hash.getBytes(), salt.getBytes())).thenReturn(true);

        int actual = userService.logIn(login, password);

        assertEquals(expected, actual);
    }

    @Test(expected = ServiceException.class)
    public void logIn_StringAndCharArray_ServiceException() throws ServiceException, DAOException, InvalidInputException, EncoderException {
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

        int actual = userService.logIn(login, password);

        assertEquals(expected, actual);
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
        when(userDAO.getVisitorsWithoutApp()).thenReturn(expected);

        List<User> actual = userService.getVisitors();

        assertEquals(expected, actual);
    }

    @Test(expected = ServiceException.class)
    public void getVisitors_DAO_ServiceException() throws ServiceException, DAOException {
        List<User> expected = new ArrayList<>();
        when(userDAO.getVisitorsWithoutApp()).thenThrow(new DAOException());

        List<User> actual = userService.getVisitors();

        assertEquals(expected, actual);
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
        when(userDAO.getVisitor(id)).thenReturn(expected);

        User actual = userService.getNewVisitor(idStr);

        assertEquals(expected, actual);
    }

    @Test(expected = ServiceException.class)
    public void getNewVisitor_String_ServiceException() throws ServiceException, InvalidInputException, DAOException {
        String id = "25";
        User expected = new User();
        expected.setName("Valera");
        expected.setDiscount(25);
        when(userDAO.getVisitor(25)).thenThrow(new DAOException());

        User actual = userService.getNewVisitor(id);

        assertEquals(expected, actual);
    }

    @Test(expected = InvalidInputException.class)
    public void getNewVisitor_String_InvalidInputException() throws ServiceException, InvalidInputException, DAOException {
        String id = "25l";
        User expected = new User();
        expected.setName("Valera");
        expected.setDiscount(25);
        when(userDAO.getVisitor(25)).thenReturn(expected);

        User actual = userService.getNewVisitor(id);

        assertEquals(expected, actual);
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
        userService.delete(idStr);

        verify(userDAO).delete(id);
    }

    @Test(expected = ServiceException.class)
    public void delete_String_ServiceException() throws ServiceException, DAOException, InvalidInputException {
        String id = "24";
        doThrow(new DAOException()).when(userDAO).delete(24);

        userService.delete(id);
    }

    @Test(expected = InvalidInputException.class)
    public void delete_String_InvalidInputException() throws ServiceException, DAOException, InvalidInputException {
        String id = "twenty four";
        userService.delete(id);

        verify(userDAO, times(1)).delete(24);
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
        String login = expected.getLogin();
        String name = expected.getName();
        String surname = expected.getSurname();
        when(encoder.encode(password)).thenReturn(encoded);

        User actual = userService.createUser(login, password, name, surname);

        assertEquals(expected, actual);
    }

    @Test(expected = ServiceException.class)
    public void createUser_UserFields_ServiceException() throws ServiceException, InvalidInputException, EncoderException {
        String login = "userName1";
        char[] password = "password1".toCharArray();
        String name = "Петр";
        String surname = "Игнатьев";
        String hash = "hash";
        String salt = "salt";
        UserRole role = UserRole.VISITOR;
        UserBuilder builder = new UserBuilder();
        User expected = builder.buildLogin(login)
                .buildName(name)
                .buildSurname(surname)
                .buildUserRole(role)
                .buildHash(hash)
                .buildSalt(salt)
                .build();
        when(encoder.encode(password)).thenThrow(new EncoderException());

        User actual = userService.createUser(login, password, name, surname);

        assertEquals(expected, actual);
    }

    @Test(expected = InvalidInputException.class)
    public void createUser_UserFields_InvalidInputException() throws ServiceException, InvalidInputException, EncoderException {
        String login = "userName1";
        char[] password = "password1".toCharArray();
        String name = "Петр1";
        String surname = "Игнатьев";
        String hash = "hash";
        String salt = "salt";
        UserRole role = UserRole.VISITOR;
        UserBuilder builder = new UserBuilder();
        User expected = builder.buildLogin(login)
                .buildName(name)
                .buildSurname(surname)
                .buildUserRole(role)
                .buildHash(hash)
                .buildSalt(salt)
                .build();
        String[] encoded = {hash, salt};
        when(encoder.encode(password)).thenReturn(encoded);

        User actual = userService.createUser(login, password, name, surname);

        assertEquals(expected, actual);
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
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        when(userDAO.getById(id)).thenReturn(expected);

        userService.changeRole(idStr, roleStr);

        verify(userDAO, times(1)).update(userCaptor.capture());
        assertEquals(expected, userCaptor.getValue());
    }

    @Test(expected = ServiceException.class)
    public void changeRole_Strings_ServiceException() throws ServiceException, DAOException, InvalidInputException {
        String id = "14";
        String roleStr = "ADMIN";
        UserBuilder builder = new UserBuilder();
        User expected = builder.buildId(14)
                .buildUserRole(UserRole.VISITOR)
                .build();
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        when(userDAO.getById(14)).thenThrow(new DAOException());

        userService.changeRole(id, roleStr);

        verify(userDAO, times(1)).update(userCaptor.capture());
        assertEquals(expected, userCaptor.getValue());
    }

    @Test(expected = InvalidInputException.class)
    public void changeRole_Strings_InvalidInputException() throws ServiceException, DAOException, InvalidInputException {
        String id = "twelve";
        String roleStr = "ADMIN";
        UserBuilder builder = new UserBuilder();
        User expected = builder.buildId(14)
                .buildUserRole(UserRole.VISITOR)
                .build();
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        when(userDAO.getById(14)).thenReturn(expected);

        userService.changeRole(id, roleStr);

        verify(userDAO, times(1)).update(userCaptor.capture());
        assertEquals(expected, userCaptor.getValue());
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
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        when(userDAO.getById(id)).thenReturn(expected);

        userService.changeDiscount(idStr, discount);

        verify(userDAO).update(userCaptor.capture());
        assertEquals(expected, userCaptor.getValue());
    }

    @Test(expected = ServiceException.class)
    public void changeDiscount_Strings_ServiceException() throws ServiceException, DAOException, InvalidInputException {
        String id = "14";
        String discount = "25";
        User expected = new User();
        expected.setId(14);
        expected.setDiscount(25);
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        when(userDAO.getById(14)).thenThrow(new DAOException());

        userService.changeDiscount(id, discount);

        verify(userDAO, times(1)).update(userCaptor.capture());
        assertEquals(expected, userCaptor.getValue());
    }

    @Test(expected = InvalidInputException.class)
    public void changeDiscount_Strings_InvalidInputException() throws ServiceException, DAOException, InvalidInputException {
        String id = "two";
        String discount = "25";
        User expected = new User();
        expected.setId(14);
        expected.setDiscount(25);
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        when(userDAO.getById(14)).thenThrow(new DAOException());

        userService.changeDiscount(id, discount);

        verify(userDAO, times(1)).update(userCaptor.capture());
        assertEquals(expected, userCaptor.getValue());
    }
}