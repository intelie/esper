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
import com.espertech.esper.support.bean.SupportBean_ST0_Container;
import com.espertech.esper.support.bean.SupportCollection;
import com.espertech.esper.support.bean.lambda.LambdaAssertionUtil;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.Collection;

public class TestEnumOrderBy extends TestCase {

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

    public void testOrderByEvents() {

        String[] fields = "val0,val1,val2,val3,val4,val5".split(",");
        String eplFragment = "select " +
                "contained.orderBy(x => p00) as val0," +
                "contained.orderBy(x => 10 - p00) as val1," +
                "contained.orderBy(x => 0) as val2," +
                "contained.orderByDesc(x => p00) as val3," +
                "contained.orderByDesc(x => 10 - p00) as val4," +
                "contained.orderByDesc(x => 0) as val5" +
                " from Bean";
        EPStatement stmtFragment = epService.getEPAdministrator().createEPL(eplFragment);
        stmtFragment.addListener(listener);
        LambdaAssertionUtil.assertTypes(stmtFragment.getEventType(), fields, new Class[]{Collection.class, Collection.class, Collection.class, Collection.class, Collection.class, Collection.class});

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value("E1,1", "E2,2"));
        LambdaAssertionUtil.assertST0Id(listener, "val0", "E1,E2");
        LambdaAssertionUtil.assertST0Id(listener, "val1", "E2,E1");
        LambdaAssertionUtil.assertST0Id(listener, "val2", "E1,E2");
        LambdaAssertionUtil.assertST0Id(listener, "val3", "E2,E1");
        LambdaAssertionUtil.assertST0Id(listener, "val4", "E1,E2");
        LambdaAssertionUtil.assertST0Id(listener, "val5", "E1,E2");
        listener.reset();

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value("E3,1", "E2,2", "E4,1", "E1,2"));
        LambdaAssertionUtil.assertST0Id(listener, "val0", "E3,E4,E2,E1");
        LambdaAssertionUtil.assertST0Id(listener, "val1", "E2,E1,E3,E4");
        LambdaAssertionUtil.assertST0Id(listener, "val2", "E3,E2,E4,E1");
        LambdaAssertionUtil.assertST0Id(listener, "val3", "E2,E1,E3,E4");
        LambdaAssertionUtil.assertST0Id(listener, "val4", "E3,E4,E2,E1");
        LambdaAssertionUtil.assertST0Id(listener, "val5", "E3,E2,E4,E1");
        listener.reset();

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value(null));
        for (String field : fields) {
            LambdaAssertionUtil.assertST0Id(listener, field, null);
        }
        listener.reset();

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value());
        for (String field : fields) {
            LambdaAssertionUtil.assertST0Id(listener, field, "");
        }
        listener.reset();
    }

    public void testOrderByScalar() {

        String[] fields = "val0,val1".split(",");
        String eplFragment = "select " +
                "strvals.orderBy() as val0, " +
                "strvals.orderByDesc() as val1 " +
                "from SupportCollection";
        EPStatement stmtFragment = epService.getEPAdministrator().createEPL(eplFragment);
        stmtFragment.addListener(listener);
        LambdaAssertionUtil.assertTypes(stmtFragment.getEventType(), fields, new Class[]{Collection.class, Collection.class});

        epService.getEPRuntime().sendEvent(SupportCollection.makeString("E2,E1,E5,E4"));
        LambdaAssertionUtil.assertValuesArrayScalar(listener, "val0", "E1", "E2", "E4", "E5");
        LambdaAssertionUtil.assertValuesArrayScalar(listener, "val1", "E5", "E4", "E2", "E1");
        listener.reset();

        epService.getEPRuntime().sendEvent(SupportCollection.makeString("E1"));
        LambdaAssertionUtil.assertValuesArrayScalar(listener, "val0", "E1");
        LambdaAssertionUtil.assertValuesArrayScalar(listener, "val1", "E1");
        listener.reset();

        epService.getEPRuntime().sendEvent(SupportCollection.makeString(null));
        LambdaAssertionUtil.assertValuesArrayScalar(listener, "val0", null);
        LambdaAssertionUtil.assertValuesArrayScalar(listener, "val1", null);
        listener.reset();

        epService.getEPRuntime().sendEvent(SupportCollection.makeString(""));
        LambdaAssertionUtil.assertValuesArrayScalar(listener, "val0");
        LambdaAssertionUtil.assertValuesArrayScalar(listener, "val1");
    }

    public void testInvalid() {
        String epl;

        epl = "select strvals.orderBy(x=> x) from SupportCollection";
        tryInvalid(epl, "Error starting statement: Invalid input for built-in enumeration method 'orderBy' and 1-parameter footprint, expecting collection of events as input, received collection of String [select strvals.orderBy(x=> x) from SupportCollection]");

        epl = "select contained.orderBy() from Bean";
        tryInvalid(epl, "Error starting statement: Invalid input for built-in enumeration method 'orderBy' and 0-parameter footprint, expecting collection of values (typically scalar values) as input, received collection of events of type 'com.espertech.esper.support.bean.SupportBean_ST0' [select contained.orderBy() from Bean]");
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
