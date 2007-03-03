using System;
using System.Collections.Generic;
using System.IO;
using System.Threading;

using net.esper.client;
using net.esper.compat;

namespace net.esper.example.marketdatafeed
{
    public class FeedSimMain
    {
        public static void Main(String[] args)
        {
            if (args.Length < 2)
            {
                Console.Out.WriteLine("Arguments are: <target targetRate> <targetRate drop probability percent>");
                Environment.Exit(-1);
            }

            int rate = 0;
            try
            {
                rate = Int32.Parse(args[0]);
            }
            catch (NullReferenceException)
            {
                Console.Out.WriteLine("Invalid targetRate:" + args[0]);
                Environment.Exit(-2);
                return;
            }

            double dropProbability = 0;
            try
            {
                dropProbability = Double.Parse(args[1]);
            }
            catch (FormatException)
            {
                Console.Out.WriteLine("Invalid drop probability:" + args[1]);
                Environment.Exit(-2);
                return;
            }

            // Run the sample
            Console.Out.WriteLine("Using a target targetRate of " + rate + " with drop probability " + dropProbability + "%, for 60 seconds");
            FeedSimMain feedSimMain = new FeedSimMain(rate, dropProbability, 60);
            feedSimMain.Run();
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

        public void Run()
        {
            // Configure engine with event names to make the statements more readable.
            // This could also be done in a configuration file.
            Configuration configuration = new Configuration();
            configuration.AddEventTypeAlias("MarketDataEvent", typeof(MarketDataEvent).Name);

            // Get engine instance
            EPServiceProvider epService = EPServiceProviderManager.GetProvider("FeedSimMain", configuration);

            // Set up statements
            TicksPerSecondStatement tickPerSecStmt = new TicksPerSecondStatement(epService.EPAdministrator);

            tickPerSecStmt.AddListener(new RateReportingListener().Update);

            TicksFalloffStatement falloffStmt = new TicksFalloffStatement(epService.EPAdministrator);
            falloffStmt.AddListener(new RateFalloffAlertListener().Update);

            // Send events
            int seconds = 0;
            while (seconds < numSeconds)
            {
                seconds++;
                SendEvents(epService.EPRuntime);
            }
        }

        private void SendEvents(EPRuntime epRuntime)
        {
            // Determine number of events to send
            int eventsFeedA = (int)(targetRate * 0.9 + targetRate * 0.2 * random.NextDouble());
            int eventsFeedB = (int)(targetRate * 0.9 + targetRate * 0.2 * random.NextDouble());

            if (random.NextDouble() * 100 < dropProbability)
            {
                if (random.Next(1) == 1)
                {
                    eventsFeedA = (int)(targetRate * 0.6);
                }
                else
                {
                    eventsFeedB = (int)(targetRate * 0.6);
                }
            }

            // send events
            long startTime = DateTimeHelper.CurrentTimeMillis;
            for (int i = 0; i < eventsFeedA; i++)
            {
                epRuntime.SendEvent(new MarketDataEvent("SYM", FeedEnum.FEED_A));
            }
            for (int i = 0; i < eventsFeedB; i++)
            {
                epRuntime.SendEvent(new MarketDataEvent("SYM", FeedEnum.FEED_B));
            }
            long endTime = DateTimeHelper.CurrentTimeMillis;

            // sleep remainder of 1 second
            long delta = startTime - endTime;
            if (delta < 950)
            {
                Thread.Sleep((int)(1000 - delta));
            }
        }
    }
}