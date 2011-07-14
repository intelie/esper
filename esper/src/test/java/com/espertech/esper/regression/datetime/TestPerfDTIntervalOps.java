package com.espertech.esper.regression.datetime;

import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportTimeStartEndA;
import com.espertech.esper.support.bean.SupportTimeStartEndB;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

public class TestPerfDTIntervalOps extends TestCase {

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp() {

        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getLogging().setEnableQueryPlan(true);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testBeforeNoDurationStream() {

        ConfigurationEventTypeLegacy config = new ConfigurationEventTypeLegacy();
        config.setStartTimestampPropertyName("msecdateStart");
        config.setEndTimestampPropertyName("msecdateEnd");
        epService.getEPAdministrator().getConfiguration().addEventType("A", SupportTimeStartEndA.class.getName(), config);
        epService.getEPAdministrator().getConfiguration().addEventType("B", SupportTimeStartEndB.class.getName(), config);

        epService.getEPAdministrator().createEPL("create window AWindow.win:keepall() as A");
        epService.getEPAdministrator().createEPL("insert into AWindow select * from A");

        // preload
        System.out.println("Preloading");
        for (int i = 0; i < 10000; i++) {
            epService.getEPRuntime().sendEvent(SupportTimeStartEndA.make("A" + i, "2002-05-30T9:00:00.000", 0));
        }
        epService.getEPRuntime().sendEvent(SupportTimeStartEndA.make("AX", "2002-05-30T8:00:00.000", 0));
        System.out.println("Preloading done");

        // query with BEFORE
        String epl = "select a.key as c0 from AWindow as a, B b unidirectional where a.before(b)";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        // query
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            epService.getEPRuntime().sendEvent(SupportTimeStartEndB.make("B", "2002-05-30T9:00:00.000", 0));
            assertEquals("AX", listener.assertOneGetNewAndReset().get("c0"));
        }
        long endTime = System.currentTimeMillis();
        long delta = endTime - startTime;
        assertTrue("Delta=" + delta/1000d, delta < 500);
    }
}
