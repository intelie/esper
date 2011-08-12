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
import com.espertech.esper.support.bean.SupportBeanInt;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.epl.SupportJoinMethods;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

public class TestFromClauseMethodNStream extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getLogging().setEnableQueryPlan(true);
        config.addEventType(SupportBeanInt.class);
        config.addImport(SupportJoinMethods.class.getName());
        config.addVariable("var1", Integer.class, 0);
        config.addVariable("var2", Integer.class, 0);
        config.addVariable("var3", Integer.class, 0);
        config.addVariable("var4", Integer.class, 0);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void test1Stream2HistStarSubordinateCartesianLast()
    {
        String expression;

        expression = "select s0.id as id, h0.val as valh0, h1.val as valh1 " +
                   "from SupportBeanInt.std:lastevent() as s0, " +
                   "method:SupportJoinMethods.fetchVal('H0', p00) as h0, " +
                   "method:SupportJoinMethods.fetchVal('H1', p01) as h1 " +
                   "order by h0.val, h1.val";

        EPStatement stmt = epService.getEPAdministrator().createEPL(expression);
        listener = new SupportUpdateListener();
        stmt.addListener(listener);

        String[] fields = "id,valh0,valh1".split(",");
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, null);

        sendBeanInt("E1", 1, 1);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, new Object[][] {{"E1", "H01", "H11"}});
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1", "H01", "H11"}});

        sendBeanInt("E2", 2, 0);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, null);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, null);

        sendBeanInt("E3", 0, 1);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, null);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, null);

        sendBeanInt("E3", 2, 2);
        Object[][] result = new Object[][] {{"E3", "H01", "H11"}, {"E3", "H01", "H12"}, {"E3", "H02", "H11"}, {"E3", "H02", "H12"}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, result);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, result);

        sendBeanInt("E4", 2, 1);
        result = new Object[][] {{"E4", "H01", "H11"}, {"E4", "H02", "H11"}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, result);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, result);
    }

    public void test1Stream2HistStarSubordinateJoinedKeepall()
    {
        String expression;

        expression = "select s0.id as id, h0.val as valh0, h1.val as valh1 " +
                   "from SupportBeanInt.win:keepall() as s0, " +
                   "method:SupportJoinMethods.fetchVal('H0', p00) as h0, " +
                   "method:SupportJoinMethods.fetchVal('H1', p01) as h1 " +
                   "where h0.index = h1.index and h0.index = p02";
        runAssertionOne(expression);

        expression = "select s0.id as id, h0.val as valh0, h1.val as valh1   from " +
                    "method:SupportJoinMethods.fetchVal('H1', p01) as h1, " +
                    "method:SupportJoinMethods.fetchVal('H0', p00) as h0, " +
                   "SupportBeanInt.win:keepall() as s0 " +
                   "where h0.index = h1.index and h0.index = p02";
        runAssertionOne(expression);
    }

    private void runAssertionOne(String expression)
    {
        EPStatement stmt = epService.getEPAdministrator().createEPL(expression);
        listener = new SupportUpdateListener();
        stmt.addListener(listener);

        String[] fields = "id,valh0,valh1".split(",");
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, null);

        sendBeanInt("E1", 20, 20, 3);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, new Object[][] {{"E1", "H03", "H13"}});
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1", "H03", "H13"}});

        sendBeanInt("E2", 20, 20, 21);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, null);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1", "H03", "H13"}});

        sendBeanInt("E3", 4, 4, 2);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, new Object[][] {{"E3", "H02", "H12"}});
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1", "H03", "H13"}, {"E3", "H02", "H12"}});

        stmt.destroy();
    }

    public void test1Stream2HistForwardSubordinate()
    {
        String expression;

        expression = "select s0.id as id, h0.val as valh0, h1.val as valh1 " +
                   "from SupportBeanInt.win:keepall() as s0, " +
                   "method:SupportJoinMethods.fetchVal('H0', p00) as h0, " +
                   "method:SupportJoinMethods.fetchVal(h0.val, p01) as h1 " +
                   "order by h0.val, h1.val";
        runAssertionTwo(expression);

        expression = "select s0.id as id, h0.val as valh0, h1.val as valh1 from " +
                   "method:SupportJoinMethods.fetchVal(h0.val, p01) as h1, " +
                   "SupportBeanInt.win:keepall() as s0, " +
                   "method:SupportJoinMethods.fetchVal('H0', p00) as h0 " +
                   "order by h0.val, h1.val";
        runAssertionTwo(expression);
    }
    
    private void runAssertionTwo(String expression)
    {
        EPStatement stmt = epService.getEPAdministrator().createEPL(expression);
        listener = new SupportUpdateListener();
        stmt.addListener(listener);

        String[] fields = "id,valh0,valh1".split(",");
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, null);

        sendBeanInt("E1", 1, 1);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, new Object[][] {{"E1", "H01", "H011"}});
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1", "H01", "H011"}});

        sendBeanInt("E2", 0, 1);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, null);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1", "H01", "H011"}});

        sendBeanInt("E3", 1, 0);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, null);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1", "H01", "H011"}});

        sendBeanInt("E4", 2, 2);
        Object[][] result = {{"E4", "H01", "H011"}, {"E4", "H01", "H012"}, {"E4", "H02", "H021"}, {"E4", "H02", "H022"}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, result);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, ArrayAssertionUtil.addArray(result, new Object[][] {{"E1", "H01", "H011"}}));
    }

    public void test1Stream3HistStarSubordinateCartesianLast()
    {
        String expression;

        expression = "select s0.id as id, h0.val as valh0, h1.val as valh1, h2.val as valh2 " +
                   "from SupportBeanInt.std:lastevent() as s0, " +
                   "method:SupportJoinMethods.fetchVal('H0', p00) as h0, " +
                   "method:SupportJoinMethods.fetchVal('H1', p01) as h1, " +
                   "method:SupportJoinMethods.fetchVal('H2', p02) as h2 " +
                   "order by h0.val, h1.val, h2.val";
        runAssertionThree(expression);

        expression = "select s0.id as id, h0.val as valh0, h1.val as valh1, h2.val as valh2 from " +
                   "method:SupportJoinMethods.fetchVal('H2', p02) as h2, " +
                   "method:SupportJoinMethods.fetchVal('H1', p01) as h1, " +
                   "method:SupportJoinMethods.fetchVal('H0', p00) as h0, " +
                   "SupportBeanInt.std:lastevent() as s0 " +
                   "order by h0.val, h1.val, h2.val";
        runAssertionThree(expression);
    }

    private void runAssertionThree(String expression)
    {
        EPStatement stmt = epService.getEPAdministrator().createEPL(expression);
        listener = new SupportUpdateListener();
        stmt.addListener(listener);

        String[] fields = "id,valh0,valh1,valh2".split(",");
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, null);

        sendBeanInt("E1", 1, 1, 1);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, new Object[][] {{"E1", "H01", "H11", "H21"}});
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1", "H01", "H11", "H21"}});

        sendBeanInt("E2", 1, 1, 2);
        Object[][] result = new Object[][] {{"E2", "H01", "H11", "H21"}, {"E2", "H01", "H11", "H22"}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, result);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, result);
    }

    public void test1Stream3HistForwardSubordinate()
    {
        String expression;

        expression = "select s0.id as id, h0.val as valh0, h1.val as valh1, h2.val as valh2 " +
                   "from SupportBeanInt.win:keepall() as s0, " +
                   "method:SupportJoinMethods.fetchVal('H0', p00) as h0, " +
                   "method:SupportJoinMethods.fetchVal('H1', p01) as h1, " +
                   "method:SupportJoinMethods.fetchVal(h0.val||'H2', p02) as h2 " +
                   " where h0.index = h1.index and h1.index = h2.index and h2.index = p03";
        runAssertionFour(expression);

        expression = "select s0.id as id, h0.val as valh0, h1.val as valh1, h2.val as valh2 from " +
                    "method:SupportJoinMethods.fetchVal(h0.val||'H2', p02) as h2, " +
                    "method:SupportJoinMethods.fetchVal('H0', p00) as h0, " +
                    "SupportBeanInt.win:keepall() as s0, " +
                    "method:SupportJoinMethods.fetchVal('H1', p01) as h1 " +
                    " where h0.index = h1.index and h1.index = h2.index and h2.index = p03";
        runAssertionFour(expression);
    }

    private void runAssertionFour(String expression)
    {
        EPStatement stmt = epService.getEPAdministrator().createEPL(expression);
        listener = new SupportUpdateListener();
        stmt.addListener(listener);

        String[] fields = "id,valh0,valh1,valh2".split(",");
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, null);

        sendBeanInt("E1", 2, 2, 2, 1);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, new Object[][] {{"E1", "H01", "H11", "H01H21"}});
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1", "H01", "H11", "H01H21"}});

        sendBeanInt("E2", 4, 4, 4, 3);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, new Object[][] {{"E2", "H03", "H13", "H03H23"}});
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1", "H01", "H11", "H01H21"}, {"E2", "H03", "H13", "H03H23"}});
    }

    public void test1Stream3HistChainSubordinate()
    {
        String expression;

        expression = "select s0.id as id, h0.val as valh0, h1.val as valh1, h2.val as valh2 " +
                   "from SupportBeanInt.win:keepall() as s0, " +
                   "method:SupportJoinMethods.fetchVal('H0', p00) as h0, " +
                   "method:SupportJoinMethods.fetchVal(h0.val||'H1', p01) as h1, " +
                   "method:SupportJoinMethods.fetchVal(h1.val||'H2', p02) as h2 " +
                   " where h0.index = h1.index and h1.index = h2.index and h2.index = p03";

        EPStatement stmt = epService.getEPAdministrator().createEPL(expression);
        listener = new SupportUpdateListener();
        stmt.addListener(listener);

        String[] fields = "id,valh0,valh1,valh2".split(",");
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, null);

        sendBeanInt("E2", 4, 4, 4, 3);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, new Object[][] {{"E2", "H03", "H03H13", "H03H13H23"}});

        sendBeanInt("E2", 4, 4, 4, 5);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, null);

        sendBeanInt("E2", 4, 4, 0, 1);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, null);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E2", "H03", "H03H13", "H03H13H23"}});
    }

    public void test2Stream2HistStarSubordinate()
    {
        String expression;

        expression = "select s0.id as ids0, s1.id as ids1, h0.val as valh0, h1.val as valh1 " +
                   "from SupportBeanInt(id like 'S0%').win:keepall() as s0, " +
                   "SupportBeanInt(id like 'S1%').std:lastevent() as s1, " +
                   "method:SupportJoinMethods.fetchVal(s0.id||'H1', s0.p00) as h0, " +
                   "method:SupportJoinMethods.fetchVal(s1.id||'H2', s1.p00) as h1 " +
                   "order by s0.id asc";

        EPStatement stmt = epService.getEPAdministrator().createEPL(expression);
        listener = new SupportUpdateListener();
        stmt.addListener(listener);

        String[] fields = "ids0,ids1,valh0,valh1".split(",");
        sendBeanInt("S00", 1);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, null);
        assertFalse(listener.isInvoked());

        sendBeanInt("S10", 1);
        Object[][] resultOne = new Object[][] {{"S00", "S10", "S00H11", "S10H21"}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, resultOne);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, resultOne);

        sendBeanInt("S01", 1);
        Object[][] resultTwo = new Object[][] {{"S01", "S10", "S01H11", "S10H21"}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, resultTwo);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, ArrayAssertionUtil.addArray(resultOne, resultTwo));

        sendBeanInt("S11", 1);
        Object[][] resultThree = new Object[][] {{"S00", "S11", "S00H11", "S11H21"}, {"S01", "S11", "S01H11", "S11H21"}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, resultThree);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, ArrayAssertionUtil.addArray(resultThree));
    }

    public void test3Stream1HistSubordinate()
    {
        String expression;

        expression = "select s0.id as ids0, s1.id as ids1, s2.id as ids2, h0.val as valh0 " +
                   "from SupportBeanInt(id like 'S0%').win:keepall() as s0, " +
                   "SupportBeanInt(id like 'S1%').std:lastevent() as s1, " +
                   "SupportBeanInt(id like 'S2%').std:lastevent() as s2, " +
                   "method:SupportJoinMethods.fetchVal(s1.id||s2.id||'H1', s0.p00) as h0 " +
                   "order by s0.id, s1.id, s2.id, h0.val";

        EPStatement stmt = epService.getEPAdministrator().createEPL(expression);
        listener = new SupportUpdateListener();
        stmt.addListener(listener);

        String[] fields = "ids0,ids1,ids2,valh0".split(",");
        sendBeanInt("S00", 2);
        sendBeanInt("S10", 1);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, null);
        assertFalse(listener.isInvoked());

        sendBeanInt("S20", 1);
        Object[][] resultOne = new Object[][] {{"S00", "S10", "S20", "S10S20H11"}, {"S00", "S10", "S20", "S10S20H12"}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, resultOne);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, resultOne);

        sendBeanInt("S01", 1);
        Object[][] resultTwo = new Object[][] {{"S01", "S10", "S20", "S10S20H11"}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, resultTwo);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, ArrayAssertionUtil.addArray(resultOne, resultTwo));

        sendBeanInt("S21", 1);
        Object[][] resultThree = new Object[][] {{"S00", "S10", "S21", "S10S21H11"}, {"S00", "S10", "S21", "S10S21H12"}, {"S01", "S10", "S21", "S10S21H11"}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, resultThree);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, ArrayAssertionUtil.addArray(resultThree));
    }

    public void test3HistPureNoSubordinate()
    {
        epService.getEPAdministrator().createEPL("on SupportBeanInt set var1=p00, var2=p01, var3=p02, var4=p03");

        String expression;
        expression = "select h0.val as valh0, h1.val as valh1, h2.val as valh2 from " +
                    "method:SupportJoinMethods.fetchVal('H0', var1) as h0," +
                    "method:SupportJoinMethods.fetchVal('H1', var2) as h1," +
                    "method:SupportJoinMethods.fetchVal('H2', var3) as h2";
        runAssertionFive(expression);

        expression = "select h0.val as valh0, h1.val as valh1, h2.val as valh2 from " +
                    "method:SupportJoinMethods.fetchVal('H2', var3) as h2," +
                    "method:SupportJoinMethods.fetchVal('H1', var2) as h1," +
                    "method:SupportJoinMethods.fetchVal('H0', var1) as h0";
        runAssertionFive(expression);
    }

    private void runAssertionFive(String expression)
    {        
        EPStatement stmt = epService.getEPAdministrator().createEPL(expression);
        listener = new SupportUpdateListener();
        stmt.addListener(listener);
        String[] fields = "valh0,valh1,valh2".split(",");

        sendBeanInt("S00", 1, 1, 1);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"H01", "H11", "H21"}});

        sendBeanInt("S01", 0, 1, 1);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, null);

        sendBeanInt("S02", 1, 1, 0);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, null);

        sendBeanInt("S03", 1, 1, 2);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"H01", "H11", "H21"}, {"H01", "H11", "H22"}});

        sendBeanInt("S04", 2, 2, 1);
        Object[][] result = new Object[][] {{"H01", "H11", "H21"}, {"H02", "H11", "H21"}, {"H01", "H12", "H21"}, {"H02", "H12", "H21"}};
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, result);
    }

    public void test3Hist1Subordinate()
    {
        epService.getEPAdministrator().createEPL("on SupportBeanInt set var1=p00, var2=p01, var3=p02, var4=p03");

        String expression;
        expression = "select h0.val as valh0, h1.val as valh1, h2.val as valh2 from " +
                    "method:SupportJoinMethods.fetchVal('H0', var1) as h0," +
                    "method:SupportJoinMethods.fetchVal('H1', var2) as h1," +
                    "method:SupportJoinMethods.fetchVal(h0.val||'-H2', var3) as h2";
        runAssertionSix(expression);

        expression = "select h0.val as valh0, h1.val as valh1, h2.val as valh2 from " +
                    "method:SupportJoinMethods.fetchVal(h0.val||'-H2', var3) as h2," +
                    "method:SupportJoinMethods.fetchVal('H1', var2) as h1," +
                    "method:SupportJoinMethods.fetchVal('H0', var1) as h0";
        runAssertionSix(expression);
    }

    private void runAssertionSix(String expression)
    {
        EPStatement stmt = epService.getEPAdministrator().createEPL(expression);
        listener = new SupportUpdateListener();
        stmt.addListener(listener);
        String[] fields = "valh0,valh1,valh2".split(",");

        sendBeanInt("S00", 1, 1, 1);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"H01", "H11", "H01-H21"}});

        sendBeanInt("S01", 0, 1, 1);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, null);

        sendBeanInt("S02", 1, 1, 0);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, null);

        sendBeanInt("S03", 1, 1, 2);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"H01", "H11", "H01-H21"}, {"H01", "H11", "H01-H22"}});

        sendBeanInt("S04", 2, 2, 1);
        Object[][] result = new Object[][] {{"H01", "H11", "H01-H21"}, {"H02", "H11", "H02-H21"}, {"H01", "H12", "H01-H21"}, {"H02", "H12", "H02-H21"}};
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, result);
    }

    public void test3Hist2SubordinateChain()
    {
        epService.getEPAdministrator().createEPL("on SupportBeanInt set var1=p00, var2=p01, var3=p02, var4=p03");

        String expression;
        expression = "select h0.val as valh0, h1.val as valh1, h2.val as valh2 from " +
                    "method:SupportJoinMethods.fetchVal('H0', var1) as h0," +
                    "method:SupportJoinMethods.fetchVal(h0.val||'-H1', var2) as h1," +
                    "method:SupportJoinMethods.fetchVal(h1.val||'-H2', var3) as h2";
        runAssertionSeven(expression);

        expression = "select h0.val as valh0, h1.val as valh1, h2.val as valh2 from " +
                    "method:SupportJoinMethods.fetchVal(h1.val||'-H2', var3) as h2," +
                    "method:SupportJoinMethods.fetchVal(h0.val||'-H1', var2) as h1," +
                    "method:SupportJoinMethods.fetchVal('H0', var1) as h0";
        runAssertionSeven(expression);
    }

    private void runAssertionSeven(String expression)
    {
        EPStatement stmt = epService.getEPAdministrator().createEPL(expression);
        listener = new SupportUpdateListener();
        stmt.addListener(listener);
        String[] fields = "valh0,valh1,valh2".split(",");

        sendBeanInt("S00", 1, 1, 1);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"H01", "H01-H11", "H01-H11-H21"}});

        sendBeanInt("S01", 0, 1, 1);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, null);

        sendBeanInt("S02", 1, 1, 0);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, null);

        sendBeanInt("S03", 1, 1, 2);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"H01", "H01-H11", "H01-H11-H21"}, {"H01", "H01-H11", "H01-H11-H22"}});

        sendBeanInt("S04", 2, 2, 1);
        Object[][] result = new Object[][] {{"H01", "H01-H11", "H01-H11-H21"}, {"H02", "H02-H11", "H02-H11-H21"}, {"H01", "H01-H12", "H01-H12-H21"}, {"H02", "H02-H12", "H02-H12-H21"}};
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, result);
    }

    private void sendBeanInt(String id, int p00, int p01, int p02, int p03)
    {
        epService.getEPRuntime().sendEvent(new SupportBeanInt(id, p00, p01, p02, p03, -1, -1));
    }

    private void sendBeanInt(String id, int p00, int p01, int p02)
    {
        sendBeanInt(id, p00, p01, p02, -1);
    }

    private void sendBeanInt(String id, int p00, int p01)
    {
        sendBeanInt(id, p00, p01, -1, -1);
    }

    private void sendBeanInt(String id, int p00)
    {
        sendBeanInt(id, p00, -1, -1, -1);
    }
}
