package com.espertech.esper.example.stockticker;

import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.example.stockticker.eventbean.PriceLimit;
import com.espertech.esper.example.stockticker.eventbean.StockTick;
import com.espertech.esper.example.stockticker.monitor.StockTickerMonitor;
import com.espertech.esper.example.stockticker.monitor.StockTickerResultListener;

import java.util.LinkedList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StockTickerMain
{
    private static final Log log = LogFactory.getLog(StockTickerMain.class);

    public static void main(String[] args)
    {
        new StockTickerMain().run("StockTicker");
    }

    public void run(String engineURI) {

        Configuration configuration = new Configuration();
        configuration.addEventType("PriceLimit", PriceLimit.class.getName());
        configuration.addEventType("StockTick", StockTick.class.getName());

        log.info("Setting up EPL");
        EPServiceProvider epService = EPServiceProviderManager.getProvider(engineURI, configuration);
        epService.initialize();
        new StockTickerMonitor(epService, new StockTickerResultListener());

        log.info("Generating test events: 1 million ticks, ratio 2 hits, 100 stocks");
        StockTickerEventGenerator generator = new StockTickerEventGenerator();
        LinkedList stream = generator.makeEventStream(1000000, 500000, 100, 25, 30, 48, 52, false);
        log.info("Generating " + stream.size() + " events");

        log.info("Sending " + stream.size() + " limit and tick events");
        for (Object event : stream)
        {
            epService.getEPRuntime().sendEvent(event);
        }

        log.info("Done.");
    }
}
