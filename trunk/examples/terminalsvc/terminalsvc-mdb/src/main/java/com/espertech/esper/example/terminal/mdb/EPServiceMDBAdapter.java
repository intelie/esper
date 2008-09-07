/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.example.terminal.mdb;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.example.terminal.common.*;

public class EPServiceMDBAdapter
{
    private final EPServiceProvider epService;

    public EPServiceMDBAdapter(OutboundSender outboundSender)
    {
        Configuration config = new Configuration();
        config.addEventTypeAlias("Checkin", Checkin.class);
        config.addEventTypeAlias("Cancelled", Cancelled.class);
        config.addEventTypeAlias("Completed", Completed.class);
        config.addEventTypeAlias("Status", Status.class);
        config.addEventTypeAlias("LowPaper", LowPaper.class);
        config.addEventTypeAlias("OutOfOrder", OutOfOrder.class);
        config.addEventTypeAlias("BaseTerminalEvent", BaseTerminalEvent.class);

        // Get engine instance - same engine instance for all MDB instances
        epService = EPServiceProviderManager.getDefaultProvider(config);
        System.out.println(TerminalMDB.class.getName() + "::instance this=" + this.toString() + " engine=" + epService.toString());

        EPStatement statement = null;
        String stmt = null;

        stmt = "select a.term.id as terminal from pattern [ every a=Checkin -> " +
                "      ( OutOfOrder(term.id=a.term.id) and not (Cancelled(term.id=a.term.id) or Completed(term.id=a.term.id)) )]";
        statement = epService.getEPAdministrator().createEPL(stmt);
        statement.addListener(new CheckinProblemListener(outboundSender));

        stmt = "select * from BaseTerminalEvent where type = 'LowPaper' or type = 'OutOfOrder'";
        statement = epService.getEPAdministrator().createEPL(stmt);
        statement.addListener(new TerminalEventListener(outboundSender));

        stmt = "select '1' as terminal, 'terminal is offline' as text from pattern [ every timer:interval(60 seconds) -> (timer:interval(65 seconds) and not Status(term.id = 'T1')) ] output first every 5 minutes";
        statement = epService.getEPAdministrator().createEPL(stmt);
        statement.addListener(new TerminalStatusListener(outboundSender));

        stmt = "insert into CountPerType " +
                "select type, count(*) as countPerType " +
                "from BaseTerminalEvent.win:time(10 min) " +
                "group by type " +
                "output all every 1 minutes";
        statement = epService.getEPAdministrator().createEPL(stmt);
        statement.addListener(new CountPerTypeListener(outboundSender));
    }

    public void sendEvent(Object event)
    {
        synchronized (epService)
        {
            epService.getEPRuntime().sendEvent(event);
        }
    }

    public EPServiceProvider getEpService()
    {
        return epService;
    }
}
