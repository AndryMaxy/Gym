package by.epam.akulich.gym.connection.exception;

/**
 * The exception throws when connection pool init or finalize troubles happens.
 * @author Andrey Akulich
 */
public class DBException extends RuntimeException {

    /**
     * SerialVersionUID is used for interoperability.
     */
    private static final long serialVersionUID = 1628827088870003890L;

    /**
     * Constructs a new exception with {@code null} as its detail message.
     */
    public DBException() {}

    /**
     * Constructs a new exception with the specified detail message.
     * @param message he detail message. The detail message is saved for
     *        later retrieval by the {@link #getMessage()} method.
     */
    public DBException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.
     *
     * @param  message the detail message (which is saved for later retrieval
     *         by the {@link #getMessage()} method).
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).
     */
    public DBException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause and a detail message.
     * @param cause the cause of exception
     */
    public DBException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new exception with the specified detail message,
     * cause, suppression enabled or disabled, and writable stack
     * trace enabled or disabled.
     *
     * @param  message the detail message.
     * @param cause the cause.  (A {@code null} value is permitted,
     * and indicates that the cause is nonexistent or unknown.)
     * @param enableSuppression whether or not suppression is enabled
     *                          or disabled
     * @param writableStackTrace whether or not the stack trace should
     *                           be writable
     */
    public DBException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
