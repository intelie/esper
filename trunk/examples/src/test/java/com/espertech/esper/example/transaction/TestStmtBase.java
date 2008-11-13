/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.example.transaction;

import junit.framework.TestCase;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;

public abstract class TestStmtBase extends TestCase
{
    protected EPServiceProvider epService;

    public void setUp()
    {
        Configuration configuration = new Configuration();
        configuration.addEventTypeAlias("TxnEventA", TxnEventA.class.getName());
        configuration.addEventTypeAlias("TxnEventB", TxnEventB.class.getName());
        configuration.addEventTypeAlias("TxnEventC", TxnEventC.class.getName());

        epService = EPServiceProviderManager.getProvider("TestStmtBase", configuration);
        epService.initialize();
    }

    protected void sendEvent(Object event)
    {
        epService.getEPRuntime().sendEvent(event);
    }

}
