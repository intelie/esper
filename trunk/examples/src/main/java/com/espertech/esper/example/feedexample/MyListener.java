package com.espertech.esper.example.feedexample;

import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.event.EventBean;

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
