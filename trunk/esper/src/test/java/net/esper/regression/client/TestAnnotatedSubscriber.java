package net.esper.regression.client;

import net.esper.client.*;
import net.esper.support.client.SupportConfigFactory;
import net.esper.support.bean.SupportBean;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.support.util.SupportUpdateListener;
import net.esper.event.EventBean;
import junit.framework.TestCase;

import java.util.List;
import java.util.ArrayList;

public class TestAnnotatedSubscriber extends TestCase
{
    // TODO: dispatch view always attached, hasChild not reliable
    // TODO: test iterator
    // TODO: test listener + method delivery
    // TODO: test join
    // TODO: test select istream/rstream selector

    // TODO: support named parameter names, support rstream delivery through alt method
    // TODO: test synthetic delivery
    // TODO: test wildcard, stream wildcard, wildcard with extra columns
    // TODO: test multirow delivery
    // TODO: test array delivery
    // TODO: test bean array delivery
    // TODO: test outside statement start/stop + statement name
    // TODO: test insert into
    // TODO: test named windows
    // TODO: test performance
    // TODO: test non-selecting views: on-delete, create window/variable
    // TODO: test on-select view
    // TODO: test widening, test inheritance and polymorphism
    // TODO: test null value delivery
    // TODO: test ordered delivery and MT thread safety

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

    public void testPerformanceNatural()
    {
        MyAnnotatedSubscriber subscriber = new MyAnnotatedSubscriber();
        epService.getEPRuntime().write(subscriber);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++)
        {
            epService.getEPRuntime().sendEvent(new SupportBean("E1", 100));
        }
        long end = System.currentTimeMillis();

        assertEquals(100000, subscriber.getAndReset().size());
        System.out.println("delta=" + (end - start));
    }

    public void testPerformanceSynthetic()
    {
        EPStatement stmt = epService.getEPAdministrator().createEQL("select string, intPrimitive from SupportBean(intPrimitive > 10)");
        final List<Object[]> fields = new ArrayList<Object[]>();

        UpdateListener listener = new UpdateListener() {

            public void update(EventBean[] newEvents, EventBean[] oldEvents)
            {
                String string = (String) newEvents[0].get("string");
                int val = (Integer) newEvents[0].get("intPrimitive");
                fields.add(new Object[] {string, val});
            }
        };
        
        stmt.addListener(listener);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++)
        {
            epService.getEPRuntime().sendEvent(new SupportBean("E1", 100));
        }
        long end = System.currentTimeMillis();

        assertEquals(100000, fields.size());
        System.out.println("delta=" + (end - start));
    }

    public void testSimpleSubscribe()
    {
        String fields[] = "string,intPrimitive".split(",");

        MyAnnotatedSubscriber subscriber = new MyAnnotatedSubscriber();
        epService.getEPRuntime().write(subscriber);

        // get statement, attach listener
        String names[] = epService.getEPAdministrator().getStatementNames();
        EPStatement stmt = epService.getEPAdministrator().getStatement(names[0]);
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        // send event
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 100));
        ArrayAssertionUtil.assertPropsPerRow(subscriber.getAndReset(), new Object[][] {{"E1", 100}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E1", 100}});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1", 100});

        // remove listener
        stmt.removeAllListeners();

        // send event
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 200));
        ArrayAssertionUtil.assertPropsPerRow(subscriber.getAndReset(), new Object[][] {{"E2", 200}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E2", 200}});
        assertFalse(listener.isInvoked());

        // remove
        epService.getEPRuntime().take(subscriber);

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 1));
        assertEquals(0, subscriber.getAndReset().size());
    }
}
