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

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.support.bean.SupportBean_ST0_Container;
import com.espertech.esper.support.bean.SupportCollection;
import com.espertech.esper.support.bean.lambda.LambdaAssertionUtil;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.Collection;

public class TestEnumTakeAndTakeLast extends TestCase {

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

    public void testTakeEvents() {

        String[] fields = "val0,val1,val2,val3,val4,val5".split(",");
        String epl = "select " +
                "contained.take(2) as val0," +
                "contained.take(1) as val1," +
                "contained.take(0) as val2," +
                "contained.take(-1) as val3," +
                "contained.takeLast(2) as val4," +
                "contained.takeLast(1) as val5" +
                " from Bean";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);
        LambdaAssertionUtil.assertTypes(stmt.getEventType(), fields, new Class[]{Collection.class, Collection.class, Collection.class, Collection.class, Collection.class, Collection.class});

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value("E1,1", "E2,2", "E3,3"));
        LambdaAssertionUtil.assertST0Id(listener, "val0", "E1,E2");
        LambdaAssertionUtil.assertST0Id(listener, "val1", "E1");
        LambdaAssertionUtil.assertST0Id(listener, "val2", "");
        LambdaAssertionUtil.assertST0Id(listener, "val3", "");
        LambdaAssertionUtil.assertST0Id(listener, "val4", "E2,E3");
        LambdaAssertionUtil.assertST0Id(listener, "val5", "E3");
        listener.reset();

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value("E1,1", "E2,2"));
        LambdaAssertionUtil.assertST0Id(listener, "val0", "E1,E2");
        LambdaAssertionUtil.assertST0Id(listener, "val1", "E1");
        LambdaAssertionUtil.assertST0Id(listener, "val2", "");
        LambdaAssertionUtil.assertST0Id(listener, "val3", "");
        LambdaAssertionUtil.assertST0Id(listener, "val4", "E1,E2");
        LambdaAssertionUtil.assertST0Id(listener, "val5", "E2");
        listener.reset();

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value("E1,1"));
        LambdaAssertionUtil.assertST0Id(listener, "val0", "E1");
        LambdaAssertionUtil.assertST0Id(listener, "val1", "E1");
        LambdaAssertionUtil.assertST0Id(listener, "val2", "");
        LambdaAssertionUtil.assertST0Id(listener, "val3", "");
        LambdaAssertionUtil.assertST0Id(listener, "val4", "E1");
        LambdaAssertionUtil.assertST0Id(listener, "val5", "E1");
        listener.reset();

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value());
        for (String field : fields) {
            LambdaAssertionUtil.assertST0Id(listener, field, "");
        }
        listener.reset();

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value(null));
        for (String field : fields) {
            LambdaAssertionUtil.assertST0Id(listener, field, null);
        }
        listener.reset();
    }

    public void testTakeScalar() {

        String[] fields = "val0,val1,val2,val3".split(",");
        String epl = "select " +
                "strvals.take(2) as val0," +
                "strvals.take(1) as val1," +
                "strvals.takeLast(2) as val2," +
                "strvals.takeLast(1) as val3" +
                " from SupportCollection";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);
        LambdaAssertionUtil.assertTypes(stmt.getEventType(), fields, new Class[]{Collection.class, Collection.class, Collection.class, Collection.class});

        epService.getEPRuntime().sendEvent(SupportCollection.makeString("E1,E2,E3"));
        LambdaAssertionUtil.assertValuesArrayScalar(listener, "val0", "E1", "E2");
        LambdaAssertionUtil.assertValuesArrayScalar(listener, "val1", "E1");
        LambdaAssertionUtil.assertValuesArrayScalar(listener, "val2", "E2", "E3");
        LambdaAssertionUtil.assertValuesArrayScalar(listener, "val3", "E3");
        listener.reset();

        epService.getEPRuntime().sendEvent(SupportCollection.makeString("E1,E2"));
        LambdaAssertionUtil.assertValuesArrayScalar(listener, "val0", "E1", "E2");
        LambdaAssertionUtil.assertValuesArrayScalar(listener, "val1", "E1");
        LambdaAssertionUtil.assertValuesArrayScalar(listener, "val2", "E1", "E2");
        LambdaAssertionUtil.assertValuesArrayScalar(listener, "val3", "E2");
        listener.reset();

        epService.getEPRuntime().sendEvent(SupportCollection.makeString("E1"));
        LambdaAssertionUtil.assertValuesArrayScalar(listener, "val0", "E1");
        LambdaAssertionUtil.assertValuesArrayScalar(listener, "val1", "E1");
        LambdaAssertionUtil.assertValuesArrayScalar(listener, "val2", "E1");
        LambdaAssertionUtil.assertValuesArrayScalar(listener, "val3", "E1");
        listener.reset();

        epService.getEPRuntime().sendEvent(SupportCollection.makeString(""));
        for (String field : fields) {
            LambdaAssertionUtil.assertValuesArrayScalar(listener, field);
        }
        listener.reset();

        epService.getEPRuntime().sendEvent(SupportCollection.makeString(null));
        for (String field : fields) {
            LambdaAssertionUtil.assertValuesArrayScalar(listener, field, null);
        }
        listener.reset();
    }
}
