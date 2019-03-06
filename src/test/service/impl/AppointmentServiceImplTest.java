package service.impl;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import dao.AppointmentDAO;
import dao.exception.DAOException;
import dao.impl.ExerciseAppointmentDAOImpl;
import dao.impl.ProductAppointmentDAOImpl;
import entity.Appointment;
import entity.ExerciseAppointment;
import entity.ProductAppointment;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.AppointmentService;
import service.exception.InvalidInputException;
import service.exception.ServiceException;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(DataProviderRunner.class)
public class AppointmentServiceImplTest {

    @Mock
    private ExerciseAppointmentDAOImpl exerciseDAO;

    @Mock
    private ProductAppointmentDAOImpl productDAO;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @DataProvider
    public static Object[][] getAppointment_String_CorrectAppointment_Data(){
        ExerciseAppointment exercise1 = new ExerciseAppointment();
        exercise1.setWeight(20);
        List<ExerciseAppointment> exerciseAppointments1 = new ArrayList<>();
        exerciseAppointments1.add(exercise1);
        ExerciseAppointment exercise2 = new ExerciseAppointment();
        exercise2.setRepCount(30);
        List<ExerciseAppointment> exerciseAppointments2 = new ArrayList<>();
        exerciseAppointments2.add(exercise2);
        ExerciseAppointment exercise3 = new ExerciseAppointment();
        exercise3.setSetCount(3);
        List<ExerciseAppointment> exerciseAppointments3 = new ArrayList<>();
        exerciseAppointments3.add(exercise3);
        ProductAppointment product1 = new ProductAppointment();
        product1.setGramInDay(180);
        List<ProductAppointment> productAppointments1 = new ArrayList<>();
        productAppointments1.add(product1);
        ProductAppointment product2 = new ProductAppointment();
        product2.setGramInDay(280);
        List<ProductAppointment> productAppointments2 = new ArrayList<>();
        productAppointments2.add(product2);
        ProductAppointment product3 = new ProductAppointment();
        product3.setGramInDay(215);
        List<ProductAppointment> productAppointments3 = new ArrayList<>();
        productAppointments3.add(product3);
        Appointment appointment1 = new Appointment();
        appointment1.setExerciseAppointments(exerciseAppointments1);
        appointment1.setProductAppointments(productAppointments1);
        Appointment appointment2 = new Appointment();
        appointment2.setExerciseAppointments(exerciseAppointments2);
        appointment2.setProductAppointments(productAppointments2);
        Appointment appointment3 = new Appointment();
        appointment3.setExerciseAppointments(exerciseAppointments3);
        appointment3.setProductAppointments(productAppointments3);
        return new Object[][] {
                {12, exerciseAppointments1, productAppointments1, appointment1},
                {89, exerciseAppointments2, productAppointments2, appointment2},
                {157, exerciseAppointments3, productAppointments3, appointment3}
        };
    }

    @Test
    @UseDataProvider("getAppointment_String_CorrectAppointment_Data")
    public void getAppointment_String_CorrectAppointment(int id, List<ExerciseAppointment> exercises, List<ProductAppointment> products, Appointment expected) throws DAOException, ServiceException {
        when(exerciseDAO.getByBookingId(id)).thenReturn(exercises);
        when(productDAO.getByBookingId(id)).thenReturn(products);

        Appointment actual = appointmentService.getAppointment(id);

        verify(productDAO).getByBookingId(id);
        verify(exerciseDAO).getByBookingId(id);
        assertEquals(expected, actual);
    }

    @Test(expected = ServiceException.class)
    public void getAppointment_String_ServiceException() throws DAOException, ServiceException {
        Appointment expected = new Appointment();
        when(exerciseDAO.getByBookingId(2)).thenReturn(new ArrayList<>());
        when(productDAO.getByBookingId(2)).thenThrow(new DAOException());

        Appointment actual = appointmentService.getAppointment(2);

        verify(productDAO).getByBookingId(2);
        verify(exerciseDAO).getByBookingId(2);
        assertEquals(expected, actual);
    }


    @DataProvider
    public static Object[][] getAll_DAO_CorrectAppointment_Data(){
        ExerciseAppointment exercise1 = new ExerciseAppointment();
        exercise1.setWeight(20);
        List<ExerciseAppointment> exerciseAppointments1 = new ArrayList<>();
        exerciseAppointments1.add(exercise1);
        ExerciseAppointment exercise2 = new ExerciseAppointment();
        exercise2.setRepCount(30);
        List<ExerciseAppointment> exerciseAppointments2 = new ArrayList<>();
        exerciseAppointments2.add(exercise2);
        ExerciseAppointment exercise3 = new ExerciseAppointment();
        exercise3.setSetCount(3);
        List<ExerciseAppointment> exerciseAppointments3 = new ArrayList<>();
        exerciseAppointments3.add(exercise3);
        ProductAppointment product1 = new ProductAppointment();
        product1.setGramInDay(180);
        List<ProductAppointment> productAppointments1 = new ArrayList<>();
        productAppointments1.add(product1);
        ProductAppointment product2 = new ProductAppointment();
        product2.setGramInDay(280);
        List<ProductAppointment> productAppointments2 = new ArrayList<>();
        productAppointments2.add(product2);
        ProductAppointment product3 = new ProductAppointment();
        product3.setGramInDay(215);
        List<ProductAppointment> productAppointments3 = new ArrayList<>();
        productAppointments3.add(product3);
        Appointment appointment1 = new Appointment();
        appointment1.setExerciseAppointments(exerciseAppointments1);
        appointment1.setProductAppointments(productAppointments1);
        Appointment appointment2 = new Appointment();
        appointment2.setExerciseAppointments(exerciseAppointments2);
        appointment2.setProductAppointments(productAppointments2);
        Appointment appointment3 = new Appointment();
        appointment3.setExerciseAppointments(exerciseAppointments3);
        appointment3.setProductAppointments(productAppointments3);
        return new Object[][] {
                {exerciseAppointments1, productAppointments1, appointment1},
                {exerciseAppointments2, productAppointments2, appointment2},
                {exerciseAppointments3, productAppointments3, appointment3}
        };
    }

