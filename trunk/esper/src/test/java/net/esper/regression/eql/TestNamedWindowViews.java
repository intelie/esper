package net.esper.regression.eql;

import junit.framework.TestCase;
import net.esper.client.*;
import net.esper.client.time.CurrentTimeEvent;
import net.esper.event.EventBean;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.client.SupportConfigFactory;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.support.util.SupportUpdateListener;

import java.util.HashMap;
import java.util.Map;

public class TestNamedWindowViews extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listenerWindow;
    private SupportUpdateListener listenerStmtOne;
    private SupportUpdateListener listenerStmtTwo;
    private SupportUpdateListener listenerStmtThree;
    private SupportUpdateListener listenerStmtDelete;

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
        tryCreateWindow("create window MyWindow.win:keepall() as MyMap",
                        "on " + SupportMarketDataBean.class.getName() + " as s0 delete from MyWindow as s1 where s0.symbol = s1.key");
    }

    public void testWithDeleteFirstAs()
    {
        tryCreateWindow("create window MyWindow.win:keepall() as select key, value from MyMap",
                        "on " + SupportMarketDataBean.class.getName() + " delete from MyWindow as s1 where symbol = s1.key");
    }

    public void testWithDeleteSecondAs()
    {
        tryCreateWindow("create window MyWindow.win:keepall() as MyMap",
                        "on " + SupportMarketDataBean.class.getName() + " as s0 delete from MyWindow where s0.symbol = key");
    }

    public void testWithDeleteNoAs()
    {
        tryCreateWindow("create window MyWindow.win:keepall() as select key as key, value as value from MyMap",
                        "on " + SupportMarketDataBean.class.getName() + " delete from MyWindow where symbol = key");
    }

    private void tryCreateWindow(String createWindowStatement, String deleteStatement)
    {
        String[] fields = new String[] {"key", "value"};
        EPStatement stmtCreate = epService.getEPAdministrator().createEQL(createWindowStatement);
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
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 10L}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelectOne.iterator(), fields, new Object[][] {{"E1", 20L}});

        sendSupportBean("E2", 20L);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E2", 40L});
        ArrayAssertionUtil.assertProps(listenerStmtTwo.getLastNewData()[0], fields, new Object[] {"E2", 20L});
        ArrayAssertionUtil.assertProps(listenerStmtTwo.getLastOldData()[0], fields, new Object[] {"E2", null});
        listenerStmtTwo.reset();
        ArrayAssertionUtil.assertProps(listenerStmtThree.assertOneGetNewAndReset(), fields, new Object[] {"E2", 20L});
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E2", 20L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 10L}, {"E2", 20L}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelectOne.iterator(), fields, new Object[][] {{"E2", 40L}});

        sendSupportBean("E3", 5L);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E3", 10L});
        ArrayAssertionUtil.assertProps(listenerStmtTwo.getLastNewData()[0], fields, new Object[] {"E3", 5L});
        ArrayAssertionUtil.assertProps(listenerStmtTwo.getLastOldData()[0], fields, new Object[] {"E3", null});
        listenerStmtTwo.reset();
        assertFalse(listenerStmtThree.isInvoked());
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E3", 5L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 10L}, {"E2", 20L}, {"E3", 5L}});

        // create delete stmt
        EPStatement stmtDelete = epService.getEPAdministrator().createEQL(deleteStatement);
        stmtDelete.addListener(listenerStmtDelete);

        // send delete event
        sendMarketBean("E1");
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"E1", 20L});
        ArrayAssertionUtil.assertProps(listenerStmtTwo.getLastNewData()[0], fields, new Object[] {"E1", null});
        ArrayAssertionUtil.assertProps(listenerStmtTwo.getLastOldData()[0], fields, new Object[] {"E1", 10l});
        listenerStmtTwo.reset();
        ArrayAssertionUtil.assertProps(listenerStmtThree.assertOneGetOldAndReset(), fields, new Object[] {"E1", 10L});
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E1", 10L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E2", 20L}, {"E3", 5L}});

        // send delete event again, none deleted now
        sendMarketBean("E1");
        assertFalse(listenerStmtOne.isInvoked());
        assertFalse(listenerStmtTwo.isInvoked());
        assertFalse(listenerWindow.isInvoked());
        assertTrue(listenerStmtDelete.isInvoked());
        listenerStmtDelete.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E2", 20L}, {"E3", 5L}});

        // send delete event
        sendMarketBean("E2");
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"E2", 40L});
        ArrayAssertionUtil.assertProps(listenerStmtTwo.getLastNewData()[0], fields, new Object[] {"E2", null});
        ArrayAssertionUtil.assertProps(listenerStmtTwo.getLastOldData()[0], fields, new Object[] {"E2", 20L});
        listenerStmtTwo.reset();
        ArrayAssertionUtil.assertProps(listenerStmtThree.assertOneGetOldAndReset(), fields, new Object[] {"E2", 20L});
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E2", 20L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E3", 5L}});

        // send delete event
        sendMarketBean("E3");
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"E3", 10L});
        ArrayAssertionUtil.assertProps(listenerStmtTwo.getLastNewData()[0], fields, new Object[] {"E3", null});
        ArrayAssertionUtil.assertProps(listenerStmtTwo.getLastOldData()[0], fields, new Object[] {"E3", 5L});
        listenerStmtTwo.reset();
        assertFalse(listenerStmtThree.isInvoked());
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E3", 5L});
        assertTrue(listenerStmtDelete.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, null);

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
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1000L}});

        sendSupportBean("E2", 5000L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E2", 5000L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E2", 5000L});

        sendSupportBean("E3", 10000L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E3", 10000L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E3", 10000L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1000L}, {"E2", 5000L}, {"E3", 10000L}});

        // Should push out the window
        sendSupportBean("E4", 11000L);
        ArrayAssertionUtil.assertProps(listenerWindow.getLastNewData()[0], fields, new Object[] {"E4", 11000L});
        ArrayAssertionUtil.assertProps(listenerWindow.getLastOldData()[0], fields, new Object[] {"E1", 1000L});
        listenerWindow.reset();
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastNewData()[0], fields, new Object[] {"E4", 11000L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastOldData()[0], fields, new Object[] {"E1", 1000L});
        listenerStmtOne.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E2", 5000L}, {"E3", 10000L}, {"E4", 11000L}});

        // delete E2
        sendMarketBean("E2");
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E2", 5000L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"E2", 5000L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E3", 10000L}, {"E4", 11000L}});

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
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E3", 1000L}, {"E2", 2000L}, {"E1", 3000L}});

        // Should push out the window
        sendTimer(11000);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E3", 1000L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"E3", 1000L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E2", 2000L}, {"E1", 3000L}});

        // delete E2
        sendMarketBean("E2");
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E2", 2000L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"E2", 2000L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 3000L}});

        sendTimer(12999);
        assertFalse(listenerWindow.isInvoked());
        assertFalse(listenerStmtOne.isInvoked());

        sendTimer(13000);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E1", 3000L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"E1", 3000L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, null);

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
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1L}, {"E2", 2L}, {"E3", 3L}});

        // delete E2
        sendMarketBean("E2");
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E2", 2L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"E2", 2L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1L}, {"E3", 3L}});

        sendSupportBean("E4", 4L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E4", 4L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E4", 4L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1L}, {"E3", 3L}, {"E4", 4L}});

        sendSupportBean("E5", 5L);
        ArrayAssertionUtil.assertProps(listenerWindow.getLastNewData()[0], fields, new Object[] {"E5", 5L});
        ArrayAssertionUtil.assertProps(listenerWindow.getLastOldData()[0], fields, new Object[] {"E1", 1L});
        listenerWindow.reset();
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastNewData()[0], fields, new Object[] {"E5", 5L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastOldData()[0], fields, new Object[] {"E1", 1L});
        listenerStmtOne.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E3", 3L}, {"E4", 4L}, {"E5", 5L}});

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
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1L}, {"E2", 2L}, {"E3", 3L}, {"E4", 4L}});

        // delete E2
        sendMarketBean("E2");
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E2", 2L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"E2", 2L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1L}, {"E3", 3L}, {"E4", 4L}});

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
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, null);

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
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E5", 5L}, {"E6", 6L}, {"E7", 7L}});

        // delete E7 - deleting the last should spit out the first 2 timely
        sendMarketBean("E7");
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E7", 7L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"E7", 7L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E5", 5L}, {"E6", 6L}});

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
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, null);

        sendTimer(50000);
        sendSupportBean("E8", 8L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E8", 8L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E8", 8L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E8", 8L}});

        sendTimer(55000);
        sendMarketBean("E8");
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E8", 8L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"E8", 8L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, null);

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
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1L}, {"E2", 2L}, {"E3", 3L}});

        // delete E2
        sendMarketBean("E2");
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1L}, {"E3", 3L}});

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
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, null);

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
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E4", 4L}});

        sendMarketBean("E4");
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, null);

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
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1L}, {"E2", 2L}});

        // delete E2
        sendMarketBean("E2");
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1L}});

        sendSupportBean("E3", 3L);
        assertFalse(listenerWindow.isInvoked());
        assertFalse(listenerStmtOne.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1L}, {"E3", 3L}});

        sendSupportBean("E4", 4L);
        assertNull(listenerWindow.getLastOldData());
        EventBean[] newData = listenerWindow.getLastNewData();
        assertEquals(3, newData.length);
        ArrayAssertionUtil.assertProps(newData[0], fields, new Object[] {"E1", 1L});
        ArrayAssertionUtil.assertProps(newData[1], fields, new Object[] {"E3", 3L});
        ArrayAssertionUtil.assertProps(newData[2], fields, new Object[] {"E4", 4L});
        listenerWindow.reset();
        listenerStmtOne.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, null);

        sendSupportBean("E5", 5L);
        sendSupportBean("E6", 6L);
        sendMarketBean("E5");
        sendMarketBean("E6");
        assertFalse(listenerWindow.isInvoked());
        assertFalse(listenerStmtOne.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, null);

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
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 10L}, {"E3", 15L}, {"E2", 20L}});

        // delete E2
        sendMarketBean("E2");
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E2", 20L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 10L}, {"E3", 15L}});

        sendSupportBean("E4", 18L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E4", 18L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 10L}, {"E3", 15L}, {"E4", 18L}});

        sendSupportBean("E5", 17L);
        ArrayAssertionUtil.assertProps(listenerWindow.getLastNewData()[0], fields, new Object[] {"E5", 17L});
        ArrayAssertionUtil.assertProps(listenerWindow.getLastOldData()[0], fields, new Object[] {"E4", 18L});
        listenerWindow.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 10L}, {"E3", 15L}, {"E5", 17L}});

        // delete E1
        sendMarketBean("E1");
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E1", 10L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E3", 15L}, {"E5", 17L}});

        sendSupportBean("E6", 16L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E6", 16L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E3", 15L}, {"E6", 16L}, {"E5", 17L}});

        sendSupportBean("E7", 16L);
        ArrayAssertionUtil.assertProps(listenerWindow.getLastNewData()[0], fields, new Object[] {"E7", 16L});
        ArrayAssertionUtil.assertProps(listenerWindow.getLastOldData()[0], fields, new Object[] {"E5", 17L});
        listenerWindow.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E3", 15L}, {"E7", 16L}, {"E6", 16L}});

        // delete E7 has no effect
        sendMarketBean("E7");
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E7", 16L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E3", 15L}, {"E6", 16L}});

        sendSupportBean("E8", 1L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E8", 1L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E8", 1L}, {"E3", 15L}, {"E6", 16L}});

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
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1L}, {"E2", 2L}});

        // delete E2
        sendMarketBean("E2");
        assertFalse(listenerWindow.isInvoked());
        assertFalse(listenerStmtOne.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1L}});

        sendSupportBean("E3", 3L);
        assertFalse(listenerWindow.isInvoked());
        assertFalse(listenerStmtOne.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1L}, {"E3", 3L}});

        sendSupportBean("E4", 4L);
        assertNull(listenerWindow.getLastOldData());
        EventBean[] newData = listenerWindow.getLastNewData();
        assertEquals(3, newData.length);
        ArrayAssertionUtil.assertProps(newData[0], fields, new Object[] {"E1", 1L});
        ArrayAssertionUtil.assertProps(newData[1], fields, new Object[] {"E3", 3L});
        ArrayAssertionUtil.assertProps(newData[2], fields, new Object[] {"E4", 4L});
        listenerWindow.reset();
        listenerStmtOne.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, null);

        sendTimer(5000);
        sendSupportBean("E5", 5L);
        sendSupportBean("E6", 6L);
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E5", 5L}, {"E6", 6L}});

        sendMarketBean("E5");   // deleting E5
        assertFalse(listenerWindow.isInvoked());
        assertFalse(listenerStmtOne.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E6", 6L}});

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
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1L}, {"E2", 1L}, {"E3", 2L}});

        // delete E2
        sendMarketBean("E2");
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E2", 1L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"E2", 1L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1L}, {"E3", 2L}});

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

    public void testTimeBatchPerGroup()
    {
        String[] fields = new String[] {"key", "value"};

        // create window
        String stmtTextCreate = "create window MyWindow.std:groupby('value').win:time_batch(10 sec) as MyMap";
        EPStatement stmtCreate = epService.getEPAdministrator().createEQL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        // create insert into
        String stmtTextInsert = "insert into MyWindow select string as key, longBoxed as value from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEQL(stmtTextInsert);

        // create consumer
        String stmtTextSelectOne = "select key, value as value from MyWindow";
        EPStatement stmtSelectOne = epService.getEPAdministrator().createEQL(stmtTextSelectOne);
        stmtSelectOne.addListener(listenerStmtOne);

        sendTimer(1000);
        sendSupportBean("E1", 10L);
        sendSupportBean("E2", 20L);
        sendSupportBean("E3", 20L);
        sendSupportBean("E4", 10L);

        sendTimer(11000);
        assertEquals(listenerWindow.getLastNewData().length, 4);
        assertEquals(listenerStmtOne.getLastNewData().length, 4);
        ArrayAssertionUtil.assertProps(listenerWindow.getLastNewData()[0], fields, new Object[] {"E1", 10L});
        ArrayAssertionUtil.assertProps(listenerWindow.getLastNewData()[1], fields, new Object[] {"E4", 10L});
        ArrayAssertionUtil.assertProps(listenerWindow.getLastNewData()[2], fields, new Object[] {"E2", 20L});
        ArrayAssertionUtil.assertProps(listenerWindow.getLastNewData()[3], fields, new Object[] {"E3", 20L});
        listenerWindow.reset();
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastNewData()[0], fields, new Object[] {"E1", 10L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastNewData()[1], fields, new Object[] {"E4", 10L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastNewData()[2], fields, new Object[] {"E2", 20L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastNewData()[3], fields, new Object[] {"E3", 20L});
        listenerStmtOne.reset();
    }

    public void testDoubleInsertSameWindow()
    {
        String[] fields = new String[] {"key", "value"};

        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as MyMap";
        EPStatement stmtCreate = epService.getEPAdministrator().createEQL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        // create insert into
        String stmtTextInsert = "insert into MyWindow select string as key, longBoxed+1 as value from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEQL(stmtTextInsert);

        stmtTextInsert = "insert into MyWindow select string as key, longBoxed+2 as value from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEQL(stmtTextInsert);

        // create consumer
        String stmtTextSelectOne = "select key, value as value from MyWindow";
        EPStatement stmtSelectOne = epService.getEPAdministrator().createEQL(stmtTextSelectOne);
        stmtSelectOne.addListener(listenerStmtOne);

        sendSupportBean("E1", 10L);
        assertEquals(2, listenerWindow.getNewDataList().size());    // listener to window gets 2 individual events
        assertEquals(1, listenerStmtOne.getNewDataList().size());   // listener to statement gets 1 individual event
        assertEquals(2, listenerWindow.getNewDataListFlattened().length);
        assertEquals(2, listenerStmtOne.getNewDataListFlattened().length);
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastNewData()[0], fields, new Object[] {"E1", 11L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastNewData()[1], fields, new Object[] {"E1", 12L});
        listenerStmtOne.reset();
    }

    public void testLastEvent()
    {
        String[] fields = new String[] {"key", "value"};

        // create window
        String stmtTextCreate = "create window MyWindow.std:lastevent() as MyMap";
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
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastNewData()[0], fields, new Object[] {"E1", 1L});
        assertNull(listenerStmtOne.getLastOldData());
        listenerStmtOne.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1L}});

        sendSupportBean("E2", 2L);
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastNewData()[0], fields, new Object[] {"E2", 2L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastOldData()[0], fields, new Object[] {"E1", 1L});
        listenerStmtOne.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E2", 2L}});

        // delete E2
        sendMarketBean("E2");
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastOldData()[0], fields, new Object[] {"E2", 2L});
        assertNull(listenerStmtOne.getLastNewData());
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, null);

        sendSupportBean("E3", 3L);
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastNewData()[0], fields, new Object[] {"E3", 3L});
        assertNull(listenerStmtOne.getLastOldData());
        listenerStmtOne.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E3", 3L}});

        // delete E3
        sendMarketBean("E3");
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastOldData()[0], fields, new Object[] {"E3", 3L});
        assertNull(listenerStmtOne.getLastNewData());
        listenerStmtOne.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, null);

        sendSupportBean("E4", 4L);
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastNewData()[0], fields, new Object[] {"E4", 4L});
        assertNull(listenerStmtOne.getLastOldData());
        listenerStmtOne.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E4", 4L}});

        // delete other event
        sendMarketBean("E1");
        assertFalse(listenerStmtOne.isInvoked());
    }

    public void testUnique()
    {
        String[] fields = new String[] {"key", "value"};

        // create window
        String stmtTextCreate = "create window MyWindow.std:unique('key') as MyMap";
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

        sendSupportBean("G1", 1L);
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastNewData()[0], fields, new Object[] {"G1", 1L});
        assertNull(listenerStmtOne.getLastOldData());
        listenerStmtOne.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"G1", 1L}});

        sendSupportBean("G2", 20L);
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastNewData()[0], fields, new Object[] {"G2", 20L});
        assertNull(listenerStmtOne.getLastOldData());
        listenerStmtOne.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"G1", 1L}, {"G2", 20L}});

        // delete G2
        sendMarketBean("G2");
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastOldData()[0], fields, new Object[] {"G2", 20L});
        assertNull(listenerStmtOne.getLastNewData());
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"G1", 1L}});

        sendSupportBean("G1", 2L);
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastNewData()[0], fields, new Object[] {"G1", 2L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastOldData()[0], fields, new Object[] {"G1", 1L});
        listenerStmtOne.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"G1", 2L}});

        sendSupportBean("G2", 21L);
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastNewData()[0], fields, new Object[] {"G2", 21L});
        assertNull(listenerStmtOne.getLastOldData());
        listenerStmtOne.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"G1", 2L}, {"G2", 21L}});

        sendSupportBean("G2", 22L);
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastNewData()[0], fields, new Object[] {"G2", 22L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastOldData()[0], fields, new Object[] {"G2", 21L});
        listenerStmtOne.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"G1", 2L}, {"G2", 22L}});

        sendMarketBean("G1");
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastOldData()[0], fields, new Object[] {"G1", 2L});
        assertNull(listenerStmtOne.getLastNewData());
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"G2", 22L}});
    }

    public void testInvalidNoDataWindow()
    {
        assertEquals("Error starting view: Named windows require one or more child views that are data window views [create window MyWindow.std:groupby('value').stat:uni('value') as MyMap]",
                     tryInvalid("create window MyWindow.std:groupby('value').stat:uni('value') as MyMap"));        
        assertEquals("Named windows require one or more child views that are data window views [create window MyWindow as MyMap]",
                     tryInvalid("create window MyWindow as MyMap"));
        assertEquals("Named window 'dummy' has not been declared [on MyMap delete from dummy]",
                     tryInvalid("on MyMap delete from dummy"));
    }

    public void testAlreadyExists()
    {
        epService.getEPAdministrator().createEQL("create window MyWindow.win:keepall() as MyMap");
        try
        {
            epService.getEPAdministrator().createEQL("create window MyWindow.win:keepall() as MyMap");
            fail();
        }
        catch (EPException ex)
        {
            assertEquals("Error starting view: A named window by name 'MyWindow' has already been created [create window MyWindow.win:keepall() as MyMap]", ex.getMessage());
        }
    }

    public void testConsumerDataWindow()
    {
        epService.getEPAdministrator().createEQL("create window MyWindow.win:keepall() as MyMap");
        try
        {
            epService.getEPAdministrator().createEQL("select key, value as value from MyWindow.win:time(10 sec)");
            fail();
        }
        catch (EPException ex)
        {
            assertEquals("Error starting view: Consuming statements to a named window cannot declare a data window view onto the named window [select key, value as value from MyWindow.win:time(10 sec)]", ex.getMessage());
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

    public void testPriorStats()
    {
        String[] fieldsPrior = new String[] {"priorKeyOne", "priorKeyTwo"};
        String[] fieldsStat = new String[] {"average"};

        String stmtTextCreate = "create window MyWindow.win:keepall() as MyMap";
        EPStatement stmtCreate = epService.getEPAdministrator().createEQL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        assertEquals(String.class, stmtCreate.getEventType().getPropertyType("key"));
        assertEquals(Long.class, stmtCreate.getEventType().getPropertyType("value"));

        String stmtTextInsert = "insert into MyWindow select string as key, longBoxed as value from " + SupportBean.class.getName();
        EPStatement stmtInsert = epService.getEPAdministrator().createEQL(stmtTextInsert);

        String stmtTextSelectOne = "select prior(1, key) as priorKeyOne, prior(2, key) as priorKeyTwo from MyWindow";
        EPStatement stmtSelectOne = epService.getEPAdministrator().createEQL(stmtTextSelectOne);
        stmtSelectOne.addListener(listenerStmtOne);

        String stmtTextSelectThree = "select average from MyWindow.stat:uni('value')";
        EPStatement stmtSelectThree = epService.getEPAdministrator().createEQL(stmtTextSelectThree);
        stmtSelectThree.addListener(listenerStmtThree);

        // send events
        sendSupportBean("E1", 1L);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fieldsPrior, new Object[] {null, null});
        ArrayAssertionUtil.assertProps(listenerStmtThree.getLastNewData()[0], fieldsStat, new Object[] {1d});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelectThree.iterator(), fieldsStat, new Object[][] {{1d}});

        sendSupportBean("E2", 2L);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fieldsPrior, new Object[] {"E1", null});
        ArrayAssertionUtil.assertProps(listenerStmtThree.getLastNewData()[0], fieldsStat, new Object[] {1.5d});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelectThree.iterator(), fieldsStat, new Object[][] {{1.5d}});

        sendSupportBean("E3", 2L);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fieldsPrior, new Object[] {"E2", "E1"});
        ArrayAssertionUtil.assertProps(listenerStmtThree.getLastNewData()[0], fieldsStat, new Object[] {5/3d});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelectThree.iterator(), fieldsStat, new Object[][] {{5/3d}});

        sendSupportBean("E4", 2L);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fieldsPrior, new Object[] {"E3", "E2"});
        ArrayAssertionUtil.assertProps(listenerStmtThree.getLastNewData()[0], fieldsStat, new Object[] {1.75});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelectThree.iterator(), fieldsStat, new Object[][] {{1.75d}});
    }

    public void testLateConsumer()
    {
        String[] fieldsWin = new String[] {"key", "value"};
        String[] fieldsStat = new String[] {"average"};
        String[] fieldsCnt = new String[] {"cnt"};

        String stmtTextCreate = "create window MyWindow.win:keepall() as MyMap";
        EPStatement stmtCreate = epService.getEPAdministrator().createEQL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        assertEquals(String.class, stmtCreate.getEventType().getPropertyType("key"));
        assertEquals(Long.class, stmtCreate.getEventType().getPropertyType("value"));

        String stmtTextInsert = "insert into MyWindow select string as key, longBoxed as value from " + SupportBean.class.getName();
        EPStatement stmtInsert = epService.getEPAdministrator().createEQL(stmtTextInsert);

        // send events
        sendSupportBean("E1", 1L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fieldsWin, new Object[] {"E1", 1L});

        sendSupportBean("E2", 2L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fieldsWin, new Object[] {"E2", 2L});

        String stmtTextSelectOne = "select average from MyWindow.stat:uni('value')";
        EPStatement stmtSelectOne = epService.getEPAdministrator().createEQL(stmtTextSelectOne);
        stmtSelectOne.addListener(listenerStmtOne);
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelectOne.iterator(), fieldsStat, new Object[][] {{1.5d}});

        sendSupportBean("E3", 2L);
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastNewData()[0], fieldsStat, new Object[] {5/3d});
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastOldData()[0], fieldsStat, new Object[] {3/2d});
        listenerStmtOne.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelectOne.iterator(), fieldsStat, new Object[][] {{5/3d}});

        sendSupportBean("E4", 2L);
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastNewData()[0], fieldsStat, new Object[] {7/4d});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelectOne.iterator(), fieldsStat, new Object[][] {{7/4d}});

        String stmtTextSelectTwo = "select count(*) as cnt from MyWindow";
        EPStatement stmtSelectTwo = epService.getEPAdministrator().createEQL(stmtTextSelectTwo);
        stmtSelectTwo.addListener(listenerStmtTwo);
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelectTwo.iterator(), fieldsCnt, new Object[][] {{4L}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelectOne.iterator(), fieldsStat, new Object[][] {{7/4d}});

        sendSupportBean("E5", 3L);
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastNewData()[0], fieldsStat, new Object[] {10/5d});
        ArrayAssertionUtil.assertProps(listenerStmtOne.getLastOldData()[0], fieldsStat, new Object[] {7/4d});
        listenerStmtOne.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelectOne.iterator(), fieldsStat, new Object[][] {{10/5d}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelectTwo.iterator(), fieldsCnt, new Object[][] {{5L}});
    }

    public void testLateConsumerJoin()
    {
        String[] fieldsWin = new String[] {"key", "value"};
        String[] fieldsJoin = new String[] {"key", "value", "symbol"};

        String stmtTextCreate = "create window MyWindow.win:keepall() as MyMap";
        EPStatement stmtCreate = epService.getEPAdministrator().createEQL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        assertEquals(String.class, stmtCreate.getEventType().getPropertyType("key"));
        assertEquals(Long.class, stmtCreate.getEventType().getPropertyType("value"));

        String stmtTextInsert = "insert into MyWindow select string as key, longBoxed as value from " + SupportBean.class.getName();
        EPStatement stmtInsert = epService.getEPAdministrator().createEQL(stmtTextInsert);

        // send events
        sendSupportBean("E1", 1L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fieldsWin, new Object[] {"E1", 1L});

        sendSupportBean("E2", 1L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fieldsWin, new Object[] {"E2", 1L});

        // This replays into MyWindow
        String stmtTextSelectTwo = "select key, value, symbol from MyWindow as s0" +
                                   " left outer join " + SupportMarketDataBean.class.getName() + " as s1" +
                                   " on s0.value = s1.volume";
        EPStatement stmtSelectTwo = epService.getEPAdministrator().createEQL(stmtTextSelectTwo);
        stmtSelectTwo.addListener(listenerStmtTwo);
        assertFalse(listenerStmtTwo.isInvoked());
        ArrayAssertionUtil.assertEqualsAnyOrder(stmtSelectTwo.iterator(), fieldsJoin, new Object[][] {{"E1", 1L, null}, {"E2", 1L, null}});

        sendMarketBean("S1", 1);    // join on long
        assertEquals(2, listenerStmtTwo.getLastNewData().length);
        if (listenerStmtTwo.getLastNewData()[0].get("key").equals("E1"))
        {
            ArrayAssertionUtil.assertProps(listenerStmtTwo.getLastNewData()[0], fieldsJoin, new Object[] {"E1", 1L, "S1"});
            ArrayAssertionUtil.assertProps(listenerStmtTwo.getLastNewData()[1], fieldsJoin, new Object[] {"E2", 1L, "S1"});
        }
        else
        {
            ArrayAssertionUtil.assertProps(listenerStmtTwo.getLastNewData()[0], fieldsJoin, new Object[] {"E2", 1L, "S1"});
            ArrayAssertionUtil.assertProps(listenerStmtTwo.getLastNewData()[1], fieldsJoin, new Object[] {"E1", 1L, "S1"});
        }
        ArrayAssertionUtil.assertEqualsAnyOrder(stmtSelectTwo.iterator(), fieldsJoin, new Object[][] {{"E1", 1L, "S1"}, {"E2", 1L, "S1"}});
        listenerStmtTwo.reset();

        sendMarketBean("S2", 2);    // join on long
        assertFalse(listenerStmtTwo.isInvoked());
        ArrayAssertionUtil.assertEqualsAnyOrder(stmtSelectTwo.iterator(), fieldsJoin, new Object[][] {{"E1", 1L, "S1"}, {"E2", 1L, "S1"}});

        sendSupportBean("E3", 2L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fieldsWin, new Object[] {"E3", 2L});
        ArrayAssertionUtil.assertProps(listenerStmtTwo.getLastNewData()[0], fieldsJoin, new Object[] {"E3", 2L, "S2"});
        ArrayAssertionUtil.assertEqualsAnyOrder(stmtSelectTwo.iterator(), fieldsJoin, new Object[][] {{"E1", 1L, "S1"}, {"E2", 1L, "S1"}, {"E3", 2L, "S2"}});
    }

    public void testPattern()
    {
        String[] fields = new String[] {"key", "value"};
        String stmtTextCreate = "create window MyWindow.win:keepall() as MyMap";
        EPStatement stmtCreate = epService.getEPAdministrator().createEQL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        String stmtTextPattern = "select a.key as key, a.value as value from pattern [every a=MyWindow(key='S1') or a=MyWindow(key='S2')]";
        EPStatement stmtPattern = epService.getEPAdministrator().createEQL(stmtTextPattern);
        stmtPattern.addListener(listenerStmtOne);

        String stmtTextInsert = "insert into MyWindow select string as key, longBoxed as value from " + SupportBean.class.getName();
        EPStatement stmtInsert = epService.getEPAdministrator().createEQL(stmtTextInsert);

        sendSupportBean("E1", 1L);
        assertFalse(listenerStmtOne.isInvoked());

        sendSupportBean("S1", 2L);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"S1", 2L});

        sendSupportBean("S1", 3L);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"S1", 3L});

        sendSupportBean("S2", 4L);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"S2", 4L});

        sendSupportBean("S1", 1L);
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

    private void sendMarketBean(String symbol, long volume)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, 0, volume, "");
        epService.getEPRuntime().sendEvent(bean);
    }

    private void sendTimer(long timeInMSec)
    {
        CurrentTimeEvent event = new CurrentTimeEvent(timeInMSec);
        EPRuntime runtime = epService.getEPRuntime();
        runtime.sendEvent(event);
    }    
}
