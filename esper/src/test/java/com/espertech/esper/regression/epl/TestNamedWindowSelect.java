/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.regression.epl;

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.client.EventType;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_A;
import com.espertech.esper.support.bean.SupportBean_B;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.core.StatementType;
import com.espertech.esper.core.EPStatementSPI;

public class TestNamedWindowSelect extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listenerSelect;
    private SupportUpdateListener listenerSelectTwo;
    private SupportUpdateListener listenerConsumer;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listenerSelect = new SupportUpdateListener();
        listenerSelectTwo = new SupportUpdateListener();
        listenerConsumer = new SupportUpdateListener();
    }

    public void testSelectAggregationHavingStreamWildcard()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean_A", SupportBean_A.class);
        String[] fields = new String[] {"sumb"};

        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as (a string, b int)";
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL(stmtTextCreate);

        String stmtTextInsertOne = "insert into MyWindow select string as a, intPrimitive as b from SupportBean";
        epService.getEPAdministrator().createEPL(stmtTextInsertOne);

        String stmtTextSelect = "on SupportBean_A select mwc.* as mwcwin from MyWindow mwc where id = a group by a having sum(b) = 20";
        EPStatement select = epService.getEPAdministrator().createEPL(stmtTextSelect);
        select.addListener(listenerSelect);

        // send 3 event
        sendSupportBean("E1", 16);
        sendSupportBean("E2", 2);
        sendSupportBean("E1", 4);

        // fire trigger
        sendSupportBean_A("E1");
        EventBean[] events = listenerSelect.getLastNewData();
        assertEquals(2, events.length);
        assertEquals("E1", events[0].get("mwcwin.a"));
        assertEquals("E1", events[1].get("mwcwin.a"));
    }

    public void testPatternTimedSelect()
    {
        // test for JIRA ESPER-332
        sendTimer(0, epService);

        String stmtTextCreate = "create window MyWindow.win:keepall() as select * from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEPL(stmtTextCreate);

        String stmtCount = "on pattern[every timer:interval(10 sec)] select count(eve), eve from MyWindow as eve";
        epService.getEPAdministrator().createEPL(stmtCount);

        String stmtTextOnSelect = "on pattern [ every timer:interval(10 sec)] select string from MyWindow having count(string) > 0";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtTextOnSelect);
        stmt.addListener(listenerConsumer);

        String stmtTextInsertOne = "insert into MyWindow select * from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEPL(stmtTextInsertOne);

        sendTimer(11000, epService);
        assertFalse(listenerConsumer.isInvoked());

        sendTimer(21000, epService);
        assertFalse(listenerConsumer.isInvoked());

        sendSupportBean("E1", 1);
        sendTimer(31000, epService);
        assertEquals("E1", listenerConsumer.assertOneGetNewAndReset().get("string"));
    }

    public void testInsertIntoWildcard()
    {
        String[] fields = new String[] {"string", "intPrimitive"};

        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select * from " + SupportBean.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL(stmtTextCreate);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select * from " + SupportBean.class.getName() + "(string like 'E%')";
        epService.getEPAdministrator().createEPL(stmtTextInsertOne);

        // create on-select stmt
        String stmtTextSelect = "on " + SupportBean_A.class.getName() + " insert into MyStream select mywin.* from MyWindow as mywin order by string asc";
        EPStatement stmtSelect = epService.getEPAdministrator().createEPL(stmtTextSelect);
        stmtSelect.addListener(listenerSelect);
        assertEquals(StatementType.ON_INSERT, ((EPStatementSPI) stmtSelect).getStatementMetadata().getStatementType());

        // create consuming statement
        String stmtTextConsumer = "select * from default.MyStream";
        EPStatement stmtConsumer = epService.getEPAdministrator().createEPL(stmtTextConsumer);
        stmtConsumer.addListener(listenerConsumer);

        // create second inserting statement
        String stmtTextInsertTwo = "insert into MyStream select * from " + SupportBean.class.getName() + "(string like 'I%')";
        epService.getEPAdministrator().createEPL(stmtTextInsertTwo);

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

        // delete all from named window
        String stmtTextDelete = "on " + SupportBean_B.class.getName() + " delete from MyWindow";
        epService.getEPAdministrator().createEPL(stmtTextDelete);
        sendSupportBean_B("B1");

        // fire trigger - nothing to insert
        sendSupportBean_A("A3");

        stmtConsumer.destroy();
        stmtSelect.destroy();
        stmtCreate.destroy();
    }

    public void testInvalid()
    {
        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select * from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEPL(stmtTextCreate);

        tryInvalid("on " + SupportBean_A.class.getName() + " select * from MyWindow where sum(intPrimitive) > 100",
                   "Error validating expression: An aggregate function may not appear in a WHERE clause (use the HAVING clause) [on com.espertech.esper.support.bean.SupportBean_A select * from MyWindow where sum(intPrimitive) > 100]");

        tryInvalid("on " + SupportBean_A.class.getName() + " insert into MyStream select * from DUMMY",
                   "Named window 'DUMMY' has not been declared [on com.espertech.esper.support.bean.SupportBean_A insert into MyStream select * from DUMMY]");

        tryInvalid("on " + SupportBean_A.class.getName() + " select prev(1, string) from MyWindow",
                   "Error starting statement: Previous function cannot be used in this context [on com.espertech.esper.support.bean.SupportBean_A select prev(1, string) from MyWindow]");
    }

    private void tryInvalid(String text, String message)
    {
        try
        {
            epService.getEPAdministrator().createEPL(text);
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
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL(stmtTextCreate);

        // create select stmt
        String stmtTextSelect = "on " + SupportBean_A.class.getName() + " select mywin.*, id from MyWindow as mywin where MyWindow.b < 3 order by a asc";
        EPStatement stmtSelect = epService.getEPAdministrator().createEPL(stmtTextSelect);
        stmtSelect.addListener(listenerSelect);
        assertEquals(StatementType.ON_SELECT, ((EPStatementSPI) stmtSelect).getStatementMetadata().getStatementType());

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string as a, intPrimitive as b from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEPL(stmtTextInsertOne);

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

    public void testSelectJoinColumnsLimit()
    {
        String[] fields = new String[] {"triggerid", "wina", "b"};

        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select string as a, intPrimitive as b from " + SupportBean.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL(stmtTextCreate);

        // create select stmt
        String stmtTextSelect = "on " + SupportBean_A.class.getName() + " as trigger select trigger.id as triggerid, win.a as wina, b from MyWindow as win order by wina";
        EPStatement stmtSelect = epService.getEPAdministrator().createEPL(stmtTextSelect);
        stmtSelect.addListener(listenerSelect);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string as a, intPrimitive as b from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEPL(stmtTextInsertOne);

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

        // try limit clause
        stmtSelect.destroy();
        stmtTextSelect = "on " + SupportBean_A.class.getName() + " as trigger select trigger.id as triggerid, win.a as wina, b from MyWindow as win order by wina limit 1";
        stmtSelect = epService.getEPAdministrator().createEPL(stmtTextSelect);
        stmtSelect.addListener(listenerSelect);

        sendSupportBean_A("A1");
        assertEquals(1, listenerSelect.getLastNewData().length);
        ArrayAssertionUtil.assertProps(listenerSelect.getLastNewData()[0], fields, new Object[] {"A1", "E1", 1});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fields, new Object[][] {{"A1", "E1", 1}});

        stmtCreate.destroy();
    }

    public void testSelectAggregation()
    {
        String[] fields = new String[] {"sumb"};

        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select string as a, intPrimitive as b from " + SupportBean.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL(stmtTextCreate);

        // create select stmt
        String stmtTextSelect = "on " + SupportBean_A.class.getName() + " select sum(b) as sumb from MyWindow";
        EPStatement stmtSelect = epService.getEPAdministrator().createEPL(stmtTextSelect);
        stmtSelect.addListener(listenerSelect);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string as a, intPrimitive as b from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEPL(stmtTextInsertOne);

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
        epService.getEPAdministrator().createEPL(stmtTextDelete);

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
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL(stmtTextCreate);

        // create select stmt
        String stmtTextSelect = "on " + SupportBean_A.class.getName() + " select sum(b) as sumb from MyWindow where a = id";
        EPStatement stmtSelect = epService.getEPAdministrator().createEPL(stmtTextSelect);
        stmtSelect.addListener(listenerSelect);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string as a, intPrimitive as b from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEPL(stmtTextInsertOne);

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
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL(stmtTextCreate);

        // create select stmt
        String stmtTextSelect = "on " + SupportBean_A.class.getName() + " select a, sum(b) as sumb from MyWindow group by a order by a desc";
        EPStatement stmtSelect = epService.getEPAdministrator().createEPL(stmtTextSelect);
        stmtSelect.addListener(listenerSelect);

        // create select stmt
        String stmtTextSelectTwo = "on " + SupportBean_A.class.getName() + " select a, sum(b) as sumb from MyWindow group by a having sum(b) > 5 order by a desc";
        EPStatement stmtSelectTwo = epService.getEPAdministrator().createEPL(stmtTextSelectTwo);
        stmtSelectTwo.addListener(listenerSelectTwo);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string as a, intPrimitive as b from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEPL(stmtTextInsertOne);

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
        epService.getEPAdministrator().createEPL(stmtTextDelete);
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
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL(stmtTextCreate);

        // create select stmt
        String stmtTextSelect = "on " + SupportBean_A.class.getName() + " select mywin.* from MyWindow as mywin where id = a";
        EPStatement stmtSelect = epService.getEPAdministrator().createEPL(stmtTextSelect);
        stmtSelect.addListener(listenerSelect);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string as a, intPrimitive as b from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEPL(stmtTextInsertOne);

        // create delete stmt
        String stmtTextDelete = "on " + SupportBean_B.class.getName() + " delete from MyWindow where a = id";
        EPStatement stmtDelete = epService.getEPAdministrator().createEPL(stmtTextDelete);

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
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL(stmtTextCreate);

        // create select stmt
        String stmtTextSelect = "on pattern [every ea=" + SupportBean_A.class.getName() +
                                " or every eb=" + SupportBean_B.class.getName() + "] select mywin.* from MyWindow as mywin where a = coalesce(ea.id, eb.id)";
        EPStatement stmtSelect = epService.getEPAdministrator().createEPL(stmtTextSelect);
        stmtSelect.addListener(listenerSelect);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string as a, intPrimitive as b from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEPL(stmtTextInsertOne);

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

    private void sendTimer(long timeInMSec, EPServiceProvider epService)
    {
        CurrentTimeEvent event = new CurrentTimeEvent(timeInMSec);
        EPRuntime runtime = epService.getEPRuntime();
        runtime.sendEvent(event);
    }
}
