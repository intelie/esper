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

package com.espertech.esper.regression.pattern;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.core.EPServiceProviderSPI;
import com.espertech.esper.core.StatementType;
import com.espertech.esper.core.EPStatementSPI;
import junit.framework.TestCase;

import java.util.Set;

public class TestPatternStartStop extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;
    private EPStatement patternStmt;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();

        String viewExpr = "every tag=" + SupportBean.class.getName();

        patternStmt = epService.getEPAdministrator().createPattern(viewExpr, "MyPattern");
        assertEquals(StatementType.PATTERN, ((EPStatementSPI) patternStmt).getStatementMetadata().getStatementType());
    }

    public void testStartStop()
    {
        // Pattern started when created
        assertFalse(patternStmt.iterator().hasNext());

        // Stop pattern
        patternStmt.stop();
        sendEvent();
        assertNull(patternStmt.iterator());

        // Start pattern
        patternStmt.start();
        assertFalse(patternStmt.iterator().hasNext());

        // Send event
        SupportBean event = sendEvent();
        assertSame(event, patternStmt.iterator().next().get("tag"));

        // Stop pattern
        patternStmt.stop();
        assertNull(patternStmt.iterator());

        // Start again, iterator is zero
        patternStmt.start();
        assertFalse(patternStmt.iterator().hasNext());

        // assert statement-eventtype reference info
        EPServiceProviderSPI spi = (EPServiceProviderSPI) epService;
        assertTrue(spi.getStatementEventTypeRef().isInUse(SupportBean.class.getName()));
        Set<String> stmtNames = spi.getStatementEventTypeRef().getStatementNamesForType(SupportBean.class.getName());
        assertTrue(stmtNames.contains("MyPattern"));

        patternStmt.destroy();

        assertFalse(spi.getStatementEventTypeRef().isInUse(SupportBean.class.getName()));
        stmtNames = spi.getStatementEventTypeRef().getStatementNamesForType(SupportBean.class.getName());
        assertFalse(stmtNames.contains("MyPattern"));
    }

    public void testAddRemoveListener()
    {
        // Pattern started when created

        // Add listener
        patternStmt.addListener(testListener);
        assertNull(testListener.getLastNewData());
        assertFalse(patternStmt.iterator().hasNext());

        // Send event
        SupportBean event = sendEvent();
        assertEquals(event, testListener.getAndResetLastNewData()[0].get("tag"));
        assertSame(event, patternStmt.iterator().next().get("tag"));

        // Remove listener
        patternStmt.removeListener(testListener);
        event = sendEvent();
        assertSame(event, patternStmt.iterator().next().get("tag"));
        assertNull(testListener.getLastNewData());

        // Add listener back
        patternStmt.addListener(testListener);
        event = sendEvent();
        assertSame(event, patternStmt.iterator().next().get("tag"));
        assertEquals(event, testListener.getAndResetLastNewData()[0].get("tag"));
    }

    private SupportBean sendEvent()
    {
        SupportBean event = new SupportBean();
        epService.getEPRuntime().sendEvent(event);
        return event;
    }
}
