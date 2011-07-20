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

    public static boolean takeWallTime(long msecTarget)
    {
        try
        {
            Thread.sleep(msecTarget);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        return true;
    }
}
