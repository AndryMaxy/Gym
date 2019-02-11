package util;

import util.exception.EncoderException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

public class Encoder {

    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final int ITERATION_COUNT = 30000;
    private static final int KEY_LENGTH = 128;

    public static String[] encode(String data) throws EncoderException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        byte[] hash = getHash(data, salt);
        return new String[]{new String(hash), new String(salt)};
    }

    public static boolean check(String data, byte[] hash, byte[] salt) throws EncoderException{
        byte[] newHash = getHash(data, salt);
        return Arrays.equals(hash, newHash);
    }

    private static byte[] getHash(String data, byte[] salt) throws EncoderException {
        KeySpec spec = new PBEKeySpec(data.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH);
        SecretKeyFactory factory;
        try {
            factory = SecretKeyFactory.getInstance(ALGORITHM);
            return factory.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new EncoderException(e);
        }
    }
}
