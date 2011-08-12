/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.regression.enummethod;

import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportBean_ST0;
import com.espertech.esper.support.bean.SupportBean_ST0_Container;
import com.espertech.esper.support.bean.SupportCollection;
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
        config.addEventType("SupportCollection", SupportCollection.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testMinMaxBy() {

        String[] fields = "val0,val1,val2,val3".split(",");
        String eplFragment = "select " +
                "contained.minBy(x => p00) as val0," +
                "contained.maxBy(x => p00) as val1," +
                "contained.minBy(x => p00).id as val2," +
                "contained.maxBy(x => p00).p00 as val3 " +
                "from Bean";
        EPStatement stmtFragment = epService.getEPAdministrator().createEPL(eplFragment);
        stmtFragment.addListener(listener);
        LambdaAssertionUtil.assertTypes(stmtFragment.getEventType(), fields, new Class[]{SupportBean_ST0.class, SupportBean_ST0.class, String.class, Integer.class});

        SupportBean_ST0_Container bean = SupportBean_ST0_Container.make2Value("E1,12", "E2,11", "E2,2");
        epService.getEPRuntime().sendEvent(bean);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields,
                new Object[]{bean.getContained().get(2), bean.getContained().get(0), "E2", 12});

        bean = SupportBean_ST0_Container.make2Value("E1,12");
        epService.getEPRuntime().sendEvent(bean);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields,
                new Object[]{bean.getContained().get(0), bean.getContained().get(0), "E1", 12});

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value(null));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields,
                new Object[]{null, null, null, null});

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value());
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields,
                new Object[]{null, null, null, null});
    }

    public void testInvalid() {
        String epl;

        epl = "select strvals.minby(x => x != 'E1') from SupportCollection";
        tryInvalid(epl, "Error starting statement: Invalid input for built-in enumeration method 'minby' and 1-parameter footprint, expecting collection of events as input, received collection of String [select strvals.minby(x => x != 'E1') from SupportCollection]");
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
