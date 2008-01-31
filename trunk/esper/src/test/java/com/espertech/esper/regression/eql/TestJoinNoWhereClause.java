package com.espertech.esper.regression.eql;

import junit.framework.TestCase;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.client.SupportConfigFactory;

public class TestJoinNoWhereClause extends TestCase
{
    private EPServiceProvider epService;
    private EPStatement joinView;
    private SupportUpdateListener updateListener;

    private Object[] setOne = new Object[5];
    private Object[] setTwo = new Object[5];

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setListenerDispatchPreserveOrder(false);
        config.getEngineDefaults().getViewResources().setShareViews(false);
        epService = EPServiceProviderManager.getDefaultProvider(config);
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
        String[] fields = new String[] {"stream_0.volume", "stream_1.longBoxed"};
        String joinStatement = "select * from " +
                SupportMarketDataBean.class.getName() + ".win:length(3)," +
                SupportBean.class.getName() + "().win:length(3)";

        joinView = epService.getEPAdministrator().createEQL(joinStatement);
        joinView.addListener(updateListener);

        // Send 2 events, should join on second one
        sendEvent(setOne[0]);
        ArrayAssertionUtil.assertEqualsAnyOrder(joinView.iterator(), fields, null);

        sendEvent(setTwo[0]);
        assertEquals(1, updateListener.getLastNewData().length);
        assertEquals(setOne[0], updateListener.getLastNewData()[0].get("stream_0"));
        assertEquals(setTwo[0], updateListener.getLastNewData()[0].get("stream_1"));
        updateListener.reset();
        ArrayAssertionUtil.assertEqualsAnyOrder(joinView.iterator(), fields,
                new Object[][] {{0L, 0L}});

        sendEvent(setOne[1]);
        sendEvent(setOne[2]);
        sendEvent(setTwo[1]);
        assertEquals(3, updateListener.getLastNewData().length);
        ArrayAssertionUtil.assertEqualsAnyOrder(joinView.iterator(), fields,
                new Object[][] {{0L, 0L},
                                {1L, 0L},
                                {2L, 0L},
                                {0L, 1L},
                                {1L, 1L},
                                {2L, 1L}});
    }

    private void sendEvent(Object event)
    {
        epService.getEPRuntime().sendEvent(event);
    }
}
