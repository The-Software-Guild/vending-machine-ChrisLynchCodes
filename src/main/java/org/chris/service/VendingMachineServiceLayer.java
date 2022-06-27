package org.chris.service;

import org.chris.dao.ItemPersistenceException;
import org.chris.dto.Item;

import java.math.BigDecimal;
import java.util.List;

public interface VendingMachineServiceLayer {

    //This is now the Data Access API for the application - Sitting in front of the DAO

    BigDecimal addToSessionBalance(BigDecimal amount) throws ItemPersistenceException;

    BigDecimal getSessionBalance();

    String buyItemFromVendingMachine(String itemId) throws ItemPersistenceException, ItemNoItemInventoryException, VendingMachineInsufficientFundsException;


    //get all items in vending machine
    List<Item> getAllItems() throws ItemPersistenceException;
}
