package net.esper.regression.eql;

import junit.framework.TestCase;
import net.esper.client.*;
import net.esper.event.EventType;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBean_A;
import net.esper.support.bean.SupportBean_B;
import net.esper.support.client.SupportConfigFactory;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.support.util.SupportUpdateListener;

public class TestNamedWindowSelect extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listenerSelect;
    private SupportUpdateListener listenerSelectTwo;
    private SupportUpdateListener listenerConsumer;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);

        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listenerSelect = new SupportUpdateListener();
        listenerSelectTwo = new SupportUpdateListener();
        listenerConsumer = new SupportUpdateListener();
    }

    public void testInsertIntoWildcard()
    {
        String[] fields = new String[] {"string", "intPrimitive"};

        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select * from " + SupportBean.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEQL(stmtTextCreate);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select * from " + SupportBean.class.getName() + "(string like 'E%')";
        epService.getEPAdministrator().createEQL(stmtTextInsertOne);

        // create on-select stmt
        String stmtTextSelect = "on " + SupportBean_A.class.getName() + " insert into MyStream select mywin.* from MyWindow as mywin order by string asc";
        EPStatement stmtSelect = epService.getEPAdministrator().createEQL(stmtTextSelect);
        stmtSelect.addListener(listenerSelect);

        // create consuming statement
        String stmtTextConsumer = "select * from MyStream";
        EPStatement stmtConsumer = epService.getEPAdministrator().createEQL(stmtTextConsumer);
        stmtConsumer.addListener(listenerConsumer);

        // create second inserting statement
        String stmtTextInsertTwo = "insert into MyStream select * from " + SupportBean.class.getName() + "(string like 'I%')";
        epService.getEPAdministrator().createEQL(stmtTextInsertTwo);

        // send event
        sendSupportBean("E1", 1);
        assertFalse(listenerSelect.isInvoked());
        assertFalse(listenerConsumer.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1}});

        // fire trigger
        sendSupportBean_A("A1");
        ArrayAssertionUtil.assertProps(listenerSelect.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1});
        ArrayAssertionUtil.assertProps(listenerConsumer.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1});

        // insert via 2nd insert into
        sendSupportBean("I2", 2);
        assertFalse(listenerSelect.isInvoked());
        ArrayAssertionUtil.assertProps(listenerConsumer.assertOneGetNewAndReset(), fields, new Object[] {"I2", 2});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1}});

        // send event
        sendSupportBean("E3", 3);
        assertFalse(listenerSelect.isInvoked());
        assertFalse(listenerConsumer.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1}, {"E3", 3}});

        // fire trigger
        sendSupportBean_A("A2");
        assertEquals(1, listenerSelect.getNewDataList().size());
        ArrayAssertionUtil.assertPropsPerRow(listenerSelect.getLastNewData(), fields, new Object[][] {{"E1", 1}, {"E3", 3}});
        listenerSelect.reset();
        assertEquals(2, listenerConsumer.getNewDataList().size());
        ArrayAssertionUtil.assertPropsPerRow(listenerConsumer.getNewDataListFlattened(), fields, new Object[][] {{"E1", 1}, {"E3", 3}});
        listenerConsumer.reset();

        // check type
        EventType consumerType = stmtConsumer.getEventType();
        assertEquals(String.class, consumerType.getPropertyType("string"));
        assertTrue(consumerType.getPropertyNames().length > 10);
        assertEquals(SupportBean.class, consumerType.getUnderlyingType());

        // check type
        EventType onSelectType = stmtSelect.getEventType();
        assertEquals(String.class, onSelectType.getPropertyType("string"));
        assertTrue(onSelectType.getPropertyNames().length > 10);
        assertEquals(SupportBean.class, onSelectType.getUnderlyingType());

        stmtConsumer.destroy();
        stmtSelect.destroy();
        stmtCreate.destroy();
    }

    public void testInvalid()
    {
        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select * from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEQL(stmtTextCreate);

        tryInvalid("on " + SupportBean_A.class.getName() + " insert into MyStream select * from DUMMY",
                   "Named window 'DUMMY' has not been declared [on net.esper.support.bean.SupportBean_A insert into MyStream select * from DUMMY]");

        tryInvalid("on " + SupportBean_A.class.getName() + " select prev(1, string) from MyWindow",
                   "Error starting view: Previous function cannot be used in this context [on net.esper.support.bean.SupportBean_A select prev(1, string) from MyWindow]");
    }

    private void tryInvalid(String text, String message)
    {
        try
        {
            epService.getEPAdministrator().createEQL(text);
            fail();
        }
        catch (EPStatementException ex)
        {
            assertEquals(message, ex.getMessage());
        }
    }

    public void testSelectCondition()
    {
        String[] fieldsCreate = new String[] {"a", "b"};
        String[] fieldsOnSelect = new String[] {"a", "b", "id"};

        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select string as a, intPrimitive as b from " + SupportBean.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEQL(stmtTextCreate);

        // create select stmt
        String stmtTextSelect = "on " + SupportBean_A.class.getName() + " select mywin.*, id from MyWindow as mywin where b < 3 order by a asc";
        EPStatement stmtSelect = epService.getEPAdministrator().createEQL(stmtTextSelect);
        stmtSelect.addListener(listenerSelect);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string as a, intPrimitive as b from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEQL(stmtTextInsertOne);

        // send 3 event
        sendSupportBean("E1", 1);
        sendSupportBean("E2", 2);
        sendSupportBean("E3", 3);
        assertFalse(listenerSelect.isInvoked());

        // fire trigger
        sendSupportBean_A("A1");
        assertEquals(2, listenerSelect.getLastNewData().length);
        ArrayAssertionUtil.assertProps(listenerSelect.getLastNewData()[0], fieldsCreate, new Object[] {"E1", 1});
        ArrayAssertionUtil.assertProps(listenerSelect.getLastNewData()[1], fieldsCreate, new Object[] {"E2", 2});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fieldsCreate, new Object[][] {{"E1", 1}, {"E2", 2}, {"E3", 3}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fieldsOnSelect, new Object[][] {{"E1", 1, "A1"}, {"E2", 2, "A1"}});

        sendSupportBean("E4", 0);
        sendSupportBean_A("A2");
        assertEquals(3, listenerSelect.getLastNewData().length);
        ArrayAssertionUtil.assertProps(listenerSelect.getLastNewData()[0], fieldsOnSelect, new Object[] {"E1", 1, "A2"});
        ArrayAssertionUtil.assertProps(listenerSelect.getLastNewData()[1], fieldsOnSelect, new Object[] {"E2", 2, "A2"});
        ArrayAssertionUtil.assertProps(listenerSelect.getLastNewData()[2], fieldsOnSelect, new Object[] {"E4", 0, "A2"});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fieldsCreate, new Object[][] {{"E1", 1}, {"E2", 2}, {"E3", 3}, {"E4", 0}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fieldsCreate, new Object[][] {{"E1", 1}, {"E2", 2}, {"E4", 0}});

        stmtSelect.destroy();
        stmtCreate.destroy();
    }

    public void testSelectJoinColumns()
    {
        String[] fields = new String[] {"triggerid", "wina", "b"};

        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select string as a, intPrimitive as b from " + SupportBean.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEQL(stmtTextCreate);

        // create select stmt
        String stmtTextSelect = "on " + SupportBean_A.class.getName() + " as trigger select trigger.id as triggerid, win.a as wina, b from MyWindow as win order by wina";
        EPStatement stmtSelect = epService.getEPAdministrator().createEQL(stmtTextSelect);
        stmtSelect.addListener(listenerSelect);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string as a, intPrimitive as b from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEQL(stmtTextInsertOne);

        // send 3 event
        sendSupportBean("E1", 1);
        sendSupportBean("E2", 2);
        assertFalse(listenerSelect.isInvoked());

        // fire trigger
        sendSupportBean_A("A1");
        assertEquals(2, listenerSelect.getLastNewData().length);
        ArrayAssertionUtil.assertProps(listenerSelect.getLastNewData()[0], fields, new Object[] {"A1", "E1", 1});
        ArrayAssertionUtil.assertProps(listenerSelect.getLastNewData()[1], fields, new Object[] {"A1", "E2", 2});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fields, new Object[][] {{"A1", "E1", 1}, {"A1", "E2", 2}});

        stmtSelect.destroy();
        stmtCreate.destroy();
    }

    public void testSelectAggregation()
    {
        String[] fields = new String[] {"sumb"};

        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select string as a, intPrimitive as b from " + SupportBean.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEQL(stmtTextCreate);

        // create select stmt
        String stmtTextSelect = "on " + SupportBean_A.class.getName() + " select sum(b) as sumb from MyWindow";
        EPStatement stmtSelect = epService.getEPAdministrator().createEQL(stmtTextSelect);
        stmtSelect.addListener(listenerSelect);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string as a, intPrimitive as b from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEQL(stmtTextInsertOne);

        // send 3 event
        sendSupportBean("E1", 1);
        sendSupportBean("E2", 2);
        sendSupportBean("E3", 3);
        assertFalse(listenerSelect.isInvoked());

        // fire trigger
        sendSupportBean_A("A1");
        ArrayAssertionUtil.assertProps(listenerSelect.assertOneGetNewAndReset(), fields, new Object[] {6});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fields, new Object[][] {{6}});

        // create delete stmt
        String stmtTextDelete = "on " + SupportBean_B.class.getName() + " delete from MyWindow where id = a";
        epService.getEPAdministrator().createEQL(stmtTextDelete);

        // Delete E2
        sendSupportBean_B("E2");

        // fire trigger
        sendSupportBean_A("A2");
        ArrayAssertionUtil.assertProps(listenerSelect.assertOneGetNewAndReset(), fields, new Object[] {4});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fields, new Object[][] {{4}});

        sendSupportBean("E4", 10);
        sendSupportBean_A("A3");
        ArrayAssertionUtil.assertProps(listenerSelect.assertOneGetNewAndReset(), fields, new Object[] {14});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fields, new Object[][] {{14}});

        EventType resultType = stmtSelect.getEventType();
        assertEquals(1, resultType.getPropertyNames().length);
        assertEquals(Integer.class, resultType.getPropertyType("sumb"));

        stmtSelect.destroy();
        stmtCreate.destroy();
    }

    public void testSelectAggregationCorrelated()
    {
        String[] fields = new String[] {"sumb"};

        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select string as a, intPrimitive as b from " + SupportBean.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEQL(stmtTextCreate);

        // create select stmt
        String stmtTextSelect = "on " + SupportBean_A.class.getName() + " select sum(b) as sumb from MyWindow where a = id";
        EPStatement stmtSelect = epService.getEPAdministrator().createEQL(stmtTextSelect);
        stmtSelect.addListener(listenerSelect);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string as a, intPrimitive as b from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEQL(stmtTextInsertOne);

        // send 3 event
        sendSupportBean("E1", 1);
        sendSupportBean("E2", 2);
        sendSupportBean("E3", 3);
        assertFalse(listenerSelect.isInvoked());

        // fire trigger
        sendSupportBean_A("A1");
        ArrayAssertionUtil.assertProps(listenerSelect.assertOneGetNewAndReset(), fields, new Object[] {null});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fields, new Object[][] {{null}});

        // fire trigger
        sendSupportBean_A("E2");
        ArrayAssertionUtil.assertProps(listenerSelect.assertOneGetNewAndReset(), fields, new Object[] {2});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fields, new Object[][] {{2}});

        sendSupportBean("E2", 10);
        sendSupportBean_A("E2");
        ArrayAssertionUtil.assertProps(listenerSelect.assertOneGetNewAndReset(), fields, new Object[] {12});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fields, new Object[][] {{12}});

        EventType resultType = stmtSelect.getEventType();
        assertEquals(1, resultType.getPropertyNames().length);
        assertEquals(Integer.class, resultType.getPropertyType("sumb"));

        stmtSelect.destroy();
        stmtCreate.destroy();
    }

    public void testSelectAggregationGrouping()
    {
        String[] fields = new String[] {"a", "sumb"};

        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select string as a, intPrimitive as b from " + SupportBean.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEQL(stmtTextCreate);

        // create select stmt
        String stmtTextSelect = "on " + SupportBean_A.class.getName() + " select a, sum(b) as sumb from MyWindow group by a order by a desc";
        EPStatement stmtSelect = epService.getEPAdministrator().createEQL(stmtTextSelect);
        stmtSelect.addListener(listenerSelect);

        // create select stmt
        String stmtTextSelectTwo = "on " + SupportBean_A.class.getName() + " select a, sum(b) as sumb from MyWindow group by a having sum(b) > 5 order by a desc";
        EPStatement stmtSelectTwo = epService.getEPAdministrator().createEQL(stmtTextSelectTwo);
        stmtSelectTwo.addListener(listenerSelectTwo);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string as a, intPrimitive as b from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEQL(stmtTextInsertOne);

        // fire trigger
        sendSupportBean_A("A1");
        assertFalse(listenerSelect.isInvoked());
        assertFalse(listenerSelectTwo.isInvoked());

        // send 3 events
        sendSupportBean("E1", 1);
        sendSupportBean("E2", 2);
        sendSupportBean("E1", 5);
        assertFalse(listenerSelect.isInvoked());
        assertFalse(listenerSelectTwo.isInvoked());

        // fire trigger
        sendSupportBean_A("A1");
        ArrayAssertionUtil.assertPropsPerRow(listenerSelect.getLastNewData(), fields, new Object[][] {{"E2", 2}, {"E1", 6}});
        assertNull(listenerSelect.getLastOldData());
        listenerSelect.reset();
        ArrayAssertionUtil.assertPropsPerRow(listenerSelectTwo.getLastNewData(), fields, new Object[][] {{"E1", 6}});
        assertNull(listenerSelect.getLastOldData());
        listenerSelect.reset();

        // send 3 events
        sendSupportBean("E4", -1);
        sendSupportBean("E2", 10);
        sendSupportBean("E1", 100);
        assertFalse(listenerSelect.isInvoked());

        sendSupportBean_A("A2");
        ArrayAssertionUtil.assertPropsPerRow(listenerSelect.getLastNewData(), fields, new Object[][] {{"E4", -1}, {"E2", 12}, {"E1", 106}});

        // create delete stmt, delete E2
        String stmtTextDelete = "on " + SupportBean_B.class.getName() + " delete from MyWindow where id = a";
        epService.getEPAdministrator().createEQL(stmtTextDelete);
        sendSupportBean_B("E2");

        sendSupportBean_A("A3");
        ArrayAssertionUtil.assertPropsPerRow(listenerSelect.getLastNewData(), fields, new Object[][] {{"E4", -1}, {"E1", 106}});
        assertNull(listenerSelect.getLastOldData());
        listenerSelect.reset();
        ArrayAssertionUtil.assertPropsPerRow(listenerSelectTwo.getLastNewData(), fields, new Object[][] {{"E1", 106}});
        assertNull(listenerSelectTwo.getLastOldData());
        listenerSelectTwo.reset();

        EventType resultType = stmtSelect.getEventType();
        assertEquals(2, resultType.getPropertyNames().length);
        assertEquals(String.class, resultType.getPropertyType("a"));
        assertEquals(Integer.class, resultType.getPropertyType("sumb"));

        stmtSelect.destroy();
        stmtCreate.destroy();
        stmtSelectTwo.destroy();
    }

    public void testSelectCorrelationDelete()
    {
        String[] fields = new String[] {"a", "b"};

        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select string as a, intPrimitive as b from " + SupportBean.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEQL(stmtTextCreate);

        // create select stmt
        String stmtTextSelect = "on " + SupportBean_A.class.getName() + " select mywin.* from MyWindow as mywin where id = a";
        EPStatement stmtSelect = epService.getEPAdministrator().createEQL(stmtTextSelect);
        stmtSelect.addListener(listenerSelect);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string as a, intPrimitive as b from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEQL(stmtTextInsertOne);

        // create delete stmt
        String stmtTextDelete = "on " + SupportBean_B.class.getName() + " delete from MyWindow where a = id";
        EPStatement stmtDelete = epService.getEPAdministrator().createEQL(stmtTextDelete);

        // send 3 event
        sendSupportBean("E1", 1);
        sendSupportBean("E2", 2);
        sendSupportBean("E3", 3);
        assertFalse(listenerSelect.isInvoked());

        // fire trigger
        sendSupportBean_A("X1");
        assertFalse(listenerSelect.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1}, {"E2", 2}, {"E3", 3}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fields, null);

        sendSupportBean_A("E2");
        ArrayAssertionUtil.assertProps(listenerSelect.assertOneGetNewAndReset(), fields, new Object[] {"E2", 2});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fields, new Object[][] {{"E2", 2}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1}, {"E2", 2}, {"E3", 3}});

        sendSupportBean_A("E1");
        ArrayAssertionUtil.assertProps(listenerSelect.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fields, new Object[][] {{"E1", 1}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1}, {"E2", 2}, {"E3", 3}});

        // delete event
        sendSupportBean_B("E1");
        assertFalse(listenerSelect.isInvoked());

        sendSupportBean_A("E1");
        assertFalse(listenerSelect.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E2", 2}, {"E3", 3}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fields, null);

        sendSupportBean_A("E2");
        ArrayAssertionUtil.assertProps(listenerSelect.assertOneGetNewAndReset(), fields, new Object[] {"E2", 2});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fields, new Object[][] {{"E2", 2}});

        stmtSelect.destroy();
        stmtDelete.destroy();
        stmtCreate.destroy();
    }

    public void testPatternCorrelation()
    {
        String[] fields = new String[] {"a", "b"};

        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select string as a, intPrimitive as b from " + SupportBean.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEQL(stmtTextCreate);

        // create select stmt
        String stmtTextSelect = "on pattern [every ea=" + SupportBean_A.class.getName() +
                                " or every eb=" + SupportBean_B.class.getName() + "] select mywin.* from MyWindow as mywin where a = coalesce(ea.id, eb.id)";
        EPStatement stmtSelect = epService.getEPAdministrator().createEQL(stmtTextSelect);
        stmtSelect.addListener(listenerSelect);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string as a, intPrimitive as b from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEQL(stmtTextInsertOne);

        // send 3 event
        sendSupportBean("E1", 1);
        sendSupportBean("E2", 2);
        sendSupportBean("E3", 3);
        assertFalse(listenerSelect.isInvoked());

        // fire trigger
        sendSupportBean_A("X1");
        assertFalse(listenerSelect.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1}, {"E2", 2}, {"E3", 3}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fields, null);

        sendSupportBean_B("E2");
        ArrayAssertionUtil.assertProps(listenerSelect.assertOneGetNewAndReset(), fields, new Object[] {"E2", 2});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fields, new Object[][] {{"E2", 2}});

        sendSupportBean_A("E1");
        ArrayAssertionUtil.assertProps(listenerSelect.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fields, new Object[][] {{"E1", 1}});

        sendSupportBean_B("E3");
        ArrayAssertionUtil.assertProps(listenerSelect.assertOneGetNewAndReset(), fields, new Object[] {"E3", 3});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fields, new Object[][] {{"E3", 3}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1", 1}, {"E2", 2}, {"E3", 3}});

        stmtCreate.destroy();
        stmtSelect.destroy();
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

    private SupportBean sendSupportBean(String string, int intPrimitive)
    {
        SupportBean bean = new SupportBean();
        bean.setString(string);
        bean.setIntPrimitive(intPrimitive);
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }
}
