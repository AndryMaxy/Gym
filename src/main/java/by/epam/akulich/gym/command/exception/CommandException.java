package by.epam.akulich.gym.command.exception;

import by.epam.akulich.gym.command.Command;

/**
 * This exception throws in {@link Command} when it has exception situation.
 * @author Andrey Akulich
 */
public class CommandException extends Exception {

    /**
     * SerialVersionUID is used for interoperability.
     */
    private static final long serialVersionUID = -1912504709867943650L;

    /**
     * Constructs a new exception with {@code null} as its detail message.
     */
    public CommandException() {
    }

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message he detail message. The detail message is saved for
     *        later retrieval by the {@link #getMessage()} method.
     */
    public CommandException(String message) {
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
    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause and a detail message.
     *
     * @param cause the cause of exception
     */
    public CommandException(Throwable cause) {
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
    public CommandException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
