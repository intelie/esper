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

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportSubscriberMRD;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;

public class TestDistinctWildcardJoinPattern extends TestCase
{
	private static Log log = LogFactory.getLog(TestDistinctWildcardJoinPattern.class);
	
    private EPServiceProvider epService;
    private SupportSubscriberMRD subscriber = new SupportSubscriberMRD();

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
    }

    public void testWildcardJoinPattern() {
        String epl = "select distinct * from " +
                "SupportBean(intPrimitive=0) as fooB unidirectional " +
                "inner join " +
                "pattern [" +
                "every-distinct(fooA.string) fooA=SupportBean(intPrimitive=1)" +
                "->" +
                "every-distinct(wooA.string) wooA=SupportBean(intPrimitive=2)" +
                " where timer:within(1 hour)" +
                "].win:time(1 hour) as fooWooPair " +
                "on fooB.longPrimitive = fooWooPair.fooA.longPrimitive" +
                " order by fooWooPair.wooA.string asc";

        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.setSubscriber(subscriber);

        log.info("Sending event (fooA) starting pattern subexpression...");
        sendEvent("E1", 1, 10L);

        log.info("Sending event (wooA 1) matching pattern subexpression...");
        sendEvent("E2", 2, 10L);

        log.info("Sending event (wooA 2) matching pattern subexpression...");
        sendEvent("E3", 2, 10L);

        log.info("Sending event (fooB) causing join with matched patterns...");
        sendEvent("Query", 0, 10L);
        
        assertTrue(subscriber.isInvoked());
        assertEquals(1, subscriber.getInsertStreamList().size());
        Object[][] inserted = subscriber.getInsertStreamList().get(0);
        assertEquals(2, inserted.length);
        assertEquals("Query", ((SupportBean)inserted[0][0]).getString());
        assertEquals("Query", ((SupportBean)inserted[1][0]).getString());
        Map mapOne = (Map) inserted[0][1];
        assertEquals("E2", ((EventBean)mapOne.get("wooA")).get("string"));
        assertEquals("E1", ((EventBean)mapOne.get("fooA")).get("string"));
        Map mapTwo = (Map) inserted[1][1];
        assertEquals("E3", ((EventBean)mapTwo.get("wooA")).get("string"));
        assertEquals("E1", ((EventBean)mapTwo.get("fooA")).get("string"));
    }

    private void sendEvent(String string, int intPrimitive, long longPrimitive) {
        SupportBean bean = new SupportBean(string, intPrimitive);
        bean.setLongPrimitive(longPrimitive);
        epService.getEPRuntime().sendEvent(bean);
    }
}