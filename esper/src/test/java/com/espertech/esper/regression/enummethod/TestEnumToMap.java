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
import com.espertech.esper.support.bean.lambda.LambdaAssertionUtil;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class TestEnumToMap extends TestCase {

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp() {

        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("Bean", SupportBean_ST0_Container.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testToMap() {

        // - duplicate value allowed, latest value wins
        // - null key & value allowed
        
        String eplFragment = "select contained.toMap(c => id, c=> p00) as val from Bean";
        EPStatement stmtFragment = epService.getEPAdministrator().createEPL(eplFragment);
        stmtFragment.addListener(listener);
        LambdaAssertionUtil.assertTypes(stmtFragment.getEventType(), "val".split(","), new Class[]{Map.class});

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value("E1,1", "E3,12", "E2,5"));
        ArrayAssertionUtil.assertPropsMap((Map) listener.assertOneGetNewAndReset().get("val"), "E1,E2,E3".split(","), new Object[] {1,5,12});

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value("E1,1", "E3,12", "E2,12", "E1,2"));
        ArrayAssertionUtil.assertPropsMap((Map) listener.assertOneGetNewAndReset().get("val"), "E1,E2,E3".split(","), new Object[]{2, 12, 12});

        epService.getEPRuntime().sendEvent(new SupportBean_ST0_Container(Collections.singletonList(new SupportBean_ST0(null, null))));
        ArrayAssertionUtil.assertPropsMap((Map) listener.assertOneGetNewAndReset().get("val"), "E1,E2,E3".split(","), new Object[]{null, null, null});
    }
}
