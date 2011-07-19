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

import com.espertech.esper.client.*;
import com.espertech.esper.client.soda.EPStatementObjectModel;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.TimerTask;

public class TestAggregateExtNTh extends TestCase {

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        listener = new SupportUpdateListener();
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportBean", SupportBean.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
    }

    public void testNth()
    {
        String epl = "select " +
                "string, " +
                "nth(intPrimitive, 0) as int1, " +  // current
                "nth(intPrimitive, 1) as int2 " +   // one before
                "from SupportBean.win:keepall() group by string output last every 3 events order by string";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        runAssertion();

        stmt.destroy();
        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(epl);
        stmt = epService.getEPAdministrator().create(model);
        stmt.addListener(listener);
        assertEquals(epl, model.toEPL());

        runAssertion();
        
        tryInvalid("select nth() from SupportBean",
                "Error starting statement: The nth aggregation function requires two parameters, an expression returning aggregation values and a numeric index constant [select nth() from SupportBean]");
    }

    private void runAssertion() {
        String[] fields = "string,int1,int2".split(",");

        epService.getEPRuntime().sendEvent(new SupportBean("G1", 10));
        epService.getEPRuntime().sendEvent(new SupportBean("G2", 11));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean("G1", 12));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields, new Object[][] {{"G1", 12, 10}, {"G2", 11, null}});

        epService.getEPRuntime().sendEvent(new SupportBean("G2", 30));
        epService.getEPRuntime().sendEvent(new SupportBean("G2", 20));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean("G2", 25));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields, new Object[][] {{"G2", 25, 20}});

        epService.getEPRuntime().sendEvent(new SupportBean("G1", -1));
        epService.getEPRuntime().sendEvent(new SupportBean("G1", -2));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean("G2", 8));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields, new Object[][] {{"G1", -2, -1}, {"G2", 8, 25}});
    }

    private void tryInvalid(String epl, String message) {
        try
        {
            epService.getEPAdministrator().createEPL(epl);
            fail();
        }
        catch (EPStatementException ex) {
            assertEquals(message, ex.getMessage());
        }
    }
}