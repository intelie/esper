package net.esper.example.marketdatafeed;

import net.esper.client.Configuration;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPRuntime;
import java.io.IOException;
import java.util.Random;

public class FeedSimMain {

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length < 2) {
            System.out.println("Arguments are: <target targetRate> <targetRate drop probability percent>");
            System.exit(-1);
        }

        int rate = 0;
        try {
            rate = Integer.parseInt(args[0]);
        } catch (NullPointerException e) {
            System.out.println("Invalid targetRate:" + args[0]);
            System.exit(-2);
            return;
        }

        double dropProbability = 0;
        try {
            dropProbability = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid drop probability:" + args[1]);
            System.exit(-2);
            return;
        }

        // Run the sample
        System.out.println("Using a target targetRate of " + rate + " with drop probability " + dropProbability + "%, for 60 seconds");
        FeedSimMain feedSimMain = new FeedSimMain(rate, dropProbability, 60 );
        feedSimMain.run();
    }

    private Random random;
    private int targetRate;
    private double dropProbability;
    private int numSeconds;

    public FeedSimMain(int targetRate, double dropProbability, int numSeconds)
    {
        this.targetRate = targetRate;
        this.dropProbability = dropProbability;
        this.numSeconds = numSeconds;
        this.random = new Random();
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
        int seconds = 0;
        while(seconds < numSeconds) {
            seconds++;
            sendEvents(epService.getEPRuntime());
        }
    }

    private void sendEvents(EPRuntime epRuntime) throws InterruptedException
    {
        // Determine number of events to send
        int eventsFeedA = (int) (targetRate * 0.9 + targetRate * 0.2 * random.nextDouble());
        int eventsFeedB = (int) (targetRate * 0.9 + targetRate * 0.2 * random.nextDouble());

        if (random.nextDouble() * 100 < dropProbability)
        {
            if (random.nextBoolean())
            {
                eventsFeedA = (int) (targetRate * 0.6);
            }
            else
            {
                eventsFeedB = (int) (targetRate * 0.6);
            }
        }

        // send events
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < eventsFeedA; i++)
        {
            epRuntime.sendEvent(new MarketDataEvent("SYM", FeedEnum.FEED_A));
        }
        for (int i = 0; i < eventsFeedB; i++)
        {
            epRuntime.sendEvent(new MarketDataEvent("SYM", FeedEnum.FEED_B));
        }
        long endTime = System.currentTimeMillis();

        // sleep remainder of 1 second
        long delta = startTime - endTime;
        if (delta < 950)
        {
            Thread.sleep(1000 - delta);
        }
    }
}
