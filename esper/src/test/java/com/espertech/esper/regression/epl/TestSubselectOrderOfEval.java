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
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

public class TestSubselectOrderOfEval extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void testCorrelatedSubqueryOrder() {
        // ESPER-564

        Configuration config = new Configuration();
        config.getEngineDefaults().getViewResources().setShareViews(true);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.getEPAdministrator().getConfiguration().addEventType("TradeEvent", TradeEvent.class);
        listener = new SupportUpdateListener();

        epService.getEPAdministrator().createEPL("select * from TradeEvent.std:lastevent()");

        epService.getEPAdministrator().createEPL(
                "select window(tl.*) as longItems, " +
                "       (SELECT window(ts.*) AS shortItems FROM TradeEvent.win:time(20 minutes) as ts WHERE ts.securityID=tl.securityID) " +
                "from TradeEvent.win:time(20 minutes) as tl " +
                "where tl.securityID = 1000" +
                "group by tl.securityID "
        ).addListener(listener);
		
        epService.getEPRuntime().sendEvent(new TradeEvent(System.currentTimeMillis(), 1000, 50, 1));
        assertEquals(1, ((Object[]) listener.assertOneGetNew().get("longItems")).length);
        assertEquals(1, ((Object[]) listener.assertOneGetNew().get("shortItems")).length);
        listener.reset();

        epService.getEPRuntime().sendEvent(new TradeEvent(System.currentTimeMillis() + 10, 1000, 50, 1));
        assertEquals(2, ((Object[]) listener.assertOneGetNew().get("longItems")).length);
        assertEquals(2, ((Object[]) listener.assertOneGetNew().get("shortItems")).length);
    }

    public void testOrderOfEvaluationSubselectFirst()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getExpression().setSelfSubselectPreeval(true);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();

        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);

        String viewExpr = "select * from SupportBean(intPrimitive<10) where intPrimitive not in (select intPrimitive from SupportBean.std:unique(intPrimitive))";
        EPStatement stmtOne = epService.getEPAdministrator().createEPL(viewExpr);
        stmtOne.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 5));
        assertFalse(listener.getAndClearIsInvoked());

        stmtOne.destroy();

        String viewExprTwo = "select * from SupportBean where intPrimitive not in (select intPrimitive from SupportBean(intPrimitive<10).std:unique(intPrimitive))";
        EPStatement stmtTwo = epService.getEPAdministrator().createEPL(viewExprTwo);
        stmtTwo.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 5));
        assertFalse(listener.getAndClearIsInvoked());
    }

    public void testOrderOfEvaluationSubselectLast()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getExpression().setSelfSubselectPreeval(false);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();

        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);

        String viewExpr = "select * from SupportBean(intPrimitive<10) where intPrimitive not in (select intPrimitive from SupportBean.std:unique(intPrimitive))";
        EPStatement stmtOne = epService.getEPAdministrator().createEPL(viewExpr);
        stmtOne.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 5));
        assertTrue(listener.getAndClearIsInvoked());

        stmtOne.destroy();

        String viewExprTwo = "select * from SupportBean where intPrimitive not in (select intPrimitive from SupportBean(intPrimitive<10).std:unique(intPrimitive))";
        EPStatement stmtTwo = epService.getEPAdministrator().createEPL(viewExprTwo);
        stmtTwo.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 5));
        assertTrue(listener.getAndClearIsInvoked());
    }

    public static class TradeEvent {
        private long time;
        private int securityID;
        private double price;
        private long volume;

        public TradeEvent(long time, int securityID, double price, long volume) {
            this.time = time;
            this.securityID = securityID;
            this.price = price;
            this.volume = volume;
        }

        public int getSecurityID() {
            return securityID;
        }

        public long getTime() {
            return time;
        }

        public double getPrice() {
            return price;
        }

        public long getVolume() {
            return volume;
        }
    }
}