package com.espertech.esper.regression.view;

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.support.bean.SupportBeanString;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.collection.UniformPair;
import com.espertech.esper.regression.support.ResultAssertTestResult;
import com.espertech.esper.regression.support.ResultAssertExecution;

public class TestOutputLimitEventPerGroup extends TestCase
{
    private static String SYMBOL_DELL = "DELL";
    private static String SYMBOL_IBM = "IBM";

    private EPServiceProvider epService;
    private SupportUpdateListener listener;
    private EPStatement selectTestView;
    private final static String CATEGORY = "Fully-Aggregated and Grouped";

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("MarketData", SupportMarketDataBean.class);
        config.addEventType("SupportBean", SupportBean.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void test1NoneNoHavingNoJoin()
    {
        String stmtText = "select symbol, sum(price) " +
                          "from MarketData.win:time(5.5 sec)" +
                          "group by symbol " +
                          "order by symbol asc";
        runAssertion12(stmtText, "none");
    }

    public void test2NoneNoHavingJoin()
    {
        String stmtText = "select symbol, sum(price) " +
                            "from MarketData.win:time(5.5 sec), " +
                            "SupportBean.win:keepall() where string=symbol " +
                            "group by symbol " +
                            "order by symbol asc";
        runAssertion12(stmtText, "none");
    }

    public void test3NoneHavingNoJoin()
    {
        String stmtText = "select symbol, sum(price) " +
                            "from MarketData.win:time(5.5 sec) " +
                            "group by symbol " +
                            " having sum(price) > 50";
        runAssertion34(stmtText, "none");
    }

    public void test4NoneHavingJoin()
    {
        String stmtText = "select symbol, sum(price) " +
                            "from MarketData.win:time(5.5 sec), " +
                            "SupportBean.win:keepall() where string=symbol " +
                            "group by symbol " +
                            "having sum(price) > 50";
        runAssertion34(stmtText, "none");
    }

    public void test5DefaultNoHavingNoJoin()
    {
        String stmtText = "select symbol, sum(price) " +
                            "from MarketData.win:time(5.5 sec) " +
                            "group by symbol " +
                            "output every 1 seconds";
        runAssertion56(stmtText, "default");
    }

    public void test6DefaultNoHavingJoin()
    {
        String stmtText = "select symbol, sum(price) " +
                            "from MarketData.win:time(5.5 sec), " +
                            "SupportBean.win:keepall() where string=symbol " +
                            "group by symbol " +
                            "output every 1 seconds";
        runAssertion56(stmtText, "default");
    }

    public void test7DefaultHavingNoJoin()
    {
        String stmtText = "select symbol, sum(price) " +
                            "from MarketData.win:time(5.5 sec) \n"  +
                            "group by symbol " +
                            "having sum(price) > 50" +
                            "output every 1 seconds";
        runAssertion78(stmtText, "default");
    }

    public void test8DefaultHavingJoin()
    {
        String stmtText = "select symbol, sum(price) " +
                            "from MarketData.win:time(5.5 sec), " +
                            "SupportBean.win:keepall() where string=symbol " +
                            "group by symbol " +
                            "having sum(price) > 50" +
                            "output every 1 seconds";
        runAssertion78(stmtText, "default");
    }

    public void test9AllNoHavingNoJoin()
    {
        String stmtText = "select symbol, sum(price) " +
                            "from MarketData.win:time(5.5 sec) " +
                            "group by symbol " +
                            "output all every 1 seconds " +
                            "order by symbol";
        runAssertion9_10(stmtText, "all");
    }

    public void test10AllNoHavingJoin()
    {
        String stmtText = "select symbol, sum(price) " +
                            "from MarketData.win:time(5.5 sec), " +
                            "SupportBean.win:keepall() where string=symbol " +
                            "group by symbol " +
                            "output all every 1 seconds " +
                            "order by symbol";
        runAssertion9_10(stmtText, "all");
    }

    public void test11AllHavingNoJoin()
    {
        String stmtText = "select symbol, sum(price) " +
                            "from MarketData.win:time(5.5 sec) " +
                            "group by symbol " +
                            "having sum(price) > 50 " +
                            "output all every 1 seconds";
        runAssertion11_12(stmtText, "all");
    }

    public void test12AllHavingJoin()
    {
        String stmtText = "select symbol, sum(price) " +
                            "from MarketData.win:time(5.5 sec), " +
                            "SupportBean.win:keepall() where string=symbol " +
                            "group by symbol " +
                            "having sum(price) > 50 " +
                            "output all every 1 seconds";
        runAssertion11_12(stmtText, "all");
    }

    public void test13LastNoHavingNoJoin()
    {
        String stmtText = "select symbol, sum(price) " +
                            "from MarketData.win:time(5.5 sec)" +
                            "group by symbol " +
                            "output last every 1 seconds " +
                            "order by symbol";
        runAssertion13_14(stmtText, "last");
    }

    public void test14LastNoHavingJoin()
    {
        String stmtText = "select symbol, sum(price) " +
                            "from MarketData.win:time(5.5 sec), " +
                            "SupportBean.win:keepall() where string=symbol " +
                            "group by symbol " +
                            "output last every 1 seconds " +
                            "order by symbol";
        runAssertion13_14(stmtText, "last");
    }

    public void test15LastHavingNoJoin()
    {
        String stmtText = "select symbol, sum(price) " +
                            "from MarketData.win:time(5.5 sec)" +
                            "group by symbol " +
                            "having sum(price) > 50 " +
                            "output last every 1 seconds";
        runAssertion15_16(stmtText, "last");
    }

    public void test16LastHavingJoin()
    {
        String stmtText = "select symbol, sum(price) " +
                            "from MarketData.win:time(5.5 sec), " +
                            "SupportBean.win:keepall() where string=symbol " +
                            "group by symbol " +
                            "having sum(price) > 50 " +
                            "output last every 1 seconds";
        runAssertion15_16(stmtText, "last");
    }

    public void test17FirstNoHavingNoJoin()
    {
        String stmtText = "select symbol, sum(price) " +
                            "from MarketData.win:time(5.5 sec) " +
                            "group by symbol " +
                            "output first every 1 seconds";
        runAssertion17(stmtText, "first");
    }

    public void test18SnapshotNoHavingNoJoin()
    {
        String stmtText = "select symbol, sum(price) " +
                            "from MarketData.win:time(5.5 sec) " +
                            "group by symbol " +
                            "output snapshot every 1 seconds " +
                            "order by symbol";
        runAssertion18(stmtText, "snapshot");
    }

    private void runAssertion12(String stmtText, String outputLimit)
    {
        sendTimer(0);
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        String fields[] = new String[] {"symbol", "sum(price)"};
        ResultAssertTestResult expected = new ResultAssertTestResult(CATEGORY, outputLimit, fields);
        expected.addResultInsRem(200, 1, new Object[][] {{"IBM", 25d}}, new Object[][] {{"IBM", null}});
        expected.addResultInsRem(800, 1, new Object[][] {{"MSFT", 9d}}, new Object[][] {{"MSFT", null}});
        expected.addResultInsRem(1500, 1, new Object[][] {{"IBM", 49d}}, new Object[][] {{"IBM", 25d}});
        expected.addResultInsRem(1500, 2, new Object[][] {{"YAH", 1d}}, new Object[][] {{"YAH", null}});
        expected.addResultInsRem(2100, 1, new Object[][] {{"IBM", 75d}}, new Object[][] {{"IBM", 49d}});
        expected.addResultInsRem(3500, 1, new Object[][] {{"YAH", 3d}}, new Object[][] {{"YAH", 1d}});
        expected.addResultInsRem(4300, 1, new Object[][] {{"IBM", 97d}}, new Object[][] {{"IBM", 75d}});
        expected.addResultInsRem(4900, 1, new Object[][] {{"YAH", 6d}}, new Object[][] {{"YAH", 3d}});
        expected.addResultInsRem(5700, 0, new Object[][] {{"IBM", 72d}}, new Object[][] {{"IBM", 97d}});
        expected.addResultInsRem(5900, 1, new Object[][] {{"YAH", 7d}}, new Object[][] {{"YAH", 6d}});
        expected.addResultInsRem(6300, 0, new Object[][] {{"MSFT", null}}, new Object[][] {{"MSFT", 9d}});
        expected.addResultInsRem(7000, 0, new Object[][] {{"IBM", 48d}, {"YAH", 6d}}, new Object[][] {{"IBM", 72d}, {"YAH", 7d}});

        ResultAssertExecution execution = new ResultAssertExecution(epService, stmt, listener, expected);
        execution.execute();
    }

    private void runAssertion34(String stmtText, String outputLimit)
    {
        sendTimer(0);
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        String fields[] = new String[] {"symbol", "sum(price)"};
        ResultAssertTestResult expected = new ResultAssertTestResult(CATEGORY, outputLimit, fields);
        expected.addResultInsRem(2100, 1, new Object[][] {{"IBM", 75d}}, null);
        expected.addResultInsRem(4300, 1, new Object[][] {{"IBM", 97d}}, new Object[][] {{"IBM", 75d}});
        expected.addResultInsRem(5700, 0, new Object[][] {{"IBM", 72d}}, new Object[][] {{"IBM", 97d}});
        expected.addResultInsRem(7000, 0, null, new Object[][] {{"IBM", 72d}});

        ResultAssertExecution execution = new ResultAssertExecution(epService, stmt, listener, expected);
        execution.execute();
    }

    private void runAssertion13_14(String stmtText, String outputLimit)
    {
        sendTimer(0);
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        String fields[] = new String[] {"symbol", "sum(price)"};
        ResultAssertTestResult expected = new ResultAssertTestResult(CATEGORY, outputLimit, fields);
        expected.addResultInsRem(1200, 0, new Object[][] {{"IBM", 25d}, {"MSFT", 9d}}, new Object[][] {{"IBM", null}, {"MSFT", null}});
        expected.addResultInsRem(2200, 0, new Object[][] {{"IBM", 75d}, {"YAH", 1d}}, new Object[][] {{"IBM", 25d}, {"YAH", null}});
        expected.addResultInsRem(3200, 0, null, null);
        expected.addResultInsRem(4200, 0, new Object[][] {{"YAH", 3d}}, new Object[][] {{"YAH", 1d}});
        expected.addResultInsRem(5200, 0, new Object[][] {{"IBM", 97d}, {"YAH", 6d}}, new Object[][] {{"IBM", 75d}, {"YAH", 3d}});
        expected.addResultInsRem(6200, 0, new Object[][] {{"IBM", 72d}, {"YAH", 7d}}, new Object[][] {{"IBM", 97d}, {"YAH", 6d}});
        expected.addResultInsRem(7200, 0, new Object[][] {{"IBM", 48d}, {"MSFT", null}, {"YAH", 6d}}, new Object[][] {{"IBM", 72d}, {"MSFT", 9d}, {"YAH", 7d}});

        ResultAssertExecution execution = new ResultAssertExecution(epService, stmt, listener, expected);
        execution.execute();
    }

    private void runAssertion15_16(String stmtText, String outputLimit)
    {
        sendTimer(0);
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        String fields[] = new String[] {"symbol", "sum(price)"};
        ResultAssertTestResult expected = new ResultAssertTestResult(CATEGORY, outputLimit, fields);
        expected.addResultInsRem(1200, 0, null, null);
        expected.addResultInsRem(2200, 0, new Object[][] {{"IBM", 75d}}, null);
        expected.addResultInsRem(3200, 0, null, null);
        expected.addResultInsRem(4200, 0, null, null);
        expected.addResultInsRem(5200, 0, new Object[][] {{"IBM", 97d}}, new Object[][] {{"IBM", 75d}});
        expected.addResultInsRem(6200, 0, new Object[][] {{"IBM", 72d}}, new Object[][] {{"IBM", 97d}});
        expected.addResultInsRem(7200, 0, null, new Object[][] {{"IBM", 72d}});

        ResultAssertExecution execution = new ResultAssertExecution(epService, stmt, listener, expected);
        execution.execute();
    }

    private void runAssertion78(String stmtText, String outputLimit)
    {
        sendTimer(0);
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        String fields[] = new String[] {"symbol", "sum(price)"};
        ResultAssertTestResult expected = new ResultAssertTestResult(CATEGORY, outputLimit, fields);
        expected.addResultInsRem(1200, 0, null, null);
        expected.addResultInsRem(2200, 0, new Object[][] {{"IBM", 75d}}, null);
        expected.addResultInsRem(3200, 0, null, null);
        expected.addResultInsRem(4200, 0, null, null);
        expected.addResultInsRem(5200, 0, new Object[][] {{"IBM", 97d}}, new Object[][] {{"IBM", 75d}});
        expected.addResultInsRem(6200, 0, new Object[][] {{"IBM", 72d}}, new Object[][] {{"IBM", 97d}});
        expected.addResultInsRem(7200, 0, null, new Object[][] {{"IBM", 72d}});

        ResultAssertExecution execution = new ResultAssertExecution(epService, stmt, listener, expected);
        execution.execute();
    }

    private void runAssertion56(String stmtText, String outputLimit)
    {
        sendTimer(0);
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        String fields[] = new String[] {"symbol", "sum(price)"};
        ResultAssertTestResult expected = new ResultAssertTestResult(CATEGORY, outputLimit, fields);
        expected.addResultInsRem(1200, 0, new Object[][] {{"IBM", 25d}, {"MSFT", 9d}}, new Object[][] {{"IBM", null}, {"MSFT", null}});
        expected.addResultInsRem(2200, 0, new Object[][] {{"IBM", 49d}, {"YAH", 1d}, {"IBM", 75d}}, new Object[][] {{"IBM", 25d}, {"YAH", null}, {"IBM", 49d}});
        expected.addResultInsRem(3200, 0, null, null);
        expected.addResultInsRem(4200, 0, new Object[][] {{"YAH", 3d}}, new Object[][] {{"YAH", 1d}});
        expected.addResultInsRem(5200, 0, new Object[][] {{"IBM", 97d}, {"YAH", 6d}}, new Object[][] {{"IBM", 75d}, {"YAH", 3d}});
        expected.addResultInsRem(6200, 0, new Object[][] {{"IBM", 72d}, {"YAH", 7d}}, new Object[][] {{"IBM", 97d}, {"YAH", 6d}});
        expected.addResultInsRem(7200, 0, new Object[][] {{"MSFT", null}, {"YAH", 6d}, {"IBM", 48d}}, new Object[][] {{"MSFT", 9d}, {"YAH", 7d}, {"IBM", 72d}});

        ResultAssertExecution execution = new ResultAssertExecution(epService, stmt, listener, expected);
        execution.execute();
    }

    private void runAssertion9_10(String stmtText, String outputLimit)
    {
        sendTimer(0);
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        String fields[] = new String[] {"symbol", "sum(price)"};
        ResultAssertTestResult expected = new ResultAssertTestResult(CATEGORY, outputLimit, fields);
        expected.addResultInsRem(1200, 0, new Object[][] {{"IBM", 25d}, {"MSFT", 9d}}, new Object[][] {{"IBM", null}, {"MSFT", null}});
        expected.addResultInsRem(2200, 0, new Object[][] {{"IBM", 75d}, {"MSFT", 9d}, {"YAH", 1d}}, new Object[][] {{"IBM", 25d}, {"MSFT", 9d}, {"YAH", null}});
        expected.addResultInsRem(3200, 0, new Object[][] {{"IBM", 75d}, {"MSFT", 9d}, {"YAH", 1d}}, new Object[][] {{"IBM", 75d}, {"MSFT", 9d}, {"YAH", 1d}});
        expected.addResultInsRem(4200, 0, new Object[][] {{"IBM", 75d}, {"MSFT", 9d}, {"YAH", 3d}}, new Object[][] {{"IBM", 75d}, {"MSFT", 9d}, {"YAH", 1d}});
        expected.addResultInsRem(5200, 0, new Object[][] {{"IBM", 97d}, {"MSFT", 9d}, {"YAH", 6d}}, new Object[][] {{"IBM", 75d}, {"MSFT", 9d}, {"YAH", 3d}});
        expected.addResultInsRem(6200, 0, new Object[][] {{"IBM", 72d}, {"MSFT", 9d}, {"YAH", 7d}}, new Object[][] {{"IBM", 97d}, {"MSFT", 9d}, {"YAH", 6d}});
        expected.addResultInsRem(7200, 0, new Object[][] {{"IBM", 48d}, {"MSFT", null}, {"YAH", 6d}}, new Object[][] {{"IBM", 72d}, {"MSFT", 9d}, {"YAH", 7d}});

        ResultAssertExecution execution = new ResultAssertExecution(epService, stmt, listener, expected);
        execution.execute();
    }

    private void runAssertion11_12(String stmtText, String outputLimit)
    {
        sendTimer(0);
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        String fields[] = new String[] {"symbol", "sum(price)"};
        ResultAssertTestResult expected = new ResultAssertTestResult(CATEGORY, outputLimit, fields);
        expected.addResultInsRem(1200, 0, null, null);
        expected.addResultInsRem(2200, 0, new Object[][] {{"IBM", 75d}}, null);
        expected.addResultInsRem(3200, 0, new Object[][] {{"IBM", 75d}}, new Object[][] {{"IBM", 75d}});
        expected.addResultInsRem(4200, 0, new Object[][] {{"IBM", 75d}}, new Object[][] {{"IBM", 75d}});
        expected.addResultInsRem(5200, 0, new Object[][] {{"IBM", 97d}}, new Object[][] {{"IBM", 75d}});
        expected.addResultInsRem(6200, 0, new Object[][] {{"IBM", 72d}}, new Object[][] {{"IBM", 97d}});
        expected.addResultInsRem(7200, 0, null, new Object[][] {{"IBM", 72d}});

        ResultAssertExecution execution = new ResultAssertExecution(epService, stmt, listener, expected);
        execution.execute();
    }

    private void runAssertion17(String stmtText, String outputLimit)
    {
        sendTimer(0);
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        String fields[] = new String[] {"symbol", "sum(price)"};
        ResultAssertTestResult expected = new ResultAssertTestResult(CATEGORY, outputLimit, fields);
        expected.addResultInsRem(200, 1, new Object[][] {{"IBM", 25d}}, new Object[][] {{"IBM", null}});
        expected.addResultInsRem(1500, 1, new Object[][] {{"IBM", 49d}}, new Object[][] {{"IBM", 25d}});
        expected.addResultInsRem(3200, 0, null, null);
        expected.addResultInsRem(3500, 1, new Object[][] {{"YAH", 3d}}, new Object[][] {{"YAH", 1d}});
        expected.addResultInsRem(4300, 1, new Object[][] {{"IBM", 97d}}, new Object[][] {{"IBM", 75d}});
        expected.addResultInsRem(5700, 0, new Object[][] {{"IBM", 72d}}, new Object[][] {{"IBM", 97d}});
        expected.addResultInsRem(6300, 0, new Object[][] {{"MSFT", null}}, new Object[][] {{"MSFT", 9d}});

        ResultAssertExecution execution = new ResultAssertExecution(epService, stmt, listener, expected);
        execution.execute();
    }

    private void runAssertion18(String stmtText, String outputLimit)
    {
        sendTimer(0);
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        String fields[] = new String[] {"symbol", "sum(price)"};
        ResultAssertTestResult expected = new ResultAssertTestResult(CATEGORY, outputLimit, fields);
        expected.addResultInsert(1200, 0, new Object[][] {{"IBM", 25d}, {"MSFT", 9d}});
        expected.addResultInsert(2200, 0, new Object[][] {{"IBM", 75d}, {"MSFT", 9d}, {"YAH", 1d}});
        expected.addResultInsert(3200, 0, new Object[][] {{"IBM", 75d}, {"MSFT", 9d}, {"YAH", 1d}});
        expected.addResultInsert(4200, 0, new Object[][] {{"IBM", 75d}, {"MSFT", 9d}, {"YAH", 3d}});
        expected.addResultInsert(5200, 0, new Object[][] {{"IBM", 97d}, {"MSFT", 9d}, {"YAH", 6d}});
        expected.addResultInsert(6200, 0, new Object[][] {{"IBM", 72d}, {"MSFT", 9d}, {"YAH", 7d}});
        expected.addResultInsert(7200, 0, new Object[][] {{"IBM", 48d}, {"YAH", 6d}});

        ResultAssertExecution execution = new ResultAssertExecution(epService, stmt, listener, expected);
        execution.execute();
    }

    public void testJoinSortWindow()
    {
        sendTimer(0);

        String fields[] = "symbol,maxVol".split(",");
        String viewExpr = "select irstream symbol, max(price) as maxVol" +
                          " from " + SupportMarketDataBean.class.getName() + ".ext:sort(1, volume desc) as s0," +
                          SupportBean.class.getName() + ".win:keepall() as s1 " +
                          "group by symbol output every 1 seconds";
        EPStatement stmt = epService.getEPAdministrator().createEPL(viewExpr);
        stmt.addListener(listener);
        epService.getEPRuntime().sendEvent(new SupportBean("JOIN_KEY", -1));

        sendEvent("JOIN_KEY", 1d);
        sendEvent("JOIN_KEY", 2d);
        listener.reset();

        // moves all events out of the window,
        sendTimer(1000);        // newdata is 2 eventa, old data is the same 2 events, therefore the sum is null
        UniformPair<EventBean[]> result = listener.getDataListsFlattened();
        assertEquals(2, result.getFirst().length);
        ArrayAssertionUtil.assertPropsPerRow(result.getFirst(), fields, new Object[][] {{"JOIN_KEY", 1.0}, {"JOIN_KEY", 2.0}});
        assertEquals(2, result.getSecond().length);
        ArrayAssertionUtil.assertPropsPerRow(result.getSecond(), fields, new Object[][] {{"JOIN_KEY", null}, {"JOIN_KEY", 1.0}});
    }

    public void testLimitSnapshot()
    {
        sendTimer(0);
        String selectStmt = "select symbol, min(price) as minprice from " + SupportMarketDataBean.class.getName() +
                ".win:time(10 seconds) group by symbol output snapshot every 1 seconds order by symbol asc";

        EPStatement stmt = epService.getEPAdministrator().createEPL(selectStmt);
        stmt.addListener(listener);
        sendEvent("ABC", 20);

        sendTimer(500);
        sendEvent("IBM", 16);
        sendEvent("ABC", 14);
        assertFalse(listener.getAndClearIsInvoked());

        sendTimer(1000);
        String fields[] = new String[] {"symbol", "minprice"};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"ABC", 14d}, {"IBM", 16d}});
        assertNull(listener.getLastOldData());
        listener.reset();

        sendTimer(1500);
        sendEvent("IBM", 18);
        sendEvent("MSFT", 30);

        sendTimer(10000);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"ABC", 14d}, {"IBM", 16d}, {"MSFT", 30d}});
        assertNull(listener.getLastOldData());
        listener.reset();

        sendTimer(11000);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"IBM", 18d}, {"MSFT", 30d}});
        assertNull(listener.getLastOldData());
        listener.reset();

        sendTimer(12000);
        assertTrue(listener.isInvoked());
        assertNull(listener.getLastNewData());
        assertNull(listener.getLastOldData());
        listener.reset();
    }

    public void testLimitSnapshotLimit()
    {
        sendTimer(0);
        String selectStmt = "select symbol, min(price) as minprice from " + SupportMarketDataBean.class.getName() +
                ".win:time(10 seconds) as m, " +
                SupportBean.class.getName() + ".win:keepall() as s where s.string = m.symbol " +
                "group by symbol output snapshot every 1 seconds order by symbol asc";

        EPStatement stmt = epService.getEPAdministrator().createEPL(selectStmt);
        stmt.addListener(listener);

        for (String string : "ABC,IBM,MSFT".split(","))
        {
            epService.getEPRuntime().sendEvent(new SupportBean(string, 1));
        }

        sendEvent("ABC", 20);

        sendTimer(500);
        sendEvent("IBM", 16);
        sendEvent("ABC", 14);
        assertFalse(listener.getAndClearIsInvoked());

        sendTimer(1000);
        String fields[] = new String[] {"symbol", "minprice"};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"ABC", 14d}, {"IBM", 16d}});
        assertNull(listener.getLastOldData());
        listener.reset();

        sendTimer(1500);
        sendEvent("IBM", 18);
        sendEvent("MSFT", 30);

        sendTimer(10000);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"ABC", 14d}, {"IBM", 16d}, {"MSFT", 30d}});
        assertNull(listener.getLastOldData());
        listener.reset();

        sendTimer(10500);
        sendTimer(11000);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"IBM", 18d}, {"MSFT", 30d}});
        assertNull(listener.getLastOldData());
        listener.reset();

        sendTimer(11500);
        sendTimer(12000);
        assertTrue(listener.isInvoked());
        assertNull(listener.getLastNewData());
        assertNull(listener.getLastOldData());
        listener.reset();
    }

    public void testGroupBy_All()
    {
        String fields[] = "symbol,sum(price)".split(",");
    	String eventName = SupportMarketDataBean.class.getName();
    	String statementString = "select irstream symbol, sum(price) from " + eventName + ".win:length(5) group by symbol output all every 5 events";
    	EPStatement statement = epService.getEPAdministrator().createEPL(statementString);
    	SupportUpdateListener updateListener = new SupportUpdateListener();
    	statement.addListener(updateListener);

    	// send some events and check that only the most recent
    	// ones are kept
    	sendEvent("IBM", 1D);
    	sendEvent("IBM", 2D);
    	sendEvent("HP", 1D);
    	sendEvent("IBM", 3D);
    	sendEvent("MAC", 1D);

    	assertTrue(updateListener.getAndClearIsInvoked());
    	EventBean[] newData = updateListener.getLastNewData();
    	assertEquals(3, newData.length);
        ArrayAssertionUtil.assertPropsPerRow(newData, fields, new Object[][] {
                {"IBM", 6d}, {"HP", 1d}, {"MAC", 1d}});
    	EventBean[] oldData = updateListener.getLastOldData();
        ArrayAssertionUtil.assertPropsPerRow(oldData, fields, new Object[][] {
                {"IBM", null}, {"HP", null}, {"MAC", null}});
    }

    public void testGroupBy_Default()
    {
        String fields[] = "symbol,sum(price)".split(",");
    	String eventName = SupportMarketDataBean.class.getName();
    	String statementString = "select irstream symbol, sum(price) from " + eventName + ".win:length(5) group by symbol output every 5 events";
    	EPStatement statement = epService.getEPAdministrator().createEPL(statementString);
    	SupportUpdateListener updateListener = new SupportUpdateListener();
    	statement.addListener(updateListener);

    	// send some events and check that only the most recent
    	// ones are kept
    	sendEvent("IBM", 1D);
    	sendEvent("IBM", 2D);
    	sendEvent("HP", 1D);
    	sendEvent("IBM", 3D);
    	sendEvent("MAC", 1D);

    	assertTrue(updateListener.getAndClearIsInvoked());
    	EventBean[] newData = updateListener.getLastNewData();
        EventBean[] oldData = updateListener.getLastOldData();
    	assertEquals(5, newData.length);
        assertEquals(5, oldData.length);
        ArrayAssertionUtil.assertPropsPerRow(newData, fields, new Object[][] {
                {"IBM", 1d}, {"IBM", 3d}, {"HP", 1d}, {"IBM", 6d}, {"MAC", 1d}});
        ArrayAssertionUtil.assertPropsPerRow(oldData, fields, new Object[][] {
                {"IBM", null}, {"IBM", 1d}, {"HP", null}, {"IBM", 3d}, {"MAC", null}});        
    }

    public void testMaxTimeWindow()
    {
        sendTimer(0);

        String fields[] = "symbol,maxVol".split(",");
        String viewExpr = "select irstream symbol, max(price) as maxVol" +
                          " from " + SupportMarketDataBean.class.getName() + ".win:time(1 sec) " +
                          "group by symbol output every 1 seconds";
        selectTestView = epService.getEPAdministrator().createEPL(viewExpr);
        selectTestView.addListener(listener);

        sendEvent("SYM1", 1d);
        sendEvent("SYM1", 2d);
        listener.reset();

        // moves all events out of the window,
        sendTimer(1000);        // newdata is 2 eventa, old data is the same 2 events, therefore the sum is null
        UniformPair<EventBean[]> result = listener.getDataListsFlattened();
        assertEquals(3, result.getFirst().length);
        ArrayAssertionUtil.assertPropsPerRow(result.getFirst(), fields, new Object[][] {{"SYM1", 1.0}, {"SYM1", 2.0}, {"SYM1", null}});
        assertEquals(3, result.getSecond().length);
        ArrayAssertionUtil.assertPropsPerRow(result.getSecond(), fields, new Object[][] {{"SYM1", null}, {"SYM1", 1.0}, {"SYM1", 2.0}});
    }

    public void testNoJoinLast()
	{
	    String viewExpr = "select irstream symbol," +
	                             "sum(price) as mySum," +
	                             "avg(price) as myAvg " +
	                      "from " + SupportMarketDataBean.class.getName() + ".win:length(3) " +
	                      "where symbol='DELL' or symbol='IBM' or symbol='GE' " +
	                      "group by symbol " +
	                      "output last every 2 events";

	    selectTestView = epService.getEPAdministrator().createEPL(viewExpr);
	    selectTestView.addListener(listener);

	    runAssertionLast();
	}

    public void testNoOutputClauseView()
    {
    	String viewExpr = "select irstream symbol," +
    	"sum(price) as mySum," +
    	"avg(price) as myAvg " +
    	"from " + SupportMarketDataBean.class.getName() + ".win:length(3) " +
    	"where symbol='DELL' or symbol='IBM' or symbol='GE' " +
    	"group by symbol";

    	selectTestView = epService.getEPAdministrator().createEPL(viewExpr);
    	selectTestView.addListener(listener);

    	runAssertionSingle();
    }

    public void testNoOutputClauseJoin()
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

    	runAssertionSingle();
    }

	public void testNoJoinAll()
    {
        String viewExpr = "select irstream symbol," +
                                 "sum(price) as mySum," +
                                 "avg(price) as myAvg " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:length(5) " +
                          "where symbol='DELL' or symbol='IBM' or symbol='GE' " +
                          "group by symbol " +
                          "output all every 2 events";

        selectTestView = epService.getEPAdministrator().createEPL(viewExpr);
        selectTestView.addListener(listener);

        runAssertionAll();
    }

    public void testJoinLast()
	{
	    String viewExpr = "select irstream symbol," +
	                             "sum(price) as mySum," +
	                             "avg(price) as myAvg " +
	                      "from " + SupportBeanString.class.getName() + ".win:length(100) as one, " +
	                                SupportMarketDataBean.class.getName() + ".win:length(3) as two " +
	                      "where (symbol='DELL' or symbol='IBM' or symbol='GE') " +
	                      "       and one.string = two.symbol " +
	                      "group by symbol " +
	                      "output last every 2 events";

	    selectTestView = epService.getEPAdministrator().createEPL(viewExpr);
	    selectTestView.addListener(listener);

	    epService.getEPRuntime().sendEvent(new SupportBeanString(SYMBOL_DELL));
	    epService.getEPRuntime().sendEvent(new SupportBeanString(SYMBOL_IBM));
	    epService.getEPRuntime().sendEvent(new SupportBeanString("AAA"));

	    runAssertionLast();
	}

	public void testJoinAll()
    {
        String viewExpr = "select irstream symbol," +
                                 "sum(price) as mySum," +
                                 "avg(price) as myAvg " +
                          "from " + SupportBeanString.class.getName() + ".win:length(100) as one, " +
                                    SupportMarketDataBean.class.getName() + ".win:length(5) as two " +
                          "where (symbol='DELL' or symbol='IBM' or symbol='GE') " +
                          "       and one.string = two.symbol " +
                          "group by symbol " +
                          "output all every 2 events";

        selectTestView = epService.getEPAdministrator().createEPL(viewExpr);
        selectTestView.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBeanString(SYMBOL_DELL));
        epService.getEPRuntime().sendEvent(new SupportBeanString(SYMBOL_IBM));
        epService.getEPRuntime().sendEvent(new SupportBeanString("AAA"));

        runAssertionAll();
    }

    private void runAssertionLast()
	{
	    // assert select result type
	    assertEquals(String.class, selectTestView.getEventType().getPropertyType("symbol"));
	    assertEquals(Double.class, selectTestView.getEventType().getPropertyType("mySum"));
	    assertEquals(Double.class, selectTestView.getEventType().getPropertyType("myAvg"));

	    sendEvent(SYMBOL_DELL, 10);
	    assertFalse(listener.isInvoked());

	    sendEvent(SYMBOL_DELL, 20);
	    assertEvent(SYMBOL_DELL,
	            null, null,
	            30d, 15d);
	    listener.reset();

	    sendEvent(SYMBOL_DELL, 100);
	    assertFalse(listener.isInvoked());

	    sendEvent(SYMBOL_DELL, 50);
	    assertEvent(SYMBOL_DELL,
	    		30d, 15d,
	            170d, 170/3d);
	}

    private void runAssertionSingle()
	{
	    // assert select result type
	    assertEquals(String.class, selectTestView.getEventType().getPropertyType("symbol"));
	    assertEquals(Double.class, selectTestView.getEventType().getPropertyType("mySum"));
	    assertEquals(Double.class, selectTestView.getEventType().getPropertyType("myAvg"));

	    sendEvent(SYMBOL_DELL, 10);
	    assertTrue(listener.isInvoked());
	    assertEvent(SYMBOL_DELL,
            	null, null,
            	10d, 10d);

	    sendEvent(SYMBOL_IBM, 20);
	    assertTrue(listener.isInvoked());
	    assertEvent(SYMBOL_IBM,
	            	null, null,
	            	20d, 20d);
	}

	private void runAssertionAll()
    {
        // assert select result type
        assertEquals(String.class, selectTestView.getEventType().getPropertyType("symbol"));
        assertEquals(Double.class, selectTestView.getEventType().getPropertyType("mySum"));
        assertEquals(Double.class, selectTestView.getEventType().getPropertyType("myAvg"));

        sendEvent(SYMBOL_IBM, 70);
        assertFalse(listener.isInvoked());

        sendEvent(SYMBOL_DELL, 10);
        assertEvents(SYMBOL_IBM,
        		null, null,
        		70d, 70d,
        		SYMBOL_DELL,
                null, null,
                10d, 10d);
	    listener.reset();

        sendEvent(SYMBOL_DELL, 20);
        assertFalse(listener.isInvoked());


        sendEvent(SYMBOL_DELL, 100);
        assertEvents(SYMBOL_IBM,
        		70d, 70d,
        		70d, 70d,
        		SYMBOL_DELL,
                10d, 10d,
                130d, 130d/3d);
    }

    private void assertEvent(String symbol,
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
}
