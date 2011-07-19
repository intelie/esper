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
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.client.SupportConfigFactory;

public class TestJoinNoTableName extends TestCase
{
    private EPServiceProvider epService;
    private EPStatement joinView;
    private SupportUpdateListener updateListener;

    private Object[] setOne = new Object[5];
    private Object[] setTwo = new Object[5];

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
        updateListener = new SupportUpdateListener();

        String joinStatement = "select * from " +
                SupportMarketDataBean.class.getName() + ".win:length(3)," +
                SupportBean.class.getName() + ".win:length(3)" +
            " where symbol=string and volume=longBoxed";

        joinView = epService.getEPAdministrator().createEPL(joinStatement);
        joinView.addListener(updateListener);

        for (int i = 0; i < setOne.length; i++)
        {
            setOne[i] = new SupportMarketDataBean("IBM", 0, (long) i, "");

            SupportBean event = new SupportBean();
            event.setString("IBM");
            event.setLongBoxed((long)i);
            setTwo[i] = event;
        }
    }

    public void testJoinUniquePerId()
    {
        sendEvent(setOne[0]);
        sendEvent(setTwo[0]);
        assertNotNull(updateListener.getLastNewData());
    }

    private void sendEvent(Object event)
    {
        epService.getEPRuntime().sendEvent(event);
    }
}
