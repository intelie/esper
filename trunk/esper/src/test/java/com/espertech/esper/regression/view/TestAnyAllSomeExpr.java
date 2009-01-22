package com.espertech.esper.regression.view;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EPStatementException;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBeanArrayCollMap;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.Arrays;
import java.util.HashMap;

public class TestAnyAllSomeExpr extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        listener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();

        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().getConfiguration().addEventType("ArrayBean", SupportBeanArrayCollMap.class);
    }

    public void testEqualsAll()
    {
        String[] fields = "eq,neq,sqlneq,nneq".split(",");
        String stmtText = "select " +
                          "intPrimitive = all (1, intBoxed) as eq, " +
                          "intPrimitive != all (1, intBoxed) as neq, " +
                          "intPrimitive <> all (1, intBoxed) as sqlneq, " +
                          "not intPrimitive = all (1, intBoxed) as nneq " +
                          " from SupportBean(string like 'E%')";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        // in the format intPrimitive, intBoxed
        int[][] testdata = {
                {1, 1},
                {1, 2},
                {2, 2},
                {2, 1},
        };

        Object[][] result = {
                {true, false, false, false}, // 1, 1
                {false, false, false, true}, // 1, 2
                {false, false, false, true}, // 2, 2
                {false, true, true, true} // 2, 1
                };

        for (int i = 0; i < testdata.length; i++)
        {
            SupportBean bean = new SupportBean("E", testdata[i][0]);
            bean.setIntBoxed(testdata[i][1]);
            epService.getEPRuntime().sendEvent(bean);
            //System.out.println("line " + i);
            ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, result[i]);
        }
    }

    public void testEqualsAllArray()
    {
        String[] fields = "e,ne".split(",");
        String stmtText = "select " +
                          "longBoxed = all ({1, 1}, intArr, intCol) as e, " +
                          "longBoxed != all ({1, 1}, intArr, intCol) as ne " +
                          "from ArrayBean";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        SupportBeanArrayCollMap arrayBean = new SupportBeanArrayCollMap(new int[] {1, 1});
        arrayBean.setIntCol(Arrays.asList(1, 1));
        arrayBean.setLongBoxed(1L);
        epService.getEPRuntime().sendEvent(arrayBean);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {true, false});
    }

    public void testRelationalOpAllArray()
    {
        String[] fields = "g,ge".split(",");
        String stmtText = "select " +
                          "longBoxed > all ({1, 2}, intArr, intCol) as g, " +
                          "longBoxed >= all ({1, 2}, intArr, intCol) as ge " +
                          "from ArrayBean";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        SupportBeanArrayCollMap arrayBean = new SupportBeanArrayCollMap(new int[] {1, 2});
        arrayBean.setIntCol(Arrays.asList(1, 2));
        arrayBean.setLongBoxed(3L);
        epService.getEPRuntime().sendEvent(arrayBean);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {true, true});

        arrayBean.setLongBoxed(2L);
        epService.getEPRuntime().sendEvent(arrayBean);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, true});

        arrayBean = new SupportBeanArrayCollMap(new int[] {1, 3});
        arrayBean.setIntCol(Arrays.asList(1, 2));
        arrayBean.setLongBoxed(3L);
        epService.getEPRuntime().sendEvent(arrayBean);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, true});

        arrayBean = new SupportBeanArrayCollMap(new int[] {1, 2});
        arrayBean.setIntCol(Arrays.asList(1, 3));
        arrayBean.setLongBoxed(3L);
        epService.getEPRuntime().sendEvent(arrayBean);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, true});
    }

    public void testRelationalOpAnyArray()
    {
        String[] fields = "g,ge".split(",");
        String stmtText = "select " +
                          "longBoxed > any ({1, 2}, intArr, intCol) as g, " +
                          "longBoxed >= any ({1, 2}, intArr, intCol) as ge " +
                          "from ArrayBean";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        SupportBeanArrayCollMap arrayBean = new SupportBeanArrayCollMap(new int[] {1, 2});
        arrayBean.setIntCol(Arrays.asList(1, 2));
        arrayBean.setLongBoxed(1L);
        epService.getEPRuntime().sendEvent(arrayBean);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, true});

        arrayBean.setLongBoxed(2L);
        epService.getEPRuntime().sendEvent(arrayBean);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {true, true});

        arrayBean = new SupportBeanArrayCollMap(new int[] {2, 2});
        arrayBean.setIntCol(Arrays.asList(2, 1));
        arrayBean.setLongBoxed(1L);
        epService.getEPRuntime().sendEvent(arrayBean);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, true});

        arrayBean = new SupportBeanArrayCollMap(new int[] {1, 1});
        arrayBean.setIntCol(Arrays.asList(1, 1));
        arrayBean.setLongBoxed(0L);
        epService.getEPRuntime().sendEvent(arrayBean);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, false});
    }

    public void testEqualsAny()
    {
        String[] fields = "eq,neq,sqlneq,nneq".split(",");
        String stmtText = "select " +
                          "intPrimitive = any (1, intBoxed) as eq, " +
                          "intPrimitive != any (1, intBoxed) as neq, " +
                          "intPrimitive <> any (1, intBoxed) as sqlneq, " +
                          "not intPrimitive = any (1, intBoxed) as nneq " +
                          " from SupportBean(string like 'E%')";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        // in the format intPrimitive, intBoxed
        int[][] testdata = {
                {1, 1},
                {1, 2},
                {2, 2},
                {2, 1},
        };

        Object[][] result = {
                {true, false, false, false}, // 1, 1
                {true, true, true, false}, // 1, 2
                {true, true, true, false}, // 2, 2
                {false, true, true, true} // 2, 1
                };

        for (int i = 0; i < testdata.length; i++)
        {
            SupportBean bean = new SupportBean("E", testdata[i][0]);
            bean.setIntBoxed(testdata[i][1]);
            epService.getEPRuntime().sendEvent(bean);
            //System.out.println("line " + i);
            ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, result[i]);
        }
    }

    public void testRelationalOpAll()
    {
        String[] fields = "g,ge,l,le".split(",");
        String stmtText = "select " +
            "intPrimitive > all (1, 3, 4) as g, " +
            "intPrimitive >= all (1, 3, 4) as ge, " +
            "intPrimitive < all (1, 3, 4) as l, " +
            "intPrimitive <= all (1, 3, 4) as le " +
            " from SupportBean(string like 'E%')";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        Object[][] result = {
                {false, false, true, true},
                {false, false, false, true},
                {false, false, false, false},
                {false, false, false, false},
                {false, true, false, false},
                {true, true, false, false}
                };
        
        for (int i = 0; i < 6; i++)
        {
            epService.getEPRuntime().sendEvent(new SupportBean("E1", i));
            //System.out.println("line " + i);
            ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, result[i]);
        }
    }

    public void testRelationalOpAny()
    {
        String[] fields = "g,ge,l,le".split(",");
        String stmtText = "select " +
            "intPrimitive > any (1, 3, 4) as g, " +
            "intPrimitive >= some (1, 3, 4) as ge, " +
            "intPrimitive < any (1, 3, 4) as l, " +
            "intPrimitive <= some (1, 3, 4) as le " +
            " from SupportBean(string like 'E%')";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        Object[][] result = {
                {false, false, true, true},
                {false, true, true, true},
                {true, true, true, true},
                {true, true, true, true},
                {true, true, false, true},
                {true, true, false, false}
                };

        for (int i = 0; i < 6; i++)
        {
            epService.getEPRuntime().sendEvent(new SupportBean("E1", i));
            //System.out.println("line " + i);
            ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, result[i]);
        }
    }

    public void testInvalid()
    {
        try
        {
            String stmtText = "select intArr = all (1, 2, 3) as r1 from ArrayBean";
            epService.getEPAdministrator().createEPL(stmtText);
            fail();
        }
        catch (EPStatementException ex)
        {
            assertEquals("Error starting view: Collection or array comparison is not allowed for the IN, ANY, SOME or ALL keywords [select intArr = all (1, 2, 3) as r1 from ArrayBean]", ex.getMessage());
        }

        try
        {
            String stmtText = "select intArr > all (1, 2, 3) as r1 from ArrayBean";
            epService.getEPAdministrator().createEPL(stmtText);
            fail();
        }
        catch (EPStatementException ex)
        {
            assertEquals("Error starting view: Collection or array comparison is not allowed for the IN, ANY, SOME or ALL keywords [select intArr > all (1, 2, 3) as r1 from ArrayBean]", ex.getMessage());
        }
    }
}
