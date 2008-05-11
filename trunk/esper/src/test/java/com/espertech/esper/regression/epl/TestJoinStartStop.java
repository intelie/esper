package com.espertech.esper.regression.epl;

import junit.framework.TestCase;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.client.SupportConfigFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestJoinStartStop extends TestCase
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

        String joinStatement = "select * from " +
                SupportMarketDataBean.class.getName() + "(symbol='IBM').win:length(3) s0, " +
                SupportMarketDataBean.class.getName() + "(symbol='CSCO').win:length(3) s1" +
            " where s0.volume=s1.volume";
        log.info(".setUp statement=" + joinStatement);

        joinView = epService.getEPAdministrator().createEPL(joinStatement);
        joinView.addListener(updateListener);

        long[] volumesOne = new long[] { 10, 20, 20, 40, 50 };
        long[] volumesTwo = new long[] { 10, 20, 30, 40, 50 };

        for (int i = 0; i < setOne.length; i++)
        {
            setOne[i] = new SupportMarketDataBean("IBM", volumesOne[i], (long) i, "");
            setTwo[i] = new SupportMarketDataBean("CSCO", volumesTwo[i], (long) i, "");
        }
    }

    public void testJoinUniquePerId()
    {
        sendEvent(setOne[0]);
        sendEvent(setTwo[0]);
        assertNotNull(updateListener.getLastNewData());
        updateListener.reset();

        joinView.stop();
        sendEvent(setOne[1]);
        sendEvent(setTwo[1]);
        assertFalse(updateListener.isInvoked());

        joinView.start();
        sendEvent(setOne[2]);
        assertFalse(updateListener.isInvoked());

        joinView.stop();
        sendEvent(setOne[3]);
        sendEvent(setOne[4]);
        sendEvent(setTwo[3]);

        joinView.start();
        sendEvent(setTwo[4]);
        assertFalse(updateListener.isInvoked());
    }

    private void sendEvent(Object event)
    {
        epService.getEPRuntime().sendEvent(event);
    }

    private static final Log log = LogFactory.getLog(TestJoinStartStop.class);
}