    @Test
    @UseDataProvider("getAll_DAO_CorrectAppointment_Data")
    public void getAll_DAO_CorrectAppointment(List<ExerciseAppointment> exercises, List<ProductAppointment> products, Appointment expected) throws DAOException, ServiceException {
        when(exerciseDAO.getAll()).thenReturn(exercises);
        when(productDAO.getAll()).thenReturn(products);

        Appointment actual = appointmentService.getAll();

        verify(productDAO).getAll();
        verify(exerciseDAO).getAll();
        assertEquals(expected, actual);
    }

    @Test(expected = ServiceException.class)
    public void getAll_DAO_ServiceException() throws DAOException, ServiceException {
        Appointment expected = new Appointment();
        when(exerciseDAO.getAll()).thenReturn(new ArrayList<>());
        when(productDAO.getAll()).thenThrow(new DAOException());

        Appointment actual = appointmentService.getAll();

        verify(productDAO).getAll();
        verify(exerciseDAO).getAll();
        assertEquals(expected, actual);
    }

    @DataProvider
    public static Object[][] addAppointment_DAO_AddedAppointment_Data() {
        ExerciseAppointment exercise1 = new ExerciseAppointment();
        exercise1.setWeight(20);
        List<ExerciseAppointment> exerciseAppointments1 = new ArrayList<>();
        exerciseAppointments1.add(exercise1);
        Appointment appointment1 = new Appointment();
        appointment1.setExerciseAppointments(exerciseAppointments1);
        ProductAppointment product2 = new ProductAppointment();
        product2.setGramInDay(280);
        List<ProductAppointment> productAppointments2 = new ArrayList<>();
        productAppointments2.add(product2);
        Appointment appointment2 = new Appointment();
        appointment2.setProductAppointments(productAppointments2);
        ProductAppointment product3 = new ProductAppointment();
        product3.setGramInDay(215);
        List<ProductAppointment> productAppointments3 = new ArrayList<>();
        productAppointments3.add(product3);
        Appointment appointment3 = new Appointment();
        appointment3.setProductAppointments(productAppointments3);
        return new Object[][]{
                {2, appointment1},
                {342, appointment2},
                {4324, appointment3}
        };
    }

    @Test
    @UseDataProvider("addAppointment_DAO_AddedAppointment_Data")
    public void addAppointment_DAO_AddedAppointment(int id, Appointment appointment) throws DAOException, ServiceException {
        appointmentService.addAppointment(id, appointment);

        verify(exerciseDAO).addAppointment(id, appointment.getExerciseAppointments());
        verify(productDAO).addAppointment(id, appointment.getProductAppointments());
    }

    @Test
    public void parseAppointments_Parameters_CorrectAppointment() throws InvalidInputException, ServiceException {
        ExerciseAppointment exerciseAppointment = new ExerciseAppointment();
        exerciseAppointment.setId(2);
        exerciseAppointment.setRepCount(25);
        exerciseAppointment.setSetCount(3);
        exerciseAppointment.setWeight(10);
        List<ExerciseAppointment> exerciseAppointments = new ArrayList<>();
        exerciseAppointments.add(exerciseAppointment);
        Appointment expected = new Appointment();
        expected.setExerciseAppointments(exerciseAppointments);
        expected.setProductAppointments(new ArrayList<>());
        Map<String, String[]> parameterMap = new HashMap<>();
        String parameterName = "exercise-2";
        parameterMap.put(parameterName, new String[]{"25", "3", "10"});
        Vector<String> vector = new Vector<>();
        vector.add(parameterName);
        Enumeration<String> enumeration = vector.elements();

        Appointment actual = appointmentService.parseAppointments(parameterMap, enumeration);

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = InvalidInputException.class)
    public void parseAppointments_Parameters_ServiceException() throws InvalidInputException {
        ExerciseAppointment exerciseAppointment = new ExerciseAppointment();
        exerciseAppointment.setId(2);
        exerciseAppointment.setRepCount(25);
        exerciseAppointment.setSetCount(3);
        exerciseAppointment.setWeight(10);
        List<ExerciseAppointment> exerciseAppointments = new ArrayList<>();
        exerciseAppointments.add(exerciseAppointment);
        Appointment expected = new Appointment();
        expected.setExerciseAppointments(exerciseAppointments);
        expected.setProductAppointments(new ArrayList<>());
        Map<String, String[]> parameterMap = new HashMap<>();
        String parameterName = "exercise-2";
        parameterMap.put(parameterName, new String[]{"25fg", "3", "10"});
        Vector<String> vector = new Vector<>();
        vector.add(parameterName);
        Enumeration<String> enumeration = vector.elements();

        Appointment actual = appointmentService.parseAppointments(parameterMap, enumeration);

        Assert.assertEquals(expected, actual);
    }
}