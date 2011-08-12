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

package com.espertech.esper.regression.view;

import junit.framework.TestCase;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestDataWindowMultipleExpiry extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        listener = new SupportUpdateListener();
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getViewResources().setAllowMultipleExpiryPolicies(true);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
    }

    public void testTimeViewUnique()
    {
        // Testing the two forms of the case expression
        // Furthermore the test checks the different when clauses and actions related.
        String caseExpr = "select volume " +
                "from " +  SupportMarketDataBean.class.getName() + ".std:unique(symbol).win:time(10)";

        EPStatement stmt = epService.getEPAdministrator().createEPL(caseExpr);
        stmt.addListener(listener);
        sendMarketDataEvent("DELL", 1, 50);
        sendMarketDataEvent("DELL", 2, 50);
        Object[] values = ArrayAssertionUtil.iteratorToArray(stmt.iterator());
        assertEquals(1, values.length);
    }

    private void sendMarketDataEvent(String symbol, long volume, double price)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, volume, null);
        epService.getEPRuntime().sendEvent(bean);
    }

    private static final Log log = LogFactory.getLog(TestDataWindowMultipleExpiry.class);
}
