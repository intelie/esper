package com.espertech.esper.regression.epl;

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_A;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.bean.SupportBean_S0;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;

public class TestNamedWindowSubquery extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listenerWindow;
    private SupportUpdateListener listenerStmtOne;
    private SupportUpdateListener listenerStmtTwo;
    private SupportUpdateListener listenerStmtThree;
    private SupportUpdateListener listenerStmtDelete;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listenerWindow = new SupportUpdateListener();
        listenerStmtOne = new SupportUpdateListener();
        listenerStmtTwo = new SupportUpdateListener();
        listenerStmtThree = new SupportUpdateListener();
        listenerStmtDelete = new SupportUpdateListener();
    }

    public void testSubquery()
    {
        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select string as a, longPrimitive as b, longBoxed as c from " + SupportBean.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string as a, longPrimitive as b, longBoxed as c from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEPL(stmtTextInsertOne);

        // create consumer
        String stmtTextSelectOne = "select irstream (select a from MyWindow) as value, symbol from " + SupportMarketDataBean.class.getName();
        EPStatement stmtSelectOne = epService.getEPAdministrator().createEPL(stmtTextSelectOne);
        stmtSelectOne.addListener(listenerStmtOne);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmtSelectOne.getEventType().getPropertyNames(), new String[] {"value", "symbol"});
        assertEquals(String.class, stmtSelectOne.getEventType().getPropertyType("value"));
        assertEquals(String.class, stmtSelectOne.getEventType().getPropertyType("symbol"));

        sendMarketBean("M1");
        String fieldsStmt[] = new String[] {"value", "symbol"};
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fieldsStmt, new Object[] {null, "M1"});

        sendSupportBean("S1", 1L, 2L);
        assertFalse(listenerStmtOne.isInvoked());
        String fieldsWin[] = new String[] {"a", "b", "c"};
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fieldsWin, new Object[] {"S1", 1L, 2L});

        // create consumer 2 -- note that this one should not start empty now
        String stmtTextSelectTwo = "select irstream (select a from MyWindow) as value, symbol from " + SupportMarketDataBean.class.getName();
        EPStatement stmtSelectTwo = epService.getEPAdministrator().createEPL(stmtTextSelectTwo);
        stmtSelectTwo.addListener(listenerStmtTwo);

        sendMarketBean("M1");
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fieldsStmt, new Object[] {"S1", "M1"});
        ArrayAssertionUtil.assertProps(listenerStmtTwo.assertOneGetNewAndReset(), fieldsStmt, new Object[] {"S1", "M1"});

        sendSupportBean("S2", 10L, 20L);
        assertFalse(listenerStmtOne.isInvoked());
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fieldsWin, new Object[] {"S2", 10L, 20L});

        sendMarketBean("M2");
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fieldsStmt, new Object[] {null, "M2"});
        assertFalse(listenerWindow.isInvoked());
        ArrayAssertionUtil.assertProps(listenerStmtTwo.assertOneGetNewAndReset(), fieldsStmt, new Object[] {null, "M2"});

        // create delete stmt
        String stmtTextDelete = "on " + SupportBean_A.class.getName() + " delete from MyWindow where id = a";
        EPStatement stmtDelete = epService.getEPAdministrator().createEPL(stmtTextDelete);
        stmtDelete.addListener(listenerStmtDelete);

        // delete S1
        epService.getEPRuntime().sendEvent(new SupportBean_A("S1"));
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fieldsWin, new Object[] {"S1", 1L, 2L});

        sendMarketBean("M3");
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fieldsStmt, new Object[] {"S2", "M3"});
        ArrayAssertionUtil.assertProps(listenerStmtTwo.assertOneGetNewAndReset(), fieldsStmt, new Object[] {"S2", "M3"});

        // delete S2
        epService.getEPRuntime().sendEvent(new SupportBean_A("S2"));
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fieldsWin, new Object[] {"S2", 10L, 20L});

        sendMarketBean("M4");
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fieldsStmt, new Object[] {null, "M4"});
        ArrayAssertionUtil.assertProps(listenerStmtTwo.assertOneGetNewAndReset(), fieldsStmt, new Object[] {null, "M4"});

        sendSupportBean("S3", 100L, 200L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fieldsWin, new Object[] {"S3", 100L, 200L});

        sendMarketBean("M5");
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fieldsStmt, new Object[] {"S3", "M5"});
        ArrayAssertionUtil.assertProps(listenerStmtTwo.assertOneGetNewAndReset(), fieldsStmt, new Object[] {"S3", "M5"});
        epService.getEPAdministrator().destroyAllStatements();
    }

    public void testSubquerySelfCheck()
    {
        String fields[] = new String[] {"key", "value"};

        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select string as key, intBoxed as value from " + SupportBean.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        // create insert into (not does insert if key already exists)
        String stmtTextInsertOne = "insert into MyWindow select string as key, intBoxed as value from " + SupportBean.class.getName() + " as s0" +
                                    " where not exists (select * from MyWindow as win where win.key = s0.string)";
        epService.getEPAdministrator().createEPL(stmtTextInsertOne);

        sendSupportBean("E1", 1);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1}});

        sendSupportBean("E2", 2);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E2", 2});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1}, {"E2", 2}});

        sendSupportBean("E1", 3);
        assertFalse(listenerWindow.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1}, {"E2", 2}});

        sendSupportBean("E3", 4);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E3", 4});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1}, {"E2", 2}, {"E3", 4}});

        // Add delete
        String stmtTextDelete = "on " + SupportBean_A.class.getName() + " delete from MyWindow where key = id";
        EPStatement stmtDelete = epService.getEPAdministrator().createEPL(stmtTextDelete);
        stmtDelete.addListener(listenerStmtDelete);

        // delete E2
        epService.getEPRuntime().sendEvent(new SupportBean_A("E2"));
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E2", 2});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1}, {"E3", 4}});

        sendSupportBean("E2", 5);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E2", 5});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1}, {"E3", 4}, {"E2", 5}});
    }

    public void testCorrelatedSubquerySelect() {

        // ESPER-452
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().getConfiguration().addEventType("ABean", SupportBean_S0.class);
        epService.getEPAdministrator().createEPL("create window MyWindow.std:unique(string) as select * from SupportBean");
        epService.getEPAdministrator().createEPL("insert into MyWindow select * from SupportBean");
        EPStatement stmt = epService.getEPAdministrator().createEPL("select status.*, (select * from MyWindow where string = ABean.p00) as details" +
                    " from ABean(p01='good') as status");
        stmt.addListener(listenerStmtOne);
        
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, "E1", "good"));
        assertTrue(listenerStmtOne.isInvoked());
    }

    public void testSubqueryDeleteInsertReplace()
    {
        String fields[] = new String[] {"key", "value"};

        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select string as key, intBoxed as value from " + SupportBean.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        // delete
        String stmtTextDelete = "on " + SupportBean.class.getName() + " delete from MyWindow where key = string";
        EPStatement stmtDelete = epService.getEPAdministrator().createEPL(stmtTextDelete);
        stmtDelete.addListener(listenerStmtDelete);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string as key, intBoxed as value from " + SupportBean.class.getName() + " as s0";
        epService.getEPAdministrator().createEPL(stmtTextInsertOne);

        sendSupportBean("E1", 1);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1}});

        sendSupportBean("E2", 2);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E2", 2});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1}, {"E2", 2}});

        sendSupportBean("E1", 3);
        assertEquals(2, listenerWindow.getNewDataList().size());
        ArrayAssertionUtil.assertProps(listenerWindow.getOldDataList().get(0)[0], fields, new Object[] {"E1", 1});
        ArrayAssertionUtil.assertProps(listenerWindow.getNewDataList().get(1)[0], fields, new Object[] {"E1", 3});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E2", 2}, {"E1", 3}});
    }

    public void testInvalidSubquery()
    {
        epService.getEPAdministrator().createEPL("create window MyWindow.win:keepall() as " + SupportBean.class.getName());
        try
        {
            epService.getEPAdministrator().createEPL("select (select string from MyWindow.std:lastevent()) from MyWindow");
            fail();
        }
        catch (EPException ex)
        {
            assertEquals("Error starting statement: Consuming statements to a named window cannot declare a data window view onto the named window [select (select string from MyWindow.std:lastevent()) from MyWindow]", ex.getMessage());
        }
    }

    public void testSubqueryAggregation()
    {
        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select string as a, longPrimitive as b from " + SupportBean.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string as a, longPrimitive as b from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEPL(stmtTextInsertOne);

        // create consumer
        String stmtTextSelectOne = "select irstream (select sum(b) from MyWindow) as value, symbol from " + SupportMarketDataBean.class.getName();
        EPStatement stmtSelectOne = epService.getEPAdministrator().createEPL(stmtTextSelectOne);
        stmtSelectOne.addListener(listenerStmtOne);

        sendMarketBean("M1");
        String fieldsStmt[] = new String[] {"value", "symbol"};
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fieldsStmt, new Object[] {null, "M1"});

        sendSupportBean("S1", 5L, -1L);
        sendMarketBean("M2");
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fieldsStmt, new Object[] {5L, "M2"});

        sendSupportBean("S2", 10L, -1L);
        sendMarketBean("M3");
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fieldsStmt, new Object[] {15L, "M3"});

        // create 2nd consumer
        EPStatement stmtSelectTwo = epService.getEPAdministrator().createEPL(stmtTextSelectOne); // same stmt
        stmtSelectTwo.addListener(listenerStmtTwo);

        sendSupportBean("S3", 8L, -1L);
        sendMarketBean("M4");
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fieldsStmt, new Object[] {23L, "M4"});
        ArrayAssertionUtil.assertProps(listenerStmtTwo.assertOneGetNewAndReset(), fieldsStmt, new Object[] {23L, "M4"});
    }

    private SupportBean sendSupportBean(String string, long longPrimitive, Long longBoxed)
    {
        SupportBean bean = new SupportBean();
        bean.setString(string);
        bean.setLongPrimitive(longPrimitive);
        bean.setLongBoxed(longBoxed);
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }

    private SupportBean sendSupportBean(String string, int intBoxed)
    {
        SupportBean bean = new SupportBean();
        bean.setString(string);
        bean.setIntBoxed(intBoxed);
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }

    private void sendMarketBean(String symbol)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, 0, 0l, "");
        epService.getEPRuntime().sendEvent(bean);
    }
}
