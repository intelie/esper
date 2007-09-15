package net.esper.multithread;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.Configuration;
import net.esper.support.bean.SupportTradeEvent;
import net.esper.multithread.TwoPatternRunnable;

/**
 * Test for multithread-safety for case of 2 patterns:
 *  1. Thread 1 starts pattern "every event1=SupportEvent(userID in ('100','101'), amount>=1000)"
 *  2. Thread 1 repeats sending 100 events and tests 5% received
 *  3. Main thread starts pattern:
 *      ( every event1=SupportEvent(userID in ('100','101')) ->
         (SupportEvent(userID in ('100','101'), direction = event1.direction ) ->
          SupportEvent(userID in ('100','101'), direction = event1.direction )
         ) where timer:within(8 hours)
         and not eventNC=SupportEvent(userID in ('100','101'), direction!= event1.direction )
        ) -> eventFinal=SupportEvent(userID in ('100','101'), direction != event1.direction ) where timer:within(1 hour)
 *   4. Main thread waits for 2 seconds and stops all threads
 */
public class TestMTStmtTwoPatternsStartStop extends TestCase
{
    private EPServiceProvider engine;

    public void setUp()
    {
        Configuration config = new Configuration();
        config.addEventTypeAlias("SupportEvent", SupportTradeEvent.class);
        
        engine = EPServiceProviderManager.getDefaultProvider(config);
        engine.initialize();
    }

    public void tearDown()
    {
        engine.initialize();
    }

    public void test2Patterns() throws Exception
    {
        String statementTwo = "( every event1=SupportEvent(userId in ('100','101')) ->\n" +
                "         (SupportEvent(userId in ('100','101'), direction = event1.direction ) ->\n" +
                "          SupportEvent(userId in ('100','101'), direction = event1.direction )\n" +
                "         ) where timer:within(8 hours)\n" +
                "         and not eventNC=SupportEvent(userId in ('100','101'), direction!= event1.direction )\n" +
                "        ) -> eventFinal=SupportEvent(userId in ('100','101'), direction != event1.direction ) where timer:within(1 hour)";

        TwoPatternRunnable runnable = new TwoPatternRunnable(engine);
        Thread t = new Thread(runnable);
        t.start();
        Thread.sleep(200);

        // Create a second pattern, wait 200 msec, destroy second pattern in a loop
        for (int i = 0; i < 10; i++)
        {
            System.out.println("Creating second statement");
            EPStatement statement = engine.getEPAdministrator().createPattern(statementTwo);
            Thread.sleep(200);
            statement.destroy();
            System.out.println("Destroying second statement");
        }

        runnable.setShutdown(true);
        Thread.sleep(1000);
        assertFalse(t.isAlive());
    }
}
