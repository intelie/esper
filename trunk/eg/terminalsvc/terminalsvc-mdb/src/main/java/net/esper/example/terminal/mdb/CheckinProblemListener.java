package net.esper.example.terminal.mdb;

import net.esper.client.UpdateListener;
import net.esper.event.EventBean;

public class CheckinProblemListener implements UpdateListener
{
    private OutboundSender outboundSender;

    public CheckinProblemListener(OutboundSender outboundSender)
    {
        this.outboundSender = outboundSender;
    }

    public void update(EventBean[] newEvents, EventBean[] oldEvents)
    {
        String terminal = (String) newEvents[0].get("terminal");
        String message = "Customer checkin problem detected at terminal " + terminal;
        outboundSender.send(message);
    }
}
