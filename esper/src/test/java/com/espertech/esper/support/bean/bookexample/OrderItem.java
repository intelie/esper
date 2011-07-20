/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

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
