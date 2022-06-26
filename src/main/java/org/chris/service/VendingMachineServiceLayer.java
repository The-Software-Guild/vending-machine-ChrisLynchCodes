package org.chris.service;

import org.chris.dao.ItemPersistenceException;
import org.chris.dto.Item;

import java.math.BigDecimal;
import java.util.List;

public interface VendingMachineServiceLayer {

    //This is now the Data Access API for the application - Sitting in front of the DAO
    void addItemToVendingMachine(Item item) throws ItemDuplicateIdException, ItemDataValidationException, ItemPersistenceException;

    void buyItemFromVendingMachine(String itemId, BigDecimal userBallance) throws ItemPersistenceException, ItemNoItemInventoryException, VendingMachineInsufficientFundsException;

    //remove item from vending machine
    void removeItemFromVendingMachine(String itemId) throws ItemPersistenceException;

    //update item in vending machine
    void updateItemInVendingMachine(Item item) throws ItemPersistenceException;

    //get all items in vending machine
    List<Item> getAllItems() throws ItemPersistenceException;
}
