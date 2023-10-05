package com.castLabs.file.exception;

public class UnreadableFileException extends RuntimeException {
    public UnreadableFileException() {
        super("File could not be read.");
    }
}
