/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.example.marketdatafeed;

import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.UpdateListener;

public class TicksPerSecondStatement
{
    private EPStatement statement;

    public TicksPerSecondStatement(EPAdministrator admin)
    {
        String stmt = "insert into TicksPerSecond " +
                      "select feed, count(*) as cnt from MarketDataEvent.win:time_batch(1 sec) group by feed";

        statement = admin.createEPL(stmt);
    }

    public void addListener(UpdateListener listener)
    {
        statement.addListener(listener);
    }
}


