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
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.time.TimerControlEvent;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.ArrayHandlingUtil;
import com.espertech.esper.support.client.SupportConfigFactory;

public class Test6StreamOuterJoin extends TestCase
{
    private EPServiceProvider epService;
    private EPStatement joinView;
    private SupportUpdateListener updateListener;

    private final static String EVENT_S0 = SupportBean_S0.class.getName();
    private final static String EVENT_S1 = SupportBean_S1.class.getName();
    private final static String EVENT_S2 = SupportBean_S2.class.getName();
    private final static String EVENT_S3 = SupportBean_S3.class.getName();
    private final static String EVENT_S4 = SupportBean_S4.class.getName();
    private final static String EVENT_S5 = SupportBean_S5.class.getName();

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
        updateListener = new SupportUpdateListener();
    }

    public void testRoot_s0()
    {
        /**
         * Query:
         *          s0 <- s1
         *                  <- s3
         *             <- s2
         *                  <- s4
         *                  <- s5
         */
        String joinStatement = "select * from " +
                                   EVENT_S0 + ".win:length(1000) as s0 " +
            " right outer join " + EVENT_S1 + ".win:length(1000) as s1 on s0.p00 = s1.p10 " +
            " right outer join " + EVENT_S2 + ".win:length(1000) as s2 on s0.p00 = s2.p20 " +
            " right outer join " + EVENT_S3 + ".win:length(1000) as s3 on s1.p10 = s3.p30 " +
            " right outer join " + EVENT_S4 + ".win:length(1000) as s4 on s2.p20 = s4.p40 " +
            " right outer join " + EVENT_S5 + ".win:length(1000) as s5 on s2.p20 = s5.p50 ";

        joinView = epService.getEPAdministrator().createEPL(joinStatement);
        joinView.addListener(updateListener);

        runAsserts();
    }

    public void testRoot_s1()
    {
        /**
         * Query:
         *          s0 <- s1
         *                  <- s3
         *             <- s2
         *                  <- s4
         *                  <- s5
         */
        String joinStatement = "select * from " +
                                   EVENT_S1 + ".win:length(1000) as s1 " +
            " left outer join " + EVENT_S0 + ".win:length(1000) as s0 on s0.p00 = s1.p10 " +
            " right outer join " + EVENT_S3 + ".win:length(1000) as s3 on s1.p10 = s3.p30 " +
            " right outer join " + EVENT_S2 + ".win:length(1000) as s2 on s0.p00 = s2.p20 " +
            " right outer join " + EVENT_S5 + ".win:length(1000) as s5 on s2.p20 = s5.p50 " +
            " right outer join " + EVENT_S4 + ".win:length(1000) as s4 on s2.p20 = s4.p40 ";

        joinView = epService.getEPAdministrator().createEPL(joinStatement);
        joinView.addListener(updateListener);

        runAsserts();
    }

    public void testRoot_s2()
    {
        /**
         * Query:
         *          s0 <- s1
         *                  <- s3
         *             <- s2
         *                  <- s4
         *                  <- s5
         */
        String joinStatement = "select * from " +
                                   EVENT_S2 + ".win:length(1000) as s2 " +
            " left outer join " + EVENT_S0 + ".win:length(1000) as s0 on s0.p00 = s2.p20 " +
            " right outer join " + EVENT_S1 + ".win:length(1000) as s1 on s0.p00 = s1.p10 " +
            " right outer join " + EVENT_S3 + ".win:length(1000) as s3 on s1.p10 = s3.p30 " +
            " right outer join " + EVENT_S4 + ".win:length(1000) as s4 on s2.p20 = s4.p40 " +
            " right outer join " + EVENT_S5 + ".win:length(1000) as s5 on s2.p20 = s5.p50 ";

        joinView = epService.getEPAdministrator().createEPL(joinStatement);
        joinView.addListener(updateListener);

        runAsserts();
    }

    public void testRoot_s3()
    {
        /**
         * Query:
         *          s0 <- s1
         *                  <- s3
         *             <- s2
         *                  <- s4
         *                  <- s5
         */
        String joinStatement = "select * from " +
                                   EVENT_S3 + ".win:length(1000) as s3 " +
            " left outer join " + EVENT_S1 + ".win:length(1000) as s1 on s1.p10 = s3.p30 " +
            " left outer join " + EVENT_S0 + ".win:length(1000) as s0 on s0.p00 = s1.p10 " +
            " right outer join " + EVENT_S2 + ".win:length(1000) as s2 on s0.p00 = s2.p20 " +
            " right outer join " + EVENT_S5 + ".win:length(1000) as s5 on s2.p20 = s5.p50 " +
            " right outer join " + EVENT_S4 + ".win:length(1000) as s4 on s2.p20 = s4.p40 ";

        joinView = epService.getEPAdministrator().createEPL(joinStatement);
        joinView.addListener(updateListener);

        runAsserts();
    }

    public void testRoot_s4()
    {
        /**
         * Query:
         *          s0 <- s1
         *                  <- s3
         *             <- s2
         *                  <- s4
         *                  <- s5
         */
        String joinStatement = "select * from " +
                                   EVENT_S4 + ".win:length(1000) as s4 " +
            " left outer join " + EVENT_S2 + ".win:length(1000) as s2 on s2.p20 = s4.p40 " +
            " right outer join " + EVENT_S5 + ".win:length(1000) as s5 on s2.p20 = s5.p50 " +
            " left outer join " + EVENT_S0 + ".win:length(1000) as s0 on s0.p00 = s2.p20 " +
            " right outer join " + EVENT_S1 + ".win:length(1000) as s1 on s0.p00 = s1.p10 " +
            " right outer join " + EVENT_S3 + ".win:length(1000) as s3 on s1.p10 = s3.p30 ";

        joinView = epService.getEPAdministrator().createEPL(joinStatement);
        joinView.addListener(updateListener);

        runAsserts();
    }

    public void testRoot_s5()
    {
        /**
         * Query:
         *          s0 <- s1
         *                  <- s3
         *             <- s2
         *                  <- s4
         *                  <- s5
         */
        String joinStatement = "select * from " +
                                   EVENT_S5 + ".win:length(1000) as s5 " +
            " left outer join " + EVENT_S2 + ".win:length(1000) as s2 on s2.p20 = s5.p50 " +
            " right outer join " + EVENT_S4 + ".win:length(1000) as s4 on s2.p20 = s4.p40 " +
            " left outer join " + EVENT_S0 + ".win:length(1000) as s0 on s0.p00 = s2.p20 " +
            " right outer join " + EVENT_S1 + ".win:length(1000) as s1 on s0.p00 = s1.p10 " +
            " right outer join " + EVENT_S3 + ".win:length(1000) as s3 on s1.p10 = s3.p30 ";

        joinView = epService.getEPAdministrator().createEPL(joinStatement);
        joinView.addListener(updateListener);

        runAsserts();
    }

    private void runAsserts()
    {
        Object[] s0Events;
        Object[] s1Events;
        Object[] s2Events;
        Object[] s3Events;
        Object[] s4Events;
        Object[] s5Events;

        // Test s0 and s1=0, s2=0, s3=0, s4=0, s5=0
        //
        s0Events = SupportBean_S0.makeS0("A", new String[] {"A-s0-1"});
        sendEvent(s0Events);
        assertFalse(updateListener.isInvoked());

        // Test s0 and s1=1, s2=0, s3=0, s4=0, s5=0
        //
        s1Events = SupportBean_S1.makeS1("B", new String[] {"B-s1-1"});
        sendEvent(s1Events);
        assertFalse(updateListener.isInvoked());

        s0Events = SupportBean_S0.makeS0("B", new String[] {"B-s0-1"});
        sendEvent(s0Events);
        assertFalse(updateListener.isInvoked());

        // Test s0 and s1=1, s2=1, s3=0, s4=0, s5=0
        //
        s1Events = SupportBean_S1.makeS1("C", new String[] {"C-s1-1"});
        sendEvent(s1Events);

        s2Events = SupportBean_S2.makeS2("C", new String[] {"C-s2-1"});
        sendEvent(s2Events);
        assertFalse(updateListener.isInvoked());

        s0Events = SupportBean_S0.makeS0("C", new String[] {"C-s0-1"});
        sendEvent(s0Events);
        assertFalse(updateListener.isInvoked());

        // Test s0 and s1=1, s2=1, s3=1, s4=0, s5=0
        //
        s1Events = SupportBean_S1.makeS1("D", new String[] {"D-s1-1"});
        sendEvent(s1Events);

        s2Events = SupportBean_S2.makeS2("D", new String[] {"D-s2-1"});
        sendEvent(s2Events);

        s3Events = SupportBean_S3.makeS3("D", new String[] {"D-s2-1"});
        sendEvent(s3Events);
        ArrayAssertionUtil.assertRefAnyOrderArr(new Object[][] {
            { null, s1Events[0], null, s3Events[0], null, null }}, getAndResetNewEvents());

        s0Events = SupportBean_S0.makeS0("D", new String[] {"D-s0-1"});
        sendEvent(s0Events);
        assertFalse(updateListener.isInvoked());

        // Test s0 and s1=1, s2=1, s3=1, s4=1, s5=0
        //
        s1Events = SupportBean_S1.makeS1("E", new String[] {"E-s1-1"});
        sendEvent(s1Events);

        s2Events = SupportBean_S2.makeS2("E", new String[] {"E-s2-1"});
        sendEvent(s2Events);

        s3Events = SupportBean_S3.makeS3("E", new String[] {"E-s2-1"});
        sendEvent(s3Events);
        ArrayAssertionUtil.assertRefAnyOrderArr(new Object[][] {
            { null, s1Events[0], null, s3Events[0], null, null }}, getAndResetNewEvents());

        s4Events = SupportBean_S4.makeS4("E", new String[] {"E-s2-1"});
        sendEvent(s4Events);
        ArrayAssertionUtil.assertRefAnyOrderArr(new Object[][] {
            { null, null, null, null, s4Events[0], null }}, getAndResetNewEvents());

        s0Events = SupportBean_S0.makeS0("E", new String[] {"E-s0-1"});
        sendEvent(s0Events);
        assertFalse(updateListener.isInvoked());

        // Test s0 and s1=2, s2=1, s3=1, s4=1, s5=1
        //
        s1Events = SupportBean_S1.makeS1("F", new String[] {"F-s1-1"});
        sendEvent(s1Events);
        assertFalse(updateListener.isInvoked());

        s2Events = SupportBean_S2.makeS2("F", new String[] {"F-s2-1"});
        sendEvent(s2Events);
        assertFalse(updateListener.isInvoked());

        s3Events = SupportBean_S3.makeS3("F", new String[] {"F-s3-1"});
        sendEvent(s3Events);
        ArrayAssertionUtil.assertRefAnyOrderArr(new Object[][] {
            { null, s1Events[0], null, s3Events[0], null, null }}, getAndResetNewEvents());

        s4Events = SupportBean_S4.makeS4("F", new String[] {"F-s2-1"});
        sendEvent(s4Events);
        ArrayAssertionUtil.assertRefAnyOrderArr(new Object[][] {
            { null, null, null, null, s4Events[0], null }}, getAndResetNewEvents());

        s5Events = SupportBean_S5.makeS5("F", new String[] {"F-s2-1"});
        sendEvent(s5Events);
        ArrayAssertionUtil.assertRefAnyOrderArr(new Object[][] {
            { null, null, s2Events[0], null, s4Events[0], s5Events[0] }}, getAndResetNewEvents());

        s0Events = SupportBean_S0.makeS0("F", new String[] {"F-s0-1"});
        sendEvent(s0Events);
        ArrayAssertionUtil.assertRefAnyOrderArr(new Object[][] {
            { s0Events[0], s1Events[0], s2Events[0], s3Events[0], s4Events[0], s5Events[0] }}, getAndResetNewEvents());

        // Test s0 and s1=2, s2=2, s3=1, s4=1, s5=2
        //
        s1Events = SupportBean_S1.makeS1("G", new String[] {"G-s1-1", "G-s1-2"});
        sendEventsAndReset(s1Events);

        s2Events = SupportBean_S2.makeS2("G", new String[] {"G-s2-1", "G-s2-2"});
        sendEventsAndReset(s2Events);

        s3Events = SupportBean_S3.makeS3("G", new String[] {"G-s3-1"});
        sendEventsAndReset(s3Events);

        s4Events = SupportBean_S4.makeS4("G", new String[] {"G-s2-1"});
        sendEventsAndReset(s4Events);

        s5Events = SupportBean_S5.makeS5("G", new String[] {"G-s5-1", "G-s5-2"});
        sendEventsAndReset(s5Events);

        s0Events = SupportBean_S0.makeS0("G", new String[] {"G-s0-1"});
        sendEvent(s0Events);
        ArrayAssertionUtil.assertRefAnyOrderArr(new Object[][] {
            { s0Events[0], s1Events[0], s2Events[0], s3Events[0], s4Events[0], s5Events[0] },
            { s0Events[0], s1Events[1], s2Events[0], s3Events[0], s4Events[0], s5Events[0] },
            { s0Events[0], s1Events[0], s2Events[0], s3Events[0], s4Events[0], s5Events[1] },
            { s0Events[0], s1Events[1], s2Events[0], s3Events[0], s4Events[0], s5Events[1] },
            { s0Events[0], s1Events[0], s2Events[1], s3Events[0], s4Events[0], s5Events[0] },
            { s0Events[0], s1Events[1], s2Events[1], s3Events[0], s4Events[0], s5Events[0] },
            { s0Events[0], s1Events[0], s2Events[1], s3Events[0], s4Events[0], s5Events[1] },
            { s0Events[0], s1Events[1], s2Events[1], s3Events[0], s4Events[0], s5Events[1] }}, getAndResetNewEvents());

        // Test s0 and s1=2, s2=2, s3=2, s4=2, s5=2
        //
        s1Events = SupportBean_S1.makeS1("H", new String[] {"H-s1-1", "H-s1-2"});
        sendEventsAndReset(s1Events);

        s2Events = SupportBean_S2.makeS2("H", new String[] {"H-s2-1", "H-s2-2"});
        sendEventsAndReset(s2Events);

        s3Events = SupportBean_S3.makeS3("H", new String[] {"H-s3-1", "H-s3-2"});
        sendEventsAndReset(s3Events);

        s4Events = SupportBean_S4.makeS4("H", new String[] {"H-s4-1", "H-s4-2"});
        sendEventsAndReset(s4Events);

        s5Events = SupportBean_S5.makeS5("H", new String[] {"H-s5-1", "H-s5-2"});
        sendEventsAndReset(s5Events);

        s0Events = SupportBean_S0.makeS0("H", new String[] {"H-s0-1"});
        sendEvent(s0Events);
        ArrayAssertionUtil.assertRefAnyOrderArr(new Object[][] {
            { s0Events[0], s1Events[0], s2Events[0], s3Events[0], s4Events[0], s5Events[0] },
            { s0Events[0], s1Events[1], s2Events[0], s3Events[0], s4Events[0], s5Events[0] },
            { s0Events[0], s1Events[0], s2Events[0], s3Events[0], s4Events[0], s5Events[1] },
            { s0Events[0], s1Events[1], s2Events[0], s3Events[0], s4Events[0], s5Events[1] },
            { s0Events[0], s1Events[0], s2Events[1], s3Events[0], s4Events[0], s5Events[0] },
            { s0Events[0], s1Events[1], s2Events[1], s3Events[0], s4Events[0], s5Events[0] },
            { s0Events[0], s1Events[0], s2Events[1], s3Events[0], s4Events[0], s5Events[1] },
            { s0Events[0], s1Events[1], s2Events[1], s3Events[0], s4Events[0], s5Events[1] },
            { s0Events[0], s1Events[0], s2Events[0], s3Events[0], s4Events[1], s5Events[0] },
            { s0Events[0], s1Events[1], s2Events[0], s3Events[0], s4Events[1], s5Events[0] },
            { s0Events[0], s1Events[0], s2Events[0], s3Events[0], s4Events[1], s5Events[1] },
            { s0Events[0], s1Events[1], s2Events[0], s3Events[0], s4Events[1], s5Events[1] },
            { s0Events[0], s1Events[0], s2Events[1], s3Events[0], s4Events[1], s5Events[0] },
            { s0Events[0], s1Events[1], s2Events[1], s3Events[0], s4Events[1], s5Events[0] },
            { s0Events[0], s1Events[0], s2Events[1], s3Events[0], s4Events[1], s5Events[1] },
            { s0Events[0], s1Events[1], s2Events[1], s3Events[0], s4Events[1], s5Events[1] },
            { s0Events[0], s1Events[0], s2Events[0], s3Events[1], s4Events[0], s5Events[0] },
            { s0Events[0], s1Events[1], s2Events[0], s3Events[1], s4Events[0], s5Events[0] },
            { s0Events[0], s1Events[0], s2Events[0], s3Events[1], s4Events[0], s5Events[1] },
            { s0Events[0], s1Events[1], s2Events[0], s3Events[1], s4Events[0], s5Events[1] },
            { s0Events[0], s1Events[0], s2Events[1], s3Events[1], s4Events[0], s5Events[0] },
            { s0Events[0], s1Events[1], s2Events[1], s3Events[1], s4Events[0], s5Events[0] },
            { s0Events[0], s1Events[0], s2Events[1], s3Events[1], s4Events[0], s5Events[1] },
            { s0Events[0], s1Events[1], s2Events[1], s3Events[1], s4Events[0], s5Events[1] },
            { s0Events[0], s1Events[0], s2Events[0], s3Events[1], s4Events[1], s5Events[0] },
            { s0Events[0], s1Events[1], s2Events[0], s3Events[1], s4Events[1], s5Events[0] },
            { s0Events[0], s1Events[0], s2Events[0], s3Events[1], s4Events[1], s5Events[1] },
            { s0Events[0], s1Events[1], s2Events[0], s3Events[1], s4Events[1], s5Events[1] },
            { s0Events[0], s1Events[0], s2Events[1], s3Events[1], s4Events[1], s5Events[0] },
            { s0Events[0], s1Events[1], s2Events[1], s3Events[1], s4Events[1], s5Events[0] },
            { s0Events[0], s1Events[0], s2Events[1], s3Events[1], s4Events[1], s5Events[1] },
            { s0Events[0], s1Events[1], s2Events[1], s3Events[1], s4Events[1], s5Events[1] }}, getAndResetNewEvents());

        // Test s0 and s1=2, s2=1, s3=1, s4=3, s5=1
        //
        s1Events = SupportBean_S1.makeS1("I", new String[] {"I-s1-1", "I-s1-2"});
        sendEventsAndReset(s1Events);

        s2Events = SupportBean_S2.makeS2("I", new String[] {"I-s2-1"});
        sendEventsAndReset(s2Events);

        s3Events = SupportBean_S3.makeS3("I", new String[] {"I-s3-1"});
        sendEventsAndReset(s3Events);

        s4Events = SupportBean_S4.makeS4("I", new String[] {"I-s4-1", "I-s4-2", "I-s4-3"});
        sendEventsAndReset(s4Events);

        s5Events = SupportBean_S5.makeS5("I", new String[] {"I-s5-1"});
        sendEventsAndReset(s5Events);

        s0Events = SupportBean_S0.makeS0("I", new String[] {"I-s0-1"});
        sendEvent(s0Events);
        ArrayAssertionUtil.assertRefAnyOrderArr(new Object[][] {
            { s0Events[0], s1Events[0], s2Events[0], s3Events[0], s4Events[0], s5Events[0] },
            { s0Events[0], s1Events[1], s2Events[0], s3Events[0], s4Events[1], s5Events[0] },
            { s0Events[0], s1Events[0], s2Events[0], s3Events[0], s4Events[2], s5Events[0] },
            { s0Events[0], s1Events[1], s2Events[0], s3Events[0], s4Events[0], s5Events[0] },
            { s0Events[0], s1Events[0], s2Events[0], s3Events[0], s4Events[1], s5Events[0] },
            { s0Events[0], s1Events[1], s2Events[0], s3Events[0], s4Events[2], s5Events[0] }}, getAndResetNewEvents());

        // Test s1 and s3=0
        //
        s1Events = SupportBean_S1.makeS1("J", new String[] {"J-s1-1"});
        sendEvent(s1Events);
        assertFalse(updateListener.isInvoked());

        // Test s1 and s0=1, s2=0, s3=1, s4=1, s5=0
        //
        s0Events = SupportBean_S0.makeS0("K", new String[] {"K-s0-1"});
        sendEvent(s0Events);

        s3Events = SupportBean_S3.makeS3("K", new String[] {"K-s3-1"});
        sendEventsAndReset(s3Events);

        s1Events = SupportBean_S1.makeS1("K", new String[] {"K-s1-1"});
        sendEvent(s1Events);
        ArrayAssertionUtil.assertRefAnyOrderArr(new Object[][] {
            { null, s1Events[0], null, s3Events[0], null, null }}, getAndResetNewEvents());

        // Test s1 and s0=1, s2=1, s3=1, s4=0, s5=1
        //
        s0Events = SupportBean_S0.makeS0("L", new String[] {"L-s0-1"});
        sendEvent(s0Events);
        assertFalse(updateListener.isInvoked());

        s2Events = SupportBean_S2.makeS2("L", new String[] {"L-s2-1"});
        sendEvent(s2Events);
        assertFalse(updateListener.isInvoked());

        s3Events = SupportBean_S3.makeS3("L", new String[] {"L-s3-1"});
        sendEventsAndReset(s3Events);

        s5Events = SupportBean_S5.makeS5("L", new String[] {"L-s5-1"});
        sendEventsAndReset(s5Events);

        s1Events = SupportBean_S1.makeS1("L", new String[] {"L-s1-1"});
        sendEvent(s1Events);
        ArrayAssertionUtil.assertRefAnyOrderArr(new Object[][] {
            { null, s1Events[0], null, s3Events[0], null, null }}, getAndResetNewEvents());

        // Test s1 and s0=1, s2=1, s3=1, s4=2, s5=1
        //
        s0Events = SupportBean_S0.makeS0("M", new String[] {"M-s0-1"});
        sendEvent(s0Events);

        s2Events = SupportBean_S2.makeS2("M", new String[] {"M-s2-1"});
        sendEventsAndReset(s2Events);

        s3Events = SupportBean_S3.makeS3("M", new String[] {"M-s3-1"});
        sendEventsAndReset(s3Events);

        s4Events = SupportBean_S4.makeS4("M", new String[] {"M-s4-1", "M-s4-2"});
        sendEventsAndReset(s4Events);

        s5Events = SupportBean_S5.makeS5("M", new String[] {"M-s5-1"});
        sendEventsAndReset(s5Events);

        s1Events = SupportBean_S1.makeS1("M", new String[] {"M-s1-1"});
        sendEvent(s1Events);
        ArrayAssertionUtil.assertRefAnyOrderArr(new Object[][] {
            { s0Events[0], s1Events[0], s2Events[0], s3Events[0], s4Events[0], s5Events[0] },
            { s0Events[0], s1Events[0], s2Events[0], s3Events[0], s4Events[1], s5Events[0] }}, getAndResetNewEvents());

        // Test s2 and s0=1, s1=0, s3=0, s4=1, s5=2
        //
        s0Events = SupportBean_S0.makeS0("N", new String[] {"N-s0-1"});
        sendEvent(s0Events);

        s4Events = SupportBean_S4.makeS4("N", new String[] {"N-s4-1"});
        sendEventsAndReset(s4Events);

        s5Events = SupportBean_S5.makeS5("N", new String[] {"N-s5-1", "N-s5-2"});
        sendEventsAndReset(s5Events);

        s2Events = SupportBean_S2.makeS2("N", new String[] {"N-s2-1"});
        sendEvent(s2Events);
        ArrayAssertionUtil.assertRefAnyOrderArr(new Object[][] {
            { null, null, s2Events[0], null, s4Events[0], s5Events[0] },
            { null, null, s2Events[0], null, s4Events[0], s5Events[1] }}, getAndResetNewEvents());

        // Test s2 and s0=1, s1=1, s3=3, s4=1, s5=2
        //
        s0Events = SupportBean_S0.makeS0("O", new String[] {"O-s0-1"});
        sendEvent(s0Events);

        s1Events = SupportBean_S1.makeS1("O", new String[] {"O-s1-1"});
        sendEvent(s1Events);

        s3Events = SupportBean_S3.makeS3("O", new String[] {"O-s3-1", "O-s3-2", "O-s3-3"});
        sendEventsAndReset(s3Events);

        s4Events = SupportBean_S4.makeS4("O", new String[] {"O-s4-1"});
        sendEventsAndReset(s4Events);

        s5Events = SupportBean_S5.makeS5("O", new String[] {"O-s5-1", "O-s5-2"});
        sendEventsAndReset(s5Events);

        s2Events = SupportBean_S2.makeS2("O", new String[] {"O-s2-1"});
        sendEvent(s2Events);
        ArrayAssertionUtil.assertRefAnyOrderArr(new Object[][] {
            { s0Events[0], s1Events[0], s2Events[0], s3Events[0], s4Events[0], s5Events[0] },
            { s0Events[0], s1Events[0], s2Events[0], s3Events[1], s4Events[0], s5Events[0] },
            { s0Events[0], s1Events[0], s2Events[0], s3Events[2], s4Events[0], s5Events[0] },
            { s0Events[0], s1Events[0], s2Events[0], s3Events[0], s4Events[0], s5Events[1] },
            { s0Events[0], s1Events[0], s2Events[0], s3Events[1], s4Events[0], s5Events[1] },
            { s0Events[0], s1Events[0], s2Events[0], s3Events[2], s4Events[0], s5Events[1] }}, getAndResetNewEvents());

        // Test s3 and s0=0, s1=0, s2=0, s4=0, s5=0
        //
        s3Events = SupportBean_S3.makeS3("P", new String[] {"P-s1-1"});
        sendEvent(s3Events);
        ArrayAssertionUtil.assertRefAnyOrderArr(new Object[][] {
            { null, null, null, s3Events[0], null, null}}, getAndResetNewEvents());

        // Test s3 and s0=0, s1=1, s2=0, s4=0, s5=0
        //
        s1Events = SupportBean_S1.makeS1("Q", new String[] {"Q-s1-1"});
        sendEvent(s1Events);

        s3Events = SupportBean_S3.makeS3("Q", new String[] {"Q-s1-1"});
        sendEvent(s3Events);
        ArrayAssertionUtil.assertRefAnyOrderArr(new Object[][] {
            { null, s1Events[0], null, s3Events[0], null, null}}, getAndResetNewEvents());

        // Test s3 and s0=1, s1=2, s2=2, s4=0, s5=0
        //
        s0Events = SupportBean_S0.makeS0("R", new String[] {"R-s0-1"});
        sendEvent(s0Events);

        s1Events = SupportBean_S1.makeS1("R", new String[] {"R-s1-1", "R-s1-2"});
        sendEvent(s1Events);

        s2Events = SupportBean_S2.makeS2("R", new String[] {"R-s2-1", "R-s2-1"});
        sendEventsAndReset(s2Events);

        s3Events = SupportBean_S3.makeS3("R", new String[] {"R-s3-1"});
        sendEvent(s3Events);
        ArrayAssertionUtil.assertRefAnyOrderArr(new Object[][] {
            { null, s1Events[0], null, s3Events[0], null, null},
            { null, s1Events[1], null, s3Events[0], null, null}}, getAndResetNewEvents());

        // Test s3 and s0=2, s1=2, s2=1, s4=2, s5=2
        //
        s0Events = SupportBean_S0.makeS0("S", new String[] {"S-s0-1", "S-s0-2"});
        sendEvent(s0Events);

        s1Events = SupportBean_S1.makeS1("S", new String[] {"S-s1-1", "S-s1-2"});
        sendEvent(s1Events);

        s2Events = SupportBean_S2.makeS2("S", new String[] {"S-s2-1", "S-s2-1"});
        sendEventsAndReset(s2Events);

        s4Events = SupportBean_S4.makeS4("S", new String[] {"S-s4-1", "S-s4-2"});
        sendEventsAndReset(s4Events);

        s5Events = SupportBean_S5.makeS5("S", new String[] {"S-s5-1", "S-s5-2"});
        sendEventsAndReset(s5Events);

        s3Events = SupportBean_S3.makeS3("S", new String[] {"s-s3-1"});
        sendEvent(s3Events);
        ArrayAssertionUtil.assertRefAnyOrderArr(new Object[][] {
            { s0Events[0], s1Events[0], s2Events[0], s3Events[0], s4Events[0], s5Events[0] },
            { s0Events[1], s1Events[0], s2Events[0], s3Events[0], s4Events[0], s5Events[0] },
            { s0Events[0], s1Events[0], s2Events[0], s3Events[0], s4Events[0], s5Events[1] },
            { s0Events[1], s1Events[0], s2Events[0], s3Events[0], s4Events[0], s5Events[1] },
            { s0Events[0], s1Events[0], s2Events[1], s3Events[0], s4Events[0], s5Events[0] },
            { s0Events[1], s1Events[0], s2Events[1], s3Events[0], s4Events[0], s5Events[0] },
            { s0Events[0], s1Events[0], s2Events[1], s3Events[0], s4Events[0], s5Events[1] },
            { s0Events[1], s1Events[0], s2Events[1], s3Events[0], s4Events[0], s5Events[1] },
            { s0Events[0], s1Events[0], s2Events[0], s3Events[0], s4Events[1], s5Events[0] },
            { s0Events[1], s1Events[0], s2Events[0], s3Events[0], s4Events[1], s5Events[0] },
            { s0Events[0], s1Events[0], s2Events[0], s3Events[0], s4Events[1], s5Events[1] },
            { s0Events[1], s1Events[0], s2Events[0], s3Events[0], s4Events[1], s5Events[1] },
            { s0Events[0], s1Events[0], s2Events[1], s3Events[0], s4Events[1], s5Events[0] },
            { s0Events[1], s1Events[0], s2Events[1], s3Events[0], s4Events[1], s5Events[0] },
            { s0Events[0], s1Events[0], s2Events[1], s3Events[0], s4Events[1], s5Events[1] },
            { s0Events[1], s1Events[0], s2Events[1], s3Events[0], s4Events[1], s5Events[1] },
            { s0Events[0], s1Events[1], s2Events[0], s3Events[0], s4Events[0], s5Events[0] },
            { s0Events[1], s1Events[1], s2Events[0], s3Events[0], s4Events[0], s5Events[0] },
            { s0Events[0], s1Events[1], s2Events[0], s3Events[0], s4Events[0], s5Events[1] },
            { s0Events[1], s1Events[1], s2Events[0], s3Events[0], s4Events[0], s5Events[1] },
            { s0Events[0], s1Events[1], s2Events[1], s3Events[0], s4Events[0], s5Events[0] },
            { s0Events[1], s1Events[1], s2Events[1], s3Events[0], s4Events[0], s5Events[0] },
            { s0Events[0], s1Events[1], s2Events[1], s3Events[0], s4Events[0], s5Events[1] },
            { s0Events[1], s1Events[1], s2Events[1], s3Events[0], s4Events[0], s5Events[1] },
            { s0Events[0], s1Events[1], s2Events[0], s3Events[0], s4Events[1], s5Events[0] },
            { s0Events[1], s1Events[1], s2Events[0], s3Events[0], s4Events[1], s5Events[0] },
            { s0Events[0], s1Events[1], s2Events[0], s3Events[0], s4Events[1], s5Events[1] },
            { s0Events[1], s1Events[1], s2Events[0], s3Events[0], s4Events[1], s5Events[1] },
            { s0Events[0], s1Events[1], s2Events[1], s3Events[0], s4Events[1], s5Events[0] },
            { s0Events[1], s1Events[1], s2Events[1], s3Events[0], s4Events[1], s5Events[0] },
            { s0Events[0], s1Events[1], s2Events[1], s3Events[0], s4Events[1], s5Events[1] },
            { s0Events[1], s1Events[1], s2Events[1], s3Events[0], s4Events[1], s5Events[1] }}, getAndResetNewEvents());

        // Test s4 and s0=1, s1=0, s2=1, s3=0, s5=0
        //
        s0Events = SupportBean_S0.makeS0("U", new String[] {"U-s0-1"});
        sendEventsAndReset(s0Events);

        s2Events = SupportBean_S2.makeS2("U", new String[] {"U-s1-1"});
        sendEventsAndReset(s2Events);

        s4Events = SupportBean_S4.makeS4("U", new String[] {"U-s4-1"});
        sendEvent(s4Events);
        ArrayAssertionUtil.assertRefAnyOrderArr(new Object[][] {
            { null, null, null, null, s4Events[0], null }}, getAndResetNewEvents());

        // Test s4 and s0=1, s1=0, s2=1, s3=0, s5=1
        //
        s0Events = SupportBean_S0.makeS0("V", new String[] {"V-s0-1"});
        sendEventsAndReset(s0Events);

        s2Events = SupportBean_S2.makeS2("V", new String[] {"V-s1-1"});
        sendEventsAndReset(s2Events);

        s5Events = SupportBean_S5.makeS5("V", new String[] {"V-s5-1"});
        sendEventsAndReset(s5Events);

        s4Events = SupportBean_S4.makeS4("V", new String[] {"V-s4-1"});
        sendEvent(s4Events);
        ArrayAssertionUtil.assertRefAnyOrderArr(new Object[][] {
            { null, null, s2Events[0], null, s4Events[0], s5Events[0] }}, getAndResetNewEvents());

        // Test s4 and s0=1, s1=1, s2=1, s3=1, s5=2
        //
        s0Events = SupportBean_S0.makeS0("W", new String[] {"W-s0-1"});
        sendEvent(s0Events);

        s1Events = SupportBean_S1.makeS1("W", new String[] {"W-s1-1"});
        sendEventsAndReset(s1Events);

        s2Events = SupportBean_S2.makeS2("W", new String[] {"W-s2-1"});
        sendEventsAndReset(s2Events);

        s3Events = SupportBean_S3.makeS3("W", new String[] {"W-s3-1"});
        sendEventsAndReset(s3Events);

        s5Events = SupportBean_S5.makeS5("W", new String[] {"W-s5-1", "W-s5-2"});
        sendEventsAndReset(s5Events);

        s4Events = SupportBean_S4.makeS4("W", new String[] {"W-s4-1"});
        sendEvent(s4Events);
        ArrayAssertionUtil.assertRefAnyOrderArr(new Object[][] {
            { s0Events[0], s1Events[0], s2Events[0], s3Events[0], s4Events[0], s5Events[0] },
            { s0Events[0], s1Events[0], s2Events[0], s3Events[0], s4Events[0], s5Events[1] }}, getAndResetNewEvents());

        // Test s5 and s0=1, s1=2, s2=2, s3=1, s4=1
        //
        s0Events = SupportBean_S0.makeS0("X", new String[] {"X-s0-1"});
        sendEvent(s0Events);

        s1Events = SupportBean_S1.makeS1("X", new String[] {"X-s1-1", "X-s1-2"});
        sendEventsAndReset(s1Events);

        s2Events = SupportBean_S2.makeS2("X", new String[] {"X-s2-1", "X-s2-2"});
        sendEvent(s2Events);

        s3Events = SupportBean_S3.makeS3("X", new String[] {"X-s3-1"});
        sendEventsAndReset(s3Events);

        s4Events = SupportBean_S4.makeS4("X", new String[] {"X-s4-1"});
        sendEventsAndReset(s4Events);

        s5Events = SupportBean_S5.makeS5("X", new String[] {"X-s5-1"});
        sendEvent(s5Events);
        ArrayAssertionUtil.assertRefAnyOrderArr(new Object[][] {
            { s0Events[0], s1Events[0], s2Events[0], s3Events[0], s4Events[0], s5Events[0] },
            { s0Events[0], s1Events[0], s2Events[1], s3Events[0], s4Events[0], s5Events[0] },
            { s0Events[0], s1Events[1], s2Events[0], s3Events[0], s4Events[0], s5Events[0] },
            { s0Events[0], s1Events[1], s2Events[1], s3Events[0], s4Events[0], s5Events[0] }}, getAndResetNewEvents());

        // Test s5 and s0=2, s1=1, s2=1, s3=1, s4=1
        //
        s0Events = SupportBean_S0.makeS0("Y", new String[] {"Y-s0-1", "Y-s0-2"});
        sendEvent(s0Events);

        s1Events = SupportBean_S1.makeS1("Y", new String[] {"Y-s1-1"});
        sendEventsAndReset(s1Events);

        s2Events = SupportBean_S2.makeS2("Y", new String[] {"Y-s2-1"});
        sendEvent(s2Events);

        s3Events = SupportBean_S3.makeS3("Y", new String[] {"Y-s3-1"});
        sendEventsAndReset(s3Events);

        s4Events = SupportBean_S4.makeS4("Y", new String[] {"Y-s4-1"});
        sendEventsAndReset(s4Events);

        s5Events = SupportBean_S5.makeS5("Y", new String[] {"X-s5-1"});
        sendEvent(s5Events);
        ArrayAssertionUtil.assertRefAnyOrderArr(new Object[][] {
            { s0Events[0], s1Events[0], s2Events[0], s3Events[0], s4Events[0], s5Events[0] },
            { s0Events[1], s1Events[0], s2Events[0], s3Events[0], s4Events[0], s5Events[0] }}, getAndResetNewEvents());

        // Test s5 and s0=1, s1=1, s2=1, s3=2, s4=2
        //
        s0Events = SupportBean_S0.makeS0("Z", new String[] {"Z-s0-1"});
        sendEvent(s0Events);

        s1Events = SupportBean_S1.makeS1("Z", new String[] {"Z-s1-1"});
        sendEventsAndReset(s1Events);

        s2Events = SupportBean_S2.makeS2("Z", new String[] {"Z-s2-1"});
        sendEventsAndReset(s2Events);

        s3Events = SupportBean_S3.makeS3("Z", new String[] {"Z-s3-1", "Z-s3-2"});
        sendEventsAndReset(s3Events);

        s4Events = SupportBean_S4.makeS4("Z", new String[] {"Z-s4-1", "Z-s4-2"});
        sendEventsAndReset(s4Events);

        s5Events = SupportBean_S5.makeS5("Z", new String[] {"Z-s5-1"});
        sendEvent(s5Events);
        ArrayAssertionUtil.assertRefAnyOrderArr(new Object[][] {
            { s0Events[0], s1Events[0], s2Events[0], s3Events[0], s4Events[0], s5Events[0] },
            { s0Events[0], s1Events[0], s2Events[0], s3Events[0], s4Events[1], s5Events[0] },
            { s0Events[0], s1Events[0], s2Events[0], s3Events[1], s4Events[0], s5Events[0] },
            { s0Events[0], s1Events[0], s2Events[0], s3Events[1], s4Events[1], s5Events[0] }}, getAndResetNewEvents());
    }

    private void sendEventsAndReset(Object[] events)
    {
        sendEvent(events);
        updateListener.reset();
    }

    private void sendEvent(Object[] events)
    {
        for (int i = 0; i < events.length; i++)
        {
            epService.getEPRuntime().sendEvent(events[i]);
        }
    }

    private Object[][] getAndResetNewEvents()
    {
        EventBean[] newEvents = updateListener.getLastNewData();
        updateListener.reset();
        return ArrayHandlingUtil.getUnderlyingEvents(newEvents, new String[] {"s0", "s1", "s2","s3", "s4", "s5"});
    }
}
