/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.multithread;

import junit.framework.TestCase;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.support.bean.SupportTradeEvent;
import com.espertech.esper.multithread.TwoPatternRunnable;

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
public class TestMTStmtTwoPatterns extends TestCase
{
    private EPServiceProvider engine;

    public void setUp()
    {
        Configuration config = new Configuration();
        config.addEventType("SupportEvent", SupportTradeEvent.class);
        
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
        Thread.sleep(100);

        // Create a second pattern, wait 500 msec, destroy second pattern in a loop
        for (int i = 0; i < 10; i++)
        {
            EPStatement statement = engine.getEPAdministrator().createPattern(statementTwo);
            Thread.sleep(200);
            statement.destroy();
        }

        runnable.setShutdown(true);
        Thread.sleep(1000);
        assertFalse(t.isAlive());
    }
}
