package com.espertech.esper.regression.enummethod;

import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_ST0;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.Collection;

public class TestEnumNamedWindowPerformance extends TestCase {

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp() {

        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportBean", SupportBean.class);
        config.addEventType("SupportBean_ST0", SupportBean_ST0.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testNamedWindowQualified() {

        epService.getEPAdministrator().createEPL("create window Win.win:keepall() as SupportBean");
        epService.getEPAdministrator().createEPL("insert into Win select * from SupportBean");

        // preload
        for (int i = 0; i < 10000; i++) {
            epService.getEPRuntime().sendEvent(new SupportBean("K" + i % 100, i));
        }
        
        runAssertiomReuse();

        runAssertiomSubquery();
    }

    private void runAssertiomSubquery() {

        // test expression reuse
        String epl =    "expression q {" +
                        "  x => (select * from Win where intPrimitive = x.p00)" +
                        "}" +
                        "select " +
                        "q(st0).where(x => string = key0) as val0, " +
                        "q(st0).where(x => string = key0) as val1, " +
                        "q(st0).where(x => string = key0) as val2, " +
                        "q(st0).where(x => string = key0) as val3, " +
                        "q(st0).where(x => string = key0) as val4, " +
                        "q(st0).where(x => string = key0) as val5, " +
                        "q(st0).where(x => string = key0) as val6, " +
                        "q(st0).where(x => string = key0) as val7, " +
                        "q(st0).where(x => string = key0) as val8, " +
                        "q(st0).where(x => string = key0) as val9 " +
                        "from SupportBean_ST0 st0";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 5000; i++) {
            epService.getEPRuntime().sendEvent(new SupportBean_ST0("ID", "K50", 1050));
            EventBean event = listener.assertOneGetNewAndReset();
            for (int j = 0; j < 10; j++) {
                Collection coll = (Collection) event.get("val" + j);
                assertEquals(1, coll.size());
                SupportBean bean = (SupportBean) coll.iterator().next();
                assertEquals("K50", bean.getString());
                assertEquals(1050, bean.getIntPrimitive());
            }
        }
        long delta = System.currentTimeMillis() - start;
        assertTrue("Delta = " + delta, delta < 1000);

        stmt.destroy();
    }

    private void runAssertiomReuse() {

        // test expression reuse
        String epl =    "expression q {" +
                        "  x => Win(string = x.key0).where(y => intPrimitive = x.p00)" +
                        "}" +
                        "select " +
                        "q(st0) as val0, " +
                        "q(st0) as val1, " +
                        "q(st0) as val2, " +
                        "q(st0) as val3, " +
                        "q(st0) as val4, " +
                        "q(st0) as val5, " +
                        "q(st0) as val6, " +
                        "q(st0) as val7, " +
                        "q(st0) as val8, " +
                        "q(st0) as val9 " +
                "from SupportBean_ST0 st0";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 5000; i++) {
            epService.getEPRuntime().sendEvent(new SupportBean_ST0("ID", "K50", 1050));
            EventBean event = listener.assertOneGetNewAndReset();
            for (int j = 0; j < 10; j++) {
                Collection coll = (Collection) event.get("val" + j);
                assertEquals(1, coll.size());
                SupportBean bean = (SupportBean) coll.iterator().next();
                assertEquals("K50", bean.getString());
                assertEquals(1050, bean.getIntPrimitive());
            }
        }
        long delta = System.currentTimeMillis() - start;
        assertTrue("Delta = " + delta, delta < 1000);

        // This will create a single dispatch
        // epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));

        stmt.destroy();
    }
}
