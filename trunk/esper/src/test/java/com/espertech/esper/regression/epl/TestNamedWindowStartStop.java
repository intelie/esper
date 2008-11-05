package com.espertech.esper.regression.epl;

import com.espertech.esper.client.*;
import com.espertech.esper.core.EPServiceProviderSPI;
import com.espertech.esper.core.EPStatementSPI;
import com.espertech.esper.core.StatementType;
import com.espertech.esper.epl.named.NamedWindowLifecycleEvent;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_A;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.epl.SupportNamedWindowObserver;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.Set;

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
        SupportNamedWindowObserver observer = new SupportNamedWindowObserver();
        ((EPServiceProviderSPI) epService).getNamedWindowService().addObserver(observer);

        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select string as a, intPrimitive as b from " + SupportBean.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL(stmtTextCreate);
        assertEquals(StatementType.CREATE_WINDOW, ((EPStatementSPI) stmtCreate).getStatementMetadata().getStatementType());
        stmtCreate.addListener(listenerWindow);
        NamedWindowLifecycleEvent event = observer.getFirstAndReset();
        assertEquals(NamedWindowLifecycleEvent.LifecycleEventType.CREATE, event.getEventType());
        assertEquals("MyWindow", event.getName());

        // stop and start, no consumers or deleters
        stmtCreate.stop();
        event = observer.getFirstAndReset();
        assertEquals(NamedWindowLifecycleEvent.LifecycleEventType.DESTROY, event.getEventType());
        assertEquals("MyWindow", event.getName());

        stmtCreate.start();
        assertEquals(NamedWindowLifecycleEvent.LifecycleEventType.CREATE, observer.getFirstAndReset().getEventType());

        // create delete stmt
        String stmtTextDelete = "on " + SupportBean_A.class.getName() + " delete from MyWindow";
        EPStatement stmtDelete = epService.getEPAdministrator().createEPL(stmtTextDelete);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string as a, intPrimitive as b from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEPL(stmtTextInsertOne);

        // create consumer
        String[] fields = new String[] {"a", "b"};
        String stmtTextSelect = "select irstream a, b from MyWindow as s1";
        EPStatement stmtSelect = epService.getEPAdministrator().createEPL(stmtTextSelect);
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
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string as a, intPrimitive as b from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEPL(stmtTextInsertOne);

        // create consumer
        String[] fields = new String[] {"a", "b"};
        String stmtTextSelect = "select a, b from MyWindow as s1";
        EPStatement stmtSelect = epService.getEPAdministrator().createEPL(stmtTextSelect);
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
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fields, new Object[][] {{"E1", 1}, {"E2", 2}});

        // consumer receives the next event
        sendSupportBean("E3", 3);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E3", 3});
        ArrayAssertionUtil.assertProps(listenerSelect.assertOneGetNewAndReset(), fields, new Object[] {"E3", 3});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1}, {"E2", 2}, {"E3", 3}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fields, new Object[][] {{"E1", 1}, {"E2", 2}, {"E3", 3}});

        // destroy consumer
        stmtSelect.destroy();
        sendSupportBean("E4", 4);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E4", 4});
        assertFalse(listenerSelect.isInvoked());
    }

    public void testAddRemoveType()
    {
        ConfigurationOperations configOps = epService.getEPAdministrator().getConfiguration();

        // test remove type with statement used (no force)
        EPStatement stmt = epService.getEPAdministrator().createEPL("create window MyWindowEventType.win:keepall() (a int, b string)", "stmtOne");
        ArrayAssertionUtil.assertEqualsExactOrder(new String[] {"stmtOne"}, configOps.getEventTypeAliasUsedBy("MyWindowEventType").toArray());

        try {
            configOps.removeEventType("MyWindowEventType", false);
        }
        catch (ConfigurationException ex) {
            assertTrue(ex.getMessage().contains("MyWindowEventType"));
        }

        // destroy statement and type
        stmt.destroy();
        assertTrue(configOps.getEventTypeAliasUsedBy("MyWindowEventType").isEmpty());
        assertTrue(configOps.isEventTypeAliasExists("MyWindowEventType"));
        assertTrue(configOps.removeEventType("MyWindowEventType", false));
        assertFalse(configOps.removeEventType("MyWindowEventType", false));    // try double-remove
        assertFalse(configOps.isEventTypeAliasExists("MyWindowEventType"));
        try {
            epService.getEPAdministrator().createEPL("select a from MyWindowEventType");
            fail();
        }
        catch (EPException ex) {
            // expected
        }

        // add back the type
        stmt = epService.getEPAdministrator().createEPL("create window MyWindowEventType.win:keepall() (c int, d string)", "stmtOne");
        assertTrue(configOps.isEventTypeAliasExists("MyWindowEventType"));
        assertFalse(configOps.getEventTypeAliasUsedBy("MyWindowEventType").isEmpty());

        // compile
        epService.getEPAdministrator().createEPL("select d from MyWindowEventType", "stmtTwo");
        Object[] usedBy = configOps.getEventTypeAliasUsedBy("MyWindowEventType").toArray();
        ArrayAssertionUtil.assertEqualsAnyOrder(new String[] {"stmtOne", "stmtTwo"}, usedBy);
        try {
            epService.getEPAdministrator().createEPL("select a from MyWindowEventType");
            fail();
        }
        catch (EPException ex) {
            // expected
        }

        // remove with force
        try {
            configOps.removeEventType("MyWindowEventType", false);
        }
        catch (ConfigurationException ex) {
            assertTrue(ex.getMessage().contains("MyWindowEventType"));
        }
        assertTrue(configOps.removeEventType("MyWindowEventType", true));
        assertFalse(configOps.isEventTypeAliasExists("MyWindowEventType"));
        assertTrue(configOps.getEventTypeAliasUsedBy("MyWindowEventType").isEmpty());

        // add back the type
        stmt.destroy();
        stmt = epService.getEPAdministrator().createEPL("create window MyWindowEventType.win:keepall() (f int)", "stmtOne");
        assertTrue(configOps.isEventTypeAliasExists("MyWindowEventType"));

        // compile
        epService.getEPAdministrator().createEPL("select f from MyWindowEventType");
        try {
            epService.getEPAdministrator().createEPL("select c from MyWindowEventType");
            fail();
        }
        catch (EPException ex) {
            // expected
        }
    }

    public void testStartStopInserter()
    {
        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select string as a, intPrimitive as b from " + SupportBean.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string as a, intPrimitive as b from " + SupportBean.class.getName();
        EPStatement stmtInsert = epService.getEPAdministrator().createEPL(stmtTextInsertOne);

        // create consumer
        String[] fields = new String[] {"a", "b"};
        String stmtTextSelect = "select a, b from MyWindow as s1";
        EPStatement stmtSelect = epService.getEPAdministrator().createEPL(stmtTextSelect);
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
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fields, new Object[][] {{"E1", 1}, {"E3", 3}});

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
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL(stmtTextCreate, "stmtCreateFirst");
        stmtCreate.addListener(listenerWindow);

        // create delete stmt
        String stmtTextDelete = "on " + SupportBean_A.class.getName() + " delete from MyWindow";
        EPStatement stmtDelete = epService.getEPAdministrator().createEPL(stmtTextDelete, "stmtDelete");

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string as a, intPrimitive as b from " + SupportBean.class.getName();
        EPStatement stmtInsert = epService.getEPAdministrator().createEPL(stmtTextInsertOne, "stmtInsert");

        // create consumer
        String[] fields = new String[] {"a", "b"};
        String stmtTextSelect = "select a, b from MyWindow as s1";
        EPStatement stmtSelect = epService.getEPAdministrator().createEPL(stmtTextSelect, "stmtSelect");
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
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fields, new Object[][] {{"E3", 3}, {"E4", 4}});

        // destroy creator
        stmtCreate.destroy();
        sendSupportBean("E5", 5);
        assertFalse(listenerSelect.isInvoked());
        assertFalse(listenerWindow.isInvoked());
        assertNull(stmtCreate.iterator());
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fields, new Object[][] {{"E3", 3}, {"E4", 4}});

        // create window anew
        stmtTextCreate = "create window MyWindow.win:keepall() as select string as a, intPrimitive as b from " + SupportBean.class.getName();
        stmtCreate = epService.getEPAdministrator().createEPL(stmtTextCreate, "stmtCreate");
        stmtCreate.addListener(listenerWindow);

        sendSupportBean("E6", 6);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E6", 6});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E6", 6}});
        assertFalse(listenerSelect.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fields, new Object[][] {{"E3", 3}, {"E4", 4}});

        // create select stmt
        String stmtTextOnSelect = "on " + SupportBean_A.class.getName() + " insert into A select * from MyWindow";
        EPStatement stmtOnSelect = epService.getEPAdministrator().createEPL(stmtTextOnSelect, "stmtOnSelect");

        // assert statement-type reference
        EPServiceProviderSPI spi = (EPServiceProviderSPI) epService;

        assertTrue(spi.getStatementEventTypeRef().isInUse("MyWindow"));
        Set<String> stmtNames = spi.getStatementEventTypeRef().getStatementNamesForType("MyWindow");
        ArrayAssertionUtil.assertEqualsAnyOrder(new String[] {"stmtCreate", "stmtSelect", "stmtInsert", "stmtDelete", "stmtOnSelect"},stmtNames.toArray());

        assertTrue(spi.getStatementEventTypeRef().isInUse(SupportBean.class.getName()));
        stmtNames = spi.getStatementEventTypeRef().getStatementNamesForType(SupportBean.class.getName());
        ArrayAssertionUtil.assertEqualsAnyOrder(new String[] {"stmtCreate", "stmtInsert"}, stmtNames.toArray());

        assertTrue(spi.getStatementEventTypeRef().isInUse(SupportBean_A.class.getName()));
        stmtNames = spi.getStatementEventTypeRef().getStatementNamesForType(SupportBean_A.class.getName());
        ArrayAssertionUtil.assertEqualsAnyOrder(new String[] {"stmtDelete", "stmtOnSelect"}, stmtNames.toArray());

        stmtInsert.destroy();
        stmtDelete.destroy();

        assertTrue(spi.getStatementEventTypeRef().isInUse("MyWindow"));
        stmtNames = spi.getStatementEventTypeRef().getStatementNamesForType("MyWindow");
        ArrayAssertionUtil.assertEqualsAnyOrder(new String[] {"stmtCreate", "stmtSelect", "stmtOnSelect"},stmtNames.toArray());

        assertTrue(spi.getStatementEventTypeRef().isInUse(SupportBean.class.getName()));
        stmtNames = spi.getStatementEventTypeRef().getStatementNamesForType(SupportBean.class.getName());
        ArrayAssertionUtil.assertEqualsAnyOrder(new String[] {"stmtCreate"}, stmtNames.toArray());

        assertTrue(spi.getStatementEventTypeRef().isInUse(SupportBean_A.class.getName()));
        stmtNames = spi.getStatementEventTypeRef().getStatementNamesForType(SupportBean_A.class.getName());
        ArrayAssertionUtil.assertEqualsAnyOrder(new String[] {"stmtOnSelect"}, stmtNames.toArray());

        stmtCreate.destroy();

        assertTrue(spi.getStatementEventTypeRef().isInUse("MyWindow"));
        stmtNames = spi.getStatementEventTypeRef().getStatementNamesForType("MyWindow");
        ArrayAssertionUtil.assertEqualsAnyOrder(new String[] {"stmtSelect", "stmtOnSelect"},stmtNames.toArray());

        assertFalse(spi.getStatementEventTypeRef().isInUse(SupportBean.class.getName()));

        assertTrue(spi.getStatementEventTypeRef().isInUse(SupportBean_A.class.getName()));
        stmtNames = spi.getStatementEventTypeRef().getStatementNamesForType(SupportBean_A.class.getName());
        ArrayAssertionUtil.assertEqualsAnyOrder(new String[] {"stmtOnSelect"}, stmtNames.toArray());

        stmtOnSelect.destroy();
        stmtSelect.destroy();

        assertFalse(spi.getStatementEventTypeRef().isInUse("MyWindow"));
        assertFalse(spi.getStatementEventTypeRef().isInUse(SupportBean.class.getName()));
        assertFalse(spi.getStatementEventTypeRef().isInUse(SupportBean_A.class.getName()));
    }

    private SupportBean_A sendSupportBean_A(String id)
    {
        SupportBean_A bean = new SupportBean_A(id);
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
