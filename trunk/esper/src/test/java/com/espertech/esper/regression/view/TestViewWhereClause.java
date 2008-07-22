package com.espertech.esper.regression.view;

import junit.framework.TestCase;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBeanNumeric;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.event.EventBean;

import java.math.BigDecimal;
import java.math.BigInteger;

public class TestViewWhereClause extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;
    private EPStatement stmt;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();

        String viewExpr = "select * from " + SupportMarketDataBean.class.getName() + ".win:length(3) where symbol='CSCO'";
        stmt = epService.getEPAdministrator().createEPL(viewExpr);
        listener = new SupportUpdateListener();
        stmt.addListener(listener);
    }
    
    public void testWhere()
    {
        sendMarketDataEvent("IBM");
        assertFalse(listener.getAndClearIsInvoked());

        sendMarketDataEvent("CSCO");
        assertTrue(listener.getAndClearIsInvoked());

        sendMarketDataEvent("IBM");
        assertFalse(listener.getAndClearIsInvoked());

        sendMarketDataEvent("CSCO");
        assertTrue(listener.getAndClearIsInvoked());
    }

    public void testWhereNumericType()
    {
        String viewExpr = "select " +
                " intPrimitive + longPrimitive as p1," +
                " intPrimitive * doublePrimitive as p2," +
                " floatPrimitive / doublePrimitive as p3" +
                " from " + SupportBean.class.getName() + ".win:length(3) where " +
                "intPrimitive=longPrimitive and intPrimitive=doublePrimitive and floatPrimitive=doublePrimitive";

        stmt = epService.getEPAdministrator().createEPL(viewExpr);
        listener = new SupportUpdateListener();
        stmt.addListener(listener);

        sendSupportBeanEvent(1,2,3,4);
        assertFalse(listener.getAndClearIsInvoked());

        sendSupportBeanEvent(2, 2, 2, 2);
        EventBean event = listener.getAndResetLastNewData()[0];
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
