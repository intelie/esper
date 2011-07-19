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
import com.espertech.esper.client.soda.*;
import com.espertech.esper.epl.agg.AggregationSupport;
import com.espertech.esper.epl.agg.AggregationValidationContext;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.epl.SupportPluginAggregationMethodOne;
import com.espertech.esper.support.epl.SupportPluginAggregationMethodThree;
import com.espertech.esper.support.epl.SupportPluginAggregationMethodTwo;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.util.SerializableObjectCopier;
import junit.framework.TestCase;

public class TestAggregationFunctionPlugIn extends TestCase
{
    private EPServiceProvider epService;

    public void setUp()
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.addPlugInAggregationFunction("concatstring", MyConcatAggregationFunction.class.getName());
        configuration.addPlugInAggregationFunction("totalup", MyInnerAggFunction.class.getName());
        epService = EPServiceProviderManager.getProvider("TestAggregationFunctionPlugIn", configuration);
        epService.initialize();
    }

    public void tearDown()
    {
        epService.initialize();
    }

    public void testGrouped_OM() throws Exception
    {
        String text = "select irstream concatstring(string) as val from " + SupportBean.class.getName() + ".win:length(10) group by intPrimitive";

        EPStatementObjectModel model = new EPStatementObjectModel();
        model.setSelectClause(SelectClause.create().streamSelector(StreamSelector.RSTREAM_ISTREAM_BOTH)
                .add(Expressions.plugInAggregation("concatstring", Expressions.property("string")), "val"));
        model.setFromClause(FromClause.create(FilterStream.create(SupportBean.class.getName()).addView("win", "length", Expressions.constant(10))));
        model.setGroupByClause(GroupByClause.create("intPrimitive"));
        assertEquals(text, model.toEPL());
        SerializableObjectCopier.copy(model);

        tryGrouped(null, model);
    }

    public void testGrouped_Compile() throws Exception
    {
        String text = "select irstream concatstring(string) as val from " + SupportBean.class.getName() + ".win:length(10) group by intPrimitive";

        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(text);
        SerializableObjectCopier.copy(model);
        assertEquals(text, model.toEPL());

        tryGrouped(null, model);
    }

    public void testGroupedLowercase()
    {
        String text = "select irstream CONCATSTRING(string) as val from " + SupportBean.class.getName() + ".win:length(10) group by intPrimitive";
        tryGrouped(text, null);
    }

    public void testGroupedUppercase()
    {
        String text = "select irstream concatstring(string) as val from " + SupportBean.class.getName() + ".win:length(10) group by intPrimitive";
        tryGrouped(text, null);
    }

    private void tryGrouped(String text, EPStatementObjectModel model)
    {
        EPStatement statement;
        if (model != null)
        {
            statement = epService.getEPAdministrator().create(model);
        }
        else
        {
            statement = epService.getEPAdministrator().createEPL(text);
        }
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("a", 1));
        listener.assertFieldEqualsAndReset("val", new Object[] {"a"}, new Object[] {""});

        epService.getEPRuntime().sendEvent(new SupportBean("b", 2));
        listener.assertFieldEqualsAndReset("val", new Object[] {"b"}, new Object[] {""});

        epService.getEPRuntime().sendEvent(new SupportBean("c", 1));
        listener.assertFieldEqualsAndReset("val", new Object[] {"a c"}, new Object[] {"a"});

        epService.getEPRuntime().sendEvent(new SupportBean("d", 2));
        listener.assertFieldEqualsAndReset("val", new Object[] {"b d"}, new Object[] {"b"});

        epService.getEPRuntime().sendEvent(new SupportBean("e", 1));
        listener.assertFieldEqualsAndReset("val", new Object[] {"a c e"}, new Object[] {"a c"});

        epService.getEPRuntime().sendEvent(new SupportBean("f", 2));
        listener.assertFieldEqualsAndReset("val", new Object[] {"b d f"}, new Object[] {"b d"});

        listener.reset();
    }

    public void testWindow()
    {
        String text = "select irstream concatstring(string) as val from " + SupportBean.class.getName() + ".win:length(2)";
        EPStatement statement = epService.getEPAdministrator().createEPL(text);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("a", -1));
        listener.assertFieldEqualsAndReset("val", new Object[] {"a"}, new Object[] {""});

        epService.getEPRuntime().sendEvent(new SupportBean("b", -1));
        listener.assertFieldEqualsAndReset("val", new Object[] {"a b"}, new Object[] {"a"});

        epService.getEPRuntime().sendEvent(new SupportBean("c", -1));
        listener.assertFieldEqualsAndReset("val", new Object[] {"b c"}, new Object[] {"a b"});

        epService.getEPRuntime().sendEvent(new SupportBean("d", -1));
        listener.assertFieldEqualsAndReset("val", new Object[] {"c d"}, new Object[] {"b c"});
    }

    public void testDistinct()
    {
        String text = "select irstream concatstring(distinct string) as val from " + SupportBean.class.getName();
        EPStatement statement = epService.getEPAdministrator().createEPL(text);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("a", -1));
        listener.assertFieldEqualsAndReset("val", new Object[] {"a"}, new Object[] {""});

        epService.getEPRuntime().sendEvent(new SupportBean("b", -1));
        listener.assertFieldEqualsAndReset("val", new Object[] {"a b"}, new Object[] {"a"});

        epService.getEPRuntime().sendEvent(new SupportBean("b", -1));
        listener.assertFieldEqualsAndReset("val", new Object[] {"a b"}, new Object[] {"a b"});

        epService.getEPRuntime().sendEvent(new SupportBean("c", -1));
        listener.assertFieldEqualsAndReset("val", new Object[] {"a b c"}, new Object[] {"a b"});

        epService.getEPRuntime().sendEvent(new SupportBean("a", -1));
        listener.assertFieldEqualsAndReset("val", new Object[] {"a b c"}, new Object[] {"a b c"});
    }

    public void testArrayParams()
    {
        epService.getEPAdministrator().getConfiguration().addPlugInAggregationFunction("countback", SupportPluginAggregationMethodOne.class.getName());

        String text = "select irstream countback({1,2,intPrimitive}) as val from " + SupportBean.class.getName();
        EPStatement statement = epService.getEPAdministrator().createEPL(text);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean());
        listener.assertFieldEqualsAndReset("val", new Object[] {-1}, new Object[] {0});
    }

    public void testMultipleParams()
    {
        epService.getEPAdministrator().getConfiguration().addPlugInAggregationFunction("countboundary", SupportPluginAggregationMethodThree.class.getName());

        String text = "select irstream countboundary(1, 10, intPrimitive) as val from " + SupportBean.class.getName();
        EPStatement statement = epService.getEPAdministrator().createEPL(text);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        runAssertion(listener);
        
        statement.destroy();
        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(text);
        assertEquals(text, model.toEPL());
        statement = epService.getEPAdministrator().create(model);
        statement.addListener(listener);
        
        runAssertion(listener);
    }

    private void runAssertion(SupportUpdateListener listener) {

        AggregationValidationContext validContext = SupportPluginAggregationMethodThree.getContexts().get(0);
        ArrayAssertionUtil.assertEqualsExactOrder(validContext.getParameterTypes(), new Class[] {Integer.class, Integer.class, int.class});
        ArrayAssertionUtil.assertEqualsExactOrder(validContext.getConstantValues(), new Object[] {1, 10, null});
        ArrayAssertionUtil.assertEqualsExactOrder(validContext.getIsConstantValue(), new boolean[] {true, true, false});

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 5));
        listener.assertFieldEqualsAndReset("val", new Object[] {1}, new Object[] {0});

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 0));
        listener.assertFieldEqualsAndReset("val", new Object[] {1}, new Object[] {1});

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 11));
        listener.assertFieldEqualsAndReset("val", new Object[] {1}, new Object[] {1});
        
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        listener.assertFieldEqualsAndReset("val", new Object[] {2}, new Object[] {1});
    }

    public void testNoSubnodesRuntimeAdd()
    {
        epService.getEPAdministrator().getConfiguration().addPlugInAggregationFunction("countback", SupportPluginAggregationMethodOne.class.getName());

        String text = "select irstream countback() as val from " + SupportBean.class.getName();
        EPStatement statement = epService.getEPAdministrator().createEPL(text);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean());
        listener.assertFieldEqualsAndReset("val", new Object[] {-1}, new Object[] {0});

        epService.getEPRuntime().sendEvent(new SupportBean());
        listener.assertFieldEqualsAndReset("val", new Object[] {-2}, new Object[] {-1});
    }

    public void testMappedPropertyLookAlike()
    {
        String text = "select irstream concatstring('a') as val from " + SupportBean.class.getName();
        EPStatement statement = epService.getEPAdministrator().createEPL(text);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);
        assertEquals(String.class, statement.getEventType().getPropertyType("val"));

        epService.getEPRuntime().sendEvent(new SupportBean());
        listener.assertFieldEqualsAndReset("val", new Object[] {"a"}, new Object[] {""});

        epService.getEPRuntime().sendEvent(new SupportBean());
        listener.assertFieldEqualsAndReset("val", new Object[] {"a a"}, new Object[] {"a"});

        epService.getEPRuntime().sendEvent(new SupportBean());
        listener.assertFieldEqualsAndReset("val", new Object[] {"a a a"}, new Object[] {"a a"});
    }

    public void testFailedValidation()
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.addPlugInAggregationFunction("concat", SupportPluginAggregationMethodTwo.class.getName());
        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();

        try
        {
            String text = "select * from " + SupportBean.class.getName() + " group by concat(1)";
            epService.getEPAdministrator().createEPL(text);
        }
        catch (EPStatementException ex)
        {
            assertEquals("Error starting statement: Plug-in aggregation function 'concat' failed validation: Invalid parameter type 'java.lang.Integer', expecting string [select * from com.espertech.esper.support.bean.SupportBean group by concat(1)]", ex.getMessage());
        }

        try
        {
            String text = "select * from " + SupportBean.class.getName() + " group by concat(1, 1)";
            epService.getEPAdministrator().createEPL(text);
        }
        catch (EPStatementException ex)
        {
            assertEquals("Error starting statement: Plug-in aggregation function 'concat' failed validation: Invalid parameter type 'java.lang.Integer', expecting string [select * from com.espertech.esper.support.bean.SupportBean group by concat(1, 1)]", ex.getMessage());
        }
    }

    public void testInvalidUse()
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.addPlugInAggregationFunction("xxx", String.class.getName());
        configuration.addPlugInAggregationFunction("yyy", "com.NoSuchClass");
        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();

        try
        {
            String text = "select * from " + SupportBean.class.getName() + " group by xxx(1)";
            epService.getEPAdministrator().createEPL(text);
        }
        catch (EPStatementException ex)
        {
            assertEquals("Error in expression: Error resolving aggregation: Aggregation class by name 'java.lang.String' does not subclass AggregationSupport [select * from com.espertech.esper.support.bean.SupportBean group by xxx(1)]", ex.getMessage());
        }

        try
        {
            String text = "select * from " + SupportBean.class.getName() + " group by yyy(1)";
            epService.getEPAdministrator().createEPL(text);
        }
        catch (EPStatementException ex)
        {
            assertEquals("Error in expression: Error resolving aggregation: Could not load aggregation class by name 'com.NoSuchClass' [select * from com.espertech.esper.support.bean.SupportBean group by yyy(1)]", ex.getMessage());
        }
    }

    public void testInvalidConfigure()
    {
        tryInvalidConfigure("a b", "MyClass");
        tryInvalidConfigure("abc", "My Class");

        // configure twice
        try
        {
            epService.getEPAdministrator().getConfiguration().addPlugInAggregationFunction("concatstring", MyConcatAggregationFunction.class.getName());
            fail();
        }
        catch (ConfigurationException ex)
        {
            // expected
        }
    }

    private void tryInvalidConfigure(String funcName, String className)
    {
        try
        {
            epService.getEPAdministrator().getConfiguration().addPlugInAggregationFunction(funcName, className);
            fail();
        }
        catch (ConfigurationException ex)
        {
            // expected
        }
    }

    public void testInvalid()
    {
        tryInvalid("select xxx(string) from " + SupportBean.class.getName(),
                "Error starting statement: Unknown single-row function, aggregation function or mapped or indexed property named 'xxx' could not be resolved [select xxx(string) from com.espertech.esper.support.bean.SupportBean]");
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

    public class MyInnerAggFunction extends AggregationSupport
    {
        private int total;

        @Override
        public void validate(AggregationValidationContext validationContext)
        {
        }

        public void enter(Object value)
        {
            total += ((Number)value).intValue();
        }

        public void leave(Object value)
        {
            total -= ((Number)value).intValue();
        }

        public Object getValue()
        {
            return total;
        }

        public Class getValueType()
        {
            return Integer.class;
        }

        public void clear()
        {
        }
    }
}
