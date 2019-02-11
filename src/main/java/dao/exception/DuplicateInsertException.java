package dao.exception;

public class DuplicateInsertException extends Exception {

    public DuplicateInsertException() {
    }

    public DuplicateInsertException(String message) {
        super(message);
    }

    public DuplicateInsertException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateInsertException(Throwable cause) {
        super(cause);
    }

    public DuplicateInsertException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
