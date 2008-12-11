package com.espertech.esper.regression.epl;

import junit.framework.TestCase;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_A;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;

public class TestPerfNamedWindow extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listenerWindow;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listenerWindow = new SupportUpdateListener();

        // force GC
        System.gc();
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
        stmtCreate.addListener(listenerWindow);
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
        assertEquals(10000, listenerWindow.getOldDataList().size());
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
        stmtCreate.addListener(listenerWindow);
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
        assertEquals(10000, listenerWindow.getOldDataList().size());
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
        stmtCreate.addListener(listenerWindow);
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
        assertEquals(20000, listenerWindow.getOldDataList().size());
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
