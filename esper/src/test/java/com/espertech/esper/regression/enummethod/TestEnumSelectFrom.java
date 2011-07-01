package com.espertech.esper.regression.enummethod;

import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportBean_ST0_Container;
import com.espertech.esper.support.bean.SupportCollection;
import com.espertech.esper.support.bean.lambda.LambdaAssertionUtil;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.Collection;
import java.util.Map;

public class TestEnumSelectFrom extends TestCase {

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp() {

        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("Bean", SupportBean_ST0_Container.class);
        config.addEventType("SupportCollection", SupportCollection.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testNew() {

        String eplFragment = "select " +
                "contained.selectFrom(x => new {c0 = id||'x', c1 = key0||'y'}) as val0 " +
                "from Bean";
        EPStatement stmtFragment = epService.getEPAdministrator().createEPL(eplFragment);
        stmtFragment.addListener(listener);
        LambdaAssertionUtil.assertTypes(stmtFragment.getEventType(), "val0".split(","), new Class[]{Collection.class});

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make3Value("E1,12,0", "E2,11,0", "E3,2,0"));
        ArrayAssertionUtil.assertPropsPerRow(toMapArray(listener.assertOneGetNewAndReset().get("val0")), "c0,c1".split(","),
                new Object[][] {{"E1x","12y"}, {"E2x","11y"}, {"E3x","2y"}});

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make3Value("E4,0,1"));
        ArrayAssertionUtil.assertPropsPerRow(toMapArray(listener.assertOneGetNewAndReset().get("val0")), "c0,c1".split(","),
                new Object[][] {{"E4x","0y"}});

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make3Value(null));
        ArrayAssertionUtil.assertPropsPerRow(toMapArray(listener.assertOneGetNewAndReset().get("val0")), "c0,c1".split(","), null);

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make3Value());
        ArrayAssertionUtil.assertPropsPerRow(toMapArray(listener.assertOneGetNewAndReset().get("val0")), "c0,c1".split(","),
                new Object[0][]);
    }

    private Map[] toMapArray(Object result) {
        if (result == null) {
            return null;
        }
        Collection<Map> val = (Collection<Map>) result;
        return val.toArray(new Map[val.size()]);
    }

    public void testSelect() {

        String eplFragment = "select " +
                "contained.selectFrom(x => id) as val0 " +
                "from Bean";
        EPStatement stmtFragment = epService.getEPAdministrator().createEPL(eplFragment);
        stmtFragment.addListener(listener);
        LambdaAssertionUtil.assertTypes(stmtFragment.getEventType(), "val0".split(","), new Class[]{Collection.class});

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value("E1,12", "E2,11", "E3,2"));
        LambdaAssertionUtil.assertValuesArrayScalar(listener, "val0", "E1", "E2", "E3");
        listener.reset();

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value(null));
        LambdaAssertionUtil.assertValuesArrayScalar(listener, "val0", null);
        listener.reset();

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value());
        LambdaAssertionUtil.assertValuesArrayScalar(listener, "val0", new String[0]);
        listener.reset();
    }

    public void testInvalid() {
        String epl;

        epl = "select strvals.selectFrom(x=> x) from SupportCollection";
        tryInvalid(epl, "Error starting statement: Invalid input for built-in enumeration method 'selectFrom' and 1-parameter footprint, expecting collection of events as input, received collection of String [select strvals.selectFrom(x=> x) from SupportCollection]");
    }

    private void tryInvalid(String epl, String message) {
        try
        {
            epService.getEPAdministrator().createEPL(epl);
            fail();
        }
        catch (EPStatementException ex) {
            assertEquals(message, ex.getMessage());
        }
    }
}
