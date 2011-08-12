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

package com.espertech.esper.regression.pattern;

import junit.framework.TestCase;
import com.espertech.esper.regression.support.CaseList;
import com.espertech.esper.regression.support.EventCollection;
import com.espertech.esper.regression.support.EventCollectionFactory;
import com.espertech.esper.regression.support.EventExpressionCase;
import com.espertech.esper.regression.support.PatternTestHarness;
import com.espertech.esper.support.bean.ISupportA;
import com.espertech.esper.support.bean.ISupportAImplSuperG;
import com.espertech.esper.support.bean.ISupportAImplSuperGImplPlus;
import com.espertech.esper.support.bean.ISupportB;
import com.espertech.esper.support.bean.ISupportBaseAB;
import com.espertech.esper.support.bean.ISupportBaseD;
import com.espertech.esper.support.bean.ISupportBaseDBase;
import com.espertech.esper.support.bean.ISupportC;
import com.espertech.esper.support.bean.ISupportD;
import com.espertech.esper.support.bean.SupportOverrideBase;
import com.espertech.esper.support.bean.SupportOverrideOne;
import com.espertech.esper.support.bean.SupportOverrideOneA;
import com.espertech.esper.support.bean.SupportOverrideOneB;

public class TestSuperAndInterfaces extends TestCase
{
    private static String INTERFACE_A = ISupportA.class.getName();
    private static String INTERFACE_B = ISupportB.class.getName();
    private static String INTERFACE_C = ISupportC.class.getName();
    private static String INTERFACE_D = ISupportD.class.getName();
    private static String INTERFACE_BASE_D = ISupportBaseD.class.getName();
    private static String INTERFACE_BASE_D_BASE = ISupportBaseDBase.class.getName();
    private static String INTERFACE_BASE_AB = ISupportBaseAB.class.getName();
    private static String SUPER_G = ISupportAImplSuperG.class.getName();
    private static String SUPER_G_IMPL = ISupportAImplSuperGImplPlus.class.getName();
    private static String OVERRIDE_BASE = SupportOverrideBase.class.getName();
    private static String OVERRIDE_ONE = SupportOverrideOne.class.getName();
    private static String OVERRIDE_ONEA = SupportOverrideOneA.class.getName();
    private static String OVERRIDE_ONEB = SupportOverrideOneB.class.getName();

    public void testInterfacedEvents() throws Exception
    {
        EventCollection events = EventCollectionFactory.getSetFiveInterfaces();
        CaseList testCaseList = new CaseList();
        EventExpressionCase testCase = null;

        testCase = new EventExpressionCase("c=" + INTERFACE_C);
        testCase.add("e1", "c", events.getEvent("e1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("baseab=" + INTERFACE_BASE_AB);
        testCase.add("e2", "baseab", events.getEvent("e2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every a=" + INTERFACE_A);
        testCase.add("e2", "a", events.getEvent("e2"));
        testCase.add("e3", "a", events.getEvent("e3"));
        testCase.add("e12", "a", events.getEvent("e12"));
        testCase.add("e13", "a", events.getEvent("e13"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every a=" + INTERFACE_B);
        testCase.add("e2", "a", events.getEvent("e2"));
        testCase.add("e4", "a", events.getEvent("e4"));
        testCase.add("e6", "a", events.getEvent("e6"));
        testCase.add("e12", "a", events.getEvent("e12"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every a=" + INTERFACE_B + "(b='B1')");
        testCase.add("e2", "a", events.getEvent("e2"));
        testCase.add("e4", "a", events.getEvent("e4"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every a=" + INTERFACE_A + "(a='A3')");
        testCase.add("e12", "a", events.getEvent("e12"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every a=" + INTERFACE_C + "(c='C2')");
        testCase.add("e6", "a", events.getEvent("e6"));
        testCase.add("e12", "a", events.getEvent("e12"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every a=" + INTERFACE_C + "(c='C1')");
        testCase.add("e1", "a", events.getEvent("e1"));
        testCase.add("e2", "a", events.getEvent("e2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every a=" + INTERFACE_D + "(d='D1')");
        testCase.add("e5", "a", events.getEvent("e5"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every a=" + INTERFACE_BASE_D + "(baseD='BaseD')");
        testCase.add("e5", "a", events.getEvent("e5"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every a=" + INTERFACE_BASE_D_BASE + "(baseDBase='BaseDBase')");
        testCase.add("e5", "a", events.getEvent("e5"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every a=" + INTERFACE_D + "(d='D1', baseD='BaseD', baseDBase='BaseDBase')");
        testCase.add("e5", "a", events.getEvent("e5"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every a=" + INTERFACE_BASE_D + "(baseD='BaseD', baseDBase='BaseDBase')");
        testCase.add("e5", "a", events.getEvent("e5"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every a=" + SUPER_G);
        testCase.add("e12", "a", events.getEvent("e12"));
        testCase.add("e13", "a", events.getEvent("e13"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every a=" + SUPER_G + "(g='G1')");
        testCase.add("e12", "a", events.getEvent("e12"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every a=" + SUPER_G + "(baseAB='BaseAB5')");
        testCase.add("e13", "a", events.getEvent("e13"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every a=" + SUPER_G + "(baseAB='BaseAB4', g='G1', a='A3')");
        testCase.add("e12", "a", events.getEvent("e12"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every a=" + SUPER_G_IMPL + "(baseAB='BaseAB4', g='G1', a='A3', b='B4', c='C2')");
        testCase.add("e12", "a", events.getEvent("e12"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every a=" + OVERRIDE_BASE);
        testCase.add("e8", "a", events.getEvent("e8"));
        testCase.add("e9", "a", events.getEvent("e9"));
        testCase.add("e10", "a", events.getEvent("e10"));
        testCase.add("e11", "a", events.getEvent("e11"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every a=" + OVERRIDE_ONE);
        testCase.add("e8", "a", events.getEvent("e8"));
        testCase.add("e9", "a", events.getEvent("e9"));
        testCase.add("e10", "a", events.getEvent("e10"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every a=" + OVERRIDE_ONEA);
        testCase.add("e8", "a", events.getEvent("e8"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every a=" + OVERRIDE_ONEB);
        testCase.add("e9", "a", events.getEvent("e9"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every a=" + OVERRIDE_BASE + "(val='OB1')");
        testCase.add("e9", "a", events.getEvent("e9"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every a=" + OVERRIDE_BASE + "(val='O3')");
        testCase.add("e10", "a", events.getEvent("e10"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every a=" + OVERRIDE_BASE + "(val='OBase')");
        testCase.add("e11", "a", events.getEvent("e11"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every a=" + OVERRIDE_BASE + "(val='O2')");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every a=" + OVERRIDE_ONE + "(val='OA1')");
        testCase.add("e8", "a", events.getEvent("e8"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every a=" + OVERRIDE_ONE + "(val='O3')");
        testCase.add("e10", "a", events.getEvent("e10"));
        testCaseList.addTest(testCase);

        PatternTestHarness util = new PatternTestHarness(events, testCaseList);
        util.runTest();
    }
}


