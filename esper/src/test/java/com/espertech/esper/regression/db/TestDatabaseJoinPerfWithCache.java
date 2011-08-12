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

package com.espertech.esper.regression.db;

import com.espertech.esper.support.bean.SupportBeanRange;
import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_S0;
import com.espertech.esper.support.epl.SupportDatabaseService;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.client.SupportConfigFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Properties;

public class TestDatabaseJoinPerfWithCache extends TestCase
{
    private EPServiceProvider epServiceRetained;
    private SupportUpdateListener listener;

    public void setUp()
    {
        ConfigurationDBRef configDB = new ConfigurationDBRef();
        configDB.setDriverManagerConnection(SupportDatabaseService.DRIVER, SupportDatabaseService.FULLURL, new Properties());
        configDB.setConnectionLifecycleEnum(ConfigurationDBRef.ConnectionLifecycleEnum.RETAIN);

        /**
         * Turn this cache setting off to turn off indexing since without cache there is no point in indexing.
         */
        configDB.setLRUCache(100000);
        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.getEngineDefaults().getLogging().setEnableQueryPlan(true);
        configuration.addDatabaseReference("MyDB", configDB);

        epServiceRetained = EPServiceProviderManager.getProvider("TestDatabaseJoinRetained", configuration);
        epServiceRetained.initialize();
    }

    public void testConstants() {
        epServiceRetained.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);

        String epl;

        epl = "select * from SupportBean sbr, sql:MyDB ['select mycol1, mycol3 from mytesttable_large'] as s1 where mycol3 = 951";
        runAssertion(epl, "s1.mycol1", "951");

        epl = "select * from SupportBean sbr, sql:MyDB ['select mycol1, mycol3 from mytesttable_large'] as s1 where mycol3 = 950 and mycol1 = '950'";
        runAssertion(epl, "s1.mycol1", "950");

        epl = "select sum(s1.mycol3) as val from SupportBean sbr unidirectional, sql:MyDB ['select mycol1, mycol3 from mytesttable_large'] as s1 where mycol3 between 950 and 953";
        runAssertion(epl, "val", 950+951+952+953);

