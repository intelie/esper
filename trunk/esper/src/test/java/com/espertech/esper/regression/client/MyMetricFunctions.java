package com.espertech.esper.regression.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.management.ThreadMXBean;
import java.lang.management.ManagementFactory;

public class MyMetricFunctions
{
    private static final Log log = LogFactory.getLog(MyMetricFunctions.class);

    public static boolean takeCPUTime(long nanoSecTarget)
    {
        if (nanoSecTarget < 100)
        {
            throw new RuntimeException("CPU time wait nsec less then zero, was " + nanoSecTarget);
        }

        ThreadMXBean mbean = ManagementFactory.getThreadMXBean();
        if (!mbean.isThreadCpuTimeEnabled())
        {
            throw new RuntimeException("ThreadMXBean CPU time reporting not enabled");
        }

        long before = mbean.getCurrentThreadCpuTime();

        while(true)
        {
            long after = mbean.getCurrentThreadCpuTime();
            long spent = after - before;
            if (spent > nanoSecTarget)
            {
                break;
            }
        }

        return true;
    }
}
