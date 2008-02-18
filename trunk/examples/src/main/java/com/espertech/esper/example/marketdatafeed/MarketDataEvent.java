package com.espertech.esper.example.marketdatafeed;

public class MarketDataEvent {

    private String symbol;
    private FeedEnum feed;

    public MarketDataEvent(String symbol, FeedEnum feed) {
        this.symbol = symbol;
        this.feed = feed;
    }

    public String getSymbol() {
        return symbol;
    }

    public FeedEnum getFeed() {
        return feed;
    }
}