        epl = "select sum(s1.mycol3) as val from SupportBean sbr unidirectional, sql:MyDB ['select mycol1, mycol3 from mytesttable_large'] as s1 where mycol1 = '950' and mycol3 between 950 and 953";
        runAssertion(epl, "val", 950);
    }

    private void runAssertion(String epl, String field, Object expected) {
        EPStatement statement = epServiceRetained.getEPAdministrator().createEPL(epl);
        listener = new SupportUpdateListener();
        statement.addListener(listener);

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            epServiceRetained.getEPRuntime().sendEvent(new SupportBean("E", 0));
            assertEquals(expected, listener.assertOneGetNewAndReset().get(field));
        }
        long endTime = System.currentTimeMillis();
        long delta = endTime - startTime;
        log.info("delta=" + delta);
        assertTrue("Delta=" + delta, delta < 500);
    }

    public void testRangeIndex() {
        epServiceRetained.getEPAdministrator().getConfiguration().addEventType("SupportBeanRange", SupportBeanRange.class);

        String stmtText = "select * from SupportBeanRange sbr, "+
                " sql:MyDB ['select mycol1, mycol3 from mytesttable_large'] as s1 where mycol3 between rangeStart and rangeEnd";

        EPStatement statement = epServiceRetained.getEPAdministrator().createEPL(stmtText);
        listener = new SupportUpdateListener();
        statement.addListener(listener);

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            epServiceRetained.getEPRuntime().sendEvent(new SupportBeanRange("R", 10, 12));
            assertEquals(3,listener.getAndResetLastNewData().length);
        }
        long endTime = System.currentTimeMillis();
        long delta = endTime - startTime;
        log.info("delta=" + delta);
        assertTrue("Delta=" + delta, delta < 500);

        // test coercion
        statement.destroy();
        stmtText = "select * from SupportBeanRange sbr, "+
                " sql:MyDB ['select mycol1, mycol3 from mytesttable_large'] as s1 where mycol3 between rangeStartLong and rangeEndLong";

        statement = epServiceRetained.getEPAdministrator().createEPL(stmtText);
        listener = new SupportUpdateListener();
        statement.addListener(listener);
        epServiceRetained.getEPRuntime().sendEvent(SupportBeanRange.makeLong("R", "K", 10L, 12L));
        assertEquals(3,listener.getAndResetLastNewData().length);
    }

    public void testKeyAndRangeIndex() {
        epServiceRetained.getEPAdministrator().getConfiguration().addEventType("SupportBeanRange", SupportBeanRange.class);

        String stmtText = "select * from SupportBeanRange sbr, "+
                " sql:MyDB ['select mycol1, mycol3 from mytesttable_large'] as s1 where mycol1 = key and mycol3 between rangeStart and rangeEnd";

        EPStatement statement = epServiceRetained.getEPAdministrator().createEPL(stmtText);
        listener = new SupportUpdateListener();
        statement.addListener(listener);

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            epServiceRetained.getEPRuntime().sendEvent(new SupportBeanRange("R", "11", 10, 12));
            assertEquals(1,listener.getAndResetLastNewData().length);
        }
        long endTime = System.currentTimeMillis();
        long delta = endTime - startTime;
        log.info("delta=" + delta);
        assertTrue("Delta=" + delta, delta < 500);

        // test coercion
        statement.destroy();
        stmtText = "select * from SupportBeanRange sbr, "+
                " sql:MyDB ['select mycol1, mycol3 from mytesttable_large'] as s1 where mycol1 = key and mycol3 between rangeStartLong and rangeEndLong";

        statement = epServiceRetained.getEPAdministrator().createEPL(stmtText);
        listener = new SupportUpdateListener();
        statement.addListener(listener);
        epServiceRetained.getEPRuntime().sendEvent(SupportBeanRange.makeLong("R", "11", 10L, 12L));
        assertEquals(1,listener.getAndResetLastNewData().length);
    }

    /**
     * Test for selecting from a table a large result set and then joining the result outside of the cache.
     * Verifies performance of indexes cached for resolving join criteria fast.
     */
    public void testSelectLargeResultSet()
    {
        String stmtText = "select id, mycol3, mycol2 from " +
                SupportBean_S0.class.getName() + ".win:keepall() as s0," +
                " sql:MyDB ['select mycol3, mycol2 from mytesttable_large'] as s1 where s0.id = s1.mycol3";

        EPStatement statement = epServiceRetained.getEPAdministrator().createEPL(stmtText);
        listener = new SupportUpdateListener();
        statement.addListener(listener);

        // Send 100 events which all perform the join
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 200; i++)
        {
            int num = i + 1;
            String col2 = Integer.toString(Math.round((float)num / 10));
            SupportBean_S0 bean = new SupportBean_S0(num);
            epServiceRetained.getEPRuntime().sendEvent(bean);
            ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), new String[] {"id", "mycol3", "mycol2"}, new Object[] {num, num, col2});
        }
        long endTime = System.currentTimeMillis();

        log.info("delta=" + (endTime - startTime));
        assertTrue(endTime - startTime < 500);
        assertFalse(listener.isInvoked());
    }

    public void testSelectLargeResultSetCoercion()
    {
        String stmtText = "select string, mycol3, mycol4 from " +
                " sql:MyDB ['select mycol3, mycol4 from mytesttable_large'] as s0, " +
                SupportBean.class.getName() + ".win:keepall() as s1 where s1.doubleBoxed = s0.mycol3 and s1.byteBoxed = s0.mycol4";

        EPStatement statement = epServiceRetained.getEPAdministrator().createEPL(stmtText);
        listener = new SupportUpdateListener();
        statement.addListener(listener);

        // Send events which all perform the join
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 200; i++)
        {
            SupportBean bean = new SupportBean();
            bean.setDoubleBoxed(100d);
            bean.setByteBoxed((byte)10);
            bean.setString("E" + i);
            epServiceRetained.getEPRuntime().sendEvent(bean);
            ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), new String[] {"string", "mycol3", "mycol4"}, new Object[] {"E" + i, 100, 10});
        }
        long endTime = System.currentTimeMillis();

        log.info("delta=" + (endTime - startTime));
        assertTrue(endTime - startTime < 500);
    }

    public void test2StreamOuterJoin()
    {
        String stmtText = "select string, mycol3, mycol1 from " +
                " sql:MyDB ['select mycol1, mycol3 from mytesttable_large'] as s1 right outer join " +
                SupportBean.class.getName() + " as s0 on string = mycol1";

        EPStatement statement = epServiceRetained.getEPAdministrator().createEPL(stmtText);
        listener = new SupportUpdateListener();
        statement.addListener(listener);

        // Send events which all perform the join
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 200; i++)
        {
            SupportBean bean = new SupportBean();
            bean.setString("50");
            epServiceRetained.getEPRuntime().sendEvent(bean);
            ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), new String[] {"string", "mycol3", "mycol1"}, new Object[] {"50", 50, "50"});
        }
        long endTime = System.currentTimeMillis();

        // no matching
        SupportBean bean = new SupportBean();
        bean.setString("-1");
        epServiceRetained.getEPRuntime().sendEvent(bean);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), new String[] {"string", "mycol3", "mycol1"}, new Object[] {"-1", null, null});

        log.info("delta=" + (endTime - startTime));
        assertTrue(endTime - startTime < 500);
    }
    
    public void testOuterJoinPlusWhere()
    {
        String stmtText = "select string, mycol3, mycol1 from " +
                " sql:MyDB ['select mycol1, mycol3 from mytesttable_large'] as s1 right outer join " +
                SupportBean.class.getName() + " as s0 on string = mycol1 where s1.mycol3 = s0.intPrimitive";

        EPStatement statement = epServiceRetained.getEPAdministrator().createEPL(stmtText);
        listener = new SupportUpdateListener();
        statement.addListener(listener);

        // Send events which all perform the join
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 200; i++)
        {
            SupportBean bean = new SupportBean();
            bean.setString("50");
            bean.setIntPrimitive(50);
            epServiceRetained.getEPRuntime().sendEvent(bean);
            ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), new String[] {"string", "mycol3", "mycol1"}, new Object[] {"50", 50, "50"});
        }
        long endTime = System.currentTimeMillis();

        // no matching on-clause
        SupportBean bean = new SupportBean();
        assertFalse(listener.isInvoked());

        // matching on-clause not matching where
        bean = new SupportBean();
        bean.setString("50");
        bean.setIntPrimitive(49);
        epServiceRetained.getEPRuntime().sendEvent(bean);
        assertFalse(listener.isInvoked());

        log.info("delta=" + (endTime - startTime));
        assertTrue(endTime - startTime < 500);
    }

    private static final Log log = LogFactory.getLog(TestDatabaseJoinPerfWithCache.class);
}
