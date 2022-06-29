package org.chris.dto;

import java.math.BigDecimal;

public class Item {

    //Instance Variables
    private final String ITEM_ID;
    private String title;
    private BigDecimal price;
    private int quantity;

    //Constructor
    public Item(String ITEM_ID)
    {
        this.ITEM_ID = ITEM_ID;
    }


    //Getters and Setters
    public String getITEM_ID()
    {
        return ITEM_ID;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
    public BigDecimal getPrice()
    {
        return price;
    }

    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }
}
