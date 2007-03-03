using System;

namespace net.esper.example.stockticker.eventbean
{
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

        public StockTick Tick
        {
            get { return tick; }
        }

        public PriceLimit Limit
        {
            get { return limit; }
        }

        public double InitialPrice
        {
            get { return initialPrice; }
        }

        public override String ToString()
        {
            return tick.ToString() +
                    "  " + limit.ToString() +
                    "  initialPrice=" + initialPrice;
        }
    }
}