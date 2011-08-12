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

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBeanRange;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestPerf2StreamRangeJoin extends TestCase
{
    private EPServiceProvider epService;
    private EPStatement stmt;
    private SupportUpdateListener listener;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getLogging().setEnableQueryPlan(true);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();

        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBeanRange", SupportBeanRange.class);
    }

    public void testPerfKeyAndRangeOuterJoin() {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBeanRange", SupportBeanRange.class);

        epService.getEPAdministrator().createEPL("create window SBR.win:keepall() as SupportBeanRange");
        epService.getEPAdministrator().createEPL("@Name('I1') insert into SBR select * from SupportBeanRange");
        epService.getEPAdministrator().createEPL("create window SB.win:keepall() as SupportBean");
        epService.getEPAdministrator().createEPL("@Name('I2') insert into SB select * from SupportBean");

        // Preload
        log.info("Preloading events");
        for (int i = 0; i < 10000; i++)
        {
            epService.getEPRuntime().sendEvent(new SupportBean("G", i));
            epService.getEPRuntime().sendEvent(new SupportBeanRange("R", "G", i-1, i+2));
        }
        log.info("Done preloading");

        // create
        String epl = "select * " +
                      "from SB sb " +
                      "full outer join " +
                      "SBR sbr " +
                      "on string = key " +
                      "where intPrimitive between rangeStart and rangeEnd";
        stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);
        
        // Repeat
        log.info("Querying");
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++)
        {
            epService.getEPRuntime().sendEvent(new SupportBean("G", 9990));
            assertEquals(4, listener.getAndResetLastNewData().length);

            epService.getEPRuntime().sendEvent(new SupportBeanRange("R", "G", 4, 10));
            assertEquals(7, listener.getAndResetLastNewData().length);
        }
        log.info("Done Querying");
        long endTime = System.currentTimeMillis();
        log.info("delta=" + (endTime - startTime));

        assertTrue((endTime - startTime) < 500);
        stmt.destroy();
    }

    public void testPerfRelationalOp() {
        epService.getEPAdministrator().createEPL("create window SBR.win:keepall() as SupportBeanRange");
        epService.getEPAdministrator().createEPL("@Name('I1') insert into SBR select * from SupportBeanRange");
        epService.getEPAdministrator().createEPL("create window SB.win:keepall() as SupportBean");
        epService.getEPAdministrator().createEPL("@Name('I2') insert into SB select * from SupportBean");

        // Preload
        log.info("Preloading events");
        for (int i = 0; i < 10000; i++)
        {
            epService.getEPRuntime().sendEvent(new SupportBean("E" + i, i));
            epService.getEPRuntime().sendEvent(new SupportBeanRange("E", i, -1));
        }
        log.info("Done preloading");

        // start query
        String epl = "select * from SBR a, SB b where a.rangeStart < b.intPrimitive";
        stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        // Repeat
        log.info("Querying");
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++)
        {
            epService.getEPRuntime().sendEvent(new SupportBean("B", 10));
            assertEquals(10, listener.getAndResetLastNewData().length);

            epService.getEPRuntime().sendEvent(new SupportBeanRange("R", 9990, -1));
            assertEquals(9, listener.getAndResetLastNewData().length);
        }
        log.info("Done Querying");
        long endTime = System.currentTimeMillis();
        log.info("delta=" + (endTime - startTime));

        assertTrue((endTime - startTime) < 500);
        stmt.destroy();
    }

    public void testPerfKeyAndRange() {
        epService.getEPAdministrator().createEPL("create window SBR.win:keepall() as SupportBeanRange");
        epService.getEPAdministrator().createEPL("@Name('I1') insert into SBR select * from SupportBeanRange");
        epService.getEPAdministrator().createEPL("create window SB.win:keepall() as SupportBean");
        epService.getEPAdministrator().createEPL("@Name('I2') insert into SB select * from SupportBean");

        // Preload
        log.info("Preloading events");
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++)
            {
                epService.getEPRuntime().sendEvent(new SupportBean(Integer.toString(i), j));
                epService.getEPRuntime().sendEvent(new SupportBeanRange("R", Integer.toString(i), j-1, j+1));
            }
        }
        log.info("Done preloading");

        // start query
        String epl = "select * from SBR sbr, SB sb where sbr.key = sb.string and sb.intPrimitive between sbr.rangeStart and sbr.rangeEnd";
        stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        // repeat
        log.info("Querying");
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++)
        {
            epService.getEPRuntime().sendEvent(new SupportBean("55", 10));
            assertEquals(3, listener.getAndResetLastNewData().length);

            epService.getEPRuntime().sendEvent(new SupportBeanRange("R", "56", 12, 20));
            assertEquals(9, listener.getAndResetLastNewData().length);
        }
        log.info("Done Querying");
        long endTime = System.currentTimeMillis();
        log.info("delta=" + (endTime - startTime));

        // test no event found
        epService.getEPRuntime().sendEvent(new SupportBeanRange("R", "56", 2000, 3000));
        epService.getEPRuntime().sendEvent(new SupportBeanRange("R", "X", 2000, 3000));
        assertFalse(listener.isInvoked());

        assertTrue((endTime - startTime) < 500);
        stmt.destroy();

        // delete all events
        epService.getEPAdministrator().createEPL("on SupportBean delete from SBR");
        epService.getEPAdministrator().createEPL("on SupportBean delete from SB");
        epService.getEPRuntime().sendEvent(new SupportBean("D", -1));
    }

    public void testPerfKeyAndRangeInverted() {

        epService.getEPAdministrator().createEPL("create window SB.win:keepall() as SupportBean");
        epService.getEPAdministrator().createEPL("@Name('I2') insert into SB select * from SupportBean");

        // Preload
        log.info("Preloading events");
        for (int i = 0; i < 10000; i++) {
            epService.getEPRuntime().sendEvent(new SupportBean("E", i));
        }
        log.info("Done preloading");

        // start query
        String epl = "select * from SupportBeanRange.std:lastevent() sbr, SB sb where sbr.key = sb.string and sb.intPrimitive not in [sbr.rangeStart:sbr.rangeEnd]";
        stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        // repeat
        log.info("Querying");
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++)
        {
            epService.getEPRuntime().sendEvent(new SupportBeanRange("R", "E", 5, 9995));
            assertEquals(9, listener.getAndResetLastNewData().length);
        }
        log.info("Done Querying");
        long endTime = System.currentTimeMillis();
        log.info("delta=" + (endTime - startTime));

        assertTrue((endTime - startTime) < 500);
        stmt.destroy();
    }

    public void testPerfUnidirectionalRelOp() {

        epService.getEPAdministrator().createEPL("create window SB.win:keepall() as SupportBean");
        epService.getEPAdministrator().createEPL("@Name('I') insert into SB select * from SupportBean");

        // Preload
        log.info("Preloading events");
        for (int i = 0; i < 100000; i++)
        {
            epService.getEPRuntime().sendEvent(new SupportBean("E" + i, i));
        }
        log.info("Done preloading");

        // Test range
        String rangeEplOne = "select min(a.intPrimitive) as mini, max(a.intPrimitive) as maxi from SupportBeanRange r unidirectional, SB a " +
                     "where a.intPrimitive between r.rangeStart and r.rangeEnd";
        String rangeEplTwo = "select min(a.intPrimitive) as mini, max(a.intPrimitive) as maxi from SB a, SupportBeanRange r unidirectional " +
                     "where a.intPrimitive between r.rangeStart and r.rangeEnd";
        String rangeEplThree = "select min(a.intPrimitive) as mini, max(a.intPrimitive) as maxi from SupportBeanRange.std:lastevent() r, SB a " +
                     "where a.intPrimitive between r.rangeStart and r.rangeEnd";
        String rangeEplFour = "select min(a.intPrimitive) as mini, max(a.intPrimitive) as maxi from SB a, SupportBeanRange.std:lastevent() r " +
                     "where a.intPrimitive between r.rangeStart and r.rangeEnd";
        String rangeEplFive = "select min(a.intPrimitive) as mini, max(a.intPrimitive) as maxi from SupportBeanRange r unidirectional, SB a\n" +
                     "where a.intPrimitive >= r.rangeStart and a.intPrimitive <= r.rangeEnd";
        String rangeEplSix = "select min(a.intPrimitive) as mini, max(a.intPrimitive) as maxi from SupportBeanRange r unidirectional, SB a " +
                     "where a.intPrimitive <= r.rangeEnd and a.intPrimitive >= r.rangeStart";
        AssertionCallback rangeCallback = new AssertionCallback() {
            public Object getEvent(int iteration) {
                return new SupportBeanRange("E", iteration + 50000, iteration + 50100);
            }
            public Object[] getExpectedValue(int iteration) {
                return new Object[] {50000 + iteration, 50100 + iteration};
            }
        };
        runAssertion(rangeEplOne, 100, rangeCallback);
        runAssertion(rangeEplTwo, 100, rangeCallback);
        runAssertion(rangeEplThree, 100, rangeCallback);
        runAssertion(rangeEplFour, 100, rangeCallback);
        runAssertion(rangeEplFive, 100, rangeCallback);
        runAssertion(rangeEplSix, 100, rangeCallback);

        // Test Greater-Equals
        String geEplOne = "select min(a.intPrimitive) as mini, max(a.intPrimitive) as maxi from SupportBeanRange r unidirectional, SB a " +
                     "where a.intPrimitive >= r.rangeStart and a.intPrimitive <= 99200";
        String geEplTwo = "select min(a.intPrimitive) as mini, max(a.intPrimitive) as maxi from SB a, SupportBeanRange r unidirectional " +
                     "where a.intPrimitive >= r.rangeStart and a.intPrimitive <= 99200";
        AssertionCallback geCallback = new AssertionCallback() {
            public Object getEvent(int iteration) {
                return new SupportBeanRange("E", iteration + 99000, null);
            }
            public Object[] getExpectedValue(int iteration) {
                return new Object[] {99000 + iteration, 99200};
            }
        };
        runAssertion(geEplOne, 100, geCallback);
        runAssertion(geEplTwo, 100, geCallback);

        // Test Greater-Then
        String gtEplOne = "select min(a.intPrimitive) as mini, max(a.intPrimitive) as maxi from SupportBeanRange r unidirectional, SB a " +
                     "where a.intPrimitive > r.rangeStart and a.intPrimitive <= 99200";
        String gtEplTwo = "select min(a.intPrimitive) as mini, max(a.intPrimitive) as maxi from SB a, SupportBeanRange r unidirectional " +
                     "where a.intPrimitive > r.rangeStart and a.intPrimitive <= 99200";
        String gtEplThree = "select min(a.intPrimitive) as mini, max(a.intPrimitive) as maxi from SupportBeanRange.std:lastevent() r, SB a " +
                     "where a.intPrimitive > r.rangeStart and a.intPrimitive <= 99200";
        String gtEplFour = "select min(a.intPrimitive) as mini, max(a.intPrimitive) as maxi from SB a, SupportBeanRange.std:lastevent() r " +
                     "where a.intPrimitive > r.rangeStart and a.intPrimitive <= 99200";
        AssertionCallback gtCallback = new AssertionCallback() {
            public Object getEvent(int iteration) {
                return new SupportBeanRange("E", iteration + 99000, null);
            }
            public Object[] getExpectedValue(int iteration) {
                return new Object[] {99001 + iteration, 99200};
            }
        };
        runAssertion(gtEplOne, 100, gtCallback);
        runAssertion(gtEplTwo, 100, gtCallback);
        runAssertion(gtEplThree, 100, gtCallback);
        runAssertion(gtEplFour, 100, gtCallback);

        // Test Less-Then
        String ltEplOne = "select min(a.intPrimitive) as mini, max(a.intPrimitive) as maxi from SupportBeanRange r unidirectional, SB a " +
                     "where a.intPrimitive < r.rangeStart and a.intPrimitive > 100";
        String ltEplTwo = "select min(a.intPrimitive) as mini, max(a.intPrimitive) as maxi from SB a, SupportBeanRange r unidirectional " +
                     "where a.intPrimitive < r.rangeStart and a.intPrimitive > 100";
        AssertionCallback ltCallback = new AssertionCallback() {
            public Object getEvent(int iteration) {
                return new SupportBeanRange("E", iteration + 500, null);
            }
            public Object[] getExpectedValue(int iteration) {
                return new Object[] {101, 499 + iteration};
            }
        };
        runAssertion(ltEplOne, 100, ltCallback);
        runAssertion(ltEplTwo, 100, ltCallback);

        // Test Less-Equals
        String leEplOne = "select min(a.intPrimitive) as mini, max(a.intPrimitive) as maxi from SupportBeanRange r unidirectional, SB a " +
                     "where a.intPrimitive <= r.rangeStart and a.intPrimitive > 100";
        String leEplTwo = "select min(a.intPrimitive) as mini, max(a.intPrimitive) as maxi from SB a, SupportBeanRange r unidirectional " +
                     "where a.intPrimitive <= r.rangeStart and a.intPrimitive > 100";
        AssertionCallback leCallback = new AssertionCallback() {
            public Object getEvent(int iteration) {
                return new SupportBeanRange("E", iteration + 500, null);
            }
            public Object[] getExpectedValue(int iteration) {
                return new Object[] {101, 500 + iteration};
            }
        };
        runAssertion(leEplOne, 100, leCallback);
        runAssertion(leEplTwo, 100, leCallback);

        // Test open range
        String openEplOne = "select min(a.intPrimitive) as mini, max(a.intPrimitive) as maxi from SupportBeanRange r unidirectional, SB a " +
                     "where a.intPrimitive > r.rangeStart and a.intPrimitive < r.rangeEnd";
        String openEplTwo = "select min(a.intPrimitive) as mini, max(a.intPrimitive) as maxi from SupportBeanRange r unidirectional, SB a " +
                     "where a.intPrimitive in (r.rangeStart:r.rangeEnd)";
        AssertionCallback openCallback = new AssertionCallback() {
            public Object getEvent(int iteration) {
                return new SupportBeanRange("E", iteration+3, iteration+7);
            }
            public Object[] getExpectedValue(int iteration) {
                return new Object[] {iteration+4, iteration+6};
            }
        };
        runAssertion(openEplOne, 100, openCallback);
        runAssertion(openEplTwo, 100, openCallback);

        // Test half-open range
        String hopenEplOne = "select min(a.intPrimitive) as mini, max(a.intPrimitive) as maxi from SupportBeanRange r unidirectional, SB a " +
                     "where a.intPrimitive >= r.rangeStart and a.intPrimitive < r.rangeEnd";
        String hopenEplTwo = "select min(a.intPrimitive) as mini, max(a.intPrimitive) as maxi from SupportBeanRange r unidirectional, SB a " +
                     "where a.intPrimitive in [r.rangeStart:r.rangeEnd)";
        AssertionCallback halfOpenCallback = new AssertionCallback() {
            public Object getEvent(int iteration) {
                return new SupportBeanRange("E", iteration+3, iteration+7);
            }
            public Object[] getExpectedValue(int iteration) {
                return new Object[] {iteration+3, iteration+6};
            }
        };
        runAssertion(hopenEplOne, 100, halfOpenCallback);
        runAssertion(hopenEplTwo, 100, halfOpenCallback);

        // Test half-closed range
        String hclosedEplOne = "select min(a.intPrimitive) as mini, max(a.intPrimitive) as maxi from SupportBeanRange r unidirectional, SB a " +
                     "where a.intPrimitive > r.rangeStart and a.intPrimitive <= r.rangeEnd";
        String hclosedEplTwo = "select min(a.intPrimitive) as mini, max(a.intPrimitive) as maxi from SupportBeanRange r unidirectional, SB a " +
                     "where a.intPrimitive in (r.rangeStart:r.rangeEnd]";
        AssertionCallback halfClosedCallback = new AssertionCallback() {
            public Object getEvent(int iteration) {
                return new SupportBeanRange("E", iteration+3, iteration+7);
            }
            public Object[] getExpectedValue(int iteration) {
                return new Object[] {iteration+4, iteration+7};
            }
        };
        runAssertion(hclosedEplOne, 100, halfClosedCallback);
        runAssertion(hclosedEplTwo, 100, halfClosedCallback);

        // Test inverted closed range
        String invertedClosedEPLOne = "select min(a.intPrimitive) as mini, max(a.intPrimitive) as maxi from SupportBeanRange r unidirectional, SB a " +
                     "where a.intPrimitive not in [r.rangeStart:r.rangeEnd]";
        String invertedClosedEPLTwo = "select min(a.intPrimitive) as mini, max(a.intPrimitive) as maxi from SupportBeanRange r unidirectional, SB a " +
                     "where a.intPrimitive not between r.rangeStart and r.rangeEnd";
        AssertionCallback invertedClosedCallback = new AssertionCallback() {
            public Object getEvent(int iteration) {
                return new SupportBeanRange("E", 20, 99990);
            }
            public Object[] getExpectedValue(int iteration) {
                return new Object[] {0, 99999};
            }
        };
        runAssertion(invertedClosedEPLOne, 100, invertedClosedCallback);
        runAssertion(invertedClosedEPLTwo, 100, invertedClosedCallback);

        // Test inverted open range
        String invertedOpenEPLOne = "select min(a.intPrimitive) as mini, max(a.intPrimitive) as maxi from SupportBeanRange r unidirectional, SB a " +
                     "where a.intPrimitive not in (r.rangeStart:r.rangeEnd)";
        runAssertion(invertedOpenEPLOne, 100, invertedClosedCallback);
    }

    public void runAssertion(String epl, int numLoops, AssertionCallback assertionCallback)
    {
        String[] fields = "mini,maxi".split(",");
        
        stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        // Send range query events
        log.info("Querying");
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < numLoops; i++)
        {
            //if (i % 10 == 0) {
            //    log.info("At loop #" + i);
            //}
            epService.getEPRuntime().sendEvent(assertionCallback.getEvent(i));
            ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, assertionCallback.getExpectedValue(i));
        }
        log.info("Done Querying");
        long endTime = System.currentTimeMillis();
        log.info("delta=" + (endTime - startTime));
        
        assertTrue((endTime - startTime) < 1500);
        stmt.destroy();
    }

    private static final Log log = LogFactory.getLog(TestPerf2StreamRangeJoin.class);

    private static interface AssertionCallback {
        public Object getEvent(int iteration);
        public Object[] getExpectedValue(int iteration);
    }        
}
