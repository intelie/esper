package net.esper.eql.named;

import net.esper.event.EventBean;

public interface DeletionStrategy
{
    public void matchedDelete(EventBean[] newData);
}
