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
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TestTypeOfExpr extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        listener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
    }

    public void testInvalid() {
        tryInvalid("select typeof(xx) from java.lang.Object", "Error starting statement: Property named 'xx' is not valid in any stream [select typeof(xx) from java.lang.Object]");
    }

    private void tryInvalid(String epl, String message) {

        try {
            epService.getEPAdministrator().createEPL(epl);
            fail();
        }
        catch (EPStatementException ex) {
            assertEquals(message, ex.getMessage());
        }
    }

    public void testDynamicProps() {
        epService.getEPAdministrator().createEPL("create schema MySchema as (key string)");

        String stmtText = "select typeof(prop?), typeof(key) from MySchema as s0";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        runAssertion();

        stmt.destroy();
        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(stmtText);
        assertEquals(stmtText, model.toEPL());
        stmt = epService.getEPAdministrator().create(model);
        stmt.addListener(listener);

        runAssertion();
    }

    private void runAssertion() {
                
        String[] fields = new String[] {"typeof(prop?)", "typeof(key)"};

        sendSchemaEvent(1, "E1");
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"Integer", "String"});

        sendSchemaEvent("test", "E2");
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"String", "String"});

        sendSchemaEvent(null, "E3");
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, "String"});
    }

    private void sendSchemaEvent(Object prop, String key) {
        Map<String, Object> event = new HashMap<String, Object>();
        event.put("prop", prop);
        event.put("key", key);
        epService.getEPRuntime().sendEvent(event, "MySchema");
    }

    public void testVariantStream()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        
        epService.getEPAdministrator().createEPL("create schema EventOne as (key string)");
        epService.getEPAdministrator().createEPL("create schema EventTwo as (key string)");
        epService.getEPAdministrator().createEPL("create schema S0 as " + SupportBean_S0.class.getName());
        epService.getEPAdministrator().createEPL("create variant schema VarSchema as *");

        epService.getEPAdministrator().createEPL("insert into VarSchema select * from EventOne");
        epService.getEPAdministrator().createEPL("insert into VarSchema select * from EventTwo");
        epService.getEPAdministrator().createEPL("insert into VarSchema select * from S0");
        epService.getEPAdministrator().createEPL("insert into VarSchema select * from SupportBean");

        String stmtText = "select typeof(A) as t0 from VarSchema as A";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(Collections.singletonMap("key", "value"), "EventOne");
        assertEquals("EventOne", listener.assertOneGetNewAndReset().get("t0"));

        epService.getEPRuntime().sendEvent(Collections.singletonMap("key", "value"), "EventTwo");
        assertEquals("EventTwo", listener.assertOneGetNewAndReset().get("t0"));

        epService.getEPRuntime().sendEvent(new SupportBean_S0(1));
        assertEquals("S0", listener.assertOneGetNewAndReset().get("t0"));

        epService.getEPRuntime().sendEvent(new SupportBean());
        assertEquals("SupportBean", listener.assertOneGetNewAndReset().get("t0"));

        stmt.destroy();
        listener.reset();
        stmt = epService.getEPAdministrator().createEPL("select * from VarSchema match_recognize(\n" +
                "  measures A as a, B as b\n" +
                "  pattern (A B)\n" +
                "  define A as typeof(A) = \"EventOne\",\n" +
                "         B as typeof(B) = \"EventTwo\"\n" +
                "  )");
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(Collections.singletonMap("key", "value"), "EventOne");
        epService.getEPRuntime().sendEvent(Collections.singletonMap("key", "value"), "EventTwo");
        assertTrue(listener.isInvoked());
    }

    public void testNamedUnnamedPOJO() {
        // test name-provided or no-name-provided
        epService.getEPAdministrator().getConfiguration().addEventType("ISupportA", ISupportA.class);
        epService.getEPAdministrator().getConfiguration().addEventType("ISupportABCImpl", ISupportABCImpl.class);

        String stmtText = "select typeof(A) as t0 from ISupportA as A";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new ISupportAImpl(null, null));
        assertEquals(ISupportAImpl.class.getName(), listener.assertOneGetNewAndReset().get("t0"));

        epService.getEPRuntime().sendEvent(new ISupportABCImpl(null, null, null, null));
        assertEquals("ISupportABCImpl", listener.assertOneGetNewAndReset().get("t0"));
    }

    public void testFragment() {
        epService.getEPAdministrator().createEPL("create schema InnerSchema as (key string)");
        epService.getEPAdministrator().createEPL("create schema MySchema as (inside InnerSchema, insidearr InnerSchema[])");

        String[] fields = new String[] {"t0", "t1"};
        String stmtText = "select typeof(s0.inside) as t0, typeof(s0.insidearr) as t1 from MySchema as s0";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);
        
        Map<String, Object> event = new HashMap<String, Object>();
        epService.getEPRuntime().sendEvent(event, "MySchema");
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, null});

        event = new HashMap<String, Object>();
        event.put("inside", new HashMap<String, Object>());
        epService.getEPRuntime().sendEvent(event, "MySchema");
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"InnerSchema", null});

        event = new HashMap<String, Object>();
        event.put("insidearr", new Map[0]);
        epService.getEPRuntime().sendEvent(event, "MySchema");
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, "InnerSchema[]"});
    }
}
