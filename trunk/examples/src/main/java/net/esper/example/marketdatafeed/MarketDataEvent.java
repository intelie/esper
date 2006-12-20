package net.esper.example.marketdatafeed;

public class MarketDataEvent {

    private String symbol;
    private FeedEnum feed;
    private double bidPrice;
    private double askPrice;

    public MarketDataEvent(String symbol, FeedEnum feed) {
        this.symbol = symbol;
        this.feed = feed;
    }

    public MarketDataEvent(String symbol, FeedEnum feed, double bidPrice, double askPrice) {
        this.symbol = symbol;
        this.feed = feed;
        this.bidPrice = bidPrice;
        this.askPrice = askPrice;
    }

    public String getSymbol() {
        return symbol;
    }

    public FeedEnum getFeed() {
        return feed;
    }

    public double getBidPrice() {
        return bidPrice;
    }

    public double getAskPrice() {
        return askPrice;
    }
}
