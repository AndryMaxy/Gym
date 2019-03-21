package by.epam.akulich.gym.entity;

/**
 * The class contains project constants.
 *
 * @author Andrey Akulich
 */
public final class Constants {

    /**
     * The class contains constants for commands.
     */
    public static final class Command {
        public static final String COMMAND = "command";
        public static final String SIGN_IN = "signIn";
        public static final String LOG_IN = "logIn";
        public static final String LOG_OUT = "logOut";
        public static final String DELETE_USER = "delete";
        public static final String HOME = "home";
        public static final String BUY_MEMBERSHIP = "buy";
        public static final String CHANGE_LOCALE = "locale";
        public static final String DO_APPOINT = "doAppoint";
        public static final String APPOINT = "appoint";
        public static final String CHANGE_ROLE = "role";
        public static final String LOOK_ORDER = "order";
        public static final String REDUCE_VISITS = "reduce";
        public static final String FEEDBACK = "feedback";
        public static final String ADD_FEEDBACK = "addFeedback";
        public static final String CHANGE_DISCOUNT = "discount";
        public static final String REFUSE_APPOINTMENT = "refuse";
        public static final String REFILL = "refill";
        public static final String UP_BALANCE = "upBalance";
    }

    /**
     * The class contains url constants.
     */
    public static final class URL {
        public static final String ROOT = "";
        public static final String HOME = "/home";
        public static final String REGISTER = "/register";
        public static final String FEEDBACK = "/feedback";
        public static final String REFILL = "/refill";
        public static final String MAIN = "/WEB-INF/jsp/common/main.jsp";
        public static final String APPOINT = "/WEB-INF/jsp/trainer/appointment.jsp";
        public static final String ORDER = "/WEB-INF/jsp/admin/order.jsp";
        public static final String FEEDBACK_JSP = "/WEB-INF/jsp/common/feedback.jsp";
        public static final String REFILL_JSP = "/WEB-INF/jsp/visitor/balance.jsp";
    }

    /**
     * The class contains parameter constants.
     */
    public static final class Parameter {
        public static final String USER_ID = "userId";
        public static final String BOOKING_ID = "bookingId";
        public static final String LOGIN = "login";
        public static final String PASSWORD = "password";
        public static final String USER = "user";
        public static final String ROLE = "role";
        public static final String MEMBERSHIP = "membership";
        public static final String DISCOUNT = "discount";
        public static final String FEEDBACK_AREA = "feedbackArea";
        public static final String LOCALE = "locale";
        public static final String PAYMENT = "payment";
        public static final String BALANCE = "balance";
    }

    /**
     * The class contains constants servlet response.
     */
    public static final class ResponseStatus {
        public static final int FORWARD = 1;
        public static final int REDIRECT = 2;
        public static final int INCORRECT_INPUT = 3;
        public static final int NOT_FOUND = 4;
    }

    /**
     * The class contains constants for database connectivity.
     */
    public final class DBKey {
        public static final String DRIVER = "db.driver";
        public static final String USER = "db.user";
        public static final String PASSWORD = "db.password";
        public static final String URL = "db.url";
        public static final String POOL_SIZE = "db.poolSize";
    }
}
