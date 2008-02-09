package com.espertech.esper.regression.view;

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.client.time.TimerControlEvent;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.collection.UniformPair;
import com.espertech.esper.regression.support.ResultAssertTestResult;
import com.espertech.esper.regression.support.ResultAssertExecution;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestOutputLimitRowForAll extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;
    private final static String CATEGORY = "Fully-Aggregated and Un-grouped";

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        config.addEventTypeAlias("MarketData", SupportMarketDataBean.class);
        config.addEventTypeAlias("SupportBean", SupportBean.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void test1NoneNoHavingNoJoin()
    {
        String stmtText = "select sum(price) " +
                            "from MarketData.win:time(5.5 sec)";
        runAssertion12(stmtText, "none");
    }

    public void test2NoneNoHavingJoin()
    {
        String stmtText = "select sum(price) " +
                            "from MarketData.win:time(5.5 sec), " +
                            "SupportBean.win:keepall() where string=symbol";
        runAssertion12(stmtText, "none");
    }

    public void test3NoneHavingNoJoin()
    {
        String stmtText = "select sum(price) " +
                            "from MarketData.win:time(5.5 sec) " +
                            " having sum(price) > 100";
        runAssertion34(stmtText, "none");
    }

    public void test4NoneHavingJoin()
    {
        String stmtText = "select sum(price) " +
                            "from MarketData.win:time(5.5 sec), " +
                            "SupportBean.win:keepall() where string=symbol " +
                            " having sum(price) > 100";
        runAssertion34(stmtText, "none");
    }

    public void test5DefaultNoHavingNoJoin()
    {
        String stmtText = "select sum(price) " +
                            "from MarketData.win:time(5.5 sec) " +
                            "output every 1 seconds";
        runAssertion56(stmtText, "default");
    }

    public void test6DefaultNoHavingJoin()
    {
        String stmtText = "select sum(price) " +
                            "from MarketData.win:time(5.5 sec), " +
                            "SupportBean.win:keepall() where string=symbol " +
                            "output every 1 seconds";
        runAssertion56(stmtText, "default");
    }

    public void test7DefaultHavingNoJoin()
    {
        String stmtText = "select sum(price) " +
                            "from MarketData.win:time(5.5 sec) \n" +
                            "having sum(price) > 100" +
                            "output every 1 seconds";
        runAssertion78(stmtText, "default");
    }

    public void test8DefaultHavingJoin()
    {
        String stmtText = "select sum(price) " +
                            "from MarketData.win:time(5.5 sec), " +
                            "SupportBean.win:keepall() where string=symbol " +
                            "having sum(price) > 100" +
                            "output every 1 seconds";
        runAssertion78(stmtText, "default");
    }

    public void test9AllNoHavingNoJoin()
    {
        String stmtText = "select sum(price) " +
                            "from MarketData.win:time(5.5 sec) " +
                            "output all every 1 seconds";
        runAssertion56(stmtText, "all");
    }

    public void test10AllNoHavingJoin()
    {
        String stmtText = "select sum(price) " +
                            "from MarketData.win:time(5.5 sec), " +
                            "SupportBean.win:keepall() where string=symbol " +
                            "output all every 1 seconds";
        runAssertion56(stmtText, "all");
    }

    public void test11AllHavingNoJoin()
    {
        String stmtText = "select sum(price) " +
                            "from MarketData.win:time(5.5 sec) " +
                            "having sum(price) > 100" +
                            "output all every 1 seconds";
        runAssertion78(stmtText, "all");
    }

    public void test12AllHavingJoin()
    {
        String stmtText = "select sum(price) " +
                            "from MarketData.win:time(5.5 sec), " +
                            "SupportBean.win:keepall() where string=symbol " +
                            "having sum(price) > 100" +
                            "output all every 1 seconds";
        runAssertion78(stmtText, "all");
    }

    public void test13LastNoHavingNoJoin()
    {
        String stmtText = "select sum(price) " +
                            "from MarketData.win:time(5.5 sec)" +
                            "output last every 1 seconds";
        runAssertion13_14(stmtText, "last");
    }

    public void test14LastNoHavingJoin()
    {
        String stmtText = "select sum(price) " +
                            "from MarketData.win:time(5.5 sec), " +
                            "SupportBean.win:keepall() where string=symbol " +
                            "output last every 1 seconds";
        runAssertion13_14(stmtText, "last");
    }

    public void test15LastHavingNoJoin()
    {
        String stmtText = "select sum(price) " +
                            "from MarketData.win:time(5.5 sec)" +
                            "having sum(price) > 100 " +
                            "output last every 1 seconds";
        runAssertion15_16(stmtText, "last");
    }

    public void test16LastHavingJoin()
    {
        String stmtText = "select sum(price) " +
                            "from MarketData.win:time(5.5 sec), " +
                            "SupportBean.win:keepall() where string=symbol " +
                            "having sum(price) > 100 " +
                            "output last every 1 seconds";
        runAssertion15_16(stmtText, "last");
    }

    public void test17FirstNoHavingNoJoin()
    {
        String stmtText = "select sum(price) " +
                            "from MarketData.win:time(5.5 sec) " +
                            "output first every 1 seconds";
        runAssertion17(stmtText, "first");
    }

    public void test18SnapshotNoHavingNoJoin()
    {
        String stmtText = "select sum(price) " +
                            "from MarketData.win:time(5.5 sec) " +
                            "output snapshot every 1 seconds";
        runAssertion18(stmtText, "first");
    }

    private void runAssertion12(String stmtText, String outputLimit)
    {
        sendTimer(0);
        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        String fields[] = new String[] {"sum(price)"};
        ResultAssertTestResult expected = new ResultAssertTestResult(CATEGORY, outputLimit, fields);
        expected.addResultInsRem(200, 1, new Object[][] {{25d}}, new Object[][] {{null}});
        expected.addResultInsRem(800, 1, new Object[][] {{34d}}, new Object[][] {{25d}});
        expected.addResultInsRem(1500, 1, new Object[][] {{58d}}, new Object[][] {{34d}});
        expected.addResultInsRem(1500, 2, new Object[][] {{59d}}, new Object[][] {{58d}});
        expected.addResultInsRem(2100, 1, new Object[][] {{85d}}, new Object[][] {{59d}});
        expected.addResultInsRem(3500, 1, new Object[][] {{87d}}, new Object[][] {{85d}});
        expected.addResultInsRem(4300, 1, new Object[][] {{109d}}, new Object[][] {{87d}});
        expected.addResultInsRem(4900, 1, new Object[][] {{112d}}, new Object[][] {{109d}});
        expected.addResultInsRem(5700, 0, new Object[][] {{87d}}, new Object[][] {{112d}});
        expected.addResultInsRem(5900, 1, new Object[][] {{88d}}, new Object[][] {{87d}});
        expected.addResultInsRem(6300, 0, new Object[][] {{79d}}, new Object[][] {{88d}});
        expected.addResultInsRem(7000, 0, new Object[][] {{54d}}, new Object[][] {{79d}});

        ResultAssertExecution execution = new ResultAssertExecution(epService, stmt, listener, expected);
        execution.execute();
    }

    private void runAssertion34(String stmtText, String outputLimit)
    {
        sendTimer(0);
        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        String fields[] = new String[] {"sum(price)"};
        ResultAssertTestResult expected = new ResultAssertTestResult(CATEGORY, outputLimit, fields);
        expected.addResultInsRem(4300, 1, new Object[][] {{109d}}, null);
        expected.addResultInsRem(4900, 1, new Object[][] {{112d}}, new Object[][] {{109d}});
        expected.addResultInsRem(5700, 0, null, new Object[][] {{112d}});

        ResultAssertExecution execution = new ResultAssertExecution(epService, stmt, listener, expected);
        execution.execute();
    }

    private void runAssertion13_14(String stmtText, String outputLimit)
    {
        sendTimer(0);
        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        String fields[] = new String[] {"sum(price)"};
        ResultAssertTestResult expected = new ResultAssertTestResult(CATEGORY, outputLimit, fields);
        expected.addResultInsRem(1200, 0, new Object[][] {{34d}}, new Object[][] {{null}});
        expected.addResultInsRem(2200, 0, new Object[][] {{85d}}, new Object[][] {{34d}});
        expected.addResultInsRem(3200, 0, new Object[][] {{85d}}, new Object[][] {{85d}});
        expected.addResultInsRem(4200, 0, new Object[][] {{87d}}, new Object[][] {{85d}});
        expected.addResultInsRem(5200, 0, new Object[][] {{112d}}, new Object[][] {{87d}});
        expected.addResultInsRem(6200, 0, new Object[][] {{88d}}, new Object[][] {{112d}});
        expected.addResultInsRem(7200, 0, new Object[][] {{54d}}, new Object[][] {{88d}});

        ResultAssertExecution execution = new ResultAssertExecution(epService, stmt, listener, expected);
        execution.execute();
    }

    private void runAssertion15_16(String stmtText, String outputLimit)
    {
        sendTimer(0);
        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        String fields[] = new String[] {"sum(price)"};
        ResultAssertTestResult expected = new ResultAssertTestResult(CATEGORY, outputLimit, fields);
        expected.addResultInsRem(1200, 0, null, null);
        expected.addResultInsRem(2200, 0, null, null);
        expected.addResultInsRem(3200, 0, null, null);
        expected.addResultInsRem(4200, 0, null, null);
        expected.addResultInsRem(5200, 0, new Object[][] {{112d}}, new Object[][] {{109d}});
        expected.addResultInsRem(6200, 0, null, new Object[][] {{112d}});
        expected.addResultInsRem(7200, 0, null, null);

        ResultAssertExecution execution = new ResultAssertExecution(epService, stmt, listener, expected);
        execution.execute();
    }

    private void runAssertion78(String stmtText, String outputLimit)
    {
        sendTimer(0);
        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        String fields[] = new String[] {"sum(price)"};
        ResultAssertTestResult expected = new ResultAssertTestResult(CATEGORY, outputLimit, fields);
        expected.addResultInsRem(1200, 0, null, null);
        expected.addResultInsRem(2200, 0, null, null);
        expected.addResultInsRem(3200, 0, null, null);
        expected.addResultInsRem(4200, 0, null, null);
        expected.addResultInsRem(5200, 0, new Object[][] {{109d}, {112d}}, new Object[][] {{109d}});
        expected.addResultInsRem(6200, 0, null, new Object[][] {{112d}});
        expected.addResultInsRem(7200, 0, null, null);

        ResultAssertExecution execution = new ResultAssertExecution(epService, stmt, listener, expected);
        execution.execute();
    }

    private void runAssertion56(String stmtText, String outputLimit)
    {
        sendTimer(0);
        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        String fields[] = new String[] {"sum(price)"};
        ResultAssertTestResult expected = new ResultAssertTestResult(CATEGORY, outputLimit, fields);
        expected.addResultInsRem(1200, 0, new Object[][] {{25d}, {34d}}, new Object[][] {{null}, {25d}});
        expected.addResultInsRem(2200, 0, new Object[][] {{58d}, {59d}, {85d}}, new Object[][] {{34d}, {58d}, {59d}});
        expected.addResultInsRem(3200, 0, new Object[][] {{85d}}, new Object[][] {{85d}});
        expected.addResultInsRem(4200, 0, new Object[][] {{87d}}, new Object[][] {{85d}});
        expected.addResultInsRem(5200, 0, new Object[][] {{109d}, {112d}}, new Object[][] {{87d}, {109d}});
        expected.addResultInsRem(6200, 0, new Object[][] {{87d}, {88d}}, new Object[][] {{112d}, {87d}});
        expected.addResultInsRem(7200, 0, new Object[][] {{79d}, {54d}}, new Object[][] {{88d}, {79d}});

        ResultAssertExecution execution = new ResultAssertExecution(epService, stmt, listener, expected);
        execution.execute();
    }

    private void runAssertion17(String stmtText, String outputLimit)
    {
        sendTimer(0);
        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        String fields[] = new String[] {"sum(price)"};
        ResultAssertTestResult expected = new ResultAssertTestResult(CATEGORY, outputLimit, fields);
        expected.addResultInsRem(200, 1, new Object[][] {{25d}}, new Object[][] {{null}});
        expected.addResultInsRem(1500, 1, new Object[][] {{58d}}, new Object[][] {{34d}});
        expected.addResultInsRem(3200, 0, new Object[][] {{85d}}, new Object[][] {{85d}});
        expected.addResultInsRem(3500, 1, new Object[][] {{87d}}, new Object[][] {{85d}});
        expected.addResultInsRem(4300, 1, new Object[][] {{109d}}, new Object[][] {{87d}});
        expected.addResultInsRem(5700, 0, new Object[][] {{87d}}, new Object[][] {{112d}});
        expected.addResultInsRem(6300, 0, new Object[][] {{79d}}, new Object[][] {{88d}});

        ResultAssertExecution execution = new ResultAssertExecution(epService, stmt, listener, expected);
        execution.execute();
    }

    private void runAssertion18(String stmtText, String outputLimit)
    {
        sendTimer(0);
        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        String fields[] = new String[] {"sum(price)"};
        ResultAssertTestResult expected = new ResultAssertTestResult(CATEGORY, outputLimit, fields);
        expected.addResultInsRem(1200, 0, new Object[][] {{34d}}, null);
        expected.addResultInsRem(2200, 0, new Object[][] {{85d}}, null);
        expected.addResultInsRem(3200, 0, new Object[][] {{85d}}, null);
        expected.addResultInsRem(4200, 0, new Object[][] {{87d}}, null);
        expected.addResultInsRem(5200, 0, new Object[][] {{112d}}, null);
        expected.addResultInsRem(6200, 0, new Object[][] {{88d}}, null);
        expected.addResultInsRem(7200, 0, new Object[][] {{54d}}, null);

        ResultAssertExecution execution = new ResultAssertExecution(epService, stmt, listener, expected);
        execution.execute();
    }

    public void testAggAllHaving()
    {
        String stmtText = "select sum(volume) as result " +
                            "from " + SupportMarketDataBean.class.getName() + ".win:length(10) as two " +
                            "having sum(volume) > 0 " +
                            "output every 5 events";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);
        String fields[] = new String[] {"result"};

        sendMDEvent(20);
        sendMDEvent(-100);
        sendMDEvent(0);
        sendMDEvent(0);
        assertFalse(listener.isInvoked());

        sendMDEvent(0);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{20L}});
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{20L}});
        listener.reset();
    }

    public void testAggAllHavingJoin()
    {
        String stmtText = "select sum(volume) as result " +
                            "from " + SupportMarketDataBean.class.getName() + ".win:length(10) as one," +
                            SupportBean.class.getName() + ".win:length(10) as two " +
                            "where one.symbol=two.string " +
                            "having sum(volume) > 0 " +
                            "output every 5 events";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);
        String fields[] = new String[] {"result"};
        epService.getEPRuntime().sendEvent(new SupportBean("S0", 0));

        sendMDEvent(20);
        sendMDEvent(-100);
        sendMDEvent(0);
        sendMDEvent(0);
        assertFalse(listener.isInvoked());

        sendMDEvent(0);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{20L}});
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{20L}});
        listener.reset();
    }

    public void testJoinSortWindow()
    {
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
        sendTimer(0);

        String viewExpr = "select irstream max(price) as maxVol" +
                          " from " + SupportMarketDataBean.class.getName() + ".ext:sort(volume, true, 1) as s0," +
                          SupportBean.class.getName() + " as s1 where s1.string = s0.symbol " +
                          "output every 1 seconds";
        EPStatement stmt = epService.getEPAdministrator().createEQL(viewExpr);
        stmt.addListener(listener);
        epService.getEPRuntime().sendEvent(new SupportBean("JOIN_KEY", -1));

        sendEvent("JOIN_KEY", 1d);
        sendEvent("JOIN_KEY", 2d);
        listener.reset();

        // moves all events out of the window,
        sendTimer(1000);        // newdata is 2 eventa, old data is the same 2 events, therefore the sum is null
        UniformPair<EventBean[]> result = listener.getDataListsFlattened();
        assertEquals(2, result.getFirst().length);
        assertEquals(1.0, result.getFirst()[0].get("maxVol"));
        assertEquals(2.0, result.getFirst()[1].get("maxVol"));
        assertEquals(2, result.getSecond().length);
        assertEquals(null, result.getSecond()[0].get("maxVol"));
        assertEquals(1.0, result.getSecond()[1].get("maxVol"));
    }

    public void testMaxTimeWindow()
    {
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
        sendTimer(0);

        String viewExpr = "select irstream max(price) as maxVol" +
                          " from " + SupportMarketDataBean.class.getName() + ".win:time(1.1 sec) " +
                          "output every 1 seconds";
        EPStatement stmt = epService.getEPAdministrator().createEQL(viewExpr);
        stmt.addListener(listener);

        sendEvent("SYM1", 1d);
        sendEvent("SYM1", 2d);
        listener.reset();

        // moves all events out of the window,
        sendTimer(1000);        // newdata is 2 eventa, old data is the same 2 events, therefore the sum is null
        UniformPair<EventBean[]> result = listener.getDataListsFlattened();
        assertEquals(2, result.getFirst().length);
        assertEquals(1.0, result.getFirst()[0].get("maxVol"));
        assertEquals(2.0, result.getFirst()[1].get("maxVol"));
        assertEquals(2, result.getSecond().length);
        assertEquals(null, result.getSecond()[0].get("maxVol"));
        assertEquals(1.0, result.getSecond()[1].get("maxVol"));
    }

    public void testTimeWindowOutputCountLast()
    {
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));

        String stmtText = "select count(*) as cnt from " + SupportBean.class.getName() + ".win:time(10 seconds) output every 10 seconds";
        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        sendTimer(0);
        sendTimer(10000);
        assertFalse(listener.isInvoked());
        sendTimer(20000);
        assertFalse(listener.isInvoked());

        sendEvent("e1");
        sendTimer(30000);
        EventBean[] newEvents = listener.getAndResetLastNewData();
        assertEquals(2, newEvents.length);
        assertEquals(1L, newEvents[0].get("cnt"));
        assertEquals(0L, newEvents[1].get("cnt"));

        sendTimer(31000);

        sendEvent("e2");
        sendEvent("e3");
        sendTimer(40000);
        newEvents = listener.getAndResetLastNewData();
        assertEquals(2, newEvents.length);
        assertEquals(1L, newEvents[0].get("cnt"));
        assertEquals(2L, newEvents[1].get("cnt"));
    }

    public void testTimeBatchOutputCount()
    {
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));

        String stmtText = "select count(*) as cnt from " + SupportBean.class.getName() + ".win:time_batch(10 seconds) output every 10 seconds";
        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        sendTimer(0);
        sendTimer(10000);
        assertFalse(listener.isInvoked());
        sendTimer(20000);
        assertFalse(listener.isInvoked());

        sendEvent("e1");
        sendTimer(30000);
        assertFalse(listener.isInvoked());
        sendTimer(40000);
        EventBean[] newEvents = listener.getAndResetLastNewData();
        assertEquals(2, newEvents.length);
        // output limiting starts 10 seconds after, therefore the old batch was posted already and the cnt is zero
        assertEquals(1L, newEvents[0].get("cnt"));
        assertEquals(0L, newEvents[1].get("cnt"));

        sendTimer(50000);
        EventBean[] newData = listener.getLastNewData();
        assertEquals(0L, newData[0].get("cnt"));
        listener.reset();

        sendEvent("e2");
        sendEvent("e3");
        sendTimer(60000);
        newEvents = listener.getAndResetLastNewData();
        assertEquals(1, newEvents.length);
        assertEquals(2L, newEvents[0].get("cnt"));
    }

    public void testLimitSnapshot()
    {
        SupportUpdateListener listener = new SupportUpdateListener();

        sendTimer(0);
        String selectStmt = "select count(*) as cnt from " + SupportBean.class.getName() + ".win:time(10 seconds) where intPrimitive > 0 output snapshot every 1 seconds";

        EPStatement stmt = epService.getEPAdministrator().createEQL(selectStmt);
        stmt.addListener(listener);
        sendEvent("s0", 1);

        sendTimer(500);
        sendEvent("s1", 1);
        sendEvent("s2", -1);
        assertFalse(listener.getAndClearIsInvoked());

        sendTimer(1000);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), new String[] {"cnt"}, new Object[][] {{2L}});
        assertNull(listener.getLastOldData());
        listener.reset();

        sendTimer(1500);
        sendEvent("s4", 2);
        sendEvent("s5", 3);
        assertFalse(listener.getAndClearIsInvoked());

        sendTimer(2000);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), new String[] {"cnt"}, new Object[][] {{4L}});
        assertNull(listener.getLastOldData());
        listener.reset();

        sendEvent("s5", 4);
        assertFalse(listener.getAndClearIsInvoked());

        sendTimer(9000);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), new String[] {"cnt"}, new Object[][] {{5L}});
        assertNull(listener.getLastOldData());
        listener.reset();

        sendTimer(10000);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), new String[] {"cnt"}, new Object[][] {{4L}});
        assertNull(listener.getLastOldData());
        listener.reset();

        sendTimer(10999);
        assertFalse(listener.getAndClearIsInvoked());

        sendTimer(11000);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), new String[] {"cnt"}, new Object[][] {{3L}});
        assertNull(listener.getLastOldData());
        listener.reset();
    }

    public void testLimitSnapshotJoin()
    {
        SupportUpdateListener listener = new SupportUpdateListener();

        sendTimer(0);
        String selectStmt = "select count(*) as cnt from " +
                SupportBean.class.getName() + ".win:time(10 seconds) as s, " +
                SupportMarketDataBean.class.getName() + " as m where m.symbol = s.string and intPrimitive > 0 output snapshot every 1 seconds";

        EPStatement stmt = epService.getEPAdministrator().createEQL(selectStmt);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("s0", 0, 0L, ""));
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("s1", 0, 0L, ""));
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("s2", 0, 0L, ""));
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("s4", 0, 0L, ""));
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("s5", 0, 0L, ""));

        sendEvent("s0", 1);

        sendTimer(500);
        sendEvent("s1", 1);
        sendEvent("s2", -1);
        assertFalse(listener.getAndClearIsInvoked());

        sendTimer(1000);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), new String[] {"cnt"}, new Object[][] {{2L}});
        assertNull(listener.getLastOldData());
        listener.reset();

        sendTimer(1500);
        sendEvent("s4", 2);
        sendEvent("s5", 3);
        assertFalse(listener.getAndClearIsInvoked());

        sendTimer(2000);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), new String[] {"cnt"}, new Object[][] {{4L}});
        assertNull(listener.getLastOldData());
        listener.reset();

        sendEvent("s5", 4);
        assertFalse(listener.getAndClearIsInvoked());

        sendTimer(9000);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), new String[] {"cnt"}, new Object[][] {{5L}});
        assertNull(listener.getLastOldData());
        listener.reset();

        // The execution of the join is after the snapshot, as joins are internal dispatch
        sendTimer(10000);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), new String[] {"cnt"}, new Object[][] {{5L}});
        assertNull(listener.getLastOldData());
        listener.reset();

        sendTimer(10999);
        assertFalse(listener.getAndClearIsInvoked());

        sendTimer(11000);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), new String[] {"cnt"}, new Object[][] {{3L}});
        assertNull(listener.getLastOldData());
        listener.reset();
    }

    private void sendEvent(String s)
	{
	    SupportBean bean = new SupportBean();
	    bean.setString(s);
	    bean.setDoubleBoxed(0.0);
	    bean.setIntPrimitive(0);
	    bean.setIntBoxed(0);
	    epService.getEPRuntime().sendEvent(bean);
	}
    
    private void sendEvent(String s, int intPrimitive)
	{
	    SupportBean bean = new SupportBean();
	    bean.setString(s);
	    bean.setIntPrimitive(intPrimitive);
	    epService.getEPRuntime().sendEvent(bean);
	}

    private void sendTimer(long time)
    {
        CurrentTimeEvent event = new CurrentTimeEvent(time);
        EPRuntime runtime = epService.getEPRuntime();
        runtime.sendEvent(event);
    }

    private void sendEvent(String symbol, double price)
	{
	    SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, null);
	    epService.getEPRuntime().sendEvent(bean);
	}

    private void sendMDEvent(long volume)
	{
	    SupportMarketDataBean bean = new SupportMarketDataBean("S0", 0, volume, null);
	    epService.getEPRuntime().sendEvent(bean);
	}

    private static Log log = LogFactory.getLog(TestOutputLimitRowForAll.class);
}





