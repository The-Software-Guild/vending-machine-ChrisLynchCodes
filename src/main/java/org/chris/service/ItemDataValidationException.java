package org.chris.service;

public class ItemDataValidationException extends Exception {

    public ItemDataValidationException(String message) {
        super(message);
    }

    public ItemDataValidationException(String message, Throwable cause) {
        super(message, cause);
    }

}
