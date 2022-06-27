package org.chris.dao;

import org.chris.dto.Item;
import org.chris.service.ItemNoItemInventoryException;

import java.util.List;

public interface ItemDao {



    /**
     * Returns a List of all items in the vending machine.
     *
     * @return List containing all items in the vending machine.
     */
    List<Item> getITEMS()throws ItemPersistenceException;

    /**
     * Returns the item object associated with the given item id.
     * Returns null if no such item exists
     *
     * @param itemId ID of the item to retrieve
     * @return the item object associated with the given item id,
     * null if no such item exists
     */
    Item getItem(String itemId)throws ItemPersistenceException;

    Item addItem(Item item) throws ItemPersistenceException;

    /**
     * Updates the item associated with the given id.
     * Returns the item object that is being edited or null if
     * there is no item associated with the given id
     *
     * @param editedItem item to be edited
     * @return item object that was edited or null if no item
     * was associated with the given item id
     */
    void updateItemQuantity(Item editedItem) throws ItemPersistenceException;
}
