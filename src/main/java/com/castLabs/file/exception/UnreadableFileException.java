package com.castLabs.file.exception;

public class UnreadableFileException extends RuntimeException {

    public static final String UNREADABLE_FILE_ERROR_MESSAGE = "File could not be read.";

    public UnreadableFileException() {
        super(UNREADABLE_FILE_ERROR_MESSAGE);
    }
}
