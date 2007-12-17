package net.esper.regression.eql;

import junit.framework.TestCase;
import net.esper.client.Configuration;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBean_A;
import net.esper.support.bean.SupportBean_B;
import net.esper.support.client.SupportConfigFactory;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.support.util.SupportUpdateListener;

import java.util.LinkedList;
import java.util.List;

public class TestNamedWindowDelete extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listenerWindow;
    private SupportUpdateListener listenerWindowTwo;
    private SupportUpdateListener listenerSelect;
    private SupportUpdateListener listenerDelete;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);

        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listenerWindow = new SupportUpdateListener();
        listenerWindowTwo = new SupportUpdateListener();
        listenerSelect = new SupportUpdateListener();
        listenerDelete = new SupportUpdateListener();
    }

    public void testStaggeredNamedWindow()
    {
        String[] fieldsOne = new String[] {"a1", "b1"};
        String[] fieldsTwo = new String[] {"a2", "b2"};

        // create window one
        String stmtTextCreateOne = "create window MyWindowOne.win:keepall() as select string as a1, intPrimitive as b1 from " + SupportBean.class.getName();
        EPStatement stmtCreateOne = epService.getEPAdministrator().createEQL(stmtTextCreateOne);
        stmtCreateOne.addListener(listenerWindow);

        // create window one
        String stmtTextCreateTwo = "create window MyWindowTwo.win:keepall() as select string as a2, intPrimitive as b2 from " + SupportBean.class.getName();
        EPStatement stmtCreateTwo = epService.getEPAdministrator().createEQL(stmtTextCreateTwo);
        stmtCreateTwo.addListener(listenerWindowTwo);

        // create delete stmt
        String stmtTextDelete = "on MyWindowOne delete from MyWindowTwo where a1 = a2";
        EPStatement stmtDelete = epService.getEPAdministrator().createEQL(stmtTextDelete);
        stmtDelete.addListener(listenerDelete);

        // create insert into
        String stmtTextInsert = "insert into MyWindowOne select string as a1, intPrimitive as b1 from " + SupportBean.class.getName() + "(intPrimitive > 0)";
        epService.getEPAdministrator().createEQL(stmtTextInsert);
        stmtTextInsert = "insert into MyWindowTwo select string as a2, intPrimitive as b2 from " + SupportBean.class.getName() + "(intPrimitive < 0)";
        epService.getEPAdministrator().createEQL(stmtTextInsert);

        sendSupportBean("E1", -10);
        ArrayAssertionUtil.assertProps(listenerWindowTwo.assertOneGetNewAndReset(), fieldsTwo, new Object[] {"E1", -10});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreateTwo.iterator(), fieldsTwo, new Object[][] {{"E1", -10}});
        assertFalse(listenerWindow.isInvoked());

        sendSupportBean("E2", 5);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fieldsOne, new Object[] {"E2", 5});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreateOne.iterator(), fieldsOne, new Object[][] {{"E2", 5}});
        assertFalse(listenerWindowTwo.isInvoked());

        sendSupportBean("E3", -1);
        ArrayAssertionUtil.assertProps(listenerWindowTwo.assertOneGetNewAndReset(), fieldsTwo, new Object[] {"E3", -1});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreateTwo.iterator(), fieldsTwo, new Object[][] {{"E1", -10}, {"E3", -1}});
        assertFalse(listenerWindow.isInvoked());

        sendSupportBean("E3", 1);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fieldsOne, new Object[] {"E3", 1});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreateOne.iterator(), fieldsOne, new Object[][] {{"E2", 5}, {"E3", 1}});
        ArrayAssertionUtil.assertProps(listenerWindowTwo.assertOneGetOldAndReset(), fieldsTwo, new Object[] {"E3", -1});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreateTwo.iterator(), fieldsTwo, new Object[][] {{"E1", -10}});

        stmtDelete.destroy();
        stmtCreateOne.destroy();
        stmtCreateTwo.destroy();
    }

    public void testDeletePattern()
    {
        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select string as a, intPrimitive as b from " + SupportBean.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEQL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        // create delete stmt
        String stmtTextDelete = "on pattern [every ea=" + SupportBean_A.class.getName() + " or every eb=" + SupportBean_B.class.getName() + "] " + " delete from MyWindow";
        EPStatement stmtDelete = epService.getEPAdministrator().createEQL(stmtTextDelete);
        stmtDelete.addListener(listenerDelete);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string as a, intPrimitive as b from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEQL(stmtTextInsertOne);

        // send 1 event
        String[] fields = new String[] {"a", "b"};
        sendSupportBean("E1", 1);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtDelete.iterator(), fields, null);

        // Delete all events using A, 1 row expected
        sendSupportBean_A("A1");
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E1", 1});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, null);
        ArrayAssertionUtil.assertProps(listenerDelete.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtDelete.iterator(), fields, new Object[][] {{"E1", 1}});

        // send 1 event
        sendSupportBean("E2", 2);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E2", 2});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E2", 2}});

        // Delete all events using B, 1 row expected
        sendSupportBean_B("B1");
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E2", 2});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, null);
        ArrayAssertionUtil.assertProps(listenerDelete.assertOneGetNewAndReset(), fields, new Object[] {"E2", 2});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtDelete.iterator(), fields, new Object[][] {{"E2", 2}});

        stmtDelete.destroy();
        stmtCreate.destroy();        
    }

    public void testDeleteAll()
    {
        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select string as a, intPrimitive as b from " + SupportBean.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEQL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        // create delete stmt
        String stmtTextDelete = "on " + SupportBean_A.class.getName() + " delete from MyWindow";
        EPStatement stmtDelete = epService.getEPAdministrator().createEQL(stmtTextDelete);
        stmtDelete.addListener(listenerDelete);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmtDelete.getEventType().getPropertyNames(), new String[] {"a", "b"});

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string as a, intPrimitive as b from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEQL(stmtTextInsertOne);

        // create consumer
        String[] fields = new String[] {"a", "b"};
        String stmtTextSelect = "select a, b from MyWindow as s1";
        EPStatement stmtSelect = epService.getEPAdministrator().createEQL(stmtTextSelect);
        stmtSelect.addListener(listenerSelect);

        // Delete all events, no result expected
        sendSupportBean_A("A1");
        assertFalse(listenerWindow.isInvoked());
        assertFalse(listenerSelect.isInvoked());
        assertFalse(listenerDelete.isInvoked());

        // send 1 event
        sendSupportBean("E1", 1);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1});
        ArrayAssertionUtil.assertProps(listenerSelect.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtDelete.iterator(), fields, null);

        // Delete all events, 1 row expected
        sendSupportBean_A("A2");
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E1", 1});
        ArrayAssertionUtil.assertProps(listenerSelect.assertOneGetOldAndReset(), fields, new Object[] {"E1", 1});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, null);
        ArrayAssertionUtil.assertProps(listenerDelete.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtDelete.iterator(), fields, new Object[][] {{"E1", 1}});

        // send 2 events
        sendSupportBean("E2", 2);
        sendSupportBean("E3", 3);
        listenerWindow.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E2", 2}, {"E3", 3}});
        assertFalse(listenerDelete.isInvoked());

        // Delete all events, 2 rows expected
        sendSupportBean_A("A2");
        ArrayAssertionUtil.assertProps(listenerWindow.getLastOldData()[0], fields, new Object[] {"E2", 2});
        ArrayAssertionUtil.assertProps(listenerWindow.getLastOldData()[1], fields, new Object[] {"E3", 3});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, null);
        assertEquals(2, listenerDelete.getLastNewData().length);
        ArrayAssertionUtil.assertProps(listenerDelete.getLastNewData()[0], fields, new Object[] {"E2", 2});
        ArrayAssertionUtil.assertProps(listenerDelete.getLastNewData()[1], fields, new Object[] {"E3", 3});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtDelete.iterator(), fields, new Object[][] {{"E2", 2}, {"E3", 3}});
    }

    public void testDeleteCondition()
    {
        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select string as a, intPrimitive as b from " + SupportBean.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEQL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        // create delete stmt
        String stmtTextDelete = "on " + SupportBean_A.class.getName() + " delete from MyWindow where 'X' || a || 'X' = id";
        epService.getEPAdministrator().createEQL(stmtTextDelete);

        // create delete stmt
        stmtTextDelete = "on " + SupportBean_B.class.getName() + " delete from MyWindow where b < 5";
        epService.getEPAdministrator().createEQL(stmtTextDelete);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string as a, intPrimitive as b from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEQL(stmtTextInsertOne);

        // send 3 event
        sendSupportBean("E1", 1);
        sendSupportBean("E2", 2);
        sendSupportBean("E3", 3);
        listenerWindow.reset();
        String[] fields = new String[] {"a", "b"};
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1}, {"E2", 2}, {"E3", 3}});

        // delete E2
        sendSupportBean_A("XE2X");
        assertEquals(1, listenerWindow.getLastOldData().length);
        ArrayAssertionUtil.assertProps(listenerWindow.getLastOldData()[0], fields, new Object[] {"E2", 2});
        listenerWindow.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1}, {"E3", 3}});

        sendSupportBean("E7", 7);
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1}, {"E3", 3}, {"E7", 7}});

        // delete all under 5
        sendSupportBean_B("B1");
        assertEquals(2, listenerWindow.getLastOldData().length);
        ArrayAssertionUtil.assertProps(listenerWindow.getLastOldData()[0], fields, new Object[] {"E1", 1});
        ArrayAssertionUtil.assertProps(listenerWindow.getLastOldData()[1], fields, new Object[] {"E3", 3});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E7", 7}});
    }

    public void testDeleteMultiPropIndexes()
    {
        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select " +
                                "string, intPrimitive, intBoxed, doublePrimitive, doubleBoxed from " + SupportBean.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEQL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        List<EPStatement> deleteStatements = new LinkedList<EPStatement>();
        String stmtTextDelete = "on " + SupportBean.class.getName() + "(string='DB') as s0 delete from MyWindow as win where win.intPrimitive = s0.doubleBoxed";
        deleteStatements.add(epService.getEPAdministrator().createEQL(stmtTextDelete));
        
        stmtTextDelete = "on " + SupportBean.class.getName() + "(string='DP') as s0 delete from MyWindow as win where win.intPrimitive = s0.doublePrimitive";
        deleteStatements.add(epService.getEPAdministrator().createEQL(stmtTextDelete));

        stmtTextDelete = "on " + SupportBean.class.getName() + "(string='IB') as s0 delete from MyWindow as win where win.intPrimitive = s0.intBoxed";
        deleteStatements.add(epService.getEPAdministrator().createEQL(stmtTextDelete));

        stmtTextDelete = "on " + SupportBean.class.getName() + "(string='IPDP') as s0 delete from MyWindow as win where win.intPrimitive = s0.intPrimitive and win.doublePrimitive = s0.doublePrimitive";
        deleteStatements.add(epService.getEPAdministrator().createEQL(stmtTextDelete));

        stmtTextDelete = "on " + SupportBean.class.getName() + "(string='IPDP2') as s0 delete from MyWindow as win where win.doublePrimitive = s0.doublePrimitive and win.intPrimitive = s0.intPrimitive";
        deleteStatements.add(epService.getEPAdministrator().createEQL(stmtTextDelete));

        stmtTextDelete = "on " + SupportBean.class.getName() + "(string='IPDPIB') as s0 delete from MyWindow as win where win.doublePrimitive = s0.doublePrimitive and win.intPrimitive = s0.intPrimitive and win.intBoxed = s0.intBoxed";
        deleteStatements.add(epService.getEPAdministrator().createEQL(stmtTextDelete));

        stmtTextDelete = "on " + SupportBean.class.getName() + "(string='CAST') as s0 delete from MyWindow as win where win.intBoxed = s0.intPrimitive and win.doublePrimitive = s0.doubleBoxed and win.intPrimitive = s0.intBoxed";
        deleteStatements.add(epService.getEPAdministrator().createEQL(stmtTextDelete));

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string, intPrimitive, intBoxed, doublePrimitive, doubleBoxed "
                                    + "from " + SupportBean.class.getName() + "(string like 'E%')";
        epService.getEPAdministrator().createEQL(stmtTextInsertOne);

        sendSupportBean("E1", 1, 10, 100d, 1000d);
        sendSupportBean("E2", 2, 20, 200d, 2000d);
        sendSupportBean("E3", 3, 30, 300d, 3000d);
        sendSupportBean("E4", 4, 40, 400d, 4000d);
        listenerWindow.reset();

        String[] fields = new String[] {"string"};
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1"}, {"E2"}, {"E3"}, {"E4"}});

        sendSupportBean("DB", 0, 0, 0d, null);
        assertFalse(listenerWindow.isInvoked());
        sendSupportBean("DB", 0, 0, 0d, 3d);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E3"});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1"}, {"E2"}, {"E4"}});

        sendSupportBean("DP", 0, 0, 5d, null);
        assertFalse(listenerWindow.isInvoked());
        sendSupportBean("DP", 0, 0, 4d, null);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E4"});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1"}, {"E2"}});

        sendSupportBean("IB", 0, -1, 0d, null);
        assertFalse(listenerWindow.isInvoked());
        sendSupportBean("IB", 0, 1, 0d, null);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E1"});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E2"}});

        sendSupportBean("E5", 5, 50, 500d, 5000d);
        sendSupportBean("E6", 6, 60, 600d, 6000d);
        sendSupportBean("E7", 7, 70, 700d, 7000d);
        listenerWindow.reset();

        sendSupportBean("IPDP", 5, 0, 500d, null);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E5"});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E2"}, {"E6"}, {"E7"}});

        sendSupportBean("IPDP2", 6, 0, 600d, null);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E6"});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E2"}, {"E7"}});

        sendSupportBean("IPDPIB", 7, 70, 0d, null);
        assertFalse(listenerWindow.isInvoked());
        sendSupportBean("IPDPIB", 7, 70, 700d, null);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E7"});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E2"}});

        sendSupportBean("E8", 8, 80, 800d, 8000d);
        listenerWindow.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E2"}, {"E8"}});

        sendSupportBean("CAST", 80, 8, 0, 800d);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E8"});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E2"}});

        for (EPStatement stmt : deleteStatements)
        {
            stmt.destroy();
        }
        deleteStatements.clear();

        // late delete on a filled window
        stmtTextDelete = "on " + SupportBean.class.getName() + "(string='LAST') as s0 delete from MyWindow as win where win.intPrimitive = s0.intPrimitive and win.doublePrimitive = s0.doublePrimitive";
        deleteStatements.add(epService.getEPAdministrator().createEQL(stmtTextDelete));
        sendSupportBean("LAST", 2, 20, 200, 2000d);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E2"});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, null);

        for (EPStatement stmt : deleteStatements)
        {
            stmt.destroy();
        }
    }

    private SupportBean_A sendSupportBean_A(String id)
    {
        SupportBean_A bean = new SupportBean_A(id);
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }

    private SupportBean_B sendSupportBean_B(String id)
    {
        SupportBean_B bean = new SupportBean_B(id);
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }

    private SupportBean sendSupportBean(String string, int intPrimitive, Integer intBoxed,
                                        double doublePrimitive, Double doubleBoxed)
    {
        SupportBean bean = new SupportBean();
        bean.setString(string);
        bean.setIntPrimitive(intPrimitive);
        bean.setIntBoxed(intBoxed);
        bean.setDoublePrimitive(doublePrimitive);
        bean.setDoubleBoxed(doubleBoxed);
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }

    private SupportBean sendSupportBean(String string, int intPrimitive)
    {
        SupportBean bean = new SupportBean();
        bean.setString(string);
        bean.setIntPrimitive(intPrimitive);
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }
}
