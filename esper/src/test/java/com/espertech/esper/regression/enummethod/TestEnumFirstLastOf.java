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

public class TestEnumFirstLastOf extends TestCase {

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp() {

        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("Bean", SupportBean_ST0_Container.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    // TODO test invalid chain: selectFrom(...).where(...)
    //

    public void testFirstLastProperty() {

        String[] fields = "val0,val1".split(",");
        String eplFragment = "select " +
                "contained.firstOf().p00 as val0, " +
                "contained.lastOf().p00 as val1 " +
                " from Bean";
        EPStatement stmtFragment = epService.getEPAdministrator().createEPL(eplFragment);
        stmtFragment.addListener(listener);
        LambdaAssertionUtil.assertTypes(stmtFragment.getEventType(), fields, new Class[]{Integer.class, Integer.class});

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value("E1,1", "E2,9", "E3,3"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {1, 3});

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value("E1,1"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {1, 1});

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value(null));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, null});

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value());
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, null});
    }

    public void testFirstLastNoPred() {

        String eplFragment = "select " +
                "contained.firstOf() as val0, " +
                "contained.lastOf() as val1 " +
                " from Bean";
        EPStatement stmtFragment = epService.getEPAdministrator().createEPL(eplFragment);
        stmtFragment.addListener(listener);
        LambdaAssertionUtil.assertTypes(stmtFragment.getEventType(), "val0,val1".split(","), new Class[]{SupportBean_ST0.class, SupportBean_ST0.class});

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value("E1,1", "E3,9", "E2,9"));
        assertId(listener, "val0", "E1");
        assertId(listener, "val1", "E2");
        listener.reset();

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value("E2,2"));
        assertId(listener, "val0", "E2");
        assertId(listener, "val1", "E2");
        listener.reset();

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value(null));
        assertNull(listener.assertOneGetNew().get("val0"));
        assertNull(listener.assertOneGetNewAndReset().get("val1"));

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value());
        assertNull(listener.assertOneGetNew().get("val0"));
        assertNull(listener.assertOneGetNewAndReset().get("val1"));
    }

    public void testFirstLastPredicate() {

        String eplFragment = "select contained.firstOf(x => p00 = 9) as val from Bean";
        EPStatement stmtFragment = epService.getEPAdministrator().createEPL(eplFragment);
        stmtFragment.addListener(listener);
        LambdaAssertionUtil.assertTypes(stmtFragment.getEventType(), "val".split(","), new Class[]{SupportBean_ST0.class});

        SupportBean_ST0_Container bean = SupportBean_ST0_Container.make2Value("E1,1", "E2,9", "E2,9");
        epService.getEPRuntime().sendEvent(bean);
        SupportBean_ST0 result = (SupportBean_ST0) listener.assertOneGetNewAndReset().get("val");
        assertSame(result, bean.getContained().get(1));

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value(null));
        assertNull(listener.assertOneGetNewAndReset().get("val"));

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value());
        assertNull(listener.assertOneGetNewAndReset().get("val"));

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value("E1,1", "E2,1", "E2,1"));
        assertNull(listener.assertOneGetNewAndReset().get("val"));
    }

    private void assertId(SupportUpdateListener listener, String property, String id) {
        SupportBean_ST0 result = (SupportBean_ST0) listener.assertOneGetNew().get(property);
        assertEquals(id, result.getId());
    }
}
