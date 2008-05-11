package com.espertech.esper.example.marketdatafeed;

import com.espertech.esper.client.EPServiceProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Random;
import java.util.concurrent.Callable;

public class MarketDataSendRunnable implements Runnable
{
    private static final Log log = LogFactory.getLog(MarketDataSendRunnable.class);
    private final EPServiceProvider engine;

    private volatile FeedEnum rateDropOffFeed;
    private volatile boolean isShutdown;
    private Random random = new Random();

    public MarketDataSendRunnable(EPServiceProvider engine)
    {
        this.engine = engine;
    }

    public void run()
    {
        log.info(".call Thread " + Thread.currentThread() + " starting");

        try
        {
            while(!isShutdown)
            {
                int nextFeed = Math.abs(random.nextInt() % 2);
                FeedEnum feed = FeedEnum.values()[nextFeed];
                if (rateDropOffFeed != feed)
                {
                    engine.getEPRuntime().sendEvent(new MarketDataEvent("SYM", feed));
                }
            }
        }
        catch (RuntimeException ex)
        {
            log.error("Error in send loop", ex);
        }

        log.info(".call Thread " + Thread.currentThread() + " done");
    }

    public void setRateDropOffFeed(FeedEnum feedToDrop)
    {
        rateDropOffFeed = feedToDrop;
    }

    public void setShutdown()
    {
        isShutdown = true;
    }
}
