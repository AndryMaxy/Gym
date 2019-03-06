package util;

import util.exception.EncoderException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

/**
 *  This util class is used for hashing data.
 *  @author Andrey Akulich
 */
public class Encoder {

    /**
     * Hash algorithm name
     */
    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";

    /**
     * Iteration count for hashing
     */
    private static final int ITERATION_COUNT = 30000;

    /**
     * Key length for hashing
     */
    private static final int KEY_LENGTH = 128;

    /**
     * This class represents initialization-on-demand holder idiom for {@link Encoder}
     */
    private static class EncoderHolder {

        /**
         * Instance of {@link Encoder}
         */
        static final Encoder INSTANCE = new Encoder();
    }

    /**
     * Constructs {@link Encoder} instance
     */
    private Encoder(){}

    /**
     * Returns {@link Encoder} instance.
     * @return {@link Encoder} instance
     */
    public static Encoder getInstance() {
        return EncoderHolder.INSTANCE;
    }

    /**
     * Encodes password.
     *
     * @param data password
     * @return sting array with generated hash and salt
     * @throws EncoderException if key spec is invalid or hash algorithm does not exist
     */
    public String[] encode(char[] data) throws EncoderException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        byte[] hash = generateHash(data, salt);
        return new String[]{new String(hash), new String(salt)};
    }

    /**
     *
     * @param data password
     * @param hash hash byte array
     * @param salt salt for hash
     * @return {@code true} if password according hash otherwise {@code false}
     * @throws EncoderException if key spec is invalid or hash algorithm does not exist
     */
    public boolean check(char[] data, byte[] hash, byte[] salt) throws EncoderException{
        byte[] newHash = generateHash(data, salt);
        return Arrays.equals(hash, newHash);
    }

    /**
     * Generates hash.
     *
     * @param data password
     * @param salt salt for hash
     * @return hash as byte array
     * @throws EncoderException if key spec is invalid or hash algorithm does not exist
     */
    private byte[] generateHash(char[] data, byte[] salt) throws EncoderException {
        KeySpec spec = new PBEKeySpec(data, salt, ITERATION_COUNT, KEY_LENGTH);
        SecretKeyFactory factory;
        try {
            factory = SecretKeyFactory.getInstance(ALGORITHM);
            return factory.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new EncoderException(e);
        }
    }
}
