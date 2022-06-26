package org.chris.dao;

import org.chris.dto.Item;
import org.chris.service.ItemNoItemInventoryException;

import java.util.List;

public interface ItemDao {

    /**
     * Adds the given item to the vending machine and associates it with the given
     * item id. If there is already a item associated with the given
     * item id it will return that item object, otherwise it will
     * return null.
     *
     * @param itemId id with which item is to be associated
     * @param item item to be added to the vending machine
     * @return the item object previously associated with the given
     * item id if it exists, null otherwise
     */
    Item addItem(String itemId, Item item)throws ItemPersistenceException;

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


    /**
     * Removes from the library the item associated with the given id.
     * Returns the item object that is being removed or null if
     * there is no item associated with the given id
     *
     * @param itemId id of item to be removed
     * @return item object that was removed or null if no item
     * was associated with the given item id
     */
    Item removeItem(String itemId)throws ItemPersistenceException;

    /**
     * Updates the item associated with the given id.
     * Returns the item object that is being edited or null if
     * there is no item associated with the given id
     *
     * @param itemId id of item to be edited
     * @return item object that was edited or null if no item
     * was associated with the given item id
     */
    Item updateItem(String itemId, Item editedItem)throws ItemPersistenceException;
}
