package util.exception;

public class IncorrectURIException extends Exception {

    private static final long serialVersionUID = 4685962452390619255L;

    public IncorrectURIException() {
    }

    public IncorrectURIException(String message) {
        super(message);
    }

    public IncorrectURIException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectURIException(Throwable cause) {
        super(cause);
    }

    public IncorrectURIException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
