package com.espertech.esper.regression.enummethod;

import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportBean_ST0_Container;
import com.espertech.esper.support.bean.lambda.LambdaAssertionUtil;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

public class TestEnumAllOf extends TestCase {

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp() {

        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("Bean", SupportBean_ST0_Container.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testAllOf() {

        String eplFragment = "select contained.allof(x => p00 = 12) as val from Bean";
        EPStatement stmtFragment = epService.getEPAdministrator().createEPL(eplFragment);
        stmtFragment.addListener(listener);
        LambdaAssertionUtil.assertTypes(stmtFragment.getEventType(), "val".split(","), new Class[]{Boolean.class});

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value("E1,1", "E2,12", "E2,2"));
        assertEquals(false, listener.assertOneGetNewAndReset().get("val"));

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value(null));
        assertEquals(null, listener.assertOneGetNewAndReset().get("val"));

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value("E1,12", "E2,12", "E2,12"));
        assertEquals(true, listener.assertOneGetNewAndReset().get("val"));

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value());
        assertEquals(true, listener.assertOneGetNewAndReset().get("val"));
    }
}
