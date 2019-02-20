package entity;

public final class Constants {

    public static final class User {
        public static final String ID = "UserId";
        public static final String LOGIN = "Login";
        public static final String NAME = "Name";
        public static final String SURNAME = "Surname";
        public static final String HASH = "Hash";
        public static final String SALT = "Salt";
        public static final String ROLE = "Role";
        public static final String DISCOUNT = "Discount";
        public static final String BALANCE = "Balance";
        public static final String VISIT_COUNT_LEFT = "VisitCountLeft";
    }

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
    }

    public static final class URL {
        public static final String REDIRECT = "";
        public static final String MAIN = "/main";
        public static final String APPOINT = "/app";
        public static final String ORDER = "/lookOrder";
        public static final String HOME = "/home";
        public static final String REGISTER = "/registration";
    }

    public static final class Parameter {
        public static final String USER = "user";
        public static final String USER_ID = "userId";
        public static final String LOGIN = "login";
        public static final String PASSWORD = "password";
        public static final String NAME = "name";
        public static final String SURNAME = "surname";
        public static final String ROLE = "role";
        public static final String MEMBERSHIP = "membership";
    }

    public final class DBKey {
        public static final String DRIVER = "db.driver";
        public static final String USER = "db.user";
        public static final String PASSWORD = "db.password";
        public static final String URL = "db.url";
        public static final String POOL_SIZE = "db.poolSize";
    }
}
