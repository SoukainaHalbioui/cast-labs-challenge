package com.castLabs.file.exception;

public class InvalidUrlException extends RuntimeException {

    public static final String INVALID_URL_ERROR_MESSAGE = "The URL provided is invalid.";

    public InvalidUrlException() {
        super(INVALID_URL_ERROR_MESSAGE);
    }
}
