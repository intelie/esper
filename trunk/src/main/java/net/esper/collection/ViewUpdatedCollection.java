package net.esper.collection;

import net.esper.event.EventBean;

public interface ViewUpdatedCollection
{
    public void update(EventBean[] newData, EventBean[] oldData);
}
