package net.esper.example.terminal.mdb;

import junit.framework.TestCase;
import net.esper.example.terminal.common.LowPaper;
import net.esper.example.terminal.common.TerminalInfo;

public class TestEPServiceMDBAdapter extends TestCase
{
    public void testAdapter() throws Exception
    {
        SupportOutboundSender sender = new SupportOutboundSender();
        EPServiceMDBAdapter adapter = new EPServiceMDBAdapter(sender);

        adapter.sendEvent(new LowPaper(new TerminalInfo("t1")));
    }
}
