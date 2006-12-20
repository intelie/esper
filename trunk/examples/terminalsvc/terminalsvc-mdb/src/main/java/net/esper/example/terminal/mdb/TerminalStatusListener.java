package net.esper.example.terminal.mdb;

import net.esper.client.UpdateListener;
import net.esper.event.EventBean;

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
