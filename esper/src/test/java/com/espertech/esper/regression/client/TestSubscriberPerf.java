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

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.client.EventBean;

import java.util.List;
import java.util.ArrayList;

public class TestSubscriberPerf extends TestCase
{
    private EPServiceProvider epService;
    private final String fields[] = "string,intPrimitive".split(",");

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        String pkg = SupportBean.class.getPackage().getName();
        config.addEventTypeAutoName(pkg);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
    }

    public void testPerformanceSyntheticUndelivered()
    {
        final int NUM_LOOP = 100000;
        epService.getEPAdministrator().createEPL("select string, intPrimitive from SupportBean(intPrimitive > 10)");

        long start = System.currentTimeMillis();
        for (int i = 0; i < NUM_LOOP; i++)
        {
            epService.getEPRuntime().sendEvent(new SupportBean("E1", 1000 + i));
        }
        long end = System.currentTimeMillis();
        // System.out.println("delta=" + (end - start));
    }

    /*
    public void testPerformanceNatural()
    {
        final int NUM_LOOP = 10;
        MyAnnotatedSimpleSubscriber subscriber = new MyAnnotatedSimpleSubscriber();

        long start = System.currentTimeMillis();
        for (int i = 0; i < NUM_LOOP; i++)
        {
            epService.getEPRuntime().sendEvent(new SupportBean("E1", 1000 + i));
        }
        long end = System.currentTimeMillis();

        List<Object[]> results = subscriber.getAndResetIndicate();
        assertEquals(NUM_LOOP, results.size());
        for (int i = 0; i < NUM_LOOP; i++)
        {
            ArrayAssertionUtil.assertEqualsAnyOrder(results.get(i), new Object[] {"E1", 1000 + i});
        }
        System.out.println("delta=" + (end - start));
    }
    */
    public void testPerformanceSynthetic()
    {
        final int NUM_LOOP = 100000;
        EPStatement stmt = epService.getEPAdministrator().createEPL("select string, intPrimitive from SupportBean(intPrimitive > 10)");
        final List<Object[]> results = new ArrayList<Object[]>();

        UpdateListener listener = new UpdateListener() {
            public void update(EventBean[] newEvents, EventBean[] oldEvents)
            {
                String string = (String) newEvents[0].get("string");
                int val = (Integer) newEvents[0].get("intPrimitive");
                results.add(new Object[] {string, val});
            }
        };
        stmt.addListener(listener);

        long start = System.currentTimeMillis();
        for (int i = 0; i < NUM_LOOP; i++)
        {
            epService.getEPRuntime().sendEvent(new SupportBean("E1", 1000 + i));
        }
        long end = System.currentTimeMillis();

        assertEquals(NUM_LOOP, results.size());
        for (int i = 0; i < NUM_LOOP; i++)
        {
            ArrayAssertionUtil.assertEqualsAnyOrder(results.get(i), new Object[] {"E1", 1000 + i});
        }
        // System.out.println("delta=" + (end - start));
    }
}
