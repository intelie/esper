package net.esper.regression.view;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.client.EPServiceProviderManager;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportBean;
import junit.framework.TestCase;

public class TestViewSimpleFilter extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider();
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
