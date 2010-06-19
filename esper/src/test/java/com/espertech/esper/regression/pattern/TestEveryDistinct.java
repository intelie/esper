package com.espertech.esper.regression.pattern;

import com.espertech.esper.client.*;
import com.espertech.esper.client.soda.EPStatementObjectModel;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBeanConstants;
import com.espertech.esper.support.bean.SupportBean_A;
import com.espertech.esper.support.bean.SupportBean_B;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import junit.framework.TestCase;

public class TestEveryDistinct extends TestCase implements SupportBeanConstants
{
    public void testEveryDistinctOverFilter() throws Exception
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportBean", SupportBean.class);
        EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider(config);
        engine.initialize();

        String expression = "select * from pattern [every-distinct(intPrimitive) a=SupportBean]";

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

        EPStatementObjectModel model = engine.getEPAdministrator().compileEPL(expression);
        assertEquals(expression, model.toEPL());
        engine.getEPAdministrator().create(model);
    }

    public void testRepeatOverDistinct() throws Exception
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

    public void testEveryDistinctOverRepeat() throws Exception
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportBean", SupportBean.class);
        EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider(config);
        engine.initialize();

        String expression = "select * from pattern [every-distinct(a[0].intPrimitive) [2] a=SupportBean]";

        EPStatement statement = engine.getEPAdministrator().createEPL(expression);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        engine.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        engine.getEPRuntime().sendEvent(new SupportBean("E2", 1));
        EventBean event = listener.assertOneGetNewAndReset();
        assertEquals("E1", event.get("a[0].string"));
        assertEquals("E2", event.get("a[1].string"));

        engine.getEPRuntime().sendEvent(new SupportBean("E3", 1));
        engine.getEPRuntime().sendEvent(new SupportBean("E4", 2));
        assertFalse(listener.isInvoked());

        engine.getEPRuntime().sendEvent(new SupportBean("E5", 2));
        engine.getEPRuntime().sendEvent(new SupportBean("E6", 1));
        event = listener.assertOneGetNewAndReset();
        assertEquals("E5", event.get("a[0].string"));
        assertEquals("E6", event.get("a[1].string"));
    }

    public void testTimerWithinOverDistinct() throws Exception
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportBean", SupportBean.class);
        EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider(config);
        engine.initialize();

        // for 10 seconds, look for every distinct A
        String expression = "select * from pattern [(every-distinct(a.intPrimitive) a=SupportBean) where timer:within(10 sec)]";

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

        engine.getEPRuntime().sendEvent(new SupportBean("E5", 1));
        assertFalse(listener.isInvoked());
    }

    public void testEveryDistinctOverTimerWithin() throws Exception
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportBean", SupportBean.class);
        EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider(config);
        engine.initialize();

        String expression = "select * from pattern [every-distinct(a.intPrimitive) (a=SupportBean where timer:within(10 sec))]";
        sendTimer(0, engine);
        EPStatement statement = engine.getEPAdministrator().createEPL(expression);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        engine.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        assertEquals("E1", listener.assertOneGetNewAndReset().get("a.string"));

        engine.getEPRuntime().sendEvent(new SupportBean("E2", 1));
        assertFalse(listener.isInvoked());

        sendTimer(5000, engine);
        engine.getEPRuntime().sendEvent(new SupportBean("E3", 2));
        assertEquals("E3", listener.assertOneGetNewAndReset().get("a.string"));
        
        sendTimer(10000, engine);
        engine.getEPRuntime().sendEvent(new SupportBean("E4", 1));
        assertFalse(listener.isInvoked());

        engine.getEPRuntime().sendEvent(new SupportBean("E5", 1));
        assertFalse(listener.isInvoked());

        engine.getEPRuntime().sendEvent(new SupportBean("E6", 2));
        assertFalse(listener.isInvoked());

        sendTimer(15000, engine);
        engine.getEPRuntime().sendEvent(new SupportBean("E7", 2));
        assertFalse(listener.isInvoked());

        sendTimer(20000, engine);
        engine.getEPRuntime().sendEvent(new SupportBean("E8", 2));
        assertFalse(listener.isInvoked());

        sendTimer(25000, engine);
        engine.getEPRuntime().sendEvent(new SupportBean("E9", 1));
        assertFalse(listener.isInvoked());

        sendTimer(50000, engine);
        engine.getEPRuntime().sendEvent(new SupportBean("E10", 1));
        assertEquals("E10", listener.assertOneGetNewAndReset().get("a.string"));

        engine.getEPRuntime().sendEvent(new SupportBean("E11", 1));
        assertFalse(listener.isInvoked());

        engine.getEPRuntime().sendEvent(new SupportBean("E12", 2));
        assertEquals("E12", listener.assertOneGetNewAndReset().get("a.string"));

        engine.getEPRuntime().sendEvent(new SupportBean("E13", 2));
        assertFalse(listener.isInvoked());
    }

    public void testEveryDistinctOverAnd() throws Exception
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportBean", SupportBean.class);
        EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider(config);
        engine.initialize();

        String expression = "select * from pattern [every-distinct(a.intPrimitive, b.intPrimitive) (a=SupportBean(string like 'A%') and b=SupportBean(string like 'B%'))]";
        EPStatement statement = engine.getEPAdministrator().createEPL(expression);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        engine.getEPRuntime().sendEvent(new SupportBean("A1", 1));
        assertFalse(listener.isInvoked());
        engine.getEPRuntime().sendEvent(new SupportBean("B1", 10));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "a.string,b.string".split(","), new Object[] {"A1", "B1"});

        engine.getEPRuntime().sendEvent(new SupportBean("A2", 1));
        engine.getEPRuntime().sendEvent(new SupportBean("B2", 10));
        assertFalse(listener.isInvoked());

        engine.getEPRuntime().sendEvent(new SupportBean("A3", 2));
        engine.getEPRuntime().sendEvent(new SupportBean("B3", 10));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "a.string,b.string".split(","), new Object[] {"A3", "B3"});

        engine.getEPRuntime().sendEvent(new SupportBean("A4", 1));
        engine.getEPRuntime().sendEvent(new SupportBean("B4", 20));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "a.string,b.string".split(","), new Object[] {"A4", "B4"});

        engine.getEPRuntime().sendEvent(new SupportBean("A5", 2));
        engine.getEPRuntime().sendEvent(new SupportBean("B5", 10));
        assertFalse(listener.isInvoked());

        engine.getEPRuntime().sendEvent(new SupportBean("A6", 2));
        engine.getEPRuntime().sendEvent(new SupportBean("B6", 20));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "a.string,b.string".split(","), new Object[] {"A6", "B6"});

        engine.getEPRuntime().sendEvent(new SupportBean("A7", 2));
        engine.getEPRuntime().sendEvent(new SupportBean("B7", 20));
        assertFalse(listener.isInvoked());
    }

    public void testEveryDistinctOverOr() throws Exception
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportBean", SupportBean.class);
        EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider(config);
        engine.initialize();

        String expression = "select * from pattern [every-distinct(coalesce(a.intPrimitive, 0) + coalesce(b.intPrimitive, 0)) (a=SupportBean(string like 'A%') or b=SupportBean(string like 'B%'))]";
        EPStatement statement = engine.getEPAdministrator().createEPL(expression);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        engine.getEPRuntime().sendEvent(new SupportBean("A1", 1));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "a.string,b.string".split(","), new Object[] {"A1", null});

        engine.getEPRuntime().sendEvent(new SupportBean("B1", 2));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "a.string,b.string".split(","), new Object[] {null, "B1"});

        engine.getEPRuntime().sendEvent(new SupportBean("B2", 1));
        engine.getEPRuntime().sendEvent(new SupportBean("A2", 2));
        engine.getEPRuntime().sendEvent(new SupportBean("A3", 2));
        engine.getEPRuntime().sendEvent(new SupportBean("B3", 1));
        assertFalse(listener.isInvoked());

        engine.getEPRuntime().sendEvent(new SupportBean("B4", 3));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "a.string,b.string".split(","), new Object[] {null, "B4"});

        engine.getEPRuntime().sendEvent(new SupportBean("B5", 4));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "a.string,b.string".split(","), new Object[] {null, "B5"});

        engine.getEPRuntime().sendEvent(new SupportBean("B6", 3));
        engine.getEPRuntime().sendEvent(new SupportBean("A4", 3));
        engine.getEPRuntime().sendEvent(new SupportBean("A5", 4));
        assertFalse(listener.isInvoked());
    }

    public void testEveryDistinctOverNot() throws Exception
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportBean", SupportBean.class);
        EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider(config);
        engine.initialize();

        String expression = "select * from pattern [every-distinct(a.intPrimitive) (a=SupportBean(string like 'A%') and not SupportBean(string like 'B%'))]";
        EPStatement statement = engine.getEPAdministrator().createEPL(expression);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        engine.getEPRuntime().sendEvent(new SupportBean("A1", 1));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "a.string".split(","), new Object[] {"A1"});

        engine.getEPRuntime().sendEvent(new SupportBean("A2", 1));
        assertFalse(listener.isInvoked());

        engine.getEPRuntime().sendEvent(new SupportBean("A3", 2));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "a.string".split(","), new Object[] {"A3"});

        engine.getEPRuntime().sendEvent(new SupportBean("B1", 1));
        assertFalse(listener.isInvoked());

        engine.getEPRuntime().sendEvent(new SupportBean("A4", 1));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "a.string".split(","), new Object[] {"A4"});

        engine.getEPRuntime().sendEvent(new SupportBean("A5", 1));
        assertFalse(listener.isInvoked());
    }

    public void testEveryDistinctOverFollowedBy() throws Exception
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportBean", SupportBean.class);
        EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider(config);
        engine.initialize();

        String expression = "select * from pattern [every-distinct(a.intPrimitive + b.intPrimitive) (a=SupportBean(string like 'A%') -> b=SupportBean(string like 'B%'))]";
        EPStatement statement = engine.getEPAdministrator().createEPL(expression);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        engine.getEPRuntime().sendEvent(new SupportBean("A1", 1));
        assertFalse(listener.isInvoked());
        engine.getEPRuntime().sendEvent(new SupportBean("B1", 1));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "a.string,b.string".split(","), new Object[] {"A1", "B1"});

        engine.getEPRuntime().sendEvent(new SupportBean("A2", 1));
        engine.getEPRuntime().sendEvent(new SupportBean("B2", 1));
        assertFalse(listener.isInvoked());

        engine.getEPRuntime().sendEvent(new SupportBean("A3", 10));
        engine.getEPRuntime().sendEvent(new SupportBean("B3", -8));
        assertFalse(listener.isInvoked());

        engine.getEPRuntime().sendEvent(new SupportBean("A4", 2));
        engine.getEPRuntime().sendEvent(new SupportBean("B4", 1));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "a.string,b.string".split(","), new Object[] {"A4", "B4"});

        engine.getEPRuntime().sendEvent(new SupportBean("A5", 3));
        engine.getEPRuntime().sendEvent(new SupportBean("B5", 0));
        assertFalse(listener.isInvoked());
    }

    public void testEveryDistinctWithinFollowedBy() throws Exception
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportBean", SupportBean.class);
        EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider(config);
        engine.initialize();

        String expression = "select * from pattern [(every-distinct(a.intPrimitive) a=SupportBean(string like 'A%')) -> b=SupportBean(intPrimitive=a.intPrimitive)]";
        EPStatement statement = engine.getEPAdministrator().createEPL(expression);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        engine.getEPRuntime().sendEvent(new SupportBean("A1", 1));
        engine.getEPRuntime().sendEvent(new SupportBean("B1", 0));
        assertFalse(listener.isInvoked());
        engine.getEPRuntime().sendEvent(new SupportBean("B2", 1));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "a.string,b.string".split(","), new Object[] {"A1", "B2"});

        engine.getEPRuntime().sendEvent(new SupportBean("A2", 2));
        engine.getEPRuntime().sendEvent(new SupportBean("A3", 3));
        engine.getEPRuntime().sendEvent(new SupportBean("A4", 1));
        assertFalse(listener.isInvoked());

        engine.getEPRuntime().sendEvent(new SupportBean("B3", 3));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "a.string,b.string".split(","), new Object[] {"A3", "B3"});

        engine.getEPRuntime().sendEvent(new SupportBean("B4", 1));
        assertFalse(listener.isInvoked());

        engine.getEPRuntime().sendEvent(new SupportBean("B5", 2));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "a.string,b.string".split(","), new Object[] {"A2", "B5"});

        engine.getEPRuntime().sendEvent(new SupportBean("A5", 2));
        engine.getEPRuntime().sendEvent(new SupportBean("B6", 2));
        assertFalse(listener.isInvoked());

        engine.getEPRuntime().sendEvent(new SupportBean("A6", 4));
        engine.getEPRuntime().sendEvent(new SupportBean("B7", 4));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "a.string,b.string".split(","), new Object[] {"A6", "B7"});
    }

    public void testFollowedByWithDistinct() throws Exception
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportBean", SupportBean.class);
        EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider(config);
        engine.initialize();

        String expression = "select * from pattern [every-distinct(a.intPrimitive) a=SupportBean(string like 'A%') -> every-distinct(b.intPrimitive) b=SupportBean(string like 'B%')]";
        EPStatement statement = engine.getEPAdministrator().createEPL(expression);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        engine.getEPRuntime().sendEvent(new SupportBean("A1", 1));
        engine.getEPRuntime().sendEvent(new SupportBean("B1", 0));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "a.string,b.string".split(","), new Object[] {"A1", "B1"});
        engine.getEPRuntime().sendEvent(new SupportBean("B2", 1));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "a.string,b.string".split(","), new Object[] {"A1", "B2"});
        engine.getEPRuntime().sendEvent(new SupportBean("B3", 0));
        assertFalse(listener.isInvoked());

        engine.getEPRuntime().sendEvent(new SupportBean("A2", 1));
        engine.getEPRuntime().sendEvent(new SupportBean("B4", 2));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "a.string,b.string".split(","), new Object[] {"A1", "B4"});

        engine.getEPRuntime().sendEvent(new SupportBean("A3", 2));
        engine.getEPRuntime().sendEvent(new SupportBean("B5", 1));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "a.string,b.string".split(","), new Object[] {"A3", "B5"});
        engine.getEPRuntime().sendEvent(new SupportBean("B6", 1));
        assertFalse(listener.isInvoked());

        engine.getEPRuntime().sendEvent(new SupportBean("B7", 3));
        EventBean[] events = listener.getLastNewDataAndReset();
        ArrayAssertionUtil.assertProps(events[0], "a.string,b.string".split(","), new Object[] {"A1", "B7"});
        ArrayAssertionUtil.assertProps(events[1], "a.string,b.string".split(","), new Object[] {"A3", "B7"});
    }

    public void testInvalid() throws Exception
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("A", SupportBean_A.class);
        config.addEventType("B", SupportBean_B.class);
        EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider(config);
        engine.initialize();
        
        tryInvalid(engine, "a=A->every-distinct(a.intPrimitive) B",
                "Failed to resolve property 'a.intPrimitive' to a stream or nested property in a stream, every-distinct requires that all properties resolve from sub-expressions to the every-distinct [a=A->every-distinct(a.intPrimitive) B]");

        tryInvalid(engine, "every-distinct(dummy) A",
                "Property named 'dummy' is not valid in any stream, every-distinct requires that all properties resolve from sub-expressions to the every-distinct [every-distinct(dummy) A]");
    }

    public void tryInvalid(EPServiceProvider engine, String statement, String message) throws Exception
    {
        try
        {
            engine.getEPAdministrator().createPattern(statement);
            fail();
        }
        catch (EPStatementException ex)
        {
            assertEquals(message, ex.getMessage());
        }
    }

    private void sendTimer(long timeInMSec, EPServiceProvider epService)
    {
        CurrentTimeEvent event = new CurrentTimeEvent(timeInMSec);
        EPRuntime runtime = epService.getEPRuntime();
        runtime.sendEvent(event);
    }
}
