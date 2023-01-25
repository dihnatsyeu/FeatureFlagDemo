package com.example.mettle.feature.flag.exceptions;

public class FeatureFlagCreateException extends RuntimeException {

    public FeatureFlagCreateException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public FeatureFlagCreateException(String message) {
        super(message);
    }
}
