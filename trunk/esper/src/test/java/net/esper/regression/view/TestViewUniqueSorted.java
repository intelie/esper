package net.esper.regression.view;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.event.EventBean;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * This test uses unique and sort views to obtain from a set of market data events the 3 currently most expensive stocks
 * and their symbols.
 * The unique view plays the role of filtering only the most recent events and making prior events for a symbol 'old'
 * data to the sort view, which removes these prior events for a symbol from the sorted window.
 */
public class TestViewUniqueSorted extends TestCase
{
    private static String SYMBOL_CSCO = "CSCO.O";
    private static String SYMBOL_IBM = "IBM.N";
    private static String SYMBOL_MSFT = "MSFT.O";
    private static String SYMBOL_C = "C.N";

    private EPServiceProvider epService;
    private SupportUpdateListener testListener;
    private EPStatement top3Prices;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();

        // Get the top 3 volumes for each symbol
        top3Prices = epService.getEPAdministrator().createEQL(
                "select * from " + SupportMarketDataBean.class.getName() +
                ".std:unique('symbol').ext:sort('price', true, 3)");
        top3Prices.addListener(testListener);
    }
    
    public void testWindowStats()
    {
        testListener.reset();

        Object beans[] = new Object[10];

        beans[0] = makeEvent(SYMBOL_CSCO, 50);
        epService.getEPRuntime().sendEvent(beans[0]);

        Object[] result = toObjectArray(top3Prices.iterator());
        ArrayAssertionUtil.assertEqualsExactOrder(result, new Object[] {beans[0]});
        assertTrue(testListener.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrder(testListener.getLastOldData(), null);
        ArrayAssertionUtil.assertEqualsExactOrder(new Object[] {testListener.getLastNewData()[0].getUnderlying()},
                new Object[] {beans[0]});
        testListener.reset();

        beans[1] = makeEvent(SYMBOL_CSCO, 20);
        beans[2] = makeEvent(SYMBOL_IBM, 50);
        beans[3] = makeEvent(SYMBOL_MSFT, 40);
        beans[4] = makeEvent(SYMBOL_C, 100);
        beans[5] = makeEvent(SYMBOL_IBM, 10);

        epService.getEPRuntime().sendEvent(beans[1]);
        epService.getEPRuntime().sendEvent(beans[2]);
        epService.getEPRuntime().sendEvent(beans[3]);
        epService.getEPRuntime().sendEvent(beans[4]);
        epService.getEPRuntime().sendEvent(beans[5]);

        result = toObjectArray(top3Prices.iterator());
        ArrayAssertionUtil.assertEqualsExactOrder(result, new Object[] { beans[4], beans[3], beans[5] });

        beans[6] = makeEvent(SYMBOL_CSCO, 110);
        beans[7] = makeEvent(SYMBOL_C, 30);
        beans[8] = makeEvent(SYMBOL_CSCO, 30);

        epService.getEPRuntime().sendEvent(beans[6]);
        epService.getEPRuntime().sendEvent(beans[7]);
        epService.getEPRuntime().sendEvent(beans[8]);

        result = toObjectArray(top3Prices.iterator());
        ArrayAssertionUtil.assertEqualsExactOrder(result, new Object[] { beans[3], beans[8], beans[7] });
    }

    private Object makeEvent(String symbol, double price)
    {
        SupportMarketDataBean event = new SupportMarketDataBean(symbol, price, 0L, "");
        return event;
    }

    private Object[] toObjectArray(Iterator<EventBean> it)
    {
        List<Object> result = new LinkedList<Object>();
        for (;it.hasNext();)
        {
            EventBean event = it.next();
            result.add(event.getUnderlying());
        }
        return result.toArray();
    }
}
