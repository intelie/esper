package com.espertech.esper.regression.client;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.metric.EngineMetric;
import com.espertech.esper.client.metric.StatementMetric;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.event.EventBean;
import junit.framework.TestCase;

import java.lang.management.ThreadMXBean;
import java.lang.management.ManagementFactory;
import java.math.BigInteger;

public class TestMetricsReporting extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        listener = new SupportUpdateListener();

        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.getEngineDefaults().getThreading().setInternalTimerEnabled(false);

        configuration.getEngineDefaults().getMetricsReporting().setEnableMetricsReporting(true);
        configuration.getEngineDefaults().getMetricsReporting().setUseMetricsThreading(false);  // use external timer thread
        configuration.getEngineDefaults().getMetricsReporting().setStatementMetricsInterval(-1);

        configuration.addImport(MyMetricFunctions.class.getName());

        configuration.addEventTypeAlias("SupportBean", SupportBean.class);
        
        epService = EPServiceProviderManager.getProvider("MyURI", configuration);
        epService.initialize();
    }

    public void tearDown()
    {
        try
        {
            epService.destroy();
        }
        catch (RuntimeException ex)
        {
            ex.printStackTrace();
        }
    }

    public void testEngineMetrics()
    {
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
        String[] engineFields = "engineURI,statementName,cpuTime".split(",");
        sendTimer(1000);

        String text = "select * from " + StatementMetric.class.getName();
        EPStatement stmt = epService.getEPAdministrator().createEPL(text);
        stmt.addListener(listener);

        long cpuGoalOne = 1000000;
        long cpuGoalTwo = 2000000;
        EPStatement stmtOne = epService.getEPAdministrator().createEPL("select * from SupportBean(intPrimitive=1) where MyMetricFunctions.takeCPUTime(" + cpuGoalOne + ")", "stmtOne");
        EPStatement stmtTwo = epService.getEPAdministrator().createEPL("select * from SupportBean(intPrimitive=2) where MyMetricFunctions.takeCPUTime(" + cpuGoalOne + ")", "stmtTwo");

        for (int i = 0; i < 10000; i++)
        {
            epService.getEPRuntime().sendEvent(new SupportBean("E", 1));
            epService.getEPRuntime().sendEvent(new SupportBean("E", 2));
        }

        sendTimer(10999);
        assertFalse(listener.isInvoked());

        sendTimer(11000);
        assertEquals(2, listener.getLastNewData().length);
        ArrayAssertionUtil.assertProps(listener.getLastNewData()[0], engineFields, new Object[] {"MyURI", "stmtOne"});
        ArrayAssertionUtil.assertProps(listener.getLastNewData()[1], engineFields, new Object[] {"MyURI", "stmtTwo"});
        BigInteger cpuOne = (BigInteger) listener.getLastNewData()[0].get("cpuTime");
        BigInteger cpuTwo = (BigInteger) listener.getLastNewData()[1].get("cpuTime");
        assertTrue(cpuOne.compareTo(BigInteger.valueOf(cpuGoalOne * 10000L)) > 0);
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
}
