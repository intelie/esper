package com.espertech.esper.regression.epl;

import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportBeanInt;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.epl.SupportJoinMethods;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

public class TestFromClauseMethodOuterNStream extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventTypeAliasSimpleName(SupportBeanInt.class);
        config.addEventTypeAliasSimpleName(SupportBean.class);
        config.addImport(SupportJoinMethods.class.getName());
        config.addVariable("var1", Integer.class, 0);
        config.addVariable("var2", Integer.class, 0);
        config.addVariable("var3", Integer.class, 0);
        config.addVariable("var4", Integer.class, 0);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();

        epService.getEPAdministrator().createEPL("on SupportBeanInt(id like 'V%') set var1=p00, var2=p01");
    }

    public void test1Stream2HistStarSubordinateLeftRight()
    {
        String expression;

        expression = "select s0.id as id, h0.val as valh0, h1.val as valh1 " +
                   "from SupportBeanInt(id like 'E%').win:keepall() as s0 " +
                   " left outer join " +
                   "method:SupportJoinMethods.fetchValMultiRow('H0', p00, p04) as h0 " +
                   " on s0.p02 = h0.index " +
                   " left outer join " +
                   "method:SupportJoinMethods.fetchValMultiRow('H1', p01, p05) as h1 " +
                   " on s0.p03 = h1.index" +
                   " order by valh0, valh1";
        runAssertionOne(expression);

        expression = "select s0.id as id, h0.val as valh0, h1.val as valh1 from " +
                   "method:SupportJoinMethods.fetchValMultiRow('H1', p01, p05) as h1 " +
                   " right outer join " +
                   "SupportBeanInt(id like 'E%').win:keepall() as s0 " +
                   " on s0.p03 = h1.index " +
                   " left outer join " +
                   "method:SupportJoinMethods.fetchValMultiRow('H0', p00, p04) as h0 " +
                   " on s0.p02 = h0.index" +
                   " order by valh0, valh1";
        runAssertionOne(expression);

        expression = "select s0.id as id, h0.val as valh0, h1.val as valh1 from " +
                   "method:SupportJoinMethods.fetchValMultiRow('H0', p00, p04) as h0 " +
                   " right outer join " +
                   "SupportBeanInt(id like 'E%').win:keepall() as s0 " +
                   " on s0.p02 = h0.index" +
                   " left outer join " +
                   "method:SupportJoinMethods.fetchValMultiRow('H1', p01, p05) as h1 " +
                   " on s0.p03 = h1.index " +
                   " order by valh0, valh1";
        runAssertionOne(expression);

        expression = "select s0.id as id, h0.val as valh0, h1.val as valh1 from " +
                   "method:SupportJoinMethods.fetchValMultiRow('H0', p00, p04) as h0 " +
                   " full outer join " +
                   "SupportBeanInt(id like 'E%').win:keepall() as s0 " +
                   " on s0.p02 = h0.index" +
                   " full outer join " +
                   "method:SupportJoinMethods.fetchValMultiRow('H1', p01, p05) as h1 " +
                   " on s0.p03 = h1.index " +
                   " order by valh0, valh1";
        runAssertionOne(expression);
    }

    public void test1Stream2HistStarSubordinateInner()
    {
        String expression;

        expression = "select s0.id as id, h0.val as valh0, h1.val as valh1 " +
                   "from SupportBeanInt(id like 'E%').win:keepall() as s0 " +
                   " inner join " +
                   "method:SupportJoinMethods.fetchValMultiRow('H0', p00, p04) as h0 " +
                   " on s0.p02 = h0.index " +
                   " inner join " +
                   "method:SupportJoinMethods.fetchValMultiRow('H1', p01, p05) as h1 " +
                   " on s0.p03 = h1.index" +
                   " order by valh0, valh1";
        runAssertionTwo(expression);

        expression = "select s0.id as id, h0.val as valh0, h1.val as valh1 from " +
                    "method:SupportJoinMethods.fetchValMultiRow('H0', p00, p04) as h0 " +
                    " inner join " +
                    "SupportBeanInt(id like 'E%').win:keepall() as s0 " +
                    " on s0.p02 = h0.index " +
                    " inner join " +
                    "method:SupportJoinMethods.fetchValMultiRow('H1', p01, p05) as h1 " +
                    " on s0.p03 = h1.index" +
                    " order by valh0, valh1";
        runAssertionTwo(expression);
    }

    public void test1Stream2HistForwardSubordinate()
    {
        String expression;

        expression = "select s0.id as id, h0.val as valh0, h1.val as valh1 " +
                   "from SupportBeanInt(id like 'E%').std:lastevent() as s0 " +
                   " left outer join " +
                   "method:SupportJoinMethods.fetchVal('H0', p00) as h0 " +
                   " on s0.p02 = h0.index " +
                   " left outer join " +
                   "method:SupportJoinMethods.fetchVal('H1', p01) as h1 " +
                   " on h0.index = h1.index" +
                   " order by valh0, valh1";
        runAssertionThree(expression);
    }

    private void runAssertionThree(String expression)
    {
        EPStatement stmt = epService.getEPAdministrator().createEPL(expression);
        listener = new SupportUpdateListener();
        stmt.addListener(listener);

        String[] fields = "id,valh0,valh1".split(",");
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, null);

        sendBeanInt("E1", 0, 0, 1);
        Object[][] result = new Object[][] {{"E1", null, null}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, result);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, result);

        sendBeanInt("E2", 0, 1, 1);
        result = new Object[][] {{"E2", null, null}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, result);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, result);

        sendBeanInt("E3", 1, 0, 1);
        result = new Object[][] {{"E3", "H01", null}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, result);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, result);

        sendBeanInt("E4", 1, 1, 1);
        result = new Object[][] {{"E4", "H01", "H11"}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, result);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, result);

        sendBeanInt("E5", 4, 4, 2);
        result = new Object[][] {{"E5", "H02", "H12"}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, result);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, result);
    }

    public void test1Stream3HistForwardSubordinate()
    {
        String expression;

        expression = "select s0.id as id, h0.val as valh0, h1.val as valh1, h2.val as valh2 " +
                    "from SupportBeanInt(id like 'E%').std:lastevent() as s0 " +
                    " left outer join " +
                    "method:SupportJoinMethods.fetchVal('H0', p00) as h0 " +
                    " on s0.p03 = h0.index " +
                    " left outer join " +
                    "method:SupportJoinMethods.fetchVal('H1', p01) as h1 " +
                    " on h0.index = h1.index" +
                    " left outer join " +
                    "method:SupportJoinMethods.fetchVal('H2', p02) as h2 " +
                    " on h1.index = h2.index" +
                    " order by valh0, valh1, valh2";
        runAssertionFour(expression);

        expression = "select s0.id as id, h0.val as valh0, h1.val as valh1, h2.val as valh2 from " +
                    "method:SupportJoinMethods.fetchVal('H0', p00) as h0 " +
                    " right outer join " +
                    "SupportBeanInt(id like 'E%').std:lastevent() as s0 " +
                    " on s0.p03 = h0.index " +
                    " left outer join " +
                    "method:SupportJoinMethods.fetchVal('H1', p01) as h1 " +
                    " on h0.index = h1.index" +
                    " full outer join " +
                    "method:SupportJoinMethods.fetchVal('H2', p02) as h2 " +
                    " on h1.index = h2.index" +
                    " order by valh0, valh1, valh2";
        runAssertionFour(expression);
    }

    private void runAssertionFour(String expression)
    {
        EPStatement stmt = epService.getEPAdministrator().createEPL(expression);
        listener = new SupportUpdateListener();
        stmt.addListener(listener);

        String[] fields = "id,valh0,valh1,valh2".split(",");
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, null);

        sendBeanInt("E1", 0, 0, 0, 1);
        Object[][] result = new Object[][] {{"E1", null, null, null}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, result);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, result);

        sendBeanInt("E2", 0, 1, 1, 1);
        result = new Object[][] {{"E2", null, null, null}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, result);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, result);

        sendBeanInt("E3", 1, 1, 1, 1);
        result = new Object[][] {{"E3", "H01", "H11", "H21"}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, result);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, result);

        sendBeanInt("E4", 1, 0, 1, 1);
        result = new Object[][] {{"E4", "H01", null, null}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, result);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, result);

        sendBeanInt("E5", 4, 4, 4, 2);
        result = new Object[][] {{"E5", "H02", "H12", "H22"}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, result);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, result);
    }

    public void test1Stream3HistForwardSubordinateChain()
    {
        String expression;

        expression = "select s0.id as id, h0.val as valh0, h1.val as valh1, h2.val as valh2 " +
                    "from SupportBeanInt(id like 'E%').std:lastevent() as s0 " +
                    " left outer join " +
                    "method:SupportJoinMethods.fetchVal(s0.id || '-H0', p00) as h0 " +
                    " on s0.p03 = h0.index " +
                    " left outer join " +
                    "method:SupportJoinMethods.fetchVal(h0.val || '-H1', p01) as h1 " +
                    " on h0.index = h1.index" +
                    " left outer join " +
                    "method:SupportJoinMethods.fetchVal(h1.val || '-H2', p02) as h2 " +
                    " on h1.index = h2.index" +
                    " order by valh0, valh1, valh2";
        runAssertionFive(expression);
    }

    private void runAssertionFive(String expression)
    {
        EPStatement stmt = epService.getEPAdministrator().createEPL(expression);
        listener = new SupportUpdateListener();
        stmt.addListener(listener);

        String[] fields = "id,valh0,valh1,valh2".split(",");
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, null);

        sendBeanInt("E1", 0, 0, 0, 1);
        Object[][] result = new Object[][] {{"E1", null, null, null}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, result);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, result);

        sendBeanInt("E2", 0, 1, 1, 1);
        result = new Object[][] {{"E2", null, null, null}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, result);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, result);

        sendBeanInt("E3", 1, 1, 1, 1);
        result = new Object[][] {{"E3", "E3-H01", "E3-H01-H11", "E3-H01-H11-H21"}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, result);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, result);

        sendBeanInt("E4", 1, 0, 1, 1);
        result = new Object[][] {{"E4", "E4-H01", null, null}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, result);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, result);

        sendBeanInt("E5", 4, 4, 4, 2);
        result = new Object[][] {{"E5", "E5-H02", "E5-H02-H12", "E5-H02-H12-H22"}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, result);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, result);
    }

    private void runAssertionOne(String expression)
    {
        EPStatement stmt = epService.getEPAdministrator().createEPL(expression);
        listener = new SupportUpdateListener();
        stmt.addListener(listener);

        String[] fields = "id,valh0,valh1".split(",");
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, null);

        sendBeanInt("E1", 0, 0, 0, 0,  1, 1);
        Object[][] resultOne = new Object[][] {{"E1", null, null}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, resultOne);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, resultOne);

        sendBeanInt("E2", 1, 1, 1, 1,  1, 1);
        Object[][] resultTwo = new Object[][] {{"E2", "H01_0", "H11_0"}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, resultTwo);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, ArrayAssertionUtil.addArray(resultOne, resultTwo));

        sendBeanInt("E3", 5, 5, 3, 4,  1, 1);
        Object[][] resultThree = new Object[][] {{"E3", "H03_0", "H14_0"}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, resultThree);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, ArrayAssertionUtil.addArray(resultOne, resultTwo, resultThree));

        sendBeanInt("E4", 0, 5, 3, 4,  1, 1);
        Object[][] resultFour = new Object[][] {{"E4", null, "H14_0"}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, resultFour);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, ArrayAssertionUtil.addArray(resultOne, resultTwo, resultThree, resultFour));

        sendBeanInt("E5", 2, 0, 2, 1,  1, 1);
        Object[][] resultFive = new Object[][] {{"E5", "H02_0", null}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, resultFive);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, ArrayAssertionUtil.addArray(resultOne, resultTwo, resultThree, resultFour, resultFive));

        // set 2 rows for H0
        sendBeanInt("E6", 2, 2, 2, 2,  2, 1);
        Object[][] resultSix = new Object[][] {{"E6", "H02_0", "H12_0"}, {"E6", "H02_1", "H12_0"}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, resultSix);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, ArrayAssertionUtil.addArray(resultOne, resultTwo, resultThree, resultFour, resultFive, resultSix));

        sendBeanInt("E7", 10, 10, 4, 5,  1, 2);
        Object[][] resultSeven = new Object[][] {{"E7", "H04_0", "H15_0"}, {"E7", "H04_0", "H15_1"}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, resultSeven);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, ArrayAssertionUtil.addArray(resultOne, resultTwo, resultThree, resultFour, resultFive, resultSix, resultSeven));
    }

    private void runAssertionTwo(String expression)
    {
        EPStatement stmt = epService.getEPAdministrator().createEPL(expression);
        listener = new SupportUpdateListener();
        stmt.addListener(listener);

        String[] fields = "id,valh0,valh1".split(",");
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, null);

        sendBeanInt("E1", 0, 0, 0, 0,  1, 1);
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, null);

        sendBeanInt("E2", 1, 1, 1, 1,  1, 1);
        Object[][] resultTwo = new Object[][] {{"E2", "H01_0", "H11_0"}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, resultTwo);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, ArrayAssertionUtil.addArray(resultTwo));

        sendBeanInt("E3", 5, 5, 3, 4,  1, 1);
        Object[][] resultThree = new Object[][] {{"E3", "H03_0", "H14_0"}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, resultThree);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, ArrayAssertionUtil.addArray(resultTwo, resultThree));

        sendBeanInt("E4", 0, 5, 3, 4,  1, 1);
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, ArrayAssertionUtil.addArray(resultTwo, resultThree));

        sendBeanInt("E5", 2, 0, 2, 1,  1, 1);
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, ArrayAssertionUtil.addArray(resultTwo, resultThree));

        // set 2 rows for H0
        sendBeanInt("E6", 2, 2, 2, 2,  2, 1);
        Object[][] resultSix = new Object[][] {{"E6", "H02_0", "H12_0"}, {"E6", "H02_1", "H12_0"}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, resultSix);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, ArrayAssertionUtil.addArray(resultTwo, resultThree, resultSix));

        sendBeanInt("E7", 10, 10, 4, 5,  1, 2);
        Object[][] resultSeven = new Object[][] {{"E7", "H04_0", "H15_0"}, {"E7", "H04_0", "H15_1"}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, resultSeven);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, ArrayAssertionUtil.addArray(resultTwo, resultThree, resultSix, resultSeven));
    }

    public void testInvalid()
    {
        String expression;
        // Invalid dependency order: a historical depends on it's own outer join child or descendant
        //              S0
        //      H0  (depends H1)
        //      H1
        expression = "select * from " +
                    "SupportBeanInt.std:lastevent() as s0 " +
                    " left outer join " +
                    "method:SupportJoinMethods.fetchVal(h1.val, 1) as h0 " +
                    " on s0.p00 = h0.index " +
                    " left outer join " +
                    "method:SupportJoinMethods.fetchVal('H1', 1) as h1 " +
                    " on h0.index = h1.index";
        tryInvalid(expression, "Error starting view: Historical stream 1 parameter dependency originating in stream 2 cannot or may not be satisfied by the join [select * from SupportBeanInt.std:lastevent() as s0  left outer join method:SupportJoinMethods.fetchVal(h1.val, 1) as h0  on s0.p00 = h0.index  left outer join method:SupportJoinMethods.fetchVal('H1', 1) as h1  on h0.index = h1.index]");

        // Optimization conflict : required streams are always executed before optional streams
        //              S0
        //  full outer join H0 to S0
        //  left outer join H1 to S0 (H1 depends on H0)
        expression = "select * from " +
                    "SupportBeanInt.std:lastevent() as s0 " +
                    " full outer join " +
                    "method:SupportJoinMethods.fetchVal('x', 1) as h0 " +
                    " on s0.p00 = h0.index " +
                    " left outer join " +
                    "method:SupportJoinMethods.fetchVal(h0.val, 1) as h1 " +
                    " on s0.p00 = h1.index";
        tryInvalid(expression, "Error starting view: Historical stream 2 parameter dependency originating in stream 1 cannot or may not be satisfied by the join [select * from SupportBeanInt.std:lastevent() as s0  full outer join method:SupportJoinMethods.fetchVal('x', 1) as h0  on s0.p00 = h0.index  left outer join method:SupportJoinMethods.fetchVal(h0.val, 1) as h1  on s0.p00 = h1.index]");
    }

    public void test2Stream1HistStarSubordinateLeftRight()
    {
        String expression;

        //   S1 -> S0 -> H0 
        expression = "select s0.id as s0id, s1.id as s1id, h0.val as valh0 from " +
                   "SupportBeanInt(id like 'E%').win:keepall() as s0 " +
                   " left outer join " +
                   "method:SupportJoinMethods.fetchVal(s0.id || 'H0', s0.p00) as h0 " +
                   " on s0.p01 = h0.index " +
                   " right outer join " +
                   "SupportBeanInt(id like 'F%').win:keepall() as s1 " +
                   " on s1.p01 = s0.p01";
        runAssertionSix(expression);

        expression = "select s0.id as s0id, s1.id as s1id, h0.val as valh0 from " +
                    "SupportBeanInt(id like 'F%').win:keepall() as s1 " +
                    " left outer join " +
                    "SupportBeanInt(id like 'E%').win:keepall() as s0 " +
                    " on s1.p01 = s0.p01" +
                    " left outer join " +
                    "method:SupportJoinMethods.fetchVal(s0.id || 'H0', s0.p00) as h0 " +
                    " on s0.p01 = h0.index ";
        runAssertionSix(expression);

        expression = "select s0.id as s0id, s1.id as s1id, h0.val as valh0 from " +
                    "method:SupportJoinMethods.fetchVal(s0.id || 'H0', s0.p00) as h0 " +
                    " right outer join " +
                    "SupportBeanInt(id like 'E%').win:keepall() as s0 " +
                    " on s0.p01 = h0.index " +
                    " right outer join " +
                    "SupportBeanInt(id like 'F%').win:keepall() as s1 " +
                    " on s1.p01 = s0.p01";
        runAssertionSix(expression);
    }

    private void runAssertionSix(String expression)
    {
        EPStatement stmt = epService.getEPAdministrator().createEPL(expression);
        listener = new SupportUpdateListener();
        stmt.addListener(listener);

        String[] fields = "s0id,s1id,valh0".split(",");
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, null);

        sendBeanInt("E1", 1, 1);
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, null);

        sendBeanInt("F1", 1, 1);
        Object[][] resultOne = new Object[][] {{"E1", "F1", "E1H01"}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, resultOne);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, resultOne);

        sendBeanInt("F2", 2, 2);
        Object[][] resultTwo = new Object[][] {{null, "F2", null}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, resultTwo);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, ArrayAssertionUtil.addArray(resultOne, resultTwo));

        sendBeanInt("E2", 2, 2);
        Object[][] resultThree = new Object[][] {{"E2", "F2", "E2H02"}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, resultThree);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, ArrayAssertionUtil.addArray(resultOne, resultThree));

        sendBeanInt("F3", 3, 3);
        Object[][] resultFour = new Object[][] {{null, "F3", null}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, resultFour);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, ArrayAssertionUtil.addArray(resultOne, resultThree, resultFour));

        sendBeanInt("E3", 0, 3);
        Object[][] resultFive = new Object[][] {{"E3", "F3", null}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, resultFive);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, ArrayAssertionUtil.addArray(resultOne, resultThree, resultFive));
    }

    public void test1Stream2HistStarNoSubordinateLeftRight()
    {
        String expression;

        expression = "select s0.id as s0id, h0.val as valh0, h1.val as valh1 from " +
                   "SupportBeanInt(id like 'E%').win:keepall() as s0 " +
                   " right outer join " +
                   "method:SupportJoinMethods.fetchVal('H0', 2) as h0 " +
                   " on s0.p00 = h0.index " +
                   " right outer join " +
                   "method:SupportJoinMethods.fetchVal('H1', 2) as h1 " +
                   " on s0.p00 = h1.index";
        runAssertionSeven(expression);

        expression = "select s0.id as s0id, h0.val as valh0, h1.val as valh1 from " +
                "method:SupportJoinMethods.fetchVal('H1', 2) as h1 " +
                " left outer join " +
                "SupportBeanInt(id like 'E%').win:keepall() as s0 " +
                " on s0.p00 = h1.index" +
                " right outer join " +
                "method:SupportJoinMethods.fetchVal('H0', 2) as h0 " +
                " on s0.p00 = h0.index ";
        runAssertionSeven(expression);
    }

    private void runAssertionSeven(String expression)
    {
        EPStatement stmt = epService.getEPAdministrator().createEPL(expression);
        listener = new SupportUpdateListener();
        stmt.addListener(listener);

        String[] fields = "s0id,valh0,valh1".split(",");
        Object[][] resultOne = new Object[][] {{null, "H01", null}, {null, "H02", null}, {null, null, "H11"}, {null, null, "H12"}};
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, resultOne);

        sendBeanInt("E1", 0);
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, resultOne);

        sendBeanInt("E2", 2);
        Object[][] resultTwo = new Object[][] {{"E2", "H02", "H12"}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, resultTwo);
        Object[][] resultIt = new Object[][] {{null, "H01", null}, {null, null, "H11"}, {"E2", "H02", "H12"}};
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, resultIt);

        sendBeanInt("E3", 1);
        resultTwo = new Object[][] {{"E3", "H01", "H11"}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, resultTwo);
        resultIt = new Object[][] {{"E3", "H01", "H11"}, {"E2", "H02", "H12"}};
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, resultIt);

        sendBeanInt("E4", 1);
        resultTwo = new Object[][] {{"E4", "H01", "H11"}};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, resultTwo);
        resultIt = new Object[][] {{"E3", "H01", "H11"}, {"E4", "H01", "H11"}, {"E2", "H02", "H12"}};
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, resultIt);
    }

    private void tryInvalid(String expression, String text)
    {
        try
        {
            epService.getEPAdministrator().createEPL(expression);
            fail();
        }
        catch (EPStatementException ex)
        {
            assertEquals(ex.getMessage(), text);
        }
    }
    
    private void sendBeanInt(String id, int p00, int p01, int p02, int p03, int p04, int p05)
    {
        epService.getEPRuntime().sendEvent(new SupportBeanInt(id, p00, p01, p02, p03, p04, p05));
    }

    private void sendBeanInt(String id, int p00, int p01, int p02, int p03)
    {
        sendBeanInt(id, p00, p01, p02, p03, -1, -1);
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
