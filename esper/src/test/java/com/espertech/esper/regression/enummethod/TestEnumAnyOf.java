package com.espertech.esper.regression.enummethod;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.support.bean.SupportBean_ST0_Container;
import com.espertech.esper.support.bean.lambda.LambdaAssertionUtil;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

public class TestEnumAnyOf extends TestCase {

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp() {

        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("Bean", SupportBean_ST0_Container.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testAnyOf() {

        String eplFragment = "select contained.anyof(x => p00 = 12) as val from Bean";
        EPStatement stmtFragment = epService.getEPAdministrator().createEPL(eplFragment);
        stmtFragment.addListener(listener);
        LambdaAssertionUtil.assertTypes(stmtFragment.getEventType(), "val".split(","), new Class[]{Boolean.class});

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value("E1,1", "E2,12", "E2,2"));
        assertEquals(true, listener.assertOneGetNewAndReset().get("val"));

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value("E1,1", "E2,11", "E2,2"));
        assertEquals(false, listener.assertOneGetNewAndReset().get("val"));

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value(null));
        assertEquals(null, listener.assertOneGetNewAndReset().get("val"));

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value());
        assertEquals(false, listener.assertOneGetNewAndReset().get("val"));
    }
}
