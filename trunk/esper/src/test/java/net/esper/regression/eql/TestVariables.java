package net.esper.regression.eql;

import junit.framework.TestCase;
import net.esper.client.*;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBean_A;
import net.esper.support.bean.SupportBean_B;
import net.esper.support.client.SupportConfigFactory;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.support.util.SupportUpdateListener;

public class TestVariables extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;
    private SupportUpdateListener listenerSet;

    // test initializatin type correct
    // test coercion
    // test expression result type correct
    // test null as a valid value
    // test configuration variable
    // test tostring on expression
    // test eventtype
    // test order of assignment
    // test multithreaded
    // test assignment between variables in same set

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);

        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
        listenerSet = new SupportUpdateListener();
    }

    public void testRuntimeConfiguration()
    {
        epService.getEPAdministrator().getConfiguration().addVariable("var1", Integer.class, 10);

        String stmtText = "select var1, string from " + SupportBean.class.getName() + "(string like 'E%')";
        EPStatement stmtSelect = epService.getEPAdministrator().createEQL(stmtText);
        stmtSelect.addListener(listener);

        String[] fields = new String[] {"var1", "string"};
        sendSupportBean("E1", 1);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {10, "E1"});

        sendSupportBean("E2", 2);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {10, "E2"});

        String stmtTextSet = "on " + SupportBean.class.getName() + "(string like 'S%') set var1 = intPrimitive";
        EPStatement stmtSet = epService.getEPAdministrator().createEQL(stmtTextSet);
        stmtSet.addListener(listenerSet);

        sendSupportBean("S1", 3);
        sendSupportBean("E3", 4);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {3, "E3"});

        try
        {
            epService.getEPAdministrator().getConfiguration().addVariable("var1", Integer.class, 10);
        }
        catch (ConfigurationException ex)
        {
            assertEquals("Error creating variable: Variable by name 'var1' has already been created", ex.getMessage());
        }
        stmtSelect.destroy();
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
}
