package net.esper.support.bean;

import java.io.Serializable;

public class SupportMarketDataIDBean implements Serializable
{
    private String symbol;
    private String id;
    private double price;

    public SupportMarketDataIDBean(String symbol, String id, double price)
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

    public String getId()
    {
        return id;
    }
}
