package com.espertech.esper.regression.epl;

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.epl.SupportStaticMethodInvocations;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;

public class TestFromClauseMethodCache extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void testLRUCache()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        ConfigurationMethodRef methodConfig = new ConfigurationMethodRef();
        methodConfig.setLRUCache(3);
        config.addMethodRef(SupportStaticMethodInvocations.class.getName(), methodConfig);
        config.addImport(SupportStaticMethodInvocations.class.getPackage().getName() + ".*");

        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();

        String joinStatement = "select id, p00, string from " +
                SupportBean.class.getName() + "().win:length(100) as s1, " +
                " method:SupportStaticMethodInvocations.fetchObjectLog(string, intPrimitive)";
        EPStatement stmt = epService.getEPAdministrator().createEPL(joinStatement);
        stmt.addListener(listener);

        // set sleep off
        SupportStaticMethodInvocations.getInvocationSizeReset();

        // The LRU cache caches per same keys
        String[] fields = new String[] {"id", "p00", "string"};
        sendBeanEvent("E1", 1);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {1, "|E1|", "E1"});
        
        sendBeanEvent("E2", 2);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {2, "|E2|", "E2"});

        sendBeanEvent("E3", 3);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {3, "|E3|", "E3"});
        assertEquals(3, SupportStaticMethodInvocations.getInvocationSizeReset());

        // should be cached
        sendBeanEvent("E3", 3);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {3, "|E3|", "E3"});
        assertEquals(0, SupportStaticMethodInvocations.getInvocationSizeReset());

        // should not be cached
        sendBeanEvent("E4", 4);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {4, "|E4|", "E4"});
        assertEquals(1, SupportStaticMethodInvocations.getInvocationSizeReset());

        // should be cached
        sendBeanEvent("E2", 2);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {2, "|E2|", "E2"});
        assertEquals(0, SupportStaticMethodInvocations.getInvocationSizeReset());

        // should not be cached
        sendBeanEvent("E1", 1);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {1, "|E1|", "E1"});
        assertEquals(1, SupportStaticMethodInvocations.getInvocationSizeReset());
    }

    public void testExpiryCache()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        ConfigurationMethodRef methodConfig = new ConfigurationMethodRef();
        methodConfig.setExpiryTimeCache(1, 10);
        config.addMethodRef(SupportStaticMethodInvocations.class.getName(), methodConfig);
        config.addImport(SupportStaticMethodInvocations.class.getPackage().getName() + ".*");

        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();

        String joinStatement = "select id, p00, string from " +
                SupportBean.class.getName() + "().win:length(100) as s1, " +
                " method:SupportStaticMethodInvocations.fetchObjectLog(string, intPrimitive)";
        EPStatement stmt = epService.getEPAdministrator().createEPL(joinStatement);
        stmt.addListener(listener);

        // set sleep off
        SupportStaticMethodInvocations.getInvocationSizeReset();

        sendTimer(1000);
        String[] fields = new String[] {"id", "p00", "string"};
        sendBeanEvent("E1", 1);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {1, "|E1|", "E1"});

        sendTimer(1500);
        sendBeanEvent("E2", 2);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {2, "|E2|", "E2"});

        sendTimer(2000);
        sendBeanEvent("E3", 3);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {3, "|E3|", "E3"});
        assertEquals(3, SupportStaticMethodInvocations.getInvocationSizeReset());

        // should be cached
        sendBeanEvent("E3", 3);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {3, "|E3|", "E3"});
        assertEquals(0, SupportStaticMethodInvocations.getInvocationSizeReset());

        sendTimer(2100);
        // should not be cached
        sendBeanEvent("E4", 4);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {4, "|E4|", "E4"});
        assertEquals(1, SupportStaticMethodInvocations.getInvocationSizeReset());

        // should be cached
        sendBeanEvent("E2", 2);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {2, "|E2|", "E2"});
        assertEquals(0, SupportStaticMethodInvocations.getInvocationSizeReset());

        // should not be cached
        sendBeanEvent("E1", 1);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {1, "|E1|", "E1"});
        assertEquals(1, SupportStaticMethodInvocations.getInvocationSizeReset());
    }

    private void sendTimer(long timeInMSec)
    {
        CurrentTimeEvent event = new CurrentTimeEvent(timeInMSec);
        EPRuntime runtime = epService.getEPRuntime();
        runtime.sendEvent(event);
    }

    private void sendBeanEvent(String string, int intPrimitive)
    {
        SupportBean bean = new SupportBean();
        bean.setString(string);
        bean.setIntPrimitive(intPrimitive);
        epService.getEPRuntime().sendEvent(bean);
    }
}
