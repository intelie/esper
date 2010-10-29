package com.espertech.esper.regression.epl;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.Collections;
import java.util.Map;

public class TestInsertIntoPopulateCreateStream extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testCreateStream()
    {
        epService.getEPAdministrator().createEPL("create schema MyEvent(myId int)");
        epService.getEPAdministrator().createEPL("create schema CompositeEvent(c1 MyEvent, c2 MyEvent, rule string)");
        epService.getEPAdministrator().createEPL("insert into MyStream select c, 'additionalValue' as value from MyEvent c");
        epService.getEPAdministrator().createEPL("insert into CompositeEvent select e1.c as c1, e2.c as c2, '4' as rule " +
                "from pattern [e1=MyStream -> e2=MyStream]");
        epService.getEPAdministrator().createEPL("@Name('Target') select * from CompositeEvent");
        epService.getEPAdministrator().getStatement("Target").addListener(listener);
        
        epService.getEPRuntime().sendEvent(makeEvent(10), "MyEvent");
        epService.getEPRuntime().sendEvent(makeEvent(11), "MyEvent");
        EventBean event = listener.assertOneGetNewAndReset();
        assertEquals(10, event.get("c1.myId"));
        assertEquals(11, event.get("c2.myId"));
        assertEquals("4", event.get("rule"));
    }

    private Map<String, Object> makeEvent(int myId) {
        return Collections.<String, Object>singletonMap("myId", myId);
    }
}
