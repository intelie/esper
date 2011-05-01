/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.example.terminal.jse.listener;

import com.espertech.esper.client.EventBean;

public class TerminalEventListener extends BaseTerminalListener {

    public TerminalEventListener(ComplexEventListener complexEventListener) {
        super(complexEventListener);
    }

    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        String terminal = (String) newEvents[0].get("terminal.id");
        String type = (String) newEvents[0].get("type");
        String message = "Terminal " + terminal + " raised a " + type + " event";
        complexEventListener.onComplexEvent(message);
    }
}
