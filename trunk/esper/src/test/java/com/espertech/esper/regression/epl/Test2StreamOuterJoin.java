package com.espertech.esper.regression.epl;

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.client.soda.*;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.support.bean.SupportBean_S0;
import com.espertech.esper.support.bean.SupportBean_S1;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.type.OuterJoinType;
import com.espertech.esper.util.SerializableObjectCopier;

public class Test2StreamOuterJoin extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener updateListener;
    private EPStatement outerJoinView;
    private String[] fields = new String[] {"s0.id", "s0.p00", "s1.id", "s1.p10"};

    private SupportBean_S0 eventsS0[] = new SupportBean_S0[15];
    private SupportBean_S1 eventsS1[] = new SupportBean_S1[15];

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
        updateListener = new SupportUpdateListener();

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

    public void testFullOuterIteratorGroupBy()
    {
        String stmt = "select string, intPrimitive, symbol, volume " +
                      "from " + SupportMarketDataBean.class.getName() + ".win:keepall() " +
                      "full outer join " +
                      SupportBean.class.getName() + ".std:groupby(string, intPrimitive).win:length(2) " +
                      "on string = symbol group by string, intPrimitive, symbol " +
                      "order by string, intPrimitive, symbol, volume";

        outerJoinView = epService.getEPAdministrator().createEPL(stmt);
        outerJoinView.addListener(updateListener);

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
        compareEvent(updateListener.assertOneGetNewAndReset(), 100, "0", null, null);
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{100, "0", null, null}});

        // Send S1[1]
        sendEvent(eventsS1[1]);
        compareEvent(updateListener.assertOneGetNewAndReset(), null, null, 201, "1");
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{100, "0", null, null},
                                {null, null, 201, "1"}});

        // Send S1[2] and S0[2]
        sendEvent(eventsS1[2]);
        compareEvent(updateListener.assertOneGetNewAndReset(), null, null, 202, "2");
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{100, "0", null, null},
                                {null, null, 201, "1"},
                                {null, null, 202, "2"} });

        sendEvent(eventsS0[2]);
        compareEvent(updateListener.assertOneGetNewAndReset(), 102, "2", 202, "2");
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{100, "0", null, null},
                                {null, null, 201, "1"},
                                {102, "2", 202, "2"} });

        // Send S0[3] and S1[3]
        sendEvent(eventsS0[3]);
        compareEvent(updateListener.assertOneGetNewAndReset(), 103, "3", null, null);
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{100, "0", null, null},
                                {null, null, 201, "1"},
                                {102, "2", 202, "2"},
                                {103, "3", null, null}});
        sendEvent(eventsS1[3]);
        compareEvent(updateListener.assertOneGetNewAndReset(), 103, "3", 203, "3");
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{100, "0", null, null},
                                {null, null, 201, "1"},
                                {102, "2", 202, "2"},
                                {103, "3", 203, "3"}});

        // Send S0[4], pushes S0[0] out of window
        sendEvent(eventsS0[4]);
        EventBean oldEvent = updateListener.getLastOldData()[0];
        EventBean newEvent = updateListener.getLastNewData()[0];
        compareEvent(oldEvent, 100, "0", null, null);
        compareEvent(newEvent, 104, "4", null, null);
        updateListener.reset();
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{null, null, 201, "1"},
                                {102, "2", 202, "2"},
                                {103, "3", 203, "3"},
                                {104, "4", null, null}});

        // Send S1[4]
        sendEvent(eventsS1[4]);
        compareEvent(updateListener.assertOneGetNewAndReset(), 104, "4", 204, "4");
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{null, null, 201, "1"},
                                {102, "2", 202, "2"},
                                {103, "3", 203, "3"},
                                {104, "4", 204, "4"}});

        // Send S1[5]
        sendEvent(eventsS1[5]);
        compareEvent(updateListener.assertOneGetNewAndReset(), null, null, 205, "5");
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{null, null, 201, "1"},
                                {102, "2", 202, "2"},
                                {103, "3", 203, "3"},
                                {104, "4", 204, "4"},
                                {null, null, 205, "5"}});

        // Send S1[6], pushes S1[1] out of window
        sendEvent(eventsS1[5]);
        oldEvent = updateListener.getLastOldData()[0];
        newEvent = updateListener.getLastNewData()[0];
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
        outerJoinView.addListener(updateListener);

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
        outerJoinView.addListener(updateListener);

        assertMultiColumnLeft();
    }

    private void assertMultiColumnLeft()
    {
        String fields[] = "s0.id, s0.p00, s0.p01, s1.id, s1.p10, s1.p11".split(",");
        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, "A_1", "B_1"));
        ArrayAssertionUtil.assertProps(updateListener.assertOneGetNewAndReset(), fields, new Object[] {1, "A_1", "B_1", null, null, null});

        epService.getEPRuntime().sendEvent(new SupportBean_S1(2, "A_1", "B_1"));
        ArrayAssertionUtil.assertProps(updateListener.assertOneGetNewAndReset(), fields, new Object[] {1, "A_1", "B_1", 2, "A_1", "B_1"});

        epService.getEPRuntime().sendEvent(new SupportBean_S1(3, "A_2", "B_1"));
        assertFalse(updateListener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S1(4, "A_1", "B_2"));
        assertFalse(updateListener.isInvoked());
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
        outerJoinView.addListener(updateListener);

        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, "A_1", "B_1"));
        assertFalse(updateListener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S1(2, "A_1", "B_1"));
        ArrayAssertionUtil.assertProps(updateListener.assertOneGetNewAndReset(), fields, new Object[] {1, "A_1", "B_1", 2, "A_1", "B_1"});

        epService.getEPRuntime().sendEvent(new SupportBean_S1(3, "A_2", "B_1"));
        ArrayAssertionUtil.assertProps(updateListener.assertOneGetNewAndReset(), fields, new Object[] {null, null, null, 3, "A_2", "B_1"});

        epService.getEPRuntime().sendEvent(new SupportBean_S1(4, "A_1", "B_2"));
        ArrayAssertionUtil.assertProps(updateListener.assertOneGetNewAndReset(), fields, new Object[] {null, null, null, 4, "A_1", "B_2"});
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
        outerJoinView.addListener(updateListener);

        sendEvent("S1_1", 10, 20d);
        ArrayAssertionUtil.assertProps(updateListener.assertOneGetNewAndReset(), fields, new Object[] {null, "S1_1"});

        sendEvent("S0_2", 11, 22d);
        assertFalse(updateListener.isInvoked());

        sendEvent("S0_3", 11, 21d);
        assertFalse(updateListener.isInvoked());

        sendEvent("S0_4", 12, 21d);
        assertFalse(updateListener.isInvoked());

        sendEvent("S1_2", 11, 22d);
        ArrayAssertionUtil.assertProps(updateListener.assertOneGetNewAndReset(), fields, new Object[] {null, "S1_2"});

        sendEvent("S1_3", 22, 11d);
        ArrayAssertionUtil.assertProps(updateListener.assertOneGetNewAndReset(), fields, new Object[] {"S0_2", "S1_3"});

        sendEvent("S0_5", 22, 11d);
        ArrayAssertionUtil.assertProps(updateListener.assertOneGetNewAndReset(), fields, new Object[] {"S0_5", "S1_2"});
    }

    public void testRightOuterJoin()
    {
        setupStatement("right");

        // Send S0 events, no events expected
        sendEvent(eventsS0[0]);
        sendEvent(eventsS0[1]);
        assertFalse(updateListener.isInvoked());
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields, null);

        // Send S1[2]
        sendEvent(eventsS1[2]);
        EventBean event = updateListener.assertOneGetNewAndReset();
        compareEvent(event, null, null, 202, "2");
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{null, null, 202, "2"}});

        // Send S0[2] events, joined event expected
        sendEvent(eventsS0[2]);
        event = updateListener.assertOneGetNewAndReset();
        compareEvent(event, 102, "2", 202, "2");
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{102, "2", 202, "2"}});

        // Send S1[3]
        sendEvent(eventsS1[3]);
        event = updateListener.assertOneGetNewAndReset();
        compareEvent(event, null, null, 203, "3");
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{102, "2", 202, "2"},
                                {null, null, 203, "3"}});

        // Send some more S0 events
        sendEvent(eventsS0[3]);
        event = updateListener.assertOneGetNewAndReset();
        compareEvent(event, 103, "3", 203, "3");
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{102, "2", 202, "2"},
                                {103, "3", 203, "3"}});

        // Send some more S0 events
        sendEvent(eventsS0[4]);
        assertFalse(updateListener.isInvoked());
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{102, "2", 202, "2"},
                                {103, "3", 203, "3"}});

        // Push S0[2] out of the window
        sendEvent(eventsS0[5]);
        event = updateListener.assertOneGetOldAndReset();
        compareEvent(event, 102, "2", 202, "2");
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{null, null, 202, "2"},
                                {103, "3", 203, "3"}});

        // Some more S1 events
        sendEvent(eventsS1[6]);
        compareEvent(updateListener.assertOneGetNewAndReset(), null, null, 206, "6");
        sendEvent(eventsS1[7]);
        compareEvent(updateListener.assertOneGetNewAndReset(), null, null, 207, "7");
        sendEvent(eventsS1[8]);
        compareEvent(updateListener.assertOneGetNewAndReset(), null, null, 208, "8");
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{null, null, 202, "2"},
                                {103, "3", 203, "3"},
                                {null, null, 206, "6"},
                                {null, null, 207, "7"},
                                {null, null, 208, "8"}});

        // Push S1[2] out of the window
        sendEvent(eventsS1[9]);
        EventBean oldEvent = updateListener.getLastOldData()[0];
        EventBean newEvent = updateListener.getLastNewData()[0];
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
        assertNull(updateListener.getLastNewData());    // No events expected
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields, null);

        // Send S0 event, expect event back from outer join
        sendEvent(eventsS0[2]);
        EventBean event = updateListener.assertOneGetNewAndReset();
        compareEvent(event, 102, "2", null, null);
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{102, "2", null, null}});

        // Send S1 event matching S0, expect event back
        sendEvent(eventsS1[2]);
        event = updateListener.assertOneGetNewAndReset();
        compareEvent(event, 102, "2", 202, "2");
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{102, "2", 202, "2"}});

        // Send some more unmatched events
        sendEvent(eventsS1[4]);
        sendEvent(eventsS1[5]);
        sendEvent(eventsS1[6]);
        assertNull(updateListener.getLastNewData());    // No events expected
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{102, "2", 202, "2"}});

        // Send event, expect a join result
        sendEvent(eventsS0[5]);
        event = updateListener.assertOneGetNewAndReset();
        compareEvent(event, 105, "5", 205, "5");
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{102, "2", 202, "2"},
                                {105, "5", 205, "5"}});

        // Let S1[2] go out of the window (lenght 5), expected old join event
        sendEvent(eventsS1[7]);
        sendEvent(eventsS1[8]);
        event = updateListener.assertOneGetOldAndReset();
        compareEvent(event, 102, "2", 202, "2");
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{102, "2", null, null},
                                {105, "5", 205, "5"}});

        // S0[9] should generate an outer join event
        sendEvent(eventsS0[9]);
        event = updateListener.assertOneGetNewAndReset();
        compareEvent(event, 109, "9", null, null);
        ArrayAssertionUtil.assertEqualsAnyOrder(outerJoinView.iterator(), fields,
                new Object[][] {{102, "2", null, null},
                                {109, "9", null, null},
                                {105, "5", 205, "5"}});

        // S0[2] Should leave the window (length 3), should get OLD and NEW event
        sendEvent(eventsS0[10]);
        EventBean oldEvent = updateListener.getLastOldData()[0];
        EventBean newEvent = updateListener.getLastNewData()[0];
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
        outerJoinView.addListener(updateListener);

        return outerJoinView;
    }

    private void sendEvent(Object event)
    {
        epService.getEPRuntime().sendEvent(event);
    }
}
