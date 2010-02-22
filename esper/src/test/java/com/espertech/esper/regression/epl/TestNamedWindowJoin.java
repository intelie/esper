package com.espertech.esper.regression.epl;

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;

public class TestNamedWindowJoin extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listenerWindow;
    private SupportUpdateListener listenerWindowTwo;
    private SupportUpdateListener listenerStmtOne;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listenerWindow = new SupportUpdateListener();
        listenerWindowTwo = new SupportUpdateListener();
        listenerStmtOne = new SupportUpdateListener();
    }

    public void testRightOuterJoinLateStart()
    {
        // Test for ESPER-186 Iterator not honoring order by clause for grouped join query with output-rate clause
        // Test for ESPER-187 Join of two or more named windows on late start may not return correct aggregation state on iterate

        // create window for Leave events
        String stmtTextCreate = "create window WindowLeave.win:time(6000) as select timeLeave, id, location from " + SupportQueueLeave.class.getName();
        EPStatement stmtNamedOne = epService.getEPAdministrator().createEPL(stmtTextCreate);
        String stmtTextInsert = "insert into WindowLeave select timeLeave, id, location from " + SupportQueueLeave.class.getName();
        epService.getEPAdministrator().createEPL(stmtTextInsert);

        // create second window for enter events
        stmtTextCreate = "create window WindowEnter.win:time(6000) as select location, sku, timeEnter, id from " + SupportQueueEnter.class.getName();
        EPStatement stmtNamedTwo = epService.getEPAdministrator().createEPL(stmtTextCreate);
        stmtTextInsert = "insert into WindowEnter select location, sku, timeEnter, id from " + SupportQueueEnter.class.getName();
        epService.getEPAdministrator().createEPL(stmtTextInsert);

        // fill data
        for (int i = 0; i < 8; i++)
        {
            String location = Integer.toString(i / 2);
            epService.getEPRuntime().sendEvent(new SupportQueueLeave(i + 1, location, 247));
        }
        /** Comment in for debug
        System.out.println("Leave events:");
        for (Iterator<EventBean> it = stmtNamedOne.iterator(); it.hasNext();)
        {
            EventBean event = it.next();
            System.out.println(event.get("timeLeave") +
                               " " + event.get("id") +
                    " " + event.get("location"));
        }
         */

        for (int i = 0; i < 10; i++)
        {
            String location = Integer.toString(i / 2);
            String sku = (i % 2 == 0) ? "166583" : "169254";
            epService.getEPRuntime().sendEvent(new SupportQueueEnter(i + 1, location, sku, 123));
        }
        /** Comment in for debug
        System.out.println("Enter events:");
        for (Iterator<EventBean> it = stmtNamedTwo.iterator(); it.hasNext();)
        {
            EventBean event = it.next();
            System.out.println(event.get("timeEnter") +
                               " " + event.get("id") +
                    " " + event.get("sku") +
                    " " + event.get("location"));
        }
         */

        String stmtTextOne = "select s1.location as loc, sku, avg((coalesce(timeLeave, 250) - timeEnter)) as avgTime, " +
                          "count(timeEnter) as cntEnter, count(timeLeave) as cntLeave, (count(timeEnter) - count(timeLeave)) as diff " +
                          "from WindowLeave as s0 right outer join WindowEnter as s1 " +
                          "on s0.id = s1.id and s0.location = s1.location " +
                          "group by s1.location, sku " +
                          "output every 1.0 seconds " +
                          "order by s1.location, sku";
        EPStatement stmtOne = epService.getEPAdministrator().createEPL(stmtTextOne);

        String stmtTextTwo = "select s1.location as loc, sku, avg((coalesce(timeLeave, 250) - timeEnter)) as avgTime, " +
                          "count(timeEnter) as cntEnter, count(timeLeave) as cntLeave, (count(timeEnter) - count(timeLeave)) as diff " +
                          "from WindowEnter as s1 left outer join WindowLeave as s0 " +
                          "on s0.id = s1.id and s0.location = s1.location " +
                          "group by s1.location, sku " +
                          "output every 1.0 seconds " +
                          "order by s1.location, sku";
        EPStatement stmtTwo = epService.getEPAdministrator().createEPL(stmtTextTwo);

        /** Comment in for debugging
        System.out.println("Statement 1");
        for (Iterator<EventBean> it = stmtOne.iterator(); it.hasNext();)
        {
            EventBean event = it.next();
            System.out.println("loc " + event.get("loc") +
                               " sku " + event.get("sku") +
                    " avgTime " + event.get("avgTime") +
                    " cntEnter " + event.get("cntEnter") +
                    " cntLeave " + event.get("cntLeave") +
                    " diff " + event.get("diff"));
        }
         */

        Object[][] expected = new Object[][] {
                        {"0", "166583", 124.0, 1L, 1L, 0L},
                        {"0", "169254", 124.0, 1L, 1L, 0L},
                        {"1", "166583", 124.0, 1L, 1L, 0L},
                        {"1", "169254", 124.0, 1L, 1L, 0L},
                        {"2", "166583", 124.0, 1L, 1L, 0L},
                        {"2", "169254", 124.0, 1L, 1L, 0L},
                        {"3", "166583", 124.0, 1L, 1L, 0L},
                        {"3", "169254", 124.0, 1L, 1L, 0L},
                        {"4", "166583", 127.0, 1L, 0L, 1L},
                        {"4", "169254", 127.0, 1L, 0L, 1L}
                    };

        // assert iterator results
        EventBean[] received = ArrayAssertionUtil.iteratorToArray(stmtTwo.iterator());
        ArrayAssertionUtil.assertPropsPerRow(received, "loc,sku,avgTime,cntEnter,cntLeave,diff".split(","),expected);
        received = ArrayAssertionUtil.iteratorToArray(stmtOne.iterator());
        ArrayAssertionUtil.assertPropsPerRow(received, "loc,sku,avgTime,cntEnter,cntLeave,diff".split(","),expected);
    }

    public void testFullOuterJoinNamedAggregationLateStart()
    {
        // create window
        String stmtTextCreate = "create window MyWindow.std:groupby(string, intPrimitive).win:length(3) as select string, intPrimitive, boolPrimitive from " + SupportBean.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL(stmtTextCreate);

        // create insert into
        String stmtTextInsert = "insert into MyWindow select string, intPrimitive, boolPrimitive from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEPL(stmtTextInsert);

        // fill window
        String[] stringValues = new String[] {"c0", "c1", "c2"};
        for (int i = 0; i < stringValues.length; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                for (int k = 0; k < 2; k++)
                {
                    SupportBean bean = new SupportBean(stringValues[i], j);
                    bean.setBoolPrimitive(true);
                    epService.getEPRuntime().sendEvent(bean);
                }
            }
        }
        SupportBean bean = new SupportBean("c1", 2);
        bean.setBoolPrimitive(true);
        epService.getEPRuntime().sendEvent(bean);

        EventBean[] received = ArrayAssertionUtil.iteratorToArray(stmtCreate.iterator());
        assertEquals(19, received.length);

        // create select stmt
        String stmtTextSelect = "select string, intPrimitive, count(boolPrimitive) as cntBool, symbol " +
                                "from MyWindow full outer join " + SupportMarketDataBean.class.getName() + ".win:keepall() " +
                                "on string = symbol " +
                                "group by string, intPrimitive, symbol order by string, intPrimitive, symbol";
        EPStatement stmtSelect = epService.getEPAdministrator().createEPL(stmtTextSelect);

        // send outer join events
        this.sendMarketBean("c0");
        this.sendMarketBean("c3");

        // get iterator results
        received = ArrayAssertionUtil.iteratorToArray(stmtSelect.iterator());
        ArrayAssertionUtil.assertPropsPerRow(received, "string,intPrimitive,cntBool,symbol".split(","),
                new Object[][] {
                        {null, null, 0L, "c3"},
                        {"c0", 0, 2L, "c0"},
                        {"c0", 1, 2L, "c0"},
                        {"c0", 2, 2L, "c0"},
                        {"c1", 0, 2L, null},
                        {"c1", 1, 2L, null},
                        {"c1", 2, 3L, null},
                        {"c2", 0, 2L, null},
                        {"c2", 1, 2L, null},
                        {"c2", 2, 2L, null},
                    });
        /*
        for (int i = 0; i < received.length; i++)
        {
            System.out.println("string=" + received[i].get("string") +
                    " intPrimitive=" + received[i].get("intPrimitive") +
                    " cntBool=" + received[i].get("cntBool") +
                    " symbol=" + received[i].get("symbol"));
        }
        */

        stmtSelect.destroy();
        stmtCreate.destroy();

    }

    public void testJoinNamedAndStream()
    {
        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select string as a, intPrimitive as b from " + SupportBean.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        // create delete stmt
        String stmtTextDelete = "on " + SupportBean_A.class.getName() + " delete from MyWindow where id = a";
        epService.getEPAdministrator().createEPL(stmtTextDelete);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string as a, intPrimitive as b from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEPL(stmtTextInsertOne);

        // create consumer
        String[] fields = new String[] {"symbol", "a", "b"};
        String stmtTextSelectOne = "select irstream symbol, a, b " +
                                   " from " + SupportMarketDataBean.class.getName() + ".win:length(10) as s0," +
                                             "MyWindow as s1 where s1.a = symbol";
        EPStatement stmtSelectOne = epService.getEPAdministrator().createEPL(stmtTextSelectOne);
        stmtSelectOne.addListener(listenerStmtOne);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmtSelectOne.getEventType().getPropertyNames(), new String[] {"symbol", "a", "b"});
        assertEquals(String.class, stmtSelectOne.getEventType().getPropertyType("symbol"));
        assertEquals(String.class, stmtSelectOne.getEventType().getPropertyType("a"));
        assertEquals(int.class, stmtSelectOne.getEventType().getPropertyType("b"));

        sendMarketBean("S1");
        assertFalse(listenerStmtOne.isInvoked());

        sendSupportBean("S1", 1);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"S1", "S1", 1});

        sendSupportBean_A("S1"); // deletes from window
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"S1", "S1", 1});

        sendMarketBean("S1");
        assertFalse(listenerStmtOne.isInvoked());

        sendSupportBean("S2", 2);
        assertFalse(listenerStmtOne.isInvoked());

        sendMarketBean("S2");
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"S2", "S2", 2});

        sendSupportBean("S3", 3);
        sendSupportBean("S3", 4);
        assertFalse(listenerStmtOne.isInvoked());

        sendMarketBean("S3");
        assertEquals(2, listenerStmtOne.getLastNewData().length);
        listenerStmtOne.reset();

        sendSupportBean_A("S3"); // deletes from window
        assertEquals(2, listenerStmtOne.getLastOldData().length);
        listenerStmtOne.reset();

        sendMarketBean("S3");
        assertFalse(listenerStmtOne.isInvoked());
    }

    public void testJoinBetweenNamed()
    {
        String[] fields = new String[] {"a1", "b1", "a2", "b2"};

        // create window
        String stmtTextCreateOne = "create window MyWindowOne.win:keepall() as select string as a1, intPrimitive as b1 from " + SupportBean.class.getName();
        EPStatement stmtCreateOne = epService.getEPAdministrator().createEPL(stmtTextCreateOne);
        stmtCreateOne.addListener(listenerWindow);

        // create window
        String stmtTextCreateTwo = "create window MyWindowTwo.win:keepall() as select string as a2, intPrimitive as b2 from " + SupportBean.class.getName();
        EPStatement stmtCreateTwo = epService.getEPAdministrator().createEPL(stmtTextCreateTwo);
        stmtCreateTwo.addListener(listenerWindowTwo);

        // create delete stmt
        String stmtTextDelete = "on " + SupportMarketDataBean.class.getName() + "(volume=1) delete from MyWindowOne where symbol = a1";
        epService.getEPAdministrator().createEPL(stmtTextDelete);

        stmtTextDelete = "on " + SupportMarketDataBean.class.getName() + "(volume=0) delete from MyWindowTwo where symbol = a2";
        epService.getEPAdministrator().createEPL(stmtTextDelete);

        // create insert into
        String stmtTextInsert = "insert into MyWindowOne select string as a1, intPrimitive as b1 from " + SupportBean.class.getName() + "(boolPrimitive = true)";
        epService.getEPAdministrator().createEPL(stmtTextInsert);

        stmtTextInsert = "insert into MyWindowTwo select string as a2, intPrimitive as b2 from " + SupportBean.class.getName() + "(boolPrimitive = false)";
        epService.getEPAdministrator().createEPL(stmtTextInsert);

        // create consumers
        String stmtTextSelectOne = "select irstream a1, b1, a2, b2 " +
                                   " from MyWindowOne as s0," +
                                         "MyWindowTwo as s1 where s0.a1 = s1.a2";
        EPStatement stmtSelectOne = epService.getEPAdministrator().createEPL(stmtTextSelectOne);
        stmtSelectOne.addListener(listenerStmtOne);

        sendSupportBean(true, "S0", 1);
        assertFalse(listenerStmtOne.isInvoked());

        sendSupportBean(false, "S0", 2);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"S0", 1, "S0", 2});

        sendSupportBean(false, "S1", 3);
        assertFalse(listenerStmtOne.isInvoked());

        sendSupportBean(true, "S1", 4);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"S1", 4, "S1", 3});

        sendSupportBean(true, "S1", 5);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"S1", 5, "S1", 3});

        sendSupportBean(false, "S1", 6);
        assertEquals(2, listenerStmtOne.getLastNewData().length);
        listenerStmtOne.reset();

        // delete and insert back in
        sendMarketBean("S0", 0);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"S0", 1, "S0", 2});

        sendSupportBean(false, "S0", 7);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"S0", 1, "S0", 7});

        // delete and insert back in
        sendMarketBean("S0", 1);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"S0", 1, "S0", 7});

        sendSupportBean(true, "S0", 8);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"S0", 8, "S0", 7});
    }

    public void testJoinBetweenSameNamed()
    {
        String[] fields = new String[] {"a0", "b0", "a1", "b1"};

        // create window
        String stmtTextCreateOne = "create window MyWindow.win:keepall() as select string as a, intPrimitive as b from " + SupportBean.class.getName();
        EPStatement stmtCreateOne = epService.getEPAdministrator().createEPL(stmtTextCreateOne);
        stmtCreateOne.addListener(listenerWindow);

        // create delete stmt
        String stmtTextDelete = "on " + SupportMarketDataBean.class.getName() + " delete from MyWindow where symbol = a";
        epService.getEPAdministrator().createEPL(stmtTextDelete);

        // create insert into
        String stmtTextInsert = "insert into MyWindow select string as a, intPrimitive as b from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEPL(stmtTextInsert);

        // create consumers
        String stmtTextSelectOne = "select irstream s0.a as a0, s0.b as b0, s1.a as a1, s1.b as b1 " +
                                   " from MyWindow as s0," +
                                         "MyWindow as s1 where s0.a = s1.a";
        EPStatement stmtSelectOne = epService.getEPAdministrator().createEPL(stmtTextSelectOne);
        stmtSelectOne.addListener(listenerStmtOne);

        sendSupportBean("E1", 1);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1, "E1", 1});

        sendSupportBean("E2", 2);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E2", 2, "E2", 2});

        sendMarketBean("E1", 1);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"E1", 1, "E1", 1});

        sendMarketBean("E0", 0);
        assertFalse(listenerStmtOne.isInvoked());
    }

    public void testJoinSingleInsertOneWindow()
    {
        String[] fields = new String[] {"a1", "b1", "a2", "b2"};

        // create window
        String stmtTextCreateOne = "create window MyWindowOne.win:keepall() as select string as a1, intPrimitive as b1 from " + SupportBean.class.getName();
        EPStatement stmtCreateOne = epService.getEPAdministrator().createEPL(stmtTextCreateOne);
        stmtCreateOne.addListener(listenerWindow);

        // create window
        String stmtTextCreateTwo = "create window MyWindowTwo.win:keepall() as select string as a2, intPrimitive as b2 from " + SupportBean.class.getName();
        EPStatement stmtCreateTwo = epService.getEPAdministrator().createEPL(stmtTextCreateTwo);
        stmtCreateTwo.addListener(listenerWindowTwo);

        // create delete stmt
        String stmtTextDelete = "on " + SupportMarketDataBean.class.getName() + "(volume=1) delete from MyWindowOne where symbol = a1";
        epService.getEPAdministrator().createEPL(stmtTextDelete);

        stmtTextDelete = "on " + SupportMarketDataBean.class.getName() + "(volume=0) delete from MyWindowTwo where symbol = a2";
        epService.getEPAdministrator().createEPL(stmtTextDelete);

        // create insert into
        String stmtTextInsert = "insert into MyWindowOne select string as a1, intPrimitive as b1 from " + SupportBean.class.getName() + "(boolPrimitive = true)";
        epService.getEPAdministrator().createEPL(stmtTextInsert);

        stmtTextInsert = "insert into MyWindowTwo select string as a2, intPrimitive as b2 from " + SupportBean.class.getName() + "(boolPrimitive = false)";
        epService.getEPAdministrator().createEPL(stmtTextInsert);

        // create consumers
        String stmtTextSelectOne = "select irstream a1, b1, a2, b2 " +
                                   " from MyWindowOne as s0," +
                                         "MyWindowTwo as s1 where s0.a1 = s1.a2";
        EPStatement stmtSelectOne = epService.getEPAdministrator().createEPL(stmtTextSelectOne);
        stmtSelectOne.addListener(listenerStmtOne);

        sendSupportBean(true, "S0", 1);
        assertFalse(listenerStmtOne.isInvoked());

        sendSupportBean(false, "S0", 2);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"S0", 1, "S0", 2});

        sendSupportBean(false, "S1", 3);
        assertFalse(listenerStmtOne.isInvoked());

        sendSupportBean(true, "S1", 4);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"S1", 4, "S1", 3});

        sendSupportBean(true, "S1", 5);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"S1", 5, "S1", 3});

        sendSupportBean(false, "S1", 6);
        assertEquals(2, listenerStmtOne.getLastNewData().length);
        listenerStmtOne.reset();

        // delete and insert back in
        sendMarketBean("S0", 0);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"S0", 1, "S0", 2});

        sendSupportBean(false, "S0", 7);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"S0", 1, "S0", 7});

        // delete and insert back in
        sendMarketBean("S0", 1);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"S0", 1, "S0", 7});

        sendSupportBean(true, "S0", 8);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"S0", 8, "S0", 7});
    }

    public void testUnidirectional()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean_A", SupportBean_A.class);
        epService.getEPAdministrator().createEPL("create window MyWindow.win:keepall() select * from SupportBean");
        epService.getEPAdministrator().createEPL("insert into MyWindow select * from SupportBean");

        EPStatement stmtOne = epService.getEPAdministrator().createEPL("select w.* from MyWindow w unidirectional, SupportBean_A.std:lastevent() s where s.id = w.string");
        stmtOne.addListener(listenerStmtOne);
        
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        epService.getEPRuntime().sendEvent(new SupportBean_A("E1"));
        epService.getEPRuntime().sendEvent(new SupportBean_A("E2"));
        assertFalse(listenerStmtOne.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 1));
        assertTrue(listenerStmtOne.isInvoked());
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

    private SupportBean sendSupportBean(boolean boolPrimitive, String string, int intPrimitive)
    {
        SupportBean bean = new SupportBean();
        bean.setBoolPrimitive(boolPrimitive);
        bean.setString(string);
        bean.setIntPrimitive(intPrimitive);
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }

    private void sendMarketBean(String symbol)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, 0, 0L, "");
        epService.getEPRuntime().sendEvent(bean);
    }

    private void sendMarketBean(String symbol, long volume)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, 0, volume, "");
        epService.getEPRuntime().sendEvent(bean);
    }
}
