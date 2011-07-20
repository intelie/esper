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

import com.espertech.esper.client.*;
import com.espertech.esper.client.soda.*;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_A;
import com.espertech.esper.support.bean.SupportBean_B;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.epl.named.NamedWindowProcessor;
import com.espertech.esper.core.EPServiceProviderSPI;
import com.espertech.esper.core.StatementType;
import com.espertech.esper.core.EPStatementSPI;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

public class TestNamedWindowInsertFrom extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener[] listeners;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportBean", SupportBean.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        listeners = new SupportUpdateListener[10];
        for (int i = 0; i < listeners.length; i++)
        {
            listeners[i] = new SupportUpdateListener();
        }
    }

    public void testCreateNamedAfterNamed()
    {
        // create window
        String stmtTextCreateOne = "create window MyWindow.win:keepall() as SupportBean";
        EPStatement stmtCreateOne = epService.getEPAdministrator().createEPL(stmtTextCreateOne);
        stmtCreateOne.addListener(listeners[0]);

        // create window
        String stmtTextCreateTwo = "create window MyWindowTwo.win:keepall() as MyWindow";
        EPStatement stmtCreateTwo = epService.getEPAdministrator().createEPL(stmtTextCreateTwo);
        stmtCreateTwo.addListener(listeners[1]);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select * from SupportBean";
        epService.getEPAdministrator().createEPL(stmtTextInsertOne);

        // create consumer
        String stmtTextSelectOne = "select string from MyWindow";
        EPStatement stmtSelectOne = epService.getEPAdministrator().createEPL(stmtTextSelectOne);
        stmtSelectOne.addListener(listeners[2]);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        String[] fields = new String[] {"string"};
        ArrayAssertionUtil.assertProps(listeners[0].assertOneGetNewAndReset(), fields, new Object[] {"E1"});
        ArrayAssertionUtil.assertProps(listeners[2].assertOneGetNewAndReset(), fields, new Object[] {"E1"});
    }

    public void testInsertWhereTypeAndFilter() throws Exception
    {
        String[] fields = new String[] {"string"};

        // create window
        String stmtTextCreateOne = "create window MyWindow.win:keepall() as SupportBean";
        EPStatement stmtCreateOne = epService.getEPAdministrator().createEPL(stmtTextCreateOne, "name1");
        stmtCreateOne.addListener(listeners[0]);
        EventType eventTypeOne = stmtCreateOne.getEventType();

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select * from SupportBean(intPrimitive > 0)";
        epService.getEPAdministrator().createEPL(stmtTextInsertOne);

        // populate some data
        assertEquals(0, getCount("MyWindow"));
        epService.getEPRuntime().sendEvent(new SupportBean("A1", 1));
        assertEquals(1, getCount("MyWindow"));
        epService.getEPRuntime().sendEvent(new SupportBean("B2", 1));
        epService.getEPRuntime().sendEvent(new SupportBean("C3", 1));
        epService.getEPRuntime().sendEvent(new SupportBean("A4", 4));
        epService.getEPRuntime().sendEvent(new SupportBean("C5", 4));
        assertEquals(5, getCount("MyWindow"));
        assertEquals("name1", getStatementName("MyWindow"));
        assertEquals(stmtTextCreateOne, getEPL("MyWindow"));
        listeners[0].reset();
        
        // create window with keep-all
        String stmtTextCreateTwo = "create window MyWindowTwo.win:keepall() as MyWindow insert";
        EPStatement stmtCreateTwo = epService.getEPAdministrator().createEPL(stmtTextCreateTwo);
        stmtCreateTwo.addListener(listeners[2]);
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreateTwo.iterator(), fields, new Object[][] {{"A1"}, {"B2"}, {"C3"}, {"A4"}, {"C5"}});
        EventType eventTypeTwo = stmtCreateTwo.iterator().next().getEventType();
        assertFalse(listeners[2].isInvoked());
        assertEquals(5, getCount("MyWindowTwo"));
        assertEquals(StatementType.CREATE_WINDOW, ((EPStatementSPI) stmtCreateTwo).getStatementMetadata().getStatementType());

        // create window with keep-all and filter
        String stmtTextCreateThree = "create window MyWindowThree.win:keepall() as MyWindow insert where string like 'A%'";
        EPStatement stmtCreateThree = epService.getEPAdministrator().createEPL(stmtTextCreateThree);
        stmtCreateThree.addListener(listeners[3]);
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreateThree.iterator(), fields, new Object[][] {{"A1"}, {"A4"}});
        EventType eventTypeThree = stmtCreateThree.iterator().next().getEventType();
        assertFalse(listeners[3].isInvoked());
        assertEquals(2, getCount("MyWindowThree"));

        // create window with last-per-id
        String stmtTextCreateFour = "create window MyWindowFour.std:unique(intPrimitive) as MyWindow insert";
        EPStatement stmtCreateFour = epService.getEPAdministrator().createEPL(stmtTextCreateFour);
        stmtCreateFour.addListener(listeners[4]);
        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreateFour.iterator(), fields, new Object[][] {{"C3"}, {"C5"}});
        EventType eventTypeFour = stmtCreateFour.iterator().next().getEventType();
        assertFalse(listeners[4].isInvoked());
        assertEquals(2, getCount("MyWindowFour"));

        epService.getEPAdministrator().createEPL("insert into MyWindow select * from SupportBean(string like 'A%')");
        epService.getEPAdministrator().createEPL("insert into MyWindowTwo select * from SupportBean(string like 'B%')");
        epService.getEPAdministrator().createEPL("insert into MyWindowThree select * from SupportBean(string like 'C%')");
        epService.getEPAdministrator().createEPL("insert into MyWindowFour select * from SupportBean(string like 'D%')");        
        assertFalse(listeners[0].isInvoked() || listeners[2].isInvoked() || listeners[3].isInvoked() || listeners[4].isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean("B9", -9));
        EventBean received = listeners[2].assertOneGetNewAndReset();
        ArrayAssertionUtil.assertProps(received, fields, new Object[] {"B9"});
        assertSame(eventTypeTwo, received.getEventType());
        assertFalse(listeners[0].isInvoked() || listeners[3].isInvoked() || listeners[4].isInvoked());
        assertEquals(6, getCount("MyWindowTwo"));

        epService.getEPRuntime().sendEvent(new SupportBean("A8", -8));
        received = listeners[0].assertOneGetNewAndReset();
        ArrayAssertionUtil.assertProps(received, fields, new Object[] {"A8"});
        assertSame(eventTypeOne, received.getEventType());
        assertFalse(listeners[2].isInvoked() || listeners[3].isInvoked() || listeners[4].isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean("C7", -7));
        received = listeners[3].assertOneGetNewAndReset();
        ArrayAssertionUtil.assertProps(received, fields, new Object[] {"C7"});
        assertSame(eventTypeThree, received.getEventType());
        assertFalse(listeners[2].isInvoked() || listeners[0].isInvoked() || listeners[4].isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean("D6", -6));
        received = listeners[4].assertOneGetNewAndReset();
        ArrayAssertionUtil.assertProps(received, fields, new Object[] {"D6"});
        assertSame(eventTypeFour, received.getEventType());
        assertFalse(listeners[2].isInvoked() || listeners[0].isInvoked() || listeners[3].isInvoked());
    }

    public void testInsertWhereOMStaggered()
    {
        Map<String, Object> dataType = makeMap(new Object[][] {{"a", String.class}, {"b", int.class}});
        epService.getEPAdministrator().getConfiguration().addEventType("MyMap", dataType);

        String stmtTextCreateOne = "create window MyWindow.win:keepall() as select a, b from MyMap";
        EPStatement stmtCreateOne = epService.getEPAdministrator().createEPL(stmtTextCreateOne);
        stmtCreateOne.addListener(listeners[0]);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select a, b from MyMap";
        epService.getEPAdministrator().createEPL(stmtTextInsertOne);

        // populate some data
        epService.getEPRuntime().sendEvent(makeMap(new Object[][] {{"a", "E1"}, {"b", 2}}), "MyMap");
        epService.getEPRuntime().sendEvent(makeMap(new Object[][] {{"a", "E2"}, {"b", 10}}), "MyMap");
        epService.getEPRuntime().sendEvent(makeMap(new Object[][] {{"a", "E3"}, {"b", 10}}), "MyMap");

        // create window with keep-all using OM
        EPStatementObjectModel model = new EPStatementObjectModel();
        Expression where = Expressions.eq("b", 10);
        model.setCreateWindow(CreateWindowClause.create("MyWindowTwo", View.create("win", "keepall")).insert(true).insertWhereClause(where));
        model.setSelectClause(SelectClause.createWildcard());
        model.setFromClause(FromClause.create(FilterStream.create("MyWindow")));
        String text = "create window MyWindowTwo.win:keepall() as select * from MyWindow insert where b = 10";
        assertEquals(text, model.toEPL());

        EPStatementObjectModel modelTwo = epService.getEPAdministrator().compileEPL(text);
        assertEquals(text, modelTwo.toEPL());
        
        EPStatement stmt = epService.getEPAdministrator().create(modelTwo);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), "a,b".split(","), new Object[][] {{"E2", 10}, {"E3", 10}});

        // test select individual fields and from an insert-from named window
        stmt = epService.getEPAdministrator().createEPL("create window MyWindowThree.win:keepall() as select a from MyWindowTwo insert where a = 'E2'");
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), "a".split(","), new Object[][] {{"E2"}});
    }

    public void testVariantStream()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean_A", SupportBean_A.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean_B", SupportBean_B.class);

        ConfigurationVariantStream config = new ConfigurationVariantStream();
        //config.setTypeVariance(ConfigurationVariantStream.TypeVariance.ANY);
        config.addEventTypeName("SupportBean_A");
        config.addEventTypeName("SupportBean_B");
        epService.getEPAdministrator().getConfiguration().addVariantStream("VarStream", config);
        epService.getEPAdministrator().createEPL("create window MyWindow.win:keepall() as select * from VarStream");
        EPStatement stmt = epService.getEPAdministrator().createEPL("create window MyWindowTwo.win:keepall() as MyWindow");

        epService.getEPAdministrator().createEPL("insert into VarStream select * from SupportBean_A");
        epService.getEPAdministrator().createEPL("insert into VarStream select * from SupportBean_B");
        epService.getEPAdministrator().createEPL("insert into MyWindowTwo select * from VarStream");
        epService.getEPRuntime().sendEvent(new SupportBean_A("A1"));
        epService.getEPRuntime().sendEvent(new SupportBean_B("B1"));
        EventBean[] events = ArrayAssertionUtil.iteratorToArray(stmt.iterator());
        System.out.println(events[0].get("id?"));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), "id?".split(","), new Object[][] {{"A1"}, {"B1"}});
    }

    public void testInvalid()
    {
        String stmtTextCreateOne = "create window MyWindow.win:keepall() as SupportBean";
        epService.getEPAdministrator().createEPL(stmtTextCreateOne);

        try
        {
            epService.getEPAdministrator().createEPL("create window testWindow3.win:keepall() as select * from " + SupportBean.class.getName() + " insert where (intPrimitive = 10)");
            fail();
        }
        catch (EPStatementException ex)
        {
            assertEquals("A named window by name 'com.espertech.esper.support.bean.SupportBean' could not be located, use the insert-keyword with an existing named window [create window testWindow3.win:keepall() as select * from com.espertech.esper.support.bean.SupportBean insert where (intPrimitive = 10)]", ex.getMessage());
        }

        try
        {
            epService.getEPAdministrator().createEPL("create window MyWindowTwo.win:keepall() as MyWindow insert where (select intPrimitive from SupportBean.std:lastevent())");
            fail();
        }
        catch (EPStatementException ex)
        {
            assertEquals("Create window where-clause may not have a subselect [create window MyWindowTwo.win:keepall() as MyWindow insert where (select intPrimitive from SupportBean.std:lastevent())]", ex.getMessage());
        }

        try
        {
            epService.getEPAdministrator().createEPL("create window MyWindowTwo.win:keepall() as MyWindow insert where sum(intPrimitive) > 2");
            fail();
        }
        catch (EPStatementException ex)
        {
            assertEquals("Create window where-clause may not have an aggregation function [create window MyWindowTwo.win:keepall() as MyWindow insert where sum(intPrimitive) > 2]", ex.getMessage());
        }

        try
        {
            epService.getEPAdministrator().createEPL("create window MyWindowTwo.win:keepall() as MyWindow insert where prev(1, intPrimitive) = 1");
            fail();
        }
        catch (EPStatementException ex)
        {
            assertEquals("Create window where-clause may not have a function that requires view resources (prior, prev) [create window MyWindowTwo.win:keepall() as MyWindow insert where prev(1, intPrimitive) = 1]", ex.getMessage());
        }
    }

    private Map<String, Object> makeMap(Object[][] entries)
    {
        Map result = new HashMap<String, Object>();
        if (entries == null)
        {
            return result;
        }
        for (int i = 0; i < entries.length; i++)
        {
            result.put(entries[i][0], entries[i][1]);
        }
        return result;
    }

    private long getCount(String windowName) throws Exception
    {
        NamedWindowProcessor processor = ((EPServiceProviderSPI)epService).getNamedWindowService().getProcessor(windowName);
        return processor.getCountDataWindow();
    }    

    private String getStatementName(String windowName) throws Exception
    {
        NamedWindowProcessor processor = ((EPServiceProviderSPI)epService).getNamedWindowService().getProcessor(windowName);
        return processor.getStatementName();
    }

    private String getEPL(String windowName) throws Exception
    {
        NamedWindowProcessor processor = ((EPServiceProviderSPI)epService).getNamedWindowService().getProcessor(windowName);
        return processor.getEplExpression();
    }
}
