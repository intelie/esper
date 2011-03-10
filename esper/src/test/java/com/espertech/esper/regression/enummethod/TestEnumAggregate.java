package com.espertech.esper.regression.enummethod;

import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportBean_ST0_Container;
import com.espertech.esper.support.bean.lambda.LambdaAssertionUtil;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.StringTokenizer;

public class TestEnumAggregate extends TestCase {

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp() {

        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("Bean", SupportBean_ST0_Container.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testAggregate() {

        String[] fields = new String[] {"val0", "val1", "val2"};
        String eplFragment = "select " +
                "contained.aggregate(0, (result, item) => result + item.p00) as val0, " +
                "contained.aggregate('', (result, item) => result || ', ' || item.id) as val1, " +
                "contained.aggregate('', (result, item) => result || (case when result='' then '' else ',' end) || item.id) as val2 " +
                " from Bean";
        EPStatement stmtFragment = epService.getEPAdministrator().createEPL(eplFragment);
        stmtFragment.addListener(listener);
        LambdaAssertionUtil.assertTypes(stmtFragment.getEventType(), fields, new Class[] {Integer.class, String.class, String.class});

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value("E1,12", "E2,11", "E2,2"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields,
                new Object[] {25, ", E1, E2, E2", "E1,E2,E2"});

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value(null));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields,
                new Object[] {null, null, null});

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value(new String[0]));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields,
                new Object[] {0, "", ""});

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value("E1,12"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields,
                new Object[]{12, ", E1", "E1"});

    }
}
