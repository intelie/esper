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

import com.espertech.esper.support.bean.*;
import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.client.soda.*;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.type.OuterJoinType;
import com.espertech.esper.util.SerializableObjectCopier;

public class Test2StreamOuterJoin extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;
    private EPStatement outerJoinView;
    private String[] fields = new String[] {"s0.id", "s0.p00", "s1.id", "s1.p10"};

    private SupportBean_S0 eventsS0[] = new SupportBean_S0[15];
    private SupportBean_S1 eventsS1[] = new SupportBean_S1[15];

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getLogging().setEnableQueryPlan(true);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();

        int count = 100;
        for (int i = 0; i < eventsS0.length; i++)
        {
            eventsS0[i] = new SupportBean_S0(count++, Integer.toString(i));
        }
        count = 200;
        for (int i = 0; i < eventsS1.length; i++)
        {
            eventsS1[i] = new SupportBean_S1(count++, Integer.toString(i));
        }
    }

    public void testRangeOuterJoin() {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBeanRange", SupportBeanRange.class);

        String stmtOne = "select sb.string as sbstr, sb.intPrimitive as sbint, sbr.key as sbrk, sbr.rangeStart as sbrs, sbr.rangeEnd as sbre " +
                      "from SupportBean.win:keepall() sb " +
                      "full outer join " +
                      "SupportBeanRange.win:keepall() sbr " +
                      "on string = key " +
                      "where intPrimitive between rangeStart and rangeEnd " +
                      "order by rangeStart asc, intPrimitive asc";
        runAssertion(stmtOne);

        String stmtTwo = "select sb.string as sbstr, sb.intPrimitive as sbint, sbr.key as sbrk, sbr.rangeStart as sbrs, sbr.rangeEnd as sbre " +
                      "from SupportBeanRange.win:keepall() sbr " +
                      "full outer join " +
                      "SupportBean.win:keepall() sb " +
                      "on string = key " +
                      "where intPrimitive between rangeStart and rangeEnd " +
                      "order by rangeStart asc, intPrimitive asc";
        runAssertion(stmtTwo);

        String stmtThree = "select sb.string as sbstr, sb.intPrimitive as sbint, sbr.key as sbrk, sbr.rangeStart as sbrs, sbr.rangeEnd as sbre " +
                      "from SupportBeanRange.win:keepall() sbr " +
                      "full outer join " +
                      "SupportBean.win:keepall() sb " +
                      "on string = key " +
                      "where intPrimitive >= rangeStart and intPrimitive <= rangeEnd " +
                      "order by rangeStart asc, intPrimitive asc";
        runAssertion(stmtThree);
    }

    private void runAssertion(String epl) {

        String fields[] = "sbstr,sbint,sbrk,sbrs,sbre".split(",");
        outerJoinView = epService.getEPAdministrator().createEPL(epl);
        outerJoinView.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("K1", 10));
        epService.getEPRuntime().sendEvent(new SupportBeanRange("R1", "K1", 20, 30));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean("K1", 30));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields, new Object[][] {{"K1", 30, "K1", 20, 30}});

        epService.getEPRuntime().sendEvent(new SupportBean("K1", 40));
        epService.getEPRuntime().sendEvent(new SupportBean("K1", 31));
        epService.getEPRuntime().sendEvent(new SupportBean("K1", 19));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBeanRange("R2", "K1", 39, 41));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields, new Object[][] {{"K1", 40, "K1", 39, 41}});

        epService.getEPRuntime().sendEvent(new SupportBeanRange("R2", "K1", 38, 40));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields, new Object[][] {{"K1", 40, "K1", 38, 40}});

        epService.getEPRuntime().sendEvent(new SupportBeanRange("R2", "K1", 40, 42));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields, new Object[][] {{"K1", 40, "K1", 40, 42}});

        epService.getEPRuntime().sendEvent(new SupportBeanRange("R2", "K1", 41, 42));
        epService.getEPRuntime().sendEvent(new SupportBeanRange("R2", "K1", 38, 39));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean("K1", 41));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields, new Object[][] {
                {"K1", 41, "K1", 39, 41}, {"K1", 41, "K1", 40, 42}, {"K1", 41, "K1", 41, 42}});

        epService.getEPRuntime().sendEvent(new SupportBeanRange("R2", "K1", 35, 42));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields, new Object[][]
                {{"K1", 40, "K1", 35, 42}, {"K1", 41, "K1", 35, 42}});

        outerJoinView.destroy();
    }

    public void testFullOuterIteratorGroupBy()
    {
        String stmt = "select string, intPrimitive, symbol, volume " +
                      "from " + SupportMarketDataBean.class.getName() + ".win:keepall() " +
                      "full outer join " +
                      SupportBean.class.getName() + ".std:groupwin(string, intPrimitive).win:length(2) " +
                      "on string = symbol group by string, intPrimitive, symbol " +
                      "order by string, intPrimitive, symbol, volume";

        outerJoinView = epService.getEPAdministrator().createEPL(stmt);
        outerJoinView.addListener(listener);

        sendEventMD("c0", 200L);
        sendEventMD("c3", 400L);

        sendEvent("c0", 0);
        sendEvent("c0", 1);
        sendEvent("c0", 2);
        sendEvent("c1", 0);
        sendEvent("c1", 1);
        sendEvent("c1", 2);
        sendEvent("c2", 0);
        sendEvent("c2", 1);
        sendEvent("c2", 2);

        SafeIterator iterator = outerJoinView.safeIterator();
        EventBean[] events = ArrayAssertionUtil.iteratorToArray(iterator);
        assertEquals(10, events.length);

        /* For debugging, comment in
        for (int i = 0; i < events.length; i++)
        {
            System.out.println(
                   "string=" + events[i].get("string") +
                   "  int=" + events[i].get("intPrimitive") +
                   "  symbol=" + events[i].get("symbol") +
                   "  volume="  + events[i].get("volume")
                );
        }
        */

        ArrayAssertionUtil.assertPropsPerRow(events, "string,intPrimitive,symbol,volume".split(","),
                new Object[][] {
                        {null, null, "c3", 400L},
                        {"c0", 0, "c0", 200L},
                        {"c0", 1, "c0", 200L},
                        {"c0", 2, "c0", 200L},
                        {"c1", 0, null, null},
                        {"c1", 1, null, null},
                        {"c1", 2, null, null},
                        {"c2", 0, null, null},
                        {"c2", 1, null, null},
                        {"c2", 2, null, null}
                    });
    }

    public void testFullOuterJoin()
    {
        setupStatement("full");

        // Send S0[0]
        sendEvent(eventsS0[0]);
        compareEvent(listener.assertOneGetNewAndReset(), 100, "0", null, null);
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{100, "0", null, null}});

        // Send S1[1]
        sendEvent(eventsS1[1]);
        compareEvent(listener.assertOneGetNewAndReset(), null, null, 201, "1");
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{100, "0", null, null},
                                {null, null, 201, "1"}});

        // Send S1[2] and S0[2]
        sendEvent(eventsS1[2]);
        compareEvent(listener.assertOneGetNewAndReset(), null, null, 202, "2");
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{100, "0", null, null},
                                {null, null, 201, "1"},
                                {null, null, 202, "2"} });

        sendEvent(eventsS0[2]);
        compareEvent(listener.assertOneGetNewAndReset(), 102, "2", 202, "2");
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{100, "0", null, null},
                                {null, null, 201, "1"},
                                {102, "2", 202, "2"} });

        // Send S0[3] and S1[3]
        sendEvent(eventsS0[3]);
        compareEvent(listener.assertOneGetNewAndReset(), 103, "3", null, null);
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{100, "0", null, null},
                                {null, null, 201, "1"},
                                {102, "2", 202, "2"},
                                {103, "3", null, null}});
        sendEvent(eventsS1[3]);
        compareEvent(listener.assertOneGetNewAndReset(), 103, "3", 203, "3");
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{100, "0", null, null},
                                {null, null, 201, "1"},
                                {102, "2", 202, "2"},
                                {103, "3", 203, "3"}});

        // Send S0[4], pushes S0[0] out of window
        sendEvent(eventsS0[4]);
        EventBean oldEvent = listener.getLastOldData()[0];
        EventBean newEvent = listener.getLastNewData()[0];
        compareEvent(oldEvent, 100, "0", null, null);
        compareEvent(newEvent, 104, "4", null, null);
        listener.reset();
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{null, null, 201, "1"},
                                {102, "2", 202, "2"},
                                {103, "3", 203, "3"},
                                {104, "4", null, null}});

        // Send S1[4]
        sendEvent(eventsS1[4]);
        compareEvent(listener.assertOneGetNewAndReset(), 104, "4", 204, "4");
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{null, null, 201, "1"},
                                {102, "2", 202, "2"},
                                {103, "3", 203, "3"},
                                {104, "4", 204, "4"}});

        // Send S1[5]
        sendEvent(eventsS1[5]);
        compareEvent(listener.assertOneGetNewAndReset(), null, null, 205, "5");
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{null, null, 201, "1"},
                                {102, "2", 202, "2"},
                                {103, "3", 203, "3"},
                                {104, "4", 204, "4"},
                                {null, null, 205, "5"}});

        // Send S1[6], pushes S1[1] out of window
        sendEvent(eventsS1[5]);
        oldEvent = listener.getLastOldData()[0];
        newEvent = listener.getLastNewData()[0];
        compareEvent(oldEvent, null, null, 201, "1");
        compareEvent(newEvent, null, null, 205, "5");
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{102, "2", 202, "2"},
                                {103, "3", 203, "3"},
                                {104, "4", 204, "4"},
                                {null, null, 205, "5"},
                                {null, null, 205, "5"}});
    }

    public void testMultiColumnLeft_OM() throws Exception
    {
        EPStatementObjectModel model = new EPStatementObjectModel();
        model.setSelectClause(SelectClause.create("s0.id, s0.p00, s0.p01, s1.id, s1.p10, s1.p11".split(",")));
        FromClause fromClause = FromClause.create(
                FilterStream.create(SupportBean_S0.class.getName(), "s0").addView("win", "keepall"),
                FilterStream.create(SupportBean_S1.class.getName(), "s1").addView("win", "keepall"));
        fromClause.add(OuterJoinQualifier.create("s0.p00", OuterJoinType.LEFT, "s1.p10").add("s1.p11", "s0.p01"));
        model.setFromClause(fromClause);
        model = (EPStatementObjectModel) SerializableObjectCopier.copy(model);

        String stmtText = "select s0.id, s0.p00, s0.p01, s1.id, s1.p10, s1.p11 from com.espertech.esper.support.bean.SupportBean_S0.win:keepall() as s0 left outer join com.espertech.esper.support.bean.SupportBean_S1.win:keepall() as s1 on s0.p00 = s1.p10 and s1.p11 = s0.p01";
        assertEquals(stmtText, model.toEPL());
        outerJoinView = epService.getEPAdministrator().create(model);
        outerJoinView.addListener(listener);

        assertMultiColumnLeft();

        EPStatementObjectModel modelReverse = epService.getEPAdministrator().compileEPL(stmtText);
        assertEquals(stmtText, modelReverse.toEPL());
    }

    public void testMultiColumnLeft()
    {
        String joinStatement = "select s0.id, s0.p00, s0.p01, s1.id, s1.p10, s1.p11 from " +
            SupportBean_S0.class.getName() + ".win:length(3) as s0 " +
            "left outer join " +
            SupportBean_S1.class.getName() + ".win:length(5) as s1" +
            " on s0.p00 = s1.p10 and s0.p01 = s1.p11";

        outerJoinView = epService.getEPAdministrator().createEPL(joinStatement);
        outerJoinView.addListener(listener);

        assertMultiColumnLeft();
    }

    private void assertMultiColumnLeft()
    {
        String fields[] = "s0.id, s0.p00, s0.p01, s1.id, s1.p10, s1.p11".split(",");
        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, "A_1", "B_1"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {1, "A_1", "B_1", null, null, null});

        epService.getEPRuntime().sendEvent(new SupportBean_S1(2, "A_1", "B_1"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {1, "A_1", "B_1", 2, "A_1", "B_1"});

        epService.getEPRuntime().sendEvent(new SupportBean_S1(3, "A_2", "B_1"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S1(4, "A_1", "B_2"));
        assertFalse(listener.isInvoked());
    }

    public void testMultiColumnRight()
    {
        String fields[] = "s0.id, s0.p00, s0.p01, s1.id, s1.p10, s1.p11".split(",");
        String joinStatement = "select s0.id, s0.p00, s0.p01, s1.id, s1.p10, s1.p11 from " +
            SupportBean_S0.class.getName() + ".win:length(3) as s0 " +
            "right outer join " +
            SupportBean_S1.class.getName() + ".win:length(5) as s1" +
            " on s0.p00 = s1.p10 and s1.p11 = s0.p01";

        outerJoinView = epService.getEPAdministrator().createEPL(joinStatement);
        outerJoinView.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, "A_1", "B_1"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S1(2, "A_1", "B_1"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {1, "A_1", "B_1", 2, "A_1", "B_1"});

        epService.getEPRuntime().sendEvent(new SupportBean_S1(3, "A_2", "B_1"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, null, null, 3, "A_2", "B_1"});

        epService.getEPRuntime().sendEvent(new SupportBean_S1(4, "A_1", "B_2"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, null, null, 4, "A_1", "B_2"});
    }

    public void testMultiColumnRightCoercion()
    {
        String fields[] = "s0.string, s1.string".split(",");
        String joinStatement = "select s0.string, s1.string from " +
            SupportBean.class.getName() + "(string like 'S0%').win:keepall() as s0 " +
            "right outer join " +
            SupportBean.class.getName() + "(string like 'S1%').win:keepall() as s1" +
            " on s0.intPrimitive = s1.doublePrimitive and s1.intPrimitive = s0.doublePrimitive";

        outerJoinView = epService.getEPAdministrator().createEPL(joinStatement);
        outerJoinView.addListener(listener);

        sendEvent("S1_1", 10, 20d);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, "S1_1"});

        sendEvent("S0_2", 11, 22d);
        assertFalse(listener.isInvoked());

        sendEvent("S0_3", 11, 21d);
        assertFalse(listener.isInvoked());

        sendEvent("S0_4", 12, 21d);
        assertFalse(listener.isInvoked());

        sendEvent("S1_2", 11, 22d);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, "S1_2"});

        sendEvent("S1_3", 22, 11d);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"S0_2", "S1_3"});

        sendEvent("S0_5", 22, 11d);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"S0_5", "S1_2"});
    }

    public void testRightOuterJoin()
    {
        setupStatement("right");

        // Send S0 events, no events expected
        sendEvent(eventsS0[0]);
        sendEvent(eventsS0[1]);
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields, null);

        // Send S1[2]
        sendEvent(eventsS1[2]);
        EventBean event = listener.assertOneGetNewAndReset();
        compareEvent(event, null, null, 202, "2");
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{null, null, 202, "2"}});

        // Send S0[2] events, joined event expected
        sendEvent(eventsS0[2]);
        event = listener.assertOneGetNewAndReset();
        compareEvent(event, 102, "2", 202, "2");
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{102, "2", 202, "2"}});

        // Send S1[3]
        sendEvent(eventsS1[3]);
        event = listener.assertOneGetNewAndReset();
        compareEvent(event, null, null, 203, "3");
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{102, "2", 202, "2"},
                                {null, null, 203, "3"}});

        // Send some more S0 events
        sendEvent(eventsS0[3]);
        event = listener.assertOneGetNewAndReset();
        compareEvent(event, 103, "3", 203, "3");
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{102, "2", 202, "2"},
                                {103, "3", 203, "3"}});

        // Send some more S0 events
        sendEvent(eventsS0[4]);
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{102, "2", 202, "2"},
                                {103, "3", 203, "3"}});

        // Push S0[2] out of the window
        sendEvent(eventsS0[5]);
        event = listener.assertOneGetOldAndReset();
        compareEvent(event, 102, "2", 202, "2");
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{null, null, 202, "2"},
                                {103, "3", 203, "3"}});

        // Some more S1 events
        sendEvent(eventsS1[6]);
        compareEvent(listener.assertOneGetNewAndReset(), null, null, 206, "6");
        sendEvent(eventsS1[7]);
        compareEvent(listener.assertOneGetNewAndReset(), null, null, 207, "7");
        sendEvent(eventsS1[8]);
        compareEvent(listener.assertOneGetNewAndReset(), null, null, 208, "8");
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{null, null, 202, "2"},
                                {103, "3", 203, "3"},
                                {null, null, 206, "6"},
                                {null, null, 207, "7"},
                                {null, null, 208, "8"}});

        // Push S1[2] out of the window
        sendEvent(eventsS1[9]);
        EventBean oldEvent = listener.getLastOldData()[0];
        EventBean newEvent = listener.getLastNewData()[0];
        compareEvent(oldEvent, null, null, 202, "2");
        compareEvent(newEvent, null, null, 209, "9");
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{103, "3", 203, "3"},
                                {null, null, 206, "6"},
                                {null, null, 207, "7"},
                                {null, null, 208, "8"},
                                {null, null, 209, "9"}});
    }

    public void testLeftOuterJoin()
    {
        setupStatement("left");

        // Send S1 events, no events expected
        sendEvent(eventsS1[0]);
        sendEvent(eventsS1[1]);
        sendEvent(eventsS1[3]);
        assertNull(listener.getLastNewData());    // No events expected
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields, null);

        // Send S0 event, expect event back from outer join
        sendEvent(eventsS0[2]);
        EventBean event = listener.assertOneGetNewAndReset();
        compareEvent(event, 102, "2", null, null);
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{102, "2", null, null}});

        // Send S1 event matching S0, expect event back
        sendEvent(eventsS1[2]);
        event = listener.assertOneGetNewAndReset();
        compareEvent(event, 102, "2", 202, "2");
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{102, "2", 202, "2"}});

        // Send some more unmatched events
        sendEvent(eventsS1[4]);
        sendEvent(eventsS1[5]);
        sendEvent(eventsS1[6]);
        assertNull(listener.getLastNewData());    // No events expected
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{102, "2", 202, "2"}});

        // Send event, expect a join result
        sendEvent(eventsS0[5]);
        event = listener.assertOneGetNewAndReset();
        compareEvent(event, 105, "5", 205, "5");
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{102, "2", 202, "2"},
                                {105, "5", 205, "5"}});

        // Let S1[2] go out of the window (lenght 5), expected old join event
        sendEvent(eventsS1[7]);
        sendEvent(eventsS1[8]);
        event = listener.assertOneGetOldAndReset();
        compareEvent(event, 102, "2", 202, "2");
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{102, "2", null, null},
                                {105, "5", 205, "5"}});

        // S0[9] should generate an outer join event
        sendEvent(eventsS0[9]);
        event = listener.assertOneGetNewAndReset();
        compareEvent(event, 109, "9", null, null);
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{102, "2", null, null},
                                {109, "9", null, null},
                                {105, "5", 205, "5"}});

        // S0[2] Should leave the window (length 3), should get OLD and NEW event
        sendEvent(eventsS0[10]);
        EventBean oldEvent = listener.getLastOldData()[0];
        EventBean newEvent = listener.getLastNewData()[0];
        compareEvent(oldEvent, 102, "2", null, null);     // S1[2] has left the window already
        compareEvent(newEvent, 110, "10", null, null);
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{110, "10", null, null},
                                {109, "9", null, null},
                                {105, "5", 205, "5"}});
    }

    public void testEventType()
    {
        EPStatement outerJoinView = setupStatement("left");

        assertEquals(String.class, outerJoinView.getEventType().getPropertyType("s0.p00"));
        assertEquals(int.class, outerJoinView.getEventType().getPropertyType("s0.id"));
        assertEquals(String.class, outerJoinView.getEventType().getPropertyType("s1.p10"));
        assertEquals(int.class, outerJoinView.getEventType().getPropertyType("s1.id"));
        assertEquals(4, outerJoinView.getEventType().getPropertyNames().length);
    }

    private void compareEvent(EventBean receivedEvent, Integer idS0, String p00, Integer idS1, String p10)
    {
        assertEquals(idS0, receivedEvent.get("s0.id"));
        assertEquals(idS1, receivedEvent.get("s1.id"));
        assertEquals(p00, receivedEvent.get("s0.p00"));
        assertEquals(p10, receivedEvent.get("s1.p10"));
    }

    private void sendEvent(String s, int intPrimitive, double doublePrimitive)
    {
        SupportBean bean = new SupportBean();
        bean.setString(s);
        bean.setIntPrimitive(intPrimitive);
        bean.setDoublePrimitive(doublePrimitive);
        epService.getEPRuntime().sendEvent(bean);
    }

    private void sendEvent(String s, int intPrimitive)
    {
        SupportBean bean = new SupportBean();
        bean.setString(s);
        bean.setIntPrimitive(intPrimitive);
        epService.getEPRuntime().sendEvent(bean);
    }

    private void sendEventMD(String symbol, long volume)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, 0, volume, "");
        epService.getEPRuntime().sendEvent(bean);
    }

    private EPStatement setupStatement(String outerJoinType)
    {
        String joinStatement = "select irstream s0.id, s0.p00, s1.id, s1.p10 from " +
            SupportBean_S0.class.getName() + ".win:length(3) as s0 " +
            outerJoinType + " outer join " +
            SupportBean_S1.class.getName() + ".win:length(5) as s1" +
            " on s0.p00 = s1.p10";

        outerJoinView = epService.getEPAdministrator().createEPL(joinStatement);
        outerJoinView.addListener(listener);

        return outerJoinView;
    }

    private void sendEvent(Object event)
    {
        epService.getEPRuntime().sendEvent(event);
    }
}
