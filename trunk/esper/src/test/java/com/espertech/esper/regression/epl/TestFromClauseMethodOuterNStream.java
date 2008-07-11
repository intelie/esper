package com.espertech.esper.regression.epl;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
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

    /**
     * TODO, listener+iterator+reversedorder
     * Then; inner, full/left/right join
     */
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
