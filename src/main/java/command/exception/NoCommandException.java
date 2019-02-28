package command.exception;

public class NoCommandException extends Exception {

    private static final long serialVersionUID = 6063633463053920704L;

    public NoCommandException() { }

    public NoCommandException(String message) {
        super(message);
    }

    public NoCommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoCommandException(Throwable cause) {
        super(cause);
    }

    public NoCommandException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
