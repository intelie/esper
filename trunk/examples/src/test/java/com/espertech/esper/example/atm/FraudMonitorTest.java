/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.example.atm;

import junit.framework.TestCase;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.time.TimerControlEvent;
import com.espertech.esper.example.support.SupportUpdateListener;
import com.espertech.esper.event.EventBean;

public class FraudMonitorTest extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        Configuration configuration = new Configuration();
        configuration.addEventTypeAlias("FraudWarning", FraudWarning.class.getName());
        configuration.addEventTypeAlias("Withdrawal", Withdrawal.class.getName());

        epService = EPServiceProviderManager.getProvider("FraudMonitorTest", configuration);
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));

        this.listener = new SupportUpdateListener();
        new FraudMonitor(epService, listener);
    }

    public void testJoin()
    {
        final String FRAUD_TEXT = "card reported stolen";

        sendWithdrawal(1001, 100);
        sendFraudWarn(1004, FRAUD_TEXT);
        sendWithdrawal(1001, 60);
        sendWithdrawal(1002, 400);
        sendWithdrawal(1001, 300);

        assertFalse(listener.getAndClearIsInvoked());

        sendWithdrawal(1004, 100);
        assertTrue(listener.getAndClearIsInvoked());

        assertEquals(1, listener.getLastNewData().length);
        EventBean event = listener.getLastNewData()[0];
        assertEquals(1004L, event.get("accountNumber"));
        assertEquals(FRAUD_TEXT, event.get("warning"));
        assertEquals(100, event.get("amount"));
        assertTrue( ((Long) event.get("timestamp")) > (System.currentTimeMillis() - 100));
        assertEquals("withdrawlFraudWarn", event.get("descr"));
    }

    private void sendWithdrawal(long acctNum, int amount)
    {
        Withdrawal event = new Withdrawal(acctNum, amount);
        epService.getEPRuntime().sendEvent(event);
    }

    private void sendFraudWarn(long acctNum, String text)
    {
        FraudWarning event = new FraudWarning(acctNum, text);
        epService.getEPRuntime().sendEvent(event);
    }
}
