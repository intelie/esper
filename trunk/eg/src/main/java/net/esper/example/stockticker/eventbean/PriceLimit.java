package net.esper.example.stockticker.eventbean;

public class PriceLimit
{
    String userId;
    String stockSymbol;
    double limitPct;

    public PriceLimit(String userId, String stockSymbol, double limitPct)
    {
        this.userId = userId;
        this.stockSymbol = stockSymbol;
        this.limitPct = limitPct;
    }

    public String getUserId()
    {
        return userId;
    }

    public String getStockSymbol()
    {
        return stockSymbol;
    }

    public double getLimitPct()
    {
        return limitPct;
    }

    public String toString()
    {
        return "userId=" + userId +
               "  stockSymbol=" + stockSymbol +
               "  limitPct=" + limitPct;
    }
}
