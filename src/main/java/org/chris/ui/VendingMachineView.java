package org.chris.ui;

import org.chris.dto.Item;

import java.math.BigDecimal;
import java.util.List;

public class VendingMachineView {
    private final UserIO io;

    public VendingMachineView(UserIO io)
    {
        this.io = io;
    }

    public int printMenuAndGetSelection()
    {
        io.print("Main Menu");
        io.print("1. Insert Money");
        io.print("2. Show Items");
        io.print("3. Buy Item");
        io.print("4. View Balance");
        io.print("5. Exit");


        return io.readInt("Please select from the above choices.", 1, 5);
    }


    public String getUserMoney()
    {
        return io.readString("Please enter the amount of money you wish to insert e.g. 0.99/2.50/9.99");
    }


    public void displayErrorMessage(String errorMsg)
    {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }

    public void printBalance(BigDecimal balance)
    {
        io.print("Your balance is: $" + balance);
    }

    public void displayItemList(List<Item> itemList)
    {
        for (Item currentItem : itemList) {

            //using string.format
            String itemInfo = String.format("\n[%s]Name-%s - Price:$%s", currentItem.getITEM_ID(), currentItem.getTitle(), currentItem.getPrice());

            io.print(itemInfo.toString());
        }
        System.out.println("");

    }

    public String getItemChoice()
    {
        return io.readString("Please enter the item number you wish to purchase.");
    }


    //////////Banners/////////
    public void displayExitBanner(BigDecimal balance)
    {
        if (balance.compareTo(BigDecimal.ZERO) == 0) {
            io.print("Thank you for shopping at the Vending Machine!");
        } else {
            io.print("Thank you for shopping at the Vending Machine! Your change is: $" + balance);
        }

    }


    public void displayUnknownCommandBanner()
    {
        io.print("Unknown Command!!!");
    }

    public void successfulPurchase(String output)
    {
        io.print(output);
    }


}
