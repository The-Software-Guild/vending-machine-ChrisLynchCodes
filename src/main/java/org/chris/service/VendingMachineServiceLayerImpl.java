package org.chris.service;

import org.chris.dao.ItemDao;
import org.chris.dao.ItemPersistenceException;
import org.chris.dao.VendingMachineAuditDao;
import org.chris.dto.Change;
import org.chris.dto.Item;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer {




    private ItemDao itemDao;
    private VendingMachineAuditDao auditDao;
    private BigDecimal sessionBalance;

    public VendingMachineServiceLayerImpl(ItemDao itemDao, VendingMachineAuditDao auditDao)
    {
        this.itemDao = itemDao;
        this.auditDao = auditDao;
        sessionBalance = new BigDecimal("0.00");
    }

    //buy item from vending machine
    public String buyItemFromVendingMachine(String itemId) throws ItemPersistenceException, ItemNoItemInventoryException, VendingMachineInsufficientFundsException
    {
        Item item = itemDao.getItem(itemId);
        String change;
        if (item == null) {
            throw new ItemNoItemInventoryException("ERROR: Item " + itemId + " not found");
        }

        if (item.getPrice().compareTo(sessionBalance) > 0) {
            throw new VendingMachineInsufficientFundsException("Insufficient funds for item " + itemId + " Please insert $" + (item.getPrice().subtract(sessionBalance)));

        } else {

            itemDao.updateItemQuantity(item);
            //if audi
            if(item.getQuantity() <= 0)
            {
                auditDao.writeAuditEntry("LOW STOCK:" + LocalDate.now() +  " " + item.getItemId() + " " + item.getTitle() + " Stock Available " + item.getQuantity());
            }

            //return change as string to be displayed on screen and update session balance
            change = Change.getChange(sessionBalance, item.getPrice());
            sessionBalance = sessionBalance.subtract(item.getPrice());


            auditDao.writeAuditEntry("SOLD:" + LocalDate.now() +  " " + item.getItemId() + " " + item.getTitle() + " " + item.getPrice());
            System.out.println(change);
        }
        return change;
    }


    //gets all items in DAO and returns a list of items with quantity > 0
    @Override
    public List<Item> getAllItems() throws ItemPersistenceException
    {

        return itemDao.getITEMS().stream()
                .filter(item -> item.getQuantity() > 0)
                .sorted(Comparator.comparing((i -> Integer.parseInt(i.getItemId()))))
                .collect(java.util.stream.Collectors.toList());

    }

    @Override
    public BigDecimal addToSessionBalance(BigDecimal amount) throws ItemPersistenceException
    {
        sessionBalance = sessionBalance.add(amount);
        auditDao.writeAuditEntry("MONEY ADDED:" + LocalDate.now() +  " " + amount);
        return sessionBalance;
    }

    @Override
    public BigDecimal getSessionBalance()
    {
        return sessionBalance;
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

        if (item.getPrice() == null) {
            throw new ItemDataValidationException("Cost is required.");
        }

    }




}
