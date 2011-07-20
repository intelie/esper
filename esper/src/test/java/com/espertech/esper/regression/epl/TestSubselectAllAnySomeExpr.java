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

import com.espertech.esper.client.*;
import com.espertech.esper.client.soda.EPStatementObjectModel;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBeanArrayCollMap;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

public class TestSubselectAllAnySomeExpr extends TestCase
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

    public void testRelationalOpAll()
    {
        String[] fields = "g,ge,l,le".split(",");
        String stmtText = "select " +
            "intPrimitive > all (select intPrimitive from SupportBean(string like \"S%\").win:keepall()) as g, " +
            "intPrimitive >= all (select intPrimitive from SupportBean(string like \"S%\").win:keepall()) as ge, " +
            "intPrimitive < all (select intPrimitive from SupportBean(string like \"S%\").win:keepall()) as l, " +
            "intPrimitive <= all (select intPrimitive from SupportBean(string like \"S%\").win:keepall()) as le " +
            "from SupportBean(string like \"E%\")";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {true, true, true, true});

        epService.getEPRuntime().sendEvent(new SupportBean("S1", 1));

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 1));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, true, false, true});

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {true, true, false, false});

        epService.getEPRuntime().sendEvent(new SupportBean("S2", 2));

        epService.getEPRuntime().sendEvent(new SupportBean("E3", 3));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {true, true, false, false});

        epService.getEPRuntime().sendEvent(new SupportBean("E4", 2));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, true, false, false});

        epService.getEPRuntime().sendEvent(new SupportBean("E5", 1));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, false, false, true});

        epService.getEPRuntime().sendEvent(new SupportBean("E6", 0));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, false, true, true});

        try
        {
            epService.getEPAdministrator().createEPL("select intArr > all (select intPrimitive from SupportBean.win:keepall()) from ArrayBean");
            fail();
        }
        catch (EPStatementException ex)
        {
            assertEquals("Error starting statement: Collection or array comparison is not allowed for the IN, ANY, SOME or ALL keywords [select intArr > all (select intPrimitive from SupportBean.win:keepall()) from ArrayBean]", ex.getMessage());
        }
        
        // test OM
        stmt.destroy();
        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(stmtText);
        assertEquals(stmtText, model.toEPL());
        stmt = epService.getEPAdministrator().create(model);
        stmt.addListener(listener);
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {true, true, true, true});
    }

    public void testRelationalOpNullOrNoRows()
    {
        String[] fields = "vall,vany".split(",");
        String stmtText = "select " +
            "intBoxed >= all (select doubleBoxed from SupportBean(string like 'S%').win:keepall()) as vall, " +
            "intBoxed >= any (select doubleBoxed from SupportBean(string like 'S%').win:keepall()) as vany " +
            " from SupportBean(string like 'E%')";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        // subs is empty
        // select  null >= all (select val from subs), null >= any (select val from subs)
        sendEvent("E1", null, null);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {true, false});

        // select  1 >= all (select val from subs), 1 >= any (select val from subs)
        sendEvent("E2", 1, null);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {true, false});

        // subs is {null}
        sendEvent("S1", null, null);

        sendEvent("E3", null, null);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, null});
        sendEvent("E4", 1, null);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, null});

        // subs is {null, 1}
        sendEvent("S2", null, 1d);

        sendEvent("E5", null, null);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, null});
        sendEvent("E6", 1, null);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, true});

        sendEvent("E7", 0, null);
        EventBean event = listener.assertOneGetNewAndReset();
        ArrayAssertionUtil.assertProps(event, fields, new Object[] {false, false});
    }

    public void testRelationalOpSome()
    {
        String[] fields = "g,ge,l,le".split(",");
        String stmtText = "select " +
            "intPrimitive > any (select intPrimitive from SupportBean(string like 'S%').win:keepall()) as g, " +
            "intPrimitive >= any (select intPrimitive from SupportBean(string like 'S%').win:keepall()) as ge, " +
            "intPrimitive < any (select intPrimitive from SupportBean(string like 'S%').win:keepall()) as l, " +
            "intPrimitive <= any (select intPrimitive from SupportBean(string like 'S%').win:keepall()) as le " +
            " from SupportBean(string like 'E%')";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, false, false, false});

        epService.getEPRuntime().sendEvent(new SupportBean("S1", 1));

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 1));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, true, false, true});

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {true, true, false, false});

        epService.getEPRuntime().sendEvent(new SupportBean("E2a", 0));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, false, true, true});

        epService.getEPRuntime().sendEvent(new SupportBean("S2", 2));

        epService.getEPRuntime().sendEvent(new SupportBean("E3", 3));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {true, true, false, false});

        epService.getEPRuntime().sendEvent(new SupportBean("E4", 2));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {true, true, false, true});

        epService.getEPRuntime().sendEvent(new SupportBean("E5", 1));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, true, true, true});

        epService.getEPRuntime().sendEvent(new SupportBean("E6", 0));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, false, true, true});
    }

    public void testEqualsNotEqualsAll()
    {
        String[] fields = "eq,neq,sqlneq,nneq".split(",");
        String stmtText = "select " +
                          "intPrimitive = all (select intPrimitive from SupportBean(string like 'S%').win:keepall()) as eq, " +
                          "intPrimitive != all (select intPrimitive from SupportBean(string like 'S%').win:keepall()) as neq, " +
                          "intPrimitive <> all (select intPrimitive from SupportBean(string like 'S%').win:keepall()) as sqlneq, " +
                          "not intPrimitive = all (select intPrimitive from SupportBean(string like 'S%').win:keepall()) as nneq " +
                          " from SupportBean(string like 'E%')";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 10));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {true, true, true, false});

        epService.getEPRuntime().sendEvent(new SupportBean("S1", 11));

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 11));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {true, false, false, false});

        epService.getEPRuntime().sendEvent(new SupportBean("E3", 10));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, true, true, true});

        epService.getEPRuntime().sendEvent(new SupportBean("S1", 12));

        epService.getEPRuntime().sendEvent(new SupportBean("E4", 11));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, false, false, true});

        epService.getEPRuntime().sendEvent(new SupportBean("E5", 14));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, true, true, true});
    }

    // Test "value = SOME (subselect)" which is the same as "value IN (subselect)"
    public void testEqualsAnyOrSome()
    {
        String[] fields = "r1,r2,r3,r4".split(",");
        String stmtText = "select " +
                    "intPrimitive = SOME (select intPrimitive from SupportBean(string like 'S%').win:keepall()) as r1, " +
                    "intPrimitive = ANY (select intPrimitive from SupportBean(string like 'S%').win:keepall()) as r2, " +
                    "intPrimitive != SOME (select intPrimitive from SupportBean(string like 'S%').win:keepall()) as r3, " +
                    "intPrimitive <> ANY (select intPrimitive from SupportBean(string like 'S%').win:keepall()) as r4 " +
                    "from SupportBean(string like 'E%')";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 10));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, false, false, false});

        epService.getEPRuntime().sendEvent(new SupportBean("S1", 11));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 11));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {true, true, false, false});

        epService.getEPRuntime().sendEvent(new SupportBean("E3", 12));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, false, true, true});

        epService.getEPRuntime().sendEvent(new SupportBean("S2", 12));
        epService.getEPRuntime().sendEvent(new SupportBean("E4", 12));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {true, true, true, true});

        epService.getEPRuntime().sendEvent(new SupportBean("E5", 13));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, false, true, true});
    }

    public void testEqualsInNullOrNoRows()
    {
        String[] fields = "eall,eany,neall,neany,isin".split(",");
        String stmtText = "select " +
            "intBoxed = all (select doubleBoxed from SupportBean(string like 'S%').win:keepall()) as eall, " +
            "intBoxed = any (select doubleBoxed from SupportBean(string like 'S%').win:keepall()) as eany, " +
            "intBoxed != all (select doubleBoxed from SupportBean(string like 'S%').win:keepall()) as neall, " +
            "intBoxed != any (select doubleBoxed from SupportBean(string like 'S%').win:keepall()) as neany, " +
            "intBoxed in (select doubleBoxed from SupportBean(string like 'S%').win:keepall()) as isin " +
            " from SupportBean(string like 'E%')";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        // subs is empty
        // select  null = all (select val from subs), null = any (select val from subs), null != all (select val from subs), null != any (select val from subs), null in (select val from subs) 
        sendEvent("E1", null, null);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {true, false, true, false, false});

        // select  1 = all (select val from subs), 1 = any (select val from subs), 1 != all (select val from subs), 1 != any (select val from subs), 1 in (select val from subs)
        sendEvent("E2", 1, null);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {true, false, true, false, false});

        // subs is {null}
        sendEvent("S1", null, null);

        sendEvent("E3", null, null);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, null, null, null, null});
        sendEvent("E4", 1, null);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, null, null, null, null});

        // subs is {null, 1}
        sendEvent("S2", null, 1d);

        sendEvent("E5", null, null);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, null, null, null, null});
        sendEvent("E6", 1, null);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, true, false, null, true});
        sendEvent("E7", 0, null);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, null,  null, true, null});
    }

    public void testInvalid()
    {
        try
        {
            String stmtText = "select intArr = all (select intPrimitive from SupportBean.win:keepall()) as r1 from ArrayBean";
            epService.getEPAdministrator().createEPL(stmtText);
            fail();
        }
        catch (EPStatementException ex)
        {
            assertEquals("Error starting statement: Collection or array comparison is not allowed for the IN, ANY, SOME or ALL keywords [select intArr = all (select intPrimitive from SupportBean.win:keepall()) as r1 from ArrayBean]", ex.getMessage());
        }
    }

    private void sendEvent(String string, Integer intBoxed, Double doubleBoxed)
    {
        SupportBean bean = new SupportBean(string, -1);
        bean.setIntBoxed(intBoxed);
        bean.setDoubleBoxed(doubleBoxed);
        epService.getEPRuntime().sendEvent(bean);
    }
}
