package net.esper.regression.client;

import junit.framework.TestCase;
import net.esper.client.*;
import net.esper.event.EventBean;
import net.esper.support.bean.SupportBean;
import net.esper.support.client.SupportConfigFactory;
import net.esper.support.util.ArrayAssertionUtil;

import java.util.ArrayList;
import java.util.List;

public class TestAnnotatedSubscriberPerf extends TestCase
{
    private EPServiceProvider epService;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        String pkg = SupportBean.class.getPackage().getName();
        config.addEventTypeAutoAlias(pkg);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
    }

    public void testPerformanceSyntheticUndelivered()
    {
        final int NUM_LOOP = 100000;
        epService.getEPAdministrator().createEQL("select string, intPrimitive from SupportBean(intPrimitive > 10)");

        long start = System.currentTimeMillis();
        for (int i = 0; i < NUM_LOOP; i++)
        {
            epService.getEPRuntime().sendEvent(new SupportBean("E1", 1000 + i));
        }
        long end = System.currentTimeMillis();
        System.out.println("delta=" + (end - start));
    }

    public void testPerformanceNatural()
    {
        final int NUM_LOOP = 10;
        MyAnnotatedSimpleSubscriber subscriber = new MyAnnotatedSimpleSubscriber();

        long start = System.currentTimeMillis();
        for (int i = 0; i < NUM_LOOP; i++)
        {
            epService.getEPRuntime().sendEvent(new SupportBean("E1", 1000 + i));
        }
        long end = System.currentTimeMillis();

        List<Object[]> results = subscriber.getAndResetIndicateSimple();
        assertEquals(NUM_LOOP, results.size());
        for (int i = 0; i < NUM_LOOP; i++)
        {
            ArrayAssertionUtil.assertEqualsAnyOrder(results.get(i), new Object[] {"E1", 1000 + i});
        }
        System.out.println("delta=" + (end - start));
    }

    public void testPerformanceSynthetic()
    {
        final int NUM_LOOP = 100000;
        EPStatement stmt = epService.getEPAdministrator().createEQL("select string, intPrimitive from SupportBean(intPrimitive > 10)");
        final List<Object[]> results = new ArrayList<Object[]>();

        UpdateListener listener = new UpdateListener() {
            public void update(EventBean[] newEvents, EventBean[] oldEvents)
            {
                String string = (String) newEvents[0].get("string");
                int val = (Integer) newEvents[0].get("intPrimitive");
                results.add(new Object[] {string, val});
            }
        };
        stmt.addListener(listener);

        long start = System.currentTimeMillis();
        for (int i = 0; i < NUM_LOOP; i++)
        {
            epService.getEPRuntime().sendEvent(new SupportBean("E1", 1000 + i));
        }
        long end = System.currentTimeMillis();

        assertEquals(NUM_LOOP, results.size());
        for (int i = 0; i < NUM_LOOP; i++)
        {
            ArrayAssertionUtil.assertEqualsAnyOrder(results.get(i), new Object[] {"E1", 1000 + i});
        }
        System.out.println("delta=" + (end - start));
    }
}
