package net.esper.regression.eql;

import junit.framework.TestCase;
import net.esper.client.*;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBean_A;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.client.SupportConfigFactory;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.support.util.SupportUpdateListener;

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
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);

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
        EPStatement stmtCreate = epService.getEPAdministrator().createEQL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string as a, longPrimitive as b, longBoxed as c from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEQL(stmtTextInsertOne);

        // create consumer
        String stmtTextSelectOne = "select (select a from MyWindow) as value, symbol from " + SupportMarketDataBean.class.getName();
        EPStatement stmtSelectOne = epService.getEPAdministrator().createEQL(stmtTextSelectOne);
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
        String stmtTextSelectTwo = "select (select a from MyWindow) as value, symbol from " + SupportMarketDataBean.class.getName();
        EPStatement stmtSelectTwo = epService.getEPAdministrator().createEQL(stmtTextSelectTwo);
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
        EPStatement stmtDelete = epService.getEPAdministrator().createEQL(stmtTextDelete);
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

    public void testInvalidSubquery()
    {
        epService.getEPAdministrator().createEQL("create window MyWindow.win:keepall() as " + SupportBean.class.getName());
        try
        {
            epService.getEPAdministrator().createEQL("select (select string from MyWindow.std:lastevent()) from MyWindow");
            fail();
        }
        catch (EPException ex)
        {
            assertEquals("Error starting view: Consuming statements to a named window cannot declare a data window view onto the named window [select (select string from MyWindow.std:lastevent()) from MyWindow]", ex.getMessage());
        }
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

    private void sendMarketBean(String symbol)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, 0, 0l, "");
        epService.getEPRuntime().sendEvent(bean);
    }
}
