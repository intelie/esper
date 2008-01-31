package com.espertech.esper.regression.event;

import junit.framework.TestCase;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.bean.SupportBeanDynRoot;
import com.espertech.esper.support.bean.SupportBean_S0;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.client.SupportConfigFactory;

public class TestEventPropertyGetter extends TestCase
{
    private SupportUpdateListener listener;
    private EPServiceProvider epService;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testGetter() throws Exception
    {
        String stmtText = "select * from " + SupportMarketDataBean.class.getName();
        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        MyGetterUpdateListener listener = new MyGetterUpdateListener(stmt.getEventType());
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("sym", 100, 1000L, "feed"));
        assertEquals("sym", listener.getLastSymbol());
        assertEquals(1000L, (long) listener.getLastVolume());
        assertEquals(stmt, listener.getEpStatement());
        assertEquals(epService, listener.getEpServiceProvider());
    }
}
