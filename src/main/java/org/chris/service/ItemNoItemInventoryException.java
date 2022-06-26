package org.chris.service;

public class ItemNoItemInventoryException extends Exception {
    public ItemNoItemInventoryException(String message)
    {
        super(message);
    }

    public ItemNoItemInventoryException(String message, Throwable cause)
    {
        super(message, cause);
    }
}

