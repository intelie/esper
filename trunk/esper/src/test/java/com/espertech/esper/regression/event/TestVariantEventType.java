package com.espertech.esper.regression.event;

import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.espertech.esper.client.*;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_A;

import java.util.Map;
import java.util.HashMap;

public class TestVariantEventType extends TestCase
{
    private static final Log log = LogFactory.getLog(TestVariantEventType.class);
    private EPServiceProvider epService;
    private SupportUpdateListener listenerOne;

    // TODO
    // test common numeric type coercion
    // test different policies
    // test invalid config
    // test dynamic, index, nested properties
    // test mixin of different types
    // test pattern and subquery
    
    // javadoc and doc

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);

        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listenerOne = new SupportUpdateListener();
    }

    public void testVariantTwoClasses()
    {
        epService.getEPAdministrator().getConfiguration().addEventTypeAlias("SupportBean", SupportBean.class);
        epService.getEPAdministrator().getConfiguration().addEventTypeAlias("SupportBean_A", SupportBean_A.class);

        ConfigurationVariantEventType variant = new ConfigurationVariantEventType();
        variant.addEventTypeAlias("SupportBean");
        variant.addEventTypeAlias("SupportBean_A");
        epService.getEPAdministrator().getConfiguration().addVariantEventType("MyVariantType", variant);

        EPStatement stmt = epService.getEPAdministrator().createEPL("select * from MyVariantType");
        stmt.addListener(listenerOne);

        epService.getEPAdministrator().createEPL("insert into MyVariantType select * from SupportBean");
        epService.getEPAdministrator().createEPL("insert into MyVariantType select * from SupportBean_A");

        Object eventOne = new SupportBean();
        epService.getEPRuntime().sendEvent(eventOne);
        assertSame(eventOne, listenerOne.assertOneGetNewAndReset().getUnderlying());

        Object eventTwo = new SupportBean_A("E1");
        epService.getEPRuntime().sendEvent(eventTwo);
        assertSame(eventTwo, listenerOne.assertOneGetNewAndReset().getUnderlying());
    }
}
