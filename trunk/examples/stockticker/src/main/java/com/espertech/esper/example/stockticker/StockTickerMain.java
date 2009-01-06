package com.espertech.esper.example.stockticker;

import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProviderManager;

import java.util.LinkedList;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StockTickerMain
{
    private static final Log log = LogFactory.getLog(StockTickerMain.class);

    public static void main(String[] args)
    {
        EPRuntime runtime = EPServiceProviderManager.getDefaultProvider().getEPRuntime();

        StockTickerEventGenerator generator = new StockTickerEventGenerator();
        LinkedList stream = generator.makeEventStream(1000, 1, 100, 25, 30, 46, 54);

        log.info(".performTest Send limit and initial tick events");
        for (Object event : stream)
        {
            runtime.sendEvent(event);
        }
    }
}
