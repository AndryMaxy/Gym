package by.epam.akulich.gym.service;

/**
 * The class is used for validate all input data of web application.
 *
 * @author Andrey Akulich
 */
public class ParameterValidator {

    private static final String TEXT_REGEX = "([A-Za-z0-9А-яёЁ,.!?]\\s*){20,}";
    private static final String NUMBER_REGEX = "\\d+";
    private static final String LOGIN_REGEX = "[A-Za-z0-9А-я]{6,15}";
    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-zА-яёЁ])(?=.*\\d)[A-Za-zА-яёЁ\\d]{6,}$";
    private static final String USER_NAME_REGEX = "[A-Za-zА-я]+";
    private static final String ROLE_TYPE_REGEX = "\\bADMIN\\b|\\bVISITOR\\b|\\bTRAINER\\b";
    private static final String MEMBERSHIP_REGEX = "\\bULTRA\\b|\\bSUPER\\b|\\bSTANDARD\\b|\\bEASY\\b|\\bONE\\b";
    private static final String DISCOUNT_REGEX = "\\b0\\b|\\b5\\b|\\b10\\b|\\b15\\b|\\b20\\b|\\b25\\b";

    /**
     * This class represents initialization-on-demand holder idiom for {@link ParameterValidator}
     */
    private static class ValidatorHolder{
        static final ParameterValidator INSTANCE = new ParameterValidator();
    }

    /**
     * Constructs ParameterValidator.
     */
    private ParameterValidator(){}

    /**
     * @return ParameterValidator instance.
     */
    public static ParameterValidator getInstance() {
        return ValidatorHolder.INSTANCE;
    }

    /**
     * Matches input string to {@link #TEXT_REGEX}.
     *
     * @param text any string from user
     * @return {@code true} if param matched to pattern
     * otherwise {@code false}
     */
    public boolean validateText(String text) {
        return text.matches(TEXT_REGEX);
    }

    /**
     * Matches input string to {@link #NUMBER_REGEX}.
     *
     * @param number any number from user
     * @return {@code true} if param matched to pattern
     * otherwise {@code false}
     */
    public boolean validateNumber(String number) {
        return number.matches(NUMBER_REGEX);
    }

    /**
     * Matches input string to {@link #USER_NAME_REGEX}.
     *
     * @param name any string from user
     * @return {@code true} if param matched to pattern
     * otherwise {@code false}
     */
    public boolean validateName(String name) {
        return name.matches(USER_NAME_REGEX);
    }

    /**
     * Matches input string to {@link #LOGIN_REGEX}.
     *
     * @param login any string from user
     * @return {@code true} if param matched to pattern
     * otherwise {@code false}
     */
    public boolean validateLogin(String login) {
        return login.matches(LOGIN_REGEX);
    }

    /**
     * Matches input string to {@link #PASSWORD_REGEX}.
     *
     * @param password any string from user
     * @return {@code true} if param matched to pattern
     * otherwise {@code false}
     */
    public boolean validatePassword(char[] password) {
        String pass = new String(password);
        return pass.matches(PASSWORD_REGEX);
    }

    /**
     * Matches input string to {@link #ROLE_TYPE_REGEX}.
     *
     * @param role any string from user
     * @return {@code true} if param matched to pattern
     * otherwise {@code false}
     */
    public boolean validateRole(String role) {
        return role.matches(ROLE_TYPE_REGEX);
    }

    /**
     * Matches input string to {@link #DISCOUNT_REGEX}.
     *
     * @param discount any string from user
     * @return {@code true} if param matched to pattern
     * otherwise {@code false}
     */
    public boolean validateDiscount(String discount) {
        return discount.matches(DISCOUNT_REGEX);
    }

    /**
     * Matches input string to {@link #MEMBERSHIP_REGEX}.
     *
     * @param membership any string from user
     * @return {@code true} if param matched to pattern
     * otherwise {@code false}
     */
    public boolean validateMembership(String membership) {
        return membership.matches(MEMBERSHIP_REGEX);
    }
}
