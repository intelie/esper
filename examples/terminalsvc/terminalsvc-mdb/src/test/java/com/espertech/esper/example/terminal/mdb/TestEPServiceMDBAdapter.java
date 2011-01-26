/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.example.terminal.mdb;

import junit.framework.TestCase;
import com.espertech.esper.example.terminal.common.LowPaper;
import com.espertech.esper.example.terminal.common.TerminalInfo;

public class TestEPServiceMDBAdapter extends TestCase
{
    public void testAdapter() throws Exception
    {
        SupportOutboundSender sender = new SupportOutboundSender();
        EPServiceMDBAdapter adapter = new EPServiceMDBAdapter(sender);

        adapter.sendEvent(new LowPaper(new TerminalInfo("t1")));
    }
}
