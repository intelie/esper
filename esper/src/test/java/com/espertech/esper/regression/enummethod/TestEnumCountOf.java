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
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

public class TestEnumCountOf extends TestCase {

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

    public void testCountOfEvents() {

        String[] fields = new String[] {"val0", "val1"};
        String eplFragment = "select " +
                "contained.countof(x=> x.p00 = 9) as val0, " +
                "contained.countof() as val1 " +
                " from Bean";
        EPStatement stmtFragment = epService.getEPAdministrator().createEPL(eplFragment);
        stmtFragment.addListener(listener);
        LambdaAssertionUtil.assertTypes(stmtFragment.getEventType(), fields, new Class[]{Integer.class, Integer.class});

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value("E1,1", "E2,9", "E2,9"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields,
                new Object[] {2, 3});

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value(null));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields,
                new Object[] {null, null});

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value(new String[0]));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields,
                new Object[] {0, 0});

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value("E1,9"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields,
                new Object[]{1, 1});

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value("E1,1"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields,
                new Object[]{0, 1});
    }

    public void testCountOfScalar() {

        String[] fields = new String[] {"val0","val1"};
        String eplFragment = "select " +
                "strvals.countof() as val0, " +
                "strvals.countof(x => x = 'E1') as val1 " +
                " from SupportCollection";
        EPStatement stmtFragment = epService.getEPAdministrator().createEPL(eplFragment);
        stmtFragment.addListener(listener);
        LambdaAssertionUtil.assertTypes(stmtFragment.getEventType(), fields, new Class[]{Integer.class, Integer.class});

        epService.getEPRuntime().sendEvent(SupportCollection.makeString("E1,E2"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {2, 1});

        epService.getEPRuntime().sendEvent(SupportCollection.makeString("E1,E2,E1,E3"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {4, 2});
    }
}
