package net.esper.support.bean;

import java.io.Serializable;

public class SupportMarketDataBean implements Serializable
{
    private String symbol;
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

    public String toString()
    {
        return "SupportMarketDataBean " +
               "symbol=" + symbol +
               " price=" + price +
               " volume=" + volume +
               " feed=" + feed;
    }
}
