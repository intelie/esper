package com.espertech.esper.support.bean.bookexample;

public class OrderItem
{
    private String itemId;
    private String productId;
    private int amount;
    private double price;

    public OrderItem(String itemId, String productId, int amount, double price)
    {
        this.itemId = itemId;
        this.amount = amount;
        this.productId = productId;
        this.price = price;
    }

    public String getItemId()
    {
        return itemId;
    }

    public int getAmount()
    {
        return amount;
    }

    public String getProductId()
    {
        return productId;
    }

    public double getPrice()
    {
        return price;
    }
}
