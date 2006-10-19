package net.esper.example.terminal.mdb;

import net.esper.client.UpdateListener;
import net.esper.event.EventBean;

public class TerminalEventListener implements UpdateListener
{
    private OutboundQueueSender outboundQueueSender;

    public TerminalEventListener(OutboundQueueSender outboundQueueSender)
    {
        this.outboundQueueSender = outboundQueueSender;
    }

    public void update(EventBean[] newEvents, EventBean[] oldEvents)
    {
        String terminal = (String) newEvents[0].get("term.id");
        String type = (String) newEvents[0].get("type");
        String message = "Terminal " + terminal + " raised an " + type + " event";
        outboundQueueSender.send(message);
    }
}
