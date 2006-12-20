package net.esper.example.stockticker.eventbean;

public class LimitAlert
{
    private StockTick tick;
    private PriceLimit limit;
    double initialPrice;

    public LimitAlert(StockTick tick, PriceLimit limit, double initialPrice)
    {
        this.tick = tick;
        this.limit = limit;
        this.initialPrice = initialPrice;
    }

    public StockTick getTick()
    {
        return tick;
    }

    public PriceLimit getLimit()
    {
        return limit;
    }

    public double getInitialPrice()
    {
        return initialPrice;
    }

    public String toString()
    {
        return tick.toString() +
                "  " + limit.toString() +
                "  initialPrice=" + initialPrice;
    }

}
