package com.example.mettle.feature.flag.utils;

import java.util.Random;

public class RandomDataFactory {

    public static String randomString() {
        return randomString(10);
    }

    private static String randomString(int targetStringLength) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
