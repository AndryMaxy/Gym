package connection.exception;

public class DBFinishException extends RuntimeException {

    public DBFinishException() {
    }

    public DBFinishException(String message) {
        super(message);
    }

    public DBFinishException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBFinishException(Throwable cause) {
        super(cause);
    }

    public DBFinishException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
