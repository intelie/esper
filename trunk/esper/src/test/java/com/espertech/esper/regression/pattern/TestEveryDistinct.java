package com.espertech.esper.regression.pattern;

import com.espertech.esper.client.*;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBeanConstants;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

public class TestEveryDistinct extends TestCase implements SupportBeanConstants
{
    // TODO: test simple
    //      every A distinct [2] (a)
    //      [4] A distinct [2] (a)
    //      every A distinct(a) where timer:within(5)
    //      every (A distinct(a) where timer:within(5))
    // TODO: test property expression: every A[book] distinct(title)
    // TODO: test match-until: [2], A distinct(id) until
    // TODO: test restart: "A distinct(id) "
    // TODO: test invalid: wrong property
    // TODO: test count of distinct "A distinct [2] (id)"
    // TODO: followed-by distinct
    // TODO: test invalid
    // TODO: test b=B -> A distinct (b.id + a.id)
    // TODO: this is an unsolved problem: how to get all distinct for 10 seconds, then restart.    

    public void testEvery() throws Exception
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportBean", SupportBean.class);
        EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider(config);
        engine.initialize();

        String expression = "select * from pattern [every-distinct(a.intPrimitive) a=SupportBean]";

        EPStatement statement = engine.getEPAdministrator().createEPL(expression);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        engine.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        assertEquals("E1", listener.assertOneGetNewAndReset().get("a.string"));

        engine.getEPRuntime().sendEvent(new SupportBean("E2", 1));
        assertFalse(listener.isInvoked());

        engine.getEPRuntime().sendEvent(new SupportBean("E3", 2));
        assertEquals("E3", listener.assertOneGetNewAndReset().get("a.string"));

        engine.getEPRuntime().sendEvent(new SupportBean("E4", 3));
        assertEquals("E4", listener.assertOneGetNewAndReset().get("a.string"));

        engine.getEPRuntime().sendEvent(new SupportBean("E5", 2));
        engine.getEPRuntime().sendEvent(new SupportBean("E6", 3));
        engine.getEPRuntime().sendEvent(new SupportBean("E7", 1));
        assertFalse(listener.isInvoked());

        engine.getEPRuntime().sendEvent(new SupportBean("E8", 0));
        assertEquals("E8", listener.assertOneGetNewAndReset().get("a.string"));
    }

    public void testRepeat() throws Exception
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportBean", SupportBean.class);
        EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider(config);
        engine.initialize();

        String expression = "select * from pattern [[2] every-distinct(a.intPrimitive) a=SupportBean]";

        EPStatement statement = engine.getEPAdministrator().createEPL(expression);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        engine.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        engine.getEPRuntime().sendEvent(new SupportBean("E2", 1));
        assertFalse(listener.isInvoked());

        engine.getEPRuntime().sendEvent(new SupportBean("E3", 2));
        EventBean event = listener.assertOneGetNewAndReset();
        assertEquals("E1", event.get("a[0].string"));
        assertEquals("E3", event.get("a[1].string"));

        engine.getEPRuntime().sendEvent(new SupportBean("E4", 3));
        engine.getEPRuntime().sendEvent(new SupportBean("E5", 2));
        assertFalse(listener.isInvoked());
    }

    public void testRepeat2() throws Exception
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportBean", SupportBean.class);
        EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider(config);
        engine.initialize();

        String expression = "select * from pattern [[2] every-distinct(a[0].intPrimitive) a=SupportBean]";

        EPStatement statement = engine.getEPAdministrator().createEPL(expression);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        engine.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        engine.getEPRuntime().sendEvent(new SupportBean("E2", 1));
        assertFalse(listener.isInvoked());

        engine.getEPRuntime().sendEvent(new SupportBean("E3", 2));
        EventBean event = listener.assertOneGetNewAndReset();
        assertEquals("E1", event.get("a[0].string"));
        assertEquals("E3", event.get("a[1].string"));

        engine.getEPRuntime().sendEvent(new SupportBean("E4", 3));
        engine.getEPRuntime().sendEvent(new SupportBean("E5", 2));
        assertFalse(listener.isInvoked());
    }

    public void testTimerWithin() throws Exception
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportBean", SupportBean.class);
        EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider(config);
        engine.initialize();

        String expression = "select * from pattern [(every a=SupportBean distinct (intPrimitive)) where timer:within(10 sec)]";

        sendTimer(0, engine);
        EPStatement statement = engine.getEPAdministrator().createEPL(expression);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        engine.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        assertEquals("E1", listener.assertOneGetNewAndReset().get("a.string"));

        engine.getEPRuntime().sendEvent(new SupportBean("E2", 1));
        assertFalse(listener.isInvoked());

        engine.getEPRuntime().sendEvent(new SupportBean("E3", 2));
        assertEquals("E3", listener.assertOneGetNewAndReset().get("a.string"));

        sendTimer(11000, engine);
        engine.getEPRuntime().sendEvent(new SupportBean("E4", 3));
        assertFalse(listener.isInvoked());

        // test precendence: new timer starts for every event
        statement.destroy();
        expression = "select * from pattern [every a=SupportBean distinct (intPrimitive) where timer:within(10 sec)]";
        sendTimer(20000, engine);
        statement = engine.getEPAdministrator().createEPL(expression);
        statement.addListener(listener);

        engine.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        assertEquals("E1", listener.assertOneGetNewAndReset().get("a.string"));

        engine.getEPRuntime().sendEvent(new SupportBean("E2", 1));
        assertEquals("E2", listener.assertOneGetNewAndReset().get("a.string"));
    }

    private void sendTimer(long timeInMSec, EPServiceProvider epService)
    {
        CurrentTimeEvent event = new CurrentTimeEvent(timeInMSec);
        EPRuntime runtime = epService.getEPRuntime();
        runtime.sendEvent(event);
    }
}
