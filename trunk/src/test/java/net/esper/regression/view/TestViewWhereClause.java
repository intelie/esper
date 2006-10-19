package net.esper.regression.view;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.bean.SupportBean;
import net.esper.support.util.SupportUpdateListener;
import net.esper.event.EventBean;

public class TestViewWhereClause extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;
    private EPStatement testView;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();

        String viewExpr = "select * from " + SupportMarketDataBean.class.getName() + ".win:length(3) where symbol='CSCO'";
        testView = epService.getEPAdministrator().createEQL(viewExpr);
        testListener = new SupportUpdateListener();
        testView.addListener(testListener);
    }
    
    public void testWhere()
    {
        sendMarketDataEvent("IBM");
        assertFalse(testListener.getAndClearIsInvoked());

        sendMarketDataEvent("CSCO");
        assertTrue(testListener.getAndClearIsInvoked());

        sendMarketDataEvent("IBM");
        assertFalse(testListener.getAndClearIsInvoked());

        sendMarketDataEvent("CSCO");
        assertTrue(testListener.getAndClearIsInvoked());
    }

    public void testWhereNumericType()
    {
        String viewExpr = "select " +
                " intPrimitive + longPrimitive as p1," +
                " intPrimitive * doublePrimitive as p2," +
                " floatPrimitive / doublePrimitive as p3" +
                " from " + SupportBean.class.getName() + ".win:length(3) where " +
                "intPrimitive=longPrimitive and intPrimitive=doublePrimitive and floatPrimitive=doublePrimitive";

        testView = epService.getEPAdministrator().createEQL(viewExpr);
        testListener = new SupportUpdateListener();
        testView.addListener(testListener);

        sendSupportBeanEvent(1,2,3,4);
        assertFalse(testListener.isInvoked());

        sendSupportBeanEvent(2, 2, 2, 2);
        EventBean event = testListener.getAndResetLastNewData()[0];
        assertEquals(Long.class, event.getEventType().getPropertyType("p1"));
        assertEquals(4l, event.get("p1"));
        assertEquals(Double.class, event.getEventType().getPropertyType("p2"));
        assertEquals(4d, event.get("p2"));
        assertEquals(Double.class, event.getEventType().getPropertyType("p3"));
        assertEquals(1d, event.get("p3"));
    }

    private void sendMarketDataEvent(String symbol)
    {
        SupportMarketDataBean event = new SupportMarketDataBean(symbol, 0, 0L, "");
        epService.getEPRuntime().sendEvent(event);
    }

    private void sendSupportBeanEvent(int intPrimitive, long longPrimitive, float floatPrimitive, double doublePrimitive)
    {
        SupportBean event = new SupportBean();
        event.setIntPrimitive(intPrimitive);
        event.setLongPrimitive(longPrimitive);
        event.setFloatPrimitive(floatPrimitive);
        event.setDoublePrimitive(doublePrimitive);
        epService.getEPRuntime().sendEvent(event);
    }
}
