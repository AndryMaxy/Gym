package service;
//TODO SHOULD BE SINGLETON?
public class ParameterValidator {


    private static final String TEXT_REGEX = "([A-z0-9А-яёЁ,.!?]\\s*){20,}";
    private static final String NUMBER_REGEX = "\\d+";
    private static final String LOGIN_REGEX = "[A-z0-9А-я]{6,}";
    private static final String PASSWORD_REGEX = "(?=.*\\d)(?=.*[A-zА-я]).{6,}";
    private static final String USER_NAME_REGEX = "[A-zА-я]+";
    private static final String ROLE_TYPE_REGEX = "\\bADMIN\\b|\\bVISITOR\\b|\\bTRAINER\\b";
    private static final String MEMBERSHIP_REGEX = "\\bULTRA\\b|\\bSUPER\\b|\\bSTANDARD\\b|\\bEASY\\b|\\bONE\\b";
    private static final String DISCOUNT_REGEX = "\\b0\\b|\\b5\\b|\\b10\\b|\\b15\\b|\\b20\\b|\\b25\\b";

    private static class ValidatorHolder{
        static final ParameterValidator INSTANCE = new ParameterValidator();
    }

    private ParameterValidator(){}

    public static ParameterValidator getInstance() {
        return ValidatorHolder.INSTANCE;
    }

    public boolean validateText(String text) {
        return text.matches(TEXT_REGEX);
    }

    public boolean validateNumber(String number) {
        return number.matches(NUMBER_REGEX);
    }

    public boolean validateName(String name) {
        return name.matches(USER_NAME_REGEX);
    }

    public boolean validateLogin(String login) {
        return login.matches(LOGIN_REGEX);
    }

    public boolean validatePassword(char[] password) {
        String pass = new String(password);
        boolean result = pass.matches(PASSWORD_REGEX);
        pass = null;
        return result;
    }

    public boolean validateRole(String role) {
        return role.matches(ROLE_TYPE_REGEX);
    }

    public boolean validateDiscount(String discount) {
        return discount.matches(DISCOUNT_REGEX);
    }

    public boolean validateMembership(String membership) {
        return membership.matches(MEMBERSHIP_REGEX);
    }
}
