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
import com.espertech.esper.support.bean.SupportBean_ST0;
import com.espertech.esper.support.bean.SupportBean_ST0_Container;
import com.espertech.esper.support.bean.SupportCollection;
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
        config.addEventType("SupportCollection", SupportCollection.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testFirstLastScalar() {

        String[] fields = "val0,val1,val2,val3".split(",");
        String eplFragment = "select " +
                "strvals.firstOf() as val0, " +
                "strvals.lastOf() as val1, " +
                "strvals.firstOf(x => x like '%1%') as val2, " +
                "strvals.lastOf(x => x like '%1%') as val3 " +
                " from SupportCollection";
        EPStatement stmtFragment = epService.getEPAdministrator().createEPL(eplFragment);
        stmtFragment.addListener(listener);
        LambdaAssertionUtil.assertTypes(stmtFragment.getEventType(), fields, new Class[]{String.class, String.class, String.class, String.class});

        epService.getEPRuntime().sendEvent(SupportCollection.makeString("E1,E2,E3"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1", "E3", "E1", "E1"});

        epService.getEPRuntime().sendEvent(SupportCollection.makeString("E1"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1", "E1", "E1", "E1"});

        epService.getEPRuntime().sendEvent(SupportCollection.makeString("E2,E3,E4"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E2", "E4", null, null});

        epService.getEPRuntime().sendEvent(SupportCollection.makeString(""));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, null, null, null});

        epService.getEPRuntime().sendEvent(SupportCollection.makeString(null));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, null, null, null});
    }

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
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[]{1, 3});

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value("E1,1"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[]{1, 1});

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value(null));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[]{null, null});

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value());
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[]{null, null});
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
