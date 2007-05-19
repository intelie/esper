package net.esper.regression.db;

import junit.framework.TestCase;
import net.esper.client.*;
import net.esper.client.time.TimerControlEvent;
import net.esper.client.time.CurrentTimeEvent;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.eql.SupportDatabaseService;
import net.esper.support.bean.SupportBean_S0;
import net.esper.event.EventBean;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestDatabaseJoinPerformance extends TestCase
{
    private EPServiceProvider epServiceRetained;
    private EPServiceProvider epServicePooled;
    private SupportUpdateListener listener;

    public void setUp()
    {
        ConfigurationDBRef configDB = new ConfigurationDBRef();
        configDB.setDriverManagerConnection(SupportDatabaseService.DRIVER, SupportDatabaseService.FULLURL, new Properties());
        configDB.setConnectionLifecycleEnum(ConfigurationDBRef.ConnectionLifecycleEnum.RETAIN);
        Configuration configuration = new Configuration();
        configuration.addDatabaseReference("MyDB", configDB);

        epServiceRetained = EPServiceProviderManager.getProvider("TestDatabaseJoinRetained", configuration);
        epServiceRetained.initialize();
        epServiceRetained.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));

        configDB = new ConfigurationDBRef();
        configDB.setDriverManagerConnection(SupportDatabaseService.DRIVER, SupportDatabaseService.FULLURL, new Properties());
        configDB.setConnectionLifecycleEnum(ConfigurationDBRef.ConnectionLifecycleEnum.POOLED);
        configuration = new Configuration();
        configuration.addDatabaseReference("MyDB", configDB);
        epServicePooled = EPServiceProviderManager.getProvider("TestDatabaseJoinPooled", configuration);
        epServicePooled.initialize();
        epServicePooled.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
    }

    public void test100EventsRetained()
    {
        long startTime = System.currentTimeMillis();
        try100Events(epServiceRetained);
        long endTime = System.currentTimeMillis();
        log.info(".test100EventsRetained delta=" + (endTime - startTime));
        assertTrue(endTime - startTime < 5000);
    }

    public void test100EventsPooled()
    {
        long startTime = System.currentTimeMillis();
        try100Events(epServicePooled);
        long endTime = System.currentTimeMillis();
        log.info(".test100EventsPooled delta=" + (endTime - startTime));
        assertTrue(endTime - startTime < 10000);
    }

    public void testSelectRStream()
    {
        String stmtText = "select rstream myvarchar from " +
                SupportBean_S0.class.getName() + ".win:length(1000) as s0," +
                " sql:MyDB ['select myvarchar from mytesttable where ${id} = mytesttable.mybigint'] as s1";

        EPStatement statement = epServiceRetained.getEPAdministrator().createEQL(stmtText);
        listener = new SupportUpdateListener();
        statement.addListener(listener);

        // 1000 events should enter the window fast, no joins
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++)
        {
            SupportBean_S0 bean = new SupportBean_S0(10);
            epServiceRetained.getEPRuntime().sendEvent(bean);
            assertFalse(listener.isInvoked());
        }
        long endTime = System.currentTimeMillis();
        log.info(".testSelectRStream delta=" + (endTime - startTime));
        assertTrue(endTime - startTime < 500);

        // 1001st event should finally join and produce a result
        SupportBean_S0 bean = new SupportBean_S0(10);
        epServiceRetained.getEPRuntime().sendEvent(bean);
        assertEquals("J", listener.assertOneGetNewAndReset().get("myvarchar"));
    }

    public void testSelectIStream()
    {
        // set time to zero
        epServiceRetained.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
        epServiceRetained.getEPRuntime().sendEvent(new CurrentTimeEvent(0));

        String stmtText = "select istream myvarchar from " +
                SupportBean_S0.class.getName() + ".win:time(1 sec) as s0," +
                " sql:MyDB ['select myvarchar from mytesttable where ${id} = mytesttable.mybigint'] as s1";

        EPStatement statement = epServiceRetained.getEPAdministrator().createEQL(stmtText);
        listener = new SupportUpdateListener();
        statement.addListener(listener);

        // Send 100 events which all fireStatementStopped a join
        for (int i = 0; i < 100; i++)
        {
            SupportBean_S0 bean = new SupportBean_S0(5);
            epServiceRetained.getEPRuntime().sendEvent(bean);
            assertEquals("E", listener.assertOneGetNewAndReset().get("myvarchar"));
        }

        // now advance the time, this should not produce events or join
        long startTime = System.currentTimeMillis();
        epServiceRetained.getEPRuntime().sendEvent(new CurrentTimeEvent(2000));
        long endTime = System.currentTimeMillis();

        log.info(".testSelectIStream delta=" + (endTime - startTime));
        assertTrue(endTime - startTime < 200);
        assertFalse(listener.isInvoked());
    }

    private void try100Events(EPServiceProvider engine)
    {
        String stmtText = "select myint from " +
                SupportBean_S0.class.getName() + " as s0," +
                " sql:MyDB ['select myint from mytesttable where ${id} = mytesttable.mybigint'] as s1";

        EPStatement statement = engine.getEPAdministrator().createEQL(stmtText);
        listener = new SupportUpdateListener();
        statement.addListener(listener);

        for (int i = 0; i < 100; i++)
        {
            int id = i % 10 + 1;

            SupportBean_S0 bean = new SupportBean_S0(id);
            engine.getEPRuntime().sendEvent(bean);

            EventBean received = listener.assertOneGetNewAndReset();
            assertEquals(id * 10, received.get("myint"));
        }
    }

    private static final Log log = LogFactory.getLog(TestDatabaseJoinPerformance.class);
}
