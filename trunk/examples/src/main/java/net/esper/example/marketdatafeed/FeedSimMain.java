package net.esper.example.marketdatafeed;

import net.esper.client.Configuration;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FeedSimMain {

    private static final Log log = LogFactory.getLog(FeedSimMain.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length < 3) {
            System.out.println("Arguments are: <number of threads> <drop probability percent> <number of seconds to run>");
            System.out.println("  number of threads: the number of threads sending feed events into the engine");
            System.out.println("  drop probability percent: a number between zero and 100 that dictates the ");
            System.out.println("                            probability that per second one of the feeds drops off");
            System.out.println("  number of seconds: the number of seconds the simulation runs");
            System.exit(-1);
        }

        int numberOfThreads;
        try {
            numberOfThreads = Integer.parseInt(args[0]);
        } catch (NullPointerException e) {
            System.out.println("Invalid number of threads:" + args[0]);
            System.exit(-2);
            return;
        }

        double dropProbability;
        try {
            dropProbability = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid drop probability:" + args[1]);
            System.exit(-2);
            return;
        }

        int numberOfSeconds;
        try {
            numberOfSeconds = Integer.parseInt(args[2]);
        } catch (NullPointerException e) {
            System.out.println("Invalid number of seconds to run:" + args[2]);
            System.exit(-2);
            return;
        }

        // Run the sample
        System.out.println("Using " + numberOfThreads + " threads with a drop probability of " + dropProbability + "%, for " + numberOfSeconds + " seconds");
        FeedSimMain feedSimMain = new FeedSimMain(numberOfThreads, dropProbability, numberOfSeconds);
        feedSimMain.run();
    }

    private int numberOfThreads;
    private double dropProbability;
    private int numSeconds;

    public FeedSimMain(int numberOfThreads, double dropProbability, int numSeconds)
    {
        this.numberOfThreads = numberOfThreads;
        this.dropProbability = dropProbability;
        this.numSeconds = numSeconds;
    }

    public void run() throws IOException, InterruptedException
    {
        // Configure engine with event names to make the statements more readable.
        // This could also be done in a configuration file.
        Configuration configuration = new Configuration();
        configuration.addEventTypeAlias("MarketDataEvent", MarketDataEvent.class.getName());

        // Get engine instance
        EPServiceProvider epService = EPServiceProviderManager.getProvider("FeedSimMain", configuration);

        // Set up statements
        TicksPerSecondStatement tickPerSecStmt = new TicksPerSecondStatement(epService.getEPAdministrator());
        tickPerSecStmt.addListener(new RateReportingListener());

        TicksFalloffStatement falloffStmt = new TicksFalloffStatement(epService.getEPAdministrator());
        falloffStmt.addListener(new RateFalloffAlertListener());

        // Send events
        ExecutorService threadPool = Executors.newFixedThreadPool(numberOfThreads);
        MarketDataSendRunnable runnables[] = new MarketDataSendRunnable[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++)
        {
            runnables[i] = new MarketDataSendRunnable(epService);
            threadPool.submit(runnables[i]);
        }

        int seconds = 0;
        Random random = new Random();
        while(seconds < numSeconds) {
            seconds++;
            Thread.sleep(1000);

            FeedEnum feedToDropOff;
            if (random.nextDouble() * 100 < dropProbability)
            {
                feedToDropOff = FeedEnum.FEED_A;
                if (random.nextBoolean())
                {
                    feedToDropOff = FeedEnum.FEED_B;
                }
                log.info("Setting drop-off for feed " + feedToDropOff);
                
            }
            else
            {
                feedToDropOff = null;
            }
            for (int i = 0; i < runnables.length; i++)
            {
                runnables[i].setRateDropOffFeed(feedToDropOff);
            }
        }

        log.info("Shutting down threadpool");
        for (int i = 0; i < runnables.length; i++)
        {
            runnables[i].setShutdown();
        }
        threadPool.shutdown();
        threadPool.awaitTermination(10, TimeUnit.SECONDS);
    }
}
