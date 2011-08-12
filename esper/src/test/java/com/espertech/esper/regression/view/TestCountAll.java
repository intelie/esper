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

import com.espertech.esper.support.util.ArrayAssertionUtil;
import junit.framework.TestCase;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;

public class TestCountAll extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;
    private EPStatement selectTestView;

    public void setUp()
    {
        listener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
    }

    public void testSize()
    {
        String statementText = "select irstream size from " + SupportMarketDataBean.class.getName() + ".std:size()";
        selectTestView = epService.getEPAdministrator().createEPL(statementText);
        selectTestView.addListener(listener);

        sendEvent("DELL", 1L);
        assertSize(1, 0);

        sendEvent("DELL", 1L);
        assertSize(2, 1);

        selectTestView.destroy();
        statementText = "select size, symbol, feed from " + SupportMarketDataBean.class.getName() + ".std:size(symbol, feed)";
        selectTestView = epService.getEPAdministrator().createEPL(statementText);
        selectTestView.addListener(listener);
        String[] fields = "size,symbol,feed".split(",");

        sendEvent("DELL", 1L);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {1L, "DELL", "f1"});

        sendEvent("DELL", 1L);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {2L, "DELL", "f1"});
    }

    public void testCountPlusStar()
    {
        // Test for ESPER-118
        String statementText = "select *, count(*) as cnt from " + SupportMarketDataBean.class.getName();
        selectTestView = epService.getEPAdministrator().createEPL(statementText);
        selectTestView.addListener(listener);

        sendEvent("S0", 1L);
        assertTrue(listener.getAndClearIsInvoked());
        assertEquals(1, listener.getLastNewData().length);
        assertEquals(1L, listener.getLastNewData()[0].get("cnt"));
        assertEquals("S0", listener.getLastNewData()[0].get("symbol"));

        sendEvent("S1", 1L);
        assertTrue(listener.getAndClearIsInvoked());
        assertEquals(1, listener.getLastNewData().length);
        assertEquals(2L, listener.getLastNewData()[0].get("cnt"));
        assertEquals("S1", listener.getLastNewData()[0].get("symbol"));

        sendEvent("S2", 1L);
        assertTrue(listener.getAndClearIsInvoked());
        assertEquals(1, listener.getLastNewData().length);
        assertEquals(3L, listener.getLastNewData()[0].get("cnt"));
        assertEquals("S2", listener.getLastNewData()[0].get("symbol"));
    }

    public void testCount()
    {
    	String statementText = "select count(*) as cnt from " + SupportMarketDataBean.class.getName() + ".win:time(1)";
        selectTestView = epService.getEPAdministrator().createEPL(statementText);
        selectTestView.addListener(listener);
       
        sendEvent("DELL", 1L);
        assertTrue(listener.getAndClearIsInvoked());
        assertEquals(1, listener.getLastNewData().length);
        assertEquals(1L, listener.getLastNewData()[0].get("cnt"));
        
        sendEvent("DELL", 1L);
        assertTrue(listener.getAndClearIsInvoked());
        assertEquals(1, listener.getLastNewData().length);
        assertEquals(2L, listener.getLastNewData()[0].get("cnt"));
        
        sendEvent("DELL", 1L);
        assertTrue(listener.getAndClearIsInvoked());
        assertEquals(1, listener.getLastNewData().length);
        assertEquals(3L, listener.getLastNewData()[0].get("cnt"));
    }

    public void testCountHaving()
    {
        String event = SupportBean.class.getName();
        String statementText = "select irstream sum(intPrimitive) as mysum from " + event + " having sum(intPrimitive) = 2";
        selectTestView = epService.getEPAdministrator().createEPL(statementText);
        selectTestView.addListener(listener);

        sendEvent();
        assertFalse(listener.getAndClearIsInvoked());
        sendEvent();
        assertEquals(2, listener.assertOneGetNewAndReset().get("mysum"));
        sendEvent();
        assertEquals(2, listener.assertOneGetOldAndReset().get("mysum"));
    }

    public void testSumHaving()
    {
        String event = SupportBean.class.getName();
        String statementText = "select irstream count(*) as mysum from " + event + " having count(*) = 2";
        selectTestView = epService.getEPAdministrator().createEPL(statementText);
        selectTestView.addListener(listener);

        sendEvent();
        assertFalse(listener.getAndClearIsInvoked());
        sendEvent();
        assertEquals(2L, listener.assertOneGetNewAndReset().get("mysum"));
        sendEvent();
        assertEquals(2L, listener.assertOneGetOldAndReset().get("mysum"));
    }

    private void sendEvent(String symbol, Long volume)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, 0, volume, "f1");
        epService.getEPRuntime().sendEvent(bean);
    }

    private void sendEvent()
    {
        SupportBean bean = new SupportBean("", 1);
        epService.getEPRuntime().sendEvent(bean);
    }

    private void assertSize(long newSize, long oldSize)
    {
        listener.assertFieldEqualsAndReset("size", new Object[] {newSize}, new Object[] {oldSize});
    }
}
