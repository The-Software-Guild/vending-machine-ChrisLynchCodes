package org.chris.dao;

import org.chris.dto.Item;

import java.util.List;

public interface ItemDao {

    List<Item> getItems()throws ItemPersistenceException;

    Item getItem(String itemId)throws ItemPersistenceException;

    Item addItem(Item item) throws ItemPersistenceException;

    void updateItem(Item editedItem) throws ItemPersistenceException;
}
