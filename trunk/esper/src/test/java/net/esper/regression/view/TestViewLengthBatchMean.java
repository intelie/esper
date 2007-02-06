package net.esper.regression.view;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.client.EPServiceProviderManager;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.util.DoubleValueAssertionUtil;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.event.EventBean;
import net.esper.view.ViewFieldEnum;

import java.util.Iterator;

public class TestViewLengthBatchMean extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;
    private SupportMarketDataBean events[];

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();

        EPStatement stmt = epService.getEPAdministrator().createEQL(
                "select * from " + SupportMarketDataBean.class.getName() + ".win:length_batch(2)");
        stmt.addListener(testListener);

        SupportMarketDataBean events[] = new SupportMarketDataBean[100];
        for (int i = 0; i < events.length; i++)
        {
            events[i] = new SupportMarketDataBean("", 0, 0l, "");
        }
    }

    public void testTimeBatchMean()
    {
        sendEvent(SYMBOL, 500);
    }

    private void sendEvent(SupportMarketDataBean event)
    {
        epService.getEPRuntime().sendEvent(event);
    }
}
