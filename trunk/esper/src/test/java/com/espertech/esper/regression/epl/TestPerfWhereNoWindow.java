package com.espertech.esper.regression.epl;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.support.bean.SupportMarketDataIDBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import junit.framework.TestCase;

public class TestPerfWhereNoWindow extends TestCase
{
    private EPServiceProvider epService;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventTypeAlias("MD", SupportMarketDataIDBean.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
    }

    // Compares the performance of
    //     select * from MD(symbol = 'xyz')
    //  against
    //     select * from MD where symbol = 'xyz'
    public void testPerfNoDelivery()
    {
        for (int i = 0; i < 1000; i++)
        {
            String text = "select * from MD where symbol = '" + Integer.toString(i) + "'";
            epService.getEPAdministrator().createEPL(text);
        }

        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++)
        {
            SupportMarketDataIDBean bean = new SupportMarketDataIDBean("NOMATCH", "", 1);
            epService.getEPRuntime().sendEvent(bean);
        }
        long end = System.currentTimeMillis();
        long delta = end - start;
        assertTrue("Delta=" + delta, delta < 500);
    }
}
