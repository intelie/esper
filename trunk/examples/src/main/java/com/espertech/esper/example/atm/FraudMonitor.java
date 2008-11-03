/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.example.atm;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;

/**
 * Demonstrates a simple join between fraud warning and withdrawal event streams.
 *
 * See the unit test with the same name for any example events generated to test this example.
 */
public class FraudMonitor
{
    private EPStatement joinView;

    public FraudMonitor(EPServiceProvider epService, UpdateListener updateListener)
    {
        String joinStatement = "select fraud.accountNumber as accountNumber, fraud.warning as warning, withdraw.amount as amount, " +
                               "max(fraud.timestamp, withdraw.timestamp) as timestamp, 'withdrawlFraudWarn' as descr from " +
                                    "FraudWarning.win:time(30 min) as fraud," +
                                    "Withdrawal.win:time(30 sec) as withdraw" +
                " where fraud.accountNumber = withdraw.accountNumber";

        joinView = epService.getEPAdministrator().createEPL(joinStatement);
        joinView.addListener(updateListener);
    }
}