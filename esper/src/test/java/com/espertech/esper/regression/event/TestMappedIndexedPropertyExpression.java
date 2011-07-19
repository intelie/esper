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

package com.espertech.esper.regression.event;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBeanComplexProps;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TestMappedIndexedPropertyExpression extends TestCase {

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
        listener = new SupportUpdateListener();
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBeanComplexProps", SupportBeanComplexProps.class);
    }

    public void testBeanMapWrap()
    {
        // test bean-type
        String eplBeans = "select " +
                "mapped(string) as val0, " +
                "indexed(intPrimitive) as val1 " +
                "from SupportBeanComplexProps.std:lastevent(), SupportBean sb unidirectional";
        runAssertionBean(eplBeans);

        // test bean-type prefixed
        String eplBeansPrefixed = "select " +
                "sbcp.mapped(string) as val0, " +
                "sbcp.indexed(intPrimitive) as val1 " +
                "from SupportBeanComplexProps.std:lastevent() sbcp, SupportBean sb unidirectional";
        runAssertionBean(eplBeansPrefixed);

        // test wrap
        epService.getEPAdministrator().createEPL("insert into SecondStream select 'a' as val0, * from SupportBeanComplexProps");

        String eplWrap = "select " +
                "mapped(string) as val0," +
                "indexed(intPrimitive) as val1 " +
                "from SecondStream .std:lastevent(), SupportBean unidirectional";
        runAssertionBean(eplWrap);

        String eplWrapPrefixed = "select " +
                "sbcp.mapped(string) as val0," +
                "sbcp.indexed(intPrimitive) as val1 " +
                "from SecondStream .std:lastevent() sbcp, SupportBean unidirectional";
        runAssertionBean(eplWrapPrefixed);

        // test Map-type
        Map<String, Object> def = new HashMap<String, Object>();
        def.put("mapped", new HashMap());
        def.put("indexed", int[].class);
        epService.getEPAdministrator().getConfiguration().addEventType("MapEvent", def);

        String eplMap = "select " +
                "mapped(string) as val0," +
                "indexed(intPrimitive) as val1 " +
                "from MapEvent.std:lastevent(), SupportBean unidirectional";
        runAssertionMap(eplMap);

        String eplMapPrefixed = "select " +
                "sbcp.mapped(string) as val0," +
                "sbcp.indexed(intPrimitive) as val1 " +
                "from MapEvent.std:lastevent() sbcp, SupportBean unidirectional";
        runAssertionMap(eplMapPrefixed);
    }

    private void runAssertionMap(String epl) {
        EPStatement stmtMap = epService.getEPAdministrator().createEPL(epl);
        stmtMap.addListener(listener);

        epService.getEPRuntime().sendEvent(makeMapEvent(), "MapEvent");
        epService.getEPRuntime().sendEvent(new SupportBean("keyOne", 1));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "val0,val1".split(","), new Object[]{"valueOne", 2});
        stmtMap.destroy();
    }

    private void runAssertionBean(String epl) {
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(SupportBeanComplexProps.makeDefaultBean());
        epService.getEPRuntime().sendEvent(new SupportBean("keyOne", 1));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "val0,val1".split(","), new Object[]{"valueOne", 2});
        stmt.destroy();
    }

    private Map<String, Object> makeMapEvent() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("mapped", Collections.singletonMap("keyOne", "valueOne"));
        map.put("indexed", new int[] {1, 2});
        return map;
    }
}
