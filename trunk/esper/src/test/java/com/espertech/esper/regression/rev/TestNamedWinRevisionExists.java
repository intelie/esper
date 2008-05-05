package com.espertech.esper.regression.rev;

import com.espertech.esper.client.*;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

public class TestNamedWinRevisionExists extends TestCase
{
    private static final Log log = LogFactory.getLog(TestNamedWinRevisionExists.class);
    private EPServiceProvider epService;
    private SupportUpdateListener listenerOne;
    private SupportUpdateListener listenerTwo;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);

        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listenerOne = new SupportUpdateListener();
        listenerTwo = new SupportUpdateListener();
    }

    public void testRevisionExists()
    {
        Map<String, Object> fullType = makeMap(new Object[][] {{"p1", String.class}, {"p2", String.class}, {"p3", String.class}, {"pf", String.class}});
        epService.getEPAdministrator().getConfiguration().addEventTypeAliasNestable("FullType", fullType);

        Map<String, Object> deltaType = makeMap(new Object[][] {{"p1", String.class}, {"p2", String.class}, {"p3", String.class}, {"pd", String.class}});
        epService.getEPAdministrator().getConfiguration().addEventTypeAliasNestable("DeltaType", deltaType);

        ConfigurationRevisionEventType revEvent = new ConfigurationRevisionEventType();
        revEvent.setAliasFullEventType("FullType");
        revEvent.addAliasDeltaEvent("DeltaType");
        revEvent.setPropertyRevision(ConfigurationRevisionEventType.PropertyRevision.EXISTS);
        revEvent.setKeyPropertyNames(new String[] {"p1"});
        epService.getEPAdministrator().getConfiguration().addRevisionEventType("MyExistsRevision", revEvent);

        epService.getEPAdministrator().createEPL("create window MyWin.win:time(10 sec) as select * from MyExistsRevision");
        epService.getEPAdministrator().createEPL("insert into MyWin select * from FullType");
        epService.getEPAdministrator().createEPL("insert into MyWin select * from DeltaType");

        String[] fields = "p1,p2,p3,pf,pd".split(",");
        EPStatement consumerOne = epService.getEPAdministrator().createEPL("select irstream * from MyWin");
        consumerOne.addListener(listenerOne);
        ArrayAssertionUtil.assertEqualsAnyOrder(consumerOne.getEventType().getPropertyNames(), fields);

        epService.getEPRuntime().sendEvent(makeMap("p1,p2,p3,pf","10,20,30,f0"), "FullType");
        ArrayAssertionUtil.assertProps(listenerOne.assertOneGetNewAndReset(), fields, new Object[] {"10", "20", "30", "f0", null});

        epService.getEPRuntime().sendEvent(makeMap("p1,p2","10,21"), "FullType");
        ArrayAssertionUtil.assertProps(listenerOne.getLastNewData()[0], fields, new Object[] {"10", "21", "30", "f0"});
        ArrayAssertionUtil.assertProps(listenerOne.getLastOldData()[0], fields, new Object[] {"10", "21", "30", "f0"});
    }

    private Map<String, Object> makeMap(Object[][] entries)
    {
        Map result = new HashMap<String, Object>();
        for (int i = 0; i < entries.length; i++)
        {
            result.put(entries[i][0], entries[i][1]);
        }
        return result;
    }

    private Map<String, Object> makeMap(String keysList, String valuesList)
    {
        String[] keys = keysList.split(",");
        String[] values = valuesList.split(",");

        Map result = new HashMap<String, Object>();
        for (int i = 0; i < keys.length; i++)
        {
            result.put(keys[i], values[i]);
        }
        return result;
    }
}
