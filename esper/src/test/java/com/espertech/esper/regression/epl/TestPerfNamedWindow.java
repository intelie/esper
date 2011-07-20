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

import com.espertech.esper.client.*;
import com.espertech.esper.core.EPServiceProviderSPI;
import com.espertech.esper.support.bean.SupportBeanRange;
import junit.framework.TestCase;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_A;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;

public class TestPerfNamedWindow extends TestCase
{
    private EPServiceProviderSPI epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        epService = (EPServiceProviderSPI) EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();

        // force GC
        System.gc();
    }

    public void testOnSelectPerformance()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBeanRange", SupportBeanRange.class);

        // create window one
        epService.getEPAdministrator().createEPL("create window MyWindow.win:keepall() as SupportBean");
        epService.getEPAdministrator().createEPL("insert into MyWindow select * from SupportBean");

        // insert X rows
        int maxRows = 10000;   //for performance testing change to int maxRows = 100000;
        for (int i = 0; i < maxRows; i++) {
            SupportBean bean = new SupportBean((i < 5000) ? "A" : "B", i);
            bean.setLongPrimitive(i);
            bean.setLongBoxed((long) i + 1);
            epService.getEPRuntime().sendEvent(bean);
        }
        epService.getEPRuntime().sendEvent(new SupportBean("B", 100));

        String eplIdx1One = "on SupportBeanRange sbr select sum(intPrimitive) as sumi from MyWindow where intPrimitive = sbr.rangeStart";
        runOnDemandAssertion(eplIdx1One, new SupportBeanRange("R", 5501, 0), 5501);

        String eplIdx1Two = "on SupportBeanRange sbr select sum(intPrimitive) as sumi from MyWindow where intPrimitive between sbr.rangeStart and sbr.rangeEnd";
        runOnDemandAssertion(eplIdx1Two, new SupportBeanRange("R", 5501, 5503), 5501+5502+5503);

        String eplIdx1Three = "on SupportBeanRange sbr select sum(intPrimitive) as sumi from MyWindow where string = key and intPrimitive between sbr.rangeStart and sbr.rangeEnd";
        runOnDemandAssertion(eplIdx1Three, new SupportBeanRange("R", "A", 4998, 5503), 4998+4999);

        String eplIdx1Four = "on SupportBeanRange sbr select sum(intPrimitive) as sumi from MyWindow " +
                "where string = key and longPrimitive = rangeStart and intPrimitive between rangeStart and rangeEnd " +
                "and longBoxed between rangeStart and rangeEnd";
        runOnDemandAssertion(eplIdx1Four, new SupportBeanRange("R", "A", 4998, 5503), 4998);

        String eplIdx1Five = "on SupportBeanRange sbr select sum(intPrimitive) as sumi from MyWindow " +
                "where intPrimitive between rangeStart and rangeEnd " +
                "and longBoxed between rangeStart and rangeEnd";
        runOnDemandAssertion(eplIdx1Five, new SupportBeanRange("R", "A", 4998, 5001), 4998 + 4999 + 5000);
    }

    private void runOnDemandAssertion(String epl, SupportBeanRange event, Integer expected) {
        assertEquals(0, epService.getNamedWindowService().getNamedWindowIndexes("MyWindow").length);

        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);
        assertEquals(1, epService.getNamedWindowService().getNamedWindowIndexes("MyWindow").length);

        long start = System.currentTimeMillis();
        int loops = 1000;

        for (int i = 0; i < loops; i++) {
            epService.getEPRuntime().sendEvent(event);
            assertEquals(expected, listener.assertOneGetNewAndReset().get("sumi"));
        }
        long end = System.currentTimeMillis();
        long delta = end - start;
        assertTrue("delta=" + delta, delta < 1000);

        stmt.destroy();
        assertEquals(0, epService.getNamedWindowService().getNamedWindowIndexes("MyWindow").length);
    }

    public void testDeletePerformance()
    {
        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select string as a, intPrimitive as b from " + SupportBean.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL(stmtTextCreate);

        // create delete stmt
        String stmtTextDelete = "on " + SupportBean_A.class.getName() + " delete from MyWindow where id = a";
        epService.getEPAdministrator().createEPL(stmtTextDelete);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string as a, intPrimitive as b from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEPL(stmtTextInsertOne);

        // load window
        for (int i = 0; i < 50000; i++)
        {
            sendSupportBean("S" + i, i);
        }

        // delete rows
        stmtCreate.addListener(listener);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++)
        {
            sendSupportBean_A("S" + i);
        }
        long endTime = System.currentTimeMillis();
        long delta = endTime - startTime;
        assertTrue("Delta=" + delta, delta < 500);

        // assert they are deleted
        assertEquals(50000 - 10000, ArrayAssertionUtil.iteratorCount(stmtCreate.iterator()));
        assertEquals(10000, listener.getOldDataList().size());
    }

    public void testDeletePerformanceCoercion()
    {
        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select string as a, longPrimitive as b from " + SupportBean.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL(stmtTextCreate);

        // create delete stmt
        String stmtTextDelete = "on " + SupportMarketDataBean.class.getName() + " delete from MyWindow where b = price";
        epService.getEPAdministrator().createEPL(stmtTextDelete);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string as a, longPrimitive as b from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEPL(stmtTextInsertOne);

        // load window
        for (int i = 0; i < 50000; i++)
        {
            sendSupportBean("S" + i, (long) i);
        }

        // delete rows
        stmtCreate.addListener(listener);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++)
        {
            sendMarketBean("S" + i, i);
        }
        long endTime = System.currentTimeMillis();
        long delta = endTime - startTime;
        assertTrue("Delta=" + delta, delta < 500);

        // assert they are deleted
        assertEquals(50000 - 10000, ArrayAssertionUtil.iteratorCount(stmtCreate.iterator()));
        assertEquals(10000, listener.getOldDataList().size());
    }

    public void testDeletePerformanceTwoDeleters()
    {
        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select string as a, longPrimitive as b from " + SupportBean.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL(stmtTextCreate);

        // create delete stmt one
        String stmtTextDeleteOne = "on " + SupportMarketDataBean.class.getName() + " delete from MyWindow where b = price";
        epService.getEPAdministrator().createEPL(stmtTextDeleteOne);

        // create delete stmt two
        String stmtTextDeleteTwo = "on " + SupportBean_A.class.getName() + " delete from MyWindow where id = a";
        epService.getEPAdministrator().createEPL(stmtTextDeleteTwo);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string as a, longPrimitive as b from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEPL(stmtTextInsertOne);

        // load window
        for (int i = 0; i < 20000; i++)
        {
            sendSupportBean("S" + i, (long) i);
        }

        // delete all rows
        stmtCreate.addListener(listener);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++)
        {
            sendMarketBean("S" + i, i);
            sendSupportBean_A("S" + (i + 10000));
        }
        long endTime = System.currentTimeMillis();
        long delta = endTime - startTime;
        assertTrue("Delta=" + delta, delta < 500);

        // assert they are all deleted
        assertEquals(0, ArrayAssertionUtil.iteratorCount(stmtCreate.iterator()));
        assertEquals(20000, listener.getOldDataList().size());
    }

    public void testDeletePerformanceIndexReuse()
    {
        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select string as a, longPrimitive as b from " + SupportBean.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL(stmtTextCreate);

        // create delete stmt
        EPStatement statements[] = new EPStatement[50];
        for (int i = 0; i < statements.length; i++)
        {
            String stmtTextDelete = "on " + SupportMarketDataBean.class.getName() + " delete from MyWindow where b = price";
            statements[i] = epService.getEPAdministrator().createEPL(stmtTextDelete);
        }

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string as a, longPrimitive as b from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEPL(stmtTextInsertOne);

        // load window
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++)
        {
            sendSupportBean("S" + i, (long) i);
        }
        long endTime = System.currentTimeMillis();
        long delta = endTime - startTime;
        assertTrue("Delta=" + delta, delta < 1000);
        assertEquals(10000, ArrayAssertionUtil.iteratorCount(stmtCreate.iterator()));

        // destroy all
        for (int i = 0; i < statements.length; i++)
        {
            statements[i].destroy();
        }
    }

    private SupportBean_A sendSupportBean_A(String id)
    {
        SupportBean_A bean = new SupportBean_A(id);
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }

    private SupportMarketDataBean sendMarketBean(String symbol, double price)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, null);
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }

    private SupportBean sendSupportBean(String string, long longPrimitive)
    {
        SupportBean bean = new SupportBean();
        bean.setString(string);
        bean.setLongPrimitive(longPrimitive);
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }

    private SupportBean sendSupportBean(String string, int intPrimitive)
    {
        SupportBean bean = new SupportBean();
        bean.setString(string);
        bean.setIntPrimitive(intPrimitive);
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }
}
