package net.esper.example.stockticker.eventbean;

public class StockTick
{
    private String stockSymbol;
    private double price;

    public StockTick(String stockSymbol, double price)
    {
        this.stockSymbol = stockSymbol;
        this.price = price;
    }

    public String getStockSymbol()
    {
        return stockSymbol;
    }

    public double getPrice()
    {
        return price;
    }

    public String toString()
    {
        return "stockSymbol=" + stockSymbol +
               "  price=" + price;
    }
}
