using System;

namespace net.esper.example.stockticker.eventbean
{
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

        public String UserId
        {
            get { return userId; }
        }

        public String StockSymbol
        {
            get { return stockSymbol; }
        }

        public double LimitPct
        {
            get { return limitPct; }
        }

        public override String ToString()
        {
            return "userId=" + userId +
                   "  stockSymbol=" + stockSymbol +
                   "  limitPct=" + limitPct;
        }
    }
}