package net.esper.regression.eql;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.client.SupportConfigFactory;

public class TestJoinNoWhereClause extends TestCase
{
    private EPServiceProvider epService;
    private EPStatement joinView;
    private SupportUpdateListener updateListener;

    private Object[] setOne = new Object[5];
    private Object[] setTwo = new Object[5];

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
        updateListener = new SupportUpdateListener();

        for (int i = 0; i < setOne.length; i++)
        {
            setOne[i] = new SupportMarketDataBean("IBM", 0, (long) i, "");

            SupportBean event = new SupportBean();
            event.setLongBoxed((long)i);
            setTwo[i] = event;
        }
    }

    public void testJoinNoWhereClause()
    {
        String joinStatement = "select * from " +
                SupportMarketDataBean.class.getName() + ".win:length(3)," +
                SupportBean.class.getName() + "().win:length(3)";

        joinView = epService.getEPAdministrator().createEQL(joinStatement);
        joinView.addListener(updateListener);

        // Send 2 events, should join on second one
        sendEvent(setOne[0]);
        sendEvent(setTwo[0]);
        assertEquals(1, updateListener.getLastNewData().length);
        assertEquals(setOne[0], updateListener.getLastNewData()[0].get("stream_0"));
        assertEquals(setTwo[0], updateListener.getLastNewData()[0].get("stream_1"));
        updateListener.reset();

        sendEvent(setOne[1]);
        sendEvent(setOne[2]);
        sendEvent(setTwo[1]);
        assertEquals(3, updateListener.getLastNewData().length);
    }

    private void sendEvent(Object event)
    {
        epService.getEPRuntime().sendEvent(event);
    }
}
