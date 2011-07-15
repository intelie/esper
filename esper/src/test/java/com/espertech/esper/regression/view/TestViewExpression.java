package com.espertech.esper.regression.view;

import com.espertech.esper.client.*;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_A;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

public class TestViewExpression extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        listener = new SupportUpdateListener();
        Configuration configuration = SupportConfigFactory.getConfiguration();
        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();

        epService.getEPAdministrator().getConfiguration().addEventType(SupportBean.class);
    }

    public void testLengthWindow()
    {
        String[] fields = new String[] {"string"};
        EPStatement stmt = epService.getEPAdministrator().createEPL("select * from SupportBean.win:expr(current_count <= 2)");
        stmt.addListener(listener);
        
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E1"}});

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E1"}, {"E2"}});

        epService.getEPRuntime().sendEvent(new SupportBean("E3", 3));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E2"}, {"E3"}});

        stmt.destroy();
    }

    public void testTimeWindow()
    {
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(0));

        String[] fields = new String[] {"string"};
        EPStatement stmt = epService.getEPAdministrator().createEPL("select irstream * from SupportBean.win:expr(oldest_timestamp > newest_timestamp - 2000)");
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(1000));
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E1"}});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1"});

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(1500));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[]{"E2"});

        epService.getEPRuntime().sendEvent(new SupportBean("E3", 3));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][]{{"E1"}, {"E2"}, {"E3"}});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[]{"E3"});

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(2500));
        epService.getEPRuntime().sendEvent(new SupportBean("E4", 4));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E1"}, {"E2"}, {"E3"}, {"E4"}});

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(3000));
        epService.getEPRuntime().sendEvent(new SupportBean("E5", 5));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E2"}, {"E3"}, {"E4"}, {"E5"}});
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][]{{"E5"}});
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastOldData(), fields, new Object[][]{{"E1"}});
        listener.reset();

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(3499));
        epService.getEPRuntime().sendEvent(new SupportBean("E6", 6));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E2"}, {"E3"}, {"E4"}, {"E5"}, {"E6"}});

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(3500));
        epService.getEPRuntime().sendEvent(new SupportBean("E7", 7));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E4"}, {"E5"}, {"E6"}, {"E7"}});
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][]{{"E7"}});
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastOldData(), fields, new Object[][]{{"E2"}, {"E3"}});
        listener.reset();

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(10000));
        epService.getEPRuntime().sendEvent(new SupportBean("E8", 8));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E8"}});
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][]{{"E8"}});
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastOldData(), fields, new Object[][]{{"E4"}, {"E5"}, {"E6"}, {"E7"}});
        listener.reset();
    }

    public void testVariable()
    {
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(0));
        epService.getEPAdministrator().createEPL("create variable boolean KEEP = true");

        String[] fields = new String[] {"string"};
        EPStatement stmt = epService.getEPAdministrator().createEPL("select irstream * from SupportBean.win:expr(KEEP)");
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(1000));
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E1"}});

        epService.getEPRuntime().setVariableValue("KEEP", false);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E1"}});
        
        listener.reset();
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(1001));
        ArrayAssertionUtil.assertProps(listener.assertOneGetOldAndReset(), fields, new Object[]{"E1"});
        assertFalse(stmt.iterator().hasNext());

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        ArrayAssertionUtil.assertProps(listener.getLastNewData()[0], fields, new Object[]{"E2"});
        ArrayAssertionUtil.assertProps(listener.getLastOldData()[0], fields, new Object[]{"E2"});
        listener.reset();
        assertFalse(stmt.iterator().hasNext());

        epService.getEPRuntime().setVariableValue("KEEP", true);

        epService.getEPRuntime().sendEvent(new SupportBean("E3", 3));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[]{"E3"});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E3"}});

        stmt.stop();
    }

    public void testDynamicTimeWindow()
    {
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(0));
        epService.getEPAdministrator().createEPL("create variable long SIZE = 1000");

        String[] fields = new String[] {"string"};
        EPStatement stmt = epService.getEPAdministrator().createEPL("select irstream * from SupportBean.win:expr(newest_timestamp - oldest_timestamp < SIZE)");
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(1000));
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 0));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E1"}});

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(2000));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 0));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E2"}});

        epService.getEPRuntime().setVariableValue("SIZE", 10000);

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(5000));
        epService.getEPRuntime().sendEvent(new SupportBean("E3", 0));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][]{{"E2"}, {"E3"}});

        epService.getEPRuntime().setVariableValue("SIZE", 2000);

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(6000));
        epService.getEPRuntime().sendEvent(new SupportBean("E4", 0));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][]{{"E3"}, {"E4"}});
    }

    public void testUDFBuiltin()
    {
        epService.getEPAdministrator().getConfiguration().addPlugInSingleRowFunction("udf", LocalUDF.class.getName(), "evaluateExpiryUDF");
        epService.getEPAdministrator().createEPL("select * from SupportBean.win:expr(udf(string, view_reference, expired_count))");

        LocalUDF.setResult(true);
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 0));
        assertEquals("E1", LocalUDF.getKey());
        assertEquals(0, (int) LocalUDF.getExpiryCount());
        assertNotNull(LocalUDF.getViewref());

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 0));

        LocalUDF.setResult(false);
        epService.getEPRuntime().sendEvent(new SupportBean("E3", 0));
        assertEquals("E3", LocalUDF.getKey());
        assertEquals(2, (int) LocalUDF.getExpiryCount());
        assertNotNull(LocalUDF.getViewref());
    }

    public void testInvalid() {
        tryInvalid("select * from SupportBean.win:expr(1)",
                   "Error starting statement: Error attaching view to event stream: Invalid return value for expiry expression, expected a boolean return value but received Integer [select * from SupportBean.win:expr(1)]");

        tryInvalid("select * from SupportBean.win:expr((select * from SupportBean.std:lastevent()))",
                   "Error starting statement: Error attaching view to event stream: Invalid expiry expression: Aggregation, sub-select, previous or prior functions are not supported in this context [select * from SupportBean.win:expr((select * from SupportBean.std:lastevent()))]");
    }

    public void tryInvalid(String epl, String message) {
        try {
            epService.getEPAdministrator().createEPL(epl);
            fail();
        }
        catch (EPStatementException ex) {
            assertEquals(message, ex.getMessage());
        }
    }

    public void testNamedWindowDelete() {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean_A", SupportBean_A.class);
        
        String[] fields = new String[] {"string"};
        EPStatement stmt = epService.getEPAdministrator().createEPL("create window NW.win:expr(true) as SupportBean");
        stmt.addListener(listener);

        epService.getEPAdministrator().createEPL("insert into NW select * from SupportBean");
        
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        epService.getEPRuntime().sendEvent(new SupportBean("E3", 3));
        listener.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][]{{"E1"}, {"E2"}, {"E3"}});

        epService.getEPAdministrator().createEPL("on SupportBean_A delete from NW where string = id");
        epService.getEPRuntime().sendEvent(new SupportBean_A("E2"));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][]{{"E1"}, {"E3"}});
        ArrayAssertionUtil.assertProps(listener.assertOneGetOldAndReset(), fields, new Object[]{"E2"});
    }

    public void testPrev() {
        String[] fields = new String[] {"val0"};
        EPStatement stmt = epService.getEPAdministrator().createEPL("select prev(1, string) as val0 from SupportBean.win:expr(true)");
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[]{null});

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[]{"E1"});
    }

    public static class LocalUDF {

        private static String key;
        private static Integer expiryCount;
        private static Object viewref;
        private static boolean result;

        public static boolean evaluateExpiryUDF(String key, Object viewref, Integer expiryCount) {
            LocalUDF.key = key;
            LocalUDF.viewref = viewref;
            LocalUDF.expiryCount = expiryCount;
            return result;
        }

        public static String getKey() {
            return key;
        }

        public static Integer getExpiryCount() {
            return expiryCount;
        }

        public static Object getViewref() {
            return viewref;
        }

        public static void setResult(boolean result) {
            LocalUDF.result = result;
        }
    }
}
