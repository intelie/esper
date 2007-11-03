package net.esper.regression.eql;

import junit.framework.TestCase;
import net.esper.client.*;
import net.esper.client.time.CurrentTimeEvent;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.client.SupportConfigFactory;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.support.util.SupportUpdateListener;

import java.util.HashMap;
import java.util.Map;

public class TestCreateWindow extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listenerWindow;
    private SupportUpdateListener listenerStmtOne;
    private SupportUpdateListener listenerStmtTwo;
    private SupportUpdateListener listenerStmtThree;
    private SupportUpdateListener listenerStmtDelete;

    // Test all different views
    // Test batch view - multiple events added/removed at once
    // Test type already exists and is compatible
    // Test type already exists and is incompatible: different fields, wrapped or mapped
    // Test type already exists and is incompatible: Boxed/unboxed
    // Test same window created twice
    // Test subqueries
    // Test statement object model + compile
    // Test remove of named windows
    // Invalid use of filter expression or subview in selecting from a named window, use of patterns
    // test optional stream name in as-clause of named window
    // test pattern just getting the insert stream
    // test iterator of create window statement
    // test iterator of statement using the window
    // test join
    // test multiple named windows in one statement as join + outer join
    // test performance with or without named window service dispatch
    // single insert-into may go into 2 named windows joined to each other, should update
    // multiple receiving statements at the same time
    // test group-by in selecting view
    // test group-by in named-window view
    // test stop/start/stop/destroy of select, delete and create window
    // test delete named window
    // test with and without aliases for both window and filter
    // Adding a delete to a already-filled window
    // Test listener not invoked for delete stmts
    // test performance and coercion
    // test MT locking/safety
    // test non-datawindow used
    // test all data windows support removing old data

    public void setUp()
    {
        Map<String, Class> types = new HashMap<String, Class>();
        types.put("key", String.class);
        types.put("value", long.class);

        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventTypeAlias("MyMap", types);
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);

        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listenerWindow = new SupportUpdateListener();
        listenerStmtOne = new SupportUpdateListener();
        listenerStmtTwo = new SupportUpdateListener();
        listenerStmtThree = new SupportUpdateListener();
        listenerStmtDelete = new SupportUpdateListener();
    }

    public void testWithDeleteUseAs()
    {
        tryCreateWindow("create window MyWindow as MyMap",
                        "on " + SupportMarketDataBean.class.getName() + " as s0 delete from MyWindow as s1 where s0.symbol = s1.key");
    }

    public void testWithDeteteFirstAs()
    {
        tryCreateWindow("create window MyWindow as MyMap",
                        "on " + SupportMarketDataBean.class.getName() + " delete from MyWindow as s1 where symbol = s1.key");
    }

    public void testWithDeteteSecondAs()
    {
        tryCreateWindow("create window MyWindow as MyMap",
                        "on " + SupportMarketDataBean.class.getName() + "as s0 delete from MyWindow where s0.symbol = key");
    }

    public void testWithDeteteNoAs()
    {
        tryCreateWindow("create window MyWindow as MyMap",
                        "on " + SupportMarketDataBean.class.getName() + " delete from MyWindow where symbol = key");
    }

    private void tryCreateWindow(String createWindowStatement, String deleteStatement)
    {
        String[] fields = new String[] {"key", "value"};
        String stmtTextCreate = createWindowStatement;
        EPStatement stmtCreate = epService.getEPAdministrator().createEQL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        String stmtTextInsert = "insert into MyWindow select string as key, longBoxed as value from " + SupportBean.class.getName();
        EPStatement stmtInsert = epService.getEPAdministrator().createEQL(stmtTextInsert);

        String stmtTextSelectOne = "select key, value*2 as value from MyWindow";
        EPStatement stmtSelectOne = epService.getEPAdministrator().createEQL(stmtTextSelectOne);
        stmtSelectOne.addListener(listenerStmtOne);

        String stmtTextSelectTwo = "select key, sum(value) as value from MyWindow group by key";
        EPStatement stmtSelectTwo = epService.getEPAdministrator().createEQL(stmtTextSelectTwo);
        stmtSelectTwo.addListener(listenerStmtTwo);

        String stmtTextSelectThree = "select key, value from MyWindow where value >= 10";
        EPStatement stmtSelectThree = epService.getEPAdministrator().createEQL(stmtTextSelectThree);
        stmtSelectThree.addListener(listenerStmtThree);

        // send events
        sendSupportBean("E1", 10L);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E1", 20L});
        ArrayAssertionUtil.assertProps(listenerStmtTwo.getLastNewData()[0], fields, new Object[] {"E1", 10L});
        ArrayAssertionUtil.assertProps(listenerStmtTwo.getLastOldData()[0], fields, new Object[] {"E1", null});
        listenerStmtTwo.reset();
        ArrayAssertionUtil.assertProps(listenerStmtThree.assertOneGetNewAndReset(), fields, new Object[] {"E1", 10L});
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E1", 10L});

        sendSupportBean("E2", 20L);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E2", 40L});
        ArrayAssertionUtil.assertProps(listenerStmtTwo.getLastNewData()[0], fields, new Object[] {"E2", 20L});
        ArrayAssertionUtil.assertProps(listenerStmtTwo.getLastOldData()[0], fields, new Object[] {"E2", null});
        listenerStmtTwo.reset();
        ArrayAssertionUtil.assertProps(listenerStmtThree.assertOneGetNewAndReset(), fields, new Object[] {"E2", 20L});
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E2", 20L});

        sendSupportBean("E3", 5L);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E3", 10L});
        ArrayAssertionUtil.assertProps(listenerStmtTwo.getLastNewData()[0], fields, new Object[] {"E3", 5L});
        ArrayAssertionUtil.assertProps(listenerStmtTwo.getLastOldData()[0], fields, new Object[] {"E3", null});
        listenerStmtTwo.reset();
        assertFalse(listenerStmtThree.isInvoked());
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E3", 5L});

        // create delete stmt
        String stmtTextDelete = deleteStatement;
        EPStatement stmtDelete = epService.getEPAdministrator().createEQL(stmtTextDelete);
        stmtDelete.addListener(listenerStmtDelete);

        // send delete event
        sendMarketBean("E1");
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"E1", 20L});
        ArrayAssertionUtil.assertProps(listenerStmtTwo.getLastNewData()[0], fields, new Object[] {"E1", null});
        ArrayAssertionUtil.assertProps(listenerStmtTwo.getLastOldData()[0], fields, new Object[] {"E1", 10l});
        listenerStmtTwo.reset();
        ArrayAssertionUtil.assertProps(listenerStmtThree.assertOneGetOldAndReset(), fields, new Object[] {"E1", 10L});
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E1", 10L});

        // send delete event again, none deleted now
        sendMarketBean("E1");
        assertFalse(listenerStmtOne.isInvoked());
        assertFalse(listenerStmtTwo.isInvoked());
        assertFalse(listenerWindow.isInvoked());

        // send delete event
        sendMarketBean("E2");
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"E2", 40L});
        ArrayAssertionUtil.assertProps(listenerStmtTwo.getLastNewData()[0], fields, new Object[] {"E2", null});
        ArrayAssertionUtil.assertProps(listenerStmtTwo.getLastOldData()[0], fields, new Object[] {"E2", 20L});
        listenerStmtTwo.reset();
        ArrayAssertionUtil.assertProps(listenerStmtThree.assertOneGetOldAndReset(), fields, new Object[] {"E2", 20L});
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E2", 20L});

        // send delete event
        sendMarketBean("E3");
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"E3", 10L});
        ArrayAssertionUtil.assertProps(listenerStmtTwo.getLastNewData()[0], fields, new Object[] {"E3", null});
        ArrayAssertionUtil.assertProps(listenerStmtTwo.getLastOldData()[0], fields, new Object[] {"E3", 5L});
        listenerStmtTwo.reset();
        assertFalse(listenerStmtThree.isInvoked());
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E3", 5L});

        stmtSelectOne.destroy();
        stmtSelectTwo.destroy();
        stmtSelectThree.destroy();
        stmtDelete.destroy();
        stmtInsert.destroy();
        stmtCreate.destroy();
    }

    public void testTimeWindow()
    {
        String[] fields = new String[] {"key", "value"};
        
        // create window
        String stmtTextCreate = "create window MyWindow.win:time(10 sec) as MyMap";
        EPStatement stmtCreate = epService.getEPAdministrator().createEQL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        // create insert into
        String stmtTextInsert = "insert into MyWindow select string as key, longBoxed as value from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEQL(stmtTextInsert);

        // create consumer
        String stmtTextSelectOne = "select key, value as value from MyWindow";
        EPStatement stmtSelectOne = epService.getEPAdministrator().createEQL(stmtTextSelectOne);
        stmtSelectOne.addListener(listenerStmtOne);

        // create delete stmt
        String stmtTextDelete = "on " + SupportMarketDataBean.class.getName() + " as s0 delete from MyWindow as s1 where s0.symbol = s1.key";
        EPStatement stmtDelete = epService.getEPAdministrator().createEQL(stmtTextDelete);
        stmtDelete.addListener(listenerStmtDelete);

        sendTimer(1000);
        sendSupportBean("E1", 1L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1L});

        sendTimer(5000);
        sendSupportBean("E2", 2L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E2", 2L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E2", 2L});

        sendTimer(10000);
        sendSupportBean("E3", 3L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E3", 3L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E3", 3L});

        // Should push out the window
        sendTimer(10999);
        assertFalse(listenerWindow.isInvoked());
        assertFalse(listenerStmtOne.isInvoked());
        sendTimer(11000);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E1", 1L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"E1", 1L});

        sendSupportBean("E4", 4L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E4", 4L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E4", 4L});

        // delete E2
        sendMarketBean("E2");
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E2", 2L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"E2", 2L});

        // nothing pushed
        sendTimer(15000);
        assertFalse(listenerWindow.isInvoked());
        assertFalse(listenerStmtOne.isInvoked());

        // push last event
        sendTimer(19999);
        assertFalse(listenerWindow.isInvoked());
        assertFalse(listenerStmtOne.isInvoked());
        sendTimer(20000);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E3", 3L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"E3", 3L});

        // delete E4
        sendMarketBean("E4");
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E4", 4L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"E4", 4L});

        sendTimer(100000);
        assertFalse(listenerWindow.isInvoked());
        assertFalse(listenerStmtOne.isInvoked());
    }

    private SupportBean sendSupportBean(String string, Long longBoxed)
    {
        SupportBean bean = new SupportBean();
        bean.setString(string);
        bean.setLongBoxed(longBoxed);
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }

    private void sendMarketBean(String symbol)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, 0, 0l, "");
        epService.getEPRuntime().sendEvent(bean);
    }

    private void sendTimer(long timeInMSec)
    {
        CurrentTimeEvent event = new CurrentTimeEvent(timeInMSec);
        EPRuntime runtime = epService.getEPRuntime();
        runtime.sendEvent(event);
    }    
}
