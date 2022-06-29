package org.chris.service;

import org.chris.dao.ItemDao;
import org.chris.dao.ItemPersistenceException;
import org.chris.dao.VendingMachineAuditDao;
import org.chris.dto.Change;
import org.chris.dto.Item;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer {


    private final ItemDao ITEM_DAO;
    private final VendingMachineAuditDao AUDIT_DAO;
    private BigDecimal sessionBalance;

    public VendingMachineServiceLayerImpl(ItemDao ITEM_DAO, VendingMachineAuditDao AUDIT_DAO)
    {
        this.ITEM_DAO = ITEM_DAO;
        this.AUDIT_DAO = AUDIT_DAO;
        sessionBalance = new BigDecimal("0.00").setScale(2, RoundingMode.HALF_UP);
    }

    //buy item from vending machine
    public String buyItemFromVendingMachine(String itemId) throws ItemPersistenceException, ItemNoItemInventoryException, VendingMachineInsufficientFundsException
    {
        Item item = ITEM_DAO.getItem(itemId);
        String change;
        if (item == null) {
            throw new ItemNoItemInventoryException("ERROR: Item " + itemId + " not found");
        }
        if (item.getQuantity() == 0) {
            throw new ItemNoItemInventoryException("ERROR: Item " + itemId + " is out of stock");
        }
        if (item.getPrice().compareTo(sessionBalance) > 0) {
            throw new VendingMachineInsufficientFundsException("Insufficient funds for item " + itemId + " Please insert $" + (item.getPrice().subtract(sessionBalance)));

        } else {

            updateItemQuantity(item);
            //if audi
            if (item.getQuantity() <= 3) {
                AUDIT_DAO.writeAuditEntry("LOW STOCK:" + LocalDate.now() + " " + item.getITEM_ID() + " " + item.getTitle() + " Stock Available " + item.getQuantity());
            }

            //return change as string to be displayed on screen and update session balance
            change = Change.getChange(sessionBalance, item.getPrice());
            sessionBalance = sessionBalance.subtract(item.getPrice());


            AUDIT_DAO.writeAuditEntry("SOLD:" + LocalDate.now() + " " + item.getITEM_ID() + " " + item.getTitle() + " " + item.getPrice());

        }
        return change;
    }

    @Override
    public void updateItemQuantity(Item item) throws ItemPersistenceException
    {
        item.setQuantity(item.getQuantity() - 1);
        ITEM_DAO.updateItem(item);
    }


    //gets all items in DAO and returns a list of items with quantity > 0
    @Override
    public List<Item> getAllItems() throws ItemPersistenceException
    {
        return ITEM_DAO.getItems().stream()
                .filter(item -> item.getQuantity() > 0)
                .sorted(Comparator.comparing((i -> Integer.parseInt(i.getITEM_ID()))))
                .collect(java.util.stream.Collectors.toList());

    }

    @Override
    public BigDecimal addToSessionBalance(BigDecimal amount) throws ItemPersistenceException
    {
        sessionBalance = sessionBalance.add(amount);
        AUDIT_DAO.writeAuditEntry("MONEY ADDED:" + LocalDate.now() + " " + amount);
        return sessionBalance;
    }

    @Override
    public BigDecimal getSessionBalance()
    {
        return sessionBalance;
    }


}
