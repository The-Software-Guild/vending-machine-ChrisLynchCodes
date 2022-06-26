package org.chris.service;

public class ItemDuplicateIdException extends Exception {
    public ItemDuplicateIdException(String message)
    {
        super(message);
    }

    public ItemDuplicateIdException(String message, Throwable cause)
    {
        super(message, cause);
    }
}

