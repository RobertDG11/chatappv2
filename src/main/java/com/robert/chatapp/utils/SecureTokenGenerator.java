package com.robert.chatapp.utils;

import java.security.SecureRandom;

public class SecureTokenGenerator {

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final int SECURE_TOKEN_LENGTH = 256;

    private static final SecureRandom random = new SecureRandom();

    private static final char[] symbols = CHARACTERS.toCharArray();

    private static final char[] buf = new char[SECURE_TOKEN_LENGTH];

    public static String nextToken() {

        for (int idx = 0; idx < SECURE_TOKEN_LENGTH; ++idx) {

            buf[idx] = symbols[random.nextInt(symbols.length)];
        }
        return new String(buf);
    }
}
