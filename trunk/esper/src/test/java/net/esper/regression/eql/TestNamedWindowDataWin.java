package net.esper.regression.eql;

import junit.framework.TestCase;
import net.esper.client.*;
import net.esper.client.time.CurrentTimeEvent;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.client.SupportConfigFactory;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.support.util.SupportUpdateListener;
import net.esper.event.EventBean;

import java.util.HashMap;
import java.util.Map;

public class TestNamedWindowDataWin extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listenerWindow;
    private SupportUpdateListener listenerStmtOne;
    private SupportUpdateListener listenerStmtTwo;
    private SupportUpdateListener listenerStmtThree;
    private SupportUpdateListener listenerStmtDelete;

    // Test remove of named windows
    // Invalid use of filter expression or subview in selecting from a named window, use of patterns
    // test pattern just getting the insert stream
    // test iterator of create window statement
    // test iterator of statement using the window
    // test join
    // test multiple named windows in one statement as join + outer join
    // test performance with or without named window service dispatch
    // single insert-into may go into 2 named windows joined to each other, should update
    // test stop/start/stop/destroy of select, delete and create window
    // test delete named window
    // Adding a delete to a already-filled window
    // test performance and coercion
    // test MT locking/safety of iterators and push

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

    public void testWithDeleteFirstAs()
    {
        tryCreateWindow("create window MyWindow as select key, value from MyMap",
                        "on " + SupportMarketDataBean.class.getName() + " delete from MyWindow as s1 where symbol = s1.key");
    }

    public void testWithDeleteSecondAs()
    {
        tryCreateWindow("create window MyWindow as MyMap",
                        "on " + SupportMarketDataBean.class.getName() + " as s0 delete from MyWindow where s0.symbol = key");
    }

    public void testWithDeleteNoAs()
    {
        tryCreateWindow("create window MyWindow as select key as key, value as value from MyMap",
                        "on " + SupportMarketDataBean.class.getName() + " delete from MyWindow where symbol = key");
    }

    private void tryCreateWindow(String createWindowStatement, String deleteStatement)
    {
        String[] fields = new String[] {"key", "value"};
        String stmtTextCreate = createWindowStatement;
        EPStatement stmtCreate = epService.getEPAdministrator().createEQL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        assertEquals(String.class, stmtCreate.getEventType().getPropertyType("key"));
        assertEquals(Long.class, stmtCreate.getEventType().getPropertyType("value"));

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
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 20L}});

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
        assertFalse(listenerStmtDelete.isInvoked());

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
        assertFalse(listenerStmtDelete.isInvoked());

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
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, null);

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
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1L}});

        sendTimer(5000);
        sendSupportBean("E2", 2L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E2", 2L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E2", 2L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1L}, {"E2", 2L}});

        sendTimer(10000);
        sendSupportBean("E3", 3L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E3", 3L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E3", 3L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1L}, {"E2", 2L}, {"E3", 3L}});

        // Should push out the window
        sendTimer(10999);
        assertFalse(listenerWindow.isInvoked());
        assertFalse(listenerStmtOne.isInvoked());
        sendTimer(11000);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E1", 1L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"E1", 1L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E2", 2L}, {"E3", 3L}});

        sendSupportBean("E4", 4L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E4", 4L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E4", 4L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E2", 2L}, {"E3", 3L}, {"E4", 4L}});

        // delete E2
        sendMarketBean("E2");
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E2", 2L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"E2", 2L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E3", 3L}, {"E4", 4L}});

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
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E4", 4L}});

        // delete E4
        sendMarketBean("E4");
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E4", 4L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"E4", 4L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, null);

        sendTimer(100000);
        assertFalse(listenerWindow.isInvoked());
        assertFalse(listenerStmtOne.isInvoked());
    }

    public void testExtTimeWindow()
    {
        String[] fields = new String[] {"key", "value"};

        // create window
        String stmtTextCreate = "create window MyWindow.win:ext_timed('value', 10 sec) as MyMap";
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
        String stmtTextDelete = "on " + SupportMarketDataBean.class.getName() + " delete from MyWindow where symbol = key";
        EPStatement stmtDelete = epService.getEPAdministrator().createEQL(stmtTextDelete);
        stmtDelete.addListener(listenerStmtDelete);

        sendSupportBean("E1", 1000L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1000L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1000L});

        sendSupportBean("E2", 5000L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E2", 5000L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E2", 5000L});

        sendSupportBean("E3", 10000L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E3", 10000L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E3", 10000L});

        // Should push out the window
        sendSupportBean("E4", 11000L);
        ArrayAssertionUtil.assertProps(listenerWindow.getLastNewData()[0], fields, new Object[] {"E4", 11000L});
        ArrayAssertionUtil.assertProps(listenerWindow.getLastOldData()[0], fields, new Object[] {"E1", 1000L});
        listenerWindow.reset();
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastNewData()[0], fields, new Object[] {"E4", 11000L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastOldData()[0], fields, new Object[] {"E1", 1000L});
        listenerStmtOne.reset();

        // delete E2
        sendMarketBean("E2");
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E2", 5000L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"E2", 5000L});

        // nothing pushed other then E5 (E2 is deleted)
        sendSupportBean("E5", 15000L);
        ArrayAssertionUtil.assertProps(listenerWindow.getLastNewData()[0], fields, new Object[] {"E5", 15000L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastNewData()[0], fields, new Object[] {"E5", 15000L});
        assertNull(listenerWindow.getLastOldData());
        assertNull(listenerStmtOne.getLastOldData());
    }

    public void testTimeOrderWindow()
    {
        String[] fields = new String[] {"key", "value"};

        // create window
        String stmtTextCreate = "create window MyWindow.ext:time_order('value', 10 sec) as MyMap";
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
        String stmtTextDelete = "on " + SupportMarketDataBean.class.getName() + " delete from MyWindow where symbol = key";
        EPStatement stmtDelete = epService.getEPAdministrator().createEQL(stmtTextDelete);
        stmtDelete.addListener(listenerStmtDelete);

        sendTimer(5000);
        sendSupportBean("E1", 3000L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E1", 3000L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E1", 3000L});

        sendTimer(6000);
        sendSupportBean("E2", 2000L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E2", 2000L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E2", 2000L});

        sendTimer(10000);
        sendSupportBean("E3", 1000L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E3", 1000L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E3", 1000L});

        // Should push out the window
        sendTimer(11000);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E3", 1000L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"E3", 1000L});

        // delete E2
        sendMarketBean("E2");
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E2", 2000L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"E2", 2000L});

        sendTimer(12999);
        assertFalse(listenerWindow.isInvoked());
        assertFalse(listenerStmtOne.isInvoked());

        sendTimer(13000);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E1", 3000L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"E1", 3000L});

        sendTimer(100000);
        assertFalse(listenerWindow.isInvoked());
        assertFalse(listenerStmtOne.isInvoked());
    }

    public void testLengthWindow()
    {
        String[] fields = new String[] {"key", "value"};

        // create window
        String stmtTextCreate = "create window MyWindow.win:length(3) as MyMap";
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
        String stmtTextDelete = "on " + SupportMarketDataBean.class.getName() + " delete from MyWindow where symbol = key";
        EPStatement stmtDelete = epService.getEPAdministrator().createEQL(stmtTextDelete);
        stmtDelete.addListener(listenerStmtDelete);

        sendSupportBean("E1", 1L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1L});

        sendSupportBean("E2", 2L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E2", 2L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E2", 2L});

        sendSupportBean("E3", 3L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E3", 3L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E3", 3L});

        // delete E2
        sendMarketBean("E2");
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E2", 2L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"E2", 2L});

        sendSupportBean("E4", 4L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E4", 4L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E4", 4L});

        sendSupportBean("E5", 5L);
        ArrayAssertionUtil.assertProps(listenerWindow.getLastNewData()[0], fields, new Object[] {"E5", 5L});
        ArrayAssertionUtil.assertProps(listenerWindow.getLastOldData()[0], fields, new Object[] {"E1", 1L});
        listenerWindow.reset();
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastNewData()[0], fields, new Object[] {"E5", 5L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastOldData()[0], fields, new Object[] {"E1", 1L});
        listenerStmtOne.reset();

        sendSupportBean("E6", 6L);
        ArrayAssertionUtil.assertProps(listenerWindow.getLastNewData()[0], fields, new Object[] {"E6", 6L});
        ArrayAssertionUtil.assertProps(listenerWindow.getLastOldData()[0], fields, new Object[] {"E3", 3L});
        listenerWindow.reset();
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastNewData()[0], fields, new Object[] {"E6", 6L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastOldData()[0], fields, new Object[] {"E3", 3L});
        listenerStmtOne.reset();

        assertFalse(listenerWindow.isInvoked());
        assertFalse(listenerStmtOne.isInvoked());
    }

    public void testTimeAccum()
    {
        String[] fields = new String[] {"key", "value"};

        // create window
        String stmtTextCreate = "create window MyWindow.win:time_accum(10 sec) as MyMap";
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

        sendTimer(15000);
        sendSupportBean("E4", 4L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E4", 4L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E4", 4L});

        // delete E2
        sendMarketBean("E2");
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E2", 2L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"E2", 2L});

        // nothing pushed
        sendTimer(24999);
        assertFalse(listenerWindow.isInvoked());
        assertFalse(listenerStmtOne.isInvoked());

        sendTimer(25000);
        assertNull(listenerWindow.getLastNewData());
        EventBean[] oldData = listenerWindow.getLastOldData();
        assertEquals(3, oldData.length);
        ArrayAssertionUtil.assertProps(oldData[0], fields, new Object[] {"E1", 1L});
        ArrayAssertionUtil.assertProps(oldData[1], fields, new Object[] {"E3", 3L});
        ArrayAssertionUtil.assertProps(oldData[2], fields, new Object[] {"E4", 4L});
        listenerWindow.reset();
        listenerStmtOne.reset();

        // delete E4
        sendMarketBean("E4");
        assertFalse(listenerWindow.isInvoked());
        assertFalse(listenerStmtOne.isInvoked());

        sendTimer(30000);
        sendSupportBean("E5", 5L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E5", 5L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E5", 5L});

        sendTimer(31000);
        sendSupportBean("E6", 6L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E6", 6L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E6", 6L});

        sendTimer(38000);
        sendSupportBean("E7", 7L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E7", 7L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E7", 7L});

        // delete E7 - deleting the last should spit out the first 2 timely
        sendMarketBean("E7");
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E7", 7L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"E7", 7L});

        sendTimer(40999);
        assertFalse(listenerWindow.isInvoked());
        assertFalse(listenerStmtOne.isInvoked());

        sendTimer(41000);
        assertNull(listenerStmtOne.getLastNewData());
        oldData = listenerStmtOne.getLastOldData();
        assertEquals(2, oldData.length);
        ArrayAssertionUtil.assertProps(oldData[0], fields, new Object[] {"E5", 5L});
        ArrayAssertionUtil.assertProps(oldData[1], fields, new Object[] {"E6", 6L});
        listenerWindow.reset();
        listenerStmtOne.reset();

        sendTimer(50000);
        sendSupportBean("E8", 8L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E8", 8L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E8", 8L});

        sendTimer(55000);
        sendMarketBean("E8");
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E8", 8L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"E8", 8L});

        sendTimer(100000);
        assertFalse(listenerWindow.isInvoked());
        assertFalse(listenerStmtOne.isInvoked());
    }

    public void testTimeBatch()
    {
        String[] fields = new String[] {"key", "value"};

        // create window
        String stmtTextCreate = "create window MyWindow.win:time_batch(10 sec) as MyMap";
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

        sendTimer(5000);
        sendSupportBean("E2", 2L);

        sendTimer(10000);
        sendSupportBean("E3", 3L);

        // delete E2
        sendMarketBean("E2");

        // nothing pushed
        sendTimer(10999);
        assertFalse(listenerWindow.isInvoked());
        assertFalse(listenerStmtOne.isInvoked());

        sendTimer(11000);
        assertNull(listenerWindow.getLastOldData());
        EventBean[] newData = listenerWindow.getLastNewData();
        assertEquals(2, newData.length);
        ArrayAssertionUtil.assertProps(newData[0], fields, new Object[] {"E1", 1L});
        ArrayAssertionUtil.assertProps(newData[1], fields, new Object[] {"E3", 3L});
        listenerWindow.reset();
        listenerStmtOne.reset();

        sendTimer(21000);
        assertNull(listenerWindow.getLastNewData());
        EventBean[] oldData = listenerWindow.getLastOldData();
        assertEquals(2, oldData.length);
        ArrayAssertionUtil.assertProps(oldData[0], fields, new Object[] {"E1", 1L});
        ArrayAssertionUtil.assertProps(oldData[1], fields, new Object[] {"E3", 3L});
        listenerWindow.reset();
        listenerStmtOne.reset();

        // send and delete E4, leaving an empty batch
        sendSupportBean("E4", 4L);
        sendMarketBean("E4");
        sendTimer(31000);
        assertFalse(listenerWindow.isInvoked());
        assertFalse(listenerStmtOne.isInvoked());
    }

    public void testLengthBatch()
    {
        String[] fields = new String[] {"key", "value"};

        // create window
        String stmtTextCreate = "create window MyWindow.win:length_batch(3) as MyMap";
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

        sendSupportBean("E1", 1L);
        sendSupportBean("E2", 2L);
        assertFalse(listenerWindow.isInvoked());
        assertFalse(listenerStmtOne.isInvoked());

        // delete E2
        sendMarketBean("E2");

        sendSupportBean("E3", 3L);
        assertFalse(listenerWindow.isInvoked());
        assertFalse(listenerStmtOne.isInvoked());

        sendSupportBean("E4", 4L);
        assertNull(listenerWindow.getLastOldData());
        EventBean[] newData = listenerWindow.getLastNewData();
        assertEquals(3, newData.length);
        ArrayAssertionUtil.assertProps(newData[0], fields, new Object[] {"E1", 1L});
        ArrayAssertionUtil.assertProps(newData[1], fields, new Object[] {"E3", 3L});
        ArrayAssertionUtil.assertProps(newData[2], fields, new Object[] {"E4", 4L});
        listenerWindow.reset();
        listenerStmtOne.reset();

        sendSupportBean("E5", 5L);
        sendSupportBean("E6", 6L);
        sendMarketBean("E5");
        sendMarketBean("E6");
        assertFalse(listenerWindow.isInvoked());
        assertFalse(listenerStmtOne.isInvoked());

        sendSupportBean("E7", 7L);
        sendSupportBean("E8", 8L);
        sendSupportBean("E9", 9L);
        EventBean[] oldData = listenerWindow.getLastOldData();
        newData = listenerWindow.getLastNewData();
        assertEquals(3, newData.length);
        assertEquals(3, oldData.length);
        ArrayAssertionUtil.assertProps(newData[0], fields, new Object[] {"E7", 7L});
        ArrayAssertionUtil.assertProps(newData[1], fields, new Object[] {"E8", 8L});
        ArrayAssertionUtil.assertProps(newData[2], fields, new Object[] {"E9", 9L});
        ArrayAssertionUtil.assertProps(oldData[0], fields, new Object[] {"E1", 1L});
        ArrayAssertionUtil.assertProps(oldData[1], fields, new Object[] {"E3", 3L});
        ArrayAssertionUtil.assertProps(oldData[2], fields, new Object[] {"E4", 4L});
        listenerWindow.reset();
        listenerStmtOne.reset();

        sendSupportBean("E10", 10L);
        sendSupportBean("E10", 11L);
        sendMarketBean("E10");

        sendSupportBean("E21", 21L);
        sendSupportBean("E22", 22L);
        assertFalse(listenerWindow.isInvoked());
        assertFalse(listenerStmtOne.isInvoked());
        sendSupportBean("E23", 23L);
        oldData = listenerWindow.getLastOldData();
        newData = listenerWindow.getLastNewData();
        assertEquals(3, newData.length);
        assertEquals(3, oldData.length);
    }

    public void testSortWindow()
    {
        String[] fields = new String[] {"key", "value"};

        // create window
        String stmtTextCreate = "create window MyWindow.ext:sort('value', false, 3) as MyMap";
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

        sendSupportBean("E1", 10L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E1", 10L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E1", 10L});

        sendSupportBean("E2", 20L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E2", 20L});

        sendSupportBean("E3", 15L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E3", 15L});

        // delete E2
        sendMarketBean("E2");
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E2", 20L});

        sendSupportBean("E4", 18L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E4", 18L});

        sendSupportBean("E5", 17L);
        ArrayAssertionUtil.assertProps(listenerWindow.getLastNewData()[0], fields, new Object[] {"E5", 17L});
        ArrayAssertionUtil.assertProps(listenerWindow.getLastOldData()[0], fields, new Object[] {"E4", 18L});
        listenerWindow.reset();

        // delete E1
        sendMarketBean("E1");
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E1", 10L});

        sendSupportBean("E6", 16L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E6", 16L});

        sendSupportBean("E7", 16L);
        ArrayAssertionUtil.assertProps(listenerWindow.getLastNewData()[0], fields, new Object[] {"E7", 16L});
        ArrayAssertionUtil.assertProps(listenerWindow.getLastOldData()[0], fields, new Object[] {"E5", 17L});
        listenerWindow.reset();

        // delete E7 has no effect
        sendMarketBean("E7");
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E7", 16L});

        sendSupportBean("E8", 1L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E8", 1L});

        sendSupportBean("E9", 1L);
        ArrayAssertionUtil.assertProps(listenerWindow.getLastNewData()[0], fields, new Object[] {"E9", 1L});
        ArrayAssertionUtil.assertProps(listenerWindow.getLastOldData()[0], fields, new Object[] {"E6", 16L});
        listenerWindow.reset();
    }

    public void testTimeLengthBatch()
    {
        String[] fields = new String[] {"key", "value"};

        // create window
        String stmtTextCreate = "create window MyWindow.win:time_length_batch(10 sec, 3) as MyMap";
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
        sendSupportBean("E2", 2L);
        assertFalse(listenerWindow.isInvoked());
        assertFalse(listenerStmtOne.isInvoked());

        // delete E2
        sendMarketBean("E2");
        assertFalse(listenerWindow.isInvoked());
        assertFalse(listenerStmtOne.isInvoked());

        sendSupportBean("E3", 3L);
        assertFalse(listenerWindow.isInvoked());
        assertFalse(listenerStmtOne.isInvoked());

        sendSupportBean("E4", 4L);
        assertNull(listenerWindow.getLastOldData());
        EventBean[] newData = listenerWindow.getLastNewData();
        assertEquals(3, newData.length);
        ArrayAssertionUtil.assertProps(newData[0], fields, new Object[] {"E1", 1L});
        ArrayAssertionUtil.assertProps(newData[1], fields, new Object[] {"E3", 3L});
        ArrayAssertionUtil.assertProps(newData[2], fields, new Object[] {"E4", 4L});
        listenerWindow.reset();
        listenerStmtOne.reset();

        sendTimer(5000);
        sendSupportBean("E5", 5L);
        sendSupportBean("E6", 6L);
        sendMarketBean("E5");   // deleting E5
        assertFalse(listenerWindow.isInvoked());
        assertFalse(listenerStmtOne.isInvoked());

        sendTimer(10999);
        assertFalse(listenerWindow.isInvoked());
        assertFalse(listenerStmtOne.isInvoked());

        sendTimer(11000);
        newData = listenerWindow.getLastNewData();
        assertEquals(1, newData.length);
        ArrayAssertionUtil.assertProps(newData[0], fields, new Object[] {"E6", 6L});
        EventBean[] oldData = listenerWindow.getLastOldData();
        assertEquals(3, oldData.length);
        ArrayAssertionUtil.assertProps(oldData[0], fields, new Object[] {"E1", 1L});
        ArrayAssertionUtil.assertProps(oldData[1], fields, new Object[] {"E3", 3L});
        ArrayAssertionUtil.assertProps(oldData[2], fields, new Object[] {"E4", 4L});
        listenerWindow.reset();
        listenerStmtOne.reset();
    }

    public void testLengthWindowPerGroup()
    {
        String[] fields = new String[] {"key", "value"};

        // create window
        String stmtTextCreate = "create window MyWindow.std:groupby('value').win:length(2) as MyMap";
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
        String stmtTextDelete = "on " + SupportMarketDataBean.class.getName() + " delete from MyWindow where symbol = key";
        EPStatement stmtDelete = epService.getEPAdministrator().createEQL(stmtTextDelete);
        stmtDelete.addListener(listenerStmtDelete);

        sendSupportBean("E1", 1L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1L});

        sendSupportBean("E2", 1L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E2", 1L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E2", 1L});

        sendSupportBean("E3", 2L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E3", 2L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E3", 2L});

        // delete E2
        sendMarketBean("E2");
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E2", 1L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"E2", 1L});

        sendSupportBean("E4", 1L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E4", 1L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E4", 1L});

        sendSupportBean("E5", 1L);
        ArrayAssertionUtil.assertProps(listenerWindow.getLastNewData()[0], fields, new Object[] {"E5", 1L});
        ArrayAssertionUtil.assertProps(listenerWindow.getLastOldData()[0], fields, new Object[] {"E1", 1L});
        listenerWindow.reset();
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastNewData()[0], fields, new Object[] {"E5", 1L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastOldData()[0], fields, new Object[] {"E1", 1L});
        listenerStmtOne.reset();

        sendSupportBean("E6", 2L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E6", 2L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E6", 2L});

        // delete E6
        sendMarketBean("E6");
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E6", 2L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"E6", 2L});

        sendSupportBean("E7", 2L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E7", 2L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E7", 2L});

        sendSupportBean("E8", 2L);
        ArrayAssertionUtil.assertProps(listenerWindow.getLastNewData()[0], fields, new Object[] {"E8", 2L});
        ArrayAssertionUtil.assertProps(listenerWindow.getLastOldData()[0], fields, new Object[] {"E3", 2L});
        listenerWindow.reset();
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastNewData()[0], fields, new Object[] {"E8", 2L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastOldData()[0], fields, new Object[] {"E3", 2L});
        listenerStmtOne.reset();
    }

    public void testInvalidNoDataWindow()
    {
        assertEquals("Error starting view: Named windows require either no child view, or one or more child views that are data window views [create window MyWindow.std:groupby('value').stat:uni('value') as MyMap]",
                     tryInvalid("create window MyWindow.std:groupby('value').stat:uni('value') as MyMap"));        
    }

    public void testAlreadyExists()
    {
        epService.getEPAdministrator().createEQL("create window MyWindow as MyMap");
        try
        {
            epService.getEPAdministrator().createEQL("create window MyWindow as MyMap");
            fail();
        }
        catch (EPException ex)
        {
            assertEquals("Error starting view: A named window by name 'MyWindow' has already been created [create window MyWindow as MyMap]", ex.getMessage());
        }
    }

    private String tryInvalid(String eql)
    {
        try
        {
            epService.getEPAdministrator().createEQL(eql);
            fail();
        }
        catch (EPException ex)
        {
            return ex.getMessage();
        }
        return null;
    }

    public void testPriorGroupStats()
    {
        String[] fieldsSel = new String[] {"key", "value"};
        String[] fieldsPrior = new String[] {"priorKeyOne", "priorKeyTwo"};
        String[] fieldsStat = new String[] {"average"};

        String stmtTextCreate = "create window MyWindow as MyMap";
        EPStatement stmtCreate = epService.getEPAdministrator().createEQL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        assertEquals(String.class, stmtCreate.getEventType().getPropertyType("key"));
        assertEquals(Long.class, stmtCreate.getEventType().getPropertyType("value"));

        String stmtTextInsert = "insert into MyWindow select string as key, longBoxed as value from " + SupportBean.class.getName();
        EPStatement stmtInsert = epService.getEPAdministrator().createEQL(stmtTextInsert);

        String stmtTextSelectOne = "select prior(1, key) as priorKeyOne, prior(2, key) as priorKeyTwo from MyWindow";
        EPStatement stmtSelectOne = epService.getEPAdministrator().createEQL(stmtTextSelectOne);
        stmtSelectOne.addListener(listenerStmtOne);

        String stmtTextSelectTwo = "select key, value from MyWindow.std:groupby('value').win:length(2)";
        EPStatement stmtSelectTwo = epService.getEPAdministrator().createEQL(stmtTextSelectTwo);
        stmtSelectTwo.addListener(listenerStmtTwo);

        String stmtTextSelectThree = "select average from MyWindow.stat:uni('value')";
        EPStatement stmtSelectThree = epService.getEPAdministrator().createEQL(stmtTextSelectThree);
        stmtSelectThree.addListener(listenerStmtThree);

        // send events
        sendSupportBean("E1", 1L);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fieldsPrior, new Object[] {null, null});
        ArrayAssertionUtil.assertProps(listenerStmtTwo.assertOneGetNewAndReset(), fieldsSel, new Object[] {"E1", 1L});
        ArrayAssertionUtil.assertProps(listenerStmtThree.getLastNewData()[0], fieldsStat, new Object[] {1d});

        sendSupportBean("E2", 2L);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fieldsPrior, new Object[] {"E1", null});
        ArrayAssertionUtil.assertProps(listenerStmtTwo.assertOneGetNewAndReset(), fieldsSel, new Object[] {"E2", 2L});
        ArrayAssertionUtil.assertProps(listenerStmtThree.getLastNewData()[0], fieldsStat, new Object[] {1.5d});

        sendSupportBean("E3", 2L);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fieldsPrior, new Object[] {"E2", "E1"});
        ArrayAssertionUtil.assertProps(listenerStmtTwo.assertOneGetNewAndReset(), fieldsSel, new Object[] {"E3", 2L});
        ArrayAssertionUtil.assertProps(listenerStmtThree.getLastNewData()[0], fieldsStat, new Object[] {5/3d});

        sendSupportBean("E4", 2L);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fieldsPrior, new Object[] {"E3", "E2"});
        ArrayAssertionUtil.assertProps(listenerStmtTwo.getLastNewData()[0], fieldsSel, new Object[] {"E4", 2L});
        ArrayAssertionUtil.assertProps(listenerStmtTwo.getLastOldData()[0], fieldsSel, new Object[] {"E2", 2L});
        ArrayAssertionUtil.assertProps(listenerStmtThree.getLastNewData()[0], fieldsStat, new Object[] {1.75});
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
