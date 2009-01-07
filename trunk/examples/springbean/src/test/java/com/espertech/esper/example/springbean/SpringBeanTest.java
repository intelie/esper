package com.espertech.esper.example.springbean;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.time.CurrentTimeEvent;
import junit.framework.TestCase;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Test case for OHLC buckets from a tick stream.
 */
public class SpringBeanTest extends TestCase
{
    private EPServiceProvider epService;

    public void setUp()
    {
        Configuration config = new Configuration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);   // external timer for testing
        config.addEventTypeAlias("OHLCTick", OHLCTick.class);
        config.addPlugInView("examples", "ohlcbarminute", OHLCBarPlugInViewFactory.class.getName());

        epService = EPServiceProviderManager.getDefaultProvider(config);

        // set time as an arbitrary start time
        sendTimer(toTime("9:01:50"));

        Object[][] statements = new Object[][] {
        {"S1",    "select * from OHLCTick.std:groupby(ticker).examples:ohlcbarminute(timestamp, price)"},
        };

        for (Object[] statement : statements)
        {
            String stmtName = (String) statement[0];
            String expression = (String) statement[1];
            System.out.println("Creating statement: " + expression);
            EPStatement stmt = epService.getEPAdministrator().createEPL(expression, stmtName);

            if (stmtName.equals("S1"))
            {
                OHLCUpdateListener listener = new OHLCUpdateListener();
                stmt.addListener(listener);
            }
        }
    }

    public void testScenarioOneNormal() throws Exception
    {
        Object[][] input = new Object[][] {
                {"9:01:51", null},  // lets start simulating at 9:01:51
                {"9:01:52", "IBM", 100.5, "9:01:52"},  // lets have an event arrive on time
                {"9:02:03", "IBM", 100.0, "9:02:03"},
                {"9:02:10", "IBM",  99.0, "9:02:04"},  // lets have an event arrive later; this timer event also triggers a bucket
                {"9:02:20", "IBM",  98.0, "9:02:16"},
                {"9:02:30", "NOC",  11.0, "9:02:30"},
                {"9:02:45", "NOC",  12.0, "9:02:45"},
                {"9:02:55", "NOC",  13.0, "9:02:55"},
                {"9:03:02", "IBM", 101.0, "9:02:58"},   // this event arrives late but counts in the same bucket
                {"9:03:06", "IBM", 109.0, "9:02:59"},   // this event arrives too late: it should be ignored (5 second cutoff time, see view)
                {"9:03:07", "IBM", 103.0, "9:03:00"},   // this event should count for the next bucket
                {"9:03:55", "NOC",  12.5, "9:03:55"},
                {"9:03:58", "NOC",  12.75, "9:03:58"},
                {"9:04:00", "IBM", 104.0, "9:03:59"},
                {"9:04:02", "IBM", 105.0, "9:04:00"},   // next bucket starts with this event
                {"9:04:07", null},   // should complete next bucket even though there is no event arriving
                {"9:04:30", null},   // pretend no events
                {"9:04:59", null},
                {"9:05:00", null},
                {"9:05:10", null},
                {"9:05:15", "IBM", 105.5, "9:05:13"},
                {"9:05:59", null},
                {"9:06:07", null},
        };

        for (int i = 0; i < input.length; i++)
        {
            String timestampArrival = (String) input[i][0];
            System.out.println("Sending timer event " + timestampArrival);
            sendTimer(toTime(timestampArrival));

            String ticker = (String) input[i][1];
            if (ticker != null)
            {
                double price = ((Number) input[i][2]).doubleValue();
                String timestampTick = (String) input[i][3];
                OHLCTick event = new OHLCTick(ticker, price, toTime(timestampTick));

                System.out.println("Sending event " + event);
                epService.getEPRuntime().sendEvent(event);
            }
        }
    }

    private void sendTimer(long timestamp)
    {
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(timestamp));
    }

    private long toTime(String time)
    {
        String[] fields = time.split(":");
        int hour = Integer.parseInt(fields[0]);
        int min = Integer.parseInt(fields[1]);
        int sec = Integer.parseInt(fields[2]);
        Calendar cal = GregorianCalendar.getInstance();
        cal.set(2008, 1, 1, hour, min, sec);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }
}
