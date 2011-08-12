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

import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.support.bean.SupportBean;
import junit.framework.TestCase;

public class TestInitializeEngine extends TestCase
{
    public void testInitialize()
    {
        Configuration config = new Configuration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        EPServiceProvider epService = EPServiceProviderManager.getProvider("TestInitializeEngine", config);

        String eplOne = "insert into A(a) select 1 from " + SupportBean.class.getName() + ".win:length(100)";
        String eplTwo = "insert into A(a, b) select 1,2 from " + SupportBean.class.getName() + ".win:length(100)";

        // Asserting that the engine allows to use the new event stream A with more properties then the old A
        epService.getEPAdministrator().createEPL(eplOne);
        epService.initialize();
        epService.getEPAdministrator().createEPL(eplTwo);
    }
}
