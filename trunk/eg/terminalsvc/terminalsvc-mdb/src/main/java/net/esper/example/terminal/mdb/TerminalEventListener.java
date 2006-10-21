package net.esper.example.terminal.mdb;

import net.esper.client.UpdateListener;
import net.esper.event.EventBean;

public class TerminalEventListener implements UpdateListener
{
    private OutboundSender outboundSender;

    public TerminalEventListener(OutboundSender outboundSender)
    {
        this.outboundSender = outboundSender;
    }

    public void update(EventBean[] newEvents, EventBean[] oldEvents)
    {
        String terminal = (String) newEvents[0].get("term.id");
        String type = (String) newEvents[0].get("type");
        String message = "Terminal " + terminal + " raised an " + type + " event";
        outboundSender.send(message);
    }
}
