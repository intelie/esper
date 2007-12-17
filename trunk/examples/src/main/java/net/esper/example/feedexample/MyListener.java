package net.esper.example.feedexample;

import net.esper.client.UpdateListener;
import net.esper.event.EventBean;

public class MyListener implements UpdateListener
{
    public void update(EventBean[] newEvents, EventBean[] oldEvents)
    {
        for (EventBean event : newEvents)
        {
            System.out.println("feed " + event.get("feed") +
             " is count " + event.get("cnt"));
        }
    }
}
