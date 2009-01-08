package com.espertech.esper.regression.epl;

import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportBeanInt;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.epl.SupportJoinMethods;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.Random;

public class TestPerfHistoricalMethodJoin extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType(SupportBeanInt.class);

        ConfigurationMethodRef configMethod = new ConfigurationMethodRef();
        configMethod.setLRUCache(10);
        config.addMethodRef(SupportJoinMethods.class.getName(), configMethod);

        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void test1Stream2HistInnerJoinPerformance()
    {
        String expression;

        expression = "select s0.id as id, h0.val as valh0, h1.val as valh1 " +
                   "from SupportBeanInt.std:lastevent() as s0, " +
                   "method:SupportJoinMethods.fetchVal('H0', 100) as h0, " +
                   "method:SupportJoinMethods.fetchVal('H1', 100) as h1 " +
                   "where h0.index = p00 and h1.index = p00";

        EPStatement stmt = epService.getEPAdministrator().createEPL(expression);
        listener = new SupportUpdateListener();
        stmt.addListener(listener);

        String[] fields = "id,valh0,valh1".split(",");
        Random random = new Random();

        long start = System.currentTimeMillis();
        for (int i = 1; i < 5000; i++)
        {
            int num = random.nextInt(98) + 1;
            sendBeanInt("E1", num);

            Object[][] result = new Object[][] {{"E1", "H0" + num, "H1" + num}};
            ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, result);
        }
        long end = System.currentTimeMillis();
        long delta = end - start;
        assertTrue("Delta to large, at " + delta + " msec", delta < 1000);
    }

    public void test1Stream2HistOuterJoinPerformance()
    {
        String expression;

        expression = "select s0.id as id, h0.val as valh0, h1.val as valh1 " +
                   "from SupportBeanInt.std:lastevent() as s0 " +
                   " left outer join " +
                   "method:SupportJoinMethods.fetchVal('H0', 100) as h0 " +
                   " on h0.index = p00 " +
                   " left outer join " +
                   "method:SupportJoinMethods.fetchVal('H1', 100) as h1 " +
                   " on h1.index = p00";

        EPStatement stmt = epService.getEPAdministrator().createEPL(expression);
        listener = new SupportUpdateListener();
        stmt.addListener(listener);

        String[] fields = "id,valh0,valh1".split(",");
        Random random = new Random();

        long start = System.currentTimeMillis();
        for (int i = 1; i < 5000; i++)
        {
            int num = random.nextInt(98) + 1;
            sendBeanInt("E1", num);

            Object[][] result = new Object[][] {{"E1", "H0" + num, "H1" + num}};
            ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, result);
        }
        long end = System.currentTimeMillis();
        long delta = end - start;
        assertTrue("Delta to large, at " + delta + " msec", delta < 1000);
    }

    public void test2Stream1HistTwoSidedEntryIdenticalIndex()
    {
        String expression;

        expression = "select s0.id as s0id, s1.id as s1id, h0.val as valh0 " +
                   "from SupportBeanInt(id like 'E%').std:lastevent() as s0, " +
                   "method:SupportJoinMethods.fetchVal('H0', 100) as h0, " +
                   "SupportBeanInt(id like 'F%').std:lastevent() as s1 " +
                   "where h0.index = s0.p00 and h0.index = s1.p00";

        EPStatement stmt = epService.getEPAdministrator().createEPL(expression);
        listener = new SupportUpdateListener();
        stmt.addListener(listener);

        String[] fields = "s0id,s1id,valh0".split(",");
        Random random = new Random();

        long start = System.currentTimeMillis();
        for (int i = 1; i < 1000; i++)
        {
            int num = random.nextInt(98) + 1;
            sendBeanInt("E1", num);
            sendBeanInt("F1", num);

            Object[][] result = new Object[][] {{"E1", "F1", "H0" + num}};
            ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, result);

            // send reset events to avoid duplicate matches
            sendBeanInt("E1", 0);
            sendBeanInt("F1", 0);
            listener.reset();
        }
        long end = System.currentTimeMillis();
        long delta = end - start;
        assertTrue("Delta to large, at " + delta + " msec", delta < 1000);
    }

    public void test2Stream1HistTwoSidedEntryMixedIndex()
    {
        String expression;

        expression = "select s0.id as s0id, s1.id as s1id, h0.val as valh0, h0.index as indexh0 from " +
                    "method:SupportJoinMethods.fetchVal('H0', 100) as h0, " +
                    "SupportBeanInt(id like 'H%').std:lastevent() as s1, " +
                    "SupportBeanInt(id like 'E%').std:lastevent() as s0 " +
                    "where h0.index = s0.p00 and h0.val = s1.id";

        EPStatement stmt = epService.getEPAdministrator().createEPL(expression);
        listener = new SupportUpdateListener();
        stmt.addListener(listener);

        String[] fields = "s0id,s1id,valh0,indexh0".split(",");
        Random random = new Random();

        long start = System.currentTimeMillis();
        for (int i = 1; i < 1000; i++)
        {
            int num = random.nextInt(98) + 1;
            sendBeanInt("E1", num);
            sendBeanInt("H0" + num, num);

            Object[][] result = new Object[][] {{"E1", "H0" + num, "H0" + num, num}};
            ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, result);

            // send reset events to avoid duplicate matches
            sendBeanInt("E1", 0);
            sendBeanInt("F1", 0);
            listener.reset();
        }
        long end = System.currentTimeMillis();
        long delta = end - start;
        assertTrue("Delta to large, at " + delta + " msec", delta < 1000);
    }

    private void sendBeanInt(String id, int p00, int p01, int p02, int p03)
    {
        epService.getEPRuntime().sendEvent(new SupportBeanInt(id, p00, p01, p02, p03, -1, -1));
    }

    private void sendBeanInt(String id, int p00)
    {
        sendBeanInt(id, p00, -1, -1, -1);
    }
}
