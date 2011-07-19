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

package com.espertech.esper.regression.epl;

import junit.framework.TestCase;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.client.EventBean;

public class TestLiteralConstants extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener updateListener;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportBean", SupportBean.class.getName());
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        updateListener = new SupportUpdateListener();
    }

    public void testLiteral()
    {
        String statement = "select 0x23 as mybyte, " +
                           "'\u0041' as myunicode " +
                           "from SupportBean";

        EPStatement stmt = epService.getEPAdministrator().createEPL(statement);
        stmt.addListener(updateListener);

        epService.getEPRuntime().sendEvent(new SupportBean("e1", 100));

        EventBean event = updateListener.assertOneGetNewAndReset();
        assertEquals((byte) 35, event.get("mybyte"));
        assertEquals("A", event.get("myunicode"));
    }
}
