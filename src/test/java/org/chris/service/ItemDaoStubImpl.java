package org.chris.service;

import org.chris.dao.ItemDao;
import org.chris.dao.ItemPersistenceException;
import org.chris.dto.Item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoStubImpl implements ItemDao {


    public Item item;
    public Item itemTwo;
    public Item itemThree;

    public ItemDaoStubImpl()
    {

        item = new Item("1");
        item.setTitle("Apple");
        item.setPrice(new BigDecimal("0.99"));
        item.setQuantity(10);

        itemTwo = new Item("2");
        itemTwo.setTitle("Orange");
        itemTwo.setPrice(new BigDecimal("1.99"));
        itemTwo.setQuantity(5);

        itemThree = new Item("3");
        itemThree.setTitle("Water");
        itemThree.setPrice(new BigDecimal("0.99"));
        itemThree.setQuantity(0);


    }

    public ItemDaoStubImpl(Item item, Item itemTwo)
    {
        this.item = item;
        this.itemTwo = itemTwo;
    }

    @Override
    public Item addItem(String itemId, Item itemToAdd) throws ItemPersistenceException
    {
        if (itemId.equals(item.getItemId())) return item;
        return null;
    }

    @Override
    public List<Item> getITEMS() throws ItemPersistenceException
    {
        List<Item> items = new ArrayList<Item>();
        items.add(item);
        items.add(itemTwo);
        return items;
    }

    @Override
    public Item getItem(String itemId) throws ItemPersistenceException
    {
        if (itemId.equals(item.getItemId()))
            return item;
        return null;
    }

    @Override
    public Item removeItem(String itemId) throws ItemPersistenceException
    {
        if (itemId.equals(item.getItemId()))
            return item;
        return null;
    }

    @Override
    public Item updateItem(String itemId, Item editedItem) throws ItemPersistenceException
    {
        if (itemId.equals(item.getItemId()))
            return item;
        return null;
    }
}
