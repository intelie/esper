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
import junit.framework.TestCase;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.client.SupportConfigFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TestViewWhereClause extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;
    private EPStatement stmt;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();

        String viewExpr = "select * from " + SupportMarketDataBean.class.getName() + ".win:length(3) where symbol='CSCO'";
        stmt = epService.getEPAdministrator().createEPL(viewExpr);
        listener = new SupportUpdateListener();
        stmt.addListener(listener);
    }
    
    public void testWhere()
    {
        sendMarketDataEvent("IBM");
        assertFalse(listener.getAndClearIsInvoked());

        sendMarketDataEvent("CSCO");
        assertTrue(listener.getAndClearIsInvoked());

        sendMarketDataEvent("IBM");
        assertFalse(listener.getAndClearIsInvoked());

        sendMarketDataEvent("CSCO");
        assertTrue(listener.getAndClearIsInvoked());
        
        // invalid return type for filter during compilation time
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        try {
            epService.getEPAdministrator().createEPL("Select string From SupportBean.win:time(30 seconds) where intPrimitive group by string");
            fail();
        }
        catch (EPStatementException ex) {
            assertEquals("Error validating expression: The where-clause filter expression must return a boolean value [Select string From SupportBean.win:time(30 seconds) where intPrimitive group by string]", ex.getMessage());
        }

        // invalid return type for filter at runtime
        Map<String, Object> dict = new HashMap<String, Object>();
        dict.put("criteria", Boolean.class);
        epService.getEPAdministrator().getConfiguration().addEventType("MapEvent", dict);
        EPStatement stmt = epService.getEPAdministrator().createEPL("Select * From MapEvent.win:time(30 seconds) where criteria");

        try {
            epService.getEPRuntime().sendEvent(Collections.singletonMap("criteria", 15), "MapEvent");
            fail(); // ensure exception handler rethrows
        }
        catch (EPException ex) {
            // fine
        }
        stmt.destroy();
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
    }

    public void testWhereNumericType()
    {
        String viewExpr = "select " +
                " intPrimitive + longPrimitive as p1," +
                " intPrimitive * doublePrimitive as p2," +
                " floatPrimitive / doublePrimitive as p3" +
                " from " + SupportBean.class.getName() + ".win:length(3) where " +
                "intPrimitive=longPrimitive and intPrimitive=doublePrimitive and floatPrimitive=doublePrimitive";

        stmt = epService.getEPAdministrator().createEPL(viewExpr);
        listener = new SupportUpdateListener();
        stmt.addListener(listener);

        sendSupportBeanEvent(1,2,3,4);
        assertFalse(listener.getAndClearIsInvoked());

        sendSupportBeanEvent(2, 2, 2, 2);
        EventBean event = listener.getAndResetLastNewData()[0];
        assertEquals(Long.class, event.getEventType().getPropertyType("p1"));
        assertEquals(4l, event.get("p1"));
        assertEquals(Double.class, event.getEventType().getPropertyType("p2"));
        assertEquals(4d, event.get("p2"));
        assertEquals(Double.class, event.getEventType().getPropertyType("p3"));
        assertEquals(1d, event.get("p3"));
    }

    private void sendMarketDataEvent(String symbol)
    {
        SupportMarketDataBean event = new SupportMarketDataBean(symbol, 0, 0L, "");
        epService.getEPRuntime().sendEvent(event);
    }

    private void sendSupportBeanEvent(int intPrimitive, long longPrimitive, float floatPrimitive, double doublePrimitive)
    {
        SupportBean event = new SupportBean();
        event.setIntPrimitive(intPrimitive);
        event.setLongPrimitive(longPrimitive);
        event.setFloatPrimitive(floatPrimitive);
        event.setDoublePrimitive(doublePrimitive);
        epService.getEPRuntime().sendEvent(event);
    }
}
