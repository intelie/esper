package com.espertech.esper.regression.view;

import junit.framework.TestCase;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.DoubleValueAssertionUtil;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.view.ViewFieldEnum;

import java.util.Iterator;

public class TestViewKeepAllWindow extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;
    private EPStatement statement;

    public void setUp()
    {
        listener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
    }

    public void testIterator()
    {
        String viewExpr = "select symbol, price from " + SupportMarketDataBean.class.getName() + ".win:keepall()";
        statement = epService.getEPAdministrator().createEQL(viewExpr);
        statement.addListener(listener);

        sendEvent("ABC", 20);
        sendEvent("DEF", 100);

        // check iterator results
        Iterator<EventBean> events = statement.iterator();
        EventBean event = events.next();
        assertEquals("ABC", event.get("symbol"));
        assertEquals(20d, event.get("price"));

        event = events.next();
        assertEquals("DEF", event.get("symbol"));
        assertEquals(100d, event.get("price"));
        assertFalse(events.hasNext());

        sendEvent("EFG", 50);

        // check iterator results
        events = statement.iterator();
        event = events.next();
        assertEquals("ABC", event.get("symbol"));
        assertEquals(20d, event.get("price"));

        event = events.next();
        assertEquals("DEF", event.get("symbol"));
        assertEquals(100d, event.get("price"));

        event = events.next();
        assertEquals("EFG", event.get("symbol"));
        assertEquals(50d, event.get("price"));
    }

    public void testWindowStats()
    {
        String viewExpr = "select irstream symbol, count(*) as cnt, sum(price) as mysum from " + SupportMarketDataBean.class.getName() +
                ".win:keepall() group by symbol";
        statement = epService.getEPAdministrator().createEQL(viewExpr);
        statement.addListener(listener);
        listener.reset();

        sendEvent("S1", 100);
        String[] fields = new String[] {"symbol", "cnt", "mysum"};
        ArrayAssertionUtil.assertProps(listener.getLastNewData()[0], fields, new Object[] {"S1", 1L, 100d});
        ArrayAssertionUtil.assertProps(listener.getLastOldData()[0], fields, new Object[] {"S1", 0L, null});
        listener.reset();

        sendEvent("S2", 50);
        ArrayAssertionUtil.assertProps(listener.getLastNewData()[0], fields, new Object[] {"S2", 1L, 50d});
        ArrayAssertionUtil.assertProps(listener.getLastOldData()[0], fields, new Object[] {"S2", 0L, null});
        listener.reset();

        sendEvent("S1", 5);
        ArrayAssertionUtil.assertProps(listener.getLastNewData()[0], fields, new Object[] {"S1", 2L, 105d});
        ArrayAssertionUtil.assertProps(listener.getLastOldData()[0], fields, new Object[] {"S1", 1L, 100d});
        listener.reset();

        sendEvent("S2", -1);
        ArrayAssertionUtil.assertProps(listener.getLastNewData()[0], fields, new Object[] {"S2", 2L, 49d});
        ArrayAssertionUtil.assertProps(listener.getLastOldData()[0], fields, new Object[] {"S2", 1L, 50d});
        listener.reset();
    }

    private void sendEvent(String symbol, double price)
    {
        SupportMarketDataBean event = new SupportMarketDataBean(symbol, price, 0L, "");
        epService.getEPRuntime().sendEvent(event);
    }
}
