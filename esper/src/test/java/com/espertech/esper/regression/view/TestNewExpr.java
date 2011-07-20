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

import com.espertech.esper.client.*;
import com.espertech.esper.client.soda.EPStatementObjectModel;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;

public class TestNewExpr extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        listener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
    }

    public void testNewAlone() {
        String epl = "select new { string = 'x' || string || 'x', intPrimitive = intPrimitive + 2} as val0 from SupportBean as sb";

        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        assertEquals(Map.class, stmt.getEventType().getPropertyType("val0"));
        FragmentEventType fragType = stmt.getEventType().getFragmentType("val0");
        assertFalse(fragType.isIndexed());
        assertFalse(fragType.isNative());
        assertEquals(String.class, fragType.getFragmentType().getPropertyType("string"));
        assertEquals(Integer.class, fragType.getFragmentType().getPropertyType("intPrimitive"));

        String[] fieldsInner = "string,intPrimitive".split(",");
        epService.getEPRuntime().sendEvent(new SupportBean("E1", -5));
        ArrayAssertionUtil.assertPropsMap((Map) listener.assertOneGetNewAndReset().get("val0"), fieldsInner, new Object[] {"xE1x", -3});
    }

    public void testDefaultColumnsAndSODA()
    {
        String epl = "select " +
                "case string" +
                " when \"A\" then new { string = \"Q\", intPrimitive, col2 = string || \"A\" }" +
                " when \"B\" then new { string, intPrimitive = 10, col2 = string || \"B\" } " +
                "end as val0 from SupportBean as sb";

        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);
        runAssertionDefault(stmt);

        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(epl);
        assertEquals(epl, model.toEPL());
        stmt = epService.getEPAdministrator().create(model);
        stmt.addListener(listener);
        runAssertionDefault(stmt);

        // test to-expression string
        epl = "select " +
                "case string" +
                " when \"A\" then new { string = \"Q\", intPrimitive, col2 = string || \"A\" }" +
                " when \"B\" then new { string, intPrimitive = 10, col2 = string || \"B\" } " +
                "end from SupportBean as sb";

        stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);
        assertEquals("case string when \"A\" then new { string = \"Q\", intPrimitive, col2 = (string||\"A\") } when \"B\" then new { string, intPrimitive = 10, col2 = (string||\"B\") } end", stmt.getEventType().getPropertyNames()[0]);
    }

    private void runAssertionDefault(EPStatement stmt) {

        assertEquals(Map.class, stmt.getEventType().getPropertyType("val0"));
        FragmentEventType fragType = stmt.getEventType().getFragmentType("val0");
        assertFalse(fragType.isIndexed());
        assertFalse(fragType.isNative());
        assertEquals(String.class, fragType.getFragmentType().getPropertyType("string"));
        assertEquals(Integer.class, fragType.getFragmentType().getPropertyType("intPrimitive"));
        assertEquals(String.class, fragType.getFragmentType().getPropertyType("col2"));

        String[] fieldsInner = "string,intPrimitive,col2".split(",");
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        ArrayAssertionUtil.assertPropsMap((Map) listener.assertOneGetNewAndReset().get("val0"), fieldsInner, new Object[] {null, null, null});

        epService.getEPRuntime().sendEvent(new SupportBean("A", 2));
        ArrayAssertionUtil.assertPropsMap((Map) listener.assertOneGetNewAndReset().get("val0"), fieldsInner, new Object[] {"Q", 2, "AA"});

        epService.getEPRuntime().sendEvent(new SupportBean("B", 3));
        ArrayAssertionUtil.assertPropsMap((Map) listener.assertOneGetNewAndReset().get("val0"), fieldsInner, new Object[] {"B", 10, "BB"});

        stmt.destroy();
    }

    public void testNewWithCase()
    {
        String epl = "select " +
                "case " +
                "  when string = 'A' then new { col1 = 'X', col2 = 10 } " +
                "  when string = 'B' then new { col1 = 'Y', col2 = 20 } " +
                "  when string = 'C' then new { col1 = null, col2 = null } " +
                "  else new { col1 = 'Z', col2 = 30 } " +
                "end as val0 from SupportBean sb";
        runAssertion(epl);

        epl = "select " +
                "case string " +
                "  when 'A' then new { col1 = 'X', col2 = 10 } " +
                "  when 'B' then new { col1 = 'Y', col2 = 20 } " +
                "  when 'C' then new { col1 = null, col2 = null } " +
                "  else new { col1 = 'Z', col2 = 30 } " +
                "end as val0 from SupportBean sb";
        runAssertion(epl);
    }

    private void runAssertion(String epl)
    {
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);
        assertEquals(Map.class, stmt.getEventType().getPropertyType("val0"));
        FragmentEventType fragType = stmt.getEventType().getFragmentType("val0");
        assertFalse(fragType.isIndexed());
        assertFalse(fragType.isNative());
        assertEquals(String.class, fragType.getFragmentType().getPropertyType("col1"));
        assertEquals(Integer.class, fragType.getFragmentType().getPropertyType("col2"));

        String[] fieldsInner = "col1,col2".split(",");
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        ArrayAssertionUtil.assertPropsMap((Map) listener.assertOneGetNewAndReset().get("val0"), fieldsInner, new Object[] {"Z", 30});

        epService.getEPRuntime().sendEvent(new SupportBean("A", 2));
        ArrayAssertionUtil.assertPropsMap((Map) listener.assertOneGetNewAndReset().get("val0"), fieldsInner, new Object[] {"X", 10});

        epService.getEPRuntime().sendEvent(new SupportBean("B", 3));
        ArrayAssertionUtil.assertPropsMap((Map) listener.assertOneGetNewAndReset().get("val0"), fieldsInner, new Object[] {"Y", 20});

        epService.getEPRuntime().sendEvent(new SupportBean("C", 4));
        ArrayAssertionUtil.assertPropsMap((Map) listener.assertOneGetNewAndReset().get("val0"), fieldsInner, new Object[] {null, null});

        stmt.destroy();
    }

    public void testInvalid() {
        String epl;

        epl = "select case when true then new { col1 = 'a' } else 1 end from SupportBean";
        tryInvalid(epl, "Error starting statement: Case node 'when' expressions require that all results either return a single value or a Map-type (new-operator) value, check the else-condition [select case when true then new { col1 = 'a' } else 1 end from SupportBean]");

        epl = "select case when true then new { col1 = 'a' } when false then 1 end from SupportBean";
        tryInvalid(epl, "Error starting statement: Case node 'when' expressions require that all results either return a single value or a Map-type (new-operator) value, check when-condition number 1 [select case when true then new { col1 = 'a' } when false then 1 end from SupportBean]");

        epl = "select case when true then new { col1 = 'a' } else new { col1 = 1 } end from SupportBean";
        tryInvalid(epl, "Error starting statement: Incompatible case-when return types by new-operator in case-when number 1: Type by name 'Case-when number 1' in property 'col1' expected class java.lang.String but receives class java.lang.Integer [select case when true then new { col1 = 'a' } else new { col1 = 1 } end from SupportBean]");

        epl = "select case when true then new { col1 = 'a' } else new { col2 = 'a' } end from SupportBean";
        tryInvalid(epl, "Error starting statement: Incompatible case-when return types by new-operator in case-when number 1: The property 'col1' is not provided but required [select case when true then new { col1 = 'a' } else new { col2 = 'a' } end from SupportBean]");

        epl = "select case when true then new { col1 = 'a', col1 = 'b' } end from SupportBean";
        tryInvalid(epl, "Error starting statement: Failed to validate new-keyword property names, property 'col1' has already been declared [select case when true then new { col1 = 'a', col1 = 'b' } end from SupportBean]");
    }

    private void tryInvalid(String epl, String message) {
        try
        {
            epService.getEPAdministrator().createEPL(epl);
            fail();
        }
        catch (EPStatementException ex) {
            assertEquals(message, ex.getMessage());
        }
    }

    private static final Log log = LogFactory.getLog(TestNewExpr.class);
}
