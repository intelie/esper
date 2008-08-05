package com.espertech.esper.regression.client;

import com.espertech.esper.client.*;
import com.espertech.esper.client.metric.EngineMetric;
import com.espertech.esper.client.metric.StatementMetric;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class TestMetricsReporting extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;
    private SupportUpdateListener listenerTwo;

    private final long cpuGoalOneNano = 80 * 1000 * 1000;
    private final long cpuGoalTwoNano = 50 * 1000 * 1000;
    private final long wallGoalOneMsec = 200;
    private final long wallGoalTwoMsec = 400;

    public void setUp()
    {
        listener = new SupportUpdateListener(); 
        listenerTwo = new SupportUpdateListener();
    }

    // TODO
    //  proper runtime integration: count for all places in which statement can be executed
    //  add output event counting
    //  statement destroy
    //  add threading
    //  statement groups testing
    //  add engine CPU/wall time
    //  statements that are not currently reported on should not get counted
    //  runtime API
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

        String text = "select * from " + StatementMetric.class.getName();
        EPStatement stmt = epService.getEPAdministrator().createEPL(text, "stmt_metrics");
        stmt.addListener(listener);

        stmt = epService.getEPAdministrator().createEPL("select * from SupportBean(intPrimitive=1).win:keepall() where MyMetricFunctions.takeCPUTime(longPrimitive)", "cpuStmtOne");
        stmt.addListener(listenerTwo);
        stmt = epService.getEPAdministrator().createEPL("select * from SupportBean(intPrimitive=2).win:keepall() where MyMetricFunctions.takeCPUTime(longPrimitive)", "cpuStmtTwo");
        stmt.addListener(listenerTwo);
        stmt = epService.getEPAdministrator().createEPL("select * from SupportBean(intPrimitive=3).win:keepall() where MyMetricFunctions.takeWallTime(longPrimitive)", "wallStmtThree");
        stmt.addListener(listenerTwo);
        stmt = epService.getEPAdministrator().createEPL("select * from SupportBean(intPrimitive=4).win:keepall() where MyMetricFunctions.takeWallTime(longPrimitive)", "wallStmtFour");
        stmt.addListener(listenerTwo);

        sendEvent("E1", 1, cpuGoalOneNano);
        sendEvent("E2", 2, cpuGoalTwoNano);
        sendEvent("E3", 3, wallGoalOneMsec);
        sendEvent("E4", 4, wallGoalTwoMsec);

        sendTimer(10999);
        assertFalse(listener.isInvoked());

        sendTimer(11000);
        runAssertion();

        sendEvent("E1", 1, cpuGoalOneNano);
        sendEvent("E2", 2, cpuGoalTwoNano);
        sendEvent("E3", 3, wallGoalOneMsec);
        sendEvent("E4", 4, wallGoalTwoMsec);

        sendTimer(21000);
        runAssertion();
    }

    private void runAssertion()
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
        long beforeWall = System.currentTimeMillis();
        MyMetricFunctions.takeCPUTime(cpuGoal);
        long afterCPU = mbean.getCurrentThreadCpuTime();
        long afterWall = System.currentTimeMillis();
        System.out.println("Goal CPU=" + cpuGoal);
        System.out.println("DeltaCPU=" + (afterCPU - beforeCPU));
        System.out.println("DeltaWall=" + (afterWall - beforeWall));
        assertTrue((afterCPU - beforeCPU) > cpuGoal);
    }

    private void sendTimer(long currentTime)
    {
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(currentTime));
    }

    private Configuration getConfig(long engineMetricInterval, long stmtMetricInterval)
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.getEngineDefaults().getThreading().setInternalTimerEnabled(false);

        configuration.getEngineDefaults().getMetricsReporting().setEnableMetricsReporting(true);
        configuration.getEngineDefaults().getMetricsReporting().setUseMetricsThreading(false);  // use external timer thread
        configuration.getEngineDefaults().getMetricsReporting().setEngineMetricsInterval(engineMetricInterval);
        configuration.getEngineDefaults().getMetricsReporting().setDefaultStmtMetricsInterval(stmtMetricInterval);

        configuration.addImport(MyMetricFunctions.class.getName());

        configuration.addEventTypeAlias("SupportBean", SupportBean.class);

        return configuration;
    }

    private void sendEvent(String id, int intPrimitive, long longPrimitive)
    {
        SupportBean bean = new SupportBean(id, intPrimitive);
        bean.setLongPrimitive(longPrimitive);
        epService.getEPRuntime().sendEvent(bean);
    }
}
