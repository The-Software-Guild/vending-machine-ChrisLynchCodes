package org.chris.service;

import org.chris.dao.ItemDao;
import org.chris.dao.ItemPersistenceException;
import org.chris.dao.VendingMachineAuditDao;
import org.chris.dto.Change;
import org.chris.dto.Item;

import java.math.BigDecimal;
import java.util.List;

public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer {




    //TODO ======================================== Start working on the controller and view ==============================================

    private ItemDao itemDao;
    private VendingMachineAuditDao auditDao;

    public VendingMachineServiceLayerImpl(ItemDao itemDao, VendingMachineAuditDao auditDao)
    {
        this.itemDao = itemDao;
        this.auditDao = auditDao;
    }

    //buy item from vending machine
    public void buyItemFromVendingMachine(String itemId, BigDecimal userBalance) throws ItemPersistenceException, ItemNoItemInventoryException, VendingMachineInsufficientFundsException
    {
        Item item = itemDao.getItem(itemId);
        if (item == null) {
            throw new ItemNoItemInventoryException("ERROR: Item " + itemId + " not found");
        }

        if (item.getPrice().compareTo(userBalance) > 0) {
            System.out.println("ERROR: Insufficient funds");
            System.out.println("Please insert $" + (item.getPrice().subtract(userBalance)));
            throw new VendingMachineInsufficientFundsException("ERROR: Insufficient funds for item " + itemId);
        } else {
            item.setQuantity(item.getQuantity() - 1);
            itemDao.updateItem(item.getItemId(), item);

            //TODO check what logs should contain and what should be in the audit log
            auditDao.writeAuditEntry(item.getItemId() + " " + item.getTitle() + " " + item.getPrice());

            //TODO the change is returned as a string currently what should it be?
            String change = Change.getChange(userBalance, item.getPrice());
            System.out.println(change);
        }
    }


    //gets all items in DAO and returns a list of items with quantity > 0
    @Override
    public List<Item> getAllItems() throws ItemPersistenceException
    {
        return itemDao.getITEMS().stream().filter(item -> item.getQuantity() > 0).collect(java.util.stream.Collectors.toList());
    }


    private void validateItemData(Item item) throws ItemDataValidationException
    {
        if (item.getItemId() == null || item.getItemId().length() == 0) {
            throw new ItemDataValidationException("Item Id is required.");
        }
        if (item.getTitle() == null || item.getTitle().length() == 0) {
            throw new ItemDataValidationException("Title is required.");
        }
        if (item.getQuantity() < 0) {
            throw new ItemDataValidationException("Quantity is required.");
        }
        //TODO: currently accepts 0 as a valid price, but should be changed to throw exception
        if (item.getPrice() == null) {
            throw new ItemDataValidationException("Cost is required.");
        }

    }

    @Override
    public void addItemToVendingMachine(Item item) throws ItemDuplicateIdException, ItemDataValidationException, ItemPersistenceException
    {
        //ensure item id is unique
        if (itemDao.getItem(item.getItemId()) != null) {
            throw new ItemDuplicateIdException(
                    "ERROR: Could not create item.  item Id "
                            + item.getItemId()
                            + " already exists");
        }

        //throw exception if any validation rules fail
        validateItemData(item);
        //add item to dao
        itemDao.addItem(item.getItemId(), item);
        //log item addition
        auditDao.writeAuditEntry("Item " + item.getItemId() + " was added to the vending machine");


    }

    //remove item from vending machine
    @Override
    public void removeItemFromVendingMachine(String itemId) throws ItemPersistenceException
    {
        Item item = itemDao.getItem(itemId);
        if (item == null) {
            throw new ItemPersistenceException("ERROR: Item " + itemId + " not found");
        }
        itemDao.removeItem(itemId);
        auditDao.writeAuditEntry("Item " + itemId + " was removed from the vending machine");
    }

    //update item in vending machine
    @Override
    public void updateItemInVendingMachine(Item item) throws ItemPersistenceException
    {
        Item itemToUpdate = itemDao.getItem(item.getItemId());
        if (itemToUpdate == null) {
            throw new ItemPersistenceException("ERROR: Item " + item.getItemId() + " not found");
        }
        itemDao.updateItem(item.getItemId(), item);
        auditDao.writeAuditEntry("Item " + item.getItemId() + " was updated in the vending machine");
    }

}
