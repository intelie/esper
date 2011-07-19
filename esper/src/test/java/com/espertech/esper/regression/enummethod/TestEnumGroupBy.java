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
import com.espertech.esper.support.bean.lambda.LambdaAssertionUtil;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class TestEnumGroupBy extends TestCase {

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp() {

        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("Bean", SupportBean_ST0_Container.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testKeySelectorOnly() {

        // - duplicate key allowed, creates a list of values
        // - null key & value allowed
        
        String eplFragment = "select contained.groupBy(c => id) as val from Bean";
        EPStatement stmtFragment = epService.getEPAdministrator().createEPL(eplFragment);
        stmtFragment.addListener(listener);
        LambdaAssertionUtil.assertTypes(stmtFragment.getEventType(), "val".split(","), new Class[]{Map.class});

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value("E1,1", "E1,2", "E2,5"));
        assertKeyObject(listener.assertOneGetNewAndReset(), "E1,E2", new String[]{"1,2", "5"});

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value(null));
        assertNull(listener.assertOneGetNewAndReset().get("val"));

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value());
        assertKeyObject(listener.assertOneGetNewAndReset(), "", new String[0]);
    }

    public void testKeyValueSelector() {

        String eplFragment = "select contained.groupBy(k => id, v => p00) as val from Bean";
        EPStatement stmtFragment = epService.getEPAdministrator().createEPL(eplFragment);
        stmtFragment.addListener(listener);

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value("E1,1", "E1,2", "E2,5"));
        assertKeyValue(listener.assertOneGetNewAndReset(), "E1,E2", new String[]{"1,2", "5"});

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value(null));
        assertNull(listener.assertOneGetNewAndReset().get("val"));

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value());
        assertKeyValue(listener.assertOneGetNewAndReset(), "", new String[0]);
    }

    private void assertKeyObject(EventBean eventBean, String keysList, String[] expectedList) {
        Map map = (Map) eventBean.get("val");
        if (keysList.isEmpty() && map.isEmpty()) {
            return;
        }

        String[] keys = keysList.split(",");
        assertEquals(map.size(), keys.length);

        for (int i = 0; i < keys.length; i++) {
            Collection value = (Collection) map.get(keys[i]);
            String[] itemsExpected = expectedList[i].split(",");
            assertEquals(itemsExpected.length, value.size());

            Iterator it = value.iterator();
            for (int j = 0; j < itemsExpected.length; j++) {
                int p00 = ((SupportBean_ST0) it.next()).getP00();
                assertEquals(itemsExpected[j], Integer.toString(p00));
            }
        }
    }

    private void assertKeyValue(EventBean eventBean, String keysList, String[] expectedList) {
        Map map = (Map) eventBean.get("val");
        if (keysList.isEmpty() && map.isEmpty()) {
            return;
        }

        String[] keys = keysList.split(",");
        assertEquals(map.size(), keys.length);

        for (int i = 0; i < keys.length; i++) {
            Collection value = (Collection) map.get(keys[i]);
            String[] itemsExpected = expectedList[i].split(",");
            assertEquals(itemsExpected.length, value.size());

            Iterator it = value.iterator();
            for (int j = 0; j < itemsExpected.length; j++) {
                int p00 = (Integer) it.next();
                assertEquals(itemsExpected[j], Integer.toString(p00));
            }
        }
    }
}
