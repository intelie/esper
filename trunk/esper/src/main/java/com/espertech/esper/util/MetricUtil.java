package com.espertech.esper.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class MetricUtil
{
    private static final Log log = LogFactory.getLog(MetricUtil.class);

    private static ThreadMXBean threadMXBean;
    private static boolean isCPUEnabled;

    public static void initialize()
    {
        threadMXBean = ManagementFactory.getThreadMXBean();
        isCPUEnabled = threadMXBean.isCurrentThreadCpuTimeSupported();

        if (!isCPUEnabled)
        {
            log.warn("CPU metrics reporting is not enabled by Java VM");
        }
    }

    public static long getCPUCurrentThread()
    {
        if (isCPUEnabled)
        {
            return threadMXBean.getCurrentThreadCpuTime();
        }
        return 0;
    }

    public static long getWall()
    {
        return System.nanoTime();
    }
}
