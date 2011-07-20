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
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.bean.SupportBean_S0;
import com.espertech.esper.support.bean.SupportBean_S1;
import com.espertech.esper.support.bean.SupportBean_S2;
import com.espertech.esper.support.bean.SupportBean_S3;
import com.espertech.esper.support.client.SupportConfigFactory;

public class Test4StreamOuterInnerJoin extends TestCase
{
    private String fields[] = "s0.id, s0.p00, s1.id, s1.p10, s2.id, s2.p20, s3.id, s3.p30".split(",");
    private EPServiceProvider epService;
    private EPStatement joinView;
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

    public void testFullMiddleJoinVariantTwo()
    {
        String joinStatement =  "select * from S3.win:keepall() s3 " +
                                " inner join S2.win:keepall() s2 on s3.p30 = s2.p20 " +
                                " full outer join S1.win:keepall() s1 on s2.p20 = s1.p10 " +
                                " inner join S0.win:keepall() s0 on s1.p10 = s0.p00";

        runAssertionMiddle(joinStatement);
    }

    public void testFullMiddleJoinVariantOne()
    {
        String joinStatement =  "select * from S0.win:keepall() s0 " +
                                " inner join S1.win:keepall() s1 on s0.p00 = s1.p10 " +
                                " full outer join S2.win:keepall() s2 on s1.p10 = s2.p20 " +
                                " inner join S3.win:keepall() s3 on s2.p20 = s3.p30";

        runAssertionMiddle(joinStatement);
    }

    public void testFullSidedJoinVariantTwo()
    {
        String joinStatement =  "select * from S3.win:keepall() s3 " +
                                " full outer join S2.win:keepall() s2 on s3.p30 = s2.p20 " +
                                " full outer join S1.win:keepall() s1 on s2.p20 = s1.p10 " +
                                " inner join S0.win:keepall() s0 on s1.p10 = s0.p00";

        runAssertionSided(joinStatement);
    }

    public void testFullSidedJoinVariantOne()
    {
        String joinStatement =  "select * from S0.win:keepall() s0 " +
                                " inner join S1.win:keepall() s1 on s0.p00 = s1.p10 " +
                                " full outer join S2.win:keepall() s2 on s1.p10 = s2.p20 " +
                                " full outer join S3.win:keepall() s3 on s2.p20 = s3.p30";

        runAssertionSided(joinStatement);
    }

    public void testStarJoinVariantTwo()
    {
        String joinStatement =  "select * from S0.win:keepall() s0 " +
                                " left outer join S1.win:keepall() s1 on s0.p00 = s1.p10 " +
                                " full outer join S2.win:keepall() s2 on s0.p00 = s2.p20 " +
                                " inner join S3.win:keepall() s3 on s0.p00 = s3.p30";

        runAssertionStar(joinStatement);
    }

    public void testStarJoinVariantOne()
    {
        String joinStatement =  "select * from S3.win:keepall() s3 " +
                                " inner join S0.win:keepall() s0 on s0.p00 = s3.p30 " +
                                " full outer join S2.win:keepall() s2 on s0.p00 = s2.p20 " +
                                " left outer join S1.win:keepall() s1 on s1.p10 = s0.p00";

        runAssertionStar(joinStatement);
    }

    public void runAssertionMiddle(String expression)
    {
        String fields[] = "s0.id, s0.p00, s1.id, s1.p10, s2.id, s2.p20, s3.id, s3.p30".split(",");

        joinView = epService.getEPAdministrator().createEPL(expression);
        joinView.addListener(listener);

        // s0, s1, s2, s3
        epService.getEPRuntime().sendEvent(new SupportBean_S0(0, "A"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S1(100, "A"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S2(200, "A"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S3(300, "A"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {0, "A", 100, "A", 200, "A", 300, "A"});

        // s0, s2, s3, s1
        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, "B"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S2(201, "B"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S3(301, "B"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S1(101, "B"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {1, "B", 101, "B", 201, "B", 301, "B"});

        // s2, s3, s1, s0
        epService.getEPRuntime().sendEvent(new SupportBean_S2(202, "C"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S3(302, "C"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S1(102, "C"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S0(2, "C"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {2, "C", 102, "C", 202, "C", 302, "C"});

        // s1, s2, s0, s3
        epService.getEPRuntime().sendEvent(new SupportBean_S1(103, "D"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S2(203, "D"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S0(3, "D"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S3(303, "D"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {3, "D", 103, "D", 203, "D", 303, "D"});
    }

    public void runAssertionSided(String expression)
    {
        joinView = epService.getEPAdministrator().createEPL(expression);
        joinView.addListener(listener);

        // s0, s1, s2, s3
        epService.getEPRuntime().sendEvent(new SupportBean_S0(0, "A"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S1(100, "A"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {0, "A", 100, "A", null, null, null, null});

        epService.getEPRuntime().sendEvent(new SupportBean_S2(200, "A"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {0, "A", 100, "A", 200, "A", null, null});

        epService.getEPRuntime().sendEvent(new SupportBean_S3(300, "A"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {0, "A", 100, "A", 200, "A", 300, "A"});

        // s0, s2, s3, s1
        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, "B"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S2(201, "B"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S3(301, "B"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S1(101, "B"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {1, "B", 101, "B", 201, "B", 301, "B"});

        // s2, s3, s1, s0
        epService.getEPRuntime().sendEvent(new SupportBean_S2(202, "C"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S3(302, "C"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S1(102, "C"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S0(2, "C"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {2, "C", 102, "C", 202, "C", 302, "C"});

        // s1, s2, s0, s3
        epService.getEPRuntime().sendEvent(new SupportBean_S1(103, "D"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S2(203, "D"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S0(3, "D"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {3, "D", 103, "D", 203, "D", null, null});

        epService.getEPRuntime().sendEvent(new SupportBean_S3(303, "D"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {3, "D", 103, "D", 203, "D", 303, "D"});
    }

    public void runAssertionStar(String expression)
    {
        joinView = epService.getEPAdministrator().createEPL(expression);
        joinView.addListener(listener);

        // s0, s1, s2, s3
        epService.getEPRuntime().sendEvent(new SupportBean_S0(0, "A"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S1(100, "A"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S2(200, "A"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S3(300, "A"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {0, "A", 100, "A", 200, "A", 300, "A"});

        // s0, s2, s3, s1
        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, "B"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S2(201, "B"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S3(301, "B"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {1, "B", null, null, 201, "B", 301, "B"});

        epService.getEPRuntime().sendEvent(new SupportBean_S1(101, "B"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {1, "B", 101, "B", 201, "B", 301, "B"});

        // s2, s3, s1, s0
        epService.getEPRuntime().sendEvent(new SupportBean_S2(202, "C"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S3(302, "C"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S1(102, "C"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S0(2, "C"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {2, "C", 102, "C", 202, "C", 302, "C"});

        // s1, s2, s0, s3
        epService.getEPRuntime().sendEvent(new SupportBean_S1(103, "D"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S2(203, "D"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S0(3, "D"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S3(303, "D"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {3, "D", 103, "D", 203, "D", 303, "D"});

        // s3, s0, s1, s2
        epService.getEPRuntime().sendEvent(new SupportBean_S3(304, "E"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S0(4, "E"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {4, "E", null, null, null, null, 304, "E"});

        epService.getEPRuntime().sendEvent(new SupportBean_S1(104, "E"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {4, "E", 104, "E", null, null, 304, "E"});

        epService.getEPRuntime().sendEvent(new SupportBean_S2(204, "E"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {4, "E", 104, "E", 204, "E", 304, "E"});
    }
}
