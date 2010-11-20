package com.espertech.esper.regression.epl;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_S0;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.concurrent.locks.ReentrantLock;

public class TestSubselectNamedWindow extends TestCase {
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportBean", SupportBean.class);
        config.addEventType("S0", SupportBean_S0.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testFilteredCorrelatedLateStart()
    {
        epService.getEPAdministrator().createEPL("create window SupportWindow.win:keepall() as select * from SupportBean");
        epService.getEPAdministrator().createEPL("insert into SupportWindow select * from SupportBean");

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", -2));

        EPStatement stmt = epService.getEPAdministrator().createEPL("select (select intPrimitive from SupportWindow(intPrimitive<0) sw where s0.p00=sw.string) as val from S0 s0");
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_S0(10, "E1"));
        assertEquals(null, listener.assertOneGetNewAndReset().get("val"));

        epService.getEPRuntime().sendEvent(new SupportBean_S0(20, "E2"));
        assertEquals(-2, listener.assertOneGetNewAndReset().get("val"));

        epService.getEPRuntime().sendEvent(new SupportBean("E3", -3));
        epService.getEPRuntime().sendEvent(new SupportBean("E4", 4));

        epService.getEPRuntime().sendEvent(new SupportBean_S0(-3, "E3"));
        assertEquals(-3, listener.assertOneGetNewAndReset().get("val"));

        epService.getEPRuntime().sendEvent(new SupportBean_S0(20, "E4"));
        assertEquals(null, listener.assertOneGetNewAndReset().get("val"));
    }
}