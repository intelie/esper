package com.espertech.esper.regression.datetime;

import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportDateTime;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

public class TestPerfDTIntervalOps extends TestCase {

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp() {

        Configuration config = SupportConfigFactory.getConfiguration();
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testPerformance() {
        ConfigurationEventTypeLegacy config = new ConfigurationEventTypeLegacy();
        config.setTimestampProperty("msecdate");
        epService.getEPAdministrator().getConfiguration().addEventType("SupportDateTime", SupportDateTime.class.getName(), config);

        String epl =
                "select a.key as c0 " +
                "from SupportDateTime(key like 'A%').win:keepall() as a, " +
                "     SupportDateTime(key like 'B%').std:lastevent() as b " +
                "where a.before(b)";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        // preload
        System.out.println("Preloading");
        for (int i = 0; i < 10000; i++) {
            epService.getEPRuntime().sendEvent(SupportDateTime.make("A" + i, "2002-05-30T9:00:00.000"));
        }
        epService.getEPRuntime().sendEvent(SupportDateTime.make("AX", "2002-05-30T8:00:00.000"));
        System.out.println("Preloading done");

        // query
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            epService.getEPRuntime().sendEvent(SupportDateTime.make("B", "2002-05-30T9:00:00.000"));
            assertEquals("AX", listener.assertOneGetNewAndReset().get("c0"));
        }
        long endTime = System.currentTimeMillis();
        long delta = endTime - startTime;
        assertTrue("Delta=" + delta/1000d, delta < 500);
    }
}
