package net.esper.regression.eql;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.client.EPServiceProviderManager;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.bean.SupportBean;

public class TestJoinCoercion extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testJoinCoercion()
    {
        String joinStatement = "select volume from " +
                SupportMarketDataBean.class.getName() + ".win:length(3) as s0," +
                SupportBean.class.getName() + "().win:length(3) as s1 " +
                " where s0.volume = s1.intPrimitive";

        EPStatement stmt = epService.getEPAdministrator().createEQL(joinStatement);
        stmt.addListener(listener);

        sendBeanEvent(100);
        sendMarketEvent(100);
        assertEquals(100L, listener.assertOneGetNewAndReset().get("volume"));
    }

    private void sendBeanEvent(int intPrimitive)
    {
        SupportBean bean = new SupportBean();
        bean.setIntPrimitive(intPrimitive);
        epService.getEPRuntime().sendEvent(bean);
    }

    private void sendMarketEvent(long volume)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean("", 0, volume, null);
        epService.getEPRuntime().sendEvent(bean);
    }
}
