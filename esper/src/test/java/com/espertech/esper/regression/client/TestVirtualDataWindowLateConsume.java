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

package com.espertech.esper.regression.client;

import com.espertech.esper.client.*;
import com.espertech.esper.client.hook.VirtualDataWindow;
import com.espertech.esper.client.hook.VirtualDataWindowKeyRange;
import com.espertech.esper.client.hook.VirtualDataWindowLookupContext;
import com.espertech.esper.client.hook.VirtualDataWindowLookupFieldDesc;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBeanRange;
import com.espertech.esper.support.bean.SupportBean_ST0;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.virtualdw.SupportVirtualDW;
import com.espertech.esper.support.virtualdw.SupportVirtualDWFactory;
import com.espertech.esper.support.virtualdw.SupportVirtualDWInvalidFactory;
import junit.framework.TestCase;

import javax.naming.NamingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestVirtualDataWindowLateConsume extends TestCase {

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        listener = new SupportUpdateListener();

        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.addPlugInVirtualDataWindow("test", "vdw", SupportVirtualDWFactory.class.getName(), SupportVirtualDW.ITERATE);    // configure with iteration
        configuration.addEventType("SupportBean", SupportBean.class);
        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();
    }

    public void testInsertConsume() {

        epService.getEPAdministrator().createEPL("create window MyVDW.test:vdw() as SupportBean");
        SupportVirtualDW window = (SupportVirtualDW) getFromContext("/virtualdw/MyVDW");
        SupportBean supportBean = new SupportBean("S1", 100);
        window.setData(Collections.singleton(supportBean));
        epService.getEPAdministrator().createEPL("insert into MyVDW select * from SupportBean");

        // test aggregated consumer - wherein the virtual data window does not return an iterator that prefills the aggregation state
        String[] fields = "val0".split(",");
        EPStatement stmtAggregate = epService.getEPAdministrator().createEPL("select sum(intPrimitive) as val0 from MyVDW");
        stmtAggregate.addListener(listener);
        ArrayAssertionUtil.assertProps(stmtAggregate.iterator().next(), fields, new Object[] {100});

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 10));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {110});

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 20));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {130});
    }

    private VirtualDataWindow getFromContext(String name) {
        try {
            return (VirtualDataWindow) epService.getContext().lookup(name);
        }
        catch (NamingException e) {
            throw new RuntimeException("Name '" + name + "' could not be looked up");
        }
    }
}
