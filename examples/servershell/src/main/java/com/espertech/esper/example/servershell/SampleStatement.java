/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.example.servershell;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.client.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SampleStatement
{
    private static Log log = LogFactory.getLog(SampleStatement.class);

    public static void createStatement(EPAdministrator admin)
    {
        EPStatement statement = admin.createEPL("select istream ipAddress, avg(duration) from SampleEvent.win:time(10 sec) group by ipAddress output snapshot every 2 seconds order by ipAddress asc");
        statement.addListener(new UpdateListener()
        {
            public void update(EventBean[] newEvents, EventBean[] oldEvents)
            {
                if (newEvents == null)
                {
                    return;
                }
                
                for (int i = 0; i < newEvents.length; i++)
                {
                    if (log.isInfoEnabled())
                    {
                        log.info("IPAddress: " + newEvents[i].get("ipAddress") +
                             " Avg Duration: " + newEvents[i].get("avg(duration)"));
                    }
                }
            }
        });

    }
}
