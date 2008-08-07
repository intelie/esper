package com.espertech.esper.example.terminal.mdb;

import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.event.EventBean;

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
