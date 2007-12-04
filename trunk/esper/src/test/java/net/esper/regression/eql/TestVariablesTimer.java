package net.esper.regression.eql;

import junit.framework.TestCase;
import net.esper.client.*;
import net.esper.client.soda.*;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.support.client.SupportConfigFactory;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBean_A;
import net.esper.event.EventType;
import net.esper.event.EventBean;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.Map;
import java.util.Arrays;
import java.io.StringReader;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class TestVariablesTimer extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;
    private SupportUpdateListener listenerSet;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(true);

        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
        listenerSet = new SupportUpdateListener();
    }

    public void testTimestamp() throws Exception
    {
        epService.getEPAdministrator().getConfiguration().addVariable("var1", long.class, "12");
        epService.getEPAdministrator().getConfiguration().addVariable("var2", Long.class, "2");
        epService.getEPAdministrator().getConfiguration().addVariable("var3", Long.class, null);

        long startTime = System.currentTimeMillis();
        String stmtTextSet = "on pattern [every timer:interval(100 milliseconds)] set var1 = current_timestamp, var2 = var1 + 1, var3 = var1 + var2";
        EPStatement stmtSet = epService.getEPAdministrator().createEQL(stmtTextSet);
        stmtSet.addListener(listenerSet);

        Thread.sleep(1000);
        stmtSet.destroy();

        EventBean[] received = listenerSet.getNewDataListFlattened();
        assertTrue("received : " + received.length, received.length >= 5);

        for (int i = 0; i < received.length; i++)
        {
            long var1 = (Long) received[i].get("var1");
            long var2 = (Long) received[i].get("var2");
            long var3 = (Long) received[i].get("var3");
            assertTrue(var1 >= startTime);
            assertEquals(var1, var2 - 1);
            assertEquals(var3, var2 + var1);
        }
    }

    private SupportBean_A sendSupportBean_A(String id)
    {
        SupportBean_A bean = new SupportBean_A(id);
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
        SupportBean bean = makeSupportBean(string, intPrimitive, intBoxed);
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }

    private void sendSupportBeanNewThread(final String string, final int intPrimitive, final Integer intBoxed) throws InterruptedException
    {
        Thread t = new Thread() {
            public void run()
            {
                SupportBean bean = makeSupportBean(string, intPrimitive, intBoxed);
                epService.getEPRuntime().sendEvent(bean);
            }
        };
        t.start();
        t.join();
    }

    private SupportBean makeSupportBean(String string, int intPrimitive, Integer intBoxed)
    {
        SupportBean bean = new SupportBean();
        bean.setString(string);
        bean.setIntPrimitive(intPrimitive);
        bean.setIntBoxed(intBoxed);
        return bean;
    }
}
