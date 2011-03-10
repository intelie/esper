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

import java.util.Collection;

public class TestEnumWhere extends TestCase {

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp() {

        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("Bean", SupportBean_ST0_Container.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testWhere() {

        String epl = "select " +
                "contained.where(x => p00 = 9) as val0," +
                "contained.where((x, i) => x.p00 = 9 and i >= 1) as val1 from Bean";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);
        LambdaAssertionUtil.assertTypes(stmt.getEventType(), "val0,val1".split(","), new Class[]{Collection.class, Collection.class});

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value("E1,1", "E2,9", "E3,1"));
        LambdaAssertionUtil.assertST0Id(listener, "val0", "E2");
        LambdaAssertionUtil.assertST0Id(listener, "val1", "E2");
        listener.reset();

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value("E1,9", "E2,1", "E3,1"));
        LambdaAssertionUtil.assertST0Id(listener, "val0", "E1");
        LambdaAssertionUtil.assertST0Id(listener, "val1", "");
        listener.reset();

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value("E1,1", "E2,1", "E3,9"));
        LambdaAssertionUtil.assertST0Id(listener, "val0", "E3");
        LambdaAssertionUtil.assertST0Id(listener, "val1", "E3");
        listener.reset();

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value(null));
        LambdaAssertionUtil.assertST0Id(listener, "val0", null);
        LambdaAssertionUtil.assertST0Id(listener, "val1", null);
        listener.reset();

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value());
        LambdaAssertionUtil.assertST0Id(listener, "val0", "");
        LambdaAssertionUtil.assertST0Id(listener, "val1", "");
        listener.reset();
    }
}
