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

package com.espertech.esper.regression.epl;

import junit.framework.TestCase;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.client.SupportConfigFactory;

public class TestPerfSubselectFiltered extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("S0", SupportBean_S0.class);
        config.addEventType("S1", SupportBean_S1.class);
        config.addEventType("S2", SupportBean_S2.class);
        config.addEventType("S3", SupportBean_S3.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testPerformanceOneCriteria()
    {
        String stmtText = "select (select p10 from S1.win:length(100000) where id = s0.id) as value from S0 as s0";

        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        // preload with 10k events
        for (int i = 0; i < 10000; i++)
        {
            epService.getEPRuntime().sendEvent(new SupportBean_S1(i, Integer.toString(i)));
        }

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++)
        {
            int index = 5000 + i % 1000;
            epService.getEPRuntime().sendEvent(new SupportBean_S0(index, Integer.toString(index)));
            assertEquals(Integer.toString(index), listener.assertOneGetNewAndReset().get("value"));
        }
        long endTime = System.currentTimeMillis();
        long delta = endTime - startTime;

        assertTrue("Failed perf test, delta=" + delta, delta < 1000);
    }

    public void testPerformanceTwoCriteria()
    {
        String stmtText = "select (select p10 from S1.win:length(100000) where s0.id = id and p10 = s0.p00) as value from S0 as s0";

        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        // preload with 10k events
        for (int i = 0; i < 10000; i++)
        {
            epService.getEPRuntime().sendEvent(new SupportBean_S1(i, Integer.toString(i)));
        }

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++)
        {
            int index = 5000 + i % 1000;
            epService.getEPRuntime().sendEvent(new SupportBean_S0(index, Integer.toString(index)));
            assertEquals(Integer.toString(index), listener.assertOneGetNewAndReset().get("value"));
        }
        long endTime = System.currentTimeMillis();
        long delta = endTime - startTime;

        assertTrue("Failed perf test, delta=" + delta, delta < 1000);
    }

    public void testPerformanceJoin3CriteriaSceneOne()
    {
        String stmtText = "select (select p00 from S0.win:length(100000) where p00 = s1.p10 and p01 = s2.p20 and p02 = s3.p30) as value " +
                "from S1.win:length(100000) as s1, S2.win:length(100000) as s2, S3.win:length(100000) as s3 where s1.id = s2.id and s2.id = s3.id";
        tryPerfJoin3Criteria(stmtText);
    }

    public void testPerformanceJoin3CriteriaSceneTwo()
    {
        String stmtText = "select (select p00 from S0.win:length(100000) where p01 = s2.p20 and p00 = s1.p10 and p02 = s3.p30 and id >= 0) as value " +
                "from S3.win:length(100000) as s3, S1.win:length(100000) as s1, S2.win:length(100000) as s2 where s2.id = s3.id and s1.id = s2.id";
        tryPerfJoin3Criteria(stmtText);
    }

    private void tryPerfJoin3Criteria(String stmtText)
    {
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        // preload with 10k events
        for (int i = 0; i < 10000; i++)
        {
            epService.getEPRuntime().sendEvent(new SupportBean_S0(i, Integer.toString(i), Integer.toString(i + 1), Integer.toString(i + 2)));
        }

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 5000; i++)
        {
            int index = i;
            epService.getEPRuntime().sendEvent(new SupportBean_S1(i, Integer.toString(index)));
            epService.getEPRuntime().sendEvent(new SupportBean_S2(i, Integer.toString(index + 1)));
            epService.getEPRuntime().sendEvent(new SupportBean_S3(i, Integer.toString(index + 2)));
            assertEquals(Integer.toString(index), listener.assertOneGetNewAndReset().get("value"));
        }
        long endTime = System.currentTimeMillis();
        long delta = endTime - startTime;

        assertTrue("Failed perf test, delta=" + delta, delta < 1500);
    }
}
