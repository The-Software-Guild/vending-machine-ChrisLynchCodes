package org.chris.service;

import org.chris.dao.ItemDao;
import org.chris.dao.ItemPersistenceException;
import org.chris.dao.VendingMachineAuditDao;
import org.chris.dto.Change;
import org.chris.dto.Item;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VendingMachineServiceLayerImplTest {
    //test service layer business logic and validation8 rules for the service layer
    private VendingMachineServiceLayer service;


    public VendingMachineServiceLayerImplTest()
    {
        ItemDao dao = new ItemDaoStubImpl();
        VendingMachineAuditDao auditDao = new VendingMachineAuditDaoStubImpl();
        service = new VendingMachineServiceLayerImpl(dao, auditDao);
    }
    @Test
    void getChange()
    {
        BigDecimal sessionBalance = new BigDecimal("3.00");
        BigDecimal itemPrice = new BigDecimal("0.99");
        String changeString = Change.getChange(sessionBalance, itemPrice);
    }


    @Test
    void getAllItems() throws Exception
    {
        List<Item> items = service.getAllItems();
        //2 items should be returned as one has a quantity of 0
        assertEquals(2, items.size(), "Incorrect number of items returned.");
        assertEquals("1", items.get(0).getITEM_ID(), "Incorrect item returned.");
        assertEquals("2", items.get(1).getITEM_ID(), "Incorrect item returned.");
        assertTrue(items.get(0).getQuantity() > 0, "Item should have a positive quantity.");
        assertTrue(items.get(1).getQuantity() > 0, "Item should have a positive quantity.");

    }

    @Test
    void buyItemFromVendingMachineWithNoStock()
    {
        //itemId 3 has quantity = 0 in the stub
        String itemId = "3";
        BigDecimal sessionBalance = new BigDecimal("3.00");

        try {
            service.addToSessionBalance(sessionBalance);
            service.buyItemFromVendingMachine(itemId);
            //If the call executes and no exception is thrown
            fail("Expected ItemNoInventory Exception was not thrown.");
        } catch (ItemPersistenceException | VendingMachineInsufficientFundsException e) {
            // ASSERT
            fail("Incorrect exception was thrown.");
        } catch (ItemNoItemInventoryException e) {
            assertEquals("ERROR: Item " + itemId + " not found", e.getMessage());
        }
    }

    @Test
    void buyItemFromVendingMachineShortBalance()
    {
        //itemId 1 has price = $0.99 in the stub
        String itemId = "1";
        BigDecimal sessionBalance = new BigDecimal("0.50");
        try {
            service.addToSessionBalance(sessionBalance);
            service.buyItemFromVendingMachine(itemId);
            //If the call executes and no exception is thrown
            fail("Expected VendingMachineInsufficientFundsException Exception was not thrown.");
        } catch (ItemPersistenceException | ItemNoItemInventoryException e) {
            // ASSERT
            fail("Incorrect exception was thrown.");
        } catch (VendingMachineInsufficientFundsException e) {
            assertEquals("Insufficient funds for item " + itemId + " Please insert $0.49", e.getMessage());
        }

    }



    @Test
    void buyItemFromVendingMachineNoChangeReceived()
    {
        String itemId = "1";
        BigDecimal sessionBalance = new BigDecimal("0.99");

        try {
            service.addToSessionBalance(sessionBalance);
            service.buyItemFromVendingMachine(itemId);
            assertEquals(9, service.getAllItems().get(0).getQuantity());
        } catch (ItemPersistenceException | ItemNoItemInventoryException | VendingMachineInsufficientFundsException e) {
            fail("Item was valid no exception should have been thrown");
        }
    }


    @Test
    void buyItemFromVendingMachineReceiveChange()
    {
        //itemId 1 has quantity = 10 in the stub
        //itemId 1 has price = $0.99 in the stub
        String itemId = "1";
        BigDecimal sessionBalance = new BigDecimal("3.00");

        try {
            service.addToSessionBalance(sessionBalance);
            service.buyItemFromVendingMachine(itemId);
            assertEquals(9, service.getAllItems().get(0).getQuantity());
            assertEquals("Change:2 DOLLAR 0 FIFTY 0 QUARTER 1 PENNY", Change.getChange(sessionBalance, service.getAllItems().get(0).getPrice()));
        } catch (ItemPersistenceException | ItemNoItemInventoryException | VendingMachineInsufficientFundsException e) {
            fail("Item was valid no exception should have been thrown");
        }


    }



}
