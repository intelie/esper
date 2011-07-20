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

package com.espertech.esper.regression.client;

import com.espertech.esper.client.*;
import com.espertech.esper.client.soda.EPStatementObjectModel;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

public class TestSingleRowFunctionPlugIn extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        listener = new SupportUpdateListener();

        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.addPlugInSingleRowFunction("power3", MySingleRowFunction.class.getName(), "computePower3");
        configuration.addPlugInSingleRowFunction("chainTop", MySingleRowFunction.class.getName(), "getChainTop");
        configuration.addPlugInSingleRowFunction("surroundx", MySingleRowFunction.class.getName(), "surroundx");
        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();
    }

    public void testPropertyOrSingleRowMethod() throws Exception
    {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        String text = "select surroundx('test') as val from SupportBean";
        EPStatement stmt = epService.getEPAdministrator().createEPL(text);
        stmt.addListener(listener);

        String fields[] = new String[] {"val"};
        epService.getEPRuntime().sendEvent(new SupportBean("a", 3));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(),fields, new Object[] {"XtestX"});
    }

    public void testChainMethod() throws Exception
    {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        String text = "select chainTop().chainValue(12, intPrimitive) as val from SupportBean";
        EPStatement stmt = epService.getEPAdministrator().createEPL(text);
        stmt.addListener(listener);

        runAssertionChainMethod();

        stmt.destroy();
        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(text);
        assertEquals(text, model.toEPL());
        stmt = epService.getEPAdministrator().create(model);
        assertEquals(text, stmt.getText());
        stmt.addListener(listener);

        runAssertionChainMethod();
    }

    public void testSingleMethod() throws Exception
    {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        String text = "select power3(intPrimitive) as val from SupportBean";
        EPStatement stmt = epService.getEPAdministrator().createEPL(text);
        stmt.addListener(listener);

        runAssertionSingleMethod();

        stmt.destroy();
        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(text);
        assertEquals(text, model.toEPL());
        stmt = epService.getEPAdministrator().create(model);
        assertEquals(text, stmt.getText());
        stmt.addListener(listener);

        runAssertionSingleMethod();

        stmt.destroy();
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        text = "select power3(2) as val from SupportBean";
        stmt = epService.getEPAdministrator().createEPL(text);
        stmt.addListener(listener);

        runAssertionSingleMethod();
    }

    private void runAssertionChainMethod()
    {
        String fields[] = new String[] {"val"};
        epService.getEPRuntime().sendEvent(new SupportBean("a", 3));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(),fields, new Object[] {36});

        listener.reset();
    }

    private void runAssertionSingleMethod()
    {
        String fields[] = new String[] {"val"};
        epService.getEPRuntime().sendEvent(new SupportBean("a", 2));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(),fields, new Object[] {8});

        listener.reset();
    }

    public void testFailedValidation()
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.addPlugInSingleRowFunction("singlerow", MySingleRowFunctionTwo.class.getName(), "testSingleRow");
        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();

        try
        {
            String text = "select singlerow('a', 'b') from " + SupportBean.class.getName();
            epService.getEPAdministrator().createEPL(text);
        }
        catch (EPStatementException ex)
        {
            assertEquals("Error starting statement: Could not find static method named 'testSingleRow' in class 'com.espertech.esper.regression.client.MySingleRowFunctionTwo' with matching parameter number and expected parameter type(s) 'String, String' (nearest match found was 'testSingleRow' taking type(s) 'String, int') [select singlerow('a', 'b') from com.espertech.esper.support.bean.SupportBean]", ex.getMessage());
        }
    }

    public void testInvalidConfigure()
    {
        tryInvalidConfigure("a b", "MyClass", "some");
        tryInvalidConfigure("abc", "My Class", "other s");

        // configured twice
        try
        {
            epService.getEPAdministrator().getConfiguration().addPlugInSingleRowFunction("concatstring", MySingleRowFunction.class.getName(), "xyz");
            epService.getEPAdministrator().getConfiguration().addPlugInAggregationFunction("concatstring", MyConcatAggregationFunction.class.getName());
            fail();
        }
        catch (ConfigurationException ex)
        {
            // expected
        }

        // configured twice
        try
        {
            epService.getEPAdministrator().getConfiguration().addPlugInAggregationFunction("teststring", MyConcatAggregationFunction.class.getName());
            epService.getEPAdministrator().getConfiguration().addPlugInSingleRowFunction("teststring", MySingleRowFunction.class.getName(), "xyz");
            fail();
        }
        catch (ConfigurationException ex)
        {
            // expected
        }
    }

    private void tryInvalidConfigure(String funcName, String className, String methodName)
    {
        try
        {
            epService.getEPAdministrator().getConfiguration().addPlugInSingleRowFunction(funcName, className, methodName);
            fail();
        }
        catch (ConfigurationException ex)
        {
            // expected
        }
    }

    private void tryInvalid(String stmtText, String expectedMsg)
    {
        try
        {
            epService.getEPAdministrator().createEPL(stmtText);
            fail();
        }
        catch (EPStatementException ex)
        {
            assertEquals(expectedMsg, ex.getMessage());
        }
    }
}
