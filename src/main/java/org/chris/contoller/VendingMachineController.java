package org.chris.contoller;

import org.chris.dao.ItemPersistenceException;
import org.chris.service.ItemNoItemInventoryException;
import org.chris.service.VendingMachineInsufficientFundsException;
import org.chris.service.VendingMachineServiceLayer;
import org.chris.ui.VendingMachineView;

import java.math.BigDecimal;

public class VendingMachineController {

    private final VendingMachineView VIEW;

    private final VendingMachineServiceLayer SERVICE;


    public VendingMachineController(VendingMachineServiceLayer SERVICE, VendingMachineView VIEW)
    {
        this.VIEW = VIEW;
        this.SERVICE = SERVICE;
    }

    public void run()
    {
        boolean keepGoing = true;
        int menuSelection;

        try {
            displayItemList();
            while (keepGoing) {
                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1 -> insertMoney();
                    case 2 -> displayItemList();
                    case 3 -> buyItem();
                    case 4 -> viewBalance();
                    case 5 -> keepGoing = false;
                    default -> unknownCommand();
                }
            }
            exitMessage();
        } catch (ItemPersistenceException | ItemNoItemInventoryException | VendingMachineInsufficientFundsException e) {
            VIEW.displayErrorMessage(e.getMessage());
        }

    }

    private void viewBalance()
    {
        BigDecimal balance = SERVICE.getSessionBalance();
        VIEW.printBalance(balance);
    }

    private void insertMoney() throws ItemPersistenceException
    {
        String money = VIEW.getUserMoney();
        try {
            BigDecimal balance = SERVICE.addToSessionBalance(new BigDecimal(money));
        } catch (NumberFormatException e) {
            VIEW.displayErrorMessage("Invalid input - please enter a numerical value.");
        }

    }

    private void buyItem() throws ItemPersistenceException, VendingMachineInsufficientFundsException, ItemNoItemInventoryException
    {
        try {
            String itemChoice = VIEW.getItemChoice();
            if (itemChoice.equals("")) {
                VIEW.displayErrorMessage("Please enter an item number.");
            } else if (itemChoice.equals("0")) {
                this.run();
            } else {
                String response = SERVICE.buyItemFromVendingMachine(itemChoice);
                VIEW.successfulPurchase(response);
            }

        } catch (ItemPersistenceException | VendingMachineInsufficientFundsException | ItemNoItemInventoryException e) {
            VIEW.displayErrorMessage(e.getMessage());

        }

    }

    private void displayItemList() throws ItemPersistenceException
    {

        VIEW.displayItemList(SERVICE.getAllItems());
    }

    private void unknownCommand()
    {
        VIEW.displayUnknownCommandBanner();
    }

    private void exitMessage()
    {
        VIEW.displayExitBanner(SERVICE.getSessionBalance());
    }

    private int getMenuSelection()
    {
        return VIEW.printMenuAndGetSelection();
    }
}
