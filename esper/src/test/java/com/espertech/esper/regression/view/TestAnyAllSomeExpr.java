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

package com.espertech.esper.regression.view;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EPStatementException;
import com.espertech.esper.client.soda.EPStatementObjectModel;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBeanArrayCollMap;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.Arrays;

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
                          "from SupportBean(string like \"E%\")";
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
                {false, true, true, true}    // 2, 1
                };

        for (int i = 0; i < testdata.length; i++)
        {
            SupportBean bean = new SupportBean("E", testdata[i][0]);
            bean.setIntBoxed(testdata[i][1]);
            epService.getEPRuntime().sendEvent(bean);
            //System.out.println("line " + i);
            ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, result[i]);
        }
        
        // test OM
        stmt.destroy();
        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(stmtText);
        assertEquals(stmtText.replace("<>", "!="), model.toEPL());
        stmt = epService.getEPAdministrator().create(model);
        stmt.addListener(listener);

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
                          "longBoxed = all ({1, 1}, intArr, longCol) as e, " +
                          "longBoxed != all ({1, 1}, intArr, longCol) as ne " +
                          "from ArrayBean";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        SupportBeanArrayCollMap arrayBean = new SupportBeanArrayCollMap(new int[] {1, 1});
        arrayBean.setLongCol(Arrays.asList(1L, 1L));
        arrayBean.setLongBoxed(1L);
        epService.getEPRuntime().sendEvent(arrayBean);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {true, false});

        arrayBean.setIntArr(new int[] {1, 1, 0});
        epService.getEPRuntime().sendEvent(arrayBean);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, false});

        arrayBean.setLongBoxed(2L);
        epService.getEPRuntime().sendEvent(arrayBean);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, true});
    }

    public void testEqualsAnyArray()
    {
        String[] fields = "e,ne".split(",");
        String stmtText = "select " +
                          "longBoxed = any ({1, 1}, intArr, longCol) as e, " +
                          "longBoxed != any ({1, 1}, intArr, longCol) as ne " +
                          "from ArrayBean";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        SupportBeanArrayCollMap arrayBean = new SupportBeanArrayCollMap(new int[] {1, 1});
        arrayBean.setLongCol(Arrays.asList(1L, 1L));
        arrayBean.setLongBoxed(1L);
        epService.getEPRuntime().sendEvent(arrayBean);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {true, false});

        arrayBean.setIntArr(new int[] {1, 1, 0});
        epService.getEPRuntime().sendEvent(arrayBean);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {true, true});

        arrayBean.setLongBoxed(2L);
        epService.getEPRuntime().sendEvent(arrayBean);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, true});
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

        // test OM
        stmt.destroy();
        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(stmtText);
        assertEquals(stmtText.replace("<>", "!="), model.toEPL());
        stmt = epService.getEPAdministrator().create(model);
        stmt.addListener(listener);

        arrayBean = new SupportBeanArrayCollMap(new int[] {1, 2});
        arrayBean.setIntCol(Arrays.asList(1, 2));
        arrayBean.setLongBoxed(3L);
        epService.getEPRuntime().sendEvent(arrayBean);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {true, true});
    }

    public void testRelationalOpNullOrNoRows()
    {
        // test array
        String[] fields = "vall,vany".split(",");
        String stmtText = "select " +
            "intBoxed >= all ({doubleBoxed, longBoxed}) as vall, " +
            "intBoxed >= any ({doubleBoxed, longBoxed}) as vany " +
            " from SupportBean(string like 'E%')";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        sendEvent("E3", null, null, null);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, null});
        sendEvent("E4", 1, null, null);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, null});

        sendEvent("E5", null, 1d, null);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, null});
        sendEvent("E6", 1, 1d, null);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, true});
        sendEvent("E7", 0, 1d, null);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, false});

        // test fields
        stmt.destroy();
        fields = "vall,vany".split(",");
        stmtText = "select " +
            "intBoxed >= all (doubleBoxed, longBoxed) as vall, " +
            "intBoxed >= any (doubleBoxed, longBoxed) as vany " +
            " from SupportBean(string like 'E%')";
        stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        sendEvent("E3", null, null, null);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, null});
        sendEvent("E4", 1, null, null);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, null});

        sendEvent("E5", null, 1d, null);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, null});
        sendEvent("E6", 1, 1d, null);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, true});
        sendEvent("E7", 0, 1d, null);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, false});
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

    public void testEqualsInNullOrNoRows()
    {
        // test fixed array case
        String[] fields = "eall,eany,neall,neany,isin".split(",");
        String stmtText = "select " +
            "intBoxed = all ({doubleBoxed, longBoxed}) as eall, " +
            "intBoxed = any ({doubleBoxed, longBoxed}) as eany, " +
            "intBoxed != all ({doubleBoxed, longBoxed}) as neall, " +
            "intBoxed != any ({doubleBoxed, longBoxed}) as neany, " +
            "intBoxed in ({doubleBoxed, longBoxed}) as isin " +
            " from SupportBean";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        sendEvent("E3", null, null, null);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, null, null, null, null});
        sendEvent("E4", 1, null, null);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, null, null, null, null});

        sendEvent("E5", null, null, 1L);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, null, null, null, null});
        sendEvent("E6", 1, null, 1L);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, true, false, null, true});
        sendEvent("E7", 0, null, 1L);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, null,  null, true, null});

        // test non-array case
        stmt.destroy();
        fields = "eall,eany,neall,neany,isin".split(",");
        stmtText = "select " +
            "intBoxed = all (doubleBoxed, longBoxed) as eall, " +
            "intBoxed = any (doubleBoxed, longBoxed) as eany, " +
            "intBoxed != all (doubleBoxed, longBoxed) as neall, " +
            "intBoxed != any (doubleBoxed, longBoxed) as neany, " +
            "intBoxed in (doubleBoxed, longBoxed) as isin " +
            " from SupportBean";
        stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        sendEvent("E3", null, null, null);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, null, null, null, null});
        sendEvent("E4", 1, null, null);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, null, null, null, null});

        sendEvent("E5", null, null, 1L);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, null, null, null, null});
        sendEvent("E6", 1, null, 1L);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, true, false, null, true});
        sendEvent("E7", 0, null, 1L);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, null,  null, true, null});
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
            assertEquals("Error starting statement: Collection or array comparison is not allowed for the IN, ANY, SOME or ALL keywords [select intArr = all (1, 2, 3) as r1 from ArrayBean]", ex.getMessage());
        }

        try
        {
            String stmtText = "select intArr > all (1, 2, 3) as r1 from ArrayBean";
            epService.getEPAdministrator().createEPL(stmtText);
            fail();
        }
        catch (EPStatementException ex)
        {
            assertEquals("Error starting statement: Collection or array comparison is not allowed for the IN, ANY, SOME or ALL keywords [select intArr > all (1, 2, 3) as r1 from ArrayBean]", ex.getMessage());
        }
    }

    public void sendEvent(String string, Integer intBoxed, Double doubleBoxed, Long longBoxed)
    {
        SupportBean bean = new SupportBean(string, -1);
        bean.setIntBoxed(intBoxed);
        bean.setDoubleBoxed(doubleBoxed);
        bean.setLongBoxed(longBoxed);
        epService.getEPRuntime().sendEvent(bean);
    }
}
