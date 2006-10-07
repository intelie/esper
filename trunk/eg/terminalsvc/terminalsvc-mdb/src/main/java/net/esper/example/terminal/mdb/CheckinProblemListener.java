package net.esper.example.terminal.mdb;

import net.esper.client.UpdateListener;
import net.esper.event.EventBean;

public class CheckinProblemListener implements UpdateListener
{
    private OutboundQueueSender outboundQueueSender;

    public CheckinProblemListener(OutboundQueueSender outboundQueueSender)
    {
        this.outboundQueueSender = outboundQueueSender;
    }

    public void update(EventBean[] newEvents, EventBean[] oldEvents)
    {
        String terminal = (String) newEvents[0].get("terminal");
        String message = "Customer checkin problem detected at terminal " + terminal;
        outboundQueueSender.send(message);
    }
}
