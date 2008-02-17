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
