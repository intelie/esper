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

import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_S0;
import com.espertech.esper.support.bean.SupportBean_S1;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

public class TestSubselectAggregation extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();        
        config.addEventType("SupportBean", SupportBean.class);
        config.addEventType("S0", SupportBean_S0.class);
        config.addEventType("S1", SupportBean_S1.class);
        config.addEventType("MarketData", SupportMarketDataBean.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testCorrelatedAggregationSelectEquals()
    {
        String stmtText = "select p00, " +
                "(select sum(intPrimitive) from SupportBean.win:keepall() where string = s0.p00) as sump00 " +
                "from S0 as s0";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);
        String[] fields = "p00,sump00".split(",");

        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, "T1"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"T1", null});

        epService.getEPRuntime().sendEvent(new SupportBean("T1", 10));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(2, "T1"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"T1", 10});

        epService.getEPRuntime().sendEvent(new SupportBean("T1", 11));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(3, "T1"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"T1", 21});

        epService.getEPRuntime().sendEvent(new SupportBean_S0(4, "T2"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"T2", null});

        epService.getEPRuntime().sendEvent(new SupportBean("T2", -2));
        epService.getEPRuntime().sendEvent(new SupportBean("T2", -7));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(5, "T2"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"T2", -9});
    }

    public void testCorrelatedAggregationWhereGreater()
    {
        String stmtText = "select p00 from S0 as s0 where id > " +
                "(select sum(intPrimitive) from SupportBean.win:keepall() where string = s0.p00)";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        runAssertionCorrAggWhereGreater();

        stmtText = "select p00 from S0 as s0 where id > " +
                "(select sum(intPrimitive) from SupportBean.win:keepall() where string||'X' = s0.p00||'X')";
        stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        runAssertionCorrAggWhereGreater();
    }

    private void runAssertionCorrAggWhereGreater() {
        String[] fields = "p00".split(",");

        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, "T1"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean("T1", 10));

        epService.getEPRuntime().sendEvent(new SupportBean_S0(10, "T1"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S0(11, "T1"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"T1"});

        epService.getEPRuntime().sendEvent(new SupportBean("T1", 11));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(21, "T1"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S0(22, "T1"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"T1"});
    }

    public void testPriceMap()
    {
        String stmtText = "select * from MarketData " +
                "where price > (select max(price) from MarketData(symbol='GOOG').std:lastevent()) ";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        sendEventMD("GOOG", 1);
        assertFalse(listener.isInvoked());

        sendEventMD("GOOG", 2);
        assertFalse(listener.isInvoked());

        Object event = sendEventMD("IBM", 3);
        assertEquals(event, listener.assertOneGetNewAndReset().getUnderlying());
    }

    public void testCorrelatedPropertiesSelected()
    {
        String stmtText = "select (select s0.id + max(s1.id) from S1.win:length(3) as s1) as value from S0 as s0";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        sendEventS0(1);
        assertEquals(null, listener.assertOneGetNewAndReset().get("value"));

        sendEventS1(100);
        sendEventS0(2);
        assertEquals(102, listener.assertOneGetNewAndReset().get("value"));

        sendEventS1(30);
        sendEventS0(3);
        assertEquals(103, listener.assertOneGetNewAndReset().get("value"));
    }

    public void testExists()
    {
        String stmtText = "select id from S0 where exists (select max(id) from S1.win:length(3))";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        sendEventS0(1);
        assertEquals(1, listener.assertOneGetNewAndReset().get("id"));

        sendEventS1(100);
        sendEventS0(2);
        assertEquals(2, listener.assertOneGetNewAndReset().get("id"));
    }

    public void testIn()
    {
        String stmtText = "select id from S0 where id in (select max(id) from S1.win:length(2))";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        sendEventS0(1);
        assertFalse(listener.isInvoked());

        sendEventS1(100);
        sendEventS0(2);
        assertFalse(listener.isInvoked());

        sendEventS0(100);
        assertEquals(100, listener.assertOneGetNewAndReset().get("id"));

        sendEventS0(200);
        assertFalse(listener.isInvoked());

        sendEventS1(-1);
        sendEventS1(-1);
        sendEventS0(-1);
        assertEquals(-1, listener.assertOneGetNewAndReset().get("id"));
    }

    public void testMaxUnfiltered()
    {
        String stmtText = "select (select max(id) from S1.win:length(3)) as value from S0";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);
        
        sendEventS0(1);
        assertEquals(null, listener.assertOneGetNewAndReset().get("value"));

        sendEventS1(100);
        sendEventS0(2);
        assertEquals(100, listener.assertOneGetNewAndReset().get("value"));

        sendEventS1(200);
        sendEventS0(3);
        assertEquals(200, listener.assertOneGetNewAndReset().get("value"));

        sendEventS1(190);
        sendEventS0(4);
        assertEquals(200, listener.assertOneGetNewAndReset().get("value"));

        sendEventS1(180);
        sendEventS0(5);
        assertEquals(200, listener.assertOneGetNewAndReset().get("value"));

        sendEventS1(170);   // note event leaving window
        sendEventS0(6);
        assertEquals(190, listener.assertOneGetNewAndReset().get("value"));
    }

    public void testAvgMaxStopStart()
    {
        String stmtText = "select (select avg(id) + max(id) from S1.win:length(3)) as value from S0";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        sendEventS0(1);
        assertEquals(null, listener.assertOneGetNewAndReset().get("value"));

        sendEventS1(100);
        sendEventS0(2);
        assertEquals(200.0, listener.assertOneGetNewAndReset().get("value"));

        sendEventS1(200);
        sendEventS0(3);
        assertEquals(350.0, listener.assertOneGetNewAndReset().get("value"));

        stmt.stop();
        sendEventS1(10000);
        sendEventS0(4);
        assertFalse(listener.isInvoked());
        stmt.start();

        sendEventS1(10);
        sendEventS0(5);
        assertEquals(20.0, listener.assertOneGetNewAndReset().get("value"));
    }

    public void testSumFilteredEvent()
    {
        String stmtText = "select (select sum(id) from S1(id < 0).win:length(3)) as value from S0";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        runAssertionSumFilter();
    }

    public void testSumFilteredWhere()
    {
        String stmtText = "select (select sum(id) from S1.win:length(3) where id < 0) as value from S0";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        runAssertionSumFilter();
    }

    public void testInvalid()
    {
        tryInvalid("", "Unexpected end of input []");

        String stmtText = "select (select sum(s0.id) from S1.win:length(3) as s1) as value from S0 as s0";
        tryInvalid(stmtText, "Error starting statement: Subselect aggregation functions cannot aggregate across correlated properties [select (select sum(s0.id) from S1.win:length(3) as s1) as value from S0 as s0]");

        stmtText = "select (select s1.id + sum(s1.id) from S1.win:length(3) as s1) as value from S0 as s0";
        tryInvalid(stmtText, "Error starting statement: Subselect properties must all be within aggregation functions [select (select s1.id + sum(s1.id) from S1.win:length(3) as s1) as value from S0 as s0]");

        stmtText = "select (select sum(s0.id + s1.id) from S1.win:length(3) as s1) as value from S0 as s0";
        tryInvalid(stmtText, "Error starting statement: Subselect aggregation functions cannot aggregate across correlated properties [select (select sum(s0.id + s1.id) from S1.win:length(3) as s1) as value from S0 as s0]");
    }

    private void tryInvalid(String stmtText, String message)
    {
        try
        {
            epService.getEPAdministrator().createEPL(stmtText);
            fail();
        }
        catch (EPStatementException ex)
        {
            assertEquals(message, ex.getMessage());
        }
    }
    
    private void runAssertionSumFilter()
    {
        sendEventS0(1);
        assertEquals(null, listener.assertOneGetNewAndReset().get("value"));

        sendEventS1(1);
        sendEventS0(2);
        assertEquals(null, listener.assertOneGetNewAndReset().get("value"));

        sendEventS1(0);
        sendEventS0(3);
        assertEquals(null, listener.assertOneGetNewAndReset().get("value"));

        sendEventS1(-1);
        sendEventS0(4);
        assertEquals(-1, listener.assertOneGetNewAndReset().get("value"));

        sendEventS1(-3);
        sendEventS0(5);
        assertEquals(-4, listener.assertOneGetNewAndReset().get("value"));

        sendEventS1(-5);
        sendEventS0(6);
        assertEquals(-9, listener.assertOneGetNewAndReset().get("value"));

        sendEventS1(-2);   // note event leaving window
        sendEventS0(6);
        assertEquals(-10, listener.assertOneGetNewAndReset().get("value"));
    }

    private void sendEventS0(int id)
    {
        epService.getEPRuntime().sendEvent(new SupportBean_S0(id));
    }

    private void sendEventS1(int id)
    {
        epService.getEPRuntime().sendEvent(new SupportBean_S1(id));
    }

    private Object sendEventMD(String symbol, double price)
    {
        Object event = new SupportMarketDataBean(symbol, price, 0L, "");
        epService.getEPRuntime().sendEvent(event);
        return event;
    }
}
