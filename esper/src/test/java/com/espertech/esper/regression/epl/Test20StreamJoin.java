package com.espertech.esper.regression.epl;

import junit.framework.TestCase;
 import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayHandlingUtil;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.client.EventBean;

import java.io.StringWriter;

public class Test20StreamJoin extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
        listener = new SupportUpdateListener();
    }
    
    public void test20StreamJoin()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("S0", SupportBean_S0.class.getName());
        
        StringWriter buf = new StringWriter();
        buf.append("select * from ");

        String delimiter = "";
        for (int i = 0; i < 20; i++) {
            buf.append(delimiter);
            buf.append("S0(id=" + i + ").std:lastevent() as s_" + i);
            delimiter = ", ";
        }
        EPStatement stmt = epService.getEPAdministrator().createEPL(buf.toString());
        stmt.addListener(listener);

        for (int i = 0; i < 19; i++) {
            epService.getEPRuntime().sendEvent(new SupportBean_S0(i));
        }
        assertFalse(listener.isInvoked());
        epService.getEPRuntime().sendEvent(new SupportBean_S0(19));
        assertTrue(listener.isInvoked());
    }
}