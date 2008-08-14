package com.espertech.esperio.opentick;

public class OpentickSubscriberSpec
{
    private final String exchange;
    private final String symbol;
    private final Integer optionalType;

    public OpentickSubscriberSpec(String exchange, String symbol, Integer optionalType)
    {
        this.exchange = exchange;
        this.symbol = symbol;
        this.optionalType = optionalType;
    }

    public String getExchange()
    {
        return exchange;
    }

    public String getSymbol()
    {
        return symbol;
    }

    public Integer getOptionalType()
    {
        return optionalType;
    }
}
