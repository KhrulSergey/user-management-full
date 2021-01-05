package com.khsa.usermanagement.util;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


public final class PasswordHelper {

    //Should be more than one
    static int CRYPTO_ITERATIONS = 1;
    /**
     * Key length of password ans Salt - 64bit
     */
    static int KEY_LENGTH = 64;

    /**
     * Generate 8 bit password for User with digits, upper and lower case String
     *
     * @return
     */
    public static String generatePassword() {
        PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                .useDigits(true)
                .useLower(true)
                .useUpper(true)
                .build();
        String password = passwordGenerator.generate(KEY_LENGTH);
        return password;
    }

    /**
     * @param actualPassword
     * @param actualSalt
     * @param checkingPassword
     * @return
     */
    public static boolean matchUserPassword(String actualPassword, String actualSalt, String checkingPassword) {
        if (checkingPassword != null && actualPassword != null) {
            String hashedCheckedPassword = hashPassword(checkingPassword, actualSalt, CRYPTO_ITERATIONS, KEY_LENGTH);
            return hashedCheckedPassword.equals(actualPassword);
        }
        return false;
    }

    /**
     * @param password
     * @param salt
     * @return
     */
    public static String getHashPassword(final String password, final String salt) {
        return hashPassword(password, salt, CRYPTO_ITERATIONS, KEY_LENGTH);
    }

    /**
     * @param password
     * @param salt
     * @param iterations
     * @param keyLength
     * @return
     */
    private static String hashPassword(final String password, final String salt,
                                       final int iterations, final int keyLength) {
        PBEKeySpec spec = null;
        try {
            spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), iterations, keyLength * 4);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            SecretKey key = skf.generateSecret(spec);
            return toHex(key.getEncoded());

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            if (spec != null) spec.clearPassword();
        }
    }

    private static String toHex(byte[] array) throws NoSuchAlgorithmException
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }
}
