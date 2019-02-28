package entity;
//TODO PACKAGE?
public final class Constants {

    public static final class Command {
        public static final String COMMAND = "command";
        public static final String SIGN_IN = "signIn";
        public static final String LOG_IN = "logIn";
        public static final String LOG_OUT = "logOut";
        public static final String DELETE_USER = "delete";
        public static final String HOME = "home";
        public static final String REGISTER = "register";
        public static final String BUY_MEMBERSHIP = "buy";
        public static final String CHANGE_LOCALE = "locale";
        public static final String DO_APPOINT = "doAppoint";
        public static final String APPOINT = "appoint";
        public static final String CHANGE_ROLE = "role";
        public static final String OPEN_ORDER = "order";
        public static final String REDUCE_VISITS = "reduce";
        public static final String FEEDBACK = "feedback";
        public static final String ADD_FEEDBACK = "addFeedback";
        public static final String CHANGE_DISCOUNT = "discount";
        public static final String ABOUT = "about";
    }

    public static final class URL {
        public static final String ROOT = "";
        public static final String MAIN = "/main";
        public static final String APPOINT = "/app";
        public static final String ORDER = "/lookOrder";
        public static final String HOME = "/home";
        public static final String REGISTER = "/register";
        public static final String REGISTRATION = "/registration";
        public static final String FEEDBACK = "/feedback";
        public static final String ABOUT_JSP = "/about.jsp";
        public static final String FEEDBACK_JSP = "/feedback.jsp";
    }

    public static final class Parameter {
        public static final String USER_ID = "userId";
        public static final String BOOKING_ID = "bookingId";
        public static final String LOGIN = "login";
        public static final String PASSWORD = "password";
        public static final String NAME = "name";
        public static final String SURNAME = "surname";
        public static final String ROLE = "role";
        public static final String MEMBERSHIP = "membership";
        public static final String DISCOUNT = "discount";
        public static final String FEEDBACK_AREA = "feedbackArea";
        public static final String LOCALE = "locale";
    }

    public final class DBKey {
        public static final String DRIVER = "db.driver";
        public static final String USER = "db.user";
        public static final String PASSWORD = "db.password";
        public static final String URL = "db.url";
        public static final String POOL_SIZE = "db.poolSize";
    }
}
