/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.example.feedexample;

public class FeedEvent
{
    private FeedEnum feed;
    private String symbol;
    private double price;

    public FeedEvent(FeedEnum feed, String symbol, double price)
    {
        this.feed = feed;
        this.symbol = symbol;
        this.price = price;
    }

    public FeedEnum getFeed()
    {
        return feed;
    }

    public String getSymbol()
    {
        return symbol;
    }

    public double getPrice()
    {
        return price;
    }
}
