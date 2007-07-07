package net.esper.regression.view;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.client.EPServiceProviderManager;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.bean.SupportBean;
import net.esper.support.client.SupportConfigFactory;
import net.esper.event.EventBean;

public class TestViewLengthBatch extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;
    private SupportBean events[];

    public void setUp()
    {
        listener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();

        events = new SupportBean[100];
        for (int i = 0; i < events.length; i++)
        {
            events[i] = new SupportBean();
        }
    }

    public void testLengthBatchSize2()
    {
        EPStatement stmt = epService.getEPAdministrator().createEQL(
                "select * from " + SupportBean.class.getName() + ".win:length_batch(2)");
        stmt.addListener(listener);

        sendEvent(events[0]);
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), new SupportBean[] {events[0] });

        sendEvent(events[1]);
        listener.assertUnderlyingAndReset(new SupportBean[] {events[0], events[1]}, null);
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), null);

        sendEvent(events[2]);
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), new SupportBean[] {events[2] });

        sendEvent(events[3]);
        listener.assertUnderlyingAndReset(new SupportBean[] {events[2], events[3]}, new SupportBean[] {events[0], events[1]});
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), null);

        sendEvent(events[4]);
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), new SupportBean[] {events[4] });

        sendEvent(events[5]);
        listener.assertUnderlyingAndReset(new SupportBean[] {events[4], events[5]}, new SupportBean[] {events[2], events[3]});
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), null);
    }

    public void testLengthBatchSize1()
    {
        EPStatement stmt = epService.getEPAdministrator().createEQL(
                "select * from " + SupportBean.class.getName() + ".win:length_batch(1)");
        stmt.addListener(listener);

        sendEvent(events[0]);
        listener.assertUnderlyingAndReset(new SupportBean[] {events[0]}, null);
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), null);

        sendEvent(events[1]);
        listener.assertUnderlyingAndReset(new SupportBean[] {events[1]}, new SupportBean[] {events[0]});
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), null);

        sendEvent(events[2]);
        listener.assertUnderlyingAndReset(new SupportBean[] {events[2]}, new SupportBean[] {events[1]});
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), null);
    }

    public void testLengthBatchSize3()
    {
        EPStatement stmt = epService.getEPAdministrator().createEQL(
                "select * from " + SupportBean.class.getName() + ".win:length_batch(3)");
        stmt.addListener(listener);

        sendEvent(events[0]);
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), new SupportBean[] {events[0] });

        sendEvent(events[1]);
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), new SupportBean[] {events[0], events[1]});

        sendEvent(events[2]);
        listener.assertUnderlyingAndReset(new SupportBean[] {events[0], events[1], events[2]}, null);
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), null);

        sendEvent(events[3]);
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), new SupportBean[] {events[3]});

        sendEvent(events[4]);
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), new SupportBean[] {events[3], events[4]});

        sendEvent(events[5]);
        listener.assertUnderlyingAndReset(new SupportBean[] {events[3], events[4], events[5]}, new SupportBean[] {events[0], events[1], events[2]});
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), null);
    }

    public void testLengthBatchSize3And2Staggered()
    {
        EPStatement stmt = epService.getEPAdministrator().createEQL(
                "select * from " + SupportBean.class.getName() + ".win:length_batch(3).win:length_batch(2)");
        stmt.addListener(listener);

        sendEvent(events[0]);
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), null);

        sendEvent(events[1]);
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), null);

        sendEvent(events[2]);
        listener.assertUnderlyingAndReset(new SupportBean[] {events[0], events[1], events[2]}, null);
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), null);

        sendEvent(events[3]);
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), null);

        sendEvent(events[4]);
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), null);

        sendEvent(events[5]);
        listener.assertUnderlyingAndReset(new SupportBean[] {events[3], events[4], events[5]}, new SupportBean[] {events[0], events[1], events[2]});
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), null);
    }

    public void testInvalid()
    {
        try
        {
            epService.getEPAdministrator().createEQL(
                "select * from " + SupportMarketDataBean.class.getName() + ".win:length_batch(0)");
            fail();
        }
        catch (Exception ex)
        {
            // expected
        }
    }

    private void sendEvent(SupportBean event)
    {
        epService.getEPRuntime().sendEvent(event);
    }
}
