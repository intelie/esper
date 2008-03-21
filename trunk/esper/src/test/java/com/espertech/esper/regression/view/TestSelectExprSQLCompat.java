package com.espertech.esper.regression.view;

import java.util.Arrays;

import junit.framework.TestCase;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.time.TimerControlEvent;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBeanComplexProps;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestSelectExprSQLCompat extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;
    private EPStatement selectTestView;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventTypeAlias("SupportBean", SupportBean.class);
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        epService = EPServiceProviderManager.getProvider("default", config);
        epService.initialize();
    }

    public void testPrefixEngineName()
    {
        String epl = "select default.SupportBean.string as val1, SupportBean.intPrimitive as val2 from SupportBean";
        selectTestView = epService.getEPAdministrator().createEPL(epl);
        selectTestView.addListener(testListener);

        sendEvent("E1", 10);
        EventBean received = testListener.getAndResetLastNewData()[0];
        assertEquals("E1", received.get("val1"));
        assertEquals(10, received.get("val2"));
    }

    private void sendEvent(String s, int intPrimitive)
    {
        SupportBean bean = new SupportBean(s, intPrimitive);
        epService.getEPRuntime().sendEvent(bean);
    }

    private static final Log log = LogFactory.getLog(TestSelectExpr.class);
}
