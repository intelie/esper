package com.espertech.esper.regression.enummethod;

import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportCollection;
import com.espertech.esper.support.bean.lambda.LambdaAssertionUtil;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

public class TestEnumSequenceEquals extends TestCase {

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp() {

        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportCollection", SupportCollection.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testSelect() {

        String[] fields = "val0".split(",");
        String eplFragment = "select " +
                "strvals.sequenceEquals(strvalstwo) as val0 " +
                "from SupportCollection";
        EPStatement stmtFragment = epService.getEPAdministrator().createEPL(eplFragment);
        stmtFragment.addListener(listener);
        LambdaAssertionUtil.assertTypes(stmtFragment.getEventType(), "val0".split(","), new Class[]{Boolean.class});

        epService.getEPRuntime().sendEvent(SupportCollection.makeString("E1,E2,E3", "E1,E2,E3"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[]{true});

        epService.getEPRuntime().sendEvent(SupportCollection.makeString("E1,E3", "E1,E2,E3"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[]{false});

        epService.getEPRuntime().sendEvent(SupportCollection.makeString("E1,E3", "E1,E3"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[]{true});

        epService.getEPRuntime().sendEvent(SupportCollection.makeString("E1,E2,E3", "E1,E3"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[]{false});

        epService.getEPRuntime().sendEvent(SupportCollection.makeString("E1,E2,null,E3", "E1,E2,null,E3"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[]{true});

        epService.getEPRuntime().sendEvent(SupportCollection.makeString("E1,E2,E3", "E1,E2,null"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[]{false});

        epService.getEPRuntime().sendEvent(SupportCollection.makeString("E1,E2,null", "E1,E2,E3"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[]{false});

        epService.getEPRuntime().sendEvent(SupportCollection.makeString("E1", ""));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[]{false});

        epService.getEPRuntime().sendEvent(SupportCollection.makeString("", "E1"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[]{false});

        epService.getEPRuntime().sendEvent(SupportCollection.makeString("E1", "E1"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[]{true});

        epService.getEPRuntime().sendEvent(SupportCollection.makeString("", ""));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[]{true});

        epService.getEPRuntime().sendEvent(SupportCollection.makeString(null, ""));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[]{null});

        epService.getEPRuntime().sendEvent(SupportCollection.makeString("", null));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[]{false});

        epService.getEPRuntime().sendEvent(SupportCollection.makeString(null, null));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[]{null});
    }

    public void testInvalid() {
        String epl;

        epl = "select window(*).sequenceEquals(strvals) from SupportCollection.std:lastevent()";
        tryInvalid(epl, "Error starting statement: Invalid input for built-in enumeration method 'sequenceEquals' and 1-parameter footprint, expecting collection of values (typically scalar values) as input, received collecton of events of type 'SupportCollection' [select window(*).sequenceEquals(strvals) from SupportCollection.std:lastevent()]");
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
