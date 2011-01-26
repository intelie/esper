package com.espertech.esper.regression.view;

import com.espertech.esper.client.*;
import com.espertech.esper.client.soda.EPStatementObjectModel;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

public class TestDotExpression extends TestCase
{
	private EPServiceProvider epService;
	private SupportUpdateListener listener;

	protected void setUp()
	{
	    epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
	    epService.initialize();
        listener = new SupportUpdateListener();
	}

    public void testInvalid() {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportChainTop", SupportChainTop.class);

        tryInvalid("select (abc).noSuchMethod() from SupportBean abc",
                "Error starting statement: Could not find instance method named 'noSuchMethod' in class 'com.espertech.esper.support.bean.SupportBean' taking no parameters [select (abc).noSuchMethod() from SupportBean abc]");
        tryInvalid("select (abc).getChildOne(\"abc\", 10).noSuchMethod() from SupportChainTop abc",
                "Error starting statement: Could not find instance method named 'noSuchMethod' in class 'com.espertech.esper.support.bean.SupportChainChildOne' taking no parameters [select (abc).getChildOne(\"abc\", 10).noSuchMethod() from SupportChainTop abc]");
    }

    public void testChainedUnparameterized() {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBeanComplexProps", SupportBeanComplexProps.class);

        String epl = "select " +
                "(nested).getNestedValue(), " +
                "(nested).getNestedNested().getNestedNestedValue() " +
                "from SupportBeanComplexProps";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        SupportBeanComplexProps bean = SupportBeanComplexProps.makeDefaultBean();
        Object[][] rows = new Object[][] {
                {"(nested).getNestedValue()", String.class}
                };
        for (int i = 0; i < rows.length; i++) {
            EventPropertyDescriptor prop = stmt.getEventType().getPropertyDescriptors()[i];
            assertEquals(rows[i][0], prop.getPropertyName());
            assertEquals(rows[i][1], prop.getPropertyType());
        }

        epService.getEPRuntime().sendEvent(bean);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNew(), "(nested).getNestedValue()".split(","), new Object[] {bean.getNested().getNestedValue()});
    }

    public void testChainedParameterized() {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportChainTop", SupportChainTop.class);

        String subexpr="(top).getChildOne(\"abc\", 10).getChildTwo(\"append\")";
        String epl = "select " +
                subexpr +
                " from SupportChainTop as top";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        runAssertionChainedParam(stmt, subexpr);

        listener.reset();
        stmt.destroy();
        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(epl);
        assertEquals(epl, model.toEPL());
        stmt = epService.getEPAdministrator().create(model);
        stmt.addListener(listener);

        runAssertionChainedParam(stmt, subexpr);
    }

    public void testArrayPropertySizeAndGet() {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBeanComplexProps", SupportBeanComplexProps.class);

        String epl = "select " +
                "(arrayProperty).size() as size, " +
                "(arrayProperty).get(0) as get0, " +
                "(arrayProperty).get(1) as get1, " +
                "(arrayProperty).get(2) as get2, " +
                "(arrayProperty).get(3) as get3 " +
                "from SupportBeanComplexProps";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        SupportBeanComplexProps bean = SupportBeanComplexProps.makeDefaultBean();
        Object[][] rows = new Object[][] {
                {"size", Integer.class},
                {"get0", int.class},
                {"get1", int.class},
                {"get2", int.class},
                {"get3", int.class}
                };
        for (int i = 0; i < rows.length; i++) {
            EventPropertyDescriptor prop = stmt.getEventType().getPropertyDescriptors()[i];
            assertEquals(rows[i][0], prop.getPropertyName());
            assertEquals(rows[i][1], prop.getPropertyType());
        }

        epService.getEPRuntime().sendEvent(bean);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNew(), "size,get0,get1,get2,get3".split(","),
                new Object[] {bean.getArrayProperty().length, bean.getArrayProperty()[0], bean.getArrayProperty()[1], bean.getArrayProperty()[2], null});
    }
    
    public void testArrayPropertySizeAndGetChained() {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBeanCombinedProps", SupportBeanCombinedProps.class);

        String epl = "select " +
                "(abc).getArray().size() as size, " +
                "(abc).getArray().get(0).getNestLevOneVal() as get0 " +
                "from SupportBeanCombinedProps as abc";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        SupportBeanCombinedProps bean = SupportBeanCombinedProps.makeDefaultBean();
        Object[][] rows = new Object[][] {
                {"size", Integer.class},
                {"get0", String.class},
                };
        for (int i = 0; i < rows.length; i++) {
            EventPropertyDescriptor prop = stmt.getEventType().getPropertyDescriptors()[i];
            assertEquals(rows[i][0], prop.getPropertyName());
            assertEquals(rows[i][1], prop.getPropertyType());
        }

        epService.getEPRuntime().sendEvent(bean);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNew(), "size,get0".split(","),
                new Object[] {bean.getArray().length, bean.getArray()[0].getNestLevOneVal()});
    }

    private void runAssertionChainedParam(EPStatement stmt, String subexpr) {

        Object[][] rows = new Object[][] {
                {subexpr, SupportChainChildTwo.class}
                };
        for (int i = 0; i < rows.length; i++) {
            EventPropertyDescriptor prop = stmt.getEventType().getPropertyDescriptors()[i];
            assertEquals(rows[i][0], prop.getPropertyName());
            assertEquals(rows[i][1], prop.getPropertyType());
        }

        epService.getEPRuntime().sendEvent(new SupportChainTop());
        Object result = listener.assertOneGetNewAndReset().get(subexpr);
        assertEquals("abcappend", ((SupportChainChildTwo)result).getText());
    }

    private void tryInvalid(String epl, String message)
    {
        try {
            epService.getEPAdministrator().createEPL(epl);
            fail();
        }
        catch (EPStatementException ex) {
            assertEquals(message, ex.getMessage());
        }
    }    
}
