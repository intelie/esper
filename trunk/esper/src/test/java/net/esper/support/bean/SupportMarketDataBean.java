package net.esper.support.bean;

import java.io.Serializable;

public class SupportMarketDataBean implements Serializable
{
    private String symbol;
    private String id;
    private double price;
    private Long volume;
    private String feed;

    public SupportMarketDataBean(String symbol, double price, Long volume, String feed)
    {
        this.symbol = symbol;
        this.price = price;
        this.volume = volume;
        this.feed = feed;
    }

    public SupportMarketDataBean(String symbol, String id, double price)
    {
        this.symbol = symbol;
        this.id = id;
        this.price = price;
    }

    public String getSymbol()
    {
        return symbol;
    }

    public double getPrice()
    {
        return price;
    }

    public Long getVolume()
    {
        return volume;
    }

    public String getFeed()
    {
        return feed;
    }

    public String getId()
    {
        return id;
    }

    public String toString()
    {
        return "SupportMarketDataBean " +
               "symbol=" + symbol +
               " price=" + price +
               " volume=" + volume +
               " feed=" + feed;
    }
}
