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

package com.espertech.esper.regression.view;

import com.espertech.esper.client.*;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.collection.UniformPair;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestGroupByEventPerGroup extends TestCase
{
    private static String SYMBOL_DELL = "DELL";
    private static String SYMBOL_IBM = "IBM";

    private EPServiceProvider epService;
    private SupportUpdateListener listener;
    private EPStatement selectTestView;

    public void setUp()
    {
        listener = new SupportUpdateListener();
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getViewResources().setAllowMultipleExpiryPolicies(true);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
    }

    public void testNamedWindowDelete()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean_A", SupportBean_A.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean_B", SupportBean_B.class);
        epService.getEPAdministrator().createEPL("create window MyWindow.win:keepall() as select * from SupportBean");
        epService.getEPAdministrator().createEPL("insert into MyWindow select * from SupportBean");
        epService.getEPAdministrator().createEPL("on SupportBean_A a delete from MyWindow w where w.string = a.id");
        epService.getEPAdministrator().createEPL("on SupportBean_B delete from MyWindow");

        String fields[] = "string,mysum".split(",");
        String viewExpr = "@Hint('DISABLE_RECLAIM_GROUP') select string, sum(intPrimitive) as mysum from MyWindow group by string order by string";
        selectTestView = epService.getEPAdministrator().createEPL(viewExpr);
        selectTestView.addListener(listener);

        runAssertion(fields);

        selectTestView.destroy();
        epService.getEPRuntime().sendEvent(new SupportBean_B("delete"));

        viewExpr = "select string, sum(intPrimitive) as mysum from MyWindow group by string order by string";
        selectTestView = epService.getEPAdministrator().createEPL(viewExpr);
        selectTestView.addListener(listener);

        runAssertion(fields);
    }

    public void testUnboundStreamIterate()
    {
        // After the oldest group is 60 second old, reclaim group older then  30 seconds
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        EPStatement stmtOne = epService.getEPAdministrator().createEPL("select string, count(*) from SupportBean group by string");
        stmtOne.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("G1", 10));
        epService.getEPRuntime().sendEvent(new SupportBean("G2", 10));
        EventBean[] events = ArrayAssertionUtil.iteratorToArray(stmtOne.iterator());
        assertEquals(1, events.length);

        stmtOne.destroy();
        stmtOne = epService.getEPAdministrator().createEPL("select string, count(*) from SupportBean group by string output snapshot every 2 events");
        stmtOne.addListener(listener);
        
        epService.getEPRuntime().sendEvent(new SupportBean("G1", 10));
        epService.getEPRuntime().sendEvent(new SupportBean("G2", 10));
        events = ArrayAssertionUtil.iteratorToArray(stmtOne.iterator());
        assertEquals(1, events.length);
    }

    public void testUnboundStreamUnlimitedKey()
    {
        // ESPER-396 Unbound stream and aggregating/grouping by unlimited key (i.e. timestamp) configurable state drop
        sendTimer(0);

        // After the oldest group is 60 second old, reclaim group older then  30 seconds
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        EPStatement stmtOne = epService.getEPAdministrator().createEPL("@Hint('reclaim_group_aged=30,reclaim_group_freq=5') select longPrimitive, count(*) from SupportBean group by longPrimitive");
        stmtOne.addListener(listener);

        for (int i = 0; i < 1000; i++)
        {
            sendTimer(1000 + i * 1000); // reduce factor if sending more events
            SupportBean event = new SupportBean();
            event.setLongPrimitive(i * 1000);
            epService.getEPRuntime().sendEvent(event);

            //if (i % 100000 == 0)
            //{
            //    System.out.println("Sending event number " + i);
            //}
        }
        
        listener.reset();

        for (int i = 0; i < 964; i++)
        {
            SupportBean event = new SupportBean();
            event.setLongPrimitive(i * 1000);
            epService.getEPRuntime().sendEvent(event);
            assertEquals("Failed at " + i, 1L, listener.assertOneGetNewAndReset().get("count(*)"));
        }

        for (int i = 965; i < 1000; i++)
        {
            SupportBean event = new SupportBean();
            event.setLongPrimitive(i * 1000);
            epService.getEPRuntime().sendEvent(event);
            assertEquals("Failed at " + i, 2L, listener.assertOneGetNewAndReset().get("count(*)"));
        }

        // no frequency provided
        epService.getEPAdministrator().createEPL("@Hint('reclaim_group_aged=30') select longPrimitive, count(*) from SupportBean group by longPrimitive");
        epService.getEPRuntime().sendEvent(new SupportBean());

        epService.getEPAdministrator().createEPL("create variable int myAge = 10");
        epService.getEPAdministrator().createEPL("create variable int myFreq = 10");

        stmtOne.destroy();
        stmtOne = epService.getEPAdministrator().createEPL("@Hint('reclaim_group_aged=myAge,reclaim_group_freq=myFreq') select longPrimitive, count(*) from SupportBean group by longPrimitive");
        stmtOne.addListener(listener);

        for (int i = 0; i < 1000; i++)
        {
            sendTimer(2000000 + 1000 + i * 1000); // reduce factor if sending more events
            SupportBean event = new SupportBean();
            event.setLongPrimitive(i * 1000);
            epService.getEPRuntime().sendEvent(event);

            if (i == 500)
            {
                epService.getEPRuntime().setVariableValue("myAge", 60);
                epService.getEPRuntime().setVariableValue("myFreq", 90);
            }

            /*
            if (i % 100000 == 0)
            {
                System.out.println("Sending event number " + i);
            }
            */
        }

        listener.reset();

        for (int i = 0; i < 890; i++)
        {
            SupportBean event = new SupportBean();
            event.setLongPrimitive(i * 1000);
            epService.getEPRuntime().sendEvent(event);
            assertEquals("Failed at " + i, 1L, listener.assertOneGetNewAndReset().get("count(*)"));
        }

        for (int i = 891; i < 1000; i++)
        {
            SupportBean event = new SupportBean();
            event.setLongPrimitive(i * 1000);
            epService.getEPRuntime().sendEvent(event);
            assertEquals("Failed at " + i, 2L, listener.assertOneGetNewAndReset().get("count(*)"));
        }        

        stmtOne.destroy();

        // invalid tests
        tryInvalid("@Hint('reclaim_group_aged=xyz') select longPrimitive, count(*) from SupportBean group by longPrimitive",
                   "Error starting statement: Failed to parse hint parameter value 'xyz' as a double-typed seconds value or variable name [@Hint('reclaim_group_aged=xyz') select longPrimitive, count(*) from SupportBean group by longPrimitive]");
        tryInvalid("@Hint('reclaim_group_aged=30,reclaim_group_freq=xyz') select longPrimitive, count(*) from SupportBean group by longPrimitive",
                   "Error starting statement: Failed to parse hint parameter value 'xyz' as a double-typed seconds value or variable name [@Hint('reclaim_group_aged=30,reclaim_group_freq=xyz') select longPrimitive, count(*) from SupportBean group by longPrimitive]");
        epService.getEPAdministrator().getConfiguration().addVariable("MyVar", String.class, "");
        tryInvalid("@Hint('reclaim_group_aged=MyVar') select longPrimitive, count(*) from SupportBean group by longPrimitive",
                   "Error starting statement: Variable type of variable 'MyVar' is not numeric [@Hint('reclaim_group_aged=MyVar') select longPrimitive, count(*) from SupportBean group by longPrimitive]");
        tryInvalid("@Hint('reclaim_group_aged=-30,reclaim_group_freq=30') select longPrimitive, count(*) from SupportBean group by longPrimitive",
                   "Error starting statement: Hint parameter value '-30' is an invalid value, expecting a double-typed seconds value or variable name [@Hint('reclaim_group_aged=-30,reclaim_group_freq=30') select longPrimitive, count(*) from SupportBean group by longPrimitive]");

        /**
         * Test natural timer - long running test to be commented out.
         */
        /*
        epService = EPServiceProviderManager.getProvider(this.getClass().getName());
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        EPStatement stmtOne = epService.getEPAdministrator().createEPL("@Hint('reclaim_group_aged=1,reclaim_group_freq=1') select longPrimitive, count(*) from SupportBean group by longPrimitive");

        int count = 0;
        while(true)
        {
            SupportBean event = new SupportBean();
            event.setLongPrimitive(System.currentTimeMillis());
            epService.getEPRuntime().sendEvent(new SupportBean());
            count++;
            if (count % 100000 == 0)
            {
                System.out.println("Sending event number " + count);
            }
        }
        */
    }

    private void runAssertion(String[] fields)
    {
        epService.getEPRuntime().sendEvent(new SupportBean("A", 100));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"A", 100});

        epService.getEPRuntime().sendEvent(new SupportBean("B", 20));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"B", 20});

        epService.getEPRuntime().sendEvent(new SupportBean("A", 101));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"A", 201});

        epService.getEPRuntime().sendEvent(new SupportBean("B", 21));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"B", 41});
        ArrayAssertionUtil.assertEqualsExactOrder(selectTestView.iterator(), fields, new Object[][] {{"A", 201}, {"B", 41}});

        epService.getEPRuntime().sendEvent(new SupportBean_A("A"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"A", null});
        ArrayAssertionUtil.assertEqualsExactOrder(selectTestView.iterator(), fields, new Object[][] {{"B", 41}});

        epService.getEPRuntime().sendEvent(new SupportBean("A", 102));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"A", 102});
        ArrayAssertionUtil.assertEqualsExactOrder(selectTestView.iterator(), fields, new Object[][] {{"A", 102}, {"B", 41}});

        epService.getEPRuntime().sendEvent(new SupportBean_A("B"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"B", null});
        ArrayAssertionUtil.assertEqualsExactOrder(selectTestView.iterator(), fields, new Object[][] {{"A", 102}});

        epService.getEPRuntime().sendEvent(new SupportBean("B", 22));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"B", 22});
        ArrayAssertionUtil.assertEqualsExactOrder(selectTestView.iterator(), fields, new Object[][] {{"A", 102}, {"B", 22}});
    }

    public void testAggregateGroupedProps()
    {
        // test for ESPER-185
        String fields[] = "mycount".split(",");
        String viewExpr = "select irstream count(price) as mycount " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:length(5) " +
                          "group by price";

        selectTestView = epService.getEPAdministrator().createEPL(viewExpr);
        selectTestView.addListener(listener);

        sendEvent(SYMBOL_DELL, 10);
        ArrayAssertionUtil.assertProps(listener.getLastNewData()[0], fields, new Object[] {1L});
        ArrayAssertionUtil.assertProps(listener.getLastOldData()[0], fields, new Object[] {0L});
        ArrayAssertionUtil.assertEqualsExactOrder(selectTestView.iterator(), fields, new Object[][] {{1L}});
        listener.reset();

        sendEvent(SYMBOL_DELL, 11);
        ArrayAssertionUtil.assertProps(listener.getLastNewData()[0], fields, new Object[] {1L});
        ArrayAssertionUtil.assertProps(listener.getLastOldData()[0], fields, new Object[] {0L});
        ArrayAssertionUtil.assertEqualsExactOrder(selectTestView.iterator(), fields, new Object[][] {{1L}, {1L}});
        listener.reset();

        sendEvent(SYMBOL_IBM, 10);
        ArrayAssertionUtil.assertProps(listener.getLastNewData()[0], fields, new Object[] {2L});
        ArrayAssertionUtil.assertProps(listener.getLastOldData()[0], fields, new Object[] {1L});
        ArrayAssertionUtil.assertEqualsExactOrder(selectTestView.iterator(), fields, new Object[][] {{2L}, {1L}});
        listener.reset();
    }

    public void testAggregateGroupedPropsPerGroup()
    {
        // test for ESPER-185
        String fields[] = "mycount".split(",");
        String viewExpr = "select irstream count(price) as mycount " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:length(5) " +
                          "group by symbol, price";

        selectTestView = epService.getEPAdministrator().createEPL(viewExpr);
        selectTestView.addListener(listener);

        sendEvent(SYMBOL_DELL, 10);
        ArrayAssertionUtil.assertProps(listener.getLastNewData()[0], fields, new Object[] {1L});
        ArrayAssertionUtil.assertProps(listener.getLastOldData()[0], fields, new Object[] {0L});
        ArrayAssertionUtil.assertEqualsExactOrder(selectTestView.iterator(), fields, new Object[][] {{1L}});
        listener.reset();

        sendEvent(SYMBOL_DELL, 11);
        ArrayAssertionUtil.assertProps(listener.getLastNewData()[0], fields, new Object[] {1L});
        ArrayAssertionUtil.assertProps(listener.getLastOldData()[0], fields, new Object[] {0L});
        ArrayAssertionUtil.assertEqualsExactOrder(selectTestView.iterator(), fields, new Object[][] {{1L}, {1L}});
        listener.reset();

        sendEvent(SYMBOL_DELL, 10);
        ArrayAssertionUtil.assertProps(listener.getLastNewData()[0], fields, new Object[] {2L});
        ArrayAssertionUtil.assertProps(listener.getLastOldData()[0], fields, new Object[] {1L});
        ArrayAssertionUtil.assertEqualsExactOrder(selectTestView.iterator(), fields, new Object[][] {{2L}, {1L}});
        listener.reset();

        sendEvent(SYMBOL_IBM, 10);
        ArrayAssertionUtil.assertProps(listener.getLastNewData()[0], fields, new Object[] {1L});
        ArrayAssertionUtil.assertProps(listener.getLastOldData()[0], fields, new Object[] {0L});
        ArrayAssertionUtil.assertEqualsExactOrder(selectTestView.iterator(), fields, new Object[][] {{2L}, {1L}, {1L}});
        listener.reset();
    }

    public void testAggregationOverGroupedProps()
    {
        // test for ESPER-185
        String fields[] = "symbol,price,mycount".split(",");
        String viewExpr = "select irstream symbol,price,count(price) as mycount " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:length(5) " +
                          "group by symbol, price order by symbol asc";

        selectTestView = epService.getEPAdministrator().createEPL(viewExpr);
        selectTestView.addListener(listener);

        sendEvent(SYMBOL_DELL, 10);
        ArrayAssertionUtil.assertProps(listener.getLastNewData()[0], fields, new Object[] {"DELL", 10.0, 1L});
        ArrayAssertionUtil.assertProps(listener.getLastOldData()[0], fields, new Object[] {"DELL", 10.0, 0L});
        ArrayAssertionUtil.assertEqualsExactOrder(selectTestView.iterator(), fields, new Object[][] {{"DELL", 10.0, 1L}});
        listener.reset();

        sendEvent(SYMBOL_DELL, 11);
        ArrayAssertionUtil.assertProps(listener.getLastNewData()[0], fields, new Object[] {"DELL", 11.0, 1L});
        ArrayAssertionUtil.assertProps(listener.getLastOldData()[0], fields, new Object[] {"DELL", 11.0, 0L});
        ArrayAssertionUtil.assertEqualsExactOrder(selectTestView.iterator(), fields, new Object[][] {{"DELL", 10.0, 1L},{"DELL", 11.0, 1L}});
        listener.reset();

        sendEvent(SYMBOL_DELL, 10);
        ArrayAssertionUtil.assertProps(listener.getLastNewData()[0], fields, new Object[] {"DELL", 10.0, 2L});
        ArrayAssertionUtil.assertProps(listener.getLastOldData()[0], fields, new Object[] {"DELL", 10.0, 1L});
        ArrayAssertionUtil.assertEqualsExactOrder(selectTestView.iterator(), fields, new Object[][] {{"DELL", 10.0, 2L},{"DELL", 11.0, 1L}});
        listener.reset();

        sendEvent(SYMBOL_IBM, 5);
        assertEquals(1, listener.getNewDataList().size());
        ArrayAssertionUtil.assertProps(listener.getLastNewData()[0], fields, new Object[] {"IBM", 5.0, 1L});
        ArrayAssertionUtil.assertProps(listener.getLastOldData()[0], fields, new Object[] {"IBM", 5.0, 0L});
        ArrayAssertionUtil.assertEqualsExactOrder(selectTestView.iterator(), fields, new Object[][] {{"DELL", 10.0, 2L},{"DELL", 11.0, 1L}, {"IBM", 5.0, 1L}});
        listener.reset();

        sendEvent(SYMBOL_IBM, 5);
        assertEquals(1, listener.getLastNewData().length);
        ArrayAssertionUtil.assertProps(listener.getLastNewData()[0], fields, new Object[] {"IBM", 5.0, 2L});
        ArrayAssertionUtil.assertProps(listener.getLastOldData()[0], fields, new Object[] {"IBM", 5.0, 1L});
        ArrayAssertionUtil.assertEqualsExactOrder(selectTestView.iterator(), fields, new Object[][] {{"DELL", 10.0, 2L},{"DELL", 11.0, 1L}, {"IBM", 5.0, 2L}});
        listener.reset();

        sendEvent(SYMBOL_IBM, 5);
        assertEquals(2, listener.getLastNewData().length);
        ArrayAssertionUtil.assertProps(listener.getLastNewData()[1], fields, new Object[] {"IBM", 5.0, 3L});
        ArrayAssertionUtil.assertProps(listener.getLastOldData()[1], fields, new Object[] {"IBM", 5.0, 2L});
        ArrayAssertionUtil.assertProps(listener.getLastNewData()[0], fields, new Object[] {"DELL", 10.0, 1L});
        ArrayAssertionUtil.assertProps(listener.getLastOldData()[0], fields, new Object[] {"DELL", 10.0, 2L});
        ArrayAssertionUtil.assertEqualsExactOrder(selectTestView.iterator(), fields, new Object[][] {{"DELL", 11.0, 1L},{"DELL", 10.0, 1L}, {"IBM", 5.0, 3L}});
        listener.reset();

        sendEvent(SYMBOL_IBM, 5);
        assertEquals(2, listener.getLastNewData().length);
        ArrayAssertionUtil.assertProps(listener.getLastNewData()[1], fields, new Object[] {"IBM", 5.0, 4L});
        ArrayAssertionUtil.assertProps(listener.getLastOldData()[1], fields, new Object[] {"IBM", 5.0, 3L});
        ArrayAssertionUtil.assertProps(listener.getLastNewData()[0], fields, new Object[] {"DELL", 11.0, 0L});
        ArrayAssertionUtil.assertProps(listener.getLastOldData()[0], fields, new Object[] {"DELL", 11.0, 1L});
        ArrayAssertionUtil.assertEqualsExactOrder(selectTestView.iterator(), fields, new Object[][] {{"DELL", 10.0, 1L}, {"IBM", 5.0, 4L}});
        listener.reset();
    }

    public void testSumOneView()
    {
        String viewExpr = "select irstream symbol," +
                                 "sum(price) as mySum," +
                                 "avg(price) as myAvg " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:length(3) " +
                          "where symbol='DELL' or symbol='IBM' or symbol='GE' " +
                          "group by symbol";

        selectTestView = epService.getEPAdministrator().createEPL(viewExpr);
        selectTestView.addListener(listener);

        runAssertion();
    }

    public void testSumJoin()
    {
        String viewExpr = "select irstream symbol," +
                                 "sum(price) as mySum," +
                                 "avg(price) as myAvg " +
                          "from " + SupportBeanString.class.getName() + ".win:length(100) as one, " +
                                    SupportMarketDataBean.class.getName() + ".win:length(3) as two " +
                          "where (symbol='DELL' or symbol='IBM' or symbol='GE') " +
                          "       and one.string = two.symbol " +
                          "group by symbol";

        selectTestView = epService.getEPAdministrator().createEPL(viewExpr);
        selectTestView.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBeanString(SYMBOL_DELL));
        epService.getEPRuntime().sendEvent(new SupportBeanString(SYMBOL_IBM));
        epService.getEPRuntime().sendEvent(new SupportBeanString("AAA"));

        runAssertion();
    }

    public void testUniqueInBatch()
    {
        String stmtOne = "insert into MyStream select symbol, price from " +
                SupportMarketDataBean.class.getName() + ".win:time_batch(1 sec)";
        epService.getEPAdministrator().createEPL(stmtOne);
        sendTimer(0);

        String viewExpr = "select symbol " +
                          "from MyStream.win:time_batch(1 sec).std:unique(symbol) " +
                          "group by symbol";

        selectTestView = epService.getEPAdministrator().createEPL(viewExpr);
        selectTestView.addListener(listener);

        sendEvent("IBM", 100);
        sendEvent("IBM", 101);
        sendEvent("IBM", 102);
        sendTimer(1000);
        assertFalse(listener.isInvoked());

        sendTimer(2000);
        UniformPair<EventBean[]> received = listener.getDataListsFlattened();
        assertEquals("IBM", received.getFirst()[0].get("symbol"));
    }

    private void runAssertion()
    {
        String[] fields = new String[] {"symbol", "mySum", "myAvg"};
        ArrayAssertionUtil.assertEqualsAnyOrder(selectTestView.iterator(), fields, null);

        // assert select result type
        assertEquals(String.class, selectTestView.getEventType().getPropertyType("symbol"));
        assertEquals(Double.class, selectTestView.getEventType().getPropertyType("mySum"));
        assertEquals(Double.class, selectTestView.getEventType().getPropertyType("myAvg"));

        sendEvent(SYMBOL_DELL, 10);
        assertEvents(SYMBOL_DELL,
                null, null,
                10d, 10d);
        ArrayAssertionUtil.assertEqualsAnyOrder(selectTestView.iterator(), fields, new Object[][] {{"DELL", 10d, 10d}});

        sendEvent(SYMBOL_DELL, 20);
        assertEvents(SYMBOL_DELL,
                10d, 10d,
                30d, 15d);
        ArrayAssertionUtil.assertEqualsAnyOrder(selectTestView.iterator(), fields, new Object[][] {{"DELL", 30d, 15d}});

        sendEvent(SYMBOL_DELL, 100);
        assertEvents(SYMBOL_DELL,
                30d, 15d,
                130d, 130d/3d);
        ArrayAssertionUtil.assertEqualsAnyOrder(selectTestView.iterator(), fields, new Object[][] {{"DELL", 130d, 130d/3d}});

        sendEvent(SYMBOL_DELL, 50);
        assertEvents(SYMBOL_DELL,
                130d, 130/3d,
                170d, 170/3d);    // 20 + 100 + 50
        ArrayAssertionUtil.assertEqualsAnyOrder(selectTestView.iterator(), fields, new Object[][] {{"DELL", 170d, 170d/3d}});

        sendEvent(SYMBOL_DELL, 5);
        assertEvents(SYMBOL_DELL,
                170d, 170/3d,
                155d, 155/3d);    // 100 + 50 + 5
        ArrayAssertionUtil.assertEqualsAnyOrder(selectTestView.iterator(), fields, new Object[][] {{"DELL", 155d, 155d/3d}});

        sendEvent("AAA", 1000);
        assertEvents(SYMBOL_DELL,
                155d, 155d/3,
                55d, 55d/2);    // 50 + 5
        ArrayAssertionUtil.assertEqualsAnyOrder(selectTestView.iterator(), fields, new Object[][] {{"DELL", 55d, 55d/2d}});

        sendEvent(SYMBOL_IBM, 70);
        assertEvents(SYMBOL_DELL,
                55d, 55/2d,
                5, 5,
                SYMBOL_IBM,
                null, null,
                70, 70);    // Dell:5
        ArrayAssertionUtil.assertEqualsAnyOrder(selectTestView.iterator(), fields, new Object[][] {
                {"DELL", 5d, 5d}, {"IBM", 70d, 70d}});

        sendEvent("AAA", 2000);
        assertEvents(SYMBOL_DELL,
                5d, 5d,
                null, null);
        ArrayAssertionUtil.assertEqualsAnyOrder(selectTestView.iterator(), fields, new Object[][] {
                {"IBM", 70d, 70d}});

        sendEvent("AAA", 3000);
        assertFalse(listener.isInvoked());

        sendEvent("AAA", 4000);
        assertEvents(SYMBOL_IBM,
                70d, 70d,
                null, null);
        ArrayAssertionUtil.assertEqualsAnyOrder(selectTestView.iterator(), fields, null);
    }

    private void assertEvents(String symbol,
                              Double oldSum, Double oldAvg,
                              Double newSum, Double newAvg)
    {
        EventBean[] oldData = listener.getLastOldData();
        EventBean[] newData = listener.getLastNewData();

        assertEquals(1, oldData.length);
        assertEquals(1, newData.length);

        assertEquals(symbol, oldData[0].get("symbol"));
        assertEquals(oldSum, oldData[0].get("mySum"));
        assertEquals(oldAvg, oldData[0].get("myAvg"));

        assertEquals(symbol, newData[0].get("symbol"));
        assertEquals(newSum, newData[0].get("mySum"));
        assertEquals("newData myAvg wrong", newAvg, newData[0].get("myAvg"));

        listener.reset();
        assertFalse(listener.isInvoked());
    }

    private void assertEvents(String symbolOne,
                              Double oldSumOne, Double oldAvgOne,
                              double newSumOne, double newAvgOne,
                              String symbolTwo,
                              Double oldSumTwo, Double oldAvgTwo,
                              double newSumTwo, double newAvgTwo)
    {
        EventBean[] oldData = listener.getLastOldData();
        EventBean[] newData = listener.getLastNewData();

        assertEquals(2, oldData.length);
        assertEquals(2, newData.length);

        int indexOne = 0;
        int indexTwo = 1;
        if (oldData[0].get("symbol").equals(symbolTwo))
        {
            indexTwo = 0;
            indexOne = 1;
        }
        assertEquals(newSumOne, newData[indexOne].get("mySum"));
        assertEquals(newSumTwo, newData[indexTwo].get("mySum"));
        assertEquals(oldSumOne, oldData[indexOne].get("mySum"));
        assertEquals(oldSumTwo, oldData[indexTwo].get("mySum"));

        assertEquals(newAvgOne, newData[indexOne].get("myAvg"));
        assertEquals(newAvgTwo, newData[indexTwo].get("myAvg"));
        assertEquals(oldAvgOne, oldData[indexOne].get("myAvg"));
        assertEquals(oldAvgTwo, oldData[indexTwo].get("myAvg"));

        listener.reset();
        assertFalse(listener.isInvoked());
    }

    private void sendEvent(String symbol, double price)
	{
	    SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, null);
	    epService.getEPRuntime().sendEvent(bean);
	}

    private void sendTimer(long timeInMSec)
    {
        CurrentTimeEvent event = new CurrentTimeEvent(timeInMSec);
        EPRuntime runtime = epService.getEPRuntime();
        runtime.sendEvent(event);
    }

    private void tryInvalid(String epl, String message)
    {
        try
        {
            epService.getEPAdministrator().createEPL(epl);
            fail();
        }
        catch (EPStatementException ex)
        {
            assertEquals(message, ex.getMessage());
        }
    }

    private static final Log log = LogFactory.getLog(TestGroupByEventPerGroup.class);
}
