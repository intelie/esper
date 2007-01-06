package net.esper.multithread;

import net.esper.client.UpdateListener;
import net.esper.event.EventBean;

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
