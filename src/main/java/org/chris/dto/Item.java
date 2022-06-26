package org.chris.dto;

import java.math.BigDecimal;

public class Item {

    //Instance Variables
    private  String itemId;
    private String title;
    private BigDecimal price;
    private int quantity;

    //Constructors
    public Item(String itemId)
    {
        this.itemId = itemId;
    }


    //Getters and Setters
    public String getItemId()
    {
        return itemId;
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
