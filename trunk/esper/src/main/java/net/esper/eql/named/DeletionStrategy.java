package net.esper.eql.named;

import net.esper.event.EventBean;

public interface DeletionStrategy
{
    public EventBean[] determineRemoveStream(EventBean[] newData);
}
