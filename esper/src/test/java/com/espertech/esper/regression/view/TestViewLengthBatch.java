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
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;

public class TestViewLengthBatch extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;
    private SupportBean events[];

    public void setUp()
    {
        listener = new SupportUpdateListener();
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getViewResources().setAllowMultipleExpiryPolicies(true);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        events = new SupportBean[100];
        for (int i = 0; i < events.length; i++)
        {
            events[i] = new SupportBean();
        }
    }

    public void testLengthBatchSize2()
    {
        EPStatement stmt = epService.getEPAdministrator().createEPL(
                "select irstream * from " + SupportBean.class.getName() + ".win:length_batch(2)");
        stmt.addListener(listener);

        sendEvent(events[0]);
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), new SupportBean[] {events[0] });

        sendEvent(events[1]);
        listener.assertUnderlyingAndReset(new SupportBean[] {events[0], events[1]}, null);
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), null);

        sendEvent(events[2]);
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), new SupportBean[] {events[2] });

        sendEvent(events[3]);
        listener.assertUnderlyingAndReset(new SupportBean[] {events[2], events[3]}, new SupportBean[] {events[0], events[1]});
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), null);

        sendEvent(events[4]);
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), new SupportBean[] {events[4] });

        sendEvent(events[5]);
        listener.assertUnderlyingAndReset(new SupportBean[] {events[4], events[5]}, new SupportBean[] {events[2], events[3]});
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), null);
    }

    public void testLengthBatchSize1()
    {
        EPStatement stmt = epService.getEPAdministrator().createEPL(
                "select irstream * from " + SupportBean.class.getName() + ".win:length_batch(1)");
        stmt.addListener(listener);

        sendEvent(events[0]);
        listener.assertUnderlyingAndReset(new SupportBean[] {events[0]}, null);
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), null);

        sendEvent(events[1]);
        listener.assertUnderlyingAndReset(new SupportBean[] {events[1]}, new SupportBean[] {events[0]});
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), null);

        sendEvent(events[2]);
        listener.assertUnderlyingAndReset(new SupportBean[] {events[2]}, new SupportBean[] {events[1]});
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), null);
    }

    public void testLengthBatchSize3()
    {
        EPStatement stmt = epService.getEPAdministrator().createEPL(
                "select irstream * from " + SupportBean.class.getName() + ".win:length_batch(3)");
        stmt.addListener(listener);

        sendEvent(events[0]);
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), new SupportBean[] {events[0] });

        sendEvent(events[1]);
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), new SupportBean[] {events[0], events[1]});

        sendEvent(events[2]);
        listener.assertUnderlyingAndReset(new SupportBean[] {events[0], events[1], events[2]}, null);
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), null);

        sendEvent(events[3]);
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), new SupportBean[] {events[3]});

        sendEvent(events[4]);
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), new SupportBean[] {events[3], events[4]});

        sendEvent(events[5]);
        listener.assertUnderlyingAndReset(new SupportBean[] {events[3], events[4], events[5]}, new SupportBean[] {events[0], events[1], events[2]});
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), null);
    }

    public void testLengthBatchSize3And2Staggered()
    {
        EPStatement stmt = epService.getEPAdministrator().createEPL(
                "select irstream * from " + SupportBean.class.getName() + ".win:length_batch(3).win:length_batch(2)");
        stmt.addListener(listener);

        sendEvent(events[0]);
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), null);

        sendEvent(events[1]);
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), null);

        sendEvent(events[2]);
        listener.assertUnderlyingAndReset(new SupportBean[] {events[0], events[1], events[2]}, null);
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), null);

        sendEvent(events[3]);
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), null);

        sendEvent(events[4]);
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), null);

        sendEvent(events[5]);
        listener.assertUnderlyingAndReset(new SupportBean[] {events[3], events[4], events[5]}, new SupportBean[] {events[0], events[1], events[2]});
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), null);
    }

    public void testInvalid()
    {
        try
        {
            epService.getEPAdministrator().createEPL(
                "select * from " + SupportMarketDataBean.class.getName() + ".win:length_batch(0)");
            fail();
        }
        catch (Exception ex)
        {
            // expected
        }
    }

    private void sendEvent(SupportBean event)
    {
        epService.getEPRuntime().sendEvent(event);
    }
}
