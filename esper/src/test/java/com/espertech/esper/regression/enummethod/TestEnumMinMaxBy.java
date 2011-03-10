package com.espertech.esper.regression.enummethod;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.support.bean.SupportBean_ST0;
import com.espertech.esper.support.bean.SupportBean_ST0_Container;
import com.espertech.esper.support.bean.lambda.LambdaAssertionUtil;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

public class TestEnumMinMaxBy extends TestCase {

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp() {

        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("Bean", SupportBean_ST0_Container.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testMinMaxBy() {

        String[] fields = "val0,val1".split(",");
        String eplFragment = "select " +
                "contained.minBy(x => p00) as val0," +
                "contained.maxBy(x => p00) as val1 " +
                "from Bean";
        EPStatement stmtFragment = epService.getEPAdministrator().createEPL(eplFragment);
        stmtFragment.addListener(listener);
        LambdaAssertionUtil.assertTypes(stmtFragment.getEventType(), fields, new Class[]{SupportBean_ST0.class, SupportBean_ST0.class});

        SupportBean_ST0_Container bean = SupportBean_ST0_Container.make2Value("E1,12", "E2,11", "E2,2");
        epService.getEPRuntime().sendEvent(bean);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields,
                new Object[]{bean.getContained().get(2), bean.getContained().get(0)});

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value(null));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields,
                new Object[]{null, null});

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value());
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields,
                new Object[]{null, null});
    }
}
