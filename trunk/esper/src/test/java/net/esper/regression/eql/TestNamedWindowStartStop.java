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
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.util.ArrayAssertionUtil;

public class TestNamedWindowStartStop extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listenerWindow;
    private SupportUpdateListener listenerSelect;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);

        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listenerWindow = new SupportUpdateListener();
        listenerSelect = new SupportUpdateListener();
    }

    public void testStartStopDeleter()
    {
        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select string as a, intPrimitive as b from " + SupportBean.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEQL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        // stop and start, no consumers or deleters
        stmtCreate.stop();
        stmtCreate.start();

        // create delete stmt
        String stmtTextDelete = "on " + SupportBean_A.class.getName() + " delete from MyWindow";
        EPStatement stmtDelete = epService.getEPAdministrator().createEQL(stmtTextDelete);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string as a, intPrimitive as b from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEQL(stmtTextInsertOne);

        // create consumer
        String[] fields = new String[] {"a", "b"};
        String stmtTextSelect = "select a, b from MyWindow as s1";
        EPStatement stmtSelect = epService.getEPAdministrator().createEQL(stmtTextSelect);
        stmtSelect.addListener(listenerSelect);

        // send 1 event
        sendSupportBean("E1", 1);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1});
        ArrayAssertionUtil.assertProps(listenerSelect.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1}});

        // Delete all events, 1 row expected
        sendSupportBean_A("A2");
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E1", 1});
        ArrayAssertionUtil.assertProps(listenerSelect.assertOneGetOldAndReset(), fields, new Object[] {"E1", 1});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, null);

        sendSupportBean("E2", 2);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E2", 2});
        ArrayAssertionUtil.assertProps(listenerSelect.assertOneGetNewAndReset(), fields, new Object[] {"E2", 2});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E2", 2}});

        // Stop the deleting statement
        stmtDelete.stop();
        sendSupportBean_A("A2");
        assertFalse(listenerWindow.isInvoked());

        // Start the deleting statement
        stmtDelete.start();
        sendSupportBean_A("A3");
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E2", 2});
        ArrayAssertionUtil.assertProps(listenerSelect.assertOneGetOldAndReset(), fields, new Object[] {"E2", 2});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, null);

        sendSupportBean("E3", 3);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E3", 3});
        ArrayAssertionUtil.assertProps(listenerSelect.assertOneGetNewAndReset(), fields, new Object[] {"E3", 3});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E3", 3}});

        stmtDelete.destroy();
        sendSupportBean_A("A3");
        assertFalse(listenerWindow.isInvoked());
    }

    public void testStartStopConsumer()
    {
        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select string as a, intPrimitive as b from " + SupportBean.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEQL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string as a, intPrimitive as b from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEQL(stmtTextInsertOne);

        // create consumer
        String[] fields = new String[] {"a", "b"};
        String stmtTextSelect = "select a, b from MyWindow as s1";
        EPStatement stmtSelect = epService.getEPAdministrator().createEQL(stmtTextSelect);
        stmtSelect.addListener(listenerSelect);

        // send 1 event
        sendSupportBean("E1", 1);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1});
        ArrayAssertionUtil.assertProps(listenerSelect.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1}});

        // stop consumer
        stmtSelect.stop();
        sendSupportBean("E2", 2);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E2", 2});
        assertFalse(listenerSelect.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1}, {"E2", 2}});

        // start consumer: the consumer has the last event even though he missed it
        stmtSelect.start();
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fields, new Object[][] {{"E2", 2}});

        // consumer receives the next event
        sendSupportBean("E3", 3);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E3", 3});
        ArrayAssertionUtil.assertProps(listenerSelect.assertOneGetNewAndReset(), fields, new Object[] {"E3", 3});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1}, {"E2", 2}, {"E3", 3}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fields, new Object[][] {{"E3", 3}});

        // destroy consumer
        stmtSelect.destroy();
        sendSupportBean("E4", 4);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E4", 4});
        assertFalse(listenerSelect.isInvoked());
    }

    public void testStartStopInserter()
    {
        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select string as a, intPrimitive as b from " + SupportBean.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEQL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string as a, intPrimitive as b from " + SupportBean.class.getName();
        EPStatement stmtInsert = epService.getEPAdministrator().createEQL(stmtTextInsertOne);

        // create consumer
        String[] fields = new String[] {"a", "b"};
        String stmtTextSelect = "select a, b from MyWindow as s1";
        EPStatement stmtSelect = epService.getEPAdministrator().createEQL(stmtTextSelect);
        stmtSelect.addListener(listenerSelect);

        // send 1 event
        sendSupportBean("E1", 1);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1});
        ArrayAssertionUtil.assertProps(listenerSelect.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1}});

        // stop inserter
        stmtInsert.stop();
        sendSupportBean("E2", 2);
        assertFalse(listenerWindow.isInvoked());
        assertFalse(listenerSelect.isInvoked());

        // start inserter
        stmtInsert.start();

        // consumer receives the next event
        sendSupportBean("E3", 3);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E3", 3});
        ArrayAssertionUtil.assertProps(listenerSelect.assertOneGetNewAndReset(), fields, new Object[] {"E3", 3});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1}, {"E3", 3}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fields, new Object[][] {{"E3", 3}});

        // destroy inserter
        stmtInsert.destroy();
        sendSupportBean("E4", 4);
        assertFalse(listenerWindow.isInvoked());
        assertFalse(listenerSelect.isInvoked());
    }

    public void testStartStopCreator()
    {
        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select string as a, intPrimitive as b from " + SupportBean.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEQL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        // create delete stmt
        String stmtTextDelete = "on " + SupportBean_A.class.getName() + " delete from MyWindow";
        EPStatement stmtDelete = epService.getEPAdministrator().createEQL(stmtTextDelete);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string as a, intPrimitive as b from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEQL(stmtTextInsertOne);

        // create consumer
        String[] fields = new String[] {"a", "b"};
        String stmtTextSelect = "select a, b from MyWindow as s1";
        EPStatement stmtSelect = epService.getEPAdministrator().createEQL(stmtTextSelect);
        stmtSelect.addListener(listenerSelect);

        // send 1 event
        sendSupportBean("E1", 1);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1});
        ArrayAssertionUtil.assertProps(listenerSelect.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fields, new Object[][] {{"E1", 1}});

        // stop creator
        stmtCreate.stop();
        sendSupportBean("E2", 2);
        assertFalse(listenerSelect.isInvoked());
        assertFalse(listenerWindow.isInvoked());
        assertNull(stmtCreate.iterator());
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fields, new Object[][] {{"E1", 1}});

        // start creator
        stmtCreate.start();
        sendSupportBean("E3", 3);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E3", 3});
        assertFalse(listenerSelect.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E3", 3}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fields, new Object[][] {{"E1", 1}});

        // stop and start consumer: should pick up last event
        stmtSelect.stop();
        stmtSelect.start();
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fields, new Object[][] {{"E3", 3}});

        sendSupportBean("E4", 4);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E4", 4});
        ArrayAssertionUtil.assertProps(listenerSelect.assertOneGetNewAndReset(), fields, new Object[] {"E4", 4});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E3", 3}, {"E4", 4}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fields, new Object[][] {{"E4", 4}});

        // destroy creator
        stmtCreate.destroy();
        sendSupportBean("E5", 5);
        assertFalse(listenerSelect.isInvoked());
        assertFalse(listenerWindow.isInvoked());
        assertNull(stmtCreate.iterator());
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fields, new Object[][] {{"E4", 4}});

        // create window anew
        stmtTextCreate = "create window MyWindow.win:keepall() as select string as a, intPrimitive as b from " + SupportBean.class.getName();
        stmtCreate = epService.getEPAdministrator().createEQL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        sendSupportBean("E6", 6);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E6", 6});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E6", 6}});
        assertFalse(listenerSelect.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fields, new Object[][] {{"E4", 4}});
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
