package net.esper.example.terminal.mdb;

import net.esper.client.UpdateListener;
import net.esper.event.EventBean;

public class CountPerTypeListener implements UpdateListener
{
    private OutboundQueueSender outboundQueueSender;

    public CountPerTypeListener(OutboundQueueSender outboundQueueSender)
    {
        this.outboundQueueSender = outboundQueueSender;
    }

    public void update(EventBean[] newEvents, EventBean[] oldEvents)
    {
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < newEvents.length; i++)
        {
            String type = (String) newEvents[i].get("type");
            long count = (Long) newEvents[i].get("countPerType");

            buffer.append("Type ");
            buffer.append(type);
            buffer.append(" counts ");
            buffer.append(count);
            buffer.append("\n");
        }

        outboundQueueSender.send("Current count per type: " + buffer.toString());
    }
}
