package net.esper.regression.eql;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.client.SupportConfigFactory;

public class TestJoinNoTableName extends TestCase
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
                SupportMarketDataBean.class.getName() + ".win:length(3)," +
                SupportBean.class.getName() + ".win:length(3)" +
            " where symbol=string and volume=longBoxed";

        joinView = epService.getEPAdministrator().createEQL(joinStatement);
        joinView.addListener(updateListener);

        for (int i = 0; i < setOne.length; i++)
        {
            setOne[i] = new SupportMarketDataBean("IBM", 0, (long) i, "");

            SupportBean event = new SupportBean();
            event.setString("IBM");
            event.setLongBoxed((long)i);
            setTwo[i] = event;
        }
    }

    public void testJoinUniquePerId()
    {
        sendEvent(setOne[0]);
        sendEvent(setTwo[0]);
        assertNotNull(updateListener.getLastNewData());
    }

    private void sendEvent(Object event)
    {
        epService.getEPRuntime().sendEvent(event);
    }
}