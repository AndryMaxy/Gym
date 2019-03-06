package service;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@RunWith(DataProviderRunner.class)
public class ParameterValidatorTest {

    private ParameterValidator validator = ParameterValidator.getInstance();

    @DataProvider
    public static Object[][] validateTextData(){
        return new Object[][] {
                {"Hello my dear friend. Do you like my web app?", true},
                {"Test method number 2 will be passed.", true},
                {"I want to hack you. <script>hack</script>", false},
                {"Do not use strange symbols like %, $, # etc.", false}
        };
    }

    @Test
    @UseDataProvider("validateTextData")
    public void validateText(String text, boolean expected) {
        boolean actual = validator.validateText(text);

        assertEquals(expected, actual);
    }

    @DataProvider
    public static Object[][] validateNumberData(){
        return new Object[][] {
                {"23", true},
                {"23523424", true},
                {"423423.3", false},
                {"nine", false}
        };
    }

    @Test
    @UseDataProvider("validateNumberData")
    public void validateNumber(String number, boolean expected) {
        boolean actual = validator.validateNumber(number);

        assertEquals(expected, actual);
    }

    @DataProvider
    public static Object[][] validateNameData(){
        return new Object[][] {
                {"Valera", true},
                {"Алексей", true},
                {"Val.", false},
                {"Andrey6", false}
        };
    }

    @Test
    @UseDataProvider("validateNameData")
    public void validateName(String name, boolean expected) {
        boolean actual = validator.validateName(name);

        assertEquals(expected, actual);
    }

    @DataProvider
    public static Object[][] validateLoginData(){
        return new Object[][] {
                {"Valera973Yes", true},
                {"Vaa9es", true},
                {"V973Y", false},
                {"A,ndrey", false}
        };
    }

    @Test
    @UseDataProvider("validateLoginData")
    public void validateLogin(String login, boolean expected) {
        boolean actual = validator.validateLogin(login);

        assertEquals(expected, actual);
    }

    @DataProvider
    public static Object[][] validatePasswordData(){
        return new Object[][] {
                {"Valera973Yes".toCharArray(), true},
                {"Vaa9es".toCharArray(), true},
                {"Valera".toCharArray(), false},
                {"alera".toCharArray(), false}
        };
    }

    @Test
    @UseDataProvider("validatePasswordData")
    public void validatePassword(char[] password, boolean expected) {
        boolean actual = validator.validatePassword(password);

        assertEquals(expected, actual);
    }

    @DataProvider
    public static Object[][] validateRoleData(){
        return new Object[][] {
                {"ADMIN", true},
                {"VISITOR", true},
                {"TRAINER", true},
                {"DOCTOR", false},
                {"traener", false},
        };
    }

    @Test
    @UseDataProvider("validateRoleData")
    public void validateRole(String role, boolean expected) {
        boolean actual = validator.validateRole(role);

        assertEquals(expected, actual);
    }

    @DataProvider
    public static Object[][] validateDiscountData(){
        return new Object[][] {
                {"0", true},
                {"5", true},
                {"10", true},
                {"15", true},
                {"20", true},
                {"25", true},
                {"30", false},
                {"45", false},
                {"zero", false},
        };
    }

    @Test
    @UseDataProvider("validateDiscountData")
    public void validateDiscount(String discount, boolean expected) {
        boolean actual = validator.validateDiscount(discount);

        assertEquals(expected, actual);
    }

    @DataProvider
    public static Object[][] validateMembershipData(){
        return new Object[][] {
                {"ULTRA", true},
                {"SUPER", true},
                {"STANDARD", true},
                {"EASY", true},
                {"ONE", true},
                {"TWO", false},
                {"TEN", false},
                {"5", false},
                {"zero", false},
        };
    }

    @Test
    @UseDataProvider("validateMembershipData")
    public void validateMembership(String membership, boolean expected) {
        boolean actual = validator.validateMembership(membership);

        assertEquals(expected, actual);
    }
}