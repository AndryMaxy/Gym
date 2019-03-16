package by.epam.akulich.gym.util.exception;

import by.epam.akulich.gym.util.Encoder;

/**
 * This exception throws in {@link Encoder} when it has exception situation.
 * @author Andrey Akulich
 */
public class EncoderException extends Exception {

    /**
     * SerialVersionUID is used for interoperability.
     */
    private static final long serialVersionUID = -2034611106110318180L;

    /**
     * Constructs a new exception with {@code null} as its detail message.
     */
    public EncoderException() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.
     * @param message he detail message. The detail message is saved for
     *        later retrieval by the {@link #getMessage()} method.
     */
    public EncoderException(String message) {
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
    public EncoderException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause and a detail message.
     * @param cause the cause of exception
     */
    public EncoderException(Throwable cause) {
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
    protected EncoderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
