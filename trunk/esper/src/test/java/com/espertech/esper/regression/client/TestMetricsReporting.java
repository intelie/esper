package com.espertech.esper.regression.client;

import com.espertech.esper.client.*;
import com.espertech.esper.client.metric.EngineMetric;
import com.espertech.esper.client.metric.StatementMetric;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportSubscriber;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class TestMetricsReporting extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;
    private SupportUpdateListener listenerStmtMetric;
    private SupportUpdateListener listenerEngineMetric;
    private SupportUpdateListener listenerTwo;

    private final long cpuGoalOneNano = 80 * 1000 * 1000;
    private final long cpuGoalTwoNano = 50 * 1000 * 1000;
    private final long wallGoalOneMsec = 200;
    private final long wallGoalTwoMsec = 400;

    public void setUp()
    {
        listener = new SupportUpdateListener(); 
        listenerTwo = new SupportUpdateListener();

        listenerStmtMetric = new SupportUpdateListener();
        listenerEngineMetric = new SupportUpdateListener();
    }

    public void tearDown()
    {
        try
        {
            if (epService != null)
            {
                epService.destroy();
            }
        }
        catch (RuntimeException ex)
        {
            ex.printStackTrace();
        }
    }

    public void testEngineMetrics()
    {
        epService = EPServiceProviderManager.getProvider("MyURI", getConfig(10000, -1));
        epService.initialize();

        String[] engineFields = "engineURI,timestamp,inputCount,scheduleDepth".split(",");
        sendTimer(1000);

        String text = "select * from " + EngineMetric.class.getName();
        EPStatement stmt = epService.getEPAdministrator().createEPL(text);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean());

        sendTimer(10999);
        assertFalse(listener.isInvoked());

        epService.getEPAdministrator().createEPL("select * from pattern[timer:interval(5 sec)]");

        sendTimer(11000);
        EventBean event = listener.assertOneGetNewAndReset();
        ArrayAssertionUtil.assertProps(event, engineFields, new Object[] {"MyURI", 11000L, 1L, 1L});

        epService.getEPRuntime().sendEvent(new SupportBean());
        epService.getEPRuntime().sendEvent(new SupportBean());

        sendTimer(20000);
        sendTimer(21000);
        event = listener.assertOneGetNewAndReset();
        ArrayAssertionUtil.assertProps(event, engineFields, new Object[] {"MyURI", 21000L, 4L, 0L});
    }

    public void testStatementGroups()
    {
        Configuration config = getConfig(-1, 7000);

        ConfigurationMetricsReporting.StmtGroupMetrics groupOne = new ConfigurationMetricsReporting.StmtGroupMetrics();
        groupOne.setInterval(8000);
        groupOne.addIncludeLike("%GroupOne%");
        groupOne.setReportInactive(true);
        config.getEngineDefaults().getMetricsReporting().addStmtGroup("GroupOneStatements", groupOne);

        ConfigurationMetricsReporting.StmtGroupMetrics groupTwo = new ConfigurationMetricsReporting.StmtGroupMetrics();
        groupTwo.setInterval(6000);
        groupTwo.setDefaultInclude(true);
        groupTwo.addExcludeLike("%Default%");
        groupTwo.addExcludeLike("%Metrics%");
        config.getEngineDefaults().getMetricsReporting().addStmtGroup("GroupTwoNonDefaultStatements", groupTwo);

        ConfigurationMetricsReporting.StmtGroupMetrics groupThree = new ConfigurationMetricsReporting.StmtGroupMetrics();
        groupThree.setInterval(-1);
        groupThree.addIncludeLike("%Metrics%");
        config.getEngineDefaults().getMetricsReporting().addStmtGroup("MetricsStatements", groupThree);

        epService = EPServiceProviderManager.getProvider("MyURI", config);
        epService.initialize();

        sendTimer(0);
        
        epService.getEPAdministrator().createEPL("select * from SupportBean(intPrimitive = 1).win:keepall()", "GroupOne");
        EPStatement stmt = epService.getEPAdministrator().createEPL("select * from SupportBean(intPrimitive = 2).win:keepall()", "GroupTwo");
        stmt.setSubscriber(new SupportSubscriber());
        epService.getEPAdministrator().createEPL("select * from SupportBean(intPrimitive = 3).win:keepall()", "Default");

        stmt = epService.getEPAdministrator().createEPL("select * from " + StatementMetric.class.getName(), "StmtMetrics");
        stmt.addListener(listener);

        sendTimer(6000);
        sendTimer(7000);
        assertFalse(listener.isInvoked());

        sendTimer(8000);
        String[] fields = "statementName,numOutputIStream".split(",");
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"GroupOne", 0L});

        sendTimer(12000);
        sendTimer(14000);
        sendTimer(15999);
        assertFalse(listener.isInvoked());

        sendTimer(16000);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"GroupOne", 0L});

        // should report as groupTwo
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 2));
        sendTimer(17999);
        assertFalse(listener.isInvoked());

        sendTimer(18000);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"GroupTwo", 1L});

        // should report as groupTwo
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 3));
        sendTimer(20999);
        assertFalse(listener.isInvoked());

        sendTimer(21000);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"Default", 0L});

        // turn off group 1
        epService.getEPAdministrator().getConfiguration().setMetricsReportingInterval("GroupOneStatements", -1);
        sendTimer(24000);
        assertFalse(listener.isInvoked());

        // turn on group 1
        epService.getEPAdministrator().getConfiguration().setMetricsReportingInterval("GroupOneStatements", 1000);
        sendTimer(25000);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"GroupOne", 0L});
    }

    public void testStatementMetrics()
    {
        Configuration config = getConfig(-1, -1);

        // report on all statements every 10 seconds
        ConfigurationMetricsReporting.StmtGroupMetrics configOne = new ConfigurationMetricsReporting.StmtGroupMetrics();
        configOne.setInterval(10000);
        configOne.addIncludeLike("%cpuStmt%");
        configOne.addIncludeLike("%wallStmt%");
        config.getEngineDefaults().getMetricsReporting().addStmtGroup("nonmetrics", configOne);

        // exclude metrics themselves from reporting
        ConfigurationMetricsReporting.StmtGroupMetrics configTwo = new ConfigurationMetricsReporting.StmtGroupMetrics();
        configTwo.setInterval(-1);
        configOne.addExcludeLike("%metrics%");
        config.getEngineDefaults().getMetricsReporting().addStmtGroup("metrics", configTwo);

        epService = EPServiceProviderManager.getProvider("MyURI", config);
        epService.initialize();

        sendTimer(1000);

        EPStatement[] statements = new EPStatement[5];
        statements[0] = epService.getEPAdministrator().createEPL("select * from " + StatementMetric.class.getName(), "stmt_metrics");
        statements[0].addListener(listener);

        statements[1] = epService.getEPAdministrator().createEPL("select * from SupportBean(intPrimitive=1).win:keepall() where MyMetricFunctions.takeCPUTime(longPrimitive)", "cpuStmtOne");
        statements[1].addListener(listenerTwo);
        statements[2] = epService.getEPAdministrator().createEPL("select * from SupportBean(intPrimitive=2).win:keepall() where MyMetricFunctions.takeCPUTime(longPrimitive)", "cpuStmtTwo");
        statements[2].addListener(listenerTwo);
        statements[3] = epService.getEPAdministrator().createEPL("select * from SupportBean(intPrimitive=3).win:keepall() where MyMetricFunctions.takeWallTime(longPrimitive)", "wallStmtThree");
        statements[3].addListener(listenerTwo);
        statements[4] = epService.getEPAdministrator().createEPL("select * from SupportBean(intPrimitive=4).win:keepall() where MyMetricFunctions.takeWallTime(longPrimitive)", "wallStmtFour");
        statements[4].addListener(listenerTwo);

        sendEvent("E1", 1, cpuGoalOneNano);
        sendEvent("E2", 2, cpuGoalTwoNano);
        sendEvent("E3", 3, wallGoalOneMsec);
        sendEvent("E4", 4, wallGoalTwoMsec);

        sendTimer(10999);
        assertFalse(listener.isInvoked());

        sendTimer(11000);
        runAssertion(11000);

        sendEvent("E1", 1, cpuGoalOneNano);
        sendEvent("E2", 2, cpuGoalTwoNano);
        sendEvent("E3", 3, wallGoalOneMsec);
        sendEvent("E4", 4, wallGoalTwoMsec);

        sendTimer(21000);
        runAssertion(21000);

        // destroy all application stmts
        for (int i = 1; i < 5; i++)
        {
            statements[i].destroy();
        }
        sendTimer(31000);
        assertFalse(listener.isInvoked());
    }

    public void testEnabledDisableRuntime()
    {
        EPStatement[] statements = new EPStatement[5];
        Configuration config = getConfig(10000, 10000);
        epService = EPServiceProviderManager.getProvider("MyURI", config);
        epService.initialize();

        sendTimer(1000);

        statements[0] = epService.getEPAdministrator().createEPL("select * from " + StatementMetric.class.getName(),"stmtmetric");
        statements[0].addListener(listenerStmtMetric);

        statements[1] = epService.getEPAdministrator().createEPL("select * from " + EngineMetric.class.getName(),"enginemetric");
        statements[1].addListener(listenerEngineMetric);

        statements[2] = epService.getEPAdministrator().createEPL("select * from SupportBean(intPrimitive=1).win:keepall() where MyMetricFunctions.takeCPUTime(longPrimitive)");
        sendEvent("E1", 1, cpuGoalOneNano);

        sendTimer(11000);
        assertTrue(listenerStmtMetric.getAndClearIsInvoked());
        assertTrue(listenerEngineMetric.getAndClearIsInvoked());

        epService.getEPAdministrator().getConfiguration().setMetricsReportingDisabled();
        sendEvent("E2", 2, cpuGoalOneNano);
        sendTimer(21000);
        assertFalse(listenerStmtMetric.getAndClearIsInvoked());
        assertFalse(listenerEngineMetric.getAndClearIsInvoked());

        sendTimer(31000);
        sendEvent("E3", 3, cpuGoalOneNano);
        assertFalse(listenerStmtMetric.getAndClearIsInvoked());
        assertFalse(listenerEngineMetric.getAndClearIsInvoked());

        epService.getEPAdministrator().getConfiguration().setMetricsReportingEnabled();
        sendEvent("E4", 4, cpuGoalOneNano);
        sendTimer(41000);
        assertTrue(listenerStmtMetric.getAndClearIsInvoked());
        assertTrue(listenerEngineMetric.getAndClearIsInvoked());

        statements[2].destroy();
        sendTimer(51000);
        assertTrue(listenerStmtMetric.isInvoked()); // metrics statements reported themselves
        assertTrue(listenerEngineMetric.isInvoked());
    }

    public void testEnabledDisableStatement()
    {
        String[] fields = new String[] {"statementName"};
        EPStatement[] statements = new EPStatement[5];
        Configuration config = getConfig(-1, 10000);

        ConfigurationMetricsReporting.StmtGroupMetrics configOne = new ConfigurationMetricsReporting.StmtGroupMetrics();
        configOne.setInterval(-1);
        configOne.addIncludeLike("%@METRIC%");
        config.getEngineDefaults().getMetricsReporting().addStmtGroup("metrics", configOne);

        epService = EPServiceProviderManager.getProvider("MyURI", config);
        epService.initialize();

        sendTimer(1000);

        statements[0] = epService.getEPAdministrator().createEPL("select * from " + StatementMetric.class.getName(),"MyStatement@METRIC");
        statements[0].addListener(listenerStmtMetric);

        statements[1] = epService.getEPAdministrator().createEPL("select * from SupportBean(intPrimitive=1).win:keepall() where 2=2", "stmtone");
        sendEvent("E1", 1, cpuGoalOneNano);
        statements[2] = epService.getEPAdministrator().createEPL("select * from SupportBean(intPrimitive>0).std:lastevent() where 1=1", "stmttwo");
        sendEvent("E2", 1, cpuGoalOneNano);

        sendTimer(11000);
        ArrayAssertionUtil.assertPropsPerRow(listenerStmtMetric.getNewDataListFlattened(), fields, new Object[][] {{"stmtone"}, {"stmttwo"}});
        listenerStmtMetric.reset();

        sendEvent("E1", 1, cpuGoalOneNano);
        sendTimer(21000);
        ArrayAssertionUtil.assertPropsPerRow(listenerStmtMetric.getNewDataListFlattened(), fields, new Object[][] {{"stmtone"}, {"stmttwo"}});
        listenerStmtMetric.reset();

        epService.getEPAdministrator().getConfiguration().setMetricsReportingStmtDisabled("stmtone");

        sendEvent("E1", 1, cpuGoalOneNano);
        sendTimer(31000);
        ArrayAssertionUtil.assertPropsPerRow(listenerStmtMetric.getNewDataListFlattened(), fields, new Object[][] {{"stmttwo"}});
        listenerStmtMetric.reset();

        epService.getEPAdministrator().getConfiguration().setMetricsReportingStmtEnabled("stmtone");
        epService.getEPAdministrator().getConfiguration().setMetricsReportingStmtDisabled("stmttwo");

        sendEvent("E1", 1, cpuGoalOneNano);
        sendTimer(41000);
        ArrayAssertionUtil.assertPropsPerRow(listenerStmtMetric.getNewDataListFlattened(), fields, new Object[][] {{"stmtone"}});
        listenerStmtMetric.reset();
    }

    private void runAssertion(long timestamp)
    {
        String[] fields = "engineURI,statementName".split(",");

        assertEquals(4, listener.getNewDataList().size());
        EventBean[] received = listener.getNewDataListFlattened();

        ArrayAssertionUtil.assertProps(received[0], fields, new Object[] {"MyURI", "cpuStmtOne"});
        ArrayAssertionUtil.assertProps(received[1], fields, new Object[] {"MyURI", "cpuStmtTwo"});
        ArrayAssertionUtil.assertProps(received[2], fields, new Object[] {"MyURI", "wallStmtThree"});
        ArrayAssertionUtil.assertProps(received[3], fields, new Object[] {"MyURI", "wallStmtFour"});

        long cpuOne = (Long) received[0].get("cpuTime");
        long cpuTwo = (Long) received[1].get("cpuTime");
        long wallOne = (Long) received[2].get("wallTime");
        long wallTwo = (Long) received[3].get("wallTime");

        assertTrue("cpuOne=" + cpuOne, cpuOne > cpuGoalOneNano);
        assertTrue("cpuTwo=" + cpuTwo, cpuTwo > cpuGoalTwoNano);
        assertTrue("wallOne=" + wallOne, (wallOne + 50) > wallGoalOneMsec);
        assertTrue("wallTwo=" + wallTwo, (wallTwo + 50) > wallGoalTwoMsec);

        for (int i = 0; i < 4; i++)
        {
            assertEquals(1L, received[i].get("numOutputIStream"));
            assertEquals(0L, received[i].get("numOutputRStream"));
            assertEquals(timestamp, received[i].get("timestamp"));
        }

        listener.reset();
    }

    public void testTakeCPUTime()
    {
        ThreadMXBean mbean = ManagementFactory.getThreadMXBean();
        if (!mbean.isThreadCpuTimeEnabled())
        {
            fail("ThreadMXBean CPU time reporting is not enabled");
        }
        
        long msecMultiplier = 1000 * 1000;
        long msecGoal = 10;
        long cpuGoal = msecGoal * msecMultiplier;

        long beforeCPU = mbean.getCurrentThreadCpuTime();
        MyMetricFunctions.takeCPUTime(cpuGoal);
        long afterCPU = mbean.getCurrentThreadCpuTime();
        assertTrue((afterCPU - beforeCPU) > cpuGoal);
    }

    /**
     * Comment-in this test for manual/threading tests.
     */
    public void testManual()
    {
        Configuration config = getConfig(1000, 1000);
        config.getEngineDefaults().getMetricsReporting().setThreading(true);

        /*
        epService = EPServiceProviderManager.getProvider("MyURI", config);
        epService.initialize();

        EPStatement[] statements = new EPStatement[5];

        statements[0] = epService.getEPAdministrator().createEPL("select * from " + StatementMetric.class.getName(), "stmt_metrics");
        statements[0].addListener(new PrintUpdateListener());

        statements[1] = epService.getEPAdministrator().createEPL("select * from " + EngineMetric.class.getName(), "engine_metrics");
        statements[1].addListener(new PrintUpdateListener());

        statements[2] = epService.getEPAdministrator().createEPL("select * from SupportBean(intPrimitive=1).win:keepall() where MyMetricFunctions.takeCPUTime(longPrimitive)", "cpuStmtOne");

        sleep(20000);
        */
    }

    private void sendTimer(long currentTime)
    {
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(currentTime));
    }

    private Configuration getConfig(long engineMetricInterval, long stmtMetricInterval)
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.getEngineDefaults().getMetricsReporting().setEnableMetricsReporting(true);
        configuration.getEngineDefaults().getMetricsReporting().setThreading(false);
        configuration.getEngineDefaults().getMetricsReporting().setEngineInterval(engineMetricInterval);
        configuration.getEngineDefaults().getMetricsReporting().setStatementInterval(stmtMetricInterval);

        configuration.addImport(MyMetricFunctions.class.getName());

        configuration.addEventType("SupportBean", SupportBean.class);

        return configuration;
    }

    private void sendEvent(String id, int intPrimitive, long longPrimitive)
    {
        SupportBean bean = new SupportBean(id, intPrimitive);
        bean.setLongPrimitive(longPrimitive);
        epService.getEPRuntime().sendEvent(bean);
    }

    private void sleep(long msec)
    {
        try
        {
            Thread.sleep(msec);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
