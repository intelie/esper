package com.espertech.esper.regression.view;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import junit.framework.TestCase;

public class TestViewSimpleFilter extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
    }

    public void testNotEqualsOp()
    {
        EPStatement statement = epService.getEPAdministrator().createEQL(
                "select * from " + SupportBean.class.getName() +
                "(string != 'a')");
        statement.addListener(testListener);

        sendEvent("a");
        assertFalse(testListener.isInvoked());

        Object event = sendEvent("b");
        assertSame(event, testListener.getAndResetLastNewData()[0].getUnderlying());

        sendEvent("a");
        assertFalse(testListener.isInvoked());

        event = sendEvent(null);
        assertFalse(testListener.isInvoked());
    }

    public void testCombinationEqualsOp()
    {
        EPStatement statement = epService.getEPAdministrator().createEQL(
                "select * from " + SupportBean.class.getName() +
                "(string != 'a', intPrimitive=0)");
        statement.addListener(testListener);

        sendEvent("b", 1);
        assertFalse(testListener.isInvoked());

        sendEvent("a", 0);
        assertFalse(testListener.isInvoked());

        Object event = sendEvent("x", 0);
        assertSame(event, testListener.getAndResetLastNewData()[0].getUnderlying());

        event = sendEvent(null, 0);
        assertFalse(testListener.isInvoked());
    }

    private Object sendEvent(String stringValue)
    {
        return sendEvent(stringValue, -1);
    }

    private Object sendEvent(String stringValue, int intPrimitive)
    {
        SupportBean event = new SupportBean();
        event.setString(stringValue);
        event.setIntPrimitive(intPrimitive);
        epService.getEPRuntime().sendEvent(event);
        return event;
    }
}
