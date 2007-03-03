using System;

namespace net.esper.example.stockticker.eventbean
{
    public class StockTick
    {
        private String stockSymbol;
        private double price;

        public StockTick(String stockSymbol, double price)
        {
            this.stockSymbol = stockSymbol;
            this.price = price;
        }

        public String StockSymbol
        {
            get { return stockSymbol; }
        }

        public double Price
        {
            get { return price; }
        }

        public override String ToString()
        {
            return "stockSymbol=" + stockSymbol +
                   "  price=" + price;
        }
    }
}