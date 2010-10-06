package com.espertech.esper.support.bean.bookexample;

public class Order
{
    private String orderId;
    private OrderItem[] items;

    public Order(String orderId, OrderItem[] items)
    {
        this.items = items;
        this.orderId = orderId;
    }

    public OrderItem[] getItems()
    {
        return items;
    }

    public String getOrderId()
    {
        return orderId;
    }
}
