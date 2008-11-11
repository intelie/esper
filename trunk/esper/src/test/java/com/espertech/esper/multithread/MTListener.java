package com.espertech.esper.multithread;

import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.event.EventBean;

import java.util.List;
import java.util.LinkedList;

public class MTListener implements UpdateListener
{
    private final String fieldName;
    private List values;

    public MTListener(String fieldName)
    {
        this.fieldName = fieldName;
        values = new LinkedList();
    }

    public void update(EventBean[] newEvents, EventBean[] oldEvents)
    {
        Object value = newEvents[0].get(fieldName);

        synchronized(values)
        {
            values.add(value);
        }
    }

    public List getValues()
    {
        return values;
    }
}
