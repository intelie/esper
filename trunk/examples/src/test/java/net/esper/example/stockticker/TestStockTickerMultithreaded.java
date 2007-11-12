package net.esper.example.stockticker;

import java.util.LinkedList;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.LinkedBlockingQueue;

import junit.framework.TestCase;
import net.esper.example.stockticker.monitor.StockTickerEmittedListener;
import net.esper.example.stockticker.monitor.StockTickerMonitor;
import net.esper.example.stockticker.eventbean.PriceLimit;
import net.esper.example.stockticker.eventbean.StockTick;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.Configuration;
import net.esper.support.SendEventRunnable;
import net.esper.support.EPRuntimeUtil;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class TestStockTickerMultithreaded extends TestCase implements StockTickerRegressionConstants
{
    StockTickerEmittedListener listener;
    private EPServiceProvider epService;

    protected void setUp() throws Exception
    {
        listener = new StockTickerEmittedListener();

        Configuration configuration = new Configuration();
        configuration.addEventTypeAlias("PriceLimit", PriceLimit.class.getName());
        configuration.addEventTypeAlias("StockTick", StockTick.class.getName());

        epService = EPServiceProviderManager.getProvider("TestStockTickerMultithreaded", configuration);
        epService.initialize();
        epService.getEPRuntime().addEmittedListener(listener, null);

        new StockTickerMonitor(epService);
    }

    public void testMultithreaded()
    {
        //performTest(3, 1000000, 100000, 60);  // on fast systems
        performTest(3, 50000, 10000, 15);   // for unit tests on slow machines
    }

    public void performTest(int numberOfThreads,
                            int numberOfTicksToSend,
                            int ratioPriceOutOfLimit,
                            int numberOfSecondsWaitForCompletion)
    {
        final int totalNumTicks = numberOfTicksToSend + 2 * TestStockTickerGenerator.NUM_STOCK_NAMES;

        log.info(".performTest Generating data, numberOfTicksToSend=" + numberOfTicksToSend +
                 "  ratioPriceOutOfLimit=" + ratioPriceOutOfLimit);

        StockTickerEventGenerator generator = new StockTickerEventGenerator();
        LinkedList stream = generator.makeEventStream(numberOfTicksToSend, ratioPriceOutOfLimit, TestStockTickerGenerator.NUM_STOCK_NAMES);

        log.info(".performTest Send limit and initial tick events - singlethreaded");
        for (int i = 0; i < TestStockTickerGenerator.NUM_STOCK_NAMES * 2; i++)
        {
            Object event = stream.removeFirst();
            epService.getEPRuntime().sendEvent(event);
        }

        log.info(".performTest Loading thread pool work queue, numberOfRunnables=" + stream.size());

        ThreadPoolExecutor pool = new ThreadPoolExecutor(0, numberOfThreads, 99999, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        for (Object event : stream)
        {
            SendEventRunnable runnable = new SendEventRunnable(epService, event);
            pool.execute(runnable);
        }

        log.info(".performTest Starting thread pool, threads=" + numberOfThreads);
        pool.setCorePoolSize(numberOfThreads);

        log.info(".performTest Listening for completion");
        EPRuntimeUtil.awaitCompletion(epService.getEPRuntime(), totalNumTicks, numberOfSecondsWaitForCompletion, 1, 10);

        pool.shutdown();
        
        // Check results : make sure the given ratio of out-of-limit stock prices was reported
        int expectedNumEmitted = (numberOfTicksToSend / ratioPriceOutOfLimit) + 1;
        assertTrue(listener.getSize() == expectedNumEmitted);

        log.info(".performTest Done test");
    }

    private static final Log log = LogFactory.getLog(TestStockTickerMultithreaded.class);
}
