package net.esper.regression.eql;

import junit.framework.TestCase;
import net.esper.client.*;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBean_A;
import net.esper.support.bean.SupportBean_B;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.client.SupportConfigFactory;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.support.util.SupportUpdateListener;
import net.esper.event.EventType;

import java.util.Map;
import java.util.Arrays;

public class TestVariables extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;
    private SupportUpdateListener listenerSet;

    // test types:
    // test coercion

    // test assignment execution:
    // test order of assignment
    // test assignment between variables in same set
    // test writing same variable(s) in same set multiple times

    // test multithreaded:
    // test atomicity of a single and multiple updates
    // test iterator

    // test timer gets variable
    // test variable in filter expression
    // test create variable syntax
    // test output rate limiting

    // invalid tests:
    // test set of variable that has not been defined
    // create of variable that has already been defined
    
    // test OM + compile

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);

        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
        listenerSet = new SupportUpdateListener();
    }

    public void testRuntimeConfig()
    {
        epService.getEPAdministrator().getConfiguration().addVariable("var1", Integer.class, 10);

        String stmtText = "select var1, string from " + SupportBean.class.getName() + "(string like 'E%')";
        EPStatement stmtSelect = epService.getEPAdministrator().createEQL(stmtText);
        stmtSelect.addListener(listener);

        String[] fieldsSelect = new String[] {"var1", "string"};
        sendSupportBean("E1", 1);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsSelect, new Object[] {10, "E1"});

        sendSupportBean("E2", 2);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsSelect, new Object[] {10, "E2"});

        String stmtTextSet = "on " + SupportBean.class.getName() + "(string like 'S%') set var1 = intPrimitive";
        EPStatement stmtSet = epService.getEPAdministrator().createEQL(stmtTextSet);
        stmtSet.addListener(listenerSet);

        EventType typeSet = stmtSet.getEventType();
        assertEquals(Integer.class, typeSet.getPropertyType("var1"));
        assertEquals(Map.class, typeSet.getUnderlyingType());
        assertTrue(Arrays.equals(typeSet.getPropertyNames(), new String[] {"var1"}));

        String[] fieldsVar = new String[] {"var1"};
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{10}});

        sendSupportBean("S1", 3);
        ArrayAssertionUtil.assertProps(listenerSet.assertOneGetNewAndReset(), fieldsVar, new Object[] {3});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{3}});

        sendSupportBean("E3", 4);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsSelect, new Object[] {3, "E3"});

        sendSupportBean("S2", -1);
        ArrayAssertionUtil.assertProps(listenerSet.assertOneGetNewAndReset(), fieldsVar, new Object[] {-1});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{-1}});

        sendSupportBean("E4", 5);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsSelect, new Object[] {-1, "E4"});

        try
        {
            epService.getEPAdministrator().getConfiguration().addVariable("var1", Integer.class, 10);
        }
        catch (ConfigurationException ex)
        {
            assertEquals("Error creating variable: Variable by name 'var1' has already been created", ex.getMessage());
        }

        stmtSet.destroy();
        stmtSelect.destroy();
    }

    public void testRuntimeOrderMultiple()
    {
        epService.getEPAdministrator().getConfiguration().addVariable("var1", Integer.class, null);
        epService.getEPAdministrator().getConfiguration().addVariable("var2", Integer.class, 1);

        String stmtTextSet = "on " + SupportBean.class.getName() + "(string like 'S%' or string like 'B%') set var1 = intPrimitive, var2 = intBoxed";
        EPStatement stmtSet = epService.getEPAdministrator().createEQL(stmtTextSet);
        stmtSet.addListener(listenerSet);
        String[] fieldsVar = new String[] {"var1", "var2"};
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{null, 1}});

        EventType typeSet = stmtSet.getEventType();
        assertEquals(Integer.class, typeSet.getPropertyType("var1"));
        assertEquals(Integer.class, typeSet.getPropertyType("var2"));
        assertEquals(Map.class, typeSet.getUnderlyingType());
        assertTrue(Arrays.equals(typeSet.getPropertyNames(), new String[] {"var1", "var2"}));

        sendSupportBean("S1", 3, null);
        ArrayAssertionUtil.assertProps(listenerSet.assertOneGetNewAndReset(), fieldsVar, new Object[] {3, null});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{3, null}});

        sendSupportBean("S1", -1, -2);
        ArrayAssertionUtil.assertProps(listenerSet.assertOneGetNewAndReset(), fieldsVar, new Object[] {-1, -2});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{-1, -2}});

        String stmtText = "select var1, var2, string from " + SupportBean.class.getName() + "(string like 'E%' or string like 'B%')";
        EPStatement stmtSelect = epService.getEPAdministrator().createEQL(stmtText);
        stmtSelect.addListener(listener);
        String[] fieldsSelect = new String[] {"var1", "var2", "string"};
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fieldsSelect, null);

        sendSupportBean("E1", 1);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsSelect, new Object[] {-1, -2, "E1"});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{-1, -2}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fieldsSelect, new Object[][] {{-1, -2, "E1"}});

        sendSupportBean("S1", 11, 12);
        ArrayAssertionUtil.assertProps(listenerSet.assertOneGetNewAndReset(), fieldsVar, new Object[] {11, 12});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{11, 12}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fieldsSelect, new Object[][] {{11, 12, "E1"}});

        sendSupportBean("E2", 2);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsSelect, new Object[] {11, 12, "E2"});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fieldsSelect, new Object[][] {{11, 12, "E2"}});

        stmtSelect.destroy();
        stmtSet.destroy();
    }

    public void testEngineConfig()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        config.addVariable("p_1", String.class, "begin");
        config.addVariable("p_2", boolean.class, true);
        config.addVariable("p_3", String.class, "value");

        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        String stmtTextSet = "on " + SupportBean.class.getName() + "(string like 'S%') set p_1 = 'end', p_2 = false, p_3 = null";
        EPStatement stmtSet = epService.getEPAdministrator().createEQL(stmtTextSet);
        stmtSet.addListener(listenerSet);
        String[] fieldsVar = new String[] {"p_1", "p_2", "p_3"};
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{"begin", true, "value"}});

        EventType typeSet = stmtSet.getEventType();
        assertEquals(String.class, typeSet.getPropertyType("p_1"));
        assertEquals(Boolean.class, typeSet.getPropertyType("p_2"));
        assertEquals(String.class, typeSet.getPropertyType("p_3"));
        assertEquals(Map.class, typeSet.getUnderlyingType());
        assertTrue(Arrays.equals(typeSet.getPropertyNames(), fieldsVar));

        sendSupportBean("S1", 3);
        ArrayAssertionUtil.assertProps(listenerSet.assertOneGetNewAndReset(), fieldsVar, new Object[] {"end", false, null});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{"end", false, null}});

        sendSupportBean("S2", 4);
        ArrayAssertionUtil.assertProps(listenerSet.assertOneGetNewAndReset(), fieldsVar, new Object[] {"end", false, null});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{"end", false, null}});

        stmtSet.destroy();
    }

    public void testCoercion()
    {
        epService.getEPAdministrator().getConfiguration().addVariable("var1", Float.class, null);
        epService.getEPAdministrator().getConfiguration().addVariable("var2", Double.class, null);
        epService.getEPAdministrator().getConfiguration().addVariable("var3", Long.class, null);

        String stmtTextSet = "on " + SupportBean.class.getName() + " set var1 = intPrimitive, var2 = intPrimitive, var3=intBoxed";
        EPStatement stmtSet = epService.getEPAdministrator().createEQL(stmtTextSet);
        stmtSet.addListener(listenerSet);
        String[] fieldsVar = new String[] {"var1", "var2", "var3"};
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{null, null, null}});

        String stmtText = "select var1, var2, var3, id from " + SupportBean_A.class.getName() + ".win:length(2)";
        EPStatement stmtSelect = epService.getEPAdministrator().createEQL(stmtText);
        stmtSelect.addListener(listener);
        String[] fieldsSelect = new String[] {"var1", "var2", "var3", "id"};
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fieldsSelect, null);

        EventType typeSet = stmtSet.getEventType();
        assertEquals(Float.class, typeSet.getPropertyType("var1"));
        assertEquals(Double.class, typeSet.getPropertyType("var2"));
        assertEquals(Long.class, typeSet.getPropertyType("var3"));
        assertEquals(Map.class, typeSet.getUnderlyingType());
        ArrayAssertionUtil.assertEqualsAnyOrder(typeSet.getPropertyNames(), fieldsVar);

        sendSupportBean_A("A1");
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsSelect, new Object[] {null, null, null, "A1"});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fieldsSelect, new Object[][] {{null, null, null, "A1"}});

        sendSupportBean("S1", 1, 2);
        ArrayAssertionUtil.assertProps(listenerSet.assertOneGetNewAndReset(), fieldsVar, new Object[] {1f, 1d, 2L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{1f, 1d, 2L}});

        sendSupportBean_A("A2");
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsSelect, new Object[] {1f, 1d, 2L, "A2"});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fieldsSelect, new Object[][] {{1f, 1d, 2L, "A1"}, {1f, 1d, 2L, "A2"}});

        sendSupportBean("S1", 10, 20);
        ArrayAssertionUtil.assertProps(listenerSet.assertOneGetNewAndReset(), fieldsVar, new Object[] {10f, 10d, 20L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSet.iterator(), fieldsVar, new Object[][] {{10f, 10d, 20L}});

        sendSupportBean_A("A3");
        ArrayAssertionUtil.assertProps(listener.getLastNewData()[0], fieldsSelect, new Object[] {10f, 10d, 20L, "A3"});
        ArrayAssertionUtil.assertProps(listener.getLastOldData()[0], fieldsSelect, new Object[] {10f, 10d, 20L, "A1"});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtSelect.iterator(), fieldsSelect, new Object[][] {{10f, 10d, 20L, "A2"}, {10f, 10d, 20L, "A3"}});

        stmtSelect.destroy();
        stmtSet.destroy();
    }

    public void testInvalidSet()
    {
        epService.getEPAdministrator().getConfiguration().addVariable("var1", String.class, null);
        epService.getEPAdministrator().getConfiguration().addVariable("var2", boolean.class, false);
        epService.getEPAdministrator().getConfiguration().addVariable("var3", int.class, 1);

        tryInvalidSet("on " + SupportBean.class.getName() + " set var1 = 1",
                      "Error starting view: Variable 'var1' of declared type 'java.lang.String' cannot be assigned a value of type 'java.lang.Integer' [on net.esper.support.bean.SupportBean set var1 = 1]");

        tryInvalidSet("on " + SupportBean.class.getName() + " set var3 = 'abc'",
                      "Error starting view: Variable 'var3' of declared type 'java.lang.Integer' cannot be assigned a value of type 'java.lang.String' [on net.esper.support.bean.SupportBean set var3 = 'abc']");

        tryInvalidSet("on " + SupportBean.class.getName() + " set var3 = doublePrimitive",
                      "Error starting view: Variable 'var3' of declared type 'java.lang.Integer' cannot be assigned a value of type 'double' [on net.esper.support.bean.SupportBean set var3 = doublePrimitive]");

        tryInvalidSet("on " + SupportBean.class.getName() + " set var2 = 'false'", null);
        tryInvalidSet("on " + SupportBean.class.getName() + " set var3 = 1.1", null);
        tryInvalidSet("on " + SupportBean.class.getName() + " set var3 = 22222222222222", null);
    }

    private void tryInvalidSet(String stmtText, String message)
    {
        try
        {
            epService.getEPAdministrator().createEQL(stmtText);
            fail();
        }
        catch (EPStatementException ex)
        {
            if (message != null)
            {
                assertEquals(message, ex.getMessage());
            }
        }
    }

    public void testInvalidInitialization()
    {
        tryInvalid(Integer.class, new Double(11.1),
                "Error creating variable: Variable 'var1' of declared type 'java.lang.Integer' cannot be initialized by a value of type 'java.lang.Double'");

        tryInvalid(Integer.class, "abcdef",
                "Error creating variable: Variable 'var1' of declared type 'java.lang.Integer' cannot be initialized by a value of type 'java.lang.String'");

        tryInvalid(Math.class, "abcdef",
                "Error creating variable: Invalid variable type for variable 'var1' as type 'java.lang.Math', only Java primitive, boxed or String types are allowed");

        tryInvalid(int.class, new Double(11.1), null);
        tryInvalid(String.class, true, null);

        // Try invalid configuration
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        config.addVariable("p_1", String.class, 5);

        try
        {
            epService = EPServiceProviderManager.getDefaultProvider(config);
            epService.initialize();
            fail();
        }
        catch (ConfigurationException ex)
        {
            // expected
        }
    }

    private void tryInvalid(Class type, Object value, String message)
    {
        try
        {
            epService.getEPAdministrator().getConfiguration().addVariable("var1", type, value);
            fail();
        }
        catch (ConfigurationException ex)
        {
            if (message != null)
            {
                assertEquals(message, ex.getMessage());
            }
        }
    }

    private SupportBean_A sendSupportBean_A(String id)
    {
        SupportBean_A bean = new SupportBean_A(id);
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }

    private SupportBean_B sendSupportBean_B(String id)
    {
        SupportBean_B bean = new SupportBean_B(id);
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }

    private SupportBean sendSupportBean(String string, int intPrimitive, Integer intBoxed,
                                        double doublePrimitive, Double doubleBoxed)
    {
        SupportBean bean = new SupportBean();
        bean.setString(string);
        bean.setIntPrimitive(intPrimitive);
        bean.setIntBoxed(intBoxed);
        bean.setDoublePrimitive(doublePrimitive);
        bean.setDoubleBoxed(doubleBoxed);
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }

    private SupportBean sendSupportBean(String string, int intPrimitive)
    {
        SupportBean bean = new SupportBean();
        bean.setString(string);
        bean.setIntPrimitive(intPrimitive);
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }

    private SupportBean sendSupportBean(String string, int intPrimitive, Integer intBoxed)
    {
        SupportBean bean = new SupportBean();
        bean.setString(string);
        bean.setIntPrimitive(intPrimitive);
        bean.setIntBoxed(intBoxed);
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }
}
