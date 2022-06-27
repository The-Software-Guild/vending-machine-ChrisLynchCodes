package org.chris.contoller;

import org.chris.dao.ItemPersistenceException;
import org.chris.dto.Item;
import org.chris.service.ItemNoItemInventoryException;
import org.chris.service.VendingMachineInsufficientFundsException;
import org.chris.service.VendingMachineServiceLayer;
import org.chris.ui.VendingMachineView;

import java.math.BigDecimal;
import java.util.List;

public class VendingMachineController {

    private final VendingMachineView view;

    private final VendingMachineServiceLayer service;


    public VendingMachineController(VendingMachineServiceLayer service, VendingMachineView view)
    {
        this.view = view;
        this.service = service;
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
            view.displayErrorMessage(e.getMessage());
        }

    }

    private void showItems()
    {
    }

    private void viewBalance()
    {
        BigDecimal balance = service.getSessionBalance();
        view.printBalance(balance);
    }


    private void insertMoney() throws ItemPersistenceException
    {

        String money = view.getUserMoney();
        BigDecimal balance = service.addToSessionBalance(new BigDecimal(money));
        view.printBalance(balance);
    }

    private void buyItem() throws ItemPersistenceException, VendingMachineInsufficientFundsException, ItemNoItemInventoryException
    {
        try {
            String itemChoice = view.getItemChoice();
            if (itemChoice.equals("")) {
                view.displayErrorMessage("Please enter an item number.");
            } else if (itemChoice.equals("0")) {
                this.run();
            } else {
                String response = service.buyItemFromVendingMachine(itemChoice);
                view.successfulPurchase(response);
            }

        } catch (ItemPersistenceException | VendingMachineInsufficientFundsException | ItemNoItemInventoryException e) {
            view.displayErrorMessage(e.getMessage());

        }


    }

    private void displayItemList() throws ItemPersistenceException
    {

        view.displayItemList(service.getAllItems());
    }

    private void unknownCommand()
    {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage()
    {
        view.displayExitBanner();
    }

    private int getMenuSelection()
    {
        return view.printMenuAndGetSelection();
    }
}
