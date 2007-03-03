using System;

namespace net.esper.example.marketdatafeed
{
    public class MarketDataEvent
    {
        private string symbol;
        private FeedEnum feed;
        private double bidPrice;
        private double askPrice;

        public MarketDataEvent(String symbol, FeedEnum feed)
        {
            this.symbol = symbol;
            this.feed = feed;
        }

        public MarketDataEvent(String symbol, FeedEnum feed, double bidPrice, double askPrice)
        {
            this.symbol = symbol;
            this.feed = feed;
            this.bidPrice = bidPrice;
            this.askPrice = askPrice;
        }

        public string Symbol
        {
            get { return symbol; }
        }

        public FeedEnum Feed
        {
            get { return feed; }
        }

        public double BidPrice
        {
            get { return bidPrice; }
        }

        public double AskPrice
        {
            get { return askPrice; }
        }
    }
}