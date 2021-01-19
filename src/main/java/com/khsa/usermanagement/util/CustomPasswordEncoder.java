package com.khsa.usermanagement.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CustomPasswordEncoder implements org.springframework.security.crypto.password.PasswordEncoder {

    @Value("${user.authKey:default_key}")
    private String authKey;

    /**
     * Encode the raw password. Generally, a good encoding algorithm applies a SHA-1 or
     * greater hash combined with an 8-byte or greater randomly generated salt.
     */
    @Override
    public String encode(CharSequence rawPassword) {

        if (rawPassword == null) {
            return null;
        }

        return PasswordEncoder.getHashPassword(rawPassword.toString(), authKey);
    }

    /**
     * Verify the encoded password obtained from storage matches the submitted raw
     * password after it too is encoded. Returns true if the passwords match, false if
     * they do not. The stored password itself is never decoded.
     *
     * @param rawPassword     the raw password to encode and match
     * @param encodedPassword the encoded password from storage to compare with
     * @return true if the raw password, after encoding, matches the encoded password from
     * storage
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
       return PasswordEncoder.matchUserPassword(encodedPassword, authKey, rawPassword.toString());
    }
}
