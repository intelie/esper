/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.example.terminal.mdb;

import com.espertech.esper.client.*;

public class TerminalStatusListener implements UpdateListener
{
    private OutboundSender outboundSender;

    public TerminalStatusListener(OutboundSender outboundSender)
    {
        this.outboundSender = outboundSender;
    }

    public void update(EventBean[] newEvents, EventBean[] oldEvents)
    {
        String terminal = (String) newEvents[0].get("terminal");
        String text = (String) newEvents[0].get("text");
        String message = "Terminal " + terminal + " detected '" + text + "'";
        outboundSender.send(message);
    }
}
