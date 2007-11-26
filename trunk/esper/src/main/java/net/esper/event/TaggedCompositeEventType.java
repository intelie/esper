package net.esper.event;

import java.util.Map;

public interface TaggedCompositeEventType
{
    public Map<String, EventType> getTaggedEventTypes();
}
