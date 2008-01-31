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
