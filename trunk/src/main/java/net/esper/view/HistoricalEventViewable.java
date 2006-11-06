package net.esper.view;

import net.esper.event.EventBean;
import net.esper.collection.MultiKey;

import java.util.Set;

public interface HistoricalEventViewable extends Viewable
{
    public void poll(EventBean[] lookupEvents, Set<MultiKey<EventBean>> joinSet);
}
